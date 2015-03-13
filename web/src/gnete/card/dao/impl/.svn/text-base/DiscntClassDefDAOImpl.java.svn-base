package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.DiscntClassDefDAO;
import gnete.card.entity.DiscntClassDef;

@Repository
public class DiscntClassDefDAOImpl extends BaseDAOIbatisImpl implements DiscntClassDefDAO {

    public String getNamespace() {
        return "DiscntClassDef";
    }

	public Paginater findDiscntClassDef(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findDiscntClassDef", params, pageNumber, pageSize);
	}
	
	public List<DiscntClassDef> findDiscntClassList(Map<String, Object> params) {
		return this.queryForList("findDiscntClassDef", params);
	}
}