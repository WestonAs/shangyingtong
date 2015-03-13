package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.CommonHelper;
import flink.util.Paginater;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.CardCustomerDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.dao.CustomerRebateRegDAO;
import gnete.card.dao.DepositBatRegDAO;
import gnete.card.dao.DepositRegDAO;
import gnete.card.dao.RebateCardRegDAO;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.dao.RebateRuleDetailDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardCustomer;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.CustomerRebateReg;
import gnete.card.entity.DepositBatReg;
import gnete.card.entity.DepositReg;
import gnete.card.entity.Rebate;
import gnete.card.entity.RebateCardReg;
import gnete.card.entity.RebateRule;
import gnete.card.entity.RebateRuleDetail;
import gnete.card.entity.flag.DepositCancelFlag;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.CustomerRebateType;
import gnete.card.entity.type.DepositFromPageType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.PreDepositType;
import gnete.card.entity.type.RebateFromType;
import gnete.card.entity.type.RebateType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.DepositService;
import gnete.card.service.RebateRuleService;
import gnete.card.service.mgr.BranchBizConfigCache;
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
 * 批量充值登记簿Action
 * DepositBatRegAction.java.
 * 
 * @author BenYan
 * @since JDK1.5
 * @history 2010-8-26
 */
public class DepositBatRegAction extends BaseAction {
	
	@Autowired
	private DepositBatRegDAO depositBatRegDAO;
	@Autowired
	private DepositService depositService;	
	@Autowired
	private RebateRuleService rebateRuleService;
	@Autowired
	private CardCustomerDAO cardCustomerDAO;
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private RebateRuleDAO rebateRuleDAO;    
	@Autowired
	private RebateRuleDetailDAO rebateRuleDetailDAO;
	@Autowired
	private CustomerRebateRegDAO customerRebateRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	@Autowired
	private DepositRegDAO depositRegDAO;
	@Autowired
	private RebateCardRegDAO rebateCardRegDAO;
	
	/** 充值登记簿对象 */
	private DepositReg depositReg;
	/** 批量充值登记簿对象 */
	private DepositBatReg depositBatReg;
	/** 购卡客户 */
	private CardCustomer cardCustomer;    
	/** 客户返利 */
	private RebateRule saleRebateRule;
	
	private List<CustomerRebateReg> customerRebateRegList;	
	private List<RebateRuleDetail> saleRebateRuleDetailList;
	
    /** 卡类型 */
    private String cardType;    
    private CardTypeCode cardTypeCode;
    
    /** 页面ACTION子名 */
    private String actionSubName;
    
    private Paginater page;
    
    private Collection statusList;
	private Paginater depositBatPage;
    
    // 输入数据
	private String binNo;
	private String[] startCardId;
	private String[] cardCount;
	private String[] amt;
		
	private String[] cardIds;
	
	// 输出数据
	private String resultErrorMsg;
	private double resultSumAmt;
	private double resultSumRealAmt;
	private double resultSumRebateAmt;
	private String[] resultCardId;	
	private double[] resultAmt;	
	private double[] resultRealAmt;
	private double[] resultRebateAmt;
	/** 手续费 */
	private double resultFeeAmt;
	
	private RebateRule rebateRule;
	private Rebate rebate;
	private List<CardTypeCode> cardClassList;
	
	private CustomerRebateReg customerRebateReg;
	private CardInfo cardInfo;
	private List<RebateRuleDetail> rebateRuleDetailList;

	private List rebateTypeList;
	private String calcMode;
	
	private String fromPage;

    /** 是否按次充值 */
    private boolean depositTypeIsTimes;
    private String preDeposit;
    
    /** 是否需要在登记簿中记录签名信息 */
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
	
