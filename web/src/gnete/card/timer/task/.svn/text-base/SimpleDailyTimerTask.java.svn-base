package gnete.card.timer.task;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import flink.ftp.IFtpCallBackProcess;
import flink.ftp.impl.CommonDirFilesCallBackImpl;
import flink.ftp.impl.FtpCallBackProcessImpl;
import flink.schedule.TaskException;
import flink.timer.FlinkTimerTask;
import flink.timer.IntervalEnum;
import flink.util.CommonHelper;
import flink.util.IOUtil;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.BizException;

/**
 * <p>
 * 简单的每日定时任务， 一些简单的，每日都需要执行的业务逻辑可以放到该类中定时执行，比如 加载logo文件到web应用路径下 等
 * </p>
 */
@Service("simpleDailyTimerTask")
public class SimpleDailyTimerTask extends FlinkTimerTask {
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
		return "简单每日定时任务";
	}

	@Override
	protected long getCheckScheduleSleepTime() {
		return 5 * 6 * DEFAULT_SLEEP_TIME; // 5分钟
	}

	@Override
	protected Date getTaskFirstTime() throws Exception {
		return CommonHelper.getFormatDateTime("00:30");
	}

	@Override
	protected boolean isLocalServerExecutable() {
		return true;
	}

	@Override
	protected void processTask() throws BizException {
		try {
			loadLogoPictures();
		} catch (IOException e) {
			logger.error("[{}]处理失败,原因[{}]", this.getTaskDescribe(), e);
			throw new TaskException("复制logo文件到WEB工程异常", e);
		}
		logger.info("[{}]处理成功", this.getTaskDescribe());
	}

	/**
	 * 加载logo文件到web应用路径下
	 */
	@SuppressWarnings("unchecked")
	private void loadLogoPictures() throws BizException, IOException {
		SysparamCache params = SysparamCache.getInstance();
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(params.getFtpServerIP(),
				params.getFtpServerUser(), params.getFtpServerPwd());

		CommonDirFilesCallBackImpl callBack = new CommonDirFilesCallBackImpl(
				params.getBranchLogoFtpSavePath(), new String[] {}, new String[] { "jpg", "gif", "png" });

		logger.info(
				"[{}]从ftp服务器[{}][{}]下载机构logo图片",
				new String[] { this.getTaskDescribe(), params.getFtpServerIP(),
						params.getBranchLogoFtpSavePath() });
		boolean flag = ftpCallBackTemplate.processFtpCallBack(callBack);

		if (flag) {
			String path = this.getClass().getClassLoader().getResource("").getPath();
			String webLogoPath = path.substring(0, path.indexOf("WEB-INF")) + "images/logo";
			logger.info("[{}]web工程下的logo文件保存路径：[{}]", this.getTaskDescribe(), webLogoPath);
			File destDir = IOUtil.getDirectoryFile(webLogoPath);

			List<Object[]> FileObjList = (List<Object[]>) callBack.getLocalFilesRefer()[1];
			for (Object[] objs : FileObjList) {
				String fileName = (String) objs[0];
				logger.debug("[{}]logo图片名[{}]", this.getTaskDescribe(), fileName.toString());
				File file = (File) objs[1];
				FileUtils.copyFileToDirectory(file, destDir);
			}
			logger.info("[{}]下载logo文件到WEB工程下成功", this.getTaskDescribe());
		} else {
			throw new BizException("从ftp服务器下载机构logo图片失败");
		}
	}
}
