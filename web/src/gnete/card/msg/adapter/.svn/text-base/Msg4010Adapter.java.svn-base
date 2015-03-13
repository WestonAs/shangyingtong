package gnete.card.msg.adapter;

import gnete.card.dao.CouponAwardRegDAO;
import gnete.card.entity.CouponAwardReg;
import gnete.card.entity.state.ExternalCardImportState;
import gnete.card.msg.MsgAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: Msg4010Adapter.java
 * 
 * @description: 赠送赠券后台命令返回处理
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-12-22
 */
@Repository
public class Msg4010Adapter implements MsgAdapter {

	@Autowired
	private CouponAwardRegDAO couponAwardRegDAO;

	static Logger logger = Logger.getLogger(Msg4010Adapter.class);

	public void deal(Long id, boolean isSuccess) throws BizException {
		CouponAwardReg reg = (CouponAwardReg) couponAwardRegDAO.findByPk(id);
		Assert.notNull(reg, "找不到该赠送赠券记录");

		if (isSuccess) {
			logger.debug("赠送赠券成功");
			reg.setStatus(ExternalCardImportState.DEAL_SUCCESS.getValue());
		} else {
			logger.debug("赠送赠券失败");
			reg.setStatus(ExternalCardImportState.DEAL_FAILED.getValue());
		}
		couponAwardRegDAO.update(reg);
	}

}
