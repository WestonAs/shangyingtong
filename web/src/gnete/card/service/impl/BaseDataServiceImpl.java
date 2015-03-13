package gnete.card.service.impl;

import flink.util.NameValuePair;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.BranchProxyDAO;
import gnete.card.dao.CardToMerchDAO;
import gnete.card.dao.DepartmentInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BranchProxyKey;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardToMerchKey;
import gnete.card.entity.DepartmentInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.BranchLevelType;
import gnete.card.entity.type.BranchType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RoleType;
import gnete.card.service.BaseDataService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("baseDataService")
public class BaseDataServiceImpl implements BaseDataService {

	@Autowired
	private BranchInfoDAO branchInfoDAO;

	@Autowired
	private MerchInfoDAO merchInfoDAO;

	@Autowired
	private DepartmentInfoDAO departmentInfoDAO;
	
	@Autowired
	private BranchProxyDAO branchProxyDAO;
	
	@Autowired
	private CardToMerchDAO cardToMerchDAO;

	public List<BranchInfo> getMyBranch(UserInfo user) {
		List<BranchInfo> result = new ArrayList<BranchInfo>();

		// 取得角色类型
		String roleType = user.getRole().getRoleType();

		// 如果是运营机构则查找其管理机构列表
		if (RoleType.CENTER.getValue().equals(roleType)
				|| RoleType.CENTER_DEPT.getValue().equals(roleType)) {
//			result.add((BranchInfo) this.branchInfoDAO.findByPk(user
//					.getBranchNo()));
			result.addAll(this.branchInfoDAO.findAll());
			
		} else if (RoleType.FENZHI.getValue().equals(roleType)) {
			result.add((BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo()));
			result.addAll(this.branchInfoDAO.findByManange(user.getRole().getBranchNo()));
		} else {
			result.add((BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo()));
		}
		return result;
	}

	public List<BranchInfo> getMyManageFenzhi(UserInfo user) {
		List<BranchInfo> result = new ArrayList<BranchInfo>();
		
		// 取得角色类型
		String roleType = user.getRole().getRoleType();
		
		// 只有运营分支机构有这功能
		if (RoleType.FENZHI.getValue().equals(roleType)) {
			result = this.branchInfoDAO.findFenzhiByManage(user.getBranchNo());
		}
		return result;
	}
	
	public List<BranchInfo> getMyCardBranch(UserInfo user) {
		List<BranchInfo> result = new ArrayList<BranchInfo>();

		// 取得角色类型
		String roleType = user.getRole().getRoleType();

		if (RoleType.CENTER.getValue().equals(roleType)
				|| RoleType.CENTER_DEPT.getValue().equals(roleType)) {
			result.addAll(this.branchInfoDAO.findByType(RoleType.CARD.getValue()));
		} 
		// 如果是运营机构则查找其管理的发卡机构列表
		else if (RoleType.FENZHI.getValue().equals(roleType)){
			List<BranchInfo> fenzhiList = this.branchInfoDAO.findFenzhiByManage(user.getBranchNo());
			for (BranchInfo branchInfo : fenzhiList) {
				result.addAll(this.branchInfoDAO.findCardByManange(branchInfo.getBranchCode()));
			}
		} else if (RoleType.CARD.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
//			result.add((BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo()));
			result.addAll(this.branchInfoDAO.findChildrenList(user.getBranchNo()));
		} else if (RoleType.CARD_MERCHANT.getValue().equals(roleType)
				|| RoleType.CARD_SELL.getValue().equals(roleType)) {
			result.addAll(this.branchInfoDAO.findCardByProxy(user.getBranchNo()));
		} else if (RoleType.MERCHANT.getValue().equals(roleType)) {
			result.addAll(this.branchInfoDAO.findByMerch(user.getMerchantNo()));
		}
		return result;
	}

