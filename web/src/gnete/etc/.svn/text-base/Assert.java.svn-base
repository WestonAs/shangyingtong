package gnete.etc;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Assertion utility.
 * 
 * @author aps-mhc
 *
 */
public abstract class Assert {
	private final static Logger logger = Logger.getLogger(Assert.class);
	
	private static void throwException(BizException e) throws BizException {
//		e.printStackTrace();
		logger.debug(e);
		throw e;
	}
	
	public static void isTrue(boolean expression, String message) throws BizException {
		isTrue(expression, null, message);
	}

	public static void isTrue(boolean expression, String errorCode, String message) throws BizException {
		if (!expression) {
			logger.info(message);
			throwException(new BizException(errorCode, message));
		}
	}
	
	public static void notTrue(boolean expression, String message) throws BizException {
		notTrue(expression, null, message);
	}
	
	public static void notTrue(boolean expression, String errorCode, String message) throws BizException {
		if (expression) {
			logger.debug(message);
			throwException(new BizException(errorCode, message));
		}
	}

	public static void isNull(Object object, String message) throws BizException {
		if (object != null) {
			throwException(new BizException(message));
		}
	}

	public static void notNull(Object object, String message) throws BizException {
		if (object == null) {
			logger.debug(message);
			throwException(new BizException(message));
		}
	}
	
	public static void notNull(Object object, String errorCode, String message) throws BizException {
		if (object == null) {
			logger.debug(message);
			throwException(new BizException(errorCode, message));
		}
	}
	
	public static void isEmpty(String data, String message) throws BizException {
		if (!StringUtils.isEmpty(data)) {
			logger.debug(message);
			throwException(new BizException(message));
		}
	}
	
	public static void notEmpty(String data, String message) throws BizException {
		if (StringUtils.isEmpty(data)) {
			logger.debug(message);
			throwException(new BizException(message));
		}
	}
	
	public static void notBlank(String data, String message) throws BizException {
		if (StringUtils.isBlank(data)) {
			logger.debug(message);
			throwException(new BizException(message));
		}
	}
	
	public static void isEmpty(Collection data, String message) throws BizException {
		if (!CollectionUtils.isEmpty(data)) {
			logger.debug(message);
			throwException(new BizException(message));
		}
	}
	
	public static void notEmpty(Collection data, String message) throws BizException {
		if (CollectionUtils.isEmpty(data)) {
			logger.debug(message);
			throwException(new BizException(message));
		}
	}
	
	public static void notEmpty(Collection data, String errorCode, String message) throws BizException {
		if (CollectionUtils.isEmpty(data)) {
			logger.debug(message);
			throwException(new BizException(errorCode, message));
		}
	}
	
	public static void notEmpty(Object[] array, String message) throws BizException {
		if(ArrayUtils.isEmpty(array)) {
			throwException(new BizException(message));
		}
	}
	
	public static void notEmpty(Object[] array, int size, String message) throws BizException {
		if(ArrayUtils.isEmpty(array) || (array.length != size)) {
			throwException(new BizException(message));
		}
	}
	
	public static void notEmpty(Object[] array, String errorCode, String message) throws BizException {
		if(ArrayUtils.isEmpty(array)) {
			throwException(new BizException(errorCode, message));
		}
	}

	public static void equals(String s1, String s2, String message) throws BizException {
		if (!StringUtils.equals(s1, s2)) {
			logger.debug(message);
			throwException(new BizException(message));
		}
	}
	
	public static void notEquals(String s1, String s2, String message) throws BizException {
		if (StringUtils.equals(s1, s2)) {
			logger.debug(message);
			throwException(new BizException(message));
		}
	}
}
