package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.Paginater;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.BranchProxyDAO;
import gnete.card.dao.CardBinDAO;
import gnete.card.dao.CardCustomerDAO;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.dao.RebateRuleDetailDAO;
import gnete.card.dao.SaleCardRegDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BranchProxyKey;
import gnete.card.entity.CardBin;
import gnete.card.entity.CardCustomer;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardStockInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.CustomerRebateReg;
import gnete.card.entity.DiscntClassDef;
import gnete.card.entity.MembClassDef;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.Rebate;
import gnete.card.entity.RebateRule;
import gnete.card.entity.RebateRuleDetail;
import gnete.card.entity.SaleCardReg;
import gnete.card.entity.flag.SaleCancelFlag;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.CardStockState;
import gnete.card.entity.state.RebateRuleState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.CustomerRebateType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.PresellType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RebateType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.RebateRuleService;
import gnete.card.service.SaleCardRegService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.util.CardUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.Symbol;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;


/**
 * 卡子类型临时类
 * @author benyan
 */
class CardSubClass{
	private String cardSubClassId;
	private String cardSubClassName;
	
	public String getCardSubClassName() {
		return cardSubClassName;
	}
	public void setCardSubClassName(String cardSubClassName) {
		this.cardSubClassName = cardSubClassName;
	}
	public String getCardSubClassId() {
		return cardSubClassId;
	}
	public void setCardSubClassId(String cardSubClassId) {
		this.cardSubClassId = cardSubClassId;
	}
}

/**
 * 售卡登记簿Action
 * SaleCardRegAction.java.
 * 
 * @author BenYan
 * @since JDK1.5
 * @history 2010-7-27
 */
public class SaleCardRegAction extends BaseAction {
	
	protected final String ACTIVATE = "activate";
	
	private String url;	
	private String msg;
	private BizException bizException;
	
	@Autowired
	private SaleCardRegDAO saleCardRegDAO;	
	@Autowired
	private SaleCardRegService saleCardRegService;	
	@Autowired
	private RebateRuleService rebateRuleService;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;
	@Autowired
	private CardCustomerDAO cardCustomerDAO;
	@Autowired
	private CardExtraInfoDAO cardExtraInfoDAO;
	@Autowired
	private CardBinDAO cardBinDAO;
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private RebateRuleDAO rebateRuleDAO;
	@Autowired
	private RebateRuleDetailDAO rebateRuleDetailDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private BranchProxyDAO branchProxyDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	
	/** 售卡登记簿对象 */
	private SaleCardReg saleCardReg;
	private Long saleCardRegId;
	
	/** 是否有持卡人资料标志：0-无资料 1-有资料 */
	private String isHasCustName;
	private CardExtraInfo cardExtraInfo;	
	
	/** 购卡客户 */
	private CardCustomer cardCustomer;    
	
	/** 客户返利 */
	private RebateRule saleRebateRule;
	private List<CustomerRebateReg> customerRebateRegList;
	private List<RebateRuleDetail> saleRebateRuleDetailList;
	
	/** 购卡客户列表 */
	private List<CardCustomer> customerList;
	
    /** 卡类型 */
    private String cardType;    
    private CardTypeCode cardTypeCode;
    private CardBin cardBin;
    
    /** 卡子类型 */
    private CardSubClass cardSubClass;
    private List<CardSubClass> cardSubClassList;    
    /** 次卡子类型定义 */
    private List<AccuClassDef> accuClassList;
    /** 积分子类型定义 */
    private List<PointClassDef> pointClassList;
    /** 会员子类型定义 */
    private List<MembClassDef> membClassList;
    /** 折扣子类型定义 */
    private List<DiscntClassDef> discntClassList;
    /** 证件类型 */
    private List<CertType> certTypeList;
    
    /** 页面Action子名 */
    private String actionSubName;
    private Paginater page;
    
    /** 售卡方式：0-实时售卡 1-预售卡 */
    private boolean isPresell;
    /** “未激活”标志 */
    private String unActivatedLabel;
    /** 是否按次充值 */
    private boolean depositTypeIsTimes;
    
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
    
    private Collection statusList;
    private String cardId;
	private List<RebateRule> rebateRuleList;
	private List rebateTypeList;
	private String rebateType;
	
	/** 是否需要在登记簿中记录签名信息 */
	private boolean signatureReg;

	/** 售卡需要实名登记的面值 */
	private static final BigDecimal MAX_SALE_FACEVALUE = new BigDecimal("1000.00");
	
