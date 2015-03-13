package gnete.card.web.transactionData;

import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.SubAcctBalKey;
import gnete.card.entity.Trans;
import gnete.card.entity.state.CardState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SubacctType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: ShineWayQueryAction.java
 * 
 * @description: 双汇
 * 
 * @copyright: (c) 2012 YLINK INC.
 * @version: 1.0
 * @since 1.0 2012-10-23
 */
public class ShineWayQueryAction extends BaseAction {

	@Autowired
	private CardInfoDAO cardInfoDAO;//卡信息表
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;

	private String cardSaleStartDate;//售卡日期
	private String cardSaleEndDate;
	
	private String consumeStartDate;//消费日期
	private String consumeEndDate;
	
	private String avaliableValueStart;//充值资金余额
	private String avaliableValueEnd;
	
	private String rebateAmtStart; // 返利资金余额
	private String rebateAmtEnd;

	private Paginater page;
	private CardInfo cardInfo;
	private CardExtraInfo cardExtraInfo;
	private CardSubClassDef cardSubClassDef;
	private Trans trans;
	List<CardType> cardTypeList;
	List<CardState> cardStatusList;
	private List<BranchInfo> branchList;
	private String startCardId;
	private String endCardId;
	
	List<String> cardIdList = new ArrayList<String>();

