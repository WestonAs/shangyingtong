package gnete.card.web.user;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.DepartmentInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.RoleInfoDAO;
import gnete.card.dao.UserInfoDAO;
import gnete.card.dao.UserRoleDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.DepartmentInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.UserRoleKey;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.UserService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

/**
 * @File: UserAction.java
 *
 * @description: 用户管理
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: LiHeng
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-7-7 下午08:20:45
 */
public class UserAction extends BaseAction {
	private static final String LOGIN_PWD_RANDOM = "LOGIN_PWD_RANDOM";
	
	@Autowired
	private UserInfoDAO userInfoDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleInfoDAO roleInfoDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private DepartmentInfoDAO departmentInfoDAO;
	
	private UserInfo userInfo;
	
	private Paginater page;

	private String roles;
	private String hasRole;
	
	private List<RoleInfo> roleList;
	private List<BranchInfo> branchs;
	private List<DepartmentInfo> depts;
	private List<MerchInfo> merchs;
	private List<RoleType> roleTypeList;
	
	// 控制页面显示
	private boolean showBranch = false;
	private boolean showDept = false;
	private boolean showMerch = false;
	
	private String oldPass;
	private String newPass;
	
	
	private String userBranchName; // 机构名 
	private String userMerchName; //商户名
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
//		initPage();
		Map<String, Object> params = new HashMap<String, Object>();
		if (userInfo != null) {
			params.put("branchNo", userInfo.getBranchNo());
			params.put("merchantNo", userInfo.getMerchantNo());
			params.put("deptId", userInfo.getDeptId());
			params.put("userName", MatchMode.ANYWHERE.toMatchString(userInfo.getUserName()));
			params.put("userId", userInfo.getUserId());
		}
		
//		if ( !(getLoginRoleType().equals(RoleType.CENTER.getValue()) 
//				|| getLoginRoleType().equals(RoleType.FENZHI.getValue()))) {
//			if (showBranch) {
//				if (CollectionUtils.isNotEmpty(branchs)) {
//					params.put("branchNo", branchs.get(0).getBranchCode());
//				}
//			} else if (showDept) {
//				if (CollectionUtils.isNotEmpty(depts)) {
//					params.put("deptId", depts.get(0).getDeptId());
//				}
//			} else if (showMerch){
//				if (CollectionUtils.isNotEmpty(merchs)) {
//					params.put("merchantNo", merchs.get(0).getMerchId());
//				}
//			}
//		}
		
		if (isCenterRoleLogined()) {// 运营中心
			this.showMerch = true;
			this.showBranch = true;
			this.showDept = true;
			depts = this.getMyDept();
		} else if (isFenzhiRoleLogined()) {// 分支机构
			this.showMerch = true;
			this.showBranch = true;
//			params.put("fenzhiCode", this.getLoginBranchCode());
			params.put("fenzhiList", this.getMyManageFenzhi());

//			branchs = this.getMyBranch();
//			merchs = this.getMyMerch();
//
//			params.put("branchs", branchs);
//			params.put("merchs", merchs);
//			if (CollectionUtils.isNotEmpty(branchs) || CollectionUtils.isNotEmpty(merchs)) {
//				params.put("union", true);
//			}
//			if (CollectionUtils.isNotEmpty(branchs) && CollectionUtils.isNotEmpty(merchs)) {
//				params.put("union_or", true);
//			}
		} else if (isCardRoleLogined()) {// 发卡机构
			this.showDept = true;
			this.showBranch = true;
//			branchs = this.getMyBranch();
			this.branchs = this.getMyCardBranch();
			depts = this.getMyDept();
			params.put("cardBranchList", branchs);
		} else if (isCenterDeptRoleLogined() || isCardDeptRoleLogined()) {// 运营中心部门和发卡机构部门
			this.showDept = true;
			depts = this.getMyDept();
			if (CollectionUtils.isNotEmpty(depts)) {
				params.put("deptId", depts.get(0).getDeptId());
			}
		} else if (isMerchantRoleLogined()) {// 商户
			this.showMerch = true;
			merchs = this.getMyMerch();
			if (CollectionUtils.isNotEmpty(merchs)) {
				params.put("merchantNo", merchs.get(0).getMerchId());
			}
		} else {// 其他机构
			this.showBranch = true;
			branchs = this.getMyBranch();

			if (CollectionUtils.isNotEmpty(branchs)) {
				params.put("branchNo", branchs.get(0).getBranchCode());
			}
		}
		
