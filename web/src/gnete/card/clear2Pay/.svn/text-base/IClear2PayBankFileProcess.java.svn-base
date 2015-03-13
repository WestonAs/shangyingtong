package gnete.card.clear2Pay;

import flink.ftp.CommunicationException;
import gnete.etc.BizException;

import java.util.List;

/**
 * 
 * @Project: Card
 * @File: IClear2PayBankFileProcess.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-16
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public interface IClear2PayBankFileProcess {
	String FILE_SEPARATOR = "/";
	
	String FILENAME_SEPARATOR =  IClear2PayInfoGenerate.FILE_SEPARATOR ;
	
	String MERCH_REMIT_KEY = "busiOrderNo".toUpperCase();
	
	String RMA_MAPSEARCH_KEY = "rmaList";
	
	int SINGLEDATE_SIZE = 1;
	
	int ARRANGEDATE_SIZE = 2;
	
	String[] DEFAULT_PREFIXS =  { IClear2PayInfoGenerate.FILE_RAM };
	
	String[] DEFAULT_SUFFIXS =  { IClear2PayInfoGenerate.FILE_SUFFIX };
	
	/**
	 * 
	 * 生成网银通划付(机构划付给商户)文件
	 * @param rmaDate
	 * @return
	 * @throws BizException  
	 * @version: 2011-7-6 下午04:45:01
	 * @See:
	 */
	boolean processClear2PayInfoBankBranchFile(String rmaDate) throws BizException;
	
	/**
	 *  生成网银通单机产品划账文件
	 *  
	 * @param rmaDate
	 * @return
	 * @throws BizException
	 */
	boolean processClear2PayInfoBankSingleProductFile(String rmaDate) throws BizException;
	
	/**
	 * 合并选中的指定日期(范围)划付文件,并进行上传
	 * @Date 2013-3-13上午9:53:27
	 * @return String
	 */
	String processRmaFileCompondAndUpload(List<String> fileFullPaths) throws CommunicationException, BizException ;
}