    // 计算返利金额、实收金额等
	public void calRealAmt(){
		JSONObject object = new JSONObject();
		try {
			String cardId = request.getParameter("cardId");
			String type = request.getParameter("type");	// type为0时是充值次数
			
//			String amt = this.saleCardReg.getAmt().toString();
			Assert.notNull(saleCardReg.getAmt(), "售卡面值或次数不能为空");
			Assert.notNull(saleCardReg.getFeeRate(), "手续费比例不能为空");
			
			BigDecimal actual = null; // 应收售卡金额
			CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在");
			
			if ("0".equals(type)) {
				// 根据卡子类型得到次卡子类型
				String subClass = cardInfo.getCardSubclass();
				CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(subClass);
				Assert.notNull(cardSubClassDef, "卡号[" + cardId + "]所属卡类型不存在");
				String frequencyClass = cardSubClassDef.getFrequencyClass();
				AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(frequencyClass);
				Assert.notNull(accuClassDef, "卡号[" + cardId + "]所属次卡子类型不存在");
				
				// 实收金额 = 充值次数 * 次卡清算金额
				actual = AmountUtil.multiply(this.saleCardReg.getAmt(), accuClassDef.getSettAmt());
			} else {
//				actual = new BigDecimal(amt);
				actual = this.saleCardReg.getAmt();
			}
			
			// 售卡手续费 = 售卡总金额*手续费比例/100
			BigDecimal feeAmt = AmountUtil.divide(AmountUtil.multiply(actual, saleCardReg.getFeeRate()), new BigDecimal(100));
			
			// 面值是否大于1000
			object.put("isNeedCredNo", AmountUtil.gt(saleCardReg.getAmt(), MAX_SALE_FACEVALUE));
			
			// Calculate
			// 计算卡金额等
			CardCustomer cardCustomer = (CardCustomer) this.cardCustomerDAO.findByPk(this.saleCardReg.getCardCustomerId());
			Assert.notNull(cardCustomer, "购卡客户[" + this.saleCardReg.getCardCustomerId() + "]不存在");
			RebateRule rebateRule = (RebateRule) this.rebateRuleDAO.findByPk(this.saleCardReg.getRebateId());
			Assert.notNull(rebateRule, "返利规则[" + this.saleCardReg.getRebateId() + "]不存在");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("amt", actual.toString());  
			params.put("rebateRule", rebateRule);
			
			if (CustomerRebateType.SPECIFY_CARD.getValue().equals(cardCustomer.getRebateType())) {
				params.put("rebateType", RebateType.CARD.getValue());
			} else {
				params.put("rebateType", this.rebateType);
			}
			Rebate rebate = this.rebateRuleService.calculateRebate(params);  // 计算返利金额等
			
			// Out param
//		String realAmt = rebate.getRealAmt().toString();
//		String rebateAmt = rebate.getRebateAmt().toString();
			
			object.put("feeAmt", feeAmt); //手续费
			//实收金额 = 返利后的售卡金额 + 工本费 + 手续费
			object.put("realAmt", AmountUtil.add(AmountUtil.add(rebate.getRealAmt(), saleCardReg.getExpenses()), feeAmt)); 
			object.put("rebateAmt", rebate.getRebateAmt());
			object.put("success", true);
		} catch (Exception e) {
			object.put("success", false);
			object.put("errorMsg", e.getMessage());
		}
//		this.respond("{'realAmt':"+ realAmt + ", 'rebateAmt':" + rebateAmt + "}");
		this.respond(object.toString());
	}

