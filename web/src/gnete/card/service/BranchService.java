package gnete.card.service;

import gnete.card.entity.BranchHasType;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BranchProxy;
import gnete.card.entity.BranchProxyKey;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.CardToMakeCard;
import gnete.card.entity.CardToMakeCardKey;
import gnete.card.entity.CardToMerch;
import gnete.card.entity.CardToMerchKey;
import gnete.card.entity.DepartmentInfo;
import gnete.card.entity.InsBankacct;
import gnete.card.entity.InsBankacctKey;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

public interface BranchService {

	/**
	 * 添加机构(此方法已经作废)
	 * @param branchInfo	机构信息
	 * @param type 
	 * @param admin 	机构管理员信息
	 * @param createUserId 
	 * @param privileges 
	 * @return
	 * @throws BizException
	 * 
	 */
	@Deprecated
	boolean addBranch(BranchInfo branchInfo, String type, 
			UserInfo admin, UserInfo createUser, String[] privileges, CardRiskReg cardRiskReg, String setMode) throws BizException;
	
	/**
	 * 添加机构（待审核状态的）
	 * @param branchInfo
	 * @param createUser
	 * @param branchHasType
	 * @return
	 * @throws BizException
	 */
	void addBranch(BranchInfo branchInfo, UserInfo createUser, BranchHasType branchHasType) throws BizException;
	
	/**
	 * 机构审核通过的处理
	 * @param branchInfo
	 * @param userId
	 * @throws BizException
	 */
	void checkBranchPass(BranchInfo branchInfo, String userId) throws BizException;
	
	/**
	 * 修改机构
	 */
	boolean modifyBranch(BranchInfo branchInfo, String modifyUserId, String setMode) throws BizException;
	
	/**
	 * 停用机构
	 * @param branchCode
	 * @return
	 * @throws BizException
	 */
	boolean stopBranch(String branchCode, UserInfo user) throws BizException;
	
	/**
	 * 启用机构
	 * @param branchCode
	 * @return
	 * @throws BizException
	 */
	boolean startBranch(String branchCode, UserInfo user) throws BizException;
	
	/**
	 * 提交通过后台添加的发卡机构的审核
	 * @param userInfo
	 * @throws BizException
	 */
	void submitCheck(UserInfo userInfo) throws BizException;
	
	/**
	 * 删除审核不通过的机构信息
	 * @param branchCode
	 * @return
	 * @throws BizException
	 */
	boolean deleteBranch(String branchCode) throws BizException;

	/**
	 * 绑定新类型
	 * @param branchCode
	 * @param type
	 * @param sessionUserCode
	 */
	void bindType(String branchCode, String type, String sessionUserCode) throws BizException ;

	/**
	 * 绑定类型
	 * 
	 * @param branchCode
	 * @param typeCodes
	 */
	void bindType(BranchInfo branchInfo, String[] typeCodes, String sessionUserCode) throws BizException ;
	
	
	/**
	 * 添加代理信息
	 * @param branchProxy
	 * @param userCode
	 * @param string 
	 */
	void addProxy(BranchProxy branchProxy, String[] privileges, String userCode) throws BizException ;
	
	/**
	 * 注销代理信息
	 * @param branchProxyKey
	 * @param userCode
	 */
	void cancelProxy(BranchProxyKey branchProxyKey, String userCode) throws BizException ;
	
	/**
	 * 恢复代理信息
	 * @param branchProxyKey
	 * @param userCode
	 */
	void activateProxy(BranchProxyKey branchProxyKey, String userCode) throws BizException ;

	/**
	 * 删除代理关系
	 * @param branchProxyKey
	 * @param sessionUserCode
	 */
	void deleteProxy(BranchProxyKey branchProxyKey, String sessionUserCode) throws BizException ;
	
	/**
	 * 给售卡代理分配权限
	 * @param branchProxy
	 * @param split
	 * @param sessionUserCode
	 */
	void assignProxy(BranchProxy branchProxy, String[] privilegeArray,
			String sessionUserCode) throws BizException ;
	
	/**
	 * 添加发卡机构与商户关系信息
	 * @param cardToMerch
	 * @param merchs 
	 * @param sessionUserCode
	 * @throws BizException
	 */
	void addCardMerch(CardToMerch cardToMerch, String[] merchs, String sessionUserCode) throws BizException;

