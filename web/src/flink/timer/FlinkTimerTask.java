package flink.timer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flink.util.DateUtil;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.BizException;
import gnete.util.LocalHostIpManager;

/**
 * <p>基于TimerTask以及项目需要的调度任务类</p>
 */
public abstract class FlinkTimerTask extends TimerTask {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**缺省线程睡眠时间 */
	protected final long DEFAULT_SLEEP_TIME = 10 * 1000;

	/**缺省错误次数累计上限*/
	protected final int DEFAULT_ABNORMAL_COUNT = 25;
	
	protected final List<String> errMsgList = new ArrayList<String>(DEFAULT_ABNORMAL_COUNT);

	@Override
	public void run() {
		if (this.isLocalServerExecutable()) {
			if (processTaskBefore()) {
				processExcuteTask();
				processTaskAfter();
			}
		}
	}

	protected boolean processTaskBefore() {
		boolean result = false;
		try {
			result = checkTimerTask();
			if (result) {
				if (StringUtils.equals(getTaskDescribe(), "消息定时器任务")) {
					logger.debug("[{}]任务处理开始，开始时间[{}]",  getTaskDescribe(), DateUtil.formatDate("yyyy-MM-dd HH:mm:ss.SSS"));
				} else {
					logger.info("[{}]任务处理开始，开始时间[{}]",  getTaskDescribe(), DateUtil.formatDate("yyyy-MM-dd HH:mm:ss.SSS"));
				}
			} else {
				logger.warn("任务[{}]参数检查失败", getTaskDescribe());
			}
		} catch (Exception ex) {
			logger.error("任务[{}]参数设置异常,原因[{}]", getTaskDescribe(), ex.toString());
		}
		return result;
	}

	protected void processExcuteTask() {
		try {
			processTask();
		} catch (BizException ex) {
			// 1.3 记录任务异常(如果有需要抛出的话)
			errMsgList.add(ex.getMessage());
			logger.error("====[{}]捕获到应用异常=====[{}]",getTaskDescribe(), ex);
		} catch (Exception ex) {
			// 1.3 记录任务异常(如果有需要抛出的话)
			errMsgList.add(ex.getMessage());
			logger.error("====[{}]捕获到其他异常=====[{}]",getTaskDescribe(), ex);
		}
	}

	protected void processTaskAfter() {
		if (errMsgList.size() > getTaskAbnormalCount()) {
			logger.warn("====调度任务中存在过多的错误，请仔细检查当前执行任务====");
			try {
				processTaskAbnormal(getTaskDescribe(), errMsgList);
			} catch (Exception ex) {
				logger.error("处理记录异常任务失败,原因[{}]", ex);
			} finally {
				// 清空记录再进行计数
				errMsgList.clear();
			}
		}

		if (StringUtils.equals(getTaskDescribe(), "消息定时器任务")) {
			logger.debug("[{}]任务处理结束，结束时间[{}]", getTaskDescribe(), DateUtil.formatDate("yyyy-MM-dd HH:mm:ss.SSS"));
		} else {
			logger.info("[{}]任务处理结束，结束时间[{}]", getTaskDescribe(), DateUtil.formatDate("yyyy-MM-dd HH:mm:ss.SSS"));
		}
	}

	/** 【具体任务处理逻辑】 */
	protected abstract void processTask() throws BizException;

	/** 任务的描述 */
	protected abstract String getTaskDescribe();

	/** 任务开始执行的时间 */
	protected abstract Date getTaskFirstTime() throws Exception;

	/**
	 * <p>任务执行间隔执行的时间 (N个IntervalEnum单位)</p>
	 * @return 时间间隔（毫秒数）
	 */
	protected long getTaskPeriod() throws Exception {
		Date currentDate = new Date();
		int[] params = getIntervalEnum().getIntervalParams();
		Date nextDate = DateUtils.add(currentDate, params[0], params[1] * this.getIntervals());
		return (nextDate.getTime() - currentDate.getTime());
	}
	
	/** 获得间隔的时刻点单位(IntervalEnum单位) */
	protected abstract IntervalEnum getIntervalEnum();
	
	
	/** 任务执行间隔的时刻点单位的数量N（即N个IntervalEnum）（默认1） */
	protected int getIntervals() {
		return 1;
	}
	
	/** 是否在线程启动时执行一次任务(缺省默认首次不执行) */
	protected boolean isPerformedOnceAtStartup() throws BizException{
		return false;
	}

	/** 任务是否是一定的频率执行(缺省为true) */
	protected boolean isTaskFixRate() {
		return true;
	}

	/** 是否是一次性任务(默认应为false) */
	protected boolean isOneShotTask(){
		return false;
	}

	/** 检查任务调度（schedule）的休眠时间(根据任务自身的特点，默认10秒) */
	protected long getCheckScheduleSleepTime(){
		return DEFAULT_SLEEP_TIME;
	}
	
	/** 检查任务关联参数 */
	protected boolean checkTimerTask() throws Exception{
		return !((getCheckScheduleSleepTime() < 0) || (getTaskAbnormalCount() < 0) || (getTaskPeriod() < 0)
				|| (getTaskFirstTime() == null) || (getIntervalEnum() == null));

	}
	
	/** 是否 可在本服务器 执行 【默认仅在指定的“web单任务调度服务器IP”机器上执行。子类如果不是仅在指定服务器执行，可重写该方法】 */
	protected boolean isLocalServerExecutable(){
		boolean able = SysparamCache.getInstance().isLocalWebSingleTaskServer();
		if(!able){
			logger.debug("不执行[{}]具体处理逻辑：本机[{}]不是系统配置的web单任务调度服务器[{}]",
					new Object[] { this.getTaskDescribe(), LocalHostIpManager.getHostIp(),
							SysparamCache.getInstance().getWebSingleTaskHostIp() });
		}
		return able; 
	}

	/** 任务最大记录的异常累计数 */
	protected int getTaskAbnormalCount(){
		return DEFAULT_ABNORMAL_COUNT;
	}

	/**
	 * <p>子任务处理异常任务计数</p>
	 * @param taskDescribe    任务描述
	 * @param errorMsgList    错误信息集合
	 * @throws Exception
	 */
	protected void processTaskAbnormal(String taskDescribe, List<String> errorMsgList) throws Exception{
		logger.warn("任务[{}],错误信息[{}]", taskDescribe,  errorMsgList.toArray());;
	}

}
