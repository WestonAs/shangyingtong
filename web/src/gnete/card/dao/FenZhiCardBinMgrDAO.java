package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

public interface FenZhiCardBinMgrDAO extends BaseDAO {
	
	/**
	 * 运营分支机构卡BIN列表
	 * @param params 查询参数信息
	 * 包括:
	 * <ul>
	 * 	<li>cardBin:String 卡BIN</li>
	 * 	<li>cardBinPrex:String 卡BIN前三位</li>
	 * 	<li>status:String 状态</li>
	 * 	<li>useFlag:String 是否已使用</li>
	 * 	<li>fenzhiCode:String 一级分支机构编码</li>
	 * </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findFenzhiCardBinPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 取得当前分支机构下可分配的最小卡BIN
	 * @param currentBranch
	 * @return
	 */
	String findMinAbleCardBin(String currentBranch);
}