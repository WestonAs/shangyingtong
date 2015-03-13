package gnete.card.service;

import gnete.card.entity.UnLossCardReg;
import gnete.etc.BizException;

/**  
 * Filename:    UnLockCardService.java  
 * Description:   
 * Copyright:   Copyright (c)2010
 * Company:     深圳雁联计算系统有限公司 
 * @author:     aps-zsg  
 * @version:    V1.0  
 * Create at:   2010-8-17 下午05:23:39  
 */

public interface UnLossCardService {

	/**
	  * @Title: addUnLossCard 
	  * @Description: 增加解挂的信息
	  * @param unlossCardReg
	  * @param updateUserId
	  * @return
	  * @throws BizException
	  * @return boolean
	 */
	boolean addUnLossCard(UnLossCardReg unlossCardReg, String updateUserId ) throws BizException;
	
	/**
	  * @Title: modifyUnLossCard 
	  * @Description: 修改解挂信息
	  * @param  unlossCardReg
	  * @param  updateUserId
	  * @return boolean
	  * @throws BizException
	 */
	boolean modifyUnLossCard(UnLossCardReg unlossCardReg, String updateUserId) throws BizException;
	
	/**
	  * @Title: delete 
	  * @Description: 删除信息
	  * @param unlossBatchId
	  * @return
	  * @throws BizException
	  * @return boolean
	 */
	boolean delete(Long unlossBatchId) throws BizException;
	
	/**
	 * 增加批量解挂的信息
	 * @param lossCardReg
	 * @param updateUserId
	 * @return
	 * @throws BizException
	 */
	boolean addUnLossCardBat(UnLossCardReg unlossCardReg, String updateUserId ) throws BizException;
	
}
