package gnete.card.clear2Pay.config;

import gnete.etc.BizException;

import java.io.InputStream;
import java.util.Map;

/**
  * <p>网银通加载接口定义</p>
  * <ul>
  * <li>1. 从*.config配置文件中载入针对网银通数据的构造的解析方式</li>
  * <li>2. 从文件读取行中获得银行id(对应大的需统计产生的文件)</li>
  * <li>3. 每个银行id将对应该字段id所面向的带顺序的解析MAP(包含对特定属性的解析接口)</li>
  * </ul> 
  * @Project: Card
  * @File: IClear2PayBankConfigLoad.java
  * @See:
  * @description：

  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-6-15
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
public interface IClear2PayBankConfigLoad {
	String CONFIG_ENCODING = "GBK";

	String CONFIG_COMMENT_BRACKET = "#";
	
	String CONFIG_LEFT_BRACKET = "[";
	
	String CONFI_REIGHT_BRACKET = "]";
	
    String FUNC_TAG_PREFIX = "fun(";

	String FUNC_TAG_SUFFIX = ")";

	int FUNC_CHECK_LENGTH = 3;
	
	String ADD_FUNC = "add";
	
	String REMOVE_FUNC = "remove";

	String[] CHECK_FUNCS = new String[] { ADD_FUNC, REMOVE_FUNC };
	
	Map<String,Map<String,Clear2PayBankConfigTemplate>> getClear2PayBankResolveMap(InputStream configInput) throws BizException;
	
	void setClear2PayBankSeqMap(Map<String,String> configSeqMap);
}
