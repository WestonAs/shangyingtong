package gnete.card.service.impl;

import flink.export.IViewBuilder;
import flink.ftp.CommunicationException;
import flink.schedule.TaskException;
import flink.security.CertOperateException;
import flink.security.IUserCertificateCache;
import flink.security.UserCertificateBean;
import flink.util.CommonHelper;
import flink.util.FileHelper;
import flink.util.ITaskCall;
import flink.util.JsonHelper;
import flink.util.Paginater;
import flink.util.TaskCallHelper;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.RoleInfoDAO;
import gnete.card.dao.UserCertificateRevDAO;
import gnete.card.dao.UserInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.UserCertificate;
import gnete.card.entity.UserCertificateKey;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CertificateState;
import gnete.card.entity.state.UserCertificateState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserBranchType;
import gnete.card.export.UserCertificateViewBuilderImpl;
import gnete.card.service.ICardCertificateKeyStore;
import gnete.card.service.ICardFileTransferProcess;
import gnete.card.service.ICardSignatureProcess;
import gnete.card.service.UserCertificateRevService;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.BizException;
import gnete.etc.RuntimeBizException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * <p>证书管理控制门面实现类</p>
 * @modified:
 *    <tt>用户如果已经拥有数字证书(绑定成功)那么机构人员不能再给其他指派证书</tt>
 */
@Service("userCertificateRevService")
public class UserCertificateServiceRevImpl implements UserCertificateRevService {
	private final Logger					log						= LoggerFactory.getLogger(getClass());

	/** 缺省选择多个证书标志(html中) */
	private static final String				ITERATE_KEY				= "ids";

	/** 用于查询跟用户绑定的数字证书 */
	private static final String				USERID_KEY				= "userId";

	/** 缺省可参与指派的角色 */
	private static final String[] ASSIGNE_ARRAY = new String[] {RoleType.CENTER.getValue(),RoleType.FENZHI.getValue(),RoleType.AGENT.getValue()};
	
	/** 查询过滤线程池 */
	private final ITaskCall<Paginater> pageCertTask = new TaskCallHelper<Paginater>();
	
	/** 证书处理线程池 */
	private final ITaskCall<Boolean> certificateTask = new TaskCallHelper<Boolean>();

	/** 机构查询DAO*/
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	/** 用户信息查询DAO*/
	@Autowired
	private UserInfoDAO userInfoDAO;
	
	/** 用户角色信息DAO*/
	@Autowired
	private RoleInfoDAO roleInfoDAO;
	
	/** 商户信息DAO*/
	@Autowired
	private MerchInfoDAO merchInfoDAO;

    /** 用户数字证书表DAO*/
	@Autowired
	@Qualifier("userCertificateRevDAO")
	private UserCertificateRevDAO userCertificateRevDAO;
	
	/** 数字证书构造及访问DAO*/
	@Autowired
	@Qualifier("cardCertificateKeyStore")
	private ICardCertificateKeyStore cardCertificateKeyStore;

	/** 数字证书FTP综合处理*/
	@Autowired
	@Qualifier("cardFileTranferProcess")
	private ICardFileTransferProcess cardFileTransferProcess;

	/** 用户数字证书验签处理*/
	@Autowired
	@Qualifier("cardSignatureProcess")
	private ICardSignatureProcess cardSignatureProcess;

	/** 用户数字证书缓存*/
	@Autowired
	@Qualifier("cardCertificateCache")
	private IUserCertificateCache cardCertificateCache;

	/**内部关联处理类 */
	private final IUserCertificateProcess userCertificateProcess = new UserCertificateProcessImpl();
	
	/**证书实体状态检查MAP*/
	private final Map<String,String> errStateMsgMap = new HashMap<String,String>();
	
	/**缺省需过滤BRANCH属性(json过滤)*/
	private static final String[] branchExcludes = new String[] {"branchAbbname","areaCode","parent","bankNo","bankName","accNo","accName","contact","address","zip",
			                                                "phone","fax","email","status","updateUser","updateTime","developSide","proxy","isSettle","curCode",
			                                                "branchIndex"};
	
	/**缺省过滤的MERCH属性(json过滤) */
	private static final String[] merchExcludes = new String[] {"merchAbb","merchEn","merchCode","merchType","merchAddress","linkMan","telNo","faxNo","areaCode","email",
			                                               "unPay","bankNo","bankName","accNo","accName","setCycle","businessTime","createTime","signingTime","openFlag",
			                                               "currCode","merchLevel","parent","branchCode","status","updateBy","updateTime","manageBranch"};
	
	/**缺省需过滤的USERINFO属性*/
	private static final String[] userExcludes = new String[] {"deptId","userPwd","pwdDate","phone","mobile","email","updateTime","updateUser","state","role","statusName"};
	
	public UserCertificateServiceRevImpl() {
		init();
	}
	
	private void init() {
		String breakErrMsg = "您的数字证书已被暂停使用,不能用于签名验证!";
		errStateMsgMap.put(UserCertificateState.BREAK.getValue(), breakErrMsg);
		
		String unboundErrMsg = "您的数字证书已被解除绑定,不能用于签名验证!";
		errStateMsgMap.put(UserCertificateState.UNBOUND.getValue(), unboundErrMsg);
		
		String assignErrMsg = "您的数字证书已指派需进行绑定,不能用于签名验证";
		errStateMsgMap.put(UserCertificateState.ASSIGNED.getValue(), assignErrMsg);
	}

	// ---------------------------------------------------------处理证书条件查询--------------------------------------------------------------------------------
	/**
	 * 
	 * <p>跟查询的角色及所在机构相关联</p>
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @param loginBranchCode
	 * @param loginRoleType
	 * @return
	 * @throws BizException
	 * @version: 2011-4-26 下午02:28:24
	 * @See:
	 */
	@SuppressWarnings("unchecked")
	public Paginater getUserCertificateList(Map params, int pageNo, int pageSize, String loginBranchCode, String loginRoleType)
			throws BizException {
		try {
			Paginater certificatePage = this.userCertificateRevDAO.find(params, pageNo, pageSize);

			return filterCertificatePage(certificatePage, loginBranchCode, loginRoleType);
		} catch (DataAccessException ex) {
			log.error("查询用户机构证书失败", ex);
			throw new BizException("查询用户机构证书失败,原因[" + ex.getMessage() + "]");
		} catch (Exception ex) {
			log.error("过滤户机构证书失败", ex);
			throw new BizException("过滤用户机构证书失败,原因[" + ex.getMessage() + "]");
		}
	}
	
	/**
	 * 
	  * <p>根据前端证书查询参数查询用户证书列表</p>
	  * @param params
	  * @return
	  * @throws BizException  
	  * @version: 2011-5-19 下午05:31:53
	  * @See:
	 */
	public List<UserCertificate> getUserCertificateList(Map params) throws BizException {
		try {
			return this.userCertificateRevDAO.findUserCertificate(params);
		}catch(DataAccessException ex) {
			log.error("查询用户机构证书失败", ex);
			throw new BizException("查询用户机构证书失败,原因[" + ex.getMessage() + "]");
		}
	}

	public Paginater getUserCertificateList(UserCertificate userCertificate, String useState) throws BizException {
		checkUserCertificate(userCertificate, useState);

		return filterCertificatePage(userCertificate);
	}

	private void checkUserCertificate(UserCertificate userCertificate, String useState) throws BizException {
		if (!userCertificateProcess.checkUserCertificate(userCertificate, useState)) {
			throw new BizException("您传入的数字证书没有满足条件,要求的状态为[" + UserCertificateState.valueOf(useState).getName() + "]");
		}
	}

