package gnete.etc;

/**
 * 业务异常
 */
public class BizException extends Exception {
	private static final long serialVersionUID = 1;
	private String _code = "9999";
	public BizException(Throwable cause) {
		super(cause);
	}
	public BizException(String msg, Throwable cause) {
		super(msg, cause);
	}
	public BizException(String msg) {
		super(msg);
	}
	public BizException(String code, String msg) {
		super(msg);
		_code = code;
	}
	public String getCode() {
		return _code;
	}
	protected BizException() {}
	
}
