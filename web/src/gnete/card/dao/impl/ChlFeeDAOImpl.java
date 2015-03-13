package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.ChlFeeDAO;
import gnete.card.entity.ChlFee;

@Repository
public class ChlFeeDAOImpl extends BaseDAOIbatisImpl implements ChlFeeDAO {

    public String getNamespace() {
        return "ChlFee";
    }

	public Paginater findChlFee(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findChlFee", params, pageNumber, pageSize);
	}

	public ChlFee getChlFee(Map<String, Object> params) {
		return (ChlFee) this.queryForObject("getChlFee", params);
	}

	public List<ChlFee> getChlFeeList(Map<String, Object> params) {
		return this.queryForList("getChlFeeList", params);
	}
}