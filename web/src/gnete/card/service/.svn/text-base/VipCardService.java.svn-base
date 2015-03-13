package gnete.card.service;

import gnete.card.entity.MembAppointReg;
import gnete.card.entity.MembClassDef;
import gnete.card.entity.MembInfoReg;
import gnete.card.entity.MembReg;
import gnete.card.entity.RenewCardReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * @File: CardBinService.java
 *
 * @description: Vip会员卡管理相关业务处理
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-8-11
 */

public interface VipCardService {
	/**
	 * 新增会员定义
	 * @param membClassDef	会员定义信息
	 * @return
	 * @throws BizException
	 */
	boolean addmMembClassDef(MembClassDef membClassDef, String sessionUserCode,String cardIssuer) throws BizException;
	
	/**
	 * 修改会员定义
	 * @param membClassDef	会员定义信息
	 * @return
	 * @throws BizException
	 */
	boolean modifyMembClassDef(MembClassDef membClassDef, String sessionUserCode) throws BizException ;
	
	/**
	 * 删除会员定义
	 */
	boolean delete(String membClass) throws BizException;
	
	/**
	 * 判断会员类型是否存在
	 * @param membClass
	 * @return
	 * @throws BizException
	 */
	public boolean isExistMembClass(String membClass) throws BizException;
	
	/**
	 * 会员升级登记
	 * @param renewCardReg	会员升级登记信息
	 * @return
	 * @throws BizException
	 */
	boolean addUpgradeCard(RenewCardReg renewCardReg, String sessionUserCode) throws BizException;
	
	/**
	 * 检查会员升级是否满足升级规则
	 */
	public boolean isMeetUpgradeRule(String cardId) throws BizException;
	
	/**
	 * 删除会员升级换卡记录
	 */
	boolean deleteUpgradeRecord(Long renewCardId) throws BizException;
	
	/**
	 * 新增会员注册
	 * @param membReg	会员注册信息
	 * @return
	 * @throws BizException
	 */
	boolean addMembReg(MembReg membReg, UserInfo userInfo) throws BizException;
	
	/**
	 * 修改会员注册资料
	 * @param membReg	会员注册信息
	 * @return
	 * @throws BizException
	 */
	boolean modifyMembReg(MembReg membReg, UserInfo sessionUser) throws BizException ;
	
	/**
	 * 删除会员注册资料
	 * @param membRegId	会员注册对象ID
	 * @return
	 * @throws BizException
	 */
	boolean deleteMembReg(Long membRegId) throws BizException;

	/**
	 * 新增会员登记
	 * @param membInfoReg	会员登记信息
	 * @return
	 * @throws BizException
	 */
	boolean addMembInfoReg(MembInfoReg membInfoReg, UserInfo userInfo) throws BizException;
	
	/**
	 * 批量新增会员登记
	 * @Date 2013-1-16上午9:27:18
	 * @return List<MembInfoReg>
	 */
	List<MembInfoReg> addMembInfoRegBat(File upload, String uploadFileName,UserInfo user) throws BizException;
	
	/**
	 * 修改会员登记资料
	 * @param membInfoReg	会员登记信息
	 * @return
	 * @throws BizException
	 */
	boolean modifyMembInfoReg(MembInfoReg membInfoReg, String sessionUserCode) throws BizException ;
	
	/**
	 * 删除会员登记资料
	 * @param membInfoRegId	会员登记对象ID
	 * @return
	 * @throws BizException
	 */
	boolean deleteMembInfoReg(MembInfoReg membInfoReg) throws BizException;

	/**
	 * 判断会员登记编号是否存在
	 * @param membInfoRegId
	 * @return
	 * @throws BizException
	 */
	boolean isExistMembInfoReg(MembInfoReg membInfoReg) throws BizException;

	/**
	 * 获取机构的积分类型列表
	 * @Date 2013-1-22上午9:45:06
	 * @return List<MembClassDef>
	 */
	List<MembClassDef> loadMtClass(String cardIssuer) throws BizException;
	
	/**
	 * 批量会员关联
	 * @Date 2013-1-25下午3:13:14
	 * @return long
	 */
	long addMembAppointRegBat(MembAppointReg membAppointReg, List<MembInfoReg> membInfoRegList,UserInfo user) throws BizException ;

	void export(HttpServletResponse response, Map<String, Object> params) throws IOException;
}
