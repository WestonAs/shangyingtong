package gnete.card.web;

import flink.util.CommonHelper;
import flink.util.PrivilegeHelper;
import flink.web.EhcacheCache;
import gnete.card.entity.Privilege;
import gnete.card.entity.PrivilegeResource;
import gnete.card.entity.UserInfo;
import gnete.card.web.util.WebUtil;
import gnete.etc.Constants;
import gnete.etc.Symbol;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;
/**
 * <p>PrivilegeFilter替代类</p>
 * <p>将公共及检查资源保存在servletContext中(而非作为静态常量保存)</p>
 * <p>去掉patternMatch的保存</p>
 * @Project: Card
 * @File: PrivilegeFilterRevamp.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-8-7
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class PrivilegeFilterRevamp implements Filter {
	private static Logger logger = LoggerFactory.getLogger(PrivilegeFilterRevamp.class);
	
    private static final String PUBLICURL_CONTEXT_ID = "gnete.card.web.publicUrl";
    
    private static final String LOGINEDURL_CONTEXT_ID = "gnete.card.web.loginedUrl";
    
    private static final String OVERTIME_URL = "/common/overtime.jsp";
    
	private static final String INVALID_URL = "/common/invalid.jsp";
	
	private static final String HOME_URL = "/home.jsp";
	
	private static final String DEFAULT_ACTION_TAG = ".do";
	
	private static final String DEFAULT_ACTION_METHOD = "action";
	
	private static final String DEFAULT_URI_SEPARATOR = "/";
	
	private static final String PRIVILEGE_REQUEST_ID = "PRIVILEGE_PATH";
	
	private static final String ACT_REQUEST_ID = "ACT";
    
    private FilterConfig config;
   
    public void init(FilterConfig config) throws ServletException {
	    this.config = config;
	    initFilter(this.config.getServletContext());
	}

	public void destroy() {
		destroyFilter();
	}

	public void doFilter(ServletRequest servletRequest, 
			ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String uri = getUrl(request);
		
		try {
			// 公共资源
			if (isPublicUri(uri)) {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}
			
			UserInfo userInfo = WebUtil.getSessionUser(request);
			if (userInfo == null) { // 还未登录.
				response.sendRedirect(request.getContextPath() + OVERTIME_URL);
				return;
			} else if (!userInfo.isCertUser()
					&& (userInfo.isInitPwd() || userInfo.getDaysOfPwdChanged() > 90)) {
				// 已登录，非证书用户 且（初始密码或已超过90天未修改密码），重定向到首页提示
				if (!isHomeOrModifyPassUri(uri)) {
					response.sendRedirect(request.getContextPath() + HOME_URL);
					return;
				}
			}
			
			// 是否具有访问权限.
	 		if (hasPrivilege(request, uri)) {
	 			String subUri = StringUtils.substring(uri, StringUtils.lastIndexOf(uri ,DEFAULT_URI_SEPARATOR), uri.length());
				logger.info("用户[{}]访问连接:{}", WebUtil.getSessionUserId(request), uri);
	 			
	 			// 是否是查询list页面
	 			String sessionId = WebUtils.getSessionId(request);
	 			
	 			// 初始化缓存对象 
	 			EhcacheCache cache = new EhcacheCache(EhcacheCache.URL_CACHE);
	 			
				Map paramsMap = request.getParameterMap();
				 // 列表页
				if (StringUtils.indexOf(StringUtils.lowerCase(subUri), "list") != -1) {
					if (StringUtils.isNotEmpty(MapUtils.getString(paramsMap, "goBack"))) { // 有goBack参数，表示 “返回” 列表页
						String paramString = (String) cache.getObject(sessionId + uri);
						if (StringUtils.isNotBlank(paramString)) {// 缓存参数不为空，则重定向
							String redirectUri = request.getRequestURL().append(paramString).toString();
							logger.info("取出缓存的查询参数，重定向连接:{}", redirectUri);
							response.sendRedirect(redirectUri); 
							return;
						}

					} else { // 表示 进入 列表页, 缓存参数
						cache.putObject(sessionId + uri, changeMapToString(paramsMap));
					}
				}

				for (String[] params : (Collection<String[]>) request.getParameterMap().values()) {
					for (int i = 0; i < params.length; i++) {
						if (CommonHelper.isNotEmpty(params[i]) && Charset.forName("ISO-8859-1").newEncoder().canEncode(params[i])) {// response.sendRedirect() 中文乱码
							params[i] = new String(params[i].getBytes("ISO-8859-1"), "UTF-8");
						}
					}
				}

				filterChain.doFilter(servletRequest, servletResponse);			
			} else {
				logger.info("用户[{}]没有权限访问链接[{}]", WebUtil.getSessionUserId(request), uri);
				response.sendRedirect(request.getContextPath() + INVALID_URL);
			}
		}catch(IOException ex) {
			this.config.getServletContext().log("过滤器捕获到IO异常", ex);
			throw ex;
		}catch(ServletException ex) {
			this.config.getServletContext().log("过滤器捕获到Servlet异常", ex);
			throw ex;
		}		
	}
	
	
	private String changeMapToString(Map<String, String[]> paramsMap) {
		StringBuilder url = new StringBuilder();
		
		url.append("?");
		
		for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
			String key = entry.getKey();
			String[] value = entry.getValue();
			
			if (StringUtils.indexOf(StringUtils.lowerCase(key), "token") != -1) {
				continue;
			}

			if (url.length() > 1) {
				url.append("&");
			}
			url.append(key + "=" + encodeUrl(Arrays.toString(value)));
		}
		
		return url.toString();
	}

	private String encodeUrl(String value) {
		String result = "";
		result = value.replaceAll("\\[", "").replaceAll("\\]", "");
		try {
			result = URLEncoder.encode(result, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("URL Encode Exception: " + e);
		}
		return result;
	}
	
	private void destroyFilter() {
		ServletContext context = this.config.getServletContext();
		context.removeAttribute(PUBLICURL_CONTEXT_ID);
		context.removeAttribute(LOGINEDURL_CONTEXT_ID);
		
		this.config = null;
		
		logger.info("权限过滤器销毁成功,[" + PUBLICURL_CONTEXT_ID + "," + LOGINEDURL_CONTEXT_ID + "]移除成功");
		
	}
	
	
	private void initFilter(ServletContext context) {
		initPublicUrl(context);
		initLoginedUrl(context);
		
		logger.info("权限过滤器初始化成功,[" + PUBLICURL_CONTEXT_ID + "," + LOGINEDURL_CONTEXT_ID + "]初始化成功");
	}
	
	private void initPublicUrl(ServletContext context) {
		final String[] PUBLIC_URLS = {
				"/",
				"/errorCode.jsp",
				"/goIndex.jsp",
				"/index.do",
				"/checkWebApp.do",
				"/login.do",
				"/certLogin.do",
				"/logout.do",
				"/loadRole.do",
				"/login.jsp",
				"/main.jsp",
				"/overtime.jsp",
				"/invalid.jsp",
				"/authorityAlert.jsp",
				"/hasNotice.do",
				"/validateImage.do",
				"/chooseRole.do",
				"/checkLogin.do",
				"/print.jsp",
				"/cardholderLogin.do",
				"/checkCardholderLogin.do",
				"/queryBalance.do",
				"/queryTrans.do",
				"/showChangePassword.do",
				"/changePassword.do",
//				"/cardholderLogin.jsp",
//				"/cardholder.jsp",
//				"/queryBalance.jsp",
//				"/loginResult.jsp",
//				"/changePassword.jsp",
//				"/chgSuccess.jsp",
//				"/chgFaile.jsp"
	    };
		
		Map<String,String> publicUrlMap = new ConcurrentHashMap<String,String>();
		
		for(String publicUrl : PUBLIC_URLS) {
			publicUrlMap.put(publicUrl, publicUrl);
		}
		
	    context.setAttribute(PUBLICURL_CONTEXT_ID, publicUrlMap);
	}
	
	
	private void initLoginedUrl(ServletContext context) {
		final String[] LOGINED_PUBLIC_URLS = {
				"/error.jsp",
				"/success.jsp",
				"/home.jsp",
				"/showModifyPass.do",
				"/modifyPass.do",
				"/menu.jsp",
				"/topbutton.do",
				"/doFlow.do",
				"/initTreeByAdd.do",
				"/initTreeByUpdate.do",
				"/initTreeByDetail.do",
				"/initTreeByUser.do",
				"/initTreeByAddSaleProxy.do",
				"/initTreeByUpdateSaleProxy.do",
				"/initTreeByAddDept.do",
				"/initTreeByUpdateDept.do",
				"/initTreeByDeptDetail.do",
				"/getRoleTypeOption.do",
				"/showSelect.do",
				"/select.do",
				"/calRealAmt.do",
				"/getAdminUserId.do",
				"/checkUserId.do",
				"/calcCardOther.do",
				"/checkCard.do",
				"/findRebateRule.do",
				"/findCustomerRebateType.do",
				"/ajaxFindFirstCardToSold.do",
				"/initTreeByRoleType.do",
				"/loadMerch.do",
				"/loadDept.do",
				"/loadBranch.do",
				"/loadCity.do",
				"/loadBranchLevel.do",
				"/getDevelopList.do",
				"/loadAccAreaCode.do",
				"/switchRole.do",
		};
		
		Map<String,String> loginedUrlMap = new ConcurrentHashMap<String,String>(); 
		
		for(String publicUrl : LOGINED_PUBLIC_URLS) {
			loginedUrlMap.put(publicUrl, publicUrl);
		}		
		
		context.setAttribute(LOGINEDURL_CONTEXT_ID, loginedUrlMap);
	}
	
	@SuppressWarnings("unchecked")
	private boolean isPublicUri(String uri) {
		String subUri = uri.substring(uri.lastIndexOf(DEFAULT_URI_SEPARATOR), uri.length());
		
		Map<String,String> publicUrlMap = (Map<String,String>)this.config.getServletContext().getAttribute(PUBLICURL_CONTEXT_ID);
		
		return publicUrlMap.containsKey(subUri);
	}
	
	/**
	 * 登陆后可访问的公共url.
	 * @param uri
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean isLoginedPublicUri(String uri) {
		String subUri = uri.substring(uri.lastIndexOf(DEFAULT_URI_SEPARATOR), uri.length());
		
        Map<String,String>loginedUrlMap = (Map<String,String>)this.config.getServletContext().getAttribute(LOGINEDURL_CONTEXT_ID);
		
		return loginedUrlMap.containsKey(subUri);
	}

	/** 是否 首页 或 用户修改密码的url */
	private boolean isHomeOrModifyPassUri(String uri) {
		String subUri = uri.substring(uri.lastIndexOf(DEFAULT_URI_SEPARATOR), uri.length());
		List<String> list = Arrays.asList(HOME_URL, "/showModifyPass.do", "/modifyPass.do");
		return list.contains(subUri);
	}
	
	/**
	 * 是否有权限访问.
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean hasPrivilege(HttpServletRequest request, String uri) {
		if (isLoginedPublicUri(uri)) {
			return true;
		}
		if (uri.substring(request.getContextPath().length()).startsWith("/ajax/ajax")) {
			return true;
		}
		
		String link = request.getRequestURI().substring(request.getContextPath().length());
		PrivilegeResource res = WebUtil.getPrivilegeResourceByLink(request, link);

		boolean hasPrivilege = (res != null);
		
		if (hasPrivilege) {
			// 权限路径.
			List privileges = (List) request.getSession().getAttribute(Constants.USER_PRIVILEGE);
			request.setAttribute(PRIVILEGE_REQUEST_ID, getPrivilegePath(privileges, res.getLimitId()));
			
			// 当前动作
			request.setAttribute(ACT_REQUEST_ID, (Privilege) PrivilegeHelper.getPrivilege(res.getLimitId(), privileges));
		} else {
			logger.debug("用户[{}]没有权限, {}", WebUtil.getSessionUserId(request), link);
		}
		
		return hasPrivilege;
	}
	
	
	/**
	 * 获取权限的路径.
	 * @param privileges
	 * @param limitId 权限编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List getPrivilegePath(List menus, String limitId) {
		List paths = new ArrayList();
		Privilege curPrivilege = (Privilege) PrivilegeHelper.getPrivilege(limitId, menus);
		
		if (Symbol.YES.equals(curPrivilege.getIsMenu())) {
			paths.add(curPrivilege);
		}
		
		// 查找父节点.
		while (!Constants.ROOT_PRIVILEGE_CODE.equals(curPrivilege.getCode())) {
			curPrivilege = (Privilege) PrivilegeHelper.getPrivilege(curPrivilege.getParent(), menus);
			
			if (Symbol.YES.equals(curPrivilege.getIsMenu())) {
				paths.add(0, curPrivilege);
			}
		}
		
		return paths;
	}
	
	/**
	 *  <p>返回获取的url(requestURI)</p>
	  * @param request
	  * @return  
	  * @version: 2011-8-7 下午12:37:42
	  * @See: DefaultActionMapper
	 */
	private static String getUrl(HttpServletRequest request) {
        String uri = request.getRequestURI(); 
		
		if(uri.endsWith(DEFAULT_ACTION_TAG)) {
			String action = request.getParameter(DEFAULT_ACTION_METHOD);
			
			if(StringUtils.isNotEmpty(action)) {
			    uri = new StringBuilder(uri).append("?action=").append(action).toString();
			}
		}
		
		return uri;
	}
	
	

}
