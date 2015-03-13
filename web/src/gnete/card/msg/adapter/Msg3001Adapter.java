package gnete.card.msg.adapter;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.ExternalCardImportRegDAO;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.ExternalCardImportReg;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.state.ExternalCardImportState;
import gnete.card.entity.type.AdjType;
import gnete.card.msg.MsgAdapter;
import gnete.card.msg.MsgType;
import gnete.card.service.CardRiskService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @description: 外部卡导入后台命令返回处理
 */
@Repository
public class Msg3001Adapter implements MsgAdapter {

	@Autowired
	private ExternalCardImportRegDAO externalCardImportRegDAO;
	@Autowired
	private CardRiskService cardRiskService;
	@Autowired
	private WaitsinfoDAO waitsinfoDao;
	
	static Logger logger = Logger.getLogger(Msg3001Adapter.class);

	public void deal(Long id, boolean isSuccess) throws BizException {
		ExternalCardImportReg reg = (ExternalCardImportReg) externalCardImportRegDAO.findByPk(id);
		Assert.notNull(reg, "找不到该外部卡导入记录");

		if (isSuccess) {
			logger.debug("外部卡导入成功");
			reg.setStatus(ExternalCardImportState.DEAL_SUCCESS.getValue());
			externalCardImportRegDAO.update(reg);
		} else {
			logger.info("外部卡导入失败");
			Waitsinfo wi = waitsinfoDao.findWaitsinfo(MsgType.EXTERNAL_CARD_IMPORT, id);
			reg.setMemo(wi.getNote());
			reg.setStatus(ExternalCardImportState.DEAL_FAILED.getValue());
			externalCardImportRegDAO.update(reg);
			
			CardRiskReg cardRiskReg = new CardRiskReg();
			cardRiskReg.setAdjType(AdjType.SELL.getValue());
			cardRiskReg.setAmt(AmountUtil.subtract(BigDecimal.ZERO, reg.getTotalAmt()));
			cardRiskReg.setBranchCode(reg.getCardBranch());	// 发卡机构
			cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
			this.cardRiskService.activateCardRisk(cardRiskReg);
		}
	}

	
}
