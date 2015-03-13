package gnete.card.web;

import flink.util.Cn2PinYinHelper;
import flink.util.NameValuePair;
import flink.util.SpringContext;
import flink.util.WebResource;
import gnete.card.dao.BranchHasTypeDAO;
import gnete.card.dao.BranchProxyDAO;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.SaleProxyPrivilegeDAO;
import gnete.card.entity.BranchHasType;
import gnete.card.entity.BranchHasTypeKey;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BranchProxyKey;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.DepartmentInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.PrivilegeResource;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.UserLog;
import gnete.card.entity.type.BranchLevelType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BaseDataService;
import gnete.card.service.LogService;
import gnete.card.service.UserService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.util.WebUtil;
import gnete.card.workflow.entity.WorkflowHis;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.Symbol;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import com.opensymphony.xwork2.ActionSupport;


public abstract class BaseAction extends ActionSupport implements SessionAware,ServletRequestAware,ServletResponseAware {
	@Autowired
	protected UserService userService;
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;
	
	/** 
	 * 简单保存来自页面的简单查询参数； 
	 */
	protected Map<String, String> formMap = new HashMap<String, String>();
	
	protected final String SUCCESS = ActionSupport.SUCCESS;
	protected final String ERROR = ActionSupport.ERROR;
	protected final String LOGIN = ActionSupport.LOGIN;
	protected final String INPUT = ActionSupport.INPUT;
	//错误信息提示
	private String error;
	//消息提示
	private String message;
	// 处理成功URL
	private String successUrl;
	
	protected final String LIST = "list";
	protected final String DETAIL = "detail";
	protected final String ADD = "add";
	protected final String MODIFY = "modify";
	protected final String CHECK_LIST = "checkList";
	
	private int pageNumber;
	private int pageSize;
	
	@Autowired
	protected WorkflowService workflowService;
	private List<WorkflowHis> flowList;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private BaseDataService baseDataService;
	protected static final String UTF_TEXT_HTML = "text/html;charset=UTF-8";
	
	protected static final Logger logger = LoggerFactory.getLogger(BaseAction.class);
	
	/** 默认方法显示列表页面 */
	public abstract String execute() throws Exception;
	
	/** 列表页 （默认调用execute方法） */
	public String list() throws Exception {
		return execute();
	}
	
	/** 显示 新增页 前的页面，通常是做一些前置条件选择（比如在preShowAdd页面里选择发卡机构） 
	 * @throws BizException
	 */
	public String preShowAdd() throws BizException{
		return "preShowAdd";
	}	
	
	/** 页面formMap参数的值是否为true，或checkbox是否被选中 */
	protected boolean isFormMapFieldTrue(String key) {
		return getFormMapValue(key) != null && getFormMapValue(key).trim().equalsIgnoreCase("true");
	}

	/** 根据页面formMap参数的key，获得value */
	protected String getFormMapValue(String key) {
		if (formMap == null || formMap.get(key) == null) {
			return null;
		} else {
			return formMap.get(key);
		}
	}
	
	/**
	 * 当前会话用户对象
	 * @param request
	 * @return
	 */
	protected UserInfo getSessionUser() {
		return (UserInfo) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
	}
	
	/**
	 * 当前会话用户ID
	 * @param request
	 * @return
	 */
	protected String getSessionUserCode() {
		UserInfo user = this.getSessionUser();
		return user == null ? null : user.getUserId();
	}
	
	/**
	 * 当前登录用户的 机构编号 或 商户编号
	 * @return
	 */
	public String getLoginBranchCode() {
		
		if (isMerchantRoleLogined()) {
			return getSessionUser().getRole().getMerchantNo();
		}
		return getSessionUser().getRole().getBranchNo();
	}
	
	/**
	 * 当前登录用户的角色信息
	 * @return
	 */
	protected RoleInfo getLoginRoleInfo(){
		return getSessionUser().getRole();
	}
	
	/**
	 * 当前登录用户的角色类型编号
	 * @deprecated 请使用getLoginRoleTypeCode()
	 * @see #getLoginRoleTypeCode()
	 * @return
	 */
	public String getLoginRoleType() {
		return getLoginRoleTypeCode();
	}
	
