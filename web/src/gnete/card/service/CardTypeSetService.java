package gnete.card.service;

import java.util.List;

import gnete.card.entity.AccuClassDef;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.DiscntClassDef;
import gnete.card.entity.PointClassDef;
import gnete.etc.BizException;

public interface CardTypeSetService {
	
	public boolean addCardTypeCode(CardTypeCode cardTypeCode) throws BizException;
	
	public boolean modifyCardTypeCode(CardTypeCode cardTypeCode) throws BizException;
	
	public boolean deleteCardTypeCode(CardTypeCode cardTypeCode) throws BizException;
	
	public boolean addPointClassDef(PointClassDef pointClassDef, String[] ptUseLimitCodes) throws BizException;

	public boolean deletePointClassDef(PointClassDef pointClassDef) throws BizException;

	public boolean modifyPointClassDef(PointClassDef pointClassDef, String[] ptUseLimitCodes) throws BizException;
	
	public boolean addCouponClassDef(CouponClassDef couponClassDef) throws BizException;

	public boolean deleteCouponClassDef(CouponClassDef couponClassDef) throws BizException;

	public boolean modifyCouponClassDef(CouponClassDef couponClassDef) throws BizException;
	
	public boolean addAccuClassDef(AccuClassDef accuClassDef) throws BizException;

	public boolean deleteAccuClassDef(AccuClassDef accuClassDef) throws BizException;

	public boolean modifyAccuClassDef(AccuClassDef accuClassDef) throws BizException;
	
	public boolean addDiscntClassDef(DiscntClassDef discntClassDef) throws BizException;

	public boolean deleteDiscntClassDef(String discntClass) throws BizException;

	public boolean modifyDiscntClassDef(DiscntClassDef discntClassDef) throws BizException;
	
	/**
	 * 解析积分用途代码（01代码），返回程序对应的代码
	 * @param ptUseLimit
	 * @return
	 * @throws BizException
	 */
	public List getPtUseLimitCode(String ptUseLimit) throws BizException;

	/**
	 * 解析积分用途代码（01代码），返回中文的积分用途，
	 * 以逗号分隔
	 * @param ptUseLimit
	 * @return
	 * @throws BizException
	 */
	public String getPtUseLimit(String ptUseLimit) throws BizException;
	
}
