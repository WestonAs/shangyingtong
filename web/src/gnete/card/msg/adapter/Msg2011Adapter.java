package gnete.card.msg.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gnete.card.dao.FreezeRegDAO;
import gnete.card.entity.FreezeReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;

/**  
 * Filename:    Msg2011Adapter.java  
 * Description: 冻结报文后台返回处理适配器  
 * Copyright:   Copyright (c)2010
 * Company:     深圳雁联计算系统有限公司 
 * @author:     aps-zsg  
 * @version:    V1.0  
 * Create at:   2010-9-20 下午03:35:14  
 */

@Repository
public class Msg2011Adapter implements MsgAdapter {

	@Autowired
	private FreezeRegDAO freezeRegDAO;
	
	public void deal(Long id, boolean isSuccess) {
		
		FreezeReg freeze = (FreezeReg) this.freezeRegDAO.findByPk(id);
		
		// 处理成功
		if (isSuccess){
			freeze.setStatus(RegisterState.NORMAL.getValue());
		} else{
			freeze.setStatus(RegisterState.DISABLE.getValue());
		}
		this.freezeRegDAO.update(freeze);

	}

}
