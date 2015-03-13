package gnete.card.web.user;

import flink.util.DateUtil;
import flink.util.IPrivilege;
import flink.util.LogUtils;
import flink.util.PrivilegeHelper;
import gnete.card.dao.LogoConfigDAO;
import gnete.card.dao.PrivilegeDAO;
import gnete.card.dao.PrivilegeResourceDAO;
import gnete.card.dao.PublishNoticeDAO;
import gnete.card.dao.RoleInfoDAO;
import gnete.card.dao.UserInfoDAO;
import gnete.card.dao.UserRoleDAO;
import gnete.card.entity.LogoConfig;
import gnete.card.entity.Privilege;
import gnete.card.entity.PrivilegeResource;
import gnete.card.entity.PublishNotice;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.UserRoleKey;
import gnete.card.entity.flag.ShowFlag;
import gnete.card.entity.state.UserState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.UserCertificateRevService;
import gnete.card.service.UserService;
import gnete.card.tag.NameTag;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.BooleanMessage;
import gnete.etc.Constants;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * LoginAction.java.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-7-7
 */
public class LoginAction extends BaseAction {
	private static final String LOGIN_PWD_RANDOM = "LOGIN_PWD_RANDOM";
	
	@Autowired
	private PrivilegeDAO privilegeDAO;
	@Autowired
	private RoleInfoDAO roleInfoDAO;
	@Autowired
	private PublishNoticeDAO publishNoticeDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private PrivilegeResourceDAO privilegeResourceDAO;
	@Autowired
	private UserCertificateRevService userCertifcateReService;
	@Autowired
	private UserInfoDAO userInfoDAO;
	@Autowired
	private LogoConfigDAO logoConfigDAO;
	@Autowired
	private UserRoleDAO	userRoleDAO;
	
	private LogoConfig logoConfig;
	
	private UserInfo userInfo;
	private IPrivilege menuTree;
	private Collection menus;
	private String roleId;
	
	private String logoName; // logo的名字
	
	private List<RoleInfo> roleList;

	private static final String LETTERS = "0123456789";
	
	// 首页大图默认的名字
	private static final String DEFAULT_HOME_BIG = "login.jpg";
	
	// 首页小图默认的名字
	private static final String DEFAULT_HOME_SMALL = "logo.jpg";
	
	// 登录后小图默认的名字
	private static final String DEFAULT_LOGIN_SMALL = "logo.gif";
	
	private String bno;
	private static final String BNO = "BNO";
	private static final String SESSION_USER_TEMP = Constants.SESSION_USER+"_TEMP";
	
//	private static Logger logger = Logger.getLogger(LoginAction.class);
	
	@Override
	public String execute() throws Exception {
		if (userInfo == null || StringUtils.isEmpty(userInfo.getUserId())){
			return ERROR;
		}
		
		//验证验证码
		String validateCode = request.getParameter("validateCode");
		if (!isValidValidateCode(validateCode)) {
			this.setMessage("验证码错误!");
			return ERROR;
		}
		
		Assert.isTrue(
				this.getSessonLoginRandom() != null
						&& this.getSessonLoginRandom().equals(this.getFormMapValue("random")),
				"页面失效，请重新登录");
		
		// 验证用户.
		BooleanMessage bm = this.userService.checkLogin(StringUtils.trim(userInfo.getUserId()),
				StringUtils.trim(userInfo.getUserPwd()),
				this.getSessonLoginRandom(), roleId);
		if (!bm.isSucc()) {// 验证失败
			this.setMessage(bm.getMsg());
			return this.ERROR;
		} else { // 验证成功
			this.userInfo = (UserInfo) userInfoDAO.findByPk(StringUtils.trim(userInfo.getUserId()));
			userInfo.setState(UserState.ONLINE.getValue());
			this.userInfoDAO.update(userInfo);
		}
		
		RoleInfo roleInfo = (RoleInfo) roleInfoDAO.findByPk(roleId);
		if (roleInfo.getRoleType().equals(RoleType.CARD_SELL.getValue())) {
			
		}
		userInfo.setRole(roleInfo);
		WebUtils.setSessionAttribute(request, Constants.SESSION_USER, userInfo);
		
		this.clearSessonLoginRandom();
		
		// 加载用户权限和菜单.
		loadPrivilege(userInfo);
		loadRoleList(userInfo);
		
		logoName = request.getParameter("logoName");
		if (StringUtils.isEmpty(logoName)) {
			this.setLogoName("logo.gif");
		}
		
		Object object = WebUtils.getSessionAttribute(request, BNO);
		if (object != null) {
			request.setAttribute("bno", (String)object);
		}
		
		
		// 记录用户日志
		String msg = LogUtils.r("用户编号为[{0}]的用户登录成功", userInfo.getUserId());
		this.log(msg, UserLogType.OTHER, "login");
		logger.debug(msg + "，登录ip为：[" + request.getRemoteAddr() + "]");
		return SUCCESS;
	}

