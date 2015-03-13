package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.WxBankDepositReg;

import java.util.List;
import java.util.Map;

public interface WxBankDepositRegDAO extends BaseDAO {
	
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<WxBankDepositReg> findList(Map<String, Object> params);
}