package gnete.card.msg.adapter;

import gnete.card.dao.MembImportRegDAO;
import gnete.card.entity.MembImportReg;
import gnete.card.entity.state.ExternalCardImportState;
import gnete.card.msg.MsgAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: Msg4003Adapter.java
 * 
 * @description: 会员注册文件导入后台命令返回处理
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-8-29
 */
@Repository
public class Msg4003Adapter implements MsgAdapter {

	@Autowired
	private MembImportRegDAO membImportRegDAO;

	static Logger logger = Logger.getLogger(Msg4003Adapter.class);

	public void deal(Long id, boolean isSuccess) throws BizException {
		MembImportReg reg = (MembImportReg) membImportRegDAO.findByPk(id);
		Assert.notNull(reg, "找不到该会员注册文件导入记录");

		if (isSuccess) {
			logger.debug("会员注册文件导入成功");
			reg.setStatus(ExternalCardImportState.DEAL_SUCCESS.getValue());
		} else {
			logger.debug("会员注册文件导入失败");
			reg.setStatus(ExternalCardImportState.DEAL_FAILED.getValue());
		}
		membImportRegDAO.update(reg);
	}

}
