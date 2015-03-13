package gnete.card.timer.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flink.timer.FlinkTimerTask;
import flink.timer.IntervalEnum;
import flink.util.CommonHelper;
import gnete.card.service.GenerateReportService;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.BizException;

/**
 * <p>月报表定时任务</p>
 */
@Service("reportGenerateMonTimerTask")
public class ReportGenerateMonTimerTask extends FlinkTimerTask {
	@Autowired
	private GenerateReportService generateReportService;

	/**
	
	//测试用
	@Override
	protected boolean isPerformedOnceAtStartup() throws BizException{
		return true;
	}

	//测试用
	@Override
	protected boolean isLocalServerExecutable(){
		return true; 
	}
	 */
	
	@Override
	protected IntervalEnum getIntervalEnum() {
		return IntervalEnum.MONTH;
	}

	@Override
	protected String getTaskDescribe() {
		return "定时产生月报表";
	}
	
	@Override
	protected long getCheckScheduleSleepTime() {
		return 5 * 6 * DEFAULT_SLEEP_TIME; // 5 分钟
	}

	/**
	 * <p>选择每个月1号某时间点执行</p>
	 */
	@Override
	protected Date getTaskFirstTime() throws Exception {
		Date monthBegin = CommonHelper.getMonthBegin(CommonHelper.getCalendarDate());
		String reportTime = SysparamCache.getInstance().getGernerateReportTime();
		return CommonHelper.getFormatDateTime(monthBegin, reportTime);
	}

	@Override
	protected void processTask() throws BizException {
		logger.info("[{}]定时任务开始执行", getTaskDescribe());
		try {
			String preWorkDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
			String preWorkMonth = preWorkDate.substring(0, 6); // 取得要统计的月份
			generateReportService.generateMonthReport(preWorkMonth);
		} catch (BizException e) {
			logger.error("[" + getTaskDescribe() + "]失败", e.getMessage());
		}
		logger.info("[{}]定时任务执行完毕", getTaskDescribe());
	}

}
