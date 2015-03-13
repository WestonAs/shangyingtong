package gnete.card.msg.adapter;

import java.util.HashMap;
import java.util.Map;

import gnete.card.dao.PointChgRegDAO;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.PointChgReg;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgAdapter;
import gnete.card.msg.MsgType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**  
 * Filename:    Msg2015Adapter.java  
 * Description: 积分调整报文后台返回处理适配器  
 * Copyright:   Copyright (c)2010
 * Company:     深圳雁联计算系统有限公司 
 * @author:     aps-mjn  
 * @version:    V1.0  
 * Create at:   2010-9-27 下午03:35:14  
 */

@Repository
public class Msg2015Adapter implements MsgAdapter {

	@Autowired
	private PointChgRegDAO pointChgRegDAO;
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;
	
	public void deal(Long id, boolean isSuccess) {
		
		PointChgReg pointChgReg = (PointChgReg) this.pointChgRegDAO.findByPk(id);
		
		// 处理成功
		if (isSuccess){
			pointChgReg.setStatus(RegisterState.NORMAL.getValue());
		} else{
			pointChgReg.setStatus(RegisterState.DISABLE.getValue());
			
			Waitsinfo waitsinfo = this.waitsinfoDAO.findWaitsinfo(MsgType.POINT_CHG, id);
			pointChgReg.setRemark(waitsinfo.getNote());
		}
		this.pointChgRegDAO.update(pointChgReg);

	}

}
