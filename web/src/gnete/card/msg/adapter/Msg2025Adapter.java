package gnete.card.msg.adapter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import gnete.card.dao.PasswordResetRegDAO;
import gnete.card.entity.PasswordResetReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;
import gnete.etc.BizException;

@Repository
public class Msg2025Adapter implements MsgAdapter{

	@Autowired
	private PasswordResetRegDAO passwordResetRegDAO;
	
	static Logger logger = Logger.getLogger(Msg2025Adapter.class);
	
	public void deal(Long id, boolean isSuccess) throws BizException {
		PasswordResetReg app = (PasswordResetReg) this.passwordResetRegDAO.findByPk(id);
		// 处理成功
		if (isSuccess) {
			logger.debug("密码重置处理成功，将密码重置登记簿中的状态改为成功。");
			app.setStatus(RegisterState.NORMAL.getValue());
		} else {
			logger.debug("密码重置处理失败，将密码重置登记簿中的状态改为失败。");
			app.setStatus(RegisterState.DISABLE.getValue());
		}
		this.passwordResetRegDAO.update(app);
		
	}

}
