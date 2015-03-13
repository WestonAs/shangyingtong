package gnete.card.clear2Pay.config;

import gnete.etc.BizException;

import java.util.Map;

/**
 *  <p>电子联航号文件加载</p>
 *  <p>这是从文件读取的方式(现在数据已经导入到数据库中)</p>
  * @Project: Card
  * @File: IClear2PayBankSeqInfoLoad.java
  * @See:
  * @description：
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-6-17
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
public interface IClear2PayBankSeqInfoLoad {
	String TXT_SUFFIX = ".txt";
	
	String[] FILE_SUFFIX = new String[] {TXT_SUFFIX};
	
	int FILE_SIZE = 1;
	
	Map<String,String> loadClear2PayBankSeqInfoMap () throws BizException;
}
