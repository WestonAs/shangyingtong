package gnete.card.adapter;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.SaleCardBatRegDAO;
import gnete.card.dao.SaleCardRegDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.CardStockInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.SaleCardBatReg;
import gnete.card.entity.SaleCardReg;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.CardStockState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.SellType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.CardRiskService;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: SaleCardBatchAdapter.java
 * @description:售卡流程适配器（包含批量售卡和单张卡售卡）
 */
@Repository
public class SaleCardBatchAdapter implements WorkflowAdapter {

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
	private AccuClassDefDAO accuClassDefDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;

	private static Logger logger = Logger.getLogger(SaleCardBatchAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {
		logger.debug("售卡审批通过的相关处理。");
		SaleCardReg saleCardReg = (SaleCardReg) this.saleCardRegDAO.findByPk(NumberUtils.toLong(refid));
		Assert.notNull(saleCardReg, "找不到该售卡[" + refid + "]记录");
		
		this.dealForSuccess(saleCardReg, userId);
	}

	public Object getJobslip(String refid) {
		return this.saleCardRegDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_SALE_CARD_BATCH;
	}

	public void postBackward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("售卡审批不通过，回退的相关处理。");
		SaleCardReg saleCardReg = (SaleCardReg) this.saleCardRegDAO.findByPk(NumberUtils.toLong(refid));
		
		this.dealForFailure(saleCardReg, userId);
	}

