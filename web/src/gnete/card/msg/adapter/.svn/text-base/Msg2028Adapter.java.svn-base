package gnete.card.msg.adapter;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.SaleCardBatRegDAO;
import gnete.card.dao.SaleCardRegDAO;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.SaleCardBatReg;
import gnete.card.entity.SaleCardReg;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.SellType;
import gnete.card.msg.MsgAdapter;
import gnete.card.msg.MsgType;
import gnete.card.service.CardRiskService;
import gnete.card.service.SaleCardRegService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: Msg2028Adapter.java
 *
 * @description: 售卡撤销报文后台返回处理适配器
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2011-11-24
 */
@Repository
public class Msg2028Adapter implements MsgAdapter{
	
	@Autowired
	private SaleCardRegDAO saleCardRegDAO;
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;
	@Autowired
	private SaleCardBatRegDAO saleCardBatRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardRiskService cardRiskService;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	@Autowired
	private SaleCardRegService saleCardRegService;

	static Logger logger = Logger.getLogger(Msg2028Adapter.class);
	
	public void deal(Long id, boolean isSuccess) throws BizException {
		SaleCardReg saleCardReg = (SaleCardReg) saleCardRegDAO.findByPk(id);
		Assert.notNull(saleCardReg, "找不到该售卡撤销[" + id + "]记录");
		
		SaleCardReg oldReg = (SaleCardReg) saleCardRegDAO.findByPk(saleCardReg.getOldSaleBatch());
		Assert.notNull(oldReg, "找不到原售卡[" + saleCardReg.getOldSaleBatch() + "]记录");
		if(saleCardReg != null && saleCardReg.isSingleCardSale()){
			logger.debug("单张卡售卡撤销");
			// 单张卡售卡
			if (isSuccess) {
				logger.debug("单张卡售卡撤销处理成功，将售卡登记簿中的状态改为成功，修改原售卡记录为已撤销，将扣的风险保证金返还。");
				saleCardReg.setStatus(RegisterState.NORMAL.getValue());
				
				oldReg.setStatus(RegisterState.CANCELED.getValue());
				oldReg.setCancelFlag(Symbol.YES); // 已撤销
				
				addDeductUserAmt(oldReg);
			} else {
				logger.debug("售卡撤销处理失败，将售卡登记簿中的状态改为失败，原售卡纪录可以再次被撤销。");
				saleCardReg.setStatus(RegisterState.DISABLE.getValue());
				saleCardReg.setRemark(getNote(id));
				oldReg.setStatus(RegisterState.NORMAL.getValue());// 修改原纪录为成功状态，可再次撤销
			}
		}
		// 批量售卡
		else {
			logger.debug("批量售卡撤销");
			// 批量售卡登记簿
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("saleBatchId", saleCardReg.getOldSaleBatch());
			List<SaleCardBatReg> saleCardBatRegList = this.saleCardBatRegDAO.findSaleCardBatReg(params);
			if (isSuccess) {
				logger.debug("售卡撤销处理成功，将售卡登记簿中的状态改为成功，修改原售卡记录为已撤销，将扣的风险保证金返还。");
				saleCardReg.setStatus(RegisterState.NORMAL.getValue());
				
				List<SaleCardBatReg> list = new ArrayList<SaleCardBatReg>();
				for (SaleCardBatReg batReg : saleCardBatRegList) {
					batReg.setStatus(RegisterState.CANCELED.getValue());
					
					list.add(batReg);
				}
				this.saleCardBatRegDAO.updateBatch(list);
				
				oldReg.setStatus(RegisterState.CANCELED.getValue());
				oldReg.setCancelFlag(Symbol.YES); // 已撤销
				
				addDeductUserAmt(saleCardReg);
			} else {
				logger.debug("售卡撤销处理失败，将售卡登记簿中的状态改为失败。原记录修改为成功状态，可再次撤销。");
				saleCardReg.setStatus(RegisterState.DISABLE.getValue());
				saleCardReg.setRemark(getNote(id));
				
				oldReg.setStatus(RegisterState.NORMAL.getValue());// 修改原纪录为成功状态，可再次撤销
				
				List<SaleCardBatReg> list = new ArrayList<SaleCardBatReg>();
				for (SaleCardBatReg batReg : saleCardBatRegList) {
					batReg.setStatus(RegisterState.NORMAL.getValue());
					
					list.add(batReg);
				}
				this.saleCardBatRegDAO.updateBatch(list);
			}
		}

		saleCardRegDAO.update(saleCardReg);
		saleCardRegDAO.update(oldReg);
		// 售卡撤销成功时的操作
		if (isSuccess) {
			this.saleCardRegService.updateExtraInfoForSuccess(saleCardReg);
		}
	}
	
