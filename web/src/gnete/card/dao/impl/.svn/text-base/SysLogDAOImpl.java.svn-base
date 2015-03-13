package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.SysLogDAO;
import gnete.card.entity.SysLog;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class SysLogDAOImpl extends BaseDAOIbatisImpl implements SysLogDAO {

    public String getNamespace() {
        return "SysLog";
    }
    
    public Paginater findLog(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findLog", params, pageNumber, pageSize);
    }

	public Paginater findSettLog(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findSettLog", params, pageNumber, pageSize);
	}
	
	public SysLog findByPkFromJFLink(Long id) {
		return (SysLog) this.queryForObject("findByPkFromJFLink", id);
	}

	public int updateJFLink(SysLog sysLog) {
		return this.update("updateJFLink", sysLog);
	}
	
}