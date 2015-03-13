package flink.event;

import org.springframework.context.ApplicationEvent;

/**
 * <p>系统抽象事件类(基于Spring提供的事件类本身)</p>
 * @Project: Card
 * @File: FlinkAppEvent.java
 * @See:  FlinkOperateEnum.java
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-12
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public abstract class FlinkAppEvent extends ApplicationEvent {
	private final FlinkOperateEnum operateEnum;

	public FlinkAppEvent(Object source, FlinkOperateEnum operateEnum) {
		super(source);
		this.operateEnum = operateEnum;
	}

	public FlinkOperateEnum getFlinkOperateEnum() {
		return this.operateEnum;
	}

}
