package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MakeCardRegDAO;
import gnete.card.entity.MakeCardReg;

@Repository
public class MakeCardRegDAOImpl extends BaseDAOIbatisImpl implements
		MakeCardRegDAO {

	public String getNamespace() {
		return "MakeCardReg";
	}

	public Paginater findMakeCardReg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findMakeCardReg", params, pageNumber, pageSize);
	}
	
	public List<MakeCardReg> findList(Map<String, Object> params) {
		return this.queryForList("findMakeCardReg", params);
	}

	public List<MakeCardReg> findByBranchCode(Map<String, Object> params) {
		return this.queryForList("findByBranchCode", params);
	}

	public List<MakeCardReg> findByCardSubtype(String cardSubtype) {
		return this.queryForList("findByCardSubtype", cardSubtype);
	}

	public boolean isExsitMakeName(String makeName) {
		return (Long) super.queryForObject("findByMakeName", makeName) > 0;
	}
}