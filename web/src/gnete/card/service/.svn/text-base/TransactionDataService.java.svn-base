package gnete.card.service;

import gnete.card.entity.RmaTransTypeLimit;
import gnete.card.entity.RmaTransTypeLimitKey;
import gnete.card.entity.TransLimit;
import gnete.card.entity.TransLimitKey;
import gnete.etc.BizException;

/**
 * @File: CardBinService.java
 *
 * @description: 交易资料相关业务处理
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-8-6
 */

public interface TransactionDataService {
	
	boolean delete(long addMagId) throws BizException;
	
	public boolean modifyTransLimit(TransLimit transLimit, String userId) throws BizException;
	
	public boolean deleteTransLimit(TransLimitKey key) throws BizException;
	
	public boolean addTransLimit(TransLimit[] transLimitList, String userId) throws BizException;

	/**
	 * 添加划付交易限制定义
	 * @param rmaTransTypeLimit
	 * @param transTypes 交易数组
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	public void addRmaTransTypeLimit(RmaTransTypeLimit rmaTransTypeLimit, String[] transTypes, String userId) throws BizException;
	
	/**
	 * 修改划付交易限制定义
	 * @param rmaTransTypeLimit
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	public boolean modifyRmaTransTypeLimit(RmaTransTypeLimit rmaTransTypeLimit, String userId) throws BizException;
	
	/**
	 * 删除划付交易限制定义
	 * @param key
	 * @return
	 * @throws BizException
	 */
	public boolean deleteRmaTransTypeLimit(RmaTransTypeLimitKey key) throws BizException;

}
