package flink.event.impl;

import flink.event.FlinkAppEvent;

/**
 *  <p>系统业务时间缺省发布类</p>
  * @Project: Card
  * @File: FlinkAppEventPublisher.java
  * @See:    
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-4-21
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
public class FlinkAppEventPublisher extends AbstractFlinkAppEventPublisher {

	private final FlinkAppEvent flinkAppEvent;

	public FlinkAppEventPublisher(FlinkAppEvent flinkAppEvent) {
		super();
		this.flinkAppEvent = flinkAppEvent;
	}

	@Override
	protected FlinkAppEvent getFlinkAppEvent() {
		return this.flinkAppEvent;
	}

}
