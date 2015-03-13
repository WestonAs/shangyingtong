package gnete.card.msg.adapter;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.IcCardExtroInfoDAO;
import gnete.card.dao.IcTempParaDAO;
import gnete.card.dao.SaleCardBatRegDAO;
import gnete.card.dao.SaleCardRegDAO;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.CardStockInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.IcCardExtroInfo;
import gnete.card.entity.IcTempPara;
import gnete.card.entity.SaleCardBatReg;
import gnete.card.entity.SaleCardReg;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.state.CardStockState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.SellType;
import gnete.card.msg.MsgAdapter;
import gnete.card.msg.MsgType;
import gnete.card.service.CardRiskService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 普通售卡 更改状态
 */
@Repository
public class Msg2001Adapter implements MsgAdapter {
	@Autowired
	private SaleCardRegDAO saleCardRegDAO;
	@Autowired
	private CardExtraInfoDAO cardExtraInfoDAO;
	@Autowired
	private SaleCardBatRegDAO saleCardBatRegDAO;
	@Autowired
	private CardRiskService cardRiskService;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;
	@Autowired
	private IcCardExtroInfoDAO icCardExtroInfoDAO;
	@Autowired
	private IcTempParaDAO icTempParaDAO;
	
	private static final Logger logger = Logger.getLogger(Msg2001Adapter.class);
	
	public void deal(Long id, boolean isSuccess) throws BizException {
		SaleCardReg saleCardReg = (SaleCardReg) this.saleCardRegDAO.findByPk(id);
		Assert.notNull(saleCardReg, "找不到售卡记录[" + id +"]");
		if (isSuccess) {
			dealForSuccess(saleCardReg);
		} else {
			dealForFailure(saleCardReg);
		}
	}
	
	private void dealForSuccess(SaleCardReg saleCardReg) throws BizException {
		saleCardReg.setStatus(RegisterState.NORMAL.getValue());
		saleCardReg.setUpdateTime(new Date());
		this.saleCardRegDAO.update(saleCardReg);
		if (!saleCardReg.isSingleCardSale()) {
			this.saleCardBatRegDAO.updateStatusByBatId(saleCardReg.getSaleBatchId(), RegisterState.NORMAL.getValue());
		}
		
		// 找出需要处理卡号List
		List<String> cardIdList = this.findCardIdsToDeal(saleCardReg); 
		// 更新 卡库存 状态
		List<CardStockInfo> stockInfoList = new ArrayList<CardStockInfo>(); 
		for (String cardId : cardIdList) {
			CardStockInfo cardStockInfo = this.cardStockInfoDAO.findCardStockInfoByCardId(cardId);
			Assert.notNull(cardStockInfo, "卡号[" + cardId + "]在库存表中的记录不存在");
			if (cardStockInfo.isSoldingOrPresoldStatus()) {
				cardStockInfo.setCardStatus(CardStockState.SOLD_OUT.getValue()); 
				stockInfoList.add(cardStockInfo);
			}
		}
		this.cardStockInfoDAO.updateBatch(stockInfoList);
		
		// 插入ic卡附加信息
		List<IcCardExtroInfo> icCardExtroList = new ArrayList<IcCardExtroInfo>();
		for (String cardId : cardIdList) {
			CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号[" + cardId + "]的记录不存在");
			CardSubClassDef subClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
			Assert.notNull(subClass, "卡类型号[" + cardInfo.getCardSubclass() + "]不存在");
			if (subClass.isIcOrCombineOrM1Type()) { // 纯IC卡或复合卡 
				icCardExtroList.add(buildIcCardExtroInfo(cardInfo));
			}
		}
		this.icCardExtroInfoDAO.insertBatch(icCardExtroList);
	}

	private void dealForFailure(SaleCardReg saleCardReg) throws BizException {
		saleCardReg.setStatus(RegisterState.DISABLE.getValue());
		saleCardReg.setRemark(getNote(saleCardReg.getSaleBatchId()));
		this.saleCardRegDAO.update(saleCardReg);
		if (!saleCardReg.isSingleCardSale()) {
			this.saleCardBatRegDAO.updateStatusByBatId(saleCardReg.getSaleBatchId(), RegisterState.DISABLE.getValue());
		}
		
		// 找出需要处理卡号List
		List<String> cardIdList = this.findCardIdsToDeal(saleCardReg);
		
		String cardSubClassStr = null;
		for(String cardId : cardIdList){
			// 删除卡附加信息表记录
			this.cardExtraInfoDAO.delete(cardId);
			
			CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(cardId);
			if(cardSubClassStr == null){
				cardSubClassStr = cardInfo.getCardSubclass();
			}
			
			// 更新卡库存状态为已领卡
			CardStockInfo cardStockInfo = this.cardStockInfoDAO.findCardStockInfoByCardId(cardId);
			if (cardStockInfo.isSoldingOrPresoldStatus()) {
				cardStockInfo.setCardStatus(CardStockState.RECEIVED.getValue());
				this.cardStockInfoDAO.update(cardStockInfo);
			}
		}
		
		this.dealCardRisk(saleCardReg, cardSubClassStr);
	}
	
	
	private String getNote(Long id) throws BizException {
		Waitsinfo waitsinfo = this.waitsinfoDAO.findWaitsinfo(MsgType.SALE_CARD, id);
		return waitsinfo == null ? "" : waitsinfo.getNote();
	}
	
