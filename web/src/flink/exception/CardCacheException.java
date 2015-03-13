package flink.exception;

/**
 * 
 * <li>描述缓存异常</li>
 * 
 * @Project: CardService
 * @File: CardCacheException.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-9-24
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class CardCacheException extends CardRuntimeException {
	private static final long serialVersionUID = 4518163228829825737L;

	public CardCacheException(String message) {
		super(message);
	}

	public CardCacheException(String code, String message) {
		super(code, message);
	}

	public CardCacheException(Throwable cause) {
		super(cause);
	}

	public CardCacheException(String message, Throwable cause) {
		super(message, cause);
	}
}
