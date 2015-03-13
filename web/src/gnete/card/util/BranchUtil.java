package gnete.card.util;

import gnete.card.entity.BranchInfo;
import gnete.card.service.mgr.BranchCache;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class BranchUtil {

	/**
	 * 是否属于同一个的顶级机构
	 * 
	 * @param branchCodeA
	 * @param branchCodeB
	 * @return
	 * @throws BizException
	 */
	public static boolean isBelong2SameTopBranch(String branchCodeA, String branchCodeB) throws BizException {
		if (StringUtils.isBlank(branchCodeA) || StringUtils.isBlank(branchCodeB)) {
			return false;
		} else if (branchCodeA.equals(branchCodeB)) {
			return true;
		} else {
			String topBrachCodeA = getTopBranchCode(branchCodeA);
			String topBrachCodeB = getTopBranchCode(branchCodeB);
			return topBrachCodeA != null && topBrachCodeA.equals(topBrachCodeB);
		}

	}

	/**
	 * 获得指定机构所属的顶级机构
	 * 
	 * @param branchCode
	 *            机构编号
	 * @return
	 * @throws BizException
	 */
	private static String getTopBranchCode(String branchCode) throws BizException {
		BranchInfo branch = BranchCache.getInstance().getBranchInfo(branchCode);
		if (branch == null) {
			return null;
		}
		List<String> usedBranchCodes = new ArrayList<String>();// 保存已经遍历过的机构编号，主要是为了防止死循环
		String superCode = "";// 上级机构编号
		while (true) {
			superCode = branch.getSuperBranchCode();
			if (superCode == null || superCode.trim().equals("")) {
				// 如果上级机构编号为空，那么当前机构就是顶级机构
				return branch.getBranchCode();
			}

			BranchInfo superBranch = BranchCache.getInstance().getBranchInfo(superCode);
			if (superBranch == null) {
				// 如果 没有找到上级机构，那么默认当前机构就是顶级机构
				return branch.getBranchCode();
			} else if (usedBranchCodes.contains(superBranch.getBranchCode())) {
				// 如果 上级机构是已经遍历过的机构 , 抛出业务异常
				usedBranchCodes.add(branch.getBranchCode());
				String msg = String.format("没有找到机构%s的顶级机构（原因：上级机构链已经成回环，链路：%s -> %s ）", branchCode,
						usedBranchCodes, superBranch.getBranchCode());
				throw new BizException(msg);
			}
			usedBranchCodes.add(branch.getBranchCode());
			branch = superBranch;
			continue;
		}
	}
}
