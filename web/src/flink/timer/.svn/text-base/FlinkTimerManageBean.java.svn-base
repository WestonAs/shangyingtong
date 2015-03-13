package flink.timer;

import java.util.Date;
import java.util.Stack;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import flink.util.CommonHelper;
import flink.util.ScheduleTaskCallHelper;
import gnete.util.DateUtil;

/**
 * <p>定时任务管理任务类</p>
 * <ul>
 * <li>1. 以TimerFactoryBean为蓝本,考虑到项目中有关调度任务的多样性</li>
 * <li>2. 为每个调度的任务分配独立的Timer(区别于TimerFactoryBean内部的做法)</li>
 * <li>3. 每个Timer的变动与调度任务获得的调度时刻相关联(当时刻改变则变动)</li>
 * <li>4. 调度任务由子类实现需要获悉开始的时间以及间隔的时间</li>
 * <li>5. 每个启动的任务将分配一个独立的线程</li>
 * </ul>
 * @Project: Card
 * @File: FlinkTimerManageBean.java
 * @See: org.springframework.scheduling.timer.TimerFactoryBean
 * @author: aps-zbw
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 */
public class FlinkTimerManageBean implements ITimerTaskManage, DisposableBean {
	protected static final Logger		logger	= LoggerFactory.getLogger(FlinkTimerManageBean.class);

	private FlinkTimerTask[]			flinkTimerTasks;

	private IFlinkTimerTaskRunnable[]	flinkTaskThreads;
	
    public FlinkTimerTask[] getFlinkTimerTasks() {
		return flinkTimerTasks;
	}

	public void setFlinkTimerTasks(FlinkTimerTask[] flinkTimerTasks) {
		this.flinkTimerTasks = flinkTimerTasks;
	}

	public void startTimerTasks() throws Exception {
		if (!ObjectUtils.isEmpty(this.flinkTimerTasks)) {
			
			// 启动定时任务调度线程
			this.flinkTaskThreads = new IFlinkTimerTaskRunnable[this.flinkTimerTasks.length];
			for (int i = 0; i < this.flinkTimerTasks.length; i++) {
				FlinkTimerTask timerTask = this.flinkTimerTasks[i];
				
				FlinkScheduleTaskThread timerTaskThread = new FlinkScheduleTaskThread(timerTask);
				timerTaskThread.start();
				
				logger.info("任务调度线程[{}]启动,线程ID=[{}]", timerTask.getTaskDescribe(), timerTaskThread.getId());
				flinkTaskThreads[i] = timerTaskThread;
			}
		}
	}

	/**
	 * 容器销毁时销毁内部创建的线程及其调度任务
	 */
	public void destroy() throws Exception {
		if (!ObjectUtils.isEmpty(this.flinkTaskThreads)) {
			for (IFlinkTimerTaskRunnable flinkTaskThread : flinkTaskThreads) {
				flinkTaskThread.stopRunnable();
				logger.info("任务调度线程[{}]销毁", flinkTaskThread.getTimerTask().getTaskDescribe());
			}
		}
	}
	

	/**
	 * 内部包装线程任务执行类
	 */
	protected static class FlinkScheduleTaskThread extends Thread implements IFlinkTimerTaskRunnable {

		private final AtomicBoolean							runnable			= new AtomicBoolean(true);

		private final FlinkTimerTask						flinkTimerTask;

		private final Stack<Date>							firstTimeStack		= new Stack<Date>();

		private final ScheduledExecutorService				schedule;

		private final AtomicReference<ScheduledFuture<?>>	scheduleFutureRefer	= new AtomicReference<ScheduledFuture<?>>();

		private final AtomicBoolean							hasFirstLoad		= new AtomicBoolean(false);
		
		public FlinkScheduleTaskThread(FlinkTimerTask flinkTimerTask) {
			Assert.notNull(flinkTimerTask, "定时器任务类不能为空!");
			this.flinkTimerTask = flinkTimerTask;
		   // this.schedule = ScheduleTaskCallHelper.getSingleScheduleExecutor(this.flinkTimerTask.getTaskDescribe());
			
			/**
			 * <li>从单个线程替换成线程池</li>
			 */
			// this.schedule = ScheduleTaskCallHelper.getFixScheduleExecutor();
			this.schedule = ScheduleTaskCallHelper.getFixScheduleExecutor(15);
		}
		
		public FlinkTimerTask getTimerTask() {
			return flinkTimerTask;
		}

		
		public void stopRunnable() {
		    this.firstTimeStack.clear();
		    if(this.schedule != null) {
				this.schedule.shutdown();
			}
			runnable.set(false);
		}

		public void run() {
			while (this.runnable.get()) {
				try {
					processScheduleInterval(this.flinkTimerTask);
					Thread.sleep(this.flinkTimerTask.getCheckScheduleSleepTime());
				} catch (Exception ex) {
					logger.error("执行任务[{}]异常,原因[{}]", flinkTimerTask.getTaskDescribe(), ex.toString());
				}
			}
		}
		
