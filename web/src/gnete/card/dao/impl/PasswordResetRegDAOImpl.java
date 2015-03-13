package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.PasswordResetRegDAO;

@Repository
public class PasswordResetRegDAOImpl extends BaseDAOIbatisImpl implements PasswordResetRegDAO {

    public String getNamespace() {
        return "PasswordResetReg";
    }

	public Paginater findPwReset(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findPwReset", params, pageNumber, pageSize);
	}
}