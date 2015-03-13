package flink.security;

import flink.exception.YlinkRunTimeException;

/**
 *  <p>描述证书签名运行时异常</p>
  * @Project: MyCard
  * @File: SignatureException.java
  * @See:
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-9
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public class SignatureException extends YlinkRunTimeException{
	private static final long serialVersionUID = 6994255735981372824L;

	public SignatureException(String message) {
		super(message);
	}

	public SignatureException(Throwable cause) {
		super(cause);
	}

	public SignatureException(String message, Throwable cause) {
		super(message, cause);
	}

}