	public List<String> getMyCardBranchBranchNo(UserInfo user) {
		List<String> result = new ArrayList<String>();

		// 取得角色类型
		String roleType = user.getRole().getRoleType();

		if (RoleType.CENTER.getValue().equals(roleType)
				|| RoleType.CENTER_DEPT.getValue().equals(roleType)) {
			List<BranchInfo> list = this.branchInfoDAO.findByType(RoleType.CARD.getValue());
			for (BranchInfo branchInfo : list) {
				result.add(branchInfo.getBranchCode());
			}
		} 
		// 如果是运营机构则查找其管理的发卡机构列表
		else if (RoleType.FENZHI.getValue().equals(roleType)){
			List<BranchInfo> fenzhiList = this.branchInfoDAO.findFenzhiByManage(user.getBranchNo());
			for (BranchInfo branchInfo : fenzhiList) {
				List<BranchInfo> cardList = this.branchInfoDAO.findCardByManange(branchInfo.getBranchCode());
				for (BranchInfo info : cardList) {
					result.add(info.getBranchCode());
				}
			}
		} else if (RoleType.CARD.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			result.add(user.getBranchNo());
		} else if (RoleType.CARD_MERCHANT.getValue().equals(roleType)
				|| RoleType.CARD_SELL.getValue().equals(roleType)) {
			List<BranchInfo> cardList = this.branchInfoDAO.findCardByProxy(user.getBranchNo());
			for (BranchInfo info : cardList) {
				result.add(info.getBranchCode());
			}
		} else if (RoleType.MERCHANT.getValue().equals(roleType)) {
			List<BranchInfo> cardList = this.branchInfoDAO.findByMerch(user.getMerchantNo());
			for (BranchInfo info : cardList) {
				result.add(info.getBranchCode());
			}
		}
		return result;
	}
	
	public List<DepartmentInfo> getMyDept(UserInfo user) {
		List<DepartmentInfo> result = new ArrayList<DepartmentInfo>();

		// 取得角色类型
		String roleType = user.getRole().getRoleType();

		// 运营中心部门用户则返回当前用户所属部门，发卡机构网点则返回当前用户所属网点
		if (RoleType.CENTER_DEPT.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			result.add((DepartmentInfo) this.departmentInfoDAO.findByPk(user
					.getDeptId()));
		}
		// 运营中心则返回运营中心下的所有部门
		else if (RoleType.CENTER.getValue().equals(roleType)
				|| RoleType.CARD.getValue().equals(roleType)) {
			result.addAll(this.departmentInfoDAO.findByBranch(user.getBranchNo()));
		}
		return result;
	}

	public List<MerchInfo> getMyMerch(UserInfo user) {
		List<MerchInfo> result = new ArrayList<MerchInfo>();

		// 取得角色类型
		String roleType = user.getRole().getRoleType();

		// 运营中心查找所有
		if (RoleType.CENTER.getValue().equals(roleType)
				|| RoleType.CENTER_DEPT.getValue().equals(roleType)) {
			result.addAll(this.merchInfoDAO.findAll());
		}
		// 分支机构查找其管理的所有商户列表
		else if (RoleType.FENZHI.getValue().equals(roleType)) {
			result.addAll(this.merchInfoDAO.findByManage(user.getBranchNo()));
		}
		// 发卡机构或其部门则返回其的特约商户列表
		else if (RoleType.CARD.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			result.addAll(merchInfoDAO.findFranchMerchList(user.getBranchNo()));
		}
		// 商户则返回其自己
		else if (RoleType.MERCHANT.getValue().equals(roleType)) {
			result.add((MerchInfo) this.merchInfoDAO.findByPk(user.getMerchantNo()));
		}
		return result;
	}
	
