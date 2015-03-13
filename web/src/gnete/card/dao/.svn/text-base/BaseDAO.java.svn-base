package gnete.card.dao;

import java.util.List;

/**
 * Title: BaseDao
 * 
 * @description: 基础dao接口, 定义简单的curd 操作.
 * 
 * @copyright: (c) 2008 ylink inc.
 * @author aps-mhc
 * @version 1.0
 * @since 1.0 2008-07-01
 */
public interface BaseDAO {
	/**
	 * 添加新记录
	 * 
	 * @param object
	 * @return pk
	 */
	Object insert(Object object);
	
	/**
	 * 批量添加 
	 * @param list
	 */
	void insertBatch(final List list);
	
	/**
	 * 更新记录.
	 * 
	 * @param object
	 * @return 
	 */
	int update(Object object);
	
	/**
	 * 删除记录.
	 * 
	 * @param pk
	 */
	int delete(Object pk);
	
	/**
	 * 根据pk 查找记录.
	 * 
	 * @param pk
	 * @return
	 */
	Object findByPk(Object pk);
	
	/**
	 * 根据pk 查找记录.
	 * 
	 * @param pk
	 * @return
	 */
	Object findByPkWithLock(Object pk);
	
	/**
	 * 根据pk 查看记录是否存在.
	 * 
	 * @param pk
	 * @return
	 */
	boolean isExist(Object pk);
	
	/**
	 * 获取所有记录
	 * 
	 * @return
	 */
	List findAll();
	
	/**
	 * 
	  * @description：
	  * @param operate
	  * @param list  
	  * @version: 2010-12-8 下午08:12:33
	  * @See:
	 */
	void insertBatch(String operate, List list);
	
	/**
	 * 
	  * @description：批量更新
	  * @param list  
	  * @version: 2010-12-8 下午08:04:52
	  * @See:
	 */
	void updateBatch(List list);
	
	
	
	/**
	 * 
	  * @description：
	  * @param operate
	  * @param list  
	  * @version: 2010-12-8 下午08:12:57
	  * @See:
	 */
	void updateBatch(String operate, List list);
	
}
