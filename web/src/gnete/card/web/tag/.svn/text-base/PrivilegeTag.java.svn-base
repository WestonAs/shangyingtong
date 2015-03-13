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
 * @author aps-mhc
 *
 */
public class PrivilegeTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	/**
	 * privilge id.
	 */
	private String pid;
	private String style;

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int doEndTag() throws JspException {
		TagUtils.getInstance().write(pageContext, "</span>");
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
			if(p.getLimitId().equals(this.pid)){
				hasPrivilege = true;
				break;
			}
		}
		
		String CssStyle = this.style==null ? "" : style;
		String span = hasPrivilege ? "<span style=\"" + CssStyle + "\">" : 
			"<span class=\"no-privilege\" style=\"display:none;" + CssStyle + "\">";
		TagUtils.getInstance().write(pageContext, span);
		
		return EVAL_BODY_INCLUDE;
	}
}
