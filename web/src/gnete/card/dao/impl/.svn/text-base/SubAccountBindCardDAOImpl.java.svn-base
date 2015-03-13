package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gnete.card.dao.SubAccountBindCardDAO;
import gnete.card.entity.SubAccountBindCard;
@Repository
public class SubAccountBindCardDAOImpl extends BaseDAOIbatisImpl implements SubAccountBindCardDAO {

    public String getNamespace() {
        return "SubAccountBindCard";
    }

	@Override
	public List<SubAccountBindCard> findBindList(Map<String, Object> params) {
		return queryForList("findBindCardByInfo", params);
	}

	@Override
	public void deleteBindByInfo(Map<String, Object> params) {
		super.delete("deleteByInfo", params);
	}
}