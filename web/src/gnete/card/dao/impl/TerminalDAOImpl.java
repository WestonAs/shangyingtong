package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.TerminalDAO;
import gnete.card.entity.Terminal;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class TerminalDAOImpl extends BaseDAOIbatisImpl implements TerminalDAO {

    public String getNamespace() {
        return "Terminal";
    }
    
	public String getNextNewTermId() {
		String termId = null;
		int maxLoopCnt = 10000;// 最多循环10000次，防止死循环。
		int currCnt = 0;
		do {
			termId = (String) this.queryForObject("getNextNewTermId");
		} while (++currCnt <= maxLoopCnt && this.isExist(termId));

		return termId;
	}
    
    public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("find", params, pageNumber, pageSize);
    }
    
    public List<Terminal> find(Map<String, Object> params) {
    	return this.queryForList("find", params);
    }
    
}