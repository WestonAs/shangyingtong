package gnete.card.web.merch;

import flink.etc.DatePair;
import flink.etc.MatchMode;
import flink.util.Cn2PinYinHelper;
import flink.util.CommonHelper;
import flink.util.DateUtil;
import flink.util.IOUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AreaDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CurrCodeDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.MerchInfoRegDAO;
import gnete.card.dao.MerchTypeDAO;
import gnete.card.dao.UserInfoDAO;
import gnete.card.entity.Area;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CurrCode;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.MerchInfoReg;
import gnete.card.entity.MerchType;
import gnete.card.entity.flag.OpenFlag;
import gnete.card.entity.flag.SetCycleFlag;
import gnete.card.entity.flag.TrueOrFalseFlag;
import gnete.card.entity.flag.UsePwdFlag;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.CurrCodeState;
import gnete.card.entity.state.MerchState;
import gnete.card.entity.state.MerchTypeState;
import gnete.card.entity.type.AcctMediaType;
import gnete.card.entity.type.AcctType;
import gnete.card.entity.type.MerchLevel;
import gnete.card.entity.type.RiskLevelType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.MerchService;
import gnete.card.tag.NameTag;
import gnete.card.util.UserOfLimitedTransQueryUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * @File: MerchAction.java
 *
 * @description: 商户管理
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lih
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-7-7
 */
public class MerchAction extends BaseAction {

	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchTypeDAO merchTypeDAO;
	@Autowired
	private MerchService merchService;
	@Autowired
	private CurrCodeDAO currCodeDAO;
	@Autowired
	private AreaDAO areaDAO;
	@Autowired
	private MerchInfoRegDAO merchInfoRegDAO;
	@Autowired
	private UserInfoDAO userInfoDAO;
	
	private Paginater page;

	private MerchInfo merchInfo;
	private MerchInfoReg merchInfoReg;

	// 管理机构
	private List<BranchInfo> manageBranchList;
	// 发展机构
	private List<BranchInfo> developBranchList;
	// 商户类型
	private List<MerchType> merchTypeList;
	/** 账户类型 */
	private List<AcctType> acctTypeList;
	/** 账户介质类型 */
	private List<AcctMediaType> acctMediaTypeList;
	
	private List<TrueOrFalseFlag> isNettingList;
	private List<MerchState> statusList;
	/** 是否单机产品类型 */
	private List<YesOrNoFlag> yesOrNoFlagList;

	private List setCycleList;
	private List openFlagList;
	private List merchLevelList;
	private List usePwdFlagList;
	/** 风险等级 */
	private List<RiskLevelType> riskLevelTypeList;
	
	private List<CurrCode> currCodeList;

	private String developBranch;
	private String manageBranch;
	private String manageBranchName;
	
	private String parent;

	// 界面选择时是否单选
	private boolean radio;

	private String card_BranchNo;
	private String proxyId;
	private String _currCode_Sel;
	private String _manageBranch;// 根据管理机构查询商户
	/** 查询出没有商户关系的发卡机构 */
	private String cardBranch;
	/** 交易控制表中没有的发卡机构 */
	private String _cardBranchNotLimit;
	
	// 发卡机构的商户组
	private String group_CardBranch;

	// 增加模式：0为添加机构管理员，1为不添加机构管理员
	private int addAdmin;
	private String merchAdmin;

	private String startDate;
	private String endDate;
	private String checkStartDate;//审核时间
	private String checkEndDate;
	
	private String merchTypeName;
	
	private String areaName;
	private String accAreaName;
	
	private String branchCode;
	
	private String cardBranchCode;
	private String cardBranchName;
	private boolean showCardBranch = false;
	private boolean showCenter = false;
	
	private boolean showModifyManage;
	
	private File upload;//上传的文件
	private String uploadFileName;// 上传的文件名

