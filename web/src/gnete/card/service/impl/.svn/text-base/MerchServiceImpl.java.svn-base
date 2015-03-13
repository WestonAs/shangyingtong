package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.IOUtil;
import flink.util.StringUtil;
import gnete.card.dao.AreaDAO;
import gnete.card.dao.BankInfoDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardMerchGroupDAO;
import gnete.card.dao.CardMerchToGroupDAO;
import gnete.card.dao.CardToMerchDAO;
import gnete.card.dao.CardissuerFreeNumDAO;
import gnete.card.dao.CardissuerPlanFeeDAO;
import gnete.card.dao.CardissuerPlanFeeRuleDAO;
import gnete.card.dao.CostRecordDAO;
import gnete.card.dao.MerchGroupDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.MerchInfoRegDAO;
import gnete.card.dao.MerchToGroupDAO;
import gnete.card.dao.MerchTypeDAO;
import gnete.card.dao.RiskMerchDAO;
import gnete.card.dao.TerminalDAO;
import gnete.card.dao.UserInfoDAO;
import gnete.card.entity.BankInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardMerchGroup;
import gnete.card.entity.CardMerchGroupKey;
import gnete.card.entity.CardMerchToGroup;
import gnete.card.entity.CardMerchToGroupKey;
import gnete.card.entity.CardToMerch;
import gnete.card.entity.CardToMerchKey;
import gnete.card.entity.CardissuerFreeNum;
import gnete.card.entity.CardissuerPlanFee;
import gnete.card.entity.CardissuerPlanFeeRule;
import gnete.card.entity.CardissuerPlanFeeRuleKey;
import gnete.card.entity.CostRecord;
import gnete.card.entity.MerchGroup;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.MerchInfoReg;
import gnete.card.entity.MerchToGroup;
import gnete.card.entity.MerchType;
import gnete.card.entity.RiskMerch;
import gnete.card.entity.Terminal;
import gnete.card.entity.UserInfo;
import gnete.card.entity.flag.SetCycleFlag;
import gnete.card.entity.flag.TrueOrFalseFlag;
import gnete.card.entity.flag.UsePwdFlag;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.CardFreeNumState;
import gnete.card.entity.state.CardMerchGroupState;
import gnete.card.entity.state.CardMerchState;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.state.CostRecordState;
import gnete.card.entity.state.MerchState;
import gnete.card.entity.state.MerchTypeState;
import gnete.card.entity.state.TerminalState;
import gnete.card.entity.type.AcctType;
import gnete.card.entity.type.ChargeType;
import gnete.card.entity.type.CostRecordType;
import gnete.card.entity.type.RiskLevelType;
import gnete.card.entity.type.RoleType;
import gnete.card.service.MerchService;
import gnete.card.service.RoleService;
import gnete.card.service.UserService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;
import gnete.util.DateUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("merchService")
public class MerchServiceImpl implements MerchService {

	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private MerchTypeDAO merchTypeDAO;
	@Autowired
	private MerchGroupDAO merchGroupDAO;
	@Autowired
	private MerchToGroupDAO merchToGroupDAO;
	@Autowired
	private TerminalDAO terminalDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private CardMerchGroupDAO cardMerchGroupDAO;
	@Autowired
	private CardMerchToGroupDAO cardMerchToGroupDAO;
	@Autowired
	private CardToMerchDAO cardToMerchDAO;
	@Autowired
	private RiskMerchDAO riskMerchDAO;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private MerchInfoRegDAO merchInfoRegDAO;
	@Autowired
	private AreaDAO areaDAO;
	@Autowired
	private BankInfoDAO bankInfoDAO;
	@Autowired
	private UserInfoDAO userInfoDAO;
	@Autowired
	private CardissuerPlanFeeDAO cardissuerPlanFeeDAO;
	@Autowired
	private CardissuerPlanFeeRuleDAO cardissuerPlanFeeRuleDAO;
	@Autowired
	private CostRecordDAO costRecordDAO;
	@Autowired
	private CardissuerFreeNumDAO cardissuerFreeNumDAO;
	
	/**
	 * 默认编码格式
	 */
	private static final String DEFAULT_ENCODING = "GBK";
	
	/**
	 * 商户文件中每一条数据数组的元素个数
	 */
	private static final int DETAIL_SUM = 25;
	
	/**
	 * 默认分隔符
	 */
	private static final String DEFAULT_SEQ = ",";
	
	private static Logger logger = Logger.getLogger(MerchServiceImpl.class);
	
	public void addMerch(MerchInfoReg merchInfoReg, UserInfo userInfo)
			throws BizException {
		Assert.notNull(merchInfoReg, "商户不能为空");
		
		// 默认按天
		merchInfoReg.setSetCycle(SetCycleFlag.DAY.getValue());

		String manageBranch = merchInfoReg.getManageBranch();
		Assert.notTrue(!this.branchInfoDAO.isExist(manageBranch), "机构不存在[" + manageBranch + "]");

		merchInfoReg.setStatus(MerchState.WAITED.getValue());
		merchInfoReg.setUpdateBy(userInfo.getUserId());
		merchInfoReg.setUpdateTime(new Date());

		this.merchInfoRegDAO.insert(merchInfoReg);
		
		// 5.启动流程
		startMerchWorkFlow(merchInfoReg, userInfo);
	}
	