	/**
	 * 登录检验
	 * @return
	 * @throws Exception
	 */
	public void checkLogin() throws Exception {
		JSONObject object = new JSONObject();
		String msg = "";
		boolean checkSuccess = false;
		boolean imgError = false;
		boolean isExpired = false;
		String validateCode = request.getParameter("validateCode");
		
		if (this.getSessonLoginRandom() == null
				|| !this.getSessonLoginRandom().equals(this.getFormMapValue("random"))) {
			isExpired = true;
			msg = "页面失效，请重新登录";
			
		}else if (!isValidValidateCode(validateCode)) { // 验证验证码
			imgError = true;
			msg = "验证码错误!";
			
		} else {
			// 验证用户.
			if (userInfo != null) {
				BooleanMessage bm = this.userService.checkLogin(StringUtils.trim(userInfo.getUserId()),
						StringUtils.trim(userInfo.getUserPwd()), this.getSessonLoginRandom(), roleId);
				checkSuccess = bm.isSucc();
				msg = bm.getMsg();
			}
		}
		object.put("success", checkSuccess);
		object.put("msg", msg);
		object.put("imgError", imgError);
		object.put("isExpired", isExpired);
		this.respond(object.toString());
	}
	
	/**
	 * 证书登录
	 */
	public String certLogin() throws Exception {
		String serialNo = request.getParameter("serialNo");
		String mySign = request.getParameter("mySign");
		
		if (StringUtils.isEmpty(serialNo)) {
			this.setMessage("取证书序列号出错");
			return ERROR;
		}
		RoleInfo roleInfo = new RoleInfo();

		// 验证用户.
		try {
			Assert.isTrue(
					this.getSessonLoginRandom() != null
							&& this.getSessonLoginRandom().equals(this.getFormMapValue("random")),
					"页面失效，请重新登录");
			// 验证签名
			boolean flag = userCertifcateReService.processUserSigVerify(serialNo, mySign, this.getSessonLoginRandom());
			if (!flag) {
				this.setMessage("验证签名失败");
				return ERROR;
			}
			
			roleList = this.userCertifcateReService.findRoleInfo(serialNo);
			if (CollectionUtils.isEmpty(roleList)) {
				this.setMessage("该证书对应的用户的角色为空");
				return ERROR;
			}
		
			this.userInfo = this.userService.certLogin(serialNo);
		
			if (roleList.size() > 1) {
				WebUtils.setSessionAttribute(request, SESSION_USER_TEMP, userInfo);
				return "chooseRole";
			}
			WebUtils.setSessionAttribute(request, SESSION_USER_TEMP, null);
			
			roleInfo = (RoleInfo) roleInfoDAO.findByPk(roleList.get(0).getRoleId());
			userInfo.setRole(roleInfo);
			
		} catch (BizException e) {
			this.setMessage(e.getMessage());
			return ERROR;
		}
		
		WebUtils.setSessionAttribute(request, Constants.SESSION_USER, userInfo);
		
		this.clearSessonLoginRandom();
		
		// 加载用户权限和菜单.
		loadPrivilege(userInfo);
		loadRoleList(userInfo);
		
		return SUCCESS;
	}
	
