package gnete.card.msg;

import gnete.etc.BizException;


/**
 * 消息后续处理接口，通过适配器方式接入到处理程序中
 * 
 * @author aps-lih
 */
public interface MsgAdapter {

	/**
	 * 消息的后续处理
	 * 
	 * @param id 登记薄ID
	 * @param isSuccess 处理成功true，失败false
	 * 
	 * @return
	 */
	public void deal(Long id, boolean isSuccess) throws BizException;
	
}
