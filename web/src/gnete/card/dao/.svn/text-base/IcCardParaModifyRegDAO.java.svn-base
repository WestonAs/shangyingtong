package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.IcCardParaModifyReg;

/**
 * @File: IcCardParaModifyRegDAO.java
 *
 * @description: IC卡卡片参数修改登记簿操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-9-11 上午10:34:07
 */
public interface IcCardParaModifyRegDAO extends BaseDAO {
	
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<IcCardParaModifyReg> findList(Map<String, Object> params);
	
	IcCardParaModifyReg findByRandomSessionId(String randomSessionId);
}