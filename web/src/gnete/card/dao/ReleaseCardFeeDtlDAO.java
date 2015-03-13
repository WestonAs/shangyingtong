package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.ReleaseCardFeeDtl;
import java.util.List;
import java.util.Map;

public interface ReleaseCardFeeDtlDAO extends BaseDAO {
	
	public Paginater findReleaseCardFeeDtl(Map<String, Object> parma, int pageNumber,
    		int pageSize);
	
	/**
	 * 获得发卡机构分段手续费明细
	 * @param params
	 * @return
	 */
	List<ReleaseCardFeeDtl> getReleaseCardFeeDtlList(Map<String, Object> params);
}