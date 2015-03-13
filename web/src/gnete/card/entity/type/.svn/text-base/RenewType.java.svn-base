package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;

public class RenewType extends AbstractType {

	public static final Map ALL = new HashMap();
	/**
	 * 换卡类型
	 */
	public static final RenewType LOSSCARD = new RenewType("挂失换卡","0");
	public static final RenewType BROKENCARD = new RenewType("损坏换卡","1");
	public static final RenewType UPGRADECARD = new RenewType("升级换卡", "2");
	
	@SuppressWarnings("unchecked")
	protected RenewType(String name, String value){
		super(name, value);
		ALL.put(value, this);
	}

	public static RenewType valueOf(String value){
		RenewType renew = (RenewType) ALL.get(value);
		
		if(renew == null){
			throw new RuntimeException("不正确的类型");
		}
		return renew;
	}
	
	public static List getAll(){
		return getOrderedList(RenewType.ALL);
	}
	
	public static List getUpgradeCard(){
		Map params = new HashMap();
		params.put(UPGRADECARD.getValue(), UPGRADECARD);
		
		return getOrderedList(params);
	}
	
	public static List getLossCard(){
		Map params = new HashMap();
		params.put(LOSSCARD.getValue(), LOSSCARD);
		return getOrderedList(params);
	}
	
	public static List getBrokeCard(){
		Map params = new HashMap();
		params.put(BROKENCARD.getValue(), BROKENCARD);
		return getOrderedList(params);
	}
	
	public static List getLossBrokeCard(){
		Map params = new HashMap();
		params.put(BROKENCARD.getValue(), BROKENCARD);
		params.put(LOSSCARD.getValue(), LOSSCARD);
		return getOrderedList(params);
	}
	
}
