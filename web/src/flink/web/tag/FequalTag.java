package flink.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author haichen.ma
 *
 */
public class FequalTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String name;
	private String value;

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		Object v = request.getAttribute(name);
		
		if (v == null) {
			return SKIP_BODY;
		}
		
		return v.equals(value) ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}
}
