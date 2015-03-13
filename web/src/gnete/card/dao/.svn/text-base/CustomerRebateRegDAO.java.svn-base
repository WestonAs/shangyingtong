package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CustomerRebateReg;

import java.util.List;
import java.util.Map;

public interface CustomerRebateRegDAO extends BaseDAO {
	/**
	 * 查询客户返利对应关系注册列表（分页）
	 * 
	 * @param params	查询参数信息
	 * 包括:
	 * <ul>
	 * 	<li>customerRebateRegId:String 客户返利注册ID</li>
	 * 	<li>cardCustomerId:String 购卡客户ID</li>
	 * 	<li>cardCustomerName:String 购卡客户名称</li>
	 * 	<li>binNo:String 卡BIN</li>
	 * 	<li>卡BIN名称:String 卡BIN名称</li>
	 * 	<li>saleRebateId:String 售卡返利规则ID</li>
	 * 	<li>saleRebateName:String 售卡返利规则名称</li>
	 * 	<li>depositRebateId:String 充值返利规则ID</li>
	 * 	<li>depositRebateName:String 充值返利规则名称</li>
	 * </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCustomerRebateRegList(Map<String, Object> params, int pageNumber,
			int pageSize);	
	
	Paginater findCustomerRebateRegByCardTypeList(Map<String, Object> params, int pageNumber,
			int pageSize);
	
    // 查询客户返利登记簿列表
	List<CustomerRebateReg> findCustomerRebateRegList(Map<String, Object> params);

	// 按卡类型查询客户返利列表
	List<CustomerRebateReg> findCustomerRebateRegByCardType(Map<String, Object> params);

}