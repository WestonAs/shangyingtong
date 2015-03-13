package gnete.card.msg.adapter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import gnete.card.dao.PointExcCouponRegDAO;
import gnete.card.entity.PointExcCouponReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;
import gnete.etc.BizException;

@Repository
public class Msg2024Adapter implements MsgAdapter{

	@Autowired
	private PointExcCouponRegDAO pointExcCouponRegDAO;
	
	static Logger logger = Logger.getLogger(Msg2024Adapter.class);
	
	public void deal(Long id, boolean isSuccess) throws BizException {
		PointExcCouponReg app = (PointExcCouponReg) this.pointExcCouponRegDAO.findByPk(id);
		// 处理成功
		if (isSuccess) {
			logger.debug("积分兑换赠券处理成功，将积分兑换赠券登记簿中的状态改为成功。");
			app.setStatus(RegisterState.NORMAL.getValue());
		} else {
			logger.debug("积分兑换赠券处理失败，将积分兑换赠券登记簿中的状态改为失败。");
			app.setStatus(RegisterState.DISABLE.getValue());
		}
		this.pointExcCouponRegDAO.update(app);
		
	}

}
