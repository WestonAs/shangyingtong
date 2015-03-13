package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.StringUtil;
import gnete.card.dao.BranchHasTypeDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.BranchPrivilegeDAO;
import gnete.card.dao.BranchProxyDAO;
import gnete.card.dao.CardCustomerDAO;
import gnete.card.dao.CardGroupDAO;
import gnete.card.dao.CardToMakeCardDAO;
import gnete.card.dao.CardToMerchDAO;
import gnete.card.dao.DepartmentInfoDAO;
import gnete.card.dao.InsBankacctDAO;
import gnete.card.dao.PrivilegeDAO;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.dao.RebateRuleDetailDAO;
import gnete.card.dao.SaleDepositCheckConfigDAO;
import gnete.card.dao.SaleProxyPrivilegeDAO;
import gnete.card.entity.BranchHasType;
import gnete.card.entity.BranchHasTypeKey;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BranchPrivilege;
import gnete.card.entity.BranchProxy;
import gnete.card.entity.BranchProxyKey;
import gnete.card.entity.CardCustomer;
import gnete.card.entity.CardGroup;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.CardToMakeCard;
import gnete.card.entity.CardToMakeCardKey;
import gnete.card.entity.CardToMerch;
import gnete.card.entity.CardToMerchKey;
import gnete.card.entity.DepartmentInfo;
import gnete.card.entity.InsBankacct;
import gnete.card.entity.InsBankacctKey;
import gnete.card.entity.Privilege;
import gnete.card.entity.RebateRule;
import gnete.card.entity.RebateRuleDetail;
import gnete.card.entity.SaleDepositCheckConfig;
import gnete.card.entity.SaleProxyPrivilege;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.BranchState;
import gnete.card.entity.state.CardCustomerState;
import gnete.card.entity.state.CardMerchState;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.state.DepartmentState;
import gnete.card.entity.state.ProxyState;
import gnete.card.entity.state.RebateRuleState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.BranchLevelType;
import gnete.card.entity.type.BranchType;
import gnete.card.entity.type.CustomerRebateType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RebateRuleCalType;
import gnete.card.entity.type.SetModeType;
import gnete.card.service.BranchService;
import gnete.card.service.CardRiskService;
import gnete.card.service.RoleService;
import gnete.card.service.SaleCardRuleService;
import gnete.card.service.UserService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("branchService")
public class BranchServiceImpl implements BranchService {
	
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private BranchHasTypeDAO branchHasTypeDAO;
	@Autowired
	private BranchProxyDAO branchProxyDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SaleProxyPrivilegeDAO saleProxyPrivilegeDAO;
	@Autowired
	private CardToMerchDAO cardToMerchDAO;
	@Autowired
	private DepartmentInfoDAO departmentInfoDAO;
	@Autowired
	private BranchPrivilegeDAO branchPrivilegeDAO;
	@Autowired
	private CardRiskService cardRiskService;
	@Autowired
	private SaleCardRuleService saleCardRuleService;
	@Autowired
	private CardGroupDAO cardGroupDAO;
	@Autowired
	private CardToMakeCardDAO cardToMakeCardDAO;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private PrivilegeDAO privilegeDAO;
	@Autowired
	private InsBankacctDAO insBankacctDAO;
	@Autowired
	private SaleDepositCheckConfigDAO saleDepositCheckConfigDAO;
	@Autowired
	private CardCustomerDAO cardCustomerDAO;
	@Autowired
	private RebateRuleDetailDAO rebateRuleDetailDAO;
	@Autowired
	private RebateRuleDAO rebateRuleDAO;

