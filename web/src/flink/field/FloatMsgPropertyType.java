package flink.field;

/**
 * <p>原文件中没有对于浮点类型的数据 但本系统仍然可以提供针对这类型数据的解析</p>
  * @Project: Card
  * @File: FloatMsgPropertyType.java
  * @See:  
  * @author: aps-zbw
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-9-2
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public class FloatMsgPropertyType extends AbstractMsgPropertyType<Float>{
    private static final long serialVersionUID = 1398904097832803487L;
    
    public static final FloatMsgPropertyType INSTANCE = new FloatMsgPropertyType();
    
    public FloatMsgPropertyType() {
    	super(Float.class);
    }

	protected FloatMsgPropertyType(Class<Float> type) {
		super(type);
	}

	public String toString(Float value) {
		return value == null ? null : value.toString();
	}

	public Float fromString(String string) {
		return Float.valueOf(string);
	}

	public String[] getDefaultMatchTypeString() {		
		return new String[] {DataFieldType.FLOAT_DATATYPE.getFieldType(),Float.class.getName()};
	}

	
	public boolean checkInArrange(int[] arrange, Float current) {
		if(arrange.length == 0) {
			return true;
		}
		Float fmin = Float.valueOf(Double.valueOf(getDefaultArrangePower(arrange[0])).floatValue());
		Float fmax = Float.valueOf(Double.valueOf(getDefaultArrangePower(arrange[arrange.length - 1])).floatValue());
		return super.checkInArrange(fmin, fmax, current);
	}

}
