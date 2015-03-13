package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 退卡销户类型
 * @author lib
 *
 */
public class CancelCardFlagType extends AbstractType{

	public static final Map ALL = new HashMap();

	public static final CancelCardFlagType CANCEL_CARD = new CancelCardFlagType("退卡", "00");
	public static final CancelCardFlagType CANCEL_ACCT = new CancelCardFlagType("销户", "01");
	
	protected CancelCardFlagType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CancelCardFlagType valueOf(String value) {
		CancelCardFlagType type = (CancelCardFlagType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CancelCardFlagType.ALL);
	}
	
	/**
	 * 得到退卡类型
	 * @return
	 */
	public static List getCancelCard(){
		Map params = new HashMap();
		params.put(CANCEL_CARD.getValue(),CANCEL_CARD);
		return getOrderedList(params);
	}

}