	@Deprecated
	public boolean addBranch(BranchInfo branchInfo, String type, 
			UserInfo admin, UserInfo createUser, String[] privileges, 
			CardRiskReg cardRiskReg, String setMode) throws BizException {
		Assert.notNull(branchInfo, "添加的机构对象不能为空");
		Assert.notEmpty(type, "机构类型不能为空");
		
		String createUserId = createUser.getUserId();
		branchInfo.setUpdateTime(new Date());
		branchInfo.setUpdateUser(createUserId);
		branchInfo.setStatus(BranchState.NORMAL.getValue());
		
		if (!type.equals(BranchType.CARD.getValue())) {
			branchInfo.setDevelopSide(null);
		}
		
		// 机构编号规则：4位序号 + 4位地区代码
		String sufix = branchInfo.getAreaCode();
		String prefix = this.branchInfoDAO.getBranchCode(sufix);
		if (StringUtils.isEmpty(prefix)){
			prefix = "1000";
		} else if (prefix.length() < 4){
			prefix = StringUtils.leftPad(prefix, 4, '0');
		}
		
		String branchCode = prefix + sufix;
		
		branchInfo.setBranchCode(branchCode);
		Assert.isNull(this.branchInfoDAO.findByPk(branchCode), "机构代码[" + branchCode + "]已存在");
		
		BranchInfo obj = (BranchInfo) this.branchInfoDAO.insert(branchInfo);
		
		// 添加机构类型绑定
		//BranchHasTypeKey typeKey = new BranchHasTypeKey(branchCode, type);
		//this.branchHasTypeDAO.insert(typeKey);
		BranchHasType branchHasType = new BranchHasType();
		branchHasType.setBranchCode(branchCode);
		branchHasType.setTypeCode(type);
		
		/*
		// 机构类型为分支机构、营运代理和发卡机构时，设置清算模式
		if(BranchType.FENZHI.getValue().equals(type)){
			branchHasType.setSetMode(setMode);
		}
		else if(BranchType.AGENT.getValue().equals(type)
				||BranchType.CARD.getValue().equals(type)){
			if(branchInfo.getParent().equals(Constants.CENTER)){
				branchHasType.setSetMode(SetModeType.SHARE.getValue());
			}
			// 分支机构
			else{
				BranchHasTypeKey key = new BranchHasTypeKey();
				key.setBranchCode(branchInfo.getParent());
				key.setTypeCode(BranchType.FENZHI.getValue());
				BranchHasType parentType = (BranchHasType) this.branchHasTypeDAO.findByPk(key);
				Assert.notNull(parentType, "管理机构相关的类型不存在");
				branchHasType.setSetMode(parentType.getSetMode());
			}
		}*/
		
		// 机构类型为发卡机构，集团时，要设置清算模式，其他的默认为成本模式
		if (BranchType.GROUP.getValue().equals(type)
				|| BranchType.CARD.getValue().equals(type)) {
			branchHasType.setSetMode(setMode);
		} else {
			branchHasType.setSetMode(SetModeType.COST.getValue());
		}
		
		this.branchHasTypeDAO.insert(branchHasType);
		
		// 发卡机构需要定义风险准备金、通用购卡客户、通用返利规则
		if (type.equals(BranchType.CARD.getValue())) {
			Assert.notNull(cardRiskReg, "发卡机构风险准备金对象不能为空");
			
			// 将万元转换成元
			cardRiskReg.setAmt(AmountUtil.multiply(cardRiskReg.getAmt(), new BigDecimal(10000)));
			cardRiskReg.setBranchCode(branchCode);
			cardRiskReg.setAdjType(AdjType.APTITUDE.getValue());
			this.cardRiskService.addCardRiskReg(cardRiskReg, createUserId);
			this.cardRiskService.activateCardRisk(cardRiskReg);
			
			CardCustomer cardCustomer = new CardCustomer();
			cardCustomer.setCardCustomerName("通用购卡客户");
			cardCustomer.setCardBranch(branchCode);
			cardCustomer.setRebateType(CustomerRebateType.CUSTOMER_CHOOSE.getValue());
			cardCustomer.setIsCommon(Symbol.YES);
			this.saleCardRuleService.addCardCustomer(cardCustomer, createUser);
			
			RebateRule rebateRule = new RebateRule();
			rebateRule.setRebateName("通用返利规则");
			rebateRule.setCardBranch(branchCode);
			rebateRule.setIsCommon(Symbol.YES);
			rebateRule.setCalType(RebateRuleCalType.FIXED.getValue());
			
			RebateRuleDetail rebateRuleDetail = new RebateRuleDetail();
			rebateRuleDetail.setRebateSect(Short.valueOf("1"));
			rebateRuleDetail.setRebateRate(new BigDecimal(0));
			rebateRuleDetail.setRebateUlimit(new BigDecimal(0));
			
			RebateRuleDetail rebateRuleDetailArray[] = new RebateRuleDetail[]{rebateRuleDetail}; 
			this.saleCardRuleService.addRebateRule(rebateRule, rebateRuleDetailArray, createUser);
		}
		
		// 代理机构需创建代理关系
//		if (type.equals(BranchType.AGENT.getValue())) {
//			BranchProxy branchProxy = new BranchProxy();
//			branchProxy.setProxyId(branchCode);
//			branchProxy.setRespOrg(developSide);
//			branchProxy.setProxyType(ProxyType.AGENT.getValue());
//			branchProxy.setStatus(ProxyState.NORMAL.getValue());
//			branchProxy.setUpdateBy(createUserId);
//			branchProxy.setUpdateTime(new Date());
//			this.branchProxyDAO.insert(branchProxy);
//		}
		if (type.equals(BranchType.CARD_SELL.getValue())){
			Assert.notNull(privileges, "售卡代理分配的权限不能为空！");
			
			// 售卡代理允许自定义权限
			for (int i = 0; i < privileges.length; i++) {
				SaleProxyPrivilege saleProxyPrivilege = new SaleProxyPrivilege();
				saleProxyPrivilege.setCardBranch(branchInfo.getProxy());
				saleProxyPrivilege.setProxyBranch(branchCode);
				saleProxyPrivilege.setLimitId(privileges[i]);
				this.saleProxyPrivilegeDAO.insert(saleProxyPrivilege);
			}
		}
		
		// 添加机构用户
		if (admin != null) {
			Assert.notTrue(this.userService.getUser(admin.getUserId()) != null, "用户[" + admin.getUserId() +"]已存在");
			
			admin.setUserName(branchInfo.getBranchName() + "管理员");
			admin.setBranchNo(branchCode);
			try {
				admin.setUserPwd(StringUtil.getMD5(SysparamCache.getInstance().getDefaultPwd()));
			} catch (Exception e) {
				throw new BizException("生成用户密码时发生错误！");
			}
			admin.setUpdateTime(new Date());
			admin.setUpdateUser(createUserId);
			this.userService.addUser(admin, SysparamCache.getInstance().getSysUser());
			
			// 分配用户以机构管理员角色
			String roleId = this.roleService.addDefaultAdmin(branchCode, branchInfo.getBranchName(), true, type, createUserId);
			this.roleService.bindUserRole(admin.getUserId(), roleId);
		}
		return obj != null;
	}
	
	public void addBranch(BranchInfo branchInfo, UserInfo createUser, 
			BranchHasType branchHasType) throws BizException {
		String type = branchHasType.getTypeCode();
		String setMode = branchHasType.getSetMode();
		Assert.notNull(branchInfo, "添加的机构对象不能为空");
		Assert.notEmpty(type, "机构类型不能为空");
		
		String createUserId = createUser.getUserId();
		branchInfo.setUpdateTime(new Date());
		branchInfo.setUpdateUser(createUserId);
		branchInfo.setStatus(BranchState.WAITED.getValue());
		
		if (!type.equals(BranchType.CARD.getValue())) {
			branchInfo.setDevelopSide(null);
		}
		
		if (StringUtils.isEmpty(branchHasType.getBranchLevel())) {
			branchHasType.setBranchLevel(BranchLevelType.FIRST.getValue());
		}
		
		// 机构编号规则：4位序号 + 4位地区代码
		String sufix = branchInfo.getAreaCode();
		String prefix = this.branchInfoDAO.getBranchCode(sufix);
		if (StringUtils.isEmpty(prefix)){
			prefix = "1000";
		} else if (prefix.length() < 4){
			prefix = StringUtils.leftPad(prefix, 4, '0');
		}
		
		String branchCode = prefix + sufix;
		
		branchInfo.setBranchCode(branchCode);
		Assert.isNull(this.branchInfoDAO.findByPk(branchCode), "机构代码[" + branchCode + "]已存在");
		
		this.branchInfoDAO.insert(branchInfo);
		
		// 添加机构类型绑定
		branchHasType.setBranchCode(branchCode);
		branchHasType.setTypeCode(type);
		
		// 机构类型为发卡机构，集团时，要设置清算模式，其他的默认为成本模式
		if (BranchType.GROUP.getValue().equals(type)
				|| BranchType.CARD.getValue().equals(type)) {
			branchHasType.setSetMode(setMode);
		} else {
			branchHasType.setSetMode(SetModeType.COST.getValue());
		}
		
		this.branchHasTypeDAO.insert(branchHasType);
		
		// 启动审核流程
		try {
			this.workflowService.startFlow(branchInfo, WorkflowConstants.BRANCH_ADAPTER, branchCode, createUser);
		} catch (Exception e) {
			throw new BizException("启动机构审核流程时发生异常，原因：" + e.getMessage());
		}
	}
	
