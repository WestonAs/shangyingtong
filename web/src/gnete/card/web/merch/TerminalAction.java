package gnete.card.web.merch;

import flink.etc.DatePair;
import flink.etc.MatchMode;
import flink.util.IOUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AreaDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.TerminalAddiDAO;
import gnete.card.dao.TerminalDAO;
import gnete.card.entity.Area;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.Terminal;
import gnete.card.entity.TerminalAddi;
import gnete.card.entity.flag.CheckIpFlag;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.TerminalState;
import gnete.card.entity.type.CommType;
import gnete.card.entity.type.EntryMode;
import gnete.card.entity.type.ExpenseType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.MerchService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.Symbol;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * TerminalAction.java.
 */
public class TerminalAction extends BaseAction {

	@Autowired
	private TerminalDAO terminalDAO;
	@Autowired
	private TerminalAddiDAO terminalAddiDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchService merchService;
	@Autowired
	private AreaDAO areaDAO;
	
	private Paginater page;
	private Terminal terminal;
	private TerminalAddi terminalAddi;
	
	private List expenseTypeList;
	private List checkIpList;
	private List entryModeList;
	private List commTypeList;
	private List<YesOrNoFlag> singleProductList;
	
	private String merchId_sel;
	private String manageBranch_sel;
	private String maintenance_sel;

	private String startDate;
	private String endDate;
	private String checkStartDate;//审核时间
	private String checkEndDate;
	
	private String cardBranchCode; // 发卡机构编号
	private String cardBranchName; // 发卡机构名
	
	private String fenzhiBranchCode; 
	private String fenzhiBranchName;
	private boolean showCardBranch = false;
	private boolean showCenter = false;
	
	// 界面选择时是否单选
	private boolean radio;
	
	// 封装上传文件域的属性
	private File upload;
	
	private String	uploadFileName;
	
	@Override
	public String execute() throws Exception {
		this.entryModeList = EntryMode.getAll();
		this.singleProductList = YesOrNoFlag.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (terminal != null) {
			params.put("termId", terminal.getTermId());
			params.put("merchName", MatchMode.ANYWHERE.toMatchString(terminal.getMerchName()));
			
			params.put("cardBranchCode", cardBranchCode);
			params.put("entryMode", terminal.getEntryMode());
			params.put("singleProduct", terminal.getSingleProduct());
			params.put("fenzhiBranchCode", fenzhiBranchCode);
			params.put("maintenance", terminal.getMaintenance());

		}
		DatePair datePair = new DatePair(this.startDate, this.endDate);
		datePair.setTruncatedTimeDate(params);
		
		DatePair datePair2 = new DatePair(this.checkStartDate, this.checkEndDate);
		datePair2.setTruncatedTimeDate(params,"checkStartDate","checkEndDate");
		
		if (isMerchantRoleLogined()){// 如果是商户则只允许查看其下的终端
			showCardBranch = false;
			params.put("merchId", this.getSessionUser().getRole().getMerchantNo());
		
		} else if (isCardOrCardDeptRoleLogined()){// 发卡机构或部门登录，可以查看自己的特约商户的终端
			showCardBranch = false;
			params.put("cardBranchCode", this.getLoginBranchCode());
		
		} else if (isCenterOrCenterDeptRoleLogined()){// 中心可以查看所有的	
			showCenter = true;
			showCardBranch = true;
			
		} else if (isFenzhiRoleLogined()){// 分支机构可查看自己管理的商户的	
			showCardBranch = true;
			List<BranchInfo> list = this.getMyManageFenzhi();
			params.put("fenzhiList", list);
			
		} else if (isAgentRoleLogined()){//运营代理商角色登录
			showCardBranch = true;
			params.put("agent", this.getLoginBranchCode());
		
		} else if (isTerminalRoleLogined()){// 出机方可以查看自己出机的终端
			params.put("chujifang", this.getLoginBranchCode());
			showCardBranch = false;
		
		} else if (isTerminalMaintainRoleLogined()){// 维护方可以查看自己维护的终端
			params.put("weihufang", this.getLoginBranchCode());
			showCardBranch = false;
			
		} else {
			throw new BizException("没有权限查看终端信息");
		}
		
		this.page = this.terminalDAO.find(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showSelect() throws Exception {
		return "select";
	}

	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", TerminalState.NORMAL.getValue());
		String cardBranch = this.getFormMapValue("cardBranch");
		params.put("cardBranchCode", cardBranch);
		String merchIdsStr = this.getFormMapValue("merchIds");
		if (StringUtils.isNotBlank(merchIdsStr)) {
			String[] merchIdsArr = merchIdsStr.split(",");
			params.put("merchIds", merchIdsArr);
		}
		String termId = getFormMapValue("termId") == null ? "" : getFormMapValue("termId").trim();
		params.put("termId", termId);
		String merchName = getFormMapValue("merchName") == null ? "" : getFormMapValue("merchName").trim();
		params.put("merchName", MatchMode.ANYWHERE.toMatchString(merchName));
		this.page = this.terminalDAO.find(params, this.getPageNumber(), Constants.DEFAULT_SELECT_PAGE_SIZE);
		return "data";
	}
	
	public String detail() throws Exception{
		this.terminal = (Terminal) this.terminalDAO.findByPk(this.terminal.getTermId());
		this.terminalAddi = (TerminalAddi) this.terminalAddiDAO.findByPk(this.terminal.getTermId());
		return DETAIL;
	}
	
	public String showAdd() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined(), "没有权限维护终端信息");
		
