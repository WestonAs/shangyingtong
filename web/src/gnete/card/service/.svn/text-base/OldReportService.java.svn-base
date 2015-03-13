package gnete.card.service;

import java.util.Map;

import gnete.card.entity.OldReportPara;
import gnete.etc.BizException;

/**
 * @File: OldReportService.java
 *
 * @description: 旧报表相关业务处理
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-24
 */
public interface OldReportService {
	/**
	 * 添加旧报表权限参数定义
	 * @param oldReportPara
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean addOldReportPara(OldReportPara oldReportPara, String userId) throws BizException ;
	
	/**
	 * 修改旧报表权限参数定义
	 * @param oldReportPara
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean modifyOldReportPara(OldReportPara oldReportPara, String userId) throws BizException;

	/**
	 * 删除旧报表权限参数定义
	 * @param issCode
	 * @return
	 * @throws BizException
	 */
	boolean deleteOldReportPara(String issCode) throws BizException;
	
	/**
	 * 生成过期商户明细报表
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>settDate:String 清算日期</li>
	 * 	<li>cardIssuer:String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	void generateOldExpireCardReport(String reportPath, Map<String, Object> params) throws BizException;

	/**
	 * 生成卡激活报表
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>settDate:String 清算日期</li>
	 * 	<li>cardIssuer:String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	void generateOldActiveCardReport(String reportPath, Map<String, Object> params) throws BizException;
	
	/**
	 * 生成商户明细报表
	 * @param params
	 * 包括
	 * <ul>
	 * 	<li>settDate:String 清算日期</li>
	 * 	<li>cardIssuer:String 机构代码</li>
	 * </ul>
	 * @param reportPath 报表保存路径
	 * @return
	 * @throws BizException
	 */
	void generateOldMerchDetailReport(String reportPath, Map<String, Object> params) throws BizException;
}
