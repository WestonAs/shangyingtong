package gnete.card.workflow.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;

/**
 */
public class WorkflowParamTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String adapter;
	
	private String returnUrl;
	
	private String refId;
	
	private String param;
	
	public void setAdapter(String adapter) {
		this.adapter = adapter;
	}
	
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	public int doEndTag() throws JspException {
		StringBuffer sb = new StringBuffer(1024);
		
		sb.append("<table class=\"form_grid\" width=\"100%\" border=\"0\" cellspacing=\"3\" cellpadding=\"0\">");
		sb.append("<tr><td width=\"80\" nowrap align=\"right\">审核意见</td><td><input id=\"id_Desc\" type=\"text\" name=\"desc\"/>");
		sb.append("<input type=\"button\" value=\"审核通过\" onclick=\"checkParam(this, true)\" class=\"ml30\"/>");
		sb.append("<input type=\"button\" value=\"审核不通过\" onclick=\"checkParam(this, false)\" class=\"ml30\" /></td>");
		sb.append("<input type=\"hidden\" id=\"pass\" name=\"pass\" value=\"\"/>");
		sb.append("<input type=\"hidden\" name=\"adapter\" value=\"").append(adapter).append("\"/>");
		sb.append("<input type=\"hidden\" name=\"returnUrl\" value=\"").append(returnUrl).append("\"/>");
		sb.append("<input type=\"hidden\" id=\"ids\" name=\"ids\" value=\"").append(refId).append("\"/>");
		sb.append("<input type=\"hidden\" id=\"idParamValue\" name=\"param\" value=\"").append(returnUrl).append("\"/>");
		sb.append("<input type=\"hidden\" id=\"idParamNames\" value=\"").append(param).append("\"/>");
		sb.append("</table></form>");
		
		TagUtils.getInstance().write(pageContext, sb.toString());
		return EVAL_PAGE;
	}

	public int doStartTag() throws JspException  {
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		String contextPath = request.getContextPath();
		
		StringBuffer sb = new StringBuffer(128);
		sb.append("<form id=\"workflowForm\" action=\"").append(contextPath).append("/workflow/doFlow.do\" method=\"post\" class=\"validate\">");
		
		TagUtils.getInstance().write(pageContext, sb.toString());
		return EVAL_BODY_INCLUDE;
	}

}
