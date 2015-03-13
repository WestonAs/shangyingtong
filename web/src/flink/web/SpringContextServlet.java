package flink.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import flink.util.SpringContext;
import flink.timer.ITimerTaskManage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  <p>SpringContext的加载已经移到CardContextLoaderListener中</p>
 *  <p>1.不能直接在容器内加载定时器的原因是存在定时器需从servlet中获得引用</p>
 *  <p>2.runQian的servlet需要优先加载reportConfig.xml中建立数据源引用</p>
 */
public class SpringContextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Log logger = LogFactory.getLog(SpringContextServlet.class);
	
	private final SpringContext context = SpringContext.getInstance();
	
	private final String DEFAULT_TIMER_BEAN = "timerTaskManage";
	
    public void init() throws ServletException {
		super.init();
		
		ITimerTaskManage timerTaskManage = getTimerTaskManage();
		
		startTimerTask(timerTaskManage);
		
	}
    
    private ITimerTaskManage getTimerTaskManage() throws ServletException{
    	try {
    		return (ITimerTaskManage)context.getServiceBean(DEFAULT_TIMER_BEAN);
    	}catch(Exception ex) {
    		String errMsg= "获得定时器管理类[" + DEFAULT_TIMER_BEAN + "]失败,原因[" + ex.getMessage() + "]"; 
    		logger.error(errMsg);    		
    		throw new ServletException(errMsg);
    	}
    }
    
    private void startTimerTask(ITimerTaskManage timerTaskManage) throws ServletException {
    	try {
    		timerTaskManage.startTimerTasks();
    	}catch(Exception ex) {
    		throw new ServletException("启动定时器出现异常,原因[" + ex.getMessage() + "]");
    	}
    }
	
}
