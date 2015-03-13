package flink.field;

/**
  *  <p>描述文件中类似SEQ(序列号)或者长整型的类型---这里用JAVA中的Long来替代</p>
  * @Project: PfCheck
  * @File: LongMsgPropertyType.java
  * @See: 
  * @author: aps-zbw
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-9-2
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public class LongMsgPropertyType extends AbstractMsgPropertyType<Long> {
	private static final long serialVersionUID = 6253280676902876620L;

	public static final LongMsgPropertyType INSTANCE = new LongMsgPropertyType();

	public LongMsgPropertyType() {
		super(Long.class);
	}

	protected LongMsgPropertyType(Class<Long> type) {
		super(type);
	}

	public String toString(Long value) {
		return value == null ? null : value.toString();
	}

	public Long fromString(String string) {
		return Long.valueOf(string);
	}

	public String[] getDefaultMatchTypeString() {		
		return new String[] {DataFieldType.LONG_DATATYPE.getFieldType(),Long.class.getName()};
	}

	public boolean checkInArrange(int[] arrange, Long current) {
		if(arrange.length == 0) {
			return true;
		}
		
		Long lmin = Long.valueOf(Double.valueOf(super.getDefaultArrangePower(arrange[0])).longValue());
		Long lmax = Long.valueOf(Double.valueOf(super.getDefaultArrangePower(arrange[arrange.length - 1])).longValue());		
		return super.checkInArrange(lmin, lmax, current);
	}

}
