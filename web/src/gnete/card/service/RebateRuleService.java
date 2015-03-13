package gnete.card.service;

import gnete.card.entity.Rebate;

import java.util.Map;

public interface RebateRuleService {


	/**
	 * TODO 记得要合并
	 * 计算返利金额、实收金额等
	 * 
	 * @param params
	 *            查询参数信息 包括:
	 *            <ul>
	 *            <li>amt:String 交易金额</li>
	 *            <li>rebateType:String 返利方式</li>
	 *            <li>rebateRule:RebateRule 返利规则</li>
	 *            </ul>
	 * @return
	 */
	Rebate calculateRebate(Map<String, Object> params);

}
