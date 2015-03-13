package flink.util;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 * <p>concurrent中线程调度构造工厂类(推荐使用这个来进行调度服务构造)</p>
 * @Project: Card
 * @File: ScheduleTaskCallHelper.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-8-3
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class ScheduleTaskCallHelper {

	public static ScheduledExecutorService getSingleScheduleExecutor() {
		return Executors.newSingleThreadScheduledExecutor();
	}

	public static ScheduledExecutorService getSingleScheduleExecutor(ThreadFactory threadFactory) {
		return Executors.newSingleThreadScheduledExecutor(threadFactory);
	}
	
	
	public static ScheduledExecutorService getSingleScheduleExecutor(String prefix) {
		return Executors.newSingleThreadScheduledExecutor(new CustomizableThreadFactory(prefix));
	}

	public static ScheduledExecutorService getFixScheduleExecutor() {
		return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
	}

	public static ScheduledExecutorService getFixScheduleExecutor(int corePoolSize) {
		return Executors.newScheduledThreadPool(corePoolSize);
	}

	public static ScheduledExecutorService getFixScheduleExecutor(int corePoolSize, ThreadFactory threadFactory) {
		return Executors.newScheduledThreadPool(corePoolSize, threadFactory);
	}

}
