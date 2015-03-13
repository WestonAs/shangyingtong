package flink.web;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @File: EhcacheCache.java
 *
 * @description: EHCache缓存处理
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-7-5 下午02:05:34
 */
public final class EhcacheCache {
	
	private static final transient Logger logger = LoggerFactory.getLogger(EhcacheCache.class);

	private static final CacheManager cacheManager = CacheManager.create();
	
	private String cacheName;
	
	/** URL重定向缓存 */
	public static final String URL_CACHE = "UrlCache";
	
	public EhcacheCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		this.cacheName = id;
		if (!cacheManager.cacheExists(this.cacheName)) {
			cacheManager.addCache(this.cacheName);

			logger.info("缓存标志{" + this.cacheName + "}创建成功");
		}
	}
	
    /**
     * 清除缓存
     */
    public void flush() {   
    	cacheManager.getCache(cacheName).removeAll();   
    }   
  
    /**
     * 从缓存中取出对象
     * @param key
     * @return
     */
    public Object getObject(Object key) {   
        Object result = null;   
        Element element = cacheManager.getCache(cacheName).get(key);   
        if (element != null) {   
            result = element.getObjectValue();   
        }   
        return result;   
  
    }   
  
    /**
     * 将对象存到缓存中
     * @param cacheName
     * @param key
     * @param object
     */
    public void putObject(Object key, Object object) {   
    	cacheManager.getCache(cacheName).put(new Element(key, object));   
    }   
  
    /**
     * 从缓存中清除对象
     * @param cacheName
     * @param key
     * @return
     */
    public Object removeObject(Object key) {   
        Object result = this.getObject(key);   
        cacheManager.getCache(cacheName).remove(key);   
        return result;   
    }   
  
    /**
     * 清除Cache
     */
    public void destory() {
    	if (cacheManager != null) {
    		cacheManager.shutdown();
    		logger.debug("清除cacheManager...");
		}
	}
}
