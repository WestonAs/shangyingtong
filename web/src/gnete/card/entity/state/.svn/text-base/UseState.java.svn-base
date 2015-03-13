package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 证书使用状态
 * 
 * @author aps-lih
 */
public class UseState extends AbstractState {
	public static final Map ALL = new HashMap();

	/*
	 * 00：未使用
	   01：已使用
	 */
	public static final UseState UNUSE = new UseState("未使用", "00");
	public static final UseState USED = new UseState("已使用", "01");	
	
	@SuppressWarnings("unchecked")
	protected UseState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static UseState valueOf(String value) {
		UseState type = (UseState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("证书使用状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(UseState.ALL);
	}
}
