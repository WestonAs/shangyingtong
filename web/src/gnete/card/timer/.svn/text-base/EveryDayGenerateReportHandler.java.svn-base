package gnete.card.timer;

import gnete.card.service.GenerateReportService;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.BizException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: EveryDayGenerateReportHandler.java
 *
 * @description: 每天定时生成报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-3-1
 */
public class EveryDayGenerateReportHandler extends EveryDayTimerHandler {
	
	static final Logger logger = Logger.getLogger(EveryDayGenerateReportHandler.class);
	
	@Autowired
	private GenerateReportService generateReportService;

	@Override
	protected String executeTime() {
		return SysparamCache.getInstance().getGernerateReportTime();
	}

	@Override
	protected synchronized void doTask() throws BizException {
		logger.debug("=======================日报表生成定时器开始执行===================");
		String preWorkDate = SysparamCache.getInstance().getPreWorkDateNotFromCache(); // 前一工作日的报表。
		try {
			generateReportService.generateDayReport(preWorkDate);
			logger.warn("生成日报表成功！");
		} catch (BizException e) {
			logger.error("日报表生成失败，" + e.getMessage());
		}
		
		try {
			generateReportService.generateOldDayReport(preWorkDate);
			logger.warn("生成旧日报表成功！");
		} catch (BizException e) {
			logger.error("旧日报表生成失败，" + e.getMessage());
		}
		
		logger.debug("====================日报表生成定时器结束执行=======================");
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}