	public void postForward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("下发");
	}
	
	/**
	 * 审核通过的相关处理
	 * @param saleCardReg
	 * @param userId
	 * @throws BizException
	 */
	private void dealForSuccess(SaleCardReg saleCardReg, String userId) throws BizException {
		Assert.notEmpty(saleCardReg.getBranchCode(), "售卡机构不能为空");
		
		CardInfo cardInfo = null;
		if(saleCardReg.isSingleCardSale()){
			logger.debug("单张卡售卡审核通过");
			cardInfo = (CardInfo)this.cardInfoDAO.findByPk(saleCardReg.getCardId());
			Assert.notNull(cardInfo, "卡号[" + saleCardReg.getCardId() + "]不存在");
		} else {
			logger.debug("批量售卡审核通过");
			List<SaleCardBatReg> saleCardBatRegList = this.saleCardBatRegDAO.findBySaleBatchId(saleCardReg.getSaleBatchId());
			
			cardInfo = (CardInfo)this.cardInfoDAO.findByPk(saleCardBatRegList.get(0).getCardId());
			Assert.notNull(cardInfo, "卡号[" + saleCardBatRegList.get(0).getCardId() + "]不存在");
		}
		
		if (saleCardReg.isPreSellReg()) { 
			// 预售卡记录，则更新状态为未激活
			saleCardReg.setStatus(RegisterState.INACTIVE.getValue());
		} else { 
			// 实时售卡记录，审核通过改成待处理
			saleCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
			// 扣减风险准备金和操作员配额（非预售）
			this.dealCardRisk(saleCardReg, cardInfo);
			// 如果是实时售卡审核通过则发报文
			MsgSender.sendMsg(MsgType.SALE_CARD, saleCardReg.getSaleBatchId(), userId);
		}
		saleCardReg.setUpdateUser(userId);
		saleCardReg.setUpdateTime(new Date());
		this.saleCardRegDAO.update(saleCardReg);
	}
	
	/**
	 * 扣减风险准备金和操作员配额（非预售）
	 * @param saleCardReg
	 * @param cardInfo
	 * @throws BizException
	 */
	private void dealCardRisk(SaleCardReg saleCardReg, CardInfo cardInfo) throws BizException {
		BigDecimal riskAmt = AmountUtil.add(saleCardReg.getAmt(), saleCardReg.getRebateAmt());
		
		// 次卡需要读取清算金额
		if (DepositType.TIMES.getValue().equals(saleCardReg.getDepositType())) {
			String cardSubClass = cardInfo.getCardSubclass();
			
			CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardSubClass);
			String freqClass = cardSubClassDef.getFrequencyClass();
			Assert.notEmpty(freqClass, "次数卡无子类型定义[" + freqClass + "]");
			
			AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(freqClass);
			if (accuClassDef.getSettAmt() == null || accuClassDef.getSettAmt().doubleValue() == 0.0) {
				riskAmt = new BigDecimal(0.0);
			} else {
				riskAmt = AmountUtil.multiply(accuClassDef.getSettAmt(), saleCardReg.getAmt());
			}
		}
		
		// 发卡机构自己不处理配额的情况
		if (!saleCardReg.getBranchCode().equals(StringUtils.trim(cardInfo.getCardIssuer()))) {
			BranchSellReg branchSellReg = new BranchSellReg(); 
			
			branchSellReg.setId(saleCardReg.getSaleBatchId());	// 售卡登记薄的ID
			branchSellReg.setAdjType(AdjType.SELL.getValue());
//			branchSellReg.setAmt(saleCardReg.getRealAmt()); // 相关金额应该为清算金额
			branchSellReg.setAmt(riskAmt); // 相关金额应该为清算金额
			branchSellReg.setCardBranch(StringUtils.trim(cardInfo.getCardIssuer()));	// 发卡机构
			branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
			branchSellReg.setSellBranch(saleCardReg.getBranchCode());
			if (saleCardReg.getBranchCode().startsWith("D")) {
				branchSellReg.setSellType(SellType.DEPT.getValue());
			} else {
				branchSellReg.setSellType(SellType.BRANCH.getValue());
			}
			this.cardRiskService.activateSell(branchSellReg);
		}
		// 扣减售卡申请人的操作员配额。 扣的是操作员清算金额
		this.cardRiskService.deductUserAmt(saleCardReg.getEntryUserid(), saleCardReg.getBranchCode(), riskAmt);
		
		// 扣减风险准备金
		CardRiskReg cardRiskReg = new CardRiskReg();
		
		cardRiskReg.setId(saleCardReg.getSaleBatchId());	// 售卡登记薄的ID
		cardRiskReg.setAdjType(AdjType.SELL.getValue());
		cardRiskReg.setAmt(riskAmt);
		cardRiskReg.setBranchCode(StringUtils.trim(cardInfo.getCardIssuer()));	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
		this.cardRiskService.activateCardRisk(cardRiskReg);
	}

	/**
	 * 售卡审核不通过的相关处理
	 * @param saleCardReg
	 * @throws BizException
	 */
	private void dealForFailure(SaleCardReg saleCardReg, String userId) throws BizException {
		saleCardReg.setStatus(RegisterState.FALURE.getValue());
		saleCardReg.setUpdateUser(userId);
		saleCardReg.setUpdateTime(new Date());
		this.saleCardRegDAO.update(saleCardReg);
		
		// 删掉卡附加信息表的记录
		if(saleCardReg.isSingleCardSale()){
			logger.debug("单张卡售卡不通过");
			String cardId = saleCardReg.getCardId();
			
			CardExtraInfo cardExtraInfo = (CardExtraInfo)this.cardExtraInfoDAO.findByPk(cardId);
			if(cardExtraInfo != null){
				this.cardExtraInfoDAO.delete(cardId);
			}
			// 更改 卡信息 表的状态为 已领卡待售
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			if (cardInfo.isPreselledStatus()) {
				this.cardInfoDAO.updateCardStatus(cardId, CardState.FORSALE.getValue());
			}
			// 更改 卡库存 表的状态为 已领卡
			CardStockInfo cardStockInfo = this.cardStockInfoDAO.findCardStockInfoByCardId(cardId);
			if (cardStockInfo.isSoldingOrPresoldStatus()) {
				this.cardStockInfoDAO.updateStatus(cardId, CardStockState.RECEIVED.getValue());
			}
		} else {
			logger.debug("批量卡售卡审核不通过");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("saleBatchId", saleCardReg.getSaleBatchId());
			List<SaleCardBatReg> saleCardBatRegList = this.saleCardBatRegDAO.findSaleCardBatReg(params);
			for (SaleCardBatReg saleCardBatReg : saleCardBatRegList) {
				saleCardBatReg.setStatus(RegisterState.FALURE.getValue());
				saleCardBatReg.setUpdateUser(userId);
				saleCardBatReg.setUpdateTime(new Date());
				saleCardBatReg.setUpdateTime(new Date());
				this.saleCardBatRegDAO.update(saleCardBatReg);
				
				String cardId = saleCardBatReg.getCardId();
				
				this.cardExtraInfoDAO.delete(cardId);
				
				// 更改 卡信息 表的状态为 已领卡待售
				CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
				if (cardInfo.isPreselledStatus()) {
					this.cardInfoDAO.updateCardStatus(cardId, CardState.FORSALE.getValue());
				}
				
				// 更改 卡库存 表的状态为 已领卡
				CardStockInfo cardStockInfo = this.cardStockInfoDAO.findCardStockInfoByCardId(cardId);
				if (cardStockInfo.isSoldingOrPresoldStatus()) {
					this.cardStockInfoDAO.updateStatus(cardId, CardStockState.RECEIVED.getValue());
				}
			}
		}
	}
		
}
