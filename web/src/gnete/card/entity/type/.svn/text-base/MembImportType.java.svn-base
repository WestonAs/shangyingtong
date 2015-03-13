package gnete.card.entity.type;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员录入方式
 * @Project: CardWeb
 * @File: MembImportType.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-23下午2:53:32
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
public class MembImportType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	public static final MembImportType SELECT = new MembImportType("指定会员录入", "01");
	public static final MembImportType BATCH = new MembImportType("指定批次录入", "02");
	
	@SuppressWarnings("unchecked")
	protected MembImportType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static MembImportType valueOf(String value) {
		MembImportType type = (MembImportType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(MembImportType.ALL);
	}
	
}
