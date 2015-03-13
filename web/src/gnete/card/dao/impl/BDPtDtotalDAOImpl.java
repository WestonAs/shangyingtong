package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.BDPtDtotalDAO;

@Repository("BDPtDtotalDAO")
public class BDPtDtotalDAOImpl extends BaseDAOIbatisImpl implements BDPtDtotalDAO {

    public String getNamespace() {
        return "BDPtDtotal";
    }

	public Paginater findBDPtDtotal(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findBDPtDtotal", params, pageNumber, pageSize);
	}
}