	/**
	 * 当前登录用户的角色类型编号
	 * @return
	 */
	public String getLoginRoleTypeCode() {
		return getLoginRoleInfo().getRoleType();
	}
	
	/* ************************************************************************
	 *  	判断登录用户角色
	 * ***********************************************************************/
	
	/** 运营中心角色登录 */
	public boolean isCenterRoleLogined() {
		return RoleType.CENTER.getValue().equals(this.getLoginRoleTypeCode());
	}
	/** 运营中心部门 角色登录*/
	public boolean isCenterDeptRoleLogined() {
		return RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleTypeCode());
	}
	/** 
	 * 运营中心 或 运营中心部门 角色登录
	 */
	public boolean isCenterOrCenterDeptRoleLogined() {
		return isCenterRoleLogined() || isCenterDeptRoleLogined();
	}

	/** 运营分支机构角色登录 */
	public boolean isFenzhiRoleLogined() {
		return RoleType.FENZHI.getValue().equals(this.getLoginRoleTypeCode());
	}
	/** 运营代理商角色登录 */
	public boolean isAgentRoleLogined() {
		return RoleType.AGENT.getValue().equals(this.getLoginRoleTypeCode());
	}

	/** 发卡机构角色登录 */
	public boolean isCardRoleLogined() {
		return RoleType.CARD.getValue().equals(this.getLoginRoleTypeCode());
	}
	/** 发卡机构网点角色登录 */
	public boolean isCardDeptRoleLogined() {
		return RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleTypeCode());
	}
	/** 发卡机构 或 发卡机构网点 角色登录 */
	public boolean isCardOrCardDeptRoleLogined() {
		return isCardRoleLogined() || isCardDeptRoleLogined();
	}
	/** 发卡机构售卡代理 角色登录 */
	public boolean isCardSellRoleLogined() {
		return RoleType.CARD_SELL.getValue().equals(this.getLoginRoleTypeCode());
	}
	/** 发卡机构商户代理 角色登录 */
	public boolean isCardMerchantRoleLogined() {
		return RoleType.CARD_MERCHANT.getValue().equals(this.getLoginRoleTypeCode());
	}
	
	/** 制卡厂商 角色登录 */
	public boolean isCardMakeRoleLogined() {
		return RoleType.CARD_MAKE.getValue().equals(this.getLoginRoleTypeCode());
	}
	/** 机具出机方角色登录 */
	public boolean isTerminalRoleLogined() {
		return RoleType.TERMINAL.getValue().equals(this.getLoginRoleTypeCode());
	}
	/** 机具维护方角色登录 */
	public boolean isTerminalMaintainRoleLogined() {
		return RoleType.TERMINAL_MAINTAIN.getValue().equals(this.getLoginRoleTypeCode());
	}
	
	/** 商户角色登录 */
	public boolean isMerchantRoleLogined() {
		return RoleType.MERCHANT.getValue().equals(this.getLoginRoleTypeCode());
	}
	
	/** 个人角色登录 */
	public boolean isPersonalRoleLogined() {
		return RoleType.PERSONAL.getValue().equals(this.getLoginRoleTypeCode());
	}
	
	// TODO 验签相关--------------------------
	
	/**
	 *检查登录用户必须是 生效的 绑定证书用户；（注意：前提是 系统参数设置了“登记薄需要验证签名”）
	 */
	public void checkEffectiveCertUser() throws BizException {
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(
					this.getSessionUser().isCertUser()
							&& this.userService.checkEffectiveCertUser(this.getSessionUserCode()),
					"必须是生效的绑定证书登录用户才能做此操作！");
		}
	}
	
	/**
	 * 为formMap.needSignatureReg赋值（注意：前提是 系统参数设置了“登记薄需要验证签名”）
	 */
	public void dealIsNeedSign() {
		formMap.put("needSignatureReg", String.valueOf(Symbol.YES.equals(SysparamCache.getInstance()
				.getSignatureReg())));
	}

	/**
	 * 验证用户证书序列号是否正确（从formMap.serialNo取页面传回证书序号）。（注意：前提是 系统参数设置了“登记薄需要验证签名”）
	 */
	public void checkUserSignatureSerialNo() throws BizException {
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			String serialNo = this.getFormMapValue("serialNo");
			Assert.isTrue(
					this.getSessionUser().isCertUser()
							&& this.userService.checkCertUser(serialNo, this.getSessionUser()),
					"请确保证书绑定的用户与当前登录用户一致");
		}
	}
	
	// TODO 以下 JSON数据应答处理 ---------------------------------------------------
	/** 
	 * 输出指定JSON格式数据，如{returnCode:"1", msg:"成功"} 
	 */
	protected void responseJsonObject(String returnCode, String msg) {
		JSONObject retJson = new JSONObject();
		retJson.put("returnCode", returnCode);
		retJson.put("msg", msg);
		this.responseJsonObject(retJson);
	}
	
	/** 
	 * 在原retJson对像的基础上，增加returnCode与msg 属性,<br/>
	 * 然后输出。
	 */
	protected void responseJsonObject(JSONObject retJson, String returnCode, String msg) {
		retJson.put("returnCode", returnCode);
		retJson.put("msg", msg);
		this.responseJsonObject(retJson);
	}
	
	/** 
	 * 输出JSON格式数据
	 */
	protected void responseJsonObject(JSONObject json) {
		try {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(json);
			IOUtils.closeQuietly(response.getWriter());
		} catch (IOException e) {
			logger.warn("", e);
		}
	}
	
	/**
	 * response输出消息
	 * @deprecated 已废弃，以后的版本不要再使用该方法
	 */
	protected void respond(String msg) {
		try {
			response.setContentType(UTF_TEXT_HTML);
			response.getWriter().println(msg == null ? "" : msg);
			IOUtils.closeQuietly(response.getWriter());
		} catch (IOException e) {
			logger.warn("",e);
		} 
	}
	// END JSON数据应答处理 ---------------------------------------------------
	
	/**
	 * 得到我可管理的机构
	 * @return
	 */
	protected List<BranchInfo> getMyBranch(){
		return this.baseDataService.getMyBranch(this.getSessionUser());
	}
	
	/**
	 * 得到我可管理的下级分支机构及下下级分支机构
	 * @return
	 */
	public List<BranchInfo> getMyManageFenzhi() {
		return this.baseDataService.getMyManageFenzhi(this.getSessionUser());
	}
	
	/**
	 * 得到我可管理的发卡机构<br/>
	 * 用途：（1）子类Action中使用；（2）jsp页面中作为Action的普通getter方法使用
	 * @return
	 */
	public List<BranchInfo> getMyCardBranch(){
		return this.baseDataService.getMyCardBranch(this.getSessionUser());
	}
	
	/**
	 * 得到我可管理的发卡机构
	 * @return
	 */
	protected List<String> getMyCardBranchNo(){
		return this.baseDataService.getMyCardBranchBranchNo(this.getSessionUser());
	}
	
	/**
	 * 根据发卡机构得到其售卡机构
	 * @return
	 */
	protected List<BranchInfo> getMySellBranch(){
		return this.baseDataService.getMySellBranch(this.getSessionUser());
	}
	
	/**
	 * 得到当前卡领入机构
	 * @return
	 */
	protected List<BranchInfo> getMyReceiveBranch(){
		return this.baseDataService.getMyReceiveBranch(this.getSessionUser());
	}
	
	/**
	 * 得到当前的卡领出机构列表
	 * @return
	 */
	protected List<BranchInfo> getMyAppOrgBranch() {
		return this.baseDataService.getMyAppOrgBranch(this.getSessionUser());
	}
	
	/**
	 * 得到我可管理的商户
	 * @return
	 */
	protected List<MerchInfo> getMyMerch(){
		return this.baseDataService.getMyMerch(this.getSessionUser());
	}
	
	/**
	 * 得到我可管理的售卡代理
	 * @return
	 */
	protected List<BranchInfo> getMyCardSellBranch() {
		return this.baseDataService.getMyCardSellBranch(this.getSessionUser());
	}
	
	/**
	 * 得到我可管理的部门
	 * @return
	 */
	protected List<DepartmentInfo> getMyDept(){
		return this.baseDataService.getMyDept(this.getSessionUser());
	}
	
	/**
	 * 得到我所管理的售卡机构号和部门号列表 
	 * @return
	 */
	protected List<String> getMyDepositList() {
		return this.baseDataService.getMyDepositList(this.getSessionUser());
	}
	
	/**
	 * 得到我所管理的促销活动发起机构号列表 
	 * @return
	 */
	protected List<String> getMyPromtIssIdList() {
		return this.baseDataService.getMyPromtIssIdList(this.getSessionUser());
	}
	
	/**
	 * 得到我所管理的领卡机构，领卡部门编号
	 * @return
	 */
	protected List<NameValuePair> getMyReciveIssuer() {
		return this.baseDataService.getMyReciveIssuer(this.getSessionUser());
	}
	
	protected List<BranchInfo> getFirstFenzhi() {
		return this.baseDataService.getFirstFenzhiBranch(this.getSessionUser());
	}
	
	protected void log(String content, UserLogType userLogType) throws Exception{
		String limitId = getPrivilegeCode(request);
		this.log(content, userLogType, limitId);
	}
	
	protected void log(String content, UserLogType userLogType, String limitId) throws Exception{
		UserLog userLog = new UserLog();
		userLog.setUserId(this.getSessionUserCode());
		userLog.setBranchNo(this.getSessionUser().getBranchNo());
		userLog.setMerchantNo(this.getSessionUser().getMerchantNo());
		userLog.setContent(content);
		userLog.setLimitId(limitId);
		userLog.setLogType(userLogType.getValue());
		userLog.setLoginIp(this.request.getRemoteAddr());
		
		this.logService.addUserLog(userLog);
	}
	
	/**
	 * 从session中保存的用户权限中获取权限编号.
	 * @return
	 */
	private String getPrivilegeCode(HttpServletRequest request) {
		String link = WebResource.getLink(request);
		PrivilegeResource res = WebUtil.getPrivilegeResourceByLink(request, link);
		return res == null ? null : res.getLimitId();
	}
	
	/**
	 * 检测是否有执行提示消息
	 * @return
	 */
	public boolean isHasMessage(){
		return this.message != null;
	}

	/**
	 * 检测是否有错误提示
	 * @return
	 */
	public boolean hasError(){
		return this.error != null && !"".equals(this.error.trim());
	}

	public String getCookie(String name) {
		
		if(name==null || "".equalsIgnoreCase(name)) {
			return null;
		}
		
		Cookie[] cookieArray =  this.request.getCookies();
		for(Cookie cookie: cookieArray) {
			if(name.equalsIgnoreCase(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
	public void addActionMessage(String url, String message) {
		this.setSuccessUrl(url);
		addActionMessage(message);
		logger.debug(message);
	}
	
	
	/**
	 * 得到我可管理的发卡机构编号
	 */
	protected String[] getMyCardBranchList(){
		List<BranchInfo> branchList = this.getMyCardBranch();
		String[] branches = new String[branchList.size()];
		for(int i=0; i < branchList.size(); i++){
			branches[i] = (branchList.get(i)).getBranchCode();
		}
		return branches;
	}
	
	/**
	 * 根据发卡机构得到特约商户的编号
	 */
	protected String[] getMyFranchMerchList(){
		List<MerchInfo> merchList = this.baseDataService.getMyFranchMerch(this.getSessionUser().getBranchNo());
		String[] merches = new String[merchList.size()];
		for(int i=0; i< merchList.size(); i++){
			merches[i] = (merchList.get(i)).getMerchId();
		}
		return merches;
	}
	
	/**
	 * 根据发卡机构得到特约商户的列表
	 */
	protected List<MerchInfo> getMyFranchMerch(String branchCode){
		return this.baseDataService.getMyFranchMerch(branchCode);
	}
	
	/**
	 * 根据营运分支机构得到所管理的发卡机构的特约商户列表
	 */
	protected List<MerchInfo> getMyFranchMerchByFenzhi(String branchCode){
		return this.baseDataService.getMyFranchMerchByFenzhi(branchCode);
	}
	
	/**
	 * 取得登录用户的机构级别
	 * @return
	 */
	protected String getLoginBranchLevel() {
		if (isMerchantRoleLogined()||isPersonalRoleLogined()) {
			return StringUtils.EMPTY;
		}
		BranchHasTypeDAO branchHasTypeDAO = (BranchHasTypeDAO) SpringContext.getService("branchHasTypeDAOImpl");
		
		BranchHasTypeKey key = new BranchHasTypeKey();
		
		key.setBranchCode(this.getLoginBranchCode());
		key.setTypeCode(this.getLoginRoleTypeCode());
		BranchHasType branchHasType = (BranchHasType) branchHasTypeDAO.findByPk(key);
		
		return branchHasType.getBranchLevel();
	}
	
	/**
	 * 登录的用户所属机构是否为一级分支机构
	 * @return
	 */
	protected boolean isLoginAsFirstFenzhi() {
		if (!isFenzhiRoleLogined()) {
			return false;
		}
		
		return StringUtils.equals(getLoginBranchLevel(), BranchLevelType.FIRST.getValue());
	}
	
	protected boolean hasPrivilege() {
		if (!(isCardOrCardDeptRoleLogined() || isCardSellRoleLogined() || isCenterRoleLogined() || isFenzhiRoleLogined())) {
			return false;
		}
		return true;
	}
	
	protected boolean hasCardSellPrivilegeByCardId(String proxyId, String cardId){
		CardInfoDAO cardInfoDAO = (CardInfoDAO) SpringContext.getService("cardInfoDAOImpl");
		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
		return hasCardSellPrivilege(proxyId, cardInfo.getCardIssuer());
	}
	
	protected boolean hasCardSellPrivilegeByCardId(String proxyId, String cardId, String limitId){
		CardInfoDAO cardInfoDAO = (CardInfoDAO) SpringContext.getService("cardInfoDAOImpl");
		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
		return hasCardSellPrivilege(proxyId, cardInfo.getCardIssuer(), limitId);
	}
	
	protected boolean hasCardSellPrivilege(String proxyId, String cardBranch) {
		String limitId = getPrivilegeCode(request);
		return this.hasCardSellPrivilege(proxyId, cardBranch, limitId);
	}
	
	protected boolean hasCardSellPrivilege(String proxyId, String cardBranch, String limitId) {
		// 判断是否属于该发卡机构的售卡代理
		BranchProxyDAO branchProxyDAO = (BranchProxyDAO) SpringContext.getService("branchProxyDAOImpl");
		BranchProxyKey branchProxyKey = new BranchProxyKey(proxyId, cardBranch, ProxyType.SELL.getValue());
		if (!branchProxyDAO.isExist(branchProxyKey)) {
			return false;
		}
		
		// 判断是否拥有该权限
		SaleProxyPrivilegeDAO saleProxyPrivilegeDAO = (SaleProxyPrivilegeDAO) SpringContext.getService("saleProxyPrivilegeDAOImpl");
		List<HashMap> privlegeList = saleProxyPrivilegeDAO.findSaleProxy(proxyId, cardBranch, limitId);
		if (CollectionUtils.isNotEmpty(privlegeList)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断售卡代理是否有权限操作此卡（延期、冻结、解冻、挂失、解挂、换卡、售卡、充值）
	 */
	protected boolean isCardSellPrivilege(CardInfo cardInfo, UserInfo user, String limitId) throws BizException{
		
		String saleOrgId = cardInfo.getSaleOrgId();
		String cardIssuer = cardInfo.getCardIssuer();
		String proxyId = user.getBranchNo();
		/*if(!saleOrgId.equals(proxyId)){
			return false;
		}*/
		Assert.equals(saleOrgId, proxyId, "本售卡代理不是该卡的售卡机构,无法操作该卡。");
		
		return hasCardSellPrivilege(proxyId, cardIssuer, limitId);
	}
	
	/**
	 * 判断机构网点是否有权限操作此卡（延期、冻结、解冻、挂失、解挂）
	 */
	protected boolean isCardDeptPrivilege(CardInfo cardInfo, UserInfo user) throws BizException{
		String saleOrgId = cardInfo.getSaleOrgId(); 
		String deptId = user.getDeptId();
		
		return saleOrgId.equals(deptId);
	}
	
	/**
	 * 根据登录用户的角色，返回不同的查询参数
	 */
	protected Map<String, Object> getParams(UserInfo user) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (isCardRoleLogined()) {
			params.put("cardIssuer", user.getBranchNo());
		} else if (isCardSellRoleLogined()) {
			params.put("saleOrgId", user.getBranchNo());
		} else if (isCardDeptRoleLogined()) {
			params.put("saleOrgId", user.getDeptId());
		} else if (isFenzhiRoleLogined()) {
			params.put("cardIssuers", this.getMyCardBranchList());
		}
		
		return params;
	}
	
	/**
	 * 非发卡机构或者售卡代理不能操作新增和修改
	 */
	protected void operatePrivilege() throws BizException{
		if (!(isCardOrCardDeptRoleLogined() || isCardSellRoleLogined())) {
			throw new BizException("非发卡机构、机构网点或者售卡代理不能操作。");
		}
	}
	
	/**
	 * 判断输入的卡号、证件类型和证件号码是否和卡附加信息表中的记录一致
	 */
	protected boolean validateCardExtraInfo(String cardId, String credType,
			String credNo)throws BizException{

		CardExtraInfoDAO cardExtraInfoDAO = (CardExtraInfoDAO) SpringContext.getService("cardExtraInfoDAOImpl");
		CardExtraInfo cardExtraInfo = (CardExtraInfo) cardExtraInfoDAO.findByPk(cardId);
		
		//假如卡附加信息表中的证件类型和证件号码不完整，那么不检查一致性
		if(cardExtraInfo == null
				|| cardExtraInfo.getCredNo() == null
				|| cardExtraInfo.getCredType() == null
				|| cardExtraInfo.getCustName() == null){
			return true;
		}
		
		if(cardExtraInfo != null 
				&& StringUtils.equals(cardExtraInfo.getCredType(), credType)
				&& StringUtils.equals(cardExtraInfo.getCredNo(), credNo)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 判断输入的卡号与持卡人姓名是否和卡附加信息表中的记录一致
	 */
	protected boolean validateCardIdName(String cardId, String custName)throws BizException{
		
		Map<String, Object> params = new HashMap<String, Object>();

		CardExtraInfoDAO cardExtraInfoDAO = (CardExtraInfoDAO) SpringContext.getService("cardExtraInfoDAOImpl");
		CardExtraInfo cardExtraInfo = (CardExtraInfo) cardExtraInfoDAO.findByPk(cardId);
		
		//假如卡附加信息表不存在或者其持卡人姓名没有填写，那么不检查一致性
		if(cardExtraInfo == null 
				|| cardExtraInfo.getCustName() == null){
			return true;
		}
		params.put("cardId", cardId);
		params.put("custName", custName);

		return cardExtraInfoDAO.findCardExtraInfoByIdName(params) != null;
	
	}
	
	/**
	 * 按机构名称排序
	 * @param branchList
	 * @return
	 */
	protected List<BranchInfo> sortBranchList(List<BranchInfo> branchList) {
		// 按名称排序
		Collections.sort(branchList, new Comparator() {
			public int compare(Object o1, Object o2) {
				BranchInfo branch1 = (BranchInfo) o1;
				BranchInfo branch2 = (BranchInfo) o2;
				
				return Cn2PinYinHelper.cn2Spell(branch1.getBranchName()).compareTo(Cn2PinYinHelper.cn2Spell(branch2.getBranchName()));
			}
		});
		return branchList;
	}

	/**
	 * 按商户名称名称排序
	 * @param branchList
	 * @return
	 */
	protected List<MerchInfo> sortMerchList(List<MerchInfo> merchList) {
		// 按名称排序
		Collections.sort(merchList, new Comparator() {
			public int compare(Object o1, Object o2) {
				MerchInfo merchInfo1 = (MerchInfo) o1;
				MerchInfo merchInfo2 = (MerchInfo) o2;
				
				return Cn2PinYinHelper.cn2Spell(merchInfo1.getMerchName()).compareTo(Cn2PinYinHelper.cn2Spell(merchInfo2.getMerchName()));
			}
		});
		return merchList;
	}

	/*************以下为程序自动产生方法**************/
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public int getPageNumber() {
		return pageNumber > 0 ? pageNumber : 1;
	}	

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize > 0 ? pageSize : Constants.DEFAULT_PAGE_SIZE;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<WorkflowHis> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<WorkflowHis> flowList) {
		this.flowList = flowList;
	}
	
	public Map<String, String> getFormMap() {
		return formMap;
	}

	public void setFormMap(Map<String, String> formMap) {
		this.formMap = formMap;
	}
}