	/** 服务端计算实收金额、返利金额等，返回给前端 */
	public String calRealAmt(){
		try{
			StopWatch stopWatchG = new StopWatch();
			
			stopWatchG.start();
			
			// 产生所有卡号
			if(!setCardIds()){				
				return "hidden";
			}
			// 查询返利规则
			this.rebateRule = (RebateRule) this.rebateRuleDAO.findByPk(this.depositReg.getRebateId());
			
	    	String type = request.getParameter("type");	// type为0时是充值次数
			
			int count = this.startCardId.length;
//			this.depositBatRegList = new ArrayList();
			resultSumRealAmt = 0;
			resultSumRebateAmt = 0;
			resultSumAmt = 0;
			DepositBatReg depositBatReg2;
			int k = 0;
			
			// 售卡手续费
	    	BigDecimal sumFeeAmt = new BigDecimal(0);
			
			for(int i = 0; i < count; i++) {
				StopWatch stopWatch = new StopWatch();
				stopWatch.start();
				
				// 计算各段首张卡金额等
				this.depositBatReg = new DepositBatReg();
				this.depositBatReg.setCardId(this.startCardId[i]);
				
				// 充值金额
				if (!NumberUtils.isNumber(this.amt[i])) {
					this.resultErrorMsg = "充值金额或次数必须为数字";
					return "hidden";
				}
				BigDecimal depositAmt = AmountUtil.format(NumberUtils.createBigDecimal(amt[i]));
				
				// 充值金额必须为正数
				if (AmountUtil.lt(depositAmt, BigDecimal.ZERO)) {
					this.resultErrorMsg = "充值金额或次数必须大于0";
					return "hidden";
				}
				
				this.depositBatReg.setAmt(depositAmt);

		    	BigDecimal actual = null;
		    	this.cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.startCardId[i]);
				if ("0".equals(type)) {
		    		// 根据卡子类型得到次卡子类型
		    		String subClass = this.cardInfo.getCardSubclass();
		    		CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(subClass);
		    		String frequencyClass = cardSubClassDef.getFrequencyClass();
		    		AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(frequencyClass);
		    		
		    		if (accuClassDef == null) {
		    			this.resultErrorMsg = "该卡["+ this.startCardId[i] +"]没有次卡子类型";
						return "hidden";
		    		}
		    		// 实收金额 = 充值次数 * 次卡清算金额
		    		actual = AmountUtil.multiply(depositAmt, accuClassDef.getSettAmt());
		    	} else {
		    		actual = depositAmt; 
		    	}
				
//				// 每张卡的充值手续费 充值手续费 = 充值总金额*手续费比例/100
//				BigDecimal feeAmt = AmountUtil.divide(AmountUtil.multiply(actual, depositReg.getFeeRate()), new BigDecimal(100));
//
//				sumFeeAmt = AmountUtil.add(sumFeeAmt, feeAmt); // 手续费
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("amt", actual.toString());  
				params.put("rebateType", this.depositReg.getRebateType());  
				params.put("rebateRule", this.rebateRule);  
			
				this.rebate = this.rebateRuleService.calculateRebate(params);  // 计算返利金额等
				depositBatReg.setRealAmt(AmountUtil.format(this.rebate.getRealAmt()));
				depositBatReg.setRebateAmt(AmountUtil.format(this.rebate.getRebateAmt()));
				
				for(int j = 0; j < Integer.parseInt(this.cardCount[i]); j++){
					depositBatReg2 = new DepositBatReg();
					depositBatReg2.setCardId(cardIds[k]); // 卡ID加1，其余相同
					
					depositBatReg2.setAmt(this.depositBatReg.getAmt());
					depositBatReg2.setRealAmt(this.depositBatReg.getRealAmt());
					depositBatReg2.setRebateAmt(this.depositBatReg.getRebateAmt());
//					depositBatRegList.add(depositBatReg2);
										
					resultSumAmt += Double.valueOf(depositBatReg2.getAmt().toString());
					resultSumRealAmt += Double.valueOf(depositBatReg2.getRealAmt().toString());
					resultSumRebateAmt += Double.valueOf(depositBatReg2.getRebateAmt().toString());
					k++;
					
					// 每张卡的充值手续费 充值手续费 = 充值总金额*手续费比例/100
					BigDecimal feeAmt = AmountUtil.divide(AmountUtil.multiply(actual, depositReg.getFeeRate()), new BigDecimal(100));

					sumFeeAmt = AmountUtil.add(sumFeeAmt, feeAmt); // 手续费
				}
				
				stopWatch.stop();
				logger.debug("计算实收金额消耗时间[" + stopWatch + "]");
			}
			
			// 充值手续费 = 充值总金额*手续费比例/100
			resultFeeAmt = sumFeeAmt.doubleValue();
			
			// 实收金额 = 返利后的金额 + 手续费
			resultSumRealAmt = resultSumRealAmt + resultFeeAmt;
			
			stopWatchG.stop();
			logger.debug("计算总的实收金额消耗总时间[" + stopWatchG + "]");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "hidden";
	}
	
