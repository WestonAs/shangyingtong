package flink.event;

import gnete.etc.BizException;

import java.util.Map;

/**
  * <p>业务事件抽象接口</p> 
  * @Project: Card
  * @File: IFlinkAppEventCreate.java
  * @See:
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-4-21
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
public interface IFlinkAppEventCreate<T> {

	/**
	 * 
	  * <p>根据泛型事件源以及传入参数构造业务事件</p>
	  * @param eventResource  事件源(事件构造的来源)
	  * @param params   传入参数(影响事件构造的外部条件)
	  * @return
	  * @throws BizException  
	  * @version: 2011-7-11 上午11:25:52
	  * @See:
	 */
	FlinkAppEvent getFlinkAppEvent(T eventResource,Map params) throws BizException;
	
	/**
	 * 
	  * <p>构造缺省业务事件</p>
	  * @return  
	  * @version: 2011-7-11 上午11:25:57
	  * @See:
	 */
	FlinkAppEvent getDefaultFlinkAppEvent();
}
