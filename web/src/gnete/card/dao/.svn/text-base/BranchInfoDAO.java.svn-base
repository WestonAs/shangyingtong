package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.type.BranchType;
import gnete.card.entity.type.ProxyType;

import java.util.List;
import java.util.Map;

public interface BranchInfoDAO extends BaseDAO {
	
	String getBranchCode(String sufix);

	/**
	 * 查找机构信息
	 * 
	 * @param params	查询参数信息
	 * 包括:
	 * <ul>
	 * 	<li>branchCode:String 机构代码</li>
	 * 	<li>branchName:String 机构名称</li>
	 * 	<li>areaCode:String 所属地区</li>
	 * 	<li>state:String 机构状态</li>
	 * </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findBranch(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查找机构列表
	 * @param params
	 * @return
	 */
	List<BranchInfo> findBranchList(Map<String, Object> params);

	/**
	 * 根据机构类型查找
	 * @param type
	 * @return
	 */
	List<BranchInfo> findByType(String type);
	
	/**
	 * 根据清算模式和机构代码查找运营代理
	 * @param params
	 * @return
	 */
	List<BranchInfo> findAgent(Map<String, Object> params);

	/**
	 * 根据管理机构查找其管理的分支机构（树形结构的）
	 * @param params
	 * @return
	 */
	List<BranchInfo> findFenzhiByManage(String fenzhiCode);
	
	/**
	 * 
	  * @description：根据机构类型来查找机构
	  * @param branchTypes
	  * @return  
	  * @version: 2011-5-18 下午02:02:51
	  * @See:
	 */
	List<BranchInfo> findByTypes(String[] branchTypes) ;

	/**
	 * 根据机构类型来查找机构
	 * @param manageBranch
	 * @return
	 */
	List<BranchInfo> findByTypes(List<BranchType> manageBranch);

	/**
	 * 根据管理机构的机构号来查找其管辖的下级机构
	 * @param branchNo
	 * @return
	 */
	List<BranchInfo> findByManange(String branchNo);

	/**
	 * 查找管理的发卡机构
	 * @param branchNo
	 * @return
	 */
	List<BranchInfo> findCardByManange(String branchNo);

	/**
	 * 查找发卡机构代理
	 * @param branchNo
	 * @param proxyType
	 * @return
	 */
	List<BranchInfo> findCardProxy(String branchNo, ProxyType proxyType);
	
	/**
	 * 根据代理机构查找其发卡机构
	 * @param branchNo
	 * @return
	 */
	List<BranchInfo> findCardByProxy(String branchNo);
	
	/**
	 * 根据某一代理机构号查询与它同样特约关系的发卡机构的售卡代理（排除自己）
	 * @param proxyId
	 * @return
	 */
	List<BranchInfo> findProxyByProxy(String proxyId);

	/**
	 * 查找我管理的售卡机构
	 * @param branchNo
	 * @return
	 */
	List<BranchInfo> findSellByManange(String branchNo);
	
	/**
	 * 根据商户查找与其有特约关系的机构
	 * @param merchNo
	 * @return
	 */
	List<BranchInfo> findByMerch(String merchNo);

	/**
	 * 查找代理机构
	 * @param params
	 * @param pageNumber
	 * @param defaultSelectPageSize
	 * @return
	 */
	Paginater findProxy(Map<String, Object> params, int pageNumber,
			int defaultSelectPageSize);

	/**
	 * 根据类型查找
	 * @param roleType
	 * @param branchNo
	 * @return
	 */
	List<BranchInfo> findByTypeAndManage(String roleType, String branchNo);
	
	/**
	 * 根据集团号查询发卡机构列表
	 * @param groupId
	 * @return
	 */
	List<BranchInfo> findByGroupId(String groupId);
	
	/**
	 * 
	  * @description：根据
	  * @param branchCode
	  * @return  
	  * @version: 2010-12-17 下午02:57:00
	  * @See:
	 */
	BranchInfo findBranchInfo(String branchCode);
	
	/**
	 * 根据发卡机构编号查询其关联的制卡厂商列表
	 * @param branchCode
	 * @return
	 */
	List<BranchInfo> findMakeBranchByCardCode(String branchCode);
	
	/**
	 * 根据营运机构或发卡机构和代理类型查找其代理机构
	 * @param branchCode 机构号
	 * @param type 代理类型
	 * @return
	 */
	List<BranchInfo> findProxyByBranchCode(String branchCode, ProxyType type);
	
	/**
	 * 根据发展机构号，查找发卡机构或集团列表（根据类型来决定是查发卡机构还是集团）
	 * @param developBranch
	 * @param branchType
	 * @return
	 */
	List<BranchInfo> findCardBranchByDevelop(String developBranch, BranchType branchType);
	
	/**
	 * 根据机构类型和级别查找机构列表
	 * @param branchLevel
	 * @param branchType
	 * @return
	 */
	List<BranchInfo> findByLevelAndType(String branchLevel, String branchType);
	
	/**
	 * 根据机构级别，类型和管理机构查找机构列表
	 * @param branchLevel
	 * @param branchType
	 * @param parent
	 * @return
	 */
	List<BranchInfo> findByLevelTypeAndParent(String branchLevel, String branchType, String parent);
	
	/**
	 * 根据给定的机构号，查询该机构所有的子机构（包括该机构自身），若自己为最下级，则只查出自己。(状态有效的机构)
	 * 
	 * @param branchCode
	 * @return
	 */
	List<BranchInfo> findChildrenList(String branchCode);
	
	/**
	 * 根据给定的发卡机构号，查询与该机构处于同一树状结构的机构列表（包括该机构自身）。(状态有效的机构)
	 * @param branchCode
	 * @return
	 */
	List<BranchInfo> findTreeBranchList(String branchCode);
	
	/**
	 * 根据给定的机构号，查询该机构所有的父机构，若自己为根，则只查出自己(状态有效的机构)
	 * 
	 * @param branchCode
	 * @return
	 */
	List<BranchInfo> findRootBranchList(String branchCode);
	
	/**
	 * 根据给定的机构号，查询该机构的上级和同级的机构(包括自己,状态有效的机构)
	 * 
	 * @param branchCode
	 * @return
	 */
	List<BranchInfo> findBrotherList(String branchCode);
	
	/**
	 * 根据给定的机构号，查询该机构的最顶级机构。若自己为根，则查出自己
	 * @param branchCode
	 * @return
	 */
	BranchInfo findRootByBranch(String branchCode);
	
	/**
	 * 根据给定的机构号，查询该机构的上级机构。
	 * @param branchCode
	 * @return
	 */
	BranchInfo findParentByBranch(String branchCode);
	
	/**
	 * 取得配置生成余额报表的发卡机构
	 * @param generateDate
	 * @return
	 */
	List<BranchInfo> findBalanceBranch();

	/** superBranchCode是subBranchCode的上级机构（或本身） 
	 * @param superBranchCode 上级机构
	 * @param subBranchCode 下级机构
	 * @return
	 */
	boolean isSuperBranch(String superBranchCode, String subBranchCode);

	/**
	 * branchCode是不是被mangeBranchCode直接管理
	 * 
	 * @param branchCode 被管理的机构
	 * @param manageBranchCode 管理机构（通常是只分支机构）
	 * @return
	 */
	boolean isDirectManagedBy(String branchCode, String manageBranchCode);
	
}