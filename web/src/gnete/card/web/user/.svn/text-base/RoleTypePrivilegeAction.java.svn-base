package gnete.card.web.user;

import flink.tree.PrivilegeTreeTool;
import flink.util.LogUtils;
import gnete.card.dao.PrivilegeDAO;
import gnete.card.dao.RoleTypePrivilegeDAO;
import gnete.card.entity.Privilege;
import gnete.card.entity.RoleTypePrivilege;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.RoleService;
import gnete.card.web.BaseAction;
import gnete.card.workflow.dao.WorkflowNodeDAO;
import gnete.card.workflow.entity.WorkflowNode;
import gnete.etc.Assert;
import gnete.etc.Constants;
import gnete.etc.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * LoginAction.java.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-7-7
 */
public class RoleTypePrivilegeAction extends BaseAction {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleTypePrivilegeDAO roleTypePrivilegeDAO;

	@Autowired
	private PrivilegeDAO privilegeDAO;

	@Autowired
	private WorkflowNodeDAO workflowNodeDAO;
	
	private String privileges;
	
	private String roleType;
	
	private List<RoleType> roleTypeList;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined(), "只有运营中心或部门才能做该操作！");
		this.roleTypeList = RoleType.getAll();
		return ADD;
	}
	
	// 新增对象操作
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		Assert.isTrue(isCenterOrCenterDeptRoleLogined(), "只有运营中心或部门才能做该操作！");
		// 调用service业务接口
		this.roleService.setRoleTypePrivilege(roleType, privileges.split(","));
		
		String msg = LogUtils.r("设置角色类型[{0}]的初始权限成功", roleType);
		this.addActionMessage("/pages/roleTypePrivilege/showAdd.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	public void initTree() throws Exception {
		List<Privilege> result = new ArrayList<Privilege>();
		// 查找该角色拥有的权限
		Map limitMap = new HashMap();
		List lstLimit = this.roleTypePrivilegeDAO.findByType(roleType);
		for (Iterator it = lstLimit.iterator(); it.hasNext();) {
			RoleTypePrivilege info = (RoleTypePrivilege) it.next();
			limitMap.put(info.getLimitId(), Boolean.TRUE);
		}
		result.addAll(this.privilegeDAO.findAll());
		
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
		this.respond(PrivilegeTreeTool.buildTree(Constants.ROOT_PRIVILEGE_CODE, 
				result, limitMap));
	}

	public String getPrivileges() {
		return privileges;
	}

	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public List<RoleType> getRoleTypeList() {
		return roleTypeList;
	}

	public void setRoleTypeList(List<RoleType> roleTypeList) {
		this.roleTypeList = roleTypeList;
	}

}
