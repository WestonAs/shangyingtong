package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 会员级别变更登记簿状态
 * @Project: CardWeb
 * @File: MembLevelChgState.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-25下午2:56:35
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
public class MembLevelChgState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final MembLevelChgState WAITEDEAL = new MembLevelChgState("待处理", "00");	
	public static final MembLevelChgState NORMAL = new MembLevelChgState("变动成功", "01");	
	public static final MembLevelChgState DISABLE = new MembLevelChgState("变动失败", "02");	
	public static final MembLevelChgState CANCELED = new MembLevelChgState("已撤销", "03");	
	public static final MembLevelChgState RETPAY = new MembLevelChgState("已冲正", "04");	
	public static final MembLevelChgState RETURNED = new MembLevelChgState("已退货", "05");	
	
	@SuppressWarnings("unchecked")
	protected MembLevelChgState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static MembLevelChgState valueOf(String value) {
		MembLevelChgState type = (MembLevelChgState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}
	
	public static List getAll() {
		return getOrderedList(MembLevelChgState.ALL);
	}
}
