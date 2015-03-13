package gnete.card.msg.adapter;

import gnete.card.dao.UnLossCardRegDAO;
import gnete.card.entity.UnLossCardReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**  
 * Filename:    Msg2005Adapter.java  
 * Description: 解挂报文后台返回处理适配器
 * Copyright:   Copyright (c)2010
 * Company:     深圳雁联计算系统有限公司 
 * @author:     aps-zsg  
 * @version:    V1.0  
 * Create at:   2010-9-25 上午11:51:46  
 */
@Repository
public class Msg2005Adapter implements MsgAdapter {

	@Autowired
	private UnLossCardRegDAO unlossDAO;
	
	static Logger logger =Logger.getLogger(Msg2005Adapter.class);
	
	public void deal(Long id, boolean isSuccess) {
		UnLossCardReg unlossReg = (UnLossCardReg) this.unlossDAO.findByPk(id);
		
		// 处理成功
		if (isSuccess){
			logger.debug("解挂报文后台返回成功！");
			unlossReg.setStatus(RegisterState.NORMAL.getValue());
		} else{
			logger.debug("解挂报文后台返回失败！");
			unlossReg.setStatus(RegisterState.DISABLE.getValue());
		}
		this.unlossDAO.update(unlossReg);

	}

}
