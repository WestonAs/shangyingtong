package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.AccountSystemInfoDAO;
import gnete.card.entity.AccountSystemInfo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public class AccountSystemInfoDAOImpl extends BaseDAOIbatisImpl implements AccountSystemInfoDAO {

    public String getNamespace() {
        return "AccountSystemInfo";
    }
    
    public Paginater findAccountSystemInfo(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findAccountSystemInfo", params, pageNumber, pageSize);
    }

	@Override
	public List<AccountSystemInfo> findByInfos(Map<String, Object> params) {
		return this.queryForList("findAccountSystemInfo", params);
	}
}
