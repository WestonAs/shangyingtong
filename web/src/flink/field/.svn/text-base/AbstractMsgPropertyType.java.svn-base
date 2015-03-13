package flink.field;

import java.util.Comparator;

import flink.util.ComparableComparator;
import flink.util.EqualsHelper;


/**
  * <p>抽象数据类型接口实现</p>
  * @Project: Card
  * @File: AbstractMsgPropertyType.java
  * @See:
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-6-16
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
public abstract class AbstractMsgPropertyType<T> implements MsgPropertyType<T> {
	private static final long serialVersionUID = 1L;

	private final Class<T> type;

	private final Comparator<T> comparator;
	
	protected static final double DEFAULT_POWER_BASIS = 10.0;
	
	@SuppressWarnings("unchecked")
	protected AbstractMsgPropertyType(Class<T> type) {
		this.type = type;
		this.comparator = Comparable.class.isAssignableFrom(type) ? (Comparator<T>) ComparableComparator.INSTANCE : null;
	}

	public Class<T> getMsgTypeClass() {
		return type;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}
	
	public boolean checkInArrange(T min, T max, T current) {
		return (comparator.compare(min, current) <= 0) ? (comparator.compare(max, current) >= 0) : false;
	}

	public boolean areEqual(T one, T another) {
		return EqualsHelper.equals(one, another);
	}

	public int extractHashCode(T value) {
		return value.hashCode();
	}
	
	protected final double getDefaultArrangePower(int power) {
		return Math.pow(DEFAULT_POWER_BASIS, power);
	}

}
