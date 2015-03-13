package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 系统日志查看状态
 * 
 * @author aps-lih
 */
public class SysLogViewState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final SysLogViewState UN_READ = new SysLogViewState("未读", "11");
	public static final SysLogViewState READ = new SysLogViewState("已读", "00");
	
	@SuppressWarnings("unchecked")
	protected SysLogViewState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SysLogViewState valueOf(String value) {
		SysLogViewState type = (SysLogViewState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("系统日志查看状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(SysLogViewState.ALL);
	}
}
