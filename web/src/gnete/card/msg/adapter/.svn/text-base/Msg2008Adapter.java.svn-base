package gnete.card.msg.adapter;

import gnete.card.dao.AdjAccRegDAO;
import gnete.card.entity.AdjAccReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgType;
import gnete.card.msg.adapter.base.MsgBaseAdapter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**  
 * 调账报文后台返回处理适配器  
 * @author:     aps-lih  
 */
@Repository
public class Msg2008Adapter extends MsgBaseAdapter {

	@Autowired
	private AdjAccRegDAO adjAccRegDAO;
	
	public void deal(Long id, boolean isSuccess) {
		AdjAccReg adjAccReg = (AdjAccReg) this.adjAccRegDAO.findByPk(id);
		// 处理成功
		if (isSuccess){
			adjAccReg.setStatus(RegisterState.NORMAL.getValue());
		} else{
			adjAccReg.setStatus(RegisterState.DISABLE.getValue());
			adjAccReg.setRemark(StringUtils.trimToEmpty(adjAccReg.getRemark())
					+ super.getWaitsinfoNote(MsgType.ADJ_ACC, id));
		}
		this.adjAccRegDAO.update(adjAccReg);
	}

}
