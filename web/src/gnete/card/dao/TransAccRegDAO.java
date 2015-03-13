package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.TransAccReg;

public interface TransAccRegDAO extends BaseDAO {
	public Paginater findTransAccReg(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	// 查询卡转账登记簿列表
	public List<TransAccReg> findTransAccReg(Map<String, Object> params);
}