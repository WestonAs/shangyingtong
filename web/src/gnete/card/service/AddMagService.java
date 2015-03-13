package gnete.card.service;

import gnete.card.entity.AddMagReg;
import gnete.etc.BizException;

/**  
 * Filename:    AddMagService.java  
 * Description:   
 * Copyright:   Copyright (c)2010
 * Company:     深圳雁联计算系统有限公司 
 * @author:     aps-zsg  
 * @version:    V1.0  
 * Create at:   2010-8-3 下午11:16:28  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2010-8-3    aps-zsg        1.0        1.0 Version  
 */

public interface AddMagService {

	/**
	 * 
	* 增加补磁的信息
	 * @param updateUserId TODO
	 * @param      addMagRegister   
	* @return      
	* @exception   BizException
	 */ 
	Long addAddMag(AddMagReg addMagReg, String updateUserId ) throws BizException;
	
	/**
	 * 修改补磁信息
	* @param      addMagID
	* @return     
	* @exception   BizException
	 */
	boolean modifyAddMag(long addMagID) throws BizException;
	
	
	
	
}
