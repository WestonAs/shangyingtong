package flink.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import flink.schedule.TaskException;

/**
 * 
  * @Project: MyCard
  * @File: TaskCallHelper.java
  * @See:
  * @description：
  *       <li>根据CPU的处理器个数来设置线程池的大小</li>
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-20
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public class TaskCallHelper<T> implements ITaskCall<T> {
	private final ExecutorService service;

	private final TimeUnit millisUnit = TimeUnit.MILLISECONDS;

	private final int DEFAULT_POOL_SIZE = 15;

	public TaskCallHelper() {
		service = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE * Runtime.getRuntime().availableProcessors());
	}

	public TaskCallHelper(int size) {
		int processorNum = Runtime.getRuntime().availableProcessors();
		service = Executors.newFixedThreadPool(size * processorNum);
	}

	public TaskCallHelper(ExecutorService service) {
		this.service = service;
	}

	public void processTask(Runnable task) throws TaskException {
		if(task == null) {
			return ;
		}
		
		try {
			this.service.execute(task);
		} catch (Exception ex) {
			throw new TaskException(ex);
		}
	}

	public T getFromCallable(Callable<T> callable) throws TaskException {
		T result = null;

		try {
			Future<T> future = this.service.submit(callable);
			result = future.get();
		} catch (InterruptedException ex) {
			throw new TaskException(ex);
		} catch (ExecutionException ex) {
			throw new TaskException(ex);
		} catch(NullPointerException ex) {
			throw new TaskException(ex);
		}

		return result;
	}

	public T getFromCallable(Callable<T> callable, long millseconds) throws TaskException {
		T result = null;
		try {
			Future<T> future = this.service.submit(callable);
			result = future.get(millseconds, millisUnit);
		} catch (InterruptedException ex) {
			throw new TaskException(ex);
		} catch (ExecutionException ex) {
			throw new TaskException(ex);
		} catch (TimeoutException ex) {
			throw new TaskException(ex);
		} catch(NullPointerException ex) {
			throw new TaskException(ex);
		}

		return result;
	}

	public List<Future<T>> getFromCallableList(Collection<Callable<T>> tasks) throws TaskException {
		if (CommonHelper.checkIsEmpty(tasks)) {
			return Collections.emptyList();
		}

		try {
			return this.service.invokeAll(tasks);
		} catch (InterruptedException ex) {
			throw new TaskException(ex);
		}
	}

	public List<Future<T>> getFromCallableList(Collection<Callable<T>> tasks, long timeout, TimeUnit unit)
			throws TaskException {
		if (CommonHelper.checkIsEmpty(tasks)) {
			return Collections.emptyList();
		}

		try {
			return this.service.invokeAll(tasks, timeout, unit);
		} catch (InterruptedException ex) {
			throw new TaskException(ex);
		}
	}

	public void closeMessageCall() throws TaskException {
		try {
			this.service.shutdown();
		} catch (Exception ex) {
			throw new TaskException(ex);
		}
	}

	public void closeMessageCall(long timeout) throws TaskException {
		cancelTaskInPoolByWait(timeout);
	}

	protected final void cancelTaskInPoolByWait(long timeout) {
		try {
			if (!this.service.awaitTermination(timeout, millisUnit)) {
				service.shutdownNow();

				if (!this.service.awaitTermination(timeout, millisUnit)) {
					throw new TaskException("线程池资源释放失败 !");
				}
			}
		} catch (InterruptedException ex) {
			this.service.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

}
