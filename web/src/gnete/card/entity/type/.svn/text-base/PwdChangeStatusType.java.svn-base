package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class PwdChangeStatusType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	密码修改状态:
		1：初始密码未修改，且不需要修改
		2：初始密码未修改，且必须修改
		9：密码已修改
	*/
	public static final PwdChangeStatusType UNNECESSARY_MODIFY = new PwdChangeStatusType("初始密码未修改,且不需要修改", "1");
	public static final PwdChangeStatusType NECESSARY_MODIFY = new PwdChangeStatusType("初始密码未修改,且必须修改", "2");
	public static final PwdChangeStatusType PWMODIFIED = new PwdChangeStatusType("密码已修改", "9");

	protected PwdChangeStatusType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PwdChangeStatusType valueOf(String value) {
		PwdChangeStatusType type = (PwdChangeStatusType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(PwdChangeStatusType.ALL);
	}
}
