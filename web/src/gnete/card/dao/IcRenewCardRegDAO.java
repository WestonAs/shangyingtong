package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.IcRenewCardReg;

import java.util.List;
import java.util.Map;

/**
 * @File: IcRenewCardRegDAO.java
 *
 * @description: IC卡换卡登记薄操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-9-21 下午02:56:30
 */
public interface IcRenewCardRegDAO extends BaseDAO {
	
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<IcRenewCardReg> findList(Map<String, Object> params);
	
	IcRenewCardReg findByRandomSessionId(String randomSessionId);
}