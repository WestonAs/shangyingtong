package gnete.card.web.user;

import flink.etc.MatchMode;
import flink.tree.PrivilegeTreeTool;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchHasTypeDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.BranchPrivilegeDAO;
import gnete.card.dao.DepartmentInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.PrivilegeDAO;
import gnete.card.dao.RoleInfoDAO;
import gnete.card.entity.BranchHasType;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BranchPrivilege;
import gnete.card.entity.DepartmentInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.Privilege;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.RoleService;
import gnete.card.tag.NameTag;
import gnete.card.web.BaseAction;
import gnete.card.workflow.dao.WorkflowPrivilegeDAO;
import gnete.card.workflow.entity.WorkflowPrivilegeKey;
import gnete.etc.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * RoleAction.java.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-7-7
 */
public class RoleAction extends BaseAction {
	
	@Autowired
	private RoleInfoDAO roleInfoDAO;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PrivilegeDAO privilegeDAO;
	@Autowired
	private WorkflowPrivilegeDAO workflowPrivilegeDAO;
	@Autowired
	private BranchPrivilegeDAO branchPrivilegeDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private DepartmentInfoDAO departmentInfoDAO;
	@Autowired
	private BranchHasTypeDAO branchHasTypeDAO;

	private RoleInfo roleInfo;
	private String privileges;
	
	private Paginater page;
	
	List<BranchInfo> branchList;
	List<MerchInfo> merchList;
	List<DepartmentInfo> deptList;
	
	private String userId;
	private List roleTypeList;
	private List<RoleInfo> roleInfoList;
	
	private String branchCode;
	private String proxyCode;
	private String deptId;
	
	// 控制页面显示
	private boolean showBranch = false;
	private boolean showDept = false;
	private boolean showMerch = false;
	
	private String userBranchName; // 机构名 
	private String userMerchName; //商户名

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
//		initPage();
		this.roleTypeList = RoleType.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (roleInfo != null) {
			params.put("roleId", roleInfo.getRoleId());
			params.put("roleName", MatchMode.ANYWHERE.toMatchString(roleInfo.getRoleName()));
			params.put("branchCode", roleInfo.getBranchNo());
			params.put("merchCode", roleInfo.getMerchantNo());
			params.put("departmentCode", roleInfo.getDeptId());
			params.put("roleType", roleInfo.getRoleType());
		}
		
		// 运营中心查看所有
		if (isCenterRoleLogined()) {
			this.showMerch = true;
			this.showBranch = true;
			this.showDept = true;
			
			deptList = this.getMyDept();
		}
		// 分支机构只允许查自己管理的所有的机构和商户
		else if (isFenzhiRoleLogined()) {
			this.showMerch = true;
			this.showBranch = true;
			
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		// 发卡机构查看自己和自己的部门的
		else if (isCardRoleLogined()) {
			this.showDept = true;
			this.showBranch = true;
			
			branchList = this.getMyCardBranch();
			deptList = this.getMyDept();
			
			params.put("cardBranchList", branchList);
//			if (CollectionUtils.isNotEmpty(branchList)) {
//				params.put("branchCode", branchList.get(0).getBranchCode());
//			}
		}
		// 运营中心部门和发卡机构部门只能看部门的
		else if (RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
			this.showDept = true;
			deptList = this.getMyDept();
			
			if (CollectionUtils.isNotEmpty(deptList)) {
				params.put("departmentCode", deptList.get(0).getDeptId());
			}
		} 
		// 商户只能查看商户自己的
		else if (RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())) {
			this.showMerch = true;
			merchList = this.getMyMerch();
			
			if (CollectionUtils.isNotEmpty(merchList)) {
				params.put("merchCode", merchList.get(0).getMerchId());
			}
		}
		// 其他机构只能查看自己机构的
		else {
			this.showBranch = true;
			branchList = this.getMyBranch();

			if (CollectionUtils.isNotEmpty(branchList)) {
				params.put("branchCode", branchList.get(0).getBranchCode());
			}
		}
		
//		if ( !(getLoginRoleType().equals(RoleType.CENTER.getValue()) 
//				|| getLoginRoleType().equals(RoleType.FENZHI.getValue()))) {
//			if (showBranch) {
//				if (CollectionUtils.isNotEmpty(branchList)) {
//					params.put("branchCode", branchList.get(0).getBranchCode());
//				}
//			} else if (showDept) {
//				if (CollectionUtils.isNotEmpty(deptList)) {
//					params.put("departmentCode", deptList.get(0).getDeptId());
//				}
//			} else if (showMerch){
//				if (CollectionUtils.isNotEmpty(merchList)) {
//					params.put("merchCode", merchList.get(0).getMerchId());
//				}
//			}
//		}
//
//		// 分支机构只允许查自己所有的机构
//		if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
//			params.put("branchs", branchList);
//			params.put("merchs", merchList);
//			if (CollectionUtils.isNotEmpty(branchList) || CollectionUtils.isNotEmpty(merchList)) {
//				params.put("union", true);
//			}
//			if (CollectionUtils.isNotEmpty(branchList) && CollectionUtils.isNotEmpty(merchList)) {
//				params.put("union_or", true);
//			}
//		}
		
