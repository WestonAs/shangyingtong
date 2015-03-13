package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface CardBinRegDAO extends BaseDAO {
	
	/**
	 * 当前发卡机构所申请的卡BIN记录列表
	 * @param params 查询参数信息
	 * 包括:
	 * <ul>
	 * 	<li>binNo:String 卡BIN号码</li>
	 * 	<li>cardIssuer:String 机构代码</li>
	 * 	<li>binName:String 卡BIN名称</li>
	 * 	<li>cardType:String 卡类型</li>
	 * 	<li>state:String 卡BIN状态</li>
	 * </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardBinReg(Map<String, Object> params, int pageNumber,
			int pageSize);
}