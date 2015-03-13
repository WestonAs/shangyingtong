package gnete.card.timer.waitsinfo;

import flink.schedule.TaskException;
import flink.util.SpringContext;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.state.WaitsinfoState;
import gnete.card.entity.state.WebState;
import gnete.card.msg.MsgAdapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("waitsInfoTaskProcess")
public class WaitsInfoTaskProcessImpl implements WaitsInfoTaskProcess, DisposableBean {
	
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private static final String ADPATER_PREFIX = "msg";
	private static final String ADAPTER_SUFFIX = "Adapter";
	
	/** 每个消息类型msgType 对应一个 已处理记录的id 集合*/
	private final Map<String, Set<Long>> treatedSetMap = new ConcurrentHashMap<String, Set<Long>>();
	
	/** 每个消息类型msgType 对应一个 处理中记录的id 集合*/
	private final Map<String, Set<Long>> submitSetMap = new ConcurrentHashMap<String, Set<Long>>();
	
	/** 每个消息类型msgType 对应一个 待处理的&lt;waitsinfoID, waitsInfo&gt;键值对集合 */
    private final Map<String, Map<Long, Waitsinfo>> msgType2TaskMapCache = new ConcurrentHashMap<String, Map<Long, Waitsinfo>>();
    
    
	public void dealWeb() {
		//待处理的任务 的 集合
		Map<String, Map<Long, Waitsinfo>> map = this.getMsgType2TaskMapCache();
		// 按msgType遍历Map取得一个新的map
		for (Map.Entry<String, Map<Long, Waitsinfo>> entry : map.entrySet()) {
			final String msgType = entry.getKey();
			final Map<Long, Waitsinfo> taskMap = entry.getValue();
			
			final MsgAdapter msgAdapter = this.buildWaitsinfoMsgAdapter(msgType);
			if(msgAdapter == null) {
				logger.error("消息适配器[" + msgType + "]不存在");
				continue;
			}
			if (MapUtils.isEmpty(taskMap)) {
				continue;
			}
			
			Set<Long> treatedSet = getTreatedSet(msgType);
			Set<Long> submitSet = getSubmitSet(msgType);
			ExecutorService executor = Executors.newFixedThreadPool(taskMap.size());
			try {
				CompletionService<Long> ecs = new ExecutorCompletionService<Long>(executor);
				
				Map<Long, Callable<Long>> submitCallableMap = buildSubmitCallableMap(taskMap, treatedSet,
						submitSet, msgAdapter);
				
				processSumitCallableMap(msgType, ecs, submitCallableMap, submitSet);
			}finally {
				executor.shutdown();
			}					
			
		}
	}
    
	/**
	 * 容器销毁时，清空掉存放命令的map
	 */
	public void destroy() throws Exception {
		if (MapUtils.isNotEmpty(msgType2TaskMapCache)) {
			logger.info("清空命令表待处理命令缓存....");
			msgType2TaskMapCache.clear();
			treatedSetMap.clear();
			submitSetMap.clear();
		}
	}
	
