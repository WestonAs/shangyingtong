package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.GdnhglIssuerCardStaticDAO;
import gnete.card.entity.GdnhglIssuerCardStatic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class GdnhglIssuerCardStaticDAOImpl extends BaseDAOIbatisImpl implements GdnhglIssuerCardStaticDAO {

	public String getNamespace() {
		return "StaticDataQry";
	}

	@Override
	public Paginater findGdnhglIssuerCardStatic(
			GdnhglIssuerCardStatic gdnhglIssuerCardStatic, int pageNumber,
			int pageSize) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("record", gdnhglIssuerCardStatic);
		return this.queryForPage("listGdnhglIssuerCardStatic", params, pageNumber, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<GdnhglIssuerCardStatic> findGdnhglIssuerCardStatic(
			GdnhglIssuerCardStatic gdnhglIssuerCardStatic) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("record", gdnhglIssuerCardStatic);
		return this.queryForList("listGdnhglIssuerCardStatic", params);
	}
}