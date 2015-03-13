package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MembAppointDetailRegDAO;
import gnete.card.entity.MembAppointDetailReg;

@Repository
public class MembAppointDetailRegDAOImpl extends BaseDAOIbatisImpl implements MembAppointDetailRegDAO {

	public String getNamespace() {
		return "MembAppointDetailReg";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findMembAppointDetailReg", params, pageNumber, pageSize);
	}

	public List<MembAppointDetailReg> findList(Map<String, Object> params) {
		return this.queryForList("findMembAppointDetailReg", params);
	}
	
	public long deleteByMembAppointRegId(Long membAppointRegId){
		return this.delete("deleteByMembAppointRegId", membAppointRegId);
	}
	
	public long updateStautsByMembAppointRegId(MembAppointDetailReg membAppointDetailReg){
		return this.delete("updateStautsByMembAppointRegId", membAppointDetailReg);
	}
}