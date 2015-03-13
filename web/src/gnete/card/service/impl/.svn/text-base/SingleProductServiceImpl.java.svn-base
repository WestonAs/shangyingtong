package gnete.card.service.impl;

import flink.ftp.CommunicationException;
import flink.ftp.IFtpCallBackProcess;
import flink.ftp.IFtpTransferCallback;
import flink.ftp.impl.CommonUploadCallBackImpl;
import flink.ftp.impl.FtpCallBackProcessImpl;
import flink.util.AmountUtil;
import flink.util.DateUtil;
import flink.util.IOUtil;
import flink.util.Paginater;
import flink.util.StringUtil;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardBinDAO;
import gnete.card.dao.CardBinRegDAO;
import gnete.card.dao.CardExampleDefDAO;
import gnete.card.dao.CardNoAssignDAO;
import gnete.card.dao.CardPrevConfigDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CardSubClassTempDAO;
import gnete.card.dao.CardToMakeCardDAO;
import gnete.card.dao.CardissuerPlanDAO;
import gnete.card.dao.CardissuerPlanFeeDAO;
import gnete.card.dao.CardissuerPlanFeeRuleDAO;
import gnete.card.dao.CostRecordDAO;
import gnete.card.dao.InsServiceAuthorityDAO;
import gnete.card.dao.MakeCardAppDAO;
import gnete.card.dao.MakeCardRegDAO;
import gnete.card.dao.MembClassDefDAO;
import gnete.card.dao.MembClassDiscntDAO;
import gnete.card.dao.MembClassTempDAO;
import gnete.card.dao.PlanCardExampleDAO;
import gnete.card.dao.PlanDefDAO;
import gnete.card.dao.PlanPrivilegeDAO;
import gnete.card.dao.PlanSubRuleDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.dao.PointClassTempDAO;
import gnete.card.dao.PromtDefDAO;
import gnete.card.dao.PromtRuleDefDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardBin;
import gnete.card.entity.CardBinReg;
import gnete.card.entity.CardExampleDef;
import gnete.card.entity.CardNoAssign;
import gnete.card.entity.CardPrevConfig;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CardSubClassTemp;
import gnete.card.entity.CardToMakeCard;
import gnete.card.entity.CardissuerPlan;
import gnete.card.entity.CardissuerPlanFee;
import gnete.card.entity.CardissuerPlanFeeRule;
import gnete.card.entity.CostRecord;
import gnete.card.entity.InsServiceAuthority;
import gnete.card.entity.MakeCardApp;
import gnete.card.entity.MakeCardReg;
import gnete.card.entity.MembClassDef;
import gnete.card.entity.MembClassDiscnt;
import gnete.card.entity.MembClassTemp;
import gnete.card.entity.PlanCardExample;
import gnete.card.entity.PlanDef;
import gnete.card.entity.PlanPrivilege;
import gnete.card.entity.PlanSubRule;
import gnete.card.entity.PlanSubRuleKey;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.PointClassTemp;
import gnete.card.entity.PromtDef;
import gnete.card.entity.PromtRuleDef;
import gnete.card.entity.UserInfo;
import gnete.card.entity.flag.BirthdayFlag;
import gnete.card.entity.flag.CardFlag;
import gnete.card.entity.flag.MakeFlag;
import gnete.card.entity.flag.OKFlag;
import gnete.card.entity.flag.TrueOrFalseFlag;
import gnete.card.entity.state.CardBinRegState;
import gnete.card.entity.state.CardBinState;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.state.CostRecordState;
import gnete.card.entity.state.MakeCardAppState;
import gnete.card.entity.state.MakeCardRegState;
import gnete.card.entity.state.MemberCertifyState;
import gnete.card.entity.state.PromotionsRuleState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AmtType;
import gnete.card.entity.type.ChargeType;
import gnete.card.entity.type.CostRecordType;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.PayType;
import gnete.card.entity.type.PromotionsRuleType;
import gnete.card.entity.type.PromtType;
import gnete.card.entity.type.TransType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.SingleProductService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.util.CardUtil;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("singleProductService")
public class SingleProductServiceImpl implements SingleProductService {
	
	@Autowired
	private PlanDefDAO planDefDAO; //套餐定义表
	@Autowired
	private CardExampleDefDAO cardExampleDefDAO; //卡样定义表
	@Autowired
	private CardissuerPlanDAO cardissuerPlanDAO; //发卡机构套餐关系表
	@Autowired
	private PlanCardExampleDAO planCardExampleDAO; // 套餐卡样关联表
	@Autowired
	private PlanPrivilegeDAO planPrivilegeDAO; //套餐业务权限表
	@Autowired
	private PlanSubRuleDAO planSubRuleDAO; // 套餐收费子规则表
	@Autowired
	private CostRecordDAO costRecordDAO; // 单机产品费用记录表
	@Autowired
	private CardSubClassTempDAO cardSubClassTempDAO; //卡类型模板表
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO; //卡类型表
	@Autowired
	private PointClassTempDAO pointClassTempDAO; // 积分类型模板表
	@Autowired
	private PointClassDefDAO pointClassDefDAO; // 积分子类型表
	@Autowired
	private MembClassTempDAO membClassTempDAO; // 会员类型模板表
	@Autowired
	private MembClassDefDAO membClassDefDAO; // 会员子类型表
	@Autowired
	private MembClassDiscntDAO membClassDiscntDAO; // 会员类型折扣表
	@Autowired
	private PromtDefDAO promtDefDAO; // 促销活动定义表 
	@Autowired
	private PromtRuleDefDAO promtRuleDefDAO; // 促销活动规则表
	@Autowired
	private BranchInfoDAO branchInfoDAO; // 机构信息表
	@Autowired
	private CardBinDAO cardBinDAO; // 卡BIN管理表
	@Autowired
	private CardBinRegDAO cardBinRegDAO; // 卡BIN登记簿
	@Autowired
	private CardToMakeCardDAO cardToMakeCardDAO; // 发卡机构制卡厂商关系表
	@Autowired
	private MakeCardRegDAO makeCardRegDAO; // 制卡登记簿
	@Autowired
	private MakeCardAppDAO makeCardAppDAO; // 制卡登记表
	@Autowired
	private CardNoAssignDAO cardNoAssignDAO; // 发卡机构卡号分配表
	@Autowired
	private InsServiceAuthorityDAO insServiceAuthorityDAO; // 业务权限控制
	@Autowired
	private CardPrevConfigDAO cardPrevConfigDAO; // 发卡机构卡前三位配置
	@Autowired
	private CardissuerPlanFeeDAO cardissuerPlanFeeDAO; //单机产品发卡机构套餐费用表
	@Autowired
	private CardissuerPlanFeeRuleDAO cardissuerPlanFeeRuleDAO; // 单机产品发卡机构费用规则表
	