	public void checkBranchPass(BranchInfo branchInfo, String userId)
			throws BizException {
		Assert.notNull(branchInfo, "审核的机构对象不能为空");
		Assert.notEmpty(userId, "登录用户的用户ID不能为空");
		
		//1. 首先更改机构的状态为可用
		branchInfo.setStatus(BranchState.NORMAL.getValue());
		branchInfo.setUpdateUser(userId);
		branchInfo.setUpdateTime(new Date());
		
		this.branchInfoDAO.update(branchInfo);
		
		// 2. 发卡机构需要定义风险准备金、通用购卡客户、通用返利规则
		List<BranchHasType> hasTypeList = this.branchHasTypeDAO.findByBranch(branchInfo.getBranchCode());
		Assert.notEmpty(hasTypeList, "机构[" + branchInfo.getBranchCode() + "]的机构类型为空");
		Assert.notTrue(hasTypeList.size() > 1, "数据错误，机构[" + branchInfo.getBranchCode() + "]的机构类型多于1个");
		String type = hasTypeList.get(0).getTypeCode();
		// 发卡机构需要定义风险准备金、通用购卡客户、通用返利规则，同时还要在售卡充值审核配置表中插入数据
		if (StringUtils.equals(BranchType.CARD.getValue(), type)) {
			CardRiskReg cardRiskReg = new CardRiskReg();
			Assert.notNull(branchInfo.getCardRiskAmt(), "发卡机构风险准备金不能为空");
			
			// 将万元转换成元
			cardRiskReg.setAmt(AmountUtil.multiply(branchInfo.getCardRiskAmt(), new BigDecimal(10000)));
			cardRiskReg.setBranchCode(branchInfo.getBranchCode());
			cardRiskReg.setAdjType(AdjType.APTITUDE.getValue());
			this.cardRiskService.addCardRiskReg(cardRiskReg, userId);
			this.cardRiskService.activateCardRisk(cardRiskReg);
			
			CardCustomer cardCustomer = new CardCustomer();
			cardCustomer.setCardCustomerName("通用购卡客户");
			cardCustomer.setCardBranch(branchInfo.getBranchCode());
			cardCustomer.setRebateType(CustomerRebateType.CUSTOMER_CHOOSE.getValue());
			cardCustomer.setIsCommon(Symbol.YES);
//			UserInfo userInfo = new UserInfo();
//			userInfo.setUserId(userId);
			cardCustomer.setUpdateTime(new Date());
			cardCustomer.setUpdateUser(userId);
			cardCustomer.setStatus(CardCustomerState.NORMAL.getValue());
//			this.saleCardRuleService.addCardCustomer(cardCustomer, userInfo);
			this.cardCustomerDAO.insert(cardCustomer);
			
			RebateRule rebateRule = new RebateRule();
			rebateRule.setRebateName("通用返利规则");
			rebateRule.setCardBranch(branchInfo.getBranchCode());
			rebateRule.setIsCommon(Symbol.YES);
			rebateRule.setCalType(RebateRuleCalType.FIXED.getValue());
			rebateRule.setUpdateTime(new Date());
			rebateRule.setUpdateUser(userId);
			rebateRule.setStatus(RebateRuleState.NORMAL.getValue());
			this.rebateRuleDAO.insert(rebateRule); //添加通用返利规则
			
			RebateRuleDetail rebateRuleDetail = new RebateRuleDetail();
			rebateRuleDetail.setRebateSect(Short.valueOf("1"));
			rebateRuleDetail.setRebateRate(new BigDecimal(0));
			rebateRuleDetail.setRebateUlimit(new BigDecimal(0));
			rebateRuleDetail.setRebateId(rebateRule.getRebateId());
			
			this.rebateRuleDetailDAO.insert(rebateRuleDetail);
//			RebateRuleDetail rebateRuleDetailArray[] = new RebateRuleDetail[]{rebateRuleDetail}; 
//			this.saleCardRuleService.addRebateRule(rebateRule, rebateRuleDetailArray, userInfo);
			
//			Assert.notTrue(this.saleDepositCheckConfigDAO.isExist(branchInfo.getBranchCode()), 
//					"发卡机构[" + branchInfo.getBranchCode() + "]的审核配置已经设置");
			SaleDepositCheckConfig checkConfig = (SaleDepositCheckConfig) this.saleDepositCheckConfigDAO.findByPk(branchInfo.getBranchCode());
			if (checkConfig == null) {
				SaleDepositCheckConfig config = new SaleDepositCheckConfig();
				
				config.setCardBranch(branchInfo.getBranchCode());
				config.setSellCheck(Symbol.NO);
				config.setDepositCheck(Symbol.NO);
				config.setLossCardCheck(Symbol.NO);
				config.setTransAccCheck(Symbol.NO);
				config.setCancelCardCheck(Symbol.NO);
				config.setUpdateBy(userId);
				config.setUpdateTime(new Date());
				
				this.saleDepositCheckConfigDAO.insert(config);
			} else {
				checkConfig.setCardBranch(branchInfo.getBranchCode());
				checkConfig.setSellCheck(Symbol.NO);
				checkConfig.setDepositCheck(Symbol.NO);
				checkConfig.setLossCardCheck(Symbol.NO);
				checkConfig.setTransAccCheck(Symbol.NO);
				checkConfig.setCancelCardCheck(Symbol.NO);
				checkConfig.setUpdateBy(userId);
				checkConfig.setUpdateTime(new Date());
				
				this.saleDepositCheckConfigDAO.update(checkConfig);
			}
		}
		
		// 3. 售卡代理需要分配权限
		if (StringUtils.equals(BranchType.CARD_SELL.getValue(), type)){
			List<Privilege> privilegeList = this.privilegeDAO.findByRoleType(BranchType.CARD_SELL.getValue());
			
			// 售卡代理允许自定义权限
			for (Privilege privilege : privilegeList) {
				SaleProxyPrivilege saleProxyPrivilege = new SaleProxyPrivilege();
				saleProxyPrivilege.setCardBranch(branchInfo.getProxy());
				saleProxyPrivilege.setProxyBranch(branchInfo.getBranchCode());
				saleProxyPrivilege.setLimitId(privilege.getCode());
				this.saleProxyPrivilegeDAO.insert(saleProxyPrivilege);
			}
		}
		
		// 4.添加用户
		String adminUserId = branchInfo.getAdminId();
		Assert.notEmpty(adminUserId, "添加的用户ID不能为空");
		Assert.notTrue(this.userService.getUser(adminUserId) != null, "用户[" + adminUserId +"]已存在");
		
		UserInfo admin = new UserInfo();
		
		admin.setUserId(adminUserId);
		admin.setUserName(branchInfo.getBranchName() + "管理员");
		admin.setBranchNo(branchInfo.getBranchCode());
		try {
			admin.setUserPwd(StringUtil.getMD5(SysparamCache.getInstance().getDefaultPwd()));
		} catch (Exception e) {
			throw new BizException("生成用户密码时发生错误！");
		}
		admin.setUpdateTime(new Date());
		admin.setUpdateUser(userId);
		this.userService.addUser(admin, SysparamCache.getInstance().getSysUser());
		
		// 分配用户以机构管理员角色
		String roleId = this.roleService.addDefaultAdmin(branchInfo.getBranchCode(), branchInfo.getBranchName(), true, type, userId);
		this.roleService.bindUserRole(adminUserId, roleId);
	}
	
