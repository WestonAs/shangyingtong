package gnete.card.entity;

import gnete.card.entity.type.SysLogFileType;
import org.apache.commons.lang.builder.CompareToBuilder;
import flink.util.FileHelper;

/**
 * 
 * @Project: CardFirst
 * @File: SysFileLog.java
 * @See:
 * @description： <li>用于描述系统后台日志文件</li> <li>从文件名称中获得</li>
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-1-12
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class SysFileLog implements java.io.Serializable, java.lang.Comparable<SysFileLog> {
	private static final long serialVersionUID = 6368974134937034507L;

	private int fileNo;

	private String fileName;

	public int getFileNo() {
		return fileNo;
	}

	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int compareTo(SysFileLog sysFileLog) {
        String[] leftRight = new String[] {};
		
		boolean cmdFlag = this.fileName.startsWith(SysLogFileType.CMDSERVER_LOGFILE.getName());

	    if (cmdFlag) {
			leftRight = getLeftRight(sysFileLog, this.fileName, SysLogFileType.CMDSERVER_LOGFILE.getName());
		}

		boolean posFlag = this.fileName.startsWith(SysLogFileType.POS_LOGFILE.getName());

		if (posFlag) {
			leftRight = getLeftRight(sysFileLog, this.fileName, SysLogFileType.POS_LOGFILE.getName());
		}

		boolean tsverFlag = this.fileName.startsWith(SysLogFileType.TIMESERVER_LOGFILE.getName());

		if (tsverFlag) {
			leftRight = getLeftRight(sysFileLog, this.fileName, SysLogFileType.TIMESERVER_LOGFILE.getName());
		}

		if (leftRight != null) {
			return new CompareToBuilder().append(leftRight[0], leftRight[1]).toComparison();
		}

		return new CompareToBuilder().append(this.fileName, sysFileLog.fileName).toComparison();
	}

	private static String[] getLeftRight(SysFileLog sysFileLog, String fileName, String prefix) {
		return new String[] { FileHelper.getFilePrefixPart(fileName, prefix),
				               FileHelper.getFilePrefixPart(sysFileLog.fileName, prefix) };
	}

}
