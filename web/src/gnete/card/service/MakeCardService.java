package gnete.card.service;

import gnete.card.entity.CardBin;
import gnete.card.entity.CardBinReg;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.IcTempPara;
import gnete.card.entity.MakeCardApp;
import gnete.card.entity.MakeCardReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

import java.io.File;

/**
 * @File: MakeCardAppService.java
 * 
 * @description: 制卡申请相关处理
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-10
 */
public interface MakeCardService {

	/**
	 * 申请卡BIN
	 * 
	 * @param cardBIN
	 *            卡BIN管理
	 * @param sessionUserCode
	 *            当前用户编码
	 * @param cardIssuer
	 *            当前用户所属发卡机构编码
	 * @return
	 * @throws BizException
	 */
	CardBinReg addCardBinReg(CardBinReg cardBinReg, UserInfo user,
			String cardIssuer) throws BizException;

	/**
	 * 申请卡BIN
	 * 
	 * @param cardBIN
	 *            卡BIN管理
	 * @param cardTypeCode
	 *            卡类型
	 * @param currCode
	 *            货币代码
	 * @param sessionUserCode
	 *            当前用户编码
	 * @return
	 * @throws BizException
	 */
	boolean addCardBin(CardBin cardBin, String cardTypeCode, String currCode,
			String sessionUserCode) throws BizException;

	/**
	 * 查看卡BIN号码是否存在
	 * 
	 * @param binNo
	 *            卡BIN号码
	 * @return
	 * @throws BizException
	 */
	boolean isExistCardBin(String binNo) throws BizException;

	/**
	 * 新增卡子类型
	 * 
	 * @param cardSubClassDef
	 *            卡子类型定义
	 * @param cardIssuer
	 *            当前用户所属发卡机构编码
	 * @return
	 * @throws BizException
	 */
	CardSubClassDef addCardSubClass(CardSubClassDef cardSubClassDef, IcTempPara icTempPara,
			UserInfo sessionUser) throws BizException;

	/**
	 * 修改卡子类型
	 * 
	 * @param cardSubClassDef
	 *            卡子类型定义
	 * @throws BizException
	 */
	void modifyCardSubClass(CardSubClassDef cardSubClassDef,
			UserInfo sessionUser) throws BizException;

	/**
	 * 删除指定卡子类型
	 * 
	 * @param cardSubclass
	 *            卡子类型编号
	 * @throws BizException
	 */
	void deleteCardSubClass(String cardSubclass) throws BizException;

	/**
	 * 新增一条制卡申请记录
	 * 
	 * @param makeCardApp
	 *            制卡申请簿
	 * @param sessionUser
	 *            登录的用户的信息
	 * @return 新增成功后的制卡申请对象
	 * @throws BizException
	 */
	MakeCardApp addMakeCardApp(MakeCardApp makeCardApp, UserInfo sessionUser)
			throws BizException;

	/**
	 * 回收卡号，清除发卡机构卡号分配表里的记录
	 * 
	 * @param makeCardApp
	 * @return
	 * @throws BizException
	 */
	boolean releaseCardNoAssign(MakeCardApp makeCardApp) throws BizException;

	/**
	 * 是否正确的起始卡号
	 * 
	 * @param makeCardApp
	 *            制卡申请对象
	 * @return
	 * @throws BizException
	 */
	boolean isCorrectStrNo(MakeCardApp makeCardApp)
			throws BizException;

	/**
	 * 根据制卡申请对象中的制卡登记ID生成起始卡号
	 * 
	 * @param makeCardApp
	 *            制卡申请对象
	 * @return
	 * @throws BizException
	 */
	String getStrNo(MakeCardApp makeCardApp)
			throws BizException;

	/**
	 * 制卡申请提交后，状态为新建的可以撤销制卡
	 * 
	 * @param makeCardApp
	 *            制卡申请对象
	 * @param sessionUser
	 *            登录的用户的信息
	 * @return
	 * @throws BizException
	 */
	boolean cancel(MakeCardApp makeCardApp, UserInfo sessionUser)
			throws BizException;

	/**
	 * 建档完成后，撤销建档
	 * 
	 * @param makeCardApp
	 *            制卡申请对象
	 * @param sessionUser
	 *            登录的用户的信息
	 * @return
	 * @throws BizException
	 */
	boolean revoke(MakeCardApp makeCardApp, UserInfo sessionUser)
			throws BizException;

	/**
	 * 新增一条制卡登记记录,此方法用同名方法替代
	 * 
	 * @param makeCardReg
	 *            制卡登记簿
	 * @param sessionUser
	 *            session里保存的用户信息
	 * @return
	 * @throws BizException
	 */
	@Deprecated
	MakeCardReg addMakeCardReg(MakeCardReg makeCardReg, UserInfo sessionUser)
			throws BizException;

	/**
	 * 新增一条制卡登记记录
	 * 
	 * @param cardStyleFile
	 *            卡样文件
	 * @param fileName
	 *            卡样文件名
	 * @param makeCardReg
	 *            制卡登记薄
	 * @param sessionUser
	 *            登录用户信息
	 * @return
	 * @throws BizException
	 */
	MakeCardReg addMakeCardReg(File cardStyleFile, String fileName,
			MakeCardReg makeCardReg, UserInfo sessionUser) throws BizException;

	/**
	 * 卡样审核通过
	 * 
	 * @param makeId
	 *            制卡ID
	 * @param sessionUser
	 *            session里保存的用户信息
	 * @return
	 * @throws BizException
	 */
	boolean passCardPic(String makeId, UserInfo sessionUser)
			throws BizException;

	/**
	 * 卡样撤销（审核失败）
	 * 
	 * @param makeId
	 *            制卡ID
	 * @param reason
	 *            失败、撤销的原因
	 * @param sessionUser
	 *            session里保存的用户信息
	 * @return
	 * @throws BizException
	 */
	boolean canceCardPic(String makeId, String reason, UserInfo sessionUser)
			throws BizException;

	/**
	 * 下载后的相关处理
	 * 
	 * @param makeId
	 *            制卡ID
	 * @param sessionUser
	 *            session里保存的用户信息
	 * @return
	 * @throws BizException
	 */
	boolean downloadCardPic(String makeId, UserInfo sessionUser)
			throws BizException;

	/**
	 * 指定下载的文件是否存在
	 * 
	 * @param makeCardReg
	 * @throws BizException
	 */
	void isFileExist(MakeCardReg makeCardReg) throws BizException;

	/**
	 * 下载制卡文本后的相关处理
	 * 
	 * @param id
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	boolean downloadMakeFile(String id, UserInfo sessionUser)
			throws BizException;
}
