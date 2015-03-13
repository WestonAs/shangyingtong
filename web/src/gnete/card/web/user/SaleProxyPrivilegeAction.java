package gnete.card.web.user;

import flink.etc.MatchMode;
import flink.tree.PrivilegeTreeTool;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardPrivilegeGroupDAO;
import gnete.card.dao.CardPrivilegeGroupLimitDAO;
import gnete.card.entity.CardPrivilegeGroup;
import gnete.card.entity.CardPrivilegeGroupLimit;
import gnete.card.entity.Privilege;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.RoleService;
import gnete.card.web.BaseAction;
import gnete.etc.Constants;

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
public class SaleProxyPrivilegeAction extends BaseAction {

	@Autowired
	private RoleService roleService;
	@Autowired
	private CardPrivilegeGroupDAO cardPrivilegeGroupDAO;
	@Autowired
	private CardPrivilegeGroupLimitDAO cardPrivilegeGroupLimitDAO;
	
	private String privileges;
	
	private CardPrivilegeGroup cardPrivilegeGroup;
	
	private List<RoleType> roleTypeList;
	
	private Paginater page;

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (cardPrivilegeGroup != null) {
			params.put("name", MatchMode.ANYWHERE.toMatchString(cardPrivilegeGroup.getName()));
		}
		page = this.cardPrivilegeGroupDAO.find(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		return ADD;
	}
	
	// 新增对象操作
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		// 调用service业务接口
		this.roleService.addCardPrivilegeGroup(cardPrivilegeGroup, privileges.split(","));
		
		String msg = LogUtils.r("添加发卡机构可分配售卡代理权限点[{0}]成功", cardPrivilegeGroup.getId());
		this.addActionMessage("/pages/saleProxyPrivilege/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.cardPrivilegeGroup = (CardPrivilegeGroup) this.cardPrivilegeGroupDAO.findByPk(cardPrivilegeGroup.getId());
		return MODIFY;
	}
	
	// 新增对象操作
	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		// 调用service业务接口
		this.roleService.modifyCardPrivilegeGroup(cardPrivilegeGroup, privileges.split(","));
		
		String msg = LogUtils.r("修改发卡机构可分配售卡代理权限点[{0}]成功", cardPrivilegeGroup.getId());
		this.addActionMessage("/pages/saleProxyPrivilege/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		// 调用service业务接口
		this.roleService.deleteCardPrivilegeGroup(cardPrivilegeGroup.getId());
		
		String msg = LogUtils.r("删除发卡机构可分配售卡代理权限点[{0}]成功", cardPrivilegeGroup.getId());
		this.addActionMessage("/pages/saleProxyPrivilege/list.do", msg);
		this.log(msg, UserLogType.DELETE);
		return SUCCESS;
	}
	
	public void initTree() throws Exception {
		// 查找该角色拥有的权限
		Map limitMap = new HashMap();
		List lstLimit = this.cardPrivilegeGroupLimitDAO.findLimit(cardPrivilegeGroup.getId());
		for (Iterator it = lstLimit.iterator(); it.hasNext();) {
			CardPrivilegeGroupLimit cardPrivilegeGroupLimit = (CardPrivilegeGroupLimit) it.next();
			limitMap.put(cardPrivilegeGroupLimit.getLimitId(), Boolean.TRUE);
		}
		List<Privilege> result = this.roleService.findPrivilgeByType(RoleType.CARD_SELL.getValue());
		this.respond(PrivilegeTreeTool.buildTree(Constants.ROOT_PRIVILEGE_CODE, 
				result, limitMap));
	}

	public String getPrivileges() {
		return privileges;
	}

	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}
	
	public List<RoleType> getRoleTypeList() {
		return roleTypeList;
	}

	public void setRoleTypeList(List<RoleType> roleTypeList) {
		this.roleTypeList = roleTypeList;
	}

	public CardPrivilegeGroup getCardPrivilegeGroup() {
		return cardPrivilegeGroup;
	}

	public void setCardPrivilegeGroup(CardPrivilegeGroup cardPrivilegeGroup) {
		this.cardPrivilegeGroup = cardPrivilegeGroup;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

}
