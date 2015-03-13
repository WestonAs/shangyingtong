package gnete.card.timer;

import gnete.etc.BizException;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * @File: AbstractTimerHandler.java
 *
 * @description:
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: Liheng
 * @version: 1.0
 * @since 1.0 2011-8-19
 */
public abstract class AbstractTimerHandler implements TimerHandler {
	protected abstract Logger getLogger();
	
	//执行定时任务
	protected abstract void doTask() throws BizException;
	
	protected abstract boolean canStart();
	protected abstract void resetTimer();
	
	public void handle() {
		if (canStart()) {
			resetTimer();		//复位计时器
			new TaskThread(this).start();	//执行定时任务（在单独的线程中）
		}
	}
	
	protected Date now() {
		return new Date();
	}
	
	class TaskThread extends Thread {
		AbstractTimerHandler handler;
		public TaskThread(AbstractTimerHandler handler) {
			this.handler = handler;
		}
		public void run() {
			handler.getLogger().debug(getName() + "开始...");
			
			try {
				handler.doTask();
			}
			catch(Exception e) {
				handler.getLogger().debug(e, e);
			}
			
			handler.getLogger().debug(getName() + "结束...");
		}
	}
	
}
