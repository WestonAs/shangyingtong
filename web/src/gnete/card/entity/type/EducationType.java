package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class EducationType extends AbstractType {
	
public static final Map ALL = new HashMap();
	
	/**
	 * 学历类型
	 */
	public static final EducationType SENIOR = new EducationType("高中","00");
	public static final EducationType JUNIOR = new EducationType("大专","01");
	public static final EducationType BACHELOR = new EducationType("本科","02");
	public static final EducationType GRADUATE = new EducationType("研究生","03");
	public static final EducationType DORTOR = new EducationType("博士","04");
	public static final EducationType OTHER = new EducationType("其他","05");
	
	@SuppressWarnings("unchecked")
	protected EducationType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static EducationType valueOf(String value) {
		EducationType type = (EducationType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(EducationType.ALL);
	}

}
