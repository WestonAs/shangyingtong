package flink.schedule.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import flink.schedule.TaskException;

/**
 * 
  * @Project: Card
  * @File: AbstractConfigureJobImpl.java
  * @See: 
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-12
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public abstract class AbstractConfigureJobImpl implements flink.schedule.IConfigureJob {
	protected final Log logger = LogFactory.getLog(getClass());

	public boolean executeJob() throws TaskException {
		logger.info("开始执行[" + setConfigureJob() + "]调度");

		boolean flag = executeInteral();
		logger.info("[" + setConfigureJob() + "]调度 运行结束");

		return flag;
	}

	protected abstract boolean executeInteral() throws TaskException;

	protected abstract String setConfigureJob();
}
