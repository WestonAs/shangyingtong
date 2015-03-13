package flink.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @author haichen.ma
 *
 */
public class SymbolTrans extends SimpleTagSupport {
	private static final long serialVersionUID = 1L;
	private String symbol;
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void doTag() throws JspException, IOException {
		String s = null;
		
		if ("Y".equals(symbol) || "YES".equalsIgnoreCase(symbol)) {
			s = "是";
		}
		else if ("N".equals(symbol) || "NO".equalsIgnoreCase(symbol)) {
			s = "否";
		}
		else {
			s = symbol;
		}
		
		this.getJspContext().getOut().write(s);
	}
}
