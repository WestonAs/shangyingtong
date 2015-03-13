package flink.security;

import java.security.KeyStore;
import java.security.cert.Certificate;
import java.io.File;
import java.io.InputStream;

import gnete.card.entity.UserCertificate;
/**
 *  <p>用户数字证书提取接口定义(缺省数字证书均为X509格式)</p>
  * @Project: Card
  * @File: ICertificateKeyStore.java
  * @See:
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-8
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public interface ICertificateKeyStore {
	/**
	 * 
	  * <p>从文件中提取C.A(根证书)的信息(用于验证签发者)</p>
	  * @param certFile
	  * @return
	  * @throws CertOperateException  
	  * @version: 2011-1-7 下午01:19:10
	  * @See:
	 */
	Certificate getCACertificate(File certFile) throws CertOperateException;
	
    /**
     * 
      * <p>根据证书库提供的文件和密码返回证书库实体</p>
      * @param keyStoreFile   证书库文件
      * @param keyStorePwd    证书库密码
      * @return
      * @throws CertOperateException  
      * @version: 2010-12-8 下午12:33:39
      * @See:
     */
	KeyStore getLoadKeyStore(File keyStoreFile, String keyStorePwd) throws CertOperateException;

	/**
	 * 
	  * <p>根据证书文件获得X509证书实体</p>
	  * @param certFile   证书文件
	  * @return
	  * @throws CertOperateException  
	  * @version: 2010-12-8 下午12:33:43
	  * @See:
	 */
	Certificate getCertficateFromFile(File certFile) throws CertOperateException;
	
	/**
	 * 
	  * <p>根据证书文件获得X509证书实体</p>
	  * @param certInput   证书文件流
	  * @return
	  * @throws CertOperateException  
	  * @version: 2010-12-9 上午12:22:52
	  * @See:
	 */
	Certificate getCertficateFromFileStream(InputStream certInput) throws CertOperateException;

	/**
	 *  <p>根据证书文件获得用户数字证书实体类</p>
	 *  <p>如果类型文件不匹配则返回为空或者发生提取异常</p>
	  * @description：
	  * @param certFile   证书文件
	  * @return
	  * @throws CertOperateException  
	  * @version: 2010-12-8 下午12:33:47
	  * @See:
	 */
	UserCertificate getUserCertificateFromCertFile(File certFile) throws CertOperateException;

}
