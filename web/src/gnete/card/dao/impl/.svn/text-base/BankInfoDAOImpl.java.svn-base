package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.BankInfoDAO;

@Repository
public class BankInfoDAOImpl extends BaseDAOIbatisImpl implements BankInfoDAO {

    public String getNamespace() {
        return "BankInfo";
    }
    
    public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("find", params, pageNumber, pageSize);
    }
}