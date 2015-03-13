package gnete.card.dao;

import gnete.card.entity.RebateCardReg;

import java.util.List;
import java.util.Map;

/**
 * @File: RebateCardRegDAO.java
 *
 * @description: 售卡充值返利卡登记
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-6-28 上午10:38:03
 */
public interface RebateCardRegDAO extends BaseDAO {
	
	/**
	 * 取得返利卡列表
	 * @param params
	 * @return
	 */
	List<RebateCardReg> findRebateCardRegList(Map<String, Object> params);
}