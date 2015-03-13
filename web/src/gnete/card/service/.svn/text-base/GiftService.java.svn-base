package gnete.card.service;
import gnete.card.entity.GiftDef;
import gnete.etc.BizException;

/**
 * @File: CardBinService.java
 *
 * @description: 礼品定义相关业务处理
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-7-31
 */

public interface GiftService {
	
	/**
	 * 新增礼品定义
	 * @param giftDef	礼品定义信息
	 * @return
	 * @throws BizException
	 */
	boolean addGiftDef(GiftDef giftDef) throws BizException;
	
	/**
	 * 修改礼品定义
	 * @param giftDef	礼品定义信息
	 * @return
	 * @throws BizException
	 */
	boolean modifyGift(GiftDef giftDef) throws BizException ;

	boolean delete(String giftId) throws BizException;
	
	public boolean isExistGift(String giftId) throws BizException;

	
}
