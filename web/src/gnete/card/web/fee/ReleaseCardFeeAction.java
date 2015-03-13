package gnete.card.web.fee;

import flink.etc.MatchMode;
import flink.util.AmountUtils;
import flink.util.CommonHelper;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardMerchGroupDAO;
import gnete.card.dao.CurrCodeDAO;
import gnete.card.dao.FeeCycleStageDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.ReleaseCardFeeDAO;
import gnete.card.dao.ReleaseCardFeeDtlDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardMerchGroup;
import gnete.card.entity.FeeCycleStage;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.ReleaseCardFee;
import gnete.card.entity.ReleaseCardFeeDtl;
import gnete.card.entity.ReleaseCardFeeKey;
import gnete.card.entity.flag.GroupFlag;
import gnete.card.entity.state.CurrCodeState;
import gnete.card.entity.type.FeeCycleType;
import gnete.card.entity.type.FeeFeeType;
import gnete.card.entity.type.FeePrincipleType;
import gnete.card.entity.type.ReleaseCardFeeFeeType;
import gnete.card.entity.type.ReleaseCardFeeTransType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SelectMerchType;
import gnete.card.entity.type.SetModeType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.ReleaseCardFeeService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: ReleaseCardFeeAction.java
 * @description: 发卡机构运营手续费设置
 */
public class ReleaseCardFeeAction extends BaseAction {
	@Autowired
	private ReleaseCardFeeDAO releaseCardFeeDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private ReleaseCardFeeService releaseCardFeeService;
	@Autowired
	private CardMerchGroupDAO cardMerchGroupDAO;
	@Autowired
	private CurrCodeDAO currCodeDAO;
	@Autowired
	private ReleaseCardFeeDtlDAO releaseCardFeeDtlDAO;
	@Autowired
	private FeeCycleStageDAO feeCycleStageDAO;
	
	private Paginater page;
	private Paginater releaseCardFeeDtlPage;
	private Paginater feeCycleStagePage;
	private ReleaseCardFee releaseCardFee;
	private ReleaseCardFeeKey key;
	private String branchCode;
	//private Collection costCycleList;
	private Collection feeTypeList;
	private Collection transTypeList;
	private List<FeeCycleType> feeCycleTypeList;
	private List<FeePrincipleType> feePrincipleList;
	private List<FeeCycleType> adjustCycleTypeList;
	
	private List<BranchInfo> branchList;
	
	private List selectMerchTypeList;
	
	private List<CardMerchGroup> merchGroupList;
	
	private boolean showCard = false;
	private boolean showRelaseCardFeeDtl = false;
	private boolean showFeeCycleStage = false;
	
	private String[] ulimit;
	private String[] feeRate;
	private String[] originalUlimit;
	private String feeRateComma;
	private String maxAmtComma;
	private String minAmtComma;
	
	private String merchs;
	private List currCodeList;
	private String updateUlmoney;
	private String branchType;
	private Collection branchTypeList;
	private List<ReleaseCardFeeDtl> releasecardFeeDtlList;
	private List<FeeCycleStage> feeCycleStageList;
	
