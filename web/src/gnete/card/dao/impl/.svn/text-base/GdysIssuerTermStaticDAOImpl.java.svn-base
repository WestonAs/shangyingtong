package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.GdysIssuerTermStaticDAO;
import gnete.card.entity.GdysIssuerTermStatic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class GdysIssuerTermStaticDAOImpl extends BaseDAOIbatisImpl implements GdysIssuerTermStaticDAO {

	public String getNamespace() {
		return "StaticDataQry";
	}

	@Override
	public Paginater findGdysIssuerTermStatic(GdysIssuerTermStatic gdysIssuerTermStatic,
			int pageNumber, int pageSize) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("record", gdysIssuerTermStatic);
		return this.queryForPage("listGdysIssuerTermStatic", params, pageNumber, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<GdysIssuerTermStatic> findGdysIssuerTermStatic(
			GdysIssuerTermStatic gdysIssuerTermStatic) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("record", gdysIssuerTermStatic);
		return this.queryForList("listGdysIssuerTermStatic", params);
	}


}