package gnete.card.service;

import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.CardBinPreReg;
import gnete.card.entity.CardBinReg;
import gnete.card.entity.FenzhiCardBinReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

/**
 * @File: CardBinService.java
 *
 * @description: 卡BIN分级管理service
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-10-10
 */
public interface CardBinService {
	
	//*==================================== 卡BIN前三位管理 ==========================================
	/**
	 * 查询列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardBinPrexPage(Map<String, Object> params, int pageNumber, int pageSize);

	/**
	 * 查询明细
	 * 
	 * @param pk
	 *            主键
	 * @return
	 */
	CardBinPreReg findCardBinPrexDetail(String pk);

	/**
	 * 新增
	 * 
	 * @param balanceReportSet
	 * @param branches
	 *            发卡机构数组
	 * @param user
	 *            登录用户信息
	 * @return
	 * @throws BizException
	 */
	void addCardBinPrexReg(CardBinPreReg cardBinPreReg, UserInfo user) throws BizException;
	
	/**
	 * 判断指定的卡BIN前三位是否已经分配下去
	 * @param cardBinPrex
	 * @return
	 * @throws BizException
	 */
	boolean isExistCardBinPrex(String cardBinPrex) throws BizException;
	
	/**
	 * 卡BIN前三位分配登记审核通过的相关处理
	 * @param cardBinPreReg
	 * @param userId
	 * @throws BizException
	 */
	void checkCardBinPrexReg(CardBinPreReg cardBinPreReg, String userId) throws BizException;
	
	//*==================================== 分支机构卡BIN管理==========================================
	
	/**
	 * 查询运营机构卡BIN分配登记列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findFenzhiCardBinRegPage(Map<String, Object> params, int pageNumber, int pageSize);

	/**
	 * 查询运营机构卡BIN列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findFenzhiCardBinPage(Map<String, Object> params, int pageNumber, int pageSize);

	/**
	 * 查询运营机构卡BIN分配登记薄明细
	 * 
	 * @param pk
	 *            主键
	 * @return
	 */
	FenzhiCardBinReg findFenzhiCardBinRegDetail(String pk);

	/**
	 * 新增运营机构卡BIN分配登记信息
	 * @param fenzhiCardBinReg
	 * @param user
	 * @throws BizException
	 */
	void addFenzhiCardBinReg(FenzhiCardBinReg fenzhiCardBinReg, UserInfo user) throws BizException;
	
	/**
	 * 运营机构卡BIN分配登记审核通过的相关处理
	 * @param cardBinPreReg
	 * @param userId
	 * @throws BizException
	 */
	void checkFenzhiCardBinReg(FenzhiCardBinReg fenzhiCardBinReg, String userId) throws BizException;
	
	//=========================================     发卡机构卡BIN管理  ===========================================
	/**查询发卡机构卡BIN申请列表
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardBinRegPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查询发卡机构卡BIN列表
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardBinPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 新增卡BIN登记
	 * @param cardBinReg
	 * @param user
	 * @throws BizException
	 */
	void addCardBin(CardBinReg cardBinReg, UserInfo user) throws BizException;
	
	/**
	 * 取得可申请的卡BIN号
	 * @param user
	 * @return
	 */
	String getBinNo(UserInfo user);
	
	/**
	 * 卡BIN是否存在
	 * @param binNO
	 * @return
	 */
	boolean isExistCardBin(String binNO) throws BizException;
	
	/**
	 * 查看卡BIN申请表的明细
	 * @param pk
	 * @return
	 */
	CardBinReg findCardBinRegDetail(Long pk);

}
