package gnete.card.service;

import gnete.card.entity.LossCardReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

/**  
 * Filename:    LossCardService.java  
 * Description:   
 * Copyright:   Copyright (c)2010
 * Company:     深圳雁联计算系统有限公司 
 * @author:     aps-zsg  
 * @version:    V1.0  
 * Create at:   2010-8-13 下午05:44:18   
 */

public interface LossCardService {
	/**
	 * 增加挂失的信息
	 * @param lossCardReg
	 * @param updateUserId
	 * @return
	 * @throws BizException
	 */
	boolean addLossCard(LossCardReg lossCardReg, UserInfo user) throws BizException;
	
	/**
	 * 修改挂失信息
	 * @param updateUserId 
	 * @param      addMagID
	* @return     
	* @exception   BizException
	 */
	boolean modifyLossCard(LossCardReg lossCardReg, String updateUserId) throws BizException;
	
	boolean delete(Long lossBatchId) throws BizException;
	
	/**
	 * 增加批量挂失的信息
	 * @param lossCardReg
	 * @param updateUserId
	 * @return
	 * @throws BizException
	 */
	boolean addLossCardBat(LossCardReg lossCardReg, UserInfo user) throws BizException;
}
