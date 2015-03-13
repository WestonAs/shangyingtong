package flink.field;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.time.DateUtils;

import flink.util.CommonHelper;
import gnete.etc.RuntimeBizException;

/**
 * 
  * @Project: Card
  * @File: TradeTimeMsgPropertyType.java
  * @See:
  * @description：<p>常用日期数据类型(精确到秒)</p>
  * @author: aps-zbw
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-9-2
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public class TradeTimeMsgPropertyType extends AbstractMsgPropertyType<Date>{
    private static final long serialVersionUID = -339025911194153882L;

	public static final TradeTimeMsgPropertyType INSTANCE = new TradeTimeMsgPropertyType();
    
    public static final String TRADETIME_FORMATE = "yyyyMMddHHmmss";
    
    public TradeTimeMsgPropertyType() {
    	super(Date.class);
    }
    
	protected TradeTimeMsgPropertyType(Class<Date> type) {
		super(type);		
	}

	public String toString(Date value) {
		return new SimpleDateFormat(TRADETIME_FORMATE ).format( value );
	}

	public Date fromString(String string) {
		try {
			return new Time(new SimpleDateFormat(TRADETIME_FORMATE ).parse( string ).getTime() );
		}
		catch ( ParseException pe ) {
			throw new RuntimeBizException( "errors.wrong.dateformate", string );
		}
	}
	
	public String[] getDefaultMatchTypeString() {		
		return new String[] {DataFieldType.TRADETIME_DATATYPE.getFieldType(),Time.class.getName()};
	}
	
	
	@Override
	public boolean areEqual(Date one, Date another) {
		if ( one == another ) {
			return true;
		}
		if ( one == null || another == null ) {
			return false;
		}

		if ( one.getTime() == another.getTime() ) {
			return true;
		}

		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime( one );
		calendar2.setTime( another );

		
		return new EqualsBuilder().append(calendar1.get(Calendar.YEAR ), calendar2.get(Calendar.YEAR ))
                                   .append(calendar1.get(Calendar.MONTH ), calendar2.get(Calendar.MONTH ))
                                   .append(calendar1.get(Calendar.DAY_OF_MONTH ), calendar2.get(Calendar.DAY_OF_MONTH ))
                                   .append(calendar1.get(Calendar.HOUR_OF_DAY ), calendar2.get(Calendar.HOUR_OF_DAY ))
                                   .append(calendar1.get(Calendar.MINUTE ), calendar2.get(Calendar.MINUTE ))
                                   .append(calendar1.get(Calendar.SECOND ), calendar2.get(Calendar.SECOND ))
                                   .isEquals();
	}
	
	@Override
	public int extractHashCode(Date value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( value );
		
		return new HashCodeBuilder(17,37).append(calendar.get( Calendar.YEAR ))
		                                  .append(calendar.get( Calendar.MONTH ))
		                                  .append(calendar.get( Calendar.DAY_OF_MONTH ))
		                                  .append(calendar.get( Calendar.HOUR_OF_DAY ))
		                                  .append(calendar.get( Calendar.MINUTE ))
		                                  .append(calendar.get( Calendar.SECOND ))
		                                  .hashCode();
    }

	public boolean checkInArrange(int[] arrange, Date current) {
		if(arrange.length == 0) {
			return true;
		}
		
	    Date currentDate = CommonHelper.getCalendarDate();
		
		Date hmin = DateUtils.addHours(currentDate, arrange[0]);
		
		Date hmax = DateUtils.addHours(currentDate, arrange[arrange.length - 1]);
		
		return super.checkInArrange(hmin, hmax, currentDate);
	}

}
