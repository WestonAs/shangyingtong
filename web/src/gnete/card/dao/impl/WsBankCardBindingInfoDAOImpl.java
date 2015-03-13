package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.WsBankCardBindingInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.WsBankCardBindingInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class WsBankCardBindingInfoDAOImpl extends BaseDAOIbatisImpl implements WsBankCardBindingInfoDAO {

	public String getNamespace() {
		return "WsBankCardBindingInfo";
	}

	@Override
	public Paginater findPage(WsBankCardBindingInfo record, List<BranchInfo> inCardBranches, int pageNumber,
			int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("record", record);
		params.put("inCardBranches", inCardBranches);
		return this.queryForPage("findPage", params, pageNumber, pageSize);
	}
}