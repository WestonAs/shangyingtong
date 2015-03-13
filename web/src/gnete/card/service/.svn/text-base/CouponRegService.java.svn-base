package gnete.card.service;

import java.math.BigDecimal;
import gnete.card.entity.CouponAwardReg;
import gnete.card.entity.CouponBatReg;
import gnete.card.entity.CouponReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

/**
 * @File: AwardRegisterService.java
 *
 * @description: 赠券派赠相关业务处理类
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-8-26
 */
public interface CouponRegService {
	
	boolean addCouponReg(CouponReg couponReg, String sessionUser) throws BizException;
	
	/**
	 * 修改赠券派赠登记信息
	 * @param couponReg	赠券派赠登记信息
	 * @return
	 * @throws BizException
	 */
	boolean modifyCouponReg(CouponReg couponReg) throws BizException ;
	
	boolean deleteCouponReg(Long couponRegId) throws BizException;
	
//	boolean updateCouponBal(BigDecimal backAmt, String acctId) throws BizException;
	
	/**
	 * 新增批量派赠赠券卡登记簿
	 * @param couponBatReg
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	boolean addCouponBatReg(CouponBatReg couponBatReg, String sessionUser) throws BizException;
	
	/**
	 * 修改批量派赠赠券卡登记簿
	 * @param couponBatReg
	 * @return
	 * @throws BizException
	 */
	boolean modifyCouponBatReg(CouponBatReg couponBatReg) throws BizException ;
	
	/**
	 * 删除批量派赠赠券登记簿
	 * @param couponBatRegId
	 * @return
	 * @throws BizException
	 */
	boolean deleteCouponBatReg(Long couponBatRegId) throws BizException;
	
	/**
	 * 新增发卡机构商户赠送赠券登记簿
	 * @param couponAwardReg
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	boolean addCouponAwardReg(CouponAwardReg[] couponAwardRegList, UserInfo sessionUser) throws BizException;
	
}
