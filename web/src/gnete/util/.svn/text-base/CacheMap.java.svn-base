package gnete.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * 简单缓存Map工具，元素经过指定时间（秒)后会失效（移除）。<br/>
 * 【本类中的方法不做做同步限制，如需要同步限制，可以通过外层的双重判断锁进行同步put处理】
 * @author KANG
 */
public class CacheMap<K, V> {

	private LinkedHashMap<K, ValueBean>	valueBeanMap	= new LinkedHashMap<K, ValueBean>();	// 保持插入的先后顺序

	private int							cacheTime;

	/**
	 * @param cacheTime
	 *            缓存时间（秒）
	 */
	public CacheMap(int cacheTime) {
		super();
		this.cacheTime = cacheTime;
	}

	public void put(K key, V value) {
		processOldBean();
		if (valueBeanMap.containsKey(key)) {
			valueBeanMap.remove(key);// 先移除，主要是保持插入的先后顺序
		}
		valueBeanMap.put(key, new ValueBean(value));
	}

	private void processOldBean() {
		Iterator<Entry<K, ValueBean>> iter = valueBeanMap.entrySet().iterator();
		while (iter.hasNext()) {
			if (iter.next().getValue().isExpired()) {
				iter.remove();
			} else {
				/*
				 * 因为使用LinkedHashMap保存的ValueBean，如果当前ValueBean没有过期，那么该节点之后的ValueBean必然也是没有过期的， 所以用break跳出循环。
				 */
				break;
			}
		}
	}

	/**
	 * 获得缓存的Value，如果key未缓存或缓存的值为null，都将返回null
	 */
	public V get(K key) {
		ValueBean bean = getValueBean(key);
		if (null == bean) {
			return null;
		}
		return bean.getValue();
	}
	
	/**
	 * 获得缓存的ValueBean【使用该函数可用于区分：某个key未缓存，还是缓存的值为null】
	 */
	public ValueBean getValueBean(K key) {
		ValueBean bean = valueBeanMap.get(key);
		if (null == bean) {
			return null;
		}

		if (bean.isExpired()) {
			valueBeanMap.remove(key);
			return null;
		} else {
			return bean;
		}
	}

	public void remove(K key) {
		valueBeanMap.remove(key);
	}

	@Override
	public String toString() {
		return this.valueBeanMap.toString();
	}

	public class ValueBean {
		V		value;
		Long	valueTime;

		public ValueBean(V value) {
			super();
			this.value = value;
			this.valueTime = System.currentTimeMillis();
		}

		/** 是否已经过期 */
		public boolean isExpired() {
			return (this.getValueTime() + cacheTime * 1000) < System.currentTimeMillis();
		}

		public V getValue() {
			return value;
		}

		public Long getValueTime() {
			return valueTime;
		}

		@Override
		public String toString() {
			return "ValueBean [" + (value != null ? "value=" + value + ", " : "")
					+ (valueTime != null ? "valueTime=" + valueTime : "") + "]";
		}
	}
}
