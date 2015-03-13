package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;

/**
 * @File: SampleCheckDAO.java
 *
 * @description: 制卡抽检表操作DAO
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-19
 */
public interface SampleCheckDAO extends BaseDAO {
	
	/**
	 * @param params
	 *            包括:
	 *            <ul>
	 *            <li>branchCode:String 机构代码</li>
	 *            <li>cardId:String 卡号ID</li>
	 *            <li>checkResult:String 抽检结果</li>
	 *            <li>makeId:String 卡批次号</li>
	 *            </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findSampleCheckPage(Map<String, Object> params, int pageNumber,
			int pageSize);
}