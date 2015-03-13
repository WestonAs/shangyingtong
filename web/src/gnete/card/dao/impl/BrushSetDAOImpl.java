package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.BrushSetDAO;
import gnete.card.entity.BrushSet;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class BrushSetDAOImpl extends BaseDAOIbatisImpl implements BrushSetDAO {

    public String getNamespace() {
        return "BrushSet";
    }
    
    public Paginater findBrushSet(Map<String, Object> params,
    		int pageNumber, int pageSize) {
    	return queryForPage("findBrushSet", params, pageNumber, pageSize);
    }

	public int deleteByPk(Long id) {
		return this.delete("deleteByPk", id);
	}

	public int deleteByDrawPrizeNo(Map params) {
		return this.delete("deleteByDrawPrizeNo", params);
	}

	public BrushSet findByDrawPrizeNo(Map params) {
		return (BrushSet)this.queryForObject("findByDrawPrizeNo", params);
	}

	public int updateByDrawPrizeNo(Map params) {
		return this.update("updateByDrawPrizeNo", params);
	}
}