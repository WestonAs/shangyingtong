package gnete.card.service.impl;

import flink.file.PointChgImporter;
import flink.util.AmountUtil;
import flink.util.DateUtil;
import flink.util.SpringContext;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.BranchProxyDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CardToMerchDAO;
import gnete.card.dao.DepositPointBatRegDAO;
import gnete.card.dao.DepositPointRegDAO;
import gnete.card.dao.PointBalDAO;
import gnete.card.dao.PointChgRegDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.dao.SaleDepositCheckConfigDAO;
import gnete.card.dao.SaleProxyPrivilegeDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.BranchProxyKey;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CardToMerchKey;
import gnete.card.entity.DepositPointBatReg;
import gnete.card.entity.DepositPointReg;
import gnete.card.entity.PointBal;
import gnete.card.entity.PointBalKey;
import gnete.card.entity.PointChgReg;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.SaleDepositCheckConfig;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SellType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.BaseDataService;
import gnete.card.service.CardRiskService;
import gnete.card.service.PointBusService;
import gnete.card.service.UserCertificateRevService;
import gnete.card.service.UserService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.util.CardUtil;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("pointBusService")
public class PointBusServiceImpl implements PointBusService {
	
	@Autowired
	private PointChgRegDAO pointChgRegDAO;
	@Autowired
	private PointBalDAO pointBalDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private DepositPointRegDAO depositPointRegDAO;
	@Autowired
	private DepositPointBatRegDAO depositPointBatRegDAO;
	@Autowired
	private SaleDepositCheckConfigDAO saleDepositCheckConfigDAO;
	@Autowired
	private CardToMerchDAO cardToMerchDAO;
	@Autowired
	protected WorkflowService workflowService;
	@Autowired
	private UserService userService;
	@Autowired
	private BaseDataService baseDataService;
	@Autowired
	private CardRiskService cardRiskService;
	@Autowired
	private UserCertificateRevService userCertificateRevService;
	
	/** 默认编码格式 */
	private static final String DEFAULT_ENCODING = "UTF-8";
	
	/** 默认分隔符 */
	private static final String DEFAULT_SEQ = ",";
	
	private final Log logger = LogFactory.getLog(getClass());
	
	public boolean pointChange(PointChgReg reg, UserInfo user) throws BizException {
		Assert.notNull(reg, "积分调整登记对象不能为空");
		Assert.notNull(user, "登录用户的信息不能为空");
		Assert.notEmpty(reg.getPtClass(), "积分类型不能为空");
		Assert.notEmpty(reg.getCardId(), "卡号不能为空");
		Assert.notEmpty(reg.getAcctId(), "账号不能为空");
		
		String roleType = user.getRole().getRoleType();
		if (RoleType.MERCHANT.getValue().equals(roleType)) {
			reg.setBranchCode(user.getMerchantNo());
		} else {
			reg.setBranchCode(user.getBranchNo());
		}

		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(reg.getCardId());
		Assert.notNull(cardInfo, "卡号[" + reg.getPtClass() + "]不存在。");
		reg.setCardBranch(cardInfo.getCardIssuer());
		
		
		PointClassDef pointClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(reg.getPtClass());
		Assert.notNull(pointClassDef, "积分类型[" + reg.getPtClass() + "]不存在。");

		PointBalKey key = new PointBalKey();
		
		key.setAcctId(reg.getAcctId());
		key.setPtClass(reg.getPtClass());
		
		PointBal pointBal = (PointBal)pointBalDAO.findByPk(key);
		Assert.notNull(pointBal, "账号为[" + reg.getAcctId() + "]积分类型为[" + reg.getPtClass() + "]的账户不存在");
		
		/*reg.setJinstId(pointBal.getJinstId());
		reg.setJinstType(pointBal.getJinstType());*/

		reg.setJinstType(pointClassDef.getJinstType());
		reg.setJinstId(pointClassDef.getJinstId());
		reg.setPtAvlb(pointBal.getPtAvlb());
		reg.setStatus(RegisterState.WAITED.getValue());
		reg.setUpdateUser(user.getUserId());
		reg.setUpdateTime(new Date());

		Object obj = pointChgRegDAO.insert(reg);
		try {
			//启动单个流程
			this.workflowService.startFlow(reg, "pointChgRegAdapter", Long.toString(reg.getPointChgId()), user);
		} catch (Exception e) {
			throw new BizException("启动积分调整流程失败，原因：" + e.getMessage());
		}
		
		
		// 发报文
		//long waitsinfoId = MsgSender.sendMsg(MsgType.POINT_CHG, reg.getPointChgId(), reg.getUpdateUser());
		//return obj!=null&&waitsinfoId!=0L;
		return obj!= null;
	}

