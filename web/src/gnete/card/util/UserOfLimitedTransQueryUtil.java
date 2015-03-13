package gnete.card.util;

import java.util.ArrayList;
import java.util.List;


/** 受限的交易查询用户工具类 */
public class UserOfLimitedTransQueryUtil {
	
	/** 需要排除的管理机构编号列表 */
	public static List<String> getExcludeManageBranchCodes(){
		List<String> list = new ArrayList<String>();
		list.add("10001910");// 内蒙古银商
		list.add("10006510");// 四川银商
		list.add("10003310");// 浙江银商
		list.add("10004910");// 河南银商
		list.add("10005510");// 湖南银商
		list.add("10026410");// 海南银商
		list.add("10006410");// 海岛一卡通
		list.add("11935810");// 环联网络
		return list;
	}
}