	public void checkMerchPass(MerchInfoReg merchInfoReg, String userId)
			throws BizException {
		Assert.notNull(merchInfoReg, "审核的商户对象不能为空");
		Assert.notEmpty(userId, "登录用户的用户ID不能为空");
		
		//1. 首先更改商户登记薄的状态为可用，同时在商户表中插入记录
		merchInfoReg.setStatus(MerchState.NORMAL.getValue());
		String merchId = getMerchIdStr(merchInfoReg);

		MerchInfo old = (MerchInfo) this.merchInfoDAO.findByPk(merchId);
		Assert.isNull(old, "商户编号为[" + merchId + "]的商户已经存在，请联系系统管理员重试！");
		merchInfoReg.setMerchId(merchId);

		merchInfoReg.setStatus(MerchState.NORMAL.getValue());
		merchInfoReg.setUpdateBy(userId);
		merchInfoReg.setUpdateTime(new Date());
		
		this.merchInfoRegDAO.update(merchInfoReg);
		MerchInfo merchInfo = new MerchInfo();
		copyMerchInfoRegToMerchInfo(merchInfoReg, merchInfo);
		this.merchInfoDAO.insert(merchInfo);
		
		// 2. 添加商户用户
		String adminUserId = merchInfoReg.getAdminId();
		Assert.notEmpty(adminUserId, "添加的用户ID不能为空");
		Assert.notTrue(this.userService.getUser(adminUserId) != null, "用户[" + adminUserId +"]已存在");
		
		UserInfo admin = new UserInfo();
		
		admin.setUserId(adminUserId);
		admin.setUserName(merchInfoReg.getMerchName() + "管理员");
		admin.setMerchantNo(merchId);
		admin.setUpdateTime(new Date());
		admin.setUpdateUser(userId);
		
		this.userService.addUser(admin, SysparamCache.getInstance().getSysUser());
		
		// 3. 分配用户以商户管理员角色
		String roleId = this.roleService.addDefaultAdmin(merchId, merchInfoReg.getMerchName(), false, RoleType.MERCHANT.getValue(), userId);
		this.roleService.bindUserRole(admin.getUserId(), roleId);
		
		// 4.如果发卡机构编号不为空，则添加特约商户关系
		if (StringUtils.isNotBlank(merchInfoReg.getCardBranch())) {
			String branchCode = merchInfoReg.getCardBranch();
			CardToMerch oldCardMerch = (CardToMerch) this.cardToMerchDAO.findByPk(new CardToMerchKey(branchCode, merchId));
			Assert.isNull(oldCardMerch, "发卡机构与商户关系信息已经存在[" + branchCode + ", " + merchId + "]");
			
			CardToMerch cardToMerch = new CardToMerch();
			cardToMerch.setMerchId(merchId);
			cardToMerch.setBranchCode(branchCode);
			cardToMerch.setProxyId(branchCode);
			cardToMerch.setStatus(CardMerchState.NORMAL.getValue());
			cardToMerch.setUpdateBy(userId);
			cardToMerch.setUpdateTime(new Date());
			this.cardToMerchDAO.insert(cardToMerch);
		}
	}
	
	
	public void addOldMerch(MerchInfoReg merchInfoReg, UserInfo userInfo) throws BizException {
		Assert.notNull(merchInfoReg, "旧商户信息不能为空");
		
		// 默认按天
		merchInfoReg.setSetCycle(SetCycleFlag.DAY.getValue());
		
		String manageBranch = merchInfoReg.getManageBranch();
		Assert.notTrue(!this.branchInfoDAO.isExist(manageBranch), "管理机构["+ manageBranch + "]不存在");
		
		MerchInfo old = (MerchInfo) this.merchInfoDAO.findByPk(merchInfoReg.getMerchId());
		Assert.isNull(old, "该商户编号已经存在[" + merchInfoReg.getMerchId() + "]");
		
		merchInfoReg.setStatus(MerchState.WAITED.getValue());
		merchInfoReg.setUpdateBy(userInfo.getUserId());
		merchInfoReg.setUpdateTime(new Date());
		
		this.merchInfoRegDAO.insert(merchInfoReg);
		
		startMerchWorkFlow(merchInfoReg, userInfo);
	}
	
	public String[] addMerchFile(File file, MerchInfoReg merchInfoReg,
			UserInfo userInfo) throws BizException {
		Assert.notNull(merchInfoReg, "添加的商户登记对象不能为空");
		Assert.notEmpty(merchInfoReg.getManageBranch(), "管理机构编号不能为空");
		Assert.notEmpty(merchInfoReg.getOpenFlag(), "开通标志不能为空");
		Assert.notEmpty(merchInfoReg.getCurrCode(), "货币代码不能为空");
		Assert.notNull(file, "上传的商户文件不能为空");
		Assert.notNull(userInfo, "登录用户的信息对象不能为空");
		
		String manageBranch = merchInfoReg.getManageBranch();
		Assert.isTrue(this.branchInfoDAO.isExist(manageBranch), "管理机构[" + manageBranch + "]不存在");
		
		List<MerchInfoReg> list = resolveFile(file);
		
		// 在数据库中插入数据，并启动审核流程
		String[] regs = insertToMerchInfoReg(list, merchInfoReg, userInfo);
		return regs;
	}
	
	public void modifyMerch(MerchInfo merchInfo, String userCode)
			throws BizException {
		Assert.notNull(merchInfo, "商户不能为空");

		String merchId = merchInfo.getMerchId();
		String manageBranch = merchInfo.getManageBranch();
		Assert.notTrue(!this.branchInfoDAO.isExist(manageBranch), "管理机构[" + manageBranch + "]不存在");

		MerchInfo old = (MerchInfo) this.merchInfoDAO.findByPk(merchId);
		Assert.notNull(old, "该商户不存在[" + merchId + "]");

		merchInfo.setStatus(old.getStatus());
		merchInfo.setUpdateBy(userCode);
		merchInfo.setUpdateTime(new Date());

		this.merchInfoDAO.update(merchInfo);
	}
	
	public void modifyMerchReg(MerchInfoReg merchInfoReg, UserInfo userInfo)
			throws BizException {
		Assert.notNull(merchInfoReg, "商户登记对象不能为空");
		Assert.notEmpty(merchInfoReg.getId(), "商户登记ID不能为空");
		
		String manageBranch = merchInfoReg.getManageBranch();
		Assert.notTrue(!this.branchInfoDAO.isExist(manageBranch), "管理机构[" + manageBranch + "]不存在");
		
		MerchInfoReg old = (MerchInfoReg) this.merchInfoRegDAO.findByPk(merchInfoReg.getId());
		Assert.notNull(old, "该商户不存在[" + merchInfoReg.getId() + "]");
		
		merchInfoReg.setStatus(old.getStatus());
		merchInfoReg.setUpdateBy(userInfo.getUserId());
		merchInfoReg.setUpdateTime(new Date());
		
		this.merchInfoRegDAO.update(merchInfoReg);
	}

	public void cancelMerch(String merchId, String userCode)
			throws BizException {
		Assert.notEmpty(merchId, "商户不能为空");

		MerchInfo old = (MerchInfo) this.merchInfoDAO.findByPk(merchId);
		Assert.notNull(old, "该商户不存在[" + merchId + "]");

		old.setStatus(MerchState.CANCEL.getValue());
		old.setUpdateBy(userCode);
		old.setUpdateTime(new Date());

		this.merchInfoDAO.update(old);
	}

	public void activeMerch(String merchId, String userCode)
			throws BizException {
		Assert.notEmpty(merchId, "商户不能为空");

		MerchInfo old = (MerchInfo) this.merchInfoDAO.findByPk(merchId);
		Assert.notNull(old, "该商户不存在[" + merchId + "]");

		old.setStatus(MerchState.NORMAL.getValue());
		old.setUpdateBy(userCode);
		old.setUpdateTime(new Date());

		this.merchInfoDAO.update(old);
	}
	
	public void submitCheck(UserInfo user) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", MerchState.PRESUB.getValue());
		
		List<MerchInfoReg> merchInfoList = this.merchInfoRegDAO.find(params);
		if (CollectionUtils.isEmpty(merchInfoList)) {
			throw new BizException("没有需要提交审核的商户");
		}
		