	public List<PointChgReg> addFilePointChgReg(File upload, 
			String uploadFileName, UserInfo user) throws Exception {
		String roleType = user.getRole().getRoleType();
		
		// 检查记录是否合法，合法的记录插入登记簿中，不合法的放在uninsertPointChgRegList中
		List<PointChgReg> uninsertPointChgRegList = new ArrayList<PointChgReg>();
		
		try{
			PointChgImporter pointChgImporter = new PointChgImporter();
			List<PointChgReg> pointChgRegList = pointChgImporter.getFileImporterList(upload, uploadFileName);
			int maxSize = 500;
			Assert.isTrue(pointChgRegList.size()<=maxSize, "文件批量调整总笔数最大"+maxSize+"条");
			
			for(PointChgReg pointChgReg : pointChgRegList){
				// 检查卡号
				CardInfo card = (CardInfo) this.cardInfoDAO.findByPk(pointChgReg.getCardId());
				if (card == null) {
					pointChgReg.setRemark("卡号[" + pointChgReg.getCardId() + "]不存在");
					uninsertPointChgRegList.add(pointChgReg);
					continue;
				}
				
				// 检查账户
				AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(card.getAcctId());
				if (acctInfo == null) {
					pointChgReg.setRemark("卡号[" + pointChgReg.getCardId() + "]的账户不存在");
					uninsertPointChgRegList.add(pointChgReg);
					continue;
				}
				
				//
				Map<String, Object> params = new HashMap<String, Object>();
				
				String branchCode = "";
				params.put("acctId", acctInfo.getAcctId());
				params.put("ptClass", pointChgReg.getPtClass());
				if (RoleType.CARD.getValue().equals(roleType)
						|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
					params.put("jinstId", user.getBranchNo());
					branchCode = user.getBranchNo();
				}
				else if (RoleType.MERCHANT.getValue().equals(roleType)) {
					params.put("jinstId", user.getMerchantNo());
					branchCode = user.getMerchantNo();
				}
				
				List<PointBal> pointBalList = this.pointBalDAO.getPointBalList(params);
				if (CollectionUtils.isEmpty(pointBalList)) {
					pointChgReg.setRemark("卡号[" + pointChgReg.getCardId() + "]不存在相关的积分账户");
					uninsertPointChgRegList.add(pointChgReg);
					continue;
				}
				
				// 调整的积分必须是数字
				if (!NumberUtils.isNumber(pointChgReg.getRemark())) {
					pointChgReg.setRemark("卡号[" + pointChgReg.getCardId() + "]对应输入的调整积分不是数字");
					uninsertPointChgRegList.add(pointChgReg);
					continue;
				}
				
				// 填写登记薄
				pointChgReg.setAcctId(card.getAcctId());
				pointChgReg.setPtChg(new BigDecimal(pointChgReg.getRemark()));
				pointChgReg.setRemark("");
				pointChgReg.setPtAvlb(pointBalList.get(0).getPtAvlb());
				pointChgReg.setBranchCode(branchCode);
				pointChgReg.setJinstType(pointBalList.get(0).getJinstType());
				pointChgReg.setJinstId(pointBalList.get(0).getJinstId());
				pointChgReg.setUpdateUser(user.getUserId());
				pointChgReg.setUpdateTime(new Date());
				pointChgReg.setStatus(RegisterState.WAITED.getValue());
				
				pointChgReg.setCardBranch(card.getCardIssuer());
				
				this.pointChgRegDAO.insert(pointChgReg);
				//启动单个流程
				this.workflowService.startFlow(pointChgReg, "pointChgRegAdapter", Long.toString(pointChgReg.getPointChgId()), user);
				
			}
		} catch (Exception e) {
			logger.error("检查积分调整文件发生异常，原因：" + e);
			throw new BizException(e.getMessage());
		}
		return uninsertPointChgRegList;
	}
	
	public void addDepositPointReg(DepositPointReg depositPointReg,
			UserInfo user, String serialNo) throws BizException {
		Assert.notNull(depositPointReg, "积分充值登记对象不能为空");
		Assert.notNull(user, "登录用户的信息不能为空");
		Assert.notEmpty(depositPointReg.getCardId(), "要做积分充值的卡号不能为空");
		
		// 1. 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
			
			// 待验签的数据
			String info = depositPointReg.getCardId() + depositPointReg.getRefAmt().toString() + depositPointReg.getRandomSessionid();
			
			Assert.isTrue(this.userCertificateRevService.processUserSigVerify(serialNo, depositPointReg.getSignature(), info), "验签失败");
		}
		
		// 2. 检查卡信息
		CardInfo cardInfo = this.checkCardNo(depositPointReg, user);
		
		// 3. 判断该发卡机构是否需要审核
		boolean isNeedCheck ;
		 if(RoleType.MERCHANT.getValue().equals(user.getRole().getRoleType())){//商户都要审核
			 isNeedCheck = true;
		 }else{
			 isNeedCheck = this.isCheckForDeposit(user);
		 }
		
		// 3. 在登记簿中插入数据
		depositPointReg = this.setDepositPointRegData(depositPointReg, cardInfo, isNeedCheck, user);
		
