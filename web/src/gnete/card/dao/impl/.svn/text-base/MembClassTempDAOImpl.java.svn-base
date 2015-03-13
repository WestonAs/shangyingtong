package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MembClassTempDAO;
import gnete.card.entity.MembClassTemp;
@Repository
public class MembClassTempDAOImpl extends BaseDAOIbatisImpl implements MembClassTempDAO {

    public String getNamespace() {
        return "MembClassTemp";
    }

	public Paginater findMembClassTemp(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findMembClassTemp", params, pageNumber, pageSize);
	    
	}

	public List<MembClassTemp> findList(Map<String, Object> params) {
		return this.queryForList("findMembClassTemp", params);
	}
}