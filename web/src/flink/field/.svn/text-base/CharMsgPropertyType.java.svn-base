package flink.field;

import gnete.etc.RuntimeBizException;

/**
 * <p>描述类型文件中smallint的类型即取一位长度的值 ,对应JAVA或者SQL中CHAR的类型</p> 
 * <p>注意如果取值超过了1个长度则会抛出运行时的异常</p>
 * @Project: Card
 * @File: CharMsgPropertyType.java
 * @See: 
 * @author: aps-zbw
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-9-2
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public class CharMsgPropertyType extends AbstractMsgPropertyType<Character> {
	private static final long serialVersionUID = -8133043714344088752L;
	
	public static final CharMsgPropertyType INSTANCE = new CharMsgPropertyType();
	
	public CharMsgPropertyType() {
		super(Character.class);
	}

	protected CharMsgPropertyType(Class<Character> type) {
		super(type);
	}

	public String toString(Character value) {
		return value.toString();
	}

	public Character fromString(String string) {
		if (string.length() != 1) {
			throw new RuntimeBizException("errors.wrong.length","");
		}

		return Character.valueOf(string.charAt(0));
	}

	public String[] getDefaultMatchTypeString() {		
		return new String[] {DataFieldType.CHAR_DATATYPE.getFieldType(),"char",Character.class.getName()};
	}

	public boolean checkInArrange(int[] arrange, Character current) {
		return (current.toString().length() == 1);
	}

}
