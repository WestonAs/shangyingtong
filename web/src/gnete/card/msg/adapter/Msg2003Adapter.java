package gnete.card.msg.adapter;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.DepositBatRegDAO;
import gnete.card.dao.DepositRegDAO;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.DepositBatReg;
import gnete.card.entity.DepositReg;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.SellType;
import gnete.card.msg.MsgAdapter;
import gnete.card.msg.MsgType;
import gnete.card.service.CardRiskService;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 充值更改状态
 * @author benyan
 *
 */
@Repository
public class Msg2003Adapter implements MsgAdapter {
	
	@Autowired
	private DepositRegDAO depositRegDAO;
	@Autowired
	private DepositBatRegDAO depositBatRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardRiskService cardRiskService;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;
	
	public void deal(Long id, boolean isSuccess) throws BizException {
//		DepositReg depositReg = (DepositReg) this.depositRegDAO.findByPk(id);
//		CardInfo cardInfo = null;
//		if(depositReg != null && StringUtils.isNotEmpty(depositReg.getCardId())){
//			if (isSuccess) {
//				depositReg.setStatus(RegisterState.NORMAL.getValue());
//				depositReg.setDepositDate(ParaMgr.getInstance().getWorkDate());
//			} else {
//				depositReg.setStatus(RegisterState.DISABLE.getValue());
//				depositReg.setRemark(getNote(id));
//
//				cardInfo = (CardInfo) this.cardInfoDAO.findByPk(depositReg.getCardId());
//				
//				// 次卡需要读取清算金额
//				BigDecimal riskAmt = AmountUtil.add(depositReg.getAmt(), depositReg.getRebateAmt());
//				if (DepositType.TIMES.getValue().equals(depositReg.getDepositType())) {
//					String cardSubClass = cardInfo.getCardSubclass();
//					
//					CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardSubClass);
//					String freqClass = cardSubClassDef.getFrequencyClass();
//					Assert.notEmpty(freqClass, "次数卡无子类型定义[" + freqClass + "]");
//					
//					AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(freqClass);
//					if (accuClassDef.getSettAmt() == null || accuClassDef.getSettAmt().doubleValue() == 0.0) {
//						riskAmt = new BigDecimal(0.0);
//					} else {
//						riskAmt = AmountUtil.multiply(accuClassDef.getSettAmt(), depositReg.getAmt());
//					}
//				}
//				
//				// 将扣掉的风险准备金和配额补回来
//				if (!depositReg.getDepositBranch().equals(cardInfo.getCardIssuer().trim())) {
//					BranchSellReg branchSellReg = new BranchSellReg(); 
//					branchSellReg.setId(depositReg.getDepositBatchId());	// 充值登记薄的ID
//					branchSellReg.setAdjType(AdjType.MANAGE.getValue());
//					branchSellReg.setAmt(depositReg.getRealAmt());
//					branchSellReg.setCardBranch(cardInfo.getCardIssuer());		// 发卡机构
//					branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
//					branchSellReg.setSellBranch(depositReg.getDepositBranch());	// 充值机构
//					if (depositReg.getDepositBranch().startsWith("D")) {
//						branchSellReg.setSellType(SellType.DEPT.getValue());
//					} else {
//						branchSellReg.setSellType(SellType.BRANCH.getValue());
//					}
//					this.cardRiskService.activateSell(branchSellReg);
//					
//				}
//				// 扣减操作员额度
//				this.cardRiskService.deductUserAmt(depositReg.getUpdateUser(), 
//						depositReg.getDepositBranch(), AmountUtil.subtract(new BigDecimal(0.0), depositReg.getRealAmt()));
//				
//				CardRiskReg cardRiskReg = new CardRiskReg();
//				cardRiskReg.setId(depositReg.getDepositBatchId());	// 售卡登记薄的ID
//				cardRiskReg.setAdjType(AdjType.MANAGE.getValue());
//				cardRiskReg.setAmt(riskAmt);
//				cardRiskReg.setBranchCode(cardInfo.getCardIssuer());	// 发卡机构
//				cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
//				this.cardRiskService.activateCardRisk(cardRiskReg);
//				
//			}
//			this.depositRegDAO.update(depositReg);
//		}else{
//			// 批量充值登记簿
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("depositBatchId", id);
//			List<DepositBatReg> depositBatRegList = this.depositBatRegDAO.findDepositBatReg(params);
//			if (depositBatRegList != null) {
//				int count = depositBatRegList.size();
//				depositReg = (DepositReg) this.depositRegDAO.findByPk(depositReg.getDepositBatchId());
//				if (isSuccess) {
//					depositReg.setStatus(RegisterState.NORMAL.getValue());
//					depositReg.setDepositDate(ParaMgr.getInstance().getWorkDate());
//					depositReg.setRemark(getNote(id));
//					
//					for(int i = 0; i < count; i++){			
//						// 批量售卡登记簿
//						DepositBatReg depositBatReg = (DepositBatReg)depositBatRegList.get(i);
//						depositBatReg.setStatus(RegisterState.NORMAL.getValue());
//						this.depositBatRegDAO.update(depositBatReg);
//					}
//				} else {
//					depositReg.setStatus(RegisterState.DISABLE.getValue());
//					BigDecimal total = new BigDecimal(0.0);
//					BigDecimal totalReal = new BigDecimal(0.0);
//					
//					for(int i = 0; i < count; i++){
//						// 批量售卡登记簿
//						DepositBatReg depositBatReg = (DepositBatReg)depositBatRegList.get(i);
//						total = AmountUtil.add(total, AmountUtil.add(depositBatReg.getAmt(), depositBatReg.getRebateAmt()));
//						totalReal = AmountUtil.add(totalReal, depositBatReg.getRealAmt());
//						
//						if (i == 0){
//							cardInfo = (CardInfo) this.cardInfoDAO.findByPk(depositBatReg.getCardId());
//						}
//						depositBatReg.setStatus(RegisterState.DISABLE.getValue());
//						this.depositBatRegDAO.update(depositBatReg);
//					}
//					
//					// 把扣掉的风险准备金补回来
//					if (!depositReg.getDepositBranch().equals(cardInfo.getCardIssuer().trim())) {
//						BranchSellReg branchSellReg = new BranchSellReg(); 
//						branchSellReg.setId(depositReg.getDepositBatchId());	// 售卡登记薄的ID
//						branchSellReg.setAdjType(AdjType.MANAGE.getValue());
//						branchSellReg.setAmt(AmountUtil.subtract(new BigDecimal(0.0), totalReal));
//						branchSellReg.setCardBranch(cardInfo.getCardIssuer());		// 发卡机构
//						branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
//						branchSellReg.setSellBranch(depositReg.getDepositBranch());	// 售卡机构
//						if (depositReg.getDepositBranch().startsWith("D")) {
//							branchSellReg.setSellType(SellType.DEPT.getValue());
//						} else {
//							branchSellReg.setSellType(SellType.BRANCH.getValue());
//						}
//						this.cardRiskService.activateSell(branchSellReg);
//
//					}
//					// 扣减操作员额度
//					this.cardRiskService.deductUserAmt(depositReg.getUpdateUser(), 
//							depositReg.getDepositBranch(), AmountUtil.subtract(new BigDecimal(0.0), totalReal));
//					
//					CardRiskReg cardRiskReg = new CardRiskReg();
//					cardRiskReg.setId(depositReg.getDepositBatchId());	// 售卡登记薄的ID
//					cardRiskReg.setAdjType(AdjType.MANAGE.getValue());
//					cardRiskReg.setAmt(AmountUtil.subtract(new BigDecimal(0.0), total));
//					cardRiskReg.setBranchCode(cardInfo.getCardIssuer().trim());	// 发卡机构
//					cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
//					this.cardRiskService.activateCardRisk(cardRiskReg);
//				}
//				this.depositRegDAO.update(depositReg);
//			}
//		}
		_deal(id, isSuccess);
	}
	
