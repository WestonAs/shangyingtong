package gnete.card.entity.type;

import flink.util.AbstractType;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * 
  * @Project: CardFirst
  * @File: SysLogFileType.java
  * @See:
  * @description：
  *    <li>系统保存文件日志类型</li>
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-1-11
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
public class SysLogFileType extends AbstractType{
	
	private static final Map<String,SysLogFileType> ALL = new HashMap<String,SysLogFileType>();
	
	public static final SysLogFileType DATE_LOGFILE = new SysLogFileType("日期","date");
	
	public static final SysLogFileType CMDSERVER_LOGFILE = new SysLogFileType("控制台服务器","CommandServer");
	
	public static final SysLogFileType POS_LOGFILE = new SysLogFileType("POS","POS");
	
	public static final SysLogFileType TIMESERVER_LOGFILE = new SysLogFileType("时间服务类型","TimeServer");
	
	protected SysLogFileType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static List<SysLogFileType> getAll() {
		return new ArrayList<SysLogFileType>(ALL.values());
	}

}
