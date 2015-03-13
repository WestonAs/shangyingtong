package flink.util;

import java.io.File;
import java.math.BigDecimal;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.collections.map.AbstractHashedMap;
import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.WebUtils;

/**
 * <p>公共基本工具使用类</p>
 * @Project: Card
 * @File: CommonHelper.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-7
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public final class CommonHelper {
	/** 缺省支持的分隔符*/
	private static final String[] SUPPORT_SPLITS = new String[] { ",", " ", ":", "", "#" };

	/** 证书序号倍数*/
	private static final int CHECK_SEQ_NUM = 4;

	/** HEX编码支持集合(基于大写字母方式)*/
	private static final char[] BCD_LOOKUP = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/** 本地HEX编码SET保存集合*/
	private static final Set<Character> bcdSet = new HashSet<Character>();

	/** GBK编码*/
	private static final String GBK_ENCODING = "GBK";

	/** 缺省字符缓冲长度*/
	private static final int MAX_BUFFER_SIZE = 1024;

	/** 缺省邮件定义正则表达式*/
	private static final String MAIL_STRICT_REG = "\\w+@(\\w+.)+[a-z]{2,3}";

	/** 缺省空串*/
	private static final String BLANK = " ";

	/** 缺省转义符定义*/
	private static final String REG_SYBOL = "\\";

	/** 缺省日期格式PATTERN支持数组*/
	public static final String[] DATE_PATTERN = new String[] { "yyyyMMdd", "yyyy-MM-dd", "yyyy/MM/dd" };

	/** 缺省可支持日期PATTERN数组(非严格意义均支持)*/
	private static final String[] TIME_PATTERN = new String[] { "yyyyMMdd HHmm", "yyyyMMdd HHmmss", "yyyyMMdd HH:mm",
			"yyyyMMdd HH:mm:ss", "yyyy-MM-dd HHmm", "yyyy-MM-dd HHmmss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss",
			"yyyy/MM/dd HHmm", "yyyy/MM/dd HHmmss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH:mm:ss" };

	/** 缺省金融数字格式化PATTERN */
	private static final String commonAmtPattern = "##0.00";

	/** 缺省金融数字格式化处理*/
	private static final DecimalFormat commonAmtFormat = new DecimalFormat(commonAmtPattern);

	/** 自定义TRUE字符表示*/
	private static final String TRUE_BOOLEAN = "M";

	/** 自定义FALSE字符表示*/
	private static final String FALSE_BOOLEAN = "O";

	/** 缺省一天的毫秒(用于天与天间隔计算)*/
	private static final long DAY_MILLIS = 1000 * 60 * 60 * 24;

	// ------------------------------------check------------------------------------------

	static {
		for (char c : BCD_LOOKUP) {
			bcdSet.add(Character.valueOf(c));
		}
	}

	/**
	 * <p>检查对象涉及类型是否相同</p>
	 */
	public static boolean compareSameType(Object obj1, Object obj2) {
		return ClassUtils.isAssignable(obj1.getClass(), obj2.getClass());
	}

	/**
	 * 
	 * <p>检查对象数组是否为空</p>
	 * @param array
	 * @return
	 * @version: 2011-1-8 下午01:42:20
	 * @See:
	 */
	public static boolean checkIsEmpty(Object[] array) {
		return ArrayUtils.isEmpty(array);
	}

	/**
	 * <p>检查字节数组是否为空</p>
	  * @param array
	  * @return  
	  * @version: 2011-7-12 下午04:05:23
	  * @See:
	 */
	public static boolean checkIsEmpty(byte[] array) {
		return ArrayUtils.isEmpty(array);
	}

	/**
	 *  <p>检查字符数组是否为空</p>
	  * @param array
	  * @return  
	  * @version: 2011-7-12 下午04:05:47
	  * @See:
	 */
	public static boolean checkIsEmpty(char[] array) {
		return ArrayUtils.isEmpty(array);
	}
	
	/**
	 *  <p></p>
	  * @param map
	  * @return  
	  * @version: 2011-7-12 下午04:06:22
	  * @See:
	 */
	public static boolean checkIsEmpty(Map map) {
		return MapUtils.isEmpty(map);
	}

	/**
	 *  <p></p>
	  * @param col
	  * @return  
	  * @version: 2011-7-12 下午04:06:32
	  * @See:
	 */
	public static boolean checkIsEmpty(Collection col) {
		return CollectionUtils.isEmpty(col);
	}

	/**
	 *  <p></p>
	  * @param files
	  * @return  
	  * @version: 2011-7-12 下午04:07:09
	  * @See:
	 */
	public static boolean checkIsEmpty(File[] files) {
		return (files == null || files.length == 0);
	}

    /**
     * 
      * @param content
      * @return  
      * @version: 2011-7-12 下午04:07:21
      * @See:
     */
	public static boolean checkIsEmpty(String content) {
		return StringUtils.isBlank(content);
	}

	/**
	 * 
	  * @param content
	  * @return  
	  * @version: 2011-7-12 下午04:07:25
	  * @See:
	 */
	public static boolean isEmpty(String content) {
		return StringUtils.isEmpty(content);
	}

	/**
	 * 
	  * @param array
	  * @return  
	  * @version: 2011-7-12 下午04:07:28
	  * @See:
	 */
	public static boolean checkIsNotEmpty(Object[] array) {
		return !checkIsEmpty(array);
	}

	/**
	 * 
	  * @param content
	  * @return  
	  * @version: 2011-7-12 下午04:07:33
	  * @See:
	 */
	public static boolean checkIsNotEmpty(byte[] content) {
		return !checkIsEmpty(content);
	}

	/**
	 * 
	  * @param content
	  * @return  
	  * @version: 2011-7-12 下午04:07:36
	  * @See:
	 */
	public static boolean checkIsNotEmpty(char[] content) {
		return !checkIsEmpty(content);
	}

	/**
	 * 
	  * @param content
	  * @return  
	  * @version: 2011-7-12 下午04:07:40
	  * @See:
	 */
	public static boolean checkIsNotEmpty(String content) {
		return StringUtils.isNotBlank(content);
	}

	/**
	 * 
	  * @param content
	  * @return  
	  * @version: 2011-7-12 下午04:07:44
	  * @See:
	 */
	public static boolean isNotEmpty(String content) {
		return StringUtils.isNotEmpty(content);
	}

	/**
	 * 
	  * @param map
	  * @return  
	  * @version: 2011-7-12 下午04:07:48
	  * @See:
	 */
	public static boolean checkIsNotEmpty(Map map) {
		return MapUtils.isNotEmpty(map);
	}

	/**
	 * 
	  * @param col
	  * @return  
	  * @version: 2011-7-12 下午04:07:52
	  * @See:
	 */
	public static boolean checkIsNotEmpty(Collection col) {
		return CollectionUtils.isNotEmpty(col);
	}

	/**
	 * 
	  * @param obj1
	  * @param obj2
	  * @return  
	  * @version: 2011-7-12 下午04:07:55
	  * @See:
	 */
	public static boolean checkEquals(Object obj1, Object obj2) {
		return ObjectUtils.nullSafeEquals(obj1, obj2);
	}

	/**
	 * 
	 * @description：检查输入是否符合正则表达式
	 * @param str
	 * @param checkedPattern
	 * @return
	 * @version: 2011-1-8 下午01:42:38
	 * @See:
	 */
	public static boolean checkPattern(String str, String checkedPattern) {
		if (checkIsEmpty(checkedPattern)) {
			throw new IllegalArgumentException("pattern can't be empty");
		}
		Pattern pattern = Pattern.compile(checkedPattern);

		Matcher matcher = pattern.matcher(str);

		return matcher.find();
	}

	public static boolean checkIsStrictEmail(String mailAddress) {
		return checkPattern(mailAddress, MAIL_STRICT_REG);
	}

	/**
	 * 
	 * @description：<li>长度必须是4的倍数且里面出现的值在BCD_LOOKUP中</li>
	 * @param seqNo
	 * @return
	 * @version: 2011-6-13 下午05:13:37
	 * @See:
	 */
	public static boolean checkCertSeqNo(String seqNo) {
		seqNo = trimAllWhitespace(seqNo);

		char[] seq_array = seqNo.toCharArray();

		if ((seq_array == null) || (seq_array.length == 0) || (seq_array.length % CHECK_SEQ_NUM != 0)) {
			return false;
		}

		for (char seq : seq_array) {
			if (!bcdSet.contains(Character.valueOf(seq))) {
				return false;
			}
		}

		return true;
	}
	
    /**
     * 
      * @param group
      * @param seachStr
      * @return  
      * @version: 2011-7-12 下午04:28:58
      * @See:
     */
	public static boolean containsStr(String[] group, String seachStr) {
		return ObjectUtils.containsElement(group, seachStr);
	}
	
	/**
	 * 
	  * @param array
	  * @param obj
	  * @return  
	  * @version: 2011-7-12 下午04:10:36
	  * @See:
	 */
	public static boolean containsElement(Object[] array, Object obj) {
		return ObjectUtils.containsElement(array, obj);
	}
	
	/**
	 * 
	  * @param coll1
	  * @param coll2
	  * @return  
	  * @version: 2011-7-12 下午04:11:06
	  * @See:
	 */
	public static boolean containsAny(Collection coll1, Collection coll2) {
		return CollectionUtils.containsAny(coll1, coll2);
	}

	/**
	 * 
	  * @param prefixList
	  * @param hasPrefixList
	  * @return  
	  * @version: 2011-7-12 下午04:11:10
	  * @See:
	 */
	public static boolean containsPrefix(Collection<String> prefixList, Collection<String> hasPrefixList) {
		boolean result = true;
		if (prefixList.size() < hasPrefixList.size()) {
			for (String hasPrefix : hasPrefixList) {
				for (String prefix : prefixList) {
					if (!hasPrefix.startsWith(prefix)) {
						result = false;
						break;
					}
				}
			}
		} else {
			for (String prefix : prefixList) {
				for (String hasPrefix : hasPrefixList) {
					if (!hasPrefix.startsWith(prefix)) {
						result = false;
						break;
					}
				}
			}
		}

		return result;
	}
	
	public static boolean containsWhitespace(String str) {
		if (checkIsEmpty(str)) {
			return false;
		}

		int strLen = str.length();

		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}

		return false;
	}
	
	public static boolean containStr(String str, String searchStr) {
		return StringUtils.contains(str, searchStr);
	}

	// -----------------------------------Map--------------------------------------------
	/**
	 * 
	  * @param map
	  * @return  
	  * @version: 2011-7-12 下午04:18:49
	  * @See:
	 */
	public static Map createDefaultMultiMap(Map map) {
		return MapUtils.multiValueMap(map);
	}

	/**
	 * 
	  * @param map
	  * @param collectionClass
	  * @return  
	  * @version: 2011-7-12 下午04:18:53
	  * @See:
	 */
	public static Map createDefaultMultiMap(Map map, Class collectionClass) {
		return MapUtils.multiValueMap(map, collectionClass);
	}

	/**
	 * 
	  * @return  
	  * @version: 2011-7-12 下午04:18:56
	  * @See:
	 */
	public static Map createDefaultMultiMap() {
		return MapUtils.multiValueMap(createDefaultMap());
	}

	/**
	 * 
	  * @param collectionClass
	  * @return  
	  * @version: 2011-7-12 下午04:19:18
	  * @See:
	 */
	public static Map createDefaultMultiMap(Class collectionClass) {
		return MapUtils.multiValueMap(createDefaultMap(), collectionClass);
	}

	/**
	 * 
	  * @param size
	  * @return  
	  * @version: 2011-7-12 下午04:19:22
	  * @See:
	 */
	public static Map createDefaultMultiMap(int size) {
		return MapUtils.multiValueMap(createDefaultMap(size));
	}

	/**
	 * 
	  * @param size
	  * @param collectionClass
	  * @return  
	  * @version: 2011-7-12 下午04:19:25
	  * @See:
	 */
	public static Map createDefaultMultiMap(int size, Class collectionClass) {
		return MapUtils.multiValueMap(createDefaultMap(size), collectionClass);
	}

	/**
	 * 
	  * @return  
	  * @version: 2011-7-12 下午04:19:28
	  * @See:
	 */
	public static MultiKeyMap createDefaultMultiKeyMap() {
		AbstractHashedMap paraMap = new LinkedMap(createDefaultMap());

		return MultiKeyMap.decorate(paraMap);
	}

	/**
	 * 
	  * @param size
	  * @return  
	  * @version: 2011-7-12 下午04:19:31
	  * @See:
	 */
	public static MultiKeyMap createDefaultMultiKeyMap(int size) {
		AbstractHashedMap paraMap = new LinkedMap(createDefaultMap(size));

		return MultiKeyMap.decorate(paraMap);
	}

	/**
	 * 
	  * @param map
	  * @return  
	  * @version: 2011-7-12 下午04:19:35
	  * @See:
	 */
	public static MultiKeyMap createDefaultMultiKeyMap(AbstractHashedMap map) {
		return MultiKeyMap.decorate(map);
	}

	/**
	 * 
	  * @return  
	  * @version: 2011-7-12 下午04:19:40
	  * @See:
	 */
	public static Map createDefaultLRUMap() {
		return MapUtils.synchronizedMap(new LRUMap());
	}

	/**
	 * 
	  * @param size
	  * @return  
	  * @version: 2011-7-12 下午04:19:44
	  * @See:
	 */
	public static Map createDefaultLRUMap(int size) {
		return MapUtils.synchronizedMap(new LRUMap(size));
	}

	/**
	 * 
	  * @return  
	  * @version: 2011-7-12 下午04:19:47
	  * @See:
	 */
	public static Map createDefaultOrderedMap() {
		return MapUtils.orderedMap(createDefaultMap());
	}

	/**
	 * 
	  * @param size
	  * @return  
	  * @version: 2011-7-12 下午04:19:50
	  * @See:
	 */
	public static Map createDefaultOrderedMap(int size) {
		return MapUtils.orderedMap(createDefaultMap(size));
	}

	/**
	 * 
	  * @param map
	  * @return  
	  * @version: 2011-7-12 下午04:19:54
	  * @See:
	 */
	public static Map createDefaultOrderedMap(Map map) {
		return MapUtils.orderedMap(map);
	}
	
    /**
     * 
      * @param map1
      * @param map2
      * @return  
      * @version: 2011-7-12 下午04:20:01
      * @See:
     */
	@SuppressWarnings("unchecked")
	public static Map getUnionMap(Map map1, Map map2) {
		final Map unionMap = createDefaultMap(map1);
		unionMap.putAll(map2);

		return unionMap;
	}

	/**
	 * 
	  * @return  
	  * @version: 2011-7-12 下午04:20:05
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	public static Map createDefaultMap() {
		return new HashMap();
	}

	/**
	 * 
	  * @param size
	  * @return  
	  * @version: 2011-7-12 下午04:20:07
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	public static Map createDefaultMap(int size) {
		return new HashMap(size);
	}

	/**
	 * 
	  * @param map
	  * @return  
	  * @version: 2011-7-12 下午04:20:12
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	public static Map createDefaultMap(Map map) {
		return new HashMap(map);
	}

	/**
	 * 
	  * @param map
	  * @return  
	  * @version: 2011-7-12 下午04:20:16
	  * @See:
	 */
	public static Map getInvertMap(Map map) {
		return MapUtils.invertMap(map);
	}

	/**
	 * 
	  * @param map
	  * @return  
	  * @version: 2011-7-12 下午04:20:19
	  * @See:
	 */
	public static Map createUnmodiMap(Map map) {
		return MapUtils.unmodifiableMap(map);
	} // 针对string,string的多值Map

	/**
	 * 
	  * @param multiMap
	  * @return  
	  * @version: 2011-7-12 下午04:20:31
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, List> filterMultiValueMap(Map multiMap) {
		Set<Map.Entry<String, List>> multiSet = multiMap.entrySet();
		Map<String, List> paraMap = new HashMap<String, List>(multiSet.size());

		for (Map.Entry<String, List> multiMe : multiSet) {
			String underlying = multiMe.getKey();
			List subUnderlying = multiMe.getValue();
			paraMap.put(underlying, subUnderlying);
		}

		return paraMap;
	}

	/**
	 * 
	  * @param paraMap
	  * @return  
	  * @version: 2011-7-12 下午04:20:35
	  * @See:
	 */
	public static Map getReverseMap(Map paraMap) {
		return MapUtils.invertMap(paraMap);
	}
	
	/**
	 * 
	  * @param props
	  * @param map  
	  * @version: 2011-7-12 下午04:22:37
	  * @See:
	 */
	public static void mergePropertiesIntoMap(Properties props, Map map) {
		if (map == null) {
			throw new IllegalArgumentException("Map must not be null");
		}
		if (props != null) {
			for (Enumeration en = props.propertyNames(); en.hasMoreElements();) {
				String key = (String) en.nextElement();
				Object value = props.getProperty(key);
				if (value == null) {
					// Potentially a non-String value...
					value = props.get(key);
				}
				map.put(key, value);
			}
		}
	}


	// ------------------------------------Col----------------------------------------
	/**
	 * 
	  * @param size
	  * @return  
	  * @version: 2011-7-12 下午04:20:43
	  * @See:
	 */
	public static List<Object> createSafeList(int size) {
		return ListUtils.synchronizedList(new ArrayList<Object>(size));
	}

	/**
	 * 
	  * @param size
	  * @return  
	  * @version: 2011-7-12 下午04:20:51
	  * @See:
	 */
	public static Set<Object> createSafeSet(int size) {
		return SetUtils.synchronizedSet(new HashSet<Object>(size));
	}

	/**
	 * 
	  * @return  
	  * @version: 2011-7-12 下午04:21:00
	  * @See:
	 */
	public static Set<Object> createSafeSet() {
		return SetUtils.synchronizedSet(new HashSet<Object>());
	}

	/**
	 * 
	  * @param col
	  * @return  
	  * @version: 2011-7-12 下午04:21:05
	  * @See:
	 */
	public static List<Object> filterCol2List(Collection col) {
		List<Object> list = createSafeList(col.size());

		synchronized (list) {
			for (Iterator iter = col.iterator(); iter.hasNext();) {
				Object obj = iter.next();
				list.add(obj);
			}
		}

		return list;
	}

	/**
	 * 
	  * @param set
	  * @return  
	  * @version: 2011-7-12 下午04:21:10
	  * @See:
	 */
	public static List<Object> filterSet2List(Set set) {
		List<Object> list = createSafeList(set.size());

		synchronized (list) {
			for (Iterator iter = set.iterator(); iter.hasNext();) {
				Object obj = iter.next();
				list.add(obj);
			}
		}

		return list;
	}
	
    /**
     * 
      * @param col
      * @return  
      * @version: 2011-7-12 下午04:21:16
      * @See:
     */
	public static Set<Object> filterColToSet(Collection<Object> col) {
		Set<Object> set = createSafeSet(col.size());

		synchronized (set) {
			for (Iterator<Object> iter = col.iterator(); iter.hasNext();) {
				Object obj = iter.next();
				set.add(obj);
			}
		}

		return set;
	}

	/**
	 * 
	  * @param list
	  * @return  
	  * @version: 2011-7-12 下午04:21:20
	  * @See:
	 */
	public static List getUnmodifyList(List list) {
		return ListUtils.unmodifiableList(list);
	}

	/**
	 * 
	  * @param list1
	  * @param list2
	  * @return  
	  * @version: 2011-7-12 下午04:21:23
	  * @See:
	 */
	public static List getCompondList(List list1, List list2) {
		return ListUtils.union(list1, list2);
	}

	/**
	 * 
	  * @param list1
	  * @param list2
	  * @return  
	  * @version: 2011-7-12 下午04:21:36
	  * @See:
	 */
	public static List getSubList(List list1, List list2) {
		return ListUtils.subtract(list1, list2);
	}

	/**
	 * 
	  * @param col1
	  * @param col2
	  * @return  
	  * @version: 2011-7-12 下午04:21:39
	  * @See:
	 */
	public static Collection getSubCollection(Collection col1, Collection col2) {
		return CollectionUtils.subtract(col1, col2);
	}

	/**
	 * 
	  * @param set1
	  * @param set2
	  * @return  
	  * @version: 2011-7-12 下午04:21:42
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	public static Set getSubSet(Set set1, Set set2) {
		final Set result = new HashSet(set1);
		final Iterator iterator = set2.iterator();

		while (iterator.hasNext()) {
			result.remove(iterator.next());
		}

		return result;
	}

	/**
	 * 
	  * @param list1
	  * @param list2
	  * @return  
	  * @version: 2011-7-12 下午04:21:46
	  * @See:
	 */
	public static List getUnionList(List list1, List list2) {
		return ListUtils.union(list1, list2);
	}

	/**
	 * 
	  * @param set1
	  * @param set2
	  * @return  
	  * @version: 2011-7-12 下午04:21:49
	  * @See:
	 */
	public static Set getUnionSet(Set set1, Set set2) {
		final Set result = new LinkedHashSet(set1);
		result.addAll(set2);

		return result;
	}

	/**
	 * 
	  * @param set1
	  * @param set2
	  * @param set3
	  * @return  
	  * @version: 2011-7-12 下午04:21:54
	  * @See:
	 */
	public static Set getUnionSet(Set set1, Set set2, Set set3) {
		final Set result = new LinkedHashSet(set1);
		result.addAll(set2);
		result.addAll(set3);

		return result;
	}

	/**
	 * 
	  * @param source
	  * @return  
	  * @version: 2011-7-12 下午04:21:58
	  * @See:
	 */
	public static List<Object> filterArray2List(Object source) {
		return Arrays.asList(ObjectUtils.toObjectArray(source));
	}
	
    /**
     * 
      * @param source
      * @return  
      * @version: 2011-7-12 下午04:22:04
      * @See:
     */
	public static Set<Object> filterArray2Set(Object source) {
		List<Object> list = filterArray2List(source);

		return filterColToSet(list);
	}

	/**
	 * 
	  * @param list
	  * @return  
	  * @version: 2011-7-12 下午04:22:07
	  * @See:
	 */
	public static String[] filterList2Array(List list) {
		if (checkIsEmpty(list)) {
			return new String[] {};
		}

		int size = list.size();
		String[] array = new String[size];

		for (int i = 0; i < size; i++) {
			Object value = list.get(i);
			array[i] = ObjectUtils.nullSafeToString(value);
		}

		return array;
	} // 将Map中value值转换成单一的集合类型

	/**
	 * 
	  * @param paraMap
	  * @return  
	  * @version: 2011-7-12 下午04:22:19
	  * @See:
	 */
	public static List<Object> filterMap2List(Map paraMap) {
		Collection col = paraMap.values();
		List<Object> list = createSafeList(col.size());

		synchronized (list) {
			for (Iterator iter = col.iterator(); iter.hasNext();) {
				Object obj = iter.next();

				if (obj == null) {
					continue;
				}

				if (obj instanceof Collection) {
					list.addAll((Collection) obj);
				} else {
					list.add(obj);
				}
			}
		}

		return list;
	}

	/**
	 * 
	  * @param startId
	  * @param endId
	  * @return  
	  * @version: 2011-7-12 下午04:22:24
	  * @See:
	 */
	public static Set<Integer> configArrangeSet(int startId, int endId) {
		int start = (startId < endId) ? startId : endId;
		int end = (startId < endId) ? endId : startId;
		int ln = end - start;
		Set<Integer> filterSet = new LinkedHashSet<Integer>(ln);

		for (int i = start; i <= end; i++) {
			filterSet.add(i);
		}

		return filterSet;
	}

	/**
	 * 
	  * @param limitStr
	  * @return  
	  * @version: 2011-7-12 下午04:22:32
	  * @See:
	 */
	public static Set<String> convertStr2Set(String limitStr) {
		final Set<String> limitSet = new HashSet<String>();

		String[] limitGroup = getSupportSplitItemGroup(limitStr);
		if (checkIsNotEmpty(limitGroup)) {
			for (String limit : limitGroup) {
				if (checkIsEmpty(limit)) {
					continue;
				}

				limitSet.add(limit);
			}
		}

		return limitSet;
	}

	// ------------------------------------------String-----------------------------------------
    /**
     * 
      * @param array
      * @return  
      * @version: 2011-7-12 下午04:23:43
      * @See:
     */
	public static String safe2Str(Object[] array) {
		return ObjectUtils.nullSafeToString(array);
	}

	/**
	 * 
	  * @param array
	  * @return  
	  * @version: 2011-7-12 下午04:23:49
	  * @See:
	 */
	public static String safe2Str(Object array) {
		return ObjectUtils.nullSafeToString(array);
	}
	
	/**
	 * 
	  * @param left
	  * @param mid
	  * @param end
	  * @return  
	  * @version: 2011-7-12 下午04:23:51
	  * @See:
	 */
	public static String createConnectStr(String left, String mid, String end) {
		return new StringBuilder(left).append(mid).append(end).toString();
	}

	/**
	 * 
	  * @param left
	  * @param right
	  * @return  
	  * @version: 2011-7-12 下午04:23:54
	  * @See:
	 */
	public static String createConnectStr(String left, String right) {
		return new StringBuilder(left).append(right).toString();
	}

	/**
	 * 
	  * @param left
	  * @param right
	  * @param split
	  * @return  
	  * @version: 2011-7-12 下午04:23:58
	  * @See:
	 */
	public static String createConnectStrWithSplit(String left, String right, String split) {
		return new StringBuilder(left).append(split).append(right).toString();
	}

	/**
	 * 
	  * @param left
	  * @param mid
	  * @param right
	  * @param split
	  * @return  
	  * @version: 2011-7-12 下午04:24:03
	  * @See:
	 */
	public static String createConnectStrWithSplit(String left, String mid, String right, String split) {
		return new StringBuilder(left).append(split).append(mid).append(split).append(right).toString();
	}

	/**
	 * 
	  * @param str
	  * @return  
	  * @version: 2011-7-12 下午04:24:06
	  * @See:
	 */
	public static String safeTrim(String str) {
		return StringUtils.stripToEmpty(str);
	}

	/**
	 * 
	  * @param str
	  * @param separatorChars
	  * @return  
	  * @version: 2011-7-12 下午04:24:11
	  * @See:
	 */
	public static String[] getSplitStr(String str, String separatorChars) {
		return StringUtils.split(str, separatorChars);
	}
	
    /**
     * 
      * @param str
      * @param remove
      * @return  
      * @version: 2011-7-12 下午04:24:17
      * @See:
     */
	public static String getRemovedStartStr(String str, String remove) {
		return StringUtils.removeStart(str, remove);
	}

	/**
	 * 
	  * @param info
	  * @return  
	  * @version: 2011-7-12 下午04:24:23
	  * @See:
	 */
	public static String filterSqlStr(String info) {
		return StringEscapeUtils.escapeSql(info);
	}

	/**
	 * 
	  * @param str
	  * @return  
	  * @version: 2011-7-12 下午04:24:26
	  * @See:
	 */
	public static String getDefaultSubStr(String str) {
		return getDefaultSubStr(str, 1);
	}

	/**
	 * 
	  * @param str
	  * @param trimLength
	  * @return  
	  * @version: 2011-7-12 下午04:24:30
	  * @See:
	 */
	public static String getDefaultSubStr(String str, int trimLength) {
		return StringUtils.substring(str, 0, str.length() - trimLength);
	}

	/**
	 * 
	  * @param str
	  * @param searchStrs
	  * @return  
	  * @version: 2011-7-12 下午04:24:33
	  * @See:
	 */
	public static int getSearchStrIndex(String str, String[] searchStrs) {
		return StringUtils.indexOfAny(str, searchStrs);
	}

	/**
	 * 
	  * @param str
	  * @param index
	  * @param tag
	  * @return  
	  * @version: 2011-7-12 下午04:24:38
	  * @See:
	 */
	public static String getSpeStrForLn(String str, int index, boolean tag) {
		int strLn = str.length();

		if (strLn < index) {
			return str;
		}

		return tag ? StringUtils.substring(str, 0, index) : StringUtils.substring(str, index, strLn);
	} // 如果str的长度达不到referLn则在str后面补空串

	/**
	 * 
	  * @param str
	  * @param referLn
	  * @return  
	  * @version: 2011-7-12 下午04:24:49
	  * @See:
	 */
	public static String compareWithReferLn(String str, int referLn) {
		int strLn = str.length();

		if (strLn < referLn) {
			StringBuilder sb = new StringBuilder(str);
			int blankLn = referLn - strLn;

			for (int i = 0; i < blankLn; i++) {
				sb.append(" ");
			}

			return sb.toString();
		}

		return str;
	}

	/**
	 * 
	  * @param str
	  * @return  
	  * @version: 2011-7-12 下午04:24:53
	  * @See:
	 */
	public static String trimWhitespace(String str) {
		if (checkIsEmpty(str)) {
			return str;
		}

		StringBuilder buf = new StringBuilder(str);

		while ((buf.length() > 0) && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}

		while ((buf.length() > 0) && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}

		return buf.toString();
	}

	/**
	 * 
	  * @param str
	  * @return  
	  * @version: 2011-7-12 下午04:24:57
	  * @See:
	 */
	public static String trimAllWhitespace(String str) {
		if (checkIsEmpty(str)) {
			return str;
		}

		StringBuilder buf = new StringBuilder(str);
		int index = 0;

		while (buf.length() > index) {
			if (Character.isWhitespace(buf.charAt(index))) {
				buf.deleteCharAt(index);
			} else {
				index++;
			}
		}

		return buf.toString();
	}

	/**
	 * 
	  * @param str
	  * @return  
	  * @version: 2011-7-12 下午04:25:02
	  * @See:
	 */
	public static String trimXML(String str) {
		return trimXML(str, "\n");
	}

	/**
	 * 
	  * @param str
	  * @param split
	  * @return  
	  * @version: 2011-7-12 下午04:25:06
	  * @See:
	 */
	public static String trimXML(String str, String split) {
		if (checkIsEmpty(str)) {
			return str;
		}

		str = str.trim();

		String[] lineXMLParts = str.split(split);

		StringBuilder buffer = new StringBuilder(1024);

		for (int i = 0, ln = lineXMLParts.length; i < ln; i++) {
			buffer.append(lineXMLParts[i].trim());
		}

		return buffer.toString();
	}

	/**
	 * 
	  * @param str
	  * @param indexLn
	  * @return  
	  * @version: 2011-7-12 下午04:25:12
	  * @See:
	 */
	public static String getBeginStrByIndex(String str, int indexLn) {
		return StringUtils.substring(str, 0, indexLn);
	}

	/**
	 * 
	  * @param str
	  * @param indexLn
	  * @return  
	  * @version: 2011-7-12 下午04:25:17
	  * @See:
	 */
	public static String getEndStrByIndex(String str, int indexLn) {
		int end = str.length();
		int start = (indexLn < end) ? (end - indexLn) : (indexLn - end);

		return StringUtils.substring(str, start, end);
	}

	/**
	 * 
	  * @param str
	  * @param removeStr
	  * @return  
	  * @version: 2011-7-12 下午04:25:22
	  * @See:
	 */
	public static String removeStartStr(String str, String removeStr) {
		return StringUtils.removeStart(str, removeStr);
	}

	/**
	 * 
	  * @param str
	  * @param removeStr
	  * @return  
	  * @version: 2011-7-12 下午04:25:25
	  * @See:
	 */
	public static String removeEndStr(String str, String removeStr) {
		return StringUtils.removeEnd(str, removeStr);
	}

	/**
	 * 
	  * @param str
	  * @param removed
	  * @return  
	  * @version: 2011-7-12 下午04:25:28
	  * @See:
	 */
	public static String removeStr(String str, String removed) {
		return StringUtils.remove(str, removed);
	}
	
	/**
	 * 
	  * @param str
	  * @param start
	  * @param end
	  * @return  
	  * @version: 2011-7-12 下午04:25:31
	  * @See:
	 */
	public static String getSubString(String str, String start, String end) {
		return StringUtils.substringBetween(str, start, end);
	}
	
	/**
	 * 
	  * @param str
	  * @param maxLength
	  * @return  
	  * @version: 2011-7-12 下午04:25:34
	  * @See:
	 */
	public static String getCompactStr(String str, int maxLength) {
		int ln = str.length();
		if (ln < maxLength) {
			return str;
		}
		return str.substring(0, maxLength);
	}	
	
    /**
     * 
      * @param splitItems
      * @return  
      * @version: 2011-7-12 下午04:30:45
      * @See:
     */
	public static String[] getSupportSplitItemGroup(String splitItems) {
		String[] splitItemGroup = new String[] {};

		if (checkIsEmpty(splitItems)) {
			return splitItemGroup;
		}

		int count = 0;

		for (String split : SUPPORT_SPLITS) {
			String[] checkedSplitItemGroup = splitItems.split(split);
			int ln = checkedSplitItemGroup.length;

			if (ln == 1) {
				count++;

				if (count == SUPPORT_SPLITS.length) {
					splitItemGroup = checkedSplitItemGroup;

					break;
				}

				continue;
			}

			if (ln > 1) {
				splitItemGroup = checkedSplitItemGroup;

				break;
			}
		}

		return splitItemGroup;
	}
	
	/**
	 * 
	  * @param list
	  * @return  
	  * @version: 2011-7-12 下午04:31:42
	  * @See:
	 */
	public static String filterListToString(List list) {
		StringBuilder sb = new StringBuilder(MAX_BUFFER_SIZE);
		for (Iterator iter = list.listIterator(); iter.hasNext();) {
			Object obj = iter.next();
			String str = (obj instanceof byte[]) ? new String((byte[]) obj) : ObjectUtils.nullSafeToString(obj);
			sb.append("[").append(str).append("]");
		}
		return sb.toString();
	}

	/**
	 * 
	  * @param message
	  * @return  
	  * @version: 2011-7-12 下午04:31:45
	  * @See:
	 */
	public static byte[] string2Bytes(String message) {
		int cnt = 0;
		for (int i = 0; i < message.length(); i++) {
			if ((message.charAt(i) & 0xff00) != 0) {
				cnt++;
				break;
			}
		}
		// 2-byte presentation?
		if (cnt == 0) {
			return message.getBytes();
		}

		try {
			byte[] msg = message.getBytes(GBK_ENCODING);
			if (msg.length == (message.length() + cnt)) {
				return msg;
			}
		} catch (Exception e) {
			return new byte[] {};
		}

		// GBK presentation?
		byte[] msg = new byte[message.length() + cnt];
		for (int i = 0; i < msg.length;) {
			char c = message.charAt(i);
			if ((c & 0xff00) != 0) {
				msg[i++] = (byte) ((c >> 8) & 0xff);
			}
			msg[i++] = (byte) (c & 0xff);
		}
		return msg;
	}

	/**
	 * 
	  * @param bcd
	  * @return  
	  * @version: 2011-7-12 下午04:31:51
	  * @See:
	 */
	public static String bytesToHexStr(byte[] bcd) {

		StringBuilder s = new StringBuilder(bcd.length * 2 + 1);

		for (int i = 0, ln = bcd.length; i < ln; i++) {
			s.append(BCD_LOOKUP[(bcd[i] >>> 4) & 0x0f]);
			s.append(BCD_LOOKUP[bcd[i] & 0x0f]);
		}

		return s.toString();
	}

	/**
	 * 
	  * @param s
	  * @return  
	  * @version: 2011-7-12 下午04:31:55
	  * @See:
	 */
	public static byte[] hexStrToBytes(String s) {

		byte[] bytes = new byte[s.length() / 2];

		for (int i = 0, ln = bytes.length; i < ln; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
		}

		return bytes;
	}

	/**
	 * 
	  * @param max
	  * @return  
	  * @version: 2011-7-12 下午04:31:59
	  * @See:
	 */
	public static String[] getFormatMaxArray(int max) {
		if (max < 0) {
			return new String[] {};
		}

		String[] array = new String[max];

		for (int i = 0; i < max; i++) {
			String _max = (i < 9) ? "0".concat(String.valueOf(i)) : String.valueOf(i);
			array[i] = _max;
		}

		return array;
	}

	/**
	 * 
	  * @param msgLine
	  * @param splitTag
	  * @return  
	  * @version: 2011-7-12 下午04:32:03
	  * @See:
	 */
	public static String[] getMsgLineArrayInSplit(String msgLine, String splitTag) {
		if (msgLine.endsWith(splitTag)) {
			msgLine = new StringBuilder(100).append(msgLine).append(BLANK).toString();
		}

		return msgLine.split(REG_SYBOL.concat(splitTag));
	}
	
	//--------------------------------------------------------------------------------------------

	/**
	 * 
	  * @param array
	  * @param index
	  * @return  
	  * @version: 2011-7-12 下午04:25:47
	  * @See:
	 */
	public static Object[] removeArray(Object[] array, int index) {
		return ArrayUtils.remove(array, index);
	}

	/**
	 * 
	  * @param array
	  * @param obj
	  * @return  
	  * @version: 2011-7-12 下午04:25:50
	  * @See:
	 */
	public static Object[] removeArray(Object[] array, Object obj) {
		return ArrayUtils.removeElement(array, obj);
	}

	/**
	 * 
	  * @param array
	  * @param removeArray
	  * @return  
	  * @version: 2011-7-12 下午04:25:54
	  * @See:
	 */
	public static Object[] removeArray(Object[] array, Object[] removeArray) {
		for (Object obj : removeArray) {
			array = removeArray(array, obj);
		}

		return array;
	}

	/**
	 * 
	  * @param array
	  * @param removeArray
	  * @return  
	  * @version: 2011-7-12 下午04:25:58
	  * @See:
	 */
	public static String[] removeStrArray(String[] array, String[] removeArray) {
		return (String[]) removeArray(array, removeArray);
	}

	/**
	 * 
	  * @param array
	  * @param index
	  * @return  
	  * @version: 2011-7-12 下午04:26:01
	  * @See:
	 */
	public static String[] removeStrArray(String[] array, int index) {
		return (String[]) removeArray(array, index);
	}
	
    /**
     * 
      * @param array
      * @param split
      * @return  
      * @version: 2011-7-12 下午04:26:06
      * @See:
     */
    public static String getStrArrayBySplit(String[] array, String split) {
		StringBuilder sb = new StringBuilder();

		for (String str : array) {
			sb.append(str).append(split);
		}

		return getDefaultSubStr(sb.toString(), split.length());
	}
    
    /**
     * 
      * @param array
      * @return  
      * @version: 2011-7-12 下午04:26:16
      * @See:
     */
    public static Object[] getOrigAndSortedArray(Object[] array) {
		Object[] origArray = array.clone();
		Arrays.sort(array);
		return new Object[] { origArray, array };
	}
    
    /**
     * 
      * @param array
      * @return  
      * @version: 2011-7-12 下午04:32:47
      * @See:
     */
	public static String filterArray2Str(Object[] array) {
		return ObjectUtils.nullSafeToString(array);
	}

    //------------------------------------------------Group------------------------------------------------------------------------------
	public static Paginater getColPage(Collection  list, int pageNumber, int pageSize) {
		Map<Integer, Collection> pageListMap = getGroupColMap(pageSize, list);

		Collection pageList = pageListMap.get(pageNumber);

		if (CommonHelper.checkIsEmpty(pageList)) {
			return Paginater.EMPTY;
		}

		Paginater page = new Paginater(list.size(), pageNumber, pageSize);

		page.setData(pageList);

		return page;
	}

	//------------------------------------------------Group------------------------------------------------------------------------------
	public static Paginater getListPage(List  list, int pageNumber, int pageSize) {
		Map<Integer, List> pageListMap = getGroupListMap(pageSize, list);
		
		List pageList = pageListMap.get(pageNumber);
		
		if (CommonHelper.checkIsEmpty(pageList)) {
			return Paginater.EMPTY;
		}
		
		Paginater page = new Paginater(list.size(), pageNumber, pageSize);
		
		page.setData(pageList);
		
		return page;
	}
	
	/**
	 * 
	  * @param size
	  * @param groupSize
	  * @return  
	  * @version: 2011-7-12 下午04:26:27
	  * @See:
	 */
	public static int getGroupNo(int size, int groupSize) {
		return ((size % groupSize) == 0) ? (int) (size / groupSize) : ((int) (size / groupSize) + 1);
	}

	/**
	 * 
	  * @param groupSize
	  * @param list
	  * @return  
	  * @version: 2011-7-12 下午04:26:38
	  * @See:
	 */
	public static Map<Integer, List> getGroupListMap(int groupSize, List list) {
		if (CommonHelper.checkIsEmpty(list)) {
			return Collections.<Integer, List> emptyMap();
		}

		Map<Integer, List> groupArrayMap = new HashMap<Integer, List>();

		int listSize = list.size();

		if (listSize <= groupSize) {
			groupArrayMap.put(1, list);
		} else {
			int groupNo = getGroupNo(listSize, groupSize);

			for (int i = 0; i < groupNo; i++) {
				groupArrayMap.put(i + 1, getArrangeList(i, groupSize, listSize, list));
			}
		}

		return groupArrayMap;

	}
	
	/**
	 * 
	  * @param groupSize
	  * @param list
	  * @return  
	  * @version: 2011-7-14 下午04:36:37
	  * @See:
	 */
	public static Map<Integer, Collection> getGroupColMap(int groupSize, Collection list) {
		if (CommonHelper.checkIsEmpty(list)) {
			return Collections.<Integer,Collection>emptyMap();
		}

		Map<Integer, Collection> groupArrayMap = new HashMap<Integer, Collection>();

		int listSize = list.size();

		if (listSize <= groupSize) {
			groupArrayMap.put(1, list);
		} else {
			int groupNo = getGroupNo(listSize, groupSize);

			for (int i = 0; i < groupNo; i++) {
				groupArrayMap.put(i + 1, getArrangeCol(i, groupSize, listSize, list));
			}
		}

		return groupArrayMap;
	}

	/**
	 * 
	  * @param groupSize
	  * @param array
	  * @return  
	  * @version: 2011-7-12 下午04:26:44
	  * @See:
	 */
	public static Map<Integer, List> getGroupListMap(int groupSize, Object[] array) {
		if (checkIsEmpty(array)) {
			return Collections.<Integer, List> emptyMap();
		}

		Map<Integer, List> groupArrayMap = new HashMap<Integer, List>();

		int arrayLn = array.length;

		if (arrayLn <= groupSize) {
			groupArrayMap.put(1, Arrays.asList(array));
		} else {
			int groupNo = getGroupNo(arrayLn, groupSize);

			for (int i = 0; i < groupNo; i++) {
				groupArrayMap.put(i + 1, getArrangeList(i, groupSize, arrayLn, array));
			}
		}

		return groupArrayMap;
	}

	/**
	 * 
	  * @param groupSize
	  * @param array
	  * @return  
	  * @version: 2011-7-12 下午04:26:49
	  * @See:
	 */
	public static List<Object[]> getGroupArrayList(int groupSize, Object[] array) {
		if (checkIsEmpty(array)) {
			return Collections.<Object[]> emptyList();
		}

		int arrayLn = array.length;

		if (arrayLn <= groupSize) {
			return Arrays.<Object[]> asList(array);
		}

		int groupNo = getGroupNo(arrayLn, groupSize);

		List<Object[]> groupArrayList = new ArrayList<Object[]>(groupNo);

		for (int i = 0; i < groupNo; i++) {
			Object[] groupNoArray = getArrangeArray(i, groupSize, arrayLn, array);

			groupArrayList.add(groupNoArray);

		}

		return groupArrayList;
	}

	/**
	 * 
	  * @param i
	  * @param groupSize
	  * @param arrayLn
	  * @param list
	  * @return  
	  * @version: 2011-7-12 下午04:26:55
	  * @See:
	 */
	private static List getArrangeList(int i, int groupSize, int arrayLn, List list) {
		int from = i * groupSize;

		int to = (from + groupSize - 1);

		List arrangeList = new ArrayList(to - from);

		for (int j = from; (j <= to) && (j < arrayLn); j++) {
			arrangeList.add(list.get(j));
		}

		return arrangeList;
	}
	
	/**
	 * 
	  * @param i
	  * @param groupSize
	  * @param arrayLn
	  * @param list
	  * @return  
	  * @version: 2011-7-14 下午04:38:35
	  * @See:
	 */
	private static Collection getArrangeCol(int i, int groupSize, int arrayLn, Collection list) {
		int from = i * groupSize;

		int to = (from + groupSize - 1);

		List wrapList = new ArrayList(list);
		List arrangeList = new ArrayList(to - from);

		for (int j = from; (j <= to) && (j < arrayLn); j++) {
			arrangeList.add(wrapList.get(j));
		}

		return arrangeList;
	}

	/**
	 * 
	  * @param i
	  * @param groupSize
	  * @param arrayLn
	  * @param array
	  * @return  
	  * @version: 2011-7-12 下午04:27:01
	  * @See:
	 */
	private static List getArrangeList(int i, int groupSize, int arrayLn, Object[] array) {
		int from = i * groupSize;

		int to = (from + groupSize - 1);

		List arrangeList = new ArrayList(to - from);

		for (int j = from; (j <= to) && (j < arrayLn); j++) {
			arrangeList.add(array[j]);
		}

		return arrangeList;
	}

	/**
	 * 
	  * @param i
	  * @param groupSize
	  * @param arrayLn
	  * @param array
	  * @return  
	  * @version: 2011-7-12 下午04:27:07
	  * @See:
	 */
	private static Object[] getArrangeArray(int i, int groupSize, int arrayLn, Object[] array) {
		int from = i * groupSize;

		int to = (from + groupSize - 1);

		List<Object> arrangeList = new ArrayList<Object>(to - from);

		for (int j = from; (j <= to) && (j < arrayLn); j++) {
			arrangeList.add(array[j]);
		}

		return arrangeList.toArray(new Object[arrangeList.size()]);
	}


	// --------------------------------Calendar---------------------------------------
	/**
	 * 
	  * @param timeZone
	  * @return  
	  * @version: 2011-7-12 下午04:32:58
	  * @See:
	 */
	public static boolean checkTimeZone(String timeZone) {
		if (checkIsEmpty(timeZone)) {
			throw new IllegalArgumentException("Time Zone Id can't be empty");
		}

		String[] availableIds = TimeZone.getAvailableIDs();

		for (String zoneId : availableIds) {
			if (timeZone.equalsIgnoreCase(zoneId)) {
				return true;
			}

			continue;
		}

		return false;
	}

	/**
	 * 
	  * @return  
	  * @version: 2011-7-12 下午04:33:05
	  * @See:
	 */
	public static Calendar getCalendar() {
		TimeZone defaultZone = TimeZone.getDefault();

		return GregorianCalendar.getInstance(defaultZone);
	}

	/**
	 * 
	  * @param timeZone
	  * @return  
	  * @version: 2011-7-12 下午04:33:07
	  * @See:
	 */
	public static TimeZone getMarketZone(String timeZone) {
		if (checkTimeZone(timeZone)) {
			return TimeZone.getTimeZone(timeZone);
		}

		throw new IllegalArgumentException("You have set invalid timeZone can't be created");
	}

	/**
	 * 
	  * @param timeZone
	  * @return  
	  * @version: 2011-7-12 下午04:33:10
	  * @See:
	 */
	public static Calendar getCalendar(String timeZone) {
		TimeZone zone = TimeZone.getTimeZone(timeZone);

		return GregorianCalendar.getInstance(zone);
	}

	/**
	 * 
	  * @return  
	  * @version: 2011-7-12 下午04:33:13
	  * @See:
	 */
	public static Date getCalendarDate() {
		Calendar defaultCalendar = getCalendar();

		return defaultCalendar.getTime();
	}

	/**
	 * 
	  * @param timeZone
	  * @return  
	  * @version: 2011-7-12 下午04:33:17
	  * @See:
	 */
	public static Date getCalendarDate(String timeZone) {
		Calendar zoneCalendar = getCalendar(timeZone);

		return zoneCalendar.getTime();
	}

    /**
     * 
      * @param timeZone
      * @param secondToLive
      * @return  
      * @version: 2011-7-12 下午04:33:23
      * @See:
     */
	public static Date getCalendarDate(String timeZone, int secondToLive) {
		Calendar zoneCalendar = getCalendar(timeZone);
		zoneCalendar.add(Calendar.SECOND, secondToLive);

		return zoneCalendar.getTime();
	}

	/**
	 * 
	  * @return  
	  * @version: 2011-7-12 下午04:33:26
	  * @See:
	 */
	public static long getInnerSeqNum() {
		return getCalendar().getTimeInMillis();
	}

	/**
	 * 
	  * @param timeZone
	  * @return  
	  * @version: 2011-7-12 下午04:33:32
	  * @See:
	 */
	public static long getInnerSeqNum(String timeZone) {
		return getCalendar(timeZone).getTimeInMillis();
	}

	/**
	 * 
	  * @param timeZone
	  * @return  
	  * @version: 2011-7-12 下午04:33:35
	  * @See:
	 */
	public static int getTimeZoneOffset(String timeZone) {
		Calendar zoneCalendar = getCalendar(timeZone);
		Calendar localeCalendar = getCalendar();

		long offSet = zoneCalendar.get(Calendar.ZONE_OFFSET) - localeCalendar.get(Calendar.ZONE_OFFSET);

		return Long.valueOf(offSet).intValue();
	}

	/**
	 * 
	  * @param timeZone
	  * @return  
	  * @version: 2011-7-12 下午04:33:41
	  * @See:
	 */
	public static Date getSyncTime(String timeZone) {
		int offSet = getTimeZoneOffset(timeZone);
		Date localeDate = getCalendarDate();
		return DateUtils.addMilliseconds(localeDate, offSet);
	}

	/**
	 * 
	  * @return  
	  * @version: 2011-7-12 下午04:33:44
	  * @See:
	 */
	public static String getCurrentTimeStr() {
		return new Timestamp(getCalendarDate().getTime()).toString();
	}

	/**
	 * 
	  * @param timeZone
	  * @return  
	  * @version: 2011-7-12 下午04:33:48
	  * @See:
	 */
	public static String getCurrentTimeStr(String timeZone) {
		return new Timestamp(getCalendarDate(timeZone).getTime()).toString();
	}

	/**
	 * 
	  * @return  
	  * @version: 2011-7-12 下午04:33:51
	  * @See:
	 */
	public static Date getYesterDayDate() {
		return getCompareDate(-1);
	}

	/**
	 * 
	  * @param date
	  * @return  
	  * @version: 2011-7-12 下午04:33:54
	  * @See:
	 */
	public static Date getDateBefore(Date date) {
		return DateUtils.addDays(date, -1);
	}

	/**
	 * 
	  * @param marketZone
	  * @return  
	  * @version: 2011-7-12 下午04:33:57
	  * @See:
	 */
	public static Date getYesterDayDate(String marketZone) {
		return getCompareDate(marketZone, -1);
	}

	/**
	 * 
	  * @return
	  * @throws ParseException  
	  * @version: 2011-7-12 下午04:34:01
	  * @See:
	 */
	public static Date getCommonDate() throws ParseException {
		Date localeDate = getCalendarDate();

		return getCommonDate(localeDate);
	}

	/**
	 * 
	  * @param marketZone
	  * @return
	  * @throws ParseException  
	  * @version: 2011-7-12 下午04:34:05
	  * @See:
	 */
	public static Date getCommonDate(String marketZone) throws ParseException {
		Date timeZoneDate = getCalendarDate(marketZone);

		return getCommonDate(timeZoneDate);
	}

	/**
	 * 
	  * @param date
	  * @return
	  * @throws ParseException  
	  * @version: 2011-7-12 下午04:34:07
	  * @See:
	 */
	public static Date getCommonDate(Date date) throws ParseException {
		String dateStr = getDateFormatStr(date, DATE_PATTERN[0]);

		return getParseDate(dateStr, new String[] { DATE_PATTERN[0] });
	}

	/**
	 * 
	  * @param amount
	  * @return  
	  * @version: 2011-7-12 下午04:34:32
	  * @See:
	 */
	public static Date getCompareDate(int amount) {
		Date localeDate = getCalendarDate();
		return getCompareDate(localeDate, amount);
	}

	/**
	 * 
	  * @param date
	  * @param amount
	  * @return  
	  * @version: 2011-7-12 下午04:34:35
	  * @See:
	 */
	public static Date getCompareDate(Date date, int amount) {
		return DateUtils.addDays(date, amount);
	}

	/**
	 * 
	  * @param timeZone
	  * @param amount
	  * @return  
	  * @version: 2011-7-12 下午04:34:39
	  * @See:
	 */
	public static Date getCompareDate(String timeZone, int amount) {
		Date timeZoneDate = getCalendarDate(timeZone);

		return getCompareDate(timeZoneDate, amount);
	}

	/**
	 * 
	  * @param amount
	  * @return  
	  * @version: 2011-7-12 下午04:34:42
	  * @See:
	 */
	public static Date getCompareDateForMonth(int amount) {
		return getCompareDateForMonth(getCalendarDate(), amount);
	}

	/**
	 * 
	  * @param timeZone
	  * @param amount
	  * @return  
	  * @version: 2011-7-12 下午04:34:45
	  * @See:
	 */
	public static Date getCompareDateForMonth(String timeZone, int amount) {
		Date timeZoneDate = getCalendarDate(timeZone);

		return getCompareDateForMonth(timeZoneDate, amount);
	}

	/**
	 * 
	  * @param date
	  * @param amount
	  * @return  
	  * @version: 2011-7-12 下午04:34:47
	  * @See:
	 */
	public static Date getCompareDateForMonth(Date date, int amount) {
		return DateUtils.addMonths(date, amount);
	}

	/**
	 * 
	  * @param amount
	  * @return  
	  * @version: 2011-7-12 下午04:34:50
	  * @See:
	 */
	public static Date getCompareDateForYear(int amount) {
		return getCompareDateForYear(getCalendarDate(), amount);
	}

	/**
	 * 
	  * @param timeZone
	  * @param amount
	  * @return  
	  * @version: 2011-7-12 下午04:34:53
	  * @See:
	 */
	public static Date getCompareDateForYear(String timeZone, int amount) {
		Date timeZoneDate = getCalendarDate(timeZone);
		return getCompareDateForYear(timeZoneDate, amount);
	}

	/**
	 * 
	  * @param date
	  * @param amount
	  * @return  
	  * @version: 2011-7-12 下午04:34:56
	  * @See:
	 */
	public static Date getCompareDateForYear(Date date, int amount) {
		return DateUtils.addYears(date, amount);
	}

	/**
	 * 
	  * @param date1
	  * @param date2
	  * @return  
	  * @version: 2011-7-12 下午04:35:00
	  * @See:
	 */
	public static boolean compareDate(Date date1, Date date2) {
		return DateUtils.isSameDay(date1, date2);
	}

	/**
	 * 
	  * @param date
	  * @return  
	  * @version: 2011-7-12 下午04:36:43
	  * @See:
	 */
	public static String getCommonDateFormatStr(Date date) {
		return DateFormatUtils.format(date, DATE_PATTERN[0]);
	}

	/**
	 * 
	  * @return  
	  * @version: 2011-7-12 下午04:36:48
	  * @See:
	 */
	public static String getCommonDateFormatStr() {
		return DateFormatUtils.format(getCalendarDate(), DATE_PATTERN[0]);
	}

	/**
	 * 
	  * @param date
	  * @param pattern
	  * @return  
	  * @version: 2011-7-12 下午04:36:51
	  * @See:
	 */
	public static String getDateFormatStr(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * 
	  * @param str
	  * @return
	  * @throws ParseException  
	  * @version: 2011-7-12 下午04:36:54
	  * @See:
	 */
	public static Date getCommonParseDate(String str) throws ParseException {
		return getParseDate(str, DATE_PATTERN);
	}

	/**
	 * 
	  * @param date
	  * @param executeTime
	  * @return
	  * @throws ParseException  
	  * @version: 2011-7-12 下午04:36:57
	  * @See:
	 */
	public static Date getFormatDateTime(Date date, String executeTime) throws ParseException {
		String dateStr = getCommonDateFormatStr(date);

		return getCommonParseTime(getDateTimeStr(dateStr, executeTime));
	}

	/**
	 * 
	  * @param executeTime
	  * @return
	  * @throws ParseException  
	  * @version: 2011-7-12 下午04:37:00
	  * @See:
	 */
	public static Date getFormatDateTime(String executeTime) throws ParseException {
		return getFormatDateTime(CommonHelper.getCalendarDate(), executeTime);
	}

	/**
	 * 
	  * @param dateStr
	  * @param executeTime
	  * @return  
	  * @version: 2011-7-12 下午04:37:04
	  * @See:
	 */
	private static String getDateTimeStr(String dateStr, String executeTime) {
		return new StringBuilder(dateStr).append(BLANK).append(executeTime).toString();
	}

	/**
	 * 
	  * @param str
	  * @return
	  * @throws ParseException  
	  * @version: 2011-7-12 下午04:37:08
	  * @See:
	 */
	public static Date getCommonParseTime(String str) throws ParseException {
		return getParseDate(str, TIME_PATTERN);
	}

	/**
	 * 
	  * @param str
	  * @param pattern
	  * @return
	  * @throws ParseException  
	  * @version: 2011-7-12 下午04:37:11
	  * @See:
	 */
	public static Date getParseDate(String str, String[] pattern) throws ParseException {
		return DateUtils.parseDate(str, pattern);
	}

	/**
	 * 
	  * @param dateBefore
	  * @param dateAfter
	  * @return
	  * @throws ParseException  
	  * @version: 2011-7-12 下午04:37:15
	  * @See:
	 */
	public static String[] getArrangeDates(String dateBefore, String dateAfter) throws ParseException {
		if (isEmpty(dateBefore)) {
			if (isEmpty(dateAfter)) {
				return new String[] {};
			}
			return (checkDateStr(dateAfter, DATE_PATTERN)) ? new String[] { dateAfter } : new String[] {};
		}

		if (isEmpty(dateAfter)) {
			return (checkDateStr(dateBefore, DATE_PATTERN)) ? new String[] { dateBefore } : new String[] {};
		}

		Date beforeDate = getParseDate(dateBefore, DATE_PATTERN);

		Date afterDate = getParseDate(dateAfter, DATE_PATTERN);

		if (beforeDate == null || afterDate == null) {
			return new String[] {};
		}
		
		if(beforeDate.compareTo(afterDate) == 0) {
			return new String[] {dateBefore};
		}

		return (beforeDate.compareTo(afterDate) < 0) ?
				new String[] { dateBefore, dateAfter } : new String[] { dateAfter,dateBefore };
	}

	/**
	 * 
	  * @param dateBefore
	  * @param dateAfter
	  * @param prefix
	  * @param suffix
	  * @param connect
	  * @return
	  * @throws ParseException  
	  * @version: 2011-7-12 下午04:37:20
	  * @See:
	 */
	public static String[] getArrangeDates(String dateBefore, String dateAfter, String prefix, String suffix, String connect)
			throws ParseException {
		String[] arrangeDates = getArrangeDates(dateBefore, dateAfter);

		if (checkIsEmpty(arrangeDates)) {
			return new String[] {};
		}

		int ln = arrangeDates.length;

		String[] filterDates = new String[ln];

		for (int i = 0; i < ln; i++) {
			filterDates[i] = new StringBuilder().append(prefix).append(connect).append(arrangeDates[i]).append(suffix).toString();
			;
		}

		return filterDates;
	}

	/**
	 * 
	  * @param dateBefore
	  * @param dateAfter
	  * @param datePattern
	  * @return
	  * @throws ParseException  
	  * @version: 2011-7-12 下午04:37:24
	  * @See:
	 */
	public static String[] getAllArrangeDates(String dateBefore, String dateAfter,String datePattern) throws ParseException {
		Date beforeDate = getParseDate(dateBefore, new String[] {datePattern});

		Date afterDate = getParseDate(dateAfter, new String[] {datePattern});
		
		if(beforeDate.compareTo(afterDate) == 0) {
			return new String[] {dateBefore};
		}
		
		beforeDate = (beforeDate.compareTo(afterDate) < 0) ?  beforeDate : afterDate;

		int days = Long.valueOf(getDistDates(beforeDate, afterDate)).intValue();
		
		List<String> arrangDateList = new LinkedList<String>();
		
		arrangDateList.add(dateBefore);
		
		Date compareDate = beforeDate;
		
		for(int i = 0 ; i < days; i ++) {
			compareDate = getCompareDate(compareDate,1);
			
			if(compareDate.compareTo(afterDate) < 0) {
				arrangDateList.add(getDateFormatStr(compareDate,datePattern));
			}
		}
		
		arrangDateList.add(dateAfter);

		return arrangDateList.toArray(new String[arrangDateList.size()]);
	}

	/**
	 * 
	  * @param startDate
	  * @param endDate
	  * @return  
	  * @version: 2011-7-12 下午04:37:30
	  * @See:
	 */
	private static long getDistDates(Date startDate, Date endDate) {
		long totalDate = 0;
		Calendar calendar = getCalendar();
		calendar.setTime(startDate);
		long timestart = calendar.getTimeInMillis();
		calendar.setTime(endDate);
		long timeend = calendar.getTimeInMillis();
		totalDate = Math.abs((timeend - timestart)) / DAY_MILLIS;
		return totalDate;
	}

	/**
	 * 
	  * @param dateBefore
	  * @param dateAfter
	  * @param prefix
	  * @param suffix
	  * @param connect
	  * @return
	  * @throws ParseException  
	  * @version: 2011-7-12 下午04:37:34
	  * @See:
	 */
	public static String[][] getArrangeDateArray(String dateBefore, String dateAfter, String prefix, String suffix, String connect)
			throws ParseException {
		String[] arrangeDates = getArrangeDates(dateBefore, dateAfter);

		if (checkIsEmpty(arrangeDates)) {
			return new String[][] {};
		}

		int ln = arrangeDates.length;

		String[][] filterDates = new String[ln][ln];

		for (int i = 0; i < ln; i++) {
			String filterDate = new StringBuilder().append(prefix).append(connect).append(arrangeDates[i]).append(suffix).toString();
			filterDates[i] = new String[] { arrangeDates[i], filterDate };
		}

		return filterDates;
	}

	/**
	 * 
	  * @param str
	  * @param pattern
	  * @return  
	  * @version: 2011-7-12 下午04:37:40
	  * @See:
	 */
	public static boolean checkDateStr(String str, String pattern) {
		try {
			return (getParseDate(str, new String[] { pattern }) != null);
		} catch (Exception ex) {

		}
		return false;
	}

	/**
	 * 
	  * @param str
	  * @param patterns
	  * @return  
	  * @version: 2011-7-12 下午04:37:46
	  * @See:
	 */
	public static boolean checkDateStr(String str, String[] patterns) {
		try {
			return (getParseDate(str, patterns) != null);
		} catch (Exception ex) {

		}
		return false;
	}

	/**
	 * 
	  * @param str
	  * @param pattern
	  * @return
	  * @throws ParseException  
	  * @version: 2011-7-12 下午04:37:51
	  * @See:
	 */
	public static Date getParseAsCommonDate(String str, String[] pattern) throws ParseException {
		Date parseDate = getParseDate(str, pattern);

		return getCommonDate(parseDate);
	}

	/**
	 * 
	  * @param date
	  * @param gap
	  * @param pattern
	  * @return  
	  * @version: 2011-7-12 下午04:37:54
	  * @See:
	 */
	public static String getFormatDateByHourGap(Date date, int gap, String pattern) {
		Date currentDate = DateUtils.addHours(date, gap);

		return DateFormatUtils.format(currentDate, pattern);
	}

	/**
	 * 
	  * @param weekPeriod
	  * @return  
	  * @version: 2011-7-12 下午04:37:58
	  * @See:
	 */
	public static Date getWeekDateBefore(int weekPeriod) {
		int weekBeforePeriod = filterPeriod(weekPeriod);

		return DateUtils.addWeeks(getCalendarDate(), weekBeforePeriod);
	}

	/**
	 * 
	  * @param timeZone
	  * @param weekPeriod
	  * @return  
	  * @version: 2011-7-12 下午04:38:05
	  * @See:
	 */
	public static Date getWeekDateBefore(String timeZone, int weekPeriod) {
		int weekBeforePeriod = filterPeriod(weekPeriod);

		return DateUtils.addWeeks(getCalendarDate(timeZone), weekBeforePeriod);
	}

	/**
	 * 
	  * @param timeZone
	  * @param weekPeriod
	  * @return  
	  * @version: 2011-7-12 下午04:38:09
	  * @See:
	 */
	public static Date getWeekDateAfter(String timeZone, int weekPeriod) {
		int weekBeforePeriod = filterPeriod(weekPeriod);

		return DateUtils.addWeeks(getCalendarDate(timeZone), -weekBeforePeriod);
	}

	/**
	 * 
	  * @param dayInWeek
	  * @param dayInMonth
	  * @return  
	  * @version: 2011-7-12 下午04:38:14
	  * @See:
	 */
	public static boolean checkOnlyDay(int dayInWeek, int dayInMonth) {
		return checkOnlyDay(getCalendar(), dayInWeek, dayInMonth);
	}

	/**
	 * 
	  * @param timeZone
	  * @param dayInWeek
	  * @param dayInMonth
	  * @return  
	  * @version: 2011-7-12 下午04:38:17
	  * @See:
	 */
	public static boolean checkOnlyDay(String timeZone, int dayInWeek, int dayInMonth) {
		return checkOnlyDay(getCalendar(timeZone), dayInWeek, dayInMonth);
	}

	/**
	 * 
	  * @return  
	  * @version: 2011-7-12 下午04:38:20
	  * @See:
	 */
	public static boolean checkIsWeekend() {
		String defaultTimeZone = TimeZone.getDefault().getID();

		return checkIsWeekend(defaultTimeZone);
	}

	/**
	 * 
	  * @param timeZone
	  * @return  
	  * @version: 2011-7-12 下午04:38:26
	  * @See:
	 */
	public static boolean checkIsWeekend(String timeZone) {
		Calendar zoneCalendar = getCalendar(timeZone);

		return checkIsWeekend(zoneCalendar);
	}

	/**
	 * 
	  * @param date
	  * @return  
	  * @version: 2011-7-12 下午04:38:29
	  * @See:
	 */
	public static boolean checkIsWeekend(Date date) {
		Calendar calendar = getCalendar();
		calendar.setTime(date);

		return checkIsWeekend(calendar);
	}

	/**
	 * 
	  * @param date
	  * @param timeZone
	  * @return  
	  * @version: 2011-7-12 下午04:38:32
	  * @See:
	 */
	public static boolean checkIsWeekend(Date date, String timeZone) {
		Calendar zoneCalendar = getCalendar(timeZone);
		zoneCalendar.setTime(date);

		return checkIsWeekend(zoneCalendar);
	}

	/**
	 * 
	  * @param calendar
	  * @return  
	  * @version: 2011-7-12 下午04:38:47
	  * @See:
	 */
	private static boolean checkIsWeekend(Calendar calendar) {
		int dw = calendar.get(Calendar.DAY_OF_WEEK);

		return ((dw == Calendar.SATURDAY) || (dw == Calendar.SUNDAY));
	}

	/**
	 * 
	  * @param prefixLn
	  * @return  
	  * @version: 2011-7-12 下午04:38:51
	  * @See:
	 */
	public static String getYearPrefix(int prefixLn) {
		int year = getCalendar().get(Calendar.YEAR);

		String yearInfo = Integer.toString(year);

		return yearInfo.substring(0, prefixLn);
	}

	/**
	 * 
	  * @param period
	  * @return  
	  * @version: 2011-7-12 下午04:38:54
	  * @See:
	 */
	private static int filterPeriod(Integer period) {
		int cmpTag = period.compareTo(NumberUtils.INTEGER_ZERO);

		if (cmpTag > 0) {
			return (-period.intValue());
		} else if (cmpTag < 0) {
			return period.intValue();
		} else {
			return NumberUtils.INTEGER_ZERO;
		}
	}

	/**
	 * 
	  * @param calendar
	  * @param dayInWeek
	  * @param dayInMonth
	  * @return  
	  * @version: 2011-7-12 下午04:38:58
	  * @See:
	 */
	private static boolean checkOnlyDay(Calendar calendar, int dayInWeek, int dayInMonth) {
		int dw = calendar.get(Calendar.DAY_OF_WEEK);
		int dm = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);

		return (dw == dayInWeek) && (dm == dayInMonth);
	}

	/**
	 * 
	  * @param date
	  * @param field
	  * @return  
	  * @version: 2011-7-12 下午04:39:03
	  * @See:
	 */
	public static Date getTruncateDate(Date date, int field) {
		return DateUtils.truncate(date, field);
	}

	/**
	 * 
	  * @param dateStr
	  * @param pattern
	  * @return  
	  * @version: 2011-7-12 下午04:39:15
	  * @See:
	 */
	public static Date getTruncateDayOfMonthDate(String dateStr, String pattern) {
		Date date = DateUtil.formatDate(dateStr, pattern);

		return getTruncateDate(date, Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	  * @param date
	  * @return  
	  * @version: 2011-7-12 下午04:39:18
	  * @See:
	 */
	public static Date getMonthBegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 
	  * @param date
	  * @return  
	  * @version: 2011-7-12 下午04:39:21
	  * @See:
	 */
	public static Date getMonthEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1, 0, 0, 0);
		cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) - 1);

		return cal.getTime();
	}

	/**
	 * 
	  * @param date
	  * @return  
	  * @version: 2011-7-12 下午04:39:26
	  * @See:
	 */
	public static Date getWeekBegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Date mm = nDaysAgo(cal.get(Calendar.DAY_OF_WEEK) - 2, date);
		return getDayBegin(mm);
	}

	/**
	 *  <p></p>
	  * @param date
	  * @return  
	  * @version: 2011-7-12 下午04:39:30
	  * @See:
	 */
	public static Date getWeekEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Date mm = nDaysAfter(cal.get(8 - Calendar.DAY_OF_WEEK), date);
		return getDayEnd(mm);

	}

	/**
	 *  <p>得到指定日期几天以后的时刻</p>
	  * @param n    天数(往后)
	  * @param date 指定日期
	  * @return  
	  * @version: 2011-7-12 下午04:39:34
	  * @See:
	 */
	public static Date nDaysAfter(int n, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + n);
		return cal.getTime();
	}
	
    /**
     *  <p>得到指定日期每天开始的时刻(Tue Jul 12 00:00:00 CST 2011)</p>
      * @param date
      * @return  
      * @version: 2011-7-12 下午04:39:40
      * @See:
     */
	public static Date getDayBegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return cal.getTime();
	}

	/**
	 *  <p>得到指定日期几天以前的时刻</p>
	  * @param n       天数(往前)
	  * @param date    指定日期
	  * @return  
	  * @version: 2011-7-12 下午04:39:47
	  * @See:
	 */
	public static Date nDaysAgo(Integer n, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - n);
		return cal.getTime();
	}

	/**
	 * <p>得到指定日期的最后时刻(Tue Jul 12 23:59:59 CST 2011)</p>
	  * @param date  指定日期
	  * @return  
	  * @version: 2011-7-12 下午04:39:53
	  * @See:
	 */
	public static Date getDayEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH) + 1, 0, 0, 0);
		cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) - 1);

		return cal.getTime();
	}
	
	/**
	 *  <p>获取两个日期之间的时间差</p>
	  * @param beforeDate
	  * @param lastDate
	  * @return  
	  * @version: 2011-8-4 下午02:10:49
	  * @See:
	 */
	public static long getCompareDateDiff(Date beforeDate, Date lastDate) {		
		return Math.abs(lastDate.getTime() - beforeDate.getTime());
	}

	// ----------------------Number----------------------------------------------------
	/**
	 * <p>将整数型字符转换成整型</p>
	  * @param str
	  * @return  
	  * @version: 2011-7-12 下午04:40:03
	  * @See:
	 */
	public static int str2Int(String str) {
		return NumberUtils.toInt(str);
	}

	/**
	 *  <p>将整数型字符转换为整型，如果异常用defaultValue替代</p>
	  * @param str
	  * @param defaultValue
	  * @return  
	  * @version: 2011-7-12 下午04:40:15
	  * @See:
	 */
	public static int str2Int(String str, int defaultValue) {
		return NumberUtils.toInt(str, defaultValue);
	}

	/**
	 *  <p>将双精度型字符转换为双精度型,如果异常则用defaultValue替代</p>
	  * @param str
	  * @param defaultValue
	  * @return  
	  * @version: 2011-7-12 下午04:40:20
	  * @See:
	 */
	public static double str2Double(String str, double defaultValue) {
		return NumberUtils.toDouble(str, defaultValue);
	}

	/**
	 *  <p>将双精度型字符转换为双精度型</p>
	  * @param str
	  * @return  
	  * @version: 2011-7-12 下午04:40:23
	  * @See:
	 */
	public static double str2Double(String str) {
		return NumberUtils.toDouble(str);
	}

	/**
	 *  <p>将长整型字符转换为长整型</p>
	  * @param str
	  * @return  
	  * @version: 2011-7-12 下午04:40:27
	  * @See:
	 */
	public static long str2Long(String str) {
		return NumberUtils.toLong(str);
	}

	/**
	  * <p>将长整型字符转换为长整型,如果异常则用defaultValue替代</p>
	  * @param str
	  * @param defaultValue  
	  * @return  
	  * @version: 2011-7-12 下午04:40:32
	  * @See:
	 */
	public static long str2Long(String str, long defaultValue) {
		return NumberUtils.toLong(str, defaultValue);
	}

	/**
	 * <p>将所支持的布尔型字符转换成布尔对象</p>
	  * @param str
	  * @return  
	  * @version: 2011-7-12 下午04:40:36
	  * @See:
	 */
	public static boolean str2Boolean(String str) {
		Boolean result = BooleanUtils.toBooleanObject(str);

		if (result == null) {
			return false;
		}

		return result.booleanValue();
	}
	
	/**
	 * <p>将所支持的布尔型字符转换成布尔对象</p>
	  * @param str
	  * @return  
	  * @version: 2011-7-12 下午04:40:41
	  * @See:
	 */
	public static Boolean toBooleanObject(String str) {
		if (CommonHelper.checkIsEmpty(str)) {
			return Boolean.FALSE;
		}

		Boolean result = BooleanUtils.toBooleanObject(str);

		if (result == null) {
			if (str.equalsIgnoreCase(TRUE_BOOLEAN)) {
				return Boolean.TRUE;
			} else if (str.equalsIgnoreCase(FALSE_BOOLEAN)) {
				return Boolean.FALSE;
			}
		}

		return result;
	}

	/**
	 *  <p>将数值型字符转换为数值型类型</p>
	  * @param value
	  * @return  
	  * @version: 2011-7-12 下午04:40:47
	  * @See:
	 */
	public static BigDecimal getDecimalFromStr(String value) {
		return NumberUtils.createBigDecimal(value);
	}

	/**
	 * <p>根据缺省金融数字化格式类对数值型进行格式化</p>
	  * @param big
	  * @return  
	  * @version: 2011-7-12 下午04:40:52
	  * @See:
	 */
	public static String getCommonFormatAmt(BigDecimal big) {
		return commonAmtFormat.format(big.doubleValue());
	}
	
	/**
	 *  <p>比较两个双精度类型值大小</p>
	  * @param d1
	  * @param d2
	  * @return  
	  * @version: 2011-7-12 下午04:40:57
	  * @See:
	 */
	public static int compareDouble(double d1, double d2) {
		return NumberUtils.compare(d1, d2);
	}
	
	//-------------------------------------------Throwable--------------------------------------------
    /**
     *  <p>获得异常链主要堆栈(原因)信息</p>
      * @param th
      * @return  
      * @version: 2011-7-12 下午04:41:10
      * @See:
     */
	public static String getRootCauseMessage(Throwable th) {
		return ExceptionUtils.getRootCauseMessage(th);
	}

	/**
	 *  <p>获得异常堆栈信息</p>
	  * @param th
	  * @return  
	  * @version: 2011-7-12 下午04:41:18
	  * @See:
	 */
	public static String getStackTraceMessage(Throwable th) {
		return ExceptionUtils.getStackTrace(th);
	}

	/**
	 *  <p>获得异常的完整堆栈信息</p>
	  * @param th
	  * @return  
	  * @version: 2011-7-12 下午04:41:21
	  * @See:
	 */
	public static String getFullStackTraceMessage(Throwable th) {
		return ExceptionUtils.getFullStackTrace(th);
	}

	/**
	 * 
	 * <p>根据会话获得随机的id</p>
	 * @param request
	 * @return
	 * @version: 2011-1-8 下午01:40:48
	 * @See:
	 */
	public static String getRandomSession(HttpServletRequest request) {
		return new StringBuilder(100).append(WebUtils.getSessionId(request)).append(System.currentTimeMillis()).toString();
	}

	/**
	 * 
	 * <p>检查证书是否在指定的日期范围内有效</p>
	 * @param certificate  证书
	 * @param date    指定日期
	 * @return
	 * @version: 2011-1-8 下午01:40:21
	 * @See:
	 */
	public static boolean checkCertificateValid(Certificate certificate, Date date) {
		if (certificate == null) {
			return false;
		}

		if (!(certificate instanceof X509Certificate)) {
			return false;
		}

		X509Certificate x509Certificate = (X509Certificate) certificate;

		try {
			if (date == null) {
				x509Certificate.checkValidity();
			} else {
				x509Certificate.checkValidity(date);
			}
		} catch (Exception ex) {
			return false;
		}

		return true;
	}
	
	
	public static void main(String[] args) throws Exception  {
		System.out.println(CommonHelper.getWeekEnd(new Date()));
		
		Date date1 = CommonHelper.getFormatDateTime("0300");
		
		System.out.println("" + CommonHelper.getCompareDateDiff(CommonHelper.getCompareDate(date1,1),new Date()));
		System.out.println("" + CommonHelper.getCompareDateDiff(date1,new Date()));
	}

}
