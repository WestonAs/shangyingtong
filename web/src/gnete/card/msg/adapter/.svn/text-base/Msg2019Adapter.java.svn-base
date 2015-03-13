package gnete.card.msg.adapter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import gnete.card.dao.CardRiskRegDAO;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;
/**  
 * 准备金调整报文后台返回处理适配器  
 * @author:     aps-lib  
 */
@Repository
public class Msg2019Adapter implements MsgAdapter{

	@Autowired
	private CardRiskRegDAO cardRiskRegDAO;
	static Logger logger = Logger.getLogger(Msg2017Adapter.class);
	
	public void deal(Long id, boolean isSuccess) {
		CardRiskReg cardRiskReg = (CardRiskReg) this.cardRiskRegDAO.findByPk(id);
		// 处理成功
		if (isSuccess) {
			logger.debug("准备金调整成功，将发卡机构风险准备金调整登记薄中的状态改为成功。");
			cardRiskReg.setStatus(RegisterState.NORMAL.getValue());
		} else {
			logger.debug("准备金调整成功，将发卡机构风险准备金调整登记薄中的状态改为失败。");
			cardRiskReg.setStatus(RegisterState.DISABLE.getValue());
		}
		this.cardRiskRegDAO.update(cardRiskReg);
		
	}

}