	@Override
	public String execute() throws Exception {
		this.statusList = MerchState.getWithOutCheck();
		this.yesOrNoFlagList = YesOrNoFlag.getAll();

		Map<String, Object> params = new HashMap<String, Object>();
		
		boolean isUserOfLimitedTransQuery = isUserOfLimitedTransQuery();
		if (isUserOfLimitedTransQuery){
			params.put("isUserOfLimitedTransQuery", isUserOfLimitedTransQuery);
			params.put("limitedExcludeManageBranchCodes", UserOfLimitedTransQueryUtil.getExcludeManageBranchCodes());
		}
		
		if (merchInfo != null) {
			String type = merchInfo.getMerchType();
			if(CommonHelper.isNotEmpty(type)){
				type =StringUtils.substring(type, 0, StringUtils.indexOf(type, "|"));
			}
			params.put("merchId", merchInfo.getMerchId());
			params.put("merchName", MatchMode.ANYWHERE.toMatchString(merchInfo.getMerchName()));
			params.put("cardBranchCode", cardBranchCode);// 发卡机构-商户关系
			params.put("manageBranch", merchInfo.getManageBranch());
			params.put("status", merchInfo.getStatus());
			params.put("merchType", type);
			params.put("singleProduct", merchInfo.getSingleProduct());
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
		}
		if(CommonHelper.isNotEmpty(checkStartDate)){
			Date otherSDate = DateUtil.formatDate(checkStartDate, "yyyyMMdd");
			params.put("checkStartDate", otherSDate);
		}
		if(CommonHelper.isNotEmpty(checkEndDate)){
			Date otherEDate = DateUtil.formatDate(checkEndDate, "yyyyMMdd");
			params.put("checkEndDate", otherEDate);
		}
		
		showCardBranch = false; // 默认不显示发卡机构查询条件
		
		if (isCenterOrCenterDeptRoleLogined()){// 运营中心
			showCardBranch = true;
			showCenter = true;
		}else if (isFenzhiRoleLogined()) {// 分支机构
			params.put("manageBranch", this.getLoginBranchCode());
			showCardBranch = true;
		}else if (isAgentRoleLogined()) {// 运营代理商
			params.put("agentBranchCode", getLoginBranchCode());
			
		} else if (isCardRoleLogined() || isCardDeptRoleLogined() ) {// 发卡机构或其部门则返回其的特约商户列表
			this.cardBranchCode = this.getLoginBranchCode();
			params.put("cardBranchCode", cardBranchCode);
			this.setCardBranchName(NameTag.getBranchName(cardBranchCode));
		} else if ( isMerchantRoleLogined() ) {// 商户
			params.put("merchId", this.getSessionUser().getMerchantNo());
		} else {
			throw new BizException("没有权限查看商户信息");
		}
		this.page = this.merchInfoDAO.find(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}


	/** 是否是受限的交易记录查询用户。注：该getter方法会在页面上使用，用于屏蔽按钮*/
	public boolean isUserOfLimitedTransQuery(){
		return userInfoDAO.isUserOfLimitedTransQuery(this.getSessionUserCode());
	}
	
	/**
	 * 登记薄列表
	 * @return
	 * @throws Exception
	 */
	public String regList() throws Exception {
		this.statusList = MerchState.getWithCheck();
		this.yesOrNoFlagList = YesOrNoFlag.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		boolean isUserOfLimitedTransQuery = isUserOfLimitedTransQuery();
		if (isUserOfLimitedTransQuery){
			params.put("isUserOfLimitedTransQuery", isUserOfLimitedTransQuery);
			params.put("limitedExcludeManageBranchCodes", UserOfLimitedTransQueryUtil.getExcludeManageBranchCodes());
		}
		
		if (merchInfoReg != null) {
			params.put("merchId", merchInfoReg.getMerchId());
			params.put("merchName", MatchMode.ANYWHERE.toMatchString(merchInfoReg.getMerchName()));
			params.put("cardBranchCode", cardBranchCode);// 发卡机构-商户关系
			params.put("status", merchInfoReg.getStatus());
			params.put("singleProduct", merchInfoReg.getSingleProduct());
			
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
		}
		
		showCardBranch = false; // 默认不显示发卡机构查询条件
		
		if (isCenterOrCenterDeptRoleLogined()){// 运营中心
			showCardBranch = true;
		}else if (isFenzhiRoleLogined()) {// 分支机构
			params.put("manageBranch", this.getLoginBranchCode());
			showCardBranch = true;
		}else if (isAgentRoleLogined()) {// 运营代理商
			params.put("agentBranchCode", getLoginBranchCode());
		} else if (isCardRoleLogined() || isCardDeptRoleLogined() ) {// 发卡机构或其部门则返回其的特约商户列表
			this.cardBranchCode = this.getLoginBranchCode();
			params.put("cardBranchCode", cardBranchCode);
			this.setCardBranchName(NameTag.getBranchName(cardBranchCode));
		} else if ( isMerchantRoleLogined() ) {// 商户
			params.put("merchId", this.getSessionUser().getMerchantNo());
		} else {
			throw new BizException("没有权限查看商户信息");
		}
		this.page = this.merchInfoRegDAO.find(params, this.getPageNumber(), this.getPageSize());
		
		return "regList";
	}

	/** 
	 * 商户信息明细页面
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		this.merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(this.merchInfo.getMerchId());
		Assert.notNull(merchInfo, "商户[" + merchInfo.getMerchId() + "]不存在");
		
		MerchType merchType = (MerchType) this.merchTypeDAO.findByPk(merchInfo.getMerchType());
		if(merchType != null){
			this.setMerchTypeName(merchType.getTypeName());
		}
		
		Area area = (Area) this.areaDAO.findByPk(this.merchInfo.getAreaCode());
		if(area!=null){
			this.setAreaName(area.getAreaName());
		}
		
		Area accArea = (Area) this.areaDAO.findByPk(this.merchInfo.getAccAreaCode());
		if (accArea != null) {
			this.setAccAreaName(accArea.getAreaName());
		}
		
		return DETAIL;
	}
	
	/**
	 * 商户登记明细
	 * @return
	 * @throws Exception
	 */
	public String regDetail() throws Exception {
		this.merchInfoReg = (MerchInfoReg) this.merchInfoRegDAO.findByPk(this.merchInfoReg.getId());
		Assert.notNull(merchInfoReg, "商户登记[" + merchInfoReg.getId() + "]信息不存在");
		
		MerchType merchType = (MerchType) this.merchTypeDAO.findByPk(merchInfoReg.getMerchType());
		if(merchType!=null){
			this.setMerchTypeName(merchType.getTypeName());
		}
		
		Area area = (Area) this.areaDAO.findByPk(this.merchInfoReg.getAreaCode());
		if(area!=null){
			this.setAreaName(area.getAreaName());
		}
		
		Area accArea = (Area) this.areaDAO.findByPk(this.merchInfoReg.getAccAreaCode());
		if (accArea != null) {
			this.setAccAreaName(accArea.getAreaName());
		}
		
		return "regDetail";
	}

	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		hasRightTodo();
		initPage();
		this.merchInfoReg = new MerchInfoReg();
		merchInfoReg.setOpenFlag(OpenFlag.OPEN.getValue());
		merchInfoReg.setUsePwdFlag(UsePwdFlag.BRANCH.getValue());
		merchInfoReg.setAcctType(AcctType.COMPANY.getValue());
		merchInfoReg.setSingleProduct(Symbol.NO);// 默认不是单机产品商户
		
		//如果是发卡机构添加商户，则自动为其与该商户建立特约关系
		if ( isCardOrCardDeptRoleLogined() ) {
			this.branchCode = this.getLoginBranchCode();
		}
		
		// 发卡机构或运营代理添加商户时，其管理机构为登录机构的管理机构
		this.showModifyManage = showModifyManageBranch();  
		if (showModifyManage) {
			BranchInfo loginBranch = this.branchInfoDAO.findBranchInfo(this.getLoginBranchCode());
			BranchInfo manageBranch = this.branchInfoDAO.findBranchInfo(loginBranch.getParent());
			this.setManageBranch(manageBranch.getBranchName());
			merchInfoReg.setManageBranch(loginBranch.getParent());
		}
		
		if (StringUtils.isNotEmpty(branchCode)) {
			BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(branchCode);
			BranchInfo manageBranchInfo = (BranchInfo) this.branchInfoDAO.findByPk(branchInfo.getParent());
			
			this.setManageBranch(manageBranchInfo.getBranchName());
			
			merchInfoReg.setCardBranch(branchInfo.getBranchCode());// 直属发卡机构
			this.setCardBranchName(branchInfo.getBranchName());
			merchInfoReg.setSingleProduct(branchInfo.getSingleProduct());
			
			merchInfoReg.setMerchName(branchInfo.getBranchName());
			merchInfoReg.setMerchAbb(branchInfo.getBranchAbbname());
			merchInfoReg.setBankName(branchInfo.getBankName());
			merchInfoReg.setBankNo(branchInfo.getBankNo());
			merchInfoReg.setAccName(branchInfo.getAccName());
			merchInfoReg.setAccNo(branchInfo.getAccNo());
			merchInfoReg.setCurrCode(branchInfo.getCurCode());
			merchInfoReg.setEmail(branchInfo.getEmail());
			merchInfoReg.setFaxNo(branchInfo.getFax());
			merchInfoReg.setLinkMan(branchInfo.getContact());
			merchInfoReg.setManageBranch(branchInfo.getParent());
			merchInfoReg.setMerchAddress(branchInfo.getAddress());
			merchInfoReg.setTelNo(branchInfo.getPhone());
			merchInfoReg.setAcctType(branchInfo.getAcctType());
			
			merchInfoReg.setLegalPersonIdcard(branchInfo.getLegalPersonIdcard());
			merchInfoReg.setLegalPersonIdcardExpDate(branchInfo.getLicenseExpDate());
			merchInfoReg.setLegalPersonName(branchInfo.getLegalPersonName());
			merchInfoReg.setTaxRegCode(branchInfo.getTaxRegCode());
			merchInfoReg.setMerchCode(branchInfo.getLicense());
			merchInfoReg.setLicenseExpDate(branchInfo.getLicenseExpDate());
			merchInfoReg.setOrganization(branchInfo.getOrganization());
			merchInfoReg.setOrganizationExpireDate(branchInfo.getOrganizationExpireDate());
			
			// 设置地区
			merchInfoReg.setAreaCode(branchInfo.getAreaCode());
			Area area = (Area) this.areaDAO.findByPk(branchInfo.getAreaCode());
			if (area != null) {
				this.setAreaName(area.getAreaName());
			}
			
			merchInfoReg.setAccAreaCode(branchInfo.getAccAreaCode());
			Area accArea = (Area) this.areaDAO.findByPk(branchInfo.getAccAreaCode());
			if (accArea != null) {
				this.setAccAreaName(accArea.getAreaName());
			}
			// 设置用户名
			this.setMerchAdmin(StringUtils.upperCase(Cn2PinYinHelper.cn2FirstSpell(merchInfoReg.getMerchAbb())) + "SHAdmin");
		}
		
		return ADD;
	}
	
