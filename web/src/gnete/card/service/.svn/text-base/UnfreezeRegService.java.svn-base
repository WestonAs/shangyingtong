package gnete.card.service;

import gnete.card.entity.UnfreezeReg;
import gnete.etc.BizException;

/**  
 * Filename:    UnfreezeRegService.java  
 * Description:   
 * Copyright:   Copyright (c)2010
 * Company:     深圳雁联计算系统有限公司 
 * @author:     aps-zsg  
 * @version:    V1.0  
 * Create at:   2010-9-20 下午05:13:35  
 */

public interface UnfreezeRegService {
	
	Long addUnfreeze(UnfreezeReg unfreezeReg, String updateUserId) throws BizException;
	
	boolean modifyUnfreeze(UnfreezeReg freezeReg, String updateUserId) throws BizException;
	
	boolean delete(Long unfreezeId) throws BizException;
	
	/**
	 * 增加批量卡账户解付信息
	 * @param unfreezeReg
	 * @param updateUserId
	 * @return
	 * @throws BizException
	 */
	boolean addUnfreezeRegBat(UnfreezeReg unfreezeReg, String updateUserId ) throws BizException;
}
