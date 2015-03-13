package gnete.card.msg.adapter;

import gnete.card.msg.MsgAdapter;
import gnete.etc.BizException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @description: 银行卡绑定/解绑/默认卡后台命令返回处理
 */
@Repository
public class Msg9003Adapter implements MsgAdapter {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void deal(Long id, boolean isSuccess) throws BizException {
		//银行卡绑定/解绑/默认卡登记，不需要更新登记簿记录的状态，因为后台处理程序已经更新登记簿的状态了。
		if (isSuccess) {
			logger.debug("银行卡绑定/解绑/默认卡登记[{}]处理成功", id);
		} else {
			logger.debug("银行卡绑定/解绑/默认卡登记[{}]处理失败", id);
		}
	}

	
}
