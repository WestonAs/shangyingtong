package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.PointSendApplyRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.PointSendApplyReg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class PointSendApplyRegDAOImpl extends BaseDAOIbatisImpl implements PointSendApplyRegDAO {

    public String getNamespace() {
        return "PointSendApplyReg";
    }
    
	@Override
	public Paginater findPage(PointSendApplyReg pointSendApplyReg, List<BranchInfo> inCardBranches,
			int pageNumber, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pointSendApplyReg", pointSendApplyReg);
		params.put("inCardBranches", inCardBranches);
    	return this.queryForPage("findPointSendApplyReg",params, pageNumber, pageSize);
	}
}