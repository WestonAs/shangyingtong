package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.entity.CouponClassDef;

@Repository
public class CouponClassDefDAOImpl extends BaseDAOIbatisImpl implements CouponClassDefDAO {

    public String getNamespace() {
        return "CouponClassDef";
    }
    
    public Paginater findCouponClassDef(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findCouponClassDef", params, pageNumber, pageSize);
    }
    
    public List<CouponClassDef> findCouponClassList(Map<String, Object> params) {
    	return this.queryForList("findCouponClassDef", params);
    }

	public List<CouponClassDef> findCouponClassByJinst(
			Map<String, Object> params) {
		return this.queryForList("findCouponClassByJinst", params);
	}
    
    
}