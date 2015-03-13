package gnete.card.dao;

import java.util.List;

import flink.util.Paginater;

/**
 * 积分累积赠送 明细处理DAO
 */
public interface PointSendDetailDAO extends BaseDAO {

	/**
	 * 查询分页列表（根据申请id）
	 * 
	 * @param applyId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPageByApplyId(Long applyId, int pageNumber, int pageSize);

	/**
	 * 审核通过/不通过
	 * 
	 * @param isPass
	 *            true表示通过; false表示不通过
	 * @param isBatchSelect
	 *            true表示针对批量选择的记录，false表示针对所有待审记录
	 * @param batchIds
	 *            明细id，isBatchSelect为true时，batchIds才有意义
	 * @param applyId
	 *            申请记录的id
	 * @param updateUser
	 *            更新人
	 */
	void checkToPass(boolean isPass, boolean isBatchSelect, List<Integer> batchIds, Long applyId,
			String updateUser);

	/** 
	 * 根据申请id和状态，查找积分赠送明细的数量
	 * @param applyId
	 * @param status
	 * @return
	 */
	long count(Long applyId, String status);
}