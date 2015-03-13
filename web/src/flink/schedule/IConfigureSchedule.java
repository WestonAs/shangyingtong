package flink.schedule;

/**
 * <p>调度处理统一入口接口</p>
 * <ul>
 * <li>1. 启动调度的抽象接口 </li>
 * <li>2. 由调度器根据调度的设定触发调度执行的JOB服务 </li>
 * </ul>
 * @Project: MyCard
 * @File: IConfigureSchedule.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-12
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public interface IConfigureSchedule {
	/**
	 * 
	  * <p>触发调度入口</p>
	  * @return
	  * @throws TaskException  
	  * @version: 2010-12-20 下午02:26:22
	  * @See:
	 */
	boolean startSchedule() throws TaskException;
}
