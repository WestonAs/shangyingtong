package gnete.card.web.report;

import javax.servlet.http.HttpServletRequest;
/**
 * 
  * @Project: Card
  * @File: ICardReportLoad.java
  * @See:
  * @description：
  *    <ul>将页面的参数装载到页面中</ul>
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-1
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public interface ICardReportLoad {	
	/**
	 * 
	  * @description：将报表模板文件所依赖的参数保存到request 
	  * @param request  
	  * @version: 	
	  * @See:
	 */
	void loadReportParams(HttpServletRequest request);
	
	/**
	 * 
	  * @description： params[0] = {"feeDateValue"} params[1] = {"branchCodeValue"}
	  * @param roleType 角色名称 (视情况而定)
	  * @param reportType 报表类型
	  * @param params：页面绑定的查询参数
	  * @return  模板文件和查询字符串文件(给页面使用)
	  * @version: 2010-12-4 上午11:05:04
	  * @See:
	 */
	String[] getReportQueryParams(String roleType,String reportType,String[] params) throws Exception;
}
