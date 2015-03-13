package gnete.card.service;

import gnete.card.entity.FreezeReg;
import gnete.etc.BizException;

/**  
 * Filename:    FreezeRegService.java  
 * Description:   
 * Copyright:   Copyright (c)2010
 * Company:     深圳雁联计算系统有限公司 
 * @author:     aps-zsg  
 * @version:    V1.0  
 * Create at:   2010-9-15 上午10:34:05  
 */

public interface FreezeRegService {

	Long addFreeze(FreezeReg freezeReg, String updateUserId) throws BizException;
	
	boolean modifyFreeze(FreezeReg freezeReg, String updateUserId) throws BizException;
	
	boolean delete(Long freezeId) throws BizException;
	
	/**
	 * 增加批量卡账户冻结信息
	 * @param freezeReg
	 * @param updateUserId
	 * @return
	 * @throws BizException
	 */
	boolean addFreezeRegBat(FreezeReg freezeReg, String updateUserId ) throws BizException;
}
