package gnete.card.entity.type.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 
 * @Project: CardWeb
 * @File: FenZhiOperateType.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2012-11-26上午10:31:56
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
public class FenzhiOperateType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
//	public static final FenZhiOperateType SUMMARY = new FenZhiOperateType("分支机构运营手续费收入汇总报表", "0");
	public static final FenzhiOperateType DETAIL = new FenzhiOperateType("分支机构运营手续费收入明细报表", "1");
	public static final FenzhiOperateType DETAILADJUST = new FenzhiOperateType("分支机构运营手续费收入年终调整报表", "2");
	
	@SuppressWarnings("unchecked")
	protected FenzhiOperateType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static FenzhiOperateType valueOf(String value) {
		FenzhiOperateType type = (FenzhiOperateType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的卡类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(FenzhiOperateType.ALL);
	}

	public static List getDetail(){
		Map params = new HashMap();
		params.put(DETAIL.getValue(), DETAIL);
		return getOrderedList(params);
	}
	
}
