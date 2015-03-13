package gnete.card.web.makecard;

import flink.etc.DatePair;
import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.util.SpringContext;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardissuerPlanFeeDAO;
import gnete.card.dao.CardissuerPlanFeeRuleDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardissuerPlanFee;
import gnete.card.entity.CardissuerPlanFeeRule;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.state.CostRecordState;
import gnete.card.entity.type.ChargeType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.SingleProductService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: SingleProductChargeAction.java
 *
 * @description:  单机产品发卡机构套餐费用维护
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-2-13
 */
public class SingleProductPlanFeeAction extends BaseAction {

	@Autowired
	private SingleProductService singleProductService;
	@Autowired
	private CardissuerPlanFeeDAO cardissuerPlanFeeDAO;
	@Autowired
	private CardissuerPlanFeeRuleDAO cardissuerPlanFeeRuleDAO;

	private CardissuerPlanFee cardissuerPlanFee;
	private List<CardissuerPlanFeeRule> planFeeRuleList;
	
	private List<CommonState> statusList;
	private List<ChargeType> chargeTypeList;
	
	/** 发卡机构名称 */
	private String cardBranchName;
	/** 周期 */
	private String[] subPeriods;
	/** 规则参数 */
	private String[] subParams; 
	/** 赠送卡数量 */
	private String[] subSendSum;
	
	/** 费用产生起始日期 */
	private String startDate;
	/** 费用产生结束日期 */
	private String endDate;
	
	private Paginater page;
	
	private static final String DEFAULT_URL = "/pages/singleProduct/planfee/list.do?goBack=goBack";

	@Override
	public String execute() throws Exception {
		// 状态列表
		this.statusList = CostRecordState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (cardissuerPlanFee != null) {
			params.put("searchCardBranch", MatchMode.ANYWHERE.toMatchString(cardissuerPlanFee.getIssId()));
			params.put("status", cardissuerPlanFee.getStatus());
			params.put("planId", cardissuerPlanFee.getPlanId());
			
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
		}
		
		// 登录用户为运营中心时，可查看所有信息
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
		}
		// 若当前登录用户角色为运营机构
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			params.put("fenzhiList", this.getMyManageFenzhi());
		} 
		// 发卡机构登录时
		else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
			params.put("branchCode", this.getSessionUser().getBranchNo());
		}
		else {
			throw new BizException("没有权限查看单机产品发卡机构列表");
		}

		this.page = this.singleProductService.findCardPlanFeePage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}

	public String detail() throws Exception {
		
		queryDetail();
		
		return DETAIL;
	}
	
	private void queryDetail() throws Exception {
		this.cardissuerPlanFee = (CardissuerPlanFee) this.cardissuerPlanFeeDAO.findByPk(cardissuerPlanFee.getIssId());
		Assert.notNull(cardissuerPlanFee, "发卡机构[" + cardissuerPlanFee.getIssId() + "]套餐费用记录不存在");
		
		BranchInfoDAO branchInfoDAO = (BranchInfoDAO) SpringContext.getService("branchInfoDAOImpl");
		BranchInfo cardBranch = branchInfoDAO.findBranchInfo(cardissuerPlanFee.getIssId());
		Assert.notNull(cardBranch, "发卡机构[" + cardissuerPlanFee.getIssId() + "]不存在");
		this.cardBranchName = cardBranch.getBranchName();
		
		if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			List<BranchInfo> fenzhiList = this.getMyManageFenzhi();
			
			Assert.isTrue(isManageCard(fenzhiList, cardBranch), "发卡机构[" + cardissuerPlanFee.getIssId() + "]不是当前运营机构所管理的，没有权限");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Assert.notEmpty(cardissuerPlanFee.getPlanId(), "发卡机构[" + cardissuerPlanFee.getIssId() + "]的套餐对应的套餐编号不能为空");
		
		params.put("branchCode", cardissuerPlanFee.getIssId());
		params.put("planId", cardissuerPlanFee.getPlanId());
		this.planFeeRuleList = this.cardissuerPlanFeeRuleDAO.findList(params);
	}
	
	/**
	 * 判断发卡机构是否属于分支管理的
	 * @param fenzhiList
	 * @param cardBranch
	 * @return
	 */
	private boolean isManageCard(List<BranchInfo> fenzhiList, BranchInfo cardBranch) {
		boolean flag = false;
		
		for (BranchInfo fenzhi : fenzhiList) {
			if (StringUtils.equals(cardBranch.getParent(), fenzhi.getBranchCode())) {
				flag = true;
				
				break;
			}
		}
		
		return flag;
	}

	/** 
	 * 修改发卡机构套餐费用初始化页面
	 * @return
	 * @throws Exception
	 */
	public String showModify() throws Exception {
		if (!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			throw new BizException("没有权限操作！");
		}
		
		this.chargeTypeList = ChargeType.getList();
		
		queryDetail();
		
		return MODIFY;
	}
	
	/**
	 * 修改发卡机构套餐费用
	 */
	public String modify() throws Exception {
		
		List<CardissuerPlanFeeRule> feeRuleList = new ArrayList<CardissuerPlanFeeRule>();
		CardissuerPlanFeeRule rule = null;
		for (int i = 0; i < subPeriods.length; i++) {
			rule = new CardissuerPlanFeeRule();
			
			rule.setPeriod(NumberUtils.toInt(subPeriods[i]));
			rule.setRuleParam(new BigDecimal(subParams[i]));
			rule.setSendNum(NumberUtils.toInt(subSendSum[i]));
			
			feeRuleList.add(rule);
		}
		
		this.singleProductService.modifyCardPlanFee(cardissuerPlanFee, feeRuleList, this.getSessionUser());
		
		String msg = LogUtils.r("用户[{0}]修改发卡机构[{1}]套餐费用成功。", this.getSessionUserCode(), cardissuerPlanFee.getIssId());
		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage(DEFAULT_URL, msg);
		
		return SUCCESS;
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
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

	public List<CommonState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CommonState> statusList) {
		this.statusList = statusList;
	}

	public List<CardissuerPlanFeeRule> getPlanFeeRuleList() {
		return planFeeRuleList;
	}

	public void setPlanFeeRuleList(List<CardissuerPlanFeeRule> planFeeRuleList) {
		this.planFeeRuleList = planFeeRuleList;
	}

	public String getCardBranchName() {
		return cardBranchName;
	}

	public void setCardBranchName(String cardBranchName) {
		this.cardBranchName = cardBranchName;
	}

	public List<ChargeType> getChargeTypeList() {
		return chargeTypeList;
	}

	public void setChargeTypeList(List<ChargeType> chargeTypeList) {
		this.chargeTypeList = chargeTypeList;
	}

	public CardissuerPlanFee getCardissuerPlanFee() {
		return cardissuerPlanFee;
	}

	public void setCardissuerPlanFee(CardissuerPlanFee cardissuerPlanFee) {
		this.cardissuerPlanFee = cardissuerPlanFee;
	}

	public String[] getSubPeriods() {
		return subPeriods;
	}

	public void setSubPeriods(String[] subPeriods) {
		this.subPeriods = subPeriods;
	}

	public String[] getSubParams() {
		return subParams;
	}

	public void setSubParams(String[] subParams) {
		this.subParams = subParams;
	}

	public String[] getSubSendSum() {
		return subSendSum;
	}

	public void setSubSendSum(String[] subSendSum) {
		this.subSendSum = subSendSum;
	}

}
