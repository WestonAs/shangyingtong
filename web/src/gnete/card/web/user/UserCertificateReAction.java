package gnete.card.web.user;

import flink.etc.MatchMode;
import flink.util.CommonHelper;
import flink.util.Paginater;
import gnete.card.dao.UserCertificateRevDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.UserCertificate;
import gnete.card.entity.UserCertificateKey;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.UserCertificateState;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.UserCertificateRevService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


/**
  * <p>用户数字证书功能ACTION</p>
  * <ul>
  *    <li>系统支持压缩文件和单个文件上传</li>    
  *    <li>运营中心负责证书录入以及未绑定证书的删除(回收)</li>
  *    <li>运营中心(包括自身)可就上述录入的证书进行指派(即证书所属机构的分配与回收),当前指派的机构包括分支机构和运营代理机构</li>
  *    <li>上述所属机构可对所辖机构涉及用户(包括机构用户和商户用户--来自用户表)进行证书(来自上述指派证书)的绑定、解除绑定、变更绑定(在所辖机构范围内)</li>
  *    <li>所属机构即为所辖机构、查询中需区分该机构</li>
  *    <li>证书一旦被绑定以后直到被解除绑定后才可重新绑定或变更绑定</li>
  *    <li>证书绑定(或变更)成功后将同步到缓存中,相反则从缓存中移除</li>    
  *    <li>为防止页面未经过LIST打开页面将控制按钮的触发</li>   
  * </ul>  
  * @Project: CardSecond
  * @File: UserCertificateReAction.java
  * @See:
  * @author: aps-zbw
  * @modified:
  *    <li>增加自动加载完成支持,注意前台获得的将是返回结构的JSON结构</li> 
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-4-12
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
public class UserCertificateReAction extends BaseAction{	
		
	private static final long serialVersionUID = 4690885878154353913L;
	
	@Autowired
	private UserCertificateRevService userCertificateRevService;
	@Autowired
	private UserCertificateRevDAO userCertificateRevDAO;
	
	
	// -----------------------------------------显示查询-----------------------------------------------------------
	private List<BranchInfo> branchs=new ArrayList<BranchInfo>();    // 发卡机构
	private List<MerchInfo> merchs=new ArrayList<MerchInfo>();    //  商户机构
	private List<UserCertificateState> userCertStateList ; // 用户证书状态 
	private Paginater userCertificatePage; // 用户数字证书分页
	
	private boolean showBranch = false; // 控制显示机构
	private boolean showMerch = false;  // 控制显示商户	
    private boolean showAdd = false;  //控制录入
	private boolean showAssign = false; //控制指派
	private boolean showExport = false; //控制导出
	
	// 注意upload + uploadContentType + uploadFileName的组合声明
	private File upload;                 //上传文件    
	private String uploadContentType;   // 文件内容类型
	private String uploadFileName;      //上传文件名
	
	private UserCertificate userCertificate; // 用户数字证书
	private UserInfo userInfo; // 关联选择用户
	private List<UserInfo> userInfoList; // 跟机构(或商户)绑定的用户信息列表
	private String startDate; // 查询开始日期
	private String endDate; // 查询结束日期
	private String useState; // 证书绑定状态	
	private String seqNo;    // 证书序列号
	private String[] ids;    //复选框选择的数字证书(注意前台提交的是拼装成","分隔字符串)
	private BranchInfo branchInfo;    //选择指派的机构
	
    private String branchCode;    //绑定用户选择的机构号(对应AJAX的参数)
    private String branchName;
    private String merchNo;       //绑定用户选择的商户号
    private String userId;        //数字证书绑定的用户ID
    
    private String branchType;    //用于查询机构用户或商户用户的区分(自动完成)
    
    private final int USER_SPLIT_LENGTH = 3;
    
    
    @Override
	public String execute() throws Exception  {
		String loginRoleType = getLoginRoleTypeCode();
		
		this.userCertStateList = this.userCertificateRevService.getUserCertStateList(loginRoleType);
		initShowControl();
		
		String loginBranchCode = getLoginBranchCode();
		Map certParaMap = getMyUserCertSearchMap(loginBranchCode);
		this.userCertificatePage = getMyUserCertificatePage(certParaMap, loginBranchCode, loginRoleType);
		
		return LIST;
	}
	
	//1------------------------------------------------------证书查询控制------------------------------------------------------------
	
	/**
	 * 
	  * <p>
	  * <li>如果用户所在机构为运营中心则缺省查询当前证书的所有情况</li>
	  * <li>如果用户(机构用户)为分支机构、代理机构则由"所属机构"来查询证书情况,
	  *     否则由"绑定机构"查看证书情况</li>
	  * <li>如果用户(商户用户)则由"绑定商户"查看证书情况</li>
	  * <li>选择某机构或商户及其他则从查询该机构或该商户下的证书情况</li>
	  * </p>     
	  * @return
	  * @throws Exception  
	  * @version: 2011-4-12 下午03:41:25
	  * @See:
	 */
	private Map getMyUserCertSearchMap(String loginBranchCode) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(this.userInfo == null) {
			setUserCertMap(params,loginBranchCode);			
		} else {
			String branchCode = this.userInfo.getBranchNo();
			String merchNo = this.userInfo.getMerchantNo();
			
			boolean isBranchEmpty = CommonHelper.checkIsEmpty(branchCode);
			boolean isMerchEmpty = CommonHelper.checkIsEmpty(merchNo);
			
			if(isBranchEmpty || isMerchEmpty) {
				setUserCertMap(params,loginBranchCode);	
			} else {				
				if(!isBranchEmpty) {
					setBranchUserCertMap(params,loginBranchCode,branchCode);
				}
				
				if(!isMerchEmpty) {
					setMerchUserCertMap(params,merchNo);
				}
			}
		
			params.put("userId", userCertificate.getUserId());
		}
		
		if (this.userCertificate != null) {
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(this.userCertificate.getBranchName()));
			params.put("bndBranchName", MatchMode.ANYWHERE.toMatchString(this.userCertificate.getBndBranchName()));
			params.put("fileName", MatchMode.ANYWHERE.toMatchString(this.userCertificate.getFileName()));
			params.put("userId", this.userCertificate.getUserId());
		}
		
		if (CommonHelper.isNotEmpty(this.startDate)) {
			params.put("startDate", startDate);
		}

		if (CommonHelper.isNotEmpty(this.endDate)) {
			params.put("endDate", endDate);
		}

		if (CommonHelper.isNotEmpty(this.useState)) {
			params.put("useState", useState);
		}
		
		if(CommonHelper.isNotEmpty(this.seqNo)) {
			params.put("seqNo", seqNo);
		}		
		
		return params;
	}
	
	/**
	 * 
	  * <p>如果所属机构及所属商户选择为空</p>
	  * @param params
	  * @param loginBranchCode  
	  * @version: 2011-4-12 下午05:21:26
	  * @See:
	 */
	private void setUserCertMap(Map<String,Object> params,String loginBranchCode) {
		if (!this.showAssign)  {
			//1.2.1 判断是否是分支机构及代理机构(查所属机构)
			if(this.showBranch && this.showMerch) {
				params.put("branchCode", loginBranchCode);
			} else {
				//1.2.2 判读是否是发卡机构(查绑定机构)
				if(this.showBranch) {
					params.put("bndBranch", loginBranchCode);
				} 
				//1.2.3 判断是否是商户(查绑定商户)
				else if (this.showMerch) {
//					if(CommonHelper.checkIsNotEmpty(this.merchs)) {
//						params.put("bndBranch", this.merchs.get(0).getMerchId());
//					}
					params.put("bndBranch", loginBranchCode);
				}				
			}
		}
	}
	
	/**
	 * 
	  * <p>如果选择的机构不为空</p>
	  * @param params
	  * @param loginBranchCode
	  * @param branchCode  
	  * @version: 2011-4-12 下午05:32:25
	  * @See:
	 */
	private void setBranchUserCertMap(Map<String,Object> params, String loginBranchCode,String branchCode) {
		 if(!this.showAssign) {
			     //1.3.1  判断是否是分支机构及代理机构
		    	 if(this.showBranch && this.showMerch) {
		    		  //1.3.2 判断是否为所属机构
		    		   if(branchCode.equals(loginBranchCode))  {
		    			   params.put("branchCode", loginBranchCode);
		    		   }
		    	 }  else {
		    		 //1.3.3判断选择的为绑定机构
		    		 if(this.showBranch)  {
		    			 params.put("bndBranch", branchCode);
		    		 }
		    	 }
		  }
	}
	
	/**
	 * 
	  * <p>如果选择的商户不为空</p>
	  * @param params
	  * @param merchNo  
	  * @version: 2011-4-12 下午05:32:44
	  * @See:
	 */
	private void setMerchUserCertMap(Map<String,Object> params, String merchNo) {
		 if(!this.showAssign) {
			 if (this.showMerch) {
				 params.put("bndBranch",merchNo);
			 }
		 }
	}
	
	 /**
     * 
      * <p>跳转到/userCert/detail.jsp</p>
      * @return
      * @throws Exception  
      * @version: 2011-4-28 上午08:53:52
      * @See:
     */
    public String detail() throws Exception {
    	this.userCertificate = this.userCertificateRevService.getSelectUserCertificate(this.userCertificate);
    	return DETAIL;
    }
    
	
	//------------------------------------------------------数字证书录入(合并指派处理)------------------------------------------------------------	
    /**
     * 
      * <p>
      * <li>运营中心管理员证书录入界面引导</li>
      * <li>跳转到/userCert/add.jsp</li>
      * </p>   
      * @return 关于指派机构改由自动获取方式
      * @modified:<p>
      * @throws Exception  
      * @version: 2011-4-12 下午05:35:55
      * @See:
     */
	public String showAdd() throws Exception {
		initShowControl();
		
		this.userInfo = getSessionUser();
		
		//this.branchs = this.userCertificateRevService.getAssignBranchInfoList();
		  
	    return ADD;	
	}
	
	
	/**
	 * 
	  * <p>运营中心管理员证书录入界面录入</p>
	  * @return
	  * @throws Exception  
	  * @version: 2011-4-12 下午05:58:06
	  * @See:
	 */
	public String add() throws Exception {
		checkUpload();
		
		UserInfo loginUser = getSessionUser();
		
		String uploadFileName = getUploadFileName();
		
		BranchInfo branchInfo = getBranchInfo();
		
		boolean uploadAndAssignFlag = 
			this.userCertificateRevService.processUserCertificateUploadAndAssign(getUpload(), uploadFileName, branchInfo, loginUser);
		
		if (uploadAndAssignFlag) {
			String msg = "用户[" + loginUser.getUserId() + "]为机构[" + branchInfo.getBranchCode() + "]指派机构数字证书[" + uploadFileName + "]成功！";			
			this.addActionMessage(getReturnSuccessURL(),msg);
			this.log(msg, UserLogType.ADD);
		}
		
		return SUCCESS;
	}

    /**
     * 
      * <p>检查数字证书上传是否合法</p>
      * @return  
      * @version: 2011-4-12 下午06:08:18
      * @See:
     */
    private void checkUpload() throws BizException{
    	if(this.branchInfo == null || CommonHelper.checkIsEmpty(this.branchInfo.getBranchCode())) {
    		throw new BizException("证书指派机构不能为空！");
    	}
    	
    	if(this.upload == null) {
    		throw new BizException("证书文件不允许为空!");
    	}
    }
    
	/**
	 * 显示 证书更新 页面
	 */
	public String showUpdate() throws Exception {
		Assert.notNull(userCertificate.getDnNo(), "证书对象DN不能为空");
		Assert.notNull(userCertificate.getSeqNo(), "证书对象序列号不能为空");
		Assert.notNull(userCertificate.getStartDate(), "证书对象开始日期不能为空");
		Assert.notNull(userCertificate.getUserId(), "证书对象绑定用户不能为空");

		initShowControl();

		UserCertificate tmpUserCertificate = (UserCertificate) this.userCertificateRevDAO
				.findByPk(new UserCertificateKey(userCertificate.getDnNo(), userCertificate.getSeqNo(),
						userCertificate.getStartDate(), userCertificate.getUserId()));
		if (null == tmpUserCertificate) {
			String msg = String.format("待更新证书信息不存在:[%s],[%s],[%s],[%s]", userCertificate.getDnNo(),
					userCertificate.getSeqNo(), userCertificate.getStartDate(), userCertificate.getUserId());
			throw new BizException(msg);
		} else {
			this.userCertificate = tmpUserCertificate;
		}

		this.userInfo = getSessionUser();

		return "update";
	}

	/**
	 * 证书更新
	 */
	public String update() throws Exception {
		UserCertificateKey key = new UserCertificateKey(userCertificate.getDnNo(),
				userCertificate.getSeqNo(), userCertificate.getStartDate(), userCertificate.getUserId());
		UserCertificate oldUserCertificate = (UserCertificate) this.userCertificateRevDAO.findByPk(key);

		boolean flag = this.userCertificateRevService.updateCertFile(upload, uploadFileName,
				oldUserCertificate, getSessionUser());
		if (flag) {
			String msg = String.format("为所属机构[%s]绑定机构[%s]用户[%s]更新机构数字证书成功！", oldUserCertificate
					.getBranchCode(), oldUserCertificate.getBndBranch(), oldUserCertificate.getUserId());
			this.addActionMessage(getReturnSuccessURL(), msg);
			this.log(msg, UserLogType.ADD);
		}
		return SUCCESS;
	}
 
    // --------------------------------------------------数字证书指派----------------------------------------------
    /**
     *    
      * <p>
      * <li>显示待指派的界面(整体指派)</li>
      * <li>显示可指派的机构以及未绑定的证书</li>
      * </p>   
      * @return
      * @throws Exception  
      * @version: 2011-4-28 上午09:00:27
      * @See:
     */
    public String showAssign() throws Exception {
        String loginRoleType = getLoginRoleTypeCode();
    	String loginBranchCode = getLoginBranchCode();
    	
    	Map paraMap = getAssignSearchMap(loginBranchCode);
    	
    	initAssignControl(loginRoleType);
    	
    	this.userCertificatePage = getMyUserCertificatePage(paraMap,loginBranchCode,loginRoleType);
    	
    	return "assign";
    }
    
    private Map getAssignSearchMap(String loginBranchCode) {
    	Map<String,String> paraMap = new HashMap<String,String>();
    	paraMap.put("branchCode", loginBranchCode);
    	paraMap.put("useState", UserCertificateState.UNBOUND.getValue());
    	return paraMap;
    }
    
    /**
     * 
      * <p>
      * <li>显示待指派的界面(单个按钮)</li>
      * <li>这是对于表格按钮中的控制</li>
      * </p>    
      * @return
      * @throws Exception  
      * @version: 2011-4-28 上午10:44:53
      * @See:
     */
    public String showSingleAssign() throws Exception {
    	initAssignControl(getLoginRoleTypeCode());
    		
    	this.userCertificatePage = this.userCertificateRevService.getUserCertificateList(this.userCertificate, UserCertificateState.UNBOUND.getValue());
    		
    	return "assign";
    }
    
   /**
     * 
      * <p>触发指派的处理(后台将检查ids是否合法)</p>
      * @return
      * @throws Exception  
      * @version: 2011-4-28 上午09:01:38
      * @See:
     */
    public String assign() throws Exception {    	
    	UserInfo loginUser = getSessionUser();
    	BranchInfo branchInfo = getBranchInfo();
    	String[] ids = getIds();
    	
    	boolean flag = this.userCertificateRevService.processUserCertificatAssign(ids, branchInfo, loginUser);
    	
    	if(flag) {    		
    		String msg = "操作用户[" + loginUser.getUserId() + "]针对机构[" + branchInfo.getBranchCode() + "]指派证书"
    		                         + CommonHelper.filterArray2Str(ids) + "成功！";
			this.addActionMessage(getReturnSuccessURL(), msg);
			this.log(msg, UserLogType.ADD);
    	}
    	
    	return SUCCESS;
    }
    
    
    private void initAssignControl(String loginRoleType) throws Exception{
    	this.branchs = this.userCertificateRevService.getAssignBranchInfoList();
    	
    	initShowControl();
    }
    
    // ------------------------------------------------数字证书绑定控制----------------------------------------------------------
    /**
     * 
      * <p>显示该机构可管辖的机构或商户</p>
      * @return
      * @throws Exception  
      * @version: 2011-4-28 上午11:35:12
      * @See:
     */
    public String showBound() throws Exception {    
    	initShowControl();
        return "bound";
    }    
    
    public String bound() throws Exception {
    	
    	UserInfo assignUser = getAssignUser();
    	UserInfo loginUser = getSessionUser();    	
    	boolean boundFlag = this.userCertificateRevService.processUserCertificateBound(assignUser, getUserCertificate(), loginUser);
    	
    	if(boundFlag) {
    		String msg = "操作用户[" + loginUser.getUserId() + "]将数字证书[" + getUserCertificate().getSeqNo() +"]绑定给用户[" 
    		                         + assignUser.getUserId() + "]成功!";			
			this.addActionMessage(getReturnSuccessURL(), msg);
			this.log(msg, UserLogType.ADD);
    	}
    	
    	return SUCCESS;
    }       
    
    
    /**
     * 
      * <p>获得绑定的用户</p>
      * @return  
      * @version: 2011-4-28 下午03:16:00
      * @See:
     */
    private UserInfo getAssignUser() throws Exception{
    	String userValue = this.userInfo.getUserId();
    	
    	if(CommonHelper.checkIsEmpty(userValue)) {
    		throw new BizException("绑定用户信息不能为空!");
    	}
    	
    	String[] array =  CommonHelper.getMsgLineArrayInSplit(userValue, UserCertificateRevService.VALUE_SPLIT);
    	
    	if(array.length != USER_SPLIT_LENGTH) {
    	   throw new BizException("绑定用户参数[" + userValue + "]存在问题!");	
    	}
    	
    	this.userInfo.setUserId(array[0]);      	
    	this.userInfo.setUserName(array[1]); 
    	this.userInfo.setBranchNo(array[2]);
    	
    	return this.userInfo;
    }
    
    // ------------------------------------------------数字证书移除控制----------------------------------------------------------------
    /**
     * 
      * <p>移除未绑定的用户数字证书</p>
      * @return
      * @throws Exception  
      * @version: 2011-4-28 上午11:37:41
      * @See:
     */
	public String remove() throws Exception {
		userCertificate = (UserCertificate) this.userCertificateRevDAO.findByPk(new UserCertificateKey(
				userCertificate.getDnNo(), userCertificate.getSeqNo(), userCertificate.getStartDate()));

		boolean removeFlag = this.userCertificateRevService.processUserCertificateRemove(userCertificate,
				getSessionUser());

		if (removeFlag) {
			String msg = "操作用户[" + getSessionUserCode() + "]针对证书[" + userCertificate.getSeqNo() + "]移除成功！";
			this.addActionMessage(getReturnSuccessURL(), msg);
			this.log(msg, UserLogType.ADD);
		}
		return SUCCESS;
	}
    
    // ------------------------------------------------数字证书回收控制----------------------------------------------------------------
    /**
     * 
      * <p>回收指派的数字证书(用于重新指派)</p>
      * @return
      * @throws Exception  
      * @version: 2011-4-28 上午11:43:25
      * @See:
     */
    public String recycle() throws Exception {
    	UserCertificate certificate = getUserCertificate();
    	UserInfo userInfo = getSessionUser();
    	
    	boolean recycleFlag = this.userCertificateRevService.processUserCertificateRecycle(certificate, userInfo);
    	
    	if(recycleFlag) {
    		String msg = "操作用户[" + userInfo.getUserId() + "]针对证书[" + certificate.getSeqNo() + "]回收成功(即可重新指派)！";
			this.addActionMessage(getReturnSuccessURL(), msg);
			this.log(msg, UserLogType.ADD);
    	}
    	
    	return SUCCESS;
    }
    
    // ------------------------------------------------数字证书解除绑定----------------------------------------------------------------
    /**
     * 
      * <p>解除已经绑定的用户数字证书</p>
      * @return
      * @throws Exception  
      * @version: 2011-4-28 上午11:44:07
      * @See:
     */
    public String unBound() throws Exception {
    	this.checkEffectiveCertUser();
    	
    	UserCertificate certificate = getUserCertificate();
    	UserInfo userInfo = getSessionUser();
    	
    	boolean unboundFlag = this.userCertificateRevService.processUserCertificateUnBound(certificate, userInfo);
    	
    	if(unboundFlag) {
    		String msg = "操作用户[" + userInfo.getUserId() + "]针对证书[" + certificate.getSeqNo() + "]解除绑定成功(该证书需重新指派)！";
			this.addActionMessage(getReturnSuccessURL(), msg);
			this.log(msg, UserLogType.ADD);
    	}
    	
    	return SUCCESS;
    }
    
    // ------------------------------------------------数字证书暂停绑定----------------------------------------------------------------
    /**
     * <p>暂停已经绑定的用户数字证书</p>
      * @return
      * @throws Exception  
      * @version: 2011-4-28 上午11:44:24
      * @See:
     */
    public String terminate() throws Exception {
    	this.checkEffectiveCertUser();
    	
    	UserCertificate certificate = getUserCertificate();
    	UserInfo userInfo = getSessionUser();
    	
    	boolean terminateFlag = this.userCertificateRevService.processUserCertificateBreak(certificate, userInfo);
    	
    	if(terminateFlag) {
    		String msg = "操作用户[" + userInfo.getUserId() + "]针对证书[" + certificate.getSeqNo() + "]暂停使用成功(可重新使用或被变更绑定)！";
			this.addActionMessage(getReturnSuccessURL(), msg);
			this.log(msg, UserLogType.ADD);
    	}
    	
    	return SUCCESS;
    }
    
   
    // ------------------------------------------------数字证书重新绑定(变更绑定)----------------------------------------------------------------
    /**
     * 
      * <p>重新绑定的已暂停的用户数字证书</p>
      * @return
      * @throws Exception  
      * @version: 2011-4-28 上午11:57:48
      * @See:
     */
    public String reBound() throws Exception {
    	UserCertificate certificate = getUserCertificate();
    	UserInfo userInfo = getSessionUser();
    	
    	boolean reboundFlag = this.userCertificateRevService.processUserCertificateReBound(certificate, userInfo);
    	
    	if(reboundFlag) {
    		String msg = "操作用户[" + userInfo.getUserId() + "]针对证书[" + certificate.getSeqNo() + "]重新使用成功！";
			this.addActionMessage(getReturnSuccessURL(), msg);
			this.log(msg, UserLogType.ADD);
    	}
    	
    	return SUCCESS;
    }
    
    public String showChgBound() throws Exception {    	    	
        //String branchCode = this.userCertificate.getBranchCode();    	
    	
    	//initBoundCtrl(branchCode,super.getSessionUser(),getLoginRoleTypeCode());
    	
    	initShowControl();
  		  
        return "chgBound";
    }
    
    /**
     *  <p>将已经暂停的数字证书变更为其他用户</p>
      * @return
      * @throws Exception  
      * @version: 2011-7-13 下午07:27:51
      * @See:
     */
    public String chgBound() throws Exception {
    	UserInfo assignUser = getAssignUser();
    	UserInfo loginUser = getSessionUser();
    	
    	boolean chgBoundFlag = this.userCertificateRevService.processUserCertificateChgBound(assignUser, getUserCertificate(), loginUser);
    	
    	if(chgBoundFlag) {
    		String msg = "操作用户[" + loginUser.getUserId() + "]将原数字证书用户[" + getUserCertificate().getUserId() + "]变更成用户["
    		                         + assignUser.getUserId() +"]成功!";			
			this.addActionMessage(getReturnSuccessURL(), msg);
			this.log(msg, UserLogType.ADD);
    	}
    	
    	return SUCCESS;    	
    }
    
    //-----------------------------------------------------用户证书查询结果导出-----------------------------------------------------------------
    public String export() throws Exception {
        String loginBranchCode = getLoginBranchCode();
		  
		Map certParaMap = getMyUserCertSearchMap(loginBranchCode);
		
		this.userCertificateRevService.processUserCertificateExport(certParaMap, request, response)	;			
    	
    	return null;
    }
    
    //------------------------------------------------------加载用户列表(AJAX)-------------------------------------------------------------------
    /**
     * 
      * <p>
      *    <li>根据选择的机构或商户编号来查找其所辖用户</li>
      *    <li>生成AJAX的片段输出流</li>
      * </p>   
      * @return
      * @throws Exception  
      * @version: 2011-4-28 下午02:17:51
      * @See:
     */
    public void loadUserInfoList() throws Exception  {
    	String userInfoOption = getMyUserInfoOption(getBranchCode(),getMerchNo(),getUserId());
    	
    	this.respond(userInfoOption);
    }
    
    /**
     * 
      * <p>
      *     <li>参考UserAction中的参数组合办法</li>
      *     <li>需有分支机构(branchs)以及商户机构(merchs)(目的确定用户)</li>
      * </p>    
      * @param branchCode
      * @param merchNo
      * @param userId
      * @return
      * @throws Exception  
      * @version: 2011-4-28 下午02:51:53
      * @See:
     */
    private String getMyUserInfoOption(String branchCode, String merchNo,String userId) throws Exception {
    	Map<String,Object> params = new HashMap<String,Object>(); 
    	
    	params.put("branchNo", branchCode);
    	
    	params.put("merchantNo", merchNo);

		if (!(isCenterRoleLogined() || isFenzhiRoleLogined())) {
			if (this.showBranch) {
				if (CommonHelper.checkIsNotEmpty(this.branchs)) {
					params.put("branchNo", branchs.get(0).getBranchCode());
				}
			} else if (this.showMerch) {
				if (CommonHelper.checkIsNotEmpty(this.merchs)) {
					params.put("merchantNo", merchs.get(0).getMerchId());
				}
			}
		}
		
		if (isFenzhiRoleLogined()) {
			params.put("branchs", branchs);
			params.put("merchs", merchs);
			if(CommonHelper.checkIsNotEmpty(branchs) ||  CommonHelper.checkIsNotEmpty(this.merchs)) {
				params.put("union", true);
			}
			
			if(CommonHelper.checkIsNotEmpty(branchs) && CommonHelper.checkIsNotEmpty(this.merchs)) {
				params.put("union_or", true);
			}
			
		}
    	
    	return this.userCertificateRevService.getCertificateUserListOption(params, userId,getCompareFlag(branchCode,merchNo));
    }
    
    private int getCompareFlag(String branchCode, String merchNo) {
    	if(CommonHelper.isEmpty(branchCode)) {
    		return (CommonHelper.isNotEmpty(merchNo)) ? UserCertificateRevService.MERCH_COMPARE_FLAG : 0;
    	}
    	
    	return CommonHelper.isNotEmpty(merchNo) ? 0 : UserCertificateRevService.BRANCH_COMPARE_FLAG;
    }
    
    
    //-------------------------------------------------------涉及实体变量GET SET------------------------------------------------------------- 
    public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public List<BranchInfo> getBranchs() {
		return branchs;
	}

	public void setBranchs(List<BranchInfo> branchs) {
		this.branchs = branchs;
	}

	public List<MerchInfo> getMerchs() {
		return merchs;
	}

	public void setMerchs(List<MerchInfo> merchs) {
		this.merchs = merchs;
	}

	public List<UserCertificateState> getUserCertStateList() {
		return userCertStateList;
	}

	public void setUserCertStateList(List<UserCertificateState> userCertStateList) {
		this.userCertStateList = userCertStateList;
	}

	public Paginater getUserCertificatePage() {
		return userCertificatePage;
	}

	public void setUserCertificatePage(Paginater userCertificatePage) {
		this.userCertificatePage = userCertificatePage;
	}

	public boolean isShowBranch() {
		return showBranch;
	}

	public void setShowBranch(boolean showBranch) {
		this.showBranch = showBranch;
	}

	public boolean isShowMerch() {
		return showMerch;
	}

	public void setShowMerch(boolean showMerch) {
		this.showMerch = showMerch;
	}

	public boolean isShowAdd() {
		return showAdd;
	}

	public void setShowAdd(boolean showAdd) {
		this.showAdd = showAdd;
	}

	public boolean isShowAssign() {
		return showAssign;
	}

	public void setShowAssign(boolean showAssign) {
		this.showAssign = showAssign;
	}
	
	public boolean isShowExport() {
		return showExport;
	}

	public void setShowExport(boolean showExport) {
		this.showExport = showExport;
	}

	public UserCertificate getUserCertificate() {
		return userCertificate;
	}

	public void setUserCertificate(UserCertificate userCertificate) {
		this.userCertificate = userCertificate;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<UserInfo> getUserInfoList() {
		return userInfoList;
	}

	public void setUserInfoList(List<UserInfo> userInfoList) {
		this.userInfoList = userInfoList;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUseState() {
		return useState;
	}

	public void setUseState(String useState) {
		this.useState = useState;
	}
	
	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}
	
	public BranchInfo getBranchInfo() {
		return branchInfo;
	}

	public void setBranchInfo(BranchInfo branchInfo) {
		this.branchInfo = branchInfo;
	}
	
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getMerchNo() {
		return merchNo;
	}

	public void setMerchNo(String merchNo) {
		this.merchNo = merchNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}
	
	//------------------------------------------------------------增加自动完成支持------------------------------------------------------------
	/**
	 * 
	  * <p>
	  *    <li>增加指派机构的自动完成</li>
	  *    <li>显示结果需格式化成branchCode+"|" + branchName 实际值为branchCode
	  * </p>    
	  * @return
	  * @throws Exception  
	  * @version: 2011-6-1 上午11:06:48
	  * @See:
	 */
	public void loadAssignBranchInfo() throws Exception{
		String assignBranchInfo = this.userCertificateRevService.getAssignBranchJsonInfo();
		
		this.respond(assignBranchInfo);
				
	}
	
	/**
	 * 
	  * <p>获得某机构管辖的发卡机构对应JSON数据结构</p>
	  * @return
	  * @throws Exception  
	  * @version: 2011-6-3 上午11:07:08
	  * @See:
	 */
	public void loadMyBranchInfo() throws Exception {
		String branchCode = this.userCertificate.getBranchCode(); 
		
		String myBranchInfo = this.userCertificateRevService.getMyBranchJsonInfo(branchCode, getSessionUser());
		
		this.respond(myBranchInfo);
		
	}
	
	/**
	 * 
	  * <p>获得某机构管辖的商户机构对应JSON数据结构</p>
	  * @return
	  * @throws Exception  
	  * @version: 2011-6-3 上午11:07:28
	  * @See:
	 */
	public void loadMyMerchInfo() throws Exception {
		String branchCode = this.userCertificate.getBranchCode(); 
		
		String myMerchInfo = this.userCertificateRevService.getMyMerchJsonInfo(branchCode, getSessionUser());
		
		this.respond(myMerchInfo);
		
	}	
	
	
	/**
	 *  <p>根据指派机构以及选择机构类型(branchType)查询所有的可能的用户信息</p>
	  * @deprecated
	  * @description：
	  *     
	  * @return
	  * @throws Exception  
	  * @version: 2011-6-1 上午11:20:07
	  * @See: loadMyBranchInfo() and loadMyMerchInfo();
	 */
	public String loadBoundUserInfo() throws Exception {
		String branchCode = this.userCertificate.getBranchCode();    	
		String branchType = getBranchType();
		
		String boundUserInfo =  this.userCertificateRevService.getBoundUserJsonInfo(branchCode, branchType, getUserId(), getSessionUser());		
			
		this.respond(boundUserInfo);
		
		return null;
	}
	
	
	//-----------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	  * <p>LIST界面URL</p>
	  * @return  
	  * @version: 2011-4-28 上午11:29:23
	  * @See:
	 */
	private String getReturnSuccessURL() {
		return "/pages/userCert/list.do";
	}

	/**
	 * 
	  * <p>LIST界面中数字证书分页</p>
	  * @param certParaMap
	  * @param loginBranchCode
	  * @param loginRoleType
	  * @return
	  * @throws Exception  
	  * @version: 2011-4-28 上午11:29:26
	  * @See:
	 */
	private Paginater getMyUserCertificatePage(Map certParaMap,String loginBranchCode,String loginRoleType) throws Exception  {		   
		return this.userCertificateRevService.getUserCertificateList(certParaMap, getPageNumber(), getPageSize(),loginBranchCode, loginRoleType) ;
	}	
	
	/**
	 * <p>设置分页为10条(覆盖父类的设置)--由于展现比较占据页面空间</p> 
	 */
	@Override
	public int getPageSize() {
		return Constants.DEFAULT_SELECT_PAGE_SIZE;
	}
	
	/**
	 * 
	  * <p>界面中角色控制(分离出来)</p>
	  * @param loginRoleType  
	  * @version: 2011-5-18 下午04:57:01
	  * @See:
	 */
	private void initShowControl() {
		  if (isCenterRoleLogined())  {
			   this.showBranch = true;
			   this.showMerch = true;
			   this.showAssign = true;
			   this.showAdd = true;
			   this.showExport = true;					  
		   } else if (isFenzhiRoleLogined() || isAgentRoleLogined()) {
			   this.showMerch = true;
			   this.showBranch = true;	
		   } else if (isCardRoleLogined()) {
			   this.showBranch = true;			   
		   } else if (isCenterDeptRoleLogined() || isCardDeptRoleLogined()) {
			  
		   } else if (isMerchantRoleLogined()) {
			   this.showMerch = true;				  
		   }else {
			   this.showBranch = true;				 
		   }	    
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	

}
