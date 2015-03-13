package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MerchTransRmaDetailDAO;

@Repository
public class MerchTransRmaDetailDAOImpl extends BaseDAOIbatisImpl implements MerchTransRmaDetailDAO {

    public String getNamespace() {
        return "MerchTransRmaDetail";
    }

	public Paginater findMerchTransRmaDetail(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findMerchTransRmaDetail", params, pageNumber, pageSize);
	}
}