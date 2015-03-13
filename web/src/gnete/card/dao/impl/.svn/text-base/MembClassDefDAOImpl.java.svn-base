package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MembClassDefDAO;
import gnete.card.entity.MembClassDef;

@Repository
public class MembClassDefDAOImpl extends BaseDAOIbatisImpl implements MembClassDefDAO {

    public String getNamespace() {
        return "MembClassDef";
    }

	public Paginater findMembClassDef(Map<String, Object> params, int pageNumber,
			int pageSize) {

		return this.queryForPage("findMembClassDef", params, pageNumber, pageSize);
	}

	public List<MembClassDef> findByCardIssuer(Map<String, Object> params) {

		return this.queryForList("findByCardIssuer", params);
	}
}