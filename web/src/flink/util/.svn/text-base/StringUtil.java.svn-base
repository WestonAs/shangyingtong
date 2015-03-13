package flink.util;

import java.security.MessageDigest;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class StringUtil {

	private final static byte[] hex = "0123456789ABCDEF".getBytes();
	
	private static final String LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * 账号中可以包含的字符
	 */
	private static final String ACCNO_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-";
	
	public static String quote(String s) {
		return new StringBuffer("'").append(s).append("'").toString();
	}

	public static final String getMD5(String key) throws Exception {
		String password = null;
		byte[] data;
		if (StringUtils.isBlank(key)) {
			return null;
		}
		MessageDigest msg = MessageDigest.getInstance("MD5");
		data = key.trim().getBytes("UnicodeLittleUnmarked");
		byte[] datas = msg.digest(data);
		password = Base64.encodeBase64String(datas);
		
		return password.toUpperCase();
	}

	/**
	 * 分割字符串后再以某种字符组装
	 * 
	 * @param value
	 * @param split
	 * @param contact
	 * @return
	 */
	public static final String rejoin(String value, String split, String contact) {
		if (StringUtils.isEmpty(value)) {
			return StringUtils.EMPTY;
		}
		return StringUtils.join(value.split(split), contact);
	}
	
	/**
	 * 判断一个字符串是否都为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		 for (int i = str.length(); --i >= 0;){   
			 if (!Character.isDigit(str.charAt(i))){
				 return false;
			 }
		 }
		 return true;
	}
	
	public static String bytesToHexString(byte[] b) {
		byte[] buff = new byte[2 * b.length];
		for (int i = 0; i < b.length; i++) {
			buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
			buff[2 * i + 1] = hex[b[i] & 0x0f];
		}
		return new String(buff);
	}
	
	/**
	 * 生成指定长度的随机数字串
	 * 
	 * @param length
	 * @return
	 */
	public static String getNoString(int length) {
		Random random = new Random();
		int upLimit = 10;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(Integer.toString(random.nextInt(upLimit)));
		}
		return buffer.toString();
	}
	
	/**
	 * 生成由数字字母组成的随机串，区分大小写
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		StringBuffer code = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(LETTERS.length());
			code.append(LETTERS.charAt(index));
		}
		return code.toString();
	}
	
	/**
	 * 判断指定的字符串是否符合账号的规范。只能包含数字，字符和“-”
	 * @param accNo
	 * @return
	 */
	public static boolean isCorrectAccNo(String accNo) {
		if (accNo == null) {
			return false;
		}
		for (int i = 0; i < accNo.length(); i++) {
			if (ACCNO_LETTERS.indexOf(accNo.charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(StringUtil.getMD5("qwer1234!@#$12"));
	}

}
