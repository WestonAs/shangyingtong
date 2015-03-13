package gnete.card.dao;

import flink.util.Paginater;
import java.util.Map;

public interface CardMerchFeeDAO extends BaseDAO {
	
	public Paginater findCardMerchFee(Map<String, Object> parma, int pageNumber,
    		int pageSize);
	
}