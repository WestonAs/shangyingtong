/**
 * 
 */
package flink.util;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: 请求页面</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 雁联</p>
 * @author dhs
 * @version 1.0
 */
public class RequestManager {

	public static final String SUCCESS_PATH = "success_path";//返回上级页面
    
    public static final String ERROR_PATH = "error_path";//返回上级页面
    private static RequestManager rm = null;
    
	/**
	 * 
	 */
	public RequestManager() {
		
	}
	public static RequestManager getInstance()
	{
		if (rm == null)
		{
			rm = new RequestManager();
		}
		return rm;
	}
	/**
	 * 返回成功页面
	 * 
	 * */
	public void setSuccessInfo(HttpServletRequest request, String infoDesc,String successPath)
	{
		InfoView info = new InfoView();
		info.setInfoDesc(infoDesc);
		info.setSuccessPath(request.getRequestURI()+"?action="+successPath);
		request.setAttribute(SUCCESS_PATH,info);
	}
	public InfoView getSuccessInfo(HttpServletRequest request)
	{
		return (InfoView)request.getAttribute(SUCCESS_PATH);
	}
	
	/**
	 * 
	 * 写入错误页面信息
	 * 
	 * */
	public void setErrorInfo(HttpServletRequest request, String errorDesc,String errorPath)
	{
		InfoView info = new InfoView();
		info.setInfoDesc(errorDesc);
		info.setErrorPath(request.getRequestURI()+"?action="+errorPath);
		request.setAttribute(ERROR_PATH, info);
	}
	public InfoView getErrorInfo(HttpServletRequest request)
	{
		return (InfoView)request.getAttribute(ERROR_PATH);
	}
}