		private void processScheduleInterval(FlinkTimerTask flinkTimerTask) throws Exception {
			//1.1 获得配置的任务首次执行时间
			Date firstTime = flinkTimerTask.getTaskFirstTime();
			
			if(firstTimeStack.isEmpty()) {
			   //1.3 将其入栈保存(当前时刻以及下次执行时刻)
			   firstTimeStack.push(firstTime);
			    
			   //1.4 启动定时器及其任务类
			   startSchedule(firstTime, flinkTimerTask);
			   
			   //1.5 进行是否首次任务处理
			   processFirstTimeLoad(firstTime, flinkTimerTask);	
			} else {
				// 2.1 获得缓存的日期
				Date cachedFirstDate = firstTimeStack.peek();
				
				// 2.2 如果在执行时间点之后才重启定时器(替换成不同就重启)
			    //if(firstTime.after(cachedFirstDate)) 
				if (firstTime.compareTo(cachedFirstDate) != 0) {
					firstTimeStack.clear();
					firstTimeStack.push(firstTime);
					restartSchedule(firstTime, flinkTimerTask);
				}
			}
		}
		
		
		/**
		 *  执行首次任务加载
		 */
		private void processFirstTimeLoad(Date firstTime,FlinkTimerTask flinkTimerTask) throws Exception {
			if(flinkTimerTask.isPerformedOnceAtStartup()) {
				if (!this.hasFirstLoad.get()) {
					sleep(1500);
					// 启动时要执行一次任务
					this.flinkTimerTask.run();
					this.hasFirstLoad.set(true);
				}
			}
		}
		
		/**
		 * <p>启动任务及其定时器(根据任务类的性质做不同启动)</p>
		 * <p>注意需要确保任务需在当前时间点之后启动</p>
		 */
		private void  startSchedule(Date firstTime, FlinkTimerTask flinkTimerTask) throws Exception {
			ScheduledFuture<?>  result = null;
			
			long taskPeriod = flinkTimerTask.getTaskPeriod();
			long firstTimeDelay = getFirstTimeDelay(firstTime, taskPeriod);
			
			if (flinkTimerTask.isOneShotTask()) {
				result = this.schedule.schedule(flinkTimerTask, firstTimeDelay, TimeUnit.MILLISECONDS);
			} else {
				if(flinkTimerTask.isTaskFixRate()) {
					result = this.schedule.scheduleAtFixedRate(flinkTimerTask, firstTimeDelay, taskPeriod, TimeUnit.MILLISECONDS);
				} else {
					result = this.schedule.scheduleWithFixedDelay(flinkTimerTask, firstTimeDelay, taskPeriod, TimeUnit.MILLISECONDS);
				}
			}
			scheduleFutureRefer.set(result);
			
			logger.info("====[{}]定时计划安排成功, 任务延迟启动[{}], 间隔执行时间[{}]====",
					new Object[] { flinkTimerTask.getTaskDescribe(), DateUtil.formatDuring(firstTimeDelay),
							DateUtil.formatDuring(flinkTimerTask.getTaskPeriod()) });
		}
		
		/**
		 *  获得启动任务与当前时间的时间差,如果获取时间在当前时间点之前则需获取下一次时间点
		 */
		private long getFirstTimeDelay(Date firstTime, long taskPeriod) throws Exception{
			Date currentDate = CommonHelper.getCalendarDate();
			if(firstTime.before(currentDate)) {
				firstTime = getNextDate(firstTime);
			}
			long computeDiff = CommonHelper.getCompareDateDiff(currentDate, firstTime);
			return (computeDiff < taskPeriod) ? computeDiff  : taskPeriod;
		}
		
		/**
		 * 
		 * 获得下次执行的任务时刻
		 */
		protected Date getNextDate(Date currentDate) {
			IntervalEnum interval = flinkTimerTask.getIntervalEnum();
			int[] params = interval.getIntervalParams();
			return DateUtils.add(currentDate, params[0], params[1]);
		}

	    /**
		 *  否则取消当前正在执行的任务并进行重新调度
		 */
		private void restartSchedule(Date firstTime, FlinkTimerTask flinkTimerTask) throws Exception {
			if(this.schedule != null) {
				ScheduledFuture<?> scheduleFuture = scheduleFutureRefer.get();
				if(scheduleFuture == null || scheduleFuture.isCancelled()) {
					logger.warn("====当前定时计划调度返回为空或已经取消====");
					return ;
				}				
				
				boolean cancle = scheduleFuture.cancel(true);
				if(cancle) {
					this.hasFirstLoad.set(false);
					logger.info("====[{}]定时计划安排取消成功,准备重新启动定时计划安排====", flinkTimerTask.getTaskDescribe());
				    startSchedule(firstTime,flinkTimerTask);
				}				
			}
		}
		
	}
}
