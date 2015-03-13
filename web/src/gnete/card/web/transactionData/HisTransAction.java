package gnete.card.web.transactionData;

import flink.etc.MatchMode;
import flink.util.DateUtil;
import flink.util.Paginater;
import flink.util.TimeInterval;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.TransAcctDtlDAO;
import gnete.card.dao.TransDAO;
import gnete.card.dao.TransPointDtlDAO;
import gnete.card.dao.UserInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.Trans;
import gnete.card.entity.state.ProcState;
import gnete.card.entity.type.TransType;
import gnete.card.service.GenerateFileService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.util.UserOfLimitedTransQueryUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

/**
 * 历史交易明细查询
 * @author aps-lib
 *
 */
public class HisTransAction extends BaseAction {

	private static final String IS_GENERATING_HIS_TRANS_EXCEL = "IS_GENERATING_HIS_TRANS_EXCEL";
	@Autowired
	private TransDAO transDao;
	@Autowired
	private TransAcctDtlDAO transAcctDtlDAO;
	@Autowired
	private TransPointDtlDAO transPointDtlDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private GenerateFileService generateFileService;
	@Autowired
	private UserInfoDAO userInfoDAO;
	
	private Trans trans;
	private Paginater page;
	private Collection statusList;
	private Collection transTypeList;
	private String settStartDate;
	private String settEndDate;
	private String fixSettEndDate;
	private Paginater acctPage;
	private Paginater pointPage;
	private List<BranchInfo> branchList;
	private String startCardId;
	private String endCardId;
	/** 发卡机构列表 */
	private List<BranchInfo> cardIssuerList;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = buildQryParams();
		
