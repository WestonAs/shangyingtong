package gnete.card.dao;

import java.util.List;
import java.util.Map;
import flink.util.Paginater;
import gnete.card.entity.BaodePointExcPara;

public interface BaodePointExcParaDAO extends BaseDAO {
	
	Paginater findBaodePointExcPara(Map<String, Object> params, int pageNumber,
			int pageSize);

	/**
	 * 取得保得积分返还参数列表
	 * @param params
	 * @return
	 */
	List<BaodePointExcPara> findBaodePointExcParaList(Map<String, Object> params);
}