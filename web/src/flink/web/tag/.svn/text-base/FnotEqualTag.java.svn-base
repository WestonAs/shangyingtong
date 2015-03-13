package flink.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author haichen.ma
 *
 */
public class FnotEqualTag extends TagSupport {
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
			return EVAL_BODY_INCLUDE;
		}
		
		return v.equals(value) ? SKIP_BODY : EVAL_BODY_INCLUDE;
	}
}
