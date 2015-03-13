package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MembLevelChgRegDAO;
import gnete.card.entity.MembLevelChgReg;

@Repository
public class MembLevelChgRegDAOImpl extends BaseDAOIbatisImpl implements MembLevelChgRegDAO {

	public String getNamespace() {
		return "MembLevelChgReg";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findMembLevelChgReg", params, pageNumber, pageSize);
	}

	@Override
	public List<MembLevelChgReg> findList(Map<String, Object> params) {
		return this.queryForList("findMembLevelChgReg", params);
	}

}