	@Override
	public String execute() throws Exception {
		feeTypeList = ReleaseCardFeeFeeType.getList();
		transTypeList = ReleaseCardFeeTransType.getList();
		Map<String, Object> param = new HashMap<String, Object>();
		
		if(releaseCardFee!=null){
			param.put("branchCode", releaseCardFee.getBranchCode());
			param.put("branchName", MatchMode.ANYWHERE.toMatchString(releaseCardFee.getBranchName()));
			if("true".equals(formMap.get("generalMerch"))){// 选中了通用商户checkbox
				param.put("merchId", 0);
			} else {
				param.put("merchName", MatchMode.ANYWHERE.toMatchString(releaseCardFee.getMerchName()));
			}
			param.put("chlName", MatchMode.ANYWHERE.toMatchString(releaseCardFee.getChlName()));
			param.put("chlCode", releaseCardFee.getChlCode());
			param.put("cardBin", releaseCardFee.getCardBin());
			param.put("feeType", releaseCardFee.getFeeType());
			param.put("transType", releaseCardFee.getTransType());
			param.put("costCycle", releaseCardFee.getCostCycle());
			param.put("feeCycleType", releaseCardFee.getFeeCycleType());
			param.put("status", releaseCardFee.getStatus());
		}
		
		
		if (isCardRoleLogined()){ // 发卡机构
			showCard = true;
			branchList = this.getMyCardBranch();
			param.put("branchList", branchList);
		} else if (isMerchantRoleLogined()){ // 商户
			param.put("merchId", this.getSessionUser().getMerchantNo());
			
		} else if(isFenzhiRoleLogined()){ // 分支机构
			param.put("chlCode", this.getSessionUser().getBranchNo());
			
		} else if (isCenterOrCenterDeptRoleLogined()){ // 营运中心或部门
			
		} else {
			throw new BizException("没有权限查询发卡机构手续费参数！");
		}
		
		page = this.releaseCardFeeDAO.findReleaseCardFee(param, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	//取得明细
	public String detail() throws Exception {
		
		this.releaseCardFee = (ReleaseCardFee) this.releaseCardFeeDAO.findByPk(this.getKey());
		
		//分段计费方式，查看手续费明细
		if(FeeFeeType.MONEY_STAGE.getValue().equals(this.releaseCardFee.getFeeType())||
				FeeFeeType.MONEY_STAGE_CUM.getValue().equals(this.releaseCardFee.getFeeType())||
				FeeFeeType.TRADE_NUM_STAGE.getValue().equals(this.releaseCardFee.getFeeType())||
				FeeFeeType.TRADE_NUM_STAGE_CUM.getValue().equals(this.releaseCardFee.getFeeType())){
			this.showRelaseCardFeeDtl = true;
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("feeRuleId", this.releaseCardFee.getFeeRuleId());
			this.releaseCardFeeDtlPage = this.releaseCardFeeDtlDAO.findReleaseCardFeeDtl(params, this.getPageNumber(), this.getPageSize());
		}
		
		//协议月，查看周期分期规则表
		if(this.releaseCardFee.getFeeCycleType().equals(FeeCycleType.PROTOCOL_MONTH.getValue())){
			this.showFeeCycleStage = true;
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("feeRuleId", this.releaseCardFee.getFeeRuleId());
			this.feeCycleStagePage = this.feeCycleStageDAO.findFeeCycleStage(params, this.getPageNumber(), this.getPageSize());
		}
		
		return DETAIL;
	}
	
	public String showAdd() throws Exception {
		if (!isFenzhiRoleLogined()){
			throw new BizException("没有权限设置发卡机构手续费参数！");
		}
		
		//costCycleList = CostCycleType.getMonth();
		feeTypeList = ReleaseCardFeeFeeType.getList();
		transTypeList = ReleaseCardFeeTransType.getList();
		this.branchTypeList = GroupFlag.getAll();
		branchList = branchInfoDAO.findByManange(getSessionUser().getBranchNo());
		selectMerchTypeList = SelectMerchType.getAll();
		feeCycleTypeList = FeeCycleType.getAll();
		feePrincipleList = FeePrincipleType.getList();
		this.currCodeList = currCodeDAO.findCurrCode(CurrCodeState.NORMAL.getValue());
		this.adjustCycleTypeList = FeeCycleType.getAdjustCycleType();
		
		this.releaseCardFee = new ReleaseCardFee();
		releaseCardFee.setMerchFlag(SelectMerchType.SINGLE.getValue());
		return ADD;
	}
	
	public void getMerchGroup() throws Exception {
		String branchCode = request.getParameter("branchCode");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", branchCode);
		
		this.merchGroupList = this.cardMerchGroupDAO.findCardMerchGroupList(params);
		
		StringBuffer sb = new StringBuffer(128);
		for (CardMerchGroup cardMerchGroup : merchGroupList){
			sb.append("<option value=\"").append(cardMerchGroup.getGroupId()).append("\">")
				.append(cardMerchGroup.getGroupName()).append("</option>");
		}
		this.respond(sb.toString());
	}
	
	public String add() throws Exception {
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.releaseCardFee.getBranchCode());
		this.releaseCardFee.setChlCode(this.getSessionUser().getBranchNo());
		this.releaseCardFee.setCurCode(branchInfo.getCurCode());
		
		// 设置集团标志
		this.releaseCardFee.setGroupFlag(this.getBranchType());
		
		// 当选择了售卡充值、次卡充值、赠券充值、通用积分充值、专属积分充值时，商户标志填"商户"，商户填0
		if (ReleaseCardFeeTransType.DEPOSIT.getValue().equals(this.releaseCardFee.getTransType())||
				ReleaseCardFeeTransType.TIME_CARD_DEPOSIT.getValue().equals(this.releaseCardFee.getTransType())||
				ReleaseCardFeeTransType.COUPON_DEPOSIT.getValue().equals(this.releaseCardFee.getTransType())||
				ReleaseCardFeeTransType.COMMON_PT_DEPOSIT.getValue().equals(this.releaseCardFee.getTransType())||
				ReleaseCardFeeTransType.SPECIAL_PT_DEPOSIT.getValue().equals(this.releaseCardFee.getTransType())){
			this.releaseCardFee.setMerchFlag(SelectMerchType.SINGLE.getValue());
			this.releaseCardFee.setMerchId("0");
		}
		
		List<ReleaseCardFee> cardFeeList = new ArrayList<ReleaseCardFee>();
		
		if (StringUtils.isNotEmpty(merchs)) {
			String[] merchArray = merchs.split(",");
			
			for (int j = 0; j < merchArray.length; j++) {
				ReleaseCardFee fee = new ReleaseCardFee();
				
				this.formToBo(fee, this.releaseCardFee);
				
				fee.setMerchId(merchArray[j]);
				
				if(StringUtils.isEmpty(this.releaseCardFee.getCardBin())){ //卡BIN
					fee.setCardBin("*");
				} else{
					fee.setCardBin(this.releaseCardFee.getCardBin());
				}
				
				//分段，取最小的费率插入分支机构与发卡机构手续费参数
				if (releaseCardFee.getFeeType().equals(ReleaseCardFeeFeeType.TRADE_NUM_STAGE_CUM.getValue())
						|| releaseCardFee.getFeeType().equals(ReleaseCardFeeFeeType.TRADE_NUM_STAGE.getValue())
						|| releaseCardFee.getFeeType().equals(ReleaseCardFeeFeeType.MONEY_STAGE.getValue())
						|| releaseCardFee.getFeeType().equals(ReleaseCardFeeFeeType.MONEY_STAGE_CUM.getValue())) { 
					fee.setUlMoney(AmountUtils.parseBigDecimal(ulimit[0]));
					fee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[0]));
					
				} 
				//交易笔数、金额固定比例
				else {
					fee.setUlMoney(Constants.FEE_MAXACCOUNT);
					fee.setFeeRate(AmountUtils.parseBigDecimal(feeRateComma));
				}
				
				cardFeeList.add(fee);
			}
		}
		// 没有选择商户
		else {
			ReleaseCardFee fee = new ReleaseCardFee();
			
			/*try {
				BeanUtils.copyProperties(fee, this.releaseCardFee);
			} catch (Exception e) {
				throw new BizException("复制对象发生异常。");
			}*/
			
			this.formToBo(fee, this.releaseCardFee);
			
			fee.setMerchId("0"); // 商户为空，默认为通用，填写"0"
			
			// 卡BIN，如果为空，默认为通用，填写"*"
			if(StringUtils.isEmpty(this.releaseCardFee.getCardBin())){
				fee.setCardBin("*");
			} else{
				fee.setCardBin(this.releaseCardFee.getCardBin());
			}
			
			//分段，取最小的费率插入分支机构与发卡机构手续费参数
			if (releaseCardFee.getFeeType().equals(ReleaseCardFeeFeeType.TRADE_NUM_STAGE_CUM.getValue())
					|| releaseCardFee.getFeeType().equals(ReleaseCardFeeFeeType.TRADE_NUM_STAGE.getValue())
					|| releaseCardFee.getFeeType().equals(ReleaseCardFeeFeeType.MONEY_STAGE.getValue())
					|| releaseCardFee.getFeeType().equals(ReleaseCardFeeFeeType.MONEY_STAGE_CUM.getValue())) { 
				fee.setUlMoney(AmountUtils.parseBigDecimal(ulimit[ulimit.length-1]));
				fee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[feeRate.length-1]));
			} 
			// 交易笔数、金额固定比例
			else {
				fee.setUlMoney(Constants.FEE_MAXACCOUNT);
				fee.setFeeRate(AmountUtils.parseBigDecimal(feeRateComma));
			}
			
			cardFeeList.add(fee);
		}
		
		releaseCardFeeService.addReleaseCardFee(cardFeeList.toArray(new ReleaseCardFee[cardFeeList.size()]), ulimit, feeRate, this.getSessionUser());
		
		String msg = "添加发卡机构[" + releaseCardFee.getBranchCode() + "分润手续费参数成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/fee/releaseCardFee/list.do", msg);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		
		if (!(isFenzhiRoleLogined() || isCenterOrCenterDeptRoleLogined())) {
			throw new BizException("没有权限设置发卡机构分润手续费参数！");
		}
		
		this.feePrincipleList = FeePrincipleType.getList();
		
		releaseCardFee = (ReleaseCardFee)this.releaseCardFeeDAO.findByPk(key);
		this.feeRateComma = this.releaseCardFee.getFeeRate().toString();
		
		// 金額固定比例保底封顶
		if(FeeFeeType.MONEY_RATE_MAX_MIN.getValue().equals(this.releaseCardFee.getFeeType())){ 
			this.maxAmtComma = this.releaseCardFee.getMaxAmt().toString();
			this.minAmtComma = this.releaseCardFee.getMinAmt().toString();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		//分段计费方式，显示分段明细
		if(FeeFeeType.TRADE_NUM_STAGE.getValue().equals(releaseCardFee.getFeeType())||
				FeeFeeType.TRADE_NUM_STAGE_CUM.getValue().equals(releaseCardFee.getFeeType())||
				FeeFeeType.MONEY_STAGE_CUM.getValue().equals(releaseCardFee.getFeeType())||
				FeeFeeType.MONEY_STAGE.getValue().equals(releaseCardFee.getFeeType())){
			
			this.showRelaseCardFeeDtl = true;
			
			params.clear();
			params.put("feeRuleId", releaseCardFee.getFeeRuleId());
			this.releasecardFeeDtlList = this.releaseCardFeeDtlDAO.getReleaseCardFeeDtlList(params);
			getOrderList(releasecardFeeDtlList);
		}
		
		//协议月，显示周期明细
		if(FeeCycleType.PROTOCOL_MONTH.getValue().equals(this.releaseCardFee.getFeeCycleType())){
			this.showFeeCycleStage = true;
			/*params.clear();
			params.put("feeRuleId", releaseCardFee.getFeeRuleId());
			this.feeCycleStageList = this.feeCycleStageDAO.getFeeCycleStageList(params);*/
		}
		
		//商户
		if (releaseCardFee.getMerchId().equals("0")){ //通用商户
			releaseCardFee.setMerchName("");
		} else {
			if (SelectMerchType.SINGLE.getValue().equals(releaseCardFee.getMerchFlag())) {
				releaseCardFee.setMerchName(((MerchInfo)merchInfoDAO.findByPk(releaseCardFee.getMerchId())).getMerchName());
			} else {
				params.clear();
				params.put("groupId", releaseCardFee.getMerchId());
				releaseCardFee.setMerchName(this.cardMerchGroupDAO.findCardMerchGroupList(params).get(0).getGroupName());
			}
		}
		
		this.updateUlmoney = releaseCardFee.getUlMoney().toString();
		return MODIFY;
	}
	
	public String modify() throws Exception {
		
		List<ReleaseCardFeeDtl> releaseCardFeeDtlList = new ArrayList<ReleaseCardFeeDtl>();
		
		//分段计费方式
		if (this.releaseCardFee.getFeeType().equals(FeeFeeType.TRADE_NUM_STAGE_CUM.getValue())
				|| this.releaseCardFee.getFeeType().equals(FeeFeeType.TRADE_NUM_STAGE.getValue())
				|| this.releaseCardFee.getFeeType().equals(FeeFeeType.MONEY_STAGE.getValue())
				|| this.releaseCardFee.getFeeType().equals(FeeFeeType.MONEY_STAGE_CUM.getValue())) { 
			for(int i=0; i < this.ulimit.length; i++){
				ReleaseCardFeeDtl FeeDtl = new ReleaseCardFeeDtl();
				FeeDtl.setUlMoney(AmountUtils.parseBigDecimal(ulimit[i]));
				FeeDtl.setFeeRate(AmountUtils.parseBigDecimal(feeRate[i]));
				releaseCardFeeDtlList.add(FeeDtl);
			}
			
			this.releaseCardFee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[this.ulimit.length-1]));
		} 
		//其他
		else {
			this.releaseCardFee.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
			
			//金额固定比例保底封顶
			if(FeeFeeType.MONEY_RATE_MAX_MIN.getValue().equals(this.releaseCardFee.getFeeType())){
				this.releaseCardFee.setMinAmt(AmountUtils.parseBigDecimal(this.getMinAmtComma()));
				this.releaseCardFee.setMaxAmt(AmountUtils.parseBigDecimal(this.getMaxAmtComma()));
			} 
			//按交易笔数，将单笔最高手续费和最低手续费置零
			else {
				this.releaseCardFee.setMinAmt(new BigDecimal(0.0));
				this.releaseCardFee.setMaxAmt(new BigDecimal(0.0));
			}
		}
		
		this.releaseCardFeeService.modifyReleaseCardFee(releaseCardFee, releaseCardFeeDtlList.toArray(new ReleaseCardFeeDtl[releaseCardFeeDtlList.size()]), originalUlimit, this.getSessionUserCode());
		
		String msg = "修改发卡机构手续费参数["+ releaseCardFee.getBranchCode() +"]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/fee/releaseCardFee/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if (!isFenzhiRoleLogined()){
			throw new BizException("没有权限设置发卡机构分润手续费参数！");
		}
		releaseCardFee.setBranchCode(branchCode);
		releaseCardFee.setCardBin(request.getParameter("cardBin"));
		releaseCardFee.setTransType(request.getParameter("transType"));
		releaseCardFee.setMerchId(request.getParameter("merchId"));
		releaseCardFee.setUlMoney(new BigDecimal(request.getParameter("ulMoney")));
		releaseCardFee.setUpdateBy(getSessionUserCode());
		releaseCardFeeService.deleteReleaseCardFee(releaseCardFee, this.getSessionUserCode());
		String msg = "删除发卡机构分润手续费参数["+ releaseCardFee.getBranchCode() +"]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/fee/releaseCardFee/list.do", msg);
		return SUCCESS;
	}
	// 停用机构
		public String stop() throws Exception {
			if (!isFenzhiRoleLogined()){
				throw new BizException("没有权限设置发卡机构分润手续费参数！");
			}
			releaseCardFee.setBranchCode(branchCode);
			releaseCardFee.setMerchId(request.getParameter("merchId"));
			releaseCardFee.setCardBin(request.getParameter("cardBin"));
			releaseCardFee.setTransType(request.getParameter("transType"));
			releaseCardFee.setUlMoney(new BigDecimal(request.getParameter("ulMoney")));
			releaseCardFee.setUpdateBy(getSessionUserCode());
			this.releaseCardFeeService.stopReleaseCardFee(releaseCardFee, this.getSessionUserCode());
			
			String msg = "停用发卡机构分润手续费参数["+ releaseCardFee.getBranchCode() +"]成功";
			this.addActionMessage("/fee/releaseCardFee/list.do", msg);
			this.log(msg, UserLogType.UPDATE);
			
			return SUCCESS;
		}
		
		// 启用机构
		public String start() throws Exception {
			if (!isFenzhiRoleLogined()){
				throw new BizException("没有权限设置发卡机构分润手续费参数！");
			}
			releaseCardFee.setBranchCode(branchCode);
			releaseCardFee.setCardBin(request.getParameter("cardBin"));
			releaseCardFee.setTransType(request.getParameter("transType"));
			releaseCardFee.setMerchId(request.getParameter("merchId"));
			releaseCardFee.setUlMoney(new BigDecimal(request.getParameter("ulMoney")));
			releaseCardFee.setUpdateBy(getSessionUserCode());
			this.releaseCardFeeService.startReleaseCardFee(releaseCardFee, this.getSessionUserCode());
			
			String msg = "启用发卡机构分润手续费参数["+ releaseCardFee.getBranchCode() +"]成功";
			this.addActionMessage("/fee/releaseCardFee/list.do", msg);
			this.log(msg, UserLogType.UPDATE);
			
			return SUCCESS;
		}
	
	// 查询指定交易类型的计费方式列表，服务端查询，返回到前端
	public String feeTypeList(){
		
		String transType = this.releaseCardFee.getTransType();
		if(transType.equals("3")||transType.equals("4")||transType.equals("5")){
			// 交易方式为次卡 、专属积分、积分兑换礼品为按按交易笔数
			this.feeTypeList = ReleaseCardFeeFeeType.getTradeSum();
		}
		// 其他
		else{
			this.feeTypeList = ReleaseCardFeeFeeType.getList();
		}
		return "feeTypeList";
	}

	// 查询指定计费方式的计费原则列表，服务端查询，返回到前端
	public String queryFeePrincipleList(){
		String feeType = this.releaseCardFee.getFeeType();
		
		if(ReleaseCardFeeFeeType.TRADE_NUM.getValue().equals(feeType)
				||ReleaseCardFeeFeeType.MONEY_RATE_MAX_MIN.getValue().equals(feeType)){ //按交易笔数、金额固定比例保底封顶
			this.feePrincipleList = FeePrincipleType.getTradeNum();
		}
		// 其他
		else{
			this.feePrincipleList = FeePrincipleType.getList();
		}
		
		return "feePrincipleList";
	}
	
	/**
	 * 返回分润模式
	 * @return
	 */
	public String getSetMode() {
		if(super.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
			return SetModeType.SHARE.getValue();
		} else {
			return "";
		}
	}
	
	/**
	 * 返回登录机构
	 * @return
	 */
	public String getParent() {
		return super.getSessionUser().getBranchNo();
	}
	
	/**
	 * 对列表升序排序
	 * @param orderedList
	 */
	private void getOrderList(List orderedList){
		Collections.sort(orderedList, new Comparator() {
			public int compare(Object o1, Object o2) {
				ReleaseCardFeeDtl dtl1 = (ReleaseCardFeeDtl) o1;
				ReleaseCardFeeDtl dtl2 = (ReleaseCardFeeDtl) o2;
				
				return (dtl2.getFeeRate()).compareTo(dtl1.getFeeRate());
			}
		});
	}
	
	private void formToBo(ReleaseCardFee dest, ReleaseCardFee src) throws Exception{
		dest.setBranchCode(src.getBranchCode());
		dest.setChlCode(src.getChlCode());
		dest.setTransType(src.getTransType());
		dest.setFeeCycleType(src.getFeeCycleType()); //计费周期类型
		dest.setEffDate(src.getEffDate()); //生效日期
		dest.setExpirDate(src.getExpirDate()); //失效日期
		dest.setFeePrinciple(src.getFeePrinciple()); //计费原则
		dest.setCostCycle(src.getCostCycle());
		dest.setFeeType(src.getFeeType());
		dest.setMerchFlag(src.getMerchFlag());
		dest.setCurCode(src.getCurCode());
		dest.setGroupFlag(src.getGroupFlag());
		dest.setFeeDate(src.getFeeDate());
		dest.setAdjustMonths(src.getAdjustMonths());
		dest.setAdjustCycleType(src.getAdjustCycleType());  //调整周期
		
		if(CommonHelper.isNotEmpty(this.getMinAmtComma())){//单笔封顶手续费
			dest.setMinAmt(AmountUtils.parseBigDecimal(this.getMinAmtComma()));
		}
		if(CommonHelper.isNotEmpty(this.getMaxAmtComma())){//单笔保底手续费
			dest.setMaxAmt(AmountUtils.parseBigDecimal(this.getMaxAmtComma()));
		}
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public ReleaseCardFee getReleaseCardFee() {
		return releaseCardFee;
	}

	public void setReleaseCardFee(ReleaseCardFee releaseCardFee) {
		this.releaseCardFee = releaseCardFee;
	}

	public ReleaseCardFeeKey getKey() {
		return key;
	}

	public void setKey(ReleaseCardFeeKey key) {
		this.key = key;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Collection getFeeTypeList() {
		return feeTypeList;
	}

	public void setFeeTypeList(Collection feeTypeList) {
		this.feeTypeList = feeTypeList;
	}

	public Collection getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(Collection transTypeList) {
		this.transTypeList = transTypeList;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public List getSelectMerchTypeList() {
		return selectMerchTypeList;
	}

	public void setSelectMerchTypeList(List selectMerchTypeList) {
		this.selectMerchTypeList = selectMerchTypeList;
	}

	public String[] getUlimit() {
		return ulimit;
	}

	public void setUlimit(String[] ulimit) {
		this.ulimit = ulimit;
	}

	public String[] getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(String[] feeRate) {
		this.feeRate = feeRate;
	}

	public String getMerchs() {
		return merchs;
	}

	public void setMerchs(String merchs) {
		this.merchs = merchs;
	}

	public List getCurrCodeList() {
		return currCodeList;
	}

	public void setCurrCodeList(List currCodeList) {
		this.currCodeList = currCodeList;
	}

	public void setMerchGroupList(List<CardMerchGroup> merchGroupList) {
		this.merchGroupList = merchGroupList;
	}

	public List<CardMerchGroup> getMerchGroupList() {
		return merchGroupList;
	}

	public String getUpdateUlmoney() {
		return updateUlmoney;
	}

	public void setUpdateUlmoney(String updateUlmoney) {
		this.updateUlmoney = updateUlmoney;
	}

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	public Collection getBranchTypeList() {
		return branchTypeList;
	}

	public void setBranchTypeList(Collection branchTypeList) {
		this.branchTypeList = branchTypeList;
	}

	public String getFeeRateComma() {
		return feeRateComma;
	}

	public void setFeeRateComma(String feeRateComma) {
		this.feeRateComma = feeRateComma;
	}

	public List<FeeCycleType> getFeeCycleTypeList() {
		return feeCycleTypeList;
	}

	public void setFeeCycleTypeList(List<FeeCycleType> feeCycleTypeList) {
		this.feeCycleTypeList = feeCycleTypeList;
	}

	public List<FeePrincipleType> getFeePrincipleList() {
		return feePrincipleList;
	}

	public void setFeePrincipleList(List<FeePrincipleType> feePrincipleList) {
		this.feePrincipleList = feePrincipleList;
	}

	public List<ReleaseCardFeeDtl> getReleasecardFeeDtlList() {
		return releasecardFeeDtlList;
	}

	public void setReleasecardFeeDtlList(
			List<ReleaseCardFeeDtl> releasecardFeeDtlList) {
		this.releasecardFeeDtlList = releasecardFeeDtlList;
	}

	public boolean isShowRelaseCardFeeDtl() {
		return showRelaseCardFeeDtl;
	}

	public void setShowRelaseCardFeeDtl(boolean showRelaseCardFeeDtl) {
		this.showRelaseCardFeeDtl = showRelaseCardFeeDtl;
	}

	public Paginater getReleaseCardFeeDtlPage() {
		return releaseCardFeeDtlPage;
	}

	public void setReleaseCardFeeDtlPage(Paginater releaseCardFeeDtlPage) {
		this.releaseCardFeeDtlPage = releaseCardFeeDtlPage;
	}

	public Paginater getFeeCycleStagePage() {
		return feeCycleStagePage;
	}

	public void setFeeCycleStagePage(Paginater feeCycleStagePage) {
		this.feeCycleStagePage = feeCycleStagePage;
	}

	public String getMaxAmtComma() {
		return maxAmtComma;
	}

	public void setMaxAmtComma(String maxAmtComma) {
		this.maxAmtComma = maxAmtComma;
	}

	public String getMinAmtComma() {
		return minAmtComma;
	}

	public void setMinAmtComma(String minAmtComma) {
		this.minAmtComma = minAmtComma;
	}

	public List<FeeCycleStage> getFeeCycleStageList() {
		return feeCycleStageList;
	}

	public void setFeeCycleStageList(List<FeeCycleStage> feeCycleStageList) {
		this.feeCycleStageList = feeCycleStageList;
	}

	public boolean isShowFeeCycleStage() {
		return showFeeCycleStage;
	}

	public void setShowFeeCycleStage(boolean showFeeCycleStage) {
		this.showFeeCycleStage = showFeeCycleStage;
	}

	public String[] getOriginalUlimit() {
		return originalUlimit;
	}

	public void setOriginalUlimit(String[] originalUlimit) {
		this.originalUlimit = originalUlimit;
	}

	public List<FeeCycleType> getAdjustCycleTypeList() {
		return adjustCycleTypeList;
	}

	public void setAdjustCycleTypeList(List<FeeCycleType> adjustCycleTypeList) {
		this.adjustCycleTypeList = adjustCycleTypeList;
	}

}
