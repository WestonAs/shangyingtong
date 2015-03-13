package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.PointSendDetailDAO;
import gnete.card.entity.state.PointSendDetailState;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class PointSendDetailDAOImpl extends BaseDAOIbatisImpl implements PointSendDetailDAO {

	@Override
	public String getNamespace() {
		return "PointSendDetail";
	}

	@Override
	public Paginater findPageByApplyId(Long applyId, int pageNumber, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("applyId", applyId);
		return this.queryForPage("findPointSendDetail", params, pageNumber, pageSize);
	}
	
	@Override
	public long count(Long applyId, String status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("applyId", applyId);
		params.put("status", status);
		Paginater p = this.queryForPage("findPointSendDetail", params, 1, 1);
		return p.getMaxRowCount();
	}

	@Override
	public void checkToPass(boolean isPass, boolean isBatchSelect, List<Integer> batchIds, Long applyId,
			String updateUser) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", isPass ? PointSendDetailState.PASSED.getValue() : PointSendDetailState.UNPASSED
				.getValue());
		params.put("updateUser", updateUser);
		params.put("updateTime", new Date());
		params.put("isBatchSelect", isBatchSelect);
		params.put("batchIds", batchIds);
		params.put("applyId", applyId);

		this.update("checkToPass", params);
	}
}