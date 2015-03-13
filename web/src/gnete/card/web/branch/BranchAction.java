package gnete.card.web.branch;

import flink.etc.DatePair;
import flink.etc.MatchMode;
import flink.util.Cn2PinYinHelper;
import flink.util.CommonHelper;
import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AreaDAO;
import gnete.card.dao.BankInfoDAO;
import gnete.card.dao.BranchHasTypeDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CurrCodeDAO;
import gnete.card.dao.UserInfoDAO;
import gnete.card.entity.Area;
import gnete.card.entity.BankInfo;
import gnete.card.entity.BranchHasType;
import gnete.card.entity.BranchHasTypeKey;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.BranchState;
import gnete.card.entity.state.CurrCodeState;
import gnete.card.entity.type.AcctMediaType;
import gnete.card.entity.type.AcctType;
import gnete.card.entity.type.BranchLevelType;
import gnete.card.entity.type.BranchType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RiskLevelType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SetModeType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BranchService;
import gnete.card.util.UserOfLimitedTransQueryUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: BranchAction.java
 *
 * @description: 机构管理
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: LiHeng
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-7-7
 */
public class BranchAction extends BaseAction {
	
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private BranchService branchService;
	@Autowired
	private BranchHasTypeDAO branchHasTypeDAO;
//	@Autowired
//	private RoleInfoDAO roleInfoDAO;
//	@Autowired
//	private PrivilegeDAO privilegeDAO;
	@Autowired
	private AreaDAO areaDAO;
	@Autowired
	private UserInfoDAO userInfoDAO;
	@Autowired
	private BankInfoDAO bankInfoDAO;

	private BranchInfo branch;
	private String type;
	private BranchHasType branchHasType;
	private List<BranchLevelType> branchLevelList;
	
	// 增加模式：0为添加机构管理员，1为不添加机构管理员
	private int addAdmin;
	private String branchAdmin;
	private boolean hasAdmin;
	
	private Paginater page;
	
	private Collection statusList;
	private Collection typeList;
	
	private String branchHasTypes;
	// 是否发卡机构
	private boolean cardBranch;
	private String[] typeCodes;
	
	// 管理机构
	private List<BranchInfo> manageBranchList;
	// 发展机构
	private List<BranchInfo> developBranchList;
	
	/** 账户类型 */
	private List<AcctType> acctTypeList;
	/** 账户介质类型 */
	private List<AcctMediaType> acctMediaTypeList;
	/** 风险等级 */
	private List<RiskLevelType> riskLevelTypeList;
	
	// 清算模式
	private List setModeList;
	
	private String branchCode;

	private String privileges;
	private String definePrivilege;

	// 界面选择时是否单选
	private boolean radio;
	private String branchTypes;
	
	private CardRiskReg cardRiskReg;

	private List currCodeList;

	@Autowired
	private CurrCodeDAO currCodeDAO;
	
	private boolean showProxy;
	private boolean showSettle = false;
	private boolean showSetMode = false;
	
	private List yesOrNoList;
	
	private String areaName;
	private String accAreaName;
	private String manageBranchName; //管理机构名

	private String startDate;
	private String endDate;
	private String checkStartDate;//审核时间
	private String checkEndDate;
	
	private String asMerch;
	private String setMode;
	private String parent;
	private String parentName;
	private String setModeName;
	private boolean showCenter = false;
	
	private static final Logger logger = Logger.getLogger(BranchAction.class);
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		this.statusList = BranchState.getAll();
		initPage();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		boolean isUserOfLimitedTransQuery = isUserOfLimitedTransQuery();
		if (isUserOfLimitedTransQuery){
			params.put("isUserOfLimitedTransQuery", isUserOfLimitedTransQuery);
			params.put("limitedExcludeManageBranchCodes", UserOfLimitedTransQueryUtil.getExcludeManageBranchCodes());
		}
		
