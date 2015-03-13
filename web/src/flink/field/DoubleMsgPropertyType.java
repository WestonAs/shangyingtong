package flink.field;

/**
 * <p>原文件中没有对于双精度类型的数据 但本系统仍然可以提供针对这类型数据的解析</p>
  * @Project: Card
  * @File: DoubleMsgPropertyType.java
  * @See:  
  * @author: aps-zbw
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-9-2
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public class DoubleMsgPropertyType extends AbstractMsgPropertyType<Double>{
    private static final long serialVersionUID = 8556916809501858492L;
	
    public static final DoubleMsgPropertyType INSTANCE = new DoubleMsgPropertyType();
	
	public DoubleMsgPropertyType() {
		super(Double.class);
	}

	protected DoubleMsgPropertyType(Class<Double> type) {
		super(type);		
	}

	public String toString(Double value) {
		return value == null ? null : value.toString();
	}

	public Double fromString(String string) {
		return Double.valueOf( string );
	}

	public String[] getDefaultMatchTypeString() {		
		return new String[] {DataFieldType.DOUBLE_DATATYPE.getFieldType(),Double.class.getName()};
	}

	public boolean checkInArrange(int[] arrange, Double current) {
		if(arrange.length == 0) {
			return true;
		}
		Double dmin = Double.valueOf(getDefaultArrangePower(arrange[0]));
		Double dmax = Double.valueOf(getDefaultArrangePower(arrange[arrange.length - 1]));
		return super.checkInArrange(dmin, dmax, current);
	}

}
