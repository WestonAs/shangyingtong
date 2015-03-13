package flink.field;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import flink.util.CommonHelper;
import gnete.etc.RuntimeBizException;


/**
 * 
  * @Project: Card
  * @File: TradeDateMsgProperty.java
  * @See:
  * @description：<p>常用日期数据类型(精确到天)</p>
  * @author: aps-zbw
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-9-2
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public class TradeDateMsgPropertyType extends AbstractMsgPropertyType<Date>{
	private static final long serialVersionUID = 4656790561663711229L;

	public static final TradeDateMsgPropertyType INSTANCE = new TradeDateMsgPropertyType();
	
	public static final String TRADEDATE_FORMAT = "yyyyMMdd";
	
	public TradeDateMsgPropertyType() {
		super(Date.class);
	}

	protected TradeDateMsgPropertyType(Class<Date> type) {
		super(type);
	}

	public String toString(Date value) {
		return new SimpleDateFormat(TRADEDATE_FORMAT ).format( value );
	}

	public Date fromString(String string) {
		try {
			return new Date( new SimpleDateFormat(TRADEDATE_FORMAT).parse( string ).getTime() );
		}
		catch (ParseException pe) {
			throw new RuntimeBizException( "errors.wrong.dateformate", string );
		}
	}
	
	public String[] getDefaultMatchTypeString() {
		return new String[] {DataFieldType.TRADEDATE_DATATYPE.getFieldType(),Date.class.getName()};
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
		
		return new EqualsBuilder().append(calendar1.get( Calendar.YEAR ), calendar2.get( Calendar.YEAR ))
		                           .append(calendar1.get( Calendar.MONTH ), calendar2.get( Calendar.MONTH ))
		                           .append(calendar1.get( Calendar.DAY_OF_MONTH ), calendar2.get( Calendar.DAY_OF_MONTH ))
		                           .isEquals();
	}

	@Override
	public int extractHashCode(Date value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( value );
		
		return new HashCodeBuilder(17,37).append(calendar.get(Calendar.YEAR))
		                                  .append(calendar.get( Calendar.MONTH ))
		                                  .append( Calendar.DAY_OF_MONTH )
		                                  .hashCode();
	}

	
	public boolean checkInArrange(int[] arrange, Date current) {
		if(arrange.length == 0) {
			return true;
		}
		
		Date curentDate = CommonHelper.getCalendarDate();
		
		Date dbefore = CommonHelper.getCompareDate(curentDate, arrange[0]);
		
		Date dafter = CommonHelper.getCompareDate(curentDate, arrange[arrange.length - 1]);
		
		return super.checkInArrange(dbefore, dafter, current);
	}
	

}
