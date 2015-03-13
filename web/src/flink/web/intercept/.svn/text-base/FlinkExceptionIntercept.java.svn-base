package flink.web.intercept;

import flink.util.SpringContext;
import gnete.card.entity.SysLog;
import gnete.card.entity.state.SysLogViewState;
import gnete.card.entity.type.SysLogClass;
import gnete.card.entity.type.SysLogType;
import gnete.card.service.LogService;
import gnete.etc.BizException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ExceptionMappingConfig;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor;

public class FlinkExceptionIntercept extends ExceptionMappingInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(FlinkExceptionIntercept.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result;

		try {
			try {
				result = invocation.invoke();
			} catch (DataAccessException ex) {
				String msg = "数据库操作失败：" + ex.getMessage();
				logger.error(msg, ex);
				throw new BizException(msg);
			} catch (NullPointerException ex) {
				String msg = "调用了未经初始化的对象或者是不存在的对象!";
				logger.error(msg, ex);
				throw new BizException(msg);
			} catch (IOException ex) {
				String msg = "IO异常：" + ex.getMessage();
				logger.error(msg, ex);
				throw new BizException(msg, ex);
			} catch (ClassNotFoundException ex) {
				String msg = "指定的类不存在：" + ex.getMessage();
				logger.error(msg, ex);
				throw new BizException(msg);
			} catch (ArithmeticException ex) {
				String msg = "数学运算异常：" + ex.getMessage();
				logger.error(msg, ex);
				throw new BizException(msg);
			} catch (ArrayIndexOutOfBoundsException ex) {
				String msg = "数组下标越界：" + ex.getMessage();
				logger.error(msg, ex);
				throw new BizException(msg);
			} catch (IllegalArgumentException ex) {
				String msg = "方法的参数错误：" + ex.getMessage();
				logger.error(msg, ex);
				throw new BizException(msg);
			} catch (ClassCastException ex) {
				String msg = "类型强制转换错误：" + ex.getMessage();
				logger.error(msg, ex);
				throw new BizException(msg);
			} catch (SecurityException ex) {
				String msg = "违背安全原则异常：" + ex.getMessage();
				logger.error(msg, ex);
				throw new BizException(msg);
			} catch (SQLException ex) {
				String msg = "操作数据库异常：" + ex.getMessage();
				logger.error(msg, ex);
				throw new BizException(msg);
			} catch (NoSuchMethodError ex) {
				String msg = "方法末找到异常：" + ex.getMessage();
				logger.error(msg, ex);
				throw new BizException(msg);
			} catch (InternalError ex) {
				String msg = "Java虚拟机发生了内部错误" + ex.getMessage();
				logger.error(msg, ex);
				throw new BizException(msg);
			} catch (BizException ex){
				String msg = "业务异常：" + ex.getMessage();
				logger.error(msg, ex);
				throw new BizException(msg);
			} catch (Exception ex) {
				String msg = "程序内部错误，操作失败：" + ex.getMessage();
				logger.error(msg, ex);
				throw new BizException(msg);
			}
		} catch (Exception e){
			if (isLogEnabled()) {
                handleLogging(e);
            }
			
			// 记录系统日志
			LogService logService = (LogService) SpringContext.getService("logService");
			SysLog log = new SysLog();
			log.setLogDate(new Date());
			log.setLogType(SysLogType.ERROR.getValue());
			log.setState(SysLogViewState.UN_READ.getValue());
			log.setLogClass(SysLogClass.WARN.getValue());
			if (e.getMessage().length() >= 250) {
				log.setContent(StringUtils.substring(e.getMessage(), 0, 500));
			} else {
				log.setContent(e.getMessage());
			}
			try {
				logService.addSyslog(log);
			} catch (Exception ex) {
				logger.error("记录系统日志时发生异常，原因：[" + ex.getMessage() + "]");
			}
			
            List<ExceptionMappingConfig> exceptionMappings = invocation.getProxy().getConfig().getExceptionMappings();
            String mappedResult = this.findResultFromExceptions(exceptionMappings, e);
            if (mappedResult != null) {
                result = mappedResult;
                publishException(invocation, new ExceptionHolder(e));
            } else {
                throw e;
            }
		}
		return result;
	}
}
