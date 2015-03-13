package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.CardDeferBatReg;

public interface CardDeferBatRegDAO extends BaseDAO {
	
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<CardDeferBatReg> findList(Map<String, Object> params);
}