package gnete.card.web.cardrisk;

import flink.util.Paginater;
import flink.util.SpringContext;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.BranchSellAmtDAO;
import gnete.card.dao.BranchSellChgDAO;
import gnete.card.dao.BranchSellRegDAO;
import gnete.card.dao.DepartmentInfoDAO;
import gnete.card.dao.UserInfoDAO;
import gnete.card.dao.UserSellAmtDAO;
import gnete.card.dao.UserSellChgDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BranchSellAmt;
import gnete.card.entity.BranchSellAmtKey;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.DepartmentInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.UserSellAmt;
import gnete.card.entity.UserSellAmtKey;
import gnete.card.entity.UserSellChg;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SellType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardRiskService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: SellAmtAction.java
 *
 * @description: 售卡配额管理
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: LiHeng
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-12-28 下午03:27:03
 */
public class SellAmtAction extends BaseAction{

	@Autowired
	private CardRiskService cardRiskService;
	@Autowired
	private BranchSellRegDAO branchSellRegDAO;
	@Autowired
	private BranchSellAmtDAO branchSellAmtDAO;
	@Autowired
	private BranchSellChgDAO branchSellChgDAO;
	@Autowired
	private UserSellAmtDAO userSellAmtDAO;
	@Autowired
	private UserSellChgDAO userSellChgDAO;
	@Autowired
	private DepartmentInfoDAO departmentInfoDAO; 
	@Autowired
	private UserInfoDAO userInfoDAO;
	
	private BranchSellAmt branchSellAmt;
	private BranchSellReg branchSellReg;
	private UserSellChg userSellChg;
	private UserSellAmt userSellAmt;
	private Paginater page;
	private Collection adjTypeList;
	
	private List sellTypeList;
	private List chgList;
	private List<BranchInfo> branchList;
	
	private boolean showAll = false;
	private boolean showDept = false;
	private boolean showSell = false;
	private boolean showType = false;
	private boolean showAllDept = false;
	
	private List<BranchInfo> sellBranchList;
	private List<DepartmentInfo> deptList;
	private List<UserInfo> userList;
	
	@Override
	public String execute() throws Exception {
		initPage();
		Map<String, Object> params =new HashMap<String, Object>();
		if (branchSellAmt != null) {
			params.put("cardBranch", branchSellAmt.getCardBranch());
			params.put("sellBranch", branchSellAmt.getSellBranch());
			params.put("sellType", branchSellAmt.getSellType());
		}
		
		// 发卡机构则允许查自己和自己的下级
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
//			params.put("cardBranch", this.branchList.get(0).getBranchCode());
			params.put("loginCardBranch", this.getLoginBranchCode());
		}
		// 售卡机构
		else if (RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
//			params.put("sellBranch", this.sellBranchList.get(0).getBranchCode());
			params.put("sellBranch", this.getLoginBranchCode());
		}
		// 发卡机构网点
		else if (RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
//			params.put("cardBranch", this.deptList.get(0).getDeptId());
			params.put("cardBranch", this.getSessionUser().getDeptId());
		} else {
			throw new BizException("只有发卡机构和售卡代理才能访问该菜单！");
		}
		
