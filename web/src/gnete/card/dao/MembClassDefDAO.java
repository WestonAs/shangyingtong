package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.MembClassDef;

public interface MembClassDefDAO extends BaseDAO {
	public Paginater findMembClassDef(Map<String, Object> params, int pageNumber,
			int pageSize);
	public List<MembClassDef> findByCardIssuer(Map<String, Object> params);
	
}