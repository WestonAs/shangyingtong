package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class MembDegradeMthdType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	会员保级方式代码:
		0: 人工控制
		1: 积分余额超过相应阈值
		2: 增加积分值超过相应阈值
		3: 刷卡次数超过相应阈值
	*/
	public static final MembDegradeMthdType MANUNAL = new MembDegradeMthdType("人工控制", "0");
	public static final MembDegradeMthdType POINTBALANCE = new MembDegradeMthdType("积分余额超过相应阈值", "1");
	public static final MembDegradeMthdType POINTINCREASE = new MembDegradeMthdType("增加积分超过相应阈值", "2");
	public static final MembDegradeMthdType CARDTIME = new MembDegradeMthdType("刷卡次数超过相应阈值", "3");
	
	protected MembDegradeMthdType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static MembDegradeMthdType valueOf(String value) {
		MembDegradeMthdType type = (MembDegradeMthdType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的保级方式");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(MembDegradeMthdType.ALL);
	}

}
