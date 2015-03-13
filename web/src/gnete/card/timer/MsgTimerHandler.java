package gnete.card.timer;

import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.Waitsinfo;
import gnete.card.service.WaitsinfoService;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.BizException;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: MsgTimerHandler.java
 *
 * @description: 定时消息定时器
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: LiHeng
 * @version: 1.0
 * @since 1.0 2011-8-19
 */
public class MsgTimerHandler extends IntervalTimerHandler {
	private static final Logger logger = Logger.getLogger(MsgTimerHandler.class);

	@Autowired
	private WaitsinfoService waitsinfoService;
	
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;
	
	protected synchronized void doTask() throws BizException {
		// 定时器执行
		logger.debug("消息定时器开始执行");
		try {
			List<Waitsinfo> list = this.waitsinfoDAO.findUndoForWeb();
			
			if (CollectionUtils.isNotEmpty(list)) {
				for (Waitsinfo waitsinfo : list) {
					try {
						this.waitsinfoService.dealWeb(waitsinfo);
					} catch (Exception e) {
						logger.error("执行该命令[消息类型："+ waitsinfo.getMsgType() + ", 登记薄ID：" 
								+ waitsinfo.getRefId() +"]时出现异常:" + e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.debug("消息定时器结束执行");
	}

	protected Logger getLogger() {
		return logger;
	}
	
	protected int getInterval() {
		return SysparamCache.getInstance().getMsgTimerInterval();
	}

}
