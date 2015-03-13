package gnete.card.web.util;

import gnete.card.entity.PrivilegeResource;
import gnete.card.entity.UserInfo;
import gnete.etc.Constants;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

public class WebUtil {
	public static UserInfo getSessionUser(HttpServletRequest request) {
		return (UserInfo) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
	}

	public static String getSessionUserId(HttpServletRequest request) {
		UserInfo user = getSessionUser(request);
		return user == null ? null : user.getUserId();
	}

	public static String getSessionUserName(HttpServletRequest request) {
		UserInfo user = getSessionUser(request);
		return user == null ? null : user.getUserName();
	}

	/** 根据link，从用户权限资源列表中获得PrivilegeResource */
	public static PrivilegeResource getPrivilegeResourceByLink(HttpServletRequest request, String link) {
		
		List<PrivilegeResource> prList = (List<PrivilegeResource>) WebUtils.getSessionAttribute(request,
				Constants.USER_PRIVILEGE_RES);
		if (prList == null) {
			return null;
		}
		for (PrivilegeResource pr : prList) {
			if (pr != null && pr.getLink() != null && pr.getLink().equals(link)) {
				return pr;
			}
		}
		return null;
	}

}
