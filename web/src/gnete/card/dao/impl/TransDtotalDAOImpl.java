package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.TransDtotalDAO;
import gnete.card.entity.TransDtotal;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class TransDtotalDAOImpl extends BaseDAOIbatisImpl implements TransDtotalDAO {

	@Override
	public String getNamespace() {
		return "TransDtotal";
	}

	@Override
	public Paginater listByPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("listTransDtotals", params, pageNumber, pageSize);
	}
	
	@Override
	public List<TransDtotal> list(Map<String, Object> params) {
		return (List<TransDtotal>)this.queryForList("listTransDtotals", params);
	}

}