	/** 接上一步"证书登录" */
	public String chooseRole() {
		if (userInfo == null || StringUtils.isBlank(userInfo.getUserId())) {
			return ERROR;
		}
		
		UserInfo sessionUser = (UserInfo) WebUtils.getSessionAttribute(request, SESSION_USER_TEMP);

		if (sessionUser == null || !userInfo.getUserId().equals(sessionUser.getUserId())) {
			this.setMessage("未找到指定用户！");
			return ERROR;
		}
		this.userInfo = sessionUser;
		
		String chooseRoleId = request.getParameter("chooseRoleId");
		boolean isExsitUserRole = userRoleDAO.isExist(new UserRoleKey(chooseRoleId, userInfo.getUserId()));
		RoleInfo roleInfo = (RoleInfo) roleInfoDAO.findByPk(chooseRoleId);
		if (!isExsitUserRole || roleInfo == null) {
			this.setMessage("角色错误！");
			return ERROR;
		}
		WebUtils.setSessionAttribute(request, SESSION_USER_TEMP, null);

		this.userInfo.setRole(roleInfo);
		WebUtils.setSessionAttribute(request, Constants.SESSION_USER, this.userInfo);
		
		// 加载用户权限和菜单.
		loadPrivilege(userInfo);
		loadRoleList(userInfo);
		
		return SUCCESS;
	}
	
