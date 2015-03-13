package flink.file;

import gnete.card.entity.MembInfoReg;
import gnete.etc.BizException;

public class MembInfoRegImporter extends AbstractCsvFileLineImporter{

	protected final String DEFAULT_ENCODING = "GBK";
	protected final String DEFAULT_LINESPLIT = "\\|";
	
	@Override
	protected Object getImportObject(String[] contentArray, int fieldNum)	throws Exception {
		try{
			//会员类型|会员级别|会员名称|证件类型|证件号|地址|性别|生日|年龄|手机号|座机号|电子邮件|工作|薪水|教育程度|备注
 			MembInfoReg membInfoReg = new MembInfoReg();
			int i=0;
 			membInfoReg.setMembClass(contentArray[i++]);
 			membInfoReg.setMembLevel(contentArray[i++]);
 			membInfoReg.setCustName(contentArray[i++]);
			membInfoReg.setCredType(contentArray[i++]);
			membInfoReg.setCredNo(contentArray[i++]);
			membInfoReg.setAddress(contentArray[i++]);
			membInfoReg.setSex(contentArray[i++]);
			membInfoReg.setBirthday(contentArray[i++]);
			membInfoReg.setAge(contentArray[i++]);
			membInfoReg.setMobileNo(contentArray[i++]);
			membInfoReg.setTelNo(contentArray[i++]);
			membInfoReg.setEmail(contentArray[i++]);
			membInfoReg.setJob(contentArray[i++]);
			membInfoReg.setSalary(contentArray[i++]);
			membInfoReg.setEducation(contentArray[i++]);
			membInfoReg.setRemark(contentArray[i++]);
			return membInfoReg;
		}
		catch (Exception ex) {
			throw new BizException("文件记录解释出现异常,原因[" + ex.getMessage() + "]");
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
