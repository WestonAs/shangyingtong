package flink.exception;

/**
 * @File: XLSException.java
 *
 * @description: 生成Excel2003文件异常
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-24
 */
public class ExcelExportException extends Exception {
	private static final long serialVersionUID = 1L;

	private String code;

	public ExcelExportException(String message) {
		super(message);
	}

	public ExcelExportException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ExcelExportException(Throwable cause) {
		super(cause);
	}

	public ExcelExportException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getCode() {
		return code;
	}
}
