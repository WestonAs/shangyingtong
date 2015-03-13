package gnete.card.web.transactionData;

import flink.etc.MatchMode;
import flink.util.Paginater;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

/**
 * 当日交易明细查询
 * @author aps-lib
 * 
 */
public class CurrentTransAction extends BaseAction {
	
	private static final String IS_GENERATING_TRANS_FILE = "IS_GENERATING_TRANS_FILE";
	@Autowired
	private GenerateFileService generateFileService;
	@Autowired
	private TransDAO transDao;
	@Autowired
	private TransAcctDtlDAO transAcctDtlDAO;
	@Autowired
	private TransPointDtlDAO transPointDtlDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private UserInfoDAO userInfoDAO;
	
	private Trans trans;
	private Paginater page;
	private Collection statusList;
	private Collection transTypeList;
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
		
		this.page = this.transDao.findTrans(params, this.getPageNumber(), this.getPageSize());	
		
		return LIST;
	}

	/**
	 * 手动生成明细文件
	 * @throws Exception
	 */
	public void generate() throws Exception {
		ActionContext.getContext().getSession().put(IS_GENERATING_TRANS_FILE,Boolean.TRUE);
		
		try {
			Map<String, Object> params = buildQryParams();
			this.generateFileService.generateTransCsv(response, params);
		} finally {
			ActionContext.getContext().getSession().remove(IS_GENERATING_TRANS_FILE);
		}
	}
	
	
	/** 
	 * ajax请求：判断是否真正生产当日交易明细的文件 
	 */
	public void ajaxIsGeneratingFile() throws Exception {
		if (Boolean.TRUE.equals(ActionContext.getContext().getSession().get(IS_GENERATING_TRANS_FILE))) {
			this.responseJsonObject("1", "正在产生交易明细文件");
		} else {
			this.responseJsonObject("-1", "没有正在产生交易明细文件");
		}
	}
	
	private Map<String, Object> buildQryParams() throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		
		boolean isUserOfLimitedTransQuery = userInfoDAO.isUserOfLimitedTransQuery(this.getSessionUserCode());
		if (isUserOfLimitedTransQuery){
			params.put("isUserOfLimitedTransQuery", isUserOfLimitedTransQuery);
			params.put("limitedTransTypes", TransType.getLimitedTransQueryType());
			params.put("limitedExcludeManageBranchCodes", UserOfLimitedTransQueryUtil.getExcludeManageBranchCodes());
		}
		this.transTypeList = isUserOfLimitedTransQuery ? TransType.getLimitedTransQueryType() : TransType.ALL
				.values(); // 加载交易类型
		
		this.statusList = ProcState.ALL.values(); //加载处理状态		
		
		params.put("settStartDate", SysparamCache.getInstance().getWorkDateNotFromCache());
		params.put("settEndDate", SysparamCache.getInstance().getWorkDateNotFromCache());
		if (trans != null) {
			params.put("transSn", trans.getTransSn());
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
			params.put("startCardId", this.getStartCardId());
			params.put("endCardId", this.getEndCardId());
			
			if (isFormMapFieldTrue("useExternalCardSearch")
					&& StringUtils.isNotBlank(trans.getReserved4())) {
				params.put("reserved4", trans.getReserved4()); // 外部号码
			}
		}
		
		if (isCardOrCardDeptRoleLogined()) {// 发卡机构、机构部门
			cardIssuerList = this.branchInfoDAO.findTreeBranchList(this.getLoginBranchCode());
			if (trans == null || StringUtils.isBlank(trans.getCardIssuer())) {
				branchList = cardIssuerList;
			}
		} else if (isMerchantRoleLogined()) {// 商户
			params.put("merNo", this.getSessionUser().getMerchantNo());
			
		} else if (isTerminalRoleLogined()) {// 机具出机方
			params.put("posProvId", this.getSessionUser().getBranchNo());
			
		} else if (isTerminalMaintainRoleLogined()) {// 机具维护方
			params.put("posManageId", this.getSessionUser().getBranchNo());
			
		} else if (isFenzhiRoleLogined()) {// 分支机构
			branchList = new ArrayList<BranchInfo>();
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			params.put("branchCode", this.getSessionUser().getBranchNo());
			
		} else if (isAgentRoleLogined()) {// 营运代理商
			params.put("centProxyNo", this.getSessionUser().getBranchNo());
			
		} else if (isCenterOrCenterDeptRoleLogined()) {// 营运中心、中心部门
		} else {
			throw new BizException("没有权限查询！");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)) {
			params.put("cardIssuers", branchList);
		}
		return params;
	}
	
	/** 取得交易明细 */
	public String detail() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (isCardOrCardDeptRoleLogined()) {
//			params.put("cardIssuer", this.getSessionUser().getBranchNo());
		} else if (isMerchantRoleLogined()) {
			params.put("merNo", this.getSessionUser().getMerchantNo());
		} else if (isTerminalRoleLogined()) {
			params.put("posProvId", this.getSessionUser().getBranchNo());
		} else if (isTerminalMaintainRoleLogined()) {
			params.put("posManageId", this.getSessionUser().getBranchNo());
		} else if (isFenzhiRoleLogined()) {
			branchList = new ArrayList<BranchInfo>();
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			params.put("branchCode", this.getSessionUser().getBranchNo());
		} else if (isAgentRoleLogined()) {
			params.put("centProxyNo", this.getSessionUser().getBranchNo());
		} else if (isCenterOrCenterDeptRoleLogined()) {
		} else {
			throw new BizException("没有权限查询！");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)) {
			params.put("cardIssuers", branchList);
		}
		params.put("transSn", this.trans.getTransSn());
		
		this.trans = (Trans) this.transDao.findByPkWithCheck(params);
		Assert.notNull(this.trans, "交易信息不存在。");
		
		//取得交易账户变动明细
		params.clear();
		params.put("transSn", this.trans.getTransSn());
		this.acctPage = this.transAcctDtlDAO.findTransAcctDtl(params, this.getPageNumber(), this.getPageSize());
		
		//取得交易积分变动明细
		params.clear();
		params.put("transSn", this.trans.getTransSn());
		this.pointPage = this.transPointDtlDAO.findTransPointDtl(params, this.getPageNumber(), this.getPageSize());
		
		//this.log("查询交易["+this.trans.getTransSn()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
		
	}
	
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

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
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
