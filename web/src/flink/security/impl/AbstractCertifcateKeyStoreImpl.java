package flink.security.impl;

import java.util.Arrays;
import java.util.Date;
import java.io.File;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.CertPathValidator;
import java.security.cert.PKIXCertPathValidatorResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import flink.security.CertOperateException;
import flink.security.ICertificateKeyStore;
import flink.util.FileHelper;
import flink.util.CommonHelper;
import java.util.Collection;

import gnete.card.entity.UserCertificate;
import gnete.card.entity.state.CertificateState;
import java.util.ArrayList;

/**
 * <p>抽象数字证书提取实现类</p>
 * @Project: Card
 * @File: AbstractCertifcateKeyStoreImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-8
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public abstract class AbstractCertifcateKeyStoreImpl implements ICertificateKeyStore {
	protected final Log log = LogFactory.getLog(getClass());

	/** 缺省证书类型 X509*/
	protected final String DEFAULT_CERTIFCATE_TYPE = "X509";

	/** 缺省证书密钥库类型*/
	protected final String DEFAULT_KEYSTORE_TYPE = "PKCS12";
	
	/** 缺省证书链验证算法类型 */
	protected final String DEFAULT_CERPATH_ALGRITHM = "PKIX";
	
	protected final int DEFAULT_SEQ_RADIX = 16;
	
	protected final String  DEFAULT_SEQ_START = "0";
	
	public KeyStore getLoadKeyStore(File keyStoreFile, String keyStorePwd) throws CertOperateException {
		KeyStore ks = null;
		try {
			ks = getKeyStore();
			InputStream ksInput = FileHelper.getBufferedInputStream(keyStoreFile);
			if (ksInput == null) {
				ks.load(null, null);
			} else {
				ks.load(ksInput, keyStorePwd.toCharArray());
				FileHelper.closeInputStream(ksInput);
			}
		} catch (Exception ex) {
			log.error("加载数字证书库失败", ex);
			throw new CertOperateException("加载证书密钥库[" + FileHelper.getFileName(keyStoreFile) +"]失败,原因[" + ex.getMessage() + "]");
		}

		return ks;
	}
	
	public Certificate getCACertificate(File certFile) throws CertOperateException {
		return getCertficateFromFile(certFile);
	}
	
	public Certificate getCertficateFromFile(File certFile) throws CertOperateException {
		Certificate certificate = null;

		InputStream certInput = null;
		try {
			certInput = FileHelper.getBufferedInputStream(certFile);
			CertificateFactory certFactory = getCertificateFactory();
			certificate = certFactory.generateCertificate(certInput);
		} catch (Exception ex) {
			log.error("提取证书文件异常", ex);
			throw new CertOperateException("提取证书文件[" + FileHelper.getFileName(certFile) + "]认证失败,原因[" + ex.getMessage() + "]");
		} finally {
			FileHelper.closeInputStream(certInput);
		}

		return certificate;
	}
	
	public Certificate getCertficateFromFileStream(InputStream certInput) throws CertOperateException {
		Certificate certificate = null;
		try {
			CertificateFactory certFactory = getCertificateFactory();
			certificate = certFactory.generateCertificate(certInput);
		}catch(Exception ex) {
			log.error("提取证书文件异常", ex);
			throw new CertOperateException("提取证书文件流认证失败,原因[" + ex.getMessage() + "]");
		}finally {
			FileHelper.closeInputStream(certInput);
		}
		return certificate;
	}

	public Collection<? extends Certificate> getCertficatesFromFile(File certFile)
			throws CertOperateException {
		Collection<? extends Certificate> certificates = null;

		InputStream certInput = null;
		try {
			certInput = FileHelper.getBufferedInputStream(certFile);
			CertificateFactory certFactory = getCertificateFactory();

			certificates = certFactory.generateCertificates(certInput);
		} catch (Exception ex) {
			log.error("提取证书文件异常", ex);
			throw new CertOperateException("提取证书文件[" + FileHelper.getFileName(certFile) + "]认证失败,原因[" + ex.getMessage() + "]");
		} finally {
			FileHelper.closeInputStream(certInput);
		}

		return certificates;
	}

	public Collection<UserCertificate> getUserCertificatesFromCertFile(File certFile) throws CertOperateException {
		Collection<UserCertificate> branchCertificates = new ArrayList<UserCertificate>();
		
		Collection<? extends Certificate> certificates = getCertficatesFromFile(certFile);
		if(CommonHelper.checkIsNotEmpty(certificates)) {
			for(Certificate certificate : certificates) {
				UserCertificate branchCertificate = getUserCertificate(certificate,certFile);
				if(branchCertificate == null) {
					continue;
				}
				branchCertificates.add(branchCertificate);
			}
		}		
		
		return branchCertificates;
	}

	/**
	 * <p>引入对证书签发者的检查(即检查证书文件是否由根证书所签发)</p>
	 */
	public UserCertificate getUserCertificateFromCertFile(File certFile) throws CertOperateException {
		Certificate certificate = getCertficateFromFile(certFile);
		
		checkCertificateIssure(certificate);
		
		return getUserCertificate(certificate,certFile);	
	}
	
	/**
	 * 
	  * <li>增加签发者的验证处理</li>
	  * @param certificate
	  * @param caCertificate
	  * @throws CertOperateException  
	  * @version: 2011-5-17 上午07:58:14
	  * @See:
	 */
	public void checkCardIssure(Certificate certificate, Certificate caCertificate) throws CertOperateException {
		if (needVerify()) {
			try {
				//certificate.verify(caCertificate.getPublicKey(),getProvider().getName());
				verify((X509Certificate)certificate,(X509Certificate)caCertificate);
			} catch (Exception ex) {
				log.error("录入证书签发核对失败", ex);
				throw new CertOperateException("录入证书签发核对失败,原因[" + ex.getMessage() + "]");
			}
		} else {
			X509Certificate _certificate = (X509Certificate) certificate;
			X509Certificate _caCertificate = (X509Certificate) caCertificate;

			if (!_certificate.getIssuerDN().equals(_caCertificate.getIssuerDN())) {
				throw new CertOperateException("录入证书签发核对失败,原因[" + "提交证书不是系统指定签发者" + "]");
			}
		}
	}
	
	
	/**
	 * 
	  * <ul>
	  *    <li>使用证书链路径来核实根证书签发</li>
	  *    <li>请参考http://download.oracle.com/javase/6/docs/technotes/guides/security/certpath/CertPathProgGuide.html</li>
	  * </ul>   
	  * @param certificate
	  * @param caCertficate
	  * @throws Exception  
	  * @version: 2011-5-17 上午11:23:13
	  * @See:
	 */
	protected  void verify(X509Certificate certificate,X509Certificate caCertficate) throws Exception {
		CertificateFactory certFactory = getCertificateFactory(); 
		
		java.security.cert.CertPath cp = certFactory.generateCertPath(Arrays.asList(new X509Certificate[] {certificate}));
		
		java.security.cert.TrustAnchor anchor = new java.security.cert.TrustAnchor(caCertficate,null);
		
		java.security.cert.PKIXParameters params = new java.security.cert.PKIXParameters(java.util.Collections.singleton(anchor));
		
		params.setRevocationEnabled(false);
		params.setDate(new Date());
		
		CertPathValidator cpv = getCertPathValidator();
		
		PKIXCertPathValidatorResult result = (PKIXCertPathValidatorResult)cpv.validate(cp, params);
		
		if(log.isDebugEnabled()) {
			log.debug("Certificate verify result===[" + result.toString() + "]");
		}		
	}

	/**
	 * 
	  * <p>构造缺省的用户证书实体（从文件中提取基本信息）</p>
	  * @param certificate
	  * @return  
	  * @version: 2010-12-8 下午12:57:26
	  * @See:
	 */
	protected UserCertificate getUserCertificate(Certificate certificate,File certFile) {
		UserCertificate userCertificate = null;

		if (certificate instanceof X509Certificate) {
			X509Certificate x509Certificate = (X509Certificate) certificate;
			String issuereDN = x509Certificate.getSubjectDN().getName();
			String seqNo = getCertificateSeqNo(x509Certificate);
			Date dateBefore = x509Certificate.getNotBefore();
			Date dateAfter = x509Certificate.getNotAfter();
			
			userCertificate = new UserCertificate(issuereDN,seqNo,CommonHelper.getCommonDateFormatStr(dateBefore));			
			userCertificate.setEndDate(CommonHelper.getCommonDateFormatStr(dateAfter));
			userCertificate.setState(getCertificateState(x509Certificate));
			userCertificate.setFileName(getCertificateFileName(issuereDN,certFile));
		}

		return userCertificate;
	}
	
	
	/**
	 * 
	 * <p>根据读取的证书是否合法有效</p>
	 * @param x509Certificate
	 * @return CertificateState
	 * @version: 2010-12-8 上午11:51:21
	 * @See:
	 */
	protected String getCertificateState(X509Certificate x509Certificate) {
		try {
			x509Certificate.checkValidity(CommonHelper.getCalendarDate());
		} catch (Exception ex) {
			return CertificateState.INVALID.getValue();
		}

		return CertificateState.VALID.getValue();
	}
	
	
	private KeyStore getKeyStore() throws KeyStoreException {
		Provider provider = getProvider();
		
		return (provider == null) ?  KeyStore.getInstance(getKeyStoreType()) : KeyStore.getInstance(getKeyStoreType(), provider);
	}
	
	
	private CertificateFactory getCertificateFactory() throws CertificateException {
		Provider provider = getProvider();
		
		return (provider == null) ? CertificateFactory.getInstance(getCertificateType()) : 
			                         CertificateFactory.getInstance(getCertificateType(),provider);
	}
	
	/**
	 *  <p>获得证书路径核实检查类</p>
	  * @return
	  * @throws NoSuchAlgorithmException  
	  * @version: 2011-7-12 下午03:36:21
	  * @See:
	 */
	private CertPathValidator getCertPathValidator() throws NoSuchAlgorithmException {
		Provider provider = getProvider();
		
		return (provider == null) ? CertPathValidator.getInstance(DEFAULT_CERPATH_ALGRITHM) :
			                         CertPathValidator.getInstance(DEFAULT_CERPATH_ALGRITHM,provider);
	}

	/**
	 * 
	  * <p>设定密钥库类型</p>
	  * @return  
	  * @version: 2010-12-9 上午12:04:34
	  * @See:
	 */
	protected abstract String getKeyStoreType();

	/**
	 * 
	  * <p>密钥库供应商</p>
	  * @return  
	  * @version: 2010-12-9 上午12:04:39
	  * @See:
	 */
	protected abstract Provider getProvider();

	/**
	 * 
	  * <p>证书文件认证类型</p>
	  * @return  
	  * @version: 2010-12-9 上午12:04:45
	  * @See:
	 */
	protected abstract String getCertificateType();
	
	/**
	 * 
	  * <p>得到证书关联序号(用于数据库保存和缓存的唯一性)</p>
	  * <p>前端如果验签最好传入读取的序列号</p>
	  * @param certificate
	  * @return  
	  * @version: 2010-12-18 上午11:58:09
	  * @See:
	 */
	protected abstract String getCertificateSeqNo(X509Certificate certificate);
	
	/**
	 * 
	  *<p>获得证书的文件名(防止上传重复的证书或者更改文件名但证书内容又一样的)</p>
	  * @param issuereDN    
	  * @param certFile
	  * @return  
	  * @version: 2011-5-18 上午10:35:03
	  * @See:
	 */
	protected abstract String getCertificateFileName(String issuereDN,File certFile);
	
	/**
	 * 
	  * <p>
	  *   <li>1.检查证书的颁发者,不通过则抛出异常(需要C.A根证书)</li>
	  *   <li>2.请参考(http://jzhua.javaeye.com/blog/844903)</li>
	  * </p>
	  * @param certificate
	  * @return  
	  * @version: 2011-1-8 上午11:29:54
	  * @See:
	 */
	protected abstract void checkCertificateIssure(Certificate certificate) throws CertOperateException;
	
	/**
	 * 
	  * <li>是否需要C.A验证(严格意义上的)</li>
	  * @return  
	  * @version: 2011-1-10 下午01:45:03
	  * @See:
	 */
	protected abstract boolean needVerify();

}