	public void merchType() throws Exception{
		String keyWord = request.getParameter("q");
		if (StringUtils.isEmpty(keyWord)) {
			return;
		}
		keyWord = keyWord.split("\\|")[0];
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyWord", MatchMode.ANYWHERE.toMatchString(keyWord));
		params.put("status", MerchTypeState.NORMAL.getValue());
		
		this.merchTypeList = this.merchTypeDAO.findList(params);
		JSONArray json = new JSONArray();
		for (MerchType type : merchTypeList) {
			JSONObject object = new JSONObject();
			object.put("merchType", type.getMerchType());
			object.put("typeName", type.getTypeName());
			json.add(object);
		}
		respond(json.toString());
	}

	private void initPage() {
//		this.manageBranchList = this.branchInfoDAO.findByTypes(BranchType.getManageBranch());
//		this.developBranchList = this.branchInfoDAO.findByTypes(BranchType.getDevelopMerch());
//		this.merchTypeList = this.merchTypeDAO.findByStatus(MerchTypeState.NORMAL.getValue());
		this.merchTypeList = new ArrayList<MerchType>();

		this.currCodeList = currCodeDAO.findCurrCode(CurrCodeState.NORMAL.getValue());

		this.setCycleList = SetCycleFlag.getAll();
		this.openFlagList = OpenFlag.getForCRUD();
		this.merchLevelList = MerchLevel.getAll();
		this.usePwdFlagList = UsePwdFlag.getAll();
		this.isNettingList = TrueOrFalseFlag.getAll();
		
		this.acctTypeList = AcctType.getAll();
		
		this.yesOrNoFlagList = YesOrNoFlag.getAll();
		this.acctMediaTypeList = AcctMediaType.getAll();
		
		this.riskLevelTypeList = RiskLevelType.getAll();
	}

