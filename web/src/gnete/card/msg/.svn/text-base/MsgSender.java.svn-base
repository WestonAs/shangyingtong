package gnete.card.msg;

import flink.util.SpringContext;
import gnete.card.service.WaitsinfoService;
import gnete.etc.BizException;

import org.apache.log4j.Logger;

public class MsgSender {
	
	static Logger logger = Logger.getLogger(MsgSender.class);
	
	/**
	 * 发送信息（即插入一条waitsinfo记录）
	 * @param msgType
	 * @param refId 登记薄ID
	 * @param userCode
	 * @return
	 * @throws BizException
	 */
	public static Long sendMsg(String msgType, Long refId, String userCode) throws BizException {
		WaitsinfoService waitsinfoService = (WaitsinfoService) SpringContext.getService("waitsinfoService");
		return waitsinfoService.addCmd(msgType, refId, userCode);
	}
	
	/**
	 * 重新发送信息（即：先删除msgType，refId指定的一条waitsinfo记录，再新加一条相应的记录）
	 * @param msgType
	 * @param refId 登记薄ID
	 * @param userCode
	 * @return
	 * @throws BizException
	 */
	public static Long reSendMsg(String msgType, Long refId, String userCode) throws BizException {
		WaitsinfoService waitsinfoService = (WaitsinfoService) SpringContext.getService("waitsinfoService");
		return waitsinfoService.reAddCmd(msgType, refId, userCode);
	}
	
}
