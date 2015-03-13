package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class MembUpgradeMthdType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	会员升级方式代码:
		0: 人工控制
		1: 积分余额超过相应阈值
		2: 增加积分值超过相应阈值
		3: 刷卡次数超过相应阈值
	*/
	public static final MembUpgradeMthdType MANUNAL = new MembUpgradeMthdType("人工控制", "0");
	public static final MembUpgradeMthdType POINTBALANCE = new MembUpgradeMthdType("积分余额超过相应阈值", "1");
	public static final MembUpgradeMthdType POINTINCREASE = new MembUpgradeMthdType("增加积分超过相应阈值", "2");
	public static final MembUpgradeMthdType CARDTIME = new MembUpgradeMthdType("刷卡次数超过相应阈值", "3");

	protected MembUpgradeMthdType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static MembUpgradeMthdType valueOf(String value) {
		MembUpgradeMthdType type = (MembUpgradeMthdType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的升级方式");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(MembUpgradeMthdType.ALL);
	}

}
