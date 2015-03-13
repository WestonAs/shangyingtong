package gnete.card.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.CommonHelper;
import gnete.card.dao.MerchTransRmaDAO;
import gnete.card.entity.MerchTransRma;

@Repository
public class MerchTransRmaDAOImpl extends BaseDAOIbatisImpl implements MerchTransRmaDAO {

    public String getNamespace() {
        return "MerchTransRma";
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
	
	public List<MerchTransRma> findMerchTransRma(Map<String, Object> params) {
		return this.queryForList("findMerchTransRma", params);
	}

	public boolean updateMerchTrans(List<MerchTransRma> merchTransList) {
		this.updateBatch("updateRmaFile", merchTransList);
		return true;
	}
}