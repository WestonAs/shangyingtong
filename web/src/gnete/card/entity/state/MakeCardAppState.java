package gnete.card.entity.state;

import java.util.LinkedHashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @description: 制卡申请状态
 */
public class MakeCardAppState extends AbstractState {
	public static final Map ALL = new LinkedHashMap();

	public static final MakeCardAppState CREATE = new MakeCardAppState("新建", "01");
	public static final MakeCardAppState CANCEL = new MakeCardAppState("撤销", "02");	
	public static final MakeCardAppState WAITED_CHECK = new MakeCardAppState("提交审核", "03");
	public static final MakeCardAppState CHECK_FAILED = new MakeCardAppState("审核失败", "04");
	public static final MakeCardAppState CHECK_PASSED = new MakeCardAppState("审核通过", "05");
	public static final MakeCardAppState CREATE_SUCCESS = new MakeCardAppState("建档成功", "06");
	public static final MakeCardAppState CREATE_FAILURE = new MakeCardAppState("建档失败", "07");
	public static final MakeCardAppState CREATE_CANCEL= new MakeCardAppState("建档撤销", "08");
	public static final MakeCardAppState CREATE_CANCEL_FAILURE = new MakeCardAppState("建档撤销失败", "12");
	public static final MakeCardAppState ALREADY_LOAD = new MakeCardAppState("制卡文本已下载", "09");
	public static final MakeCardAppState MAKECARD_COMPLETED = new MakeCardAppState("制卡完成", "10");
	public static final MakeCardAppState HAS_STORAGE = new MakeCardAppState("已入库", "11");
	
	@SuppressWarnings("unchecked")
	protected MakeCardAppState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static MakeCardAppState valueOf(String value) {
		MakeCardAppState type = (MakeCardAppState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
}
