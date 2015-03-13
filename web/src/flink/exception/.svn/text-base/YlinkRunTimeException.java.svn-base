package flink.exception;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 *  <p>缺省抽象运行时异常类(继承与NestableXX)</p>
  * @Project: Card
  * @File: YlinkRunTimeException.java
  * @See:
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-8
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public abstract class YlinkRunTimeException extends NestableRuntimeException {

	private static final long serialVersionUID = 1L;

	private String code;

	public YlinkRunTimeException(String message) {
		super(message);
	}

	public YlinkRunTimeException(String code, String message) {
		super(message);
		this.code = code;
	}

	public YlinkRunTimeException(Throwable cause) {
		super(cause);
	}

	public YlinkRunTimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getCode() {
		return code;
	}
}