	// 新增对象操作
	public String add() throws Exception {
		String type = merchInfoReg.getMerchType();
		Assert.notEmpty(type, "商户类型不能为空");
		this.merchInfoReg.setMerchType(StringUtils.substring(type, 0, StringUtils.indexOf(type, "|")));
//		
//		// 调用service业务接口;现在默认都是要创建系统管理员的
//		UserInfo admin = new UserInfo();
//		admin.setUserId(this.merchAdmin);
//		this.merchService.addMerch(this.merchInfo, admin, branchCode, this.getSessionUserCode());
//		
//		if (addAdmin == 1) {
//			this.merchService.addMerch(this.merchInfo, admin, branchCode, this.getSessionUserCode());
//		} else {
//			this.merchService.addMerch(this.merchInfo, null, branchCode, this.getSessionUserCode());
//		}
		
		this.merchInfoReg.setAdminId(this.merchAdmin);
		
		this.merchService.addMerch(this.merchInfoReg, this.getSessionUser());

		String msg = LogUtils.r("登记商户id为[{0}]的商户信息成功", this.merchInfoReg.getId());
		this.addActionMessage("/pages/merch/regList.do?goBack=goBack", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	public void ajaxFindCardBranchInfos() throws Exception {
		String cardBranchCode = this.getFormMapValue("cardBranchCode");
		
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(cardBranchCode);
		if(branchInfo==null){
			this.responseJsonObject("-1", "没有找到相应的发卡机构");
		}else{
			JSONObject json = new JSONObject();
			json.put("license", branchInfo.getLicense());
			json.put("licenseExpDate", gnete.util.DateUtil.formatDate("yyyy-MM-dd", branchInfo.getLicenseExpDate()));
			json.put("organization", branchInfo.getOrganization());
			json.put("organizationExpireDate", branchInfo.getOrganizationExpireDate());
			json.put("legalPersonName", branchInfo.getLegalPersonName());
			json.put("legalPersonIdcard", branchInfo.getLegalPersonIdcard());
			json.put("legalPersonIdcardExpDate", gnete.util.DateUtil.formatDate("yyyy-MM-dd",branchInfo.getLegalPersonIdcardExpDate()));
			json.put("taxRegCode", branchInfo.getTaxRegCode());
			this.responseJsonObject(json, "1", "查询成功");
		}
	}
	
	// 打开新增页面的初始化操作
	public String showAddOldMerch() throws Exception {
		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined())) {
			throw new BizException("没有权限维护旧商户信息");
		}
		initPage();
		this.merchInfoReg = new MerchInfoReg();
		merchInfoReg.setOpenFlag(OpenFlag.OPEN.getValue());
		merchInfoReg.setUsePwdFlag(UsePwdFlag.NO.getValue());
		
		return "addOldMerch";
	}
	
