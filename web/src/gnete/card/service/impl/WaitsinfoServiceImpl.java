package gnete.card.service.impl;

import flink.schedule.TaskException;
import flink.util.ITaskCall;
import flink.util.SpringContext;
import flink.util.TaskCallHelper;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.SysLog;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.state.SysLogViewState;
import gnete.card.entity.state.WaitsinfoState;
import gnete.card.entity.state.WebState;
import gnete.card.entity.type.SysLogClass;
import gnete.card.entity.type.SysLogType;
import gnete.card.msg.MsgAdapter;
import gnete.card.service.LogService;
import gnete.card.service.WaitsinfoService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 *  <p>命令表服务接口处理类</p>
  * @Project: Card
  * @File: WaitsinfoServiceImpl.java
  * @See:
  * @author: 
  * @modified: aps-zbw
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-7-14
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
@Service("waitsinfoService")
public class WaitsinfoServiceImpl implements WaitsinfoService {
	private final Log logger = LogFactory.getLog(getClass());
	
	private static final int CMD_LENGTH = 3;

	private static final String ADPATER_PREFIX = "msg";
	
	private static final String ADAPTER_SUFFIX = "Adapter";
	
	private static final int WAITSTASK_POOL_SIZE = 100;
	
	private static final long  WAITSTASK_TIMEOUT = 90000L;
	
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;
	@Autowired
	private LogService logService;
	
	private final Map<String, ITaskCall<Void>> waitsInforTaskMap = new ConcurrentHashMap<String, ITaskCall<Void>>();
	
	/**
	 * 为每一个命令创建一个线程池
	 * @param msgType
	 * @return
	 */
	private ITaskCall<Void> getWaitsInforTask(String msgType) {
		ITaskCall<Void> waitsInfoTask = this.waitsInforTaskMap.get(msgType);
		
		if(waitsInfoTask == null) {
			waitsInfoTask =  new TaskCallHelper<Void>(WAITSTASK_POOL_SIZE);
			
			this.waitsInforTaskMap.put(msgType, waitsInfoTask);
		}
		
		return waitsInfoTask;
	}
	
	/**
	 * <p>增加批量处理任务表的任务池</p>
	 */
	private final ITaskCall<Void> waitsInfoTask = new TaskCallHelper<Void>(WAITSTASK_POOL_SIZE);

	public Long addCmd(String msgType, Long refId, String userCode) throws BizException {
		Assert.notEmpty(msgType, "报文类型不能为空");
		Assert.notNull(refId, "登记薄ID不能为空");
		Assert.notEmpty(userCode, "发送用户不能为空");

		Waitsinfo waitsinfo = new Waitsinfo();
		waitsinfo.setMsgType(msgType);
		waitsinfo.setRefId(refId);
		waitsinfo.setUserCode(userCode);
		waitsinfo.setSendTime(new Date());
		waitsinfo.setStatus(WaitsinfoState.UNDEAL.getValue());
		waitsinfo.setWebState(WebState.UNDEAL.getValue());
		
		this.waitsinfoDAO.insert(waitsinfo);
		return waitsinfo.getId();
	}

	@Override
	public Long reAddCmd(String msgType, Long refId, String userCode)  throws BizException {
		Waitsinfo waitsinfo = this.waitsinfoDAO.findWaitsinfo(msgType, refId);
		Assert.notNull(waitsinfo, String.format("没有找到对应waitsinfo！msgType=%s, refId=%s", msgType, refId));
		waitsinfoDAO.delete(waitsinfo.getId());
		
		waitsinfo.setId(null);
		this.addCmd(waitsinfo.getMsgType(), waitsinfo.getRefId(), userCode);
		return waitsinfo.getId();
	}
	
	public Long[] addCmds(List<Object[]> cmdList) throws BizException {
		//1.1 检查大的非空
		Assert.notEmpty(cmdList, "命令列表不能为空!");

		List<Waitsinfo> waitsInfoList = new ArrayList<Waitsinfo>(cmdList.size());

		for (Object[] cmd : cmdList) {
			//1.2 检查内部参数
			checkCmd(cmd);
			//1.3 没有异常抛出则添加(批量加入)
			waitsInfoList.add(getWaitsInfo(cmd));
		}

		//1.4 获得批量处理返回
		return getCmdsReturn(waitsInfoList);
	}