	/**
	 * 获得 即将处理的msgType2TaskMapCache
	 * @return
	 */
	private Map<String, Map<Long, Waitsinfo>> getMsgType2TaskMapCache() {
		//检查需要进行处理的命令(查出前100条记录)
		List<Waitsinfo> list = this.waitsinfoDAO.findUndoForWebPage(1, 100);
		
		Map<Long, Waitsinfo> taskMap = null;
		for (Waitsinfo waitsinfo : list) {
			
			taskMap = this.getTaskMap(waitsinfo.getMsgType());
			
			if (!taskMap.containsKey(waitsinfo.getId())) {
				if (checkTreatedId(waitsinfo.getId()) || checkSubmitId(waitsinfo.getId())) {
					continue;
				}
				taskMap.put(waitsinfo.getId(), waitsinfo);
			}
		}
		return msgType2TaskMapCache;
	}
	
	
	/**
	 * 从 msgType2TasksMap 字段中 获得指定类型msgType的 待处理的 任务Map集合；
	 * 如果msgType对应的Map为空，则新建一个map
	 * @param msgType 消息类型
	 * @return
	 */
	private Map<Long, Waitsinfo> getTaskMap(String msgType) {
		Map<Long, Waitsinfo> taskMap = this.msgType2TaskMapCache.get(msgType);
		if(MapUtils.isEmpty(taskMap)) {
			taskMap = new ConcurrentHashMap<Long, Waitsinfo>();
			this.msgType2TaskMapCache.put(msgType, taskMap);
		}
		return taskMap;
	}
	
	
	/** 检查是否是已经处理过的记录 */
	private boolean checkTreatedId(Long id) {
		boolean flag = false;
		for ( Set<Long> treatedSet : treatedSetMap.values() ){
			if (CollectionUtils.isNotEmpty(treatedSet)) {
				if (treatedSet.contains(id)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	/** 检查是否是正在处理中的记录 */
	private boolean checkSubmitId(Long id) {
		boolean flag = false;
		for ( Set<Long> submitSet : submitSetMap.values() ){
			if (CollectionUtils.isNotEmpty(submitSet)) {
				if (submitSet.contains(id)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	private Map<Long, Callable<Long>> buildSubmitCallableMap(Map<Long, Waitsinfo> taskMap,
			Set<Long> treatedSet, Set<Long> submitSet, MsgAdapter msgAdapter) {
		
		Map<Long,Callable<Long>> submitCallableMap = new HashMap<Long, Callable<Long>>(taskMap.size());
		for (Map.Entry<Long, Waitsinfo> task : taskMap.entrySet() ){
			final Long id = task.getKey(); // 命令表的ID
			if(treatedSet.contains(id) || submitSet.contains(id)) {
				continue;
			}
			Waitsinfo waitsinfo = task.getValue();
			// 命令表中的msgType，refId，status字段
			Object[] params = new Object[]{waitsinfo.getMsgType(), waitsinfo.getRefId(), waitsinfo.getStatus()}; 
			submitCallableMap.put(id, buildSubmitCallable(id, params, msgAdapter));
		}
		return submitCallableMap;
	}
	
	private Callable<Long> buildSubmitCallable(final Long id, final Object[] params,
			final MsgAdapter msgAdapter) {
		
		return new Callable<Long>() {
			public Long call() throws Exception {
				final String msgType = (String)params[0];
				final Long refId = (Long) params[1];
				final String status = (String) params[2];
				
				StopWatch stopWatch = new StopWatch();
				try {
					logger.info("开始处理消息[" + msgType + "][" + refId + "]....");
					stopWatch.start();
					try {
						//1.1
						//1.2 由适配器来处理缓存中待处理记录
						processWaitsInfo(msgType,refId, status, msgAdapter);
					}finally {
						//1.3 更新缓存，同时更新命令表中的web处理状态字段
						processWaitsInfoAfter(msgType,refId);
					}
				} catch (Exception e) {
					logger.error("处理消息[" + msgType + "][" + refId + "]发生异常，原因：" + e.getMessage(), e);
				} finally {
					stopWatch.stop();
					logger.info("处理消息[" + msgType + "][" + refId + "]完成。消耗时间[" + stopWatch + "]");
				}
				return id;
			}
			
			private void processWaitsInfo(String msgType,Long refId, String status, MsgAdapter msgAdapter) {
				StopWatch stopWatch = new StopWatch();
				try {
					stopWatch.start();
					msgAdapter.deal(refId, WaitsinfoState.SUCCESS.getValue().equals(status));
				}catch(Exception ex) {
					logger.error("消息适配器[" + msgType +"]处理记录[" + refId +"]失败,原因[" + ex.getMessage()+"]");
				} finally {
					stopWatch.stop();
					logger.info("消息适配器[" + msgType + "]处理记录[" + refId +"]消耗时间[" + stopWatch + "]");
				}
			}	
			
			private void processWaitsInfoAfter(String msgType,Long refId) throws TaskException {
				try {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("webState", WebState.DONE.getValue());
					params.put("msgType", msgType);
					params.put("refId", refId);
					waitsinfoDAO.updateWebStatus(params);
				}catch(Exception ex) {
					throw new TaskException("更新指令表行记录失败,记录[" + id +"],原因[" + ex.getMessage() +"]");
				}
			}
		};
	}
	
	private void processSumitCallableMap(String msgType, CompletionService<Long> executorService,
			Map<Long, Callable<Long>> submitCallableMap, Set<Long> submitSet) {
		
		Set<Long> resultSet = new HashSet<Long>(submitCallableMap.size());
		try {
			for( Map.Entry<Long,Callable<Long>> entry : submitCallableMap.entrySet()){
				executorService.submit(entry.getValue());
			}
			for( Map.Entry<Long,Callable<Long>> entry : submitCallableMap.entrySet() ){
				//保存每次提交的记录（全局）
				submitSet.add(entry.getKey());
				//保存执行完后的记录（阻塞读取）
				resultSet.add(executorService.take().get());
			}			
		} catch (Exception e) {
			logger.error(e);
		}
		
		if(!resultSet.isEmpty()) {
			logger.info("已提交的记录：" + submitSet.toString() + "，已处理的记录" + resultSet.toString() + "");
			//替换msgType关联的处理结果
			treatedSetMap.put(msgType, resultSet);
			//从全局提交的记录中移除已处理的
			synchronized (submitSet) {
				submitSet.removeAll(resultSet);
				// 移除已处理的Map
				Map<Long, Waitsinfo> taskMap = this.getTaskMap(msgType);
				for (Long resultId : resultSet) {
					synchronized (taskMap) {
						taskMap.remove(resultId);
					}
				}
			}
		}
	}
	
	/**
	 * 根据MsgType，获得MsgAdapter实例bean。
	 * @param msgType
	 * @return
	 */
	private MsgAdapter buildWaitsinfoMsgAdapter(String msgType)  {
		MsgAdapter msgAdapter = null;
		String msgAdapterBean = new StringBuilder().append(ADPATER_PREFIX).append(msgType).append(ADAPTER_SUFFIX).toString();
		try {
			msgAdapter = (MsgAdapter) SpringContext.getService(msgAdapterBean);
		} catch (Exception e) {
			logger.error("找不到消息适配器[" + msgType + "]", e);
		}
		return msgAdapter;					
	}

	/**
	 * 得到每个msgType已处理的记录对应的缓冲区结构
	 * 
	 * @param msgType
	 * @return
	 */
	private Set<Long> getTreatedSet(String msgType) {
		Set<Long> treatedSet = treatedSetMap.get(msgType);
		if(treatedSet == null) {
			treatedSet = new HashSet<Long>();
			treatedSetMap.put(msgType, treatedSet);
		}
		return treatedSet;
	}

	/**
	 * 得到每个msgType处理中的记录对应的缓冲区结构
	 * 
	 * @param msgType
	 * @return
	 */
	private Set<Long> getSubmitSet(String msgType) {
		Set<Long> submitSet = submitSetMap.get(msgType);
		if(submitSet == null) {
			submitSet = new HashSet<Long>();
			submitSetMap.put(msgType, submitSet);
		}
		return submitSet;
	}
	
}