		this.page = this.userInfoDAO.findUser(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.userInfo = (UserInfo) this.userInfoDAO.findByPk(this.userInfo.getUserId());
		this.roleList = roleInfoDAO.findByUserId(this.userInfo.getUserId());
//		this.log("查询用户信息["+ userInfo.getUserId() +"]成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		initPage();
		return ADD;
	}

	private void initPage() {
//		branchs = this.getMyBranch();
		depts = this.getMyDept();
//		merchs = this.getMyMerch();
		
		if (this.getLoginRoleType().equals(RoleType.CENTER.getValue())){
			this.showMerch = true;
			this.showBranch = true;
			this.showDept = true;
			this.roleTypeList = RoleType.getCenter();
			
		} else if (this.getLoginRoleType().equals(RoleType.FENZHI.getValue())) {
			this.showMerch = true;
			this.showBranch = true;
			this.roleTypeList = RoleType.getManage();
			
		}  else if (this.getLoginRoleType().equals(RoleType.CARD.getValue())) {
			this.showDept = true;
			this.showBranch = true;
			this.roleTypeList = RoleType.getForCard();
			
			this.branchs = this.getMyCardBranch();
//			this.branchs = new ArrayList<BranchInfo>();
//			this.branchs.add(this.branchInfoDAO.findBranchInfo(this.getLoginBranchCode()));
			
		} else if (this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue())) {
			this.showDept = true;
			this.branchs = new ArrayList<BranchInfo>();
			this.branchs.add(this.branchInfoDAO.findBranchInfo(this.getLoginBranchCode()));
			
		} else if (this.getLoginRoleType().equals(RoleType.MERCHANT.getValue())) {
			this.showMerch = true;
			this.merchs = new ArrayList<MerchInfo>();
			this.merchs.add((MerchInfo) this.merchInfoDAO.findByPk(this.getLoginBranchCode()));
			
		} else {
			this.showBranch = true;
			this.branchs = new ArrayList<BranchInfo>();
			this.branchs.add(this.branchInfoDAO.findBranchInfo(this.getLoginBranchCode()));
		}
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.userInfo = (UserInfo) this.userInfoDAO.findByPk(this.userInfo.getUserId());
		
