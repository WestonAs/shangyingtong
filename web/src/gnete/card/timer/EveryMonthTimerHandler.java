package gnete.card.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @File: EveryMonthTimerHandler.java
 *
 * @description:
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: Liheng
 * @version: 1.0
 * @since 1.0 2011-8-19
 */
public abstract class EveryMonthTimerHandler extends AbstractTimerHandler {
	protected void resetTimer() {
		lastTime = now();
	}
	
	protected abstract String executeTime();
	
	private Date executeTime(Date now) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String date = sdf.format(now);
			String et = executeTime();
			if (et.length() == 2) {
				et = "01" + et + "00";	//每月1日的特定时刻
			}
			else if (et.length() == 4) {
				et = "01" + et;	//每月1日的特定时刻
			}
			else if (et.length() == 6) {
				//right format, do nothing
			}
			else {
				throw new RuntimeException("时间格式错误，正确格式：ddHHmm");
			}

			date += et;
			
			sdf = new SimpleDateFormat("yyyyMMddHHmm");
			return sdf.parse(date);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private boolean first = true;
	private Date lastTime;
	protected boolean canStart() {
		if (first) {
			first = false;
			resetTimer();
		}
		Date now = now();
		Date executeTime = executeTime(now);
		return (now.compareTo(executeTime) >= 0 && lastTime.compareTo(executeTime) < 0);
	}
}
