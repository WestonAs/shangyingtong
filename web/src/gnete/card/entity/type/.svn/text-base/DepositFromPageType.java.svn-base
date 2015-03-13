package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class DepositFromPageType extends AbstractType {

	
	public static final Map ALL = new HashMap();
	
	/**
	 * 充值来源页面
	 * 0:单张充值,1:签单卡充值,2:批量储值卡充值,3:批量折扣卡充值,4:批量次卡充值,5:批量储值卡预充,6:批量折扣卡预充,7:批量次卡预充
	 */
	public static final DepositFromPageType SINGLE = new DepositFromPageType("单张充值","0");
	public static final DepositFromPageType SIGN = new DepositFromPageType("签单卡充值","1");
	public static final DepositFromPageType DEPOSIT = new DepositFromPageType("批量储值卡充值","2");
	public static final DepositFromPageType DISCNT = new DepositFromPageType("批量折扣卡充值","3");
	public static final DepositFromPageType ACCU = new DepositFromPageType("批量次卡充值","4");
	public static final DepositFromPageType PRE_DEPOSIT = new DepositFromPageType("批量储值卡预充","5");
	public static final DepositFromPageType PRE_DISCNT = new DepositFromPageType("批量折扣卡预充","6");
	public static final DepositFromPageType PRE_ACCU = new DepositFromPageType("批量次卡预充","7");
	public static final DepositFromPageType FILE = new DepositFromPageType("文件方式批量充值","8");
	public static final DepositFromPageType PRE_FILE = new DepositFromPageType("文件方式批量预充","9");
	
	@SuppressWarnings("unchecked")
	protected DepositFromPageType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static DepositFromPageType valueOf(String value) {
		DepositFromPageType cert = (DepositFromPageType) ALL.get(value);
		
		if (cert == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return cert;
	}
	
	public static List getAll(){
		return getOrderedList(DepositFromPageType.ALL);
	}
}
