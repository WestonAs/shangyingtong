package gnete.card.service;

import flink.util.Paginater;
import gnete.card.entity.SysLog;
import gnete.card.entity.UserLog;
import gnete.card.entity.type.SysLogClass;
import gnete.card.entity.type.SysLogType;
import gnete.etc.BizException;

import java.util.List;
import java.util.Map;

public interface LogService {

	public void addUserLog(UserLog userLog) throws BizException ;
	
	public void readSysLog(Long sysLogId, String sessionUser) throws BizException;

	public void readSettSysLog(Long sysLogId, String sessionUser) throws BizException;
	
	void addSyslog(SysLog sysLog) throws BizException;
	
	boolean addSysLog(List<SysLog> sysLogList) throws BizException;
	
	/**
	 * 取得FTP中后台日志文件列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @param bgType 1表示后台1，2表示后台2
	 * @return
	 * @throws BizException
	 */
	Paginater getBackLogFileList(Map<String, Object> params, int pageNumber,
			int pageSize, int bgType) throws BizException;
	
	/**
	 * 从FTP上下载后台日志log文件
	 * @param fullPath
	 * @param bgType 1表示后台1，2表示后台2
	 * @return
	 * @throws BizException
	 */
	boolean downloadBackLogFile(String fullPath, int bgType) throws BizException;

	/**
	 * 插入系统日志
	 * @throws BizException
	 */
	void insertSysLog(String branchNo, String merchantNo, String msg, SysLogType sysLogType,
			SysLogClass sysLogClass);
}