	public void bindType(String branchCode, String type, String sessionUserCode) throws BizException {
		Assert.notNull(branchCode, "机构代码不能为空");
		
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(branchCode);
		Assert.notNull(branchInfo, "找不到该机构[" + branchCode + "]");
		
		branchInfo.setUpdateTime(new Date());
		branchInfo.setUpdateUser(sessionUserCode);
		this.branchInfoDAO.update(branchInfo);
		
		// 添加机构类型绑定
		BranchHasTypeKey typeKey = new BranchHasTypeKey(branchCode, type);
		Assert.notTrue(this.branchHasTypeDAO.isExist(typeKey), "该机构已经绑定该类型，不能重复操作");
		this.branchHasTypeDAO.insert(typeKey);
	}
	
	public void bindType(BranchInfo branchInfo, String[] typeCodes, String sessionUserCode) throws BizException {
		Assert.notNull(branchInfo, "机构代码不能为空");
		Assert.notNull(typeCodes, "机构所属类型不能为空");
		
		String branchCode = branchInfo.getBranchCode();
		BranchInfo old = (BranchInfo) this.branchInfoDAO.findByPk(branchCode);
		Assert.notNull(old, "找不到该机构[" + branchCode + "]");
		
		old.setUpdateTime(new Date());
		old.setUpdateUser(sessionUserCode);
		if (StringUtils.isNotEmpty(branchInfo.getDevelopSide())) {
			old.setDevelopSide(branchInfo.getDevelopSide());
		}
		this.branchInfoDAO.update(old);
		List<BranchHasType> branchTypeList = this.branchHasTypeDAO.findByBranch(branchCode);
		Assert.notTrue(CollectionUtils.isEmpty(branchTypeList), "该机构没有指定机构类型");
		BranchHasType branchType = branchTypeList.get(0);
		
		this.branchHasTypeDAO.deleteByBranch(branchCode);
		
		for (String type : typeCodes) {
			//BranchHasTypeKey typeKey = new BranchHasTypeKey(branchCode, type);
			BranchHasType branchHasType = new BranchHasType();
			branchHasType.setBranchCode(branchCode);
			branchHasType.setTypeCode(type);
			branchHasType.setSetMode(branchType.getSetMode());
			this.branchHasTypeDAO.insert(branchHasType);
		}
	}