	/** 根据前端传入的卡号段产生所有卡号ID */
	private boolean setCardIds(){
		int count = this.startCardId.length;
		int k = 0;				
		for(int i=0; i<count; i++){
			k += Integer.parseInt(this.cardCount[i]);
		}
		this.cardIds = new String[k];
		k = 0;
		for(int i=0; i < count; i++){
			
			// 得到卡号数组,19位的是新卡，否则是旧卡 2012年9月25日注释
			/*int subCount = Integer.parseInt(this.cardCount[i]);
			String[] cardArray = this.startCardId[i].length() == 19 
						? CardUtil.getCard(this.startCardId[i], Integer.parseInt(this.cardCount[i]))
						: CardUtil.getOldCard(this.startCardId[i], Integer.parseInt(this.cardCount[i]));
			for(int j = 0; j < subCount; j++){
				
				// 检查卡号
				CardInfo batCardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardArray[j]);
				if (batCardInfo == null) {
					this.resultErrorMsg = "第[" + (k + 1) + "]张卡号[" + cardArray[j] + "]不存在";
					return false;
				}
				if (!CardState.ACTIVE.getValue().equals(batCardInfo.getCardStatus())) {
					this.resultErrorMsg = "第[" + (k + 1) + "]张卡号[" + cardArray[j] + "]不是“正常(已激活)”状态";
					return false;
				}
				
				cardIds[k] = cardArray[j];
				// 如果是旧卡
				if (this.startCardId[i].length() == 18) {
					// 按旧校验位生成的第一种方法生成的
					if (batCardInfo == null) {
						String cardPrex = startCardId[i].substring(0, startCardId[i].length() - 1);
						String cardId = cardPrex + CardUtil.getOldCardLast(cardPrex);
						batCardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
						
						cardIds[k] = cardId;
					}
					
					// 如果第4位到7位不是0000的话则不能充值
					if (!"0000".equals(cardIds[i].substring(3, 7))) {
						this.resultErrorMsg = "第[" + (k + 1) + "]张卡号[" + cardArray[j] + "]不能充值！";
						return false;
					}
				}
				
				k++;
			}*/
			
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			// 2012年9月25日新改的
			String endCardId = CardUtil.getEndCard(this.startCardId[i], Integer.parseInt(this.cardCount[i]));
			
			CardInfo endCardInfo = (CardInfo) this.cardInfoDAO.findByPk(endCardId);
			if (endCardInfo == null) {
				this.resultErrorMsg = "最后一张卡号为[" + endCardId + "]的卡不存在";
				return false;
			}
			
			List<CardInfo> cardInfoList = this.cardInfoDAO.getCardList(this.startCardId[i], endCardId);
			CardInfo batCardInfo = null;
			String cardIdInLoop = "";
			for (int j = 0; j < cardInfoList.size(); j++) {
				batCardInfo = cardInfoList.get(j);
				cardIdInLoop = batCardInfo.getCardId();
				
				if (!CardState.ACTIVE.getValue().equals(batCardInfo.getCardStatus())) {
					this.resultErrorMsg = "第[" + (k + 1) + "]张卡号[" + cardIdInLoop + "]不是“正常(已激活)”状态";
					return false;
				}
				
				cardIds[k] = cardIdInLoop;
				// 如果是旧卡
				if (cardIdInLoop.length() == 18) {
					// 如果第4位到7位不是0000的话则不能充值
					if (!"0000".equals(cardIds[i].substring(3, 7))) {
						this.resultErrorMsg = "第[" + (k + 1) + "]张卡号[" + cardIdInLoop + "]不能充值！";
						return false;
					}
				}
				
				k++;
			}
			stopWatch.stop();
			logger.debug("取起始卡号和结束卡号之间的卡消耗时间[" + stopWatch + "]");
		}
		return true;
	}
	
	/*// 检查批量充值卡号是否有重号
	private boolean checkCardRepeated(){
		int count = cardIds.length;
		String cardId = "";
		String msg = "";	
		for(int i=0; i<count; i++){
			cardId = cardIds[i];
			for(int j=i+1; j<count; j++){
				if(cardId.equals(cardIds[j])){
					msg += "[" + cardId + "]";
					break;
				}
			}
		}
		if(msg.length() > 0){
			this.resultErrorMsg = "下列卡号重号，请重新输入！\n\n" + msg;
			return false;
		}
		return true;
	}*/
	
//	// 检查批量充值卡的卡BIN
//	private boolean checkBinNo(){
//		int count = cardIds.length;
//		String cardBinNo = "";
//		String msg = "";	
//		for(int i=0; i<count; i++){
//			cardBinNo = CardBin.getBinNo(cardIds[i]);
//			if(!cardBinNo.equals(this.binNo)){
//				msg += cardIds[i] + "\n";
//			}
//		}
//		if(msg.length() > 0){
//			this.resultErrorMsg = "卡号的第 " + CardBin.BINPOS + " 位开始取 " 
//				+ CardBin.BINSIZE + " 位是卡BIN!\n\n";
//			this.resultErrorMsg += "下列卡号的卡BIN错误，请重新输入！\n\n" + msg;
//			return false;
//		}
//		return true;
//	}
	
