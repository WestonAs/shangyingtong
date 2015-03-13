package flink.web.plugin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import flink.util.DateUtil;

public class DateConverter extends StrutsTypeConverter {

	private static final DateFormat[] ACCEPT_DATE_FORMATS = {
			new SimpleDateFormat("yyyyMMdd"),
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
			new SimpleDateFormat("yyyy/MM/dd") }; // 支持转换的日期格式

	@Override  
    public Object convertFromString(Map context, String[] values, Class toClass) {   
        Date date = null;
        String dateString = null;
        if (values != null && values.length > 0) {
            dateString = values[0];
            if (dateString != null) {
            	// 遍历日期支持格式，进行转换
                for (DateFormat format : ACCEPT_DATE_FORMATS) {
                	try {
                		date = format.parse(dateString);
                	} catch(Exception e) {
                		continue;
					}
                }
            }
        }
        return date;
    }	
	
	@Override
	public String convertToString(Map context, Object o) {
		// 格式化为date格式的字符串
		Date date = (Date) o;
		String dateTimeString = DateUtil.getDateYYYYMMDD(date);
		return dateTimeString;
	}

}
