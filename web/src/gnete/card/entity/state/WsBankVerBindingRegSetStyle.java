package gnete.card.entity.state;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 银行卡绑定/解绑/默认卡 申请登记簿状态
 */
public class WsBankVerBindingRegSetStyle extends AbstractState {
	public static final Map<String, WsBankVerBindingRegSetStyle> ALL = new LinkedHashMap<String, WsBankVerBindingRegSetStyle>();

	public static final WsBankVerBindingRegSetStyle BINDING = new WsBankVerBindingRegSetStyle("绑定", "1");
	public static final WsBankVerBindingRegSetStyle BINDING_DEFAULT = new WsBankVerBindingRegSetStyle("绑定并设置默认", "2");
	public static final WsBankVerBindingRegSetStyle DEFAULT = new WsBankVerBindingRegSetStyle("设置默认", "3");
	public static final WsBankVerBindingRegSetStyle UNBINGDING = new WsBankVerBindingRegSetStyle("解绑", "0");
	public static final WsBankVerBindingRegSetStyle REBINGDING = new WsBankVerBindingRegSetStyle("重绑", "4");
	
	protected WsBankVerBindingRegSetStyle(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static WsBankVerBindingRegSetStyle valueOf(String value) {
		WsBankVerBindingRegSetStyle type = (WsBankVerBindingRegSetStyle) ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("状态错误！"); 
		}
		return type;
	}
	
	public static List<WsBankVerBindingRegSetStyle> getAll() {
		return new ArrayList<WsBankVerBindingRegSetStyle>(WsBankVerBindingRegSetStyle.ALL.values());
	}
}
