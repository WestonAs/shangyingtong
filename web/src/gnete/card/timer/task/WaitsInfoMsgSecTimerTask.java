package gnete.card.timer.task;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flink.timer.FlinkTimerTask;
import flink.timer.IntervalEnum;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.timer.waitsinfo.WaitsInfoTaskProcess;
import gnete.etc.BizException;

/**
 * <p>命令表定时任务</p>
 */
@Service("waitsInfoMsgSecTimerTask")
public class WaitsInfoMsgSecTimerTask extends FlinkTimerTask {
	@Autowired
	private WaitsInfoTaskProcess waitsInfoTaskProcess;

    private final AtomicReference<Date> firstTimeRefer = new AtomicReference<Date>();

	@Override
	protected String getTaskDescribe() {
		return "消息定时器任务";
	}

	@Override
	protected IntervalEnum getIntervalEnum() {
		return IntervalEnum.SECOND;
	}
	
	@Override
	protected int getIntervals() {
		return SysparamCache.getInstance().getMsgTimerInterval();
	}
	

	/**
	 * <p>设定当前启动的时间，假定是当前日期再加延迟时间</p>
	 */
	@Override
	protected Date getTaskFirstTime() throws Exception {
		Date firstTime = firstTimeRefer.get();
		if (firstTime == null) {
			firstTime = DateUtils.add(new Date(), Calendar.SECOND, this.getIntervals());
			firstTimeRefer.set(firstTime);
		}
		return firstTime;
	}

	@Override
	protected void processTask() throws BizException {
		this.waitsInfoTaskProcess.dealWeb();
	}

}
