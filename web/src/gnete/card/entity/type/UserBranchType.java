package gnete.card.entity.type;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

import flink.util.AbstractType;

/**
 * 
 * @Project: Card
 * @File: UserBranchType.java
 * @See:
 * @description： <li>机构用户类型</li>
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-1
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class UserBranchType extends AbstractType {
	public static final Map<String, UserBranchType> ALL = new HashMap<String, UserBranchType>();

	public static final UserBranchType branchUserType = new UserBranchType("发卡机构用户", "00");

	public static final UserBranchType merchUserType = new UserBranchType("商户机构用户", "01");

	protected UserBranchType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static List<UserBranchType> getALL() {
		return Arrays.asList(new UserBranchType[] { branchUserType, merchUserType });
	}

	public static List<UserBranchType> getBranchUserType() {
		return Arrays.asList(new UserBranchType[] { branchUserType });
	}

	public static List<UserBranchType> getMerchUserType() {
		return Arrays.asList(new UserBranchType[] { merchUserType });
	}

}
