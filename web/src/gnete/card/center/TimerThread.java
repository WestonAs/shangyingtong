package gnete.card.center;


import flink.util.SpringContext;
import gnete.card.timer.TimerManager;

import org.apache.log4j.Logger;

/**
  * @deprecated
  * @Project: CardFifth
  * @File: TimerThread.java
  * @See:
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-8-16
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
public class TimerThread extends Thread {
	static Logger logger = Logger.getLogger(TimerThread.class);
	
	private static TimerThread instance = null;
	
	public static TimerThread getInstance() {
		if (instance == null) {
			instance = new TimerThread();
		}
		return instance;
	}
	
	public static void destroyInstance() {
		if (instance != null) {
			instance.interrupt();
		}
		instance = null;
	}
	
	protected TimerThread() {
		setName("定时线程");
	}

	public void run() {
		logger.info("定时线程开始....");

		TimerManager mgr = (TimerManager) SpringContext.getService("timerManager");
//		if (mgr == null) {
//			logger.debug("mgr的值：" + mgr);
//			return;
//		}
		while(!isInterrupted()) {
			try {
				mgr.doTasks();
				sleep(1000);
			}
			catch(InterruptedException e) {
				break;
			}
			catch(Exception e) {
				logger.debug(e, e);
			}
		}
		logger.info("定时线程结束.");
	}
}
