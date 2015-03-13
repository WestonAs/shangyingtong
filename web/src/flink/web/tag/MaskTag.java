package flink.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * <li>将字符串后4位之前的4位替换为“*”</li>
 * @Project: portal
 * @File: MaskTag.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-6-7 下午06:09:32
 */
public class MaskTag extends SimpleTagSupport {
	
	/** 替换成的字符 */
	private static final char MASK_CHAR = '*';
	
	/** 替换的字符数 */
	private static final int DEFAULT_MASK_LENGTH = 4;
	
	/** 最后保留的字符位数 */
	private static final int DEFAULT_LAST_REMAIN_LENGTH = 4;
	
	private String value;
	
	/** 需替换的长度 */
	private String maskLength;
	
	/** 需保留字符串的最后的位数 */
	private String remainLength;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getMaskLength() {
		return maskLength;
	}

	public void setMaskLength(String maskLength) {
		this.maskLength = maskLength;
	}

	public String getRemainLength() {
		return remainLength;
	}

	public void setRemainLength(String remainLength) {
		this.remainLength = remainLength;
	}
	
	public void doTag() throws JspException, IOException {
		if (StringUtils.isEmpty(value)) {
			return;
		}
		
		int maskLen = 0;
		int remainLen = 0;
		
		if (StringUtils.isNotEmpty(maskLength) && NumberUtils.isDigits(maskLength)) {
			maskLen = NumberUtils.toInt(maskLength);
		} else {
			maskLen = DEFAULT_MASK_LENGTH;
		}

		if (StringUtils.isNotEmpty(remainLength) && NumberUtils.isDigits(remainLength)) {
			remainLen = NumberUtils.toInt(remainLength);
		} else {
			remainLen = DEFAULT_LAST_REMAIN_LENGTH;
		}
		
		try {
			String maskStr = mask(value, MASK_CHAR, maskLen, remainLen);
			String html = ObjectUtils.toString(maskStr);
			this.getJspContext().getOut().write(html);
		} catch (Exception e) {
			// do nothing.
		}
	}
	
	/**
	 * @param s 要替换的字符串
	 * @param maskChar 替换后的字符
	 * @param maskLen 替换字符串的长度
	 * @param remainLen 保留的最后的位数
	 * @return
	 */
	private static String mask(String s, char maskChar, int maskLen, int remainLen) {
		if (StringUtils.isEmpty(s)) {
			return s;
		}
		
		int beginIndex = s.length() - maskLen - remainLen;
		
		int endIndex = s.length() - remainLen;
		
		if (beginIndex < 0 || endIndex > s.length() || beginIndex > endIndex) {
			return s;
		}
		
		String maskString = StringUtils.leftPad(StringUtils.EMPTY, endIndex - beginIndex, maskChar);
		
		return new StringBuffer(s).replace(beginIndex, endIndex, maskString).toString();
	}
	
	public static void main(String[] args) {
		System.out.println(mask("12345678901111", '*', 8, 2));
	}
}
