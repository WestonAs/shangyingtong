package gnete.card.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  @deprecated
 *  <p>每天特定时刻执行一次的定时任务</p>
  * @Project: CardFifth
  * @File: EveryDayTimerHandler.java
  * @See:
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-8-16
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
/**
 * @File: EveryDayTimerHandler.java
 *
 * @description:<p>每天特定时刻执行一次的定时任务</p>
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: Liheng
 * @version: 1.0
 * @since 1.0 2010-8-19
 */
public abstract class EveryDayTimerHandler extends AbstractTimerHandler {
	protected void resetTimer() {
		lastTime = now();
	}
	
	/**
	 * 任务执行时间，HHmm格式
	 * @return
	 */
	protected abstract String executeTime();
	
	private Date executeTime(Date now) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String date = sdf.format(now);
			
			String et = executeTime();
			if (et.length() == 2) {
				et = et + "00";
			}
			else if (et.length() == 4) {
				//right format, do nothing
			}
			else {
				throw new RuntimeException("时间格式错误，正确格式：HHmm");
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
