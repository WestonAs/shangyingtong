package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.IssuerSubTransDtotalDAO;
import gnete.card.entity.IssuerSubTransDtotal;

@Repository
public class IssuerSubTransDtotalDAOImpl extends BaseDAOIbatisImpl implements IssuerSubTransDtotalDAO {

	public String getNamespace() {
		return "IssuerSubTransDtotal";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findIssuerSubTransDtotal", params, pageNumber, pageSize);
	}

//	@Override
//	public List<IssuerSubTransDtotal> findIssuerSubTransDtotal(Map<String, Object> params) {
//		List<IssuerSubTransDtotal> issuerSubTransDtotals = this.queryForList("findIssuerSubTransDtotalList", params);
//		return issuerSubTransDtotals.size() > 0 ? issuerSubTransDtotals : null;
//	}

}