		this.page = this.branchSellAmtDAO.find(params, this.getPageNumber(), this.getPageSize());	
		return LIST;
	}
	
	//取得机构配额登记的明细
	public String detail() throws Exception {
		String cardBranch = request.getParameter("cardBranch");
		String sellBranch = request.getParameter("sellBranch");
		
		this.branchSellAmt = (BranchSellAmt) this.branchSellAmtDAO.findByPk(new BranchSellAmtKey(cardBranch, sellBranch));
		return DETAIL;
	}
	
	//查看变动明细
	public String chgDetail() throws Exception {
		String cardBranch = request.getParameter("cardBranch");
		String sellBranch = request.getParameter("sellBranch");

		Map<String, Object> params =new HashMap<String, Object>();
		params.put("cardBranch", cardBranch);
		params.put("sellBranch", sellBranch);
		
		this.page = this.branchSellChgDAO.findByBranch(params, this.getPageNumber(), this.getPageSize());
	
		return "chgDetail";
	}
	
	public String regList() throws Exception {
		initPage();
		Map<String, Object> params =new HashMap<String, Object>();
		
		if (branchSellReg != null) {
			params.put("cardBranch", branchSellReg.getCardBranch());
			params.put("sellBranch", branchSellReg.getSellBranch());
			params.put("sellType", branchSellReg.getSellType());
		}
		// 发卡机构则允许查自己
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
//			params.put("cardBranch", this.branchList.get(0).getBranchCode());
			params.put("loginCardBranch", this.getLoginBranchCode());
		}
		// 售卡机构
		else if (RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
//			params.put("sellBranch", this.sellBranchList.get(0).getBranchCode());
			params.put("sellBranch", this.getLoginBranchCode());
		} 
		// 发卡机构网点
		else if (RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
//			params.put("cardBranch", this.deptList.get(0).getDeptId());
			params.put("cardBranch", this.getSessionUser().getDeptId());
		} else {
			throw new BizException("只有发卡机构和售卡代理才能访问该菜单！");
		}

		this.page = this.branchSellRegDAO.find(params, this.getPageNumber(), this.getPageSize());	
		return "regList";
	}
	
	public String regDetail() throws Exception {
		this.branchSellReg = (BranchSellReg) this.branchSellRegDAO.findByPk(branchSellReg.getId());
		return "regDetail";
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		if (! (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType()))) {
			throw new BizException("非发卡机构用户禁止配额申请！");
		}
