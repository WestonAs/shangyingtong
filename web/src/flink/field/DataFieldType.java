package flink.field;

/**
 * <p>文件字典数据类型(用于抽象数据类型的枚举定义)</p>
 * @Project: Card
 * @File: DateFieldType.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-14
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public enum DataFieldType {

	BIGINT_DATATYPE("bigint"),

	CHAR_DATATYPE("smallint"),

	DOUBLE_DATATYPE("double"),

	FLOAT_DATATYPE("float"),

	INTEGER_DATATYPE("int"),

	LONG_DATATYPE("long"),

	STRING_DATATYPE("string"),

	TRADEDATE_DATATYPE("tradedate"),

	TRADETIME_DATATYPE("tradetime"),
	
	BIGDECIMAL_DATATYPE("bigDecimal"),
	
	/** 可忽略的数据类型(当前不对应任何处理) */
	IGNORE_DATATYPE("C");

	private String fieldType;

	private DataFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldType() {
		return this.fieldType;
	}
}
