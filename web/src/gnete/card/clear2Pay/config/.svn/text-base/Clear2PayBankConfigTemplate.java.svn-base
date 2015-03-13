package gnete.card.clear2Pay.config;

import flink.field.MsgPropertyType;

/**
 * <p>网银通配置文件模板类接口定义</p>
 * <ul>
 * <li>1. 将配置文件中需进行指定的行转换为模板接口(即类型化处理)</li> 
 * <li>2. 比如busiOrderNo=业务单据号|string|32|M</li> 
 * <li>3. 网银通可能还有类似截取的要求(添加add|移除remove)则再添加一个额外的关键字说明
 *        fun(add|remove,index,'内容') </li>  
 * </ul> 
 * @Project: Card
 * @File: Clear2PayBankConfigTemplate.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-15
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public interface Clear2PayBankConfigTemplate {
	/**缺省字符提取的范围是100个 */
	int MAX_ARRANG = 100;
	
	/**
	 * 
	 * <p>对应行记录中等好左边的属性</p>
	 * @return
	 * @version: 2011-6-15 下午11:12:41
	 * @See:
	 */
	String getMsgPropertyField();

	/**
	 * 
	 * <p>对应属性关联的名称</p>
	 * @return
	 * @version: 2011-6-15 下午11:12:44
	 * @See:
	 */
	String getMsgPropertyName();

	/**
	 * 
	 * <p>对应属性关联的类型</p>
	 * @return
	 * @version: 2011-6-15 下午11:12:47
	 * @See:
	 */
	MsgPropertyType<Object> getMsgPropertyType();

	/**
	 * 
	 * <p>对应属性约束的最大范围</p>
	 * @return
	 * @version: 2011-6-15 下午11:12:51
	 * @See:
	 */
	int getMsgPropertyMax();

	/**
	 * 
	 * @description：对应属性是否是必须的
	 * @return
	 * @version: 2011-6-15 下午11:12:54
	 * @See:
	 */
	boolean isMsgPropertyNeeded();

	/**
	 * 
	 * <p>得到属于附加处理的接口</p>
	 * @return
	 * @version: 2011-6-16 上午10:37:16
	 * @See:
	 */
	IClear2PayBankFuncTemplate getMsgPropFuncTemplate();
	
	/**
	 * 
	  * <p>接口整体描述</p>
	  * @return  
	  * @version: 2011-6-16 下午03:53:21
	  * @See:
	 */
	String toString();
	
}
