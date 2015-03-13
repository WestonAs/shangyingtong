package gnete.card.service;

import flink.util.Paginater;
import gnete.card.entity.IcCancelCardReg;
import gnete.card.entity.IcCardActive;
import gnete.card.entity.IcCardParaModifyReg;
import gnete.card.entity.IcCardReversal;
import gnete.card.entity.IcEcashDepositReg;
import gnete.card.entity.IcEcashReversal;
import gnete.card.entity.IcRenewCardReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.IcReversalType;
import gnete.card.entity.type.ReversalType;
import gnete.etc.BizException;

import java.util.Map;

import net.sf.json.JSONObject;

/**
 * @File: IcDepositService.java
 * 
 * @description: IC卡相关处理的Service接口
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2011-12-13
 */
public interface IcCardService {

	/**
	 * 查询IC卡电子现金充值列表页面
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	Paginater findIcEcashDepositPage(Map<String, Object> params,
			int pageNumber, int pageSize) throws BizException;

	/**
	 * IC卡电子现金充值明细
	 * 
	 * @param icEcashDepositReg
	 * @return
	 * @throws BizException
	 */
	IcEcashDepositReg getIcEcashDepositDetail(
			IcEcashDepositReg icEcashDepositReg) throws BizException;

	/**
	 * 在IC卡电子现金充值登记表中插入记录并发送命令到后台
	 * 
	 * @param icEcashDepositReg
	 * @param user
	 * @return 登记薄ID
	 * @throws BizException
	 */
	String addIcEcashDepositReg(IcEcashDepositReg icEcashDepositReg, UserInfo user, 
			String serialNo) throws BizException;
	
	/**
	 * 轮询命令表，根据后台的返回修改IC卡电子现金充值登记薄的状态，同时将后台的返回输出到前台页面
	 * 
	 * @param refId
	 * @return
	 * @throws BizException
	 */
	IcEcashDepositReg dealIcEcashDeposit(String refId) throws BizException;
	
	/**
	 * 写卡失败时需要发冲正报文到后台。
	 * 在IC卡电子现金冲正登记簿中插入记录并发送命令到后台
	 * 
	 * @param icEcashReversal 充值登记簿
	 * @param user 登录用户信息
	 * @param type 冲正类型
	 * @return
	 * @throws BizException
	 */
	String addIcEcashReversal(IcEcashReversal icEcashReversal, UserInfo user, ReversalType type) throws BizException;
	
	/**
	 * 写卡成功发通知
	 * @throws BizException
	 */
	void noticeSuccess(String depositId) throws BizException;
	
	/**
	 * 根据卡号取得一个卡片的信息，返回一个json类型的数据
	 * @param cardId
	 * @return
	 * @throws BizException
	 */
	JSONObject searchCardId(String cardId);
	
	//====================================== IC卡激活处理 ========================================
	/**
	 * 查询IC激活列表页面
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	Paginater findIcCardActivePage(Map<String, Object> params, int pageNumber, int pageSize) throws BizException;
	
	/**
	 *  查询IC卡激活明细页面
	 * @param pk
	 * @return
	 * @throws BizException
	 */
	IcCardActive findIcCardActiveDetail(String pk) throws BizException;
	
	/**
	 * 在IC卡激活登记表中插入记录并发送命令到后台
	 * 
	 * @param icEcashDepositReg
	 * @param user
	 * @return 登记薄ID
	 * @throws BizException
	 */
	String addIcCardActive(IcCardActive icCardActive, UserInfo user, String serialNo) throws BizException;
	
	/**
	 * 轮询命令表，根据后台的返回修改IC卡激活登记表的状态，同时将后台的返回输出到前台页面
	 * 
	 * @param refId
	 * @return
	 * @throws BizException
	 */
	IcCardActive dealIcCardActive(String refId) throws BizException;
	
	/**
	 * 写卡成功发通知
	 * @throws BizException
	 */
	IcCardActive noticeActiveSuccess(String activeId) throws BizException;
	
	//================================ IC卡卡片参数修改处理 =========================================
	/**
	 * 查询卡片参数修改登记簿记录列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	Paginater findParaModifyPage(Map<String, Object> params, int pageNumber, int pageSize) throws BizException;
	
	/**
	 * 查询卡片参数修改登记簿明细
	 * 
	 * @param id
	 * @return
	 */
	IcCardParaModifyReg findParaModifyDetail(String id);
	
