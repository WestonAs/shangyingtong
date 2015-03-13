package gnete.card.clear2Pay.config;

import java.util.Map;
import java.util.List;

/**
  * <p>网银通配置文件保存接口</p>
  * <ul>
  * <li>描述配置文件中的区域字段值，分为节点标志以及节点之间内容值</li>
  * <li>比如
  *     [标志]
  *     配置信息行1(seqNo=序号|long|32|M) 
  *     配置信息行2
  *  </li>  
  * </ul>
  * @Project: Card
  * @File: IClear2PayBankConfigLineBuffer.java
  * @See:  clear2PayBank.config
  * @description：
   
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-6-16
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
public interface IClear2PayBankConfigLineBuffer {
	/**
	 * 信息行中键值分隔符
	 */
	String LINE_SPLIT = "\\=";
	
	/**
	 * 值分隔符
	 */
	String CONTENT_SPLIT = "\\|";
	
	/**
	 * 网银通银行id标志
	 */
	String BANKNO_TAG = "bank_no";
	
	/**
	 * 键值分割后长度至少为2
	 */
	int LINE_CHECK_LENGTH = 2;
	
	/**
	 * 值至少由 描述|数据类型|最大长度|是否必须(4个部分)
	 */
	int CONTENT_CHECK_LENGTH = 4;
	
	/**
	 *  <p>保存行记录</p>
	  * @param lineInfo  
	  * @version: 2011-7-12 下午10:26:14
	  * @See:
	 */
	void setContentBuffer(String lineInfo);
	
	/**
	 *  <p>获得行记录缓存结构(键值保存)</p>
	  * @return  
	  * @version: 2011-7-12 下午10:26:28
	  * @See:
	 */
	Map<String,String[]> getContentBuffer();
	
	/**
	 *  <p>返回配置保存记录</p>
	  * @return  
	  * @version: 2011-7-12 下午10:28:09
	  * @See:
	 */
	List<String> getContentList();
	
	/**
	 *  <p>获得配置节点信息(区域标志信息)</p>
	  * @return  
	  * @version: 2011-7-12 下午10:28:39
	  * @See:
	 */
	String getTagNode();

	/**
	 *  <p>设置节点信息(区域标志信息)</p>
	  * @param tagNode  
	  * @version: 2011-7-12 下午10:28:56
	  * @See:
	 */
	void setTagNode(String tagNode);
	
	/**
	 *  <p>保存银行id编号(与电子联航中前三位保持一致)</p>
	  * @param bankNo  
	  * @version: 2011-7-12 下午10:29:00
	  * @See:
	 */
	void setBankNo(String bankNo);
	
	/**
	 *  <p>银行id编号</p>
	  * @return  
	  * @version: 2011-7-12 下午10:29:03
	  * @See:
	 */
	String getBankNo();
	
	
}
