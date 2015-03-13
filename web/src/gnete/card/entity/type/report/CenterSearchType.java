package gnete.card.entity.type.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 集群运营机构查询分类
 * @Project: CardWeb
 * @File: CenterSearchType.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2012-11-23下午2:37:39
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
public class CenterSearchType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	0-按集群查询
		1-按机构查询
	*/
	public static final CenterSearchType CLUSTER = new CenterSearchType("按集群查询", "0");
	public static final CenterSearchType BRANCH = new CenterSearchType("按机构查询", "1");
	
	@SuppressWarnings("unchecked")
	protected CenterSearchType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CenterSearchType valueOf(String value) {
		CenterSearchType type = (CenterSearchType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的查询类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CenterSearchType.ALL);
	}
}
