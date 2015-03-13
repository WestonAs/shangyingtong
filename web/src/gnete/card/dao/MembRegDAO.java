package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.MembReg;

public interface MembRegDAO extends BaseDAO {
	
	Paginater findMembReg(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<MembReg> findList(Map<String, Object> params);

	Paginater findMembRegToExport(Map<String, Object> params, int pageNumber, int pageSize);
}