package gnete.card.service;

import java.io.File;

import gnete.card.entity.CardMerchGroup;
import gnete.card.entity.MerchGroup;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.MerchInfoReg;
import gnete.card.entity.MerchType;
import gnete.card.entity.RiskMerch;
import gnete.card.entity.Terminal;
import gnete.card.entity.TerminalAddi;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

/**
 * MerchService.java.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-8-7
 */
public interface MerchService {

	/**
	 *  添加新商户（待审核）将商户的添加信息写入登记簿
	 * @param merchInfo
	 * @param userCode
	 * @throws BizException
	 */
	void addMerch(MerchInfoReg merchInfoReg, UserInfo userInfo) throws BizException;
	
	/**
	 * 商户新增审核通过处理 
	 * @param merchInfo
	 * @param userId
	 * @throws BizException
	 */
	void checkMerchPass(MerchInfoReg merchInfoReg, String userId) throws BizException;
	
	/**
	 *  添加旧商户（待审核）
	 * @param merchInfo
	 * @param userCode
	 * @throws BizException
	 */
	void addOldMerch(MerchInfoReg merchInfoReg, UserInfo userInfo) throws BizException;
	
	/**
	 *  文件方式批量录入商户
	 * @param file
	 * @param merchInfoReg
	 * @param userInfo
	 * @throws BizException
	 */
	String[] addMerchFile(File file, MerchInfoReg merchInfoReg, UserInfo userInfo) throws BizException;

	/**
	 * 修改商户
	 * 
	 * @param merchInfo
	 * @param userCode
	 */
	void modifyMerch(MerchInfo merchInfo, String userCode) throws BizException;
	
	/**
	 * 修改商户登记簿
	 * @param merchInfoReg
	 * @param userInfo
	 * @throws BizException
	 */
	void modifyMerchReg(MerchInfoReg merchInfoReg, UserInfo userInfo) throws BizException;

	/**
	 * 注销商户
	 * 
	 * @param merchId
	 * @param userCode
	 */
	void cancelMerch(String merchId, String userCode) throws BizException;

	/**
	 * 生效商户
	 * 
	 * @param merchId
	 * @param userCode
	 */
	void activeMerch(String merchId, String userCode) throws BizException;
	
	/**
	 * 提交商户审核
	 * @param user
	 * @throws BizException
	 */
	void submitCheck(UserInfo user) throws BizException;
	
	/**
	 * 删除商户
	 * @param merchId
	 * @return
	 * @throws BizException
	 */
	boolean deleteMerch(String merchId) throws BizException;

	/**
	 * 添加商圈
	 * 
	 * @param merchGroup
	 * @param merchs
	 * @param userCode
	 */
	void addMerchGroup(MerchGroup merchGroup, String merchs, UserInfo userInfo)
			throws BizException;

	/**
	 * 修改商户组
	 * 
	 * @param merchGroup
	 * @param merchs
	 * @param userCode
	 */
	void modifyMerchGroup(MerchGroup merchGroup, String merchs, String userCode)
			throws BizException;

	/**
	 * 删除商户组
	 * 
	 * @param groupId
	 * @param userCode
	 */
	void deleteMerchGroup(String groupId, String userCode) throws BizException;

	/**
	 * 添加商户类型
	 * 
	 * @param merchType
	 * @param userCode
	 */
	void addMerchType(MerchType merchType, String userCode) throws BizException;

	/**
	 * 修改商户类型
	 * 
	 * @param merchType
	 * @param userCode
	 */
	void modifyMerchType(MerchType merchType, String userCode)
			throws BizException;

	/**
	 * 注销商户类型
	 * 
	 * @param merchType
	 * @param userCode
	 */
	void cancelMerchType(String merchType, String userCode) throws BizException;

	/**
	 * 生效商户类型
	 * 
	 * @param merchType
	 * @param userCode
	 */
	void activeMerchType(String merchType, String userCode) throws BizException;

	/**
	 * 添加终端
	 * 
	 * @param terminal
	 * @param userCode
	 */
	void addTerminal(Terminal terminal, String cardBranchCode, String userCode) throws BizException;
	
	/**
	 * 添加旧系统终端
	 * 
	 * @param terminal
	 * @param userCode
	 */
	void addOldTerminal(Terminal terminal, String userCode) throws BizException;

	/**
	 * 添加多个终端
	 * @param terminal
	 * @param parseInt
	 * @param sessionUserCode
	 */
	String[] addTerminalBatch(Terminal terminal, String cardBranchCode, int parseInt,
			String sessionUserCode) throws BizException;
	
	/**
	 * 修改终端
	 * 
	 * @param terminal
	 * @param userCode
	 */
	void modifyTerminal(Terminal terminal, String userCode) throws BizException;

	/**
	 * 停用终端
	 * 
	 * @param deptId
	 * @param userCode
	 */
	void stopTerminal(String termId, String userCode) throws BizException;

	/**
	 * 启用终端
	 * 
	 * @param deptId
	 * @param userCode
	 */
	void startTerminal(String termId, String userCode) throws BizException;

	/**
	 * 添加发卡机构商户组
	 * 
	 * @param cardMerchGroup
	 * @param merchants
	 * @param userId
	 * @throws BizException
	 */
	void addCardMerchGroup(CardMerchGroup cardMerchGroup, String merchants,
			String userId) throws BizException;

	/**
	 * 修改发卡机构商户组
	 * @param cardMerchGroup
	 * @param merchants
	 * @param userId
	 * @throws BizException
	 */
	void modifyCardMerchGroup(CardMerchGroup cardMerchGroup, String merchants,
			String userId) throws BizException;
	
	/**
	 * 注销发卡机构商户组
	 * @param branchCode
	 * @param groupId
	 * @throws BizException
	 */
	void cancelCardMerchGroup(String branchCode, String groupId)throws BizException;

	/** 
	 * 启用发卡机构商户组
	 * @param branchCode
	 * @param groupId
	 * @throws BizException
	 */
	void startCardMerchGroup(String branchCode, String groupId)throws BizException;
	
	/**
	 * 添加风险商户
	 * 
	 * @param riskMerch
	 * @param userCode
	 */
	boolean addRiskMerch(RiskMerch riskMerch , String userCode) throws BizException;
	
	/**
	 * 删除风险商户
	 * @param merchId
	 * @throws BizException
	 */
	boolean deleteRiskMerch(String merchId) throws BizException;

	/** 文件方式批量新增终端 */
	int addTermBatchFile(File upload, UserInfo sessionUser) throws BizException;

}
