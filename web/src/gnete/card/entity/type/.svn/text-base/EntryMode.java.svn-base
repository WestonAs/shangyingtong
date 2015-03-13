package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class EntryMode extends AbstractType {
	
public static final Map ALL = new HashMap();
	
	/**
	 * 输入方式
	 * 0-只允许刷卡
	 * 1-可以刷卡和手工录入卡号
	 */
	public static final EntryMode CARD_ONLY = new EntryMode("只允许刷卡","0");
	public static final EntryMode CARD_INPUT = new EntryMode("可以刷卡和手工录入卡号","1");
	
	@SuppressWarnings("unchecked")
	protected EntryMode(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static EntryMode valueOf(String value) {
		EntryMode type = (EntryMode) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(EntryMode.ALL);
	}

}
