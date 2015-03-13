package gnete.card.dao;

import java.util.List;

import flink.util.Paginater;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.WsBankVerBindingReg;

/**
 * 银行卡绑定/解绑/默认卡 登记薄处理DAO
 */
public interface WsBankVerBindingRegDAO extends BaseDAO {

	/**
	 * 分页列表
	 * 
	 * @param record
	 *            查询条件对象
	 * @param inCardBranches
	 *            发卡机构条件范围
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(WsBankVerBindingReg record, List<BranchInfo> inCardBranches, int pageNumber,
			int pageSize);

}