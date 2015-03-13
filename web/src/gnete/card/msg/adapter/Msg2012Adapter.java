package gnete.card.msg.adapter;

import gnete.card.dao.UnfreezeRegDAO;
import gnete.card.entity.UnfreezeReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**  
 * Filename:    Msg2012Adapter.java  
 * Description: 解付报文后台返回处理适配器
 * Copyright:   Copyright (c)2010
 * Company:     深圳雁联计算系统有限公司 
 * @author:     aps-zsg  
 * @version:    V1.0  
 * Create at:   2010-9-21 下午03:07:09  
 */
@Repository
public class Msg2012Adapter implements MsgAdapter {

	@Autowired
	private UnfreezeRegDAO unfreezeRegDAO;
	/* (non-Javadoc)
	 * @see gnete.card.msg.MsgAdapter#deal(java.lang.Long, boolean)
	 */
	public void deal(Long id, boolean isSuccess) {
		UnfreezeReg unfreezeReg = (UnfreezeReg) this.unfreezeRegDAO.findByPk(id);
		
		// 处理成功
		if (isSuccess){
			unfreezeReg.setStatus(RegisterState.NORMAL.getValue());
		} else{
			unfreezeReg.setStatus(RegisterState.DISABLE.getValue());
		}
		this.unfreezeRegDAO.update(unfreezeReg);

	}

}
