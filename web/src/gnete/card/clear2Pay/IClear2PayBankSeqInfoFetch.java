package gnete.card.clear2Pay;

import gnete.etc.BizException;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * <p>电子联航号文件提取定义接口</p>
 * @Project: Card
 * @File: IClear2PayBankSeqInfoFetch.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-15
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public interface IClear2PayBankSeqInfoFetch {
	/** 电子联行号文件行记录分隔符*/
	String CONTENT_SPLIT = "\\,";

	/** 电子联航号文件编码*/
	String CONTENT_ENCODING = "GBK";

	/**
	 * 
	 * <p>从服务端目录中加载有关电子联行号的信息</p>
	 * @param file
	 * @return
	 * @throws BizException
	 * @version: 2011-6-17 上午10:23:21
	 * @See:
	 */
	Map<String, String> getClear2PayBankSeqInfoMap(File file) throws BizException;

	/**
	 * 
	 * <p>从服务端目录中加载有关电子联行号的信息</p>
	 * @param payBankSeqStream
	 * @return
	 * @throws BizException
	 * @version: 2011-6-15 下午08:17:34
	 * @See:
	 */
	Map<String, String> getClear2PayBankSeqInfoMap(InputStream payBankSeqStream) throws BizException;
}
