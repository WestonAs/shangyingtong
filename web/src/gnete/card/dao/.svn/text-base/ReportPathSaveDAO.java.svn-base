package gnete.card.dao;

import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.ReportPathSave;

/**
 * @File: ReportPathSaveDAO.java
 *
 * @description: 自动生成报表文件路径保存表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-9
 */
public interface ReportPathSaveDAO extends BaseDAO {
	
	/**
	 * 分页查看报表文件的列表
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	/**
	 * 根据传入的值删除相关的记录
	 * @param params
	  * 包括:
	 * <ul>
	 * 	<li>reportDate:String 报表日期【必填项】</li>
	 * 	<li>merchantNo:String 商户或机构代码</li>
	 * 	<li>reportType:String 报表类型</li>
	 * </ul>
	 * @return
	 */
	boolean deleteByMap(Map<String, Object> params);

	ReportPathSave findByPk(String reportType, String merchantNo, String reportDate);
}