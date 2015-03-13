package gnete.card.service;

import flink.util.Paginater;
import gnete.card.entity.DepositBatReg;
import gnete.card.entity.DepositReg;
import gnete.card.entity.RebateCardReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @File: DepositService.java
 *
 * @description: 充值相关的业务逻辑处理
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-3-16
 */
public interface DepositService {
	
	/**
	 * 添加充值登记
	 * @param depositReg	充值登记信息
	 * @return
	 * @throws BizException
	 */
	long addDepositReg(DepositReg depositReg,UserInfo user, String serialNo) throws BizException;

	/**
	 * 添加批量充值登记
	 * 
	 * @param depositBatReg
	 *            批量充值登记信息
	 * @param depositBatRegList
	 * @param user
	 * @return
	 * @throws BizException
	 */
	long addDepositBatReg(DepositReg depositReg, DepositBatReg depositBatReg, 
			List<RebateCardReg> rebateCardList, UserInfo user, String serialNo) throws BizException;

	/**
	 * 预充值撤销
	 * @Date 2013-3-29下午2:45:42
	 * @return void
	 */
	void addDepositPreCancel(DepositBatReg depositReg, UserInfo user) throws BizException ;
	
	Paginater findDepositRegCancelPage(Map<String, Object> params,int pageNumber, int pageSize) throws BizException;
	/**
	 * 修改批量充值登记
	 * 
	 * @param depositBatReg
	 *            批量充值登记信息
	 * @param string
	 * @return
	 * @throws BizException
	 */
	boolean modifyDepositBatReg(DepositBatReg depositBatReg, String modifyUserId) throws BizException;

	/**
	 * 删除批量充值登记
	 * 
	 * @param string
	 * @return
	 * @throws BizException
	 */
	boolean deleteDepositBatReg(long depositBatchId) throws BizException;

	/**
	 * 激活预充值
	 * 
	 * @param depositReg
	 * @param sessionUser
	 */
	boolean activate(DepositReg depositReg, UserInfo sessionUser) throws BizException;

	/**
	 * 以文件上传的方式批量充值
	 * 
	 * @param file
	 *            要充值的文件
	 * @param isOldFileFmt
	 *            是否是旧文件格式（积分卡一代）
	 * @param depositReg
	 *            充值登记簿
	 * @param totalNum
	 *            总记录数
	 * @param user
	 *            登录用户信息
	 * @param limitId
	 *            权限点
	 * @return
	 * @throws BizException
	 */
	DepositReg addDepositBatRegFile(File file, boolean isOldFileFmt, DepositReg depositReg,
			long totalNum, UserInfo user, String limitId, String serialNo) throws BizException;
	
	/**
	 * 充值撤销处理
	 * @param depositBatchId
	 * @param user
	 * @return
	 * @throws BizException
	 */
	DepositReg depositCancel(String depositBatchId, UserInfo user) throws BizException;
	
	/**
	 * 取消充值的处理（对于未激活的充值记录才行）
	 * @param depositReg
	 * @param user
	 * @throws BizException
	 */
	void depositRevoke(String depositBatchId, UserInfo user) throws BizException;

}
