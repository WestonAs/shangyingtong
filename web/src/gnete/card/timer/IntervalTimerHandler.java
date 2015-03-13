package gnete.card.timer;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * @File: IntervalTimerHandler.java
 *
 * @description: Interval定时处理器，间隔时间单位：秒
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: LiHeng
 * @version: 1.0
 * @since 1.0 2011-8-19
 */
public abstract class IntervalTimerHandler extends AbstractTimerHandler {
	protected boolean canStart() {
		if (first) {
			first = false;
			resetTimer();
		}
		Date now = now();
		return (now.compareTo(endTime) >= 0);
	}

	private boolean first = true;
	private Date endTime;
	protected abstract int getInterval();
	
	protected void resetTimer() {
		endTime = DateUtils.addSeconds(now(), getInterval());
	}
}