		// 4.如果需要审核，则启动审核流程
		if (isNeedCheck) {
			try {
				this.workflowService.startFlow(depositPointReg, WorkflowConstants.DEPOSIT_POINT_ADAPTER, 
								Long.toString(depositPointReg.getDepositBatchId()), user);
			} catch (Exception e) {
				throw new BizException(e.getMessage());
			}
		} else {
			// 5. 发送充值报文，同时扣减风险准备金和售卡配额（单张卡充值没有预充值）
			this.sendMsgAndDealCardRisk(depositPointReg, user);
		}
	}
	
	/**
	 * 检查要做积分充值的卡
	 * @param depositPointReg
	 * @param user
	 * @return
	 * @throws BizException
	 */
	private CardInfo checkCardNo(DepositPointReg depositPointReg, UserInfo user) throws BizException {
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(depositPointReg.getCardId());
		
		Assert.notNull(cardInfo, "卡号[" + depositPointReg.getCardId() + "]不存在");
		Assert.notEmpty(cardInfo.getAcctId(), "卡号[" + depositPointReg.getCardId() + "]的账户不存在");
		Assert.equals(CardState.ACTIVE.getValue(), cardInfo.getCardStatus(), 
				"卡号[" + depositPointReg.getCardId() + "]不是正常状态，不能充值！");
		
		// 如果没有权限充值则报错
		if (!baseDataService.hasRightsToDepositCoupon(user, cardInfo)){
			throw new BizException("没有给该卡[" + depositPointReg.getCardId() + "]充值的权限");
		}
		
		return cardInfo;
	}
	
	/**
	 * 判断登录的机构充值是否需要审核。
	 * @param cardInfo
	 * @return
	 * @throws BizException
	 */
	private boolean isCheckForDeposit(UserInfo user) throws BizException {
		String roleType = user.getRole().getRoleType();
		boolean isNeedCheck = false;
		
		if (RoleType.CARD.getValue().equals(roleType)) {
			SaleDepositCheckConfig config = (SaleDepositCheckConfig) this.saleDepositCheckConfigDAO.findByPk(user.getBranchNo());
			Assert.notNull(config, "发卡机构[" + user.getBranchNo() + "]没有配置充值审核权限");
			
			isNeedCheck = StringUtils.equals(Symbol.YES, config.getDepositCheck());
		}
		return isNeedCheck;
	}
	
	/**
	 * 为积分充值登记对象赋值，同时在数据库中插入记录
	 * 
	 * @param depositPointReg
	 * @param isCheck
	 * @param user
	 * @return
	 * @throws BizException
	 */
	private DepositPointReg setDepositPointRegData(DepositPointReg depositPointReg, 
			CardInfo cardInfo, boolean isCheck, UserInfo user) throws BizException {

		// 如果需要审核，则状态为待审核
		if (isCheck) {
			depositPointReg.setStatus(RegisterState.WAITED.getValue());
		} else {
			depositPointReg.setStatus(RegisterState.WAITEDEAL.getValue());
		}
		
		String roleType = user.getRole().getRoleType();
		
		// 商户做积分充值时
		if (RoleType.MERCHANT.getValue().equals(roleType)) {
			depositPointReg.setDepositBranch(user.getMerchantNo());
		} 
		// 部门充值时
		else if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			depositPointReg.setDepositBranch(user.getDeptId());
		} else {
			depositPointReg.setDepositBranch(user.getBranchNo());
		}
		
		// 数据预处理
		depositPointReg.setCardBranch(cardInfo.getCardIssuer());
		depositPointReg.setUpdateTime(new Date());
		depositPointReg.setUpdateUser(user.getUserId());
		depositPointReg.setEntryUserid(user.getUserId());
		depositPointReg.setFileDeposit(Symbol.NO); // 不是以文件方式充值
		// 取得系统工作日，作为充值日期
		String workDate = SysparamCache.getInstance().getWorkDateNotFromCache();
		depositPointReg.setDepositDate(workDate);

		// 存储充值登记簿
		this.depositPointRegDAO.insert(depositPointReg);
		
		return depositPointReg;
	}
	
	/**
	 * 发送充值报文，同时扣减操作员的配额和发卡机构风险准备金。（单张卡充值没有预充值）
	 * @throws BizException
	 */
	private void sendMsgAndDealCardRisk(DepositPointReg depositPointReg, UserInfo user) throws BizException {
		// 发送积分充值报文
		MsgSender.sendMsg(MsgType.DEPOSIT_POINT, depositPointReg.getDepositBatchId(), user.getUserId());
		
		String roleType = user.getRole().getRoleType();
		// 商户不用扣减操作
		if (!RoleType.MERCHANT.getValue().equals(roleType)) {
			// 扣减操作员配额和发卡机构的风险准备金
			this.deductUserAmtAndCardRisk(depositPointReg, user);
		}
	}
	
	/**
	 * 扣减风险保证金和操作员额度
	 * @param depositPointReg
	 * @param user
	 * @throws BizException
	 */
	private void deductUserAmtAndCardRisk(DepositPointReg depositPointReg, UserInfo user) throws BizException {
		BigDecimal totalRisk = depositPointReg.getRefAmt(); // 积分充值扣的是积分折算金额。
		
		// 扣减风险准备金和充值售卡额度
		if (!RoleType.CARD.getValue().equals(user.getRole().getRoleType())) {
			BranchSellReg branchSellReg = new BranchSellReg(); 
			branchSellReg.setId(depositPointReg.getDepositBatchId());	// 充值登记薄的ID
			branchSellReg.setAdjType(AdjType.MANAGE.getValue());
			branchSellReg.setAmt(totalRisk);						// 清算金额
			branchSellReg.setCardBranch(depositPointReg.getCardBranch());		// 发卡机构
			branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
			branchSellReg.setSellBranch(depositPointReg.getDepositBranch());	// 充值机构
			if (RoleType.CARD_DEPT.getValue().equals(user.getRole().getRoleType())) {
				branchSellReg.setSellType(SellType.DEPT.getValue());
			} else {
				branchSellReg.setSellType(SellType.BRANCH.getValue());
			}
			this.cardRiskService.activateSell(branchSellReg);
		}
		// 扣减操作员额度
		this.cardRiskService.deductUserAmt(user.getUserId(), depositPointReg.getDepositBranch(), totalRisk);
		
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(depositPointReg.getDepositBatchId());	// 积分充值登记薄的ID
		cardRiskReg.setAdjType(AdjType.MANAGE.getValue());
		cardRiskReg.setAmt(totalRisk);						// 积分折算金额
		cardRiskReg.setBranchCode(depositPointReg.getCardBranch());	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.formatDate("yyyyMMdd"));
		
		this.cardRiskService.activateCardRisk(cardRiskReg);
	}
	
	public CardInfo checkCardId(String cardId, String cardCount, UserInfo user) throws BizException {
		Assert.notEmpty(cardId, "卡号不能为空");
		
		CardInfo cardInfo = null;
		int count = 1; 
		if (StringUtils.isNotEmpty(cardCount)) {
			Assert.isTrue(NumberUtils.isDigits(cardCount), "卡数量必须为正整数");
			count = Integer.valueOf(cardCount);
		}
		if (cardId.length() == 19) {//新卡
			String[] cardArray = CardUtil.getCard(cardId, count);
			for (String cardNo : cardArray) {
				cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardNo);
				Assert.notNull(cardInfo, "卡号[" + cardNo + "]不存在");
				
				Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + cardNo + "]不是“正常(已激活)”状态的卡");
				
				AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
				Assert.notNull(acctInfo, "卡号[" + cardNo + "]的账户不存在");
			}
		}
		// 18位的是旧卡
		else if (cardId.length() == 18) {
			String[] cardArray = CardUtil.getOldCard(cardId, count);
			for (String cardNo : cardArray) {
				cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardNo);
				// 按另一种规则试试
				if (cardInfo == null) {
					String cardPrex = StringUtils.substring(cardId, 0, cardId.length() - 1);
					cardId = cardPrex + CardUtil.getOldCardLast(cardPrex);
					cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
					
					Assert.notNull(cardInfo, "旧卡结束卡号[" + cardPrex + "*]不存在");
				}
				Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + cardNo + "]不是“正常(已激活)”状态的卡");
				
				AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
				Assert.notNull(acctInfo, "卡号[" + cardNo + "]的账户不存在");
			}
		} else {
			Assert.isTrue(count == 1, "外部卡不能使用批量积分充值");
			
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在");
			
			Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + cardId + "]不是“正常(已激活)”状态的卡");
			
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
			Assert.notNull(acctInfo, "卡号[" + cardId + "]的账户不存在");
		}
		
