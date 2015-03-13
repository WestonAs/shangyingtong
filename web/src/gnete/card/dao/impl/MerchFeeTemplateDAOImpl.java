package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.MerchFeeTemplateDAO;
import gnete.card.entity.MerchFeeTemplate;

@Repository
public class MerchFeeTemplateDAOImpl extends BaseDAOIbatisImpl implements MerchFeeTemplateDAO {

    public String getNamespace() {
        return "MerchFeeTemplate";
    }

	public Paginater findMerchFeeTemplate(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findMerchFeeTemplate", params, pageNumber, pageSize);
	}

	public List<MerchFeeTemplate> getMerchFeeTemplateList(
			Map<String, Object> params) {
		return this.queryForList("getMerchFeeTemplateList", params);
	}
}