package flink.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.taglib.TagUtils;

/**
 */
public class Link extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String href;
	private String name;
	private String onclick;
	private String onfocus;
	private String styleClass;
	private String styleId;
	private String target;
	private String title;
	
	public void setHref(String href) {
		this.href = href;
	}
	
	public int doEndTag() throws JspException {
		TagUtils.getInstance().write(pageContext, "</a>");
		return EVAL_PAGE;
	}

	public int doStartTag() throws JspException  {
		String contextPath = ((HttpServletRequest) this.pageContext.getRequest()).getContextPath();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"").append(contextPath).append(href).append("\"");
		
		if (StringUtils.isNotEmpty(name)) {
			sb.append(" name=\"").append(name).append("\"");
		}
		if (StringUtils.isNotEmpty(onclick)) {
			sb.append(" onclick=\"").append(onclick).append("\"");
		}
		if (StringUtils.isNotEmpty(onfocus)) {
			sb.append(" onfocus=\"").append(onfocus).append("\"");
		}
		if (StringUtils.isNotEmpty(styleClass)) {
			sb.append(" styleClass=\"").append(styleClass).append("\"");
		}
		if (StringUtils.isNotEmpty(styleId)) {
			sb.append(" styleId=\"").append(styleId).append("\"");
		}
		if (StringUtils.isNotEmpty(target)) {
			sb.append(" target=\"").append(target).append("\"");
		}
		if (StringUtils.isNotEmpty(title)) {
			sb.append(" title=\"").append(title).append("\"");
		}
		sb.append(">");
		
		TagUtils.getInstance().write(pageContext, sb.toString());
		return EVAL_BODY_INCLUDE;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
