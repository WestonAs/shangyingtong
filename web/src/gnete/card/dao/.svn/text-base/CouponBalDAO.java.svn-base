package gnete.card.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.CouponBal;

public interface CouponBalDAO extends BaseDAO {
	BigDecimal getBalTotal(Map<String, Object> params); 
	
	Paginater getCouponBalList(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	List<CouponBal> findCouponBalList(Map<String, Object> params);
}