		this.page = this.roleInfoDAO.findRole(params, this.getPageNumber(), this.getPageSize());
		logger.debug("用户[" + this.getSessionUserCode() + "][" + NameTag.getUserName(this.getSessionUserCode()) + "]查看角色列表成功");
		return LIST;
	}

	private void initPage() {
//		this.branchList = this.getMyBranch();
//		this.merchList = this.getMyMerch();
//		this.deptList = this.getMyDept();
		this.branchList = new ArrayList<BranchInfo>();
		this.merchList = new ArrayList<MerchInfo>();
		this.deptList = new ArrayList<DepartmentInfo>();
		
		
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())){
			this.showMerch = true;
			this.showBranch = true;
			this.showDept = true;
			this.roleTypeList = RoleType.getCenter();
			
		} else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			this.showMerch = true;
			this.showBranch = true;
			this.roleTypeList = RoleType.getManage();
			
		} else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
			this.showDept = true;
			this.showBranch = true;
			this.roleTypeList = RoleType.getForCard();
			
			this.deptList = this.getMyDept();
			this.branchList = this.getMyCardBranch();
		}
		else if (RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
			this.showDept = true;
			this.deptList = this.getMyDept();
		} else if (RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())) {
			this.showMerch = true;
			this.merchList = this.getMyMerch();
		} else {
			this.showBranch = true;
			this.branchList = this.getMyBranch();
		}
		if (StringUtils.isEmpty(this.getSessionUser().getBranchNo())){
			return;
		}
		
		// 读取当前登录用户拥有几个机构
		List<BranchHasType> list = this.branchHasTypeDAO.findByBranch(this.getSessionUser().getBranchNo());
		if (CollectionUtils.isEmpty(list) || list.size() <= 1) {
			return;
		}
		
		// 拥有多个机构类型时，创建角色时允许选择角色类型
		Map<String, Object> map = new HashMap<String, Object>();
		if (CollectionUtils.isEmpty(this.roleTypeList)) {
			this.roleTypeList = new ArrayList();
		} else {
			for (int i = 0; i < roleTypeList.size(); i++) {
				RoleType roleType = (RoleType) roleTypeList.get(i);
				map.put(roleType.getValue(), roleType);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			BranchHasType key = list.get(i);
			if (map.get(key.getTypeCode()) == null){
				roleTypeList.add(RoleType.valueOf(key.getTypeCode()));
			}
		}
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.roleInfo = (RoleInfo) this.roleInfoDAO.findByPk(this.roleInfo.getRoleId());
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		initPage();
		return ADD;
	}
	
	// 打开新增页面的初始化操作
	public String showAddCommon() throws Exception {
		this.checkEffectiveCertUser();
		
		this.roleTypeList = RoleType.getAll();
		return ADD;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.roleInfo = (RoleInfo) this.roleInfoDAO.findByPk(this.roleInfo.getRoleId());
		
		// 判断是否修改通用角色
//		if (StringUtils.isEmpty(roleInfo.getBranchNo()) &&
//				StringUtils.isEmpty(roleInfo.getMerchantNo()) &&
//				StringUtils.isEmpty(roleInfo.getDeptId())) {
//			this.roleTypeList = RoleType.getAll();
//			return "modifyCommon";
//		}

		if (roleInfo.getBranchNo() != null && StringUtils.isNotEmpty(roleInfo.getBranchNo())) {
			this.branchList = new ArrayList<BranchInfo>();
			this.branchList.add((BranchInfo) this.branchInfoDAO.findByPk(roleInfo.getBranchNo()));
		}
		if (roleInfo.getMerchantNo() != null && StringUtils.isNotEmpty(roleInfo.getMerchantNo())) {
			this.merchList = new ArrayList<MerchInfo>();
			this.merchList.add((MerchInfo) this.merchInfoDAO.findByPk(roleInfo.getMerchantNo()));
		}
		if (roleInfo.getDeptId() != null && StringUtils.isNotEmpty(roleInfo.getDeptId())) {
			this.deptList = new ArrayList<DepartmentInfo>();
			this.deptList.add((DepartmentInfo) this.departmentInfoDAO.findByPk(roleInfo.getDeptId()));
		}
		
		if (RoleType.MERCHANT.getValue().equals(this.roleInfo.getRoleType())) {
			showMerch = true;
		} else if (RoleType.PERSONAL.getValue().equals(this.roleInfo.getRoleType())){
			
		} else if (RoleType.CENTER_DEPT.getValue().equals(this.roleInfo.getRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.roleInfo.getRoleType())){
			showDept = true;
			
		} else {
			showBranch = true;
		}
		
		return MODIFY;
	}
	
	// 新增角色
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		this.roleService.addRole(this.roleInfo, this.privileges.split(","), this.getSessionUser(), false);
		
		String msg = LogUtils.r("添加角色[{0}]成功", this.roleInfo.getRoleId());
		this.addActionMessage("/pages/role/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 新增角色
	public String addCommon() throws Exception {
		this.checkEffectiveCertUser();
		
		this.roleService.addRole(this.roleInfo, this.privileges.split(","), this.getSessionUser(), true);
		
		String msg = LogUtils.r("添加角色[{0}]成功", this.roleInfo.getRoleId());
		this.addActionMessage("/pages/role/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 修改对象操作
	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.roleService.modifyRole(roleInfo, this.privileges.split(","), this.getSessionUser(), false);
		
		String msg = LogUtils.r("修改角色[{0}]成功", this.roleInfo.getRoleId());
		this.addActionMessage("/pages/role/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	// 修改对象操作
	public String modifyCommon() throws Exception {
		this.checkEffectiveCertUser();
		
		this.roleService.modifyRole(roleInfo, this.privileges.split(","), this.getSessionUser(), true);
		
		String msg = LogUtils.r("修改角色[{0}]成功", this.roleInfo.getRoleId());
		this.addActionMessage("/pages/role/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	// 删除对象操作
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		this.roleService.deleteRole(this.roleInfo.getRoleId());
		
		String msg = LogUtils.r("删除角色[{0}]成功", this.roleInfo.getRoleId());
		this.addActionMessage("/pages/role/list.do", msg);
		this.log(msg, UserLogType.DELETE);
		return SUCCESS;
	}
	
	/**
	 * 增加页面树形初始化.
	 * 
	 * @return
	 * @throws Exception
	 */
	public void initTreeByAdd() throws Exception {
		this.respond(PrivilegeTreeTool.buildTree(Constants.ROOT_PRIVILEGE_CODE, 
				this.roleService.findManagePrivilge(this.getSessionUser()), null));
	}
	
	/**
	 * 修改页面树形初始化.
	 * 
	 * @return
	 * @throws Exception
	 */
	public void initTreeByUpdate() throws Exception {
		String roleId = this.roleInfo.getRoleId();
		
		RoleInfo roleInfo = (RoleInfo) this.roleInfoDAO.findByPk(roleId);
		String roleType = roleInfo.getRoleType();
		
		// 查找该角色拥有的权限
		Map limitMap = new HashMap();
		List lstLimit = this.privilegeDAO.findByRoleId(roleId);
		for (Iterator it = lstLimit.iterator(); it.hasNext();) {
			Privilege info = (Privilege) it.next();
			limitMap.put(info.getLimitId(), Boolean.TRUE);
		}
		
		List workflowPrivileges = this.workflowPrivilegeDAO.findByRole(roleId);
		for (Iterator it = workflowPrivileges.iterator(); it.hasNext();) {
			WorkflowPrivilegeKey key = (WorkflowPrivilegeKey) it.next();
			limitMap.put("###WWW" + "_" + key.getWorkflowId() + "_" + key.getNodeId() 
					+ "_" + key.getRefId() + "_" + key.getIsBranch(), Boolean.TRUE);
		}
		
		this.respond(PrivilegeTreeTool.buildTree(Constants.ROOT_PRIVILEGE_CODE, 
				this.roleService.findPrivilgeByType(roleType), limitMap));
	}
	
	/**
	 * 明细页面树形初始化.
	 * 
	 * @return
	 * @throws Exception
	 */
	public void initTreeByDetail() throws Exception {
		String roleId = this.roleInfo.getRoleId();
		
		List lstLimit = this.privilegeDAO.findByRoleId(roleId);
		
		this.respond(PrivilegeTreeTool.buildDetailTree(Constants.ROOT_PRIVILEGE_CODE, lstLimit));
	}
	
	/**
	 * 增加页面树形初始化.
	 * 
	 * @return
	 * @throws Exception
	 */
	public void initTreeByAddSaleProxy() throws Exception {
		this.respond(PrivilegeTreeTool.buildTree(Constants.ROOT_PRIVILEGE_CODE, 
				this.roleService.findPrivilgeByType(RoleType.CARD_SELL.getValue()), null));
	}
	
	/**
	 * 修改页面树形初始化.
	 * 
	 * @return
	 * @throws Exception
	 */
	public void initTreeByUpdateSaleProxy() throws Exception {
		// 查找该角色拥有的权限
		Map limitMap = new HashMap();
		List lstLimit = this.privilegeDAO.findByProxyAndCard(proxyCode, branchCode);
		for (Iterator it = lstLimit.iterator(); it.hasNext();) {
			Privilege info = (Privilege) it.next();
			limitMap.put(info.getLimitId(), Boolean.TRUE);
		}
		
		this.respond(PrivilegeTreeTool.buildTree(Constants.ROOT_PRIVILEGE_CODE, 
				this.roleService.findPrivilgeByType(RoleType.CARD_SELL.getValue()), limitMap));
	}
	
	/**
	 * 增加页面树形初始化.
	 * 
	 * @return
	 * @throws Exception
	 */
	public void initTreeByAddDept() throws Exception {
		String roleType = null;
		if (this.getLoginRoleType().equals(RoleType.CENTER.getValue())) {
			roleType = RoleType.CENTER_DEPT.getValue();
		} else if (this.getLoginRoleType().equals(RoleType.CARD.getValue())){
			roleType = RoleType.CARD_DEPT.getValue();
		}
		this.respond(PrivilegeTreeTool.buildTree(Constants.ROOT_PRIVILEGE_CODE, 
				this.privilegeDAO.findByRoleType(roleType), null));
	}
	
	/**
	 * 修改页面树形初始化.
	 * 
	 */
	public void initTreeByUpdateDept() throws Exception {
		String roleType = null;
		if (this.getLoginRoleType().equals(RoleType.CENTER.getValue())) {
			roleType = RoleType.CENTER_DEPT.getValue();
		} else if (this.getLoginRoleType().equals(RoleType.CARD.getValue())){
			roleType = RoleType.CARD_DEPT.getValue();
		}
		
		// 查找该角色拥有的权限
		Map limitMap = new HashMap();
		List lstLimit = this.branchPrivilegeDAO.findByDept(deptId);
		
		for (Iterator it = lstLimit.iterator(); it.hasNext();) {
			BranchPrivilege info = (BranchPrivilege) it.next();
			limitMap.put(info.getLimitId(), Boolean.TRUE);
		}
		
		this.respond(PrivilegeTreeTool.buildTree(Constants.ROOT_PRIVILEGE_CODE, 
				this.privilegeDAO.findByRoleType(roleType), limitMap));
	}
	
	/**
	 * 部门明细页面树形初始化.
	 * 
	 */
	public void initTreeByDeptDetail() throws Exception {
		// 查找该角色拥有的权限
		List lstLimit = this.privilegeDAO.findByDept(branchCode, deptId);
		this.respond(PrivilegeTreeTool.buildTree(Constants.ROOT_PRIVILEGE_CODE, lstLimit, null));
	}
	
	/**
	 * 增加页面树形初始化.
	 * 
	 * @return
	 * @throws Exception
	 */
	public void initTreeByRoleType() throws Exception {
		String roleType = request.getParameter("roleType");
		
		this.respond(PrivilegeTreeTool.buildTree(Constants.ROOT_PRIVILEGE_CODE, 
				this.roleService.findPrivilgeByType(roleType), null));
	}
	
	public String getRoleTypeOption() throws Exception {
		this.roleInfoList = this.roleInfoDAO.findCommonByRoleType(this.roleInfo.getRoleType());
		return "roleOption";
	}
	
	public void loadMerch() throws Exception {
		this.merchList = this.getMyMerch();
		StringBuffer sb = new StringBuffer(128);
		sb.append("<option value=\"\">--请选择--</option>");
		for (MerchInfo merchInfo : merchList) {
			sb.append("<option value=\"").append(merchInfo.getMerchId()).append("\">").append(merchInfo.getMerchName()).append("</option>");
		}
		this.respond(sb.toString());
	}
	
	public void loadDept() throws Exception {
		this.deptList = this.getMyDept();
		StringBuffer sb = new StringBuffer(128);
		sb.append("<option value=\"\">--请选择--</option>");
		for (DepartmentInfo dept : deptList) {
			sb.append("<option value=\"").append(dept.getDeptId()).append("\">").append(dept.getDeptName()).append("</option>");
		}
		this.respond(sb.toString());
	}
	
	public void loadBranch() throws Exception {
		String roleType = request.getParameter("roleType");
		this.branchList = new ArrayList<BranchInfo>();
		String branchNo = this.getSessionUser().getRole().getBranchNo();
		
		if (this.getLoginRoleType().equals(RoleType.FENZHI.getValue())) {
			if (roleType.equals(RoleType.FENZHI.getValue())) {
				branchList.add((BranchInfo) this.branchInfoDAO.findByPk(branchNo));
			} else {
				// 如果当前登录的分支机构也拥有该角色，则需要添加自己
				List<BranchHasType> hasTypes = this.branchHasTypeDAO.findByBranch(branchNo);
				for (BranchHasType key : hasTypes){
					if (key.getTypeCode().equals(roleType)) {
						branchList.add((BranchInfo) this.branchInfoDAO.findByPk(branchNo));
					}
				}
				branchList.addAll(this.branchInfoDAO.findByTypeAndManage(roleType, branchNo));
			}
		} else if (this.getLoginRoleType().equals(RoleType.CENTER.getValue())) {
			this.branchList = this.branchInfoDAO.findByType(roleType);
		} else {
			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(branchNo));
		}
		
		StringBuffer sb = new StringBuffer(128);
		sb.append("<option value=\"\">--请选择--</option>");
		for (BranchInfo branchInfo : branchList){
			sb.append("<option value=\"").append(branchInfo.getBranchCode()).append("\">").append(branchInfo.getBranchName()).append("</option>");
		}
		this.respond(sb.toString());
	}
	
	public RoleInfo getRoleInfo() {
		return roleInfo;
	}
	
	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getPrivileges() {
		return privileges;
	}

	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public List<MerchInfo> getMerchList() {
		return merchList;
	}

	public void setMerchList(List<MerchInfo> merchList) {
		this.merchList = merchList;
	}

	public List<DepartmentInfo> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<DepartmentInfo> deptList) {
		this.deptList = deptList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List getRoleTypeList() {
		return roleTypeList;
	}

	public void setRoleTypeList(List roleTypeList) {
		this.roleTypeList = roleTypeList;
	}

	public List<RoleInfo> getRoleInfoList() {
		return roleInfoList;
	}

	public void setRoleInfoList(List<RoleInfo> roleInfoList) {
		this.roleInfoList = roleInfoList;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getProxyCode() {
		return proxyCode;
	}

	public void setProxyCode(String proxyCode) {
		this.proxyCode = proxyCode;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
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
