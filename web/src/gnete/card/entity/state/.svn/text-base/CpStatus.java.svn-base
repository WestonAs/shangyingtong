package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 代收付状态
 * @author aps-lib
 *
 */
public class CpStatus extends AbstractState {

	public static final Map ALL = new HashMap();

	/*
	 * 00未处理
	 * 01文件已产生
	 * 02文件已发送
	 * 03成功
	 * 04手工成功
	 * 05失败
	 * 06手工失败
	 */
	public static final CpStatus UNPAID = new CpStatus("未处理", "00");
	public static final CpStatus FILE_GENERATED = new CpStatus("文件已产生", "01");	
	public static final CpStatus FILE_SENT = new CpStatus("文件已发送", "02");	
	public static final CpStatus SUCCESS = new CpStatus("成功", "03");	
	public static final CpStatus SUCCESS_MANUAL = new CpStatus("手工成功", "04");	
	public static final CpStatus FAILURE = new CpStatus("失败", "05");	
	public static final CpStatus FAILURE_MANUAL = new CpStatus("手工失败", "06");	
	
	protected CpStatus(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CpStatus valueOf(String value) {
		CpStatus type = (CpStatus) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}
	
	public static List getList() {
		return getValueOrderedList(ALL);
	}

}