	private void _deal(Long id, boolean isSuccess) throws BizException {
		DepositReg depositReg = (DepositReg) this.depositRegDAO.findByPk(id);
		Assert.notNull(depositReg, "充值ID为[" + id + "]的记录不存在");
		CardInfo cardInfo = null;
		// 卡号不为空位单笔充值
		if(StringUtils.isNotEmpty(depositReg.getCardId())){
			if (isSuccess) {
				depositReg.setStatus(RegisterState.NORMAL.getValue());
				depositReg.setDepositDate(SysparamCache.getInstance().getWorkDateNotFromCache());
			} else {
				depositReg.setStatus(RegisterState.DISABLE.getValue());
				depositReg.setRemark(getNote(id));

				cardInfo = (CardInfo) this.cardInfoDAO.findByPk(depositReg.getCardId());
				
				this.dealCardRisk(depositReg, cardInfo);
			}
			this.depositRegDAO.update(depositReg);
		}
		//  否则是批量充值
		else{
			// 批量充值登记簿
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("depositBatchId", id);
			List<DepositBatReg> depositBatRegList = this.depositBatRegDAO.findDepositBatReg(params);
			
			Map<String, Object> batMap = new HashMap<String, Object>();
			if (isSuccess) {
				depositReg.setStatus(RegisterState.NORMAL.getValue());
				depositReg.setDepositDate(SysparamCache.getInstance().getWorkDateNotFromCache());
				
				batMap.put("status", RegisterState.NORMAL.getValue());
			} else {
				depositReg.setStatus(RegisterState.DISABLE.getValue());
				depositReg.setRemark(getNote(id));
				
				batMap.put("status", RegisterState.DISABLE.getValue());
				
				Assert.notEmpty(depositBatRegList, "充值登记ID为[" + depositReg.getDepositBatchId() + "]的充值明细表中无记录");
				cardInfo = (CardInfo) this.cardInfoDAO.findByPk(depositBatRegList.get(0).getCardId());
				Assert.notNull(cardInfo, "卡号[" + depositBatRegList.get(0).getCardId() + "]不存在");
				
				this.dealCardRisk(depositReg, cardInfo);
			}
			batMap.put("depositBatchId", depositReg.getDepositBatchId());
			
			this.depositBatRegDAO.updateStatusByBatchId(batMap);
			this.depositRegDAO.update(depositReg);
		}
	}
	
	/**
	 * 把扣减风险准备金和操作员配额补回来
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
				riskAmt = BigDecimal.ZERO;
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
			branchSellReg.setAmt(AmountUtil.subtract(BigDecimal.ZERO, riskAmt));
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
		this.cardRiskService.deductUserAmt(depositReg.getEntryUserId(), depositReg.getDepositBranch(), 
				AmountUtil.subtract(BigDecimal.ZERO, riskAmt));
		
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(depositReg.getDepositBatchId());	// 售卡登记薄的ID
		cardRiskReg.setAdjType(AdjType.MANAGE.getValue());
		cardRiskReg.setAmt(AmountUtil.subtract(BigDecimal.ZERO, riskAmt));
		cardRiskReg.setBranchCode(cardInfo.getCardIssuer().trim());	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
		this.cardRiskService.activateCardRisk(cardRiskReg);
	}
	
	private String getNote(Long id) throws BizException {
		Waitsinfo waitsinfo = this.waitsinfoDAO.findWaitsinfo(MsgType.DEPOSIT, id);
		return waitsinfo == null ? "" : waitsinfo.getNote();
	}
}
