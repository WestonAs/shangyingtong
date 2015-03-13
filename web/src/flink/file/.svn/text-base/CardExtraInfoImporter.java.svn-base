package flink.file;

import gnete.card.entity.CardExtraInfo;
import gnete.etc.BizException;

import org.apache.commons.lang3.StringUtils;

public class CardExtraInfoImporter extends AbstractCsvFileLineImporter{

	protected final String DEFAULT_ENCODING = "GBK";
	protected final String DEFAULT_LINESPLIT = "\\|";
	private static final int num = 14;
	
	@Override
	protected Object getImportObject(String[] contentArray, int fieldNum)
			throws Exception {
		if(num != fieldNum){
			throw new BizException("表头字段数目恒为14个，上传的文件字段数目有误。");
		}
	
		try{
 			CardExtraInfo cardExtraInfo = new CardExtraInfo();
			
 			cardExtraInfo.setCardId(contentArray[0]);
			cardExtraInfo.setCustName(contentArray[1]);
			cardExtraInfo.setCredType(contentArray[2]);
			cardExtraInfo.setCredNo(contentArray[3]);
			cardExtraInfo.setCredNoExpiryDate(contentArray[4]);
			cardExtraInfo.setCareer(contentArray[5]);
			cardExtraInfo.setNationality(contentArray[6]);
			cardExtraInfo.setAddress(contentArray[7]);
			cardExtraInfo.setTelNo(contentArray[8]);
			cardExtraInfo.setMobileNo(contentArray[9]);
			cardExtraInfo.setEmail(contentArray[10]);
			cardExtraInfo.setSmsFlag("1".equals(contentArray[11])?"1":"0");
			cardExtraInfo.setBirthday(contentArray[12]);
			cardExtraInfo.setRemark(StringUtils.trimToNull(contentArray[13]));
			return cardExtraInfo;
		}
		catch (Exception ex) {
			throw new BizException("文件记录插入出现异常,原因[" + ex.getMessage() + "]");
		}
	}
	
	@Override
	protected String getFileEncoding() {
		return DEFAULT_ENCODING;
	}

	@Override
	protected String getLineSplit() {
		return DEFAULT_LINESPLIT;
	}

}