	private static final Logger logger = Logger.getLogger(SingleProductServiceImpl.class);
	
	public Paginater findStylePage(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.cardExampleDefDAO.findPage(params, pageNumber, pageSize);
	}
	
	public Paginater findStyleSelectPage(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.cardExampleDefDAO.findSelectPage(params, pageNumber, pageSize);
	}

	public void addStyle(File file, String fileName,
			CardExampleDef cardExample, String planId, UserInfo user) throws BizException {
		Assert.notNull(cardExample, "要添加的卡样对象不能为空");
		Assert.notNull(user, "当前登录的用户对象不能为空");
		Assert.notNull(file, "卡样文件不能为空");
		
		String exampleName = StringUtils.trim(cardExample.getCardExampleName());
		Assert.notEmpty(exampleName, "卡样名称不能为空");
		Assert.notTrue(cardExampleDefDAO.isExsitStyleName(exampleName, user.getBranchNo()), "该卡样名称已经存在于当前登录的运营机构，请更换！");
		cardExample.setCardExampleName(exampleName);
		
		// 上传卡样文件
		this.uploadCardStyleFile(file, fileName);
		
		// 在数据库中插入记录 
		cardExample.setCardExamplePath(fileName);
		cardExample.setStatus(CommonState.NORMAL.getValue());
		cardExample.setBranchCode(user.getBranchNo());
		cardExample.setUpdateBy(user.getUserId());
		cardExample.setUpdateTime(new Date());
		
		this.cardExampleDefDAO.insert(cardExample);
		
		// 如果套餐编号不为空的话，则在套餐卡样关联表里插入记录
		String[] planIds = StringUtils.split(planId, ",");
		if (!ArrayUtils.isEmpty(planIds)) {
			for (int i = 0; i < planIds.length; i++) {
				PlanCardExample example = new PlanCardExample();
				
				example.setCardExampleId(cardExample.getCardExampleId());
				example.setPlanId(planIds[i]);
				example.setStatus(CommonState.NORMAL.getValue());
				example.setUpdateBy(user.getUserId());
				example.setUpdateTime(new Date());
				
				this.planCardExampleDAO.insert(example);
			}
		}
	}

	/**
	 * 上传卡样文件
	 * 
	 * @param uploadFile 上传文件名
	 * @return
	 * @throws BizException
	 */
	private void uploadCardStyleFile(File uploadFile, String fileName) throws BizException {
		SysparamCache mgr = SysparamCache.getInstance();
		
		String ftpServer = SysparamCache.getInstance().getFtpServerIP();
		String user = mgr.getFtpServerUser();
		String pwd = mgr.getFtpServerPwd();
		String ftpPath = mgr.getCardStyleFtpSavePath();
		String webSavePath = mgr.getCardStyleLocalWebSavePath();

		File locFile = null;
		try {
			// 先将卡样文件传到web服务器
			IOUtil.uploadFile(uploadFile, fileName, webSavePath);
			logger.debug("uploadFile:" + uploadFile.getName() + "===" + uploadFile.length());
			
			locFile = IOUtil.getFile(webSavePath, fileName, true);
			logger.debug("localFile:" + locFile.getName() + "===" + locFile.length());
		} catch (IOException ex) {
			logger.warn(ex.getMessage());
			throw new BizException(ex.getMessage());
		}
		
		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);
		
		// 构造面向uploadFile的回调处理类
		IFtpTransferCallback uploadCallBack = new CommonUploadCallBackImpl(ftpPath, locFile);

