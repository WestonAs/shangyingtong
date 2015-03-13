package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.entity.PointClassDef;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class PointClassDefDAOImpl extends BaseDAOIbatisImpl implements PointClassDefDAO {
	@Override
    public String getNamespace() {
        return "PointClassDef";
    }
    @Override
    public Paginater findPointClassDef(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPointClassDef", params, pageNumber, pageSize);
    }
    @Override
	public List<PointClassDef> findPtClassByJinst(Map<String, Object> params) {
		return this.queryForList("findPtClassByJinst", params);
	}
	
	@Override
	public List<PointClassDef> findPtClassByJinstId(String jinstId) {
		if(StringUtils.isBlank(jinstId)){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jinstId", jinstId);
		return this.queryForList("findPtClassByJinst", params);
	}
	@Override
	public List<PointClassDef> getPtClassByCardOrMerch(Map<String, Object> params) {
		return this.queryForList("getPtClassByCardOrMerch", params);
	}
	@Override
	public List<PointClassDef> findPontClassList(Map<String, Object> params) {
		return this.queryForList("findPointClassDef", params);
	}
    
}