	// 新增对象操作
	public String addOldMerch() throws Exception {
		String type = merchInfoReg.getMerchType();
		Assert.notEmpty(type, "商户类型不能为空");
//		this.merchInfoReg.setMerchType(type.substring(0, type.indexOf("|")));
		this.merchInfoReg.setMerchType(StringUtils.substring(type, 0, StringUtils.indexOf(type, "|")));
		
//		if (addAdmin == 1) {
//			UserInfo admin = new UserInfo();
//			admin.setUserId(this.merchAdmin);
//			this.merchService.addOldMerch(this.merchInfo, admin, branchCode, this
//					.getSessionUserCode());
//		} else {
//			this.merchService.addOldMerch(this.merchInfo, null, branchCode, this
//					.getSessionUserCode());
//		}

		this.merchInfoReg.setAdminId(this.merchAdmin);
		
		this.merchService.addOldMerch(this.merchInfoReg, this.getSessionUser());

		String msg = LogUtils.r("登记旧系统商户[{0}]成功", this.merchInfoReg.getMerchId());
		this.addActionMessage("/pages/merch/regList.do?goBack=goBack", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}

	/**
	 * 通过文件方式新增商户
	 * @return
	 * @throws Exception
	 */
	public String showAddMerchFile() throws Exception {
		hasRightTodo();
		
		this.currCodeList = currCodeDAO.findCurrCode(CurrCodeState.NORMAL.getValue());
		this.openFlagList = OpenFlag.getForCRUD();

		this.merchInfoReg = new MerchInfoReg();
		this.merchInfoReg.setOpenFlag(OpenFlag.OPEN.getValue());
		this.merchInfoReg.setCurrCode("CNY");

		//如果是发卡机构添加商户，则自动为其与该商户建立特约关系
		if (isCardOrCardDeptRoleLogined()) {
			this.setBranchCode(this.getLoginBranchCode());
			this.merchInfoReg.setCardBranch(this.getLoginBranchCode());
		}
		
		// 发卡机构或运营代理添加商户时，其管理机构为登录机构的管理机构
		this.showModifyManage = showModifyManageBranch();  
		if (showModifyManage) {
			BranchInfo loginBranch = this.branchInfoDAO.findBranchInfo(this.getLoginBranchCode());
			BranchInfo manageBranch = this.branchInfoDAO.findBranchInfo(loginBranch.getParent());
			this.setManageBranch(manageBranch.getBranchName());
			merchInfoReg.setManageBranch(loginBranch.getParent());
		}
		
		return "addMerchFile";
	}
	
	public String addMerchFile() throws Exception {
		
		Assert.isTrue(IOUtil.testFileFix(uploadFileName, Arrays.asList("txt","csv")), "商户文件只能是文本格式的文件");
		
		String[] regs = this.merchService.addMerchFile(upload, merchInfoReg, this.getSessionUser());
		
		String msg = "登记商户[" + ObjectUtils.nullSafeToString(regs) + "]成功";
		
		this.addActionMessage("/pages/merch/regList.do?goBack=goBack", msg);
		this.log(msg, UserLogType.ADD);
		
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined())) {
			throw new BizException("没有权限维护商户信息");
		}
		Assert.notEmpty(merchInfo.getMerchId(), "商户编号不能为空");
		this.merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(this.merchInfo.getMerchId());
		this.setManageBranch(NameTag.getBranchName(merchInfo.getManageBranch()));
		//this.setDevelopBranch(getBranchName(merchInfo.getBranchCode()));
		this.setParent(getMerchName(merchInfo.getParent()));
		
		this.setCardBranchName(NameTag.getBranchName(merchInfo.getCardBranch()));
		