	public String logout() throws Exception{
		if (getSessionUser() != null) {
			String _userId = this.getSessionUser().getUserId();
			String _name = this.getSessionUser().getUserName();
			
			// 记录用户日志
			String msg = LogUtils.r("用户编号为[{0}]的用户退出系统成功", _userId, _name);
			this.log(msg, UserLogType.OTHER, "logout");
			this.logger.debug(msg);
		}

		Object object = WebUtils.getSessionAttribute(request, BNO);
		if (object != null) {
			this.setBno((String)object);
		}
		this.userService.logout(this.getSessionUser());
		
		WebUtils.setSessionAttribute(request, Constants.USER_MENU, null);
		WebUtils.setSessionAttribute(request, Constants.USER_PRIVILEGE, null);
		WebUtils.setSessionAttribute(request, Constants.USER_PRIVILEGE_RES, null);
		WebUtils.setSessionAttribute(request, Constants.SESSION_USER, null);
		WebUtils.setSessionAttribute(request, BNO, null);
		
		if (request.getSession(false) != null) {
			request.getSession(false).invalidate();
		}
		
		if (object == null) {
			String bno = request.getParameter("bno");
			this.setBno(bno);
			WebUtils.setSessionAttribute(request, BNO, bno);
		}
		
		return SUCCESS;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String index() throws Exception {
		
		// 得到本类的目录，根据此目录取得应用的根目录
//		String path = this.getClass().getClassLoader().getResource("").getPath();
//		path = path.substring(0, path.indexOf("WEB-INF")) + "images/logo/";
		String path = request.getContextPath() + "/images/logo/";
		
		String branchNo = request.getParameter("bno");
		if (StringUtils.isNotEmpty(branchNo)) {
			WebUtils.setSessionAttribute(request, BNO, branchNo);
		} else {
			Object object = WebUtils.getSessionAttribute(request, BNO);
			if (object != null) {
				branchNo = (String)object;
				request.setAttribute("bno", (String)object);
			}
		}

		if (StringUtils.isEmpty(branchNo)) {
			this.logoConfig = new LogoConfig();
			
			this.logoConfig.setHomeBig(path + DEFAULT_HOME_BIG);
			this.logoConfig.setHomeSmall(path + DEFAULT_HOME_SMALL);
			this.logoConfig.setLoginSmall(path + DEFAULT_LOGIN_SMALL);
		} else {
			this.logoConfig = (LogoConfig) this.logoConfigDAO.findByPk(branchNo);
			if (logoConfig == null) {
				this.logoConfig = new LogoConfig();
				
				this.logoConfig.setHomeBig(path + DEFAULT_HOME_BIG);
				this.logoConfig.setHomeSmall(path + DEFAULT_HOME_SMALL);
				this.logoConfig.setLoginSmall(path + DEFAULT_LOGIN_SMALL);
			} else {
				String homeBig = StringUtils.isNotEmpty(logoConfig.getHomeBig()) ? logoConfig.getHomeBig() : DEFAULT_HOME_BIG;
				this.logoConfig.setHomeBig(path + homeBig);
				
				String homeSmall = StringUtils.isNotEmpty(logoConfig.getHomeSmall()) ? logoConfig.getHomeSmall() : DEFAULT_HOME_SMALL;
				this.logoConfig.setHomeSmall(path + homeSmall);
				
				String loginSmall = StringUtils.isNotEmpty(logoConfig.getLoginSmall()) ? logoConfig.getLoginSmall() : DEFAULT_LOGIN_SMALL;
				this.logoConfig.setLoginSmall(path + loginSmall);
			}
		}
		
		this.saveSessonLoginRandom();
		
		return "index";
	}
	
	//加载是否有用户通知
	public void hasNotice() throws Exception {
		List<PublishNotice> noticeList = publishNoticeDAO.findByShowFlag(ShowFlag.SHOW.getValue());
		
		JSONObject object = new JSONObject();
		if (CollectionUtils.isNotEmpty(noticeList)){
			PublishNotice publishNotice = noticeList.get(0);
//			UserInfo pubUser = this.userService.getUser(publishNotice.getPubUser());
			
			object.put("title", publishNotice.getTitle());
			object.put("content", publishNotice.getContent());
			object.put("pubTime", DateUtil.getDate(publishNotice.getPubTime(), "yyyy年MM月dd日HH:mm"));
			object.put("pubUser", NameTag.getUserName(publishNotice.getPubUser()));
			object.put("success", true);
//			this.respond("{'success':true, 'title':'" + publishNotice.getTitle()
//					+ "', 'content':'" + publishNotice.getContent()
//					+ "', 'pubTime':'" + DateUtil.getDate(publishNotice.getPubTime(), "yyyy年MM月dd日HH:mm") 
//					+ "', 'pubUser':'" + pubUser.getUserName() + "'}");
		} else {
			object.put("success", false);
//			this.respond("{'success':false}");
		}
		this.respond(object.toString());
	}
	
	/**
	 * 加载验证码
	 */
	public void validateImage() throws Exception {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		Random random = new Random();
		StringBuffer code = new StringBuffer();
		
		try {
			for (int i = 0; i < 4; i++) {
				int index = random.nextInt(LETTERS.length());
				code.append(LETTERS.charAt(index));
			}
			
			BufferedImage image = new ValidateImage().create(code.toString());
			request.getSession().setAttribute("validateCode", code.toString());
//			ImageIO.write(image, "JPEG", response.getOutputStream());
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
			encoder.encode(image); //防止删掉tomcat根目录下的temp目录时，验证码产生不了的问题
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 是否为有效的验证码.
	 * 
	 * @param validateCode
	 * @param request
	 * @return
	 */
	private boolean isValidValidateCode(String validateCode) {
		String code = (String) request.getSession().getAttribute("validateCode");
		
		if (code == null) {
			return false;
		}
		
		return code.equalsIgnoreCase(validateCode);
		// FIXME
//		return true;
	}
	
	/**
	 * 加载用户权限和菜单, 保存在request(或session) 中.
	 * 
	 * @param user
	 * @param request
	 */
	private void loadPrivilege(UserInfo user) {
		String roleId = user.getRole().getRoleId();
		List menus = privilegeDAO.getMenus(roleId);
		
		// 构造根节点.
		IPrivilege root = new Privilege();
		root.setCode("00");
		
		// 将菜单转换为树形状, 保存到session中.
		menuTree = PrivilegeHelper.getPrivilegeTree(root, menus);
		WebUtils.setSessionAttribute(request, Constants.USER_MENU, menuTree);
		
//		// 售卡代理拥有的权限组放入session中
//		if (RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())) {
//			
//			
//		}
		
		// 获取用户权限资源, 保存到session中.
		List<PrivilegeResource> resources = privilegeResourceDAO.getPrivilegeResources(roleId);
		WebUtils.setSessionAttribute(request, Constants.USER_PRIVILEGE_RES, resources);
		
		// 获取用户所有权限(包括菜单), 保存到session中.
		List<Privilege> privileges = privilegeDAO.getPrivilege(roleId);
		WebUtils.setSessionAttribute(request, Constants.USER_PRIVILEGE, privileges);
	}
	
	/**
	 * 加载登录用户权限菜单中, 加载第三级菜单.
	 */
	public String topbutton() {
		// 第一级菜单权限编号(不包括根节点).
		String parentCode = request.getParameter("parentCode");
		
		// 第二级菜单权限编号(不包括根节点).
		String privilegeCode = request.getParameter("privilegeCode");
		Privilege menuTree = (Privilege) WebUtils.getSessionAttribute(request, Constants.USER_MENU);
		
		// 查询第三级菜单.
		this.menus = getPrivilegeTopButton(menuTree, parentCode, privilegeCode);
		
		return "topbutton";
	}
	
	/**
	 * @deprecated 角色切换刷新不了页面
	 * @return
	 */
	public String switchRole() {
		String switchRoleId = request.getParameter("switchRoleId");
		RoleInfo role = (RoleInfo) this.roleInfoDAO.findByPk(switchRoleId);
		UserInfo user = getSessionUser();
		user.setRole(role);
		
		WebUtils.setSessionAttribute(request, Constants.SESSION_USER, user);
		
		// 加载用户权限和菜单.
		loadPrivilege(user);
		
		loadRoleList(user);
		
		return SUCCESS;
	}
	
	/**
	 * 查询第三级菜单.
	 * 
	 * @param menuTree
	 * @param firstCode
	 * @param secondCode
	 * @return
	 */
	private Collection getPrivilegeTopButton(Privilege menuTree, String firstCode, String secondCode) {
		// 循环第一级菜单.
		for (Iterator i = menuTree.getChildren().iterator(); i.hasNext();) {
			Privilege first = (Privilege) i.next();
			
			// 找到第一级菜单, 循环子节点查询第二级.
			if (first.getCode().equals(firstCode)) {
				for (Iterator j = first.getChildren().iterator(); j.hasNext();) {
					Privilege second = (Privilege) j.next();
					
					if (second.getCode().equals(secondCode)) {
						return second.getChildren();
					}
				}
			}
		}
		return Collections.EMPTY_LIST;
	}
	
	/**
	 * 查询角色列表
	 * @return
	 */
	private void loadRoleList(UserInfo user) {
		String userId = user.getUserId();
		roleList = this.roleInfoDAO.findByUserId(userId);
		WebUtils.setSessionAttribute(request, "ROLE_LIST", roleList);
	}
	
	public void loadRole(){
		String userId = this.userInfo.getUserId();
		List<RoleInfo> hasRoleList = this.roleInfoDAO.findByUserId(userId);
		StringBuffer sb = new StringBuffer(128);
		for (RoleInfo roleInfo : hasRoleList) {
			sb.append("<option value=\"").append(roleInfo.getRoleId()).append("\">")
				.append(roleInfo.getRoleName()).append("</option>");
		}
		this.respond(sb.toString());
	}

	/** 保存登录密码随机数 */
	private void saveSessonLoginRandom() {
		String random = RandomStringUtils.randomNumeric(16);
		this.formMap.put("random", random);
		WebUtils.setSessionAttribute(request, LOGIN_PWD_RANDOM, random);
	}
	/** 获得会话保存的登录密码随机数 */
	private String getSessonLoginRandom() {
		return (String) WebUtils.getSessionAttribute(request, LOGIN_PWD_RANDOM);
	}
	/** 清除会话保存的登录密码 */
	private void clearSessonLoginRandom() {
		WebUtils.setSessionAttribute(request, LOGIN_PWD_RANDOM, null);
	}
	
	
	// =================== getters and setters following ===================
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public IPrivilege getMenuTree() {
		return menuTree;
	}

	public void setMenuTree(IPrivilege menuTree) {
		this.menuTree = menuTree;
	}

	public Collection getMenus() {
		return menus;
	}

	public void setMenus(Collection menus) {
		this.menus = menus;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<RoleInfo> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleInfo> roleList) {
		this.roleList = roleList;
	}

	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	public LogoConfig getLogoConfig() {
		return logoConfig;
	}

	public void setLogoConfig(LogoConfig logoConfig) {
		this.logoConfig = logoConfig;
	}

	public String getBno() {
		return bno;
	}

	public void setBno(String bno) {
		this.bno = bno;
	}

}
