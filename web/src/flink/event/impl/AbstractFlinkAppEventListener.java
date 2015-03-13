package flink.event.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import flink.event.FlinkAppEvent;

/**
 * <p>抽象系统事件(FlinkAppEvent)监听器</p>
 * <ul>
 * <li>1.检查事件的类型是否是定义的FlinkAppEvent</li>
 * <li>2.FlinkAppEvent包含的事件源是否符合处理的要求</li>
 * </ul>
 * @Project: Card
 * @File: AbstractFlinkAppEventListener.java
 * @See:  
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-12
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public abstract class AbstractFlinkAppEventListener implements ApplicationListener {
	protected final Log logger = LogFactory.getLog(getClass());

	public void onApplicationEvent(ApplicationEvent event) {
		if (checkApplicationEvent(event)) {
			FlinkAppEvent appEvent = (FlinkAppEvent) event;
			if (checkApplicationEvent(appEvent)) {
				logger.info("系统收到要处理的发布事件===[" + appEvent.toString() + "]");
				processApplicationEvent(appEvent);
			}
		}
	}

	// 检查事件抽象类型
	private boolean checkApplicationEvent(ApplicationEvent event) {
		return (event != null) && (event instanceof FlinkAppEvent);
	}

	// 检查事件子类型
	protected abstract boolean checkApplicationEvent(FlinkAppEvent event);

	// 处理该事件
	protected abstract void processApplicationEvent(FlinkAppEvent event);

}
