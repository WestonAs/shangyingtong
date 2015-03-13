package gnete.card.clear2Pay.config;

/**
 *  <p>网银通文件涉及函数接口描述</p>
 * @Project: Card
 * @File: IClear2PayBankFuncProcess.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-16
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public interface IClear2PayBankFuncTemplate {
    /**
      * 
      * <p>得到该部分函数的描述,必须是当前支持的模式</p>
      * @return  
      * @version: 2011-6-16 上午10:31:39
      * @See:
     */
	String getFuncDescribe();

	/**
	  * 
	  * <p>得到上述函数要处理的字符位置范围(比如在某个区间中进行增加或者移除相关内容)</p>
	  * @return  
	  * @version: 2011-6-16 上午10:31:43
	  * @See:
	 */
	int getFuncResortIndex();

	/**
	 * 
	  *<p>待检查和处理的字符</p>
	  * @return  
	  * @version: 2011-6-16 上午10:31:47
	  * @See:
	 */
	String getFuncResortContent();
	
	/**
	 * 
	  * <p>经过处理完毕后的约束范围</p>
	  * @return  
	  * @version: 2011-6-16 下午07:19:33
	  * @See:
	 */
	int getFuncLimitMax();
	
}
