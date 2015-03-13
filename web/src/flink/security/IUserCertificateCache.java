package flink.security;

import java.security.cert.Certificate;

/**
 * 
 * @Project:Card
 * @File: IUserCertificateCache.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-19
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public interface IUserCertificateCache {
	/**
	 * 
	  * @description：添加C.A的证书
	  * @param cacertificate
	  * @return  
	  * @version: 2011-1-7 上午10:57:42
	  * @See:
	 */
	boolean addCACertificate(Certificate cacertificate);
	
	/**
	 * 
	  * @description：获得C.A证书
	  * @return  
	  * @version: 2011-1-7 上午10:57:46
	  * @See:
	 */
	Certificate getCACertificate();
	
	/**
	 * 
	  * @description：将证书缓存清空
	  * @return  
	  * @version: 2010-12-20 下午02:23:35
	  * @See:
	 */
	boolean initAppCache();

	/**
	 * 
	  * @description：检查证书缓存是否为空
	  * @return  
	  * @version: 2010-12-20 下午02:23:38
	  * @See:
	 */
	boolean checkAppCache();

	/**
	 * 
	  * @description：得到证书缓存实体
	  * @param serialId  证书序列号
	  * @return  
	  * @version: 2010-12-20 下午02:23:45
	  * @See:
	 */
	UserCertificateBean getCertificateBean(String serialId);

	/**
	 * 
	  * @description：将证书缓存从缓存中移除
	  * @param serialId   证书序列号
	  * @return  
	  * @version: 2010-12-20 下午02:23:51
	  * @See:
	 */
	boolean removeCertificateBean(String serialId);
}