	/*// 检查卡号是否存在
	private boolean isExistCardId(){
		int count = cardIds.length;
		CardInfo cardInfo;
		for(int i=0; i<count; i++){
			cardInfo = (CardInfo)this.cardInfoDAO.findByPk(cardIds[i]);
			
			// 如果是旧卡
			if (cardIds[i].length() == 18) {
				if (cardInfo == null) {
					String cardPrex = cardIds[i].substring(0, cardIds[i].length() - 1);
					String cardId1 = cardPrex + CardUtil.getOldCardLast(cardPrex);
					
					cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId1);
					cardIds[i] = cardId1;
				}
				// 如果第4位到7位不是0000的话则不能充值
				if (!"0000".equals(cardIds[i].substring(3, 7))) {
					this.resultErrorMsg = "第[" + (i + 1) + "]张卡号[" + cardIds[i] + "]不能充值！";
					return false;
				}
			}
			
			if((cardInfo==null )|| (cardInfo!=null && 
					!(cardInfo.getCardStatus().equals(CardState.ACTIVE.getValue())))){
				this.resultErrorMsg = "第[" + (i + 1) + "]张卡号不存在或不是“正常(已激活)”状态，请重新输入！";
				return false;
			}
		}
		return true;
	}*/
	

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		this.statusList = RegisterState.ALL.values();
		//this.cardClassList = this.cardTypeCodeDAO.findCardTypeCode(CardTypeState.NORMAL.getValue());
		this.rebateTypeList = RebateType.getForSellBatch();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (depositBatReg != null) {
			params.put("depositBatchId", depositBatReg.getDepositBatchId());
//			params.put("cardId", MatchMode.ANYWHERE.toMatchString(depositBatReg.getCardId()));
			//params.put("cardClass", depositBatReg.getCardClass());		
			params.put("status", depositBatReg.getStatus());
			
			params.put("cardBranch", depositReg.getCardBranch()); //发卡机构
			params.put("cardCustomer", MatchMode.ANYWHERE.toMatchString(depositReg.getCardCustomerName()));// 购卡客户
			params.put("rebateType", depositReg.getRebateType()); // 返利方式
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		
		if (isCenterOrCenterDeptRoleLogined()) {// 如果登录用户为运营中心，运营中心部门时，查看所有
			showCardBranch = true;

		} else if (isFenzhiRoleLogined()) {// 登录用户为分支时，查看自己及自己的下级分支机构管理的所有发卡机构的记录
			showCardBranch = true;
			// params.put("fenzhiCode", this.getLoginBranchCode());
			params.put("fenzhiList", this.getMyManageFenzhi());
			
		} else if (isCardSellRoleLogined()) {// 售卡代理
			this.cardBranchList = this.getMyCardBranch();
			params.put("depositBranch", this.getLoginBranchCode());
			
		}else if(isCardDeptRoleLogined()){ // 发卡机构部门
			this.cardBranchList = this.getMyCardBranch();
			params.put("depositBranch", this.getSessionUser().getDeptId());
			
		} else if (isCardRoleLogined()) {// 如果登录用户为发卡机构时
			this.cardBranchList = this.getMyCardBranch();
			params.put("cardBranchList", cardBranchList);
			// params.put("cardBranch", super.getSessionUser().getBranchNo());
			
		} else {
			throw new BizException("没有权限查看批量充值记录");
		}

		// 设置页面来源，取消卡类型设置
		params.put("fromPage", this.getFromPage());
		// 设置卡类型
//		params.put("cardClass", this.getCardType()); 
		
		// 是否按文件方式
		params.put("fileDeposit", Symbol.NO);
//		params.put("cancelFlag", Symbol.NO);
		params.put("depositCancel", DepositCancelFlag.NORMAL.getValue());// 正常充值
		params.put("isDepositBatch", true);
		
		//查询优化
		if(depositBatReg != null && CommonHelper.isNotEmpty(depositBatReg.getCardId())){
			Map<String, Object> depositBatRegParams = new HashMap<String, Object>();
			depositBatRegParams.put("cardId", depositBatReg.getCardId());
			List<DepositBatReg> depositCardBatRegList = depositBatRegDAO.findDepositBatReg(depositBatRegParams);
			if(CommonHelper.checkIsNotEmpty(depositCardBatRegList)){
				Long[] ids = new Long[depositCardBatRegList.size()];
				for(int i=0; i<depositCardBatRegList.size(); i++){
					ids[i] = depositCardBatRegList.get(i).getDepositBatchId();
				}
				params.put("ids", ids);
			}else{
				return LIST;//查询为空
			}
		}
		
		this.page = this.depositBatRegDAO.findDepositReg(params, this.getPageNumber(), this.getPageSize());
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查询[" 
				+ DepositFromPageType.valueOf((String) params.get("fromPage")).getName() +"]列表成功！");
		return LIST;
	}
	
	// 储值卡列表
	public String listDeposit() throws Exception{
		this.setCardType(CardType.DEPOSIT.getValue());
		this.setActionSubName("Deposit");
		this.setFromPage(DepositFromPageType.DEPOSIT.getValue());
		return execute();
	}
	
	// 折扣卡列表
	public String listDiscnt() throws Exception{
		this.setCardType(CardType.DISCNT.getValue());
		this.setActionSubName("Discnt");
		this.setFromPage(DepositFromPageType.DISCNT.getValue());
		return execute();
	}

	// 次卡卡列表
	public String listAccu() throws Exception{
		this.setCardType(CardType.ACCU.getValue());
		this.setActionSubName("Accu");
		this.setFromPage(DepositFromPageType.ACCU.getValue());
		return execute();
	}
	
	// 预充储值卡列表
	public String listPreDeposit() throws Exception{
		this.setCardType(CardType.DEPOSIT.getValue());
		this.setActionSubName("PreDeposit");
		this.setFromPage(DepositFromPageType.PRE_DEPOSIT.getValue());
		this.setPreDeposit(PreDepositType.PRE_DEPOSIT.getValue());
		return execute();
	}
	
