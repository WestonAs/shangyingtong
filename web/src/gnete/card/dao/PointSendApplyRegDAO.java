package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.PointSendApplyReg;

import java.util.List;

/**
 * 积分累积赠送 登记薄处理DAO
 */
public interface PointSendApplyRegDAO extends BaseDAO {

	/**
	 * 分页列表
	 * @param pointSendApplyReg PointSendApplyReg查询条件对象
	 * @param inCardBranches 发卡机构条件范围
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(PointSendApplyReg pointSendApplyReg, List<BranchInfo> inCardBranches, int pageNumber,
			int pageSize);

}