package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.WsMsgTypeCtrlDAO;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class WsMsgTypeCtrlDAOImpl extends BaseDAOIbatisImpl implements WsMsgTypeCtrlDAO {

    public String getNamespace() {
        return "WsMsgTypeCtrl";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }

}