package flink.file;

/**
 * @File: XLSException.java
 * @description: 导入Excel2003文件异常
 * @author slt02
 */
public class ExcelImportException extends Exception {
	private static final long serialVersionUID = 1L;

	private String code;

	public ExcelImportException(String message) {
		super(message);
	}

	public ExcelImportException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ExcelImportException(Throwable cause) {
		super(cause);
	}

	public ExcelImportException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getCode() {
		return code;
	}
}
