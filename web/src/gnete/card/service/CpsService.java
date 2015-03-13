package gnete.card.service;

import java.io.File;
import gnete.card.entity.BDPtDtotal;
import gnete.card.entity.CpsPara;
import gnete.card.entity.CpsParaKey;
import gnete.card.entity.MembImportReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

/**
 * @File: CpsService.java
 *
 * @description: 代收付相关业务处理
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-8-17
 */
public interface CpsService {
	
	/**
	 * 添加代收付权限参数定义
	 * @param cpsPara
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean addCpsPara(CpsPara cpsPara, String userId) throws BizException ;
	
	/**
	 * 修改代收付权限参数定义
	 * @param cpsPara
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean modifyCpsPara(CpsPara cpsPara, String userId) throws BizException;

	/**
	 * 删除代收付权限参数定义
	 * @param key
	 * @return
	 * @throws BizException
	 */
	boolean deleteCpsPara(CpsParaKey key) throws BizException;
	
	/**
	 * 发送代收付手动发起命令
	 * @param msgType
	 * @param refId
	 * @param userCode
	 * @return
	 * @throws BizException
	 */
	void sendCtManualMsg(BDPtDtotal bdPtDtotal, String branchCode, String userCode) throws BizException ;

	/**
	 * 发送代收付确认命令
	 * @param msgType
	 * @param refId
	 * @param userCode
	 * @return
	 * @throws BizException
	 */
	Long sendCtConfirmMsg(BDPtDtotal bdPtDtotal, String branchCode, String userCode) throws BizException ;
	
	/**
	 * 添加会员注册文件导入登记
	 * @param file 文件名
	 * @param importReg 导入登记簿
	 * @param totalNum 总记录数
	 * @param user
	 * @return
	 * @throws BizException
	 */
	MembImportReg addMembImportReg(File file, MembImportReg importReg, UserInfo user)
			throws BizException;
}
