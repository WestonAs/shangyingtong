package gnete.card.web.makecard;

import flink.etc.DatePair;
import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardSubClassTempDAO;
import gnete.card.dao.PlanDefDAO;
import gnete.card.dao.PlanPrivilegeDAO;
import gnete.card.dao.PlanSubRuleDAO;
import gnete.card.entity.CardExampleDef;
import gnete.card.entity.CardSubClassTemp;
import gnete.card.entity.PlanDef;
import gnete.card.entity.PlanPrivilege;
import gnete.card.entity.PlanSubRule;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.ChargeType;
import gnete.card.entity.type.InsServiceType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.SingleProductService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: SingleProductModelAction.java
 *
 * @description: 单机产品套餐定义
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-2-8
 */
public class SingleProductModelAction extends BaseAction {

	@Autowired
	private SingleProductService singleProductService;
	@Autowired
	private PlanDefDAO planDefDAO;
	@Autowired
	private PlanPrivilegeDAO planPrivilegeDAO;
	@Autowired
	private PlanSubRuleDAO planSubRuleDAO;
	@Autowired
	private CardSubClassTempDAO cardSubClassTempDAO;

	private List<CommonState> statusList;
	private List<CardExampleDef> cardExampleList; // 有效的卡样列表
	private List<ChargeType> chargeTypeList; // 收费标准
	private List<InsServiceType> serviceTypeList; // 业务权限点
	private List<PlanSubRule> planSubRuleList; // 套餐收费子规则
	private List<CardSubClassTemp> tempList; // 卡子类型模板列表 

	private PlanDef planDef; // 套餐定义表
	private String planNo; //套餐编号
	private String cardStyleIds; //卡样编号
	private String[] serviceIds; //业务权限点id
	
	//套餐收费子规则字段
	private String[] subPeriods; //子规则周期
	private String[] subParams; //子规则参数
	private String[] subSendSum; // 赠送卡数量
	
	private String startDate;
	private String endDate;

	// 界面选择时是否单选
	private boolean radio;
	private String hiddenBranchCode;
	
	private Paginater page;
	
	private static final String DEFAULT_URL = "/pages/singleProduct/model/list.do?goBack=goBack";

	@Override
	public String execute() throws Exception {
		// 状态列表
		this.statusList = CommonState.getList();
		this.chargeTypeList = ChargeType.getList();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (planDef != null) {
			params.put("planId", planDef.getPlanId());
			params.put("planName", MatchMode.ANYWHERE.toMatchString(planDef.getPlanName()));
			params.put("status", planDef.getStatus());
			params.put("chargeType", planDef.getChargeType());
			
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
		}
		
		if (isCenterOrCenterDeptRoleLogined()) {
			
		} else if (isFenzhiRoleLogined()) {
			params.put("branchCode", this.getSessionUser().getBranchNo());
		} else {
			throw new BizException("没有权限查看套餐列表");
		}

		this.page = this.singleProductService.findModelPage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}

	public String detail() throws Exception {
		planDef = (PlanDef) this.planDefDAO.findByPk(planDef.getPlanId());
		Assert.notNull(planDef, "套餐编号为[" + planDef.getPlanId() + "]的套餐不存在");
		
		// 套餐拥有的权限
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("planId", planDef.getPlanId());
		params.put("status", CommonState.NORMAL.getValue());
		List<PlanPrivilege> list = this.planPrivilegeDAO.findList(params);
		
		StringBuffer sb = new StringBuffer(128);
		for (PlanPrivilege planPrivilege : list) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			String serviceName = (InsServiceType.ALL.get(planPrivilege.getServiceId()) == null ? "" 
					: InsServiceType.valueOf(planPrivilege.getServiceId()).getName());
			sb.append(serviceName);
		}
		planDef.setAuthority(sb.toString());
		
		// 套餐收费子规则
		params.clear();
		params.put("planId", planDef.getPlanId());
		planSubRuleList = this.planSubRuleDAO.findList(params);
		
		// 套餐关联的卡样
		params.clear();
		params.put("planNo", planDef.getPlanId());
		cardExampleList = this.singleProductService.findSytleList(params);
		