		initPage();
		this.terminal = new Terminal();
		this.terminal.setExpenseType(ExpenseType.CHECKOUT.getValue());
		this.terminal.setEntryMode(EntryMode.CARD_ONLY.getValue());
		this.terminal.setSingleProduct(Symbol.NO); // 默认不是单机产品终端
		return ADD;
	}
	
	private void initPage(){
		this.expenseTypeList = ExpenseType.getAll();
		this.checkIpList = CheckIpFlag.getAll();
		this.entryModeList = EntryMode.getAll();
		this.commTypeList = CommType.getAll();
		this.singleProductList = YesOrNoFlag.getAll();
	}
	
	public String add() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined(), "没有权限维护终端信息");
		
		// 调用service业务接口
		this.merchService.addTerminal(this.terminal, cardBranchCode, this.getSessionUserCode());
		
		if ("1".equals(this.getFormMapValue("needBankAcctInfo")) && terminalAddi != null
				&& this.terminalAddi.isNotBlank()) {
			this.terminalAddi.setTermId(this.terminal.getTermId());
			this.terminalAddiDAO.insert(terminalAddi);
		}	
		
		String msg = LogUtils.r("添加终端[{0}]成功", this.terminal.getTermId());
		this.addActionMessage("/pages/terminal/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	public String showAddBatch() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined(), "没有权限维护终端信息");
		
		initPage();
		this.terminal = new Terminal();
		this.terminal.setExpenseType(ExpenseType.CHECKOUT.getValue());
		this.terminal.setEntryMode(EntryMode.CARD_ONLY.getValue());
		this.terminal.setSingleProduct(Symbol.NO);
		
		return "addBatch";
	}
	
	public String addBatch() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined(), "没有权限维护终端信息");
		
		String count = request.getParameter("terminalCount");
		// 调用service业务接口
		String[] terminalIdArray = this.merchService.addTerminalBatch(this.terminal, 
				cardBranchCode, Integer.parseInt(count), this.getSessionUserCode());
		
		String batchMsg = "";
		if (!ArrayUtils.isEmpty(terminalIdArray)) {
			batchMsg = ObjectUtils.nullSafeToString(terminalIdArray);
		}
		
		String msg = "批量添加终端[" + batchMsg +"]成功";
		this.addActionMessage("/pages/terminal/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	public String showAddBatchFile() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined(), "没有权限维护终端信息");
		return "addBatchFile";
	}
	
	public String addBatchFile() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined(), "没有权限维护终端信息");
		
		Assert.isTrue(IOUtil.testFileFix(uploadFileName, Arrays.asList("txt","csv")), "新增终端文件只能是文本格式的文件");

		int cnt = this.merchService.addTermBatchFile(upload, this.getSessionUser());

		String msg = "文件方式批量新增终端成功，新增终端数量"+cnt;
		String retUrl = "/pages/terminal/list.do?goBack=goBack";
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(retUrl, msg);
		return SUCCESS;
	}
	
	public String showAddOldTerminal() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined(), "没有权限维护终端信息");
		
		initPage();
		this.terminal = new Terminal();
		this.terminal.setExpenseType(ExpenseType.CHECKOUT.getValue());
		this.terminal.setEntryMode(EntryMode.CARD_ONLY.getValue());
		this.terminal.setSingleProduct(Symbol.NO);
		
		return "addOldTerminal";
	}
	
	public String addOldTerminal() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined(), "没有权限维护终端信息");
		
		// 旧系统终端默认不是单机产品终端
		this.terminal.setSingleProduct(Symbol.NO);
		// 调用service业务接口
		this.merchService.addOldTerminal(this.terminal, this.getSessionUserCode());
		
		String msg = LogUtils.r("添加终端[{0}]成功", this.terminal.getTermId());
		this.addActionMessage("/pages/terminal/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	/**
	 * 加载发卡机构
	 * @throws Exception
	 */
	public void loadBranch() throws Exception {
		String singleProduct = request.getParameter("singleProduct");
		String merchId = request.getParameter("merchId");
		
		StringBuilder sb = new StringBuilder(128);
		sb.append("<option value=\"\">--请选择--</option>");
		
		if (StringUtils.isEmpty(singleProduct)
				|| StringUtils.isEmpty(merchId)) {
			this.respond(sb.toString());
			return;
		}
		
		if (Symbol.NO.equals(singleProduct)) {
			this.respond(sb.toString());
			return;
		}
		
		MerchInfo merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(merchId);
		
		if (merchInfo == null || Symbol.NO.equals(merchInfo.getSingleProduct())) {
			this.respond(sb.toString());
			return;
		}
		
		List<BranchInfo> branchList = this.branchInfoDAO.findByMerch(merchId);
		
		for (BranchInfo branchInfo : branchList) {
			if (Symbol.YES.equals(branchInfo.getSingleProduct())) {
				sb.append("<option value=\"").append(branchInfo.getBranchCode())
						.append("\">").append(branchInfo.getBranchName()).append("</option>");
			}
		}
		
		this.respond(sb.toString());
	}
	
	public String showModify() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined(), "没有权限维护终端信息");
		
		initPage();
		this.terminal = (Terminal) this.terminalDAO.findByPk(this.terminal.getTermId());
		this.terminalAddi = (TerminalAddi) this.terminalAddiDAO.findByPk(this.terminal.getTermId());
		if(terminalAddi != null){
			this.formMap.put("needBankAcctInfo","1");
			Area accArea = (Area) this.areaDAO.findByPk(this.terminalAddi.getAccAreaCode());
			if (accArea != null) {
				this.formMap.put("accAreaName",accArea.getAreaName());
			}
		}else{
			this.formMap.put("needBankAcctInfo","0");
		}
		
		this.setMaintenance_sel(getBranchName(this.terminal.getMaintenance()));
		this.setManageBranch_sel(getBranchName(this.terminal.getManageBranch()));
		this.setMerchId_sel(getMerchName(this.terminal.getMerchId()));
		return MODIFY;
	}
	
	private String getBranchName(String branchCode){
		if (StringUtils.isEmpty(branchCode)) {return "";}
		BranchInfo branch = (BranchInfo) this.branchInfoDAO.findByPk(branchCode);
		if (branch == null) {return "";}
		return branch.getBranchName();
	}
	private String getMerchName(String merchId){
		if (StringUtils.isEmpty(merchId)) {return "";}
		MerchInfo merch = (MerchInfo) this.merchInfoDAO.findByPk(merchId);
		if (merch == null) {return "";}
		return merch.getMerchName();
	}
	
	public String modify() throws Exception {
		this.merchService.modifyTerminal(this.terminal, this.getSessionUserCode());
		
		this.terminalAddiDAO.delete(this.terminal.getTermId());
		if ("1".equals(this.getFormMapValue("needBankAcctInfo"))) {
			if (terminalAddi != null && this.terminalAddi.isNotBlank()) {
				this.terminalAddi.setTermId(this.terminal.getTermId());
				this.terminalAddiDAO.insert(terminalAddi);
			}
		} 
		
		String msg = LogUtils.r("修改终端[{0}]成功", this.terminal.getTermId());
		this.addActionMessage("/pages/terminal/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String stop() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined(), "没有权限维护终端信息");
		
		String termId = request.getParameter("termId");
		this.merchService.stopTerminal(termId, this.getSessionUserCode());
		
		String msg = LogUtils.r("停用终端[{0}]成功", termId);
		this.addActionMessage("/pages/terminal/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String start() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined(), "没有权限维护终端信息");
		
		String termId = request.getParameter("termId");
		this.merchService.startTerminal(termId, this.getSessionUserCode());
		
		String msg = LogUtils.r("启用终端[{0}]成功", termId);
		this.addActionMessage("/pages/terminal/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List getExpenseTypeList() {
		return expenseTypeList;
	}

	public void setExpenseTypeList(List expenseTypeList) {
		this.expenseTypeList = expenseTypeList;
	}

	public List getCheckIpList() {
		return checkIpList;
	}

	public void setCheckIpList(List checkIpList) {
		this.checkIpList = checkIpList;
	}

	public List getEntryModeList() {
		return entryModeList;
	}

	public void setEntryModeList(List entryModeList) {
		this.entryModeList = entryModeList;
	}

	public List getCommTypeList() {
		return commTypeList;
	}

	public void setCommTypeList(List commTypeList) {
		this.commTypeList = commTypeList;
	}

	public String getMerchId_sel() {
		return merchId_sel;
	}

	public void setMerchId_sel(String merchId_sel) {
		this.merchId_sel = merchId_sel;
	}

	public String getManageBranch_sel() {
		return manageBranch_sel;
	}

	public void setManageBranch_sel(String manageBranch_sel) {
		this.manageBranch_sel = manageBranch_sel;
	}

	public String getMaintenance_sel() {
		return maintenance_sel;
	}

	public void setMaintenance_sel(String maintenance_sel) {
		this.maintenance_sel = maintenance_sel;
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
	
	public String getFenzhiBranchCode() {
		return fenzhiBranchCode;
	}

	public void setFenzhiBranchCode(String fenzhiBranchCode) {
		this.fenzhiBranchCode = fenzhiBranchCode;
	}

	public String getFenzhiBranchName() {
		return fenzhiBranchName;
	}

	public void setFenzhiBranchName(String fenzhiBranchName) {
		this.fenzhiBranchName = fenzhiBranchName;
	}

	public boolean isShowCardBranch() {
		return showCardBranch;
	}

	public void setShowCardBranch(boolean showCardBranch) {
		this.showCardBranch = showCardBranch;
	}

	public List<YesOrNoFlag> getSingleProductList() {
		return singleProductList;
	}

	public void setSingleProductList(List<YesOrNoFlag> singleProductList) {
		this.singleProductList = singleProductList;
	}
	public boolean isShowCenter() {
		return showCenter;
	}

	public void setShowCenter(boolean showCenter) {
		this.showCenter = showCenter;
	}

	public TerminalAddi getTerminalAddi() {
		return terminalAddi;
	}

	public void setTerminalAddi(TerminalAddi terminalAddi) {
		this.terminalAddi = terminalAddi;
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
	
}
