package gnete.card.entity.state;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: ExternalCardImportState.java
 * 
 * @description: 外部卡导入/外部号码开卡 登记簿状态
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-4-12
 */
public class ExternalCardImportState extends AbstractState {
	public static final Map						ALL				= new LinkedHashMap();

	/** 待审核 */
	public static final ExternalCardImportState	WAIT_CHECK		= new ExternalCardImportState("待审核", "40");
	/** 审核不通过 */
	public static final ExternalCardImportState	UNPASS			= new ExternalCardImportState("审核不通过", "50");
	/** 待处理 */
	public static final ExternalCardImportState	WAIT_DEAL		= new ExternalCardImportState("待处理", "00");
	/** 处理成功 */
	public static final ExternalCardImportState	DEAL_SUCCESS	= new ExternalCardImportState("处理成功", "10");
	/** 处理失败 */
	public static final ExternalCardImportState	DEAL_FAILED		= new ExternalCardImportState("处理失败", "20");
	/** 重新待处理 */
	public static final ExternalCardImportState	RE_WAIT_DEAL	= new ExternalCardImportState("重新待处理", "30");

	@SuppressWarnings("unchecked")
	protected ExternalCardImportState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static ExternalCardImportState valueOf(String value) {
		ExternalCardImportState type = (ExternalCardImportState) ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}
		return type;
	}

	public static List<ExternalCardImportState> getAll() {
		return new ArrayList(ExternalCardImportState.ALL.values());
	}

	/** 获得 外部卡导入 状态集合 */
	public static List<ExternalCardImportState> getImportRegStates() {
		ArrayList<ExternalCardImportState> list = new ArrayList<ExternalCardImportState>();
		list.add(ExternalCardImportState.WAIT_DEAL);
		list.add(ExternalCardImportState.DEAL_SUCCESS);
		list.add(ExternalCardImportState.DEAL_FAILED);
		list.add(ExternalCardImportState.RE_WAIT_DEAL);
		return list;
	}
	/** 获得 外部号码开卡 状态集合 */
	public static List<ExternalCardImportState> getMakeCardRegStates() {
		ArrayList<ExternalCardImportState> list = new ArrayList<ExternalCardImportState>();
		list.add(ExternalCardImportState.WAIT_CHECK);
		list.add(ExternalCardImportState.UNPASS);
		list.add(ExternalCardImportState.WAIT_DEAL);
		list.add(ExternalCardImportState.DEAL_SUCCESS);
		list.add(ExternalCardImportState.DEAL_FAILED);
		return list;
	}
}
