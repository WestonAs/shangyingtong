package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.MembRegDAO;
import gnete.card.entity.MembReg;

@Repository
public class MembRegDAOImpl extends BaseDAOIbatisImpl implements MembRegDAO {

	@Override
    public String getNamespace() {
        return "MembReg";
    }

    @Override
	public Paginater findMembReg(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findMembReg", params, pageNumber, pageSize);
	}
	
	@Override
	public Paginater findMembRegToExport(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findMembRegToExport", params, pageNumber, pageSize);
	}
	
	@Override
	public List<MembReg> findList(Map<String, Object> params) {
		return this.queryForList("findMembReg", params);
	}
}