package gnete.card.dao;

import java.util.List;
import java.util.Map;
import flink.util.Paginater;
import gnete.card.entity.ChlFee;

public interface ChlFeeDAO extends BaseDAO {
	public Paginater findChlFee(Map<String, Object> params, int pageNumber, int pageSize);
	
	public ChlFee getChlFee(Map<String, Object> params);
	
	/**
	 * 根据机构代码和计费类型得到平台运营手续费列表
	 * @param params
	 * @return
	 */
	public List<ChlFee> getChlFeeList(Map<String, Object> params);
	
}