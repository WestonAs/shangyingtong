package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.SignRuleReg;

public interface SignRuleRegDAO extends BaseDAO {
	/**
	 * 分页查询签单规则
	 * 
	 * @param params
	 *            查询参数信息 包括:
	 *            <ul>
	 *            <li>SignRuleId:String 签单规则ID</li>
	 *            <li>SignRuleName:String 签单规则名称</li>
	 *            <li>state:String 状态</li>
	 *            </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findSignRuleReg(Map<String, Object> params, int pageNumber,
			int pageSize);

	/**
	 * 查询签单规则列表
	 * 
	 * @param params
	 * @return
	 */
	List<SignRuleReg> findSignRuleReg(Map<String, Object> params);

	/**
	 * 查询指定客户状态为审核通过的签单规则列表
	 * 
	 * @param params
	 * @return
	 */
	List<SignRuleReg> findSignRuleByCust(String signCustId);

}