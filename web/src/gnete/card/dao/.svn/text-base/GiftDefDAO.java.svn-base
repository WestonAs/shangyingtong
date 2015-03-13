package gnete.card.dao;

import java.util.List;
import java.util.Map;
import flink.util.Paginater;
import gnete.card.entity.GiftDef;

public interface GiftDefDAO extends BaseDAO {
	
	public Paginater findGift(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	public Paginater findAvalGift(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	public List<GiftDef> findGiftByPtClass(Map<String, Object> params);
}