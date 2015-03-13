package gnete.card.service;

import flink.security.ISignatureProcess;
import flink.security.SignatureException;
import flink.security.UserCertificateBean;

/**
 * 
 * @Project: Card
 * @File: ICardSignatureProcess.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-9
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public interface ICardSignatureProcess extends ISignatureProcess {
	/**
	 * 
	  * <p>验证前端传递的签名信息,前台还传入证书序列号</p>
	  * @param serialId   证书序列号
	  * @param hexSignedInfo : 用户客户端签名的信息
	  * @param info ： 用户原始的信息
	  * @return
	  * @throws SignatureException  
	  * @version: 2010-12-9 下午04:51:46
	  * @See:
	 */
	boolean verifySignedInfo(String serialId, String hexSignedInfo, String info) throws SignatureException;
	
	/**
	 * 
	  * <p>验证前端传递的签名信息,前台还传入证书绑定实体</p>
	  * @param certificateBean
	  * @param hexSignedInfo
	  * @param info
	  * @return
	  * @throws SignatureException  
	  * @version: 2011-5-26 上午11:54:55
	  * @See:
	 */
	boolean verifySignedInfo(UserCertificateBean certificateBean, String hexSignedInfo, String info) throws SignatureException;
}