	/**
	 * 
	 * <p>
	 *     <li>对机构证书分页进行过滤处理(借助内置的线程池)</li> 
	 *     <li>如果登入机构为所属机构或者绑定机构那么均可进行绑定解除</li> 
	 *     <li>如果证书非绑定且机构为运营中心才可以进行删除</li> 
	 *     <li>运营中心可以回收已经指派的数字证书</li>
	 * </p>
	 * @param certificatePage
	 * @return
	 * @version: 2011-4-12 下午11:06:28
	 * @See:
	 */
	private Paginater filterCertificatePage(final Paginater certificatePage, final String loginBranchCode,
			final String loginRoleType) {

		return this.pageCertTask.getFromCallable(new Callable<Paginater>() {
			
			@SuppressWarnings("unchecked")
			public Paginater call() throws Exception {
				Collection<UserCertificate> certificateData = certificatePage.getData();

				Collection<UserCertificate> filterCertificateData = new ArrayList<UserCertificate>(certificateData.size());

				for (UserCertificate certificate : certificateData) {
					checkShowProcess(certificate,loginBranchCode,loginRoleType);

					filterCertificateData.add(certificate);
				}

				certificatePage.setData(filterCertificateData);

				return certificatePage;
			}				
			
			private void checkShowProcess(UserCertificate certificate, String loginBranchCode,String logingRoleType) {
				String _branchCode = certificate.getBranchCode();
				String _bndBranch = certificate.getBndBranch();
				String useState= certificate.getUseState();
				
				//1.1 如果所属机构或绑定机构与登录用户机构一致
				//1.2 如果当前用户为运营中心 ---- 可进行绑定解除
				boolean checkShowUnBound = (CommonHelper.checkEquals(_branchCode, loginBranchCode) 
						                      || CommonHelper.checkEquals(_bndBranch,loginBranchCode)
						                      || (CommonHelper.checkEquals(useState, UserCertificateState.BOUND.getValue())
							                          && CommonHelper.checkEquals(loginRoleType,RoleType.CENTER.getValue())));
				
				//2.1 如果证书已经指派且用户为运营中心 ----- 可进行证书回收
				boolean checkShowRecycle = (CommonHelper.checkEquals(useState, UserCertificateState.ASSIGNED.getValue())
					                          && CommonHelper.checkEquals(loginRoleType,RoleType.CENTER.getValue()));
				
				//3.1 如果证书尚未绑定且用户为运营中心 --- 可进行证书删除
				boolean checkShowRemove = (CommonHelper.checkEquals(useState, UserCertificateState.UNBOUND.getValue())
					                          && CommonHelper.checkEquals(loginRoleType,RoleType.CENTER.getValue()));
				
				//4.1 如果证书尚未绑定且用户为运营中心、分支机构或代理机构 ----- 可进行证书指派(单个控制)
				boolean checkShowAssign = (CommonHelper.checkEquals(useState, UserCertificateState.UNBOUND.getValue())
						                      && (CommonHelper.containsElement(ASSIGNE_ARRAY, loginRoleType)));
				
				//5.1 如果证书已经指派且用户为运营中心、分支机构或代理机构 ----- 可进行证书的绑定
				boolean checkShowBound = (CommonHelper.checkEquals(useState, UserCertificateState.ASSIGNED.getValue())
	                                          && (CommonHelper.containsElement(ASSIGNE_ARRAY, loginRoleType)));
				
				//6.1 如果证书已被暂停且用户为运营中心、分支机构或代理机构 ----- 可进行证书的变更绑定
				boolean checkShowChgBound = (CommonHelper.checkEquals(useState, UserCertificateState.BREAK.getValue())
                        && (CommonHelper.containsElement(ASSIGNE_ARRAY, loginRoleType)));
				
				if(checkShowUnBound) {
					certificate.setShowUnBound(Boolean.TRUE.toString());
				}
				
				if(checkShowRecycle) {
					certificate.setShowRecycle(Boolean.TRUE.toString());
				}
				
				if(checkShowRemove) {
					certificate.setShowRemove(Boolean.TRUE.toString());
				}
				
				if(checkShowAssign) {
					certificate.setShowAssign(Boolean.TRUE.toString());
				}
				
				if(checkShowBound) {
					certificate.setShowBound(Boolean.TRUE.toString());
				}
				
			    if(checkShowChgBound) {
			    	certificate.setShowChgBound(Boolean.TRUE.toString());
			    }
			}			
		});
	}

	private Paginater filterCertificatePage(UserCertificate userCertificate) {
		Paginater certficatePage = new Paginater(1, 1);
		Collection<UserCertificate> pageData = new ArrayList<UserCertificate>(1);
		pageData.add(userCertificate);
		certficatePage.setData(pageData);
		return certficatePage;
	}

	// ----------------------------------------------------查询数字证书明细---------------------------------------------------------------
	public UserCertificate getSelectUserCertificate(UserCertificate userCertificate) throws BizException {
		if (!userCertificateProcess.checkUserCertificate(userCertificate, IUserCertificateProcess.defaultUseState)) {
			throw new BizException("您传入的数字证书查询不完整,请仔细检查!");
		}

		return getDetailUserCertificate(new UserCertificateKey(userCertificate.getDnNo(), userCertificate.getSeqNo(),
				userCertificate.getStartDate()));

	}

	public UserCertificate getDetailUserCertificate(UserCertificateKey certificateKey) throws BizException {
		if ((certificateKey == null) || CommonHelper.isEmpty(certificateKey.getSeqNo())) {
			throw new BizException("您传入的数字证书查询不完整,请仔细检查!");
		}

		try {
			return (UserCertificate) this.userCertificateRevDAO.findByPk(certificateKey);
		} catch (DataAccessException ex) {
			log.error("查询选择用户证书失败", ex);
			throw new BizException("查询选择用户证书失败,原因[" + ex.getMessage() + "]");
		}
	}

	// ----------------------------------------------------查询数字证书状态---------------------------------------------------------------
	public List<UserCertificateState> getUserCertStateList(String loginRoleType) {
		if (loginRoleType.equals(RoleType.CENTER.getValue())) {
			return UserCertificateState.getAllCertificateStateList();
		}

		if (loginRoleType.equals(RoleType.FENZHI.getValue()) || loginRoleType.equals(RoleType.AGENT.getValue())) {
			return UserCertificateState.getMidCertificateStateList();
		}

		return UserCertificateState.getCertificateStateList();
	}
	
	// ----------------------------------------------------查询当前可指派的机构列表---------------------------------------------------------------
	public List<BranchInfo> getAssignBranchInfoList() throws BizException {
		try {
			return this.branchInfoDAO.findByTypes(ASSIGNE_ARRAY);
		}catch(DataAccessException ex) {
			throw new BizException("机构" + CommonHelper.filterArray2Str(ASSIGNE_ARRAY) + "查询失败,原因[" + ex.getMessage() + "]");
		}		
	}
	
	public String getAssignBranchJsonInfo() throws BizException {
		List<BranchInfo> assignBranchInfoList = getAssignBranchInfoList();
		
		return JsonHelper.getJsonString(assignBranchInfoList, branchExcludes, false);
	}
	
	// ----------------------------------------------------根据证书序列号查找用户角色形象---------------------------------------------------------------
	public List<RoleInfo> findRoleInfo(String seqNo) throws BizException {
		try {
			UserCertificate userCertificate = (UserCertificate) this.userCertificateRevDAO.findByPk(new UserCertificateKey(seqNo));
			
			if(userCertificate != null) {
				return this.roleInfoDAO.findByUserId(userCertificate.getUserId());
			}
			
			return Collections.<RoleInfo>emptyList();
			
		}catch(DataAccessException ex) {
			throw new BizException("查询用户角色列表异常，证书序号[" + seqNo + "],原因[" + ex.getMessage() + "]");
		}
	}
	
	/**
	 * 
	  * <p>得到机构自身以及其所管辖机构</p>
	  * @param branchCode
	  * @return
	  * @throws BizException  
	  * @version: 2011-5-25 下午01:39:47
	  * @See:
	 */
	public Object[] getBranchAndMerchInfoList(String branchCode,UserInfo userInfo) throws BizException {
		if(CommonHelper.checkIsEmpty(branchCode)) {
			throw new BizException("机构代号不能为空!");
		}
		
		List<BranchInfo> branchInfoList = null;		
		List<MerchInfo> merchInfoList = null;
		
		try {
			branchInfoList = getMyBranchInfoList(branchCode,userInfo);
			merchInfoList = getMyMerchInfoList(branchCode,userInfo);
		}catch(DataAccessException ex) {
			throw new BizException("获得[" + branchCode + "]关联发卡机构或商户机构列表异常,原因[" + ex.getMessage() + "]");
		}		
		
		return new Object[] {branchInfoList, merchInfoList};
	}
	
