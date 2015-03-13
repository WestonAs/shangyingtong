package gnete.card.util;

import flink.util.SpringContext;
import gnete.card.dao.CardInfoDAO;
import gnete.card.entity.CardInfo;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class CardUtil {
	
	/**
	 * 卡号的校验位计算
	 * 
	 * @param cardId
	 *            卡号
	 * @return
	 */
	public static final String luhnMod10(String cardNumber) {
		int i, odd, sum;
		int[][] table = { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 },
				{ 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 } };

		for (i = cardNumber.length() - 1, odd = 0, sum = 0; i >= 0; i--) {
			if (Character.isDigit(cardNumber.charAt(i))) {
				odd = 1 - odd;
				sum += table[odd][cardNumber.charAt(i) - '0'];
			}
		}
		sum %= 10;
		return Integer.toString((sum != 0 ? 10 - sum : 0)); 

	}
	
	/**
	 * 根据起始卡号和卡数量取得卡数组
	 * @param startCard
	 * @param count
	 * @return
	 */
	public static final String[] getCard(String startCard, int count) {
		if (StringUtils.isEmpty(startCard)) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		String[] cardArray = new String[count];
		// 取第12到18位，即流水号
		String strCardNo = startCard.substring(11, 18);
		String prixStr = startCard.substring(0, 11);
		for (int i = 0; i < count; i++){
			
//			BigDecimal b = new BigDecimal(i);
//			BigDecimal result = b.add(new BigDecimal(strCardNo));
			
			Long result = i + NumberUtils.toLong(strCardNo);
			
			String cardpre = prixStr + StringUtils.leftPad(result.toString(), 7, "0");
			cardArray[i] = cardpre + CardUtil.luhnMod10(cardpre);
		}
		return cardArray;
	}
	
	/**
	 * 根据起始卡号，卡数量得到结束卡号
	 * @param startCard
	 * @param count
	 * @return
	 */
	public static final String getEndCardId(String startCard, int count) {
		if (StringUtils.isEmpty(startCard)
				|| Integer.valueOf(count) == null) {
			return StringUtils.EMPTY;
		}
		// 新卡号的规则 3位系统号 + 6位卡BIN号 + 2位卡类型号 + 7流水号 + 1位校验位
		// 取第12到18位，即流水号
		String strCardNo = startCard.substring(11, 18);
		String prixStr = startCard.substring(0, 11);
//		BigDecimal b = new BigDecimal(count);
//		BigDecimal result = AmountUtil.subtract(AmountUtil.add(b, new BigDecimal(strCardNo)), new BigDecimal(1));
		
		Long result = count + NumberUtils.toLong(strCardNo) - 1;
		
		String cardpre = prixStr + StringUtils.leftPad(result.toString(), 7, "0");
		return cardpre + luhnMod10(cardpre);
	}
	
	/**
	 * 取得旧卡的结束卡号（第一种规则）
	 * @param startCard
	 * @param count
	 * @return
	 */
	public static final String getOldEndCardId(String startCard, int count) {
		if (StringUtils.isEmpty(startCard)
				|| Integer.valueOf(count) == null) {
			return StringUtils.EMPTY;
		}
		
		// 旧卡号的规则：3位系统号 + 4位金额 + 3位组别号 + 6位流水号 + 1位类型 + 1位校验位
		String prixStr = startCard.substring(0, 10);
		String cardSNo = startCard.substring(10, 16); //  6位流水号
		String type = startCard.substring(16, 17); //类型
		
		Long result = count + NumberUtils.toLong(cardSNo) - 1;
		
		String cardpre = prixStr + StringUtils.leftPad(result.toString(), 6, "0") + type;
		return (cardpre + CardUtil.getOldCardIdEnd(cardpre));
	}
	
	/**
	 *  旧卡号校验位的计算
	 * @param cardPrex
	 * @return
	 */
	public static final String getOldCardIdEnd(String cardPrex) {
		int resultOdd = 0; //奇数位乘以2对10取模后的值
		int resultEven = 0; // 偶数位乘以3对10取模后的值
		
		// 取奇数位。乘以2，对10取模
		for (int i = 0; i < cardPrex.length(); i += 2) {
			char tmp = cardPrex.charAt(i);
			resultOdd += (2*(NumberUtils.toInt(CharUtils.toString(tmp)))) % 10;
		}
		resultOdd %= 10;
		
		// 取偶数位乘以3对10取模后的值
		for (int i = 1; i < cardPrex.length(); i += 2) {
			char tmp = cardPrex.charAt(i);
			resultEven += (3*(NumberUtils.toInt(CharUtils.toString(tmp)))) % 10;
		}
		resultEven %= 10;
		int result = (resultOdd + resultEven) % 10;
		return Integer.toString(result);
	}
	
	/**
	 * 根据起始卡号和卡数量取得卡数组(针对旧卡)(第一种校验规则)
	 * @param startCard
	 * @param count
	 * @return
	 */
	public static final String[] getOldCard(String startCard, int count) {
		if (StringUtils.isEmpty(startCard)
				|| Integer.valueOf(count) == null) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		
		String[] cardArray = new String[count];
		// 旧卡号的规则：3位系统号 + 4位金额 + 3位组别号 + 6位流水号 + 1位类型 + 1位校验位
		String prixStr = startCard.substring(0, 10);
		String cardSNo = startCard.substring(10, 16); //  6位流水号
		String type = startCard.substring(16, 17); //类型

		for (int i = 0; i < count; i++){
			
//			BigDecimal b = new BigDecimal(i);
//			BigDecimal result = b.add(new BigDecimal(cardSNo));
			Long result = i + Long.valueOf(cardSNo);
			
			String cardpre = prixStr + StringUtils.leftPad(result.toString(), 6, "0") + type;
			cardArray[i] = cardpre + CardUtil.getOldCardIdEnd(cardpre);
		}
		return cardArray;
	}
	
	/**
	 * 旧卡的另一种校验码生成规则
	 * @param startCardIdPrex
	 * @return
	 */
	public static final String getOldCardLast(String startCardIdPrex) {
		char[] crd = startCardIdPrex.toCharArray();
		
		int i,i4,ii=0,iii;
		int c;
		for(i=1,i4=0;i<9;i++){
			ii=(crd[i*2]-'0');
			if(ii > 9) {
				i4 += (1+ii-10);
			}else{
				i4 += ii;
			}
		}
		for(i=1;i<8;i++){
			ii += (crd[i*2+1]-'0')*2;
		}
		iii=(i4+ii)%10;
		if(iii == 0) {
			c=0;
		} else {
			c=10-iii;
		}
		return String.valueOf(c);
	}
	
	/**
	 * 根据起始卡号，卡数量，生成结束卡号，新旧卡号都支持
	 * @param strCardId
	 * @param cardNum
	 * @return
	 */
	public static final String getEndCard(String strCardId, Integer cardNum) {
		if (StringUtils.isEmpty(strCardId)) {
			return StringUtils.EMPTY;
		}
		
		String endCardId = "";
		// 新卡
		if (strCardId.length() == 19) {
			endCardId = CardUtil.getEndCardId(strCardId, cardNum);
		}
		// 旧卡
		else if (strCardId.length() == 18) {
			endCardId = CardUtil.getOldEndCardId(strCardId, cardNum);
			CardInfoDAO cardInfoDAO = (CardInfoDAO) SpringContext.getService("cardInfoDAOImpl");
			
			CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(endCardId);
			if (cardInfo == null) {
				String cardPrex = endCardId.substring(0, endCardId.length() - 1);
				endCardId = cardPrex + CardUtil.getOldCardLast(cardPrex);
				
				cardInfo = (CardInfo) cardInfoDAO.findByPk(endCardId);
			}
		}
		
		return endCardId;
	}
	
	/**
	 * 判断卡号是否是正确的卡号
	 * @param cardId
	 * @return
	 */
	public static final boolean isValidCardId(String cardId) {
		boolean isValidCardId = false;
		
		if (StringUtils.isBlank(cardId)
				 || cardId.length() < 18) {
			return false;
		}
		
		// 新卡
		if (cardId.length() == 19) {
			String cardPrex = StringUtils.substring(cardId, 0, 18);
			String suffix = StringUtils.substring(cardId, 18, 19);
			
			isValidCardId = StringUtils.equals(luhnMod10(cardPrex), suffix);
		}
		// 旧卡
		else if (cardId.length() == 18) {
			String cardPrex = StringUtils.substring(cardId, 0, 17);
			String suffix = StringUtils.substring(cardId, 17, 18);
			
			isValidCardId = (StringUtils.equals(getOldCardIdEnd(cardPrex), suffix) || StringUtils.equals(getOldCardLast(cardPrex), suffix));
		}
		
		return isValidCardId;
	}
	
	/**
	  * 根据起始卡号，卡数量，取得结束最大卡号，位数为9
	 * @param strCardId
	 * @param cardNum
	 * @return
	 */
	public static String getMaxEndCardId(String startCard, int cardNum){
		if (StringUtils.isEmpty(startCard)
				|| Integer.valueOf(cardNum) == null) {
			return StringUtils.EMPTY;
		}
		
		String endCardMax = "";
		
		// 新卡号的规则 3位系统号 + 6位卡BIN号 + 2位卡类型号 + 7流水号 + 1位校验位
		if (startCard.length() == 19) {
			// 取第12到18位，即流水号
			String strCardNo = startCard.substring(11, 18);
			String prixStr = startCard.substring(0, 11);
			
			Long result = cardNum + NumberUtils.toLong(strCardNo) - 1;
			
			String cardpre = prixStr + StringUtils.leftPad(result.toString(), 7, "0");
			
			endCardMax = cardpre + "9";
		} 
		// 旧卡号的规则：3位系统号 + 4位金额 + 3位组别号 + 6位流水号 + 1位类型 + 1位校验位
		else if (startCard.length() == 18) {
			String prixStr = startCard.substring(0, 10);
			String cardSNo = startCard.substring(10, 16); //  6位流水号
			String type = startCard.substring(16, 17); //类型
			
			Long result = cardNum + NumberUtils.toLong(cardSNo) - 1;
			
			String cardpre = prixStr + StringUtils.leftPad(result.toString(), 6, "0") + type;
			
			endCardMax = cardpre + "9";
		}
		return endCardMax;
	}
	
	public static void main(String[] args) {
		String cardId = "208050031800001148";
		System.out.println(CardUtil.getMaxEndCardId(cardId, 2));
//		System.out.println(cardId + CardUtil.getOldCardIdEnd(cardId));
//		System.out.println(cardId  + CardUtil.getOldCardLast(cardId));
//		System.out.println("cardId:"  + CardUtil.getOldCard("208000031800002413", 2)[1]);
	}
}