		return DETAIL;
	}

	/** 
	 * 新增初始化页面
	 * @return
	 * @throws Exception
	 */
	public String showAdd() throws Exception {
		if (!isFenzhiRoleLogined()) {
			throw new BizException("没有权限操作！");
		}
		
		this.chargeTypeList = ChargeType.getList();
//		this.cardExampleList = this.singleProductService.findSytleList(this.getLoginBranchCode());
		this.serviceTypeList = InsServiceType.getAllByValue();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", this.getLoginBranchCode());
		params.put("status", CommonState.NORMAL.getValue());
		this.tempList = this.cardSubClassTempDAO.findList(params);
		
		return ADD;
	}
	
	/**
	 * 新增套餐定义
	 */
	public String add() throws Exception {
		List<PlanSubRule> subRuleList = new ArrayList<PlanSubRule>();
		for (int i = 0; i < subPeriods.length; i++) {
			PlanSubRule rule = new PlanSubRule();
			
			rule.setPeriod(NumberUtils.toInt(subPeriods[i]));
			rule.setRuleParam(new BigDecimal(subParams[i]));
			rule.setSendNum(NumberUtils.toInt(subSendSum[i]));
			
			subRuleList.add(rule);
		}
		
		this.singleProductService.addModel(planDef, cardStyleIds, serviceIds, subRuleList, getSessionUser());
		
		String msg = LogUtils.r("新增套餐定义[{0}]成功。", planDef.getPlanId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(DEFAULT_URL, msg);
		
		return SUCCESS;
	}
	
	/**
	 * 修改初始化页面
	 * @return
	 * @throws Exception
	 */
	public String showModify() throws Exception {
		return MODIFY;
	}
	
	/**
	 * 修改套餐
	 * @return
	 * @throws Exception
	 */
	public String modify() throws Exception {
		return SUCCESS;
	}
	
	/**
	 *  套餐选择器
	 * @return
	 * @throws Exception
	 */
	public String showSelect() throws Exception {
		return "select";
	}
	
	/**
	 *  套餐选择器
	 * @return
	 * @throws Exception
	 */
	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (planDef != null) {
			params.put("planId", planDef.getPlanId());
			params.put("planName", MatchMode.ANYWHERE.toMatchString(planDef.getPlanName()));
			params.put("status", CommonState.NORMAL.getValue());
			params.put("branchCode", hiddenBranchCode);
		}
		
		this.page = this.singleProductService.findModelPage(params, this.getPageNumber(), Constants.DEFAULT_SELECT_PAGE_SIZE);
		return "data";
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<CommonState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CommonState> statusList) {
		this.statusList = statusList;
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

	public PlanDef getPlanDef() {
		return planDef;
	}

	public void setPlanDef(PlanDef planDef) {
		this.planDef = planDef;
	}

	public List<CardExampleDef> getCardExampleList() {
		return cardExampleList;
	}

	public void setCardExampleList(List<CardExampleDef> cardExampleList) {
		this.cardExampleList = cardExampleList;
	}

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public List<ChargeType> getChargeTypeList() {
		return chargeTypeList;
	}

	public void setChargeTypeList(List<ChargeType> chargeTypeList) {
		this.chargeTypeList = chargeTypeList;
	}

	public String[] getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(String[] serviceIds) {
		this.serviceIds = serviceIds;
	}

	public List<InsServiceType> getServiceTypeList() {
		return serviceTypeList;
	}

	public void setServiceTypeList(List<InsServiceType> serviceTypeList) {
		this.serviceTypeList = serviceTypeList;
	}

	public String getCardStyleIds() {
		return cardStyleIds;
	}

	public void setCardStyleIds(String cardStyleIds) {
		this.cardStyleIds = cardStyleIds;
	}

	public boolean isRadio() {
		return radio;
	}

	public void setRadio(boolean radio) {
		this.radio = radio;
	}

	public String getHiddenBranchCode() {
		return hiddenBranchCode;
	}

	public void setHiddenBranchCode(String hiddenBranchCode) {
		this.hiddenBranchCode = hiddenBranchCode;
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

	public String[] getSubPeriods() {
		return subPeriods;
	}

	public void setSubPeriods(String[] subPeriods) {
		this.subPeriods = subPeriods;
	}

	public List<PlanSubRule> getPlanSubRuleList() {
		return planSubRuleList;
	}

	public void setPlanSubRuleList(List<PlanSubRule> planSubRuleList) {
		this.planSubRuleList = planSubRuleList;
	}

	public List<CardSubClassTemp> getTempList() {
		return tempList;
	}

	public void setTempList(List<CardSubClassTemp> tempList) {
		this.tempList = tempList;
	}
}
