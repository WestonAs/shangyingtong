package gnete.etc;

/**
 * 业务异常
 */
public class RuntimeBizException extends RuntimeException {
	private static final long serialVersionUID = 1;
	private String _code = "9999";
	public RuntimeBizException(Throwable cause) {
		super(cause);
	}
	public RuntimeBizException(String msg) {
		super(msg);
	}
	public RuntimeBizException(String code, String msg) {
		super(msg);
		_code = code;
	}
	public String getCode() {
		return _code;
	}
	protected RuntimeBizException() {}
	
}
