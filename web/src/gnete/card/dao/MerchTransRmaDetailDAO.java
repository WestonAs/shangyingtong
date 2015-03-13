package gnete.card.dao;

import java.util.Map;
import flink.util.Paginater;

public interface MerchTransRmaDetailDAO extends BaseDAO {
	Paginater findMerchTransRmaDetail(Map<String, Object> params, int pageNumber, int pageSize);
}