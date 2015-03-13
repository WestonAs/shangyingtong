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
public class WsBankVerBindingRegState extends AbstractState {
	public static final Map<String, WsBankVerBindingRegState> ALL = new LinkedHashMap<String, WsBankVerBindingRegState>();

	public static final WsBankVerBindingRegState PRE_PROCESS = new WsBankVerBindingRegState("待处理", "10");
	public static final WsBankVerBindingRegState PROCESSING = new WsBankVerBindingRegState("处理中", "20");
	public static final WsBankVerBindingRegState PRE_RETURN = new WsBankVerBindingRegState("待返回", "30");
	public static final WsBankVerBindingRegState FAIL = new WsBankVerBindingRegState("处理失败", "90");
	public static final WsBankVerBindingRegState SUCCESS = new WsBankVerBindingRegState("处理成功", "00");
	
	protected WsBankVerBindingRegState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static WsBankVerBindingRegState valueOf(String value) {
		WsBankVerBindingRegState type = (WsBankVerBindingRegState) ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("状态错误！"); 
		}
		return type;
	}
	
	public static List<WsBankVerBindingRegState> getAll() {
		return new ArrayList<WsBankVerBindingRegState>(WsBankVerBindingRegState.ALL.values());
	}
}
