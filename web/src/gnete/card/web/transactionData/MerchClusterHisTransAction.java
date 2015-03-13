package gnete.card.web.transactionData;

import flink.util.DateUtil;
import flink.util.Paginater;
import flink.util.TimeInterval;
import gnete.card.dao.ClusterMerchDAO;
import gnete.card.dao.MerchClusterInfoDAO;
import gnete.card.dao.TransDAO;
import gnete.card.dao.TransDtotalDAO;
import gnete.card.entity.ClusterMerch;
import gnete.card.entity.MerchClusterInfo;
import gnete.card.service.GenerateFileService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

/**
 * 发卡机构商户集群 历史交易明细查询
 */
public class MerchClusterHisTransAction extends BaseAction {

	private static final String IS_GENERATING_MERCH_CLUSTER_HIS_TRANS_EXCEL = "IS_GENERATING_MERCH_CLUSTER_HIS_TRANS_EXCEL";
	@Autowired
	private TransDAO transDao;
	@Autowired
	private TransDtotalDAO transDtotalDAO;
	@Autowired
	private MerchClusterInfoDAO merchClusterInfoDAO;
	@Autowired
	private ClusterMerchDAO clusterMerchDAO;
	@Autowired
	private GenerateFileService generateFileService;

	private Paginater page;

	private String procStatus;// 处理状态
	private String transType;// 交易类型
	private String settStartDate;
	private String settEndDate;
	private String cardIssuer;
	private String merchClusterId;
	private List<MerchClusterInfo> merchClusterInfos = new ArrayList<MerchClusterInfo>();// 商户集群列表，用于页面select标签显示
	private String merchNo;
	private boolean generateExcelTableTitle = true;// 是否生成excel表标题行

