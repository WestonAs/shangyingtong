package gnete.card.service;

import java.util.List;

import gnete.card.entity.MessageParam;
import gnete.card.entity.MessageParamKey;
import gnete.card.entity.MessageReg;
import gnete.card.entity.PointAccReg;
import gnete.etc.BizException;

/**
 * @File: PointAccFileService.java
 * 
 * @description: 读取ftp积分充值及账户变更文件的service处理。
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-4-12
 */
public interface PointAccService {
	
	/**
	 * 读取交易类型文件，在积分赠送及账户变更登记簿中记录信息
	 * @return
	 * @throws BizException
	 */
	List<PointAccReg> readPointAccFile(String branchCode, String date) throws BizException;
	
	/**
	 * 
	  * @description：<li>增加针对日期及日期登记簿关联处理</li>
	  * @param branchCode
	  * @param date
	  * @throws BizException  
	  * @version: 2011-4-22 下午02:00:28
	  * @See:
	 */
	void processPointAccFile(String branchCode, String date) throws BizException;
	
	/**
	 * 获得指定目录下文件列表
	 * @return
	 * @throws BizException
	 */
	String[] getFileList() throws BizException;

	/**
	 * 获得昨天的日期，格式为yyyyMMdd
	 * @return
	 * @throws BizException
	 */
	String getYesterdayDate() throws BizException;
	
	/**
	 * 新增积分赠送及账户变更登记
	 * @param pointAccReg	积分赠送及账户变更登记信息
	 * @return
	 * @throws BizException
	 */
	boolean addPointAccReg(PointAccReg pointAccReg) throws BizException;
	
	/**
	 * 操作人有异议，暂停相关的业务处理
	 * @param pointAccId
	 * @return
	 * @throws BizException
	 */
	boolean stopPointAcc(Long pointAccId) throws BizException;
	
	/**
	 * 新增短信参数定义
	 * @param messageParam
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean addMessageParam(MessageParam messageParam, String userId) throws BizException;
	
	/**
	 * 修改短信参数定义
	 * @param messageParam
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean modifyMessageParam(MessageParam messageParam, String userId) throws BizException;
	
	/**
	 * 删除短信参数定义
	 * @param key
	 * @return
	 * @throws BizException
	 */
	boolean deleteMessageParam(MessageParamKey key) throws BizException;
	
	/**
	 * 新增短信登记簿
	 * @param messageReg
	 * @return
	 * @throws BizException
	 */
	boolean addMessageReg(MessageReg messageReg) throws BizException;

	/**
	 * 获得短信登记簿
	 * @param pointAccReg
	 * @return
	 * @throws BizException
	 */
	MessageReg getMessageReg(PointAccReg pointAccReg) throws BizException;

	/**
	 * 获得短信信息
	 * @param pointAccReg
	 * @return
	 * @throws BizException
	 */
	String getMsg(PointAccReg pointAccReg) throws BizException;

	/**
	 * 新增短信和积分赠送及账户变更登记
	 * @param pointAccReg
	 * @return
	 * @throws BizException
	 */
	boolean addMsgAndPointAcc(PointAccReg pointAccReg) throws BizException;
	
}
