package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @description: 核销结转标志
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-mjn
 * @version: 1.0
 * @since 1.0 2010-8-5
 */
public class VerifyJzFlag extends AbstractType {
	public static final Map ALL = new HashMap();

	public static final VerifyJzFlag YES = new VerifyJzFlag("结转", "0");
	public static final VerifyJzFlag NO = new VerifyJzFlag("不结转", "1");
	
	@SuppressWarnings("unchecked")
	protected VerifyJzFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static VerifyJzFlag valueOf(String value) {
		VerifyJzFlag type = (VerifyJzFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的代码");
		}
		return type;
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
}
