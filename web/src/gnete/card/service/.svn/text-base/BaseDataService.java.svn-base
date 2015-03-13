package gnete.card.service;

import flink.util.NameValuePair;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.DepartmentInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.UserInfo;

import java.util.List;

public interface BaseDataService {

	/**
	 * 得到我可管理的机构列表
	 * 
	 * @param user
	 * @return
	 */
	List<BranchInfo> getMyBranch(UserInfo user);
	
	/**
	 * 得到我可管理的下级分支机构及下下级分支机构
	 * @param user
	 * @return
	 */
	List<BranchInfo> getMyManageFenzhi(UserInfo user);

	/**
	 * 得到我可管理的商户列表
	 * 
	 * @param user
	 * @return
	 */
	List<MerchInfo> getMyMerch(UserInfo user);

	/**
	 * 得到我可管理的售卡代理列表
	 * 
	 * @param user
	 * @return
	 */
	List<BranchInfo> getMyCardSellBranch(UserInfo user);

	/**
	 * 得到我发卡机构的特约商户列表
	 * 
	 * @param user
	 * @return
	 */
	List<MerchInfo> getMyFranchMerch(String branchCode);

	/**
	 * 根据营运中心分支机构得到其管理的 发卡机构的特约商户列表
	 * 
	 * @param branchCode
	 * @return
	 */
	List<MerchInfo> getMyFranchMerchByFenzhi(String branchCode);

	/**
	 * 得到我可管理的部门(网点)列表
	 * 
	 * @param user
	 * @return
	 */
	List<DepartmentInfo> getMyDept(UserInfo user);

	/**
	 * 得到我管理的发卡机构
	 * 
	 * @param sessionUser
	 * @return
	 */
	List<BranchInfo> getMyCardBranch(UserInfo sessionUser);
	
	/**
	 * 得到我管理的发卡机构编号 
	 * 
	 * @param sessionUser
	 * @return
	 */
	List<String> getMyCardBranchBranchNo(UserInfo sessionUser);

	/**
	 * 得到当前卡领入机构列表
	 * 
	 * @param sessionUser
	 * @return
	 */
	List<BranchInfo> getMyReceiveBranch(UserInfo sessionUser);
	
	/**
	 * 得到当前的卡领出机构列表
	 * @param sessionUser
	 * @return
	 */
	List<BranchInfo> getMyAppOrgBranch(UserInfo sessionUser);

	/**
	 * 得到我管理的售卡机构
	 * 
	 * @param sessionUser
	 * @return
	 */
	List<BranchInfo> getMySellBranch(UserInfo sessionUser);

	/**
	 * 得到我所管理的售卡机构号和部门号列表
	 * 
	 * @param sessionUser
	 * @return
	 */
	List<String> getMyDepositList(UserInfo user);

	/**
	 * 得到我所管理的促销活动发起机构号列表
	 * 
	 * @param sessionUser
	 * @return
	 */
	List<String> getMyPromtIssIdList(UserInfo user);

	/**
	 * 得到我所管理的领卡机构或领卡部门
	 * 
	 * @param user
	 * @return
	 */
	List<NameValuePair> getMyReciveIssuer(UserInfo user);

	boolean hasRightsToDeposit(UserInfo user, CardInfo cardInfo);
	
	boolean hasRightsToDepositCoupon(UserInfo user, CardInfo cardInfo) ;
	
	/**
	 * 得到所有的一级分支机构
	 * @param user
	 * @return
	 */
	List<BranchInfo> getFirstFenzhiBranch(UserInfo user);

}
