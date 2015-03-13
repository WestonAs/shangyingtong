package gnete.card.service.impl;

import flink.util.LogUtils;
import gnete.card.dao.AccountSystemInfoDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.BusinessSubAccountInfoDAO;
import gnete.card.dao.SubAccountBindCardDAO;
import gnete.card.dao.SubAccountTypeDAO;
import gnete.card.entity.AccountSystemInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BusinessSubAccountInfo;
import gnete.card.entity.SubAccountBindCard;
import gnete.card.entity.SubAccountBindCardKey;
import gnete.card.entity.SubAccountType;
import gnete.card.service.BusinessSubAccountService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("businessSubAccountService")
public class BusinessSubAccountServiceImpl implements BusinessSubAccountService {

	@Autowired
	private SubAccountTypeDAO subAccountTypeDAO;
	@Autowired
	private AccountSystemInfoDAO accountSystemInfoDAO;
	@Autowired
	private BusinessSubAccountInfoDAO businessSubAccountInfoDAO;
	@Autowired
	private SubAccountBindCardDAO subAccountBindCardDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	public boolean modifySubAccountType(SubAccountType subAccountType, String sessionUser) throws BizException {
		Assert.notNull(subAccountType, "修改的虚账户类型不能为空");

		SubAccountType old = (SubAccountType) this.subAccountTypeDAO.findByPk(subAccountType.getSubAccountTypeId());
		Assert.notNull(old, "该虚账户类型" + subAccountType.getSubAccountTypeId() + "不存在");

		return this.subAccountTypeDAO.update(subAccountType) > 0;
	}

	public boolean modifyAccountSystemInfo(AccountSystemInfo accountSystemInfo, String sessionUser) throws BizException {
		Assert.notNull(accountSystemInfo, "修改的虚账户体系不能为空");

		AccountSystemInfo old = (AccountSystemInfo) this.accountSystemInfoDAO.findByPk(accountSystemInfo.getSystemId());
		Assert.notNull(old, "该虚账户体系" + accountSystemInfo.getSystemId() + "不存在");
		Map<String, Object>	params = new HashMap<String, Object>();
		params.put("systemName", accountSystemInfo.getSystemName());
		params.put("currentId", accountSystemInfo.getSystemId());
		params.put("nameCheck", Symbol.YES);
		List<AccountSystemInfo>list = accountSystemInfoDAO.findByInfos(params);
		BranchInfo branchInfo = (BranchInfo)branchInfoDAO.findByPk(accountSystemInfo.getCustId());
		accountSystemInfo.setCustName(branchInfo.getBranchName());
		Assert.isTrue(list.size() == 0, LogUtils.r("虚户体系[{0}]名称已存在", accountSystemInfo.getSystemName()));
		return this.accountSystemInfoDAO.update(accountSystemInfo) > 0;
	}

	public 	boolean modifyBusinessSubAccountInfo(BusinessSubAccountInfo businessSubAccountInfo, String sessionUser) throws BizException {
		Assert.notNull(businessSubAccountInfo, "修改的虚账户不能为空");

		BusinessSubAccountInfo old = (BusinessSubAccountInfo) this.businessSubAccountInfoDAO.findByPk(businessSubAccountInfo.getAccountId());
		Assert.notNull(old, "该虚账户" + businessSubAccountInfo.getAccountId() + "不存在");

		return this.businessSubAccountInfoDAO.update(businessSubAccountInfo) > 0;
	}

	
	public AccountSystemInfo addAccountSystemInfo(AccountSystemInfo accountSystemInfo) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("custId", accountSystemInfo.getCustId());
		BranchInfo branchInfo = (BranchInfo)branchInfoDAO.findByPk(accountSystemInfo.getCustId());
		accountSystemInfo.setCustName(branchInfo.getBranchName());
		List<AccountSystemInfo> list = accountSystemInfoDAO.findByInfos(params);
		Assert.isTrue(list.size() == 0, LogUtils.r("机构[{0}]虚户体系已存在", accountSystemInfo.getCustName()));
		params.clear();
		params.put("systemName", accountSystemInfo.getSystemName());
		list = accountSystemInfoDAO.findByInfos(params);
		Assert.isTrue(list.size() == 0, LogUtils.r("虚户体系[{0}]名称已存在", accountSystemInfo.getSystemName()));
		try {
			accountSystemInfoDAO.insert(accountSystemInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return accountSystemInfo;
	}
	
	public BusinessSubAccountInfo addBusinessSubAccountInfo(BusinessSubAccountInfo businessSubAccountInfo) throws BizException {
		businessSubAccountInfoDAO.insert(businessSubAccountInfo);
		return businessSubAccountInfo;
	}

	@Override
	public void modifyBindAcct(BusinessSubAccountInfo bsai) throws BizException {
		String[] bindAccts = bsai.getBindAccts();
		String accountId = bsai.getAccountId();
		List<SubAccountBindCard> list = new ArrayList<SubAccountBindCard>();
		SubAccountBindCardKey sabck = new SubAccountBindCardKey();
		sabck.setAccountId(accountId);
		//删除取消绑定的
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountId", bsai.getAccountId());
		List<SubAccountBindCard> bindedAccts = subAccountBindCardDAO.findBindList(params);
		for (int i = 0; i < bindedAccts.size(); i++) {
			SubAccountBindCard sabc = bindedAccts.get(i);
			if(!ArrayUtils.contains(bindAccts, sabc.getBankCardNo())){
				sabck.setBankCardNo(sabc.getBankCardNo());
				subAccountBindCardDAO.delete(sabck);
			}
		}
		//subAccountBindCardDAO.deleteBindByInfo(params);
		//新增或修改的项目
		if (!ArrayUtils.isEmpty(bindAccts)) {
			for (int i = 0; i < bindAccts.length; i++) {
				sabck.setBankCardNo(bindAccts[i]);
				SubAccountBindCard sabc = (SubAccountBindCard)subAccountBindCardDAO.findByPk(sabck);
				if (sabc != null) {
					if (Symbol.YES.equals(sabc.getIsDefault())) {
						if (!sabc.getBankCardNo().equals(bsai.getDefaultAcct())) {
							sabc.setIsDefault(Symbol.NO);
							sabc.setUpdateTime(new Date());
							subAccountBindCardDAO.update(sabc);
						}
					}else {
						if (sabc.getBankCardNo().equals(bsai.getDefaultAcct())) {
							sabc.setIsDefault(Symbol.YES);
							sabc.setUpdateTime(new Date());
							subAccountBindCardDAO.update(sabc);
						}
					}
				} else {
					sabc = new SubAccountBindCard();
					sabc.setAccountId(accountId);
					sabc.setBankCardNo(bindAccts[i]);
					sabc.setCardBindTime(new Date());
					if (sabc.getBankCardNo().equals(bsai.getDefaultAcct())) {
						sabc.setIsDefault(Symbol.YES);
					}
					sabc.setUpdateTime(new Date());
					list.add(sabc);
				}
			}
		}
		if (list.size() > 0) {
			subAccountBindCardDAO.insertBatch(list);
		}
	}
}
