package flink.util;

import java.util.Date;

/** 时间段统计 */
public class TimeInterval {
	private Date startTime;
	
	public TimeInterval () {
		startTime = new Date();
	}
	
	/**
	 * 重置开始时间；
	 */
	public void reset(){
		startTime = new Date();
	}
	
	/**
	 * 获得 从开始时间到目前调用本方法的时间 的时间段（秒）
	 * @return
	 */
	public double getIntervalOfSec(){
		return (double)(System.currentTimeMillis() - startTime.getTime())/1000;
	}
	
	public static void main(String[] args) throws InterruptedException {
		TimeInterval ti = new TimeInterval();
		Thread.sleep(1234);
		System.out.println("时间间隔："+ti.getIntervalOfSec());
	}
}
