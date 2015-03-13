package gnete.card.tag;

import flink.util.SpringContext;
import gnete.card.dao.UserInfoDAO;
import gnete.card.entity.UserInfo;
import gnete.card.service.mgr.BranchCache;

import org.apache.commons.lang.StringUtils;

/**
 * EL取名称的Function
 * 
 * @author aps-lih
 */
public class NameTag {

	/**
	 * 取得机构信息
	 * @param branchCode
	 * @return
	 */
	public static String getBranchName(String branchCode) {
		if (StringUtils.isBlank(branchCode) || branchCode.length() != 8 || branchCode.startsWith("D")) {
			return StringUtils.EMPTY;
		}
		return BranchCache.getInstance().getBranchName(branchCode);
	}
	
	/**
	 * 取得部门名称
	 * @param branchCode
	 * @return
	 */
	public static String getDeptName(String deptId) {
		if (StringUtils.isBlank(deptId) || !deptId.startsWith("D")) {
			return StringUtils.EMPTY;
		}
		return BranchCache.getInstance().getDeptName(deptId);
	}
	
	/**
	 * 取得商户名称
	 * @param branchCode
	 * @return
	 */
	public static String getMerchName(String merchId) {
		if (StringUtils.isBlank(merchId) || merchId.length() != 15) {
			return StringUtils.EMPTY;
		}

		return BranchCache.getInstance().getMerchName(merchId);
	}
	
	/**
	 * 取得用户名称
	 * @param branchCode
	 * @return
	 */
	public static String getUserName(String userId) {
		if (StringUtils.isBlank(userId)) {
			return StringUtils.EMPTY;
		}
		
		UserInfoDAO userInfoDAO = (UserInfoDAO) SpringContext.getService("userInfoDAOImpl");
		UserInfo userInfo = (UserInfo) userInfoDAO.findByPk(userId);
		if (userInfo == null) {
			return StringUtils.EMPTY;
		}
		return userInfo.getUserName();
	}
	
}
