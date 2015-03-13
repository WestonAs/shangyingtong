package gnete.card.service.impl;

import java.io.File;
import java.math.BigInteger;
import java.security.Provider;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import flink.security.CertOperateException;
import flink.security.IUserCertificateCache;
import flink.security.impl.AbstractCertifcateKeyStoreImpl;
import flink.security.impl.CardSecProviderImpl;
import flink.util.CommonHelper;
import flink.util.FileHelper;
import gnete.card.entity.UserCertificate;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.UserCertificateState;
import gnete.card.service.ICardCertificateKeyStore;
import gnete.card.service.UserCertificateRevService;
import gnete.card.service.mgr.SysparamCache;

/**
 * <p>本系统数字证书提取实现类</p>
 * @Project: MyCard
 * @File: CardCertificateKeyStoreImpl.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-8
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
@Service("cardCertificateKeyStore")
public class CardCertificateKeyStoreImpl extends AbstractCertifcateKeyStoreImpl implements ICardCertificateKeyStore {

	/** 用户数字证书缓存*/
	@Autowired
	@Qualifier("cardCertificateCache")
	private IUserCertificateCache cardCertificateCache;

	public CardCertificateKeyStoreImpl() {
		super();
	}

	public UserCertificate getUserCertificate(File certFile, String branchCode, String deptId, UserInfo loginUser)
			throws CertOperateException {
		return _getUserCertificate(certFile, branchCode, deptId, loginUser);
	}

	public List<UserCertificate> getUserCertificates(File[] certFiles, String branchCode, String deptId, UserInfo loginUser)
			throws CertOperateException {

		List<UserCertificate> branchCertifcateList = new ArrayList<UserCertificate>();

		for (File certFile : certFiles) {
			UserCertificate branchCertifcate = _getUserCertificate(certFile, branchCode, deptId, loginUser);
			branchCertifcateList.add(branchCertifcate);
		}

		return branchCertifcateList;
	}

	public Map<UserCertificate, File> getUserCertificateMap(File[] certFiles, String branchCode, String deptId, UserInfo loginUser)
			throws CertOperateException {

		Map<UserCertificate, File> certificateFileMap = new HashMap<UserCertificate, File>(certFiles.length);

		for (File certFile : certFiles) {
			UserCertificate branchCertifcate = _getUserCertificate(certFile, branchCode, deptId, loginUser);
			certificateFileMap.put(branchCertifcate, certFile);
		}

		return certificateFileMap;
	}

	/**
	 * <p>满足新需求去掉deptId(部门编号)并改变文件名</p>
	 */
	public Map<UserCertificate, File> getUserCertificateMap(File[] certFiles, String branchCode, UserInfo loginUser)
			throws CertOperateException {
		Map<UserCertificate, File> certificateFileMap = new LinkedHashMap<UserCertificate, File>(certFiles.length);

		for (File certFile : certFiles) {
			UserCertificate branchCertifcate = _getUserCertificate(certFile, branchCode, loginUser);
			certificateFileMap.put(branchCertifcate, certFile);
		}

		return certificateFileMap;
	}
	

	private UserCertificate _getUserCertificate(File certFile, String branchCode, String deptId, UserInfo loginUser)
			throws CertOperateException {

		UserCertificate userCertificate = super.getUserCertificateFromCertFile(certFile);

		String fileName = FileHelper.getFullFileName(certFile);

		if (userCertificate == null) {
			throw new CertOperateException("上传的证书文件类型错误,文件名[" + fileName + "]");
		}

		userCertificate.setBranchCode(branchCode);		
		userCertificate.setFileName(fileName);
		userCertificate.setUseState(UserCertificateState.UNBOUND.getValue());

		userCertificate.setUpdateTime(CommonHelper.getCalendarDate());
		userCertificate.setUpdateUser(loginUser.getUserId());

		return userCertificate;

	}

	/**
	 * 
	 * <p>构造用户数字证书对象(从文件以及机构和登录用户中整合)</p> 	
	 * @param certFile
	 * @param branchCode
	 * @param deptId
	 * @param loginUser
	 * @return
	 * @throws CertOperateException
	 * @version: 2011-4-25 下午04:05:03
	 * @See:
	 */
	private UserCertificate _getUserCertificate(File certFile, String branchCode, UserInfo loginUser) throws CertOperateException {

		UserCertificate userCertificate = super.getUserCertificateFromCertFile(certFile);

		String uploadName = FileHelper.getFullFileName(certFile);

		if (userCertificate == null) {
			throw new CertOperateException("上传的证书文件类型错误,文件名[" + uploadName + "]");
		}

		userCertificate.setBranchCode(getFilterBranchCode(branchCode));
		userCertificate.setUploadName(uploadName);
        userCertificate.setUseState(getUseState(loginUser));
        userCertificate.setUpdateTime(CommonHelper.getCalendarDate());
		userCertificate.setUpdateUser(loginUser.getUserId());

		return userCertificate;
	}
	
	/**
	 * 
	  * <p>
	  * <li>兼容前台传入的机构代码(为显示方便使用了branchCode +"|"+branchName)</li>
	  *  <li>注意一下pages/userCert/add.jsp中的分隔符缺省使用|</li>
	  * </p>    
	  * @param branchCode
	  * @return  
	  * @version: 2011-6-2 上午10:10:18
	  * @See:
	 */
	private String getFilterBranchCode(String branchCode) {
		String[] array = branchCode.split("\\" + UserCertificateRevService.VALUE_SPLIT);
		
		return CommonHelper.safeTrim(array[0]);
	}
	
	/**
	 * 
	  * <p>便捷办法将useState在loginUserInfo中携带过来</p>
	  * @param loginUser
	  * @return  
	  * @version: 2011-5-20 下午05:10:54
	  * @See:
	 */
	private String getUseState(UserInfo loginUser) {
		if(UserCertificateState.ALL.containsKey(loginUser.getState())) {
			return loginUser.getState();
		}
		return UserCertificateState.UNBOUND.getValue();
	}


	@Override
	protected String getCertificateSeqNo(X509Certificate certificate) {
		BigInteger serial = certificate.getSerialNumber();
		String _serial = serial.toString(DEFAULT_SEQ_RADIX).toUpperCase();
		return (_serial.length() % 2 == 1) ? DEFAULT_SEQ_START.concat(_serial) : _serial;
	}

	@Override
	protected void checkCertificateIssure(Certificate certificate) throws CertOperateException {

		String checkVerify = SysparamCache.getInstance().getCACertFileVerify();

		boolean flag = CommonHelper.str2Boolean(checkVerify);

		if (flag) {
			Certificate caCertificate = this.cardCertificateCache.getCACertificate();

			if (caCertificate == null) {
				throw new CertOperateException("根证书尚未设置加载,无法进行证书签发核对!");
			}
			
			super.checkCardIssure(certificate, caCertificate);
	    }
	}

	@Override
	protected String getCertificateType() {
		return DEFAULT_CERTIFCATE_TYPE;
	}

	@Override
	protected String getKeyStoreType() {
		return DEFAULT_KEYSTORE_TYPE;
	}

	@Override
	protected Provider getProvider() {
		return CardSecProviderImpl.getInstance().getProvider();
	}

	/**
	 * <p>
	 * <li>issureDN==E=WST@GNETE.COM,C=CN,ST=GUANGXI,L=NANING,O=NANNINGSHIGUANGYINXIAOEDAIKUANGUFENYOUXIANGONGSI,OU=CAIWU,CN=LIULIXIA2</li>
	 * <li>从上述中提取CN部分对应的内容</li>
	 * </p>  
	 */
	@Override
	protected String getCertificateFileName(String issureDN,File certFile) {
		String[] issureArray = issureDN.split("\\,");
		String cnContent = issureArray[issureArray.length - 1];
		String[] cnArray = cnContent.split("\\=");
		return new StringBuilder(25).append(cnArray[cnArray.length - 1]).append(".")
                                     .append(FileHelper.getFileExtension(certFile)).toString();
	}

	/**
	 * <p>
	 * <li>好易联提供的根证书比较奇怪,无论是基于公钥还是路径(网上或实际部分)</li> 
	 * <li>当下只有获取签发者的对象进行比较</li>
	 * </p>
	 */
	@Override
	protected boolean needVerify() {
		return false;
	}

	

}
