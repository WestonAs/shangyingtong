package flink.field;

import java.math.BigDecimal;
import flink.util.CommonHelper;

/**
 * <p>数值精度类型数据处理</p>
 * @Project: Card
 * @File: BigDecimalMsgPropertyType.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-16
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class BigDecimalMsgPropertyType extends AbstractMsgPropertyType<BigDecimal> {
	private static final long serialVersionUID = -5664277729755387707L;

	public static final BigDecimalMsgPropertyType INSTANCE = new BigDecimalMsgPropertyType();

	public BigDecimalMsgPropertyType() {
		super(BigDecimal.class);
	}

	protected BigDecimalMsgPropertyType(Class<BigDecimal> type) {
		super(type);
	}

	public boolean checkInArrange(int[] arrange, BigDecimal current) {
		if(arrange.length == 0) {
			return true;
		}
		
		BigDecimal bmin = new BigDecimal(Double.valueOf(getDefaultArrangePower(arrange[0])).doubleValue());
		BigDecimal bmax = new BigDecimal(Double.valueOf(getDefaultArrangePower(arrange[arrange.length - 1])).doubleValue());
		
		return super.checkInArrange(bmin, bmax, current);
	}

	public BigDecimal fromString(String string) {
		return CommonHelper.getDecimalFromStr(string);
	}

	public String[] getDefaultMatchTypeString() {
		return new String[] { DataFieldType.BIGDECIMAL_DATATYPE.getFieldType(), BigDecimal.class.getName() };
	}

	public String toString(BigDecimal value) {
		return CommonHelper.getCommonFormatAmt(value);
	}

}
