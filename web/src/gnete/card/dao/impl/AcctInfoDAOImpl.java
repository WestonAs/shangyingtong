package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.entity.AcctInfo;

@Repository
public class AcctInfoDAOImpl extends BaseDAOIbatisImpl implements AcctInfoDAO {

    public String getNamespace() {
        return "AcctInfo";
    }

	public Paginater findAcctInfo(Map<String, Object> params, int pageNumber,
			int pageSize) {

		return this.queryForPage("findAcctInfo", params, pageNumber, pageSize);
		
	}

	public AcctInfo findAcctInfoByCardId(String cardId) {
		
		return (AcctInfo) this.queryForObject("findAcctInfoByCardId",cardId);
	}

	public AcctInfo findByPkWithCheck(Map<String, Object> params) {
		return (AcctInfo) this.queryForObject("findByPkWithCheck", params);
	}

	public List<AcctInfo> findAcctInfoList(Map<String, Object> params) {
		return this.queryForList("findAcctInfo", params);
	}
}