package gnete.card.service;

import gnete.card.entity.AwardReg;
import gnete.etc.BizException;

/**
 * @File: AwardRegisterService.java
 *
 * @description: 奖品登记簿相关业务处理类
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-18
 */
public interface AwardRegisterService {
	
	/**
	 * 更新奖品登记薄相关信息
	 * 
	 * @param awardReg
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	boolean updateAwardRegister(AwardReg awardReg, String sessionUserCode) throws BizException;
	
	boolean delete(String drawId, String awdTicketNo) throws BizException;
	
}
