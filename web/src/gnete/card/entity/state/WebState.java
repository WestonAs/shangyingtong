package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 内部命令前台处理状态
 * 
 * @author aps-lih
 */
public class WebState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final WebState UNDEAL = new WebState("未处理", "0");
	public static final WebState DONE = new WebState("已处理", "1");
	public static final WebState DOING = new WebState("处理中", "2");
	
	@SuppressWarnings("unchecked")
	protected WebState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static WebState valueOf(String value) {
		WebState type = (WebState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("内部命令前台处理状态错误！");
		}

		return type;
	}
}
