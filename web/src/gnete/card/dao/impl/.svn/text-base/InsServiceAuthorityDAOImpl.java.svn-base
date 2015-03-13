package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.InsServiceAuthorityDAO;
import gnete.card.entity.InsServiceAuthority;

@Repository
public class InsServiceAuthorityDAOImpl extends BaseDAOIbatisImpl implements InsServiceAuthorityDAO {

    public String getNamespace() {
        return "InsServiceAuthority";
    }

	public Paginater findInsServiceAuthority(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findInsServiceAuthority", params, pageNumber, pageSize);
	}
	
	public List<InsServiceAuthority> getInsServiceAuthority(
			Map<String, Object> params) {
		return this.queryForList("findInsServiceAuthority", params);
	}
}