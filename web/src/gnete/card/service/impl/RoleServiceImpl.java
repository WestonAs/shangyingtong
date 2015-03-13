package gnete.card.service.impl;

import flink.util.Cn2PinYinHelper;
import flink.util.StringUtil;
import gnete.card.dao.BranchHasTypeDAO;
import gnete.card.dao.CardPrivilegeGroupDAO;
import gnete.card.dao.CardPrivilegeGroupLimitDAO;
import gnete.card.dao.DepartmentInfoDAO;
import gnete.card.dao.PrivilegeDAO;
import gnete.card.dao.RoleInfoDAO;
import gnete.card.dao.RolePrivilegeDAO;
import gnete.card.dao.RoleTypePrivilegeDAO;
import gnete.card.dao.UserRoleDAO;
import gnete.card.entity.BranchHasType;
import gnete.card.entity.CardPrivilegeGroup;
import gnete.card.entity.CardPrivilegeGroupLimit;
import gnete.card.entity.DepartmentInfo;
import gnete.card.entity.Privilege;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.RolePrivilegeKey;
import gnete.card.entity.RoleTypePrivilege;
import gnete.card.entity.UserInfo;
import gnete.card.entity.UserRoleKey;
import gnete.card.entity.type.RoleType;
import gnete.card.service.RoleService;
import gnete.card.workflow.dao.WorkflowNodeDAO;
import gnete.card.workflow.dao.WorkflowPrivilegeDAO;
import gnete.card.workflow.entity.WorkflowNode;
import gnete.card.workflow.entity.WorkflowPrivilegeKey;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.Symbol;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private UserRoleDAO userRoleDAO;

	@Autowired
	private RoleTypePrivilegeDAO roleTypePrivilegeDAO;

	@Autowired
	private RolePrivilegeDAO rolePrivilegeDAO;
	
	@Autowired
	private RoleInfoDAO roleInfoDAO;

	@Autowired
	private PrivilegeDAO privilegeDAO;
	
	@Autowired
	private BranchHasTypeDAO branchHasTypeDAO;
	
	@Autowired
	private WorkflowPrivilegeDAO workflowPrivilegeDAO;
	
	@Autowired
	private WorkflowNodeDAO workflowNodeDAO;
	@Autowired
	private DepartmentInfoDAO departmentInfoDAO;

	@Autowired
	private CardPrivilegeGroupDAO cardPrivilegeGroupDAO;
	@Autowired
	private CardPrivilegeGroupLimitDAO cardPrivilegeGroupLimitDAO;
	
	public void addRole(RoleInfo roleInfo, String[] privileges,
			UserInfo user, boolean isCommon) throws BizException {
		Assert.notNull(user, "操作用户不能为空");
		Assert.notNull(roleInfo, "传入的角色信息不能为空");
		Assert.notNull(privileges, "角色拥有权限不能为空");
		Assert.notEmpty(roleInfo.getRoleName(), "角色名称不能为空");
		
		Assert.isEmpty(this.roleInfoDAO.findByRoleName(roleInfo.getRoleName()), "角色名["+ roleInfo.getRoleName() +"]已经存在");
		
		// 判断角色id是否已经存在
		String roleId = StringUtils.upperCase(Cn2PinYinHelper.cn2FirstSpell(roleInfo.getRoleName()));
		do {
			roleId += StringUtil.getNoString(4);
		} while (this.roleInfoDAO.isExist(roleId));
		roleInfo.setRoleId(roleId);
		
		RoleInfo old = (RoleInfo) this.roleInfoDAO.findByPk(roleInfo.getRoleId());
		Assert.isNull(old, "该角色已经存在["+roleInfo.getRoleId()+"]");
		
		// 非通用角色让系统自动判断属于什么角色类型
		if (StringUtils.isEmpty(roleInfo.getRoleType())) {
			if (!isCommon) {
				roleInfo.setRoleType(this.assignRoleType(roleInfo, user).getValue());
			}
		}
		
		// 当添加部门用户时，给用户添加机构编号
		if (StringUtils.isNotEmpty(roleInfo.getDeptId())) {
			DepartmentInfo departmentInfo = (DepartmentInfo) this.departmentInfoDAO.findByPk(roleInfo.getDeptId());
			roleInfo.setBranchNo(departmentInfo.getBranchNo());
		}
		
		
		roleInfo.setUpdateUser(user.getUserId());
		roleInfo.setUpdateTime(new Date());
		this.roleInfoDAO.insert(roleInfo);

		this.addRootPrivilge(roleInfo.getRoleId());
		for (String privilege : privileges) {
			if (privilege.startsWith(Constants.START_PRIVILEGE)) {
				WorkflowPrivilegeKey key = new WorkflowPrivilegeKey();
				String[] array = privilege.split("_");
				key.setRoleId(roleInfo.getRoleId());
				key.setWorkflowId(array[1]);
				key.setNodeId(Integer.parseInt(array[2]));
				key.setRefId(array[3]);
				key.setIsBranch(array[4]);
				
				this.workflowPrivilegeDAO.insert(key);
			} else {
				RolePrivilegeKey key = new RolePrivilegeKey();
				key.setRoleId(roleInfo.getRoleId());
				key.setLimitId(privilege);
				
				this.rolePrivilegeDAO.insert(key);
			}
		}
	}
	
	/**
	 * 添加root权限点
	 * @param roleId
	 */
	private void addRootPrivilge(String roleId){
		RolePrivilegeKey key = new RolePrivilegeKey();
		key.setRoleId(roleId);
		key.setLimitId(Constants.ROOT_PRIVILEGE_CODE);
		this.rolePrivilegeDAO.insert(key);
	}

	/**
	 * 自动分配角色类型
	 * @param roleInfo
	 * @param user
	 * @return
	 */
	private RoleType assignRoleType(RoleInfo roleInfo, UserInfo user) {
		String branchCode = roleInfo.getBranchNo();
		String merchCode = roleInfo.getMerchantNo();
		String deptCode = roleInfo.getDeptId();
		
		// 取得当前登录用户的角色类型
		String loginUserRoleType = user.getRole().getRoleType();
		
		// 取得当前选择机构的机构类型
		String branchTypes = "";
		if (StringUtils.isNotEmpty(branchCode)) {
			List<BranchHasType> branchTypeList = this.branchHasTypeDAO.findByBranch(branchCode);
			for (BranchHasType key : branchTypeList) {
				branchTypes += key.getTypeCode() + ",";
			}
		}
		
		// 当部门ID不为空时，只可能是运营中心角色或发卡机构角色
		if (StringUtils.isNotEmpty(deptCode)) {
			// 如果当前操作员是发卡机构操作员，则定义成 发卡机构网点角色
			if (user.getRole().getRoleType().equals(RoleType.CARD.getValue())) {
				return RoleType.CARD_DEPT;
			} 
			// 如果当前操作员是运营中心的操作员，则需要判断该部门属于运营中心还是发卡机构
			else if (loginUserRoleType.equals(RoleType.CENTER.getValue())) {
				if (branchTypes.indexOf(RoleType.CENTER.getValue()) != -1) {
					return RoleType.CENTER_DEPT;
				} else {
					return RoleType.CARD_DEPT;
				}
			}
			// 如果是运营中心部门则直接返回
			else if (loginUserRoleType.equals(RoleType.CENTER_DEPT.getValue())){
				return RoleType.CENTER_DEPT;
			}
			// 如果是发卡机构网点则直接返回
			else if (loginUserRoleType.equals(RoleType.CARD_DEPT.getValue())){
				return RoleType.CARD_DEPT;
			}
		}
		// 当商户ID不为空时，该角色一定属于商户角色
		else if (StringUtils.isNotEmpty(merchCode)) {
			return RoleType.MERCHANT;
		}
		
		// 当机构ID不为空时，需要根据选择机构拥有的类型来判断
		else if (StringUtils.isNotEmpty(branchCode)) {
			if (branchTypes.indexOf(RoleType.CENTER.getValue()) != -1) {
				return RoleType.CENTER;
			} else if (branchTypes.indexOf(RoleType.FENZHI.getValue()) != -1){
				return RoleType.FENZHI;
			} else if (branchTypes.indexOf(RoleType.AGENT.getValue()) != -1){
				return RoleType.AGENT;
			} else if (branchTypes.indexOf(RoleType.CARD.getValue()) != -1){
				return RoleType.CARD;
			} else if (branchTypes.indexOf(RoleType.CARD_MAKE.getValue()) != -1){
				return RoleType.CARD_MAKE;
			} else if (branchTypes.indexOf(RoleType.CARD_MERCHANT.getValue()) != -1){
				return RoleType.CARD_MERCHANT;
			} else if (branchTypes.indexOf(RoleType.CARD_SELL.getValue()) != -1){
				return RoleType.CARD_SELL;
			} else if (branchTypes.indexOf(RoleType.TERMINAL.getValue()) != -1){
				return RoleType.TERMINAL;
			} else if (branchTypes.indexOf(RoleType.TERMINAL_MAINTAIN.getValue()) != -1){
				return RoleType.TERMINAL_MAINTAIN;
			}
			return null;
		}
		// 剩下的都是个人用户类型了
		return RoleType.PERSONAL;
	}
	
	public void modifyRole(RoleInfo roleInfo, String[] privileges,
			UserInfo user, boolean isCommon) throws BizException {
		Assert.notNull(user, "操作用户不能为空");
		Assert.notNull(roleInfo, "传入的角色信息不能为空");
		Assert.notNull(privileges, "角色拥有权限不能为空");
		
		RoleInfo role = (RoleInfo) roleInfoDAO.findByPk(roleInfo.getRoleId());
		Assert.notNull(role, "找不到该角色["+ roleInfo.getRoleId() +"]");

		// 修改时禁止修改机构号码、商户号码和部门号码
		roleInfo.setRoleType(role.getRoleType());
		roleInfo.setBranchNo(role.getBranchNo());
		roleInfo.setMerchantNo(role.getMerchantNo());
		roleInfo.setDeptId(role.getDeptId());
		
		roleInfo.setUpdateUser(user.getUserId());
		roleInfo.setUpdateTime(new Date());
		this.roleInfoDAO.update(roleInfo);
		
		// 首先删除该角色拥有的权限数据，再添加新的权限数据
		String roleId = roleInfo.getRoleId();
		this.rolePrivilegeDAO.deleteByRoleId(roleId);
		this.workflowPrivilegeDAO.deleteByRoleId(roleId);
		
		this.addRootPrivilge(roleInfo.getRoleId());
		for (String privilege : privileges) {
			if (privilege.startsWith(Constants.START_PRIVILEGE)) {
				WorkflowPrivilegeKey key = new WorkflowPrivilegeKey();
				String[] array = privilege.split("_");
				key.setRoleId(roleInfo.getRoleId());
				key.setWorkflowId(array[1]);
				key.setNodeId(Integer.parseInt(array[2]));
				key.setRefId(array[3]);
				key.setIsBranch(array[4]);
				
				this.workflowPrivilegeDAO.insert(key);
			} else {
				RolePrivilegeKey key = new RolePrivilegeKey();
				key.setRoleId(roleInfo.getRoleId());
				key.setLimitId(privilege);
				
				this.rolePrivilegeDAO.insert(key);
			}
		}
	}
	
	public void deleteRole(String roleId) throws BizException {
		Assert.notEmpty(roleId, "删除时需要指定角色编号");
		
		// 如果该角色已经分配给用户，则禁止删除
		List<UserRoleKey> list = this.userRoleDAO.findByRoleId(roleId);
		Assert.notTrue(CollectionUtils.isNotEmpty(list), "该角色[" + roleId + "]已经分配给用户，不能删除！");
		
		// 删除角色信息、权限信息
		this.roleInfoDAO.delete(roleId);
		this.rolePrivilegeDAO.deleteByRoleId(roleId);
		this.workflowPrivilegeDAO.deleteByRoleId(roleId);
	}

	public String addDefaultAdmin(String refCode, String name, boolean isBranch,
			String roleType, String userId) throws BizException {
		Assert.notNull(refCode, "传入的机构或商户编号不能为空");
		Assert.notNull(roleType, "传入的角色类型不能为空");
		
		// 创建一个新的角色
		String roleId = refCode + "admin";
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setRoleId(roleId);
		if (isBranch) {
			roleInfo.setBranchNo(refCode);
		} else {
			roleInfo.setMerchantNo(refCode);
		}
		roleInfo.setRoleType(roleType);
		roleInfo.setRoleName(name + "管理员");
		roleInfo.setUpdateTime(new Date());
		roleInfo.setUpdateUser(userId);
		Assert.notTrue(this.roleInfoDAO.isExist(roleId), "已经存在该管理员[" + roleId + "]");
		
		this.roleInfoDAO.insert(roleInfo);
		
		List<RoleTypePrivilege> list = this.roleTypePrivilegeDAO.findByType(roleType);
		
		for (RoleTypePrivilege roleTypePrivilege : list) {
			String limitId = roleTypePrivilege.getLimitId();
			if (limitId.startsWith(Constants.START_PRIVILEGE)) {
				WorkflowPrivilegeKey key = new WorkflowPrivilegeKey();
				String[] array = limitId.split("_");
				key.setRoleId(roleInfo.getRoleId());
				key.setWorkflowId(array[1]);
				key.setNodeId(Integer.parseInt(array[2]));
				key.setRefId(array[3]);
				key.setIsBranch(array[4]);
				
				this.workflowPrivilegeDAO.insert(key);
			} else {
				RolePrivilegeKey key = new RolePrivilegeKey(limitId, roleId);
				this.rolePrivilegeDAO.insert(key);
			}
		}
		return roleId;
	}

	public void bindUserRole(String userId, String roleId) throws BizException {
		UserRoleKey userRoleKey = new UserRoleKey(roleId, userId);
		Assert.notTrue(this.userRoleDAO.isExist(userRoleKey), "该用户["+userId+"]已经拥有该角色["+roleId+"]了");
		
		this.userRoleDAO.insert(userRoleKey);
	}
	
	public List<Privilege> findManagePrivilge(UserInfo user) {
		List<Privilege> result = null;
		String roleType = user.getRole().getRoleType();
		
		// 如果是售卡代理则读取 sale_proxy_privilege表的权限，即根据售卡代理机构ID和发卡机构ID来查找
		if (RoleType.CARD_SELL.getValue().equals(roleType)) {
//			result = this.privilegeDAO.findByProxyAndCard(user.getRole().getBranchNo(), user.getRole().getProxyCard());
			result = this.privilegeDAO.findByRoleType(roleType);
		}
		// 如果是部门(网点)用户则读取该部门或网点拥有的权限
		else if (RoleType.CENTER_DEPT.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)){
			result = privilegeDAO.findByDept(user.getRole().getBranchNo(), user.getRole().getDeptId());
		}
		// 如果是其他类型，则读取该类型对应的权限
		else {
			result = this.privilegeDAO.findByRoleType(roleType);
		}
		
		List workflowPrivilegs = workflowNodeDAO.findAll();
		for (Iterator i = workflowPrivilegs.iterator(); i.hasNext();) {
			WorkflowNode key = (WorkflowNode) i.next();
			
			Privilege privilege = new Privilege();
			privilege.setCode(Constants.START_PRIVILEGE + "_" + key.getWorkflowId() + "_" + key.getNodeId() 
					+ "_" + key.getRefId() + "_" + key.getIsBranch());
			privilege.setLimitName(key.getNodeName());
			privilege.setIsWorkflow(Symbol.YES);
			privilege.setParent(key.getAuditLimit());
			result.add(privilege);
		}
		return result;
	}
	
	public List<Privilege> findPrivilgeByType(String roleType) {
		List<Privilege> result = null;
		result = this.privilegeDAO.findByRoleType(roleType);
		
		List workflowPrivilegs = workflowNodeDAO.findAll();
		for (Iterator i = workflowPrivilegs.iterator(); i.hasNext();) {
			WorkflowNode key = (WorkflowNode) i.next();
			
			Privilege privilege = new Privilege();
			privilege.setCode(Constants.START_PRIVILEGE + "_" + key.getWorkflowId() + "_" + key.getNodeId() 
					+ "_" + key.getRefId() + "_" + key.getIsBranch());
			privilege.setLimitName(key.getNodeName());
			privilege.setIsWorkflow(Symbol.YES);
			privilege.setParent(key.getAuditLimit());
			result.add(privilege);
		}
		return result;
	}
	
	public void setRoleTypePrivilege(String roleType, String[] privileges) throws BizException {
		Assert.notEmpty(roleType, "角色类型不能为空");
		Assert.notNull(privileges, "角色拥有的权限不能为空");
		
		this.roleTypePrivilegeDAO.deleteByRoleType(roleType);
		
		this.addRootTypePrivilge(roleType);
		for (String privilege : privileges) {
			RoleTypePrivilege key = new RoleTypePrivilege();
			key.setLimitId(privilege);
			key.setRoleType(roleType);
			
			this.roleTypePrivilegeDAO.insert(key);
		}
	}

	private void addRootTypePrivilge(String roleType) {
		RoleTypePrivilege key = new RoleTypePrivilege();
		key.setLimitId(Constants.ROOT_PRIVILEGE_CODE);
		key.setRoleType(roleType);
		this.roleTypePrivilegeDAO.insert(key);
	}
	
	public void addCardPrivilegeGroup(CardPrivilegeGroup cardPrivilegeGroup,
			String[] array) throws BizException {
		Assert.notNull(cardPrivilegeGroup, "对象不能为空");
		Assert.notNull(array, "权限不能为空");
		
		this.cardPrivilegeGroupDAO.insert(cardPrivilegeGroup);
		for (String limit : array) {
			CardPrivilegeGroupLimit cardPrivilegeGroupLimit = new CardPrivilegeGroupLimit();
			cardPrivilegeGroupLimit.setId(cardPrivilegeGroup.getId());
			cardPrivilegeGroupLimit.setLimitId(limit);
			this.cardPrivilegeGroupLimitDAO.insert(cardPrivilegeGroupLimit);
		}
	}
	
	public void modifyCardPrivilegeGroup(CardPrivilegeGroup cardPrivilegeGroup,
			String[] array) throws BizException {
		Assert.notNull(cardPrivilegeGroup, "对象不能为空");
		Assert.notNull(array, "权限不能为空");
		
		this.cardPrivilegeGroupLimitDAO.deleteByGroupId(cardPrivilegeGroup.getId());
		
		this.cardPrivilegeGroupDAO.update(cardPrivilegeGroup);
		for (String limit : array) {
			CardPrivilegeGroupLimit cardPrivilegeGroupLimit = new CardPrivilegeGroupLimit();
			cardPrivilegeGroupLimit.setId(cardPrivilegeGroup.getId());
			cardPrivilegeGroupLimit.setLimitId(limit);
			this.cardPrivilegeGroupLimitDAO.insert(cardPrivilegeGroupLimit);
		}
	}
	
	public void deleteCardPrivilegeGroup(Long id) throws BizException {
		Assert.notNull(id, "权限不能为空");
		this.cardPrivilegeGroupDAO.delete(id);
		this.cardPrivilegeGroupLimitDAO.deleteByGroupId(id);
	}

}