	public void dealWeb(Waitsinfo waitsinfo) throws BizException {
		// 先尝试锁定命令表记录
		Waitsinfo lock = null;
		try {
			lock = (Waitsinfo) this.waitsinfoDAO.findByPkWithLock(waitsinfo.getId());
		} catch (Exception e) {
			throw new BizException("记录锁定失败，可能已经被锁定！");
		}

		// 执行适配器方法
		String msgType = lock.getMsgType();
		String msgAdapter = "msg" + msgType + "Adapter";
		MsgAdapter adapter = (MsgAdapter) SpringContext.getService(msgAdapter);
		if (adapter == null) {
			throw new BizException("找不到该适配器" + msgAdapter + "！");
		}
		try {
			adapter.deal(waitsinfo.getRefId(), waitsinfo.getStatus().equals(WaitsinfoState.SUCCESS.getValue()));
		} catch (BizException e) {
			e.printStackTrace();
		}
		lock.setWebState(WebState.DONE.getValue());
		this.waitsinfoDAO.update(lock);
	}
	
	
	/**
	  * <p>把每个锁记录的处理放在一个线程处理(批次处理失败)</p>
	  * @param waitsInfoList
	  * @throws BizException  
	  * @version: 2011-4-23 上午10:38:57
	  * @See: 已经不用 
	 */
	public void dealWeb(List<Waitsinfo> waitsInfoList) throws BizException {
		try {
			for(final Waitsinfo waitsInfo : waitsInfoList) {
				waitsInfoTask.getFromCallable(new Callable<Void>(){

					public Void call() throws Exception {
						MsgAdapter msgAdapter = getWaitsInfoAdapter(waitsInfo);
						
						if(msgAdapter != null) {
							//1.1 活得待锁的命令表记录(行记录)
							Waitsinfo lockedWaitsInfo = getLockedWaitsInfo(waitsInfo);
							try {
								//1.2 由适配器来处理待锁记录
								processLockedWaitsInfo(lockedWaitsInfo,msgAdapter);
							}finally {
								//1.3 释放行记录锁
								processWaitsInfoRelease(lockedWaitsInfo);
							}
						}
						
						return null;
					}
					
					private MsgAdapter getWaitsInfoAdapter(Waitsinfo waitsInfo)  {
						MsgAdapter msgAdapter = null;
						String msgAdapterBean = new StringBuilder().append(ADPATER_PREFIX).append(waitsInfo.getMsgType())
																	.append(ADAPTER_SUFFIX).toString();
						try {
							msgAdapter = (MsgAdapter) SpringContext.getService(msgAdapterBean);
						} catch (Exception e) {
							logger.error("找不到消息适配器[" + msgAdapterBean + "]", e);
						}
						
						return msgAdapter;					
					}
					
					private Waitsinfo getLockedWaitsInfo(Waitsinfo waitsInfo) throws BizException{
						try {
							return (Waitsinfo)waitsinfoDAO.findByPkWithLock(waitsInfo.getId());
						}catch(Exception ex) {
							throw new BizException("指令表行记录[" + waitsInfo.getMsgType() + "][" + waitsInfo.getRefId() + "]锁定处理失败,原因[" + ex.getMessage() +"]");
						}					
					}				
					
					private void processLockedWaitsInfo(Waitsinfo lockWaitsInfo,MsgAdapter msgAdapter) {
						StopWatch stopWatch = new StopWatch();
						try {
							stopWatch.start();
							msgAdapter.deal(lockWaitsInfo.getRefId(), lockWaitsInfo.getStatus().equals(WaitsinfoState.SUCCESS.getValue()));
						}catch(Exception ex) {
							logger.error("消息适配器[" + msgAdapter.toString() + "]处理行锁记录[" + lockWaitsInfo.getId() +"]失败,原因[" + ex.getMessage()+"]");
						} finally {
							stopWatch.stop();
							logger.debug("消息适配器[Msg" + lockWaitsInfo.getMsgType() + ADAPTER_SUFFIX + "]处理记录[" + lockWaitsInfo.getId() +"]消耗时间[" + stopWatch + "]");
						}
					}	
					
					private void processWaitsInfoRelease(Waitsinfo lockedWaitsInfo)  throws BizException{
						lockedWaitsInfo.setWebState(WebState.DONE.getValue());
						try {
							waitsinfoDAO.update(lockedWaitsInfo);
						}catch(Exception ex) {
							throw new BizException("指令表行记录释放处理失败,记录[" + lockedWaitsInfo.toString() +"],原因[" + ex.getMessage() +"]");
						}
					}
					
				},WAITSTASK_TIMEOUT);				
			}
		}catch(Exception ex) {
			logger.error("命令表批量处理失败",ex);
			throw new BizException(ex);
		}		
	}
	