	// 预售储值卡未激活列表
	public String listPreDepositCancel() throws Exception{
		this.statusList = RegisterState.getForSaleCancelList();
		this.cardTypeList = CardType.getCardTypeWithoutPoint();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (depositReg != null) {
			params.put("cardId", MatchMode.ANYWHERE.toMatchString(depositReg.getCardId()));
			params.put("depositBatchId", depositReg.getDepositBatchId());
			params.put("status", depositReg.getStatus());		
			params.put("cardClass", depositReg.getCardClass());	
		}

		if (isCenterOrCenterDeptRoleLogined()) {// 如果登录用户为运营中心，运营中心部门
			showCardBranch = true;

		} else if (isFenzhiRoleLogined()) {// 登录用户为分支时，查看自己及自己的下级分支机构管理的所有发卡机构的记录
			showCardBranch = true;
			params.put("fenzhiList", this.getMyManageFenzhi());
			
		} else if (isCardDeptRoleLogined() || isCardSellRoleLogined()) {// 登录用户为售卡代理或发卡机构部门时
			this.cardBranchList = this.getMyCardBranch();
			params.put("branchCode", super.getSessionUser().getBranchNo());
			
		} else if (isCardRoleLogined()) {// 如果登录用户为发卡机构时，查看发卡机构为自己的记录
		// params.put("cardBranch", super.getSessionUser().getBranchNo());
			this.cardBranchList = this.getMyCardBranch();
			params.put("cardBranchList", cardBranchList);

		} else {
			throw new BizException("没有权限查看充值撤销记录");
		}
		
		params.put("preDeposit", PreDepositType.PRE_DEPOSIT.getValue());
		params.put("status", RegisterState.INACTIVE.getValue());// 未激活
		params.put("depositCancel", DepositCancelFlag.NORMAL.getValue());// 普通售卡
		
		this.page = this.depositService.findDepositRegCancelPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 预充折扣卡列表
	public String listPreDiscnt() throws Exception{
		this.setCardType(CardType.DISCNT.getValue());
		this.setActionSubName("PreDiscnt");
		this.setFromPage(DepositFromPageType.PRE_DISCNT.getValue());
		this.setPreDeposit(PreDepositType.PRE_DEPOSIT.getValue());
		return execute();
	}
	
	// 预充次卡卡列表
	public String listPreAccu() throws Exception{
		this.setCardType(CardType.ACCU.getValue());
		this.setActionSubName("PreAccu");
		this.setFromPage(DepositFromPageType.PRE_ACCU.getValue());
		this.setPreDeposit(PreDepositType.PRE_DEPOSIT.getValue());
		return execute();
	}
	
	// 明细页面
	public String detail() throws Exception{
		queryDetail();
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查询批量充值批次[" + depositReg.getDepositBatchId() + "]明细！");
		return DETAIL;
	}
	
	// 明细页面
	public String detailPreDepositCancel() throws Exception{
		queryDetail();
		logger.debug("用户[" + this.getSessionUserCode() + "]查询批量充值批次[" + depositReg.getDepositBatchId() + "]明细！");
		return DETAIL;
	}

	private void queryDetail() throws BizException {
		Assert.notNull(this.depositBatReg, "充值对象不能为空");	
		Assert.notNull(this.depositBatReg.getDepositBatchId(), "充值对象批次ID不能为空");			
		
		// 充值登记簿
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("depositBatchId", this.depositBatReg.getDepositBatchId());
		this.depositReg = (DepositReg)(this.depositBatRegDAO.findDepositReg(params)).get(0);		
		//this.depositReg = (DepositReg)this.depositRegDAO.findByPk(this.depositBatReg.getDepositBatchId());
		
		// 批量充值登记簿		
		params = new HashMap<String, Object>();
		params.put("depositBatchId", this.depositBatReg.getDepositBatchId());  
		
		this.depositBatPage = this.depositBatRegDAO.findDepositBatRegByPage(params, this.getPageNumber(), this.getPageSize());
		
		// 充值类型：0-按金额充值 1-按次充值
		if(this.depositReg.getDepositType().equals(DepositType.TIMES.getValue())){
			this.setDepositTypeIsTimes(true);
		}else{
			this.setDepositTypeIsTimes(false);
		}
		
		// 卡类型
		this.cardTypeCode = (CardTypeCode)this.cardTypeCodeDAO.findByPk(this.depositReg.getCardClass());
		if (StringUtils.equals(this.depositReg.getFromPage() ,DepositFromPageType.DEPOSIT.getValue())) {
			this.setActionSubName("Deposit");
		} else if(StringUtils.equals(this.depositReg.getFromPage() ,DepositFromPageType.DISCNT.getValue())){
			this.setActionSubName("Discnt");
		} else if(StringUtils.equals(this.depositReg.getFromPage() ,DepositFromPageType.ACCU.getValue())){
			this.setActionSubName("Accu");
		} else if (StringUtils.equals(this.depositReg.getFromPage() ,DepositFromPageType.PRE_DEPOSIT.getValue())) {
			this.setActionSubName("PreDeposit");
		} else if(StringUtils.equals(this.depositReg.getFromPage() ,DepositFromPageType.PRE_DISCNT.getValue())){
			this.setActionSubName("PreDiscnt");
		} else if(StringUtils.equals(this.depositReg.getFromPage() ,DepositFromPageType.PRE_ACCU.getValue())){
			this.setActionSubName("PreAccu");
		} else if (StringUtils.equals(this.depositReg.getFromPage(), DepositFromPageType.PRE_FILE.getValue())) {
			this.setActionSubName("PreFile");
		}
		
		// 购卡客户明细
		this.cardCustomer = (CardCustomer)this.cardCustomerDAO.findByPk(this.depositReg.getCardCustomerId());
		// 充值返利明细
		this.saleRebateRule = (RebateRule)this.rebateRuleDAO.findByPk(this.depositReg.getRebateId());
		// 充值返利分段比例明细列表
		params = new HashMap<String, Object>();
		params.put("rebateId", this.depositReg.getRebateId());			
		this.rebateRuleDetailList = this.rebateRuleDetailDAO.findRebateRuleDetail(params);
		
		//返利卡明细
		Map<String, Object> rebateMap = new HashMap<String, Object>();
		rebateMap.put("batchId", depositBatReg.getDepositBatchId());
		rebateMap.put("rebateFrom", RebateFromType.DEPOSIT.getValue());
		this.rebateCardList = this.rebateCardRegDAO.findRebateCardRegList(rebateMap);
	}
	
	// 储值卡充值
	public String showAddDeposit() throws Exception{		
		this.setCardType(CardType.DEPOSIT.getValue());		
		this.setActionSubName("Deposit");
		this.setFromPage(DepositFromPageType.DEPOSIT.getValue());
		
		return showAdd();
	}

	// 折扣卡充值
	public String showAddDiscnt() throws Exception{
		this.setCardType(CardType.DISCNT.getValue());
		this.setActionSubName("Discnt");
		this.setFromPage(DepositFromPageType.DISCNT.getValue());
		return showAdd();
	}

	// 次卡充值
	public String showAddAccu() throws Exception{
		this.setCardType(CardType.ACCU.getValue());
		this.setActionSubName("Accu");
		this.setFromPage(DepositFromPageType.ACCU.getValue());
		return showAdd();
	}
	
	// 预充储值卡充值
	public String showAddPreDeposit() throws Exception{		
		this.setCardType(CardType.DEPOSIT.getValue());		
		this.setActionSubName("PreDeposit");
		this.setFromPage(DepositFromPageType.PRE_DEPOSIT.getValue());
		this.setPreDeposit(PreDepositType.PRE_DEPOSIT.getValue());
		
		return showAdd();
	}

	// 预充折扣卡充值
	public String showAddPreDiscnt() throws Exception{
		this.setCardType(CardType.DISCNT.getValue());
		this.setActionSubName("PreDiscnt");
		this.setFromPage(DepositFromPageType.PRE_DISCNT.getValue());
		this.setPreDeposit(PreDepositType.PRE_DEPOSIT.getValue());
		return showAdd();
	}

	// 预充次卡充值
	public String showAddPreAccu() throws Exception{
		this.setCardType(CardType.ACCU.getValue());
		this.setActionSubName("PreAccu");
		this.setFromPage(DepositFromPageType.PRE_ACCU.getValue());
		this.setPreDeposit(PreDepositType.PRE_DEPOSIT.getValue());
		return showAdd();
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		this.checkEffectiveCertUser();
		
		return MODIFY;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		// 发卡机构和发卡机构网点和售卡代理
		Assert.isTrue(isCardOrCardDeptRoleLogined() || isCardSellRoleLogined(), "非充值机构禁止进入充值！");
		
		rebateTypeList = RebateType.getForSellBatch();
		
		// 是否按次充值
		if(this.getCardType().equals(CardType.ACCU.getValue())){
			this.setDepositTypeIsTimes(true);
		} else {
			this.setDepositTypeIsTimes(false);
		}
		
		depositReg = new DepositReg();
		depositReg.setRebateType(RebateType.SHARE.getValue());
		
		signatureReg = StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES);
		if (signatureReg) {
    		// 随机数
    		this.depositReg.setRandomSessionId(Long.toString(System.currentTimeMillis()) + WebUtils.getSessionId(request));
		} else {
			this.depositReg.setRandomSessionId("");
		}
		
		// 卡类型对应的客户返利规则
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardType", this.getCardType());
		this.customerRebateRegList = this.customerRebateRegDAO.findCustomerRebateRegByCardType(params);
		return ADD;
	}

