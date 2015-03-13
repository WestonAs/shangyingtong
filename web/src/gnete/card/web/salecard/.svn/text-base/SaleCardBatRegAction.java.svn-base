package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.CommonHelper;
import flink.util.Paginater;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.CardCustomerDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.dao.RebateCardRegDAO;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.dao.RebateRuleDetailDAO;
import gnete.card.dao.SaleCardBatRegDAO;
import gnete.card.dao.SaleCardRegDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardBin;
import gnete.card.entity.CardCustomer;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.CustomerRebateReg;
import gnete.card.entity.DiscntClassDef;
import gnete.card.entity.MembClassDef;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.Rebate;
import gnete.card.entity.RebateCardReg;
import gnete.card.entity.RebateRule;
import gnete.card.entity.RebateRuleDetail;
import gnete.card.entity.SaleCardBatReg;
import gnete.card.entity.SaleCardReg;
import gnete.card.entity.flag.SaleCancelFlag;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.CustomerRebateType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.PresellType;
import gnete.card.entity.type.RebateFromType;
import gnete.card.entity.type.RebateType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.RebateRuleService;
import gnete.card.service.SaleCardBatRegService;
import gnete.card.service.SaleCardRegService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.util.CardUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

/**
 * 售卡登记簿Action
 * SaleCardBatRegAction.java.
 * 
 * @author BenYan
 * @since JDK1.5
 * @history 2010-7-27
 */
public class SaleCardBatRegAction extends BaseAction {
	
	protected final String ACTIVATE = "activate";
	
	private String url;	
	private String msg;
	private boolean isOk ; 
	private BizException bizException;
	
	@Autowired
	private SaleCardRegDAO saleCardRegDAO;
	@Autowired
	private SaleCardBatRegDAO saleCardBatRegDAO;
	@Autowired
	private SaleCardBatRegService saleCardBatRegService;
	@Autowired
	private SaleCardRegService saleCardRegService;	//预售卡撤销
	@Autowired
	private RebateRuleService rebateRuleService;
//	@Autowired
//	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardCustomerDAO cardCustomerDAO;
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private RebateRuleDAO rebateRuleDAO;    
	@Autowired
	private RebateRuleDetailDAO rebateRuleDetailDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	@Autowired
	private RebateCardRegDAO rebateCardRegDAO;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;
	
	// 售卡登记簿对象
	private SaleCardReg saleCardReg;
	
	// 批量售卡登记簿对象
	private SaleCardBatReg saleCardBatReg;
	private Long saleCardBatRegId;
	
	// 是否有持卡人资料标志：0-无资料 1-有资料
	private String isHasCustName;
	private CardExtraInfo cardExtraInfo;	
	
	// 购卡客户
	private CardCustomer cardCustomer;
	
	// 客户返利
	private RebateRule saleRebateRule;
	private List<CustomerRebateReg> customerRebateRegList;	
	private List<RebateRuleDetail> saleRebateRuleDetailList;
	
    // 卡类型
    private String cardType;    
    private CardTypeCode cardTypeCode;
    private CardBin cardBin;
    
    // 卡子类型
    private CardSubClass cardSubClass;
    private List<CardSubClass> cardSubClassList;    
    //次卡子类型定义
    private List<AccuClassDef> accuClassList;
    //积分子类型定义
    private List<PointClassDef> pointClassList;
    //会员子类型定义
    private List<MembClassDef> membClassList;
    //折扣子类型定义
    private List<DiscntClassDef> discntClassList;    
    
    // 页面Action子名
    private String actionSubName;
    private String actionSubLabel;
    
    private Paginater page;
    

    
    // 是否按次充值
    private boolean depositTypeIsTimes;
    
    // 售卡方式：0-实时售卡 1-预售卡
    private boolean isPresell;
    // “未激活”标志
    private String  unActivatedLabel;
    
    private Collection statusList;
//	private List<SaleCardBatReg> saleCardBatRegList;
	private Paginater saleCardBatPage;
    
    // 输入数据
	private String[] startCardId; //起始卡号
	private String[] cardCount;
	private String[] endCardId; // 结束卡号
	private String[] amt;
	private String[] expenses;
	
	// 输出数据
	private double resultSumAmt;
	private double resultSumExpenses;
	private double resultSumRealAmt;
	private double resultSumRebateAmt;
	/** 手续费 */
	private double resultFeeAmt;

	private String[] resultCardId;	
	private double[] resultAmt;	
	private double[] resultExpenses;
	private double[] resultRealAmt;
	private double[] resultRebateAmt;
	
	private RebateRule rebateRule;
	private Rebate rebate;

    private String cardId;

	private List<RebateRule> rebateRuleList;
	
	private List rebateTypeList;
    
	private String rebateType;
	private String calcMode;
	
	// 是否需要在登记簿中记录签名信息
	private boolean signatureReg;
	
	/** 是否显示发卡机构列表 */
    private boolean showCardBranch = false;
    /** 发卡机构列表 */
    private List<BranchInfo> cardBranchList;
    /** 发卡机构名称 */
    private String cardBranchName;
    /** 开始日期 */
    private String startDate; 
    /** 结束日期 */
    private String endDate;
    
    /** 多张卡返利卡号数组 */
    private String[] rebateCardIds;
    /** 多张卡返利金额数组*/
    private String[] rebateAmts;
    /** 返利卡张数 */
    private Long rebateCount;
    
    /** 返利卡列表 */
    private List<RebateCardReg> rebateCardList;
    /** 卡类型列表 */
	private List<CardType> cardTypeList;
	