	/**
	 * 根据卡号取得需要修改卡片参数的卡的相关信息，返回一个Json数据
	 * @param cardId
	 * @return
	 */
	JSONObject searchCardInfoForModify(String cardId);
	
	/**
	 * 新增IC卡卡片参数修改登记
	 * 
	 * @param icCardParaModifyReg 卡片参数修改登记簿
	 * @param user 登录用户信息
	 * @param serialNo 证书序列号
	 * @param signature web端签名后的数据
	 * @return
	 * @throws BizException
	 */
	String addParaModifyReg(IcCardParaModifyReg icCardParaModifyReg, 
			UserInfo user, String serialNo, String signature) throws BizException;

	/**
	 * 轮询命令表
	 * @param refId
	 * @return
	 * @throws BizException
	 */
	IcCardParaModifyReg dealModifyPara(String refId) throws BizException;
	
	/**
	 * 写卡成功后的相关处理
	 * 
	 * @param id
	 * @return
	 * @throws BizException
	 */
	IcCardParaModifyReg noticeModifyParaSuccess(String id) throws BizException;
	
	//================================ IC卡换卡的相关处理 ==========================================================================
	/**
	 *  查询IC卡换卡登记页面
	 *  
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	Paginater findIcRenewCardRegPage(Map<String, Object> params, int pageNumber, int pageSize) throws BizException;
	
	/**
	 * 查询换卡登记明细
	 * 
	 * @param id
	 * @return
	 */
	IcRenewCardReg findIcRenewCardRegDetail(String id); 
	
	/**
	 * 在IC卡换卡登记表中插入记录并发送命令到后台
	 * 
	 * @param icEcashDepositReg
	 * @param user
	 * @return 登记薄ID
	 * @throws BizException
	 */
	String addIcRenewCardReg(IcRenewCardReg icRenewCardReg, 
			UserInfo user, String serialNo, String signature) throws BizException;
	
	/**
	 * 轮询命令表，根据后台的返回修改IC卡换卡登记薄的状态，同时将后台的返回输出到前台页面
	 * 
	 * @param refId
	 * @return
	 * @throws BizException
	 */
	IcRenewCardReg dealIcRenewCardReg(String refId) throws BizException;
	
	/**
	 * 写卡失败时需要发冲正报文到后台。
	 * 在IC卡冲正登记簿中插入记录并发送命令到后台
	 * （包括换卡冲正和退卡冲正）
	 * 
	 * @param icCardReversal 充值登记簿
	 * @param user 登录用户信息
	 * @param reversalType 冲正类型
	 * @return
	 * @throws BizException
	 */
	String addIcCardReversal(IcCardReversal icCardReversal, UserInfo user, IcReversalType reversalType) throws BizException;
	
	/**
	 * 写卡成功发通知
	 * @throws BizException
	 */
	void noticeIcRenewCardSuccess(String refId) throws BizException;
	
	//================================ IC卡销卡的相关处理 ==========================================================================
	/**
	 *  查询IC卡销卡登记页面
	 *  
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	Paginater findIcCancelCardRegPage(Map<String, Object> params, int pageNumber, int pageSize) throws BizException;
	
	/**
	 * 查询IC卡销卡登记明细
	 * 
	 * @param id
	 * @return
	 */
	IcCancelCardReg findIcCancelCardRegDetail(String id); 
	
	/**
	 * 在IC卡销卡登记表中插入记录并发送命令到后台
	 * @param icCancelCardReg
	 * @param user
	 * @param serialNo
	 * @param signature
	 * @return
	 * @throws BizException
	 */
	String addIcCancelCardReg(IcCancelCardReg icCancelCardReg, 
			UserInfo user, String serialNo, String signature) throws BizException;
	
	/**
	 * 轮询命令表，根据后台的返回修改IC卡销卡登记薄的状态，同时将后台的返回输出到前台页面
	 * 
	 * @param refId
	 * @return
	 * @throws BizException
	 */
	IcCancelCardReg dealIcCancelCardReg(String refId) throws BizException;
	
	/**
	 * 写卡成功发通知
	 * @throws BizException
	 */
	void noticeIcCancelCardSuccess(String refId) throws BizException;
}
