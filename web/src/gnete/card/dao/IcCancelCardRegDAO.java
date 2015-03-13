package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.IcCancelCardReg;

import java.util.List;
import java.util.Map;

/**
 * @File: IcCancelCardRegDAO.java
 *
 * @description: IC卡退卡登记簿操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: 
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-10-9 下午03:50:56
 */
public interface IcCancelCardRegDAO extends BaseDAO {

	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<IcCancelCardReg> findList(Map<String, Object> params);
	
	IcCancelCardReg findByRandomSessionId(String randomSessionId);
}