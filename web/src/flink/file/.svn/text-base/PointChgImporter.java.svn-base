package flink.file;

import gnete.card.entity.PointChgReg;
import gnete.etc.BizException;

public class PointChgImporter extends AbstractCsvFileLineImporter{

	protected final String DEFAULT_ENCODING = "GBK";
	
	@Override
	protected Object getImportObject(String[] contentArray, int fieldNum)
			throws Exception {
		try{
			PointChgReg pointChgReg = new PointChgReg();
			
			//填入卡号、积分类型、调整积分
			pointChgReg.setCardId(contentArray[0]);
			pointChgReg.setPtClass(contentArray[1]);
			pointChgReg.setRemark(contentArray[2]);
			
			return pointChgReg;
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
