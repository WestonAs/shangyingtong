package flink.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author haichen.ma
 * 
 */
public abstract class ReflectUtil {
	private static final Log logger = LogFactory.getLog(ReflectUtil.class);

	public static void setFieldValue(Object target, String fieldName, 
			Class fieldType, Object value)  {
		if (target == null
				|| fieldName == null || "".equals(fieldName.trim())
				|| fieldType == null
				|| (value != null && !fieldType.isAssignableFrom(value.getClass()))) {
			throw new IllegalArgumentException("argument illegal.");
		}
		
		Class targetClass = target.getClass();
		
		try {
			Method method = targetClass.getDeclaredMethod("set"
	                    + Character.toUpperCase(fieldName.charAt(0))
	                    + fieldName.substring(1), new Class[]{fieldType});
			
			if (!Modifier.isPublic(method.getModifiers())) {
				method.setAccessible(true);
			}
			
			method.invoke(target, new Object[]{value});
		} 
		catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			
			try {
				Field field = targetClass.getDeclaredField(fieldName);
				
				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
				}
				
				field.set(target, value);
			} 
			catch (Exception e1) {
				if (logger.isDebugEnabled()) {
					logger.debug(e1);
				}
			}
		}
	}
	
	public static Object getFieldValue(Object target, String fieldName) {
		try {
			Class targetClass = target.getClass();
			Field field = targetClass.getDeclaredField(fieldName);
			
			if (!Modifier.isPublic(field.getModifiers())) {
				field.setAccessible(true);
			}
			
			return field.get(target);
		} 
		catch (Exception e1) {
			if (logger.isDebugEnabled()) {
				logger.debug(e1);
			}
		}
		
		return null;
	}
	
	public static String getDomainName(Object obj){
		return obj.getClass().getSimpleName();
	}
}
