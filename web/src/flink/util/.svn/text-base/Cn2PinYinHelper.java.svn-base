package flink.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * <p>汉语转拼音API处理,用于支持基于拼音的模糊检索(或基于此的构造处理)</p>
 * @Project: Card
 * @File: Cn2PinYinHelper.java
 * @See: http://lavasoft.blog.51cto.com/62575/178320/
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-9
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public abstract class Cn2PinYinHelper {
	private static final Log logger = LogFactory.getLog(Cn2PinYinHelper.class);

	/**
	 * 
	 * <p>获取汉字串拼音首字母，英文字符不变</p>
	 * @param chinese  输入汉字
	 * @return
	 * @version: 2011-6-9 下午01:14:40
	 * @See:
	 */
	public static String cn2FirstSpell(String chinese) {
		if (CommonHelper.checkIsEmpty(chinese)) {
			return "";
		}

		StringBuilder pybf = new StringBuilder();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 128) {
				try {
					String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
					if (_t != null) {
						pybf.append(_t[0].charAt(0));
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pybf.append(arr[i]);
			}
		}
		return pybf.toString().replaceAll("\\W", "").trim();
	}

	/**
	 * 
	 * <p>获取汉字串拼音，英文字符不变</p>
	 * @param chinese  输入汉字
	 * @return
	 * @version: 2011-6-9 下午01:14:45
	 * @See:
	 */
	public static String cn2Spell(String chinese) {
		if (CommonHelper.checkIsEmpty(chinese)) {
			return "";
		}

		StringBuilder pybf = new StringBuilder();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0, ln = arr.length; i < ln; i++) {
			if (arr[i] > 128) {
				try {

					pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
				} catch (Exception ex) {
//					logger.debug("传入中文[" + chinese + "]引发格式化问题,原因[" + ex.getMessage() + "]");
				}
			} else {
				pybf.append(arr[i]);
			}
		}
		return pybf.toString();
	}

	public static void main(String[] args) throws Exception {
		String test = "商务机构 【功能测试】";
		logger.debug("");
		System.out.println(cn2Spell(test));
	}

}
