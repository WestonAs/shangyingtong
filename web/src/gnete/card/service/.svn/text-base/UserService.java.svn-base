package gnete.card.service;

import gnete.card.entity.UserCertificate;
import gnete.card.entity.UserCertificateKey;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;
import gnete.etc.BooleanMessage;

public interface UserService {

	/**
	 * 添加用户
	 * @param userInfo
	 * @return
	 * @throws BizException
	 */
	boolean addUser(UserInfo userInfo, String sessionUser) throws BizException;
	
	/**
	 * 修改用户
	 * @param userInfo
	 * @return
	 * @throws BizException
	 */
	boolean modifyUser(UserInfo userInfo, String sessionUser) throws BizException ;
	
	/**
	 * 注销用户
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean cancelUser(String userId, String sessionUser) throws BizException ;
	
	/**
	 * 生效某用户
	 * @param userId
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	boolean activateUser(String userId, String sessionUser) throws BizException ;
	
	/**
	 * 查找用户
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	UserInfo getUser(String userId) throws BizException;

	/**
	 * 给用户分配角色
	 * @param userId
	 * @param roles
	 * @param sessionUser
	 */
	void assignUser(String userId, String roles, UserInfo sessionUser) throws BizException;

	
	/**
	 * 登录检查
	 * @param userId
	 * @param pwdWithDoubleMd5 已加密过的输入密码：md5(随机数+md5(源密码)
	 * @param roleId
	 * @return 登录是否成功，及相关信息
	 */
	BooleanMessage checkLogin(String userId, String pwdWithDoubleMd5, String random, String roleId);

	/**
	 * 证书登录
	 * @param serialNo
	 * @return
	 * @throws BizException
	 */
	UserInfo certLogin(String serialNo) throws BizException;

	/**
	 * 添加用户证书
	 * @param userCertificate
	 * @param sessionUserCode
	 */
	void addUserCert(UserCertificate userCertificate, String sessionUserCode) throws BizException;

	/**
	 * 修改用户证书
	 * @param userCertificate
	 * @param sessionUserCode
	 */
	void modifyUserCert(UserCertificate userCertificate, String sessionUserCode) throws BizException;

	/**
	 * 注销用户证书
	 * @param userCertificateKey
	 * @param sessionUserCode
	 */
	void cancelUserCert(UserCertificateKey userCertificateKey,
			String sessionUserCode) throws BizException;

	/**
	 * 生效用户证书
	 * @param userCertificateKey
	 * @param sessionUserCode
	 */
	void activeUserCert(UserCertificateKey userCertificateKey,
			String sessionUserCode) throws BizException;

	/**
	 * 修改用户密码
	 * @param oldPwdWithDoubleMd5
	 * @param random
	 * @param newPassWithMd5
	 * @param sessionUser
	 */
	void modifyPass(String oldPwdWithDoubleMd5, String random, String newPassWithMd5, UserInfo sessionUser) throws BizException ;

	/**
	 * 重置用户密码
	 * @param userId
	 * @param sessionUserCode
	 */
	String resetPass(String userId, String sessionUserCode) throws BizException ;

	/**
	 * 登出时的处理
	 * @param sessionUser
	 */
	void logout(UserInfo sessionUser);
	
	/**
	 * 检查证书绑定的用户与当前做操作的用户的用户ID是否一致
	 * @param serialNo
	 * @param user
	 * @return
	 * @throws BizException
	 */
	public boolean checkCertUser(String serialNo, UserInfo user) throws BizException;

	/**
	 *  检查用户是否已经绑定了证书 
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean checkUserIsExistCert(String userId) throws BizException;

	/**
	 *  检查用户是否有生效的绑定证书 
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean checkEffectiveCertUser(String userId) throws BizException;
	
}