		for (MerchInfoReg merchInfoReg : merchInfoList) {
			Assert.notEmpty(merchInfoReg.getId(), "要提交审核的商户登记id不能为空");
			
			// 更新状态为待审核
			merchInfoReg.setStatus(MerchState.WAITED.getValue());
			merchInfoReg.setUpdateBy(user.getUserId());
			merchInfoReg.setUpdateTime(new Date());
			this.merchInfoRegDAO.update(merchInfoReg);
			
			this.startMerchWorkFlow(merchInfoReg, user);
		}
	}
	
	public boolean deleteMerch(String merchId) throws BizException {
		Assert.notEmpty(merchId, "要删除的商户ID不能为空");
		
		MerchInfo old = (MerchInfo) this.merchInfoDAO.findByPk(merchId);
		Assert.notNull(old, "商户[" + merchId + "]不存在");
		Assert.isTrue(MerchState.UNPASS.getValue().equals(old.getStatus()), "只能删除状态为“审核不通过”的商户");
		// 删除商户信息表中的数据
		boolean delMerch = this.merchInfoDAO.delete(merchId) > 0;
		
		// 同时还要删除该商户号的流程信息
		try {
			workflowService.deleteFlow(WorkflowConstants.WORKFLOW_ADD_MERCH, merchId);
		} catch (Exception e) {
			throw new BizException("删除商户流程信息失败，原因：" + e.getMessage());
		}
		
		return delMerch;
	}

	public void addMerchType(MerchType merchType, String userCode)
			throws BizException {
		Assert.notNull(merchType, "商户类型不能为空");

		String type = merchType.getMerchType();

		MerchType old = (MerchType) this.merchTypeDAO.findByPk(type);
		Assert.isNull(old, "该商户类型已经存在[" + type + "]");

		merchType.setStatus(MerchTypeState.NORMAL.getValue());
		merchType.setUpdateBy(userCode);
		merchType.setUpdateTime(new Date());

		this.merchTypeDAO.insert(merchType);
	}

	public void modifyMerchType(MerchType merchType, String userCode)
			throws BizException {
		Assert.notNull(merchType, "商户类型不能为空");

		String type = merchType.getMerchType();

		MerchType old = (MerchType) this.merchTypeDAO.findByPk(type);
		Assert.notNull(old, "该商户类型不存在[" + type + "]");

		merchType.setStatus(old.getStatus());
		merchType.setUpdateBy(userCode);
		merchType.setUpdateTime(new Date());
		this.merchTypeDAO.update(merchType);
	}

	public void cancelMerchType(String merchType, String userCode)
			throws BizException {
		Assert.notNull(merchType, "商户类型不能为空");

		MerchType old = (MerchType) this.merchTypeDAO.findByPk(merchType);
		Assert.notNull(old, "该商户类型不存在[" + merchType + "]");

		old.setStatus(MerchTypeState.CANCEL.getValue());
		old.setUpdateBy(userCode);
		old.setUpdateTime(new Date());
		this.merchTypeDAO.update(old);
	}

	public void activeMerchType(String merchType, String userCode)
			throws BizException {
		Assert.notNull(merchType, "商户类型不能为空");

		MerchType old = (MerchType) this.merchTypeDAO.findByPk(merchType);
		Assert.notNull(old, "该商户类型不存在[" + merchType + "]");

		old.setStatus(MerchTypeState.NORMAL.getValue());
		old.setUpdateBy(userCode);
		old.setUpdateTime(new Date());
		this.merchTypeDAO.update(old);
	}

	public void addMerchGroup(MerchGroup merchGroup, String merchs,
			UserInfo userInfo) throws BizException {
		Assert.notNull(merchGroup, "商圈不能为空");

		String groupId = generateMerchGroupId();

		String[] merchArray = merchs.split(",");
		if (!ArrayUtils.isEmpty(merchArray)) {
			for (String merchId : merchArray) {
				MerchToGroup toGroup = new MerchToGroup();
				toGroup.setGroupId(groupId);
				toGroup.setMerchId(merchId);
				toGroup.setUpdateBy(userInfo.getUserId());
				toGroup.setUpdateTime(new Date());
				
				this.merchToGroupDAO.insert(toGroup);
			}
		}

		merchGroup.setGroupId(groupId);
		merchGroup.setManageBranch(userInfo.getBranchNo());
		merchGroup.setStatus(CommonState.NORMAL.getValue());
		merchGroup.setUpdateBy(userInfo.getUserId());
		merchGroup.setInsertTime(new Date());

		this.merchGroupDAO.insert(merchGroup);
	}

	public void modifyMerchGroup(MerchGroup merchGroup, String merchs,
			String userCode) throws BizException {
		Assert.notNull(merchGroup, "商户组不能为空");
		Assert.notEmpty(merchs, "商户组内商户不能为空");

		String groupId = merchGroup.getGroupId();
		MerchGroup old = (MerchGroup) this.merchGroupDAO.findByPk(merchGroup);
		Assert.notNull(old, "该商户组不存在[" + groupId + "]");

		// 先删除商户组内的商户信息
		this.merchToGroupDAO.deleteByGroupId(groupId);

		String[] merchArray = merchs.split(",");
		for (String merchId : merchArray) {
			MerchToGroup toGroup = new MerchToGroup();
			toGroup.setGroupId(groupId);
			toGroup.setMerchId(merchId);
			toGroup.setUpdateBy(userCode);
			toGroup.setUpdateTime(new Date());

			this.merchToGroupDAO.insert(toGroup);
		}

		old.setGroupName(merchGroup.getGroupName());
		old.setCardIssuer(merchGroup.getCardIssuer());
		old.setUpdateBy(userCode);
		old.setUpdateTime(new Date());

		this.merchGroupDAO.update(old);
	}

	public void deleteMerchGroup(String groupId, String userCode)
			throws BizException {
		Assert.notEmpty(groupId, "商户组不能为空");

		MerchGroup old = (MerchGroup) this.merchGroupDAO.findByPk(groupId);
		Assert.notNull(old, "该商户组不存在[" + groupId + "]");

		// 先删除商户组内的商户信息
		this.merchToGroupDAO.deleteByGroupId(groupId);
		this.merchGroupDAO.delete(groupId);
	}
	
	public void addTerminal(Terminal terminal, String cardBranchCode, String userCode) throws BizException {
		Assert.notNull(terminal, "终端不能为空");
		Assert.notEmpty(terminal.getMerchId(), "商户编号不能为空");

		String merchId = terminal.getMerchId();
		String termId = this.terminalDAO.getNextNewTermId();
		terminal.setTermId(termId);

		String manageBranch = terminal.getManageBranch();
		String maintenance = terminal.getMaintenance();
		
		MerchInfo merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(merchId);
		Assert.notNull(merchInfo, "商户[" + merchId + "]不存在");
		
		if (Symbol.YES.equals(terminal.getSingleProduct())) {
			Assert.notEmpty(cardBranchCode, "发卡机构编号不能为空");
			
			BranchInfo branchInfo = this.branchInfoDAO.findBranchInfo(cardBranchCode);
			Assert.notNull(branchInfo, "发卡机构[" + cardBranchCode + "]不存在");
			Assert.isTrue(Symbol.YES.equals(branchInfo.getSingleProduct()), "发卡机构[" + cardBranchCode + "]不是单机产品机构");
			
			// 在套餐费用表中产生一条套餐费用
			this.recordCost(cardBranchCode, userCode, termId);
		}
		
		Assert.notTrue(StringUtils.isNotEmpty(manageBranch)
				&& !this.branchInfoDAO.isExist(manageBranch), "出机方不存在[" + manageBranch + "]");
		Assert.notTrue(StringUtils.isNotEmpty(maintenance)
				&& !this.branchInfoDAO.isExist(maintenance), "维护方不存在[" + maintenance + "]");

		Terminal old = (Terminal) this.terminalDAO.findByPk(termId);
		Assert.isNull(old, "该终端已经存在[" + termId + "]");

		terminal.setPosStatus(TerminalState.NORMAL.getValue());
		terminal.setUpdateBy(userCode);
		terminal.setUpdateTime(new Date());
		terminal.setCreateTime(new Date());

		this.terminalDAO.insert(terminal);
		
	}
	
	public void addOldTerminal(Terminal terminal, String userCode) throws BizException {
		Assert.notNull(terminal, "终端不能为空");
		
		String manageBranch = terminal.getManageBranch();
		String maintenance = terminal.getMaintenance();
		Assert.notTrue(!this.merchInfoDAO.isExist(terminal.getMerchId()), "商户不存在[" + terminal.getMerchId() + "]");
		Assert.notTrue(StringUtils.isNotEmpty(manageBranch)
				&& !this.branchInfoDAO.isExist(manageBranch), "出机方不存在[" + manageBranch + "]");
		Assert.notTrue(StringUtils.isNotEmpty(maintenance)
				&& !this.branchInfoDAO.isExist(maintenance), "维护方不存在[" + maintenance + "]");
		
		Terminal old = (Terminal) this.terminalDAO.findByPk(terminal.getTermId());
		Assert.isNull(old, "该终端编号已经存在[" + terminal.getTermId() + "]");
		
		terminal.setPosStatus(TerminalState.NORMAL.getValue());
		terminal.setUpdateBy(userCode);
		terminal.setUpdateTime(new Date());
		terminal.setCreateTime(new Date());
		
		this.terminalDAO.insert(terminal);
	}
	
	public String[] addTerminalBatch(Terminal terminal, String cardBranchCode, 
			int count, String userCode) throws BizException {
		Assert.notNull(terminal, "终端不能为空");
		Assert.notEmpty(terminal.getMerchId(), "商户编号不能为空");
		String[] terminalIdArray = new String[count];
		
		for (int i = 0; i < count; i++){
			String merchId = terminal.getMerchId();
			String termId = this.terminalDAO.getNextNewTermId();
			terminalIdArray[i] = termId;
			terminal.setTermId(termId);
	
			String manageBranch = terminal.getManageBranch();
			String maintenance = terminal.getMaintenance();
			Assert.notTrue(!this.merchInfoDAO.isExist(merchId), "商户不存在[" + merchId + "]");
			Assert.notTrue(StringUtils.isNotEmpty(manageBranch)
					&& !this.branchInfoDAO.isExist(manageBranch), "出机方不存在[" + manageBranch + "]");
			Assert.notTrue(StringUtils.isNotEmpty(maintenance)
					&& !this.branchInfoDAO.isExist(maintenance), "维护方不存在[" + maintenance + "]");
	
			Terminal old = (Terminal) this.terminalDAO.findByPk(termId);
			Assert.isNull(old, "该终端已经存在[" + termId + "]");
	
			terminal.setPosStatus(TerminalState.NORMAL.getValue());
			terminal.setUpdateBy(userCode);
			terminal.setUpdateTime(new Date());
			terminal.setCreateTime(new Date());
			
			this.terminalDAO.insert(terminal);
		}
		
		if (Symbol.YES.equals(terminal.getSingleProduct())) {
			Assert.notEmpty(cardBranchCode, "发卡机构编号不能为空");
			
			BranchInfo branchInfo = this.branchInfoDAO.findBranchInfo(cardBranchCode);
			Assert.notNull(branchInfo, "发卡机构[" + cardBranchCode + "]不存在");
			Assert.isTrue(Symbol.YES.equals(branchInfo.getSingleProduct()), "发卡机构[" + cardBranchCode + "]不是单机产品机构");
			// 在套餐费用表中产生一条套餐费用
			this.recordCost(cardBranchCode, userCode, terminalIdArray);
		}
		return terminalIdArray;
	}
	
	@Override
	public int addTermBatchFile(File upload, UserInfo sessionUser) throws BizException {
		
		List<Pair<Terminal, Integer>> termList = buildTermsFromFile(upload);
		
		int termTotalCnt = 0;// 新增终端总数（计算）
		for(Pair<Terminal, Integer> pair :termList){
			Terminal term = pair.getLeft();
			Integer termCnt = pair.getRight();
			termTotalCnt += termCnt;
			
			term.setSingleProduct(YesOrNoFlag.NO.getValue());
			term.setPosStatus(TerminalState.NORMAL.getValue());
			term.setCreateTime(new Date());
			term.setUpdateBy(sessionUser.getUserId());
			term.setUpdateTime(new Date());
			
			for(int i=0;i<termCnt;i++){
				term.setTermId(this.terminalDAO.getNextNewTermId());
				this.terminalDAO.insert(term);
			}
		}
		return termTotalCnt;
	}
	
	public void modifyTerminal(Terminal terminal, String userCode)
			throws BizException {
		Assert.notNull(terminal, "终端不能为空");

		String termId = terminal.getTermId();
		String merchId = terminal.getMerchId();
		String manageBranch = terminal.getManageBranch();
		String maintenance = terminal.getMaintenance();
		Assert.notTrue(!this.merchInfoDAO.isExist(merchId), "商户不存在[" + merchId+ "]");
		Assert.notTrue(StringUtils.isNotEmpty(manageBranch)
				&& !this.branchInfoDAO.isExist(manageBranch), "出机方不存在[" + manageBranch + "]");
		Assert.notTrue(StringUtils.isNotEmpty(maintenance)
				&& !this.branchInfoDAO.isExist(maintenance), "维护方不存在[" + maintenance + "]");
		
		Terminal old = (Terminal) this.terminalDAO.findByPk(termId);
		Assert.notNull(old, "该终端不存在[" + termId + "]");

		terminal.setPosStatus(old.getPosStatus());
		terminal.setUpdateBy(userCode);
		terminal.setUpdateTime(new Date());
		terminal.setCreateTime(old.getCreateTime());

		this.terminalDAO.update(terminal);
	}

	public void startTerminal(String termId, String userCode)
			throws BizException {
		Assert.notEmpty(termId, "终端不能为空");

		Terminal old = (Terminal) this.terminalDAO.findByPk(termId);
		Assert.notNull(old, "该终端不存在[" + termId + "]");

		old.setPosStatus(TerminalState.NORMAL.getValue());
		old.setUpdateBy(userCode);
		old.setUpdateTime(new Date());

		this.terminalDAO.update(old);
	}

	public void stopTerminal(String termId, String userCode)
			throws BizException {
		Assert.notEmpty(termId, "终端不能为空");

		Terminal old = (Terminal) this.terminalDAO.findByPk(termId);
		Assert.notNull(old, "该终端不存在[" + termId + "]");

		old.setPosStatus(TerminalState.STOP.getValue());
		old.setUpdateBy(userCode);
		old.setUpdateTime(new Date());

		this.terminalDAO.update(old);
	}

	public void addCardMerchGroup(CardMerchGroup cardMerchGroup,
			String merchants, String userId) throws BizException {
		Assert.notNull(cardMerchGroup, "要新增的发卡机构商户组对象不能为空");
		Assert.notEmpty(cardMerchGroup.getBranchCode(), "发卡机构不能为空");
//		Assert.notEmpty(merchants, "传入的商户信息不能为空");
		Assert.notEmpty(userId, "登录用户的用户ID不能为空");

		cardMerchGroup.setStatus(CardMerchGroupState.NORMAL.getValue());
		cardMerchGroup.setUpdateBy(userId);
		cardMerchGroup.setUpdateTime(new Date());
		cardMerchGroupDAO.insert(cardMerchGroup);
		
		if (StringUtils.isEmpty(merchants)){
			return;
		}
		CardMerchToGroup group = new CardMerchToGroup();
		String[] merchantId = merchants.split(",");
		for (int i = 0; i < merchantId.length; i++) {
			group.setBranchCode(cardMerchGroup.getBranchCode());
			group.setGroupName(cardMerchGroup.getGroupName());
			group.setGroupId(cardMerchGroup.getGroupId());
			group.setMerchId(merchantId[i]);
			group.setStatus(CardMerchGroupState.NORMAL.getValue());
			group.setUpdateBy(userId);
			group.setUpdateTime(new Date());

			cardMerchToGroupDAO.insert(group);
		}
	}

	public void modifyCardMerchGroup(CardMerchGroup cardMerchGroup,
			String merchants, String userId) throws BizException {
		Assert.notNull(cardMerchGroup, "要修改的发卡机构商户组对象不能为空");
		Assert.notEmpty(cardMerchGroup.getBranchCode(), "发卡机构不能为空");
//		Assert.notEmpty(merchants, "传入的商户信息不能为空");
		Assert.notEmpty(userId, "登录用户的用户ID不能为空");

		CardMerchGroupKey key = new CardMerchGroupKey();
		key.setBranchCode(cardMerchGroup.getBranchCode());
		key.setGroupId(cardMerchGroup.getGroupId());

		CardMerchGroup his = (CardMerchGroup) cardMerchGroupDAO.findByPk(key);
		cardMerchGroup.setStatus(his.getStatus());
		cardMerchGroup.setUpdateBy(userId);
		cardMerchGroup.setUpdateTime(new Date());
		cardMerchGroupDAO.update(cardMerchGroup);

		// 先删除发卡机构的商户关系表的记录
		CardMerchToGroupKey groupKey = new CardMerchToGroupKey();
		groupKey.setBranchCode(his.getBranchCode());
		groupKey.setGroupId(his.getGroupId());
		cardMerchToGroupDAO.deleteByBranchAndGroupId(groupKey);
		
		if (StringUtils.isEmpty(merchants)){
			return;
		}
		// 再增加发卡机构的商户关系表的记录
		CardMerchToGroup group = new CardMerchToGroup();
		group.setBranchCode(groupKey.getBranchCode());
		group.setGroupId(groupKey.getGroupId());
		String[] merchantId = merchants.split(",");
		for (int i = 0; i < merchantId.length; i++) {
			group.setMerchId(merchantId[i]);
			group.setGroupName(his.getGroupName());
			group.setStatus(his.getStatus());
			group.setUpdateBy(userId);
			group.setUpdateTime(new Date());

			cardMerchToGroupDAO.insert(group);
		}

	}

	public void cancelCardMerchGroup(String branchCode, String groupId)
			throws BizException {
		Assert.notEmpty(branchCode, "发卡机构代码不能为空");
		Assert.notEmpty(groupId, "商户组代码不能为空");

		CardMerchGroupKey key = new CardMerchGroupKey();
		key.setBranchCode(branchCode);
		key.setGroupId(groupId);

		CardMerchGroup group = (CardMerchGroup) cardMerchGroupDAO.findByPkWithLock(key);
		group.setStatus(CardMerchGroupState.CANCEL.getValue());
		group.setUpdateTime(new Date());
		
		cardMerchGroupDAO.update(group);
	}

	public void startCardMerchGroup(String branchCode, String groupId)
			throws BizException {
		Assert.notEmpty(branchCode, "发卡机构代码不能为空");
		Assert.notEmpty(groupId, "商户组代码不能为空");
		
		CardMerchGroupKey key = new CardMerchGroupKey();
		key.setBranchCode(branchCode);
		key.setGroupId(groupId);

		CardMerchGroup group = (CardMerchGroup) cardMerchGroupDAO.findByPkWithLock(key);
		group.setStatus(CardMerchGroupState.NORMAL.getValue());
		group.setUpdateTime(new Date());
		
		cardMerchGroupDAO.update(group);
	}

	public boolean addRiskMerch(RiskMerch riskMerch, String userCode) throws BizException {
		Assert.notNull(riskMerch, "添加的风险商户对象不能为空");
		RiskMerch oldRiskMerch = (RiskMerch)this.riskMerchDAO.findByPk(riskMerch.getMerchId());
		Assert.isNull(oldRiskMerch, "添加的风险商户已经存在,请不要重复添加。");
		riskMerch.setUpdateBy(userCode);
		riskMerch.setUpdateTime(new Date());
		return this.riskMerchDAO.insert(riskMerch) != null;
	}

	public boolean deleteRiskMerch(String merchId) throws BizException {
		Assert.notNull(merchId, "删除的风险商户对象不能为空");		
		int count = this.riskMerchDAO.delete(merchId);
		return count > 0;
	}
	
	/*
	 * ----------------------------- private methods followed ------------------------------
	 */
	
	/**
	 * 启动商户审核流程
	 */
	private void startMerchWorkFlow(MerchInfoReg merchInfoReg, UserInfo userInfo) throws BizException{
		try {
			String roleType = userInfo.getRole().getRoleType();
			// 如果是发卡机构添加的商户，则启动另外的流程（由管理的分支机构来审核）
			if (RoleType.CARD.getValue().equals(roleType)
					|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
				this.workflowService.startFlow(merchInfoReg, WorkflowConstants.MERCH_CARD_ADAPTER, merchInfoReg.getId(), userInfo);
			}
			// 如果是运营中心或分支机构 或运营代理 添加的商户，则自己审核自己
			else if (RoleType.CENTER.getValue().equals(roleType)
					|| RoleType.CENTER_DEPT.getValue().equals(roleType)
					|| RoleType.FENZHI.getValue().equals(roleType)
					|| RoleType.AGENT.getValue().equals(roleType)) {
				this.workflowService.startFlow(merchInfoReg, WorkflowConstants.MERCH_ADAPTER, merchInfoReg.getId(), userInfo);
			}
		} catch (Exception e) {
			throw new BizException("启动商户审核流程时发生异常，原因：" + e.getMessage());
		}
	}
	
	/**
	 *  启动商户审核流程
	 */
	private void startAddMerchFileWorkFlow(MerchInfoReg merchInfoReg, UserInfo userInfo) throws BizException {
		try {
			String roleType = userInfo.getRole().getRoleType();
			// 如果是发卡机构或运营代理添加的商户，则启动另外的流程（由管理的分支机构来审核）
			if (RoleType.CARD.getValue().equals(roleType)
					|| RoleType.CARD_DEPT.getValue().equals(roleType)
					|| RoleType.AGENT.getValue().equals(roleType)) {
				this.workflowService.startFlow(merchInfoReg, WorkflowConstants.MERCH_CARD_ADAPTER, merchInfoReg.getId(), userInfo);
			}
			// 如果是运营中心或分支机构添加的商户，则自己审核自己
			else if (RoleType.CENTER.getValue().equals(roleType)
					|| RoleType.CENTER_DEPT.getValue().equals(roleType)
					|| RoleType.FENZHI.getValue().equals(roleType)) {
				this.workflowService.startFlow(merchInfoReg, WorkflowConstants.MERCH_ADAPTER, merchInfoReg.getId(), userInfo);
			}
		} catch (Exception e) {
			throw new BizException("启动商户审核流程时发生异常，原因：" + e.getMessage());
		}
		
	}
	
	
	/**
	 * 取得商户编号
	 * @throws BizException
	 */
	private String getMerchIdStr(MerchInfoReg merchInfoReg) throws BizException{
		
		// 新商户则按照规则来添加
		if (StringUtils.isEmpty(merchInfoReg.getMerchId())) {
			String merchId_prev = "";
			if (merchInfoReg.isMerch898()) {
				// 商户编号规则：898+地区代码（4位）+MCC码（4位，0003）+序号(4位)
				merchId_prev = "898" + merchInfoReg.getAreaCode() + "0003";
			} else {
				// 商户编号规则：208+地区代码（4位）+商户类型代码(4位)+序号(4位)
				merchId_prev = SysparamCache.getInstance().getSysNo() + merchInfoReg.getAreaCode()
						+ merchInfoReg.getMerchType();
			}
			
			String merchId_suf = this.merchInfoDAO.getMerchId(merchId_prev);
			if (StringUtils.isEmpty(merchId_suf)) {
				merchId_suf = "1000";
			} else if (merchId_suf.length() < 4) {
				merchId_suf = StringUtils.leftPad(merchId_suf, 4, '0');
			}
			
			return merchId_prev + merchId_suf;
		} else {
			// 如果添加的是旧商户，则商户登记簿中的商户号就不为空，则直接返回
			return merchInfoReg.getMerchId();
		}
	}

	/**
	 * 将商户登记簿的数据复制到商户信息表
	 */
	private void copyMerchInfoRegToMerchInfo(MerchInfoReg merchInfoReg, MerchInfo merchInfo) throws BizException {
		try {
			BeanUtils.copyProperties(merchInfo, merchInfoReg);
		} catch (Exception e) {
			throw new BizException("复制实体时发生异常");
		}
	}
	
	/**
	 * 解析商户文件，得到商户登记明细对象
	 * @param file 商户文件
	 * @throws BizException
	 */
	private List<MerchInfoReg> resolveFile(File file) throws BizException {
		byte[] fileData = getFileByte(file);
		
		List lines = getLines(fileData);
		
		Assert.isTrue(CollectionUtils.isNotEmpty(lines) && lines.size() > 1, "文件无内容或格式错误");
		
		return resolveDepositDetail(lines);
	}
	
	/**
	 * 解析商户明细
	 */
	private List<MerchInfoReg> resolveDepositDetail(List lines) throws BizException {
		List<MerchInfoReg> list = new ArrayList<MerchInfoReg>();
		
		// 获取表头字段数目
		int fieldNum = 0;
		if(lines.size()!= 0){
			fieldNum = ((String) lines.get(0)).split(DEFAULT_SEQ, -1).length;
		}
		
		// 记录卡号, 用作判断是否重复.
		Set<String> merchNameSet = new HashSet<String>();
		
		// 解析充值明细， 从第二行开始解析
		for (int i = 1, n = lines.size(); i < n; i++) {
			String depositLine = (String) lines.get(i);
			
			// 空行略过.
			if (StringUtils.isEmpty(depositLine)) {
				continue;
			}
			
			String[] arr = depositLine.split(DEFAULT_SEQ, -1);
			
			// 检查商户名是否有重复的，或是商户已经存在
			checkMerchName(arr, i, fieldNum, merchNameSet);
			
			// 取得商户明细对象
			MerchInfoReg merchInfoReg = getMerchInfoRegFromDetail(arr);
			
			list.add(merchInfoReg);
		}
		
		return list;
	}
	
	/**
	 * 读取上传文件的二进制流
	 * @param file
	 * @return
	 * @throws BizException
	 */
	private byte[] getFileByte(File file) throws BizException {
		byte[] fileData;
		try {
			fileData = IOUtils.toByteArray(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			logger.error(e, e);
			throw new BizException("上传文件时发生FileNotFoundException");
		} catch (IOException e) {
			logger.error(e, e);
			throw new BizException("上传文件时发生IOException");
		}
		return fileData;
	}
	
	/**
	 * 读取类容
	 * @param fileData
	 * @return
	 * @throws BizException
	 */
	private List getLines(byte[] fileData) throws BizException {
		// 读取内容.
		List lines = null;
		
		try {
			lines = IOUtils.readLines(new ByteArrayInputStream(fileData), DEFAULT_ENCODING);
		} catch (IOException e) {
			logger.error(e, e);
			throw new BizException("解析文件失败, 编码错误");
		}
		return lines;
	}
	
	
	/**
	 * 对商户文件明细里的商户名称，进行检查
	 * @param arr 单条记录的数组
	 * @param count 记录的条数的序号
	 * @param fieldNum 模板中的明细个数
	 * @param merchNameSet 商户文件明细的商户名称
	 * @throws BizException
	 */
	private void checkMerchName(String[] arr, int count, int fieldNum, 
			Set<String> merchNameSet) throws BizException {
		// 充值明细元素个数要和模板配置明细个数一致.
		if (arr.length != fieldNum) {
			String msg = "明细第[" + count + "]行格式错误,元素不为[" + fieldNum + "]个";
			logger.error(msg);
			throw new BizException(msg);
		}
		
		if (ArrayUtils.isEmpty(arr)) {
			throw new BizException("解析出的数组为空");
		}
		
		//取得商户名称,为空表示有错，抛出异常，跳出循环
		String merchName = arr[0];
		if (StringUtils.isBlank(merchName)) {
			throw new BizException("充值明细第[" + count + " ]行格式错误，商户名称不能为空。");
		}
		
		//商户名称不能重复
		if (merchNameSet.contains(merchName)) {
			throw new BizException("商户[" + merchName + "]的记录重复");
		}
		merchNameSet.add(merchName);
	}
	
	/**
	 * 从明细中取得充值明细对象
	 * @param arr
	 * @return
	 */
	private MerchInfoReg getMerchInfoRegFromDetail(String[] arr) throws BizException {
		Assert.isTrue(arr.length == DETAIL_SUM, "商户明细数组的长度有误，必须有[" + DETAIL_SUM + "]个元素");

		MerchInfoReg merchInfoReg = new MerchInfoReg();
		// 商户名称，最大长度为200
		Assert.isTrue(arr[0].length() <= 100, "商户名[" + arr[0] + "]超长");
		merchInfoReg.setMerchName(arr[0]);
		
		// 商户简称
		Assert.isTrue(arr[1].length() <= 15, "商户简称[" + arr[1] + "]超长");
		merchInfoReg.setMerchAbb(arr[1]);
		
		Assert.isTrue(arr[2].length() == 4, "商户类型代码[" + arr[2] + "]必须为4位");
		Assert.isTrue(this.merchTypeDAO.isExist(arr[2]), "商户类型[" + arr[2]+ "]不存在。");
		merchInfoReg.setMerchType(arr[2]);
		
		Assert.isTrue(arr[3].length() == 4, "地区码[" + arr[3] + "]必须为4位");
		Assert.isTrue(this.areaDAO.isExist(arr[3]), "地区码[" + arr[3] + "]不存在");
		merchInfoReg.setAreaCode(arr[3]);
		
		Assert.isTrue(arr[4].length() <= 30, "联系人[" + arr[4] + "]超长");
		merchInfoReg.setLinkMan(arr[4]);
		
		Assert.isTrue(arr[5].length() <= 30, "联系电话[" + arr[5] + "]超长");
		merchInfoReg.setTelNo(arr[5]);
		
		Assert.isTrue(arr[6].length() <= 120, "联系地址[" + arr[6] + "]超长");
		merchInfoReg.setMerchAddress(arr[6]);
		
		Assert.notNull(AcctType.ALL.get(arr[7]), "账户类型代码[" + arr[7] + "]录入不正确");
		merchInfoReg.setAcctType(arr[7]);
		
		BankInfo bankInfo = (BankInfo) this.bankInfoDAO.findByPk(arr[8]);
		Assert.notNull(bankInfo, "银行行号[" + arr[8] + "]不存在");
		merchInfoReg.setBankNo(bankInfo.getBankNo());
		merchInfoReg.setBankName(bankInfo.getBankName());
		merchInfoReg.setAccAreaCode(bankInfo.getAddrNo());
		
		Assert.isTrue(arr[9].length() <= 30, "账户户名[" + arr[9] + "]超长");
		merchInfoReg.setAccName(arr[9]);
		
		Assert.isTrue(arr[10].length() <= 32, "账号[" + arr[10] + "]超长");
		Assert.isTrue(StringUtil.isCorrectAccNo(arr[10]), "账号中只能包含数字，字符和“-”");
		merchInfoReg.setAccNo(arr[10]);
		
		Assert.notNull(UsePwdFlag.ALL.get(arr[11]), "是否使用密码标志类型代码[" + arr[11] + "]录入不正确");
		merchInfoReg.setUsePwdFlag(arr[11]);
		
		Assert.notNull(TrueOrFalseFlag.ALL.get(arr[12]), "清算资金是否类型代码[" + arr[12] + "]录入不正确");
		merchInfoReg.setIsNetting(arr[12]);
		
		Assert.isTrue(arr[13].length() <= 30, "管理员用户名[" + arr[13] + "]超长");
		Assert.notTrue(this.userInfoDAO.isExist(arr[13]), "用户[" + arr[13] + "]已经存在");
		merchInfoReg.setAdminId(arr[13]);
		
		String riskLevel = arr[14];
		if(RiskLevelType.ALL.get(riskLevel)!=null){
			merchInfoReg.setRiskLevel(riskLevel);
		}
		
		String license = arr[15];
		Assert.isTrue(license.length() <= 60, "营业执照编号[" + license + "]超长");
		merchInfoReg.setMerchCode(license);

		String licenseExpireDate = arr[16];
		Assert.notTrue(licenseExpireDate.trim().length()>0 && !DateUtil.isValidDate(licenseExpireDate), "营业执照有效期["+licenseExpireDate+"]格式不对");
		merchInfoReg.setLicenseExpDate(DateUtil.parseDate(licenseExpireDate));
		
		String organization = arr[17];
		Assert.isTrue(organization.length() <= 32, "组织机构号[" + organization + "]超长");
		merchInfoReg.setOrganization(organization);
		
		String organizationExpireDate = arr[18];
		Assert.notTrue(organizationExpireDate.trim().length()>0 && !DateUtil.isValidDate(organizationExpireDate), "组织机构代码有效期["+organizationExpireDate+"]格式不对");
		merchInfoReg.setOrganizationExpireDate(organizationExpireDate);

		String taxRegCode = arr[19];
		Assert.isTrue(taxRegCode.length() <= 32, "税务登记号[" + taxRegCode + "]超长");
		merchInfoReg.setTaxRegCode(taxRegCode);
		
		String legalPersonName = arr[20];
		Assert.isTrue(legalPersonName.length() <= 64, "法人姓名[" + legalPersonName + "]超长");
		merchInfoReg.setLegalPersonName(legalPersonName);
		
		
		String legalPersonIdcard = arr[21];
		Assert.isTrue(legalPersonIdcard.length() <= 32, "法人身份证[" + legalPersonIdcard + "]超长");
		merchInfoReg.setLegalPersonIdcard(legalPersonIdcard);
		
		String legalPersonIdcardExpDate = arr[22];
		Assert.notTrue(legalPersonIdcardExpDate.trim().length()>0 && !DateUtil.isValidDate(legalPersonIdcardExpDate), "法人身份证有效期["+legalPersonIdcardExpDate+"]格式不对");
		merchInfoReg.setLegalPersonIdcardExpDate(DateUtil.parseDate(legalPersonIdcardExpDate));
		
		String companyBusinessScope = arr[23];
		Assert.isTrue(companyBusinessScope.length() <= 128, "单位经营范围[" + companyBusinessScope + "]超长");
		merchInfoReg.setCompanyBusinessScope(companyBusinessScope);
		
		String remark = arr[24];
		Assert.isTrue(remark.length() <= 128, "备注[" + remark + "]超长");
		merchInfoReg.setRemark(remark);
		
		
		return merchInfoReg;
	}
	
	/**
	 * 在数据库中插入数据，并启动审核流程
	 * @param list
	 * @param merchInfoReg
	 * @return
	 * @throws BizException
	 */
	private String[] insertToMerchInfoReg(List<MerchInfoReg> list, MerchInfoReg merchInfoReg, UserInfo userInfo) throws BizException {
		List<String> regList = new ArrayList<String>();
		// 为得到的list设值
		for (MerchInfoReg reg : list) {
			reg.setManageBranch(merchInfoReg.getManageBranch());
			reg.setOpenFlag(merchInfoReg.getOpenFlag());
			reg.setCurrCode(merchInfoReg.getCurrCode());
			reg.setStatus(MerchState.WAITED.getValue());
			reg.setSetCycle(SetCycleFlag.DAY.getValue());
			reg.setUpdateBy(userInfo.getUserId());
			reg.setUpdateTime(new Date());
			reg.setCardBranch(merchInfoReg.getCardBranch());
			
			reg.setSingleProduct(YesOrNoFlag.NO.getValue());
			reg.setMerch898(merchInfoReg.isMerch898());
			
			this.merchInfoRegDAO.insert(reg);
			startAddMerchFileWorkFlow(reg, userInfo);
			regList.add(reg.getId());
		}
		return regList.toArray(new String[list.size()]);
	}
	
	/**
	 * 构造商圈ID
	 * @Date 2013-2-20下午4:57:27
	 * @return String
	 */
	private String generateMerchGroupId(){
		return "SQ".concat( StringUtils.leftPad(merchGroupDAO.selectMerchGroupSEQ(), 6, '0'));
	}
	
	
	/**
	 * 如果是添加的单机产品的终端的话，则需要在费用记录表中记录一条收费的记录
	 * @param cardBranchCode
	 * @param termId
	 * @throws BizException
	 */
	private void recordCost(String cardBranchCode, String userCode, String...termIdArrays) throws BizException {
		
		CardissuerPlanFee planFee = (CardissuerPlanFee) this.cardissuerPlanFeeDAO.findByPk(cardBranchCode);
		
		Assert.notNull(planFee, "发卡机构[" + cardBranchCode + "]的套餐费用配置不存在");
		
		// 取出套餐第一年的规则
		CardissuerPlanFeeRuleKey key = new CardissuerPlanFeeRuleKey();
		key.setPlanId(planFee.getPlanId());
		key.setBranchCode(cardBranchCode);
		key.setPeriod(1); // 第一年
		
		CardissuerPlanFeeRule rule = (CardissuerPlanFeeRule) this.cardissuerPlanFeeRuleDAO.findByPk(key);
		Assert.notNull(rule, "发卡机构[" + cardBranchCode + "]套餐[" + planFee.getPlanId() + "]第一年的收费子规则没有设定");
		
		BigDecimal amt = BigDecimal.ZERO;
		// 如果是按金额百分比收费参数要除以100
		if (ChargeType.PRECENTED.getValue().equals(planFee.getChargeType())) {
			amt = AmountUtil.multiply(planFee.getChargeAmt(), AmountUtil.divide(rule.getRuleParam(), new BigDecimal(100)));
		} else {
			amt = rule.getRuleParam();
		}
		
		// 在单机产品费用记录表中记录一条数据 
		CostRecord record = null;
		List<CostRecord> recordList = new ArrayList<CostRecord>();

		// 记录赠送卡的数量
		CardissuerFreeNum cardissuerFreeNum = null;
		List<CardissuerFreeNum> freeNumList = new ArrayList<CardissuerFreeNum>();
		for (String termId : termIdArrays) {
			record = new CostRecord();
			
			record.setAmt(amt);
			record.setBranchCode(cardBranchCode);
			record.setType(CostRecordType.PLAN_AMT.getValue());
			record.setStatus(CostRecordState.UNPAY.getValue());
			record.setGenTime(new Date());
			
			record.setInvoiceStatus(Symbol.NO);
			record.setPeriod(1);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.YEAR, 1);
			record.setNextPayTime(calendar.getTime());
			
			record.setTermId(termId);
			recordList.add(record);
			
			cardissuerFreeNum = new CardissuerFreeNum();
			// 赠送卡数量
			cardissuerFreeNum.setPeriod(1);
			cardissuerFreeNum.setIssId(cardBranchCode);
			cardissuerFreeNum.setSendNum(rule.getSendNum());
			cardissuerFreeNum.setUsedNum(0);
			cardissuerFreeNum.setStatus(CardFreeNumState.UN_SEND.getValue());
			cardissuerFreeNum.setUpdateBy(userCode);
			cardissuerFreeNum.setUpdateTime(new Date());
			
			cardissuerFreeNum.setTermId(termId);
			freeNumList.add(cardissuerFreeNum);
		}
		this.costRecordDAO.insertBatch(recordList);
		this.cardissuerFreeNumDAO.insertBatch(freeNumList);
	}
	
	/**
	 * 根据文件内容，构建 终端对象集合
	 */
	private List<Pair<Terminal, Integer>> buildTermsFromFile(File file) throws BizException {
		List<String> lines = null;
		try {
			lines = IOUtil.readLines(file, DEFAULT_ENCODING);
			Assert.isTrue(lines.size() >= 2 && lines.size() <= 101, "批量终端文件明细行最多100行！");
		} catch (IOException e) {
			logger.warn(e, e);
			throw new BizException("读取批量终端文件失败！");
		}

		List<Pair<Terminal, Integer>> termList = new ArrayList<Pair<Terminal, Integer>>(lines.size() - 1);
		int termTotalCnt = 0;// 新增终端总数（计算）
		for (int i = 1; i < lines.size(); i++) {
			String errMsgPrefix = String.format("明细第%s行错误：", i);
			String detailLine = (String) lines.get(i);
			Assert.isTrue(!StringUtils.isBlank(detailLine), errMsgPrefix + "导入文件不能有空行！");

			String[] lineArr = detailLine.split(",", -1);
			Assert.isTrue(!ArrayUtils.isEmpty(lineArr), errMsgPrefix + "解析出的数组不能为空");
			Assert.isTrue(lineArr.length == 8, errMsgPrefix + "字段个数必须是8个");

			Integer termCnt = Integer.valueOf(lineArr[7].trim());
			termTotalCnt = termTotalCnt + termCnt;
			Assert.isTrue(termTotalCnt <= 1000, "单次批量新增终端的总数量最多为1000个！");

			Terminal term = new Terminal();
			term.setMerchId(lineArr[0].trim());
			term.setManageBranch(lineArr[1].trim());
			term.setMaintenance(lineArr[2].trim());
			term.setMerchAddress(lineArr[3].trim());
			term.setPosContact(lineArr[4].trim());
			term.setPosContactPhone(lineArr[5].trim());
			term.setEntryMode(lineArr[6].trim());

			Assert.notBlank(term.getMerchId(), errMsgPrefix + "商户编号不能为空");
			Assert.isTrue(this.merchInfoDAO.isExist(term.getMerchId()), errMsgPrefix + "商户不存在["
					+ term.getMerchId() + "]");

			Assert.notTrue(StringUtils.isNotEmpty(term.getManageBranch())
					&& !this.branchInfoDAO.isExist(term.getManageBranch()), errMsgPrefix + "出机方不存在["
					+ term.getManageBranch() + "]");

			Assert.notBlank(term.getMaintenance(), errMsgPrefix + "维护方不能为空");
			Assert.notTrue(StringUtils.isNotEmpty(term.getMaintenance())
					&& !this.branchInfoDAO.isExist(term.getMaintenance()), errMsgPrefix + "维护方不存在["
					+ term.getMaintenance() + "]");

			Assert.isTrue(Arrays.asList("0", "1").contains(term.getEntryMode()), errMsgPrefix + "输入方式必须是0或1");

			termList.add(new MutablePair<Terminal, Integer>(term, termCnt));
		}
		return termList;
	}

}