	/** 默认方法显示列表页面 */
	@Override
	public String execute() throws Exception {
		
		this.statusList = RegisterState.getAll();
		this.rebateTypeList = RebateType.getForSellSingle();
		
		this.setUnActivatedLabel(RegisterState.INACTIVE.getValue());
		
		Map<String, Object> params = new HashMap<String, Object>();
		// 预售卡标识
		if(this.getIsPresell()){
			params.put("presell", PresellType.PRESELL.getValue());  
		}else{
			params.put("presell", PresellType.REALSELL.getValue());  
		}
		if (saleCardReg != null) {
			params.put("saleBatchId", saleCardReg.getSaleBatchId());
			params.put("status", saleCardReg.getStatus());
			params.put("cardId", saleCardReg.getCardId());
			params.put("cardBranch", saleCardReg.getCardBranch()); //发卡机构
			params.put("cardCustomer", MatchMode.ANYWHERE.toMatchString(saleCardReg.getCardCustomerName()));// 购卡客户
			params.put("rebateType", saleCardReg.getRebateType()); // 返利方式
//			params.put("saleDate", saleCardReg.getSaleDate()); //售卡日期
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		
		// 如果登录用户为运营中心，运营中心部门时，查看所有
		if (isCenterOrCenterDeptRoleLogined()) {
			showCardBranch = true;

		} else if (isFenzhiRoleLogined()) {
			showCardBranch = true;
			params.put("fenzhiList", this.getMyManageFenzhi());

		} else if (isCardSellRoleLogined()) {
			this.cardBranchList = this.getMyCardBranch();
			params.put("branchCode", super.getSessionUser().getBranchNo());

		} else if (isCardDeptRoleLogined()) {
			this.cardBranchList = this.getMyCardBranch();
			params.put("branchCode", super.getSessionUser().getDeptId());

		} else if (isCardRoleLogined()) {
			// params.put("cardBranch", super.getSessionUser().getBranchNo());
			this.cardBranchList = this.getMyCardBranch();
			params.put("cardBranchList", cardBranchList);
		} else {
			throw new BizException("没有权限查看售卡记录");
		}
		// 设置卡类型
		params.put("cardClass", this.getCardType());
		params.put("saleCancel", SaleCancelFlag.NORMAL.getValue());// 普通售卡
		this.page = this.saleCardRegDAO.findSaleCardReg(params, this.getPageNumber(), this.getPageSize());
		logger.debug("用户[" + this.getSessionUserCode() + "]查询售卡列表！");
		return LIST;
	}
	
	public void calcCardOther() throws Exception{
		JSONObject object = new JSONObject();
		try {
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.cardId);
			Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在");
			Assert.equals(cardInfo.getCardClass(), cardType, "卡号[" + cardId + "]不是[" + CardType.valueOf(cardType).getName() + "]");
			
			this.hasRightSaleByRoleType(cardInfo);
			
			Assert.equals(CardState.FORSALE.getValue(), cardInfo.getCardStatus(), "卡号[" + cardId + "]不是待售状态");

			CardStockInfo cardStockInfo = this.cardStockInfoDAO.findCardStockInfoByCardId(cardId);
			Assert.notNull(cardStockInfo, "库存中没有卡号[" + cardId + "]的记录");
			Assert.equals(CardStockState.RECEIVED.getValue(), cardStockInfo.getCardStatus(), 
					"库存中 卡[" + cardId	+ "]的状态是 " + cardStockInfo.getCardStatusName());
			
			
			// 查找通用购卡客户
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("cardBranch", cardInfo.getCardIssuer());
			params.put("isCommon", Symbol.YES);
			List<CardCustomer> customerList = this.cardCustomerDAO.findCardCustomer(params);
			Assert.notEmpty(customerList, "卡号[" + cardId + "]所属的发卡机构没有通用返利规则");
			CardCustomer customer = customerList.get(0);
			object.put("cardCustomerId", customer.getCardCustomerId());
			object.put("cardCustomerName", customer.getCardCustomerName());
			object.put("rebateType", RebateType.SHARE.getValue());
			
			CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
			Assert.notNull(cardSubClassDef, "卡号[" + cardId + "]所属卡类型不存在");
			this.cardBin = (CardBin) this.cardBinDAO.findByPk(cardInfo.getCardBin());
			Assert.notNull(cardBin, "卡号[" + cardId + "]所属卡BIN不存在");
			BranchInfo branchInfo = (BranchInfo) branchInfoDAO.findByPk(StringUtils.trim(cardInfo.getCardIssuer()));
			Assert.notNull(branchInfo, "卡号[" + cardId + "]所属的发卡机构[" + StringUtils.trim(cardInfo.getCardIssuer()) + "]不存在");
			
//			StringBuffer sb = new StringBuffer(128);
			
			String isBatFlag = request.getParameter("isBatFlag");
			
			// 批量售卡的话要计算可售卡的数量
			if (Symbol.YES.equals(isBatFlag)) {
				String cardCount = request.getParameter("cardCount");
				Assert.notEmpty(cardCount, "卡连续张数不能为空");
				int cardNum = NumberUtils.toInt(cardCount);
				Assert.notTrue(cardNum <= 0, "卡连续张数必须大于0");
				Assert.notTrue(cardNum > 10000, "卡连续张数最多为10000");
				
				StopWatch stopWatch = new StopWatch();
				stopWatch.start();
				if (isCardSellRoleLogined()) {
					Map<String, Object> map = new HashMap<String, Object> ();
					map.put("cardSubclass", cardInfo.getCardSubclass());
					map.put("cardStatus", CardStockState.RECEIVED.getValue());
					map.put("appOrgId", this.getLoginBranchCode());
					
					Long canSellNum = this.cardStockInfoDAO.getCouldReceive(map);
					
					Assert.isTrue(cardNum <= canSellNum, "当前登录的售卡代理机构最多可售卡号[" + cardId + "]所属的卡类型的卡[" + canSellNum + "]张");
				}
				
				// 新卡写在卡子类型表的面额字段，要计算出结束卡号 2012年9月25日注释
				/*if (this.cardId.length() == 19) {
					String[] cardArray = CardUtil.getCard(cardId, cardNum);
					for (int i = 0; i < cardArray.length; i++) {
						CardInfo info = (CardInfo) this.cardInfoDAO.findByPk(cardArray[i]);
						
						Assert.notNull(info, "第[" + (i + 1) + "]张卡[" + cardArray[i] + "]不存在");
						Assert.equals(CardState.FORSALE.getValue(), info.getCardStatus(), "第[" + (i + 1) + "]张卡[" + cardArray[i] + "]不是待售状态");
						Assert.isTrue(hasRightSaleByRoleType(info), "用户[" + this.getSessionUserCode() + "]没有权限售第[" + (i + 1) + "]张卡[" + cardArray[i] + "]!");
					}

					String endCardId = CardUtil.getEndCardId(cardId, cardNum);
					object.put("endCardId", endCardId);
				} else if (this.cardId.length() == 18) {
					String endCardId = CardUtil.getOldEndCardId(cardId, cardNum);
					CardInfo info = (CardInfo) this.cardInfoDAO.findByPk(endCardId);
					
					// 按另一种规则试试
					if (info == null) {
						String cardPrex = StringUtils.substring(endCardId, 0, endCardId.length() - 1);
						endCardId = cardPrex + CardUtil.getOldCardLast(cardPrex);
						info = (CardInfo) this.cardInfoDAO.findByPk(endCardId);
						
						Assert.notNull(info, "旧卡结束卡号[" + cardPrex + "*]不存在");
					}
					object.put("endCardId", endCardId);
				}*/
				
				// 2012年9月25日现在修改为不计算每张卡了
				String endCardId = CardUtil.getEndCard(cardId, cardNum);
				object.put("endCardId", endCardId);
				
				CardInfo endCardInfo = (CardInfo) this.cardInfoDAO.findByPk(endCardId);
				Assert.notNull(endCardInfo, "最后一张卡号为[" + endCardId + "]的卡不存在");
				
				List<CardInfo> cardInfoList = this.cardInfoDAO.getCardList(cardId, endCardId);
				CardInfo info = null;
				String cardIdInLoop = "";
				for (int i = 0; i < cardInfoList.size(); i++) {
					info = cardInfoList.get(i);
					cardIdInLoop = info.getCardId();
					Assert.equals(CardState.FORSALE.getValue(), info.getCardStatus(), "第[" + (i + 1) + "]张卡[" + cardIdInLoop + "]不是待售状态");
					Assert.isTrue(hasRightSaleByRoleType(info), "用户[" + this.getSessionUserCode() + "]没有权限售第[" + (i + 1) + "]张卡[" + cardIdInLoop + "]!");
				}
				stopWatch.stop();
				logger.debug("取起始卡号和结束卡号之间的卡消耗时间[" + stopWatch + "]");
			}
			
			if (this.cardId.length() == 19) {
				// 新卡的面值写在卡子类型表的面额字段
				object.put("amt", cardSubClassDef.getFaceValue());
				// 面值是否大于1000
				object.put("isNeedCredNo", AmountUtil.gt(cardSubClassDef.getFaceValue(), MAX_SALE_FACEVALUE));
			} else {
				// 旧卡的话，外部卡号字段放面额
				String amt = StringUtils.isEmpty(cardInfo.getExternalCardId()) ? "0.0" : cardInfo.getExternalCardId();
				object.put("amt", amt);
				object.put("isNeedCredNo", AmountUtil.gt(NumberUtils.createBigDecimal(amt), MAX_SALE_FACEVALUE));
			}
			
			object.put("expenses", cardSubClassDef.getCardPrice());
			object.put("isModifyFaceValue", StringUtils.equals(Symbol.YES, cardSubClassDef.getChangeFaceValue()));
			object.put("cardBin", cardInfo.getCardBin());
			object.put("cardBinName", cardBin.getBinName());
			object.put("cardBranch", branchInfo.getBranchCode());
			object.put("cardBranchName", branchInfo.getBranchName());
			object.put("success", true);

//				sb.append("{success:true, amt:'").append(cardSubClassDef.getFaceValue())
//				.append("', expenses:'").append(cardSubClassDef.getCardPrice())
//				.append("', cardBin:'").append(cardInfo.getCardBin())
//				.append("', cardBinName:'").append(cardBin.getBinName())
//				.append("', cardBranchName:'").append(branchInfo.getBranchName())
//				.append("'}");
		} catch (BizException e){
			object.put("success", false);
			object.put("errorMsg", e.getMessage());
//			this.respond("{success:false, error:'"+ e.getMessage() +"'}");
		}
		this.respond(object.toString());
	}
	
