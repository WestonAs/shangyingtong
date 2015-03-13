package gnete.card.msg;


/**
 * 消息转换接口
 * 
 * @author aps-lih
 */
public interface MsgConverter {

	/**
	 * 从对象转换成消息
	 * 
	 * @param obj
	 * @return
	 */
	public String toMessage(Object obj);
	
}
