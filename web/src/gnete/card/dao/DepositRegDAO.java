package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.DepositReg;

public interface DepositRegDAO extends BaseDAO {
	
	
	// 分页查询充值登记簿明细
	Paginater findDepositReg(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	Paginater findDepositRegSign(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	// 查询充值登记簿明细
	List<DepositReg> findDepositReg(Map<String, Object> params);

	// 查询充值登记簿明细
	List<DepositReg> findDepositRegSign(Map<String, Object> params);
	
	Paginater findDepositRegCancel(Map<String, Object> params, int pageNumber,
			int pageSize);

}