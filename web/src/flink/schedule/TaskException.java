package flink.schedule;

import flink.exception.YlinkRunTimeException;

/**
 * <p>描述执行任务出现的异常</p>
 * @Project: MyCard
 * @File: TaskException.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-12
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public class TaskException extends YlinkRunTimeException {
	private static final long serialVersionUID = 8509232902039235537L;

	public TaskException(String message) {
		super(message);
	}

	public TaskException(Throwable cause) {
		super(cause);
	}

	public TaskException(String message, Throwable cause) {
		super(message, cause);
	}
}
