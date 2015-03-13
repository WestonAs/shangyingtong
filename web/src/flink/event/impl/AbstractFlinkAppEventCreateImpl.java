package flink.event.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import flink.event.FlinkAppEvent;
import gnete.etc.BizException;

/**
 * <p>业务事件构造基类:<p>
 * <ul>
 * <li>1. 当不符合业务事件构造条件时返回缺省构造事件对象(防御式,避免返回空对象)</li>
 * <li>2. 当符合时根据上述条件构造事件</li>
 * <li>3. 事件可以通过IOC容器达到监听事件的部分(进行处理)</li>
 * <ul> 
 * @Project: Card
 * @File: AbstractFlinkAppCreateImpl.java
 * @See:
 * @Description：   
 * @author: aps-zbw 
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-21
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public abstract class AbstractFlinkAppEventCreateImpl<T> implements flink.event.IFlinkAppEventCreate<T> {
	protected final Log logger = LogFactory.getLog(getClass());

	public FlinkAppEvent getDefaultFlinkAppEvent() {
		return createDefaultFlinkAppEvent();
	}

	public FlinkAppEvent getFlinkAppEvent(T eventResource, Map params) throws BizException {
		if (!checkFlinkAppEventParams(eventResource, params)) {
			return getDefaultFlinkAppEvent();
		}

		return createFlinkAppEvent(eventResource, params);
	}

	protected abstract FlinkAppEvent createDefaultFlinkAppEvent();

	protected abstract boolean checkFlinkAppEventParams(T eventResource, Map params);

	protected abstract FlinkAppEvent createFlinkAppEvent(T eventResource, Map params) throws BizException;

}
