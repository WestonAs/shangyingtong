package gnete.card.msg.adapter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import gnete.card.dao.TrailBalanceRegDAO;
import gnete.card.entity.TrailBalanceReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;
import gnete.etc.BizException;

@Repository
public class Msg2026Adapter implements MsgAdapter{

	@Autowired
	private TrailBalanceRegDAO trailBalanceRegDAO;
	
	static Logger logger = Logger.getLogger(Msg2026Adapter.class);
	
	public void deal(Long id, boolean isSuccess) throws BizException {
		TrailBalanceReg app = (TrailBalanceReg) this.trailBalanceRegDAO.findByPk(id);
		if (isSuccess) {
			logger.debug("试算平衡处理成功，将试算平衡登记簿中的状态改为成功。");
			app.setStatus(RegisterState.NORMAL.getValue());
		} else {
			logger.debug("试算平衡处理失败，将试算平衡登记簿中的状态改为失败。");
			app.setStatus(RegisterState.DISABLE.getValue());
		}
		this.trailBalanceRegDAO.update(app);		
	}

}
