package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-lih
 */
public class UserLogType extends AbstractType {
	public static final Map ALL = new HashMap();

	
	/*
		S:查询
		A:新增
		U:修改
		D:删除
		C:审核
		O:其他
	*/
	
	public static final UserLogType SEARCH = new UserLogType("查询", "S");
	public static final UserLogType ADD = new UserLogType("新增", "A");
	public static final UserLogType UPDATE = new UserLogType("修改", "U");
	public static final UserLogType DELETE = new UserLogType("删除", "D");
	public static final UserLogType CHECK = new UserLogType("审核", "C");
	public static final UserLogType OTHER = new UserLogType("其他", "O");
	
	@SuppressWarnings("unchecked")
	protected UserLogType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static UserLogType valueOf(String value) {
		UserLogType type = (UserLogType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的日志类型" + value);
		}

		return type;
	}
	
	public static List<UserLogType> getAll() {
		return getOrderedList(UserLogType.ALL);
	}
	
	public static void main(String[] args) {
		System.out.println(UserLogType.valueOf("01").getName());
	}
	
}
