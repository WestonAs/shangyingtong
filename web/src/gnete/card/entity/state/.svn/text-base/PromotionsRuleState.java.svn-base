package gnete.card.entity.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: PromotionsRuleState.java
 *
 * @description: 审核通用状态
 * <li>00: 生效</li>
 * <li>10: 新建</li>
 * <li>20: 待审核</li>
 * <li>30: 失效</li>
 * <li>40: 审核失败</li>
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-9-15
 */
public class PromotionsRuleState extends AbstractState {
	public static final Map ALL = new LinkedHashMap();

	public static final PromotionsRuleState CREATE = new PromotionsRuleState("新建", "10");
	public static final PromotionsRuleState WAITED = new PromotionsRuleState("待审核", "20");
	public static final PromotionsRuleState FAILED = new PromotionsRuleState("审核失败", "40");
	public static final PromotionsRuleState EFFECT = new PromotionsRuleState("生效", "00");
	public static final PromotionsRuleState INVALID = new PromotionsRuleState("失效", "30");	
	
	@SuppressWarnings("unchecked")
	protected PromotionsRuleState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PromotionsRuleState valueOf(String value) {
		PromotionsRuleState type = (PromotionsRuleState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return new ArrayList(PromotionsRuleState.ALL.values());
	}
	
	public static List getPtConsmRuleState(){
		Map params = new HashMap();
		params.put(EFFECT.getValue(), EFFECT);
		params.put(INVALID.getValue(), INVALID);
		return getOrderedList(params);
	}
}
