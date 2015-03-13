package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface FenzhiCardBinRegDAO extends BaseDAO {
	
	/**
	 * 运营分支机构卡BIN分配登记列表
	 * @param params 查询参数信息
	 * 包括:
	 * <ul>
	 * 	<li>regId:String 登记薄ID</li>
	 * 	<li>strBinNo:String 起始卡BIN</li>
	 * 	<li>state:String 状态</li>
	 * 	<li>appBranch:String 申请机构</li>
	 * </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findFenzhiCardBinRegPage(Map<String, Object> params, int pageNumber, int pageSize);
}