	// 计算返利金额等
	public String calRealAmt(){
//		JSONObject object = new JSONObject();
		StopWatch watch = new StopWatch();
		watch.start();
		try{
			// 查询购卡客户
			this.cardCustomer = (CardCustomer)this.cardCustomerDAO.findByPk(this.saleCardReg.getCardCustomerId());
			// 查询返利规则
			this.rebateRule = (RebateRule) this.rebateRuleDAO.findByPk(this.saleCardReg.getRebateId());
			
			int count = this.startCardId.length;
//			this.saleCardBatRegList = new ArrayList();
			resultSumRealAmt = 0;
			resultSumRebateAmt = 0;
			resultSumExpenses = 0;
			resultSumAmt = 0;

	    	String type = request.getParameter("type");	// type为0时是充值次数
	    	
	    	// 售卡手续费
	    	BigDecimal sumFeeAmt = new BigDecimal(0);
	    	
			for(int i = 0; i < count; i++){
				// 计算各段首张卡金额等
				this.saleCardBatReg = new SaleCardBatReg();
				this.saleCardBatReg.setCardId(this.startCardId[i]);
				this.saleCardBatReg.setAmt(NumberUtils.createBigDecimal(this.amt[i]));
				this.saleCardBatReg.setExpenses(NumberUtils.createBigDecimal(this.expenses[i]));
				
				BigDecimal actual = null;
				CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.startCardId[i]);
				Assert.notNull(cardInfo, "卡号[" + this.startCardId[i] + "]不存在");
				if ("0".equals(type)) {
		    		// 根据卡子类型得到次卡子类型
		    		String subClass = cardInfo.getCardSubclass();
		    		CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(subClass);
		    		Assert.notNull(cardSubClassDef, "卡号[" + this.startCardId[i] + "]所属卡类型不存在");
		    		String frequencyClass = cardSubClassDef.getFrequencyClass();
		    		AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(frequencyClass);
		    		Assert.notNull(accuClassDef, "卡号[" + this.startCardId[i] + "]所属次卡子类型不存在");
		    		
		    		// 实收金额 = 充值次数 * 次卡清算金额
		    		actual = AmountUtil.multiply(new BigDecimal(this.amt[i]), accuClassDef.getSettAmt());
		    	} else {
		    		actual = new BigDecimal(this.amt[i]); 
		    	}
				
//				// 每张卡的售卡手续费 售卡手续费 = 售卡总金额*手续费比例/100
//				BigDecimal feeAmt = AmountUtil.divide(AmountUtil.multiply(actual, saleCardReg.getFeeRate()), new BigDecimal(100));
//
//				sumFeeAmt = AmountUtil.add(sumFeeAmt, feeAmt); // 手续费
					
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("amt", actual.toString());  
				if (CustomerRebateType.SPECIFY_CARD.getValue().equals(cardCustomer.getRebateType())) {
					params.put("rebateType", RebateType.CARD.getValue());
				} else {
					params.put("rebateType", this.rebateType);
				}
				
				params.put("rebateRule", this.rebateRule);  
				this.rebate = this.rebateRuleService.calculateRebate(params);  // 计算返利金额等
				
				saleCardBatReg.setRealAmt(this.rebate.getRealAmt());
				saleCardBatReg.setRebateAmt(this.rebate.getRebateAmt());
				
				// // 得到卡号数组,19位的是新卡，否则是旧卡
				String strCardId = this.saleCardBatReg.getCardId();
				String[] cardArray = (strCardId.length() == 19 
								? CardUtil.getCard(strCardId, NumberUtils.toInt(this.cardCount[i]))
								: CardUtil.getOldCard(strCardId, NumberUtils.toInt(this.cardCount[i])));
				for(int j = 0; j < NumberUtils.toInt(this.cardCount[i]); j++){
					SaleCardBatReg saleCardBatReg2 = new SaleCardBatReg();
					saleCardBatReg2.setCardId(cardArray[j]);
					
					saleCardBatReg2.setAmt(this.saleCardBatReg.getAmt());
					saleCardBatReg2.setExpenses(this.saleCardBatReg.getExpenses());
					saleCardBatReg2.setRealAmt(this.saleCardBatReg.getRealAmt());
					saleCardBatReg2.setRebateAmt(this.saleCardBatReg.getRebateAmt());
//					saleCardBatRegList.add(saleCardBatReg2);

//					resultSumAmt += Double.valueOf(saleCardBatReg2.getAmt().toString());
//					resultSumExpenses += Double.valueOf(saleCardBatReg2.getExpenses().toString());
//					resultSumRealAmt += Double.valueOf(saleCardBatReg2.getRealAmt().toString());
//					resultSumRebateAmt += Double.valueOf(saleCardBatReg2.getRebateAmt().toString());
					
					resultSumAmt += saleCardBatReg2.getAmt().doubleValue();
					resultSumExpenses += saleCardBatReg2.getExpenses().doubleValue();
					resultSumRealAmt += saleCardBatReg2.getRealAmt().doubleValue();
					resultSumRebateAmt += saleCardBatReg2.getRebateAmt().doubleValue();
					
					// 每张卡的售卡手续费 售卡手续费 = 售卡总金额*手续费比例/100
					BigDecimal feeAmt = AmountUtil.divide(AmountUtil.multiply(actual, saleCardReg.getFeeRate()), new BigDecimal(100));

					sumFeeAmt = AmountUtil.add(sumFeeAmt, feeAmt); // 手续费
				}

			}

			// 售卡手续费 = 售卡总金额*手续费比例/100
			resultFeeAmt = sumFeeAmt.doubleValue();
			
			// 实收金额 = 返利后的金额 + 工本费 + 手续费
			resultSumRealAmt = resultSumRealAmt + resultSumExpenses + resultFeeAmt;
//			this.respond("{'resultRealAmt':"+ resultRealAmt + ", 'resultRebateAmt':"+ resultRebateAmt 
//					+ ", 'resultExpenses':"+ resultExpenses + ", 'resultAmt':" + resultAmt  + " }");
//					//+ ", 'resultSaleCardBatRegList':" + this.saleCardBatRegList + " }");
//			object.put("success", true);
			
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			watch.stop();
			logger.debug("计算批量售卡实收金额总消耗时间[" + watch + "]");
		}
		return "hidden";
	}

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		this.statusList = RegisterState.getAll();
		this.rebateTypeList = RebateType.getForSellBatch();
		
		this.setUnActivatedLabel(RegisterState.INACTIVE.getValue());
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(!this.getIsPresell()){
			params.put("presell", PresellType.REALSELL.getValue());  // 实时售卡			
		}else{
			params.put("presell", PresellType.PRESELL.getValue());  // 预售卡
		}
		if (saleCardBatReg != null) {
			params.put("saleBatchId", saleCardBatReg.getSaleBatchId());
			params.put("cardId", saleCardBatReg.getCardId());
			params.put("status", saleCardBatReg.getStatus());
			params.put("cardBranch", saleCardReg.getCardBranch()); //发卡机构
			params.put("cardCustomer", MatchMode.ANYWHERE.toMatchString(saleCardReg.getCardCustomerName()));// 购卡客户
			params.put("rebateType", saleCardReg.getRebateType()); // 返利方式
//			params.put("saleDate", saleCardReg.getSaleDate()); //售卡日期
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			
		}
		if (isCenterOrCenterDeptRoleLogined()){// 运营中心，运营中心部门
			showCardBranch = true;
		} else if (isFenzhiRoleLogined()) {// 运营分支 ， 自己及自己的下级分支机构管理的所有发卡机构的记录
			showCardBranch = true;
			params.put("fenzhiList", this.getMyManageFenzhi());
		} else if (isCardSellRoleLogined()) {// 售卡代理
			this.cardBranchList = this.getMyCardBranch();
			params.put("branchCode", super.getSessionUser().getBranchNo());
		} else if (isCardRoleLogined()) {	// 发卡机构
			this.cardBranchList = this.getMyCardBranch();
			params.put("cardBranchList", cardBranchList); 
		} else if (isCardDeptRoleLogined()) {
			this.cardBranchList = this.getMyCardBranch();
			params.put("branchCode", super.getSessionUser().getDeptId());
		} else {
			throw new BizException("没有权限查看批量售卡记录");
		}
		// 设置卡类型
		params.put("cardClass", this.getCardType()); 
		params.put("saleCancel", SaleCancelFlag.NORMAL.getValue());// 普通售卡
		params.put("isSaleBatch", true);
		
		//查询优化
		if(saleCardBatReg != null && CommonHelper.isNotEmpty(saleCardBatReg.getCardId())){
			Map<String, Object> saleCardBatRegParams = new HashMap<String, Object>();
			saleCardBatRegParams.put("cardId", saleCardBatReg.getCardId());
			List<SaleCardBatReg> saleCardBatRegList = saleCardBatRegDAO.findSaleCardBatReg(saleCardBatRegParams);
			if(CommonHelper.checkIsNotEmpty(saleCardBatRegList)){
				Long[] ids = new Long[saleCardBatRegList.size()];
				for(int i=0; i<saleCardBatRegList.size(); i++){
					ids[i] = saleCardBatRegList.get(i).getSaleBatchId();
				}
				params.put("ids", ids);
			}else{
				return LIST;//查询为空
			}
		}
		
		this.page = this.saleCardBatRegDAO.findSaleCardReg(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 储值卡列表
	public String listDeposit() throws Exception{
		this.setCardType(CardType.DEPOSIT.getValue());
		this.setActionSubName("Deposit");
		this.setIsPresell(false);
		return execute();
	}	
	
	// 预售储值卡列表
	public String listPreDeposit() throws Exception{
		this.setCardType(CardType.DEPOSIT.getValue());
		this.setActionSubName("PreDeposit");
		this.setIsPresell(true);
		return execute();
	}
	
	// 预售储值卡未激活列表
	public String listPreDepositCancel() throws Exception{
		this.statusList = RegisterState.getForSaleCancelList();
		this.cardTypeList = CardType.getCardTypeWithoutPoint();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (saleCardReg != null) {
			params.put("cardId", MatchMode.ANYWHERE.toMatchString(saleCardReg.getCardId()));
			params.put("saleBatchId", saleCardReg.getSaleBatchId());
			params.put("cardCustomer", saleCardReg.getCardCustomerName());
			params.put("cardBranch", saleCardReg.getCardBranch());
			params.put("cardClass", saleCardReg.getCardClass());	
			params.put("startDate", this.startDate);
			params.put("endDate", this.endDate);
		}
		if (isCenterOrCenterDeptRoleLogined()){// 运营中心，运营中心部门
			showCardBranch = true;
		} else if (isFenzhiRoleLogined()) {// 运营分支
			showCardBranch = true;
			params.put("fenzhiList", this.getMyManageFenzhi());
		} else if ( isCardSellRoleLogined() || isCardDeptRoleLogined()) {// 售卡代理或发卡机构部门
			this.cardBranchList = this.getMyCardBranch();
			params.put("branchCode", super.getSessionUser().getBranchNo());
		} else if (isCardRoleLogined()) {//发卡机构
//			params.put("cardBranch", super.getSessionUser().getBranchNo());
			this.cardBranchList = this.getMyCardBranch();
			params.put("cardBranchList", cardBranchList);
		} else {
			throw new BizException("没有权限查看售卡撤销记录");
		}
		
		params.put("presell", PresellType.PRESELL.getValue());
		params.put("status", RegisterState.INACTIVE.getValue());// 未激活
		params.put("saleCancel", SaleCancelFlag.NORMAL.getValue());// 普通售卡
		
		this.page = this.saleCardRegService.findSaleCardCancelPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 折扣卡列表
	public String listDiscnt() throws Exception{
		this.setCardType(CardType.DISCNT.getValue());
		this.setActionSubName("Discnt");
		this.setIsPresell(false);
		return execute();
	}

	// 预售折扣卡列表
	public String listPreDiscnt() throws Exception{
		this.setCardType(CardType.DISCNT.getValue());
		this.setActionSubName("PreDiscnt");
		this.setIsPresell(true);
		return execute();
	}

	// 次卡卡列表
	public String listAccu() throws Exception{
		this.setCardType(CardType.ACCU.getValue());
		this.setActionSubName("Accu");
		this.setIsPresell(false);
		return execute();
	}
	
	// 预售次卡卡列表
	public String listPreAccu() throws Exception{
		this.setCardType(CardType.ACCU.getValue());
		this.setActionSubName("PreAccu");
		this.setIsPresell(true);
		return execute();
	}
	
	// 会员卡列表
	public String listMemb() throws Exception{
		this.setCardType(CardType.MEMB.getValue());
		this.setActionSubName("Memb");
		this.setIsPresell(false);
		return execute();
	}
	
	// 预售会员卡列表
	public String listPreMemb() throws Exception{
		this.setCardType(CardType.MEMB.getValue());
		this.setActionSubName("PreMemb");
		this.setIsPresell(true);
		return execute();
	}
	
	// 查询售卡明细
	public void querySaleCardDetail() throws Exception{
		Assert.notNull(this.saleCardBatReg, "售卡对象不能为空");	
		Assert.notNull(this.saleCardBatReg.getSaleBatchId(), "售卡对象批次ID不能为空");			
		
		// 售卡登记簿
		this.saleCardReg = (SaleCardReg)this.saleCardRegDAO.findByPk(this.saleCardBatReg.getSaleBatchId());
		
		// 批量售卡登记簿		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("saleBatchId", this.saleCardBatReg.getSaleBatchId());  
		//this.saleCardBatRegList = this.saleCardBatRegDAO.findSaleCardBatReg(params);
		saleCardBatPage = this.saleCardBatRegDAO.findSaleCardBatByPage(params, this.getPageNumber(), this.getPageSize());
		
		// 卡类型
		this.cardTypeCode = (CardTypeCode)this.cardTypeCodeDAO.findByPk(this.saleCardReg.getCardClass());
		// 卡子类型
		this.cardSubClass = new CardSubClass();
		if (this.saleCardReg.getCardClass().equals(CardType.DEPOSIT.getValue())) {
			this.setActionSubName("Deposit");
		}else if(this.saleCardReg.getCardClass().equals(CardType.DISCNT.getValue())){
			this.setActionSubName("Discnt");
		}else if(this.saleCardReg.getCardClass().equals(CardType.ACCU.getValue())){
			this.setActionSubName("Accu");
		}else if(this.saleCardReg.getCardClass().equals(CardType.MEMB.getValue())){
			this.setActionSubName("Memb");
		}
		
		// 充值类型：0-按金额充值 1-按次充值
		if(this.saleCardReg.getDepositType().equals(DepositType.TIMES.getValue())){
			this.setDepositTypeIsTimes(true);
		}else{
			this.setDepositTypeIsTimes(false);
		}
		
		// 预售卡Action名称
		if(this.saleCardReg.getPresell().equals(PresellType.PRESELL.getValue())){
			this.setActionSubName("Pre" + this.getActionSubName());
			this.setActionSubLabel("预售");
		}else{
			this.setActionSubLabel("售");
		}
		// 购卡客户明细
		this.cardCustomer = (CardCustomer)this.cardCustomerDAO.findByPk(this.saleCardReg.getCardCustomerId());
//		// 卡BIN明细
//		this.cardBin = (CardBin)this.cardBinDAO.findByPk(CardTypeCode.getBinNo(this.cardCustomer.getCardId()));		
//		// 持卡人明细
//		this.cardExtraInfo = (CardExtraInfo)this.cardExtraInfoDAO.findByPk(this.saleCardReg.getCardId());
		// 售卡返利明细
		this.saleRebateRule = (RebateRule)this.rebateRuleDAO.findByPk(this.saleCardReg.getRebateId());
		// 售卡返利分段比例明细列表
		params = new HashMap<String, Object>();
		params.put("rebateId", this.saleCardReg.getRebateId());			
		this.saleRebateRuleDetailList = this.rebateRuleDetailDAO.findRebateRuleDetail(params);
		
		//返利卡明细
		Map<String, Object> rebateMap = new HashMap<String, Object>();
		rebateMap.put("batchId", saleCardBatReg.getSaleBatchId());
		rebateMap.put("rebateFrom", RebateFromType.SALE_CARD.getValue());
		this.rebateCardList = this.rebateCardRegDAO.findRebateCardRegList(rebateMap);
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.querySaleCardDetail();
		
		return DETAIL;
	}
	
	// 明细页面
	public String detailPreDepositCancel() throws Exception{
		this.querySaleCardDetail();
		
		return DETAIL;
	}
	
	// 储值卡售卡
	public String showAddDeposit() throws Exception{		
		this.setCardType(CardType.DEPOSIT.getValue());		
		this.setActionSubName("Deposit");
		this.saleCardBatReg = new SaleCardBatReg();
		this.saleCardBatReg.setPresell(PresellType.REALSELL.getValue());		
		return showAdd();
	}

	// 储值卡预售卡
	public String showAddPreDeposit() throws Exception{		
		this.setCardType(CardType.DEPOSIT.getValue());		
		this.setActionSubName("PreDeposit");
		this.saleCardBatReg = new SaleCardBatReg();
		this.saleCardBatReg.setPresell(PresellType.PRESELL.getValue());		
		return showAdd();
	}

	// 折扣卡售卡
	public String showAddDiscnt() throws Exception{
		this.setCardType(CardType.DISCNT.getValue());
		this.setActionSubName("Discnt");
		this.saleCardBatReg = new SaleCardBatReg();
		this.saleCardBatReg.setPresell(PresellType.REALSELL.getValue());		
		return showAdd();
	}

	// 折扣卡预售卡
	public String showAddPreDiscnt() throws Exception{
		this.setCardType(CardType.DISCNT.getValue());
		this.setActionSubName("PreDiscnt");
		this.saleCardBatReg = new SaleCardBatReg();
		this.saleCardBatReg.setPresell(PresellType.PRESELL.getValue());		
		return showAdd();
	}

	// 次卡售卡
	public String showAddAccu() throws Exception{
		this.setCardType(CardType.ACCU.getValue());
		this.setActionSubName("Accu");
		this.saleCardBatReg = new SaleCardBatReg();
		this.saleCardBatReg.setPresell(PresellType.REALSELL.getValue());		
		return showAdd();
	}
	
	// 次卡预售卡
	public String showAddPreAccu() throws Exception{
		this.setCardType(CardType.ACCU.getValue());
		this.setActionSubName("PreAccu");
		this.saleCardBatReg = new SaleCardBatReg();
		this.saleCardBatReg.setPresell(PresellType.PRESELL.getValue());		
		return showAdd();
	}

	// 会员卡售卡
	public String showAddMemb() throws Exception{
		this.setCardType(CardType.MEMB.getValue());
		this.setActionSubName("Memb");
		this.saleCardBatReg = new SaleCardBatReg();
		this.saleCardBatReg.setPresell(PresellType.REALSELL.getValue());		
		return showAdd();
	}
	
	// 会员预售卡
	public String showAddPreMemb() throws Exception{
		this.setCardType(CardType.MEMB.getValue());
		this.setActionSubName("PreMemb");
		this.saleCardBatReg = new SaleCardBatReg();
		this.saleCardBatReg.setPresell(PresellType.PRESELL.getValue());		
		return showAdd();
	}
	
	/** 
	 * ajax查找指定领卡机构、卡种的第一张待售的卡号 
	 */
	public void ajaxFindFirstCardToSold(){
		String firstCardToSold = cardStockInfoDAO.findFirstCardToSold(this.getSessionUser().getBranchNo(),this.cardType);
		this.respond(String.format("{ 'firstCardToSold': '%s' }", firstCardToSold==null ? "" : firstCardToSold ) );
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		
//		this.rebateTypeList = RebateType.ALL.values();				
//		this.saleCardBatReg = (SaleCardBatReg) this.saleCardBatRegDAO.findByPk(this.saleCardBatReg.getSaleBatchId());
//		
		return MODIFY;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		rebateTypeList = RebateType.getForSellBatch();
		
		// 卡类型对应的客户返利规则
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("cardType", this.getCardType());
//		this.customerRebateRegList = this.customerRebateRegDAO.findCustomerRebateRegByCardType(params);
		
		// 是否按次充值
		if(this.getCardType().equals(CardType.ACCU.getValue())){
			this.setDepositTypeIsTimes(true);
		}else{
			this.setDepositTypeIsTimes(false);
		}
		saleCardReg = new SaleCardReg();
		
		signatureReg = StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES);
		
		// 随机数
		saleCardReg.setRandomSessionId(Long.toString(System.currentTimeMillis()) + WebUtils.getSessionId(request));
		
		// 发卡机构和发卡机构网点和售卡代理
		if (!(isCardOrCardDeptRoleLogined() || isCardSellRoleLogined())) {
			throw new BizException("非售卡机构禁止进入售卡！");
		}
		return ADD;
	}

	// 新增
	public String add() throws Exception {
		String url = "";	
		String msg = "";
		long id = 0L; 
		
		// 设置售卡机构
		if (isCardDeptRoleLogined()) {
			this.saleCardReg.setBranchCode(this.getSessionUser().getDeptId());
		} else {
			this.saleCardReg.setBranchCode(this.getSessionUser().getBranchNo());
		}
		
		logger.debug("setCardClass");
		this.saleCardReg.setCardClass(this.getCardType());
		this.saleCardReg.setPresell(this.saleCardBatReg.getPresell());
		
		// 设置是否使用了手工指定返利，如果是需要手工发起一个流程
		if (calcMode.equals("1") &&( isCardSellRoleLogined() || isCardRoleLogined())) {
			this.saleCardReg.setManual(true);
		} else {
			this.saleCardReg.setManual(false);
		}
		
		// 充值类型：0-按金额充值 1-按次充值
		if(this.getCardType().equals(CardType.ACCU.getValue())){
			this.saleCardReg.setDepositType(DepositType.TIMES.getValue());
		} else {
			this.saleCardReg.setDepositType(DepositType.AMT.getValue());
		}
		
		// 如果是购卡客户指定返利卡，则添加卡号到返利卡字段中
		CardCustomer cardCustomer = (CardCustomer) this.cardCustomerDAO.findByPk(saleCardReg.getCardCustomerId());
		if (cardCustomer.getRebateType().equals(CustomerRebateType.SPECIFY_CARD.getValue())) {
			this.saleCardReg.setRebateType(RebateType.CARD.getValue());
			this.saleCardReg.setRebateCard(cardCustomer.getRebateCard());
		}
		this.saleCardReg.setCardBranch(cardCustomer.getCardBranch());
		logger.debug("设置发卡机构成功！");
		
		Set<String> cardNoSet = new HashSet<String>();
		List<RebateCardReg> rebateCardRegList = new ArrayList<RebateCardReg>();
		//如果是多张卡返利的话，还要在返利表中写数据
		if (RebateType.CARDS.getValue().equals(saleCardReg.getRebateType())) {
			Assert.notEmpty(rebateCardIds, "返利卡号数组不能为空");
			Assert.isTrue(rebateCardIds.length == rebateCount, "返利卡的张数与页面填写的返利张数不一致");
			
			for (int i = 0; i < rebateCardIds.length; i++) {
				RebateCardReg rebateCardReg = new RebateCardReg();
				
				//卡号不能重复
				if (cardNoSet.contains(rebateCardIds[i])) {
					throw new BizException("卡号[" + rebateCardIds[i] + "]的记录重复");
				}
				rebateCardReg.setCardId(rebateCardIds[i]);
				rebateCardReg.setRebateAmt(NumberUtils.createBigDecimal(rebateAmts[i]));
				rebateCardRegList.add(rebateCardReg);
				cardNoSet.add(rebateCardIds[i]);
			}
		}
		
		String serialNo = request.getParameter("serialNo");
		
		logger.debug("调用service中的addSaleCardBatReg方法，各个参数分别为：[" + this.saleCardReg + "][" + saleCardBatReg
				+ "][" + rebateCardRegList + "][" + serialNo + "]");
		
		if(!this.isFormMapFieldTrue("isDepositCoupon")){
			this.saleCardReg.setCouponClass(null);
			this.saleCardReg.setCouponAmt(null);
		}
		
		id = this.saleCardBatRegService.addSaleCardBatReg(this.saleCardReg, this.saleCardBatReg,
				rebateCardRegList, this.getSessionUser(), serialNo);
		
		if(id == -1){
			return ERROR;
		} else {
			msg = "添加批量售卡登记号[" + id + "]，成功！";
			url = "/saleCardBatReg/list" + this.getActionSubName() + ".do";
			this.addActionMessage(url, msg);
			this.log(msg, UserLogType.ADD);
			return SUCCESS;
		}
	}

	// 显示激活页面
	public String showActivate() throws Exception {
		querySaleCardDetail();
		
		return ACTIVATE;
	}
	
	// 激活
	public String activate() throws Exception {
		Assert.notNull(this.saleCardReg, "预售卡对象不能为空");	
		Assert.notNull(this.saleCardReg.getSaleBatchId(), "预售卡对象批次ID不能为空");
		
		// 售卡登记簿明细
		this.saleCardReg = (SaleCardReg)this.saleCardRegDAO.findByPk(this.saleCardReg.getSaleBatchId());

		// 设置激活机构
		this.saleCardReg.setActivateBranch(this.getSessionUser().getBranchNo());
		
		isOk = this.saleCardBatRegService.activate(this.saleCardReg, this.getSessionUser());
		
		msg = this.saleCardBatRegService.getMessage();
		url = "/saleCardBatReg/list" + this.getActionSubName() + ".do";
		this.addActionMessage(url, msg);
		this.log(msg, UserLogType.ADD);	
		if(!isOk){
			bizException = new BizException(msg);
			throw bizException;			
		}
		return SUCCESS;
	}
	
	// 预售卡撤销
	public String preDepositCancel() throws Exception {
		Assert.notNull(this.saleCardBatReg, "预售卡对象不能为空");	
		Assert.notNull(this.saleCardBatReg.getSaleBatchId(), "预售卡对象批次ID不能为空");
		this.saleCardRegService.addSaleCardPreCancel(saleCardBatReg, this.getSessionUser());
		this.addActionMessage("/saleCardBatReg/listPreDepositCancel.do", "撤销预售卡成功！");
		return SUCCESS;
	}

	// 修改
	public String modify() throws Exception {
		
		this.saleCardBatRegService.modifySaleCardBatReg(this.saleCardBatReg, this.getSessionUserCode());
		
		this.addActionMessage("/saleCardBatReg/list.do", "修改售卡登记成功！");
		return SUCCESS;
	}
	
	// 删除
	public String delete() throws Exception {
		
		this.saleCardBatRegService.deleteSaleCardBatReg(this.getSaleCardBatRegId());
		
		this.addActionMessage("/saleCardBatReg/list.do", "删除售卡登记成功！");
		return SUCCESS;
	}
	
	// 审核流程-待审核记录列表
	public String checkList() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		if (isCardRoleLogined()) {
			// 售卡机构做审核，可给售卡的售卡，手动设置返利时的审核
			params.put("checkBranchCode", this.getSessionUser().getBranchNo()); 
		} else if (isCardDeptRoleLogined()) {
			params.put("branchCode", this.getSessionUser().getDeptId());
		} else {
			throw new BizException("没有权限做售卡审核");
		}
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] saleCardBatchIds = this.workflowService.getMyJob(WorkflowConstants.WORKFLOW_SALE_CARD_BATCH, this.getSessionUser());
		
		if (ArrayUtils.isEmpty(saleCardBatchIds)) {
			return CHECK_LIST;
		}
		
		params.put("ids", saleCardBatchIds);