	@Override
	public boolean modifyBranch(BranchInfo branchInfo, String modifyUserId, String setMode) throws BizException {
		Assert.notNull(branchInfo, "修改的机构对象不能为空");
		
		String branchCode = branchInfo.getBranchCode();
		//boolean changeParent = false;
		
		//BranchInfo oldBranch = (BranchInfo) this.branchInfoDAO.findByPk(branchCode);
		/*if(!branchInfo.getParent().equals(oldBranch.getParent())){
			changeParent = true;
		}*/
		
		branchInfo.setUpdateTime(new Date());
		branchInfo.setUpdateUser(modifyUserId);
		if (BranchState.STOPED.getValue().equals(branchInfo.getStatus())) {
			branchInfo.setStatus(BranchState.NORMAL.getValue());
		}
//		branchInfo.setStatus(BranchState.NORMAL.getValue());
		
		// 机构的上级不能是自己
		Assert.notEquals(branchInfo.getBranchCode(), branchInfo.getParent(), 
				"要修改的机构[" + branchInfo.getBranchCode() + "]的管理机构不能是自己");
		
		int count = this.branchInfoDAO.update(branchInfo);
		
		//boolean fenzhiType = false;
		//boolean agentType = false;
		//boolean cardType = false;
		
		// 获取机构类型
		List<BranchHasType> typeList = this.branchHasTypeDAO.findByBranch(branchInfo.getBranchCode());
		Assert.notTrue(CollectionUtils.isEmpty(typeList), "该机构无有效机构类型属性");
		String type = typeList.get(0).getTypeCode();
		
		/*for(BranchHasType branchHasType : typeList){
			if(branchHasType.getTypeCode().equals(BranchType.FENZHI.getValue())){
				fenzhiType = true;
			}
			if(branchHasType.getTypeCode().equals(BranchType.AGENT.getValue())){
				agentType = true;
			}
			if(branchHasType.getTypeCode().equals(BranchType.CARD.getValue())){
				cardType = true;
			}
		}*/
		
		BranchHasType newType = new BranchHasType();
		newType.setBranchCode(branchCode);
		
/*		// 修改分支机构的清算模式
		if(fenzhiType){
			BranchHasType oldBranchHasType = (BranchHasType) this.branchHasTypeDAO.findByPk(new BranchHasTypeKey(branchCode, BranchType.FENZHI.getValue()));
			if(!oldBranchHasType.getSetMode().equals(setMode)){
				newType.setTypeCode(BranchType.FENZHI.getValue());
				newType.setSetMode(setMode);
				this.branchHasTypeDAO.update(newType);
				BranchHasType type2 = new BranchHasType();
				
				//修改其管理的发卡机构和营运代理的清算模式。
				List<BranchInfo> branchList = this.branchInfoDAO.findByManange(branchCode);
				for(BranchInfo branch : branchList){
					type2.setBranchCode(branch.getBranchCode());
					type2.setSetMode(setMode);
					if(this.branchHasTypeDAO.findByPk(new BranchHasTypeKey(branch.getBranchCode(), BranchType.AGENT.getValue()))!=null){
						type2.setTypeCode(BranchType.AGENT.getValue());
						this.branchHasTypeDAO.update(type2);
					}
					if(this.branchHasTypeDAO.findByPk(new BranchHasTypeKey(branch.getBranchCode(), BranchType.CARD.getValue()))!=null){
						type2.setTypeCode(BranchType.CARD.getValue());
						this.branchHasTypeDAO.update(type2);
					}
				}
			}
		}
		// 营运代理和发卡机构的清算模式
		else{
			// 更新清算模式，删除原来的营运代理分润参数
			if(agentType&&changeParent){
				newType.setTypeCode(BranchType.AGENT.getValue());
				if(Constants.CENTER.equals(branchInfo.getParent())){
					newType.setSetMode(SetModeType.SHARE.getValue());
					this.branchHasTypeDAO.update(newType);
				}
				else{
					newType.setSetMode(SetModeType.COST.getValue());
					this.branchHasTypeDAO.update(newType);
				}
				
			}
			// 更新清算模式，删除原来的发卡机构运营手续费
			if(cardType&&changeParent){
				newType.setTypeCode(BranchType.CARD.getValue());
				if(Constants.CENTER.equals(branchInfo.getParent())){
					newType.setSetMode(SetModeType.SHARE.getValue());
					this.branchHasTypeDAO.update(newType);
				}
				else{
					newType.setSetMode(SetModeType.COST.getValue());
					this.branchHasTypeDAO.update(newType);
				}
			}
		}*/
		
		return count > 0;
	}

	public boolean startBranch(String branchCode, UserInfo userInfo) throws BizException {
		Assert.notEmpty(branchCode, "启用机构的编号不能为空");
		
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(branchCode);
		Assert.notNull(branchInfo, "找不到该机构[" + branchCode + "]");
		
		branchInfo.setStatus(BranchState.NORMAL.getValue());
		branchInfo.setUpdateUser(userInfo.getUserId());
		branchInfo.setUpdateTime(new Date());
		
		int count = this.branchInfoDAO.update(branchInfo);
		return count > 0;
	}
	