//		cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
//		Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在");
//		
//		Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "只有“正常(已激活)”状态的卡才可以做积分充值");
//		
//		AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
//		Assert.notNull(acctInfo, "卡号[" + cardId + "]的账户不存在");
		
		String roleType = user.getRole().getRoleType();
		// 商户。判断商户是否属于该卡所属发卡机构
		if (RoleType.MERCHANT.getValue().equals(roleType)) {
			CardToMerchKey key = new CardToMerchKey();
			
			key.setBranchCode(cardInfo.getCardIssuer());
			key.setMerchId(user.getMerchantNo());
			
			Assert.isTrue(this.cardToMerchDAO.isExist(key), "商户[" + user.getMerchantNo() + "]不是卡号[" + cardId + "]所属发卡机构的特约商户。");
		}
		// 发卡机构、机构网点
		else if (RoleType.CARD.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			Assert.equals(user.getBranchNo(), cardInfo.getCardIssuer(), "卡号[" + cardId + "]不是登录发卡机构发的卡");
		}
		// 售卡代理
		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			boolean flag = isCardSellPrivilege(cardInfo, user, Constants.POINT_EXC_GIFT_PRIVILEGE_CODE);
			Assert.isTrue(flag, "售卡代理[" + cardId +  "]没有权限为卡[" + cardId + "]做积分充值");
		}
		
		return cardInfo;
	}
	
	/**
	 * 判断售卡代理是否有权限操作此卡
	 */
	private boolean isCardSellPrivilege(CardInfo cardInfo, UserInfo user, String limitId) throws BizException{
		
//		String saleOrgId = cardInfo.getSaleOrgId();
		String cardIssuer = cardInfo.getCardIssuer();
		String proxyId = user.getBranchNo();

//		Assert.equals(saleOrgId, proxyId, "本售卡代理不是该卡的售卡机构,无法操作该卡。");
		
		// 判断是否属于该发卡机构的售卡代理
		BranchProxyDAO branchProxyDAO = (BranchProxyDAO) SpringContext.getService("branchProxyDAOImpl");
		BranchProxyKey branchProxyKey = new BranchProxyKey(proxyId, cardIssuer, ProxyType.SELL.getValue());
		if (!branchProxyDAO.isExist(branchProxyKey)) {
			return false;
		}
		
		// 判断是否拥有该权限
		SaleProxyPrivilegeDAO saleProxyPrivilegeDAO = (SaleProxyPrivilegeDAO) SpringContext.getService("saleProxyPrivilegeDAOImpl");
		List<HashMap> privlegeList = saleProxyPrivilegeDAO.findSaleProxy(proxyId, cardIssuer, limitId);
		if (CollectionUtils.isNotEmpty(privlegeList)) {
			return true;
		}
		return false;
	}
	
	public void addDepositPointBatReg(DepositPointReg depositPointReg, 
			DepositPointBatReg depositPointBatReg, String cardCount, 
			UserInfo user, String serialNo) throws BizException {
		Assert.notNull(depositPointReg, "积分充值登记对象不能为空");
		Assert.notNull(user, "登录用户的信息不能为空");
		Assert.notEmpty(depositPointReg.getStrCardId(), "起始卡号不能为空");
		Assert.notEmpty(depositPointReg.getEndCardId(), "结束卡号不能为空");
		Assert.notEmpty(cardCount, "批量充值的卡数量不能为空");
		Assert.isTrue(NumberUtils.isDigits(cardCount), "批量充值的卡连续张数必须为正整数");
		Long count = NumberUtils.toLong(cardCount);
		
		// 1. 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
			// 待验签的数据
			String info = depositPointReg.getRefAmt().toString() + depositPointReg.getRandomSessionid();
			
			Assert.isTrue(this.userCertificateRevService.processUserSigVerify(serialNo, depositPointReg.getSignature(), info), "验签失败");
		}
		// 2. 检查卡信息
		Object[] objects = this.checkCardNoBat(depositPointReg, depositPointBatReg, count, user);
		CardInfo cardInfo = (CardInfo) objects[0];
		List<DepositPointBatReg> batList = (List<DepositPointBatReg>) objects[1];
		
		// 3. 判断该发卡机构是否需要审核
		boolean isNeedCheck ;
		 if(RoleType.MERCHANT.getValue().equals(user.getRole().getRoleType())){//商户都要审核
			 isNeedCheck = true;
		 }else{
			 isNeedCheck = this.isCheckForDeposit(user);
		 }
		
		// 3. 在登记簿中插入数据
		depositPointReg = this.setDepositPointBatRegData(depositPointReg, batList, cardInfo, isNeedCheck, user);
		
		// 4.如果需要审核，则启动审核流程
		if (isNeedCheck) {
			try {
				this.workflowService.startFlow(depositPointReg, WorkflowConstants.DEPOSIT_POINT_ADAPTER, 
								Long.toString(depositPointReg.getDepositBatchId()), user);
			} catch (Exception e) {
				throw new BizException(e.getMessage());
			}
		} else {
			// 5. 发送充值报文，同时扣减风险准备金和售卡配额（单张卡充值没有预充值）
			this.sendMsgAndDealCardRisk(depositPointReg, user);
		}
		
	}
	
	/**
	 * 检查要做积分充值的卡
	 * @param depositPointReg
	 * @param user
	 * @return
	 * @throws BizException
	 */
	private Object[] checkCardNoBat(DepositPointReg depositPointReg, 
			DepositPointBatReg depositPointBatReg, Long cardCount, UserInfo user) throws BizException {
		List<DepositPointBatReg> batList = new ArrayList<DepositPointBatReg>();
		String cardId = depositPointReg.getStrCardId();
		Assert.notEmpty(cardId, "起始卡号不能为空");
		Assert.notNull(depositPointBatReg.getPtPoint(), "单张卡充值积分不能为空");
		Assert.notNull(depositPointBatReg.getRefAmt(), "单张卡充值积分折算金额不能为空");

		CardInfo cardInfo = null;
		DepositPointBatReg batReg = null;
		if (cardId.length() == 19) {//新卡
			String[] cardArray = CardUtil.getCard(cardId, cardCount.intValue());
			for (String cardNo : cardArray) {
				cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardNo);
				Assert.notNull(cardInfo, "卡号[" + cardNo + "]不存在");
				
				Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + cardNo + "]不是“正常(已激活)”状态的卡");
				
				AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
				Assert.notNull(acctInfo, "卡号[" + cardNo + "]的账户不存在");
				
				batReg = new DepositPointBatReg();
				batReg.setCardId(cardNo);
				batReg.setPtPoint(depositPointBatReg.getPtPoint());
				batReg.setRefAmt(depositPointBatReg.getRefAmt());
				
				batList.add(batReg);
			}
		}
		// 18位的是旧卡
		else if (cardId.length() == 18) {
			String[] cardArray = CardUtil.getOldCard(cardId,  cardCount.intValue());
			for (String cardNo : cardArray) {
				cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardNo);
				// 按另一种规则试试
				if (cardInfo == null) {
					String cardPrex = StringUtils.substring(cardId, 0, cardId.length() - 1);
					cardId = cardPrex + CardUtil.getOldCardLast(cardPrex);
					cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
					
					Assert.notNull(cardInfo, "旧卡结束卡号[" + cardPrex + "*]不存在");
				}
				Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + cardNo + "]不是“正常(已激活)”状态的卡");
				
				AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
				Assert.notNull(acctInfo, "卡号[" + cardNo + "]的账户不存在");
				
				batReg = new DepositPointBatReg();
				batReg.setCardId(cardNo);
				batReg.setPtPoint(depositPointBatReg.getPtPoint());
				batReg.setRefAmt(depositPointBatReg.getRefAmt());
				
				batList.add(batReg);
			}
		}
		
		// 如果没有权限充值则报错
		if (!baseDataService.hasRightsToDepositCoupon(user, cardInfo)){
			throw new BizException("没有给该卡[" + depositPointReg.getCardId() + "]充值的权限");
		}
		
		return new Object[]{cardInfo, batList};
	}
	
	/**
	 * 为积分充值登记对象赋值，同时在数据库中插入记录
	 * 
	 * @param depositPointReg
	 * @param isCheck
	 * @param user
	 * @return
	 * @throws BizException
	 */
	private DepositPointReg setDepositPointBatRegData(DepositPointReg depositPointReg, 
			List<DepositPointBatReg> batList, CardInfo cardInfo, boolean isCheck, UserInfo user) throws BizException {

		// 如果需要审核，则状态为待审核
		String status = "";
		if (isCheck) {
			status = RegisterState.WAITED.getValue();
		} else {
			status = RegisterState.WAITEDEAL.getValue();
		}
		
		String roleType = user.getRole().getRoleType();
		
		// 商户做积分充值时
		if (RoleType.MERCHANT.getValue().equals(roleType)) {
			depositPointReg.setDepositBranch(user.getMerchantNo());
		} 
		// 部门充值时
		else if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			depositPointReg.setDepositBranch(user.getDeptId());
		} else {
			depositPointReg.setDepositBranch(user.getBranchNo());
		}
		
		// 数据预处理
		depositPointReg.setStatus(status);
		depositPointReg.setCardBranch(cardInfo.getCardIssuer());
		depositPointReg.setUpdateTime(new Date());
		depositPointReg.setUpdateUser(user.getUserId());
		depositPointReg.setEntryUserid(user.getUserId());
		depositPointReg.setFileDeposit(Symbol.NO); // 不是以文件方式充值
		// 取得系统工作日，作为充值日期
		String workDate = SysparamCache.getInstance().getWorkDateNotFromCache();
		depositPointReg.setDepositDate(workDate);

		// 存储充值登记簿
		this.depositPointRegDAO.insert(depositPointReg);
		
		List<DepositPointBatReg> batRegList = new ArrayList<DepositPointBatReg>();
		for (DepositPointBatReg batReg : batList) {
			batReg.setDepositBatchId(depositPointReg.getDepositBatchId());
			batReg.setStatus(status);
			
			batRegList.add(batReg);
		}
		this.depositPointBatRegDAO.insertBatch(batRegList);
		
		return depositPointReg;
	}
	
	public void addDepositPointBatRegFile(DepositPointReg depositPointReg,
			File file, String cardCount, UserInfo user, String serialNo, String limitId) throws BizException {
		Assert.notNull(depositPointReg, "要添加的积分充值对象不能为空");
		Assert.notNull(user, "登录用户的信息对象不能为空");
		Assert.notEmpty(cardCount, "批量充值的卡数量不能为空");
		Assert.isTrue(NumberUtils.isDigits(cardCount), "批量充值的卡连续张数必须为正整数");
		Long count = NumberUtils.toLong(cardCount);
		Assert.notNull(file, "充值文件不能为空");
		Assert.notEmpty(depositPointReg.getPtClass(), "积分充值对象中的积分类型不能为空");
		
		// 1. 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
			// 待验签的数据
			String info = depositPointReg.getRefAmt().toString() + depositPointReg.getRandomSessionid();
			
			Assert.isTrue(this.userCertificateRevService.processUserSigVerify(serialNo, depositPointReg.getSignature(), info), "验签失败");
		}
		
		// 2. 解析充值文件，存入到一个list中
		List<DepositPointBatReg> batList = resolveFile(file);
		
		// 3. 判断该发卡机构是否需要审核
		boolean isNeedCheck ;
		 if(RoleType.MERCHANT.getValue().equals(user.getRole().getRoleType())){//商户都要审核
			 isNeedCheck = true;
		 }else{
			 isNeedCheck = this.isCheckForDeposit(user);
		 }
		
		// 4.在数据中插入记录
		depositPointReg = addDepositDetail(depositPointReg, batList, count, isNeedCheck, user, limitId);
		
		// 5.如果需要审核，则启动审核流程
		if (isNeedCheck) {
			try {
				this.workflowService.startFlow(depositPointReg, WorkflowConstants.DEPOSIT_POINT_ADAPTER, 
								Long.toString(depositPointReg.getDepositBatchId()), user);
			} catch (Exception e) {
				throw new BizException(e.getMessage());
			}
		} else {
			// 5. 发送充值报文，同时扣减风险准备金和售卡配额（单张卡充值没有预充值）
			this.sendMsgAndDealCardRisk(depositPointReg, user);
		}
		
	}
	
	/**
	 * 解析充值文件，得到充值明细对象
	 * @param file 充值文件
	 * @return
	 * @throws BizException
	 */
	private List<DepositPointBatReg> resolveFile(File file) throws BizException {
		byte[] fileData = getFileByte(file);
		
		List lines = getLines(fileData);
		
		Assert.isTrue(CollectionUtils.isNotEmpty(lines) && lines.size() > 1, "文件无内容或格式错误");
		
		return resolveDepositDetail(lines);
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
	 * 解析充值明细
	 * @return
	 */
	private List<DepositPointBatReg> resolveDepositDetail(List lines) throws BizException {
		List<DepositPointBatReg> list = new ArrayList<DepositPointBatReg>();
		
		// 获取表头字段数目
		int fieldNum = 0;
		if(lines.size()!=0){
			fieldNum = ((String) lines.get(0)).split(DEFAULT_SEQ).length;
		}
		
		// 记录卡号, 用作判断是否重复.
		Set<String> cardNoSet = new HashSet<String>();
		
		// 解析充值明细， 从第二行开始解析
		// 充值格式为，即第一行为：卡号,充值积分数
		DepositPointBatReg batReg = null;
		String depositLine = "";
		for (int i = 1, n = lines.size(); i < n; i++) {
			depositLine = (String) lines.get(i);
			
			// 空行略过.
			if (StringUtils.isEmpty(depositLine)) {
				continue;
			}
			
			String[] arr = depositLine.split(DEFAULT_SEQ, -1);
			
			// 检查卡号
			checkCardNo(arr, i, fieldNum, cardNoSet);
			
			// 取得充值明细对象
			batReg = getBatRegFromDetail(arr);
			
			list.add(batReg);
		}
		
		return list;
	}
	
	/**
	 * 对充值明细里的卡号，进行检查
	 * @param arr 单条记录的数组
	 * @param count 记录的条数的序号
	 * @param fieldNum 模板中的明细个数
	 * @param cardNoSet 充值明细里的卡号
	 * @throws BizException
	 */
	private void checkCardNo(String[] arr, int count, int fieldNum, 
			Set<String> cardNoSet) throws BizException {
		// 充值明细元素个数要和模板配置明细个数一致.
		if (arr.length != fieldNum) {
			String msg = "明细第" + count + "行格式错误,元素不为" + fieldNum + "个";
			logger.error(msg);
			throw new BizException(msg);
		}
		
		if (ArrayUtils.isEmpty(arr)) {
			throw new BizException("解析出的数组为空");
		}
		
		//取得卡号,为空表示有错，抛出异常，跳出循环
		String cardNo = arr[0];
		if (StringUtils.isEmpty(cardNo)) {
			throw new BizException("充值明细第" + count + " 行格式错误，卡号不能为空。");
		}
		
		//卡号不能重复
		if (cardNoSet.contains(cardNo)) {
			throw new BizException("卡号[" + cardNo + "]的记录重复");
		}
		cardNoSet.add(cardNo);
	}
	
	/**
	 * 从明细中取得充值明细对象
	 * @param arr
	 * @return
	 */
	private DepositPointBatReg getBatRegFromDetail(String[] arr) throws BizException {
		DepositPointBatReg batReg = new DepositPointBatReg();
		Assert.isTrue(arr.length == 2, "明细数组的长度有误");
		batReg.setCardId(arr[0]);
		Assert.isTrue(NumberUtils.isNumber(arr[1]), "充值积分数必须为数值");
		
		BigDecimal ptPoint = NumberUtils.createBigDecimal(arr[1]);
		Assert.isTrue(AmountUtil.gt(ptPoint, BigDecimal.ZERO), "充值积分数必须大于0");
		batReg.setPtPoint(ptPoint);
		
		return batReg;
	}
	
	/**
	 * 添加充值明细到批量充值明细表
	 * @param list
	 * @param depositReg
	 * @return
	 * @throws BizException
	 */
	private DepositPointReg addDepositDetail(DepositPointReg depositPointReg, List<DepositPointBatReg> batList, 
			Long count, boolean isNeedCheck, UserInfo user, String limitId) throws BizException {
		Assert.isTrue(batList.size() == count.intValue(), "充值文件中的充值笔数与页面录入的充值笔数不一致");
		BigDecimal totalPointAmt = BigDecimal.ZERO;
		
		PointClassDef pointClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(depositPointReg.getPtClass());
		Assert.notNull(pointClassDef, "积分类型[" + depositPointReg.getPtClass() + "]不存在");
		
		// 如果需要审核，则状态为待审核
		String status = "";
		if (isNeedCheck) {
			status = RegisterState.WAITED.getValue();
		} else {
			status = RegisterState.WAITEDEAL.getValue();
		}
		
		String roleType = user.getRole().getRoleType();
		// 商户做积分充值时
		if (RoleType.MERCHANT.getValue().equals(roleType)) {
			depositPointReg.setDepositBranch(user.getMerchantNo());
		} 
		// 部门充值时
		else if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			depositPointReg.setDepositBranch(user.getDeptId());
		} else {
			depositPointReg.setDepositBranch(user.getBranchNo());
		}
		
		depositPointReg.setStatus(status);
		depositPointReg.setDepositDate(SysparamCache.getInstance().getWorkDateNotFromCache());
		depositPointReg.setUpdateTime(new Date());
		depositPointReg.setUpdateUser(user.getUserId());
		depositPointReg.setEntryUserid(user.getUserId());
		depositPointReg.setFileDeposit(Symbol.YES); // 是以文件方式充值
		
		this.depositPointRegDAO.insert(depositPointReg);
		
		List<DepositPointBatReg> list = new ArrayList<DepositPointBatReg>();
		// 在明细表里插入记录
		for (DepositPointBatReg reg : batList) {
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(reg.getCardId());
			Assert.notNull(cardInfo, "要充值的卡号[" + reg.getCardId() + "]不存在");
			Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + reg.getCardId() + "]不是“正常(已激活)”状态");
			Assert.equals(cardInfo.getCardIssuer(), depositPointReg.getCardBranch(), 
					"卡号[" + reg.getCardId() + "]所属的发卡机构与页面选择的发卡机构[" + depositPointReg.getCardBranch() + "]不一致");
			
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
			Assert.notNull(acctInfo, "要充值的卡号[" + reg.getCardId() + "]的账户不存在");
			
			CardSubClassDef cardSubClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
			Assert.notNull(cardSubClass, "要充值的卡号[" + reg.getCardId() + "]所属卡类型[" + cardInfo.getCardSubclass() + "]不存在");
			
			// 判断是否有给该卡充值的权限
			if (RoleType.CARD_SELL.getValue().equals(user.getRole().getRoleType())) {
				Assert.isTrue(hasCardSellPrivilegeByCardId(user.getRole().getBranchNo(), reg.getCardId(), limitId), 
						"该售卡代理没有权限为该卡[" + reg.getCardId() + "]充值");
			}
			
			// 如果没有权限充值则报错
			if (!baseDataService.hasRightsToDeposit(user, cardInfo)){
				throw new BizException("没有给该卡[" + reg.getCardId() + "]充值的权限");
			}
			
			totalPointAmt = AmountUtil.add(totalPointAmt, reg.getPtPoint());
			
			// 积分折算金额 = 充值积分*积分折算率
			BigDecimal refAmt = AmountUtil.multiply(reg.getPtPoint(), pointClassDef.getPtDiscntRate());
			
			reg.setDepositBatchId(depositPointReg.getDepositBatchId());
			reg.setRefAmt(refAmt);
			reg.setStatus(status);
			
			list.add(reg);
		}
		
		this.depositPointBatRegDAO.insertBatch(list);
		
		totalPointAmt = AmountUtil.format(totalPointAmt);
		BigDecimal pagePointSum = AmountUtil.format(depositPointReg.getPtPoint());
		
		Assert.isTrue(AmountUtil.et(totalPointAmt, pagePointSum), 
				"充值文件里的金额总和[" + totalPointAmt + "]与页面录入的总金额[" + pagePointSum + "]不一致");
		
		return depositPointReg;
	}
	
	private boolean hasCardSellPrivilegeByCardId(String proxyId, String cardId, String limitId){
		CardInfoDAO cardInfoDAO = (CardInfoDAO) SpringContext.getService("cardInfoDAOImpl");
		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
		
		return hasCardSellPrivilege(proxyId, cardInfo.getCardIssuer(), limitId);
	}
	
	private boolean hasCardSellPrivilege(String proxyId, String cardBranch, String limitId) {
		// 判断是否属于该发卡机构的售卡代理
		BranchProxyDAO branchProxyDAO = (BranchProxyDAO) SpringContext.getService("branchProxyDAOImpl");
		BranchProxyKey branchProxyKey = new BranchProxyKey(proxyId, cardBranch, ProxyType.SELL.getValue());
		if (!branchProxyDAO.isExist(branchProxyKey)) {
			return false;
		}
		
		// 判断是否拥有该权限
		SaleProxyPrivilegeDAO saleProxyPrivilegeDAO = (SaleProxyPrivilegeDAO) SpringContext.getService("saleProxyPrivilegeDAOImpl");
		List<HashMap> privlegeList = saleProxyPrivilegeDAO.findSaleProxy(proxyId, cardBranch, limitId);

		return CollectionUtils.isNotEmpty(privlegeList);
	}
}
