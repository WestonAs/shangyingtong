package gnete.card.web.fee;

/**
 * 商户手续费
 */
import flink.etc.MatchMode;
import flink.util.AmountUtils;
import flink.util.Paginater;
import flink.util.Type;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardMerchFeeDAO;
import gnete.card.dao.CardMerchFeeDtlDAO;
import gnete.card.dao.CardMerchGroupDAO;
import gnete.card.dao.CardMerchToGroupDAO;
import gnete.card.dao.CurrCodeDAO;
import gnete.card.dao.MerchFeeTemplateDAO;
import gnete.card.dao.MerchFeeTemplateDetailDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardMerchFee;
import gnete.card.entity.CardMerchFeeDtl;
import gnete.card.entity.CardMerchFeeKey;
import gnete.card.entity.CardMerchGroup;
import gnete.card.entity.CardMerchToGroup;
import gnete.card.entity.MerchFeeTemplate;
import gnete.card.entity.MerchFeeTemplateDetail;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.state.CurrCodeState;
import gnete.card.entity.type.CardMerchFeeFeeType;
import gnete.card.entity.type.MerchFeeTransType;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.FeeFeeType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SelectMerchType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardMerchFeeService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class CardMerchFeeAction extends BaseAction {
	
	@Autowired
	private CardMerchFeeDAO cardMerchFeeDAO;
	@Autowired
	private CardMerchFeeService cardMerchFeeService;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private MerchFeeTemplateDAO merchFeeTemplateDAO;
	@Autowired
	private MerchFeeTemplateDetailDAO merchFeeTemplateDetailDAO;
	@Autowired
	private CardMerchFeeDtlDAO cardMerchFeeDtlDAO;
	
	private Paginater page;
	private CardMerchFee cardMerchFee;
	private MerchFeeTemplate merchFeeTemplate;
	private List<Type> feeTypeList;
	private Collection transTypeList;
	private Collection costCycleList;
	private String branchCode;
	private BranchInfo branchInfo;
	private CardMerchFeeDtl cardMerchFeeDtl;
	
	private Paginater merchFeeAmtPage;
	private Paginater cardMerchFeeDtlPage;
	
	protected final String LISTMERCHFEEAMT = "listMerchFeeAmt";
	protected final String ADDMERCHFEEAMT = "addMerchFeeAmt";
	protected final String MODIFYMERCHFEEAMT = "modifyMerchFeeAmt";
	
	private boolean showCard = false;
	private boolean showMerch = false;
	private boolean showCardMerchFeeDtl = false;
	
	private String[] ulimit;
	private String[] originalUlimit;
	private String[] feeRate;
	private String feeRateComma;
	private String maxAmtComma;
	private String minAmtComma;
	
	private String merchs;
	
	private List currCodeList;
	private List<BranchInfo> branchList;
	private List<MerchInfo> merchList;
	private List selectMerchTypeList;
	private List<MerchFeeTemplate> merchFeeTemplateList;
	private List<MerchFeeTemplateDetail> merchFeeTemplateDetailList;
	private List<CardMerchFeeDtl> cardMerchFeeDtlList;

	@Autowired
	private CurrCodeDAO currCodeDAO;
	@Autowired
	private CardMerchGroupDAO cardMerchGroupDAO;
	@Autowired
	private CardMerchToGroupDAO cardMerchToGroupDAO;
	private String updateUlmoney;
	
	@Override
	public String execute() throws Exception {
		feeTypeList = FeeFeeType.getList();
		transTypeList = MerchFeeTransType.getList();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(cardMerchFee!=null){
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(cardMerchFee.getBranchName()));
			params.put("merchName", MatchMode.ANYWHERE.toMatchString(cardMerchFee.getMerchName()));
			params.put("feeType", cardMerchFee.getFeeType());
			params.put("transType", cardMerchFee.getTransType());
			params.put("cardBin", cardMerchFee.getCardBin());
		}
		
		// 营运中心、中心部门
		if(RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				 || RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
				
		}
		// 分支机构
		else if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			branchList = new ArrayList<BranchInfo>();
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if(CollectionUtils.isEmpty(branchList)){
				params.put("cardIssuers", " ");
			}
		}
 		// 发卡机构、机构部门	
		else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			showCard = true;
			branchList = this.getMyCardBranch();
			params.put("branchCode", branchList.get(0).getBranchCode());
		} 
		// 商户
		else if (RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			showMerch = true;
			merchList = this.getMyMerch();
			params.put("merchId", merchList.get(0).getMerchId());
		}
		else {
			throw new BizException("没有权限查询发卡机构与商户手续费费率参数！");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)) {
			params.put("cardIssuers", branchList);
		}
		
		page = this.cardMerchFeeDAO.findCardMerchFee(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	//取得明细
	public String detail() throws Exception {
		CardMerchFeeKey key = new CardMerchFeeKey();
		key.setBranchCode(this.getBranchCode());
		key.setMerchId(request.getParameter("merchId"));
		key.setTransType(request.getParameter("transType"));
		key.setCardBin(request.getParameter("cardBin"));
		key.setUlMoney(new BigDecimal(request.getParameter("ulMoney")));
		
		this.cardMerchFee = (CardMerchFee) this.cardMerchFeeDAO.findByPk(key);
		
		if(FeeFeeType.MONEY_STAGE.getValue().equals(this.cardMerchFee.getFeeType())||
				FeeFeeType.MONEY_STAGE_CUM.getValue().equals(this.cardMerchFee.getFeeType())||
				FeeFeeType.TRADE_NUM_STAGE.getValue().equals(this.cardMerchFee.getFeeType())||
				FeeFeeType.TRADE_NUM_STAGE_CUM.getValue().equals(this.cardMerchFee.getFeeType())){
			this.showCardMerchFeeDtl = true;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("feeRuleId", this.cardMerchFee.getFeeRuleId());
		
		this.cardMerchFeeDtlPage = this.cardMerchFeeDtlDAO.findCardMerchFeeDtl(params, this.getPageNumber(), this.getPageSize() );
		
		return DETAIL;
	}
	
	public void getMerchGroup() throws Exception {
		String branchCode = request.getParameter("branchCode");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", branchCode);
		List<CardMerchGroup> merchGroupList = this.cardMerchGroupDAO.findCardMerchGroupList(params);
		
		StringBuffer sb = new StringBuffer(128);
		for (CardMerchGroup cardMerchGroup : merchGroupList){
			sb.append("<option value=\"").append(cardMerchGroup.getGroupId()).append("\">")
				.append(cardMerchGroup.getGroupName()).append("</option>");
		}
		this.respond(sb.toString());
	}
	
	public String showAdd() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue()))){
			throw new BizException("没有权限设置商户手续费参数！");
		}
		transTypeList = MerchFeeTransType.getList();
		//costCycleList = CostCycleType.getDayMonth();
		costCycleList = CostCycleType.getMonth();
		//feeTypeList = FeeFeeType.getList();
		selectMerchTypeList = SelectMerchType.getAll();
		this.currCodeList = currCodeDAO.findCurrCode(CurrCodeState.NORMAL.getValue());
		
		cardMerchFee = new CardMerchFee();
		this.cardMerchFee.setBranchCode(this.getSessionUser().getBranchNo());
		BranchInfo branchInfo = (BranchInfo)this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo());
		this.cardMerchFee.setBranchName(branchInfo.getBranchName());
		return ADD;
	}
	
	public String add() throws Exception {
		// 取币种
		String merchId = null;
		
		if (StringUtils.isNotEmpty(merchs)) {
			String[] merchArray = merchs.split(",");
			merchId = merchArray[0];
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("groupId", this.cardMerchFee.getMerchId());
			List<CardMerchToGroup> list = cardMerchToGroupDAO.findByGroupIdAndBranch(params);
			merchId = list.get(0).getMerchId();
		}
		
		MerchInfo merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(merchId);
		this.cardMerchFee.setCurCode(merchInfo.getCurrCode());
		this.cardMerchFee.setBranchCode(this.getSessionUser().getBranchNo());
		
		// 如果卡BIN为空，则填写'*'表示通用
		if(StringUtils.isEmpty(this.cardMerchFee.getCardBin())){
			this.cardMerchFee.setCardBin("*");
		}
		
		List<CardMerchFee> cardMerchFeeList = new ArrayList<CardMerchFee>();
		
		if (StringUtils.isNotEmpty(merchs)) {
			String[] merchArray = merchs.split(",");
			
			for (int j = 0; j < merchArray.length; j++) {
				CardMerchFee fee = new CardMerchFee();
				fee.setMerchId(merchArray[j]);
				formToBo(fee, this.cardMerchFee);
				
				//固定金额保底封顶
				if(FeeFeeType.MONEY_RATE_MAX_MIN.getValue().equals(this.cardMerchFee.getFeeType())){
					fee.setMinAmt(AmountUtils.parseBigDecimal(this.getMinAmtComma()));
					fee.setMaxAmt(AmountUtils.parseBigDecimal(this.getMaxAmtComma()));
				} else {
					fee.setMinAmt(new BigDecimal(0.0));
					fee.setMaxAmt(new BigDecimal(0.0));
				}
				
				//分段，取最小的费率插入分支机构与发卡机构手续费参数
				if (this.cardMerchFee.getFeeType().equals(FeeFeeType.TRADE_NUM_STAGE_CUM.getValue())
						|| this.cardMerchFee.getFeeType().equals(FeeFeeType.TRADE_NUM_STAGE.getValue())
						|| this.cardMerchFee.getFeeType().equals(FeeFeeType.MONEY_STAGE.getValue())
						|| this.cardMerchFee.getFeeType().equals(FeeFeeType.MONEY_STAGE_CUM.getValue())) { 
					fee.setUlMoney(AmountUtils.parseBigDecimal(ulimit[ulimit.length-1]));
					fee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[feeRate.length-1]));
					
				} 
				// 交易笔数、金额固定比例
				else {
					fee.setUlMoney(Constants.FEE_MAXACCOUNT);
					fee.setFeeRate(AmountUtils.parseBigDecimal(feeRateComma));
				}
				
				cardMerchFeeList.add(fee);
			}
		}
		// 没有选择商户
		else {
			CardMerchFee fee = new CardMerchFee();
			
			fee.setMerchId("0"); // 商户为空，默认为通用，填写"0"
			formToBo(fee, this.cardMerchFee);
			
			//固定金额保底封顶
			if(FeeFeeType.MONEY_RATE_MAX_MIN.getValue().equals(this.cardMerchFee.getFeeType())){
				fee.setMinAmt(AmountUtils.parseBigDecimal(this.getMinAmtComma()));
				fee.setMaxAmt(AmountUtils.parseBigDecimal(this.getMaxAmtComma()));
			} else {
				fee.setMinAmt(new BigDecimal(0.0));
				fee.setMaxAmt(new BigDecimal(0.0));
			}
			
			//分段，取最小的费率插入分支机构与发卡机构手续费参数
			if (this.cardMerchFee.getFeeType().equals(FeeFeeType.TRADE_NUM_STAGE_CUM.getValue())
					|| this.cardMerchFee.getFeeType().equals(FeeFeeType.TRADE_NUM_STAGE.getValue())
					|| this.cardMerchFee.getFeeType().equals(FeeFeeType.MONEY_STAGE.getValue())
					|| this.cardMerchFee.getFeeType().equals(FeeFeeType.MONEY_STAGE_CUM.getValue())) { 
				fee.setUlMoney(AmountUtils.parseBigDecimal(ulimit[ulimit.length-1]));
				fee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[feeRate.length-1]));
				
			} 
			// 交易笔数、金额固定比例
			else {
				fee.setUlMoney(Constants.FEE_MAXACCOUNT);
				fee.setFeeRate(AmountUtils.parseBigDecimal(feeRateComma));
			}
			
			cardMerchFeeList.add(fee);
		}
		
		cardMerchFeeService.addCardMerchFee(cardMerchFeeList.toArray(new CardMerchFee[cardMerchFeeList.size()]), ulimit, feeRate, this.getSessionUserCode());
		
		String msg = "添加发卡机构与商户手续费费率参数["+ cardMerchFee.getBranchCode() +"]成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/fee/cardMerchFee/list.do", msg);
		return SUCCESS;
	}
	
	private void formToBo(CardMerchFee dest, CardMerchFee src){
		dest.setBranchCode(src.getBranchCode());
		dest.setTransType(src.getTransType());
		dest.setCardBin(src.getCardBin());
		dest.setCostCycle(src.getCostCycle());
		dest.setFeeType(src.getFeeType());
		dest.setMerchFlag(src.getMerchFlag());
		dest.setCurCode(src.getCurCode());
	}
	
	public String showModify() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue()))){
			throw new BizException("没有权限设置商户手续费参数！");
		}
		
		CardMerchFeeKey key = new CardMerchFeeKey();
		key.setBranchCode(cardMerchFee.getBranchCode());
		key.setCardBin(cardMerchFee.getCardBin());
		key.setMerchId(cardMerchFee.getMerchId());
		key.setTransType(cardMerchFee.getTransType());
		key.setUlMoney(cardMerchFee.getUlMoney());
		cardMerchFee = (CardMerchFee)cardMerchFeeDAO.findByPk(key);
		cardMerchFee.setBranchName(((BranchInfo)branchInfoDAO.findByPk(cardMerchFee.getBranchCode())).getBranchName());
		
		if (SelectMerchType.SINGLE.getValue().equals(cardMerchFee.getMerchFlag())) {
			cardMerchFee.setMerchName(((MerchInfo)merchInfoDAO.findByPk(cardMerchFee.getMerchId())).getMerchName());
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("groupId", cardMerchFee.getMerchId());
			cardMerchFee.setMerchName(this.cardMerchGroupDAO.findCardMerchGroupList(params).get(0).getGroupName());
		}
		this.updateUlmoney = cardMerchFee.getUlMoney().toString();
		this.feeRateComma = this.cardMerchFee.getFeeRate().toString();
		
		// 金額固定比例保底封顶
		if(this.cardMerchFee.getFeeType().equals(FeeFeeType.MONEY_RATE_MAX_MIN.getValue())){ 
			this.maxAmtComma = this.cardMerchFee.getMaxAmt().toString();
			this.minAmtComma = this.cardMerchFee.getMinAmt().toString();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		// 分段计费方式
		if (this.cardMerchFee.getFeeType().equals(FeeFeeType.TRADE_NUM_STAGE_CUM.getValue())
				|| this.cardMerchFee.getFeeType().equals(FeeFeeType.TRADE_NUM_STAGE.getValue())
				|| this.cardMerchFee.getFeeType().equals(FeeFeeType.MONEY_STAGE.getValue())
				|| this.cardMerchFee.getFeeType().equals(FeeFeeType.MONEY_STAGE_CUM.getValue())) { 
			params.put("feeRuleId", this.cardMerchFee.getFeeRuleId());
			this.cardMerchFeeDtlList = this.cardMerchFeeDtlDAO.getCardMerchFeeDtlList(params);
			
		} 
		
		return MODIFY;
	}
	
	public String modify() throws Exception {
		
		List<CardMerchFeeDtl> cardMerchFeeDtlList = new ArrayList<CardMerchFeeDtl>();
		
		//分段
		if (this.cardMerchFee.getFeeType().equals(FeeFeeType.TRADE_NUM_STAGE_CUM.getValue())
				|| this.cardMerchFee.getFeeType().equals(FeeFeeType.TRADE_NUM_STAGE.getValue())
				|| this.cardMerchFee.getFeeType().equals(FeeFeeType.MONEY_STAGE.getValue())
				|| this.cardMerchFee.getFeeType().equals(FeeFeeType.MONEY_STAGE_CUM.getValue())) { 
			for(int i=0; i < this.ulimit.length; i++){
				CardMerchFeeDtl FeeDtl = new CardMerchFeeDtl();
				FeeDtl.setUlMoney(AmountUtils.parseBigDecimal(ulimit[i]));
				FeeDtl.setFeeRate(AmountUtils.parseBigDecimal(feeRate[i]));
				cardMerchFeeDtlList.add(FeeDtl);
			}
			
			this.cardMerchFee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[this.ulimit.length-1]));
		} 
		//其他
		else {
			this.cardMerchFee.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
			
			//金额固定比例保底封顶
			if(FeeFeeType.MONEY_RATE_MAX_MIN.getValue().equals(this.cardMerchFee.getFeeType())){
				cardMerchFee.setMinAmt(AmountUtils.parseBigDecimal(this.getMinAmtComma()));
				cardMerchFee.setMaxAmt(AmountUtils.parseBigDecimal(this.getMaxAmtComma()));
			} 
			//按交易笔数，将单笔最高手续费和最低手续费置零
			else {
				cardMerchFee.setMinAmt(new BigDecimal(0.0));
				cardMerchFee.setMaxAmt(new BigDecimal(0.0));
			}
		}
		
		this.cardMerchFeeService.modifyCardMerchFee(cardMerchFee, cardMerchFeeDtlList.toArray(new CardMerchFeeDtl[cardMerchFeeDtlList.size()]), originalUlimit, this.getSessionUserCode());
		
		String msg = "修改发卡机构与商户手续费费率参数["+ cardMerchFee.getBranchCode() +"]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/fee/cardMerchFee/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue()))){
			throw new BizException("没有权限设置商户手续费参数！");
		}
		
		cardMerchFee.setBranchCode(branchCode);
		cardMerchFee.setMerchId(request.getParameter("merchId"));
		cardMerchFee.setTransType(request.getParameter("transType"));
		cardMerchFee.setCardBin(request.getParameter("cardBin"));
		cardMerchFee.setUlMoney(new BigDecimal(request.getParameter("ulMoney")));
		this.cardMerchFeeService.deleteCardMerchFee(cardMerchFee);
		
		String msg = "删除发卡机构与商户手续费费率参数["+ cardMerchFee.getBranchCode() +"]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/fee/cardMerchFee/list.do", msg);
		return SUCCESS;
	}
	
	// 查询指定交易类型的计费方式列表，服务端查询，返回到前端
	public String feeTypeList(){
		String transType = this.cardMerchFee.getTransType();
		
		//消费交易、消费赠送、通用积分消费、折扣功能
		if(MerchFeeTransType.CONSUME.getValue().equals(transType)||
				MerchFeeTransType.COMMON_PT_CONSULE.getValue().equals(transType)||
				MerchFeeTransType.CONSUME_PRESENT.getValue().equals(transType)||
				MerchFeeTransType.DISCOUNT_FUN.getValue().equals(transType)){
			this.feeTypeList = FeeFeeType.getForMerchFee();
		} else{
			this.feeTypeList = FeeFeeType.getForMerchFeeOther();
		}
		
		//this.feeTypeList = FeeFeeType.getList(); //暂时不做控制
		return "feeTypeList";
	}
	
	// 打开新增页面的初始化操作
	public String showAddTemplate() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue()))){
			throw new BizException("没有权限设置商户手续费参数！");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", this.getSessionUser().getBranchNo());
		this.merchFeeTemplateList = this.merchFeeTemplateDAO.getMerchFeeTemplateList(params);
		
		cardMerchFee = new CardMerchFee();
		this.cardMerchFee.setBranchCode(this.getSessionUser().getBranchNo());
		BranchInfo branchInfo = (BranchInfo)this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo());
		this.cardMerchFee.setBranchName(branchInfo.getBranchName());
		
		selectMerchTypeList = SelectMerchType.getAll();
		
		return "addTemplate";
	}
	
	// 使用商户手续费模板新增商户手续费
	public String addTemplate() throws Exception {
		
		// 取币种
		String merchId = null;
		
		if (StringUtils.isNotEmpty(merchs)) {
			String[] merchArray = merchs.split(",");
			merchId = merchArray[0];
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("groupId", this.cardMerchFee.getMerchId());
			List<CardMerchToGroup> list = cardMerchToGroupDAO.findByGroupIdAndBranch(params);
			merchId = list.get(0).getMerchId();
		}
		
		MerchInfo merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(merchId);
		this.cardMerchFee.setCurCode(merchInfo.getCurrCode());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("templateId", this.merchFeeTemplate.getTemplateId());
		List<MerchFeeTemplateDetail> merchFeeTemplateDetailList = this.merchFeeTemplateDetailDAO.getMerchFeeTemplateDetailList(params);
		MerchFeeTemplateDetail merchFeeTemplateDetail = merchFeeTemplateDetailList.get(0);
		
		// 如果是分段的
		List<CardMerchFee> cardFeeList = new ArrayList<CardMerchFee>();
		
		if (merchFeeTemplateDetail.getFeeType().equals(CardMerchFeeFeeType.TRADEMONEY.getValue())
				|| merchFeeTemplateDetail.getFeeType().equals(CardMerchFeeFeeType.SSUM.getValue())) {
			
			for (int i = 0; i < merchFeeTemplateDetailList.size(); i++) {
				MerchFeeTemplateDetail temp = merchFeeTemplateDetailList.get(i);
				
				// 选择了多个商户的情况
				if (StringUtils.isNotEmpty(merchs)) {
					String[] merchArray = merchs.split(",");
					for (int j = 0; j < merchArray.length; j++) {
						CardMerchFee fee = new CardMerchFee();
						fee = new CardMerchFee();
						fee.setMerchId(merchArray[j]);
						
						fee.setBranchCode(this.cardMerchFee.getBranchCode());
						fee.setCurCode(this.cardMerchFee.getCurCode());
						fee.setMerchFlag(this.cardMerchFee.getMerchFlag());
						
						templateToFee(fee, temp); 
						
						fee.setMinAmt(new BigDecimal(0.0));
						fee.setMaxAmt(new BigDecimal(0.0));
						cardFeeList.add(fee);
					}
				}
				else {
					CardMerchFee fee = new CardMerchFee();
					fee = new CardMerchFee();
					fee.setMerchId(this.cardMerchFee.getMerchId());
					fee.setBranchCode(this.cardMerchFee.getBranchCode());
					fee.setCurCode(this.cardMerchFee.getCurCode());
					fee.setMerchFlag(this.cardMerchFee.getMerchFlag());
					
					templateToFee(fee, temp);
					
					fee.setMinAmt(new BigDecimal(0.0));
					fee.setMaxAmt(new BigDecimal(0.0));
					cardFeeList.add(fee);
				}
			}
		} else {
			if (StringUtils.isNotEmpty(merchs)) {
				String[] merchArray = merchs.split(",");
				for (int j = 0; j < merchArray.length; j++) {
					CardMerchFee fee = new CardMerchFee();
					fee = new CardMerchFee();
					fee.setMerchId(merchArray[j]);
					fee.setBranchCode(this.cardMerchFee.getBranchCode());
					fee.setCurCode(this.cardMerchFee.getCurCode());
					fee.setMerchFlag(this.cardMerchFee.getMerchFlag());
					
					templateToFee(fee, merchFeeTemplateDetail);
					
					if (!merchFeeTemplateDetail.getFeeType().equals(CardMerchFeeFeeType.FEE.getValue())){
						fee.setMinAmt(new BigDecimal(0.0));
						fee.setMaxAmt(new BigDecimal(0.0));
					} else {
						fee.setMinAmt(merchFeeTemplateDetail.getMinAmt());
						fee.setMaxAmt(merchFeeTemplateDetail.getMaxAmt());
					}
					cardFeeList.add(fee);
				}
			} else {
				CardMerchFee fee = new CardMerchFee();
				fee = new CardMerchFee();
				fee.setMerchId(this.cardMerchFee.getMerchId());
				fee.setBranchCode(this.cardMerchFee.getBranchCode());
				fee.setCurCode(this.cardMerchFee.getCurCode());
				fee.setMerchFlag(this.cardMerchFee.getMerchFlag());
				
				templateToFee(fee, merchFeeTemplateDetail);
				
				if (!merchFeeTemplateDetail.getFeeType().equals(CardMerchFeeFeeType.FEE.getValue())){
					fee.setMinAmt(new BigDecimal(0.0));
					fee.setMaxAmt(new BigDecimal(0.0));
				} else {
					fee.setMinAmt(merchFeeTemplateDetail.getMinAmt());
					fee.setMaxAmt(merchFeeTemplateDetail.getMaxAmt());
				}
				cardFeeList.add(fee);
			}
		}
		cardMerchFeeService.addCardMerchFee(cardFeeList.toArray(new CardMerchFee[cardFeeList.size()]), this.getSessionUserCode());
		
		String msg = "使用商户模板添加发卡机构与商户手续费费率参数["+ cardMerchFee.getBranchCode() +"]成功";
		this.addActionMessage("/fee/cardMerchFee/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	//取得模板数据
	public void getTempaltePara() throws Exception {
		
		this.merchFeeTemplateDetailList = new ArrayList<MerchFeeTemplateDetail>();
		
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("templateId", this.merchFeeTemplate.getTemplateId());
			merchFeeTemplateDetailList = this.merchFeeTemplateDetailDAO.getMerchFeeTemplateDetailList(params);
			
			Assert.notEmpty(merchFeeTemplateDetailList, "手续费模板不存在,请重新选择！");
		}
		catch (Exception e) {
			this.respond("{'success':" + false + ", 'error':'" + e.getMessage() + "'}");
			return;
		}
		
		MerchFeeTemplateDetail merchFeeTemplateDetail = merchFeeTemplateDetailList.get(0);
		String feeType = merchFeeTemplateDetail.getFeeType(); // 计费方式
		String feeTypeName = merchFeeTemplateDetail.getFeeType() != null ? 
								CardMerchFeeFeeType.valueOf(merchFeeTemplateDetail.getFeeType()).getName() : ""; // 计费方式
		String transTypeName = merchFeeTemplateDetail.getTransType() != null ? 
				        		MerchFeeTransType.valueOf(merchFeeTemplateDetail.getTransType()).getName() : "" ; // 交易类型
		String cardBin = merchFeeTemplateDetail.getCardBin(); // 卡bin
		String costCycleName = merchFeeTemplateDetail.getCostCycle() != null ? 
								CostCycleType.valueOf(merchFeeTemplateDetail.getCostCycle()).getName() : "" ; // 计费周期
		
		String feeRate = ""; // 费率
		String maxAmt = ""; // 单笔最高手续费
		String minAmt = ""; // 单笔最低手续费
		
		if(CardMerchFeeFeeType.EACH.getValue().equals(merchFeeTemplateDetail.getFeeType())){ // 按交易笔数
			feeRate = merchFeeTemplateDetail.getFeeRate().toString();
			merchFeeTemplateDetail.getMaxAmt().toString();
		}
		else if(CardMerchFeeFeeType.FEE.getValue().equals(merchFeeTemplateDetail.getFeeType())){ // 金额固定比例
			feeRate = merchFeeTemplateDetail.getFeeRate().toString();
			maxAmt = merchFeeTemplateDetail.getMaxAmt().toString();
			minAmt = merchFeeTemplateDetail.getMinAmt().toString();
		}

		this.respond("{'success':"+ true + ", 'feeTypeName':'" + feeTypeName + "', 'feeType':'" + feeType + 
				"', 'transTypeName':'" + transTypeName + "', 'cardBin':'" + cardBin + 
				"', 'costCycleName':'" + costCycleName + "', 'feeRate':'" + feeRate + 
				"', 'maxAmt':'" + maxAmt + "', 'minAmt':'" + minAmt + "'}");
		
	}
	
	public String merchFeeTemplateDetailList() throws BizException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("templateId", this.merchFeeTemplate.getTemplateId());
		merchFeeTemplateDetailList = this.merchFeeTemplateDetailDAO.getMerchFeeTemplateDetailList(params);
			
		if (merchFeeTemplateDetailList.isEmpty()) {
			return null;
		}
		
		return "merchFeeTemplateDetailList";
	}
	
	private void templateToFee(CardMerchFee dest,  MerchFeeTemplateDetail src){
		dest.setUlMoney(src.getUlMoney());
		dest.setFeeRate(src.getFeeRate());
		dest.setTransType(src.getTransType());
		dest.setCardBin(src.getCardBin());
		dest.setCostCycle(src.getCostCycle());
		dest.setFeeType(src.getFeeType());
	}
	
	public CardMerchFee getCardMerchFee() {
		return cardMerchFee;
	}

	public void setCardMerchFee(CardMerchFee cardMerchFee) {
		this.cardMerchFee = cardMerchFee;
	}

	public Collection getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(Collection transTypeList) {
		this.transTypeList = transTypeList;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public BranchInfo getBranchInfo() {
		return branchInfo;
	}

	public void setBranchInfo(BranchInfo branchInfo) {
		this.branchInfo = branchInfo;
	}

	public Collection getCostCycleList() {
		return costCycleList;
	}

	public void setCostCycleList(Collection costCycleList) {
		this.costCycleList = costCycleList;
	}

	public Paginater getMerchFeeAmtPage() {
		return merchFeeAmtPage;
	}

	public void setMerchFeeAmtPage(Paginater merchFeeAmtPage) {
		this.merchFeeAmtPage = merchFeeAmtPage;
	}

	public List<Type> getFeeTypeList() {
		return feeTypeList;
	}

	public void setFeeTypeList(List<Type> feeTypeList) {
		this.feeTypeList = feeTypeList;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public boolean isShowMerch() {
		return showMerch;
	}

	public void setShowMerch(boolean showMerch) {
		this.showMerch = showMerch;
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

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public List<MerchInfo> getMerchList() {
		return merchList;
	}

	public void setMerchList(List<MerchInfo> merchList) {
		this.merchList = merchList;
	}

	public List getSelectMerchTypeList() {
		return selectMerchTypeList;
	}

	public void setSelectMerchTypeList(List selectMerchTypeList) {
		this.selectMerchTypeList = selectMerchTypeList;
	}

	public String getUpdateUlmoney() {
		return updateUlmoney;
	}

	public void setUpdateUlmoney(String updateUlmoney) {
		this.updateUlmoney = updateUlmoney;
	}

	public String getFeeRateComma() {
		return feeRateComma;
	}

	public void setFeeRateComma(String feeRateComma) {
		this.feeRateComma = feeRateComma;
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

	public List<MerchFeeTemplate> getMerchFeeTemplateList() {
		return merchFeeTemplateList;
	}

	public void setMerchFeeTemplateList(List<MerchFeeTemplate> merchFeeTemplateList) {
		this.merchFeeTemplateList = merchFeeTemplateList;
	}

	public MerchFeeTemplate getMerchFeeTemplate() {
		return merchFeeTemplate;
	}

	public void setMerchFeeTemplate(MerchFeeTemplate merchFeeTemplate) {
		this.merchFeeTemplate = merchFeeTemplate;
	}

	public List<MerchFeeTemplateDetail> getMerchFeeTemplateDetailList() {
		return merchFeeTemplateDetailList;
	}

	public void setMerchFeeTemplateDetailList(
			List<MerchFeeTemplateDetail> merchFeeTemplateDetailList) {
		this.merchFeeTemplateDetailList = merchFeeTemplateDetailList;
	}

	public CardMerchFeeDtl getCardMerchFeeDtl() {
		return cardMerchFeeDtl;
	}

	public void setCardMerchFeeDtl(CardMerchFeeDtl cardMerchFeeDtl) {
		this.cardMerchFeeDtl = cardMerchFeeDtl;
	}

	public Paginater getCardMerchFeeDtlPage() {
		return cardMerchFeeDtlPage;
	}

	public void setCardMerchFeeDtlPage(Paginater cardMerchFeeDtlPage) {
		this.cardMerchFeeDtlPage = cardMerchFeeDtlPage;
	}

	public boolean isShowCardMerchFeeDtl() {
		return showCardMerchFeeDtl;
	}

	public void setShowCardMerchFeeDtl(boolean showCardMerchFeeDtl) {
		this.showCardMerchFeeDtl = showCardMerchFeeDtl;
	}

	public List<CardMerchFeeDtl> getCardMerchFeeDtlList() {
		return cardMerchFeeDtlList;
	}

	public void setCardMerchFeeDtlList(List<CardMerchFeeDtl> cardMerchFeeDtlList) {
		this.cardMerchFeeDtlList = cardMerchFeeDtlList;
	}

	public String[] getOriginalUlimit() {
		return originalUlimit;
	}

	public void setOriginalUlimit(String[] originalUlimit) {
		this.originalUlimit = originalUlimit;
	}

}