	/** 
	 * 获得需要处理的卡号集合 
	 */
	private List<String> findCardIdsToDeal(SaleCardReg saleCardReg) throws BizException {
		List<String> cardIdList  = new ArrayList<String>();
		if (saleCardReg.isSingleCardSale()) {// 单笔预售卡激活
			cardIdList.add(saleCardReg.getCardId());
		} else { //批量预售卡激活
			List<SaleCardBatReg> saleCardBatRegList = this.saleCardBatRegDAO.findBySaleBatchId(saleCardReg.getSaleBatchId());
			Assert.notEmpty(saleCardBatRegList, "售卡批次号[" + saleCardReg.getSaleBatchId() + "]的记录在批量明细表中没有记录");
			for(SaleCardBatReg scbReg : saleCardBatRegList){
				cardIdList.add(scbReg.getCardId());		
			}
		}
		return cardIdList;
	}
	
	/** 构建 IcCardExtroInfo 对象 */
	private IcCardExtroInfo buildIcCardExtroInfo(CardInfo cardInfo)
			throws BizException {
		IcTempPara icTempPara = (IcTempPara) this.icTempParaDAO.findByPk(cardInfo.getCardSubclass());
		Assert.notNull(icTempPara, "IC卡个人化参数表中没有卡类型号为[" + cardInfo.getCardSubclass() + "]的记录");

		Assert.isNull(icCardExtroInfoDAO.findByPk(cardInfo.getCardId()), "IC卡附加信息表中已经存在卡号["
				+ cardInfo.getCardId() + "]的记录");

		IcCardExtroInfo icCardExtroInfo = new IcCardExtroInfo();

		icCardExtroInfo.setCardId(cardInfo.getCardId());
		icCardExtroInfo.setCardSubclass(cardInfo.getCardSubclass());
		icCardExtroInfo.setAutoDepositFlag(icTempPara.getAutoDepositFlag());
		if (Symbol.YES.equals(icTempPara.getAutoDepositFlag())) {
			icCardExtroInfo.setAutoDepositAmt(icTempPara.getAutoDepositAmt());
		} else {
			icCardExtroInfo.setAutoDepositAmt(BigDecimal.ZERO);
		}
		icCardExtroInfo.setBalanceLimit(icTempPara.getBalanceLimit());
		icCardExtroInfo.setAmountLimit(icTempPara.getAmountLimit());
		icCardExtroInfo.setCardBranch(cardInfo.getCardIssuer());
		icCardExtroInfo.setAppOrgId(cardInfo.getAppOrgId());
		icCardExtroInfo.setUpdateTime(new Date());
		return icCardExtroInfo;
	}
	
	private void dealCardRisk(SaleCardReg saleCardReg, String cardSubClassStr) throws BizException {
		// 次卡需要读取清算金额
		BigDecimal riskAmt = AmountUtil.add(saleCardReg.getAmt(), saleCardReg.getRebateAmt());
		if (DepositType.TIMES.getValue().equals(saleCardReg.getDepositType())) {
			CardSubClassDef subClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardSubClassStr);
			Assert.notNull(subClass, "卡类型号[" + cardSubClassStr + "]不存在");
			String freqClass = subClass.getFrequencyClass();
			Assert.notEmpty(freqClass, "次数卡无子类型定义[" + freqClass + "]");
			
			AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(freqClass);
			if (accuClassDef.getSettAmt() == null || accuClassDef.getSettAmt().doubleValue() == 0.0) {
				riskAmt = BigDecimal.ZERO;
			} else {
				riskAmt = AmountUtil.multiply(accuClassDef.getSettAmt(), saleCardReg.getAmt());
			}
		}
		
		// 把扣掉的风险准备金补回来
		// 发卡机构自己不做限制
		if (!StringUtils.equals(saleCardReg.getBranchCode(), saleCardReg.getCardBranch())) {
			BranchSellReg branchSellReg = new BranchSellReg(); 
			branchSellReg.setId(saleCardReg.getSaleBatchId());	// 售卡登记薄的ID
			branchSellReg.setAdjType(AdjType.SELL.getValue());
			branchSellReg.setAmt(AmountUtil.subtract(BigDecimal.ZERO, riskAmt));
			branchSellReg.setCardBranch(saleCardReg.getCardBranch());		// 发卡机构
			branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
			branchSellReg.setSellBranch(saleCardReg.getBranchCode());	// 售卡机构
			if (saleCardReg.getBranchCode().startsWith("D")) {
				branchSellReg.setSellType(SellType.DEPT.getValue());
			} else {
				branchSellReg.setSellType(SellType.BRANCH.getValue());
			}
			this.cardRiskService.activateSell(branchSellReg);

		}
		this.cardRiskService.deductUserAmt(saleCardReg.getUpdateUser(), 
				saleCardReg.getBranchCode(), AmountUtil.subtract(BigDecimal.ZERO, riskAmt));
		
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(saleCardReg.getSaleBatchId());	// 售卡登记薄的ID
		cardRiskReg.setAdjType(AdjType.SELL.getValue());
		cardRiskReg.setAmt(AmountUtil.subtract(BigDecimal.ZERO, riskAmt));
		cardRiskReg.setBranchCode(saleCardReg.getCardBranch());	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
		this.cardRiskService.activateCardRisk(cardRiskReg);
	}
	
}
