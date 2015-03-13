package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.DepositBatReg;
import gnete.card.entity.DepositReg;

import java.util.List;
import java.util.Map;

public interface DepositBatRegDAO extends BaseDAO {
	
	// 分页查询批量充值登记簿列表
	Paginater findDepositReg(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	// 查询批量充值登记簿列表
	List<DepositReg> findDepositReg(Map<String, Object> params);
	
	// 查询批量充值明细登记簿列表
	List<DepositBatReg> findDepositBatReg(Map<String, Object> params);
	
	Paginater findDepositBatRegByPage(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	/**
	 * 根据充值批次号批量修改充值明细登记簿的状态
	 * @param params
	 * @return
	 */
	int updateStatusByBatchId(Map<String, Object> params);
}