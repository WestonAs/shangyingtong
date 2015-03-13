package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.ReleaseCardFeeMSetDAO;

@Repository
public class ReleaseCardFeeMSetDAOImpl extends BaseDAOIbatisImpl implements ReleaseCardFeeMSetDAO {

    public String getNamespace() {
        return "ReleaseCardFeeMSet";
    }
    public Paginater findReleaseCardFeeMSet(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findReleaseCardFeeMSet", params, pageNumber, pageSize);
    }
}