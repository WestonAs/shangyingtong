package gnete.card.timer.waitsinfo;


/**
 * @File: IWaitsInfoTaskProcess.java
 *
 * @description: 消息定时器对消息的处理
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-7-13 下午02:50:41
 */
public interface WaitsInfoTaskProcess {
	
	/** 
	 * 对后台处理成功或失败，但还未反馈到前台web的watisinfo表记录，进行适配处理 
	 */
	void dealWeb();

}
