package flink.field;

/**
 * <p>描述文件中类似int的类型---这里用JAVA中的Integer来替代</p>
 * <p>注意Integer的取值不能超过Integer的上限</p>
 * @Project: Card
 * @File: IntegerMsgPropertyType.java
 * @See: 
 * @author: aps-zbw
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-9-2
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public class IntegerMsgPropertyType extends AbstractMsgPropertyType<Integer> {

	private static final long serialVersionUID = -2769010597890325957L;

	public static final IntegerMsgPropertyType INSTANCE = new IntegerMsgPropertyType();

	public IntegerMsgPropertyType() {
		super(Integer.class);
	}

	protected IntegerMsgPropertyType(Class<Integer> type) {
		super(type);
	}

	public String toString(Integer value) {
		return value == null ? null : value.toString();
	}

	public Integer fromString(String string) {
		return string == null ? null : Integer.valueOf(string);
	}

	public String[] getDefaultMatchTypeString() {		
		return new String[] {DataFieldType.INTEGER_DATATYPE.getFieldType(),Integer.class.getName()};
	}

	
	public boolean checkInArrange(int[] arrange, Integer current) {
		if(arrange.length == 0) {
			return true;
		}
		
		Integer imin = Integer.valueOf(Double.valueOf(super.getDefaultArrangePower(arrange[0])).intValue());
		
		Integer imax = Integer.valueOf(Double.valueOf(super.getDefaultArrangePower(arrange[arrange.length - 1])).intValue());
		
		if(imin.compareTo(Integer.MIN_VALUE) > 0 && imax.compareTo(Integer.MAX_VALUE) < 0) {
			return super.checkInArrange(imin, imax, current);
		}
		
		return false;
	}

}
