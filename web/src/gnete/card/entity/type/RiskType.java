package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class RiskType extends AbstractType{

	public static final Map ALL = new HashMap();
	/**
	 * 风控类型
		01：单笔充值限额 
		02：账户余额
		11: 单笔消费限额
		12：当日累计消费次数
		13：当日累计消费金额
	 */
	public static final RiskType SINGLE_RECHARGE = new RiskType("单笔充值限额","01");
	public static final RiskType ACCT_BALANCE = new RiskType("账户余额", "02");
	public static final RiskType SINGLE_CONSUME = new RiskType("单笔消费限额", "11");
	public static final RiskType CUR_TOTAL_CONSUME_TIME = new RiskType("当日累计消费次数", "12");
	public static final RiskType CUR_TOTAL_CONSUME_AMT = new RiskType("当日累计消费金额", "13");
	
	protected RiskType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static RiskType valueOf(String value) {
		RiskType type = (RiskType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(RiskType.ALL);
	}


}
