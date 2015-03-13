package gnete.card.dao;

import java.math.BigDecimal;
import java.util.Map;

import flink.util.Paginater;

public interface MerchTransDSetDAO extends BaseDAO {
	
	/**
	 * 查询商户交易核销列表（分页）
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Paginater findMerchTrans(Map<String, Object> params, int pageNumber,
	    		int pageSize) ;
	 
	/**
	 * 查询发卡机构、商户交易结算日结算列表（分页）
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Paginater findMerchTransDSet(Map<String, Object> params, int pageNumber,
	    		int pageSize) ;
	    
	/**
	 * 查询售卡充值类清算信息
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findSaleDepositTransDSet(Map<String, Object> params,
			int pageNumber, int pageSize);
	
	/**
	 * 查询发卡机构售卡核销列表（分页）
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Paginater findCardSale(Map<String, Object> params, int pageNumber,
	    		int pageSize) ;
	 
	public Object findByPkWithName(Map<String, Object> params);
	 
	public BigDecimal getSaleDepositAmounTotal(Map<String, Object> params); 
	
	public BigDecimal getAmounTotal(Map<String, Object> params); 
		
}