		this.page = this.transDao.findHisTrans(params, this.getPageNumber(), this.getPageSize());	
		return LIST;

	}


	private Map<String, Object> buildQryParams() throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isListNewCardFirstDeposit", this.isFormMapFieldTrue("isListNewCardFirstDeposit"));
		
		boolean isUserOfLimitedTransQuery = isUserOfLimitedTransQuery();
		if (isUserOfLimitedTransQuery){
			params.put("isUserOfLimitedTransQuery", isUserOfLimitedTransQuery);
			params.put("limitedTransTypes", TransType.getLimitedTransQueryType());
			params.put("limitedExcludeManageBranchCodes", UserOfLimitedTransQueryUtil.getExcludeManageBranchCodes());
		}
		this.transTypeList = isUserOfLimitedTransQuery ? TransType.getLimitedTransQueryType() : TransType.ALL
				.values(); // 加载交易类型
		this.statusList = ProcState.ALL.values(); //加载处理状态		
		
		if(StringUtils.isNotBlank(settStartDate)){ // 默认查询前一工作日
			params.put("settStartDate", settStartDate);
		} else {
			settStartDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
		}

		if (StringUtils.isNotBlank(settEndDate)) {
			params.put("settEndDate", settEndDate);
		} else {
			settEndDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
		}
		
		fixSettEndDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
		
		params.put("settStartDate", settStartDate);
		params.put("settEndDate", settEndDate);
		if (trans != null) {
			params.put("sysTraceNum", trans.getSysTraceNum());
			params.put("cardId", trans.getCardId()); 
			params.put("merNo", trans.getMerNo());
			params.put("issuerName", MatchMode.ANYWHERE.toMatchString(trans.getIssuerName()));
			params.put("termlId", trans.getTermlId());
			params.put("cardIssuer", trans.getCardIssuer());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(trans.getBranchName()));
			params.put("transType",trans.getTransType());
			params.put("eiaCardId",trans.getEiaCardId());
			params.put("procStatus", trans.getProcStatus());
			
			if (isFormMapFieldTrue("useExternalCardSearch")
					&& StringUtils.isNotBlank(trans.getReserved4())) {
				params.put("reserved4", trans.getReserved4()); // 外部号码
			}
		}
		
		if (isCardOrCardDeptRoleLogined()){
			cardIssuerList = this.branchInfoDAO.findTreeBranchList(this.getLoginBranchCode());
			if (trans == null || StringUtils.isBlank(trans.getCardIssuer())) {
				branchList = cardIssuerList;
			}
		} else if (isMerchantRoleLogined()){
			params.put("merNo", this.getSessionUser().getMerchantNo());
		} else if (isTerminalRoleLogined()){
			params.put("posProvId", this.getSessionUser().getBranchNo());
		} else if (isTerminalMaintainRoleLogined()){
			params.put("posManageId", this.getSessionUser().getBranchNo());
		} else if (isFenzhiRoleLogined()){
			branchList = new ArrayList<BranchInfo>();
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			params.put("branchCode", this.getSessionUser().getBranchNo());
		} else if (isAgentRoleLogined()){
			params.put("centProxyNo", this.getSessionUser().getBranchNo());
		} else if (isCenterOrCenterDeptRoleLogined()){
		} else {
			throw new BizException("没有权限查询！");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)) {
			params.put("cardIssuers", branchList);
		}
		
		if(StringUtils.isNotBlank(settStartDate) && StringUtils.isNotBlank(settEndDate)){ //如果清算日期不为空，检查跨度是否超过31天
			if(DateUtil.chkDateFormat(settStartDate) && DateUtil.chkDateFormat(settEndDate)){ //检查清算日期格式
				if (StringUtils.isEmpty(this.getStartCardId()) && StringUtils.isEmpty(this.getEndCardId())) {
					
					Date curDate = DateUtil.formatDate(settStartDate, "yyyyMMdd");
					Date oriDate = DateUtil.formatDate(settEndDate, "yyyyMMdd");
					int diffDays =  DateUtil.getDateDiffDays(curDate, oriDate);
					if(diffDays > 31){
						throw new BizException("开始结束卡号为空时，清算日期的查询间隔不得超过31天");
					}
				}
			}
		}
		
		if(!StringUtils.isEmpty(this.getStartCardId()) && StringUtils.isEmpty(this.getEndCardId())){ // 开始卡号不为空，结束卡号为空
			params.put("startCardId", this.getStartCardId());
			params.put("endCardId", this.getStartCardId());
		} else if(StringUtils.isEmpty(this.getStartCardId()) && !StringUtils.isEmpty(this.getEndCardId())){ // 开始卡号为空，结束卡号不为空
			params.put("startCardId", this.getEndCardId());
			params.put("endCardId", this.getEndCardId());
		} else if(!StringUtils.isEmpty(this.getStartCardId()) && !StringUtils.isEmpty(this.getEndCardId())){ // 开始卡号和结束卡号不为空
			params.put("startCardId", this.getStartCardId());
			params.put("endCardId", this.getEndCardId());
			
			//判断起始卡号间隔不能超过5000张
			if(new BigDecimal(this.getEndCardId()).subtract(new BigDecimal(this.getStartCardId())).compareTo(new BigDecimal("50000"))>0){
				throw new BizException("查询卡号不能超过5000张。");
			}
		}
		return params;
	}
	
	
	/** 是否是受限的交易记录查询用户。注：该getter方法会在页面上使用，用于屏蔽按钮*/
	public boolean isUserOfLimitedTransQuery(){
		return userInfoDAO.isUserOfLimitedTransQuery(this.getSessionUserCode());
	}
	
	// 取得交易明细
	public String detail() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (isCardOrCardDeptRoleLogined()){
//			params.put("cardIssuer", this.getSessionUser().getBranchNo());
		} else if (isMerchantRoleLogined()){
			params.put("merNo", this.getSessionUser().getMerchantNo());
		} else if (isTerminalRoleLogined()){
			params.put("posProvId", this.getSessionUser().getBranchNo());
		} else if (isTerminalMaintainRoleLogined()){
			params.put("posManageId", this.getSessionUser().getBranchNo());
		} else if (isFenzhiRoleLogined()){
			branchList = new ArrayList<BranchInfo>();
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			params.put("branchCode", this.getSessionUser().getBranchNo());
		} else if (isAgentRoleLogined()){
			params.put("centProxyNo", this.getSessionUser().getBranchNo());
		} else if (isCenterOrCenterDeptRoleLogined()){
		} else {
			throw new BizException("没有权限查询！");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)) {
			params.put("cardIssuers", branchList);
		}
		params.put("transSn", this.trans.getTransSn());
		try {
//			DbContextHolder.setDbType(DbType.SETTLE_DB);
			this.trans = (Trans) this.transDao.findByPkFromJFLINK(params);
			Assert.notNull(this.trans, "交易流水为[" + trans.getTransSn() + "]交易信息不存在。");
			
			//取得交易账户变动明细
			params.clear();
			params.put("transSn", this.trans.getTransSn());
			this.acctPage = this.transAcctDtlDAO.findTransAcctDtl(params, this.getPageNumber(), this.getPageSize());
			
			//取得交易积分变动明细
			params.clear();
			params.put("transSn", this.trans.getTransSn());
			this.pointPage = this.transPointDtlDAO.findTransPointDtl(params, this.getPageNumber(), this.getPageSize());
		} finally {
//			DbContextHolder.setDbType(DbType.TRANS_DB);
		}
		return DETAIL;
		
	}
	
	/**
	 * 手动生成文件
	 * @throws Exception
	 */
	public void generate() throws Exception {
		ActionContext.getContext().getSession().put(IS_GENERATING_HIS_TRANS_EXCEL,Boolean.TRUE);
		
		try {
			Map<String, Object> params = buildQryParams();
			String exportType = this.getFormMapValue("exportType");
			logger.info("用户{},机构{}导出历史交易明细{}...  [params: {}]", new Object[] { this.getSessionUserCode(),
					this.getSessionUser().getBranchNo(), exportType, params });
			TimeInterval ti = new TimeInterval();
			if ("excel".equals(exportType)) {
				this.generateFileService.generateHisTransExcel(response, params);
			} else if ("csv".equals(exportType)) {
				this.generateFileService.generateHisTransCsv(response, params);
			}
			logger.info("用户{},机构{}导出历史交易明细{}完成！用时 {}秒", new Object[] { this.getSessionUserCode(),
					this.getSessionUser().getBranchNo(), exportType, ti.getIntervalOfSec() });
		} finally {
			ActionContext.getContext().getSession().remove(IS_GENERATING_HIS_TRANS_EXCEL);
		}
	}
	
	
	/** 
	 * ajax请求：判断是否真正生产历史交易明细的excel文件 
	 */
	public void ajaxIsGeneratingExcel() throws Exception {
		JSONObject json = new JSONObject();
		if (Boolean.TRUE.equals(ActionContext.getContext().getSession().get(IS_GENERATING_HIS_TRANS_EXCEL))) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		this.respond(json.toString());
	}
	
	// ----------------------------- getter and setter methods followed ------------------------------
	public TransDAO getTransDao() {
		return transDao;
	}

	public void setTransDao(TransDAO transDao) {
		this.transDao = transDao;
	}

	public Trans getTrans() {
		return trans;
	}

	public void setTrans(Trans trans) {
		this.trans = trans;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Paginater getPage() {
		return page;
	}

	public void setStatusList(Collection statusList) {
		this.statusList = statusList;
	}

	public Collection getStatusList() {
		return statusList;
	}

	public void setTransType(Collection transType) {
		this.transTypeList = transType;
	}

	public Collection getTransType() {
		return transTypeList;
	}

	public void setTransTypeList(Collection transTypeList) {
		this.transTypeList = transTypeList;
	}

	public Collection getTransTypeList() {
		return transTypeList;
	}

	public String getSettStartDate() {
		return settStartDate;
	}

	public void setSettStartDate(String settStartDate) {
		this.settStartDate = settStartDate;
	}

	public String getSettEndDate() {
		return settEndDate;
	}

	public void setSettEndDate(String settEndDate) {
		this.settEndDate = settEndDate;
	}

	public Paginater getAcctPage() {
		return acctPage;
	}

	public void setAcctPage(Paginater acctPage) {
		this.acctPage = acctPage;
	}

	public Paginater getPointPage() {
		return pointPage;
	}

	public void setPointPage(Paginater pointPage) {
		this.pointPage = pointPage;
	}

	public String getFixSettEndDate() {
		return fixSettEndDate;
	}

	public void setFixSettEndDate(String fixSettEndDate) {
		this.fixSettEndDate = fixSettEndDate;
	}

	public String getStartCardId() {
		return startCardId;
	}

	public void setStartCardId(String startCardId) {
		this.startCardId = startCardId;
	}

	public String getEndCardId() {
		return endCardId;
	}

	public void setEndCardId(String endCardId) {
		this.endCardId = endCardId;
	}

	public List<BranchInfo> getCardIssuerList() {
		return cardIssuerList;
	}

	public void setCardIssuerList(List<BranchInfo> cardIssuerList) {
		this.cardIssuerList = cardIssuerList;
	}
}