	public List<BranchInfo> getMyCardSellBranch(UserInfo user) {
		List<BranchInfo> result = new ArrayList<BranchInfo>();

		// 取得角色类型
		String roleType = user.getRole().getRoleType();
		
		// 运营中心查找所有售卡代理
		if (RoleType.CENTER.getValue().equals(roleType)
				|| RoleType.CENTER_DEPT.getValue().equals(roleType)) {
			result.addAll(this.branchInfoDAO.findByType(BranchType.CARD_SELL.getValue()));
		}
		// 分支机构查找其管理的所有售卡代理列表
		else if (RoleType.FENZHI.getValue().equals(roleType)) {
			result.addAll(this.branchInfoDAO.findByTypeAndManage(RoleType.CARD_SELL.getValue(), user.getBranchNo()));
		}
		// 售卡代理则返回其自己
		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			result.add((BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo()));
		}
		return result;
	}

	public List<MerchInfo> getMyFranchMerch(String branchCode) {
		List<MerchInfo> result = new ArrayList<MerchInfo>();
		result.addAll(this.merchInfoDAO.findFranchMerchList(branchCode));
		return result;
	}

	public List<BranchInfo> getMyReceiveBranch(UserInfo user) {
		List<BranchInfo> result = new ArrayList<BranchInfo>();

		// 取得角色类型
		String roleType = user.getRole().getRoleType();

		// 发卡机构则返回自己
		if (RoleType.CARD.getValue().equals(roleType)) {
			result.add((BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo()));
			result.addAll(this.branchInfoDAO.findCardProxy(user.getBranchNo(), ProxyType.SELL));
		}
		// 售卡代理也返回自己
		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			result.add((BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo()));
		}
		// 运营代理或者分支机构则返回其管理的所有发卡机构
		else if (RoleType.CENTER.getValue().equals(roleType)
				|| RoleType.FENZHI.getValue().equals(roleType)) {
			result.addAll(this.branchInfoDAO.findCardByManange(user.getBranchNo()));
		}
		return result;
	}
	
	public List<BranchInfo> getMyAppOrgBranch(UserInfo user) {
		List<BranchInfo> result = new ArrayList<BranchInfo>();

		// 取得角色类型
		String roleType = user.getRole().getRoleType();
		
		// 先根据售卡代理查找其发卡机构，然后根据发卡查询售卡代理列表
		if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			result.addAll(this.branchInfoDAO.findProxyByProxy(user.getBranchNo()));
		}
		return result;
	}

	public List<BranchInfo> getMySellBranch(UserInfo user) {
		List<BranchInfo> result = new ArrayList<BranchInfo>();

		// 取得角色类型
		String roleType = user.getRole().getRoleType();

		// 发卡机构则返回自己和其签约的售卡机构
		if (RoleType.CARD.getValue().equals(roleType)) {
			result.add((BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo()));
			result.addAll(this.branchInfoDAO.findCardProxy(user.getBranchNo(), ProxyType.SELL));
//			result.addAll(this.branchInfoDAO.findChildrenList(user.getBranchNo()));
		}
		// 售卡代理也返回自己
		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			result.add((BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo()));
		}
		// 运营代理或者分支机构则返回其管理的所有售卡机构
		else if (RoleType.CENTER.getValue().equals(roleType)
				|| RoleType.FENZHI.getValue().equals(roleType)) {
			result.addAll(this.branchInfoDAO.findSellByManange(user.getBranchNo()));
		}
		return result;
	}

	public List<DepartmentInfo> getMySellDept(UserInfo user) {
		List<DepartmentInfo> result = new ArrayList<DepartmentInfo>();

		// 取得角色类型
		String roleType = user.getRole().getRoleType();

		if (RoleType.CARD.getValue().equals(roleType)) {
			result.addAll(this.departmentInfoDAO.findByBranch(user.getBranchNo()));
		}
		return result;
	}

	public List<String> getMyDepositList(UserInfo user) {
		List<String> result = new ArrayList<String>();

		// 取得角色类型
		String roleType = user.getRole().getRoleType();
		// 如果登录用户为运营中心或运营中心部门，显示所有记录
		if (RoleType.CENTER.getValue().equals(roleType)
				|| RoleType.CENTER_DEPT.getValue().equals(roleType)) {
			result = null;
		}
		// 如果登录用户为运营分支机构
		else if (RoleType.FENZHI.getValue().equals(roleType)) {
			List<BranchInfo> list = this.branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), user.getBranchNo());
			for (BranchInfo cardBranchInfo : list) {
				// 发卡机构自己的记录
				result.add(cardBranchInfo.getBranchCode());
				// 发卡机构部门的记录
				List<DepartmentInfo> deptList = departmentInfoDAO.findByBranch(cardBranchInfo.getBranchCode());
				for (DepartmentInfo deptInfo : deptList) {
					result.add(deptInfo.getDeptId());
				}
			}
			// 售卡代理的充值，售卡记录
			List<BranchInfo> sellList = this.branchInfoDAO.findByTypeAndManage(RoleType.CARD_SELL.getValue(), user.getBranchNo());
			for (BranchInfo sellInfo : sellList) {
				result.add(sellInfo.getBranchCode());
			}
		}
		// 如果登录用户为发卡机构，可以看到自己和自己的部门的售卡充值的记录
		else if (RoleType.CARD.getValue().equals(roleType)) {
			// 发卡机构自己的记录
			result.add(user.getBranchNo());
			// 发卡机构部门的记录
			List<DepartmentInfo> deptList = departmentInfoDAO.findByBranch(user.getBranchNo());
			for (DepartmentInfo deptInfo : deptList) {
				result.add(deptInfo.getDeptId());
			}
		}
		// 如果登录用户为售卡代理，就是自己的机构号
		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			result.add(user.getBranchNo());
		}
		// 如果登录用户为发卡机构网点，则是自己的部门号
		else if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			result.add(user.getDeptId());
		}

		return result;
	}

	public List<String> getMyPromtIssIdList(UserInfo user) {
		List<String> result = new ArrayList<String>();

		// 取得角色类型
		String roleType = user.getRole().getRoleType();
		// 如果登录用户为运营中心或运营中心部门，可看到所有
		if (RoleType.CENTER.getValue().equals(roleType)
				|| RoleType.CENTER_DEPT.getValue().equals(roleType)) {
			List<BranchInfo> list = this.branchInfoDAO.findByType(RoleType.CARD.getValue());
			for (BranchInfo info : list) {
				// 发卡机构自己的记录
				result.add(info.getBranchCode());
				
				List<MerchInfo> merchList = this.merchInfoDAO.findByManage(info.getBranchCode());
				for (MerchInfo merchInfo : merchList) {
					// 商户的记录
					result.add(merchInfo.getMerchId());
				}
			}
		}
		// 如果登录的用户为运营分支机构，可看到自己管理的发卡机构
		else if (RoleType.FENZHI.getValue().equals(roleType)) {
			List<BranchInfo> branchList = branchInfoDAO.findCardByManange(user.getBranchNo());
			for (BranchInfo cardBranchInfo : branchList) {
				// 发卡机构的记录
				result.add(cardBranchInfo.getBranchCode());
			}

			List<MerchInfo> merchList = this.merchInfoDAO.findByManage(user.getBranchNo());
			for (MerchInfo merchInfo : merchList) {
				// 商户的记录
				result.add(merchInfo.getMerchId());
			}
		}
		// 如果登录用户为发卡机构，可看到自己发起的促销活动
		else if (RoleType.CARD.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			result.add(user.getBranchNo());
		}
		// 如果登录用户为商户，可看到自己发起的活动
		else if (RoleType.MERCHANT.getValue().equals(roleType)) {
			result.add(user.getMerchantNo());
		}
		return result;
	}
	
	public boolean hasRightsToDeposit(UserInfo user, CardInfo cardInfo) {
		String roleType = user.getRole().getRoleType();
		String cardIssuer = cardInfo.getCardIssuer();
		boolean flag = false;
		
//		// 分别验证发卡机构、网点、售卡代理是否拥有权限卖该卡
//		if (RoleType.CARD.getValue().equals(roleType)
//				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
//			// 发卡机构的下级可以给上级发的卡充值
//			if (!StringUtils.equals(user.getBranchNo(), cardIssuer)) {
//				return false;
//			}
//			
//		} 
//		// 售卡代理可以给与自己有代理关系的发卡机构的卡做充值
//		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
//			BranchProxyKey branchProxyKey = new BranchProxyKey(user.getRole().getBranchNo(), cardIssuer, ProxyType.SELL.getValue());
//			if (!this.branchProxyDAO.isExist(branchProxyKey)) {
//				return false;
//			}
//		}
		
		// 发卡机构部门可以自己给所属的发卡机构发的卡做充值
		if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			flag = StringUtils.equals(user.getBranchNo(), cardIssuer);
		}
		// 发卡机构可以给自己的上级，下级机构发的卡做充值
		if (RoleType.CARD.getValue().equals(roleType)) {
			flag = checkCardBranches(user.getBranchNo(), cardIssuer);
		}
		// 售卡代理可以给与自己有代理关系的发卡机构的卡做充值
		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			BranchProxyKey branchProxyKey = new BranchProxyKey(user.getBranchNo(), cardIssuer, ProxyType.SELL.getValue());
			
			flag = this.branchProxyDAO.isExist(branchProxyKey);
		}
		return flag;
	}
	
	/**
	 * 此方法只为 积分充值和赠券赠送使用 , 增加商户判断
	 * @Date 2013-3-26上午11:07:32
	 * @return boolean
	 */
	public boolean hasRightsToDepositCoupon(UserInfo user, CardInfo cardInfo) {
		String roleType = user.getRole().getRoleType();
		String cardIssuer = cardInfo.getCardIssuer();
		boolean flag = false;
		
		// 发卡机构部门可以自己给所属的发卡机构发的卡做充值
		if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			flag = StringUtils.equals(user.getBranchNo(), cardIssuer);
		}
		// 发卡机构可以给自己的上级，下级机构发的卡做充值
		if (RoleType.CARD.getValue().equals(roleType)) {
			flag = checkCardBranches(user.getBranchNo(), cardIssuer);
		}
		// 售卡代理可以给与自己有代理关系的发卡机构的卡做充值
		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			BranchProxyKey branchProxyKey = new BranchProxyKey(user.getBranchNo(), cardIssuer, ProxyType.SELL.getValue());
			flag = this.branchProxyDAO.isExist(branchProxyKey);
		}
		// 商户。判断商户是否属于该卡所属发卡机构
		if (RoleType.MERCHANT.getValue().equals(roleType)) {
			CardToMerchKey key = new CardToMerchKey();
			key.setBranchCode(cardInfo.getCardIssuer());
			key.setMerchId(user.getMerchantNo());
			flag = this.cardToMerchDAO.isExist(key);
		}
		return flag;
	}
	
	/**
	 *  检查两个发卡机构之间是否有上下级关系
	 *  
	 * @param branch1
	 * @param branch2
	 * @return
	 */
	private boolean checkCardBranches(String branch1, String branch2) {
		boolean flag = false;
		
		// 发卡机构的顶级
		BranchInfo rootBranchInfo = this.branchInfoDAO.findRootByBranch(branch1);
		if (rootBranchInfo == null) {
			return false;
		}
		
		// 顶级发卡机构的所有下级
		List<BranchInfo> childrenList = this.branchInfoDAO.findChildrenList(rootBranchInfo.getBranchCode());
		for (BranchInfo branchInfo : childrenList) {
			if (StringUtils.equals(branchInfo.getBranchCode(), branch2)) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}

	public List<MerchInfo> getMyFranchMerchByFenzhi(String branchCode) {
		List<BranchInfo> branchInfoList = branchInfoDAO.findCardByManange(branchCode);
		List<MerchInfo> merchInfoList = new ArrayList<MerchInfo>();
		for (int i = 0; i < branchInfoList.size(); i++) {
			merchInfoList.addAll(this.getMyFranchMerch(branchInfoList.get(i).getBranchCode()));
		}
		return merchInfoList;
	}

	public List<NameValuePair> getMyReciveIssuer(UserInfo user) {
		List<NameValuePair> result = new ArrayList<NameValuePair>();

		// 取得角色类型
		String roleType = user.getRole().getRoleType();

		// 如果登录用户为运营中心或运营中心部门，查的是所有
		if (RoleType.CENTER.getValue().equals(roleType)
				|| RoleType.CENTER_DEPT.getValue().equals(roleType)) {
			// 所有发卡机构
			List<BranchInfo> cardlist = this.branchInfoDAO.findByType(RoleType.CARD.getValue());
			for (BranchInfo branch : cardlist) {
				NameValuePair branchPair = new NameValuePair();
				branchPair.setValue(branch.getBranchCode());
				branchPair.setName(branch.getBranchName());
				
				result.add(branchPair);
				
				// 发卡机构的所有部门
				List<DepartmentInfo> deptList = this.departmentInfoDAO.findByBranch(branch.getBranchCode());
				for (DepartmentInfo dept : deptList) {
					NameValuePair deptPair = new NameValuePair();
					deptPair.setValue(dept.getDeptId());
					deptPair.setName(dept.getDeptName());
					
					result.add(deptPair);
				}
			}
			// 所有售卡代理
			List<BranchInfo> sellList = this.branchInfoDAO.findByType(RoleType.CARD_SELL.getValue());
			for (BranchInfo branchSell : sellList) {
				NameValuePair sellPair = new NameValuePair();
				sellPair.setValue(branchSell.getBranchCode());
				sellPair.setName(branchSell.getBranchName());
				
				result.add(sellPair);
			}
			// 所有的商户
			List<MerchInfo> merList = this.merchInfoDAO.findAll();
			for (MerchInfo merchInfo : merList) {
				NameValuePair merchPair = new NameValuePair();
				merchPair.setValue(merchInfo.getMerchId());
				merchPair.setName(merchInfo.getMerchName());
				
				result.add(merchPair);
			}
		}
		// 如果为分支机构，可以查自己管理的
		else if (RoleType.FENZHI.getValue().equals(roleType)) {
			// 管理的发卡机构
			List<BranchInfo> cardList = branchInfoDAO.findCardByManange(user.getBranchNo());
			for (BranchInfo branch : cardList) {
				NameValuePair pair = new NameValuePair();
				pair.setValue(branch.getBranchCode());
				pair.setName(branch.getBranchName());
				result.add(pair);
			}

			// 管理的售卡代理
			List<BranchInfo> saleList = branchInfoDAO.findSellByManange(user.getBranchNo());
			for (BranchInfo branch : saleList) {
				NameValuePair pair = new NameValuePair();
				pair.setValue(branch.getBranchCode());
				pair.setName(branch.getBranchName());
				result.add(pair);
			}
			// 管理的商户
			List<MerchInfo> merchList = merchInfoDAO.findByManage(user.getBranchNo());
			for (MerchInfo merchInfo : merchList) {
				NameValuePair pair = new NameValuePair();
				pair.setValue(merchInfo.getMerchId());
				pair.setName(merchInfo.getMerchName());
				result.add(pair);
			}
		}
		// 如果登录用户为发卡机构，可看到自己，自己部门的领卡记录和自己的售卡代理领的卡，以及下级发卡机构
		else if (RoleType.CARD.getValue().equals(roleType)) {
			List<BranchInfo> branchList = branchInfoDAO.findChildrenList(user.getBranchNo());
			for(BranchInfo info : branchList){
				NameValuePair pair = new NameValuePair();
				pair.setValue(info.getBranchCode());
				pair.setName(info.getBranchName());
				result.add(pair);
			}
			List<DepartmentInfo> list = departmentInfoDAO.findByBranch(user.getBranchNo());
			for (DepartmentInfo dept : list) {
				NameValuePair namePair = new NameValuePair();
				namePair.setValue(dept.getDeptId());
				namePair.setName(dept.getDeptName());
				result.add(namePair);// 部门
			}
			List<BranchInfo> sellList = branchInfoDAO.findCardProxy(user.getBranchNo(), ProxyType.SELL);
			for (BranchInfo sellBranch : sellList) {
				NameValuePair sellPair = new NameValuePair();
				sellPair.setValue(sellBranch.getBranchCode());
				sellPair.setName(sellBranch.getBranchName());
				result.add(sellPair);// 售卡代理
			}
		}
		// 如果登录用户为部门的话，可看到自己的领卡记录
		else if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			DepartmentInfo dept = (DepartmentInfo) departmentInfoDAO.findByPk(user.getDeptId());
			NameValuePair pair = new NameValuePair();
			pair.setValue(dept.getDeptId());
			pair.setName(dept.getDeptName());
			result.add(pair);
		}
		// 如果登陆的是售卡代理，可看到自己的领卡记录
		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(user.getBranchNo());
			NameValuePair pair = new NameValuePair();
			pair.setValue(info.getBranchCode());
			pair.setName(info.getBranchName());
			result.add(pair);
		}
		// 如果登陆的是商户代理，可看到自己的领卡记录
		else if (RoleType.MERCHANT.getValue().equals(roleType)) {
			MerchInfo info = (MerchInfo) merchInfoDAO.findByPk(user.getMerchantNo());
			NameValuePair pair = new NameValuePair();
			pair.setValue(info.getMerchId());
			pair.setName(info.getMerchName());
			result.add(pair);
		}
		return result;
	}
	
	public List<BranchInfo> getFirstFenzhiBranch(UserInfo user) {
		// 取得角色类型
		String roleType = user.getRole().getRoleType();
		
		List<BranchInfo> result = new ArrayList<BranchInfo>();
		
		if (RoleType.CENTER.getValue().equals(roleType)
				|| RoleType.CENTER_DEPT.getValue().equals(roleType)) {
			result = this.branchInfoDAO.findByLevelAndType(BranchLevelType.FIRST.getValue(), BranchType.FENZHI.getValue());
		}
		return result;
	}
}
