package gnete.card.service;

import gnete.card.entity.RenewCardReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

/**  
 * Filename:    RenewCardRegService.java  
 * Description: 换卡业务实现层
 * Copyright:   Copyright (c)2010
 * Company:     深圳雁联计算系统有限公司 
 * @author:     aps-zsg  
 * @version:    V1.0  
 * Create at:   2010-8-20 下午05:33:58  
 */

public interface RenewCardRegService {

	/**
	 * 
	  * @Title: addRenewCard 
	  * @Description: 换卡登记
	  * @param renewCardReg
	  * @param updateUserId
	  * @throws BizException
	  * @return boolean
	 *
	 */
	void addRenewCard(RenewCardReg renewCardReg, UserInfo user) throws BizException;
	
	void addUpgradeRecord(RenewCardReg renewCardReg, UserInfo user) throws BizException;
	
	
	/**
	  * @Title: modifyRenewCard 
	  * @Description: 修改换卡信息
	  * @param newCardId
	  * @return
	  * @throws BizException
	  * @return boolean
	 *
	 */
	boolean modifyRenewCard(RenewCardReg renewCardReg, String updateUserId) throws BizException;
	
	/**
	  * @Title: delete 
	  * @Description: 删除信息
	  * @param unlossBatchId
	  * @return
	  * @throws BizException
	  * @return boolean
	 *
	 */
	boolean delete(Long renewCardId) throws BizException;
}
