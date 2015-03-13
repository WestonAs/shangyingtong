package gnete.card.dao.impl;

import gnete.card.dao.ProcDAO;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ProcDAOImpl extends BaseDAOIbatisImpl implements ProcDAO {

    public String getNamespace() {
        return "Proc";
    }

    public Map<String, Object> spCardDayEnd(String workdate) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("p_workdate", workdate);
    	params.put("p_errorcode", null);
    	params.put("p_errordesc", null);
    	this.queryForObject("spCardDayendProc", params);
    	return params;
    }

	public Map<String, Object> spCardSettDayEnd(String workdate) {
		Map<String, Object> params = new HashMap<String, Object>();
    	params.put("p_workdate", workdate);
    	params.put("p_errorcode", null);
    	params.put("p_errordesc", null);
    	this.queryForObject("spCardSettDayEndProc", params);
    	return params;
	}
    
}