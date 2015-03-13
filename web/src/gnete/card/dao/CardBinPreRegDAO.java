package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

/**
 * @File: CardBinPreRegDAO.java
 *
 * @description: 分支机构卡BIN登记
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-10-13
 */
public interface CardBinPreRegDAO extends BaseDAO {
	
	/**
	 * 分支机构所申请的卡BIN前三位记录列表
	 * @param params 查询参数信息
	 * 包括:
	 * <ul>
	 * 	<li>cardBinPrex:String 卡BIN前三位号码</li>
	 * 	<li>branchCode:String 一级分支机构</li>
	 * 	<li>state:String 状态</li>
	 * </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardBinPrexRegPage(Map<String, Object> params, int pageNumber, int pageSize);
}