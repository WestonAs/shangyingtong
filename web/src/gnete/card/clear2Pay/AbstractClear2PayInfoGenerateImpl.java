package gnete.card.clear2Pay;

import gnete.etc.BizException;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>网银通划付文件处理抽象类</p>
 * @Project: Card
 * @File: AbstractClear2PayInfoGenerateImpl.java
 * @See: 
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-14
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public abstract class AbstractClear2PayInfoGenerateImpl implements IClear2PayInfoGenerate {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected final Object obj = new Object();
	
	public Object[] getClear2PayBankFileTotalResult(String rmaDate, Map clear2FileInfoMap, String filePrex) throws BizException {
		if (!checkClear2PayBankParams(rmaDate, clear2FileInfoMap)) {
			return new Object[] {};
		}
		
		return generateClear2PayBankFileTotalResult(rmaDate, clear2FileInfoMap, filePrex);
	}
	
	public Object[] getClear2PayBankBranchFileResult(String rmaDate, Map clear2FileInfoMap) throws BizException {
		if (!checkClear2PayBankParams(rmaDate, clear2FileInfoMap)) {
			return new Object[] {};
		}

		return generateClear2PayBankBranchFileResult(rmaDate, clear2FileInfoMap);
	}
	
	/**
	 * 
	  * <p>检查该日期产生的结果集</p>
	  * @param rmaDate                划账日期
	  * @param clear2FileInfoMap      划账结果
	  * @return
	  * @throws BizException  
	  * @version: 2011-6-16 下午07:52:12
	  * @See:
	 */
	protected abstract boolean checkClear2PayBankParams(String rmaDate, Map clear2FileInfoMap) throws BizException;

	/**
	 * 
	  * <p>根据产生的结果集产生相应的统计文件(针对不同机构)</p>
	  * @param rmaDate              划账日期
	  * @param clear2FileInfoMap    划账结果
	  * @return
	  * @throws BizException  
	  * @version: 2011-6-16 下午07:52:15
	  * @See:
	 */
	protected abstract Object[] generateClear2PayBankBranchFileResult(String rmaDate, Map clear2FileInfoMap) throws BizException;
	
	/**
	  * 
	  * <p>根据产生的结果集产生相应的统计文件(合并)</p>
	  * @param rmaDate
	  * @param clear2FileInfoMap
	  * @return
	  * @throws BizException  
	  * @version: 2011-7-6 下午05:13:10
	  * @See:
	 */
	protected abstract Object[] generateClear2PayBankFileTotalResult(String rmaDate, Map clear2FileInfoMap, String filePrex) throws BizException;

	/**
	 * 
	 * <p>获得产生的文件名称(根据机构号 日期以及行号)</p>
	 * @param recBankNo
	 * @param rmaDate
	 * @return
	 * @version: 2011-6-14 下午07:47:32
	 * @See:
	 */
	protected String getClear2PayBranchFileName(String branchCode, String recBankNo, String rmaDate) {
		return new StringBuilder().append(branchCode).append(FILE_SEPARATOR).append(rmaDate).append(FILE_SEPARATOR).append(recBankNo).append(FILE_SUFFIX).toString();
	}
	
	protected String getClear2PayFileNameRev(String rmaDate, String filePrex) {
		return new StringBuilder().append(filePrex).append(FILE_SEPARATOR).append(rmaDate).append(FILE_SUFFIX).toString();
	}
	

	/**
	 * 
	 * <p>创建本地的文件目录</p>
	 * @return
	 * @version: 2011-6-14 下午07:39:19
	 * @See:
	 */
	protected abstract File getFileLocalDir();

	/**
	 * 
	 * <p>文件编码指定</p>
	 * @return
	 * @version: 2011-6-14 下午07:39:45
	 * @See:
	 */
	protected abstract String getFileEncoding();

	/**
	 * 
	 * <p>获得开户行名称</p>
	 * @param clear2PayBankFileRowMap
	 * @return
	 * @version: 2011-6-16 下午04:18:11
	 * @See:
	 */
	protected abstract String getClear2PayBankNo(Map clear2PayBankFileRowMap);
	
	/**
	 * 
	 * <p>获得数据主键值</p>
	 * @param clear2PayBankFileRowMap
	 * @return
	 * @version: 2011-6-16 下午04:18:11
	 * @See:
	 */
	protected abstract String getClear2PayBankKey(Map clear2PayBankFileRowMap);

	/**
	 * <p>获得机构号</p>
	 * @Date 2013-3-8下午3:00:36
	 * @return String
	 */
	protected abstract String getClear2PayBranchCode(Map clear2PayBankFileRowMap);
	
	/**
	 * 
	 * <p>获得针对开户行关联模板对应的bank_no</p>
	 * @return
	 * @version: 2011-6-16 下午04:12:36
	 * @See:
	 */
	protected abstract String getFilterRecBankNo(String recBankNo) throws BizException;

	/**
	 * 
	 * <p>获得缺省的统计信息值</p>
	 * @return
	 * @version: 2011-6-16 下午04:49:38
	 * @See:
	 */
	protected abstract String getClear2PayBankStatis();
	
	/**
	 * 
	  * <p>指定返回结果集中接收行的关键字属性</p>
	  * @return  
	  * @version: 2011-6-16 下午07:49:42
	  * @See:
	 */
	protected abstract String getClear2PayBankNoProperty();
	
	/**
	 * 
	  * <p>制定返回结果集中涉及金额的关键字属性</p>
	  * @return  
	  * @version: 2011-6-16 下午07:49:45
	  * @See:
	 */
	protected abstract String getClear2PayBankAmountProperty();
	
	/**
	  * 
	  * <p>指定返回结果集中唯一关键字(比如)</p>
	  * @return  
	  * @version: 2011-6-17 下午02:38:26
	  * @See:
	 */
	protected abstract String getClear2PayBankKeyProperty();

	/**
	 * <p>返回结果集中机构号关键字属性</p>
	 * @Date 2013-3-8下午2:42:47
	 * @return String
	 */
	protected abstract String getClear2PayBranchCodeProperty() ;

}
