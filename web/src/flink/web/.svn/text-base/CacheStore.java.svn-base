package flink.web;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
@Deprecated
public class CacheStore {

	private static CacheManager manager;
	private static Cache cache;
	static {
		CacheStore cs = new CacheStore();
		cs.init();
	}

	/**
	 * 初试化cache
	 */
	private void init() {
		manager = CacheManager.create();
//		URL url = getClass().getResource("/conf/ehcache.xml");  
//      manager = new CacheManager(url);  
		cache = manager.getCache("UrlCache");
	}

	/**
	 * 清除cache
	 */
	@SuppressWarnings("unused")
	private void destory() {
		manager.shutdown();
	}

	/**
	 * 得到某个key的cache值
	 * 
	 * @param key
	 * @return
	 */
	public static Element get(String key) {
		return cache.get(key);
	}

	/**
	 * 清除key的cache
	 * 
	 * @param key
	 */
	public static void remove(String key) {
		cache.remove(key);
	}

	/**
	 * 设置或更新某个cache值
	 * 
	 * @param element
	 */
	public static void put(Element element) {
		cache.put(element);
	}

	public static void removeAll() {
		cache.removeAll();
	}
	public static void main(String[] args) {
		try {
			cache.put(new Element("1", "1"));
			System.out.println(CacheStore.get("1"));  
			CacheStore.remove("1");  
			System.out.println(CacheStore.get("1"));  
		} catch (Exception e) {
			e.printStackTrace();
		}
    }  
}
