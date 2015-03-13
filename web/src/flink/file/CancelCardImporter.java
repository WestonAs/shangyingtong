package flink.file;

import gnete.card.entity.CancelCardReg;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;


public class CancelCardImporter extends AbstractCsvFileLineImporter{
	
	protected final String DEFAULT_ENCODING = "GBK";	

	@Override
	protected CancelCardReg getImportObject(String[] contentArray, int fieldNum) throws Exception {
		CancelCardReg cancelCardReg = new CancelCardReg();
		
		//填入卡号,手续费,备注
		cancelCardReg.setCardId(StringUtils.trim(contentArray[0]));
		cancelCardReg.setExpenses(StringUtils.isBlank(contentArray[1]) ? new BigDecimal(0) : new BigDecimal(StringUtils.trim(contentArray[1])));
		cancelCardReg.setRemark(contentArray[2]);
		
		return cancelCardReg;
	}
	
	@Override
	protected String getFileEncoding() {

		return DEFAULT_ENCODING;
	}

}
