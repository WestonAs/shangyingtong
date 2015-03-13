package gnete.card.timer.task;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flink.timer.FlinkTimerTask;
import flink.timer.IntervalEnum;
import flink.util.LogUtils;
import gnete.card.service.ChannelTradeService;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.BizException;

@Service("returnFileHandleTimerTask")
public class ReturnFileHandleTimerTask extends FlinkTimerTask{

	@Autowired
	private ChannelTradeService channelTradeService;
	private final AtomicReference<Date> firstTimeRefer = new AtomicReference<Date>();

	@Override
	protected IntervalEnum getIntervalEnum() {
		return IntervalEnum.MINUTE;
	}

	@Override
	protected int getIntervals() {
		return SysparamCache.getInstance().getReturnTimerInterval();
	}
	
	@Override
	protected String getTaskDescribe() {
		return "定时处理渠道交易回盘文件";
	}
	
	@Override
	protected long getCheckScheduleSleepTime() {		
		return 5 * 6 * DEFAULT_SLEEP_TIME;
	}

	@Override
	protected Date getTaskFirstTime() throws Exception {
		Date firstTime = firstTimeRefer.get();
		if (firstTime == null) {
			int interval = SysparamCache.getInstance().getReturnTimerInterval();
			firstTime = DateUtils.add(new Date(), Calendar.MINUTE, interval);
			firstTimeRefer.set(firstTime);
		}
		return firstTime;
	}
	
	@Override
	protected void processTask() throws BizException {
		logger.info("处理渠道交易回盘文件开始执行");
		int cnt = 0;
		try {
			cnt = channelTradeService.handleReturnFile();
			logger.info("处理渠道交易回盘文件成功！");
		} catch (BizException e) {
			logger.error("处理渠道交易回盘文件失败，" + e.getMessage());
		}
		logger.debug(LogUtils.r("处理渠道交易回盘文件执行结束,本次处理[{0}]笔", cnt));
	}

}
