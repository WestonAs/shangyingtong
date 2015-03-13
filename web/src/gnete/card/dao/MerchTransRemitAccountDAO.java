package gnete.card.dao;

import java.util.List;
import java.util.Map;
import flink.util.Paginater;
import gnete.card.entity.MerchTransRemitAccount;

public interface MerchTransRemitAccountDAO extends BaseDAO {
	Paginater findMerchTransRemitAccount(Map<String, Object> params, int pageNumber, int pageSize);

	List<MerchTransRemitAccount> findMerchTransRemitAccount(Map<String, Object> params);

	Map findMerchTransMap(String param, String keyProperty);

	boolean updateMerchTrans(List<MerchTransRemitAccount> remitAcctList);
}