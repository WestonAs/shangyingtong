package gnete.card.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.Waitsinfo;
import gnete.card.msg.MsgType;

@Repository
public class WaitsinfoDAOImpl extends BaseDAOIbatisImpl implements WaitsinfoDAO {

    public String getNamespace() {
        return "Waitsinfo";
    }
    
    public List<Waitsinfo> findUndoForWeb() {
    	return this.queryForList("findUndoForWeb");
    }

	public Waitsinfo findWaitsinfo(String msgType, Long refId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("msgType", msgType);
		params.put("refId", refId);
		return (Waitsinfo) this.queryForObject("findWaitsinfo", params);
	}
	
	public int updateWebStatus(Map<String, Object> params) {
		return this.update("updateWebStatus", params);
	}
	
	public List<Waitsinfo> findUndoForWebPage(int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (pageNum < 1) {
			pageNum = 1;
		}
		
		params.put("offsetIndex", (pageNum - 1) * pageSize);
		params.put("toIndex", pageNum * pageSize);
		return this.queryForList("findUndoForWebPage", params);
	}
}