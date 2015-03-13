package gnete.card.entity.type;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PtUsageType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	/*
	积分使用方法:
		1: 永久有效的累计积分(分期数为1个月)，随时使用
		2: 分期积分，上期积分限时使用；本期积分不可使用
		3: 分期积分，随时使用。若干期以前积分失效
		4: 分期积分，永久有效或者若干期以前积分失效， 未失效的旧积分可以分期折扣累计
		5: 保得分期积分，随时使用。每一期为一天，积分有效期为一年
	*/
	public static final PtUsageType FORVEREFFECTIVE = new PtUsageType("永久有效", "1");
	public static final PtUsageType INSTM1 = new PtUsageType("限时分期积分", "2");
	public static final PtUsageType INSTM2 = new PtUsageType("分期积分", "3");
	public static final PtUsageType INSTM3 = new PtUsageType("分期积分折旧积分", "4");
	public static final PtUsageType BAODE_POINT = new PtUsageType("保得分期积分", "5");
	public static final PtUsageType FLOAT_POINT = new PtUsageType("浮动费率积分", "6");
	
	@SuppressWarnings("unchecked")
	protected PtUsageType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PtUsageType valueOf(String value) {
		PtUsageType type = (PtUsageType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(PtUsageType.ALL);
	}

	/**
	 * 获得永久有效、限时分期积分、分期积分、分期积分折旧积分
	 * @return
	 */
	public static List getAllExcBaode(){
		Map params = new HashMap();
		params.put(FORVEREFFECTIVE.getValue(), FORVEREFFECTIVE);
		params.put(INSTM1.getValue(), INSTM1);
		params.put(INSTM2.getValue(), INSTM2);
		params.put(INSTM3.getValue(), INSTM3);
		return getOrderedList(params);
	}
	
	
}
