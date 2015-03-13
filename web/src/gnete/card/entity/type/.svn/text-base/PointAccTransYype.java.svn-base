package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 积分充值及账户变更业务类型
 * 
 * @author aps-lib
 */
public class PointAccTransYype extends AbstractType{

public static final Map ALL = new HashMap();
	
	public static final PointAccTransYype POINT_PRESENT = new PointAccTransYype("积分赠送", "01");
	public static final PointAccTransYype CARD_STATUS_CHG = new PointAccTransYype("卡状态异常", "02");
	public static final PointAccTransYype CARD_ID_CHG = new PointAccTransYype("卡号变更", "03");
	public static final PointAccTransYype BRAND_CHG = new PointAccTransYype("品牌转换", "04");
	
	protected PointAccTransYype(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static PointAccTransYype valueOf(String value) {
		PointAccTransYype type = (PointAccTransYype) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(PointAccTransYype.ALL);
	}
}
