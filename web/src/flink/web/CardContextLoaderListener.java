package flink.web;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;

import flink.util.SpringContext;

/**
 * <p> 系统上下文加载监听处理类 </p>
 * <ul>
 *     <li>1.作为Spring ContextLoaderListener上下文加载类</li>
 *     <li>2.将容器上下文进行本地化保存</li>
 * </ul>
 */
public class CardContextLoaderListener extends ContextLoaderListener {

	private final Log			logger	= LogFactory.getLog(getClass());

	private final SpringContext	context	= SpringContext.getInstance();

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// 初始化上下文
		super.contextInitialized(event);
		// 保存context(本地)
		context.initContext(event.getServletContext());
		logger.info("========上下文加载成功==============");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// 销毁本地context
		context.destroyContext();
		// 触发上下文销毁
		super.contextDestroyed(event);
		logger.info("========上下文卸载成功==============");
	}
}