		if (userInfo.getBranchNo() != null && StringUtils.isNotEmpty(userInfo.getBranchNo())) {
			this.branchs = new ArrayList<BranchInfo>();
			this.branchs.add((BranchInfo) this.branchInfoDAO.findByPk(userInfo.getBranchNo()));
			showBranch = true;
		}
		if (userInfo.getMerchantNo() != null && StringUtils.isNotEmpty(userInfo.getMerchantNo())) {
			this.merchs = new ArrayList<MerchInfo>();
			this.merchs.add((MerchInfo) this.merchInfoDAO.findByPk(userInfo.getMerchantNo()));
			showMerch = true;
		}
		if (userInfo.getDeptId() != null && StringUtils.isNotEmpty(userInfo.getDeptId())) {
			this.depts = new ArrayList<DepartmentInfo>();
			this.depts.add((DepartmentInfo) this.departmentInfoDAO.findByPk(userInfo.getDeptId()));
			showDept = true;
		}
		return MODIFY;
	}
	
	// 新增对象操作
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		// 调用service业务接口
		this.userService.addUser(this.userInfo, this.getSessionUserCode());
		String msg = "添加用户["+this.userInfo.getUserId()+"]成功！";
		this.addActionMessage("/pages/user/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 修改对象操作
	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		// 调用service业务接口
		this.userService.modifyUser(userInfo, this.getSessionUserCode());

		String msg = "修改用户["+this.userInfo.getUserId()+"]成功！";
		this.addActionMessage("/pages/user/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	// 注销对象操作
	public String cancel() throws Exception {
		
		String userId = request.getParameter("userId");
		this.userService.cancelUser(userId, this.getSessionUserCode());

		String msg = "注销用户["+userId+"]成功！";
		this.addActionMessage("/pages/user/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	// 生效对象操作
	public String activate() throws Exception {
		this.checkEffectiveCertUser();
		
		String userId = request.getParameter("userId");
		this.userService.activateUser(userId, this.getSessionUserCode());
		
		String msg = "生效用户["+userId+"]成功！";
		this.addActionMessage("/pages/user/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String showAssign() throws Exception {
		this.checkEffectiveCertUser();
		
		this.userInfo = (UserInfo) this.userInfoDAO.findByPk(this.userInfo.getUserId());
		String branchCode = this.userInfo.getBranchNo();
		
		this.roleList = roleInfoDAO.findAssignRole(branchCode, userInfo.getMerchantNo(), userInfo.getDeptId());
		List<UserRoleKey> hasRoles = userRoleDAO.findByUserId(this.userInfo.getUserId());
		hasRole = ",";
		for (UserRoleKey key : hasRoles) {
			hasRole += StringUtils.trim(key.getRoleId()) + ",";
		}
		return "assign";
	}
	
	public String assign() throws Exception {
		this.checkEffectiveCertUser();
		
		this.userService.assignUser(this.userInfo.getUserId(), roles, this.getSessionUser());

		String msg = "给用户["+this.userInfo.getUserId()+"]分配角色成功！";
		this.addActionMessage("/pages/user/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	/** 重设密码 */
	public String resetPass() throws Exception {
		this.checkEffectiveCertUser();
		
		if(!(isCenterOrCenterDeptRoleLogined()||isFenzhiRoleLogined())){
			throw new BizException("非 运营中心或部门、运营机构,不允许进行操作。");
		}
		
		String userId = request.getParameter("userId");
		String defaultPass = this.userService.resetPass(userId, this.getSessionUserCode());
		
		String msg = "重置用户["+userId+"]密码成功，密码已经改成["+ defaultPass +"]！";
		this.addActionMessage("/pages/user/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String showModifyPass() throws Exception {
		String random = RandomStringUtils.randomNumeric(16);
		WebUtils.setSessionAttribute(request, LOGIN_PWD_RANDOM, random);
		this.formMap.put("random", random);
		
		return "modifyPass";
	}

	public String modifyPass() throws Exception {
		this.userService.modifyPass(oldPass, WebUtils.getSessionAttribute(request, LOGIN_PWD_RANDOM)
				.toString(), newPass, this.getSessionUser());

		WebUtils.setSessionAttribute(request, LOGIN_PWD_RANDOM, null);
		
		String msg = "用户["+this.getSessionUser().getUserId()+"]修改密码成功！";
		this.addActionMessage("/home.jsp", msg);
		this.log(msg, UserLogType.UPDATE, "modifypass");
		return SUCCESS;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public List<RoleInfo> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleInfo> roleList) {
		this.roleList = roleList;
	}

	public String getHasRole() {
		return hasRole;
	}

	public void setHasRole(String hasRole) {
		this.hasRole = hasRole;
	}

	public List<BranchInfo> getBranchs() {
		return branchs;
	}

	public void setBranchs(List<BranchInfo> branchs) {
		this.branchs = branchs;
	}

	public List<DepartmentInfo> getDepts() {
		return depts;
	}

	public void setDepts(List<DepartmentInfo> depts) {
		this.depts = depts;
	}

	public List<MerchInfo> getMerchs() {
		return merchs;
	}

	public void setMerchs(List<MerchInfo> merchs) {
		this.merchs = merchs;
	}

	public boolean isShowBranch() {
		return showBranch;
	}

	public void setShowBranch(boolean showBranch) {
		this.showBranch = showBranch;
	}

	public boolean isShowDept() {
		return showDept;
	}

	public void setShowDept(boolean showDept) {
		this.showDept = showDept;
	}

	public boolean isShowMerch() {
		return showMerch;
	}

	public void setShowMerch(boolean showMerch) {
		this.showMerch = showMerch;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public List<RoleType> getRoleTypeList() {
		return roleTypeList;
	}

	public void setRoleTypeList(List<RoleType> roleTypeList) {
		this.roleTypeList = roleTypeList;
	}

	public String getUserBranchName() {
		return userBranchName;
	}

	public void setUserBranchName(String userBranchName) {
		this.userBranchName = userBranchName;
	}

	public String getUserMerchName() {
		return userMerchName;
	}

	public void setUserMerchName(String userMerchName) {
		this.userMerchName = userMerchName;
	}

}
