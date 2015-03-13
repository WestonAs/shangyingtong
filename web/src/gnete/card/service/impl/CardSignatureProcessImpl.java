package gnete.card.service.impl;

import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;

import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import flink.security.IUserCertificateCache;
import flink.security.SignatureException;
import flink.security.UserCertificateBean;
import flink.security.impl.AbstractSignatureProcessImpl;
import flink.security.impl.CardSecProviderImpl;
import gnete.card.service.ICardSignatureProcess;

/**
 * <p>
 * <ul>   
 * <li>用户验证客户登陆或者交易时的数字签名</li> 
 * <li>客户关联的证书验证实体将从缓存中读取</li>
 * <li>尽可能使用bouncycastle提高的API</li>
 * </ul>     
 * </p>
 * @Project: Card
 * @File: CardSignatureProcessImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-9
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
@Service("cardSignatureProcess")
public class CardSignatureProcessImpl extends AbstractSignatureProcessImpl implements ICardSignatureProcess {
	@Autowired
	@Qualifier("cardCertificateCache")
	private IUserCertificateCache cardCertificateCache;	
	
	public CardSignatureProcessImpl() {
		super();		
	}
	
	public boolean verifySignedInfo(String serialId, String hexSignedInfo, String info) throws SignatureException {
		UserCertificateBean certificateBean = this.cardCertificateCache.getCertificateBean(serialId);
		return super.verifyHexSignedInfo(certificateBean.getCertificate().getPublicKey(), hexSignedInfo, info);
	}
	
	public boolean verifySignedInfo(UserCertificateBean certificateBean, String hexSignedInfo, String info) throws SignatureException {
		return super.verifyHexSignedInfo(certificateBean.getCertificate().getPublicKey(), hexSignedInfo, info);
	}

	@Override
	public Provider getProvider() {
		return CardSecProviderImpl.getInstance().getProvider();
	}

	@Override
	public String getSigAlgorithm() {
		return DEFAULT_CA_SIGNATURE_ALGORITHM;
	}

	@Override
	public PrivateKey getSigPrivateKey(String hexPrivateKey) throws SignatureException {
		return getDefaultSigPrivateKey(hexPrivateKey);
	}

	@Override
	public PublicKey getSigPublicKey(String hexPublicKey) throws SignatureException {
		return getDefaultSigPublicKey(hexPublicKey);
	}

	@Override
	public String setKeyFactoryAlgorithm() {
		return DEFAULT_KEYFACTORY_ALGORITHM;
	}

	@Override
	public byte[] getUpdateInfoBytes(String info) {
		return Strings.toUTF8ByteArray(info);
	}

	@Override
	protected String bytesToHexStr(byte[] bcd) {
		return new String(Hex.encode(bcd));
	}

	@Override
	protected byte[] hexStrToBytes(String s) {
		return Hex.decode(s);
	}

}
