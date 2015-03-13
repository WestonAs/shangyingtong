package gnete.card.entity.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @description 角色类型代码<br/>
 */
public class RoleType extends AbstractType {
	public static final Map ALL = new HashMap();
	
	public static final RoleType CENTER = new RoleType("运营中心", "00");
	/** 运营机构 */
	public static final RoleType FENZHI = new RoleType("运营机构", "01");
	/** 运营中心部门 */
	public static final RoleType CENTER_DEPT = new RoleType("运营中心部门", "02");
	/** 运营代理商 */
	public static final RoleType AGENT = new RoleType("运营代理商", "11");
	/** 集团 */
	public static final RoleType GROUP = new RoleType("集团", "12");
	/** 发卡机构 */
	public static final RoleType CARD = new RoleType("发卡机构", "20");
	/** 发卡机构商户代理 */
	public static final RoleType CARD_MERCHANT = new RoleType("发卡机构商户代理", "21");
	/** 发卡机构售卡代理 */
	public static final RoleType CARD_SELL = new RoleType("发卡机构售卡代理", "22");
	/** 发卡机构网点 */
	public static final RoleType CARD_DEPT = new RoleType("发卡机构网点", "23");
	/** 制卡厂商 */
	public static final RoleType CARD_MAKE = new RoleType("制卡厂商", "30");
	
	public static final RoleType TERMINAL = new RoleType("机具出机方", "31");
	public static final RoleType TERMINAL_MAINTAIN = new RoleType("机具维护方", "32");
//	public static final RoleType SHOUDAN = new RoleType("收单机构", "33");
	/** 商户 */
	public static final RoleType MERCHANT = new RoleType("商户用户", "40");
	public static final RoleType PERSONAL = new RoleType("个人用户", "50");
	
	@SuppressWarnings("unchecked")
	protected RoleType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static RoleType valueOf(String value) {
		RoleType type = (RoleType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的用户类型");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(RoleType.ALL);
	}
	
	@SuppressWarnings("unchecked")
	public static List getCenter(){
		Map params = new HashMap();
		params.put(CENTER.getValue(), CENTER);
		params.put(CENTER_DEPT.getValue(), CENTER_DEPT);
		params.put(FENZHI.getValue(), FENZHI);
		params.put(AGENT.getValue(), AGENT);
		params.put(GROUP.getValue(), GROUP);
		params.put(CARD.getValue(), CARD);
		params.put(CARD_MAKE.getValue(), CARD_MAKE);
		params.put(CARD_MERCHANT.getValue(), CARD_MERCHANT);
		params.put(CARD_SELL.getValue(), CARD_SELL);
		params.put(TERMINAL.getValue(), TERMINAL);
		params.put(TERMINAL_MAINTAIN.getValue(), TERMINAL_MAINTAIN);
		params.put(MERCHANT.getValue(), MERCHANT);
		params.put(PERSONAL.getValue(), PERSONAL);
		return getOrderedList(params);
	}
	
	/**
	 * 得到可以售卡的角色类型
	 */
	public static List getSaleCardBranch(){
		List<String> list = new ArrayList<String>();
		list.add(CARD_SELL.getValue());
		list.add(CARD.getValue());
		list.add(CARD_DEPT.getValue());
		return list;
	}
	
	public static List getManage(){
		Map params = new HashMap();
		params.put(FENZHI.getValue(), FENZHI);
		params.put(GROUP.getValue(), GROUP);
		params.put(AGENT.getValue(), AGENT);
		params.put(CARD.getValue(), CARD);
		params.put(CARD_MAKE.getValue(), CARD_MAKE);
		params.put(CARD_MERCHANT.getValue(), CARD_MERCHANT);
		params.put(CARD_SELL.getValue(), CARD_SELL);
		params.put(TERMINAL.getValue(), TERMINAL);
		params.put(TERMINAL_MAINTAIN.getValue(), TERMINAL_MAINTAIN);
		params.put(MERCHANT.getValue(), MERCHANT);
		return getOrderedList(params);
	}
	
	public static List getForCard(){
		Map params = new HashMap();
		params.put(CARD.getValue(), CARD);
		params.put(CARD_DEPT.getValue(), CARD_DEPT);
		return getOrderedList(params);
	}
	
}
