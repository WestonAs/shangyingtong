package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.LogoConfigDAO;

@Repository
public class LogoConfigDAOImpl extends BaseDAOIbatisImpl implements LogoConfigDAO {

    public String getNamespace() {
        return "LogoConfig";
    }
    
    public Paginater findLogoConfigPage(Map<String, Object> params,
    		int pageNumber, int pageSize) {
    	return this.queryForPage("findLogoConfigPage", params, pageNumber, pageSize);
    }
}