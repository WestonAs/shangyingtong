package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.TransDtotal;

import java.util.List;
import java.util.Map;

public interface TransDtotalDAO extends BaseDAO {
	Paginater listByPage(Map<String, Object> params, int pageNumber, int pageSize);
	List<TransDtotal> list(Map<String, Object> params);
}