package gnete.card.dao;

import java.util.List;
import java.util.Map;
import flink.util.Paginater;
import gnete.card.entity.MerchFeeTemplateDetail;

public interface MerchFeeTemplateDetailDAO extends BaseDAO {
	public Paginater findMerchFeeTemplateDetail(Map<String, Object> params, int pageNumber,
    		int pageSize);
	
	/**
	 * 获得商户模板明细列表
	 * @param params
	 * @return
	 */
	List<MerchFeeTemplateDetail> getMerchFeeTemplateDetailList(Map<String, Object> params);
}