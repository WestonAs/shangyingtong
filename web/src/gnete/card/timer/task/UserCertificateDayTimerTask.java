package gnete.card.timer.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import flink.schedule.TaskException;
import flink.security.IUserCertificateCache;
import flink.timer.FlinkTimerTask;
import flink.timer.IntervalEnum;
import flink.util.CommonHelper;
import gnete.card.dao.UserCertificateRevDAO;
import gnete.card.service.ICardFileTransferProcess;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.BizException;

/**
 *  <p>用户数字证当天定时任务</p>
 */
@Service("userCertificateDayTimerTask")
public class UserCertificateDayTimerTask extends FlinkTimerTask{
    protected final String DEFAULT_SYS_USER = "sys";

	@Autowired
	private UserCertificateRevDAO userCertificateRevDAO;

	@Autowired
	@Qualifier("cardCertificateCache")
	private IUserCertificateCache cardCertificateCache;

	@Autowired
	@Qualifier("cardFileTranferProcess")
	private ICardFileTransferProcess cardFileTransferProcess;

	@Override
	protected boolean isPerformedOnceAtStartup() throws BizException {
		return true;
	}

	@Override
	protected IntervalEnum getIntervalEnum() {
		return IntervalEnum.DAY;
	}

	@Override
	protected String getTaskDescribe() {
		return "用户数字证书检查";
	}
	
	@Override
	protected long getCheckScheduleSleepTime() {
		return 5 * 6 * DEFAULT_SLEEP_TIME; // 5分钟
	}

	@Override
	protected Date getTaskFirstTime() throws Exception {
		String executeTime =  SysparamCache.getInstance().getCardCertCheckTime();
		return CommonHelper.getFormatDateTime(executeTime);
	}

	@Override
	protected boolean isLocalServerExecutable(){
		return true;
	}
	
	@Override
	protected void processTask() throws BizException {
		boolean result = false;
		try {
			// 更新数字证书表(对过期的证书)
			userCertificateRevDAO.updateExpiredUserCertificate(CommonHelper.getCommonDateFormatStr());
			// 同步处理
			result = reloadValidCertificate();
			
		} catch (Exception ex) {
			logger.error("[{}]处理失败,原因[{}]", this.getTaskDescribe(), ex.getMessage());
			throw new TaskException(ex);
		}
		if (result) {
			logger.info("[{}]处理成功", this.getTaskDescribe());
		}
	}

	/**
	 * @description：1.清空证书缓存 2.加载C.A证书
	 */
	private boolean reloadValidCertificate() throws Exception {
		if (cardCertificateCache.initAppCache()) {
			String caPath = SysparamCache.getInstance().getCACertFileRemotePath();
			String caFile = SysparamCache.getInstance().getCACertFileName();
			return this.cardCertificateCache.addCACertificate(this.cardFileTransferProcess
					.getTransferCertificate(caPath, caFile));
		}
		return false;
	}

}
