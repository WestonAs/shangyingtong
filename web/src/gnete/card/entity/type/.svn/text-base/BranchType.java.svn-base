package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-lih
 * @modify ZhaoWei 新增集团类型
 */
@SuppressWarnings("unchecked")
public class BranchType extends AbstractType {
	public static final Map ALL = new HashMap();
	
	/*
	机构类型代码:
		00 运营管理中心、
		01 运营机构、
		11 运营机构代理商、
		12 集团
		20 发卡机构、
		21 发卡机构商户代理商
		22 发卡机构售卡代理商、
		30 制卡厂商、
		31 机具出机方、
		32 机具维护方 
		33 收单机构
		
		40 渠道机构
	*/

	public static final BranchType CENTER = new BranchType("运营中心", "00");
	public static final BranchType FENZHI = new BranchType("运营机构", "01");
	public static final BranchType AGENT = new BranchType("运营代理商", "11");
	public static final BranchType GROUP = new BranchType("集团", "12");
	public static final BranchType CARD = new BranchType("发卡机构", "20");
	public static final BranchType CARD_MERCHANT = new BranchType("发卡机构商户代理", "21");
	public static final BranchType CARD_SELL = new BranchType("发卡机构售卡代理", "22");
	public static final BranchType CARD_MAKE = new BranchType("制卡厂商", "30");
	public static final BranchType TERMINAL = new BranchType("机具出机方", "31");
	public static final BranchType TERMINAL_MAINTAIN = new BranchType("机具维护方", "32");
	public static final BranchType CHANNEL = new BranchType("渠道机构", "40");
//	public static final BranchType SHOUDAN = new BranchType("收单机构", "33");
	
	
	@SuppressWarnings("unchecked")
	protected BranchType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static BranchType valueOf(String value) {
		BranchType type = (BranchType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(BranchType.ALL);
	}
	
	public static List getForCenter(){
		Map params = new HashMap();
		params.put(FENZHI.getValue(), FENZHI);
		params.put(AGENT.getValue(), AGENT);
		params.put(GROUP.getValue(), GROUP);
		params.put(CARD.getValue(), CARD);
		params.put(CARD_MERCHANT.getValue(), CARD_MERCHANT);
		params.put(CARD_SELL.getValue(), CARD_SELL);
		params.put(CARD_MAKE.getValue(), CARD_MAKE);
		params.put(TERMINAL.getValue(), TERMINAL);
		params.put(TERMINAL_MAINTAIN.getValue(), TERMINAL_MAINTAIN);
//		params.put(SHOUDAN.getValue(), SHOUDAN);
		return getOrderedList(params);
	}
	
	public static List getForBranch(){
		Map params = new HashMap();
		params.put(AGENT.getValue(), AGENT);
		params.put(GROUP.getValue(), GROUP);
		params.put(CARD.getValue(), CARD);
		
		params.put(CARD_MERCHANT.getValue(), CARD_MERCHANT);
		params.put(CARD_SELL.getValue(), CARD_SELL);
		params.put(CARD_MAKE.getValue(), CARD_MAKE);
		params.put(TERMINAL.getValue(), TERMINAL);
		params.put(TERMINAL_MAINTAIN.getValue(), TERMINAL_MAINTAIN);
		return getOrderedList(params);
	}
	
	/**
	 * 得到管理机构类型
	 * @return
	 */
	public static List getCenter(){
		Map params = new HashMap();
		params.put(CENTER.getValue(), CENTER);
		return getOrderedList(params);
	}
	
	/**
	 * 得到管理机构类型
	 * @return
	 */
	public static List getManageBranch(){
		Map params = new HashMap();
//		params.put(CENTER.getValue(), CENTER);
		params.put(FENZHI.getValue(), FENZHI);
		return getOrderedList(params);
	}
		
	/**
	 * 得到发展机构类型
	 * @return
	 */
	public static List getDevelopBranch(){
		Map params = new HashMap();
		params.put(CENTER.getValue(), CENTER);
		params.put(FENZHI.getValue(), FENZHI);
		params.put(AGENT.getValue(), AGENT);
		return getOrderedList(params);
	}
	
	/**
	 * 得到可发展商户的机构类型
	 * @return
	 */
	public static List getDevelopMerch(){
		Map params = new HashMap();
		params.put(CENTER.getValue(), CENTER);
		params.put(FENZHI.getValue(), FENZHI);
		params.put(AGENT.getValue(), AGENT);
		params.put(CARD.getValue(), CARD);
		params.put(CARD_MERCHANT.getValue(), CARD_MERCHANT);
		return getOrderedList(params);
	}
	
	/**
	 * 得到发卡机构类型
	 * @return
	 */
	public static List getCardBranch(){
		Map params = new HashMap();
		params.put(CARD.getValue(), CARD);
		return getOrderedList(params);
	}

	/**
	 * 得到集团、发卡机构类型
	 * @return
	 */
	public static List getGroupOrCard(){
		Map params = new HashMap();
		params.put(GROUP.getValue(), GROUP);
		params.put(CARD.getValue(), CARD);
		return getOrderedList(params);
	}
}
