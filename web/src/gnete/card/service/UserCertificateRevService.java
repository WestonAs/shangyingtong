package gnete.card.service;

import flink.util.Paginater;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.UserCertificate;
import gnete.card.entity.UserCertificateKey;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.UserCertificateState;
import gnete.etc.BizException;
import gnete.etc.RuntimeBizException;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>证书管理控制门面接口</p>
 * <ul>
 *   <li>用于处理证书的录入、证书的绑定、证书绑定解除以及证书申请等业务</li>
 * </ul>
 * @Project: Card
 * @File: UserCertificateRevService.java
 * @See: UserCertifcateService.java 
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-25
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public interface UserCertificateRevService {
	int MAX_FILE_SIZE = 15 * 1024 * 1024;
	
	int MAX_BUFFER = 1024;	
		
	String VALUE_SPLIT = "|";
	
	String VIEW = "UserCertificate";
	
	int BRANCH_COMPARE_FLAG = 1;
	
	int MERCH_COMPARE_FLAG = 2;

	/**
	 * 
	 * <p>获得查询条件下的数字证书分页</p>
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @param loginBranchCode
	 * @param loginRoleType
	 * @return
	 * @throws BizException
	 * @version: 2011-4-26 下午07:18:52
	 * @See:
	 */
	Paginater getUserCertificateList(Map params, int pageNo, int pageSize, String loginBranchCode, String loginRoleType)
			throws BizException;
	
	/**
	 * 
	  * <p>获得查询条件下的数字证书</p>
	  * @param params
	  * @return
	  * @throws BizException  
	  * @version: 2011-5-19 下午05:32:09
	  * @See:
	 */
	List<UserCertificate> getUserCertificateList(Map params) throws BizException;
	
	/**
	 * 
	  * @description：<li>将单个的证书进行分页包装</li>
	  * @param userCertificate
	  * @param useState
	  * @return
	  * @throws BizException  
	  * @version: 2011-4-28 上午11:27:26
	  * @See:
	 */
	Paginater getUserCertificateList(UserCertificate userCertificate, String useState) throws BizException;
	
	/**
	 * 
	  * <p>获得选择数字证书明细</p>
	  * @param userCertificate
	  * @return
	  * @throws BizException  
	  * @version: 2011-4-26 下午08:11:49
	  * @See:
	 */
	UserCertificate getSelectUserCertificate(UserCertificate userCertificate) throws BizException;
	
	/**
	 * 
	  * <p>获得选择数字证书明细</p>
	  * @param certificateKey
	  * @return
	  * @throws BizException  
	  * @version: 2011-4-27 上午08:51:56
	  * @See:
	 */
	UserCertificate getDetailUserCertificate(UserCertificateKey certificateKey) throws BizException;
	
	/**
	 * 
	  * <p>根据登入角色获得数字证书状态查询集合</p>
	  * @param loginRoleType
	  * @return  
	  * @version: 2011-4-26 下午08:19:39
	  * @See:
	 */
	List<UserCertificateState> getUserCertStateList(String loginRoleType);
	
	/**
	 * 
	  * <p>根据机构编号查询机构列表</p>
	  * @param branchCode
	  * @return
	  * @throws BizException  
	  * @version: 2011-4-26 下午08:27:51
	  * @See:
	 */
	List<BranchInfo> getBranchInfo(String branchCode) throws BizException;
	
	/**
	 * 
	  * <p>查询当前可进行指派的机构列表</p>
	  * @return
	  * @throws BizException  
	  * @version: 2011-5-18 下午02:07:34
	  * @See:
	 */
	List<BranchInfo> getAssignBranchInfoList() throws BizException;
	
	/**
	 * 
	  * <p>获得指派结构对应的JSON数据结构</p>
	  * @return
	  * @throws BizException  
	  * @version: 2011-6-1 上午11:03:30
	  * @See:
	 */
	String getAssignBranchJsonInfo() throws BizException;
	
	/**
	 * 
	  * <p>获得机构或商户的用户对应的JSON数据结构</p>
	  * @param branchCode
	  * @param branchType
	  * @param userId
	  * @param userInfo
	  * @return
	  * @throws BizException  
	  * @version: 2011-6-1 下午12:51:53
	  * @See:
	 */
	String getBoundUserJsonInfo(String branchCode, String branchType, String userId, UserInfo userInfo) throws BizException;
	
	/**
	 * 
	  * <p>生成跟机构或商户关联的用户下拉框列表</p>
	  * @param params
	  * @param userId  要除开的用户(用于变更用户绑定)
	  * @return
	  * @throws BizException  
	  * @version: 2011-4-28 下午03:04:54
	  * @See:
	 */
	String getCertificateUserListOption(Map params, String userId) throws BizException;
	
	/**
	 * 
	  * <p>同上并引入比较器</p>
	  * @param params
	  * @param userId
	  * @param compareFlag
	  * @return
	  * @throws BizException  
	  * @version: 2011-6-3 下午02:07:24
	  * @See:
	 */
	String getCertificateUserListOption(Map params, String userId,int compareFlag) throws BizException;
	
	
	/**
	 * 
	  * <p>根据证书序列号查询用户角色</p>
	  * @param userId
	  * @return
	  * @throws BizException  
	  * @version: 2011-1-18 下午12:40:34
	  * @See:
	 */
	List<RoleInfo> findRoleInfo(String seqNo) throws BizException;
	
	/**
	 * 
	  * <p>得到机构自身以及其所管辖机构(包括商户)</p>
	  * @param branchCode
	  * @param userInfo
	  * @return
	  * @throws BizException  
	  * @version: 2011-5-25 下午02:15:51
	  * @See:
	 */
	Object[] getBranchAndMerchInfoList(String branchCode,UserInfo userInfo) throws BizException;
	
	/**
	 * 
	  * <p>得到机构所管辖的发卡机构</p>
	  * @param branchCode
	  * @param userInfo
	  * @return
	  * @throws BizException  
	  * @version: 2011-6-3 上午10:51:25
	  * @See:
	 */
	String getMyBranchJsonInfo(String branchCode, UserInfo userInfo) throws BizException;
	
	/**
	 * 
	  * <p>得到机构所管辖的商户机构</p>
	  * @param branchCode
	  * @param userInfo
	  * @return
	  * @throws BizException  
	  * @version: 2011-6-3 上午10:51:49
	  * @See:
	 */
	 String getMyMerchJsonInfo(String branchCode, UserInfo userInfo) throws BizException;

	/**
	 * 
	  * <p>处理文件上传</p>
	  * @param uploadFile
	  * @param uploadName
	  * @param branchCode
	  * @param loginUser
	  * @return
	  * @throws BizException
	  * @throws RuntimeBizException  
	  * @version: 2011-4-26 下午08:09:02
	  * @See:
	 */
	boolean processUserCertificateUpload(File uploadFile, String uploadName, String branchCode, UserInfo loginUser)
			throws BizException, RuntimeBizException;

	/**
	 * 
	  * <p>处理尚未绑定证书的指派</p>
	  * @param unBoundUserCertificates
	  * @param branchInfo
	  * @param loginUser
	  * @return
	  * @throws BizException  
	  * @version: 2011-4-26 下午08:06:12
	  * @See:
	 */
	boolean processUserCertificatAssign(UserCertificate[] unBoundUserCertificates, BranchInfo branchInfo, UserInfo loginUser)
			throws BizException;
	
	/**
	 * 
	  * <p>处理尚未绑定证书的指派</p>
	  * @param unBoundUserCertificates  上述证书序列号拼字符串(",")
	  * @param branchInfo
	  * @param loginUser
	  * @return
	  * @throws BizException  
	  * @version: 2011-4-28 上午10:40:35
	  * @See:
	 */
	boolean processUserCertificatAssign(String[] unBoundUserCertificates, BranchInfo branchInfo, UserInfo loginUser) throws BizException;
	
	/**
	 * 
	  * <p>移除尚未绑定的数字证书(数据库中的记录也将删除)</p>
	  * @param unBoundUserCertificate
	  * @param loginUser
	  * @return
	  * @throws BizException  
	  * @version: 2011-4-26 下午08:06:57
	  * @See:
	 */
	boolean processUserCertificateRemove(UserCertificate unBoundUserCertificate, UserInfo loginUser) throws BizException;
	
	/**
	 * 
	  * <p>回收指派的证书</p>
	  * @param assignUserCertificate
	  * @param loginUser
	  * @return
	  * @throws BizException  
	  * @version: 2011-4-27 下午03:17:46
	  * @See:
	 */
	boolean processUserCertificateRecycle(UserCertificate assignUserCertificate, UserInfo loginUser) throws BizException;
	
	/**
	 * 
	  * <p>绑定已经指派的数字证书</p>
	  * @param assignUser
	  * @param assignUserCertificate
	  * @param loginUser
	  * @return
	  * @throws BizException  
	  * @version: 2011-4-26 下午08:07:56
	  * @See:
	 */
    boolean processUserCertificateBound(UserInfo assignUser, UserCertificate assignUserCertificate, UserInfo loginUser)throws BizException;
    
    /**
     * 
      * <p>解除绑定已经绑定的数字证书</p>
      * @param boundUserCertificate
      * @param loginUser
      * @return
      * @throws BizException  
      * @version: 2011-4-26 下午08:07:59
      * @See:
     */
    boolean processUserCertificateUnBound(UserCertificate boundUserCertificate, UserInfo loginUser) throws BizException ;
    
    /**
     * 
      * <p>暂停已经绑定的数字证书</p>
      * @param boundUserCertificate
      * @param loginUser
      * @return
      * @throws BizException  
      * @version: 2011-4-26 下午08:08:02
      * @See:
     */
    boolean processUserCertificateBreak(UserCertificate boundUserCertificate, UserInfo loginUser) throws BizException;
    
    /**
     * 
      * <p>重新绑定已经暂停的数字证书</p>
      * @param breakUserCertificate
      * @param loginUser
      * @return
      * @throws BizException  
      * @version: 2011-4-26 下午08:08:05
      * @See:
     */
    boolean processUserCertificateReBound(UserCertificate breakUserCertificate, UserInfo loginUser) throws BizException ;
    
    /**
     * 
      * <p>可以将暂停的证书进行变更绑定</p>
      * @param assignUser
      * @param breakUserCertificate
      * @param loginUser
      * @return
      * @throws BizException  
      * @version: 2011-4-28 下午04:05:39
      * @See:
     */
    boolean processUserCertificateChgBound(UserInfo assignUser, UserCertificate breakUserCertificate, UserInfo loginUser) throws BizException;
    
    /**
     * 
      * <p>处理查询条件视图输出</p>
      * @param certParaMap
      * @param request
      * @param response
      * @throws BizException  
      * @version: 2011-5-19 下午05:44:33
      * @See:
     */
    void processUserCertificateExport(Map certParaMap,HttpServletRequest request, HttpServletResponse response) throws BizException;
    
    /**
     * 
      * <p>处理证书的上传与指派</p>
      * @param uploadFile
      * @param uploadName
      * @param branchInfo
      * @param loginUser
      * @return
      * @throws BizException  
      * @version: 2011-5-20 下午05:19:08
      * @See:
     */
    boolean processUserCertificateUploadAndAssign(File uploadFile, String uploadName,BranchInfo branchInfo, UserInfo loginUser) 
         throws BizException ;
    
    /**
     * <p>处理证书的上传与绑定</p>
     * @Date 2013-4-20上午11:17:25
     * @return boolean
     */
    boolean processUserCertificateUploadAndBound(Map<UserCertificate, File> branchCertificateMap ,UserInfo loginUser, UserCertificate boundedUserCertificate) 
            throws BizException ;
    
    /**
     * 
      * <p>处理数字证书签名</p>
      * @param seqNo
      * @param hexSignedInfo
      * @param info
      * @return
      * @throws BizException  
      * @version: 2011-4-26 下午08:11:10
      * @See:
     */
    boolean processUserSigVerify(String seqNo, String hexSignedInfo, String info) throws BizException;
    
    /** 证书更新（页面操作） 
     * @throws BizException 
     */
	boolean updateCertFile(File uploadFile, String uploadFileName, UserCertificate oldUserCertificate,
			UserInfo loginUser) throws BizException;

}
