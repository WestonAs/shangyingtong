package gnete.card.web.para;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.ReportConfigParaDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.ReportConfigPara;
import gnete.card.entity.ReportConfigParaKey;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.CycleType;
import gnete.card.entity.type.DayOfMonthType;
import gnete.card.entity.type.DayOfWeekType;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.report.ReportType;
import gnete.card.service.ReportService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: ReportConfigParaAction.java
 *
 * @description: 报表配置生成参数定义
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-10-12
 */
public class ReportConfigParaAction extends BaseAction{

	@Autowired
	private ReportConfigParaDAO reportConfigParaDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private ReportService reportService;
	
	private ReportConfigPara reportConfigPara;
	private Paginater page;
	private List<CardTypeState> statusList;
	private List<IssType> insTypeList;
	private List<ReportType> reportTypeList;
	private List<CycleType> cycleTypeList;
	private List<DayOfMonthType> cycleOfMonthTypeList;
	private List<DayOfWeekType> cycleOfWeekTypeList;
	private boolean showCardMerch = false;
	private String insIds; // 要添加的机构或者商户数组
	
	private String insId; 
	private String insType; 
	private String reportType; 
	
	private boolean showModify = false;
	
	@Override
	public String execute() throws Exception {
		
		initPage();
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (reportConfigPara != null) {
			params.put("reportType", reportConfigPara.getReportType());
			params.put("insName", MatchMode.ANYWHERE.toMatchString(reportConfigPara.getInsName()));
		}
		
		
		if (isCenterOrCenterDeptRoleLogined()){ // 如果登录用户为运营中心，运营中心部门
			this.showCardMerch = true;
			
		} else if(isFenzhiRoleLogined()){// 分支机构
			params.put("parent", getLoginBranchCode());
			this.showCardMerch = true;
			
		} else if(isCardOrCardDeptRoleLogined()){ // 发卡机构、机构部门
			params.put("insId", getSessionUser().getBranchNo());
			this.showCardMerch = false;
			
		} else if(isMerchantRoleLogined()){ // 商户
			params.put("insId", getSessionUser().getMerchantNo());
			this.showCardMerch = false;
		} else {
			throw new BizException("没有权限查询。");
		}
		
		this.page = this.reportConfigParaDAO.findReportConfigPara(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		hasRightToDo();
		initPage();
		return ADD;
	}
	
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		String[] insCodes = insIds.split(",");
		this.reportService.addReportConfigPara(this.reportConfigPara, insCodes, this.getSessionUserCode());
		
		String msg = "新增报表配置生成参数成功!";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/para/reportConfigPara/list.do", msg);
		return SUCCESS;
		
	}

	public String showModify() throws Exception {
		this.checkEffectiveCertUser();
		
		hasRightToDo();
		initPage();
		
		ReportConfigParaKey key = new ReportConfigParaKey();
		key.setInsId(this.getInsId());
		key.setInsType(this.getInsType());
		key.setReportType(this.getReportType());
		this.reportConfigPara = (ReportConfigPara) this.reportConfigParaDAO.findByPk(key);
		
		if(IssType.CARD.getValue().equals(this.getInsType())){ // 发卡机构
			BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.getInsId());
			Assert.notNull(branchInfo, "不存在机构号" + this.getInsId());
			this.reportConfigPara.setInsName(branchInfo.getBranchName());
		}
		else if(IssType.MERCHANT.getValue().equals(this.getInsType())){ // 商户
			MerchInfo merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(this.getInsId());
			Assert.notNull(merchInfo, "不存在商户号" + this.getInsId());
			this.reportConfigPara.setInsName(merchInfo.getMerchName());
		}
		
