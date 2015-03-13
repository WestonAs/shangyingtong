package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.WxCashierInfo;

import java.util.List;
import java.util.Map;

public interface WxCashierInfoDAO extends BaseDAO {

	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);

	List<WxCashierInfo> findList(Map<String, Object> params);
}