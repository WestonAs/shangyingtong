package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.RenewCardReg;

import java.util.List;
import java.util.Map;

public interface RenewCardRegDAO extends BaseDAO {
	
	Paginater findRenewCard(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<RenewCardReg> findRenewCardList(Map<String, Object> params);
	
}