	public void submitCheck(UserInfo userInfo) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", BranchState.PRESUB.getValue());
		params.put("branchTypes", new String[]{BranchType.CARD.getValue()});
		List<BranchInfo> branchList = this.branchInfoDAO.findBranchList(params);
		if (CollectionUtils.isEmpty(branchList)) {
			throw new BizException("没有需要提交审核的发卡机构");
		}
		for (BranchInfo branchInfo : branchList) {
			Assert.notEmpty(branchInfo.getBranchCode(), "要提交审核的机构编号不能为空");
			
			// 更新状态
			branchInfo.setStatus(BranchState.WAITED.getValue());
			branchInfo.setUpdateUser(userInfo.getUserId());
			branchInfo.setUpdateTime(new Date());
			this.branchInfoDAO.update(branchInfo);
			
			// 启动审核流程
			try {
				this.workflowService.startFlow(branchInfo, WorkflowConstants.BRANCH_ADAPTER, branchInfo.getBranchCode(), userInfo);
			} catch (Exception e) {
				throw new BizException("启动机构审核流程时发生异常，原因：" + e.getMessage());
			}
		}
	}
	
	public boolean deleteBranch(String branchCode) throws BizException {
		Assert.notEmpty(branchCode, "要删除的机构编号不能为空");
		
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(branchCode);
		Assert.notNull(branchInfo, "找不到该机构[" + branchCode + "]");
		Assert.isTrue(BranchState.UNPASS.getValue().equals(branchInfo.getStatus()) 
				|| BranchState.PRESUB.getValue().equals(branchInfo.getStatus()), "只能删除状态为“审核不通过”或“待提交”的机构");
		// 删除机构信息表的内容 
		boolean flag = this.branchInfoDAO.delete(branchCode) > 0;
		// 删除机构类型表的数据
		boolean deleteType = this.branchHasTypeDAO.deleteByBranch(branchCode) > 0;
		
		try {
			workflowService.deleteFlow(WorkflowConstants.WORKFLOW_ADD_BRANCH, branchCode);
		} catch (Exception e) {
			throw new BizException("删除机构审核流程信息失败，原因：" + e.getMessage());
		}
		
		return flag && deleteType;
	}

	public boolean stopBranch(String branchCode, UserInfo userInfo) throws BizException {
		Assert.notEmpty(branchCode, "停用机构的编号不能为空");
		
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(branchCode);
		Assert.notNull(branchInfo, "找不到该机构[" + branchCode + "]");
		
		branchInfo.setStatus(BranchState.STOPED.getValue());
		branchInfo.setUpdateUser(userInfo.getUserId());
		branchInfo.setUpdateTime(new Date());
		
		int count = this.branchInfoDAO.update(branchInfo);
		return count > 0;
	}
	
	public void addProxy(BranchProxy branchProxy, String[] privileges, String userCode) throws BizException {
		Assert.notNull(branchProxy, "代理属性不能为空");
		
		String proxyId = branchProxy.getProxyId();
		String respOrg = branchProxy.getRespOrg();
		Assert.notTrue(!this.branchInfoDAO.isExist(proxyId), "机构不存在[" + proxyId +"]");
		Assert.notTrue(!this.branchInfoDAO.isExist(respOrg), "机构不存在[" + respOrg +"]");
		
		BranchProxy oldBranchProxy = (BranchProxy) this.branchProxyDAO.findByPk(
				new BranchProxyKey(proxyId, respOrg, branchProxy.getProxyType()));
		Assert.isNull(oldBranchProxy, "该代理关系已经存在[" + proxyId + ","+ respOrg +"]");
		
		branchProxy.setStatus(ProxyState.NORMAL.getValue());
		branchProxy.setUpdateBy(userCode);
		branchProxy.setUpdateTime(new Date());
		
		this.branchProxyDAO.insert(branchProxy);
		if (ProxyType.SELL.getValue().equals(branchProxy.getProxyType())){
			this.assignProxy(branchProxy, privileges, userCode);
		}
	}
	
	public void cancelProxy(BranchProxyKey branchProxyKey, String userCode) throws BizException {
		Assert.notNull(branchProxyKey, "代理属性不能为空");
		
		BranchProxy branchProxy = (BranchProxy) this.branchProxyDAO.findByPk(branchProxyKey);
		Assert.notNull(branchProxy, "该代理关系不存在[" + branchProxyKey.getProxyId() 
				+ ","+ branchProxyKey.getRespOrg() +"]");
		
		branchProxy.setStatus(ProxyState.CANCEL.getValue());
		branchProxy.setUpdateBy(userCode);
		branchProxy.setUpdateTime(new Date());
		
		this.branchProxyDAO.update(branchProxy);
	}
	
	public void activateProxy(BranchProxyKey branchProxyKey, String userCode) throws BizException {
		Assert.notNull(branchProxyKey, "代理属性不能为空");
		
		BranchProxy branchProxy = (BranchProxy) this.branchProxyDAO.findByPk(branchProxyKey);
		Assert.notNull(branchProxy, "该代理关系不存在[" + branchProxyKey.getProxyId() 
				+ ","+ branchProxyKey.getRespOrg() +"]");
		
		branchProxy.setStatus(ProxyState.NORMAL.getValue());
		branchProxy.setUpdateBy(userCode);
		branchProxy.setUpdateTime(new Date());
		
		this.branchProxyDAO.update(branchProxy);
	}

	public void deleteProxy(BranchProxyKey branchProxyKey,
			String sessionUserCode) throws BizException {
		Assert.notNull(branchProxyKey, "代理属性不能为空");
		
		BranchProxy branchProxy = (BranchProxy) this.branchProxyDAO.findByPk(branchProxyKey);
		Assert.notNull(branchProxy, "该代理关系不存在[" + branchProxyKey.getProxyId() 
				+ ","+ branchProxyKey.getRespOrg() +"]");
		
		this.branchProxyDAO.delete(branchProxyKey);
	}
	
	public void assignProxy(BranchProxy branchProxy, String[] privileges,
			String sessionUserCode) throws BizException {
		Assert.notNull(branchProxy, "代理属性不能为空");
		Assert.notNull(privileges, "代理的权限不能为空");
		
		this.saleProxyPrivilegeDAO.deleteByBranch(branchProxy.getProxyId(), branchProxy.getRespOrg());
		
		// 售卡代理允许自定义权限
		for (int i = 0; i < privileges.length; i++) {
			SaleProxyPrivilege saleProxyPrivilege = new SaleProxyPrivilege();
			saleProxyPrivilege.setCardBranch(branchProxy.getRespOrg());
			saleProxyPrivilege.setProxyBranch(branchProxy.getProxyId());
			saleProxyPrivilege.setLimitId(privileges[i]);
			this.saleProxyPrivilegeDAO.insert(saleProxyPrivilege);
		}
	}
	
	public void addCardMerch(CardToMerch cardToMerch, String[] merchs, String userCode)
			throws BizException {
		Assert.notNull(cardToMerch, "发卡机构与商户关系信息不能为空");
		
		String branchCode = cardToMerch.getBranchCode();
		String proxyId = cardToMerch.getProxyId();
		Assert.notTrue(!this.branchInfoDAO.isExist(branchCode), "机构不存在[" + branchCode +"]");
		Assert.notTrue(!this.branchInfoDAO.isExist(proxyId), "代理机构不存在[" + proxyId +"]");
		
		for (String merch : merchs) {
			CardToMerch old = (CardToMerch) this.cardToMerchDAO.findByPk(
					new CardToMerchKey(branchCode, merch));
			Assert.isNull(old, "发卡机构与商户关系信息已经存在[" + branchCode + ", " + merch + "]");
			
			cardToMerch.setMerchId(merch);
			cardToMerch.setStatus(CardMerchState.NORMAL.getValue());
			cardToMerch.setUpdateBy(userCode);
			cardToMerch.setUpdateTime(new Date());
			
			this.cardToMerchDAO.insert(cardToMerch);
		}
	}
	
	public void cancelCardMerch(CardToMerchKey cardToMerchKey, String userCode) 
			throws BizException {
		Assert.notNull(cardToMerchKey, "发卡机构与商户关系信息不能为空");
		
		CardToMerch old = (CardToMerch) this.cardToMerchDAO.findByPk(cardToMerchKey);
		Assert.notNull(old, "发卡机构与商户关系信息不存在[" + cardToMerchKey.getBranchCode() + ", " 
				+ cardToMerchKey.getMerchId() + "]");
		
		old.setStatus(CardMerchState.CANCEL.getValue());
		old.setUpdateBy(userCode);
		old.setUpdateTime(new Date());
		
		this.cardToMerchDAO.update(old);
	}
	
	public void activateCardMerch(CardToMerchKey cardToMerchKey, String userCode) 
			throws BizException {
		Assert.notNull(cardToMerchKey, "发卡机构与商户关系信息不能为空");
		
		CardToMerch old = (CardToMerch) this.cardToMerchDAO.findByPk(cardToMerchKey);
		Assert.notNull(old, "发卡机构与商户关系信息不存在[" + cardToMerchKey.getBranchCode() + ", " 
				+ cardToMerchKey.getMerchId() + "]");
		
		old.setStatus(CardMerchState.NORMAL.getValue());
		old.setUpdateBy(userCode);
		old.setUpdateTime(new Date());
		
		this.cardToMerchDAO.update(old);
	}

	public void deleteCardMerch(CardToMerchKey cardToMerchKey,
			String sessionUserCode) throws BizException {
		Assert.notNull(cardToMerchKey, "发卡机构与商户关系信息不能为空");
		
		CardToMerch old = (CardToMerch) this.cardToMerchDAO.findByPk(cardToMerchKey);
		Assert.notNull(old, "发卡机构与商户关系信息不存在[" + cardToMerchKey.getBranchCode() + ", " 
				+ cardToMerchKey.getMerchId() + "]");
		
		this.cardToMerchDAO.delete(old);
	}
	
	public void addDept(DepartmentInfo departmentInfo, String privileges,
			String sessionUserCode) throws BizException {
		Assert.notNull(departmentInfo, "部门信息不能为空");
		Assert.notNull(privileges, "部门拥有的权限不能为空");
		
		departmentInfo.setStatus(DepartmentState.NORMAL.getValue());
		departmentInfo.setUpdateBy(sessionUserCode);
		departmentInfo.setUpdateTime(new Date());
		this.departmentInfoDAO.insert(departmentInfo);
		
		// 添加部门拥有的权限
		String[] privilegeArray = privileges.split(",");
		this.addDeptRootPrivilge(departmentInfo.getDeptId());
		
		List<BranchPrivilege> privilegeList = new ArrayList<BranchPrivilege>();
		BranchPrivilege branchPrivilege = null;
		for (String privilege : privilegeArray) {
			branchPrivilege = new BranchPrivilege();
			branchPrivilege.setDeptId(departmentInfo.getDeptId());
			branchPrivilege.setLimitId(privilege);
			
			privilegeList.add(branchPrivilege);
		}
		this.branchPrivilegeDAO.insertBatch(privilegeList);
	}
	
	public void modifyDept(DepartmentInfo departmentInfo, String privileges,
			String sessionUserCode) throws BizException {
		Assert.notNull(departmentInfo, "部门信息不能为空");
		Assert.notNull(privileges, "部门拥有的权限不能为空");
		
		
		String deptId = departmentInfo.getDeptId();
		DepartmentInfo old = (DepartmentInfo) this.departmentInfoDAO.findByPk(deptId);
		Assert.notNull(old, "该部门不存在[" + deptId +"]");
		
		departmentInfo.setBranchNo(old.getBranchNo());
		departmentInfo.setMerchantNo(old.getMerchantNo());
		departmentInfo.setStatus(old.getStatus());
		departmentInfo.setUpdateBy(sessionUserCode);
		departmentInfo.setUpdateTime(new Date());
		this.departmentInfoDAO.update(departmentInfo);
		
		// 先删除部门拥有的权限，再添加
		this.branchPrivilegeDAO.deleteByDeptId(deptId);
		
		String[] privilegeArray = privileges.split(",");
		this.addDeptRootPrivilge(departmentInfo.getDeptId());
		
		List<BranchPrivilege> privilegeList = new ArrayList<BranchPrivilege>();
		BranchPrivilege branchPrivilege = null;
		for (String privilege : privilegeArray) {
			branchPrivilege = new BranchPrivilege();
			branchPrivilege.setDeptId(departmentInfo.getDeptId());
			branchPrivilege.setLimitId(privilege);
		
			privilegeList.add(branchPrivilege);
		}
		this.branchPrivilegeDAO.insertBatch(privilegeList);
	}
	
	/**
	 * 添加root权限点
	 * @param roleId
	 */
	private void addDeptRootPrivilge(String deptId){
		BranchPrivilege branchPrivilege = new BranchPrivilege();
		branchPrivilege.setDeptId(deptId);
		branchPrivilege.setLimitId(Constants.ROOT_PRIVILEGE_CODE);
		this.branchPrivilegeDAO.insert(branchPrivilege);
	}
	
	public void cancelDept(String deptId, String sessionUserCode) throws BizException {
		Assert.notEmpty(deptId, "部门信息不能为空");
		
		DepartmentInfo old = (DepartmentInfo) this.departmentInfoDAO.findByPk(deptId);
		Assert.notNull(old, "该部门不存在[" + deptId +"]");
		
		old.setStatus(DepartmentState.CANCEL.getValue());
		old.setUpdateBy(sessionUserCode);
		old.setUpdateTime(new Date());
		this.departmentInfoDAO.update(old);
	}
	
	public void activeDept(String deptId, String sessionUserCode) throws BizException {
		Assert.notEmpty(deptId, "部门信息不能为空");
		
		DepartmentInfo old = (DepartmentInfo) this.departmentInfoDAO.findByPk(deptId);
		Assert.notNull(old, "该部门不存在[" + deptId +"]");
		
		old.setStatus(DepartmentState.NORMAL.getValue());
		old.setUpdateBy(sessionUserCode);
		old.setUpdateTime(new Date());
		this.departmentInfoDAO.update(old);
	}
	
	public void addCardGroup(String groupId, String branches,
			UserInfo sessionUser) throws BizException {
		Assert.notEmpty(groupId, "集团编号不能为空");
		Assert.notEmpty(branches, "传入的发卡机构信息不能空");
		Assert.notNull(sessionUser, "登录用户信息不能为空");
		
		CardGroup group = new CardGroup();
		group.setGroupId(groupId);
		group.setStatus(CommonState.NORMAL.getValue());
		group.setUpdateBy(sessionUser.getUserId());
		group.setUpdateTime(new Date());
		
		String[] branchIds = branches.split(",");
		for (int i = 0; i < branchIds.length; i++) {
			group.setBranchCode(branchIds[i]);
			
			CardGroup cardGroup = (CardGroup) this.cardGroupDAO.findByPk(branchIds[i]);
			Assert.isNull(cardGroup, "发卡机构[" + branchIds[i] + "]已经与集团[" + groupId + "]建立了关系");
			
			this.cardGroupDAO.insert(group);
		}
	}
	
	public boolean cancelCardGroup(String branchCode, String groupId, UserInfo sessionUser)
			throws BizException {
		Assert.notEmpty(branchCode, "传入的发卡机构号不能空");
		Assert.notNull(sessionUser, "登录用户信息不能为空");
		
		CardGroup group = (CardGroup) this.cardGroupDAO.findByPk(branchCode);
		Assert.notNull(group, "发卡机构[" + branchCode + "]与集团[" + groupId + "]的关系已经不存在");
		
		group.setStatus(CommonState.DISABLE.getValue());
		group.setUpdateBy(sessionUser.getUserId());
		group.setUpdateTime(new Date());
		return cardGroupDAO.update(group) > 1;
	}
	
	public boolean activateCardGroup(String branchCode, String groupId, UserInfo sessionUser)
			throws BizException {
		Assert.notEmpty(branchCode, "传入的发卡机构号不能空");
		Assert.notNull(sessionUser, "登录用户信息不能为空");
		
		CardGroup group = (CardGroup) this.cardGroupDAO.findByPk(branchCode);
		Assert.notNull(group, "发卡机构[" + branchCode + "]与集团[" + groupId + "]的关系已经不存在");
		
		group.setStatus(CommonState.NORMAL.getValue());
		group.setUpdateBy(sessionUser.getUserId());
		group.setUpdateTime(new Date());
		return cardGroupDAO.update(group) > 1;
	}
	
	public boolean deleteCardGroup(String branchCode) throws BizException {
		Assert.notEmpty(branchCode, "传入的发卡机构号不能空");
		CardGroup group = (CardGroup) this.cardGroupDAO.findByPk(branchCode);
		Assert.notNull(group, "发卡机构[" + branchCode + "]没有与任何集团建立关系");
		return cardGroupDAO.delete(branchCode) > 1;
	}

	public boolean addCardToMakeCard(CardToMakeCard cardToMakeCard,
			String sessionUserCode) throws BizException {
		Assert.notNull(cardToMakeCard, "添加的发卡机构与制卡机构关系对象不能为空");
		
		//检查新增对象是否已经存在
		CardToMakeCardKey key = new CardToMakeCardKey();
		key.setBranchCode(cardToMakeCard.getBranchCode());
		key.setMakeCard(cardToMakeCard.getMakeCard());
		CardToMakeCard old = (CardToMakeCard)this.cardToMakeCardDAO.findByPk(key);
		
		Assert.isNull(old, "发卡机构[" + cardToMakeCard.getBranchCode() + "]与制卡机构["
				+ cardToMakeCard.getMakeCard() + "]关系已经存在，不能重复定义。");
				
		cardToMakeCard.setStatus(CardTypeState.NORMAL.getValue());		
		cardToMakeCard.setUpdateTime(new Date());
		cardToMakeCard.setUpdateBy(sessionUserCode);
				
		return this.cardToMakeCardDAO.insert(cardToMakeCard) != null; 
	}

	public boolean deleteCardToMakeCard(CardToMakeCardKey key)
			throws BizException {
		Assert.notNull(key, "删除的发卡机构与制卡机构关系对象不能为空");		
		return this.cardToMakeCardDAO.delete(key) > 0;	
	}

	public boolean modifyCardToMakeCard(CardToMakeCard cardToMakeCard,
			String sessionUserCode) throws BizException {
		Assert.notNull(cardToMakeCard, "更新的发卡机构与制卡机构关系对象不能为空");		
		
		if(CardTypeState.NORMAL.getValue().equals(cardToMakeCard.getStatus())){
			cardToMakeCard.setStatus(CardTypeState.CANCEL.getValue());
		}else if(CardTypeState.CANCEL.getValue().equals(cardToMakeCard.getStatus())){
			cardToMakeCard.setStatus(CardTypeState.NORMAL.getValue());
		}
		cardToMakeCard.setUpdateBy(sessionUserCode);
		cardToMakeCard.setUpdateTime(new Date());
		return this.cardToMakeCardDAO.update(cardToMakeCard) > 0;
	}

	public boolean addInsBankacct(InsBankacct insBankacct,
			String sessionUserCode) throws BizException {
		Assert.notNull(insBankacct, "添加的银行帐户信息不能为空");
		
		//检查新增对象是否已经存在
		InsBankacctKey key = new InsBankacctKey();
		key.setBankAcctType(insBankacct.getBankAcctType());
		key.setInsCode(insBankacct.getInsCode());
		key.setType(insBankacct.getType());
		InsBankacct old = (InsBankacct)this.insBankacctDAO.findByPk(key);
		
		Assert.isNull(old, insBankacct.getTypeName() + insBankacct.getInsCode() 
				+ "已经定义了账户类型为" + insBankacct.getBankAcctTypeName() + "的账户，不能重复定义。");
				
		insBankacct.setUpdateTime(new Date());
		insBankacct.setUpdateBy(sessionUserCode);
				
		return this.insBankacctDAO.insert(insBankacct) != null; 
	}

	public boolean deleteInsBankacct(InsBankacctKey key) throws BizException {
		Assert.notNull(key, "删除的银行帐户信息不能为空");		
		return this.insBankacctDAO.delete(key) > 0;	
	}

	public boolean modifyInsBankacct(InsBankacct insBankacct,
			String sessionUserCode) throws BizException {
		Assert.notNull(insBankacct, "更新的银行帐户信息不能为空");		
		
		insBankacct.setUpdateTime(new Date());
		insBankacct.setUpdateBy(sessionUserCode);
		return this.insBankacctDAO.update(insBankacct) > 0;
	}
}
