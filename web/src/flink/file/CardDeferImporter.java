package flink.file;

import org.apache.commons.lang.StringUtils;

import gnete.card.entity.CardDeferReg;

public class CardDeferImporter extends AbstractCsvFileLineImporter{
	
	protected final String DEFAULT_ENCODING = "GBK";	

	@Override
	protected CardDeferReg getImportObject(String[] contentArray, int fieldNum) throws Exception {
		CardDeferReg cardDeferReg = new CardDeferReg();
		
		//填入卡号,延期日期,备注
		cardDeferReg.setCardId(StringUtils.trim(contentArray[0]));
//		cardDeferReg.setExpirDate(contentArray[1]);
		cardDeferReg.setNewExpirDate(contentArray[1]);
		cardDeferReg.setRemark(contentArray[2]);
		/*if(contentArray.length!=fieldNum){
			cardDeferReg.setRemark("");
		}
		else{
			cardDeferReg.setRemark(contentArray[3]);
		}*/
		
		return cardDeferReg;
	}
	
	@Override
	protected String getFileEncoding() {

		return DEFAULT_ENCODING;
	}

}
