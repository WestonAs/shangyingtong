package flink.file;

import gnete.card.entity.AdjAccReg;
import gnete.etc.BizException;

public class AdjAccImporter extends AbstractCsvFileLineImporter{

	protected final String DEFAULT_ENCODING = "GBK";
	
	@Override
	protected Object getImportObject(String[] contentArray, int fieldNum)
			throws Exception {
		try{
			AdjAccReg adjAccReg = new AdjAccReg();
			
			//填入交易流水
			adjAccReg.setTransSn(contentArray[0]);
			
			return adjAccReg;
		}
		catch (Exception ex) {
			throw new BizException("文件记录插入出现异常,原因[" + ex.getMessage() + "]");
		}
	}
	
	@Override
	protected String getFileEncoding() {
		return DEFAULT_ENCODING;
	}

}