	// 新增
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		// 发卡机构和发卡机构网点和售卡代理
		Assert.isTrue(isCardOrCardDeptRoleLogined() || isCardSellRoleLogined(), "非充值机构禁止进入充值！");
		
		long id ; 

		// 设置页面来源
		this.depositReg.setFromPage(this.getFromPage());
		
		// 充值类型：0-按次充值 1-按金额充值
		String type = request.getParameter("timesDepositType");
		if ("0".equals(type)){
			this.depositReg.setDepositType(DepositType.TIMES.getValue());
		} else {
			this.depositReg.setDepositType(DepositType.AMT.getValue());
		}
		
		// 预充值
		if (PreDepositType.PRE_DEPOSIT.getValue().equals(preDeposit)){
			this.depositReg.setPreDeposit(preDeposit);
		} else {
			this.depositReg.setPreDeposit(PreDepositType.REAL_DEPOSIT.getValue());
		}
		
		// 设置充值机构
		CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(this.depositReg.getStrCardId());
		if(BranchBizConfigCache.isSetDepositBranchByAppOrgId(cardInfo.getCardIssuer())){
			this.depositReg.setDepositBranch(cardInfo.getAppOrgId());
		}else{
			if(isCardDeptRoleLogined()){//部门登录
				this.depositReg.setDepositBranch(this.getSessionUser().getDeptId());
			}else{
				this.depositReg.setDepositBranch(this.getLoginBranchCode());
			}
		}
		
		
		// 设置是否使用了手工指定返利，如果是需要手工发起一个流程
		if ("1".equals(calcMode) &&( isCardSellRoleLogined() || isCardRoleLogined())) {
			this.depositReg.setManual(true);
		} else {
			this.depositReg.setManual(false);
		}
		
