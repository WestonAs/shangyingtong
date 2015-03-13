package flink.util;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import flink.schedule.TaskException;
/**
 *  <p></p>
  * @Project: Card
  * @File: ITaskCall.java
  * @See:  
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-20
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public interface ITaskCall<T> {
	/**
	 * 
	  * @param task
	  * @throws TaskException  
	  * @version: 2011-7-12 下午04:58:09
	  * @See:
	 */
	void processTask(Runnable task) throws TaskException;

	/**
	 * 
	  * @param callable
	  * @return
	  * @throws TaskException  
	  * @version: 2011-7-12 下午04:58:12
	  * @See:
	 */
	T getFromCallable(Callable<T> callable) throws TaskException;

	/**
	 * 
	  * @param callable
	  * @param millseconds
	  * @return
	  * @throws TaskException  
	  * @version: 2011-7-12 下午04:58:16
	  * @See:
	 */
	T getFromCallable(Callable<T> callable, long millseconds) throws TaskException;

	/**
	 * 
	  * @throws TaskException  
	  * @version: 2011-7-12 下午04:58:19
	  * @See:
	 */
	void closeMessageCall() throws TaskException;

	/**
	 * 
	  * @param timeout
	  * @throws TaskException  
	  * @version: 2011-7-12 下午04:58:23
	  * @See:
	 */
	void closeMessageCall(long timeout) throws TaskException;

	/**
	 * 
	  * @param tasks
	  * @return
	  * @throws TaskException  
	  * @version: 2011-7-12 下午04:58:26
	  * @See:
	 */
	List<Future<T>> getFromCallableList(Collection<Callable<T>> tasks) throws TaskException;

	/**
	 * 
	  * @param tasks
	  * @param timeout
	  * @param unit
	  * @return
	  * @throws TaskException  
	  * @version: 2011-7-12 下午04:58:34
	  * @See:
	 */
	List<Future<T>> getFromCallableList(Collection<Callable<T>> tasks, long timeout, TimeUnit unit) throws TaskException;

}
