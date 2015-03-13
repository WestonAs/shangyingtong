package gnete.card.msg.adapter;

import gnete.card.dao.AddMagRegDAO;
import gnete.card.entity.AddMagReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**  
 * Filename:    Msg2006Adapter.java  
 * Description: 加磁报文后台返回处理适配器
 * Copyright:   Copyright (c)2010
 * Company:     深圳雁联计算系统有限公司 
 * @author:     aps-zsg  
 * @version:    V1.0  
 * Create at:   2010-9-25 上午09:23:36  
 */
@Repository
public class Msg2006Adapter implements MsgAdapter {
	
	@Autowired
	private AddMagRegDAO addMagRegDAO;

	/* (non-Javadoc)
	 * @see gnete.card.msg.MsgAdapter#deal(java.lang.Long, boolean)
	 */
	public void deal(Long id, boolean isSuccess) {
		AddMagReg addmag = (AddMagReg) this.addMagRegDAO.findByPk(id);
		
		// 处理成功
		if (isSuccess){
			addmag.setStatus(RegisterState.NORMAL.getValue());
		} else {
			addmag.setStatus(RegisterState.DISABLE.getValue());
		}
		this.addMagRegDAO.update(addmag);
	}

}
