package gnete.card.clear2Pay;

import gnete.etc.BizException;

import java.util.Map;

/**
 * <p>网银通划付文件生成定义接口</p>
 * @Project: Card
 * @File: IClear2PayInfoGenerate.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-14
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public interface IClear2PayInfoGenerate {
	/**生成文件注释开始标志 */
	String FILE_COMMNET = "#";
	
	/**文件名称内部分隔符*/
	String FILE_SEPARATOR = "_";
	
	/**文件缺省后缀*/
	String FILE_SUFFIX = ".txt";
	
	/**文件编码 */
	String FILE_ENCODING = "GBK";
	
	/** */
	//String PROP_SEPARATOR = " ";
	
	/**文件行属性及值分隔符*/
	String FILE_CONNECT = "|";
	
	/**文件名称日期类型*/
	String FILE_DATEPATTERN = "yyyyMMdd";
	
	/**银行id长度(电子联航号前三位故为3)*/
	int BANKNO_INDEX = 3;
	
	/**缺省为该字符*/
	String DEFAULT_VALUE = "";
	
	/**网银通划付(机构划付给商户)文件前缀*/
	String BRANCH_MERCH_FILE_RAM = "";
	
	/**系统文件前缀(系统编号)*/
	String FILE_RAM = "pds";
	
	/** 单机产品制卡费用对账划付文件前缀 */
	String MAKE_CARD_RAM = "wytdjhzwj_mk";

	/** 单机产品套餐费用对账划付文件前缀 */
	String PLAN_RAM = "wytdjhzwj_tc";
	
    /**
      * <p>根据划账记录(Map)以及日期构造网银通划账文件</p>	
      * <p>通上(区别在于将所有银行的记录合并到划账日期上)</p>
	 * @param rmaDate
	 * @param clear2FileInfoMap
	 * @param filePrex 文件名前缀
	 * @return
	 * @throws BizException
	 */
	Object[] getClear2PayBankFileTotalResult(String rmaDate, Map clear2FileInfoMap, String filePrex) throws BizException ;
	
	/**
	 *  <p>根据划账记录(Map)以及日期构造网银通划账文件</p>	
      * <p>本接口将为每个机构产生一个划付文件</p>
	 * @Date 2013-3-8下午2:26:55
	 * @return Object[]
	 */
	Object[] getClear2PayBankBranchFileResult(String rmaDate, Map clear2FileInfoMap) throws BizException;
	
}
