package gnete.card.service.impl;

import gnete.card.dao.AddMagRegDAO;
import gnete.card.entity.AddMagReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.AddMagService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**  
 * Filename:    AddMagServiceImpl.java  
 * Description:   
 * Copyright:   Copyright (c)2010
 * Company:     深圳雁联计算系统有限公司 
 * @author:     aps-zsg  
 * @version:    V1.0  
 * Create at:   2010-8-4 上午12:13:09  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2010-8-4    aps-zsg        1.0        1.0 Version  
 */

@Service("AddMagService")
public class AddMagServiceImpl implements AddMagService {
	
	@Autowired
	private AddMagRegDAO addMagRegDAO;

	public Long addAddMag(AddMagReg addMagReg, String updateUserId) throws BizException {
		Assert.notNull(addMagReg, "添加的补磁对象不能为空");
		
		addMagReg.setStatus(RegisterState.WAITEDEAL.getValue());//默认待处理
		addMagReg.setUpdateTime(new Date());
		addMagReg.setUpdateUser(updateUserId);
						
		this.addMagRegDAO.insert(addMagReg);
		
		// 发报文到后台
		return MsgSender.sendMsg(MsgType.ADD_MAG, addMagReg.getAddMagId(),
				addMagReg.getUpdateUser());
	}

	/* (non-Javadoc)
	 * @see gnete.card.service.AddMagService#modifyAddMag(long)
	 */
	public boolean modifyAddMag(long addMagID) throws BizException {
		// TODO Auto-generated method stub
		return false;
	}

}
