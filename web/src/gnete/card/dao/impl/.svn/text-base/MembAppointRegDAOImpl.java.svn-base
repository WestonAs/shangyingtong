package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.MembAppointRegDAO;
import gnete.card.entity.MembAppointReg;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class MembAppointRegDAOImpl extends BaseDAOIbatisImpl implements MembAppointRegDAO {

	public String getNamespace() {
		return "MembAppointReg";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findMembAppointReg", params, pageNumber, pageSize);
	}

	public List<MembAppointReg> findList(Map<String, Object> params) {
		return this.queryForList("findMembAppointReg", params);
	}
}