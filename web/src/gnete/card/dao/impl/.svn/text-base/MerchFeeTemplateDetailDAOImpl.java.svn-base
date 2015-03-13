package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MerchFeeTemplateDetailDAO;
import gnete.card.entity.MerchFeeTemplateDetail;

@Repository
public class MerchFeeTemplateDetailDAOImpl extends BaseDAOIbatisImpl implements MerchFeeTemplateDetailDAO {

    public String getNamespace() {
        return "MerchFeeTemplateDetail";
    }

	public Paginater findMerchFeeTemplateDetail(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findMerchFeeTemplateDetail", params, pageNumber, pageSize);
	}

	public List<MerchFeeTemplateDetail> getMerchFeeTemplateDetailList(
			Map<String, Object> params) {
		return this.queryForList("getMerchFeeTemplateDetailList", params);
	}
}