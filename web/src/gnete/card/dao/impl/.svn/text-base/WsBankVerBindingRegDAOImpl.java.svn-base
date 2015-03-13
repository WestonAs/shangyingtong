package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.WsBankVerBindingRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.WsBankVerBindingReg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class WsBankVerBindingRegDAOImpl extends BaseDAOIbatisImpl implements WsBankVerBindingRegDAO {

	public String getNamespace() {
		return "WsBankVerBindingReg";
	}

	@Override
	public Paginater findPage(WsBankVerBindingReg record, List<BranchInfo> inCardBranches, int pageNumber,
			int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("record", record);
		params.put("inCardBranches", inCardBranches);
		return this.queryForPage("findPage", params, pageNumber, pageSize);
	}
}