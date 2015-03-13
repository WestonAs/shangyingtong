package gnete.card.msg.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gnete.card.dao.LossCardRegDAO;
import gnete.card.entity.LossCardReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;

/**  
 * Filename:    Msg2004Adapter.java  
 * Description: 挂失报文后台返回处理适配器
 * Copyright:   Copyright (c)2010
 * Company:     深圳雁联计算系统有限公司 
 * @author:     aps-zsg  
 * @version:    V1.0  
 * Create at:   2010-9-25 上午11:46:06  
 */
@Repository
public class Msg2004Adapter implements MsgAdapter {

	@Autowired
	private LossCardRegDAO lossDAO ;
	/* (non-Javadoc)
	 * @see gnete.card.msg.MsgAdapter#deal(java.lang.Long, boolean)
	 */
	public void deal(Long id, boolean isSuccess) throws BizException {
		LossCardReg lossReg = (LossCardReg) this.lossDAO.findByPk(id);
		Assert.notNull(lossReg, "找不到该对象。");
		// 处理成功
		if (isSuccess){
			lossReg.setStatus(RegisterState.NORMAL.getValue());
		} else{
			lossReg.setStatus(RegisterState.DISABLE.getValue());
		}
		this.lossDAO.update(lossReg);

	}

}
