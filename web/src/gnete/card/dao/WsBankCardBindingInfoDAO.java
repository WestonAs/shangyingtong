package gnete.card.dao;

import java.util.List;

import flink.util.Paginater;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.WsBankCardBindingInfo;

/**
 * 绑定银行卡查询 DAO
 */
public interface WsBankCardBindingInfoDAO extends BaseDAO {

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
	Paginater findPage(WsBankCardBindingInfo record, List<BranchInfo> inCardBranches, int pageNumber,
			int pageSize);

}