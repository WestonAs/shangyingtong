package gnete.card.service.impl; 

import flink.util.StringUtil;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.DepartmentInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.UserCertificateDAO;
import gnete.card.dao.UserCertificateRevDAO;
import gnete.card.dao.UserInfoDAO;
import gnete.card.dao.UserRoleDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.DepartmentInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.UserCertificate;
import gnete.card.entity.UserCertificateKey;
import gnete.card.entity.UserInfo;
import gnete.card.entity.UserRoleKey;
import gnete.card.entity.state.BranchState;
import gnete.card.entity.state.DepartmentState;
import gnete.card.entity.state.MerchState;
import gnete.card.entity.state.UseState;
import gnete.card.entity.state.UserCertificateState;
import gnete.card.entity.state.UserState;
import gnete.card.service.UserService;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.BooleanMessage;
import gnete.util.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserInfoDAO userInfoDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;
	@Autowired
	private UserCertificateDAO userCertificateDAO;
	@Autowired
	private UserCertificateRevDAO userCertificateRevDAO;
	@Autowired
	private DepartmentInfoDAO departmentInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;

	public boolean addUser(UserInfo userInfo, String sessionUser) throws BizException {
		Assert.notNull(userInfo, "新增的用户对象不能为空");
		
		UserInfo old = (UserInfo) this.userInfoDAO.findByPk(userInfo.getUserId());
		Assert.isNull(old, "该用户代码"+ userInfo.getUserId() +"已经存在");
		
		// 当添加部门用户时，给用户添加机构编号
		if (StringUtils.isNotEmpty(userInfo.getDeptId())) {
			DepartmentInfo departmentInfo = (DepartmentInfo) this.departmentInfoDAO.findByPk(userInfo.getDeptId());
			userInfo.setBranchNo(departmentInfo.getBranchNo());
		}
		
		userInfo.setState(UserState.NORMAL.getValue());
		userInfo.setUpdateUser(sessionUser);
		userInfo.setUpdateTime(new Date());
		try {
			userInfo.setUserPwd(StringUtil.getMD5(SysparamCache.getInstance().getDefaultPwd()));
			userInfo.setInitPwd(true);
			userInfo.setPwdDate(DateUtil.formatDate("yyyyMMdd"));
			userInfo.setPwdMismatchCnt(0);
		} catch (Exception e) {
			throw new BizException("生成用户密码时发生错误！");
		}
		
		return this.userInfoDAO.insert(userInfo) != null;
	}
	
	public boolean modifyUser(UserInfo userInfo, String sessionUser) throws BizException {
		Assert.notNull(userInfo, "修改的用户对象不能为空");
		
		UserInfo old = (UserInfo) this.userInfoDAO.findByPk(userInfo.getUserId());
		Assert.notNull(old, "该用户代码"+ userInfo.getUserId() +"不存在");
		
		// 当添加部门用户时，给用户添加机构编号
		if (StringUtils.isNotEmpty(userInfo.getDeptId())) {
			DepartmentInfo departmentInfo = (DepartmentInfo) this.departmentInfoDAO.findByPk(userInfo.getDeptId());
			userInfo.setBranchNo(departmentInfo.getBranchNo());
		}
		
		userInfo.setUserPwd(old.getUserPwd());
		userInfo.setInitPwd(old.isInitPwd());
		userInfo.setPwdDate(old.getPwdDate());
		userInfo.setPwdMismatchCnt(old.getPwdMismatchCnt());
		userInfo.setState(UserState.NORMAL.getValue());
		userInfo.setUpdateUser(sessionUser);
		userInfo.setUpdateTime(new Date());
		
		return this.userInfoDAO.update(userInfo) > 0;
	}
	
	public boolean cancelUser(String userId, String sessionUser) throws BizException {
		Assert.notEmpty(userId, "注销的用户编号不能为空");
		
		UserInfo old = (UserInfo) this.userInfoDAO.findByPk(userId);
		Assert.notNull(old, "该用户代码"+ userId +"不存在");
		
		old.setState(UserState.CANCEL.getValue());
		old.setUpdateUser(sessionUser);
		old.setUpdateTime(new Date());
		
		return this.userInfoDAO.update(old) > 0;
	}
	
	public boolean activateUser(String userId, String sessionUser) throws BizException {
		Assert.notEmpty(userId, "生效的用户编号不能为空");
		
		UserInfo old = (UserInfo) this.userInfoDAO.findByPk(userId);
		Assert.notNull(old, "该用户代码"+ userId +"不存在");
		
		old.setState(UserState.NORMAL.getValue());
		old.setUpdateUser(sessionUser);
		old.setUpdateTime(new Date());
		
		return this.userInfoDAO.update(old) > 0;
	}
	
	public void assignUser(String userId, String roles, UserInfo sessionUser) throws BizException {
		Assert.notEmpty(userId, "用户编号不能为空");
		Assert.notEmpty(roles, "角色不能为空");
		
		// 先删掉原有角色
		this.userRoleDAO.deleteByUserId(userId);
		
		String[] roleArray = roles.split(",");
		for (String roleId : roleArray) {
			UserRoleKey key = new UserRoleKey(StringUtils.trim(roleId), userId);
			this.userRoleDAO.insert(key);
		}
	}
	
	public UserInfo getUser(String userId) {
		return (UserInfo) this.userInfoDAO.findByPk(userId);
	}
	
	@Override
	public BooleanMessage checkLogin(String userId, String pwdWithDoubleMd5, String random, String roleId) {
		try {
			Assert.notEmpty(userId, "用户编号不能为空");
			Assert.notEmpty(pwdWithDoubleMd5, "用户密码不能为空");
			Assert.notEmpty(random, "页面失效，请刷新页面");
			Assert.notEmpty(roleId, "角色不能为空");
			
			UserInfo userInfo = (UserInfo) this.userInfoDAO.findByPk(userId);
			Assert.notNull(userInfo, "用户名错误");
			Assert.notTrue(checkUserIsExistCert(userId), "该用户已经绑定了证书，请使用证书登陆系统！");

			checkState(userInfo);
			Assert.notNull(this.userRoleDAO.findByPk(new UserRoleKey(roleId, userId)), "非法访问");

			Assert.isTrue(userInfo.getPwdMismatchCnt() < 5, "该用户已经连续输错5次密码，已经被锁定");
			
			String orgPwdWithDoubleMd5 = null;
			try {
				orgPwdWithDoubleMd5 = StringUtil.getMD5(random + userInfo.getUserPwd());
			} catch (Exception e) {
				throw new BizException("系统验证密码时发生错误");
			}
			
			if (!pwdWithDoubleMd5.equals(orgPwdWithDoubleMd5)) { // 密码不正确
				userInfo.setPwdMismatchCnt(userInfo.getPwdMismatchCnt() + 1);
				this.userInfoDAO.update(userInfo);
				throw new BizException("密码错误，连续错误" + userInfo.getPwdMismatchCnt() + "次数，超过5次将被锁定！");
			} else {
				if (userInfo.getPwdMismatchCnt() != 0) {
					userInfo.setPwdMismatchCnt(0);
					this.userInfoDAO.update(userInfo);
				}
				return new BooleanMessage(true);
			}
		} catch (BizException e) {
			return new BooleanMessage(false,e.getMessage());
		}
	}
	

	@Override
	public boolean checkUserIsExistCert(String userId) throws BizException {
		Map<String, Object>params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("useState", UserCertificateState.BOUND.getValue());
		List<UserCertificate> list = this.userCertificateRevDAO.findUserCertificate(params);
		return CollectionUtils.isNotEmpty(list);
	}
	
	@Override
	public boolean checkEffectiveCertUser(String userId) throws BizException {
		Map<String, Object>params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("useState", UserCertificateState.BOUND.getValue());
		params.put("state", "00"); // 生效
		List<UserCertificate> list = this.userCertificateRevDAO.findUserCertificate(params);
		return CollectionUtils.isNotEmpty(list);
	}
	
	public UserInfo certLogin(String serialNo) throws BizException {
		Assert.notEmpty(serialNo, "证书序列号不能为空");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seqNo", serialNo);
		List<UserCertificate> userCertlist = userCertificateRevDAO.findUserCertificate(params);
		Assert.isTrue(CollectionUtils.isNotEmpty(userCertlist), "该证书没有绑定任何用户。");
		
		UserCertificate userCert = userCertlist.get(0);
		Assert.isTrue(userCert.getDaysToExpire() >= 0, "该用户证书已经过了失效日期!");

		UserInfo userInfo = (UserInfo) this.userInfoDAO.findByPk(userCert.getUserId());
		Assert.notNull(userInfo, "该证书所绑定的用户已经不存在");
		
		checkState(userInfo);

		userInfo.setState(UserState.ONLINE.getValue());
		this.userInfoDAO.update(userInfo);

		userInfo.setUserCertificate(userCert);

		return userInfo;
	}
	
	private void checkState(UserInfo userInfo) throws BizException {
		Assert.notTrue(userInfo.getState().equals(UserState.CANCEL.getValue()), "该用户已经注销");
		
		// 判断机构是否已停用、商户是否已注销、部门是否已注销
		String branchNo = userInfo.getBranchNo();
		if (StringUtils.isNotEmpty(branchNo)){
			BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(branchNo);
			Assert.notTrue(BranchState.STOPED.getValue().equals(branchInfo.getStatus()), "当前用户所属机构[" + branchNo + "]已停用");
		}
		String merchantNo = userInfo.getMerchantNo();
		if (StringUtils.isNotEmpty(merchantNo)){
			MerchInfo merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(merchantNo);
			Assert.notTrue(MerchState.CANCEL.getValue().equals(merchInfo.getStatus()), "当前用户所属商户[" + merchantNo + "]已被注销");
		}
		String deptId = userInfo.getDeptId();
		if (StringUtils.isNotEmpty(deptId)){
			DepartmentInfo departmentInfo = (DepartmentInfo) this.departmentInfoDAO.findByPk(deptId);
			Assert.notTrue(DepartmentState.CANCEL.getValue().equals(departmentInfo.getStatus()), "当前用户所属部门[" + deptId + "]已被注销");
		}
	}
	
	public void addUserCert(UserCertificate userCertificate,
			String sessionUser) throws BizException {
		Assert.notNull(userCertificate, "新增的用户证书对象不能为空");
		
		UserInfo oldUser = (UserInfo) this.userInfoDAO.findByPk(userCertificate.getUserId());
		Assert.notNull(oldUser, "该用户编号"+ userCertificate.getUserId() +"不存在");
		
		UserCertificate old = (UserCertificate) this.userCertificateDAO.findByPk(
				new UserCertificateKey(userCertificate.getDnNo(), userCertificate.getSeqNo(), 
				userCertificate.getStartDate(), userCertificate.getUserId()));
		Assert.isNull(old, "该用户证书["+ userCertificate.getDnNo()+ ", " + userCertificate.getSeqNo()+ ", " +  
				userCertificate.getStartDate()+ ", " + userCertificate.getUserId() +"]已经存在");
		
		userCertificate.setState(UserCertificateState.UNBOUND.getValue());
		userCertificate.setUseState(UseState.UNUSE.getValue());
		userCertificate.setUpdateUser(sessionUser);
		userCertificate.setUpdateTime(new Date());
		
		this.userCertificateDAO.insert(userCertificate);
	}
	
	public void modifyUserCert(UserCertificate userCertificate,
			String sessionUser) throws BizException {
		Assert.notNull(userCertificate, "修改的用户证书对象不能为空");
		
		UserCertificate old = (UserCertificate) this.userCertificateDAO.findByPk(
				new UserCertificateKey(userCertificate.getDnNo(), userCertificate.getSeqNo(), 
						userCertificate.getStartDate(), userCertificate.getUserId()));
		Assert.notNull(old, "该用户证书["+ userCertificate.getDnNo()+ ", " + userCertificate.getSeqNo()+ ", " +  
				userCertificate.getStartDate()+ ", " + userCertificate.getUserId() +"]不存在");
		
		userCertificate.setState(old.getState());
		userCertificate.setUseState(old.getUseState());
		userCertificate.setUpdateUser(sessionUser);
		userCertificate.setUpdateTime(new Date());
		
		this.userCertificateDAO.update(userCertificate);
	}
	
	public void cancelUserCert(UserCertificateKey userCertificateKey,
			String sessionUser) throws BizException {
		Assert.notNull(userCertificateKey, "注销的用户证书不能为空");
		
		UserCertificate old = (UserCertificate) this.userCertificateDAO.findByPk(userCertificateKey);
		Assert.notNull(old, "该用户证书["+ userCertificateKey.getDnNo()+ ", " + userCertificateKey.getSeqNo()+ ", " +  
				userCertificateKey.getStartDate()+ ", " + userCertificateKey.getUserId() +"]不存在");
		
		old.setState(UserCertificateState.UNBOUND.getValue());
		old.setUpdateUser(sessionUser);
		old.setUpdateTime(new Date());
		
		this.userCertificateDAO.update(old);
	}
	
	public void activeUserCert(UserCertificateKey userCertificateKey,
			String sessionUser) throws BizException {
		Assert.notNull(userCertificateKey, "生效的用户证书不能为空");
		
		UserCertificate old = (UserCertificate) this.userCertificateDAO.findByPk(userCertificateKey);
		Assert.notNull(old, "该用户证书["+ userCertificateKey.getDnNo()+ ", " + userCertificateKey.getSeqNo()+ ", " +  
				userCertificateKey.getStartDate()+ ", " + userCertificateKey.getUserId() +"]不存在");
		
		old.setState(UserCertificateState.BOUND.getValue());
		old.setUpdateUser(sessionUser);
		old.setUpdateTime(new Date());
		
		this.userCertificateDAO.update(old);
	}
	
	public void modifyPass(String oldPwdWithDoubleMd5, String random, String newPassWithMd5, UserInfo sessionUser) throws BizException {
		Assert.notEmpty(oldPwdWithDoubleMd5, "用户旧密码不能为空");
		Assert.notEmpty(random, "页面失效，请重新进入该页面");
		Assert.notEmpty(newPassWithMd5, "用户新密码不能为空");
		
		try {
			Assert.isTrue(StringUtil.getMD5(random + sessionUser.getUserPwd()).equals(oldPwdWithDoubleMd5), "用户旧密码不对");
			Assert.notTrue(StringUtil.getMD5(SysparamCache.getInstance().getDefaultPwd()).equals(newPassWithMd5), "新密码不能过于简单");
		}catch (BizException e) {
			throw e;
		} catch (Exception e) {
			throw new BizException("系统验证密码时发生错误");
		}
		
		
		sessionUser.setUserPwd(newPassWithMd5);
		sessionUser.setUpdateTime(new Date());
		sessionUser.setUpdateUser(sessionUser.getUserId());
		sessionUser.setPwdDate(DateUtil.formatDate("yyyyMMdd"));
		sessionUser.setInitPwd(false);
		this.userInfoDAO.update(sessionUser);
	}
	
	public String resetPass(String userId, String sessionUserCode) throws BizException {
		Assert.notEmpty(userId, "用户编号不能为空");
		
		UserInfo old = (UserInfo) this.userInfoDAO.findByPk(userId);
		Assert.notNull(old, "该用户代码"+ userId +"不存在");
		
		String pass = SysparamCache.getInstance().getDefaultPwd();
		try {
			old.setUserPwd(StringUtil.getMD5(pass));
			old.setPwdDate(DateUtil.formatDate("yyyyMMdd"));
			old.setInitPwd(true);
			old.setPwdMismatchCnt(0);
		} catch (Exception e) {
			throw new BizException("生成用户密码时发生错误！");
		}
		this.userInfoDAO.update(old);
		return pass;
	}
	
	public void logout(UserInfo userInfo) {
		if (userInfo == null) return;
		if (StringUtils.isEmpty(userInfo.getUserId())) return;

		UserInfo old = (UserInfo) this.userInfoDAO.findByPk(userInfo.getUserId());
		old.setState(UserState.NORMAL.getValue());
		this.userInfoDAO.update(old);
	}
	
	/**
	 * 检查证书绑定的用户与当前做操作的用户的用户ID是否一致
	 * @param serialNo
	 * @param user
	 * @return
	 * @throws BizException
	 */
	public boolean checkCertUser(String serialNo, UserInfo user) throws BizException{
		
		Assert.notBlank(serialNo, "取证书序列号出错");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seqNo", serialNo);
		List<UserCertificate> list = userCertificateRevDAO.findUserCertificate(params);
		
		Assert.isTrue(CollectionUtils.isNotEmpty(list), "该证书没有绑定任何用户。");
		
		return StringUtils.equals(user.getUserId(), list.get(0).getUserId());
	}

}
