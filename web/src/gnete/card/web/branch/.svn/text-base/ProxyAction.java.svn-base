package gnete.card.web.branch;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchHasTypeDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.BranchProxyDAO;
import gnete.card.dao.CardPrivilegeGroupDAO;
import gnete.card.dao.SaleProxyPrivilegeDAO;
import gnete.card.entity.BranchHasType;
import gnete.card.entity.BranchHasTypeKey;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BranchProxy;
import gnete.card.entity.BranchProxyKey;
import gnete.card.entity.CardPrivilegeGroup;
import gnete.card.entity.SaleProxyPrivilege;
import gnete.card.entity.state.BranchState;
import gnete.card.entity.type.BranchType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SetModeType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BranchService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: ProxyAction.java
 *
 * @description: 代理关系管理
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author：LiHeng
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-7-7
 */
public class ProxyAction extends BaseAction {

	@Autowired
	private BranchProxyDAO branchProxyDAO;
	@Autowired
	private BranchService branchService;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardPrivilegeGroupDAO cardPrivilegeGroupDAO;
	@Autowired
	private SaleProxyPrivilegeDAO saleProxyPrivilegeDAO;
	@Autowired
	private BranchHasTypeDAO branchHasTypeDAO;
	
	private Paginater page;
	
	private BranchProxy branchProxy;
	
	private Collection types;
	
	// 界面选择时是否单选
	private boolean radio;
	private BranchInfo branchInfo;
	private String cardBranch;
	private String proxyType;
	
	private String[] groups;
	
	private String hasGroup;
	
	private List<CardPrivilegeGroup> cardPrivilegeList;
	private List<BranchInfo> respOrgList;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		this.types = ProxyType.ALL.values();
		Map<String, Object> params = new HashMap<String, Object>();
		if (branchProxy != null) {
			params.put("proxyName", MatchMode.ANYWHERE.toMatchString(StringUtils.trim(branchProxy.getProxyName())));
			params.put("respName", MatchMode.ANYWHERE.toMatchString(StringUtils.trim(branchProxy.getRespName())));
			params.put("proxyType", branchProxy.getProxyType());
		}
		
