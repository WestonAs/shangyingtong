/**
 * 
 */
package flink.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author haichen.ma
 *
 */
public abstract class AbstractState implements State {
	private NameValuePair nameValue = new NameValuePair();
	
	protected AbstractState(String name, String value) {
		this.nameValue.setName(name);
		this.nameValue.setValue(value);
	}
//	
//	@SuppressWarnings("unchecked")
//	protected static List getOrderedList(Map all) {
//		List orderedList = new ArrayList();
//		orderedList.addAll(all.values());
//		
//		Collections.sort(orderedList, new Comparator() {
//			public int compare(Object o1, Object o2) {
//				AbstractState state1 = (AbstractState) o1;
//				AbstractState state2 = (AbstractState) o2;
//				
//				return state1.getValue().compareTo(state2.getValue());
//			}
//		});
//		
//		return orderedList;
//	}
	
	
	protected static List getList(Map all) {
		return Arrays.asList(all.values());
	}
	
	/**
	 * 改成按名称来排序
	 * @param all
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected static List getOrderedList(Map all) {
		List orderedList = new ArrayList();
		orderedList.addAll(all.values());
		
		Collections.sort(orderedList, new Comparator() {
			public int compare(Object o1, Object o2) {
				AbstractState state1 = (AbstractState) o1;
				AbstractState state2 = (AbstractState) o2;
				
				return Cn2PinYinHelper.cn2Spell(state1.getName()).compareTo(Cn2PinYinHelper.cn2Spell(state2.getName()));
			}
		});
		
		return orderedList;
	}
	
	/**
	 * 根据名称排序
	 * @param all
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected static List getNameOrderedList(Map all) {
		List orderedList = new ArrayList();
		orderedList.addAll(all.values());
		
		Collections.sort(orderedList, new Comparator() {
			public int compare(Object o1, Object o2) {
				AbstractState type1 = (AbstractState) o1;
				AbstractState type2 = (AbstractState) o2;
				
				return type1.getName().compareTo(type2.getName());
			}
		});
		
		return orderedList;
	}
	
	/**
	 * 根据值排序
	 * @param all
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected static List getValueOrderedList(Map all) {
		List orderedList = new ArrayList();
		orderedList.addAll(all.values());
		
		Collections.sort(orderedList, new Comparator() {
			public int compare(Object o1, Object o2) {
				AbstractState type1 = (AbstractState) o1;
				AbstractState type2 = (AbstractState) o2;
				
				return type1.getValue().compareTo(type2.getValue());
			}
		});
		
		return orderedList;
	}

	/* (non-Javadoc)
	 * @see com.flink.model.State#getName()
	 */
	public String getName() {
		return this.nameValue.getName();
	}

	/* (non-Javadoc)
	 * @see com.flink.model.State#getValue()
	 */
	public String getValue() {
		return this.nameValue.getValue();
	}
	
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		
		return this.nameValue.getValue().equals(((State) obj).getValue());
	}

	public int hashCode() {
		return this.nameValue.getValue().hashCode();
	}
	
	/**
	 * 获取状态值.
	 * 
	 * @param states
	 * @return
	 */
	public static String[] values(State[] states) {
		if (ArrayUtils.isEmpty(states)) {
			return null;
		}
		
		String[] result = new String[states.length];
		
		for (int i = 0; i < states.length; i++) {
			result[i] = states[i] == null ? null : states[i].getValue();
		}
		
		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append(this.nameValue.getName());
		sb.append(":");
		sb.append("\"");
		sb.append(this.nameValue.getValue());
		sb.append("\"");
		sb.append("}");
		
		return sb.toString();
	}
}