	/**
	 * 根据角色判断是否有权限售卡
	 * @param cardInfo
	 * @return
	 */
	private boolean hasRightSaleByRoleType(CardInfo cardInfo) throws BizException{
		boolean flag = false;
		// 发卡机构可售自己发的卡和自己领的卡
		if (isCardRoleLogined()) {
			flag = (StringUtils.equals(cardInfo.getCardIssuer(), this.getLoginBranchCode())
					|| StringUtils.equals(cardInfo.getAppOrgId(), this.getLoginBranchCode()));
			Assert.isTrue(flag, "当前机构没有权限售卡[" + cardInfo.getCardId() + "]，发卡机构只能售自己发的卡和自己领的卡");
		}
		// 发卡机构部门可售自己领的卡
		else if (isCardDeptRoleLogined()) {
			flag = StringUtils.equals(cardInfo.getAppOrgId(), this.getSessionUser().getDeptId());
			Assert.isTrue(flag, "当前机构没有权限售卡[" + cardInfo.getCardId() + "]，发卡机构部门只能售自己领的卡");
		}
		// 售卡代理可售自己领的卡
		else if (isCardSellRoleLogined()) {
			flag = StringUtils.equals(cardInfo.getAppOrgId(), this.getSessionUser().getBranchNo());
			Assert.isTrue(flag, "当前机构没有权限售卡[" + cardInfo.getCardId() + "]，售卡代理只能售自己领的卡");
		} 
		return flag;
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
	
	// 积分卡列表
	public String listPoint() throws Exception{
		this.setCardType(CardType.POINT.getValue());
		this.setActionSubName("Point");
		this.setIsPresell(false);
		return execute();
	}
	
	// 预售积分卡列表
	public String listPrePoint() throws Exception{
		this.setCardType(CardType.POINT.getValue());
		this.setActionSubName("PrePoint");
		this.setIsPresell(true);
		return execute();
	}
	
	// 查询售卡明细
	public void querySaleCardDetail() throws Exception{
		
		Assert.notNull(this.saleCardReg, "售卡对象不能为空");	
		Assert.notNull(this.saleCardReg.getSaleBatchId(), "售卡对象批次ID不能为空");			
		
		// 售卡登记簿明细
		this.saleCardReg = (SaleCardReg)this.saleCardRegDAO.findByPk(this.saleCardReg.getSaleBatchId());
		
		// 卡类型
		this.cardTypeCode = (CardTypeCode)this.cardTypeCodeDAO.findByPk(this.saleCardReg.getCardClass());
		
		// 卡子类型
		this.cardSubClass = new CardSubClass();
		if   (this.saleCardReg.getCardClass().equals(CardType.DEPOSIT.getValue())) {
			this.setActionSubName("Deposit");
			this.cardSubClass.setCardSubClassId("");
			this.cardSubClass.setCardSubClassName("");
		}else if(this.saleCardReg.getCardClass().equals(CardType.MEMB.getValue())) {
			this.setActionSubName("Memb");			
		}else if(this.saleCardReg.getCardClass().equals(CardType.DISCNT.getValue())){
			this.setActionSubName("Discnt");
		}else if(this.saleCardReg.getCardClass().equals(CardType.ACCU.getValue())){
			this.setActionSubName("Accu");
		}else if(this.saleCardReg.getCardClass().equals(CardType.POINT.getValue())){
			this.setActionSubName("Point");
		}
		CardSubClassDef cardSubClassDef = (CardSubClassDef)this.cardSubClassDefDAO.findByPk(this.saleCardReg.getCardSubClass());
		if(cardSubClassDef!=null){
			this.cardSubClass.setCardSubClassId(cardSubClassDef.getCardSubclass());
			this.cardSubClass.setCardSubClassName(cardSubClassDef.getCardSubclassName());	
		}
		
		// 充值类型：0-按金额充值 1-按次充值
		if(this.saleCardReg.getDepositType().equals(DepositType.TIMES.getValue())){
			this.setDepositTypeIsTimes(true);
		}else{
			this.setDepositTypeIsTimes(false);
		}
		
		if(this.saleCardReg.getPresell().equals(PresellType.PRESELL.getValue())){
			// 预售卡Action名称
			this.setActionSubName("Pre" + this.getActionSubName());
		}
		// 购卡客户明细
		this.cardCustomer = (CardCustomer)this.cardCustomerDAO.findByPk(this.saleCardReg.getCardCustomerId());
		// 卡BIN明细
		this.cardBin = (CardBin)this.cardBinDAO.findByPk(CardBin.getBinNo(this.saleCardReg.getCardId()));		
		// 持卡人明细
		this.cardExtraInfo = (CardExtraInfo)this.cardExtraInfoDAO.findByPk(this.saleCardReg.getCardId());
		// 售卡返利明细
		this.saleRebateRule = (RebateRule)this.rebateRuleDAO.findByPk(this.saleCardReg.getRebateId());
		// 售卡返利分段比例明细列表
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("rebateId", this.saleCardReg.getRebateId());			
		this.saleRebateRuleDetailList = this.rebateRuleDetailDAO.findRebateRuleDetail(params);
	}
	
	// 明细页面
	public String detail() throws Exception{
		querySaleCardDetail();
		return DETAIL;
	}
	
	// 储值卡售卡
	public String showAddDeposit() throws Exception{		
		this.setCardType(CardType.DEPOSIT.getValue());		
		this.setActionSubName("Deposit");
		this.saleCardReg = new SaleCardReg();
		this.saleCardReg.setPresell(PresellType.REALSELL.getValue());		
		return showAdd();
	}

	// 储值卡预售卡
	public String showAddPreDeposit() throws Exception{		
		this.setCardType(CardType.DEPOSIT.getValue());		
		this.setActionSubName("PreDeposit");
		this.saleCardReg = new SaleCardReg();
		this.saleCardReg.setPresell(PresellType.PRESELL.getValue());		
		return showAdd();
	}

	// 会员卡售卡
	public String showAddMemb() throws Exception{
		this.setCardType(CardType.MEMB.getValue());
		this.setActionSubName("Memb");
		this.saleCardReg = new SaleCardReg();
		this.saleCardReg.setPresell(PresellType.REALSELL.getValue());		
		return showAdd();
	}
	
	// 会员卡预售卡
	public String showAddPreMemb() throws Exception{
		this.setCardType(CardType.MEMB.getValue());
		this.setActionSubName("PreMemb");
		this.saleCardReg = new SaleCardReg();
		this.saleCardReg.setPresell(PresellType.PRESELL.getValue());		
		return showAdd();
	}
	
	// 折扣卡售卡
	public String showAddDiscnt() throws Exception{
		this.setCardType(CardType.DISCNT.getValue());
		this.setActionSubName("Discnt");
		this.saleCardReg = new SaleCardReg();
		this.saleCardReg.setPresell(PresellType.REALSELL.getValue());		
		return showAdd();
	}

	// 折扣卡预售卡
	public String showAddPreDiscnt() throws Exception{
		this.setCardType(CardType.DISCNT.getValue());
		this.setActionSubName("PreDiscnt");
		this.saleCardReg = new SaleCardReg();
		this.saleCardReg.setPresell(PresellType.PRESELL.getValue());		
		return showAdd();
	}

	// 次卡售卡
	public String showAddAccu() throws Exception{
		this.setCardType(CardType.ACCU.getValue());
		this.setActionSubName("Accu");
		this.saleCardReg = new SaleCardReg();
		this.saleCardReg.setPresell(PresellType.REALSELL.getValue());		
		return showAdd();
	}
	
	// 次卡预售卡
	public String showAddPreAccu() throws Exception{
		this.setCardType(CardType.ACCU.getValue());
		this.setActionSubName("PreAccu");
		this.saleCardReg = new SaleCardReg();
		this.saleCardReg.setPresell(PresellType.PRESELL.getValue());		
		return showAdd();
	}
	
	// 积分卡售卡
	public String showAddPoint() throws Exception{
		this.setCardType(CardType.POINT.getValue());
		this.setActionSubName("Point");
		this.saleCardReg = new SaleCardReg();
		this.saleCardReg.setPresell(PresellType.REALSELL.getValue());		
		return showAdd();
	}
	
	// 积分卡预售卡
	public String showAddPrePoint() throws Exception{
		this.setCardType(CardType.POINT.getValue());
		this.setActionSubName("PrePoint");
		this.saleCardReg = new SaleCardReg();
		this.saleCardReg.setPresell(PresellType.PRESELL.getValue());		
		return showAdd();
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		return MODIFY;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		rebateTypeList = RebateType.getForSellSingle();
		certTypeList = CertType.getAll();
		// 是否按次充值
		if(this.getCardType().equals(CardType.ACCU.getValue())){
			this.setDepositTypeIsTimes(true);
		}else{
			this.setDepositTypeIsTimes(false);
		}
		
		signatureReg = StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES);
		if (signatureReg) {
			// 随机数
			saleCardReg.setRandomSessionId(Long.toString(System.currentTimeMillis()) + WebUtils.getSessionId(request));
		}
		
		// 发卡机构和发卡机构网点和售卡代理
		if (!(isCardOrCardDeptRoleLogined() || isCardSellRoleLogined())){
			throw new BizException("非售卡机构禁止进入售卡！");
		}
		return ADD;
	}
	
