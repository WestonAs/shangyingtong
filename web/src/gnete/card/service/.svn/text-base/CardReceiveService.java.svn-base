package gnete.card.service;

import flink.util.NameValuePair;
import gnete.card.entity.AppReg;
import gnete.card.entity.ExternalCardImportReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * @File: CardReceiveService.java
 * 
 * @description: 领卡登记/返库登记业务处理
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-24
 */
public interface CardReceiveService {

	/**
	 * 新增一条领卡记录
	 * 
	 * @param appReg
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	AppReg addCardReceive(AppReg appReg, UserInfo sessionUser) throws BizException;
	
	/**
	 * 根据卡子类型，卡的领出机构号和登录用户名取得起始卡号
	 * @param cardSubclass 卡子类型
	 * @param branchCode 卡的领出机构号
	 * @param user 登录用户名
	 * @return
	 */
	@Deprecated
	String getStrNo(String cardSubclass, String branchCode, UserInfo user) throws BizException;

	/**
	 * 根据卡子类型，领卡机构号和卡的领入机构号取得起始卡号
	 * @param cardSubclass 卡子类型
	 * @param branchCode 卡的领出机构号
	 * @param appOrgId 卡的领入机构号
	 * @param user 登录用户名
	 * @return
	 */
	@Deprecated
	String getStrNo(String cardSubclass, String branchCode, String appOrgId, UserInfo user) throws BizException;
	
	/**
	 * 根据卡子类型，领卡机构号和卡的领入机构号取得起始卡号
	 * @param cardSubclass 卡子类型
	 * @param branchCode 卡的领出机构号
	 * @param appOrgId 卡的领入机构号
	 * @param stockStatus 卡库存状态
	 * @param user 登录用户名
	 * @return
	 * @throws BizException
	 */
	String getStrNo(String cardSubclass, String branchCode, String appOrgId, String stockStatus, UserInfo user) throws BizException;
	
	/**
	 * 取得可领卡总数量，本次可领卡数量
	 * @param cardSubClass 卡类型
	 * @param cardSectorId 卡的领出机构
	 * @param strNo 起始卡号
	 * @param user 登录用户名
	 * @return
	 * @throws BizException
	 */
	@Deprecated
	long[] canReaciveCardNum(String cardSubClass, String cardSectorId, String strNo, UserInfo user) throws BizException;

	/**
	 * 取得可领卡总数量，本次可领卡数量
	 * 
	 * @param cardSubClass 卡类型
	 * @param cardSectorId 卡的领出机构
	 * @param appOrgId 卡的领入机构号
	 * @param stockStatus 卡库存状态
	 * @param strNo 起始卡号
	 * @param user 登录用户信息
	 * @return
	 * @throws BizException
	 */
	long[] canReaciveCardNum(String cardSubClass, String cardSectorId, 
			String appOrgId, String stockStatus, String strNo, UserInfo user) throws BizException;

	/**
	 * 新增一条返库记录
	 * 
	 * @param appReg
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	AppReg addCardWithdraw(AppReg appReg, UserInfo sessionUser) throws BizException;

	/**
	 * 根据传入的领卡机构号得到领卡机构或部门，商户列表
	 * @param keyWord
	 * @return
	 * @throws BizException
	 */
	List<NameValuePair> getReciveIssuer(String keyWord, UserInfo user) throws BizException;
	
	/**
	 * 导出卡库存信息的Excel文件
	 * @param response
	 * @param params
	 * @throws BizException
	 */
	void generateExcel(HttpServletResponse response, Map<String, Object> params) throws BizException;
}