	@Override
	public String execute() throws Exception {
		this.cardTypeList = CardType.getAll();
		this.cardStatusList = CardState.getAll();
		
		/*Map<String, Object> params = new HashMap<String, Object>();
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(getSessionUser().getBranchNo());
			branchList = new ArrayList<BranchInfo>();
			branchList.add(info);
			params.put("branchList", branchList);
		}
		// 售卡代理
		else if(RoleType.CARD_SELL.getValue().equals(getLoginRoleType())){
			branchList = getMyCardBranch();
			if (CollectionUtils.isEmpty(branchList)) {
				branchList.add(new BranchInfo());
			}
			params.put("branchList", branchList);
		} 
		// 商户
		else if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){
			params.put("appOrgId", this.getSessionUser().getMerchantNo());
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		//先根据条件查询卡号
		Map<String, Object> cardIdParams = new HashMap<String, Object>();
		//card_extra_info
		if(null != cardExtraInfo &&
				(CommonHelper.isNotEmpty(cardExtraInfo.getCustName()) || 
				CommonHelper.isNotEmpty(cardExtraInfo.getMobileNo()) || 
				CommonHelper.isNotEmpty(cardExtraInfo.getCredNo()))){//有附加信息,则只根据附加信息查询卡号(简化查询)
			Map<String, Object> cardExtraInfoParams = new HashMap<String, Object>();//独立参数处理
			cardExtraInfoParams.put("custName", cardExtraInfo.getCustName());
			cardExtraInfoParams.put("mobileNo", cardExtraInfo.getMobileNo());
			cardExtraInfoParams.put("credNo", cardExtraInfo.getCredNo());
			List<CardExtraInfo>  cardExtraInfoList  = cardExtraInfoDAO.findCardExtraInfoByParam(cardExtraInfoParams);
			if(CommonHelper.checkIsNotEmpty(cardExtraInfoList)){
				for(CardExtraInfo cardExtraInfo : cardExtraInfoList){
					cardIdList.add(cardExtraInfo.getCardId());
				}
				if(CommonHelper.checkIsNotEmpty(cardIdList)){
					params.put("cardids",cardIdList);
				}
			}else{
				return LIST;//没有卡号不予查询
			}
		}else {
			//card_info
			if (cardInfo != null) {
				if (CommonHelper.isNotEmpty(cardInfo.getSaleOrgId())) {
					cardIdParams.put("saleOrgId", cardInfo.getSaleOrgId());
				}
				if (CommonHelper.isNotEmpty(cardInfo.getAppOrgId())) {
					cardIdParams.put("appOrgId", cardInfo.getAppOrgId());
				}
				if (CommonHelper.isNotEmpty(cardInfo.getCardStatus())) {
					cardIdParams.put("cardStatus", cardInfo.getCardStatus());
				}
				if (CommonHelper.isNotEmpty(cardInfo.getCardClass())) {
					cardIdParams.put("cardClass", cardInfo.getCardClass());
				}
			}
			
			if(CommonHelper.isNotEmpty(startCardId) && CommonHelper.isNotEmpty(endCardId)){//卡ID
				cardIdParams.put("startCardId", startCardId);
				cardIdParams.put("endCardId", endCardId);
			}else {
				// 如果只是输入开始卡号和结束卡号中的一个，则不予查询
				if((StringUtils.isNotEmpty(this.getStartCardId())&&StringUtils.isEmpty(this.getEndCardId()))||
						(StringUtils.isEmpty(this.getStartCardId())&&StringUtils.isNotEmpty(this.getEndCardId()))){ 
					return LIST;
				}
			}
			
			if(CommonHelper.isNotEmpty(cardSaleStartDate) && CommonHelper.isNotEmpty(cardSaleEndDate)){//售卡日期
				cardIdParams.put("cardSaleStartDate", cardSaleStartDate);
				cardIdParams.put("cardSaleEndDate", cardSaleEndDate);
			}
			
			//card_sub_calss_def
			if(null != cardSubClassDef && null != cardSubClassDef.getFaceValue()){
				cardIdParams.put("faceValue", cardSubClassDef.getFaceValue());
			}
			
			//sub_acct_bal
			if(CommonHelper.isNotEmpty(avaliableValueStart) && CommonHelper.isNotEmpty(avaliableValueEnd)){//余额
				cardIdParams.put("avaliableValueStart", NumberUtils.createDouble(avaliableValueStart));
				cardIdParams.put("avaliableValueEnd", NumberUtils.createDouble(avaliableValueEnd));
			}
			
			if(CommonHelper.checkIsNotEmpty(cardIdParams)){
				Map cardIds = cardInfoDAO.findShineWayQueryCardIds(cardIdParams);
				for(Iterator iter = cardIds.entrySet().iterator(); iter.hasNext();) {   
		            Map.Entry entry = (Map.Entry) iter.next();
		            cardIdList.add((String)entry.getKey());
		        }
				if(CommonHelper.checkIsNotEmpty(cardIdList)){
					params.put("cardids",cardIdList);
				}
			}else {
				return LIST;
			}
		}
		
		//trans
		if(CommonHelper.isNotEmpty(consumeStartDate) && CommonHelper.isNotEmpty(consumeEndDate)){//消费日期
			DatePair datePair = new DatePair(this.consumeStartDate, this.consumeEndDate);
			datePair.setTruncatedTimeDate(cardIdParams, "consumeStartDate", "consumeEndDate");
		}
		
		
		if(null != params.get("cardids")){//有CardIds才进行查询
			this.page = cardInfoDAO.findShineWayQueryCardInfo(params, getPageNumber(), getPageSize());
		}*/
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			branchList = this.getMyCardBranch();
			params.put("branchList", branchList);
		}
		// 售卡代理
		else if(RoleType.CARD_SELL.getValue().equals(getLoginRoleType())){
			branchList = getMyCardBranch();
			if (CollectionUtils.isEmpty(branchList)) {
				branchList.add(new BranchInfo());
			}
			params.put("branchList", branchList);
		} 
		// 商户
		else if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){
			params.put("appOrgId", this.getSessionUser().getMerchantNo());
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (cardInfo != null) {
			params.put("startCardId", this.getStartCardId()); //卡号 
			params.put("endCardId", this.getEndCardId());
			
			params.put("cardClass", cardInfo.getCardClass()); //卡类型 
			params.put("cardStatus", cardInfo.getCardStatus()); // 卡状态
			params.put("faceValue", cardSubClassDef.getFaceValue()); // 卡面值
			
			params.put("cardSaleStartDate", this.getCardSaleStartDate()); // 售卡日期
			params.put("cardSaleEndDate", this.getCardSaleEndDate());
			
			if (StringUtils.isNotEmpty(consumeStartDate)
					&& StringUtils.isNotEmpty(consumeEndDate)) {
				params.put("consumeStartDate", consumeStartDate);
				params.put("consumeEndDate", consumeEndDate);
			}
			
			if (StringUtils.isNotEmpty(avaliableValueStart)
					&& StringUtils.isNotEmpty(avaliableValueEnd)) {
				params.put("avaliableValueStart", NumberUtils.createDouble(avaliableValueStart)); // 充值资金余额
				params.put("avaliableValueEnd", NumberUtils.createDouble(avaliableValueEnd));
			}
			
			if (StringUtils.isNotEmpty(rebateAmtStart)
					&& StringUtils.isNotEmpty(rebateAmtEnd)) {
				params.put("rebateAmtStart", NumberUtils.createDouble(rebateAmtStart)); // 返利资金余额
				params.put("rebateAmtEnd", NumberUtils.createDouble(rebateAmtEnd));
			}
			
			params.put("cardIssuer", cardInfo.getCardIssuer()); // 发卡机构
			params.put("saleOrgId", cardInfo.getSaleOrgId()); // 售卡卡机构
			params.put("appOrgId", cardInfo.getAppOrgId()); // 领卡机构
			
			params.put("custName", cardExtraInfo.getCustName()); // 姓名
			params.put("mobileNo", cardExtraInfo.getMobileNo()); // 手机号
			params.put("credNo", cardExtraInfo.getCredNo()); // 身份证号

			if (StringUtils.isNotEmpty(cardInfo.getCardIssuer())
					|| (StringUtils.isNotEmpty(startCardId) && StringUtils.isNotEmpty(endCardId))) {
				this.page = cardInfoDAO.findShineWayQueryCardInfo(params, getPageNumber(), getPageSize());
			} else {
				this.page = Paginater.EMPTY;
			}
		}
		
		page = this.setPageData(page);
		
		return LIST;
	}
	
	private Paginater setPageData(Paginater paginater) throws BizException {
		if (paginater == null || paginater.getMaxRowCount() == 0 ) {
			return Paginater.EMPTY;
		}
		
		List<CardInfo> resultList = new ArrayList<CardInfo>();
		List<CardInfo> infoList = (List<CardInfo>) paginater.getData();
		
		for (CardInfo info : infoList) {
			String cardId = info.getCardId();
			String acctId = info.getAcctId();
			Assert.notEmpty(cardId, "卡号不能为空");
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(acctId);
			
			if (acctInfo != null) {
				// 初始金额
				info.setInitAmt(acctInfo.getInitialChargeAmt());
	
				// 充值资金余额
				SubAcctBalKey depositKey = new SubAcctBalKey(acctId, SubacctType.DEPOSIT.getValue());
				SubAcctBal depositBal = (SubAcctBal) this.subAcctBalDAO.findByPk(depositKey);
				if (depositBal != null) {
					info.setBalanceAmt(depositBal.getAvlbBal());
				} else {
					info.setBalanceAmt(BigDecimal.ZERO);
				}
				
				// 返利资金余额
				SubAcctBalKey rebateKey = new SubAcctBalKey(acctId, SubacctType.REBATE.getValue());
				SubAcctBal rebateBal = (SubAcctBal) this.subAcctBalDAO.findByPk(rebateKey);
				if (rebateBal != null) {
					info.setBal(rebateBal.getAvlbBal());
				} else {
					info.setBal(BigDecimal.ZERO);
				}
			} else {
				info.setInitAmt(BigDecimal.ZERO);
				info.setBalanceAmt(BigDecimal.ZERO);
				info.setBal(BigDecimal.ZERO);
			}
			
			Map<String, Object> map = this.cardInfoDAO.findTransByCardIdAndDate(cardId, consumeStartDate, consumeEndDate);
			// 消费总金额和总次数
			Object times = MapUtils.getObject(map, "totalAmt");
			if (times == null) {
				info.setConsumeAmt(BigDecimal.ZERO);
			} else {
				info.setConsumeAmt((BigDecimal) times);
			}
			
			info.setConsumeTimes((BigDecimal) MapUtils.getObject(map, "cardNum"));
			
			resultList.add(info);
		}
		
		paginater.setData(resultList);
		
		return paginater;
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getCardSaleStartDate() {
		return cardSaleStartDate;
	}

	public void setCardSaleStartDate(String cardSaleStartDate) {
		this.cardSaleStartDate = cardSaleStartDate;
	}

	public String getCardSaleEndDate() {
		return cardSaleEndDate;
	}

	public void setCardSaleEndDate(String cardSaleEndDate) {
		this.cardSaleEndDate = cardSaleEndDate;
	}
	
	public String getConsumeStartDate() {
		return consumeStartDate;
	}

	public void setConsumeStartDate(String consumeStartDate) {
		this.consumeStartDate = consumeStartDate;
	}

	public String getConsumeEndDate() {
		return consumeEndDate;
	}

	public void setConsumeEndDate(String consumeEndDate) {
		this.consumeEndDate = consumeEndDate;
	}
	
	public String getavaliableValueStart() {
		return avaliableValueStart;
	}

	public void setAvaliableValueStart(String avaliableValueStart) {
		this.avaliableValueStart = avaliableValueStart;
	}

	public String getAvaliableValueEnd() {
		return avaliableValueEnd;
	}

	public void setAvaliableValueEnd(String avaliableValueEnd) {
		this.avaliableValueEnd = avaliableValueEnd;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}
	
	public CardExtraInfo getCardExtraInfo() {
		return cardExtraInfo;
	}

	public void setCardExtraInfo(CardExtraInfo cardExtraInfo) {
		this.cardExtraInfo = cardExtraInfo;
	}
	
	public CardSubClassDef getCardSubClassDef() {
		return cardSubClassDef;
	}

	public void setCardSubClassDef(CardSubClassDef cardSubClassDef) {
		this.cardSubClassDef = cardSubClassDef;
	}
	
	public Trans getTrans() {
		return trans;
	}

	public void setTrans(Trans trans) {
		this.trans = trans;
	}
	
	public List<CardState> getCardStatusList() {
		return cardStatusList;
	}

	public void setCardStatusList(List<CardState> cardStatusList) {
		this.cardStatusList = cardStatusList;
	}

	public List<CardType> getCardTypeList() {
		return cardTypeList;
	}

	public void setCardTypeList(List<CardType> cardTypeList) {
		this.cardTypeList = cardTypeList;
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

	public String getAvaliableValueStart() {
		return avaliableValueStart;
	}

	public String getRebateAmtStart() {
		return rebateAmtStart;
	}

	public void setRebateAmtStart(String rebateAmtStart) {
		this.rebateAmtStart = rebateAmtStart;
	}

	public String getRebateAmtEnd() {
		return rebateAmtEnd;
	}

	public void setRebateAmtEnd(String rebateAmtEnd) {
		this.rebateAmtEnd = rebateAmtEnd;
	}
}
