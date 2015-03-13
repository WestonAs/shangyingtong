package flink.event.impl;

import org.springframework.context.ApplicationContext;

import org.springframework.beans.BeansException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import flink.event.FlinkAppEvent;
import flink.util.SpringContext;

/**
 * <p>系统事件发布类，依赖于容器上下文(可以通过构造方式或者另外保存的容器上下文来触发)</p>
 * <p>注意这里负责将系统事件发布到Spring中来触发(每次通过new的方式来发布)</p>
 * @Project: Card
 * @File: AbstractFlinkAppEventPublishImpl.java
 * @See: * 
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-12
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public abstract class AbstractFlinkAppEventPublisher implements flink.event.IFlinkAppEventPublish {
	protected final Log logger = LogFactory.getLog(getClass());

	private ApplicationContext ctx;
	
	/** 从本地上下文缓存中写入到ApplicationContext中*/
	protected AbstractFlinkAppEventPublisher() {
		setApplicationContext(SpringContext.getInstance().getContext());
	}
	
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}

	public void publishFlinkEvent() {
		if (getApplicationContext() == null) {
			logger.warn("容器上下文没有注入,请重新注入处理!!!!");
			return;
		}

		FlinkAppEvent flinkAppEvent = getFlinkAppEvent();

		if (flinkAppEvent == null) {
			logger.warn("事件发布对象为空无法进行发布!!!!");
			return;
		}

		this.ctx.publishEvent(flinkAppEvent);
		logger.info("系统发布处理事件====[" + flinkAppEvent.toString() + "]");

	}

	protected ApplicationContext getApplicationContext() {
		return this.ctx;
	}

	protected abstract FlinkAppEvent getFlinkAppEvent();

}