		if (branch != null) {
			params.put("branchCode", branch.getBranchCode());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(branch.getBranchName()));
			params.put("status", branch.getStatus());
			params.put("areaCode", branch.getAreaCode());
			params.put("parent", parent);
			params.put("singleProduct", branch.getSingleProduct());
			
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
		}
		
		//为审核时间查询增加的属性
		if(CommonHelper.isNotEmpty(checkStartDate) || CommonHelper.isNotEmpty(checkEndDate)){
			Date otherSDate = CommonHelper.isNotEmpty(checkStartDate) ? DateUtil.formatDate(checkStartDate, "yyyyMMdd"):null;
			Date otherEDate = CommonHelper.isNotEmpty(checkEndDate) ? DateUtil.formatDate(checkEndDate, "yyyyMMdd"):null;
			otherSDate = otherSDate==null ? otherEDate:otherSDate;
			otherEDate = otherEDate==null ? otherSDate:otherEDate;
			params.put("checkStartDate", otherSDate);
			params.put("checkEndDate", otherEDate);
		}
		
		if (StringUtils.isNotEmpty(type)) {
			params.put("branchTypes", new String[]{type});
		}
       
		showCenter = false; // 默认不显示发卡机构查询条件
		// 判断登录角色的类型
		if (isCenterOrCenterDeptRoleLogined()) { // 运营中心和运营中心部门
			showCenter = true;
			params.put("branchs", null);
		} else if (isFenzhiRoleLogined()) { // 分支机构
			params.put("fenzhiCode", this.getLoginBranchCode());
		} else if (isAgentRoleLogined()) { // 运营代理商
			params.put("agentBranchCode", this.getLoginBranchCode());
		} else if (isMerchantRoleLogined() || isPersonalRoleLogined()) { // 商户和个人
			throw new BizException("非机构禁止访问该菜单！");
		} else {// 其他机构 均只能查询自己
			params.put("branchs", this.getMyBranch());
		}
		this.page = this.branchInfoDAO.findBranch(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	/** 是否是受限的交易记录查询用户。注：该getter方法会在页面上使用，用于屏蔽按钮*/
	public boolean isUserOfLimitedTransQuery(){
		return userInfoDAO.isUserOfLimitedTransQuery(this.getSessionUserCode());
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.branch = (BranchInfo) this.branchInfoDAO.findByPk(this.branch.getBranchCode());
		
		// 查询机构拥有的类型
		this.branchHasTypes = "";
		List<BranchHasType> list = this.branchHasTypeDAO.findByBranch(this.branch.getBranchCode());
		for (int i = 0; i < list.size(); i++) {
			BranchHasType key = list.get(i);
			if(BranchType.FENZHI.getValue().equals(key.getTypeCode())
					|| BranchType.AGENT.getValue().equals(key.getTypeCode())
					|| BranchType.CARD.getValue().equals(key.getTypeCode())){
				this.showSetMode = true;
				if (BranchType.CARD.getValue().equals(key.getTypeCode())) {
					this.cardBranch = true;
				}
				
				this.setMode = key.getSetMode();
				if(this.setMode != null){
					this.setModeName = (SetModeType.ALL.get(this.setMode) == null ? "" : SetModeType.valueOf(this.setMode).getName());
				}
			}
			
			String branchLevelName = "";
			if (BranchType.FENZHI.getValue().equals(key.getTypeCode())
					|| BranchType.CARD_SELL.getValue().equals(key.getTypeCode())) {
				branchLevelName = ( BranchLevelType.ALL.get(key.getBranchLevel()) == null ? "" 
						: BranchLevelType.valueOf(key.getBranchLevel()).getName());
			}
			this.branchHasTypes += branchLevelName + (BranchType.ALL.get(key.getTypeCode()) == null ? "" : BranchType.valueOf(key.getTypeCode()).getName());
			if (i < list.size() - 1) {
				this.branchHasTypes += ",";
			}
			
			if (BranchType.CARD_SELL.getValue().equals(key.getTypeCode())){
				showSettle = true;
			}
		}
		
		Area area = (Area) this.areaDAO.findByPk(this.branch.getAreaCode());
		if (area != null) {
			this.setAreaName(area.getAreaName());
		}
		
		Area accArea = (Area) this.areaDAO.findByPk(this.branch.getAccAreaCode());
		if (accArea != null) {
			this.setAccAreaName(accArea.getAreaName());
		}
		
//		this.log("查询机构["+this.branch.getBranchCode()+"]明细信息", UserLogType.SEARCH);
		logger.debug("用户[" + this.getSessionUserCode() + "]查询机构[" + this.branch.getBranchCode() + "]明细信息");
		
		return DETAIL;
	}

	// 打开新增页面的初始化操作。
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		if (isCenterOrCenterDeptRoleLogined() || isAgentRoleLogined() ) {//运营中心（部门）  或  运营代理商
			
		} else if (this.isFenzhiRoleLogined()) { // 成本模式的分支机构可以添加机构。
			BranchHasTypeKey key = new BranchHasTypeKey(getLoginBranchCode(), RoleType.FENZHI.getValue());
			BranchHasType type = (BranchHasType) this.branchHasTypeDAO.findByPk(key);
			Assert.isTrue(SetModeType.COST.getValue().equals(type.getSetMode()), "没有权限添加机构！");
		} else {
			throw new BizException("没有权限添加机构！");
		}
		this.branch = new BranchInfo();
		this.branch.setCurCode("CNY");
		this.branch.setAcctMediaType(AcctMediaType.BANK_CARD.getValue());
		this.branch.setAcctType(AcctType.COMPANY.getValue());
		this.branch.setSingleProduct(Symbol.NO); // 默认不是单机产品机构
		
		initPage();
		return ADD;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.branch = (BranchInfo) this.branchInfoDAO.findByPk(this.branch.getBranchCode());
		if ( isCenterOrCenterDeptRoleLogined()) {//运营中心（部门）
			
		} else if ( this.isFenzhiRoleLogined() ) { // 成本模式的分支机构可以编辑机构。
			BranchHasTypeKey key = new BranchHasTypeKey(getLoginBranchCode(), RoleType.FENZHI.getValue());
			BranchHasType type = (BranchHasType) this.branchHasTypeDAO.findByPk(key);
			Assert.isTrue(SetModeType.COST.getValue().equals(type.getSetMode()), "没有权限编辑机构！");
			
		} else if ( isAgentRoleLogined()) { // 运营代理商登录
			boolean bool = branch.isWaitedStatus() && branch.getDevelopSide().equals(this.getLoginBranchCode());
			Assert.isTrue(bool, "运营代理只能编辑 待审核状态的机构！");
			
		} else {
			throw new BizException("没有权限编辑机构！");
			
		}
		initPage();
		
		BranchInfo parentBranch = (BranchInfo) this.branchInfoDAO.findByPk(this.branch.getParent());
		this.parentName = (parentBranch != null) ? parentBranch.getBranchName(): "";  
		if (isAgentRoleLogined()) {
			this.developBranchList = new ArrayList<BranchInfo>();
			this.developBranchList.add(this.branchInfoDAO.findBranchInfo(getLoginBranchCode()));
		}else{
			this.developBranchList = this.branchInfoDAO.findProxyByBranchCode(branch.getParent(), ProxyType.AGENT);
			this.developBranchList.add(this.branchInfoDAO.findBranchInfo(this.branch.getParent()));
		}
		
		Area area = (Area) this.areaDAO.findByPk(this.branch.getAreaCode());
		if (area != null) {
			this.setAreaName(area.getAreaName());
		}
		Area accArea = (Area) this.areaDAO.findByPk(this.branch.getAccAreaCode());
		if (accArea != null) {
			this.setAccAreaName(accArea.getAreaName());
		}
		
		List<BranchHasType> list = this.branchHasTypeDAO.findByBranch(this.branch.getBranchCode());
		this.cardBranch = false;
		
		boolean isFenzhi = false;
		for (BranchHasType key : list) {
			if (BranchType.CARD.getValue().equals(key.getTypeCode())) {
				this.cardBranch = true;
				this.showProxy = true;
				this.showSetMode = true;
				
				this.setMode = key.getSetMode();
				if(this.setMode!=null){
					this.setModeName = SetModeType.valueOf(this.setMode).getName();
				}
			}
			if (BranchType.CARD_MERCHANT.getValue().equals(key.getTypeCode())
					|| BranchType.CARD_SELL.getValue().equals(key.getTypeCode())){
				showProxy = true;
			}
			if (BranchType.CARD_SELL.getValue().equals(key.getTypeCode())){
				showSettle = true;
			}
			if (BranchType.FENZHI.getValue().equals(key.getTypeCode())
					|| BranchType.AGENT.getValue().equals(key.getTypeCode())) {
				isFenzhi = true;
				this.showSetMode = true;
				this.setMode = key.getSetMode();
				if(this.setMode != null){
					this.setModeName = SetModeType.valueOf(this.setMode).getName();
				}
				/*if(key.getTypeCode().equals(BranchType.FENZHI.getValue())){
					this.setMode = key.getSetMode();
					this.setModeName = SetModeType.valueOf(this.setMode).getName();
					this.showSetMode = true;
				}*/
			}
		}
		// 读取管理机构
		if (isFenzhi){
			this.manageBranchList = this.branchInfoDAO.findByTypes(BranchType.getCenter());
		} else {
			this.manageBranchList = this.branchInfoDAO.findByTypes(BranchType.getManageBranch());
		}
		
		// 查找其是否存在机构管理员
//		this.hasAdmin = this.roleInfoDAO.isExist(this.branch.getBranchCode() + "admin");
		return MODIFY;
	}

	private void initPage() {
		this.typeList = new ArrayList<BranchType>();// 默认
		this.branchLevelList = new ArrayList<BranchLevelType>();// 默认
		if (isCenterOrCenterDeptRoleLogined()) {
			this.typeList = BranchType.getForCenter();
			this.branchLevelList = BranchLevelType.getAll();
		} else if (isFenzhiRoleLogined()) {
			BranchHasTypeKey key = new BranchHasTypeKey(getLoginBranchCode(), BranchType.FENZHI.getValue());
			BranchHasType bHasType = (BranchHasType) this.branchHasTypeDAO.findByPk(key);
			if (bHasType != null) {
				// 一级分支机构可以创建下级分支机构
				if (BranchLevelType.FIRST.getValue().equals(bHasType.getBranchLevel())) {
					this.typeList = BranchType.getForCenter();
					this.branchLevelList = BranchLevelType.getFirstList();
				}
				// 2级分支机构可以创建3级分支机构
				else if (BranchLevelType.SECOND.getValue().equals(bHasType.getBranchLevel())) {
					this.typeList = BranchType.getForCenter();
					this.branchLevelList = BranchLevelType.getSecondList();
				}
				// 3级分支机构不能创建分支机构
				else if (BranchLevelType.THIRD.getValue().equals(bHasType.getBranchLevel())) {
					this.typeList = BranchType.getForBranch();
				}
			} 
		} else if (isAgentRoleLogined()) {
			this.typeList.add(BranchType.CARD);
		}
		
		this.yesOrNoList = YesOrNoFlag.getAll();
		
		this.currCodeList = currCodeDAO.findCurrCode(CurrCodeState.NORMAL.getValue());
		this.setModeList = SetModeType.getAll();
		this.acctTypeList = AcctType.getAll();
		this.acctMediaTypeList = AcctMediaType.getAll();
		this.riskLevelTypeList = RiskLevelType.getAll();
	}
	
	/**
	 * 根据角色类型得到机构级别列表
	 * @throws Exception
	 */
	public void loadBranchLevel() throws Exception {
		String branchType = request.getParameter("type");
		
		this.branchLevelList = new ArrayList<BranchLevelType>();
		if ( isCenterOrCenterDeptRoleLogined()){
			this.branchLevelList = BranchLevelType.getAll();
		} else if ( this.isFenzhiRoleLogined() ) {		// 分支机构登录时
			BranchHasTypeKey key = new BranchHasTypeKey(getLoginBranchCode(),BranchType.FENZHI.getValue());
			BranchHasType bHasType = (BranchHasType) this.branchHasTypeDAO.findByPk(key);
			
			if (bHasType != null) {
				if (RoleType.FENZHI.getValue().equals(branchType)) {
					// 一级分支机构可以创建下级分支机构
					if (BranchLevelType.FIRST.getValue().equals(bHasType.getBranchLevel())) {
						this.branchLevelList = BranchLevelType.getFirstList();
					}
					// 2级分支机构可以创建3级分支机构
					else if (BranchLevelType.SECOND.getValue().equals(bHasType.getBranchLevel())) {
						this.branchLevelList = BranchLevelType.getSecondList();
					}
					// 3级分支机构不能创建分支机构
					else {
						this.branchLevelList = new ArrayList<BranchLevelType>();
					}
				}
				// 增加分支机构时
				else if (RoleType.CARD_SELL.getValue().equals(branchType)) {
					this.branchLevelList = BranchLevelType.getAll();
				}
			}
		}
		
		StringBuffer sb = new StringBuffer(128);
		sb.append("<option value=\"\">").append("--请选择--</option>");
		for (BranchLevelType levelType : branchLevelList) {
			sb.append("<option value=\"")
				.append(levelType.getValue()).append("\">")
				.append(levelType.getName()).append("</option>");
		}
		
		this.respond(sb.toString());
	}
	
	/**
	 * ajax 查找 发展方 列表(要包含自己)
	 * @throws Exception
	 */
	public void getDevelopList() throws Exception {
		String manageBranch = request.getParameter("manageBranch");
		List<BranchInfo> developList = new ArrayList<BranchInfo>();
		StringBuffer sb = new StringBuffer(128);
		if (StringUtils.isNotEmpty(manageBranch)) {
			if (isAgentRoleLogined()) {
				developList.add(this.branchInfoDAO.findBranchInfo(getLoginBranchCode()));
			} else {
				developList = this.branchInfoDAO.findProxyByBranchCode(manageBranch, ProxyType.AGENT);
				developList.add(this.branchInfoDAO.findBranchInfo(manageBranch));
			}
			
			for (BranchInfo branchInfo : developList) {
				sb.append("<option value=\"")
				.append(branchInfo.getBranchCode()).append("\">")
				.append(branchInfo.getBranchName()).append("</option>");
			}
		}
		this.respond(sb.toString());
	}
	
	// 新增机构信息
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		isHasRight();
		if (this.cardRiskReg != null) {
			this.branch.setCardRiskAmt(this.cardRiskReg.getAmt());
		}
		this.branch.setAdminId(this.branchAdmin);
		
		this.branchService.addBranch(this.branch, this.getSessionUser(), this.branchHasType);

		// 如果发卡机构选择的同时作为商户，则跳转到商户新增页面
		if (Symbol.YES.equals(this.asMerch)){
			String url = request.getContextPath() + "/pages/merch/showAdd.do?branchCode=" + this.branch.getBranchCode();
			logger.debug("机构[" + this.branch.getBranchCode() + "]的添加申请已经提交！现在跳转到添加商户页面。。。");
			response.sendRedirect(url);
			return null;
		}
		
		String msg = "机构[" + this.branch.getBranchCode() + "]的添加申请已经提交！";
		if (RoleType.CARD.getValue().equals(branchHasType.getTypeCode())) {
			msg = msg + "审核通过后，请在菜单业务规则及参数定义->业务权限参数定义，为该发卡机构定义相关业务权限。";
		}
		this.addActionMessage("/pages/branch/list.do", msg);
		this.log(msg, UserLogType.ADD);
		
