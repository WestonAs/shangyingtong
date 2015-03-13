package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.AppRegDAO;

@Repository
public class AppRegDAOImpl extends BaseDAOIbatisImpl implements AppRegDAO {

	public String getNamespace() {
		return "AppReg";
	}

	public Paginater findAppRegPage(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return queryForPage("findAppRegPage", params, pageNumber, pageSize);
	}
	
	public boolean isInOrWithDrawCardNo(Map<String, Object> parmas) {
		return (Long) queryForObject("isInOrWithDrawCardNo" ,parmas) > 0;
	}
}