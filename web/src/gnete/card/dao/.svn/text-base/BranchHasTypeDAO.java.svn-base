package gnete.card.dao;

import gnete.card.entity.BranchHasType;
import java.util.List;

public interface BranchHasTypeDAO extends BaseDAO {

	/**
	 * 根据机构代码查找类型信息
	 * @param branchCode
	 * @return
	 */
	List<BranchHasType> findByBranch(String branchCode);

	/**
	 * 根据机构代码删除机构拥有的类型信息
	 * @param branchCode
	 */
	int deleteByBranch(String branchCode);
}