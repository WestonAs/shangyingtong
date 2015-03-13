package gnete.card.dao;

import java.util.List;

public interface UserSellChgDAO extends BaseDAO {

	/**
	 * 根据用户查找明细
	 * @param userId
	 * @return
	 */
	List findByUser(String userId);
}