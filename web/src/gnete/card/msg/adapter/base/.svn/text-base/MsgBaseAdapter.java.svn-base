package gnete.card.msg.adapter.base;

import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.Waitsinfo;
import gnete.card.msg.MsgAdapter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 与后台command命令交互（waitsinfo表）基础adapter
 */
@Repository
public abstract class MsgBaseAdapter implements MsgAdapter {
	@Autowired
	public WaitsinfoDAO waitsinfoDAO;
	
	protected String getWaitsinfoNote(String msgType, Long refId) {
		Waitsinfo waitsinfo = this.waitsinfoDAO.findWaitsinfo(msgType, refId);
		return waitsinfo == null ? "" : "[后台日志："+StringUtils.trimToEmpty(waitsinfo.getNote())+"]";
	}
}