	/**
	 * 将扣掉的风险保障金和配额补回来
	 * 
	 * @param saleCardReg 售卡登记薄
	 * @throws BizException
	 */
	private void addDeductUserAmt(SaleCardReg saleCardReg) throws BizException {
		CardInfo cardInfo = new CardInfo();
		if (saleCardReg.isSingleCardSale()) {
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(saleCardReg.getCardId());
		} else {
			// 批量售卡登记簿
			Map<String, Object> params = new HashMap<String, Object>();
			Assert.notNull(saleCardReg.getOldSaleBatch(), "原售卡批次号不能为空");
			params.put("saleBatchId", saleCardReg.getOldSaleBatch());
			List<SaleCardBatReg> saleCardBatRegList = this.saleCardBatRegDAO.findSaleCardBatReg(params);
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(saleCardBatRegList.get(0).getCardId());
		}
		
		// 次卡需要读取清算金额
		BigDecimal riskAmt = AmountUtil.add(saleCardReg.getAmt(), saleCardReg.getRebateAmt());
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
		
		// 将扣掉的风险准备金和配额补回来 //发卡机构自己不做限制
		if (!saleCardReg.getBranchCode().equals(cardInfo.getCardIssuer().trim())) {
			BranchSellReg branchSellReg = new BranchSellReg(); 
			branchSellReg.setId(saleCardReg.getSaleBatchId());	// 售卡登记薄的ID
			branchSellReg.setAdjType(AdjType.SELL.getValue());
			branchSellReg.setAmt(AmountUtil.subtract(new BigDecimal(0.0), saleCardReg.getRealAmt()));
			branchSellReg.setCardBranch(cardInfo.getCardIssuer());		// 发卡机构
			branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
			branchSellReg.setSellBranch(saleCardReg.getBranchCode());	// 售卡机构
			if (saleCardReg.getBranchCode().startsWith("D")) {
				branchSellReg.setSellType(SellType.DEPT.getValue());
			} else {
				branchSellReg.setSellType(SellType.BRANCH.getValue());
			}
			this.cardRiskService.activateSell(branchSellReg);
			
		}
		// 扣减操作员额度
		this.cardRiskService.deductUserAmt(saleCardReg.getUpdateUser(), 
				saleCardReg.getBranchCode(), AmountUtil.subtract(new BigDecimal(0.0), saleCardReg.getRealAmt()));
		
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(saleCardReg.getSaleBatchId());	// 售卡登记薄的ID
		cardRiskReg.setAdjType(AdjType.SELL.getValue());
		cardRiskReg.setAmt(AmountUtil.subtract(new BigDecimal(0.0), riskAmt));
		cardRiskReg.setBranchCode(cardInfo.getCardIssuer());	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
		this.cardRiskService.activateCardRisk(cardRiskReg);
		
	}

	private String getNote(Long id) throws BizException {
		Waitsinfo waitsinfo = this.waitsinfoDAO.findWaitsinfo(MsgType.SELL_CARD_CANCEL, id);
		return waitsinfo == null ? "" : waitsinfo.getNote();
	}
}