	/**
	 * 商户集群历史交易 明细
	 */
	@Override
	public String execute() throws Exception {
		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined() || isCardOrCardDeptRoleLogined())) {
			throw new BizException("没有权限查询！");
		}
		if (settStartDate == null || settEndDate == null) { // 从菜单进入
			if (isCardOrCardDeptRoleLogined()) {
				cardIssuer = getLoginBranchCode();
				merchClusterInfos = merchClusterInfoDAO.findByCardIssuer(getLoginBranchCode());
			}
			settStartDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
			settEndDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
			return LIST;
		}

		Map<String, Object> params = buildParamsMap();
		this.page = this.transDao.findHisTrans(params, this.getPageNumber(), this.getPageSize());

		return LIST;
	}

	/**
	 * 商户集群历史交易 汇总
	 */
	public String listSummary() throws Exception {
		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined() || isCardOrCardDeptRoleLogined())) {
			throw new BizException("没有权限查询！");
		}
		final String strustResult = "listSummary";
		if (settStartDate == null || settEndDate == null) { // 从菜单进入
			if (isCardOrCardDeptRoleLogined()) {
				cardIssuer = getLoginBranchCode();
				merchClusterInfos = merchClusterInfoDAO.findByCardIssuer(getLoginBranchCode());
			}
			settStartDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
			settEndDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
			return strustResult;
		}

		Map<String, Object> params = buildParamsMap();
		this.page = this.transDtotalDAO.listByPage(params, this.getPageNumber(), this.getPageSize());

		return strustResult;
	}

	/**
	 * 手动生成商户集群历史交易明细Excel文件
	 */
	public void generate() throws Exception {
		ActionContext.getContext().getSession()
				.put(IS_GENERATING_MERCH_CLUSTER_HIS_TRANS_EXCEL, Boolean.TRUE);
		try {
			Map<String, Object> params = buildParamsMap();
			logger.info("用户{},机构{}导出商户集群历史交易明细excel...  [params: {}]", new Object[] {
					this.getSessionUserCode(), this.getSessionUser().getBranchNo(), params });
			TimeInterval ti = new TimeInterval();
			this.generateFileService.generateMerchClusterHisTransExcel(response, params,
					this.generateExcelTableTitle);
			logger.info("用户{},机构{}导出商户集群历史交易明细excel完成！用时 {}秒", new Object[] { this.getSessionUserCode(),
					this.getSessionUser().getBranchNo(), ti.getIntervalOfSec() });
		} finally {
			ActionContext.getContext().getSession().remove(IS_GENERATING_MERCH_CLUSTER_HIS_TRANS_EXCEL);
		}
	}

	/**
	 * 手动生成商户集群历史交易汇总Excel文件
	 */
	public void generateSummary() throws Exception {
		ActionContext.getContext().getSession()
				.put(IS_GENERATING_MERCH_CLUSTER_HIS_TRANS_EXCEL, Boolean.TRUE);
		try {
			Map<String, Object> params = buildParamsMap();
			logger.info("用户{},机构{}导出商户集群历史交易汇总excel...  [params: {}]", new Object[] {
					this.getSessionUserCode(), this.getSessionUser().getBranchNo(), params });
			TimeInterval ti = new TimeInterval();
			this.generateFileService.generateMerchClusterHisTransSummaryExcel(response, params);
			logger.info("用户{},机构{}导出商户集群历史交易汇总excel完成！用时 {}秒", new Object[] { this.getSessionUserCode(),
					this.getSessionUser().getBranchNo(), ti.getIntervalOfSec() });
		} finally {
			ActionContext.getContext().getSession().remove(IS_GENERATING_MERCH_CLUSTER_HIS_TRANS_EXCEL);
		}
	}

	/**
	 * ajax请求：判断是否 正生产商户集群历史交易的excel文件
	 */
	public void ajaxIsGeneratingExcel() throws Exception {
		JSONObject json = new JSONObject();
		if (Boolean.TRUE.equals(ActionContext.getContext().getSession().get(
				IS_GENERATING_MERCH_CLUSTER_HIS_TRANS_EXCEL))) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		this.respond(json.toString());
	}

	// ----------------------------- private methods followed ------------------------------

	private Map<String, Object> buildParamsMap() throws BizException {
		if (StringUtils.isBlank(settStartDate) || StringUtils.isBlank(settEndDate)
				|| StringUtils.isBlank(cardIssuer) || StringUtils.isBlank(merchClusterId)) {
			throw new BizException("参数错误");
		}
		if (DateUtil.getDateDiffDays(settEndDate, settStartDate, "yyyyMMdd") > 90) {
			throw new BizException("清算日期范围不能超过90天！");
		}
		merchClusterInfos = merchClusterInfoDAO.findByCardIssuer(cardIssuer);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("settStartDate", settStartDate);
		params.put("settEndDate", settEndDate);
		params.put("startRcvTime", this.getFormMapValue("startRcvTime"));
		params.put("endRcvTime", this.getFormMapValue("endRcvTime"));
		params.put("merNo", merchNo);
		params.put("transType", transType);

		List<ClusterMerch> clusterMerchList = clusterMerchDAO.findByMerchClusterId(merchClusterId);
		if (CollectionUtils.isEmpty(clusterMerchList)) {
			throw new BizException(String.format("没有找到发卡机构商户集群下[%s]的商户！", merchClusterId));
		}
		List<String> merNoList = new ArrayList<String>();
		for (ClusterMerch m : clusterMerchList) {
			merNoList.add(m.getMerchNo());
		}
		params.put("merNoList", merNoList);
		params.put("procStatus", procStatus);
		return params;
	}

	// ----------------------------- getter and setter methods followed ------------------------------

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Paginater getPage() {
		return page;
	}

	public String getProcStatus() {
		return procStatus;
	}

	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
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

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getMerchClusterId() {
		return merchClusterId;
	}

	public void setMerchClusterId(String merchClusterId) {
		this.merchClusterId = merchClusterId;
	}

	public List<MerchClusterInfo> getMerchClusterInfos() {
		return merchClusterInfos;
	}

	public void setMerchClusterInfos(List<MerchClusterInfo> merchClusterInfos) {
		this.merchClusterInfos = merchClusterInfos;
	}

	public String getMerchNo() {
		return merchNo;
	}

	public void setMerchNo(String merchNo) {
		this.merchNo = merchNo;
	}

	public boolean isGenerateExcelTableTitle() {
		return generateExcelTableTitle;
	}

	public void setGenerateExcelTableTitle(boolean generateExcelTableTitle) {
		this.generateExcelTableTitle = generateExcelTableTitle;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
}
