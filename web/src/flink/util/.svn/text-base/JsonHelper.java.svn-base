package flink.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

/**
 * <p>增加将对象转换为JSON数据结构</p>
 * @Project: Card
 * @File: JsonHelper.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-1
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public abstract class JsonHelper {
	/**
	 * 从一个JSON 对象字符格式中得到一个java对象 说明：Bean的无参构造函数一定要写, 否则会报:
	 * net.sf.json.JSONException: java.lang.NoSuchMethodException
	 * 
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object getObjectFromJsonString(String jsonString, Class pojoCalss) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);

		return pojo;
	}

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param javaObj
	 * @return
	 */
	public static String getJsonStringFromObject(Object javaObj) {
		JSONObject json;
		json = JSONObject.fromObject(javaObj);
		return json.toString();
	}

	/**
	 * 从json HASH表达式中获取一个map
	 * 
	 * @param jsonString
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map getMapFromJsonString(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();
		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}
		return valueMap;
	}

	/**
	 * 从Map对象得到Json字串
	 * 
	 * @param map
	 * @return
	 */
	public static String getJsonStringFromMap(Map map) {
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}

	/**
	 * 从json字串中得到相应java数组
	 * 
	 * @param jsonString
	 *            like "[\"李斯\",100]"
	 * @return
	 */
	public static Object[] getObjectArrayFromJsonString(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();
	}

	/**
	 * 将list转换成Array
	 * 
	 * @param list
	 * @return
	 */
	public static Object[] getObjectArrayFromList(List list) {
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toArray();
	}

	/**
	 * 
	 * @description：<li>通用方法来获得JSON字符串</li>
	 * 
	 * @param obj
	 * @return
	 * @version: 2011-6-1 下午01:44:39
	 * @See:
	 */
	public static String getJsonString(Object obj) {
		return JSONSerializer.toJSON(obj).toString();
	}

	public static String getJsonString(Object obj, JsonConfig config) {
		return JSONSerializer.toJSON(obj, config).toString();
	}

	/**
	 * 
	 * @description：<li>带属性过滤的JSON字符串(减少JSON的体积)</li>
	 * @param obj
	 * @param excludes
	 * @param needCycle
	 * @return
	 * @version: 2011-6-2 上午07:29:13
	 * @See:
	 */
	public static String getJsonString(Object obj, String[] excludes, boolean needCycle) {
		return getJsonString(obj, getJsonConfig(excludes, needCycle));
	}
	
    /**
     * 
      * @description：<li>带属性过滤的JSON字符串(减少JSON的体积)</li>
      * @param obj
      * @param excludes
      * @param needCycle
      * @param needFilter
      * @return  
      * @version: 2011-6-2 上午07:41:28
      * @See:
     */
	public static String getJsonString(Object obj, String[] excludes, boolean needCycle,boolean needFilter) {
		return getJsonString(obj, getJsonConfig(excludes, needCycle,needFilter));
	}

	private static JsonConfig getJsonConfig(String[] excludes, boolean needCycle) {
		JsonConfig config = new JsonConfig();

		if (CommonHelper.checkIsNotEmpty(excludes)) {
			config.setExcludes(excludes);
		}

		if (needCycle) {
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		}

		return config;
	}
	
	
	private static JsonConfig getJsonConfig(String[] excludes, boolean needCycle,boolean needFilter) {
		JsonConfig config = new JsonConfig();
		
	    if(CommonHelper.checkIsNotEmpty(excludes)) {
	    	if(needFilter) {
	    		config.setJavaPropertyFilter(new IgnoreFieldProcessorImpl(true,excludes));	
	    	}
	    	
	    	config.setExcludes(excludes);
	    }
	    
	    if (needCycle) {
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		}
		
		return config;

	}
	

	public static class IgnoreFieldProcessorImpl implements PropertyFilter {
		/**
		 * 忽略的属性名称
		 */
		private String[] fields;

		/**
		 * 是否忽略集合
		 */
		private boolean ignoreColl = false;

		/**
		 * 空参构造方法<br/>
		 * 默认不忽略集合
		 */
		public IgnoreFieldProcessorImpl() {
			// empty
		}

		/**
		 * 构造方法
		 * 
		 * @param fields
		 *            忽略属性名称数组
		 */
		public IgnoreFieldProcessorImpl(String[] fields) {
			this.fields = fields;
		}

		/**
		 * 构造方法
		 * 
		 * @param ignoreColl
		 *            是否忽略集合
		 * @param fields
		 *            忽略属性名称数组
		 */
		public IgnoreFieldProcessorImpl(boolean ignoreColl, String[] fields) {
			this.fields = fields;
			this.ignoreColl = ignoreColl;
		}

		/**
		 * 构造方法
		 * 
		 * @param ignoreColl
		 *            是否忽略集合
		 */
		public IgnoreFieldProcessorImpl(boolean ignoreColl) {
			this.ignoreColl = ignoreColl;
		}

		public boolean apply(Object source, String name, Object value) {
			Field declaredField = null;
			// 忽略值为null的属性
			if (value == null)
				return true;
			// 剔除自定义属性，获取属性声明类型
			if (!"data".equals(name) && "data" != name && !"totalSize".equals(name) && "totalSize" != name) {
				try {
					declaredField = source.getClass().getDeclaredField(name);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
			}
			// 忽略集合
			if (declaredField != null) {
				if (ignoreColl) {
					if (declaredField.getType() == Collection.class || declaredField.getType() == Set.class) {
						return true;
					}
				}
			}

			// 忽略设定的属性
			if (fields != null && fields.length > 0) {
				if (juge(fields, name)) {
					return true;
				} else {
					return false;
				}
			}

			return false;
		}

		/**
		 * 过滤忽略的属性
		 * 
		 * @param s
		 * @param s2
		 * @return
		 */
		public boolean juge(String[] s, String s2) {
			boolean b = false;
			for (String sl : s) {
				if (s2.equals(sl)) {
					b = true;
				}
			}
			return b;
		}

		public String[] getFields() {
			return fields;
		}

		/**
		 * 设置忽略的属性
		 * 
		 * @param fields
		 */
		public void setFields(String[] fields) {
			this.fields = fields;
		}

		public boolean isIgnoreColl() {
			return ignoreColl;
		}

		/**
		 * 设置是否忽略集合类
		 * 
		 * @param ignoreColl
		 */
		public void setIgnoreColl(boolean ignoreColl) {
			this.ignoreColl = ignoreColl;
		}

	}

}
