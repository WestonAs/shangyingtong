package gnete.card.dao;

import java.util.List;
import java.util.Map;
import flink.util.Paginater;
import gnete.card.entity.InsServiceAuthority;

/**
 * @File: InsServiceAuthorityDAO.java
 *
 * @description: 业务权限控制
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: LiZhenBin
 * @version: 1.0
 * @since 1.0 2011-7-1
 */
public interface InsServiceAuthorityDAO extends BaseDAO {
	
	Paginater findInsServiceAuthority(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	List<InsServiceAuthority> getInsServiceAuthority(Map<String, Object> params);
}