		// 分不同角色查看不同的数据
		List<BranchInfo> branchs = new ArrayList<BranchInfo>();
		List<BranchInfo> proxys = new ArrayList<BranchInfo>();
		// 运营中心可以查看所有
		if (this.getLoginRoleType().equals(RoleType.CENTER.getValue()) 
				|| this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())) {
		}
		// 分支机构可以查看自己管理的机构的记录
		else if (this.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
//			branchs = this.getMyCardBranch();
			//this.types = ProxyType.getForCard();
//			branchs.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
//			if (CollectionUtils.isEmpty(branchs)) {
//				branchs.add(new BranchInfo());
//			}
			params.put("fenzhiCode", this.getLoginBranchCode());
		}
		// 发卡机构可以查看发卡机构为自己的
		else if (this.getLoginRoleType().equals(RoleType.CARD.getValue())){
			branchs.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getRole().getBranchNo()));
			this.types = ProxyType.getForCard();
			params.put("branchs", branchs);
		}
		// 代理机构可以查看代理机构为自己的
		else if (this.getLoginRoleType().equals(RoleType.CARD_MERCHANT.getValue())){
			proxys.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getRole().getBranchNo()));
			this.types = ProxyType.getForMerch();
			params.put("proxyType", ProxyType.MERCHANT.getValue());
			
		} else if (this.getLoginRoleType().equals(RoleType.CARD_SELL.getValue())){
			proxys.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getRole().getBranchNo()));
			this.types = ProxyType.getForSell();
			params.put("proxyType", ProxyType.SELL.getValue());
			
		} else if (this.getLoginRoleType().equals(RoleType.AGENT.getValue())){
			proxys.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getRole().getBranchNo()));
			this.types = ProxyType.getForAgent();
			params.put("proxyType", ProxyType.AGENT.getValue());
			
		} else {
			throw new BizException("没有权限查看商户关系!");
		}
		params.put("proxys", proxys);
		
		
		this.page = this.branchProxyDAO.find(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.branchProxy = (BranchProxy) this.branchProxyDAO.findByPk(
				new BranchProxyKey(this.branchProxy.getProxyId(), this.branchProxy.getRespOrg(), this.branchProxy.getProxyType()));
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAssign() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!(this.getLoginRoleType().equals(RoleType.CENTER.getValue()) 
				|| this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue()))) {
			throw new BizException("只有运营中心和发卡机构可以给售卡代理分配权限！");
		}
		this.cardPrivilegeList = this.cardPrivilegeGroupDAO.findAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("proxyBranch", branchProxy.getProxyId());
		params.put("cardBranch", branchProxy.getRespOrg());
		List<SaleProxyPrivilege> saleProxys = saleProxyPrivilegeDAO.findSaleProxyPrivilege(params);
		hasGroup = ",";
		for (SaleProxyPrivilege key : saleProxys) {
			hasGroup += StringUtils.trim(key.getLimitId()) + ",";
		}
		return "assign";
	}
	
	// 新增对象操作
	public String assign() throws Exception {
		this.checkEffectiveCertUser();
		
		this.branchService.assignProxy(this.branchProxy, groups, this.getSessionUserCode());

		String msg = LogUtils.r("给售卡代理[{0}, {1}]分配权限成功", this.branchProxy.getProxyId(), this.branchProxy.getRespOrg());
		this.addActionMessage("/pages/proxy/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
		} 
		// 成本模式的分支机构可以添加机构。
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			BranchHasTypeKey key = new BranchHasTypeKey();
			key.setBranchCode(this.getLoginBranchCode());
			key.setTypeCode(RoleType.FENZHI.getValue());
			BranchHasType type = (BranchHasType) this.branchHasTypeDAO.findByPk(key);
			Assert.isTrue(SetModeType.COST.getValue().equals(type.getSetMode()), "没有权限维护代理关系！");
		} else {
			throw new BizException("没有权限维护代理关系！");
		}
		this.respOrgList = new ArrayList<BranchInfo>();
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
			this.respOrgList.add((BranchInfo)branchInfoDAO.findByPk(Constants.CENTER));
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			params.put("parent", this.getLoginBranchCode());
		}
		params.put("setMode", SetModeType.COST.getValue());
		params.put("type", BranchType.FENZHI.getValue());
		this.respOrgList.addAll(this.branchInfoDAO.findAgent(params));
		
		if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			BranchInfo info = this.branchInfoDAO.findBranchInfo(this.getLoginBranchCode());
			this.respOrgList.add(info);
		}
		
		this.types = ProxyType.ALL.values();
		this.cardPrivilegeList = this.cardPrivilegeGroupDAO.findAll();
		return ADD;
	}
	
	// 新增对象操作
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		this.branchService.addProxy(this.branchProxy, groups, this.getSessionUserCode());

		String msg = LogUtils.r("添加机构代理关系[{0}, {1}]成功", this.branchProxy.getProxyId(), this.branchProxy.getRespOrg());
		this.addActionMessage("/pages/proxy/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	public String cancel() throws Exception {
		this.checkEffectiveCertUser();
		
		String proxyId = this.request.getParameter("proxyId");
		String respOrg = this.request.getParameter("respOrg");
		String proxyType = this.request.getParameter("proxyType");
		this.branchService.cancelProxy(
				new BranchProxyKey(proxyId, respOrg, proxyType), 
				this.getSessionUserCode());

		String msg = LogUtils.r("注销机构代理关系[{0}, {1}]成功", proxyId, respOrg);
		this.addActionMessage("/pages/proxy/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String activate() throws Exception {
		this.checkEffectiveCertUser();
		
		String proxyId = this.request.getParameter("proxyId");
		String respOrg = this.request.getParameter("respOrg");
		String proxyType = this.request.getParameter("proxyType");
		this.branchService.activateProxy(
				new BranchProxyKey(proxyId, respOrg, proxyType), 
				this.getSessionUserCode());
		
		String msg = LogUtils.r("生效机构代理关系[{0}, {1}]成功", proxyId, respOrg);
		this.addActionMessage("/pages/proxy/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		String proxyId = this.request.getParameter("proxyId");
		String respOrg = this.request.getParameter("respOrg");
		String proxyType = this.request.getParameter("proxyType");
		this.branchService.deleteProxy(
				new BranchProxyKey(proxyId, respOrg, proxyType), 
				this.getSessionUserCode());
		
		String msg = LogUtils.r("删除机构代理关系[{0}, {1}]成功", proxyId, respOrg);
		this.addActionMessage("/pages/proxy/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String showSelect() throws Exception {
		return "select";
	}
	
	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (branchInfo != null) {
			params.put("branchCode", branchInfo.getBranchCode());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(branchInfo.getBranchName()));
			params.put("status", BranchState.NORMAL.getValue());
		}
		params.put("cardBranch", cardBranch);
		params.put("proxyType", proxyType);
		this.page = this.branchInfoDAO.findProxy(params, this.getPageNumber(), Constants.DEFAULT_SELECT_PAGE_SIZE);
		return "data";
	}
	
	public BranchProxy getBranchProxy() {
		return branchProxy;
	}

	public void setBranchProxy(BranchProxy branchProxy) {
		this.branchProxy = branchProxy;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getTypes() {
		return types;
	}

	public void setTypes(Collection types) {
		this.types = types;
	}

	public boolean isRadio() {
		return radio;
	}

	public void setRadio(boolean radio) {
		this.radio = radio;
	}

	public BranchInfo getBranchInfo() {
		return branchInfo;
	}

	public void setBranchInfo(BranchInfo branchInfo) {
		this.branchInfo = branchInfo;
	}

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getProxyType() {
		return proxyType;
	}

	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}

	public List<CardPrivilegeGroup> getCardPrivilegeList() {
		return cardPrivilegeList;
	}

	public void setCardPrivilegeList(List<CardPrivilegeGroup> cardPrivilegeList) {
		this.cardPrivilegeList = cardPrivilegeList;
	}

	public String[] getGroups() {
		return groups;
	}

	public void setGroups(String[] groups) {
		this.groups = groups;
	}

	public String getHasGroup() {
		return hasGroup;
	}

	public void setHasGroup(String hasGroup) {
		this.hasGroup = hasGroup;
	}

	public List<BranchInfo> getRespOrgList() {
		return respOrgList;
	}

	public void setRespOrgList(List<BranchInfo> respOrgList) {
		this.respOrgList = respOrgList;
	}

}
