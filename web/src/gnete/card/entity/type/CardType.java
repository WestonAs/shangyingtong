package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 卡类型（卡种）
 */
@SuppressWarnings("unchecked")
public class CardType extends AbstractType {

	public static final Map ALL = new HashMap();
	/** 储值卡 */
	public static final CardType DEPOSIT = new CardType("储值卡", "01");

	// public static final CardType SIGN = new CardType("签单卡","02");

	/** 折扣卡 */
	public static final CardType DISCNT = new CardType("折扣卡", "03");

	/** 会员卡 */
	public static final CardType MEMB = new CardType("会员卡", "04");

	/** 赠券卡 */
	public static final CardType COUPON = new CardType("赠券卡", "05");

	/** 次卡 */
	public static final CardType ACCU = new CardType("次卡", "06");

	/** 积分 */
	public static final CardType POINT = new CardType("积分", "07");

	/** 虚拟账户 */
	public static final CardType VIRTUAL = new CardType("虚拟账户", "10");

	@SuppressWarnings("unchecked")
	protected CardType(String name, String value) {

		super(name, value);
		ALL.put(value, this);
	}

	public static CardType valueOf(String value) {
		CardType cert = (CardType) ALL.get(value);

		if (cert == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return cert;
	}

	public static List getCardTypeWithoutPoint() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(DEPOSIT.getValue(), DEPOSIT);
		map.put(DISCNT.getValue(), DISCNT);
		map.put(ACCU.getValue(), ACCU);
		map.put(POINT.getValue(), POINT);

		return getOrderedList(map);
	}

	public static List getAll() {
		return getOrderedList(CardType.ALL);
	}
}
