package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.AddMagRegDAO;

@Repository
public class AddMagRegDAOImpl extends BaseDAOIbatisImpl implements AddMagRegDAO {

    public String getNamespace() {
        return "AddMagReg";
    }

	public Paginater findAddMag(Map<String, Object> params, int pageNumber,
			int pageSize) {
		
		return this.queryForPage("findAddMag", params, pageNumber, pageSize);
	}
	

}