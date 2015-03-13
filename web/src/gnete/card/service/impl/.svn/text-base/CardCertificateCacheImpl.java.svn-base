package gnete.card.service.impl;

import java.security.cert.Certificate;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import flink.security.IUserCertificateCache;
import flink.security.UserCertificateBean;
import flink.util.CommonHelper;
import gnete.card.dao.UserCertificateRevDAO;
import gnete.card.entity.UserCertificate;
import gnete.card.entity.UserCertificateKey;
import gnete.card.service.ICardFileTransferProcess;
import gnete.util.CacheMap;

@Service("cardCertificateCache")
public class CardCertificateCacheImpl implements IUserCertificateCache {
	private final Logger								logger				= LoggerFactory.getLogger(this
																					.getClass());
	/** 根证书引用对象结构 */
	private final AtomicReference<Certificate>			cacertificateRef	= new AtomicReference<Certificate>();

	private final CacheMap<String, UserCertificateBean>	userCertBeanMap		= new CacheMap<String, UserCertificateBean>(
																					10 * 60);						// 秒

	/** 用户数字证书文件传输(FTP) */
	@Autowired
	@Qualifier("cardFileTranferProcess")
	private ICardFileTransferProcess					cardFileTransferProcess;

	@Autowired
	private UserCertificateRevDAO						userCertificateRevDAO;

	/**
	 * <p>
	 * 注意银联给的根证书早已经过期,因此这里不能直接调用checkXXValid的方法
	 * </p>
	 */
	public boolean addCACertificate(Certificate cacertificate) {
		boolean result = (needDateCheck()) ? (CommonHelper.checkCertificateValid(cacertificate,
				CommonHelper.getCalendarDate())) : true;
		if (result) {
			cacertificateRef.set(cacertificate);
		}
		return result;
	}

	public boolean checkAppCache() {
		return getCACertificate() == null;
	}

	public boolean initAppCache() {
		if (cacertificateRef.get() != null) {
			cacertificateRef.set(null);
		}
		return true;
	}

	public Certificate getCACertificate() {
		return cacertificateRef.get();
	}

	/**
	 * 设置是否需要验证根证书过期
	 */
	protected boolean needDateCheck() {
		return false;
	}

	public UserCertificateBean getCertificateBean(String serialId) {
		UserCertificateBean bean = userCertBeanMap.get(serialId);
		if (bean != null) {
			return bean;
		}

		UserCertificate record = (UserCertificate) userCertificateRevDAO.findByPk(new UserCertificateKey(
				serialId));
		if (record == null || StringUtils.isBlank(record.getFileName())) {
			logger.warn("数据库没有找到用户证书记录！相关信息：serialId[{}]", serialId);
			return null;
		}

		try {
			Certificate certificate = cardFileTransferProcess.getTransferCertificate(record.getFileName());
			UserCertificateBean boundCertificateBean = new UserCertificateBean(record.getUseState(),
					certificate);
			userCertBeanMap.put(serialId, boundCertificateBean);
			return boundCertificateBean;
		} catch (Exception e) {
			String msg = String.format("没有找到用户证书，serialId[%s], fileName[%s]", serialId, record.getFileName());
			logger.warn(msg, e);
			return null;
		}
	}

	public boolean removeCertificateBean(String serialId) {
		logger.info("手动移除证书对象:serialId[{}]", serialId);
		userCertBeanMap.remove(serialId);
		return true;
	}

}