//		if (BranchState.NORMAL.equals(branch.getStatus())) {
//			// 刷新机构缓存
//			CacheMgr.getInstance().refreshOneBranch(this.branch.getBranchCode());
//		}
		return SUCCESS;
	}
	
	/** ajax 查找管理机构 */
	public void getManageBranch() throws Exception {
		// 新增的机构类型
		String branchType = request.getParameter("type");
		// 新增的机构级别（只有新增运营分支机构和售卡代理时才需要设置此值）
		String branchLevel = request.getParameter("branchLevel");
		
		this.manageBranchList = new ArrayList<BranchInfo>(); // 默认管理机构
		
		if (isCenterOrCenterDeptRoleLogined()) { // 运营中心的用户登录的话
			
			if (RoleType.FENZHI.getValue().equals(branchType)) { // 新增的是分支机构时
				if (BranchLevelType.FIRST.getValue().equals(branchLevel)) {
					// 新增的是一级分支机构，管理机构为运营中心
					this.manageBranchList = this.branchInfoDAO.findByTypes(BranchType.getCenter());
				} else if (BranchLevelType.SECOND.getValue().equals(branchLevel)) {
					// 新增的是二级分支机构，管理机构为一级分支机构
					this.manageBranchList = this.branchInfoDAO.findByLevelAndType(BranchLevelType.FIRST.getValue(), branchType);
				} else if (BranchLevelType.THIRD.getValue().equals(branchLevel)) {
					// 新增的是三级分支机构，管理机构为二级分支机构
					this.manageBranchList = this.branchInfoDAO.findByLevelAndType(BranchLevelType.SECOND.getValue(), branchType);
				}
			} else {
				// 否则，则是取得所有的分支机构
				this.manageBranchList = this.branchInfoDAO.findByType(BranchType.FENZHI.getValue());
			}
			
		} else if( isFenzhiRoleLogined() ) { // 分支机构登录的话 
			
			if (RoleType.FENZHI.getValue().equals(branchType)) { // 新增的是分支机构时
				if (BranchLevelType.FIRST.getValue().equals(this.getLoginBranchLevel())) {// 登录的分支机构为一级分支机构
					if (BranchLevelType.SECOND.getValue().equals(branchLevel)) {
						// 新增的是二级分支机构，管理机构为自己
						manageBranchList.add(this.branchInfoDAO.findBranchInfo(this.getLoginBranchCode()));
					} else if (BranchLevelType.THIRD.getValue().equals(branchLevel)) {
						// 新增的是三级分支机构，管理机构为登录机构所管理的二级分支机构
						manageBranchList = this.branchInfoDAO.findByTypeAndManage(BranchType.FENZHI.getValue(), this.getLoginBranchCode());
					}
				} else if (BranchLevelType.SECOND.getValue().equals(this.getLoginBranchLevel())) {
					// 登记的机构为二级分支机构时只能添加三级分支机构,管理机构为自己
					manageBranchList.add(this.branchInfoDAO.findBranchInfo(this.getLoginBranchCode()));
				}
			} else { // 新增的是非分支机构的话
				if (BranchLevelType.FIRST.getValue().equals(this.getLoginBranchLevel())) {// 登录的分支机构为一级分支机构
					// 自己肯定是管理机构之一
					this.manageBranchList.add(this.branchInfoDAO.findBranchInfo(this.getLoginBranchCode()));
					// 再取出二级分支机构
					List<BranchInfo> list = this.branchInfoDAO.findByLevelTypeAndParent(BranchLevelType.SECOND.getValue(), 
							BranchType.FENZHI.getValue(), this.getLoginBranchCode());
					//将其作为管理机构
					this.manageBranchList.addAll(list);
					for (BranchInfo info : list) {
						// 根据二级可查找出三级分支机构
						manageBranchList.addAll(this.branchInfoDAO.findByLevelTypeAndParent(BranchLevelType.THIRD.getValue(), 
								BranchType.FENZHI.getValue(), info.getBranchCode()));
					}
				} else if (BranchLevelType.SECOND.getValue().equals(this.getLoginBranchLevel())) {// 登录用户所属机构为二级分支机构时
					this.manageBranchList.add(this.branchInfoDAO.findBranchInfo(this.getLoginBranchCode()));
					// 再取出三级分支机构
					List<BranchInfo> list = this.branchInfoDAO.findByLevelTypeAndParent(BranchLevelType.THIRD.getValue(), 
							BranchType.FENZHI.getValue(), this.getLoginBranchCode());
					//将其作为管理机构
					this.manageBranchList.addAll(list);
				} else if (BranchLevelType.THIRD.getValue().equals(this.getLoginBranchLevel())) {// 登录用户所属机构为二级分支机构时
					// 自己肯定是管理机构之一
					this.manageBranchList.add(this.branchInfoDAO.findBranchInfo(this.getLoginBranchCode()));
				}
			}
			
		} else if (isAgentRoleLogined()) { // 运营代理商登录
			BranchInfo loginBranch = this.branchInfoDAO.findBranchInfo(getLoginBranchCode());
			this.manageBranchList.add(this.branchInfoDAO.findBranchInfo(loginBranch.getParent()));
		}

		// 按名称排序
		Collections.sort(manageBranchList, new Comparator() {
			public int compare(Object o1, Object o2) {
				BranchInfo branch1 = (BranchInfo) o1;
				BranchInfo branch2 = (BranchInfo) o2;
				return Cn2PinYinHelper.cn2Spell(branch1.getBranchName()).compareTo(Cn2PinYinHelper.cn2Spell(branch2.getBranchName()));
			}
		});

		StringBuffer sb = new StringBuffer(128);
		sb.append("<option value=\"\">").append("--请选择--</option>");
		for (BranchInfo branchInfo : manageBranchList) {
			sb.append("<option value=\"")
				.append(branchInfo.getBranchCode()).append("\">")
				.append(branchInfo.getBranchName()).append("</option>");
		}
		this.respond(sb.toString());
	}
	
	// 打开新增页面的初始化操作
	public String showAddExist() throws Exception {
		this.checkEffectiveCertUser();
		
		isHasRight();
		this.typeList = new ArrayList<BranchType>();// 默认
		if (isCenterRoleLogined()) {
			this.typeList = BranchType.getForCenter();
		} else if (isFenzhiRoleLogined()) {
			this.typeList = BranchType.getForBranch();
		}
		return "addExist";
	}
	
	// 新增机构信息
	public String addExist() throws Exception {
		this.checkEffectiveCertUser();
		
		isHasRight();
		
		this.branchService.bindType(this.branch.getBranchCode(), type, this.getSessionUserCode());
		
		String msg = "绑定新机构类型["+this.branch.getBranchCode()+"]成功！";
		this.addActionMessage("/pages/branch/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 修改机构信息
	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		isHasRight(); // 权限
		this.branchService.modifyBranch(this.branch, this.getSessionUserCode(), this.setMode);
		
		String msg = "修改机构信息["+this.branch.getBranchCode()+"]成功！";
		this.addActionMessage("/pages/branch/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		
		return SUCCESS;
	}
	
	public String showModifyType() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!(isCenterRoleLogined() || isFenzhiRoleLogined())) {
			throw new BizException("没有权限修改机构类型");
		}
		
		this.typeList = BranchType.getAll();
		
		this.branch = (BranchInfo) this.branchInfoDAO.findByPk(this.branch.getBranchCode());
		
		this.developBranchList = this.branchInfoDAO.findProxyByBranchCode(this.branch.getParent(), ProxyType.AGENT);
		this.developBranchList.add(this.branchInfoDAO.findBranchInfo(this.branch.getParent()));
		
		List<BranchHasType> list = this.branchHasTypeDAO.findByBranch(this.branch.getBranchCode());
		for (BranchHasType key : list) {
			this.branchHasTypes += key.getTypeCode() + ",";
		}
		
		return "modifyType";
	}
	
	public String modifyType() throws Exception {
		this.checkEffectiveCertUser();
		
		isHasRight();
		this.branchService.bindType(this.branch, typeCodes, this.getSessionUserCode());
		
		String msg = "修改机构["+this.branch.getBranchCode()+"]类型信息成功！";
		this.addActionMessage("/pages/branch/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		
		return SUCCESS;
	}
	
	// 停用机构
	public String stop() throws Exception {
		this.checkEffectiveCertUser();
		
		isHasRight();
		this.branchService.stopBranch(this.getBranchCode(), this.getSessionUser());
		
		String msg = "用户[" + this.getSessionUserCode() + "]停用机构["+this.getBranchCode()+"]成功！";
		this.addActionMessage("/pages/branch/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.UPDATE);
		
		return SUCCESS;
	}
	
	// 启用机构
	public String start() throws Exception {
		this.checkEffectiveCertUser();
		
		isHasRight();
		this.branchService.startBranch(this.getBranchCode(), this.getSessionUser());
		
		String msg = "启用机构["+this.getBranchCode()+"]成功！";
		this.addActionMessage("/pages/branch/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.UPDATE);
		
		return SUCCESS;
	}
	
	/**
	 * 提交审核，提交通过后台程序导入的旧发卡机构
	 * @return
	 * @throws Exception
	 */
	public String submitCheck() throws Exception{
		this.checkEffectiveCertUser();
		
		if (!isCenterOrCenterDeptRoleLogined()) {
			throw new BizException("没有权限提交审核");
		}
		
		this.branchService.submitCheck(this.getSessionUser());
		
		String msg = LogUtils.r("将所有“待提交”机构提煎熬审核成功!");
		this.addActionMessage("/pages/branch/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	/**
	 * 删除审核不通过和待提交审核的机构
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		isHasRight();
		
		this.branchService.deleteBranch(this.getBranchCode());
		
		String msg = LogUtils.r("删除机构[{0}]成功！", this.getBranchCode());
		this.addActionMessage("/pages/branch/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.DELETE);
		
		return SUCCESS;
	}
	
	/**
	 * 是否有权限维护机构信息
	 * @throws BizException
	 */
	private void isHasRight() throws BizException{
		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined() || isAgentRoleLogined())) {
			throw new BizException("没有权限维护机构信息");
		}
	}
	
	public String showSelect() throws Exception {
		return "select";
	}
	
	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (branch != null) {
			params.put("branchCode", branch.getBranchCode());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(branch.getBranchName()));
			params.put("status", BranchState.NORMAL.getValue());
			params.put("setMode", setMode);
			params.put("parent", parent);
		}
		if (StringUtils.isNotEmpty(branchTypes)) {
			params.put("branchTypes", branchTypes.split(","));
		}
		
		if (isAgentRoleLogined()) {//只能查看 运营代理自身 和 运营代理发展的机构
			params.put("agentBranchCode", this.getLoginBranchCode());
		}
		
		this.page = this.branchInfoDAO.findBranch(params, this.getPageNumber(), Constants.DEFAULT_SELECT_PAGE_SIZE);
//		// 分支机构登录时，则把自己也算进去。
//		if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())
//				&& StringUtils.isNotBlank(parent)) {
//			if (StringUtils.isNotEmpty(branchTypes)) {
//				String[] types = branchTypes.split(",");
//				List<String> list = Arrays.asList(types);
//				
//				if (list.contains(RoleType.FENZHI.getValue())) {
//					BranchInfo branchInfo = this.branchInfoDAO.findBranchInfo(this.getLoginBranchCode());
//					Collection collection = page.getData();
//					collection.add(branchInfo);
//					
//					this.page.setData(collection);
//				}
//			}
//		}
		return "data";
	}
	
	/**
	 * 根据分支机构号查询其管理的发卡机构号
	 * @return
	 * @throws Exception
	 */
	public void loadBranch() throws Exception {
		String fenzhiCode = request.getParameter("fenzhiCode");
		
		List<BranchInfo> fenzhiList = new ArrayList<BranchInfo>();
		fenzhiList.addAll(this.branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), fenzhiCode));
		fenzhiList = sortBranchList(fenzhiList);
		
		StringBuffer sb = new StringBuffer(128);
		sb.append("<option value=\"\">--请选择--</option>");
		for (BranchInfo branchInfo : fenzhiList){
			sb.append("<option value=\"").append(branchInfo.getBranchCode())
						.append("\">").append(branchInfo.getBranchName()).append("</option>");
		}
		
		this.respond(sb.toString());
	}
	
	/**
	 * 根据机构简称，取机构简称的首字母的大写+Admin作为用户id
	 * @throws Exception
	 */
	public void getAdminUserId() throws Exception {
		String branchAbbname = request.getParameter("branchAbbname");
		if (StringUtils.isEmpty(branchAbbname)) {
			return;
		}
		String fromMerch = request.getParameter("fromMerch");
		
		String userId = "";
		if (StringUtils.equals(Symbol.YES, fromMerch)) {
			userId = StringUtils.upperCase(Cn2PinYinHelper.cn2FirstSpell(branchAbbname)) + "SHAdmin";
		} else {
			userId = StringUtils.upperCase(Cn2PinYinHelper.cn2FirstSpell(branchAbbname)) + "Admin";
		}
		
		JSONObject object = new JSONObject();
		object.put("userId", userId);
		
		this.respond(object.toString());
	}
	
	/**
	 * 判断用户ID是否已经存在
	 * @throws Exception
	 */
	public void checkUserId() throws Exception {
		String userId = request.getParameter("branchAdmin");
		if (StringUtils.isEmpty(userId)) {
			return;
		}
		
		boolean isExsit = this.userInfoDAO.isExist(userId);
		
		JSONObject object = new JSONObject();
		object.put("isExist", isExsit);
	
		this.respond(object.toString());
	}
	
	/**
	 * 根据银行号取得账户地区码
	 * @throws Exception
	 */
	public void loadAccAreaCode() throws Exception {
		String bankNo = request.getParameter("bankNo");
		BankInfo bankInfo = (BankInfo) this.bankInfoDAO.findByPk(bankNo);
		
		if (bankInfo != null) {
			Area accArea = (Area) this.areaDAO.findByPk(bankInfo.getAddrNo());
			JSONObject object = new JSONObject();
			object.put("accAreaCode", accArea.getAreaCode());
			object.put("accAreaName", accArea.getAreaName());
			
			this.respond(object.toString());
		}
	}
	
	/**
	 * 机构审核列表
	 * @return
	 * @throws Exception
	 */
	public String checkList() throws Exception {
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String ids[] = workflowService.getMyJob(WorkflowConstants.WORKFLOW_ADD_BRANCH, getSessionUser());
		
		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		
		this.page = this.branchInfoDAO.findBranch(params, this.getPageNumber(), this.getPageSize());

		return CHECK_LIST;
	}
	
	public BranchInfo getBranch() {
		return branch;
	}

	public void setBranch(BranchInfo branch) {
		this.branch = branch;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getStatusList() {
		return statusList;
	}

	public void setStatusList(Collection statusList) {
		this.statusList = statusList;
	}

	public Collection getTypeList() {
		return typeList;
	}

	public void setTypeList(Collection typeList) {
		this.typeList = typeList;
	}

	public int getAddAdmin() {
		return addAdmin;
	}

	public void setAddAdmin(int addAdmin) {
		this.addAdmin = addAdmin;
	}

	public BranchInfoDAO getBranchDAO() {
		return branchInfoDAO;
	}

	public void setBranchDAO(BranchInfoDAO branchDAO) {
		this.branchInfoDAO = branchDAO;
	}

	public String getBranchAdmin() {
		return branchAdmin;
	}

	public void setBranchAdmin(String branchAdmin) {
		this.branchAdmin = branchAdmin;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isHasAdmin() {
		return hasAdmin;
	}

	public void setHasAdmin(boolean hasAdmin) {
		this.hasAdmin = hasAdmin;
	}
	
	public String getBranchHasTypes() {
		return branchHasTypes;
	}

	public void setBranchHasTypes(String branchHasTypes) {
		this.branchHasTypes = branchHasTypes;
	}

	public String[] getTypeCodes() {
		return typeCodes;
	}

	public void setTypeCodes(String[] typeCodes) {
		this.typeCodes = typeCodes;
	}

	public List<BranchInfo> getManageBranchList() {
		return manageBranchList;
	}

	public void setManageBranchList(List<BranchInfo> manageBranchList) {
		this.manageBranchList = manageBranchList;
	}

	public List<BranchInfo> getDevelopBranchList() {
		return developBranchList;
	}

	public void setDevelopBranchList(List<BranchInfo> developBranchList) {
		this.developBranchList = developBranchList;
	}

	public boolean isCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(boolean cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getPrivileges() {
		return privileges;
	}

	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}

	public String getDefinePrivilege() {
		return definePrivilege;
	}

	public void setDefinePrivilege(String definePrivilege) {
		this.definePrivilege = definePrivilege;
	}

	public boolean isRadio() {
		return radio;
	}

	public void setRadio(boolean radio) {
		this.radio = radio;
	}

	public String getBranchTypes() {
		return branchTypes;
	}

	public void setBranchTypes(String branchTypes) {
		this.branchTypes = branchTypes;
	}

	public CardRiskReg getCardRiskReg() {
		return cardRiskReg;
	}

	public void setCardRiskReg(CardRiskReg cardRiskReg) {
		this.cardRiskReg = cardRiskReg;
	}

	public List getCurrCodeList() {
		return currCodeList;
	}

	public void setCurrCodeList(List currCodeList) {
		this.currCodeList = currCodeList;
	}

	public boolean isShowProxy() {
		return showProxy;
	}

	public void setShowProxy(boolean showProxy) {
		this.showProxy = showProxy;
	}

	public boolean isShowSettle() {
		return showSettle;
	}

	public void setShowSettle(boolean showSettle) {
		this.showSettle = showSettle;
	}

	public List getYesOrNoList() {
		return yesOrNoList;
	}

	public void setYesOrNoList(List yesOrNoList) {
		this.yesOrNoList = yesOrNoList;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getCheckStartDate() {
		return checkStartDate;
	}

	public void setCheckStartDate(String checkStartDate) {
		this.checkStartDate = checkStartDate;
	}

	public String getCheckEndDate() {
		return checkEndDate;
	}

	public void setCheckEndDate(String checkEndDate) {
		this.checkEndDate = checkEndDate;
	}

	public String getAsMerch() {
		return asMerch;
	}

	public void setAsMerch(String asMerch) {
		this.asMerch = asMerch;
	}

	public List getSetModeList() {
		return setModeList;
	}

	public void setSetModeList(List setModeList) {
		this.setModeList = setModeList;
	}

	public String getSetMode() {
		return setMode;
	}

	public void setSetMode(String setMode) {
		this.setMode = setMode;
	}

	public boolean isShowSetMode() {
		return showSetMode;
	}

	public void setShowSetMode(boolean showSetMode) {
		this.showSetMode = showSetMode;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getSetModeName() {
		return setModeName;
	}

	public void setSetModeName(String setModeName) {
		this.setModeName = setModeName;
	}

	public boolean isShowCenter() {
		return showCenter;
	}

	public void setShowCenter(boolean showCenter) {
		this.showCenter = showCenter;
	}

	public String getAccAreaName() {
		return accAreaName;
	}

	public void setAccAreaName(String accAreaName) {
		this.accAreaName = accAreaName;
	}

	public String getManageBranchName() {
		return manageBranchName;
	}

	public void setManageBranchName(String manageBranchName) {
		this.manageBranchName = manageBranchName;
	}

	public BranchHasType getBranchHasType() {
		return branchHasType;
	}

	public void setBranchHasType(BranchHasType branchHasType) {
		this.branchHasType = branchHasType;
	}

	public List<BranchLevelType> getBranchLevelList() {
		return branchLevelList;
	}

	public void setBranchLevelList(List<BranchLevelType> branchLevelList) {
		this.branchLevelList = branchLevelList;
	}

	public List<AcctType> getAcctTypeList() {
		return acctTypeList;
	}

	public void setAcctTypeList(List<AcctType> acctTypeList) {
		this.acctTypeList = acctTypeList;
	}

	public List<AcctMediaType> getAcctMediaTypeList() {
		return acctMediaTypeList;
	}

	public List<RiskLevelType> getRiskLevelTypeList() {
		return riskLevelTypeList;
	}
}
