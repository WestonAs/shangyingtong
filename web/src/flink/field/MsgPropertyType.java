package flink.field;

import java.util.Comparator;

/**
 * <p>描述字段属性的数据类型</p>
 * <ul> 
 * <li>1. 跟文件描述的定义保持一致,比如smallint int string</li>
 * <li>2. 注意这个将作为将字符串文本解析出来的转换依据,工程将根据文件涵盖的类型范围来增加支持</li>
 * <li>3. 参考了Hibernate中type类型的设计</li>
 * </ul>
 * @Project: Card
 * @File: MsgPropertyType.java
 * @See:

 * @author: aps-zbw
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-9-2
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public interface MsgPropertyType<T> extends java.io.Serializable {
	/**
	 * Retrieve the Java type handled here.
	 *
	 * @return The Java type.
	 */
	Class<T> getMsgTypeClass();

	/**
	 * Retrieve the natural comparator for this type.
	 *
	 * @return The natural comparator.
	 */
	Comparator<T> getComparator();

	/**
	 * Extract a proper hash code for this value.
	 *
	 * @param value The value for which to extract a hash code.
	 *
	 * @return The extracted hash code.
	 */
	int extractHashCode(T value);

	/**
	 * Determine if two instances are equal
	 *
	 * @param one One instance
	 * @param another The other instance
	 *
	 * @return True if the two are considered equal; false otherwise.
	 */
	boolean areEqual(T one, T another);

	/**
	 * 
	  * @description：类型转换为类型描述
	  * @param value
	  * @return  
	  * @version: 2010-9-2 上午11:31:42
	  * @See:
	 */
	String toString(T value);

	/**
	 * 
	  * <p>从类型描述转换为JAVA内部的数据类型</p>
	  * @param str
	  * @return  
	  * @version: 2010-9-2 上午11:32:05
	  * @See:
	 */
	T fromString(String string);
	
	
	/**
	 * 
	  * <p>获得缺省匹配的数据类型 比如 smallint --- char 等</p>
	  * @return  
	  * @version: 2010-9-2 下午04:18:41
	  * @See:
	 */
	String[] getDefaultMatchTypeString();
	
	/**
	 * 
	  * <p>检查在取值区间范围</p>
	  * @param min
	  * @param max
	  * @param current
	  * @return  
	  * @version: 2010-9-15 下午07:13:50
	  * @See:
	 */
	boolean checkInArrange(T min, T max, T current);
	
	
	/**
	 * 
	  * <p>根据取值范围来比较</p>
	  * @param arrange
	  * @param current
	  * @return  
	  * @version: 2010-9-15 下午07:31:30
	  * @See:
	 */
	boolean checkInArrange(int[] arrange, T current);
}
