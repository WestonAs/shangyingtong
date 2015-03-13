package gnete.card.dao;

import java.util.Map;

public interface ProcDAO extends BaseDAO {
	
	// 手动日切
	public Map<String, Object> spCardDayEnd(String workdate);

	// 手动计费
	public Map<String, Object> spCardSettDayEnd(String workdate);
	
}