	/**
	 * 
	  * <p>增加查询所在发卡机构的查询(自动查询支持)</p>
	  * @param branchCode
	  * @param userInfo
	  * @return
	  * @throws BizException  
	  * @version: 2011-6-3 上午10:50:28
	  * @See:
	 */
	public String getMyBranchJsonInfo(String branchCode, UserInfo userInfo) throws BizException {
		if(CommonHelper.checkIsEmpty(branchCode)) {
			throw new BizException("机构代号不能为空!");
		}
		try {
			List<BranchInfo> myBranchList = getMyBranchInfoList(branchCode,userInfo);
			
			Collections.sort(myBranchList,new BranchInfoComparator());
			
			return JsonHelper.getJsonString(myBranchList, branchExcludes, false);
			
		}catch(Exception ex) {
			log.error("获取发卡机构关联列表异常", ex);
			throw new BizException("获得[" + branchCode + "]关联发卡机构列表异常,原因[" + ex.getMessage() + "]");
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<BranchInfo> getMyBranchInfoList(String branchCode,UserInfo userInfo) throws DataAccessException {
		List<BranchInfo> myBranchInfoList = new ArrayList<BranchInfo>();
		
		boolean centerFlag = checkIsCenter(branchCode,userInfo);
		
		if(centerFlag) {
			myBranchInfoList.addAll(this.branchInfoDAO.findAll());
		} else {
			myBranchInfoList.add(this.branchInfoDAO.findBranchInfo(branchCode));		
			myBranchInfoList.addAll(this.branchInfoDAO.findByManange(branchCode));
		}
		
		return myBranchInfoList;
	}
	
	/**
	 * 
	  * <p>增加查询所在商户机构的查询(自动查询支持)</p>
	  * @param branchCode
	  * @param userInfo
	  * @return
	  * @throws BizException  
	  * @version: 2011-6-3 上午10:50:53
	  * @See:
	 */
	public String getMyMerchJsonInfo(String branchCode, UserInfo userInfo) throws BizException {
		if(CommonHelper.checkIsEmpty(branchCode)) {
			throw new BizException("机构代号不能为空!");
		}
		
		try {
			List<MerchInfo> myMerchList =  getMyMerchInfoList(branchCode,userInfo);
			
			Collections.sort(myMerchList, new MerchInfoComparator());
			
			return JsonHelper.getJsonString(myMerchList, merchExcludes, false);
		}catch(Exception ex) {
			log.error("获取商户机构关联列表异常", ex);
			throw new BizException("获得[" + branchCode + "]关联商户机构列表异常,原因[" + ex.getMessage() + "]");
		}		
	}
	
	@SuppressWarnings("unchecked")
	private List<MerchInfo> getMyMerchInfoList(String branchCode,UserInfo userInfo) throws DataAccessException {
		List<MerchInfo> myMerchInfoList = new ArrayList<MerchInfo>();
		
		boolean centerFlag = checkIsCenter(branchCode,userInfo);
		
		if(centerFlag) {
			myMerchInfoList.addAll(this.merchInfoDAO.findAll());
		} else {
			myMerchInfoList.addAll(this.merchInfoDAO.findByManage(branchCode));
		}
		
		return myMerchInfoList;
	}
	
	private boolean checkIsCenter(String branchCode, UserInfo userInfo) {
		//1.1 检查登录用户是否是中心角色
		boolean isCenterRole = RoleType.valueOf(userInfo.getRole().getRoleType()).equals(RoleType.CENTER);
		
		if(isCenterRole) {
		   //1.2 再检查传入的机构编号是否是中心对应的编号 	
		   return userInfo.getBranchNo().equals(branchCode);	
		}
		
		return false;
	}
	
	/**
	 * 
	  * @description：
	  * @param branchCode
	  * @param branchType
	  * @param userInfo
	  * @return
	  * @throws BizException  
	  * @version: 2011-6-1 下午12:40:41
	  * @See:
	 */
	public String getBoundUserJsonInfo(String branchCode, String branchType, String userId, UserInfo userInfo) throws BizException {
		if(CommonHelper.checkIsEmpty(branchCode)) {
			throw new BizException("机构代号不能为空!");
		}
		
		if(!UserBranchType.ALL.containsKey(branchType)) {
			throw new BizException("机构用户类型设定错误[" + branchType + "]!");
		}
		
		Map<String,Object> paraMap = new HashMap<String,Object>();  
		
		int compareFlag = 0;
		
		if(branchType.equals(UserBranchType.branchUserType.getValue())) {
			List<BranchInfo> myBranchInfoList = getMyBranchInfoList(branchCode,userInfo);
			
			paraMap.put("branchs", myBranchInfoList);
			
			compareFlag = BRANCH_COMPARE_FLAG;
			
			
		} else if (branchType.equals(UserBranchType.merchUserType.getValue())) {
			List<MerchInfo> myMerchInfoList = getMyMerchInfoList(branchCode,userInfo);
			
			paraMap.put("merchs", myMerchInfoList);
			
			compareFlag = MERCH_COMPARE_FLAG;
		}
		
		List<UserInfo> userInfoList = getCertificateUserList(paraMap,userId,compareFlag);
		
		return JsonHelper.getJsonString(userInfoList,userExcludes,false);
	}
	
	
	// --------------------------------------------------------处理证书文件录入--------------------------------------------------------------------------------
	public boolean processUserCertificateUpload(File uploadFile, String uploadName, String branchCode, UserInfo loginUser)
			throws BizException, RuntimeBizException {
		// 1.1 检查上传文件大小
		if (!(checkValidUploadFile(uploadFile))) {
			throw new BizException("您上传的文件过大,请重新上传!");
		}

		// 1.2 从系统参数表中获得文件支持后缀
		String zipSuffix = SysparamCache.getInstance().getUploadCertZipFileSuffix();

		String suffix = SysparamCache.getInstance().getUploadCertFileFuffix();

		boolean isZipSuffix = uploadName.endsWith(zipSuffix);

		// 1.3 对压缩文件格式支持
		if (isZipSuffix) {
			return processZipUserCertificateUpload(uploadFile, uploadName, suffix, branchCode, loginUser);
		}

		// 1.4 对单个文件支持
 		boolean isSingleSuffix = uploadName.endsWith(suffix);

		if (isSingleSuffix) {
			return processSingleUserCertificateUpload(uploadFile, uploadName, branchCode, loginUser);
		}

		throw new BizException("上传文件不符合系统要求,请上传后缀为[" + zipSuffix + "]的压缩文件，或后缀为[" + suffix + "]的证书文件");
	}

	/**
	 * 
	 * @description：<p>检查文件大小</li>
	 * @param uploadFile
	 * @return
	 * @version: 2011-4-26 下午07:21:02
	 * @See:
	 */
	private boolean checkValidUploadFile(File uploadFile) {
		return (uploadFile.length() < UserCertificateRevService.MAX_FILE_SIZE);
	}

	/**
	 * 
	 * <p>处理压缩文件等解析</p>
	 * @param zipUpload
	 * @param zipFileName
	 * @param suffix
	 * @param branchCode
	 * @param deptId
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @version: 2011-4-12 下午07:30:08
	 * @See:
	 */
	private boolean processZipUserCertificateUpload(File zipUpload, String zipFileName, String suffix, String branchCode,
			UserInfo loginUser) throws BizException, RuntimeBizException {

		String localPath = SysparamCache.getInstance().getUploadCertLocalSavePath();
		
		/** 使用系统临时文件夹替换  */
		//String tempPath = ParaMgr.getInstance().getCardCertificateTempPath();
		File tempPath = FileHelper.getLocalTempDir(System.getProperty("java.io.tmpdir"));
		
		try {
			Object[] extractFiles = FileHelper.extractZipFiles(zipUpload, zipFileName, localPath, tempPath, suffix);

			if (CommonHelper.checkIsNotEmpty(extractFiles)) {
				File[] uploadFiles = (File[]) extractFiles[0];

				File tempFileDir = (File) extractFiles[1];

				boolean uploadFlag = processUserCertificateUpload(uploadFiles, branchCode, loginUser);

				if (uploadFlag) {
					try {
						FileHelper.forceDeleteFile(tempFileDir);
					} catch (Exception ex) {
						log.error("删除临时目录[" + tempFileDir.getAbsolutePath() + "]失败 ，原因[" + ex.getMessage() + "]");
					}
				}

				return uploadFlag;
			}
		} catch (Exception ex) {
			throw new BizException("提取上传文件失败,原因[" + ex.getMessage() + "]");
		}

		return false;
	}

	/**
	 * 
	 * <p>处理单个文件上传</p>
	 * @param upload
	 * @param fileName
	 * @param branchCode
	 * @param deptId
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @version: 2011-4-12 下午07:30:17
	 * @See:
	 */
	private boolean processSingleUserCertificateUpload(File upload, String fileName, String branchCode, UserInfo loginUser)
			throws BizException, RuntimeBizException {
		String localPath = SysparamCache.getInstance().getUploadCertLocalSavePath();

		File[] uploadFiles = null;
		try {
			uploadFiles = FileHelper.getLocalFiles(upload, fileName, localPath);
		} catch (Exception ex) {
			throw new BizException("提取上传文件失败,原因[" + ex.getMessage() + "]");
		}

		if (CommonHelper.checkIsEmpty(uploadFiles)) {
			return false;
		}

		return processUserCertificateUpload(uploadFiles, branchCode, loginUser);
	}

	/**
	 * 
	 * <p>处理文件的上传与数据库的持久化</p>
	 * @param uploadFiles
	 * @param branchCode
	 * @param deptId
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @version: 2011-4-12 下午08:21:33
	 * @See:
	 */
	private boolean processUserCertificateUpload(File[] uploadFiles, String branchCode, UserInfo loginUser) throws BizException {

		boolean uploadProcess = false;

		Map<UserCertificate, File> branchCertificateMap = null;
		try {
			// 1.1 根据证书文件构造内部处理结构Map<UserCertificate, File>
			branchCertificateMap = this.cardCertificateKeyStore.getUserCertificateMap(uploadFiles, branchCode, loginUser);

			// 1.2 根据上述处理结构进行上传和持久化
			uploadProcess = processUserCertificatePersist(branchCertificateMap, loginUser);
		} catch (CertOperateException ex) {
			throw new BizException(ex.getMessage());
		} finally {
			// 1.3 及时回收内部处理结构
			branchCertificateMap = null;
		}

		return uploadProcess;
	}
	
	/**
	 * <p>处理文件的上传与数据库的持久化</p>
	 * <p>用于证书更新</p>
	 * @param uploadFile
	 * @param branchCode
	 * @param deptId
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @version: 2011-4-12 下午08:21:33
	 * @See:
	 */
	private boolean processUserCertificateUpload(Map<UserCertificate, File> branchCertificateMap, UserInfo loginUser, UserCertificate boundedUserCertificate) throws BizException {
		boolean uploadProcess = false;
		try {
			// 1.1 将证书数据加入旧的绑定信息
			wrapBranchCertificateMap(branchCertificateMap, boundedUserCertificate, loginUser);
			// 1.2 根据上述处理结构进行上传和持久化
			uploadProcess = processUserCertificatePersist(branchCertificateMap, loginUser);
			cardCertificateCache.removeCertificateBean(boundedUserCertificate.getSeqNo());//移除旧证书缓存
		} catch (CertOperateException ex) {
			throw new BizException(ex.getMessage());
		} finally {
			// 1.3 及时回收内部处理结构
			branchCertificateMap = null;
		}
		return uploadProcess;
	}
	
	@Override
	public boolean updateCertFile(File uploadFile, String uploadFileName, UserCertificate oldUserCertificate,
			UserInfo loginUser) throws BizException {
		if (uploadFile == null || uploadFile.length() < 0) {
			throw new BizException("证书文件不允许为空!");
		}
		if (!checkValidUploadFile(uploadFile)) {
			throw new BizException("您上传的文件过大,请重新上传!");
		}
		String suffix = SysparamCache.getInstance().getUploadCertFileFuffix();
		if (!StringUtils.endsWithIgnoreCase(uploadFileName, suffix)) {
			throw new BizException("上传文件不符合系统要求,请上传后缀为[" + suffix + "]的证书文件");
		}

		// 1.4 提取上传的证书文件到本地
		String localPath = SysparamCache.getInstance().getUploadCertLocalSavePath();
		File[] uploadFiles = null;
		try {
			uploadFiles = FileHelper.getLocalFiles(uploadFile, uploadFileName, localPath);
		} catch (Exception ex) {
			throw new BizException("提取上传文件失败,原因[" + ex.getMessage() + "]");
		}

		// 根据证书文件构造内部处理结构Map<UserCertificate, File>
		Map<UserCertificate, File> branchCertificateMap = this.cardCertificateKeyStore.getUserCertificateMap(
				uploadFiles, oldUserCertificate.getBranchCode(), loginUser);

		// 检查新上传的证书(是否已存在)
		checkCertificateMap(branchCertificateMap, oldUserCertificate);

		// 移除旧证书
		UserCertificate tmpUserCertificate = (UserCertificate) oldUserCertificate.clone();
		tmpUserCertificate.setUseState(UserCertificateState.UNBOUND.getValue());// 为了复用移除方法而做的操作
		boolean removeFlag = processUserCertificateRemove(tmpUserCertificate, loginUser);
		if (removeFlag) {
			log.info("移除旧证书信息成功!:{} {} {} {}", new String[] { tmpUserCertificate.getDnNo(),
					tmpUserCertificate.getSeqNo(), tmpUserCertificate.getStartDate(), tmpUserCertificate.getUserId() });
		} else {
			throw new BizException("旧证书移除失败,不再进行证书更新操作!");
		}

		// 上传新证书并绑定机构用户,完成证书更新
		return processUserCertificateUploadAndBound(branchCertificateMap, loginUser, oldUserCertificate);
	}
	
	private void checkCertificateMap(Map<UserCertificate, File> branchCertificateMap, UserCertificate oldUserCertificate)
			throws BizException {

		for (Map.Entry<UserCertificate, File> entry : branchCertificateMap.entrySet()) {
			UserCertificate userCertificate = entry.getKey();
			UserCertificate uc = (UserCertificate) userCertificateRevDAO.findByPk(new UserCertificateKey(
					userCertificate.getDnNo(), userCertificate.getSeqNo()));
			if (uc != null) {
				throw new BizException("数据库中已存在相同的证书!" + userCertificate.getSeqNo());
			}

			// 新证书文件与旧证书文件不同名，则检查上传目录是否已存在相同名字的证书
			if (!userCertificate.getFileName().equals(oldUserCertificate.getFileName())) {
				String remoteFileName = cardFileTransferProcess.getRemoteFilePath(userCertificate.getFileName());
				if (CommonHelper.isNotEmpty(remoteFileName)) {
					throw new BizException("证书上传目录已存在相同名字的证书! [" + userCertificate.getFileName() + "]");
				}
			}
		}
	}
	
	/**
	 * 将证书数据加入旧的绑定信息
	 * @Date 2013-4-20上午10:46:31
	 * @return void
	 */
	private void wrapBranchCertificateMap(Map<UserCertificate, File> branchCertificateMap, UserCertificate boundedUserCertificate, UserInfo loginUser) throws BizException {
		try {
			for (Map.Entry<UserCertificate, File> entry : branchCertificateMap.entrySet()) {
				UserCertificate userCertificate = entry.getKey();
				
//				userCertificate.setBranchCode(boundedUserCertificate.getBranchCode());//前面已设置
				userCertificate.setUseState(UserCertificateState.BOUND.getValue());
				userCertificate.setBndBranch(boundedUserCertificate.getBndBranch());//绑定旧的机构和用户
				userCertificate.setUserId(boundedUserCertificate.getUserId());
				userCertificate.setState(CertificateState.VALID.getValue());//默认有效
			}			
		} catch (Exception ex) {
			log.error("证书上传处理异常", ex);
			throw new BizException("证书上传处理异常,原因[" + ex.getMessage() + "]");
		} 
	}

	/**
	 * 
	 * <p>获得待持久化的数字证书并进行批处理写入</p>
	 * @param userCertificateMap
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @version: 2011-4-12 下午10:46:19
	 * @See:
	 */
	@SuppressWarnings("unchecked")
	private boolean processUserCertificatePersist(Map<UserCertificate, File> userCertificateMap, UserInfo loginUser)
			throws BizException {

		// 1.1 获得待后续处理的集合
		Object[] persistUserCertificateResult = getPersistUserCertificateArray(userCertificateMap, loginUser);
		List<UserCertificate> persistCertificates = (List<UserCertificate>) persistUserCertificateResult[0];
		Set<String> repeatCertificates = (Set<String>) persistUserCertificateResult[1];

		// 1.2 进行比较处理
		boolean checkHasPersist = CommonHelper.checkIsNotEmpty(persistCertificates);
		boolean checkHasRepeat = CommonHelper.checkIsNotEmpty(repeatCertificates);

		if (checkHasRepeat) {
			throw new BizException("您上传了重复的证书文件"
					+ CommonHelper.filterArray2Str(repeatCertificates.toArray(new String[repeatCertificates.size()]))
					+ ",请重新检查上传文件!");
		}

		if (checkHasPersist) {
			try {
				this.userCertificateRevDAO.insertBatch(persistCertificates);
			} catch (DataAccessException ex) {
				log.error("证书上传文件保存异常", ex);
				throw new BizException("证书上传文件保存异常,原因[" + ex.getMessage() + "]");
			}

			return true;

		}

		return false;
	}

	/**
	 * 
	 * <p>基于多线程的方式上传文件</p>
	 * @param userCertificateMap
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @version: 2011-4-12 下午08:16:34
	 * @See:
	 */
	private Object[] getPersistUserCertificateArray(Map<UserCertificate, File> userCertificateMap, UserInfo loginUser)
			throws BizException {
		List<UserCertificate> persistCertificates = new ArrayList<UserCertificate>(userCertificateMap.size());

		Set<Integer> hashCertificates = new LinkedHashSet<Integer>(userCertificateMap.size());

		Set<String> repeatCertificates = new LinkedHashSet<String>();

		try {
			for(Iterator<Map.Entry<UserCertificate, File>> iterator = userCertificateMap.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<UserCertificate, File> entry = iterator.next();
				
				UserCertificate userCertificate = entry.getKey();
				
				File certFile = entry.getValue();
				
				int certHash = userCertificate.hashCode();

				// 1.1 检查这个批次中是否存在相同的证书
				if (hashCertificates.contains(certHash)) {
					repeatCertificates.add(userCertificate.getUploadName());
					continue;
				}

				hashCertificates.add(certHash);

				// 1.2 检查数据库中是否含有相同的证书
				boolean result = handleCertificateUpload(userCertificate, certFile, loginUser);

				if (result) {
					persistCertificates.add(userCertificate);
				} else {
					repeatCertificates.add(userCertificate.getUploadName());
				}
			}			
		} catch (Exception ex) {
			log.error("证书上传处理异常", ex);
			throw new BizException("证书上传处理异常,原因[" + ex.getMessage() + "]");
		} finally {
			hashCertificates = null;
		}

		return new Object[] { persistCertificates, repeatCertificates };
	}

	private boolean handleCertificateUpload(final UserCertificate userCertificate, final File certFile, final UserInfo loginUser)
			throws TaskException {
		return this.certificateTask.getFromCallable(new Callable<Boolean>() {

			public Boolean call() throws Exception {
				boolean hasUserCertificate = checkHasUserCertificate();

				return processUserCertificate(hasUserCertificate);
			}

			private boolean checkHasUserCertificate() throws Exception {
				UserCertificate searchUserCertificate = getSearchUserCertificate(userCertificate);

				return (searchUserCertificate != null);
			}

			private boolean processUserCertificate(boolean hasUserCertificate) throws Exception {
				boolean result = false;

				boolean uploadTransfer = cardFileTransferProcess.uploadTransferFile(certFile, userCertificate.getFileName());

				if (hasUserCertificate) {
					log.warn("客户[" + loginUser.getUserId() + "]提交了已存在的文件[" + userCertificate.getUploadName() + "]");
				} else {
					result = true;
				}

				if (!uploadTransfer) {
					log.warn("=============服务端已存在相应文件或传输未完成=========文件{" + userCertificate.getFileName() + "}");
				}

				return result;
			}
			
			private UserCertificate getSearchUserCertificate(UserCertificate userCertificate) throws DataAccessException {
				return (UserCertificate) userCertificateRevDAO.findByPk(new UserCertificateKey(userCertificate.getDnNo(),userCertificate.getSeqNo()));
			}
		});
	}

	

	// ------------------------------------------------------------处理证书指派---------------------------------------------------------------------
	public boolean processUserCertificatAssign(UserCertificate[] unBoundUserCertificates, BranchInfo branchInfo,
			UserInfo loginUser) throws BizException {
		checkUnBoundUserCertificates(unBoundUserCertificates, branchInfo);

		return _processUserCertificatAssign(unBoundUserCertificates, branchInfo, loginUser);
	}

	public boolean processUserCertificatAssign(String[] unBoundUserCertificates, BranchInfo branchInfo, UserInfo loginUser)
			throws BizException {
		checkUnBoundUserCertificates(unBoundUserCertificates, branchInfo);

		List<UserCertificate> list = getUnBoundUserCertificates(unBoundUserCertificates);

		return _processUserCertificatAssign(list.toArray(new UserCertificate[list.size()]), branchInfo, loginUser);
	}

	/**
	 * 
	 * <p>检查传入的数字证书</li>
	 * @param unBoundUserCertificates
	 * @throws BizException
	 * @version: 2011-4-26 下午02:07:36
	 * @See:
	 */
	private void checkUnBoundUserCertificates(UserCertificate[] unBoundUserCertificates, BranchInfo branchInfo)
			throws BizException {
		if (CommonHelper.checkIsEmpty(unBoundUserCertificates)) {
			throw new BizException("待指派数字证书不能为空!");
		}

		if (branchInfo == null || CommonHelper.checkIsEmpty(branchInfo.getBranchCode())) {
			throw new BizException("待指派数字证书对应机构不能为空!");
		}

		for (UserCertificate certificate : unBoundUserCertificates) {
			if (!userCertificateProcess.checkUserCertificate(certificate, UserCertificateState.UNBOUND.getValue())) {
				throw new BizException("您所选择的证书不符合指派的条件(需未绑定)，无法指派!");
			}
		}
	}

	private void checkUnBoundUserCertificates(String[] unBoundUserCertificates, BranchInfo branchInfo) throws BizException {
		if (CommonHelper.checkIsEmpty(unBoundUserCertificates)) {
			throw new BizException("待指派数字证书不能为空!");
		}

		if (branchInfo == null || CommonHelper.checkIsEmpty(branchInfo.getBranchCode())) {
			throw new BizException("待指派数字证书对应机构不能为空!");
		}		
	}

	/**
	 * 
	 * <p>将未绑定的证书集合进行指派(到某家机构)</p>
	 * @param unBoundUserCertificates
	 * @param branchInfo
	 * @param loginUser
	 * @return
	 * @version: 2011-4-26 下午02:17:56
	 * @See:
	 */
	private List<UserCertificate> getAssignedUserCertificates(UserCertificate[] unBoundUserCertificates, BranchInfo branchInfo,
			UserInfo loginUser) {
		List<UserCertificate> assignedCertificateList = new ArrayList<UserCertificate>(unBoundUserCertificates.length);

		for (UserCertificate certificate : unBoundUserCertificates) {
			certificate = userCertificateProcess.getAssignUserCertificate(certificate, branchInfo, loginUser);
			assignedCertificateList.add(certificate);
		}

		return assignedCertificateList;
	}

	@SuppressWarnings("unchecked")
	private List<UserCertificate> getUnBoundUserCertificates(String[] unBoundUserCertificates) throws BizException {		
		Map paraMap = new HashMap(1);
		paraMap.put(ITERATE_KEY, unBoundUserCertificates);

		try {
			return this.userCertificateRevDAO.findUserCertificate(paraMap);
		} catch (DataAccessException ex) {
			log.error("获得数字证书异常", ex);
			throw new BizException("获得数字证书异常,证书列表" + CommonHelper.filterArray2Str(unBoundUserCertificates) + ",原因[" + ex.getMessage() + "]");
		}

	}

	private boolean _processUserCertificatAssign(UserCertificate[] unBoundUserCertificates, BranchInfo branchInfo,
			UserInfo loginUser) throws BizException {
		List<UserCertificate> assignedCertificateList = getAssignedUserCertificates(unBoundUserCertificates, branchInfo,
				loginUser);

		if (CommonHelper.checkIsEmpty(assignedCertificateList)) {
			return false;
		}

		try {
			this.userCertificateRevDAO.updateBatch(assignedCertificateList);
		} catch (DataAccessException ex) {
			log.error("证书指派失败", ex);
			throw new BizException("证书指派失败,指派机构[" + branchInfo.getBranchCode() + "],指派人[" + loginUser.getUserId() + "],原因["
					+ ex.getMessage() + "]");
		}

		return true;
	}

	// -------------------------------------------------------------处理证书移除---------------------------------------------------------------------
	public boolean processUserCertificateRemove(UserCertificate unBoundUserCertificate, UserInfo loginUser) throws BizException {
		checkRemoveUserCertificates(unBoundUserCertificate, loginUser);

		UserCertificate removeCertificate = this.userCertificateProcess.getRemoveUserCertificate(unBoundUserCertificate, loginUser);
		
		try {
			int updateCount = this.userCertificateRevDAO.delete(removeCertificate);

			if (updateCount > 0) {
				return this.cardFileTransferProcess.removeRemoteFile(new String[] { removeCertificate.getFileName() });
			}
		} catch (DataAccessException ex) {
			log.error("证书移除失败", ex);
			throw new BizException("证书移除失败,指派人[" + loginUser.getUserId() + "],原因[" + ex.getMessage() + "]");
		} catch (CommunicationException ex) {
			log.error("证书移除失败", ex);
			throw new BizException("证书移除失败,指派人[" + loginUser.getUserId() + "],原因[" + ex.getMessage() + "]");
		}

		return false;
	}

	/**
	 * 
	 * <p>检查是否可进行证书的删除</p>
	 * @param unBoundUserCertificate
	 * @param loginUser
	 * @throws BizException
	 * @version: 2011-4-26 下午03:18:03
	 * @See:
	 */
	private void checkRemoveUserCertificates(UserCertificate unBoundUserCertificate, UserInfo loginUser) throws BizException {
		boolean checkUnBound = 
			userCertificateProcess.checkUserCertificate(unBoundUserCertificate, UserCertificateState.UNBOUND.getValue());

		if (!checkUnBound) {
			throw new BizException("您所选择的证书不符合删除的条件(需未指派)，无法删除!");
		}
		
        String removeFile = unBoundUserCertificate.getFileName();
		
		if(CommonHelper.checkIsEmpty(removeFile)) {
			throw new BizException("您所选择删除的证书文件不能为空!");
		}

		boolean checkCenter = RoleType.valueOf(loginUser.getRole().getRoleType()).equals(RoleType.CENTER);

		if (!checkCenter) {
			throw new BizException("您的角色[" + loginUser.getRole().getRoleType() + "]不具备删除证书的权限!");
		}	
	}

	// -------------------------------------------------------------处理证书回收---------------------------------------------------------------------
	public boolean processUserCertificateRecycle(UserCertificate assignUserCertificate, UserInfo loginUser) throws BizException {
		checkRecyleUserCertificate(assignUserCertificate, loginUser);

		UserCertificate recycleCertificate = userCertificateProcess.getRecycleUserCertificate(assignUserCertificate, loginUser);

		try {
			int updateCount = this.userCertificateRevDAO.update(recycleCertificate);

			return (updateCount > 0);
		} catch (DataAccessException ex) {
			log.error("证书回收失败", ex);
			throw new BizException("证书回收失败,指派人[" + loginUser.getUserId() + "],原因[" + ex.getMessage() + "]");
		}
	}

	private void checkRecyleUserCertificate(UserCertificate assignUserCertificate, UserInfo loginUser) throws BizException {
		boolean checkAssign = userCertificateProcess.checkUserCertificate(assignUserCertificate, UserCertificateState.ASSIGNED.getValue());

		if (!checkAssign) {
			throw new BizException("您所选择的证书不符合回收的条件(需已指派)，无法回收!");
		}

		boolean checkCenter = RoleType.valueOf(loginUser.getRole().getRoleType()).equals(RoleType.CENTER);

		if (!checkCenter) {
			throw new BizException("您的角色[" + loginUser.getRole().getRoleType() + "]不具备回收证书的权限!");
		}
	}

	// -------------------------------------------------------------处理证书绑定---------------------------------------------------------------------
	public boolean processUserCertificateBound(UserInfo assignUser, UserCertificate assignUserCertificate, UserInfo loginUser)
			throws BizException {
		/**
		 * @Patch:<tt>检查用户是否已经绑定书证书(2011-07-28)</tt>
		 * @Modified:aps-bwzhang
		 */
		checkUserHasCertificate(assignUser);
		checkAssignUserCertificate(assignUser, assignUserCertificate);
		UserCertificate boundUserCertificate = userCertificateProcess.getBoundUserCertificate(assignUser, assignUserCertificate,loginUser);
		try {
			this.userCertificateRevDAO.update(boundUserCertificate);
			this.cardCertificateCache.removeCertificateBean(boundUserCertificate.getSeqNo());
		} catch (DataAccessException ex) {
			log.error("证书绑定失败", ex);
			throw new BizException("证书绑定失败,绑定用户[" + assignUser.getUserId() + "],原因[" + ex.getMessage() + "]");
		}
		return true;
	}
	
	private void checkAssignUserCertificate(UserInfo assignUser, UserCertificate assignUserCertificate) throws BizException {
		if (assignUser == null || assignUser.getUserId() == null) {
			throw new BizException("用户信息不能为空！");
		}

		if (!userCertificateProcess.checkUserCertificate(assignUserCertificate, UserCertificateState.ASSIGNED.getValue())) {
			throw new BizException("您所选择的证书不符合绑定的条件(需指派)，无法绑定!");
		}
	}
	
    private void checkUserHasCertificate(UserInfo assignUser) throws BizException {
		Map<String,String> params = new HashMap<String,String>();
		
		params.put(USERID_KEY, assignUser.getUserId());
		
		List<UserCertificate> userCertificateList = this.userCertificateRevDAO.findUserCertificate(params);
		
		if(CommonHelper.checkIsEmpty(userCertificateList)) {
			return ;
		}
		
		boolean userHasBound = false;
		
		for(UserCertificate userCertificate: userCertificateList) {
		    String useState = userCertificate.getUseState();
		    
		    if(CommonHelper.isEmpty(useState)) {
		    	continue;
		    }
		    
		    if(useState.equals(UserCertificateState.BOUND.getValue())) {
		    	userHasBound = true;
		    	break;
		    }
		}
		
		if(userHasBound) {
			throw new BizException("用户[" + assignUser.getUserId() +"]已绑定证书,无法再次进行绑定!");
		}		
	}

	// -------------------------------------------------------------处理证书解除绑定----------------------------------------------------------------
	public boolean processUserCertificateUnBound(UserCertificate boundUserCertificate, UserInfo loginUser) throws BizException {
		checkBoundUserCertificate(boundUserCertificate);
		UserCertificate userCertificateUnBound = userCertificateProcess.getUnBoundUserCertificate(boundUserCertificate, loginUser);
		try {
			this.userCertificateRevDAO.update(userCertificateUnBound);
			this.cardCertificateCache.removeCertificateBean(userCertificateUnBound.getSeqNo());
		} catch (DataAccessException ex) {
			log.error("证书绑定解除失败", ex);
			throw new BizException("用户[" + boundUserCertificate.getUserId() + "]证书绑定解除失败,原因[" + ex.getMessage() + "]");
		}
		return true;
	}

	private void checkBoundUserCertificate(UserCertificate boundUserCertificate) throws BizException {
		if (!userCertificateProcess.checkUserCertificate(boundUserCertificate, UserCertificateState.BOUND.getValue())) {
			throw new BizException("您申请解除绑定的证书尚未绑定,无法执行绑定解除！");
		}
	}

	// --------------------------------------------------------------处理证书暂停-------------------------------------------------------------------
	public boolean processUserCertificateBreak(UserCertificate boundUserCertificate, UserInfo loginUser) throws BizException {
		_checkBoundUserCertificate(boundUserCertificate);
		UserCertificate userCertificateBreak = userCertificateProcess.getBreakUserCertificate(boundUserCertificate, loginUser);
		try {
			this.userCertificateRevDAO.update(userCertificateBreak);
			this.cardCertificateCache.removeCertificateBean(userCertificateBreak.getSeqNo());
		} catch (DataAccessException ex) {
			log.error("证书绑定解除失败", ex);
			throw new BizException("用户[" + boundUserCertificate.getUserId() + "]证书绑定暂停失败,原因[" + ex.getMessage() + "]");
		}

		return true;
	}

	private void _checkBoundUserCertificate(UserCertificate boundUserCertificate) throws BizException {
		if (!userCertificateProcess.checkUserCertificate(boundUserCertificate, UserCertificateState.BOUND.getValue())) {
			throw new BizException("您申请解除绑定的证书尚未绑定,无法执行绑定暂停！");
		}
	}

	// -----------------------------------------------------处理证书重新绑定(变更绑定)----------------------------------------------------
	public boolean processUserCertificateReBound(UserCertificate breakUserCertificate, UserInfo loginUser) throws BizException {
		checkBreakUserCertificate(breakUserCertificate);
		UserCertificate userCertificateRebound = userCertificateProcess.getReBoundUserCertificate(breakUserCertificate, loginUser);
		try {
			this.userCertificateRevDAO.update(userCertificateRebound);
			this.cardCertificateCache.removeCertificateBean(userCertificateRebound.getSeqNo());
		} catch (DataAccessException ex) {
			throw new BizException("用户[" + breakUserCertificate.getUserId() + "]证书绑定暂停失败,原因[" + ex.getMessage() + "]");
		}

		return true;
	}

	public boolean processUserCertificateChgBound(UserInfo assignUser, UserCertificate breakUserCertificate, UserInfo loginUser) throws BizException {
		checkBreakUserCertificate(breakUserCertificate,assignUser);
		UserCertificate userCertificateChgBound = userCertificateProcess.getBoundUserCertificate(assignUser,breakUserCertificate, loginUser);
		try {
			this.userCertificateRevDAO.update(userCertificateChgBound);
			this.cardCertificateCache.removeCertificateBean(userCertificateChgBound.getSeqNo());
		} catch (DataAccessException ex) {
			throw new BizException("用户[" + breakUserCertificate.getUserId() + "]证书变更绑定失败,原因[" + ex.getMessage() + "]");
		}
		return true;
	}
	
	private void checkBreakUserCertificate(UserCertificate breakUserCertificate) throws BizException {
		if (!userCertificateProcess.checkUserCertificate(breakUserCertificate, UserCertificateState.BREAK.getValue())) {
			throw new BizException("您申请重新绑定的证书尚未暂停,无法执行重新绑定！");
		}
	}
	
	private void checkBreakUserCertificate(UserCertificate breakUserCertificate,UserInfo assignUser) throws BizException {
		if (!userCertificateProcess.checkUserCertificate(breakUserCertificate, UserCertificateState.BREAK.getValue())) {
			throw new BizException("您申请变更绑定的证书尚未暂停,无法执行重新绑定！");
		}
		
		if(breakUserCertificate.getUserId().equals(assignUser.getUserId())) {
			throw new BizException("您申请变更绑定的证书不能指向同一用户[" + assignUser.getUserId()+"]");
		}
	}

	// --------------------------------------------------------------处理证书签名--------------------------------------------------------------------
	/**
	 * <p>先判断缓存中是否存在该记录 如果没有则从数据库中加载并读取如果获取不到则提示错误信息</p>
	 */
	public boolean processUserSigVerify(String seqNo, String hexSignedInfo, String info) throws BizException {
		// 1.1 从缓存中读取证书绑定实体
		UserCertificateBean certificateBean = getUserCertificateBean(seqNo);
    		
        // 1.2 对绑定实体进行验证
		try {
			return this.cardSignatureProcess.verifySignedInfo(certificateBean, hexSignedInfo, info);
		} catch (Exception ex) {
			throw new BizException("签名验证失败，原因[" + ex.getMessage() + "]", ex);
		}
	}
	
	private UserCertificateBean getUserCertificateBean(String seqNo) throws BizException {
		if (CommonHelper.checkIsEmpty(seqNo)) {
			throw new BizException("证书序列号不能为空!");
		}
		UserCertificateBean certificateBean = this.cardCertificateCache.getCertificateBean(seqNo);
		if (certificateBean == null) {
			throw new BizException("您的数字证书[" + CommonHelper.safeTrim(seqNo) + "]记录不存在，无法进行签名验证!");
		} else if (errStateMsgMap.containsKey(certificateBean.getUseState())) {
			throw new BizException(errStateMsgMap.get(certificateBean.getUseState()));
		}
		return certificateBean;
	}
	
	//------------------------------------------------处理证书视图输出------------------------------------------------------------
	public void processUserCertificateExport(Map certParaMap,HttpServletRequest request, HttpServletResponse response) throws BizException {
	    List<UserCertificate> userCertificateList = getUserCertificateList(certParaMap);
	    
	    String contentHead = getExportName();
	    
	    IViewBuilder certificateViewBuilder = new UserCertificateViewBuilderImpl(contentHead);
	    
	    try {
	       certificateViewBuilder.viewExport(userCertificateList, request, response);
	    }catch(Exception ex) {
	       log.error("证书视图导出异常",ex);
	       throw new BizException("证书视图导出异常,原因[" + ex.getMessage() + "]");
	    }
	}
	
	/**
	 * 
	  * <p>构造导出的文件名称</p>
	  * @param certParaMap
	  * @return  
	  * @version: 2011-5-19 下午06:45:41
	  * @See:
	 */
	private String getExportName() {
		return new StringBuilder().append(CommonHelper.getCommonDateFormatStr()).append("_").append(VIEW).toString();
	}

	// --------------------------------------------处理跟证书关联的机构用户等-------------------------------------------------
	/**
	 * <p>得到分支机构</p>
	 */
	public List<BranchInfo> getBranchInfo(String branchCode) throws BizException {
		try {
			return Arrays.asList(new BranchInfo[] { this.branchInfoDAO.findBranchInfo(branchCode) });
		} catch (Exception ex) {
			throw new BizException("查询分支机构[" + branchCode + "],原因[" + ex.getMessage() + "]");
		}
	}
		
	
	/**
	 * <p>
	 *   <li>得到用户下拉框列表</li>
	 *   <li>注意当前UserInfo表的设计:若是发卡机构用户则对应机构在branchNo，如果是商户机构用户则机构对应merchantNo</li>
	 * </p>  
	 */
	public String getCertificateUserListOption(Map params, String userId) throws BizException {
		List<UserInfo> userInfoList = getCertificateUserList(params,userId);
		
		if(CommonHelper.checkIsEmpty(userInfoList)) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder(MAX_BUFFER);

		for (UserInfo userInfo : userInfoList) {		
			
		    StringBuilder nameBuilder = new StringBuilder(userInfo.getUserId()).append(VALUE_SPLIT).append(userInfo.getUserName());
		    
		    StringBuilder valueBuilder = nameBuilder.append(VALUE_SPLIT).append(getBranchOrMerchantNo(userInfo));
		    
			sb.append("<option value=\"").append(valueBuilder).append("\">")
			  .append(nameBuilder)
			  .append("</option>");
		}
		
		return sb.toString();
	}
	
	/**
	 * 
	  * <p>同上引入比较器></p>
	  * @param params
	  * @param userId
	  * @param compareFlag
	  * @return
	  * @throws BizException  
	  * @version: 2011-6-3 下午02:04:55
	  * @See:
	 */
	public String getCertificateUserListOption(Map params, String userId,int compareFlag) throws BizException  {
        List<UserInfo> userInfoList = getCertificateUserList(params,userId);
		
		if(CommonHelper.checkIsEmpty(userInfoList)) {
			return "";
		}
		
		Collections.sort(userInfoList, new UserInfoComparator(compareFlag));
		
		StringBuilder sb = new StringBuilder(MAX_BUFFER);

		for (UserInfo userInfo : userInfoList) {		
			
		    StringBuilder nameBuilder = new StringBuilder(userInfo.getUserId()).append(VALUE_SPLIT).append(userInfo.getUserName());
		    
		    StringBuilder valueBuilder = nameBuilder.append(VALUE_SPLIT).append(getBranchOrMerchantNo(userInfo));
		    
			sb.append("<option value=\"").append(valueBuilder).append("\">")
			  .append(nameBuilder)
			  .append("</option>");
		}
		
		return sb.toString();
	}
	
	
	private String getBranchOrMerchantNo(UserInfo userInfo) {
		String branchNo = userInfo.getBranchNo();
		String merchantNo = userInfo.getMerchantNo();
		
		return CommonHelper.isEmpty(branchNo) ?  merchantNo : branchNo;
	}
	
	private List<UserInfo> getCertificateUserList(Map params, String userId) throws BizException {
		try {
			List<UserInfo> userInfoList = this.userInfoDAO.findCertficateUser(params);
			
			return filterUserInfoList(userInfoList,userId);
		} catch (Exception ex) {
			log.error("查询机构关联用户失败", ex);
			throw new BizException("查询机构关联用户失败,原因[" + ex.getMessage() + "]");
		}
	}
	
	
	private List<UserInfo> getCertificateUserList(Map params, String userId,int compareFlag) throws BizException {
		try {
			List<UserInfo> userInfoList = this.userInfoDAO.findCertficateUser(params);
			
			return filterUserInfoList(userInfoList,userId,compareFlag);
		} catch (Exception ex) {
			log.error("查询机构关联用户失败", ex);
			throw new BizException("查询机构关联用户失败,原因[" + ex.getMessage() + "]");
		}
	}
	
	private List<UserInfo> filterUserInfoList(List<UserInfo> userInfoList,String userId) throws Exception{
		if(CommonHelper.checkIsNotEmpty(userId)) {			
			for(Iterator<UserInfo> iter = userInfoList.iterator(); iter.hasNext();) {
				UserInfo userInfo = iter.next();
				if(userInfo.getUserId().equals(userId)) {
					iter.remove();
				}
			}
		}		
						
	    return userInfoList;	
	}
	
	private List<UserInfo> filterUserInfoList(List<UserInfo> userInfoList,String userId,int compareFlag) throws Exception{
		if(CommonHelper.checkIsNotEmpty(userId)) {			
			for(Iterator<UserInfo> iter = userInfoList.iterator(); iter.hasNext();) {
				UserInfo userInfo = iter.next();
				if(userInfo.getUserId().equals(userId)) {
					iter.remove();
				}
			}
		}
		
		Collections.sort(userInfoList, new UserInfoComparator(compareFlag));
						
	    return userInfoList;	
	}
	
	//-------------------------------------------------证书上传与指派功能进行合并----------------------------------------------------------------
    public boolean processUserCertificateUploadAndAssign(File uploadFile, String uploadName,BranchInfo branchInfo, UserInfo loginUser) 
        throws BizException {
    	return this.processUserCertificateUpload(uploadFile, uploadName, branchInfo.getBranchCode(), getAssignUserInfo(loginUser));
    	
    }
    
    private UserInfo getAssignUserInfo(UserInfo loginUser) {
    	loginUser.setState(UserCertificateState.ASSIGNED.getValue());
    	return loginUser;
    }
    
    //-------------------------------------------------证书上传与绑定功能进行合并(用于证书更新)----------------------------------------------------------------
    public boolean processUserCertificateUploadAndBound(Map<UserCertificate, File>	branchCertificateMap, UserInfo loginUser, UserCertificate boundedUserCertificate) 
        throws BizException {
    	return this.processUserCertificateUpload(branchCertificateMap,loginUser,boundedUserCertificate);
    }
    
    //-----------------------------------------------------内部包装类(处理检查以及状态对象的包装)-----------------------------------------
	protected static interface IUserCertificateProcess {
		String defaultUseState = "";

		UserCertificate getAssignUserCertificate(UserCertificate unBoundUserCertificate, BranchInfo branchInfo, UserInfo loginUser);

		UserCertificate getRemoveUserCertificate(UserCertificate unBoundUserCertificate, UserInfo loginUser);
		
		UserCertificate getRecycleUserCertificate(UserCertificate unBoundUserCertificate, UserInfo loginUser);

		UserCertificate getBoundUserCertificate(UserInfo assignUser, UserCertificate assignUserCertificate, UserInfo loginUser);

		UserCertificate getReBoundUserCertificate(UserCertificate unBoundUserCertificate, UserInfo loginUser);

		UserCertificate getUnBoundUserCertificate(UserCertificate boundUserCertificate, UserInfo loginUser);

		UserCertificate getBreakUserCertificate(UserCertificate boundUserCertificate, UserInfo loginUser);

		boolean checkUserCertificate(UserCertificate userCertificate, String useState);

	}

	protected static class UserCertificateProcessImpl implements IUserCertificateProcess {
		/**
		 * 
		 * <p>获得指派证书实体</p>
		 * @param unBoundUserCertificate
		 * @param branchInfo
		 * @param loginUser
		 * @return
		 * @version: 2011-4-26 下午06:13:58
		 * @See:
		 */
		public UserCertificate getAssignUserCertificate(UserCertificate unBoundUserCertificate, BranchInfo branchInfo,
				UserInfo loginUser) {
			UserCertificate assignUserCertificate = (UserCertificate) unBoundUserCertificate.clone();
			assignUserCertificate.setUseState(UserCertificateState.ASSIGNED.getValue());
			assignUserCertificate.setBranchCode(branchInfo.getBranchCode());
			assignUserCertificate.setUpdateUser(loginUser.getUserId());
			assignUserCertificate.setUpdateTime(CommonHelper.getCalendarDate());

			return assignUserCertificate;
		}

		/**
		 * 
		 * <p>获得回收证书实体(运营中心回收指派的证书)</p>
		 * @param unBoundUserCertificate
		 * @param loginUser
		 * @return
		 * @version: 2011-4-26 下午06:14:02
		 * @See:
		 */
		public UserCertificate getRecycleUserCertificate(UserCertificate assignUserCertificate, UserInfo loginUser) {
			UserCertificate recycleUserCertificate = (UserCertificate) assignUserCertificate.clone();
			recycleUserCertificate.setUseState(UserCertificateState.UNBOUND.getValue());
			recycleUserCertificate.setBranchCode(loginUser.getBranchNo());
			recycleUserCertificate.setUpdateUser(loginUser.getUserId());
			recycleUserCertificate.setUpdateTime(CommonHelper.getCalendarDate());

			return recycleUserCertificate;
		}

		/**
		 * 
		 * <p>运营中心删除没有指派(即非绑定的)的证书</p>
		 * @param unBoundUserCertificate
		 * @param loginUser
		 * @return
		 * @version: 2011-4-27 下午03:01:35
		 * @See:
		 */
		public UserCertificate getRemoveUserCertificate(UserCertificate unBoundUserCertificate, UserInfo loginUser) {
			UserCertificate removeUserCertificate = (UserCertificate) unBoundUserCertificate.clone();
			removeUserCertificate.setUpdateUser(loginUser.getUserId());
			removeUserCertificate.setUpdateTime(CommonHelper.getCalendarDate());

			return removeUserCertificate;
		}
		
		/**
		 * 
		 * <p>获得绑定证书实体(开通证书)</p>
		 * @param assignUser
		 * @param assignUserCertificate
		 * @param loginUser
		 * @return
		 * @version: 2011-4-26 下午06:14:06
		 * @See:
		 */
		public UserCertificate getBoundUserCertificate(UserInfo assignUser, UserCertificate assignUserCertificate,
				UserInfo loginUser) {
			UserCertificate boundUserCertificate = (UserCertificate) assignUserCertificate.clone();
			Date updateTime = CommonHelper.getCalendarDate();
			boundUserCertificate.setUserId(assignUser.getUserId());
			boundUserCertificate.setBndBranch(assignUser.getBranchNo());
			boundUserCertificate.setUseState(UserCertificateState.BOUND.getValue());
			boundUserCertificate.setUpdateTime(updateTime);
			boundUserCertificate.setUpdateUser(loginUser.getUserId());
			boundUserCertificate.setUpdate(CommonHelper.getDateFormatStr(updateTime, "yyyyMMdd"));

			return boundUserCertificate;
		}

		/**
		 * 
		 * <p>获得证书重新绑定实体</p>
		 * @param unBoundUserCertificate
		 * @param loginUser
		 * @return
		 * @version: 2011-4-26 下午06:14:12
		 * @See:
		 */
		public UserCertificate getReBoundUserCertificate(UserCertificate unBoundUserCertificate, UserInfo loginUser) {
			UserCertificate reBoundUserCertificate = (UserCertificate) unBoundUserCertificate.clone();
			Date updateTime = CommonHelper.getCalendarDate();
			reBoundUserCertificate.setUseState(UserCertificateState.BOUND.getValue());
			reBoundUserCertificate.setStartDate(unBoundUserCertificate.getStartDate());
			reBoundUserCertificate.setUpdateTime(updateTime);
			reBoundUserCertificate.setUpdateUser(loginUser.getUserId());
			reBoundUserCertificate.setUpdate(CommonHelper.getDateFormatStr(updateTime, "yyyyMMdd"));

			return reBoundUserCertificate;
		}

		/**
		 * 
		 * <p>获得证书解除绑定实体(回到指派状态)</p>
		 * @param boundUserCertificate
		 * @param loginUser
		 * @return
		 * @version: 2011-4-26 下午06:14:17
		 * @See:
		 */
		public UserCertificate getUnBoundUserCertificate(UserCertificate boundUserCertificate, UserInfo loginUser) {
			UserCertificate unBoundUserCertificate = (UserCertificate) boundUserCertificate.clone();
			unBoundUserCertificate.setUseState(UserCertificateState.ASSIGNED.getValue());
			unBoundUserCertificate.setBndBranch("");
			unBoundUserCertificate.setUserId("");			
			unBoundUserCertificate.setUpdateTime(CommonHelper.getCalendarDate());
			unBoundUserCertificate.setUpdateUser(loginUser.getUserId());

			return unBoundUserCertificate;
		}

		/**
		 * 
		 * <p>获得证书暂停绑定实体</p>
		 * @param boundUserCertificate
		 * @param loginUser
		 * @return
		 * @version: 2011-4-26 下午07:08:20
		 * @See:
		 */
		public UserCertificate getBreakUserCertificate(UserCertificate boundUserCertificate, UserInfo loginUser) {
			UserCertificate breakUserCertificate = (UserCertificate) boundUserCertificate.clone();
			breakUserCertificate.setUseState(UserCertificateState.BREAK.getValue());
			breakUserCertificate.setUpdateTime(CommonHelper.getCalendarDate());
			breakUserCertificate.setUpdateUser(loginUser.getUserId());

			return breakUserCertificate;
		}

		public boolean checkUserCertificate(UserCertificate userCertificate, String useState) {
			if (useState.equals(UserCertificateState.UNBOUND.getValue())) {
				return checkUserCertificateUnBound(userCertificate);
			} else if (useState.equals(UserCertificateState.ASSIGNED.getValue())) {
				return checkUserCertificateAssign(userCertificate);
			} else if (useState.equals(UserCertificateState.BOUND.getValue())) {
				return checkUserCertificateBound(userCertificate);
			} else if (useState.equals(UserCertificateState.BREAK.getValue())) {
				return checkUserCertificateBreak(userCertificate);
			} else if (useState.equals(defaultUseState)) {
				return checkUserCertificateExists(userCertificate);
			}

			return false;
		}

		/**
		 * 
		 * <p>检查证书实体存在</p>
		 * @param userCertificate
		 * @return
		 * @version: 2011-4-26 下午02:02:07
		 * @See:
		 */
		private boolean checkUserCertificateExists(UserCertificate userCertificate) {			
			return (userCertificate != null) && (userCertificate.getDnNo() != null) && (userCertificate.getSeqNo() != null)
			        && (userCertificate.getStartDate() != null) ;
		}

		/**
		 * 
		 * <p>检查证书非绑定状态</p>
		 * @param userCertificate
		 * @return
		 * @version: 2011-4-26 下午02:01:44
		 * @See:
		 */
		private boolean checkUserCertificateUnBound(UserCertificate userCertificate) {
			return (checkUserCertificateExists(userCertificate))
					&& (userCertificate.getUseState().equals(UserCertificateState.UNBOUND.getValue()));
		}

		/**
		 * 
		 * <p>检查证书指派状态</p>
		 * @param userCertificate
		 * @return
		 * @version: 2011-4-26 下午03:33:07
		 * @See:
		 */
		private boolean checkUserCertificateAssign(UserCertificate userCertificate) {
			return (checkUserCertificateExists(userCertificate))
					&& (userCertificate.getUseState().equals(UserCertificateState.ASSIGNED.getValue()));
		}

		/**
		 * 
		 * <p>检查证书绑定状态</p>
		 * @param userCertificate
		 * @return
		 * @version: 2011-4-26 下午04:47:59
		 * @See:
		 */
		private boolean checkUserCertificateBound(UserCertificate userCertificate) {
			return (checkUserCertificateExists(userCertificate))
					&& (userCertificate.getUseState().equals(UserCertificateState.BOUND.getValue()))
					&& (CommonHelper.checkIsNotEmpty(userCertificate.getUserId()));
		}

		/**
		 * 
		 * <p>检查证书暂停状态</p>
		 * @param userCertificate
		 * @return
		 * @version: 2011-4-26 下午07:49:57
		 * @See:
		 */
		private boolean checkUserCertificateBreak(UserCertificate userCertificate) {
			return (checkUserCertificateExists(userCertificate))
					&& (userCertificate.getUseState().equals(UserCertificateState.BREAK.getValue()))
					&& (CommonHelper.checkIsNotEmpty(userCertificate.getUserId()));
		}

	}
	// ----------------------------------------------------------------------------------------------------------------------------------------
	
	protected static class BranchInfoComparator implements Comparator<BranchInfo>, java.io.Serializable {					
		private static final long serialVersionUID = 1935269812333991787L;

		public int compare(BranchInfo o1, BranchInfo o2) {
			return o1.getBranchCode().compareTo(o2.getBranchCode());
		}
		
	}
	
	protected static class MerchInfoComparator implements Comparator<MerchInfo>, java.io.Serializable {			
		private static final long serialVersionUID = -3703941703330770811L;

		public int compare(MerchInfo o1, MerchInfo o2) {
			return o1.getMerchId().compareTo(o2.getMerchId());
		}		
	}
	
	protected static class UserInfoComparator implements Comparator<UserInfo>, java.io.Serializable {		
		private static final long serialVersionUID = 1859094970845832320L;
		
		private int compareFlag = 0;
		
		public UserInfoComparator(int compareFlag) {
		     this.compareFlag = compareFlag;	
		}

		/**
		 * <p>当机构(发卡或商户)相同时比较用户Id,否则则按照机构进行排序</p>
		 */
		public int compare(UserInfo o1, UserInfo o2) {
			
			if(compareFlag == BRANCH_COMPARE_FLAG) {
				 String o1_branchNo = o1.getBranchNo();
		         String o2_branchNo = o2.getBranchNo();
		         
		         if(CommonHelper.isEmpty(o1_branchNo) || CommonHelper.isEmpty(o2_branchNo)) {
		        	 return 0;
		         }
		         
		         if(CommonHelper.checkEquals(o1_branchNo, o2_branchNo)) {
		        	 return compareUserId(o1,o2);
		         }
		         
		         return o1_branchNo.compareTo(o2_branchNo);
				
			} else if (compareFlag == MERCH_COMPARE_FLAG) {
				String o1_merchNo = o1.getMerchantNo();
				String o2_merchNo = o2.getMerchantNo();
				
				if(CommonHelper.isEmpty(o1_merchNo) || CommonHelper.isEmpty(o2_merchNo)) {
					return 0;
				}
				
				if(CommonHelper.checkEquals(o1_merchNo, o2_merchNo)) {
					return compareUserId(o1,o2);
				}
				
				return o1_merchNo.compareTo(o2_merchNo);
			}
			
            return 0;
		}
		
		private int compareUserId(UserInfo o1, UserInfo o2) {
		   return o1.getUserId().compareTo(o2.getUserId());	
		}
		
	}
	
	
}
