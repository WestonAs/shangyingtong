package gnete.card.web.tag;

import gnete.card.entity.Privilege;
import gnete.etc.Constants;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;

/**
 * 生成的链接, 无权限时禁用链接, 结合js链接以模态对话框打开.
 * @author aps-mhc
 *
 */
public class PrivilegeLinkTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	/**
	 * privilge id.
	 */
	private String pid;
	private String href;
	private boolean refreshParent;

	public void setRefreshParent(boolean refreshParent) {
		this.refreshParent = refreshParent;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int doEndTag() throws JspException {
		TagUtils.getInstance().write(pageContext, "</a>");
		return EVAL_PAGE;
	}

	public int doStartTag() throws JspException {
		HttpSession session = (HttpSession) ((HttpServletRequest) this.pageContext.getRequest()).getSession();
		List<Privilege> userPrvlgs = (List<Privilege>) session.getAttribute(Constants.USER_PRIVILEGE);
		boolean hasPrivilege = false;
		
		for(Privilege p : userPrvlgs){
			if(p==null || p.getLimitId()==null){
				continue;
			}
			if(p.getLimitId().trim().equals(this.pid)){
				hasPrivilege = true;
				break;
			}
		}
		
		String refresh = "";
		if (refreshParent) {
			refresh = ", true";
		}
		
		String span = hasPrivilege 
				? "<a href=\"javascript:void(0)\" onclick=\"openContextDialog('" + href + "'" + refresh + ")\">" 
				: "<a disabled=\"disabled\" style=\"display:none\">";
		TagUtils.getInstance().write(pageContext, span);
		
		return EVAL_BODY_INCLUDE;
	}
}