	/**
	 * 注销发卡机构与商户关系信息
	 * @param cardToMerchKey
	 * @param sessionUserCode
	 * @throws BizException
	 */
	void cancelCardMerch(CardToMerchKey cardToMerchKey, String sessionUserCode) throws BizException;
	
	/**
	 * 生效发卡机构与商户关系信息
	 * @param cardToMerchKey
	 * @param sessionUserCode
	 * @throws BizException
	 */
	void activateCardMerch(CardToMerchKey cardToMerchKey, String sessionUserCode) throws BizException;

	/**
	 * 添加部门
	 * @param departmentInfo
	 * @param privileges
	 * @param sessionUserCode
	 */
	void addDept(DepartmentInfo departmentInfo, String privileges,
			String sessionUserCode) throws BizException;

	/**
	 * 修改部门
	 * @param departmentInfo
	 * @param privileges
	 * @param sessionUserCode
	 */
	void modifyDept(DepartmentInfo departmentInfo, String privileges,
			String sessionUserCode) throws BizException;

	/**
	 * 注销部门
	 * @param deptId
	 * @param sessionUserCode
	 */
	void cancelDept(String deptId, String sessionUserCode) throws BizException;

	/**
	 * 生效部门
	 * @param deptId
	 * @param sessionUserCode
	 */
	void activeDept(String deptId, String sessionUserCode) throws BizException;

	/**
	 * 删除发卡机构商户关系
	 * @param cardToMerchKey
	 * @param sessionUserCode
	 */
	void deleteCardMerch(CardToMerchKey cardToMerchKey, String sessionUserCode) throws BizException;
	
	/**
	 *  添加发卡机构与集团关系
	 * @param groupId 集团机构编号
	 * @param branches 发卡机构数组
	 * @param sessionUser 登录用户信息
	 * @return
	 * @throws BizException
	 */
	void addCardGroup(String groupId, String branches, UserInfo sessionUser) throws BizException;
	
	/**
	 * 注销发卡机构与集团关系
	 * @param branchCode 发卡机构编码
	 * @param groupId 集团编码
	 * @param sessionUser 登录用户信息
	 * @return
	 * @throws BizException
	 */
	boolean cancelCardGroup(String branchCode, String groupId, UserInfo sessionUser) throws BizException;
	
	/**
	 * 启用发卡机构与集团关系
	 * @param branchCode 发卡机构编码
	 * @param groupId 集团编码
	 * @param sessionUser 登录用户信息
	 * @return
	 * @throws BizException
	 */
	boolean activateCardGroup(String branchCode, String groupId, UserInfo sessionUser) throws BizException;
	
	/**
	 * 删除发卡机构与集团关系
	 * @param branchCode 发卡机构编码
	 * @param groupId 集团编码
	 * @return
	 * @throws BizException
	 */
	boolean deleteCardGroup(String branchCode) throws BizException;
	
	/**
	 * 添加发卡机构与制卡厂商关系
	 * @param cardToMakeCard
	 * @param sessionUserCode
	 * @throws BizException
	 */
	boolean addCardToMakeCard(CardToMakeCard cardToMakeCard, String sessionUserCode) throws BizException;
	
	/**
	 * 修改发卡机构与制卡厂商关系（注销或者激活）
	 * @param cardToMakeCard
	 * @param sessionUserCode
	 * @return
	 * @throws BizException
	 */
	boolean modifyCardToMakeCard(CardToMakeCard cardToMakeCard, String sessionUserCode) throws BizException;
	
	/**
	 * 删除发卡机构与制卡厂商关系
	 * @param key
	 * @return
	 * @throws BizException
	 */
	boolean deleteCardToMakeCard(CardToMakeCardKey key) throws BizException;
	
	/**
	 * 添加银行账户信息
	 * @param insBankacct
	 * @param sessionUserCode
	 * @return
	 * @throws BizException
	 */
	boolean addInsBankacct(InsBankacct insBankacct, String sessionUserCode) throws BizException;
	
	/**
	 * 修改银行账户信息
	 * @param insBankacct
	 * @param sessionUserCode
	 * @return
	 * @throws BizException
	 */
	boolean modifyInsBankacct(InsBankacct insBankacct, String sessionUserCode) throws BizException;
	
	/**
	 * 删除银行账户信息
	 * @param key
	 * @return
	 * @throws BizException
	 */
	boolean deleteInsBankacct(InsBankacctKey key) throws BizException;
}
