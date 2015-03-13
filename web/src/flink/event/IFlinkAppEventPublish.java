package flink.event;

import org.springframework.context.ApplicationContextAware;

/**
 * <p>抽象ApplicationEvent的事件发布接口</p>
 * @Project:Card
 * @File: IFlinkAppEventPublish.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-12
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public interface IFlinkAppEventPublish extends ApplicationContextAware {
    /**
     * <p>发布FlinkEvent业务事件 <p>  
      * @version: 2010-12-20 下午02:27:02
      * @See:
     */
	void publishFlinkEvent();
}