		MerchType merchType = (MerchType) this.merchTypeDAO.findByPk(merchInfo.getMerchType());
		if(merchType != null){
			this.setMerchTypeName(merchType.getTypeName());
		}
		
		Area area = (Area) this.areaDAO.findByPk(this.merchInfo.getAreaCode());
		if (area != null) {
			this.setAreaName(area.getAreaName());
		}
		Area accArea = (Area) this.areaDAO.findByPk(this.merchInfo.getAccAreaCode());
		if (accArea != null) {
			this.setAccAreaName(accArea.getAreaName());
		}

		initPage();
		return MODIFY;
	}
	
	private boolean showModifyManageBranch() {
		boolean flag = false;
		if (isCardOrCardDeptRoleLogined() || isAgentRoleLogined()){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 是否有权限进行商户新增，修改等操作
	 * @throws BizException
	 */
	private void hasRightTodo() throws BizException {
		boolean isAllow = isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined()
				|| isCardOrCardDeptRoleLogined() || isAgentRoleLogined();
		Assert.isTrue(isAllow, "没有权限维护商户信息");
	}

	private String getMerchName(String merchId) {
		if (StringUtils.isEmpty(merchId)) {
			return "";
		}
		MerchInfo merch = (MerchInfo) this.merchInfoDAO.findByPk(merchId);
		if (merch == null) {
			return "";
		}
		return merch.getMerchName();
	}

	public String modify() throws Exception {
		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined() || isAgentRoleLogined())) {
			throw new BizException("没有权限维护商户信息");
		}
		
		this.merchService.modifyMerch(this.merchInfo, this.getSessionUserCode());

		String msg = LogUtils.r("修改商户[{0}]成功", this.merchInfo.getMerchId());
		this.addActionMessage("/pages/merch/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	/**
	 * 修改商户登记簿的信息
	 * @return
	 * @throws Exception
	 */
	public String showModifyReg() throws Exception {
		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined() || isAgentRoleLogined())) {
			throw new BizException("没有权限维护商户信息");
		}
		Assert.notEmpty(merchInfoReg.getId(), "商户登记编号不能为空");
		this.merchInfoReg = (MerchInfoReg) this.merchInfoRegDAO.findByPk(this.merchInfoReg.getId());
		
		this.setManageBranch(NameTag.getBranchName(merchInfoReg.getManageBranch()));
		this.setParent(NameTag.getMerchName(merchInfoReg.getParent()));
		
		this.setCardBranchName(NameTag.getBranchName(merchInfoReg.getCardBranch()));
		
		MerchType merchType = (MerchType) this.merchTypeDAO.findByPk(merchInfoReg.getMerchType());
		if(merchType != null){
			this.setMerchTypeName(merchType.getTypeName());
		}
		
		Area area = (Area) this.areaDAO.findByPk(this.merchInfoReg.getAreaCode());
		if (area != null) {
			this.setAreaName(area.getAreaName());
		}
		
		Area accArea = (Area) this.areaDAO.findByPk(this.merchInfoReg.getAccAreaCode());
		if (accArea != null) {
			this.setAccAreaName(accArea.getAreaName());
		}

		initPage();
		
		return "modifyReg";
	}
	
	/**
	 * 修改商户登记簿(只有待审核状态的才可以修改)
	 * @return
	 * @throws Exception
	 */
	public String modifyReg() throws Exception {
		
		this.merchService.modifyMerchReg(merchInfoReg, this.getSessionUser());
		
		String msg = LogUtils.r("修改商户登记信息[{0}]成功", this.merchInfoReg.getId());
		this.addActionMessage("/pages/merch/regList.do?goBack=goBack", msg);
		this.log(msg, UserLogType.UPDATE);
		
		return SUCCESS;
	}

	/**
	 * 注销商户
	 * @return
	 * @throws Exception
	 */
	public String cancel() throws Exception {
		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined())){
			throw new BizException("没有权限维护商户信息");
		}
		String merchId = request.getParameter("merchId");
		this.merchService.cancelMerch(merchId, this.getSessionUserCode());

		String msg = LogUtils.r("注销商户[{0}]成功", merchId);
		this.addActionMessage("/pages/merch/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}

	/**
	 * 生效商户
	 * @return
	 * @throws Exception
	 */
	public String activate() throws Exception {
		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined())){
			throw new BizException("没有权限维护商户信息");
		}
		String merchId = request.getParameter("merchId");
		this.merchService.activeMerch(merchId, this.getSessionUserCode());

		String msg = LogUtils.r("生效商户[{0}]成功", merchId);
		this.addActionMessage("/pages/merch/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	/**
	 * 提交审核，提交通过后台程序导入的旧商户
	 * @return
	 * @throws Exception
	 */
	public String submitCheck() throws Exception{
		if (!isCenterOrCenterDeptRoleLogined()) {
			throw new BizException("没有权限提交审核");
		}
		
		this.merchService.submitCheck(this.getSessionUser());
		
		String msg = LogUtils.r("将所有“待提交”的商户审核成功!");
		this.addActionMessage("/pages/merch/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	/**
	 * 删除审核不通过的商户信息
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		hasRightTodo();
		
		String id = request.getParameter("merchId");
		
		this.merchService.deleteMerch(id);
		String msg = LogUtils.r("删除商户登记簿ID为[{0}]的商户信息成功", id);
		this.addActionMessage("/pages/merch/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.DELETE);
		
		return SUCCESS;
	}
	
	public String showSelect() throws Exception {
		this.currCodeList = currCodeDAO.findCurrCode(CurrCodeState.NORMAL.getValue());
		return "select";
	}

	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (merchInfo != null) {
			params.put("merchId", merchInfo.getMerchId());
			params.put("merchName", MatchMode.ANYWHERE.toMatchString(merchInfo.getMerchName()));
			params.put("status", MerchState.NORMAL.getValue());
			params.put("card_BranchNo", card_BranchNo);
			params.put("proxyId", proxyId);
			params.put("_currCode_Sel", _currCode_Sel);
			params.put("group_CardBranch", group_CardBranch);
			params.put("manageBranch", _manageBranch);
			params.put("cardBranchNotExsit", cardBranch);
			params.put("_cardBranchNotLimit", _cardBranchNotLimit);
		}
		this.page = this.merchInfoDAO.find(params, this.getPageNumber(), Constants.DEFAULT_SELECT_PAGE_SIZE);
		return "data";
	}
	
	public String checkList() throws Exception {
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String ids[] = workflowService.getMyJob(WorkflowConstants.WORKFLOW_ADD_MERCH, getSessionUser());
		if (ArrayUtils.isEmpty(ids)) {
			this.page = new Paginater(this.getPageSize(),this.getPageNumber());
			return CHECK_LIST;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		this.page = this.merchInfoRegDAO.find(params, this.getPageNumber(), this.getPageSize());

		return CHECK_LIST;
	}
	
	/**
	 * 发卡机构机构或运营代理添加的商户，在这里进行审批
	 * @return
	 * @throws Exception
	 */
	public String fenzhiCheckList() throws Exception {
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String ids[] = workflowService.getMyJob(WorkflowConstants.WORKFLOW_CARD_ADD_MERCH, getSessionUser());
		if (ArrayUtils.isEmpty(ids)) {
			this.page = new Paginater(this.getPageSize(),this.getPageNumber());
			return "fenzhiCheckList";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		this.page = this.merchInfoRegDAO.find(params, this.getPageNumber(), this.getPageSize());
		
		return "fenzhiCheckList";
	}
	
	public String fenzhiCheckDetail() throws Exception {
		this.merchInfoReg = (MerchInfoReg) this.merchInfoRegDAO.findByPk(this.merchInfoReg.getId());
		MerchType merchType = (MerchType) this.merchTypeDAO.findByPk(merchInfoReg.getMerchType());
		if(merchType!=null){
			this.setMerchTypeName(merchType.getTypeName());
		}
		
		Area area = (Area) this.areaDAO.findByPk(this.merchInfoReg.getAreaCode());
		if(area!=null){
			this.setAreaName(area.getAreaName());
		}
		
		return "fenzhiCheckDetail";
	}

	public MerchInfo getMerchInfo() {
		return merchInfo;
	}

	public void setMerchInfo(MerchInfo merchInfo) {
		this.merchInfo = merchInfo;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
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

	public List<MerchType> getMerchTypeList() {
		return merchTypeList;
	}

	public void setMerchTypeList(List<MerchType> merchTypeList) {
		this.merchTypeList = merchTypeList;
	}

	public List getSetCycleList() {
		return setCycleList;
	}

	public void setSetCycleList(List setCycleList) {
		this.setCycleList = setCycleList;
	}

	public List getOpenFlagList() {
		return openFlagList;
	}

	public void setOpenFlagList(List openFlagList) {
		this.openFlagList = openFlagList;
	}

	public List getMerchLevelList() {
		return merchLevelList;
	}

	public void setMerchLevelList(List merchLevelList) {
		this.merchLevelList = merchLevelList;
	}

	public List getUsePwdFlagList() {
		return usePwdFlagList;
	}

	public void setUsePwdFlagList(List usePwdFlagList) {
		this.usePwdFlagList = usePwdFlagList;
	}

	public List<CurrCode> getCurrCodeList() {
		return currCodeList;
	}

	public void setCurrCodeList(List<CurrCode> currCodeList) {
		this.currCodeList = currCodeList;
	}

	public String getDevelopBranch() {
		return developBranch;
	}

	public void setDevelopBranch(String developBranch) {
		this.developBranch = developBranch;
	}

	public String getManageBranch() {
		return manageBranch;
	}

	public void setManageBranch(String manageBranch) {
		this.manageBranch = manageBranch;
	}

	public String getManageBranchName() {
		return manageBranchName;
	}

	public void setManageBranchName(String manageBranchName) {
		this.manageBranchName = manageBranchName;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public boolean isRadio() {
		return radio;
	}

	public void setRadio(boolean radio) {
		this.radio = radio;
	}

	public String getCard_BranchNo() {
		return card_BranchNo;
	}

	public void setCard_BranchNo(String card_BranchNo) {
		this.card_BranchNo = card_BranchNo;
	}

	public int getAddAdmin() {
		return addAdmin;
	}

	public void setAddAdmin(int addAdmin) {
		this.addAdmin = addAdmin;
	}

	public String getMerchAdmin() {
		return merchAdmin;
	}

	public void setMerchAdmin(String merchAdmin) {
		this.merchAdmin = merchAdmin;
	}

	public String getProxyId() {
		return proxyId;
	}

	public void setProxyId(String proxyId) {
		this.proxyId = proxyId;
	}

	public String get_currCode_Sel() {
		return _currCode_Sel;
	}

	public void set_currCode_Sel(String code_Sel) {
		_currCode_Sel = code_Sel;
	}

	public String getGroup_CardBranch() {
		return group_CardBranch;
	}

	public void setGroup_CardBranch(String group_CardBranch) {
		this.group_CardBranch = group_CardBranch;
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

	public String getMerchTypeName() {
		return merchTypeName;
	}

	public void setMerchTypeName(String merchTypeName) {
		this.merchTypeName = merchTypeName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String get_manageBranch() {
		return _manageBranch;
	}

	public void set_manageBranch(String branch) {
		_manageBranch = branch;
	}

	public String getAccAreaName() {
		return accAreaName;
	}

	public void setAccAreaName(String accAreaName) {
		this.accAreaName = accAreaName;
	}

	public String getCardBranchCode() {
		return cardBranchCode;
	}

	public void setCardBranchCode(String cardBranchCode) {
		this.cardBranchCode = cardBranchCode;
	}

	public String getCardBranchName() {
		return cardBranchName;
	}

	public void setCardBranchName(String cardBranchName) {
		this.cardBranchName = cardBranchName;
	}

	public boolean isShowCardBranch() {
		return showCardBranch;
	}

	public void setShowCardBranch(boolean showCardBranch) {
		this.showCardBranch = showCardBranch;
	}

	public List<TrueOrFalseFlag> getIsNettingList() {
		return isNettingList;
	}

	public void setIsNettingList(List<TrueOrFalseFlag> isNettingList) {
		this.isNettingList = isNettingList;
	}

	public List<MerchState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<MerchState> statusList) {
		this.statusList = statusList;
	}

	public MerchInfoReg getMerchInfoReg() {
		return merchInfoReg;
	}

	public void setMerchInfoReg(MerchInfoReg merchInfoReg) {
		this.merchInfoReg = merchInfoReg;
	}

	public boolean isShowModifyManage() {
		return showModifyManage;
	}

	public void setShowModifyManage(boolean showModifyManage) {
		this.showModifyManage = showModifyManage;
	}

	public List<AcctType> getAcctTypeList() {
		return acctTypeList;
	}

	public void setAcctTypeList(List<AcctType> acctTypeList) {
		this.acctTypeList = acctTypeList;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public List<AcctMediaType> getAcctMediaTypeList() {
		return acctMediaTypeList;
	}

	public List<YesOrNoFlag> getYesOrNoFlagList() {
		return yesOrNoFlagList;
	}

	public void setYesOrNoFlagList(List<YesOrNoFlag> yesOrNoFlagList) {
		this.yesOrNoFlagList = yesOrNoFlagList;
	}

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String get_cardBranchNotLimit() {
		return _cardBranchNotLimit;
	}

	public void set_cardBranchNotLimit(String branchNotLimit) {
		_cardBranchNotLimit = branchNotLimit;
	}
	
	public List<RiskLevelType> getRiskLevelTypeList() {
		return riskLevelTypeList;
	}
	
	public boolean isShowCenter() {
		return showCenter;
	}

	public void setShowCenter(boolean showCenter) {
		this.showCenter = showCenter;
	}
}
