package gnete.card.entity.state;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 短信登记簿状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-4-15
 */
public class MessageRegStatus  extends AbstractState{
	
	public static final Map ALL = new HashMap();

	public static final MessageRegStatus WAITE_SENT = new MessageRegStatus("待发送", "01");
	public static final MessageRegStatus SUCCESS_SENT = new MessageRegStatus("发送成功 ", "02");	
	public static final MessageRegStatus FAILE_SENT = new MessageRegStatus("发送失败", "03");

	protected MessageRegStatus(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static MessageRegStatus valueOf(String value) {
		MessageRegStatus type = (MessageRegStatus) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}
		
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(MessageRegStatus.ALL);
	}
}
