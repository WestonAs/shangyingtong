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
 *  <p>日报表任务定时器任务</p>
 */
@Service("reportGenerateDayTimerTask")
public class ReportGenerateDayTimerTask extends FlinkTimerTask{
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
		return IntervalEnum.DAY;
	}

	@Override
	protected String getTaskDescribe() {
		return "定时产生日报表";
	}
	
	@Override
	protected long getCheckScheduleSleepTime() {
		return 5 * 6 * DEFAULT_SLEEP_TIME; // 5 分钟
	}

	@Override
	protected Date getTaskFirstTime() throws Exception{
		String executeTime =  SysparamCache.getInstance().getGernerateReportTime();
		return CommonHelper.getFormatDateTime(executeTime);
	}

	@Override
	protected void processTask() throws BizException {
		logger.info("日报表生成定时器开始执行");
		String preWorkDate = SysparamCache.getInstance().getPreWorkDateNotFromCache(); // 前一工作日的报表。
		try {
			generateReportService.generateDayReport(preWorkDate);
			logger.info("生成日报表线程 启动成功！");
		} catch (BizException e) {
			logger.error("日报表生成失败", e);
		}
		
		try {
			generateReportService.generateOldDayReport(preWorkDate);
			logger.info("生成旧日报表成功！");
		} catch (BizException e) {
			logger.error("旧日报表生成失败", e);
		}
		logger.info("日报表生成定时器结束执行");
	}

}
