package gnete.card.entity.type.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: CenterOperateType.java
 *
 * @description: 中心角色的平台运营手续费报表类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-8
 */
public class CenterOperateType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	0-积分卡业务平台运营手续费收入汇总报表
		1-积分卡业务平台运营手续费收入明细报表
	*/
	public static final CenterOperateType SUMMARY = new CenterOperateType("平台运营手续费收入汇总报表", "0");
	public static final CenterOperateType DETAIL = new CenterOperateType("平台运营手续费收入明细报表", "1");
	public static final CenterOperateType DETAILADJUST = new CenterOperateType("平台运营手续费收入年终调整报表", "2");
	public static final CenterOperateType SUMMARYADJUST = new CenterOperateType("平台运营手续费收入汇总终调整报表", "3");
	
	@SuppressWarnings("unchecked")
	protected CenterOperateType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CenterOperateType valueOf(String value) {
		CenterOperateType type = (CenterOperateType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的卡类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CenterOperateType.ALL);
	}
	
	public static List getFenZhi(){
		Map fenZhi = new HashMap();
		fenZhi.put(DETAIL.getValue(), DETAIL);
		fenZhi.put(DETAILADJUST.getValue(), DETAILADJUST);
		return getOrderedList(fenZhi);
	}

	public static List getDetail(){
		Map params = new HashMap();
		params.put(DETAIL.getValue(), DETAIL);
		return getOrderedList(params);
	}
	
}
