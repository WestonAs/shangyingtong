package flink.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts2.views.jsp.TagUtils;
import com.opensymphony.xwork2.ognl.OgnlValueStack;

import flink.util.Paginater;

/**
 * 
 * @author aps-lih
 * 
 */
public class PageButtons extends TagSupport {
	private static final long serialVersionUID = -171297941256524379L;
	private String name;
	private String href = "javascript:turnPage({});";
	private long currentPage;
	private static int BUTTON_COUNT = 10;
	private static int CURRENT_POS = 3;
	private OgnlValueStack valueStack;

	@Override
	public int doEndTag() throws JspException {
		valueStack = (OgnlValueStack) TagUtils.getStack(pageContext);
		Paginater paginater = (Paginater) valueStack.findValue(name);
		currentPage = paginater.getCurrentPage();
		long startPos = this.getStartPos(paginater);

		// 页号比较大的时候将显示页数减为8个
		if (startPos > 95) {
			BUTTON_COUNT = 8;
		} else {
			BUTTON_COUNT = 10;
		}
		// if(paginater.getMaxPage()>1) {
		try {
			JspWriter out = pageContext.getOut();
			out.write(makeStartHtml(paginater));
			out.write(makeMiddleHtml(paginater));
			out.write(makeEndHtml(paginater));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// }

		return super.doEndTag();
	}

	private String makeStartHtml(Paginater paginater) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<div>");
		buffer.append("<div class=\"pages\">");
		buffer.append("<em>");
		buffer.append(paginater.getMaxRowCount());
		buffer.append("</em>");
		long start = getStartPos(paginater);
		if (start > 2) {
			buffer.append("<a href=\"" + makeTarget(1) + "\">");
			buffer.append(1 + "...");
			buffer.append("</a>");
		}
		if (start > 1) {
			buffer.append("<a href=\"" + makeTarget(start - 1) + "\">&lt;</a>");
		}
		return buffer.toString();
	}

	private String makeMiddleHtml(Paginater paginater) {
		long maxPage = paginater.getMaxPage();
		long startPos = getStartPos(paginater);
		long endPos = startPos + BUTTON_COUNT - 1;
		long currentPage = paginater.getCurrentPage();
		StringBuffer buffer = new StringBuffer();

		for (long i = startPos; i <= endPos && i <= maxPage; i++) {
			if (i == currentPage) {
				buffer.append("<strong>");
				buffer.append(i);
				buffer.append("</strong>");
			} else {
				buffer.append("<a href=\"" + makeTarget(i) + "\">");
				buffer.append(i);
				buffer.append("</a>");
			}
		}
		return buffer.toString();
	}

	private String makeEndHtml(Paginater paginater) {
		StringBuffer buffer = new StringBuffer();
		long maxPage = paginater.getMaxPage();
		long lastPos = getStartPos(paginater) + BUTTON_COUNT - 1;

		if (lastPos < maxPage) {
			buffer.append("<a href=\"" + makeTarget(lastPos + 1) + "\">&gt;</a>");
		}

		if (lastPos < maxPage - 1) {
			buffer.append("<a href=\"" + makeTarget(maxPage) + "\">");
			buffer.append("..." + maxPage);
			buffer.append("</a>");
		}

		// if(maxPage>20){
		// buffer.append("<input class=\"turn\" type=\"text\" ");
		// buffer.append("onkeyup=\"if(event.keyCode==13){alert(this.value);}\"");
		// buffer.append(" />");
		// }

		buffer.append("</div><div style=\"clear:both;\"></div></div>");
		return buffer.toString();
	}

	private long getStartPos(Paginater paginater) {
		long currentPage = paginater.getCurrentPage();
		long maxPage = paginater.getMaxPage();
		long start = (currentPage - CURRENT_POS > 0) ? currentPage - CURRENT_POS : 1;
		long endPos = start + BUTTON_COUNT - 1;
		if (endPos > maxPage) {
			start = maxPage - BUTTON_COUNT + 1;
		}
		return start > 0 ? start : 1;
	}

	private String makeTarget(long currentPage) {
		return this.href.replaceFirst("#", Long.toString(currentPage));
	}

	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return super.doStartTag();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

}
