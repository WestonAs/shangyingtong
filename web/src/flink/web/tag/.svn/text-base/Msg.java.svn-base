package flink.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * 输出request 中的msg.
 * @author haichen.ma
 *
 */
public class Msg extends SimpleTagSupport {
	private static final long serialVersionUID = 1L;
	private String styleClass;

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public void doTag() throws JspException, IOException {
		HttpServletRequest request = (HttpServletRequest) ((PageContext) this.getJspContext()).getRequest();
		String msg = (String) request.getAttribute("msg");
		
		if (StringUtils.isEmpty(msg)) {
			return;
		}
		
		String classHtml = StringUtils.isEmpty(styleClass) ? "" : " class=\"" + styleClass + "\"";
		this.getJspContext().getOut().write("<div" + classHtml + ">" + msg + "</div>");
	}
}
