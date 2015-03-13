package gnete.card.msg.adapter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gnete.card.dao.CardStockInfoDAO;
import gnete.card.dao.SaleSignCardRegDAO;
import gnete.card.entity.CardStockInfo;
import gnete.card.entity.SaleSignCardReg;
import gnete.card.entity.state.CardStockState;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;

/**
 * 签单卡售卡更改状态
 * 
 * @author benyan
 * 
 */
@Repository
public class Msg2002Adapter implements MsgAdapter {

	@Autowired
	private SaleSignCardRegDAO saleSignCardRegDAO;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;

	static Logger logger = Logger.getLogger(Msg2002Adapter.class);

	public void deal(Long id, boolean isSuccess) {
		SaleSignCardReg reg = (SaleSignCardReg) saleSignCardRegDAO.findByPk(id);
		if (reg != null && reg.getCardId().length() > 0) {
			if (isSuccess) {
				logger.debug("签单卡售卡成功。");
				reg.setStatus(RegisterState.NORMAL.getValue());
				CardStockInfo info = cardStockInfoDAO
						.findCardStockInfoByCardId(reg.getCardId());
				info.setCardStatus(CardStockState.SOLD_OUT.getValue());
				cardStockInfoDAO.update(info);
			} else {
				logger.debug("签单卡售卡失败。");
				reg.setStatus(RegisterState.DISABLE.getValue());
			}
			this.saleSignCardRegDAO.update(reg);
		}

		// 扣减发卡机构可用风险准备金额度；扣减售卡机构售卡配额
	}
}