		// 如果是购卡客户指定返利卡，则添加卡号到返利卡字段中
		CardCustomer cardCustomer = (CardCustomer) this.cardCustomerDAO.findByPk(depositReg.getCardCustomerId());
		if (CustomerRebateType.SPECIFY_CARD.getValue().equals(cardCustomer.getRebateType())) {
			this.depositReg.setRebateType(RebateType.CARD.getValue());
			this.depositReg.setRebateCard(cardCustomer.getRebateCard());
		}
		this.depositReg.setCardBranch(cardCustomer.getCardBranch());
		
//		this.depositBatRegList = new ArrayList();
//		int count = this.resultCardId.length;
		
		// 得到权限点
//		String limitId = null;
//		if  (CardType.DEPOSIT.getValue().equals(this.cardType)) {
//			limitId = Constants.PRIVILEGE_DEPOSIT_BAT_CARD;
//		}else if(CardType.DISCNT.getValue().equals(this.cardType)){
//			limitId = Constants.PRIVILEGE_DISCNT_BAT_CARD;
//		}else if(CardType.ACCU.getValue().equals(this.cardType)){
//			limitId = Constants.PRIVILEGE_ACCU_BAT_CARD;
//		}
		
//		BigDecimal avg = AmountUtil.divide(this.depositReg.getRebateAmt(), new BigDecimal(count));
//		for(int i = 0; i < count; i++){
//			// 判断是否有给该卡充值的权限
////			if (RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())) {
////				Assert.isTrue(hasCardSellPrivilegeByCardId(this.getSessionUser().getRole().getBranchNo(), this.resultCardId[i], limitId), 
////						"该售卡代理没有权限为该卡[" + this.resultCardId[i] + "]充值");
////			}
//			
//			depositBatReg = new DepositBatReg();
//			depositBatReg.setCardId(this.resultCardId[i]);
//			depositBatReg.setAmt(BigDecimal.valueOf(this.resultAmt[i]));
//			//depositBatReg2.setExpenses(BigDecimal.valueOf(this.resultExpenses[i]));
//			depositBatReg.setRealAmt(BigDecimal.valueOf(this.resultRealAmt[i]));
//			
//			// 如果手工设定返利则重设
//			if (RebateType.CARD.getValue().equals(depositReg.getRebateType())
//					|| RebateType.CASH.getValue().equals(depositReg.getRebateType())
//					|| RebateType.CARDS.getValue().equals(depositReg.getRebateType())) {
//				depositBatReg.setRebateAmt(new BigDecimal(0));
//			} 
//			// 手工指定返利的话，则每张卡的返利金额为返利总金额/卡张数
//			else if (calcMode.equals("1")) { 
//				depositBatReg.setRebateAmt(avg);
//			} else {
//				depositBatReg.setRebateAmt(BigDecimal.valueOf(this.resultRebateAmt[i]));
//			}
//			
//			depositBatReg.setCardClass(this.getCardType());
//			
////			this.depositBatRegList.add(depositBatReg2);
//		}
		
		Set<String> cardNoSet = new HashSet<String>();
		List<RebateCardReg> rebateCardRegList = new ArrayList<RebateCardReg>();
		//如果是多张卡返利的话，还要在返利表中写数据
		if (RebateType.CARDS.getValue().equals(depositReg.getRebateType())) {
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
		
		id = this.depositService.addDepositBatReg(this.depositReg, this.depositBatReg, rebateCardRegList, this.getSessionUser(), serialNo);
		
		if(id == -1){
//			msg = "添加充值登记失败！卡号[" + this.depositBatReg.getCardId() + "]已经存在。";
//			url = "/depositBatReg/list" + this.getActionSubName() + ".do";
//			this.addActionMessage(url, msg);
//			this.log(msg, UserLogType.ADD);			
			return ERROR;
			
		}else{
			String msg = "添加批量充值登记号[" + id + "]，成功！";
			String url = "/depositBatReg/list" + this.getActionSubName() + ".do";
			this.addActionMessage(url, msg);
			this.log(msg, UserLogType.ADD);
			return SUCCESS;
		}
	}

	// 修改
	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.depositService.modifyDepositBatReg(this.depositBatReg, this.getSessionUserCode());
		
