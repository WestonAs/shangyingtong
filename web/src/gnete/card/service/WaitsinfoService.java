package gnete.card.service;

import gnete.card.entity.Waitsinfo;
import gnete.etc.BizException;

import java.util.List;
import java.util.Map;

public interface WaitsinfoService {

	/**
	 * 添加内部命令
	 * @param msgType
	 * @param refId
	 * @param userCode
	 * @return
	 * @throws BizException
	 */
	Long addCmd(String msgType, Long refId, String userCode) throws BizException;
	

	/**
	 * 重新添加命令（即：先删除msgType，refId指定的记录，然后在添加一条新的记录）
	 * @param msgType
	 * @param refId
	 * @param userCode
	 * @return
	 * @throws BizException 
	 */
	Long reAddCmd(String msgType, Long refId, String userCode) throws BizException;

    /**
	 * 
	  * @description：<li>批量命令表添加</li>
	  * @param cmdList : Object[] {String msgType, Long refId, String userCode}
	  * @return
	  * @throws BizException  
	  * @version: 2011-4-18 上午01:21:43
	  * @See:
	 */
	 Long[] addCmds(List<Object[]> cmdList) throws BizException;
	 
	/**
	 *  
	  * @description：<li>处理单个命令表信息</li>
	  * @param waitsinfo
	  * @throws BizException  
	  * @version: 2011-5-19 下午01:28:28
	  * @See:
	 */
	void dealWeb(Waitsinfo waitsinfo) throws BizException;
	 
	/**
	 *  
	  * @description：<li>批量处理命令表</li>
	  * @param waitsInfoList
	  * @throws BizException  
	  * @version: 2011-4-23 上午11:05:13
	  * @See:
	 */
	void dealWeb(List<Waitsinfo> waitsInfoList) throws BizException;
	
	/**
	 * @description：<li>批量处理命令表</li>
	 * 每个MsgType在一个线程池里处理(由于是在后台线程中跑的，发生异常不用跳出去)
	 * @param map
	 * @throws BizException
	 */
	void dealWebByMsgType(Map map);
	
	/**
	 * 处理处理命令表
	 * 每个MsgType在一个线程池中处理。传入的参数为MsgType和要处理的命令Map
	 * 处理完成后，清除掉对应命令的Map中的值
	 * @param map
	 */
	void dealWebFromCacheMap(final Map<String, Map<Long, Object[]>> map);

}
