package gnete.card.service;

import gnete.card.entity.CardInfo;
import gnete.card.entity.DepositCouponBatReg;
import gnete.card.entity.DepositCouponReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

import java.io.File;

public interface CouponBusService {
	
	/**
	 * 添加单笔赠券赠送登记信息
	 * 
	 * @param depositPointReg
	 * @param user
	 * @param serialNo
	 * @throws BizException
	 */
	void addDepositCouponReg(DepositCouponReg depositPointReg, UserInfo user, String serialNo) throws BizException;
	
	/**
	 * 根据传来的卡号，检查该卡能否做赠券赠送(批量时为起始卡号，批量时卡数量为空)
	 * 
	 * @param cardId
	 * @param cardCount
	 * @param user
	 * @return
	 * @throws BizException
	 */
	CardInfo checkCardId(String cardId, String cardCount, UserInfo user) throws BizException;
	
	/**
	 * 添加批量充值登记信息
	 * 
	 * @param depositPointReg
	 * @param depositPointBatReg
	 * @param user
	 * @param serialNo
	 * @throws BizException
	 */
	void addDepositCouponBatReg(DepositCouponReg depositPointReg, DepositCouponBatReg depositPointBatReg, 
			String cardCount, UserInfo user, String serialNo) throws BizException;

	/**
	 *  通过文件方式做批量赠券赠送
	 *  
	 * @param depositPointReg
	 * @param upload
	 * @param cardCount
	 * @param user
	 * @param serialNo
	 * @param limitId
	 * @throws BizException
	 */
	void addDepositCouponBatRegFile(DepositCouponReg depositPointReg, File upload, 
		String cardCount, UserInfo user, String serialNo, String limitId) throws BizException;
}
