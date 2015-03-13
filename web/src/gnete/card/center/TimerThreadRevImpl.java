package gnete.card.center;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import gnete.card.timer.TimerManager;

/**
 * <p>TimerThread重构类,增加线程终止的方法处理</p>
 * @deprecated
 * @Project: Card
 * @File: TimerThreadRevImpl.java
 * @See: TimerThread.java  
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-30
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class TimerThreadRevImpl extends Thread {
	private final Log logger = LogFactory.getLog(getClass());

	private final AtomicBoolean ctrl = new AtomicBoolean(false);

	private final ApplicationContext context;

	public TimerThreadRevImpl(ApplicationContext context) {
		Assert.notNull(context, "上下文不允许为空!");
		this.context = context;
		setName("定时器线程");
	}

	public void run() {
		TimerManager timerManager = getTimerManager();

		if (timerManager != null) {
			logger.info("定时线程开始....");

			while (!isInterrupted() && !ctrl.get()) {
				try {
					timerManager.doTasks();
					sleep(1000);
				} catch (InterruptedException e) {
					break;
				} catch (Exception e) {
					logger.debug(e, e);
				}
			}
			logger.info("定时线程结束.");
		}
	}

	public void setFlag(boolean flag) {
		this.ctrl.set(flag);
	}

	private TimerManager getTimerManager() {
		try {
			return (TimerManager) this.context.getBean("timerManager");
		} catch (Exception ex) {
			logger.error("获得定时器管理处理类异常,原因[" + ex.getMessage() + "]");
		}

		return null;
	}
}
