package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: ReversalFlag.java
 *
 * @description: 冲正标志：00-未冲正，01-已冲正，02-冲正失败，03-待处理
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2011-12-19
 */
public class ReversalFlag extends AbstractType{

	public static final Map ALL = new HashMap();
	
	public static final ReversalFlag WAITE_REVERSAL = new ReversalFlag("未冲正", "00");
	public static final ReversalFlag SUCCESS_REVERSAL = new ReversalFlag("已冲正", "01");
	public static final ReversalFlag FAILURE_REVERSAL = new ReversalFlag("冲正失败", "02");
	public static final ReversalFlag WAITE_DEAL = new ReversalFlag("待处理", "03");
	
	protected ReversalFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static ReversalFlag valueOf(String value) {
		ReversalFlag type = (ReversalFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的标志");
		}
		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(ReversalFlag.ALL);
	}

}