		// 处理文件上传
		try {
			ftpCallBackTemplate.processFtpCallBack(uploadCallBack);
		} catch (CommunicationException e) {
			String msg = "FTP上传时发生异常：" + e.getMessage();
			logger.warn(msg);
			throw new BizException(msg);
		}
	}
	
	public List<PlanDef> findPlanList(Map<String, Object> params) {
		return this.planDefDAO.findList(params);
	}

	public Paginater findModelPage(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.planDefDAO.findPage(params, pageNumber, pageSize);
	}
	
	public List<CardExampleDef> findSytleList(Map<String, Object> params) {
		return this.cardExampleDefDAO.findList(params);
	}
	
	public void addModel(PlanDef planDef, String cardStyleIds,
			String[] serviceIds, List<PlanSubRule> subRuleList, UserInfo user)
			throws BizException {
		Assert.notNull(planDef, "要添加的套餐定义对象不能为空");
		Assert.notTrue(ArrayUtils.isEmpty(serviceIds), "套餐业务权限不能为空");
		Assert.notTrue(CollectionUtils.isEmpty(subRuleList), "套餐收费子规则不能为空");
		Assert.notNull(user, "登录用户信息不能为空");
		
		planDef.setPlanName(StringUtils.trim(planDef.getPlanName()));
		Assert.notEmpty(planDef.getPlanName(), "套餐名称不能为空");
		
		Assert.notTrue(this.planDefDAO.isExsitModelName(planDef.getPlanName(), user.getBranchNo()), "该套餐名称已经存在于当前登录的运营机构");
		
		// 添加套餐定义表的数据
		planDef.setBranchCode(user.getBranchNo());
		planDef.setStatus(CommonState.NORMAL.getValue());
		planDef.setUpdateBy(user.getUserId());
		planDef.setUpdateTime(new Date());
		
		this.planDefDAO.insert(planDef);
		
		// 添加套餐业务权限
		for (int i = 0; i < serviceIds.length; i++) {
			PlanPrivilege privilege = new PlanPrivilege();
			
			privilege.setPlanId(planDef.getPlanId());
			privilege.setServiceId(serviceIds[i]);
			privilege.setStatus(CommonState.NORMAL.getValue());
			privilege.setUpdateBy(user.getUserId());
			privilege.setUpdateTime(new Date());
			
			this.planPrivilegeDAO.insert(privilege);
		}
		
		// 添加套餐收费子规则表
		for (PlanSubRule rule : subRuleList) {
			rule.setPlanId(planDef.getPlanId());
			
			this.planSubRuleDAO.insert(rule);
		}
		
		// 在套餐卡样关联表中插入数据
		String[] styleIds = StringUtils.split(cardStyleIds, ",");
		if (!ArrayUtils.isEmpty(styleIds)) {
			for (int i = 0; i < styleIds.length; i++) {
				PlanCardExample example = new PlanCardExample();
				
				example.setPlanId(planDef.getPlanId());
				example.setCardExampleId(styleIds[i]);
				example.setStatus(CommonState.NORMAL.getValue());
				example.setUpdateBy(user.getUserId());
				example.setUpdateTime(new Date());
				
				this.planCardExampleDAO.insert(example);
			}
		}
	}
	
	public Paginater findCardPage(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.cardissuerPlanDAO.findPage(params, pageNumber, pageSize);
	}
	
	public void addCardExamplePlan(CardissuerPlan cardissuerPlan, String[] memLevels, String[] discnts, 
			String pointExchangeRate, String makeBranch, UserInfo user) throws BizException {
		Assert.notNull(cardissuerPlan, "发卡机构套餐关系对象不能为空");
		Assert.notNull(user, "登录用户的信息不能为空");
		Assert.notEmpty(cardissuerPlan.getBrancheCode(), "发卡机构编号不能为空");
//		Assert.notEmpty(cardissuerPlan.getCardSubclass(), "卡子类型号不能为空");
		Assert.notEmpty(cardissuerPlan.getDefaultCardExample(), "默认卡样编号不能为空");
		Assert.notEmpty(cardissuerPlan.getPlanId(), "套餐编号不能为空");
		Assert.notEmpty(cardissuerPlan.getBrancheCode(), "发卡机构号不能为空");
		Assert.notEmpty(makeBranch, "制卡厂商编号不能为空");
		
		Assert.notTrue(this.cardissuerPlanDAO.isExist(cardissuerPlan.getBrancheCode()), "发卡机构[" 
				+ cardissuerPlan.getBrancheCode() + "]已经配置，不能重复配置!");
		
		// 1. 根据套餐里卡类型模板中的积分类型模板、会员类型模板分别为该发卡机构生成积分类型和会员类型。
		// 2. 同时根据积分类型和会员类型为该发卡机构生成一个卡子类型。若有会员类型产生，则为每个会员级别设定一个优惠折扣
		Object[] objects = this.generateCardSubClass(cardissuerPlan, memLevels, discnts, pointExchangeRate, user);
		
		cardissuerPlan = (CardissuerPlan) objects[0];
		List<MembClassDiscnt> discntList = (List<MembClassDiscnt>) objects[1];
		CardSubClassDef subClassDef = (CardSubClassDef) objects[2];
		PlanDef planDef = (PlanDef) objects[3];
		
		// 3. 在发卡机构套餐关系表中插入数据
		this.insertCardissuerPlan(cardissuerPlan, user);
		
		// 4. 在单机产品费用记录表中记录一条数据
//		this.recordCost(cardissuerPlan, planDef);
		// 4. 在单机产品发卡机构套餐费用表和单机产品套餐费用子规则表中插入费用规则数据
		this.insertPlansFee(cardissuerPlan, planDef, user);
		
		// 5. 分配权限
		this.assignPrivilege(cardissuerPlan, planDef, user);
		
		// 6. 插入活动表
		this.insertPromtDef(cardissuerPlan, discntList, subClassDef, user);
		
		// 7. 添加发卡机构与制卡厂商关系
		this.addMakeBranch(cardissuerPlan.getBrancheCode(), makeBranch, user);
	}

	public Paginater findChargePage(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.costRecordDAO.findPage(params, pageNumber, pageSize);
	}
	
	public void costCharge(CostRecord costRecord, UserInfo user)
			throws BizException {
		Assert.notNull(costRecord, "单机产品费用记录对象不能为空");
		Assert.notNull(user, "登录用户的信息不能为空");
		Assert.notNull(costRecord.getPaidAmt(), "缴费金额不能为空");
		Assert.notEmpty(costRecord.getType(), "费用类型不能为空");
		Assert.notEmpty(costRecord.getBranchCode(), "发卡机构编号为空");
		
		costRecord.setPayer(user.getUserId());
		costRecord.setPayTime(new Date());
		costRecord.setPayType(PayType.WEB.getValue());
		costRecord.setInvoiceStatus(Symbol.NO);
		if (AmountUtil.et(costRecord.getAmt(), costRecord.getPaidAmt())) {
			costRecord.setStatus(CostRecordState.PAYED.getValue());
		}
		
		// 如果是制卡费用缴费的话。还要发制卡申请。
		if (CostRecordType.MAKE_AMT.getValue().equals(costRecord.getType())) {
			Assert.notEmpty(costRecord.getCardBin(), "卡BIN不能为空");
			Assert.notEmpty(costRecord.getCardSubClass(), "卡类型不能为空");
			Assert.notNull(costRecord.getCardNum(), "制卡数量不能为空");
			Assert.notEmpty(costRecord.getCardExampleId(), "卡样编号不能为空");
			
			Assert.isTrue(costRecord.getCardNum().longValue() - 1 >= 0, "制卡数量必须大于0");
			
			CardSubClassDef cardSubClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(costRecord.getCardSubClass());
			Assert.notNull(cardSubClass, "卡类型[" + costRecord.getCardSubClass() + "]不存在");
			
			// 1. 先查makeCardReg表中的指定卡样名称的记录是否存在，不存在的话则插入一条记录
			MakeCardReg makeCardReg = this.addMakeCardReg(costRecord, user);
			
			// 2. 在制卡申请表中写入一条制卡申请
			String makeId = this.addMakeCardApp(makeCardReg, costRecord.getCardNum(), cardSubClass, user);
			
			costRecord.setMakeId(makeId);
		}

		this.costRecordDAO.update(costRecord);
	}
	
	public void singleProductCardInvoice(String ids) throws BizException {
		String[] idsArray = StringUtils.split(ids, ",");
		Assert.notEmpty(idsArray, "请至少选择一条要开发票的缴费记录");
		
		List<CostRecord> recordList = new ArrayList<CostRecord>();
		CostRecord costRecord = null;
		for (String id : idsArray) {
			 costRecord = (CostRecord) this.costRecordDAO.findByPk(StringUtils.trim(id));
			
			Assert.notNull(costRecord, "ID为[" + id + "]的缴费记录不存在");
			Assert.equals(costRecord.getStatus(), CostRecordState.PAYED.getValue(), "ID为[" + id + "]的缴费记录不是“已付款”状态");
			
			costRecord.setInvoiceStatus(Symbol.YES);
			costRecord.setInvoiceTime(new Date());
			
			recordList.add(costRecord);
		}
		this.costRecordDAO.updateBatch(recordList);
		
	}
	
	public Paginater findCardPlanFeePage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.cardissuerPlanFeeDAO.findPage(params, pageNumber, pageSize);
	}
	
	//================================================ 单机产品发卡机构套餐费用维护 =======================================================
	public void modifyCardPlanFee(CardissuerPlanFee cardissuerPlanFee,
			List<CardissuerPlanFeeRule> feeRuleList, UserInfo user) throws BizException {
		Assert.notNull(cardissuerPlanFee, "发卡机构套餐费用对象不能为空");
		Assert.notEmpty(feeRuleList, "发卡机构费用规则不能为空");
		Assert.notNull(user, "登录用户信息不能为空");
		Assert.notEmpty(cardissuerPlanFee.getIssId(), "发卡机构编号不能为空");
		Assert.notEmpty(cardissuerPlanFee.getPlanId(), "套餐编号不能为空");
		Assert.notEmpty(cardissuerPlanFee.getChargeType(), "收费标准不能为空");
		
		cardissuerPlanFee.setUpdateBy(user.getUserId());
		cardissuerPlanFee.setUpdateTime(new Date());
		cardissuerPlanFee.setStatus(CommonState.NORMAL.getValue());
		this.cardissuerPlanFeeDAO.update(cardissuerPlanFee);
		
		this.cardissuerPlanFeeRuleDAO.deleteByBranch(cardissuerPlanFee.getIssId());
		
		List<CardissuerPlanFeeRule> feeRuleNewList = new ArrayList<CardissuerPlanFeeRule>();
		for (CardissuerPlanFeeRule rule : feeRuleList) {
			rule.setBranchCode(cardissuerPlanFee.getIssId());
			rule.setPlanId(cardissuerPlanFee.getPlanId());
			rule.setUpdateBy(user.getUserId());
			rule.setUpdateTime(new Date());
			
			feeRuleNewList.add(rule);
		}
		this.cardissuerPlanFeeRuleDAO.insertBatch(feeRuleNewList);
	}
	
	//================================================ 单机产品缴费  ===================================================================
	/**
	 * 根据缴费记录表的数据取得制卡登记对象
	 * 
	 * @param costRecord
	 * @param user
	 * @return
	 * @throws BizException
	 */
	private MakeCardReg addMakeCardReg(CostRecord costRecord, UserInfo user) throws BizException {
		CardExampleDef cardExample = (CardExampleDef) this.cardExampleDefDAO.findByPk(costRecord.getCardExampleId());
		Assert.notNull(cardExample, "卡样[" + costRecord.getCardExampleId() + "]不存在"); //标准卡样
		String cardExampleName = costRecord.getCardExampleId() + cardExample.getCardExampleName();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardExampleName", cardExampleName);
		params.put("picStatus", MakeCardRegState.EFFECTIVE.getValue());
		params.put("branchCode", costRecord.getBranchCode());
		params.put("cardSubtype", costRecord.getCardSubClass());
		
		List<MakeCardReg> styleList = this.makeCardRegDAO.findList(params);
		MakeCardReg makeCardReg = new MakeCardReg();
		if (CollectionUtils.isEmpty(styleList)) {
			List<BranchInfo> makeList = this.branchInfoDAO.findMakeBranchByCardCode(costRecord.getBranchCode());
			Assert.notEmpty(makeList, "发卡机构[" + costRecord.getBranchCode() + "]没有指定制卡厂商");
			
			makeCardReg.setBranchCode(costRecord.getBranchCode());
			makeCardReg.setCardSubtype(costRecord.getCardSubClass());
			makeCardReg.setMakeName(cardExampleName);
			makeCardReg.setMakeUser(makeList.get(0).getBranchCode());
			makeCardReg.setInitUrl(cardExample.getCardExamplePath());
			makeCardReg.setFinUrl(cardExample.getCardExamplePath());
			makeCardReg.setAppUpload(new Date());
			makeCardReg.setOkFlag(OKFlag.TRUE.getValue());
			makeCardReg.setOkDate(DateUtil.formatDate("yyyyMMdd"));
			makeCardReg.setSmsFlag(TrueOrFalseFlag.FALSE.getValue());
			makeCardReg.setPicStatus(MakeCardRegState.EFFECTIVE.getValue());
			makeCardReg.setUpdateBy(user.getUserId());
			makeCardReg.setUpdateTime(new Date());
			
			this.makeCardRegDAO.insert(makeCardReg);
		} else {
			makeCardReg = styleList.get(0);
		}
		
		return makeCardReg;
	}
	
	/**
	 * 根据制卡登记对象，制卡数量和登录用户信息，写制卡申请表，同时向后台发命令
	 * @param makeCardReg
	 * @param cardNum
	 * @param user
	 * @return
	 * @throws BizException
	 */
	private String addMakeCardApp(MakeCardReg makeCardReg, BigDecimal cardNum, 
			CardSubClassDef cardSubClass, UserInfo user) throws BizException {
		
		Assert.notEmpty(cardSubClass.getBinNo(), "卡类型[" + makeCardReg.getCardSubtype() + "]的卡BIN为空");
		Assert.notEmpty(cardSubClass.getCardClass(), "卡类型[" + makeCardReg.getCardSubtype() + "]的卡种为空");
		
		BranchInfo cardBranch = this.branchInfoDAO.findBranchInfo(makeCardReg.getBranchCode());
		Assert.notNull(cardBranch, "发卡机构[" + makeCardReg.getBranchCode() + "]不存在");
		
		String strCardNo = this.getStrCardNo(makeCardReg, cardSubClass);
		String strCardNoPrex = StringUtils.substring(strCardNo, 11, strCardNo.length() - 1);
		
		int strCardNoInt = NumberUtils.toInt(strCardNoPrex);
		CardNoAssign cardNoAssign = new CardNoAssign();
		
		cardNoAssign.setBranchCode(makeCardReg.getBranchCode());
		cardNoAssign.setBinNo(cardSubClass.getBinNo());
		cardNoAssign.setStrCardNo(strCardNoInt);
		cardNoAssign.setEndCardNo(strCardNoInt + cardNum.intValue() - 1);
		cardNoAssign.setUseCardNo(cardNoAssign.getEndCardNo());
		cardNoAssign.setUpdateBy(user.getUserId());
		cardNoAssign.setUpdateTime(new Date());
		
		this.cardNoAssignDAO.insert(cardNoAssign);
		
		MakeCardApp makeCardApp = new MakeCardApp();
		
		makeCardApp.setMakeId(makeCardReg.getMakeId());
		makeCardApp.setBranchCode(makeCardReg.getBranchCode());
		makeCardApp.setMakeFlag(MakeFlag.NORMAL_CARD.getValue());
		makeCardApp.setCardSubtype(cardSubClass.getCardSubclass());
		makeCardApp.setBinNo(cardSubClass.getBinNo());
		makeCardApp.setStrNo(strCardNo);
		makeCardApp.setCardNum(cardNum);
		makeCardApp.setStatus(MakeCardAppState.CHECK_PASSED.getValue());
		makeCardApp.setAppUser(user.getUserId());
		makeCardApp.setUpdateBy(user.getUserId());
		makeCardApp.setUpdateTime(new Date());
		makeCardApp.setDeliveryUnit(cardBranch.getBranchName()); // 收货单位填发卡机构名称
		makeCardApp.setDeliveryAdd(cardBranch.getAddress()); //交货地点填发卡机构地址
		makeCardApp.setRecvContact(cardBranch.getPhone()); //收货人员联系方式填发卡机构电话
		
		this.makeCardAppDAO.insert(makeCardApp);
		
		MsgSender.sendMsg(MsgType.CREATE_CARD_FILE, makeCardApp.getId(), user.getUserId());
	
		return Long.toString(makeCardApp.getId());
	}
	
	/**
	 * 根据卡类型和制卡登记对象，取得起始卡号
	 * @param makeCardReg
	 * @param cardSubClass
	 * @return
	 */
	private String getStrCardNo(MakeCardReg makeCardReg, CardSubClassDef cardSubClass) {
//		String sysNo = ParaMgr.getInstance().getSysNo();
		String sysNo = this.getSysNo(makeCardReg.getBranchCode());
		// 卡号前11位为： 系统号 + 卡BIN号 + 卡类型号
		String strNoPrix = sysNo + cardSubClass.getBinNo() + cardSubClass.getCardClass();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", makeCardReg.getBranchCode());
		params.put("binNo", cardSubClass.getBinNo());
		Long strNoLong = this.cardNoAssignDAO.findUseCardNo(params);
		if (strNoLong == null) {
			strNoLong = 0L;
		}
		String str = Long.toString(strNoLong + 1L);
		
		String cardNoPre = strNoPrix + StringUtils.leftPad(str, 7, "0");
		StringBuffer sb = new StringBuffer(128);
		
		sb.append(cardNoPre).append(CardUtil.luhnMod10(cardNoPre));
		
		return sb.toString();
	}
	
	/**
	 * 系统号
	 */
	private String getSysNo(String branchCode) {
		String sysNo = "";
		
		CardPrevConfig config = (CardPrevConfig) this.cardPrevConfigDAO.findByPk(branchCode);
		if (config != null) {
			sysNo = config.getCardPrev();
		} else {
			sysNo = SysparamCache.getInstance().getSysNo();
		}
		return sysNo;
	} 
	
	//================================================  添加发卡机构套餐关系  ==================================================
	/**
	 *  1. 根据套餐里卡类型模板中的积分类型模板、会员类型模板分别为该发卡机构生成积分类型和会员类型。
	 *  2. 同时根据积分类型和会员类型为该发卡机构生成一个卡子类型。若有会员类型产生，则为每个会员级别设定一个优惠折扣
	 * @throws BizException
	 */
	private Object[] generateCardSubClass(CardissuerPlan cardissuerPlan, String[] memLevels,
			String[] discnts, String pointExchangeRate, UserInfo user) throws BizException {
		PlanDef planDef = (PlanDef) this.planDefDAO.findByPk(cardissuerPlan.getPlanId());
		Assert.notNull(planDef, "套餐[" + cardissuerPlan.getPlanId() + "]不存在");
		
		CardSubClassTemp subClassTemp = (CardSubClassTemp) this.cardSubClassTempDAO.findByPk(planDef.getCardSubclassTemp());
		Assert.notNull(subClassTemp, "卡类型模板[" + planDef.getCardSubclassTemp() + "]不存在");
		
		BranchInfo cardBranch = this.branchInfoDAO.findBranchInfo(cardissuerPlan.getBrancheCode());
		Assert.notNull(cardBranch, "发卡机构[" + cardissuerPlan.getBrancheCode() + "]不存在");
		
		List<MembClassDiscnt> discntList = new ArrayList<MembClassDiscnt>();
		PointClassDef pointClassDef = new PointClassDef();
		// 积分类型不为空的话，则为该发卡机构生成积分类型
		if (StringUtils.isNotEmpty(subClassTemp.getPtClass())) {
			PointClassTemp temp = (PointClassTemp) this.pointClassTempDAO.findByPk(subClassTemp.getPtClass());
			Assert.notNull(temp, "积分类型模板[" + subClassTemp.getPtClass() + "]不存在");
			
			// 类型名称为机构简称 + 模板中的类型名称
			pointClassDef.setClassName(cardBranch.getBranchAbbname() + temp.getClassName());
			pointClassDef.setCardIssuer(cardissuerPlan.getBrancheCode());
			pointClassDef.setJinstType(IssType.CARD.getValue());
			pointClassDef.setJinstId(cardissuerPlan.getBrancheCode());
			pointClassDef.setJinstName(cardBranch.getBranchName());
			
			pointClassDef.setPtDiscntRate(NumberUtils.createBigDecimal(pointExchangeRate));
			pointClassDef.setAmtType(temp.getAmtType());
			pointClassDef.setPtUsage(temp.getPtUsage());
			pointClassDef.setPtInstmMthd(temp.getPtInstmMthd());
			pointClassDef.setInstmPeriod(temp.getInstmPeriod());
			pointClassDef.setPtValidityCyc(temp.getPtValidityCyc());
			pointClassDef.setPtLatestCyc(temp.getPtLatestCyc());
			pointClassDef.setPtDeprecRate(temp.getPtDeprecRate());
			pointClassDef.setPtClassParam1(temp.getPtClassParam1());
			pointClassDef.setReserved1(temp.getReserved1());
			pointClassDef.setReserved2(temp.getReserved2());
			pointClassDef.setReserved3(temp.getReserved3());
			pointClassDef.setReserved4(temp.getReserved4());
			pointClassDef.setReserved5(temp.getReserved5());
			pointClassDef.setClassShortName(temp.getClassShortName());
			pointClassDef.setPtRef(temp.getPtRef());
			pointClassDef.setPtUseLimit(temp.getPtUseLimit());
			
			this.pointClassDefDAO.insert(pointClassDef);
		}
		
		MembClassDef membClassDef = new MembClassDef();
		// 会员类型不为空的话，则为该发卡机构生成会员类型
		if (StringUtils.isNotEmpty(subClassTemp.getMembClass())) {
			MembClassTemp temp = (MembClassTemp) this.membClassTempDAO.findByPk(subClassTemp.getMembClass());
			Assert.notNull(temp, "会员类型模板[" + subClassTemp.getMembClass() + "]不存在");
			Assert.notEmpty(memLevels, "会员级别不能为空");
			Assert.notEmpty(discnts, "输入的折扣率不能为空");
			
			// 类型名称为机构简称 + 模板中的类型名称
			membClassDef.setClassName(cardBranch.getBranchAbbname() + temp.getClassName());
			membClassDef.setCardIssuer(cardissuerPlan.getBrancheCode());
			membClassDef.setJinstId(cardissuerPlan.getBrancheCode());
			membClassDef.setJinstType(IssType.CARD.getValue());
			
			membClassDef.setMembLevel(temp.getMembLevel());
			membClassDef.setMembClassName(temp.getMembClassName());
			membClassDef.setMembUpgradeMthd(temp.getMembDegradeMthd());
			membClassDef.setMembUpgradeThrhd(temp.getMembUpgradeThrhd());
			membClassDef.setMembDegradeMthd(temp.getMembDegradeMthd());
			membClassDef.setMembDegradeThrhd(temp.getMembDegradeThrhd());
			membClassDef.setMustExpirDate(temp.getMustExpirDate());
			membClassDef.setStatus(MemberCertifyState.PASSED.getValue());
			membClassDef.setUpdateBy(user.getUserId());
			membClassDef.setUpdateTime(new Date());
			
			this.membClassDefDAO.insert(membClassDef);
//			discnts = (String[]) ArrayUtils.removeElement(discnts, StringUtils.EMPTY);
			
			List<String> list = Arrays.asList(discnts);
			List<String> emptyValue = new ArrayList<String>();
			emptyValue.add(StringUtils.EMPTY);
			list = ListUtils.removeAll(list, emptyValue);
			
//			discnts = (String[]) list.toArray();
			
			Assert.isTrue(ArrayUtils.isSameLength(memLevels, list.toArray()), "请确保为每个会员级别都设置了一个折扣率");
			
			// 为每个级别的会员设定一个折扣
			MembClassDiscnt discnt = null;
			for (int j = 0; j < ArrayUtils.getLength(memLevels); j++) {
				discnt = new MembClassDiscnt();
				
				discnt.setMembClass(membClassDef.getMembClass());
				discnt.setMemLevel(memLevels[j]);
				discnt.setDiscnt(NumberUtils.createBigDecimal(list.get(j)));
				discnt.setUpdateTy(user.getUserId());
				discnt.setUpdateTime(new Date());
				
				discntList.add(discnt);
			}
			this.membClassDiscntDAO.insertBatch(discntList);
		}
		
		cardissuerPlan.setCardSubclassTemp(planDef.getCardSubclassTemp());
		// 在卡类型表中插入记录
		CardSubClassDef subClassDef = new CardSubClassDef();
		
		String cardSubClassName = cardBranch.getBranchAbbname() + subClassTemp.getCardSubclassName();
		int i = 1;
		while (this.cardSubClassDefDAO.isExsitCardSubClassName(cardSubClassName)) {
			cardSubClassName = cardSubClassName.concat(Integer.toString(i));
			i += 1;
		}
		
		subClassDef.setCardSubclassName(cardSubClassName);
		// 生成卡类型号
		String cardSubClassId = getCardSubClassId();
		subClassDef.setCardSubclass(cardSubClassId);
		
		// 取发卡机构的卡BIN
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("cardIssuer", cardissuerPlan.getBrancheCode());
		params.put("status", CardBinState.NORMAL.getValue());
		List<CardBin> cardBinList = this.cardBinDAO.findCardBin(params);
//		Assert.notEmpty(cardBinList, "发卡机构[" + cardissuerPlan.getBrancheCode() + "]没有卡BIN，请先申请卡BIN");
	
		if (CollectionUtils.isEmpty(cardBinList)) {
			//================== 卡BIN begin =========================================
			String binNo = this.generateCardBin("99", 6);
			CardBinReg cardBinReg = new CardBinReg();
			
			cardBinReg.setBinNo(binNo);
			cardBinReg.setBinName("单机产品卡BIN[" + cardissuerPlan.getBrancheCode() + "]");
			cardBinReg.setCardIssuer(cardissuerPlan.getBrancheCode());
			cardBinReg.setCardType(subClassTemp.getCardClass());
			cardBinReg.setCurrCode("CNY");
			cardBinReg.setStatus(CardBinRegState.PASSED.getValue());
			cardBinReg.setUpdateBy("sys");
			cardBinReg.setUpdateTime(new Date());
			
			this.cardBinRegDAO.insert(cardBinReg);
			
			CardBin cardBin = new CardBin();
			
			cardBin.setBinNo(binNo);
			cardBin.setBinName(cardBinReg.getBinName());
			cardBin.setCardIssuer(cardBinReg.getCardIssuer());
			cardBin.setCardType(cardBinReg.getCardType());
			cardBin.setCurrCode(cardBinReg.getCurrCode());
			cardBin.setStatus(CardBinState.NORMAL.getValue());
			cardBin.setUpdateBy("sys");
			cardBin.setUpdateTime(new Date());
			
			this.cardBinDAO.insert(cardBin);
			//================== 卡BIN end =============================================
			
			subClassDef.setBinNo(binNo);
		} else {
			subClassDef.setBinNo(cardBinList.get(0).getBinNo());
		}
		
		subClassDef.setCardClass(subClassTemp.getCardClass());
		subClassDef.setCardIssuer(cardissuerPlan.getBrancheCode());
		subClassDef.setFaceValue(subClassTemp.getFaceValue());
		subClassDef.setMembLevel(subClassTemp.getMembLevel());
		subClassDef.setPtOpencard(subClassTemp.getPtOpencard());
		subClassDef.setMembClass(membClassDef.getMembClass());
		subClassDef.setPtClass(pointClassDef.getPtClass());
		subClassDef.setCardPrice(subClassTemp.getCardPrice());
		subClassDef.setChkPwd(subClassTemp.getChkPwd());
		subClassDef.setPwdType(subClassTemp.getPwdType());
		subClassDef.setPwd(subClassTemp.getPwd());
		subClassDef.setChkPfcard(subClassTemp.getChkPfcard());
		subClassDef.setAutoCancelFlag(subClassTemp.getAutoCancelFlag());
		subClassDef.setCreditUlimit(subClassTemp.getCreditUlimit());
		subClassDef.setChargeUlimit(subClassTemp.getChargeUlimit());
		subClassDef.setConsmUlimit(subClassTemp.getConsmUlimit());
		subClassDef.setMustExpirDate(subClassTemp.getMustExpirDate());
		subClassDef.setEffPeriod(subClassTemp.getEffPeriod());
		subClassDef.setExtenPeriod(subClassTemp.getExtenPeriod());
		subClassDef.setExtenMaxcnt(subClassTemp.getExtenMaxcnt());
		subClassDef.setExpirMthd(subClassTemp.getExpirMthd());
		subClassDef.setExpirDate(subClassTemp.getExpirDate());
		subClassDef.setStatus(RegisterState.PASSED.getValue());
		subClassDef.setUpdateBy(user.getUserId());
		subClassDef.setUpdateTime(new Date());
		subClassDef.setIcType(CardFlag.CARD.getValue());
		subClassDef.setChangeFaceValue(subClassTemp.getChangeFaceValue());
		subClassDef.setDepositFlag(subClassTemp.getDepositFlag());
		subClassDef.setIsChgPwd(subClassTemp.getIsChgPwd());
		
		
		this.cardSubClassDefDAO.insert(subClassDef);
		cardissuerPlan.setCardSubclass(subClassDef.getCardSubclass());
		
		return new Object[] {cardissuerPlan, discntList, subClassDef, planDef};
	}
	
	/**
	 *  3. 在发卡机构套餐关系表中插入数据
	 * @param cardissuerPlan
	 * @param user
	 * @throws BizException
	 */
	private void insertCardissuerPlan(CardissuerPlan cardissuerPlan, UserInfo user) throws BizException{
		cardissuerPlan.setStatus(CommonState.NORMAL.getValue());
		cardissuerPlan.setUpdateBy(user.getUserId());
		cardissuerPlan.setUpdateTime(new Date());
		cardissuerPlan.setEffDate(DateUtil.formatDate("yyyyMMdd"));
		
		this.cardissuerPlanDAO.insert(cardissuerPlan);
	}
	
	/**
	 * 4. 在单机产品费用记录表中记录一条数据
	 * (发卡机构关联套餐时不计费，添加终端时才计费)
	 * 
	 * @param cardissuerPlan
	 * @return
	 * @throws BizException
	 * 这个方法已经没用使用
	 */
	@SuppressWarnings("unused")
	private void recordCost(CardissuerPlan cardissuerPlan, PlanDef planDef) throws BizException {
		// 计算应收金额。先找出套餐

		// 在单机产品费用记录表中记录一条数据 
		CostRecord record = new CostRecord();
		record.setBranchCode(cardissuerPlan.getBrancheCode());
		record.setType(CostRecordType.PLAN_AMT.getValue());
		record.setStatus(CostRecordState.UNPAY.getValue());
		record.setGenTime(new Date());
		
		// 取出套餐第一年的规则
		PlanSubRuleKey key = new PlanSubRuleKey();
		key.setPlanId(cardissuerPlan.getPlanId());
		key.setPeriod(1);
		
		PlanSubRule rule = (PlanSubRule) this.planSubRuleDAO.findByPk(key);
		Assert.notNull(rule, "套餐[" + cardissuerPlan.getPlanId() + "]第一年的收费子规则没有设定");
		
		BigDecimal amt = BigDecimal.ZERO;
		// 如果是按金额百分比收费参数要除以100
		if (ChargeType.PRECENTED.getValue().equals(planDef.getChargeType())) {
			amt = AmountUtil.multiply(planDef.getChargeAmt(), AmountUtil.divide(rule.getRuleParam(), new BigDecimal(100)));
		} else {
			amt = rule.getRuleParam();
		}
		record.setAmt(amt);
		this.costRecordDAO.insert(record);
	}
	
	/**
	 * 4. 在单机产品发卡机构套餐费用表和单机产品套餐费用子规则表中插入费用规则数据
	 * @throws BizException
	 */
	private void insertPlansFee(CardissuerPlan cardissuerPlan, PlanDef planDef, UserInfo user) throws BizException {
		//在单价产品费用表中插入数据
		CardissuerPlanFee cardissuerPlanFee = new CardissuerPlanFee();
		
		cardissuerPlanFee.setIssId(cardissuerPlan.getBrancheCode());
		cardissuerPlanFee.setPlanId(cardissuerPlan.getPlanId());
		cardissuerPlanFee.setChargeType(planDef.getChargeType());
		cardissuerPlanFee.setChargeAmt(planDef.getChargeAmt());
		cardissuerPlanFee.setDefauleAmt(planDef.getDefauleAmt());
		cardissuerPlanFee.setCustomAmt(planDef.getCustomAmt());
		cardissuerPlanFee.setStatus(CommonState.NORMAL.getValue());
		cardissuerPlanFee.setUpdateBy(user.getUserId());
		cardissuerPlanFee.setUpdateTime(new Date());
		
		this.cardissuerPlanFeeDAO.insert(cardissuerPlanFee);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("planId", planDef.getPlanId());
		List<PlanSubRule> ruleList = this.planSubRuleDAO.findList(params);
		
		List<CardissuerPlanFeeRule> list = new ArrayList<CardissuerPlanFeeRule>();
		
		// 在单机产品发卡机构费用规则表中插入数据
		CardissuerPlanFeeRule planFeeRule = null;
		for (PlanSubRule rule : ruleList) {
			planFeeRule = new CardissuerPlanFeeRule();

			planFeeRule.setBranchCode(cardissuerPlan.getBrancheCode());
			planFeeRule.setPlanId(cardissuerPlan.getPlanId());
			planFeeRule.setUpdateBy(user.getUserId());
			planFeeRule.setUpdateTime(new Date());
			
			planFeeRule.setPeriod(rule.getPeriod());
			planFeeRule.setRuleParam(rule.getRuleParam());
			planFeeRule.setSendNum(rule.getSendNum());
			
			list.add(planFeeRule);
		}
		
		this.cardissuerPlanFeeRuleDAO.insertBatch(list);
		
	}
	
	/**
	 * 5.分配权限
	 * 
	 * @throws BizException
	 */
	private void assignPrivilege(CardissuerPlan cardissuerPlan, PlanDef planDef, 
			UserInfo user) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("planId", planDef.getPlanId());
		params.put("status", CommonState.NORMAL.getValue());
		List<PlanPrivilege> privilegeList = this.planPrivilegeDAO.findList(params);
		Assert.notTrue(CollectionUtils.isEmpty(privilegeList), "套餐[" + cardissuerPlan.getPlanId() + "]的权限未分配");
		
		InsServiceAuthority authority = null;
		for (PlanPrivilege privilege : privilegeList) {
			authority = new InsServiceAuthority();
			
			authority.setInsType(IssType.CARD.getValue());
			authority.setInsId(cardissuerPlan.getBrancheCode()); //发卡机构号
			authority.setServiceId(privilege.getServiceId());
			
			authority.setStatus(CardTypeState.NORMAL.getValue());
			authority.setUpdateBy(user.getUserId());
			authority.setUpdateTime(new Date());
			
			if (this.insServiceAuthorityDAO.isExist(authority)) {
				this.insServiceAuthorityDAO.update(authority);
			} else {
				this.insServiceAuthorityDAO.insert(authority);
			}
		}
	}
	
	/**
	 * 6. 插入活动表
	 * @throws BizException
	 */
	private void insertPromtDef(CardissuerPlan cardissuerPlan, List<MembClassDiscnt> discntList, 
		 	CardSubClassDef subClassDef, UserInfo user) throws BizException {
		StringBuffer sb = new StringBuffer(128);
		if (CollectionUtils.isNotEmpty(discntList)) {
		
			for (MembClassDiscnt discnt : discntList ) {
				// 对每个会员级别的折扣定义一个打折活动
				PromtDef promtDef = new PromtDef();
				sb.append(discnt.getMemLevel()).append(";");
				
				promtDef.setPromtName("单机产品打折活动["+ cardissuerPlan.getBrancheCode() + "]" + discnt.getMemLevel());
				promtDef.setPromtType(PromtType.ACTIVITY.getValue());
				promtDef.setIssType(IssType.CARD.getValue());
				promtDef.setIssId(cardissuerPlan.getBrancheCode());
				promtDef.setPinstType(IssType.CARD.getValue());
				promtDef.setPinstId(cardissuerPlan.getBrancheCode());
				promtDef.setTransType(TransType.NORMAL_CONSUME.getValue());
				promtDef.setCardBinScope(subClassDef.getBinNo());
				promtDef.setEffDate(DateUtil.formatDate("yyyyMMdd"));
				promtDef.setExpirDate("20990101");
				promtDef.setAddScope(TrueOrFalseFlag.FALSE.getValue());
				promtDef.setReserved2(subClassDef.getMembClass());
				promtDef.setReserved3(discnt.getMemLevel());
				promtDef.setStatus(PromotionsRuleState.EFFECT.getValue());
				promtDef.setUpdateBy(user.getUserId());
				promtDef.setUpdateTime(new Date());
				
				this.promtDefDAO.insert(promtDef);
				
				PromtRuleDef ruleDef = new PromtRuleDef();
				
				ruleDef.setPromtId(promtDef.getPromtId());
				ruleDef.setAmtType(AmtType.TRAN.getValue());
				ruleDef.setAmtRef(new BigDecimal("0.1"));
				ruleDef.setPromtRuleType(PromotionsRuleType.DISCOUNT_CARD.getValue());
				ruleDef.setRuleParam3(AmountUtil.divide(discnt.getDiscnt(), new BigDecimal(100)));
				ruleDef.setRuleParam4(AmountUtil.divide(discnt.getDiscnt(), new BigDecimal(100)));
				ruleDef.setBirthdayFlag(BirthdayFlag.ALL_DATE.getValue());
				ruleDef.setRuleStatus(promtDef.getStatus());
				
				this.promtRuleDefDAO.insert(ruleDef);
			}
		}
		
		String membLevels = sb.toString();
		// 如果有积分类型的话，就要送积分的活动
		if (StringUtils.isNotEmpty(subClassDef.getPtClass())) {
			PromtDef promtDef = new PromtDef();
			
			promtDef.setPromtName("单机产品积分活动["+ cardissuerPlan.getBrancheCode() + "]");
			promtDef.setPromtType(PromtType.ACTIVITY.getValue());
			promtDef.setIssType(IssType.CARD.getValue());
			promtDef.setIssId(cardissuerPlan.getBrancheCode());
			promtDef.setPinstType(IssType.CARD.getValue());
			promtDef.setPinstId(cardissuerPlan.getBrancheCode());
			promtDef.setTransType(TransType.NORMAL_CONSUME.getValue());
			promtDef.setCardBinScope(subClassDef.getBinNo());
			promtDef.setEffDate(DateUtil.formatDate("yyyyMMdd"));
			promtDef.setExpirDate("20990101");
			promtDef.setAddScope(TrueOrFalseFlag.FALSE.getValue());
			promtDef.setReserved2(subClassDef.getMembClass());
			promtDef.setReserved3(membLevels);
			promtDef.setStatus(PromotionsRuleState.EFFECT.getValue());
			promtDef.setUpdateBy(user.getUserId());
			promtDef.setUpdateTime(new Date());
			
			this.promtDefDAO.insert(promtDef);
			
			PromtRuleDef ruleDef = new PromtRuleDef();
			
			ruleDef.setPromtId(promtDef.getPromtId());
			ruleDef.setAmtType(AmtType.TRAN.getValue());
			ruleDef.setAmtRef(new BigDecimal("1"));
			ruleDef.setPromtRuleType(PromotionsRuleType.SEND_POINTS_RATE.getValue());
			ruleDef.setRuleParam3(new BigDecimal(1));
			ruleDef.setRuleParam5(subClassDef.getPtClass());
			ruleDef.setBirthdayFlag(BirthdayFlag.ALL_DATE.getValue());
			ruleDef.setRuleStatus(promtDef.getStatus());
			
			this.promtRuleDefDAO.insert(ruleDef);
		}
	}
	
	/**
	 * 7. 添加发卡机构与制卡厂商关系
	 * @param cardIssuer 发卡机构编号
	 * @param makeBranch 制卡厂商编号
	 * @param user 登录用户信息
	 * @throws BizException
	 */
	private void addMakeBranch(String cardIssuer, String makeBranch, 
			UserInfo user) throws BizException {
		CardToMakeCard cardToMakeCard = new CardToMakeCard();
		
		cardToMakeCard.setBranchCode(cardIssuer);
		cardToMakeCard.setMakeCard(makeBranch);
		
		CardToMakeCard oldToMakeCard = (CardToMakeCard) this.cardToMakeCardDAO.findByPk(cardToMakeCard);
		if (oldToMakeCard != null) {
			if (StringUtils.equals(oldToMakeCard.getStatus(), CardTypeState.CANCEL.getValue())) {
				oldToMakeCard.setStatus(CardTypeState.NORMAL.getValue());
				oldToMakeCard.setUpdateBy(user.getUserId());
				oldToMakeCard.setUpdateTime(new Date());
				
				this.cardToMakeCardDAO.update(oldToMakeCard);
			}
		} else {
			cardToMakeCard.setStatus(CardTypeState.NORMAL.getValue());		
			cardToMakeCard.setUpdateTime(new Date());
			cardToMakeCard.setUpdateBy(user.getUserId());
			
			this.cardToMakeCardDAO.insert(cardToMakeCard);
		}
	}
	
	/**
	 * 生成卡类型编号
	 * @return
	 * @throws BizException
	 */
	private String getCardSubClassId() {
		String cardSubClassId = "";
		do {
			cardSubClassId = StringUtil.getRandomString(8);
		} while (this.cardSubClassDefDAO.isExistCardSubClass(cardSubClassId));
		return cardSubClassId;
	}
	
	/**
	 * 生成以99开头，同时卡bin管理表里不存在的卡bin号码
	 * 
	 * @param length
	 * @return
	 */
	private String generateCardBin(String prex, int length) {
		String binNoEnd = "";
		String binNo = "";
		while (true) {
			binNoEnd = StringUtil.getNoString(length - prex.length());
			binNo = prex + binNoEnd;
			// 检查卡bin是否已经存在
			boolean isExist = cardBinDAO.isExist(binNo);
			// 如果不存在则跳出while循环
			if (!isExist) {
				break;
			}
		}
		return binNo;
	}
}
