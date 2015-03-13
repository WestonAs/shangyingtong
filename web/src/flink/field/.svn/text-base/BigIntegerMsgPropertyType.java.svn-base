package flink.field;

import java.math.BigInteger;

/**
  * <p>描述文件中类似bigint(或许潜在的描述)的类型---这里用JAVA中的BigInteger来替代</p>
  * @Project: Card
  * @File: BigIntegerMsgPropertyType.java
  * @See:  
  * @author: aps-zbw
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-9-2
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public class BigIntegerMsgPropertyType extends AbstractMsgPropertyType<BigInteger> {
	private static final long serialVersionUID = -2687547492399089843L;
	
	public static final BigIntegerMsgPropertyType INSTANCE = new BigIntegerMsgPropertyType();
	
	public BigIntegerMsgPropertyType() {
		super(BigInteger.class);
	}

	protected BigIntegerMsgPropertyType(Class<BigInteger> type) {
		super(type);		
	}
	
	@Override
	public int extractHashCode(BigInteger value) {
		return value.intValue();
	}

	@Override
	public boolean areEqual(BigInteger one, BigInteger another) {
		return one == another || ( one != null && another != null && one.compareTo( another ) == 0 );
	}

	public String toString(BigInteger value) {
		return value.toString();
	}

	public BigInteger fromString(String string) {
		return new BigInteger( string );
	}

	public String[] getDefaultMatchTypeString() {		
		return new String[] {DataFieldType.BIGINT_DATATYPE.getFieldType(),BigInteger.class.getName()};
	}

	public boolean checkInArrange(int[] arrange, BigInteger current) {
		if(arrange.length == 0) {
			return true;
		}
		BigInteger bmin = getArrangeCheckValue(arrange[0]);
		BigInteger bmax = getArrangeCheckValue(arrange[arrange.length - 1]);
	    return checkInArrange(bmin,bmax,current);
	}
		
	private BigInteger getArrangeCheckValue(int power) {
		double value = getDefaultArrangePower(power);
		return BigInteger.valueOf(Double.valueOf(value).longValue());
	}

}
