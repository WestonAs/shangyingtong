package gnete.card.timer;

import gnete.card.service.GenerateReportService;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.BizException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: EveryMonthGenerateReportHandler.java
 *
 * @description: 每个月定时生成月统计报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-3-1
 */
public class EveryMonthGenerateReportHandler extends EveryMonthTimerHandler {
	
	static final Logger logger = Logger.getLogger(EveryMonthGenerateReportHandler.class);
	
	@Autowired
	private GenerateReportService generateReportService;

	@Override
	protected String executeTime() {
		return SysparamCache.getInstance().getGernerateReportTime();
	}

	@Override
	protected void doTask() throws BizException {
		logger.debug("按月统计报表生成定时器开始执行");
		try {
			String preWorkDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
			String preWorkMonth = preWorkDate.substring(0, 6); // 取得要统计的月份
			generateReportService.generateMonthReport(preWorkMonth);
			logger.warn("生成月统计报表成功！");
		} catch (BizException e) {
			logger.error("月统计报表生成失败，" + e.getMessage());
		}
		logger.debug("按月统计报表生成定时器结束执行");
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}
