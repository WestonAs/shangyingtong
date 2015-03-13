package flink.security;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
  * <p>证书签名处理接口</p>
  * @Project: Card
  * @File: ISignatureProcess.java
  * @See: 
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-9
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public interface ISignatureProcess {
	/**
	 * 
	  * <p>根据私钥字符串和待签名数据得到签名数据</p>
	  * @param hexPrivateKey   私钥字符串
	  * @param info    待签名数据
	  * @return
	  * @throws SignatureException  
	  * @version: 2010-12-9 下午04:38:39
	  * @See:
	 */
	String getHexSignedInfo(String hexPrivateKey, String info) throws SignatureException;

	/**
	 * 
	  * <p>根据私钥和待签名数据得到签名后的二进制字符串</p>
	  * @param privateKey   私钥
	  * @param info   待签名数据
	  * @return
	  * @throws SignatureException  
	  * @version: 2010-12-9 下午04:38:42
	  * @See:
	 */
	String getHexSignedInfo(PrivateKey privateKey, String info) throws SignatureException;

	/**
	 * 
	  * <p>根据私钥串、签名算法以及待签名数据得到签名数据</p>
	  * @param hexPrivateKey 私钥串
	  * @param sigAlgorithm  签名算法
	  * @param info  待签名数据
	  * @return
	  * @throws SignatureException  
	  * @version: 2010-12-9 下午04:38:46
	  * @See:
	 */
	String getHexSignedInfo(String hexPrivateKey, String sigAlgorithm, String info) throws SignatureException;

	/**
	 * 
	  * <p>根据私钥、签名算法以及待签名数据得到签名数据</p>
	  * @param privateKey   私钥
	  * @param sigAlgorithm 签名算法
	  * @param info         待签名数据
	  * @return
	  * @throws SignatureException  
	  * @version: 2010-12-9 下午04:38:50
	  * @See:
	 */
	String getHexSignedInfo(PrivateKey privateKey, String sigAlgorithm, String info) throws SignatureException;

	/**
	 * 
	  * <p>根据公钥串、签名数据以及待签名数据进行签名验证</p>
	  * @param hexPublicKey  公钥串
	  * @param hexSignedInfo 签名数据
	  * @param info          待签名数据
	  * @return
	  * @throws SignatureException  
	  * @version: 2010-12-9 下午04:38:53
	  * @See:
	 */
	boolean verifyHexSignedInfo(String hexPublicKey, String hexSignedInfo, String info) throws SignatureException;

	/**
	 * 
	  * <p>根据公钥、签名数据以及待签名数据进行签名验证</p>
	  * @param publicKey      公钥
	  * @param hexSignedInfo  签名数据
	  * @param info           签名数据
	  * @return
	  * @throws SignatureException  
	  * @version: 2010-12-9 下午04:38:56
	  * @See:
	 */
	boolean verifyHexSignedInfo(PublicKey publicKey, String hexSignedInfo, String info) throws SignatureException;

	/**
	 * 
	  * <p>根据公钥串、签名算法、签名数据以及待签名数据进行签名验证</p>
	  * @param hexPubKey     公钥串
	  * @param sigAlgorithm  签名算法
	  * @param hexSignedInfo 签名数据
	  * @param info          待签名数据
	  * @return
	  * @throws SignatureException  
	  * @version: 2010-12-9 下午04:39:00
	  * @See:
	 */
	boolean verifyHexSignedInfo(String hexPubKey, String sigAlgorithm, String hexSignedInfo, String info)
			throws SignatureException;

	/**
	 * 
	  * <p>根据公钥、签名算法、签名数据以及待签名数据进行签名验证</p>
	  * @param publicKey     公钥
	  * @param sigAlgorithm  签名算法
	  * @param hexSignedInfo 签名数据
	  * @param info          待签名数据
	  * @return
	  * @throws SignatureException  
	  * @version: 2010-12-9 下午04:39:03
	  * @See:
	 */
	boolean verifyHexSignedInfo(PublicKey publicKey, String sigAlgorithm, String hexSignedInfo, String info)
			throws SignatureException;
}