	public String findCustomerRule() throws Exception {
		String cardBin = request.getParameter("cardBin");
		String cardType = request.getParameter("cardType");
		CardBin bin = (CardBin) this.cardBinDAO.findByPk(cardBin);
		if (bin == null) {
			this.setMsg("卡不存在！");
			return "customerRule";
		}
		
		if (cardType.equals(bin.getCardType())) {
			this.setMsg("卡种类不符！");
			return "customerRule";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (isCardOrCardDeptRoleLogined()){// 发卡机构和发卡机构网点
			// 验证发卡机构与卡bin
			if (!bin.getCardIssuer().equals(this.getSessionUser().getBranchNo())){
				this.setMsg("该卡不是该发卡机构的卡");
				return "customerRule";
			}
			params.put("cardBranch", bin.getCardIssuer());
		} else if (isCardSellRoleLogined()){ // 售卡代理
			// 验证代理关系
			BranchProxyKey branchProxyKey = new BranchProxyKey(this.getSessionUser().getBranchNo(), bin.getCardIssuer(), ProxyType.SELL.getValue());
			if (!this.branchProxyDAO.isExist(branchProxyKey)) {
				this.setMsg("没有权限卖这张卡");
				return "customerRule";
			}
			params.put("cardBranch", bin.getCardIssuer());
		} else {
			this.setMsg("非售卡机构禁止进入售卡！");
			return "customerRule";
		}
		this.customerList = this.cardCustomerDAO.findCardCustomer(params);
		return "customerRule";
	}
	
	public void findCustomerRebateType() throws Exception {
		this.cardCustomer = (CardCustomer) this.cardCustomerDAO.findByPk(this.cardCustomer.getCardCustomerId());
		if (cardCustomer == null) {
			return;
		}
		JSONObject object = new JSONObject();
		if (CustomerRebateType.CUSTOMER_CHOOSE.getValue().equals(cardCustomer.getRebateType())){
			object.put("choose", true);
			object.put("rebateType", RebateType.SHARE.getValue());
		} else {
			object.put("choose", false);
		}
		this.respond(object.toString());
	}

	// 新增
	public String add() throws Exception {
		this.saleCardReg.setCardClass(this.getCardType());
		
		String limitId = null;
		// 预售的权限点
		if (this.saleCardReg.getPresell().equals(PresellType.PRESELL.getValue())) {
			if  (this.cardType.equals(CardType.DEPOSIT.getValue())) {
				limitId = Constants.PRIVILEGE_PRE_SALE_DEPOSIT;
			}else if(this.cardType.equals(CardType.MEMB.getValue())){
				limitId = Constants.PRIVILEGE_PRE_SALE_MEMB;
			}else if(this.cardType.equals(CardType.DISCNT.getValue())){
				limitId = Constants.PRIVILEGE_PRE_SALE_DISCNT;
			}else if(this.cardType.equals(CardType.ACCU.getValue())){
				limitId = Constants.PRIVILEGE_PRE_SALE_ACCU;
			}
		} else {
			if  (this.cardType.equals(CardType.DEPOSIT.getValue())) {
				limitId = Constants.PRIVILEGE_SALE_DEPOSIT;
			}else if(this.cardType.equals(CardType.MEMB.getValue())){
				limitId = Constants.PRIVILEGE_SALE_MEMB;
			}else if(this.cardType.equals(CardType.DISCNT.getValue())){
				limitId = Constants.PRIVILEGE_SALE_DISCNT;
			}else if(this.cardType.equals(CardType.ACCU.getValue())){
				limitId = Constants.PRIVILEGE_SALE_ACCU;
			}
		}
		
		// 判断是否有给该卡充值的权限
		if (isCardSellRoleLogined()) {
			Assert.isTrue(hasCardSellPrivilegeByCardId(this.getLoginBranchCode(), saleCardReg.getCardId(), limitId), 
					"该售卡代理没有权限售该卡[" + saleCardReg.getCardId() + "]");
		}
		
		// 设置售卡机构
		if (isCardDeptRoleLogined()) {
			this.saleCardReg.setBranchCode(this.getSessionUser().getDeptId());
		} else {
			this.saleCardReg.setBranchCode(this.getSessionUser().getBranchNo());
		}
		
		// 充值类型：0-按金额充值 1-按次充值
		if(this.getCardType().equals(CardType.ACCU.getValue())){
			this.saleCardReg.setDepositType(DepositType.TIMES.getValue());
		}else{
			this.saleCardReg.setDepositType(DepositType.AMT.getValue());
		}
		
		// 如果是购卡客户指定返利卡，则添加卡号到返利卡字段中
		CardCustomer cardCustomer = (CardCustomer) this.cardCustomerDAO.findByPk(saleCardReg.getCardCustomerId());
		if (cardCustomer.getRebateType().equals(CustomerRebateType.SPECIFY_CARD.getValue())) {
			this.saleCardReg.setRebateType(RebateType.CARD.getValue());
			this.saleCardReg.setRebateCard(cardCustomer.getRebateCard());
		}
		
		this.saleCardReg.setCardBranch(cardCustomer.getCardBranch());
		
		String serialNo = request.getParameter("serialNo");
		
		if(!this.isFormMapFieldTrue("isDepositCoupon")){
			this.saleCardReg.setCouponClass(null);
			this.saleCardReg.setCouponAmt(null);
		}
		
		this.saleCardRegService.addSaleCardReg(this.saleCardReg, this.cardExtraInfo, this.getSessionUser(), serialNo);

		msg = this.saleCardRegService.getMessage();
		url = "/saleCardReg/list" + this.getActionSubName() + ".do";
		this.addActionMessage(url, msg);
		return SUCCESS;
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
				
		boolean isOk = this.saleCardRegService.activate(this.saleCardReg, this.getSessionUser());
		
		msg = this.saleCardRegService.getMessage();
		url = "/saleCardReg/list" + this.getActionSubName() + ".do";
		this.addActionMessage(url, msg);
		this.log(msg, UserLogType.ADD);	
		if(!isOk){
			throw new BizException(msg);
		}
		return SUCCESS;
	}

	// 修改
	public String modify() throws Exception {
		
		this.saleCardRegService.modifySaleCardReg(this.saleCardReg, this.getSessionUserCode());
		
		this.addActionMessage("/saleCardReg/list.do", "修改售卡登记成功！");
		return SUCCESS;
	}
	
	// 删除
	public String delete() throws Exception {
		
		this.saleCardRegService.deleteSaleCardReg(this.getSaleCardRegId());
		
		this.addActionMessage("/saleCardReg/list.do", "删除售卡登记成功！");
		return SUCCESS;
	}

	public String findRebateRule() throws Exception {
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.cardId);
		
		if (cardInfo == null) {
			this.setMessage("卡号["+ this.cardId +"]不存在");
			return "rebateRule";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		this.rebateRuleList = new ArrayList<RebateRule>();
		
		// 查找通用返利规则
		params.put("isCommon", Symbol.YES);
		params.put("cardBranch", cardInfo.getCardIssuer().trim());
		
		String[] statusArray = new String[]{RebateRuleState.NORMAL.getValue(), RebateRuleState.USED.getValue()};
		params.put("statusArray", statusArray);
		rebateRuleList.addAll(this.rebateRuleDAO.findRebateRule(params));
		
		// 查找该购卡客户的返利规则
		params.put("isCommon", Symbol.NO);
		if(!this.isFormMapFieldTrue("ablePeriod")){
			params.put("rebateType", "0"); // 一次性返利
		}
		params.put("cardBranch", cardInfo.getCardIssuer());
		params.put("cardBin", cardInfo.getCardBin());
		params.put("saleCardCustomerId", cardCustomer.getCardCustomerId());
		params.put("statusArray", statusArray);
		rebateRuleList.addAll(this.rebateRuleDAO.findRebateRule(params));
		
		if (CollectionUtils.isEmpty(rebateRuleList)) {
			this.setMessage("没有合适的返利规则！");
		}
		return "rebateRule";
	}
	
	public SaleCardReg getSaleCardReg() {
		return saleCardReg;
	}

	public void setSaleCardReg(SaleCardReg saleCardReg) {
		this.saleCardReg = saleCardReg;
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

	public Long getSaleCardRegId() {
		return saleCardRegId;
	}

	public void setSaleCardRegId(Long saleCardRegId) {
		this.saleCardRegId = saleCardRegId;
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

	public boolean getDepositTypeIsTimes() {
		return depositTypeIsTimes;
	}

	public void setDepositTypeIsTimes(boolean depositTypeIsTimes) {
		this.depositTypeIsTimes = depositTypeIsTimes;
	}

	public boolean getIsPresell() {
		return isPresell;
	}

	public void setIsPresell(boolean isPresell) {
		this.isPresell = isPresell;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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

	public List<CardCustomer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<CardCustomer> customerList) {
		this.customerList = customerList;
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

	public boolean isSignatureReg() {
		return signatureReg;
	}

	public void setSignatureReg(boolean signatureReg) {
		this.signatureReg = signatureReg;
	}

	public List<CertType> getCertTypeList() {
		return certTypeList;
	}

	public void setCertTypeList(List<CertType> certTypeList) {
		this.certTypeList = certTypeList;
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

}
