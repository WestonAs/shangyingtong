package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: RiskType.java
 *
 * @description: 风控类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-1-10
 */
public class MonitorRiskType extends AbstractType {

	public static final Map ALL = new HashMap();
	
	/**
	 * 风控类型字段
	    01：单笔充值限额 
		02：账户余额
		11: 单笔消费限额
		12：当日累计消费次数
		13：当日累计消费金额
		
		A1:大额交易
		B1:密码错
		B2:非法卡
		B3:过期卡
		C1:无磁交易
		预警顺序：
		C1>A1>其他
	 */
	public static final MonitorRiskType SINGLE_RECHARGE = new MonitorRiskType("单笔充值限额超过上限","01");
	public static final MonitorRiskType ACCT_BALANCE = new MonitorRiskType("账户余额超过上限", "02");
	public static final MonitorRiskType SINGLE_CONSUME = new MonitorRiskType("单笔消费限额超过上限", "11");
	public static final MonitorRiskType CUR_TOTAL_CONSUME_TIME = new MonitorRiskType("当日累计消费次数超过上限", "12");
	public static final MonitorRiskType CUR_TOTAL_CONSUME_AMT = new MonitorRiskType("当日累计消费金额超过上限", "13");
	
//	public static final MonitorRiskType LARGE_TRADE = new MonitorRiskType("大额交易","A1");
	public static final MonitorRiskType ERROR_MAC = new MonitorRiskType("MAC错误","B0");
	public static final MonitorRiskType ERROR_PASSWORD = new MonitorRiskType("密码错","B1");
	public static final MonitorRiskType ILLEGAL_CARD = new MonitorRiskType("非法卡","B2");
	public static final MonitorRiskType EXPIRED_CARD = new MonitorRiskType("过期卡","B3");
	public static final MonitorRiskType NONMAGNETIC_TRADE = new MonitorRiskType("无磁交易","C1");
	
	protected MonitorRiskType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static MonitorRiskType valueOf(String value) {
		MonitorRiskType type = (MonitorRiskType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(MonitorRiskType.ALL);
	}

}