//		initPage();
		
		BranchInfoDAO branchInfoDAO = (BranchInfoDAO) SpringContext.getService("branchInfoDAOImpl");

		this.adjTypeList = AdjType.getSellAmtRegType(); //加载调整类型
		this.sellTypeList = SellType.getAll();
		
		this.sellBranchList = this.getMySellBranch();
		this.deptList = this.getMyDept();
		this.branchList = new ArrayList<BranchInfo>();
		this.branchList.add(branchInfoDAO.findBranchInfo(this.getSessionUser().getBranchNo()));
		
		sellBranchList = new ArrayList<BranchInfo>();
		// 发卡机构的顶级
		BranchInfo rootBranchInfo = branchInfoDAO.findRootByBranch(this.getSessionUser().getBranchNo());
		// 顶级发卡机构的所有下级
		sellBranchList.addAll(branchInfoDAO.findChildrenList(rootBranchInfo.getBranchCode()));
		sellBranchList.addAll(branchInfoDAO.findCardProxy(this.getSessionUser().getBranchNo(), ProxyType.SELL));
		this.sortBranchList(sellBranchList);
		
		return ADD;
	}
	
	public void loadDept() throws Exception {
		String cardBranch = request.getParameter("cardBranch");
		List<DepartmentInfo> deptList = this.departmentInfoDAO.findByBranch(cardBranch);
		StringBuffer sb = new StringBuffer(128);
		for (DepartmentInfo dept : deptList) {
			sb.append("<option value=\"").append(dept.getDeptId()).append("\">").append(dept.getDeptName()).append("</option>");
		}
		this.respond(sb.toString());
	}
	
	// 新增准备金调整申请登记
	public String add() throws Exception {
		String deptId = request.getParameter("department");
		if (StringUtils.isNotEmpty(deptId)) {
			branchSellReg.setSellBranch(deptId);
		}
		
		this.cardRiskService.addSellAmtReg(branchSellReg, this.getSessionUserCode());
		
		//启动单个流程
		this.workflowService.startFlow(branchSellReg, "branchSellAdapter", branchSellReg.getId().toString(), this.getSessionUser());
		
		String msg = "机构准备金调整申请["+this.branchSellReg.getId()+"]成功！";
		this.addActionMessage("/sellAmt/regList.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 准备金调整审核列表
	public String checkList() throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
			params.put("loginCardBranch", this.getLoginBranchCode());
		} else {
			throw new BizException("只有发卡机构能够做机构准备金调整");
		}
		
		// 首先调用流程引擎，得到我的待审批的工单ID
		String[] branchSellRegIds = this.workflowService.getMyJob(WorkflowConstants.WORKFLOW_BRANCH_SELL_REG, this.getSessionUser());
		
		if (ArrayUtils.isEmpty(branchSellRegIds)) {
			return CHECK_LIST;
		}
		
		params.put("ids", branchSellRegIds);
		this.page = this.branchSellRegDAO.find(params, this.getPageNumber(), this.getPageSize());
		logger.debug("用户[" + this.getSessionUserCode() + "]查询准备金调整申请审核列表信息");
		
		return CHECK_LIST;
	}

	//取得配额调整申请的明细，给流程审核人查看
	public String checkDetail() throws Exception {
		
		this.branchSellReg = (BranchSellReg)this.branchSellRegDAO.findByPk(branchSellReg.getId());
//		this.log("查询准备金调整申请["+this.branchSellReg.getId()+"]审核明细信息成功", UserLogType.SEARCH);
		logger.debug("用户[" + this.getSessionUserCode() + "]查询准备金调整申请[" + this.branchSellReg.getId() + "]审核明细信息");
		return DETAIL;
	}
	
	public String optList() throws Exception {
		initPage();
		Map<String, Object> params =new HashMap<String, Object>();
		
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
			params.put("branchCode", this.branchList.get(0).getBranchCode());
			this.userList = this.userInfoDAO.findByBranch(this.branchList.get(0).getBranchCode(), null);
		}
		// 售卡机构
		else if (RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
			params.put("branchCode", this.sellBranchList.get(0).getBranchCode());
			this.userList = this.userInfoDAO.findByBranch(this.sellBranchList.get(0).getBranchCode(), null);
		}
		// 发卡机构网点
		else if (RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			params.put("branchCode", this.deptList.get(0).getDeptId());
			this.userList = this.userInfoDAO.findByBranch(null, this.deptList.get(0).getDeptId());
		} else {
			throw new BizException("只有发卡机构和售卡代理才能访问该菜单！");
		}
		
		if (userSellAmt != null) {
			params.put("userId", userSellAmt.getUserId());
			params.put("branchCode", userSellAmt.getBranchCode());
		}
		
		this.page = this.userSellAmtDAO.find(params, this.getPageNumber(), this.getPageSize());	
		return "optList";
	}
	
	public String showOpt() throws Exception {
		initPage();
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
			this.userList = this.userInfoDAO.findByBranch(this.branchList.get(0).getBranchCode(), null);
		}
		// 售卡机构
		else if (RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
			this.userList = this.userInfoDAO.findByBranch(this.sellBranchList.get(0).getBranchCode(), null);
		}
		// 发卡机构网点
		else if (RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			this.userList = this.userInfoDAO.findByBranch(null, this.deptList.get(0).getDeptId());
		} else {
			throw new BizException("只有发卡机构和售卡代理才能访问该菜单！");
		}
		return "showOpt";
	}
	
	public void getUserOpt() throws Exception {
		String userId = request.getParameter("userId");
		String branchCode = request.getParameter("branchCode");
		
		UserSellAmt userSellAmt = (UserSellAmt) this.userSellAmtDAO.findByPk(new UserSellAmtKey(branchCode, userId));
		StringBuffer sb = new StringBuffer(128);
		if (userSellAmt != null) {
			sb.append("{success:true, amt:'").append(userSellAmt.getAmt())
				.append("', availableAmt:'").append(userSellAmt.getAvailableAmt()).append("'}");
		} else {
			sb.append("{success:true, amt:'").append(0)
				.append("', availableAmt:'").append(0).append("'}");
		}
		this.respond(sb.toString());
	}
	
	public String setOpt() throws Exception {
		this.cardRiskService.setUserSell(this.userSellChg, this.getSessionUserCode());
		String msg = "重置用户配额["+ this.userSellChg.getUserId() +"]成功";
		this.addActionMessage("/sellAmt/optList.do", msg);
		this.log(msg, UserLogType.ADD);
		return this.SUCCESS;
	}
	
	public String userChgDetail() throws Exception {
		String userId = request.getParameter("userId");
		this.chgList = this.userSellChgDAO.findByUser(userId);
		return "userChgDetail";
	}
	
	private void initPage() {
		BranchInfoDAO branchInfoDAO = (BranchInfoDAO) SpringContext.getService("branchInfoDAOImpl");

		this.adjTypeList = AdjType.getSellAmtRegType(); //加载调整类型
		this.sellTypeList = SellType.getAll();
		
		this.sellBranchList = this.getMySellBranch();
		this.deptList = this.getMyDept();
		
		this.sellBranchList = new ArrayList<BranchInfo>();
		this.branchList = new ArrayList<BranchInfo>();

		// 发卡机构的顶级
		BranchInfo rootBranchInfo = branchInfoDAO.findRootByBranch(this.getSessionUser().getBranchNo());
		this.branchList.addAll(branchInfoDAO.findChildrenList(rootBranchInfo.getBranchCode()));
		// 顶级发卡机构的所有下级
		this.sellBranchList.addAll(this.branchList);
		this.sellBranchList.addAll(branchInfoDAO.findCardProxy(this.getSessionUser().getBranchNo(), ProxyType.SELL));
		this.sortBranchList(this.sellBranchList);
		this.sortBranchList(this.branchList);
		
		// 如果是运营中心则允许查询所有
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())) {
			showAll = true;
			showSell = true;
			showAllDept = true;
		}
		// 分支机构则允许查询其管理的机构
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			showAll = true;
			showSell = true;
			showAllDept = true;
		}
		// 发卡机构则允许查自己
		else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
			showSell = true;
			showDept = true;
			showType = true;
			showAllDept = true;
		}
		// 售卡机构
		else if (RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
			showSell = true;
			this.sellTypeList = SellType.getForBranch();
		} 
		// 发卡机构网点
		else if (RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
			showDept = true;
			this.sellTypeList = SellType.getForDept();
		}
		else {
			showAll = false;
			showSell = false;
			showDept = false;
			showType = false;
		}
	}
	
	public BranchSellReg getBranchSellReg() {
		return branchSellReg;
	}

	public void setBranchSellReg(BranchSellReg branchSellReg) {
		this.branchSellReg = branchSellReg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getAdjTypeList() {
		return adjTypeList;
	}

	public void setAdjTypeList(Collection adjTypeList) {
		this.adjTypeList = adjTypeList;
	}

	public BranchSellAmt getBranchSellAmt() {
		return branchSellAmt;
	}

	public void setBranchSellAmt(BranchSellAmt branchSellAmt) {
		this.branchSellAmt = branchSellAmt;
	}

	public List getChgList() {
		return chgList;
	}

	public void setChgList(List chgList) {
		this.chgList = chgList;
	}

	public UserSellChg getUserSellChg() {
		return userSellChg;
	}

	public void setUserSellChg(UserSellChg userSellChg) {
		this.userSellChg = userSellChg;
	}

	public UserSellAmt getUserSellAmt() {
		return userSellAmt;
	}

	public void setUserSellAmt(UserSellAmt userSellAmt) {
		this.userSellAmt = userSellAmt;
	}

	public List getSellTypeList() {
		return sellTypeList;
	}

	public void setSellTypeList(List sellTypeList) {
		this.sellTypeList = sellTypeList;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public boolean isShowAll() {
		return showAll;
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

	public List<BranchInfo> getSellBranchList() {
		return sellBranchList;
	}

	public void setSellBranchList(List<BranchInfo> sellBranchList) {
		this.sellBranchList = sellBranchList;
	}

	public List<DepartmentInfo> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<DepartmentInfo> deptList) {
		this.deptList = deptList;
	}

	public boolean isShowDept() {
		return showDept;
	}

	public void setShowDept(boolean showDept) {
		this.showDept = showDept;
	}

	public boolean isShowSell() {
		return showSell;
	}

	public void setShowSell(boolean showSell) {
		this.showSell = showSell;
	}

	public boolean isShowType() {
		return showType;
	}

	public void setShowType(boolean showType) {
		this.showType = showType;
	}

	public boolean isShowAllDept() {
		return showAllDept;
	}

	public void setShowAllDept(boolean showAllDept) {
		this.showAllDept = showAllDept;
	}

	public List<UserInfo> getUserList() {
		return userList;
	}

	public void setUserList(List<UserInfo> userList) {
		this.userList = userList;
	}

}
