package gnete.card.service;

import gnete.card.entity.GiftExcReg;
import gnete.card.entity.PointConsmRuleDef;
import gnete.card.entity.PointExcCouponReg;
import gnete.card.entity.PointExcReg;
import gnete.etc.BizException;

public interface PointExchgService {
	
	/**
	 * 新增积分兑换礼品登记
	 * @param giftExcReg	积分兑换礼品登记信息
	 * @return
	 * @throws BizException
	 */
	boolean addGiftExcReg(GiftExcReg giftExcReg, String sessionUserCode) throws BizException;
	
	/**
	 * 新增积分返利登记
	 * @param pointExcReg
	 * @param sessionUserCode
	 * @return
	 * @throws BizException
	 */
	boolean addPointExcReg(PointExcReg pointExcReg, String sessionUserCode) throws BizException;
	
	/**
	 * 修改积分兑换礼品登记
	 * @param giftExcReg	积分兑换礼品登记信息
	 * @return
	 * @throws BizException
	 */
	boolean modifyGiftExcReg(GiftExcReg giftExcReg, String sessionUserCode) throws BizException ;

	/**
	 * 删除积分兑换礼品登记
	 * @param giftExcId
	 * @return
	 * @throws BizException
	 */
	boolean deleteGiftExcReg(Long giftExcId) throws BizException;
	
	/**
	 * 删除积分返利登记
	 * @param pointExcId
	 * @return
	 * @throws BizException
	 */
	boolean deletePointExc(Long pointExcId) throws BizException;
	
	//public boolean isExistGift(String giftId) throws BizException;
	
	/**
	 * 新增积分兑换赠券定义
	 * @param pointConsmRuleDef
	 * @param sessionUserCode
	 * @return
	 * @throws BizException
	 */
	boolean addPointConsmRuleDef(PointConsmRuleDef pointConsmRuleDef, String sessionUserCode) throws BizException;
	
	/**
	 * 修改积分兑换赠券定义
	 * @param pointConsmRuleDef
	 * @param sessionUserCode
	 * @return
	 * @throws BizException
	 */
	boolean modifyPointConsmRuleDef(PointConsmRuleDef pointConsmRuleDef, String sessionUserCode) throws BizException ;
	
	/**
	 * 删除积分兑换赠券定义
	 * @param ptExchgRuleId
	 * @return
	 * @throws BizException
	 */
	boolean deletePointConsmRuleDef(String ptExchgRuleId) throws BizException;
	
	boolean effectptConsmRuleDef(String ptExchgRuleId) throws BizException ;
	
	boolean invalidptConsmRuleDef(String ptExchgRuleId) throws BizException ;
	
	/**
	 * 积分兑换赠券相关函数
	 */
	boolean addPointExcCouponReg(PointExcCouponReg pointExcCouponReg, String sessionUserCode) throws BizException;
	
	boolean modifyPointExcCouponReg(PointExcCouponReg pointExcCouponReg, String sessionUserCode) throws BizException ;
	
	boolean deletePointExcCouponReg(Long pointExcCouponRegId) throws BizException;
}
