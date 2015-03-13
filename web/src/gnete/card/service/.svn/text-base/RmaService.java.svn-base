package gnete.card.service;

import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.RmaFilePara;
import gnete.etc.BizException;

/**
 * @File: RmaService.java
 *
 * @description: 划付相关业务处理
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-8-31
 */
public interface RmaService {
	
	/**
	 * 添加划付文件权限参数定义
	 * @param rmaFilePara
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean addRmaFilePara(RmaFilePara rmaFilePara, String userId) throws BizException ;
	
	/**
	 * 修改划付文件权限参数定义
	 * @param rmaFilePara
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean modifyRmaFilePara(RmaFilePara rmaFilePara, String userId) throws BizException;

	/**
	 * 删除划付文件权限参数定义
	 * @param issCode
	 * @return
	 * @throws BizException
	 */
	boolean deleteRmaFilePara(String issCode) throws BizException;

	/**
	 * 取得FTP中划付文件列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	Paginater getRmaFileList(Map<String, Object> params, int pageNumber,
			int pageSize) throws BizException;
	
	/**
	 * 取得FTP中网银通划付文件列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	Paginater getBranchRmaFileList(Map<String, Object> params, int pageNumber,
			int pageSize) throws BizException;
	
	/**
	 * 从FTP上下载划付txt文件
	 * @param fullPath
	 * @return
	 * @throws BizException
	 */
	boolean downloadRmaFile(String fullPath) throws BizException;
	
	/**
	 * 针对网银通的划付文件列表
	 */
	boolean downloadBranchRmaFile(String fullPath) throws BizException;
}
