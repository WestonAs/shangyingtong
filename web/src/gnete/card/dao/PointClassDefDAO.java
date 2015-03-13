package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.PointClassDef;

import java.util.List;
import java.util.Map;

public interface PointClassDefDAO extends BaseDAO {
	
	/* 
	 * 查询积分类型列表
	 */
	Paginater findPointClassDef(Map<String, Object> params, int pageNumber,
			int pageSize);

	/**
	 * 查询积分类型列表
	 * 
	 * @param params
	 * @return
	 */
	List<PointClassDef> findPtClassByJinst(Map<String, Object> params);
	
	/**
	 * 根据联名机构id，查询积分类型列表
	 * @return 如果jinstId为空，则直接返回null；
	 */
	List<PointClassDef> findPtClassByJinstId(String jinstId);
	
	/*
	 * 根据账号找到商户可用的积分类型列表
	 */
	public List<PointClassDef> getPtClassByCardOrMerch(Map<String, Object> params);

	List<PointClassDef> findPontClassList(Map<String, Object> params);

	
}