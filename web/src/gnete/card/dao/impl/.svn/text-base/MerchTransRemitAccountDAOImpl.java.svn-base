package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Collections;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.MerchTransRemitAccountDAO;
import gnete.card.entity.MerchTransRemitAccount;
import flink.util.CommonHelper;

@Repository
public class MerchTransRemitAccountDAOImpl extends BaseDAOIbatisImpl implements MerchTransRemitAccountDAO {

	public String getNamespace() {
		return "MerchTransRemitAccount";
	}
	
	@SuppressWarnings("unchecked")
	public List<MerchTransRemitAccount> findMerchTransRemitAccount(Map<String, Object> params) {
		return this.queryForList("findMerchTransRemitAccount", params);
	}

	public Paginater findMerchTransRemitAccount(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findMerchTransRemitAccount", params, pageNumber, pageSize);
	}
	
	public boolean updateMerchTrans(List<MerchTransRemitAccount> remitAcctList) {
		this.updateBatch("updateRmaFile", remitAcctList);
		
		return true;
	}

	/**
	 *@description : <li>注意关键字必须对应大写方式</li>
	 */
	public Map findMerchTransMap(String param, String keyProperty) {
		if (CommonHelper.checkIsEmpty(param) || CommonHelper.checkIsEmpty(keyProperty)) {
			return Collections.EMPTY_MAP;
		}

		return this.getSqlRunner().queryForMap(getStatementName("findClear2PayTransRemitResult"), param,keyProperty.toUpperCase());
	}
}