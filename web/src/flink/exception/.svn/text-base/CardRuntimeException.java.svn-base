package flink.exception;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 *  <li>抽象运行时异常</li>
  * @Project: CardService
  * @File: CardRuntimeException.java
  * @See:
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-8-21
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
public abstract class CardRuntimeException extends NestableRuntimeException {
	private static final long serialVersionUID = 1L;

	private String code;
	
	public CardRuntimeException(){
		
	}	

	public CardRuntimeException(String message) {
		super(message);
	}

	public CardRuntimeException(String code, String message) {
		super(message);
		this.code = code;
	}

	public CardRuntimeException(Throwable cause) {
		super(cause);
	}

	public CardRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CardRuntimeException(String code, String message,Throwable cause) {
		super(message,cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