	private MsgAdapter getWaitsInfoAdapter(String msgType)  {
		MsgAdapter msgAdapter = null;
		String msgAdapterBean = new StringBuilder().append(ADPATER_PREFIX).append(msgType).append(ADAPTER_SUFFIX).toString();
		try {
			msgAdapter = (MsgAdapter) SpringContext.getService(msgAdapterBean);
		} catch (Exception e) {
			logger.error("找不到消息适配器[" + msgType + "]", e);
		}
		
		return msgAdapter;					
	}
	
	public void dealWebByMsgType(Map map) {
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			final Map.Entry<String, List<Waitsinfo>> entry = (Map.Entry<String, List<Waitsinfo>>) iterator.next();
			
			final MsgAdapter msgAdapter = getWaitsInfoAdapter(entry.getKey());
			
			if(msgAdapter == null) {
				logger.error("消息适配器[" + entry.getKey() + "]不存在");
				continue;
			}
			
			ITaskCall<Void> task = getWaitsInforTask(entry.getKey());
			
			for(final Waitsinfo waitsInfo : entry.getValue()) {
				try {
					
					task.getFromCallable(new Callable<Void>(){
						
						public Void call() throws Exception {
							StopWatch stopWatch = new StopWatch();
							try {
								logger.debug("开始处理消息[" + entry.getKey() + "]....");
								stopWatch.start();
								Waitsinfo lockedWaitsInfo = getLockedWaitsInfo(waitsInfo);
								try {
									//1.2 由适配器来处理待锁记录
									processLockedWaitsInfo(lockedWaitsInfo,msgAdapter);
								}finally {
									//1.3 释放行记录锁
									processWaitsInfoRelease(lockedWaitsInfo);
								}
							} catch (Exception e) {
								logger.error("处理消息[" + entry.getKey() + "]发生异常，原因：" + e.getMessage(), e);
							} finally {
								stopWatch.stop();
								logger.debug("处理消息[" + entry.getKey() + "]完成。消耗时间[" + stopWatch + "]");
							}
							return null;
						}
						
						private Waitsinfo getLockedWaitsInfo(Waitsinfo waitsInfo) throws BizException{
							try {
								return (Waitsinfo)waitsinfoDAO.findByPk(waitsInfo.getId());
							}catch(Exception ex) {
								throw new BizException("指令表行记录锁定处理失败,原因[" + ex.getMessage() +"]");
							}					
						}
						
						private void processLockedWaitsInfo(Waitsinfo lockWaitsInfo, MsgAdapter msgAdapter) {
							StopWatch stopWatch = new StopWatch();
							try {
								stopWatch.start();
								msgAdapter.deal(lockWaitsInfo.getRefId(), lockWaitsInfo.getStatus().equals(WaitsinfoState.SUCCESS.getValue()));
							}catch(Exception ex) {
								logger.error("消息适配器[" + msgAdapter.toString() +"]处理行锁记录[" + lockWaitsInfo.getId() +"]失败,原因[" + ex.getMessage()+"]");
							} finally {
								stopWatch.stop();
								logger.debug("消息适配器[Msg" + lockWaitsInfo.getMsgType() + ADAPTER_SUFFIX + "]处理记录[" + lockWaitsInfo.getId() +"]消耗时间[" + stopWatch + "]");
							}
						}	
						
						private void processWaitsInfoRelease(Waitsinfo lockedWaitsInfo)  throws BizException{
							lockedWaitsInfo.setWebState(WebState.DONE.getValue());
							try {
								waitsinfoDAO.update(lockedWaitsInfo);
							}catch(Exception ex) {
								throw new BizException("指令表行记录释放处理失败,记录[" + lockedWaitsInfo.toString() +"],原因[" + ex.getMessage() +"]");
							}
						}
					}, WAITSTASK_TIMEOUT);
				} catch (TaskException ex) {
					String msg = "消息适配器处理命令[" + entry.getKey() + "]失败";
					logger.error(msg, ex);
					
					rollBackWaitsInfo(waitsInfo);
					logSysLog(msg + "原因：" + ex.getMessage());
				}
			}
		}
		
	}
	
	/**
	 * 回滚命令表的web处理状态
	 * @param waitsinfo
	 */
	private void rollBackWaitsInfo(Waitsinfo waitsinfo) {
		try {
			logger.debug("回滚命令表命令[" + waitsinfo.getMsgType() + "][" + waitsinfo.getRefId() + "]的web处理状态");
			waitsinfo.setWebState(WebState.UNDEAL.getValue());
			
			this.waitsinfoDAO.update(waitsinfo);
			logger.debug("回滚成功！");
		} catch (Exception e) {
			logger.error("回滚命令表中的web处理状态发生异常，原因：" + e);
		}
	}
	
	/**
	 *  记录系统日志
	 * @param msg
	 */
	private void logSysLog(String msg){
		try {
			SysLog sysLog = new SysLog();
			
			sysLog.setLogDate(new Date());
			sysLog.setLogType(SysLogType.ERROR.getValue());
			sysLog.setState(SysLogViewState.UN_READ.getValue());
			sysLog.setLogClass(SysLogClass.WARN.getValue());
			if (msg.length() >= 250) {
				sysLog.setContent(StringUtils.substring(msg, 0, 500));
			} else {
				sysLog.setContent(msg);
			}
			
			logService.addSyslog(sysLog);
		} catch (BizException e) {
			logger.error("记录系统日志发生异常，原因：" + e);
		}
	}
	
	public void dealWebFromCacheMap(final Map<String, Map<Long, Object[]>> map) {
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, Map<Long, Object[]>> entry = (Map.Entry<String, Map<Long, Object[]>>) iterator.next();
			
			final String msgType = entry.getKey();
			final Map<Long, Object[]> taskMap = entry.getValue();
			
			final MsgAdapter msgAdapter = getWaitsInfoAdapter(msgType);
			
			if(msgAdapter == null) {
				logger.error("消息适配器[" + msgType + "]不存在");
				continue;
			}
			
			ITaskCall<Void> task = getWaitsInforTask(msgType);
			
			for (Iterator iterate = taskMap.entrySet().iterator(); iterate.hasNext();) {
				Map.Entry<Long, Object[]> entry2 = (Map.Entry<Long, Object[]>) iterate.next();
				
				final Long id = entry2.getKey(); // 命令表的ID
				Object[] object = entry2.getValue(); // 命令表中的msgType，refId，status字段
				if (object.length != 3) {
					logger.error("命令表处理Map中传入的参数个数不正确。参数为[" + ArrayUtils.toString(object) + "]");
				}
				final Long refId = (Long) object[1];
				final String status = (String) object[2];
				
				try {
					// 用多线程处理每个MsgType的命令，由于数据是从缓存中取出，故不需要锁表，减少对数据库的操作
					task.getFromCallable(new Callable<Void>(){
						
						public Void call() throws Exception {
							StopWatch stopWatch = new StopWatch();
							try {
								logger.debug("开始处理消息[" + msgType + "][" + refId + "]....");
								stopWatch.start();
								try {
									//1.2 由适配器来处理缓存中待处理记录
									processWaitsInfo(refId, status, msgAdapter);
								}finally {
									//1.3 更新缓存，同时更新命令表中的web处理状态字段
									processWaitsInfoAfter(refId);
								}
							} catch (Exception e) {
								logger.error("处理消息[" + msgType + "][" + refId + "]发生异常，原因：" + e.getMessage(), e);
							} finally {
								stopWatch.stop();
								logger.debug("处理消息[" + msgType + "][" + refId + "]完成。消耗时间[" + stopWatch + "]");
							}
							return null;
						}
						
						private void processWaitsInfo(Long refId, String status, MsgAdapter msgAdapter) {
							StopWatch stopWatch = new StopWatch();
							try {
								stopWatch.start();
								msgAdapter.deal(refId, WaitsinfoState.SUCCESS.getValue().equals(status));
							}catch(Exception ex) {
								logger.error("消息适配器[" + msgType +"]处理记录[" + refId +"]失败,原因[" + ex.getMessage()+"]");
							} finally {
								stopWatch.stop();
								logger.debug("消息适配器[" + msgType + "]处理记录[" + refId +"]消耗时间[" + stopWatch + "]");
							}
						}	
						
						private void processWaitsInfoAfter(Long refId) throws TaskException {
							Map<String, Object> params = new HashMap<String, Object>();
							
							params.put("webState", WebState.DONE.getValue());
							params.put("", msgType);
							params.put("", refId);
							try {
								waitsinfoDAO.updateWebStatus(params);
								
								// 清除已经处理的ID
								if (taskMap.containsKey(id)) {
									taskMap.remove(id);
								}
							}catch(Exception ex) {
								throw new TaskException("更新指令表行记录失败,记录[" + id +"],原因[" + ex.getMessage() +"]");
							}
						}
					}, WAITSTASK_TIMEOUT);
				} catch (TaskException ex) {
					String msg = "消息适配器处理命令[" + entry.getKey() + "]失败";
					logger.error(msg, ex);
					
					logSysLog(msg + "原因：" + ex.getMessage());
				}
			}
			
			// 更新map
			if (map.containsKey(msgType)) {
				
			}
		}
	}
	
    //------------------------------------------批量命令表返回的处理(added by bwzhang)-----------------------------------------------------------
    private Long[] getCmdsReturn(List<Waitsinfo> waitsInfoList) throws BizException {
		if(waitsInfoList.isEmpty()) {
			return new Long[] {};
		}
		
		try {
			this.waitsinfoDAO.insertBatch(waitsInfoList);
			
			Long[] cmdReturn = new Long[waitsInfoList.size()];

			for (int i = 0, ln = waitsInfoList.size(); i < ln; i++) {
				cmdReturn[i] = waitsInfoList.get(i).getId();
			}

			return cmdReturn;

		} catch (DataAccessException ex) {
			throw new BizException("批量写入命令表出现异常,原因[" + ex.getMessage() + "]");
		}
	}

	private Waitsinfo getWaitsInfo(Object[] cmd) {
		String msgType = (String) cmd[0];
		Long refId = (Long) cmd[1];
		String userCode = (String) cmd[2];
		Waitsinfo waitsInfo = new Waitsinfo();
		waitsInfo.setMsgType(msgType);
		waitsInfo.setRefId(refId);
		waitsInfo.setUserCode(userCode);
		waitsInfo.setSendTime(new Date());
		waitsInfo.setStatus(WaitsinfoState.UNDEAL.getValue());
		waitsInfo.setWebState(WebState.UNDEAL.getValue());
		return waitsInfo;
	}

	private void checkCmd(Object[] cmd) throws BizException {
		Assert.notEmpty(cmd, CMD_LENGTH, "命令表信息不能为空且长度应该为" + CMD_LENGTH+"!");

		try {
			Assert.notEmpty((String) cmd[0], "报文类型不能为空");
			Assert.notNull(cmd[1], "登记薄ID不能为空");
			Assert.notEmpty((String) cmd[2], "发送用户不能为空");
		} catch (Exception ex) {
			throw new BizException("提取命令信息异常,原因[" + ex.getMessage() + "]");
		}
	}
	//----------------------------------------------------------------------------------------------------------------------------------
}
