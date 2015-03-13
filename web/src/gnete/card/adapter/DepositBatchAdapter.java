package gnete.card.adapter;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.DepositBatRegDAO;
import gnete.card.dao.DepositRegDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.DepositBatReg;
import gnete.card.entity.DepositReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.PreDepositType;
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
 * @File: DepositBatchAdapter.java
 *
 * @description: 充值审核适配器，包含批量和单笔充值
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lih
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-12-14
 */
@Repository
public class DepositBatchAdapter implements WorkflowAdapter {

	@Autowired
	private DepositRegDAO depositRegDAO;
	@Autowired
	private DepositBatRegDAO depositBatRegDAO;
	@Autowired
	private CardRiskService cardRiskService;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO; 
	
	static Logger logger = Logger.getLogger(DepositBatchAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {
		logger.debug("审批通过的相关处理。");
		DepositReg depositReg = (DepositReg) this.depositRegDAO.findByPk(NumberUtils.toLong(refid));
		
		/*// 如果是实时充值，则改状态为待处理并发报文
		if (!PreDepositType.PRE_DEPOSIT.getValue().equals(depositReg.getPreDeposit())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("depositBatchId", depositReg.getDepositBatchId());
			List<DepositBatReg> depositBatRegList = this.depositBatRegDAO.findDepositBatReg(params);
			
			CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(depositBatRegList.get(0).getCardId());
			
			// 实时售卡审核通过改成待处理
			depositReg.setStatus(RegisterState.WAITEDEAL.getValue());
			
			// 次卡需要读取清算金额
			BigDecimal riskAmt = AmountUtil.add(depositReg.getAmt(), depositReg.getRebateAmt());
			if (DepositType.TIMES.getValue().equals(depositReg.getDepositType())) {
				String cardSubClass = cardInfo.getCardSubclass();
				
				CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardSubClass);
				String freqClass = cardSubClassDef.getFrequencyClass();
				Assert.notEmpty(freqClass, "次数卡无子类型定义[" + freqClass + "]");
				
				AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(freqClass);
				if (accuClassDef.getSettAmt() == null || accuClassDef.getSettAmt().doubleValue() == 0.0) {
					riskAmt = new BigDecimal(0.0);
				} else {
					riskAmt = AmountUtil.multiply(accuClassDef.getSettAmt(), depositReg.getAmt());
				}
			}
			
			// 发卡机构自己不处理配额的情况
			if (!depositReg.getDepositBranch().equals(cardInfo.getCardIssuer().trim())) {
				BranchSellReg branchSellReg = new BranchSellReg(); 
				branchSellReg.setId(depositReg.getDepositBatchId());	// 售卡登记薄的ID
				branchSellReg.setAdjType(AdjType.MANAGE.getValue());
				branchSellReg.setAmt(depositReg.getRealAmt());
				branchSellReg.setCardBranch(cardInfo.getCardIssuer().trim());	// 发卡机构
				branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
				branchSellReg.setSellBranch(depositReg.getDepositBranch());
				if (depositReg.getDepositBranch().startsWith("D")) {
					branchSellReg.setSellType(SellType.DEPT.getValue());
				} else {
					branchSellReg.setSellType(SellType.BRANCH.getValue());
				}
				this.cardRiskService.activateSell(branchSellReg);
			}
			
			CardRiskReg cardRiskReg = new CardRiskReg();
			cardRiskReg.setId(depositReg.getDepositBatchId());	// 售卡登记薄的ID
			cardRiskReg.setAdjType(AdjType.MANAGE.getValue());
			cardRiskReg.setAmt(riskAmt);
			cardRiskReg.setBranchCode(cardInfo.getCardIssuer().trim());	// 发卡机构
			cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
			this.cardRiskService.activateCardRisk(cardRiskReg);
			
			// 如果是实时售卡审核通过则发报文
			MsgSender.sendMsg(MsgType.DEPOSIT, depositReg.getDepositBatchId(), userId);;
			
		}

		// 如果是预充值，则改状态为未激活
		else {
			depositReg.setStatus(RegisterState.INACTIVE.getValue());
		}
		this.depositRegDAO.update(depositReg);*/
		
		this.dealForSuccess(depositReg, userId);
	}

	public Object getJobslip(String refid) {
		return this.depositRegDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_DEPOSIT_BATCH;
	}

	public void postBackward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		DepositReg depositReg = (DepositReg) this.depositRegDAO.findByPk(NumberUtils.toLong(refid));
		
		/*depositReg.setStatus(RegisterState.FALURE.getValue());
		depositReg.setUpdateUser(userId);
		depositReg.setUpdateTime(new Date());
		this.depositRegDAO.update(depositReg);
		
		// 删掉卡附加信息表的记录
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("saleBatchId", depositReg.getDepositBatchId());
		List<DepositBatReg> depositBatRegList = this.depositBatRegDAO.findDepositBatReg(params);
		
		for (DepositBatReg depositBatReg : depositBatRegList) {
			depositBatReg.setStatus(RegisterState.FALURE.getValue());
			this.depositBatRegDAO.update(depositBatReg);
		}*/
		
		this.dealForFailure(depositReg, userId);
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
	private void dealForSuccess(DepositReg depositReg, String userId) throws BizException {
		CardInfo cardInfo = null;
		
		boolean isBat = false;
		if(StringUtils.isNotEmpty(depositReg.getCardId())){
			logger.debug("单张卡充值审核通过");
			cardInfo = (CardInfo)this.cardInfoDAO.findByPk(depositReg.getCardId());
			Assert.notNull(cardInfo, "卡号[" + depositReg.getCardId() + "]不存在");
		} else {
			logger.debug("批量充值审核通过");
			isBat = true;
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("depositBatchId", depositReg.getDepositBatchId());
			List<DepositBatReg> depositBatRegList = this.depositBatRegDAO.findDepositBatReg(params);
			
			cardInfo = (CardInfo)this.cardInfoDAO.findByPk(depositBatRegList.get(0).getCardId());
			Assert.notNull(cardInfo, "卡号[" + depositBatRegList.get(0).getCardId() + "]不存在");
		}
		
		// 如果是实时充值，则改状态为待处理并发报文
		if (PreDepositType.REAL_DEPOSIT.getValue().equals(depositReg.getPreDeposit())) {
			// 实时充值审核通过改成待处理
			depositReg.setStatus(RegisterState.WAITEDEAL.getValue());
			if (isBat) {
				Map<String, Object> batMap = new HashMap<String, Object>();
				
				batMap.put("status", RegisterState.WAITEDEAL.getValue());
				batMap.put("depositBatchId", depositReg.getDepositBatchId());
				
				int count = this.depositBatRegDAO.updateStatusByBatchId(batMap);
				logger.debug("批量充值审核通过后，更新批量明细表的笔数为[" + count + "]");
			}
			
			// 扣减风险准备金和操作员配额
			this.dealCardRisk(depositReg, cardInfo);
			
			// 实时充值审核通过直接发送报文
			MsgSender.sendMsg(MsgType.DEPOSIT, depositReg.getDepositBatchId(), userId);
		} else {
			// 如果是预充值。则改状态为未激活
			depositReg.setStatus(RegisterState.INACTIVE.getValue());
		}
		
		depositReg.setUpdateUser(userId);
		depositReg.setUpdateTime(new Date());
		
		this.depositRegDAO.update(depositReg);
	}
	
	/**
	 * 扣减风险准备金和操作员配额（非预充值）
	 * @param saleCardReg
	 * @param cardInfo
	 * @throws BizException
	 */
	private void dealCardRisk(DepositReg depositReg, CardInfo cardInfo) throws BizException {
		// 计算风险保证金的扣减金额
		BigDecimal riskAmt = AmountUtil.add(depositReg.getAmt(), depositReg.getRebateAmt());
		// 次卡需要读取清算金额
		if (DepositType.TIMES.getValue().equals(depositReg.getDepositType())) {
			String cardSubClass = cardInfo.getCardSubclass();
			
			CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardSubClass);
			String freqClass = cardSubClassDef.getFrequencyClass();
			Assert.notEmpty(freqClass, "次数卡无子类型定义[" + freqClass + "]");
			
			AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(freqClass);
			if (accuClassDef.getSettAmt() == null || accuClassDef.getSettAmt().doubleValue() == 0.0) {
				riskAmt = new BigDecimal(0.0);
			} else {
				riskAmt = AmountUtil.multiply(accuClassDef.getSettAmt(), depositReg.getAmt());
			}
		}
		
		// 发卡机构自己不处理配额的情况
		if (!depositReg.getDepositBranch().equals(cardInfo.getCardIssuer().trim())) {
			BranchSellReg branchSellReg = new BranchSellReg(); 
			branchSellReg.setId(depositReg.getDepositBatchId());	// 售卡登记薄的ID
			branchSellReg.setAdjType(AdjType.MANAGE.getValue());
//			branchSellReg.setAmt(depositReg.getRealAmt());
			branchSellReg.setAmt(riskAmt);
			branchSellReg.setCardBranch(cardInfo.getCardIssuer().trim());	// 发卡机构
			branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
			branchSellReg.setSellBranch(depositReg.getDepositBranch());
			if (depositReg.getDepositBranch().startsWith("D")) {
				branchSellReg.setSellType(SellType.DEPT.getValue());
			} else {
				branchSellReg.setSellType(SellType.BRANCH.getValue());
			}
			this.cardRiskService.activateSell(branchSellReg);
		}
		// 扣减充值申请人的操作员配额。 扣的是操作员清算金额
		this.cardRiskService.deductUserAmt(depositReg.getEntryUserId(), depositReg.getDepositBranch(), riskAmt);
		
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(depositReg.getDepositBatchId());	// 售卡登记薄的ID
		cardRiskReg.setAdjType(AdjType.MANAGE.getValue());
		cardRiskReg.setAmt(riskAmt);
		cardRiskReg.setBranchCode(cardInfo.getCardIssuer().trim());	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
		this.cardRiskService.activateCardRisk(cardRiskReg);
	}
	
	/**
	 * 审核不通过的相关处理
	 * @param saleCardReg
	 * @param userId
	 * @throws BizException
	 */
	private void dealForFailure(DepositReg depositReg, String userId) throws BizException {
		
		depositReg.setStatus(RegisterState.FALURE.getValue());
		
		if(StringUtils.isNotEmpty(depositReg.getCardId())){
			logger.debug("单张卡充值审核不通过");
		} else {
			logger.debug("批量卡充值审核不通过");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("depositBatchId", depositReg.getDepositBatchId());
			List<DepositBatReg> depositBatRegList = this.depositBatRegDAO.findDepositBatReg(params);
			
			for (DepositBatReg depositBatReg : depositBatRegList) {
				depositBatReg.setStatus(RegisterState.FALURE.getValue());
				depositBatReg.setUpdateTime(new Date());
				depositBatReg.setUpdateUser(userId);
				
				this.depositBatRegDAO.update(depositBatReg);
			}
		}
		
		depositReg.setUpdateUser(userId);
		depositReg.setUpdateTime(new Date());
		
		this.depositRegDAO.update(depositReg);
	}

}
