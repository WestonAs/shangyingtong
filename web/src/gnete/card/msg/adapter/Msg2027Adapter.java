package gnete.card.msg.adapter;

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
import gnete.card.entity.type.SellType;
import gnete.card.msg.MsgType;
import gnete.card.msg.adapter.base.MsgBaseAdapter;
import gnete.card.service.CardRiskService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: Msg2027Adapter.java
 *
 * @description: 充值撤销报文后台返回处理类
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-3-16
 */
@Repository
public class Msg2027Adapter extends MsgBaseAdapter{
	
	@Autowired
	private DepositRegDAO depositRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardRiskService cardRiskService;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	@Autowired
	private DepositBatRegDAO depositBatRegDAO;

	static Logger logger = Logger.getLogger(Msg2027Adapter.class);
	
	public void deal(Long id, boolean isSuccess) throws BizException {
		DepositReg deposit = (DepositReg) depositRegDAO.findByPk(id);
		Assert.notNull(deposit, "找不到该充值撤销记录");
		
		DepositReg oldReg = (DepositReg) depositRegDAO.findByPk(deposit.getOldDepositBatch());
		Assert.notNull(oldReg, "找不到原充值记录");
		if(deposit != null && StringUtils.isNotEmpty(deposit.getCardId())){
			logger.debug("单笔充值撤销");
			// 单笔充值
			if (isSuccess) {
				logger.debug("充值撤销处理成功，将充值登记簿中的状态改为成功，修改原充值记录为已撤销，将扣的风险保证金返还。");
				deposit.setStatus(RegisterState.NORMAL.getValue());
				
				oldReg.setStatus(RegisterState.CANCELED.getValue());
				oldReg.setCancelFlag(Symbol.YES); // 已撤销
				
				addDeductUserAmt(oldReg);
			} else {
				logger.debug("充值撤销处理失败，将充值登记簿中的状态改为失败，原纪录可以再次被撤销。");
				deposit.setRemark(StringUtils.trimToEmpty(deposit.getRemark())
						+ super.getWaitsinfoNote(MsgType.DEPOSIT_CANCEL, id));
				deposit.setStatus(RegisterState.DISABLE.getValue());
				oldReg.setStatus(RegisterState.NORMAL.getValue());// 修改原纪录为成功状态，可再次撤销
			}
			depositRegDAO.update(oldReg);
			depositRegDAO.update(deposit);
		}
		// 批量充值
		else {
			logger.debug("批量撤销");
			// 批量充值登记簿
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("depositBatchId", deposit.getOldDepositBatch());
			List<DepositBatReg> depositBatRegList = this.depositBatRegDAO.findDepositBatReg(params);
			if (isSuccess) {
				logger.debug("充值撤销处理成功，将充值登记簿中的状态改为成功，修改原充值记录为已撤销，将扣的风险保证金返还。");
				deposit.setStatus(RegisterState.NORMAL.getValue());
				for (DepositBatReg batReg : depositBatRegList) {
					batReg.setStatus(RegisterState.CANCELED.getValue());
					
					this.depositBatRegDAO.update(batReg);
				}
				
				oldReg.setStatus(RegisterState.CANCELED.getValue());
				oldReg.setCancelFlag(Symbol.YES); // 已撤销
				
				addDeductUserAmt(deposit);
			} else {
				logger.debug("充值撤销处理失败，将充值登记簿中的状态改为失败。");
				deposit.setStatus(RegisterState.DISABLE.getValue());
				deposit.setRemark(StringUtils.trimToEmpty(deposit.getRemark())
						+ getWaitsinfoNote(MsgType.DEPOSIT_CANCEL, id));
				oldReg.setStatus(RegisterState.NORMAL.getValue());// 修改原纪录为成功状态，可再次撤销
			}
			depositRegDAO.update(oldReg);
			depositRegDAO.update(deposit);
		}
	}
	
	/**
	 * 将扣掉的风险保障金和配额补回来
	 * 
	 * @param depositReg
	 * @throws BizException
	 */
	private void addDeductUserAmt(DepositReg depositReg) throws BizException {
		CardInfo cardInfo = new CardInfo();
		if (StringUtils.isNotEmpty(depositReg.getCardId())) {
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(depositReg.getCardId());
		} else {
			// 批量充值登记簿
			Map<String, Object> params = new HashMap<String, Object>();
			Assert.notNull(depositReg.getOldDepositBatch(), "原充值批次号不能为空");
			params.put("depositBatchId", depositReg.getOldDepositBatch());
			List<DepositBatReg> depositBatRegList = this.depositBatRegDAO.findDepositBatReg(params);
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(depositBatRegList.get(0).getCardId());
		}
		
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
		
		// 将扣掉的风险准备金和配额补回来
		if (!depositReg.getDepositBranch().equals(cardInfo.getCardIssuer().trim())) {
			BranchSellReg branchSellReg = new BranchSellReg(); 
			branchSellReg.setId(depositReg.getDepositBatchId());	// 充值登记薄的ID
			branchSellReg.setAdjType(AdjType.MANAGE.getValue());
			branchSellReg.setAmt(AmountUtil.subtract(new BigDecimal(0.0), depositReg.getRealAmt()));
			branchSellReg.setCardBranch(cardInfo.getCardIssuer());		// 发卡机构
			branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
			branchSellReg.setSellBranch(depositReg.getDepositBranch());	// 充值机构
			if (depositReg.getDepositBranch().startsWith("D")) {
				branchSellReg.setSellType(SellType.DEPT.getValue());
			} else {
				branchSellReg.setSellType(SellType.BRANCH.getValue());
			}
			this.cardRiskService.activateSell(branchSellReg);
			
		}
		// 扣减操作员额度
		this.cardRiskService.deductUserAmt(depositReg.getUpdateUser(), 
				depositReg.getDepositBranch(), AmountUtil.subtract(new BigDecimal(0.0), depositReg.getRealAmt()));
		
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(depositReg.getDepositBatchId());	// 售卡登记薄的ID
		cardRiskReg.setAdjType(AdjType.MANAGE.getValue());
		cardRiskReg.setAmt(AmountUtil.subtract(new BigDecimal(0.0), riskAmt));
		cardRiskReg.setBranchCode(cardInfo.getCardIssuer());	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
		this.cardRiskService.activateCardRisk(cardRiskReg);
		
	}

}