		this.addActionMessage("/depositBatReg/list.do", "修改充值登记成功！");
		return SUCCESS;
	}
	
	// 删除
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
//		this.depositBatRegService.deleteDepositBatReg(this.getDepositBatRegId());
		
		this.addActionMessage("/depositBatReg/list.do", "删除充值登记成功！");
		return SUCCESS;
	}
	
	// 审核流程-待审核记录列表
	public String checkList() throws Exception {
		String depositBranch = "";
		if (isCardRoleLogined()) {
			depositBranch = this.getSessionUser().getBranchNo();
			
		} else if (isCardDeptRoleLogined()) {
			depositBranch = this.getSessionUser().getDeptId();
			
		} else {
			throw new BizException("没有权限做充值审核");
		}
		
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] depositBatchIds = this.workflowService.getMyJob(WorkflowConstants.WORKFLOW_DEPOSIT_BATCH, this.getSessionUser());
		
		if (ArrayUtils.isEmpty(depositBatchIds)) {
			return CHECK_LIST;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", depositBatchIds);
//		params.put("cardBranch", this.getLoginBranchCode()); //发卡机构做审核
		params.put("depositBranch", depositBranch); //充值机构做审核
		
		this.page = this.depositBatRegDAO.findDepositReg(params, this.getPageNumber(), this.getPageSize());
		
		return CHECK_LIST;
	}

	// 审核流程-待审核记录明细
	public String checkDetail() throws Exception{
		this.queryDetail();
		
		return DETAIL;
	}
	
	// 显示激活页面
	public String showActivate() throws Exception {
		this.queryDetail();
		return "activate";
	}
	
	// 激活
	public String activate() throws Exception {
		this.checkEffectiveCertUser();
		
		Assert.notNull(this.depositReg, "预售卡对象不能为空");	
		Assert.notNull(this.depositReg.getDepositBatchId(), "预售卡对象批次ID不能为空");
		
		// 售卡登记簿明细
		this.depositReg = (DepositReg)this.depositRegDAO.findByPk(this.depositReg.getDepositBatchId());

		// 设置激活机构
		this.depositReg.setActivateBranch(this.getSessionUser().getBranchNo());
		
		this.depositService.activate(this.depositReg, this.getSessionUser());
		
		String msg = "预充值批次[" + this.depositReg.getDepositBatchId() + "]激活成功";
		String url = "/depositBatReg/list" + this.getActionSubName() + ".do";
		this.addActionMessage(url, msg);
		this.log(msg, UserLogType.ADD);	
		return SUCCESS;
	}
	
	// 预充值撤销
	public String preDepositCancel() throws Exception {
		this.checkEffectiveCertUser();
		
		Assert.notNull(this.depositBatReg, "预充值对象不能为空");	
		Assert.notNull(this.depositBatReg.getDepositBatchId(), "预充值对象批次ID不能为空");
		this.depositService.addDepositPreCancel(depositBatReg, this.getSessionUser());
		this.addActionMessage("/depositBatReg/listPreDepositCancel.do", "撤销预充值成功！");
		return SUCCESS;
	}
	
	public DepositBatReg getDepositBatReg() {
		return depositBatReg;
	}

	public void setDepositBatReg(DepositBatReg depositBatReg) {
		this.depositBatReg = depositBatReg;
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

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
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

	public CardTypeCode getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(CardTypeCode cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}

	public DepositReg getDepositReg() {
		return depositReg;
	}

	public void setDepositReg(DepositReg depositReg) {
		this.depositReg = depositReg;
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


	public String[] getCardIds() {
		return cardIds;
	}


	public void setCardIds(String[] cardIds) {
		this.cardIds = cardIds;
	}

	public String getBinNo() {
		return binNo;
	}

	public void setBinNo(String binNo) {
		this.binNo = binNo;
	}

	public String getResultErrorMsg() {
		return resultErrorMsg;
	}

	public void setResultErrorMsg(String resultErrorMsg) {
		this.resultErrorMsg = resultErrorMsg;
	}

	public List<CardTypeCode> getCardClassList() {
		return cardClassList;
	}

	public void setCardClassList(List<CardTypeCode> cardClassList) {
		this.cardClassList = cardClassList;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public List<RebateRuleDetail> getRebateRuleDetailList() {
		return rebateRuleDetailList;
	}

	public void setRebateRuleDetailList(List<RebateRuleDetail> rebateRuleDetailList) {
		this.rebateRuleDetailList = rebateRuleDetailList;
	}

	public List<CustomerRebateReg> getCustomerRebateRegList() {
		return customerRebateRegList;
	}

	public void setCustomerRebateRegList(
			List<CustomerRebateReg> customerRebateRegList) {
		this.customerRebateRegList = customerRebateRegList;
	}

	public CustomerRebateReg getCustomerRebateReg() {
		return customerRebateReg;
	}

	public void setCustomerRebateReg(CustomerRebateReg customerRebateReg) {
		this.customerRebateReg = customerRebateReg;
	}

	public List getRebateTypeList() {
		return rebateTypeList;
	}

	public void setRebateTypeList(List rebateTypeList) {
		this.rebateTypeList = rebateTypeList;
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

	public Paginater getDepositBatPage() {
		return depositBatPage;
	}

	public void setDepositBatPage(Paginater depositBatPage) {
		this.depositBatPage = depositBatPage;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public String getPreDeposit() {
		return preDeposit;
	}

	public void setPreDeposit(String preDeposit) {
		this.preDeposit = preDeposit;
	}

	public boolean isSignatureReg() {
		return signatureReg;
	}

	public void setSignatureReg(boolean signatureReg) {
		this.signatureReg = signatureReg;
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
