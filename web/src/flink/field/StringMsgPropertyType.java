package flink.field;

import flink.util.CommonHelper;
/**
 * 
  * @Project: Card
  * @File: StringMsgPropertyType.java
  * @See:
  * @description：
  *   <li>描述文件中类似string的类型---这里用JAVA中的String来替代(当前如果是NULL则以""来替代)</li>
  * @author: aps-zbw
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-9-2
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public class StringMsgPropertyType extends AbstractMsgPropertyType<String> {    		
	private static final long serialVersionUID = 1838147837220538688L;
	
	public static final StringMsgPropertyType INSTANCE = new StringMsgPropertyType();

	public StringMsgPropertyType() {
		super(String.class);
	}

	protected StringMsgPropertyType(Class<String> type) {
		super(type);		
	}

	public String toString(String value) {
		return CommonHelper.safeTrim(value);
	}

	public String fromString(String string) {
		return string;
	}

	public String[] getDefaultMatchTypeString() {	
		return new String[] {DataFieldType.STRING_DATATYPE.getFieldType(),String.class.getName()};
	}

	
	public boolean checkInArrange(int[] arrange, String current) {
		if(arrange.length == 0) {
			return true;
		}
		int strLn = current.length();		
		return (strLn >= arrange[0]) && (strLn <= arrange[arrange.length - 1]);
	}

}