		return MODIFY;
	}
	
	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.reportService.modifyReportConfigPara(this.reportConfigPara, this.getSessionUserCode());
		
		String msg = "修改" + IssType.valueOf(reportConfigPara.getInsType()).getName() + 
		"[" + this.reportConfigPara.getInsId() + "]报表类型为[" + ReportType.valueOf(this.reportConfigPara.getReportType()).getName() + "]的报表配置参数成功！";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/para/reportConfigPara/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		ReportConfigParaKey key = new ReportConfigParaKey();
		key.setInsId(this.getInsId());
		key.setInsType(this.getInsType());
		key.setReportType(this.getReportType());
		this.reportService.deleteReportConfigPara(key);
		
		String msg = "删除" + IssType.valueOf(this.getInsType()).getName() + 
		"[" + this.getInsId() + "]报表类型为[" + ReportType.valueOf(this.getReportType()).getName() + "]的报表配置参数成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/para/reportConfigPara/list.do", msg);
		return SUCCESS;
	}
	
	private void initPage() {
		
		this.statusList = CardTypeState.getList();
		this.insTypeList = IssType.getCardDefineType();
		this.reportTypeList = ReportType.getReportConfigType();
		this.cycleTypeList = CycleType.getAll();
		this.cycleOfMonthTypeList = DayOfMonthType.getAllWithOutEveryDay();
		this.cycleOfWeekTypeList = DayOfWeekType.getAll();
		//this.insServiceTypeList = InsServiceType.getAll();
	}
	
	// 根据机构类型查询可用的业务类型列表，服务端查询，返回到前端
	public void serviceList() throws BizException {
		/*String insType = this.insServiceAuthority.getInsType();
		
		//发卡机构
		if(IssType.CARD.getValue().equals(insType)){
			this.insServiceTypeList =  InsServiceType.getAll();
		}
		// 商户
		else if(IssType.MERCHANT.getValue().equals(insType)){
			this.insServiceTypeList =  InsServiceType.getMerchServiceList();
		}
		else {
			this.insServiceTypeList.clear();
		}
		if (CollectionUtils.isNotEmpty(insServiceTypeList)) {
			StringBuffer sb = new StringBuffer(128);
			for (InsServiceType type : insServiceTypeList) {
				sb.append("<input type=\"checkbox\" name=\"serviceIds\" value=\"")
							.append(type.getValue()).append("\"/>")
							.append(type.getName()).append("<br />");
			}
			respond(sb.toString());
		}*/
	}
	
	private void hasRightToDo() throws BizException {
		if(!isCenterOrCenterDeptRoleLogined()){
			throw new BizException("只有营运中心、中心部门有权限进行该操作！");
		} 
	}
	
	/**
	 * 返回登录机构
	 * @return
	 */
	public String getParent() {
		if(isFenzhiRoleLogined()){
			return super.getSessionUser().getBranchNo();
		}
		return "";
	}
	
	// 服务端查询周期类型列表，返回给前端
	public String queryCycleTypeList(){
		
		// 累积消费充值余额报表类型，周期类型只能是“按日”
		if(ReportType.CONSM_CHARGE_BAL.getValue().equals(this.reportConfigPara.getReportType())){
			this.cycleTypeList = CycleType.getDay();
		}
		
		return "cycleTypeList";
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<CardTypeState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CardTypeState> statusList) {
		this.statusList = statusList;
	}

	public List<IssType> getInsTypeList() {
		return insTypeList;
	}

	public void setInsTypeList(List<IssType> insTypeList) {
		this.insTypeList = insTypeList;
	}

	public ReportConfigPara getReportConfigPara() {
		return reportConfigPara;
	}

	public void setReportConfigPara(ReportConfigPara reportConfigPara) {
		this.reportConfigPara = reportConfigPara;
	}

	public boolean isShowCardMerch() {
		return showCardMerch;
	}

	public void setShowCardMerch(boolean showCardMerch) {
		this.showCardMerch = showCardMerch;
	}

	public List<ReportType> getReportTypeList() {
		return reportTypeList;
	}

	public void setReportTypeList(List<ReportType> reportTypeList) {
		this.reportTypeList = reportTypeList;
	}

	public List<DayOfMonthType> getCycleOfMonthTypeList() {
		return cycleOfMonthTypeList;
	}

	public void setCycleOfMonthTypeList(List<DayOfMonthType> cycleOfMonthTypeList) {
		this.cycleOfMonthTypeList = cycleOfMonthTypeList;
	}

	public List<DayOfWeekType> getCycleOfWeekTypeList() {
		return cycleOfWeekTypeList;
	}

	public void setCycleOfWeekTypeList(List<DayOfWeekType> cycleOfWeekTypeList) {
		this.cycleOfWeekTypeList = cycleOfWeekTypeList;
	}

	public List<CycleType> getCycleTypeList() {
		return cycleTypeList;
	}

	public void setCycleTypeList(List<CycleType> cycleTypeList) {
		this.cycleTypeList = cycleTypeList;
	}

	public String getInsIds() {
		return insIds;
	}

	public void setInsIds(String insIds) {
		this.insIds = insIds;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getInsType() {
		return insType;
	}

	public void setInsType(String insType) {
		this.insType = insType;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public boolean isShowModify() {
		return showModify;
	}

	public void setShowModify(boolean showModify) {
		this.showModify = showModify;
	}

}
