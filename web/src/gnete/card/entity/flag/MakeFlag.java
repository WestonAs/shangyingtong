package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: MakeFlag.java
 *
 * @description: 制卡模式
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-10
 */
public class MakeFlag extends AbstractType {
	public static final Map ALL = new HashMap();

	/**
	  1 普通卡 
	  2 发卡机构自行写磁
	  3 白卡
	 */
	public static final MakeFlag NORMAL_CARD = new MakeFlag("普通卡", "1");
	public static final MakeFlag SELF_WRITE = new MakeFlag("发卡机构自行写磁", "2");
	public static final MakeFlag WHITE_CARD = new MakeFlag("白卡", "3");
	
	@SuppressWarnings("unchecked")
	protected MakeFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static MakeFlag valueOf(String value) {
		MakeFlag type = (MakeFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的代码");
		}
		return type;
	}
}
