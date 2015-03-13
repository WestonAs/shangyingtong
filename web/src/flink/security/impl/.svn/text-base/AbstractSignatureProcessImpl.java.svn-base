package flink.security.impl;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import flink.util.CommonHelper;
import flink.security.SignatureException;
import flink.security.ISignatureProcess;
/**
 * <p>签名处理抽象类</p>
 * <ul> 
 * <li>一般来讲私钥保存在客户端、公钥保存在系统或公布出来</li> 
 * <li>客户用私钥签名相关数据 然后接受方使用其公钥和相关签名算法来核对</li>               
 * </ul>
 * @Project: Card
 * @File: AbstractSignatureProcessImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-9
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public abstract class AbstractSignatureProcessImpl implements ISignatureProcess {
	protected final Log log = LogFactory.getLog(getClass());

    protected final String DEFAULT_KEYFACTORY_ALGORITHM = "RSA";
    
    protected final String DEFAULT_CA_SIGNATURE_ALGORITHM = "Md5withRSA";

	/** 如果存在有私钥串 ---- 用于签名数据 */
	public String getHexSignedInfo(String hexPrivateKey, String info) throws SignatureException {
		return getHexSignedInfo(getDefaultSignature(getSigAlgorithm()), hexPrivateKey, info);
	}

	public String getHexSignedInfo(PrivateKey privateKey, String info) throws SignatureException {
		return getHexSignedInfo(getDefaultSignature(getSigAlgorithm()), privateKey, info);
	}

	public String getHexSignedInfo(String hexPrivateKey, String sigAlgorithm, String info)
			throws SignatureException {
		return getHexSignedInfo(getDefaultSignature(sigAlgorithm), hexPrivateKey, info);
	}

	public String getHexSignedInfo(PrivateKey privateKey, String sigAlgorithm, String info)
			throws SignatureException {
		return getHexSignedInfo(getDefaultSignature(sigAlgorithm), privateKey, info);
	}

	/** 如果存在有公钥串 ---- 用于验证签名  */
	public boolean verifyHexSignedInfo(String hexPublicKey, String hexSignedInfo, String info)
			throws SignatureException {
		return verifyHexSignedInfo(getDefaultSignature(getSigAlgorithm()), hexPublicKey, hexSignedInfo, info);
	}

	public boolean verifyHexSignedInfo(PublicKey publicKey, String hexSignedInfo, String info)
			throws SignatureException {
		return verifyHexSignedInfo(getDefaultSignature(getSigAlgorithm()), publicKey, hexSignedInfo, info);
	}

	public boolean verifyHexSignedInfo(String hexPubKey, String sigAlgorithm, String hexSignedInfo,
			String info) throws SignatureException {
		return verifyHexSignedInfo(getDefaultSignature(sigAlgorithm), hexPubKey, hexSignedInfo, info);
	}

	public boolean verifyHexSignedInfo(PublicKey publicKey, String sigAlgorithm, String hexSignedInfo,
			String info) throws SignatureException {
		return verifyHexSignedInfo(getDefaultSignature(sigAlgorithm), publicKey, hexSignedInfo, info);
	}
	
	private String getHexSignedInfo(Signature signet, String hexPrivateKey, String info)
			throws SignatureException {
		return getHexSignedInfo(signet, getSigPrivateKey(hexPrivateKey), info);
	}
    
	/**
	  *  <p>内部签名处理过程类</p>
	  * @param signet
	  * @param privateKey
	  * @param info
	  * @return
	  * @throws SignatureException  
	  * @version: 2011-7-12 下午03:40:33
	  * @See:
	 */
	private String getHexSignedInfo(Signature signet, PrivateKey privateKey, String info)
			throws SignatureException {
		String hexSigedInfo = null;
		try {
			signet.initSign(privateKey);
			signet.update(getUpdateInfoBytes(info));  			
			byte[] signed = signet.sign();
			hexSigedInfo = bytesToHexStr(signed);
		} catch (Exception ex) {
			throw new SignatureException(ex);
		}
		return hexSigedInfo;
	}

	private boolean verifyHexSignedInfo(Signature signetcheck, String hexPublicKey,
			String hexSignedInfo, String info) throws SignatureException {
		return verifyHexSignedInfo(signetcheck, getSigPublicKey(hexPublicKey), hexSignedInfo, info);
	}

	/**
	 * 
	  * <p>注意：增加如果验签失败抛出异常的处理，并记录错误日志</p>
	  * @param signetcheck
	  * @param pubKey
	  * @param hexSignedInfo
	  * @param info
	  * @return
	  * @throws SignatureException  
	  * @version: 2011-5-17 上午10:14:48
	  * @See:
	 */
	private boolean verifyHexSignedInfo(Signature signetcheck, PublicKey pubKey,
			String hexSignedInfo, String info) throws SignatureException {
		boolean checkFlag = (signetcheck == null) || (pubKey == null)|| (CommonHelper.checkIsEmpty(hexSignedInfo)) || (CommonHelper.checkIsEmpty(info));
		
		if(checkFlag) {
			throw  new SignatureException("用于验证数字签名的参数不合法(签署算法、公钥、签名串以及验签串均不能为空)!");
		}
		
		boolean result = false;
		
		try {
			byte[] signed = hexStrToBytes(hexSignedInfo);
			signetcheck.initVerify(pubKey);			
			signetcheck.update(getUpdateInfoBytes(info));
			result = signetcheck.verify(signed);
		}catch(Exception ex) {
			throw new SignatureException("验证签名中存在异常，签名内容[" + hexSignedInfo + "],验签内容[" + info + "]，异常原因[" + ex.getMessage() + "]");			
		}
		
		if(!result) {
			String err = "验证签名失败原因待定，签名内容[" + hexSignedInfo + "],验签内容[" + info + "]";
			log.warn(err);
			throw new SignatureException(err);
		}
		
		return result;	
	}

	// ---------------------------------------------------------------------------------------------------------------------
    // 根据算法和provider获得签名的实体类
	private Signature getDefaultSignature(String sigAlgorithm) throws SignatureException {
		Signature signature = null;

		try {
			Provider provider = getProvider();
			
			signature = (provider == null) ? Signature.getInstance(sigAlgorithm) : Signature.getInstance(sigAlgorithm, provider);
			
		} catch (NoSuchAlgorithmException ex) {
			throw new SignatureException(ex);
		}

		return signature;

	}

	// 设置Provider
	public abstract Provider getProvider();

	// 设置密钥工厂缺省的算法
	public abstract String setKeyFactoryAlgorithm();

	// 设置签名缺省依赖的
	public abstract String getSigAlgorithm();
	
	//将字节数组转换为hex编码字符
	protected abstract String bytesToHexStr(byte[] bcd) ;
	
	//将hex编码字符转换成字节数组
	protected abstract byte[] hexStrToBytes(String s) ;
	
	// 得到原始串的字节数组
	public abstract byte[] getUpdateInfoBytes(String info);

	// 设置如果传输了编码的私密钥串如何生成服务端的私钥
	public abstract PrivateKey getSigPrivateKey(String hexPrivateKey) throws SignatureException;

	// 设置如果传输了编码的公密钥串如何生成服务端的公钥
	public abstract PublicKey getSigPublicKey(String hexPublicKey) throws SignatureException;
	
	// ------------------------------------------------------------------------------------------------------------------
	protected PrivateKey getDefaultSigPrivateKey(String hexPrivateKey) throws SignatureException {
		PrivateKey sigPrivateKey = null;
		
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(hexStrToBytes(hexPrivateKey));
			KeyFactory keyf = KeyFactory.getInstance(setKeyFactoryAlgorithm(), getProvider());
			sigPrivateKey = keyf.generatePrivate(priPKCS8);
		} catch (Exception ex) {
			throw new SignatureException("获得私钥处理异常,原因[" + ex.getMessage() + "]");
		}

		return sigPrivateKey;
	}

	protected PublicKey getDefaultSigPublicKey(String hexPublicKey) throws SignatureException {
		PublicKey sigPublicKey = null;
		try {
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(hexStrToBytes(hexPublicKey));
			KeyFactory keyf = KeyFactory.getInstance(setKeyFactoryAlgorithm(), getProvider());
			sigPublicKey = keyf.generatePublic(pubKeySpec);
		} catch (Exception ex) {
			throw new SignatureException("获得公钥处理异常,原因[" + ex.getMessage() + "]");
		}
		
		return sigPublicKey;
	}

}
