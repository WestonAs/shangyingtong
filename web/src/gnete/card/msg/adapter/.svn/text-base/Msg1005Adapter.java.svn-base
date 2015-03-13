package gnete.card.msg.adapter;

import gnete.card.dao.AppRegDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.entity.AppReg;
import gnete.card.entity.state.CardStockState;
import gnete.card.msg.MsgAdapter;
import gnete.card.util.CardUtil;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: Msg1005Adapter.java
 * 
 * @description: 卡片返库报文返回后续处理
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-9-20
 */
@Repository
public class Msg1005Adapter implements MsgAdapter {

	@Autowired
	private AppRegDAO appRegDAO;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;

	static Logger logger = Logger.getLogger(Msg1005Adapter.class);

	public void deal(Long id, boolean isSuccess) {
		logger.debug("进入卡片返库报文返回适配器处理......");
		if (isSuccess) {
			AppReg appReg = (AppReg) appRegDAO.findByPk(id);
			// 更新卡库存信息表
			String strNo = null;
			int cardNum = 0;
			if (StringUtils.isNotEmpty(appReg.getCheckStrNo())) {
				strNo = appReg.getCheckStrNo();
				cardNum = appReg.getCheckCardNum().intValue();
			} else {
				strNo = appReg.getStrNo();
				cardNum = appReg.getCardNum().intValue();
			}
			logger.debug("卡片返库成功！");
			//  再更新批准的起始卡号
			Map<String, Object> params2 = new HashMap<String, Object>();
			params2.put("cardStatus", CardStockState.IN_STOCK.getValue());
			params2.put("appRegId", "");
			params2.put("appOrgId", "");
			params2.put("appDate", "");
			params2.put("cardSectorId", "");
			params2.put("strCardId", strNo);
			params2.put("endCardId", CardUtil.getMaxEndCardId(strNo, cardNum));
			params2.put("cardStatusLimit", CardStockState.RECEIVED.getValue());
			cardStockInfoDAO.updateStockBatch(params2);
			
		} else {
			logger.debug("卡片返库失败！");
		}
		
		logger.debug("卡片返库报文返回处理适配器处理结束......");
	}

}
