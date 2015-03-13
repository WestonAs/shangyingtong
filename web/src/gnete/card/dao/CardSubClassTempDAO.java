package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.CardSubClassTemp;

public interface CardSubClassTempDAO extends BaseDAO {
	/**
	 * 查询卡类型模板列表
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findSubClassTemp(Map<String, Object> params, int pageNumber,int pageSize);
	
	/**
	 * 查询卡类型模板列表
	 * @param params
	 * @return
	 */
	List<CardSubClassTemp> findList(Map<String, Object> params);
	
	/**
	 * 卡子类型名是否已经存在 
	 * @param cardSubClassName
	 * @return
	 */
	boolean isExsitCardSubClassName(String cardSubClassName);
	
	
}