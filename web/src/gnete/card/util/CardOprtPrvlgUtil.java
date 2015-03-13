package gnete.card.util;

import gnete.card.entity.CardInfo;
import gnete.card.entity.type.RoleType;
import gnete.etc.Assert;
import gnete.etc.BizException;

/**
 * 卡操作 权限工具类
 * 
 * @author Administrator
 */
public class CardOprtPrvlgUtil {

	/**
	 * 检查登录机构是否有 卡操作的 权限（即：登录的发卡机构与卡的发行机构属于同一顶级机构，或登录的售卡代理是卡的领入机构）； <br/>
	 * 主要用于 卡挂失、卡解挂、卡延期等判断；
	 * 
	 * @param loginRoleType
	 * @param loginBranchCode
	 * @param cardInfo
	 * @param oprtName
	 *            操作名
	 * @throws BizException
	 *             如果没有权限，则抛出该业务异常；
	 */
	public static void checkPrvlg(String loginRoleType, String loginBranchCode, CardInfo cardInfo,
			String oprtName) throws BizException {
		if (RoleType.CARD.getValue().equals(loginRoleType)
				|| RoleType.CARD_DEPT.getValue().equals(loginRoleType)) {
			boolean flag = BranchUtil.isBelong2SameTopBranch(loginBranchCode, cardInfo.getCardIssuer());
			String msg = String.format("登录机构与卡[%s]的发行机构不属于同一顶级机构，不能做[%s]处理。", cardInfo.getCardId(),
					oprtName);
			Assert.isTrue(flag, msg);
		} else if (RoleType.CARD_SELL.getValue().equals(loginRoleType)) {
			String msg = String.format("该售卡代理不是卡[%s]的领入机构，不能做[%s]处理", cardInfo.getCardId(), oprtName);
			Assert.equals(loginBranchCode, cardInfo.getAppOrgId(), msg);
		} else {
			throw new BizException(String.format("非发卡机构、机构网点或者售卡代理不能做[%s]处理。", oprtName));
		}
	}
}