//		params.put("cardBranch", this.getLoginBranchCode()); // 发卡机构做审核
		
		this.page = this.saleCardBatRegDAO.findSaleCardReg(params, this.getPageNumber(), this.getPageSize());
		
		return CHECK_LIST;
	}

	// 审核流程-待审核记录明细
	public String checkDetail() throws Exception{
		this.querySaleCardDetail();	
		return DETAIL;
	}
	
	public SaleCardBatReg getSaleCardBatReg() {
		return saleCardBatReg;
	}

	public void setSaleCardBatReg(SaleCardBatReg saleCardBatReg) {
		this.saleCardBatReg = saleCardBatReg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getStatusList() {
		return statusList;
	}

	public void setStatusList(Collection statusList) {
		this.statusList = statusList;
	}

	public Long getSaleCardBatRegId() {
		return saleCardBatRegId;
	}

	public void setSaleCardBatRegId(Long saleCardBatRegId) {
		this.saleCardBatRegId = saleCardBatRegId;
	}

	public CardExtraInfo getCardExtraInfo() {
		return cardExtraInfo;
	}

	public void setCardExtraInfo(CardExtraInfo cardExtraInfo) {
		this.cardExtraInfo = cardExtraInfo;
	}

	public String getIsHasCustName() {
		return isHasCustName;
	}

	public void setIsHasCustName(String isHasCustName) {
		this.isHasCustName = isHasCustName;
	}

	public List<AccuClassDef> getAccuClassList() {
		return accuClassList;
	}

	public void setAccuClassList(List<AccuClassDef> accuClassList) {
		this.accuClassList = accuClassList;
	}

	public List<MembClassDef> getMembClassList() {
		return membClassList;
	}

	public void setMembClassList(List<MembClassDef> membClassList) {
		this.membClassList = membClassList;
	}

	public List<DiscntClassDef> getDiscntClassList() {
		return discntClassList;
	}

	public void setDiscntClassList(List<DiscntClassDef> discntClassList) {
		this.discntClassList = discntClassList;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public List<CardSubClass> getCardSubClassList() {
		return cardSubClassList;
	}

	public void setCardSubClassList(List<CardSubClass> cardSubClassList) {
		this.cardSubClassList = cardSubClassList;
	}

	public List<PointClassDef> getPointClassList() {
		return pointClassList;
	}

	public void setPointClassList(List<PointClassDef> pointClassList) {
		this.pointClassList = pointClassList;
	}

	public String getActionSubName() {
		return actionSubName;
	}

	public void setActionSubName(String actionSubName) {
		this.actionSubName = actionSubName;
	}

	public CardCustomer getCardCustomer() {
		return cardCustomer;
	}

	public void setCardCustomer(CardCustomer cardCustomer) {
		this.cardCustomer = cardCustomer;
	}

	public RebateRule getSaleRebateRule() {
		return saleRebateRule;
	}

	public void setSaleRebateRule(RebateRule saleRebateRule) {
		this.saleRebateRule = saleRebateRule;
	}

	public List<RebateRuleDetail> getSaleRebateRuleDetailList() {
		return saleRebateRuleDetailList;
	}

	public void setSaleRebateRuleDetailList(
			List<RebateRuleDetail> saleRebateRuleDetailList) {
		this.saleRebateRuleDetailList = saleRebateRuleDetailList;
	}

	public CardBin getCardBin() {
		return cardBin;
	}

	public void setCardBin(CardBin cardBin) {
		this.cardBin = cardBin;
	}

	public CardSubClass getCardSubClass() {
		return cardSubClass;
	}

	public void setCardSubClass(CardSubClass cardSubClass) {
		this.cardSubClass = cardSubClass;
	}

	public CardTypeCode getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(CardTypeCode cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}

	public String getActionSubLabel() {
		return actionSubLabel;
	}

	public void setActionSubLabel(String actionSubLabel) {
		this.actionSubLabel = actionSubLabel;
	}

	public SaleCardReg getSaleCardReg() {
		return saleCardReg;
	}

	public void setSaleCardReg(SaleCardReg saleCardReg) {
		this.saleCardReg = saleCardReg;
	}

	public String[] getCardCount() {
		return cardCount;
	}

	public void setCardCount(String[] cardCount) {
		this.cardCount = cardCount;
	}

	public String[] getAmt() {
		return amt;
	}

	public void setAmt(String[] amt) {
		this.amt = amt;
	}

	public String[] getExpenses() {
		return expenses;
	}

	public void setExpenses(String[] expenses) {
		this.expenses = expenses;
	}

	public String[] getStartCardId() {
		return startCardId;
	}

	public void setStartCardId(String[] startCardId) {
		this.startCardId = startCardId;
	}

	public double[] getResultRealAmt() {
		return resultRealAmt;
	}

	public void setResultRealAmt(double[] resultRealAmt) {
		this.resultRealAmt = resultRealAmt;
	}

	public double[] getResultRebateAmt() {
		return resultRebateAmt;
	}

	public void setResultRebateAmt(double[] resultRebateAmt) {
		this.resultRebateAmt = resultRebateAmt;
	}

	public double[] getResultExpenses() {
		return resultExpenses;
	}

	public void setResultExpenses(double[] resultExpenses) {
		this.resultExpenses = resultExpenses;
	}

	public double[] getResultAmt() {
		return resultAmt;
	}

	public void setResultAmt(double[] resultAmt) {
		this.resultAmt = resultAmt;
	}

	public double getResultSumRealAmt() {
		return resultSumRealAmt;
	}

	public void setResultSumRealAmt(double resultSumRealAmt) {
		this.resultSumRealAmt = resultSumRealAmt;
	}

	public double getResultSumRebateAmt() {
		return resultSumRebateAmt;
	}

	public void setResultSumRebateAmt(double resultSumRebateAmt) {
		this.resultSumRebateAmt = resultSumRebateAmt;
	}

	public double getResultSumExpenses() {
		return resultSumExpenses;
	}

	public void setResultSumExpenses(double resultSumExpenses) {
		this.resultSumExpenses = resultSumExpenses;
	}

	public double getResultSumAmt() {
		return resultSumAmt;
	}

	public void setResultSumAmt(double resultSumAmt) {
		this.resultSumAmt = resultSumAmt;
	}

	public RebateRule getRebateRule() {
		return rebateRule;
	}

	public void setRebateRule(RebateRule rebateRule) {
		this.rebateRule = rebateRule;
	}

	public Rebate getRebate() {
		return rebate;
	}

	public void setRebate(Rebate rebate) {
		this.rebate = rebate;
	}

	public String[] getResultCardId() {
		return resultCardId;
	}

	public void setResultCardId(String[] resultCardId) {
		this.resultCardId = resultCardId;
	}

	public List<CustomerRebateReg> getCustomerRebateRegList() {
		return customerRebateRegList;
	}

	public void setCustomerRebateRegList(
			List<CustomerRebateReg> customerRebateRegList) {
		this.customerRebateRegList = customerRebateRegList;
	}

	public String getUnActivatedLabel() {
		return unActivatedLabel;
	}

	public void setUnActivatedLabel(String unActivatedLabel) {
		this.unActivatedLabel = unActivatedLabel;
	}

	public boolean getIsPresell() {
		return isPresell;
	}

	public void setIsPresell(boolean isPresell) {
		this.isPresell = isPresell;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public List<RebateRule> getRebateRuleList() {
		return rebateRuleList;
	}

	public void setRebateRuleList(List<RebateRule> rebateRuleList) {
		this.rebateRuleList = rebateRuleList;
	}

	public List getRebateTypeList() {
		return rebateTypeList;
	}

	public void setRebateTypeList(List rebateTypeList) {
		this.rebateTypeList = rebateTypeList;
	}

	public String getRebateType() {
		return rebateType;
	}

	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}

	public String getCalcMode() {
		return calcMode;
	}

	public void setCalcMode(String calcMode) {
		this.calcMode = calcMode;
	}

	public boolean isDepositTypeIsTimes() {
		return depositTypeIsTimes;
	}

	public void setDepositTypeIsTimes(boolean depositTypeIsTimes) {
		this.depositTypeIsTimes = depositTypeIsTimes;
	}

	public Paginater getSaleCardBatPage() {
		return saleCardBatPage;
	}

	public void setSaleCardBatPage(Paginater saleCardBatPage) {
		this.saleCardBatPage = saleCardBatPage;
	}

	public boolean isSignatureReg() {
		return signatureReg;
	}

	public void setSignatureReg(boolean signatureReg) {
		this.signatureReg = signatureReg;
	}

	public String[] getEndCardId() {
		return endCardId;
	}

	public void setEndCardId(String[] endCardId) {
		this.endCardId = endCardId;
	}

	public boolean isShowCardBranch() {
		return showCardBranch;
	}

	public void setShowCardBranch(boolean showCardBranch) {
		this.showCardBranch = showCardBranch;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public String getCardBranchName() {
		return cardBranchName;
	}

	public void setCardBranchName(String cardBranchName) {
		this.cardBranchName = cardBranchName;
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

	public double getResultFeeAmt() {
		return resultFeeAmt;
	}

	public void setResultFeeAmt(double resultFeeAmt) {
		this.resultFeeAmt = resultFeeAmt;
	}

	public String[] getRebateCardIds() {
		return rebateCardIds;
	}

	public void setRebateCardIds(String[] rebateCardIds) {
		this.rebateCardIds = rebateCardIds;
	}

	public String[] getRebateAmts() {
		return rebateAmts;
	}

	public void setRebateAmts(String[] rebateAmts) {
		this.rebateAmts = rebateAmts;
	}

	public Long getRebateCount() {
		return rebateCount;
	}

	public void setRebateCount(Long rebateCount) {
		this.rebateCount = rebateCount;
	}

	public List<RebateCardReg> getRebateCardList() {
		return rebateCardList;
	}

	public void setRebateCardList(List<RebateCardReg> rebateCardList) {
		this.rebateCardList = rebateCardList;
	}
	
	public List<CardType> getCardTypeList() {
		return cardTypeList;
	}

	public void setCardTypeList(List<CardType> cardTypeList) {
		this.cardTypeList = cardTypeList;
	}
}
