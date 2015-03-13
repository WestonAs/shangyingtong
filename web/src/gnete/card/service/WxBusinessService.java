package gnete.card.service;

import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.UserInfo;
import gnete.card.entity.WxCommonDataBean;
import gnete.card.entity.WxDepositReconReg;
import gnete.etc.BizException;

/**
 * <li>微信相关业务处理Service</li>
 * @Project: cardWx
 * @File: WxBusinessService.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-17 下午05:11:10
 */
public interface WxBusinessService {
	
	/**
	 * 分页显示实名卡扣款调账补帐登记薄记录
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findWxDepositReconRegPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	WxDepositReconReg findWxDepositReconReg(Long id);
	
	/**
	 * 添加实名卡扣款充值调账补帐登记薄记录
	 * @param wxDepositReconReg
	 * @param sessionUser
	 * @throws BizException
	 */
	void addWxDepositReconReg(WxDepositReconReg wxDepositReconReg, UserInfo sessionUser) throws BizException;

	WxCommonDataBean getWxCommonDataBean(String cardIssuer, String cardId, String extCardId) throws BizException;
}
