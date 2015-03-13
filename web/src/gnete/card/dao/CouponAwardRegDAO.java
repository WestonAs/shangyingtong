package gnete.card.dao;

import java.util.List;
import java.util.Map;
import flink.util.Paginater;
import gnete.card.entity.CouponAwardReg;

public interface CouponAwardRegDAO extends BaseDAO {
	public Paginater findCouponAwardReg(Map<String, Object> params, int pageNumber,
			int pageSize);

	public List<CouponAwardReg> getCouponAwardRegList(Map<String, Object> params);
}