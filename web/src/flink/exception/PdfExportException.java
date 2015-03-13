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
/**
 * @File: PdfExportException.java
 *
 * @description: 生成PDF文件异常
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-3-1
 */
public class PdfExportException extends Exception {
	private static final long serialVersionUID = 1L;

	private String code;

	public PdfExportException(String message) {
		super(message);
	}

	public PdfExportException(String code, String message) {
		super(message);
		this.code = code;
	}

	public PdfExportException(Throwable cause) {
		super(cause);
	}

	public PdfExportException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getCode() {
		return code;
	}
}
