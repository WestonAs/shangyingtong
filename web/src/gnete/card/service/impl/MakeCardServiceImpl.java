package gnete.card.service.impl;

import flink.ftp.CommunicationException;
import flink.ftp.IFtpCallBackProcess;
import flink.ftp.IFtpTransferCallback;
import flink.ftp.impl.CommonDownloadCallBackImpl;
import flink.ftp.impl.CommonUploadCallBackImpl;
import flink.ftp.impl.FtpCallBackProcessImpl;
import flink.util.DateUtil;
import flink.util.IOUtil;
import flink.util.StringUtil;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardBinDAO;
import gnete.card.dao.CardBinRegDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardNoAssignDAO;
import gnete.card.dao.CardPrevConfigDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.IcTempParaDAO;
import gnete.card.dao.InsServiceAuthorityDAO;
import gnete.card.dao.MakeCardAppDAO;
import gnete.card.dao.MakeCardPersonDAO;
import gnete.card.dao.MakeCardRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardBin;
import gnete.card.entity.CardBinReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardNoAssign;
import gnete.card.entity.CardNoAssignKey;
import gnete.card.entity.CardPrevConfig;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.IcTempPara;
import gnete.card.entity.InsServiceAuthority;
import gnete.card.entity.MakeCardApp;
import gnete.card.entity.MakeCardPerson;
import gnete.card.entity.MakeCardReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.flag.MakeFlag;
import gnete.card.entity.flag.OKFlag;
import gnete.card.entity.flag.SMSFlag;
import gnete.card.entity.state.CardBinRegState;
import gnete.card.entity.state.CardBinState;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.state.CheckState;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.state.MakeCardAppState;
import gnete.card.entity.state.MakeCardRegState;
import gnete.card.entity.type.CardType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.MakeCardService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.util.CardUtil;
import gnete.card.util.TradeCardTypeMap;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("makeCardService")
public class MakeCardServiceImpl implements MakeCardService {

	@Autowired
	private MakeCardAppDAO makeCardAppDAO;
	@Autowired
	private CardNoAssignDAO cardNoAssignDAO;
	@Autowired
	private MakeCardRegDAO makeCardRegDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private CardBinDAO cardBinDAO;
	@Autowired
	private CardBinRegDAO cardBinRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MakeCardPersonDAO makeCardPersonDAO;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private InsServiceAuthorityDAO insServiceAuthorityDAO;
	@Autowired
	private IcTempParaDAO icTempParaDAO;
	@Autowired
	private CardPrevConfigDAO cardPrevConfigDAO;
	
	private static Logger logger = Logger.getLogger(MakeCardServiceImpl.class);

	/**
	 * 系统号，查询系统参数表获得
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

	/**
	 * 卡号长度，为19位
	 */
	private static int CARD_NO_LENGTH = 19;

	public CardBinReg addCardBinReg(CardBinReg cardBinReg,
			UserInfo user, String cardIssuer) throws BizException {
		// 检查数据
		Assert.notNull(cardBinReg, "卡BIN申请对象不能为空");
		Assert.notNull(user, "登录用户的信息不能为空 ");
		Assert.notEmpty(cardBinReg.getBinNo(), "卡BIN号码不能为空");
		Assert.notEmpty(cardIssuer, "发卡机构编号不能为空");
		Assert.notEmpty(cardBinReg.getCardType(), "卡种不能为空");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("insId", cardIssuer);
		params.put("serviceId", TradeCardTypeMap.getInsServiceTradeType(cardBinReg.getCardType()));
		params.put("status", CardTypeState.NORMAL.getValue());
		List<InsServiceAuthority> authorityList = this.insServiceAuthorityDAO.getInsServiceAuthority(params);
		if (CollectionUtils.isEmpty(authorityList)) {
			throw new BizException("发卡机构[" + cardIssuer + "]没有开通[" 
					+ CardType.valueOf(cardBinReg.getCardType()).getName() 
					+ "]功能，请联系运营分支机构管理员。");
		}

		// 卡bin号码是否限制以99开头
		String binNoLimit = SysparamCache.getInstance().getBinNoLimit();
		if (StringUtils.equals(binNoLimit, Symbol.YES)
				&& (!cardBinReg.getBinNo().startsWith("99"))) {
			throw new BizException("现阶段卡bin号码必须以99开头，请更换重试！");
		}
		
		BranchInfo cardBranch = (BranchInfo) branchInfoDAO.findByPk(cardIssuer);
		Assert.notNull(cardBranch, "机构信息表中不存在该发卡机构的信息");
		Assert.notEmpty(cardBranch.getCurCode(), "该发卡机构的货币代码为空");

		cardBinReg.setCurrCode(cardBranch.getCurCode());
		cardBinReg.setUpdateTime(new Date());
		cardBinReg.setUpdateBy(user.getUserId());
		cardBinReg.setStatus(CardBinRegState.WAITED.getValue());// 待审核状态
		cardBinReg.setCardIssuer(cardIssuer);

		CardBin cardBin = new CardBin();
		cardBin.setBinNo(cardBinReg.getBinNo());
		cardBin.setBinName(cardBinReg.getBinName());
		cardBin.setCardType(cardBinReg.getCardType());
		cardBin.setCurrCode(cardBinReg.getCurrCode());
		cardBin.setUpdateTime(cardBinReg.getUpdateTime());
		cardBin.setUpdateBy(user.getUserId());
		cardBin.setStatus(CardBinState.WAITED.getValue());// 待审核状态
		cardBin.setCardIssuer(cardIssuer);

		this.cardBinRegDAO.insert(cardBinReg);
		this.cardBinDAO.insert(cardBin);
		
		// 启动单个流程
		try {
			this.workflowService.startFlow(cardBinReg, "cardBinAdapter", Long.toString(cardBinReg.getId()), user);
		} catch (Exception e) {
			throw new BizException("启动卡BIN审核流程时发生异常，原因：" + e.getMessage());
		}

		return cardBinReg;
	}

	public boolean addCardBin(CardBin cardBin, String cardTypeCode,
			String currCode, String sessionUserCode) throws BizException {
		Assert.notNull(cardBin, "申请添加卡BIN对象不能为空");

		// String sysNo = "888";// 系统号，从系统参数表里取
		// String binNo = sysNo + cardBin.getBinNo() + cardTypeCode;
		// cardBin.setBinNo(binNo);

		cardBin.setCardType(cardTypeCode);
		cardBin.setCurrCode(currCode);
		cardBin.setUpdateTime(new Date());
		cardBin.setUpdateBy(sessionUserCode);
		cardBin.setStatus(CardBinState.UNUSED.getValue());
		return this.cardBinDAO.insert(cardBin) != null;
	}

	public boolean isExistCardBin(String binNo) throws BizException {
		Assert.notNull(binNo, "卡BIN号码不能为空");
		return this.cardBinDAO.findByPk(binNo) != null;
	}

	public CardSubClassDef addCardSubClass(CardSubClassDef cardSubClassDef, IcTempPara icTempPara,
			UserInfo sessionUser) throws BizException {
		String cardSubClassName = StringUtils.trim(cardSubClassDef.getCardSubclassName());
		// 检查数据
//		Assert.notEmpty(cardSubClassDef.getCardSubclass(), "卡类型号码不能为空");
		Assert.notEmpty(cardSubClassName, "卡类型名称不能为空");
		Assert.notEmpty(cardSubClassDef.getCardClass(), "卡种不能为空");
		Assert.notEmpty(cardSubClassDef.getCardIssuer(), "发卡机构代码不能为空");
		Assert.notEmpty(cardSubClassDef.getBinNo(), "卡BIN号码不能为空");
		Assert.notEmpty(cardSubClassDef.getIcType(), "卡片类型不能为空");
		
		if (StringUtils.isNotEmpty(cardSubClassDef.getExpirDate())) {
			Assert.isTrue(DateUtil.isValidDate(cardSubClassDef.getExpirDate(), "yyyyMMdd"), "失效日期的格式错误");
		}
		// 卡类型名不能重复 
		Assert.notTrue(this.cardSubClassDefDAO.isExsitCardSubClassName(cardSubClassName), 
				"卡类型名["+ cardSubClassName + "]已经存在，请更换重试");
		cardSubClassDef.setCardSubclassName(cardSubClassName);
		
		// add 2014年3月13日11:38:05 只能定义一个虚拟账户的卡子类型
		if (CardType.VIRTUAL.getValue().equals(cardSubClassDef.getCardClass())) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("cardClass", cardSubClassDef.getCardClass());
			
			Assert.isEmpty(cardSubClassDefDAO.findCardSubClass(map), "只能定义一个虚拟账户的卡子类型");
		}
		// end
		
		// 生成卡类型号
		String cardSubClassId = getCardSubClassId();
		cardSubClassDef.setCardSubclass(cardSubClassId);

//		// 如果该卡类型号已经存在，则禁止新增
//		boolean isExist = cardSubClassDefDAO.isExist(cardSubClassDef.getCardSubclass());
//		Assert.notTrue(isExist, "该卡类型号[" + cardSubClassDef.getCardSubclass() + "]已经存在，请更换卡子类号！");

		if (StringUtils.isBlank(cardSubClassDef.getMustExpirDate())) {
			cardSubClassDef.setMustExpirDate("20991231");
		}
//		cardSubClassDef.setStatus(CheckState.WAITED.getValue());
		cardSubClassDef.setStatus(CheckState.PASSED.getValue());
		cardSubClassDef.setUpdateBy(sessionUser.getUserId());
		cardSubClassDef.setUpdateTime(new Date());

		this.cardSubClassDefDAO.insert(cardSubClassDef);
		
		// 如果是制IC卡或复合卡的话，则需要在IC个性化参数表中插入数据
		if (cardSubClassDef.isIcOrCombineOrM1Type()) {
			Assert.notNull(icTempPara.getEcashbalance(), "电子现金余额不能为空");
			Assert.notNull(icTempPara.getBalanceLimit(), "电子现金余额上限不能为空");
			Assert.notNull(icTempPara.getAmountLimit(), "电子现金单笔交易限额不能为空");
			//Assert.notEmpty(icTempPara.getOnlineCheck(), "是否执行新卡检测不能为空");
			if (StringUtils.isEmpty(icTempPara.getOnlineCheck())) {
				icTempPara.setOnlineCheck(Symbol.NO);
			}
			Assert.notEmpty(icTempPara.getAutoDepositFlag(), "是否自动圈存不能为空");
			
			icTempPara.setCardSubclass(cardSubClassId);
			Assert.notTrue(this.icTempParaDAO.isExist(cardSubClassId), "卡子类型号[" + cardSubClassId + "]的信息已经在IC卡个人化参数表中存在");
			
			this.icTempParaDAO.insert(icTempPara);
		}
		
		// 启动单个流程
//		try {
//			this.workflowService.startFlow(cardSubClassDef, WorkflowConstants.CARDSUBCLASS_ADAPTER, 
//					cardSubClassDef.getCardSubclass(), sessionUser);
//		} catch (Exception e) {
//			throw new BizException("启动卡类型审核流程时发生异常" + e.getMessage());
//		}
		return cardSubClassDef;
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
	
	public void modifyCardSubClass(CardSubClassDef cardSubClassDef, 
			UserInfo sessionUser) throws BizException {
		Assert.notNull(cardSubClassDef, "传入的卡类型信息不能为空");
		Assert.notEmpty(cardSubClassDef.getCardSubclass(), "卡类型号码不能为空");

		CardSubClassDef def = (CardSubClassDef) cardSubClassDefDAO.findByPk(cardSubClassDef.getCardSubclass());
		Assert.notNull(def, "要修改的卡类型已经不存在");
		Assert.isTrue(StringUtils.equals(CheckState.WAITED.getValue(), cardSubClassDef.getStatus()), "该卡类型的状态不是“待审核”状态，不能修改");
		
		cardSubClassDef.setCardIssuer(def.getCardIssuer());
		cardSubClassDef.setStatus(def.getStatus());
		cardSubClassDef.setIsChgPwd(def.getIsChgPwd());
		cardSubClassDef.setUpdateBy(sessionUser.getUserId());
		cardSubClassDef.setUpdateTime(new Date());

		this.cardSubClassDefDAO.update(cardSubClassDef);
	}

	public void deleteCardSubClass(String cardSubclass) throws BizException {
		Assert.notEmpty(cardSubclass, "删除卡类型需指定卡类型编号");

		// 如果该卡类型已经发卡，则禁止删除
		List<CardInfo> list = this.cardInfoDAO.findByCardSubClass(cardSubclass);
		Assert.notTrue(CollectionUtils.isNotEmpty(list), "该卡类型[" + cardSubclass + "]已经发卡，不能删除！");

		// 撤销流程
		try {
			this.workflowService.deleteFlow(WorkflowConstants.CARD_SUB_CLASS_DEF, cardSubclass);
		} catch (Exception e) {
			throw new BizException(e);
		}
		// 删除卡类型
		this.cardSubClassDefDAO.delete(cardSubclass);
	}

	public MakeCardApp addMakeCardApp(MakeCardApp makeCardApp, UserInfo sessionUser) throws BizException {
		Assert.notNull(makeCardApp, "要添加的制卡申请对象不能为空。");
		Assert.notNull(sessionUser, "当前登录的用户对象不能为空。");
		Assert.notEmpty(makeCardApp.getMakeId(), "传入的制卡申请对象中制卡登记ID不能为空。");

		CardSubClassDef cardSubClass = getCardSubClassDef(makeCardApp);

		String cardstrNostr = makeCardApp.getStrNo().substring(11, makeCardApp.getStrNo().length() - 1);
		int strCardNo = NumberUtils.toInt(cardstrNostr);
		CardNoAssign cardNoAssign = new CardNoAssign();
		cardNoAssign.setBranchCode(makeCardApp.getBranchCode());
		cardNoAssign.setBinNo(cardSubClass.getBinNo());
		cardNoAssign.setStrCardNo(strCardNo);
		cardNoAssign.setEndCardNo(makeCardApp.getCardNum().add(new BigDecimal(strCardNo)).intValue() - 1);
		cardNoAssign.setUseCardNo(cardNoAssign.getEndCardNo());
		cardNoAssign.setUpdateBy(sessionUser.getUserId());
		cardNoAssign.setUpdateTime(new Date());
		
		this.cardNoAssignDAO.insert(cardNoAssign);

		// TODO 从机构信息里取
		makeCardApp.setMakeFlag(MakeFlag.NORMAL_CARD.getValue()); // 默认制卡方式
		makeCardApp.setCardSubtype(cardSubClass.getCardSubclass());
		makeCardApp.setBinNo(cardSubClass.getBinNo());
		makeCardApp.setStrNo(makeCardApp.getStrNo());
		makeCardApp.setStatus(MakeCardAppState.CREATE.getValue());
		makeCardApp.setAppUser(sessionUser.getUserId());
		makeCardApp.setUpdateBy(sessionUser.getUserId());
		makeCardApp.setUpdateTime(new Date());

		this.makeCardAppDAO.insert(makeCardApp);
		
		try {
			// 启动单个流程
			workflowService.startFlow(makeCardApp, "makeCardAppAdapter", Long.toString(makeCardApp.getId()), sessionUser);
		} catch (Exception e) {
			throw new BizException("启动制卡申请审核流程时发生异常，原因：" + e);
		}
		return makeCardApp;
	}

	public boolean releaseCardNoAssign(MakeCardApp makeCardApp)
			throws BizException {
		Assert.notNull(makeCardApp, "制卡申请对象不能为空。");
		Assert.notEmpty(makeCardApp.getBranchCode(), "发卡机构不能为空。");
		Assert.notEmpty(makeCardApp.getBinNo(), "卡bin号码不能为空。");
		Assert.notEmpty(makeCardApp.getStrNo(), "起始卡号不能为空。");

		CardNoAssignKey key = new CardNoAssignKey();
		key.setBranchCode(makeCardApp.getBranchCode());
		key.setBinNo(makeCardApp.getBinNo());
		String strNoStr = makeCardApp.getStrNo().substring(11, 18);
		key.setStrCardNo(NumberUtils.toInt(strNoStr));

		return cardNoAssignDAO.delete(key) > 0;
	}

	public boolean isCorrectStrNo(MakeCardApp makeCardApp)
			throws BizException {
		boolean isCorrectStrNo = false;

		CardSubClassDef cardSubClass = getCardSubClassDef(makeCardApp);
		String strNo = makeCardApp.getStrNo();
		String sysNo = strNo.substring(0, 3);
		String binNo = strNo.substring(3, 9);
		String cardType = strNo.substring(9, 11);
		String cardStrno = strNo.substring(11, strNo.length() - 1);
		if (strNo.length() != CARD_NO_LENGTH) {
			return false;
		}

		Assert.notEmpty(cardSubClass.getBinNo(), "查到的卡类型定义对象中的卡BIN号码不能为空！");
		Assert.notEmpty(cardSubClass.getCardClass(), "查到的卡类型定义对象中的卡类型号不能为空！");

		if (!StringUtils.equals(sysNo, getSysNo(cardSubClass.getCardIssuer()))
				|| !StringUtils.equals(binNo, cardSubClass.getBinNo())
				|| !StringUtils.equals(cardType, cardSubClass.getCardClass())) {
			return false;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardNo", NumberUtils.toLong(cardStrno));
		params.put("branchCode", cardSubClass.getCardIssuer());
		params.put("binNo", cardSubClass.getBinNo());
		Long result = cardNoAssignDAO.isExistCardNo(params);
		if (result == 0L) {
			isCorrectStrNo = true;
		}
		return isCorrectStrNo;
	}

	public String getStrNo(MakeCardApp makeCardApp)
			throws BizException {
		CardSubClassDef cardSubClass = getCardSubClassDef(makeCardApp);

		Assert.notNull(cardSubClass, "查询到的卡类型对象不能为空");
		Assert.notEmpty(cardSubClass.getBinNo(), "查到的卡类型定义对象中的卡BIN号码不能为空。");

		// 卡号前11位为： 系统号 + 卡BIN号 + 卡类型号
		String strNoPrix = getSysNo(cardSubClass.getCardIssuer()) + cardSubClass.getBinNo() + cardSubClass.getCardClass();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", cardSubClass.getCardIssuer());
		params.put("binNo", cardSubClass.getBinNo());
		Long strNoLong = this.cardNoAssignDAO.findUseCardNo(params);
		if (strNoLong == null) {
			strNoLong = 0L;
		}
		String str = Long.toString(strNoLong + 1L);
		
		String cardNo = strNoPrix + StringUtils.leftPad(str, 7, "0");

		return cardNo + CardUtil.luhnMod10(cardNo);
	}

	/**
	 * 根据制卡申请对象查询卡类型对象
	 */
	private CardSubClassDef getCardSubClassDef(MakeCardApp makeCardApp)
			throws BizException {
		Assert.notNull(makeCardApp, "要添加的制卡申请对象不能为空。");
		Assert.notEmpty(makeCardApp.getMakeId(), "传入的制卡申请对象中制卡登记ID不能为空。");

		// 根据制卡登记ID取得制卡申请对象
		MakeCardReg makeCardReg = (MakeCardReg) makeCardRegDAO.findByPk(makeCardApp.getMakeId());

		Assert.notEmpty(makeCardReg.getCardSubtype(), "查到的制卡申请对象中的卡类型号不能为空。");
		// 根据卡类型号取得卡类型对象
		CardSubClassDef cardSubClass = (CardSubClassDef) cardSubClassDefDAO.findByPk(makeCardReg.getCardSubtype());

		return cardSubClass;
	}

	public boolean cancel(MakeCardApp makeCardApp, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(makeCardApp, "传入的制卡申请对象不能为空。");
		Assert.notNull(sessionUser, "传入的登录用户对象不能为空。");
		Assert.notEmpty(Long.toString(makeCardApp.getId()), "ID不能为空");

		MakeCardApp newMakeCardApp = null;
		try {
			newMakeCardApp = (MakeCardApp) makeCardAppDAO.findByPkWithLock(makeCardApp.getId());
		} catch (Exception e) {
			throw new BizException("锁定制卡申请[" + makeCardApp.getId() + "]对应记录失败，可能已经被锁定！");
		}
		Assert.notNull(newMakeCardApp, "根据ID找不到对应的制卡申请对象。");
		newMakeCardApp.setReason(makeCardApp.getReason());
		newMakeCardApp.setStatus(MakeCardAppState.CANCEL.getValue());
		newMakeCardApp.setCancelUser(sessionUser.getUserId());
		newMakeCardApp.setCancelDate(DateUtil.formatDate("yyyyMMdd"));
		newMakeCardApp.setUpdateBy(sessionUser.getUserId());
		newMakeCardApp.setUpdateTime(new Date());
		// 撤销流程
		try {
			workflowService.deleteFlow(WorkflowConstants.MAKE_CARD_APP, Long.toString(newMakeCardApp.getId()));
		} catch (Exception e) {
			throw new BizException(e);
		}
		// 删除发卡机构卡号分配表里的记录
		this.releaseCardNoAssign(newMakeCardApp);
		// 更新制卡申请簿里的记录
		return makeCardAppDAO.update(newMakeCardApp) > 0;
	}

	public boolean revoke(MakeCardApp makeCardApp, UserInfo sessionUser)
			throws BizException {
		boolean flag = false;
		Assert.notNull(makeCardApp, "传入的制卡申请对象不能为空。");
		Assert.notNull(sessionUser, "传入的登录用户对象不能为空。");
		Assert.notEmpty(Long.toString(makeCardApp.getId()), "ID不能为空");

		MakeCardApp newmakeCardApp = new MakeCardApp();
		try {
			makeCardApp = (MakeCardApp) makeCardAppDAO.findByPkWithLock(makeCardApp.getId());
		} catch (Exception e) {
			throw new BizException("锁定制卡申请[" + makeCardApp.getId() + "]对应记录失败，可能已经被锁定！");
		}
		Assert.notNull(newmakeCardApp, "根据ID找不到对应的制卡申请对象。");
		
		newmakeCardApp.setReason(makeCardApp.getReason());
		newmakeCardApp.setStatus(MakeCardAppState.CREATE_CANCEL.getValue());
		newmakeCardApp.setCancelUser(sessionUser.getUserId());
		newmakeCardApp.setCancelDate(DateUtil.formatDate("yyyyMMdd"));
		newmakeCardApp.setUpdateBy(sessionUser.getUserId());
		newmakeCardApp.setUpdateTime(new Date());
		flag = makeCardAppDAO.update(newmakeCardApp) > 0;

		// 组建档撤销报文到后台
		MsgSender.sendMsg(MsgType.CREATE_CARD_UNDO, makeCardApp.getId(),
				sessionUser.getUserId());

		return flag;
	}

	@Deprecated
	public MakeCardReg addMakeCardReg(MakeCardReg makeCardReg,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(makeCardReg, "要添加的制卡申请对象不能为空");
		Assert.notNull(sessionUser, "当前登录的用户对象不能为空");
		// Assert.notEmpty(makeCardReg.getMakeId(), "制卡登记ID不能为空");
		Assert.notEmpty(makeCardReg.getCardSubtype(), "卡类型不能为空");
		Assert.notEmpty(makeCardReg.getInitUrl(), "初始卡样图案路径不能为空");
		String makeName = StringUtils.trim(makeCardReg.getMakeName());
		Assert.notEmpty(makeName, "卡样名称不能为空");
		Assert.notTrue(makeCardRegDAO.isExsitMakeName(makeName), "该卡样名称已经存在，请更换！");
		makeCardReg.setMakeName(makeName);

		makeCardReg.setBranchCode(sessionUser.getBranchNo());
		makeCardReg.setAppUpload(new Date());
		makeCardReg.setOkFlag(OKFlag.FALSE.getValue());// 未定版
		// 当前卡样图案状态为“待下载”
		makeCardReg.setPicStatus(MakeCardRegState.WAITE_LOAD.getValue());
		makeCardReg.setSmsFlag(SMSFlag.CLOSED.getValue());
		makeCardReg.setUpdateBy(sessionUser.getUserId());
		makeCardReg.setUpdateTime(new Date());

		this.makeCardRegDAO.insert(makeCardReg);
		return makeCardReg;
	}
	
	public MakeCardReg addMakeCardReg(File cardStyleFile, String fileName,
			MakeCardReg makeCardReg, UserInfo sessionUser) throws BizException {
		Assert.notNull(makeCardReg, "要添加的制卡申请对象不能为空");
		Assert.notNull(sessionUser, "当前登录的用户对象不能为空");
		Assert.notEmpty(makeCardReg.getCardSubtype(), "卡类型不能为空");
		String makeName = StringUtils.trim(makeCardReg.getMakeName());
		Assert.notEmpty(makeName, "卡样名称不能为空");
		Assert.notTrue(makeCardRegDAO.isExsitMakeName(makeName), "该卡样名称已经存在，请更换！");
		makeCardReg.setMakeName(makeName);
		
		// 检查该制卡厂商是否已经指定制卡用户
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchNo", makeCardReg.getMakeUser());
		params.put("state", CommonState.NORMAL.getValue());
		List<MakeCardPerson> list = makeCardPersonDAO.findMakeCardPersonList(params);
		Assert.isTrue(CollectionUtils.isNotEmpty(list), "该制卡厂商没有指定制卡人，请联系该制卡厂商。");
		
		// 上传卡样文件到FTP
		boolean flag = this.uploadCardStyleFile(cardStyleFile, fileName);
		Assert.isTrue(flag, "上传卡样文件到FTP服务器失败！");
		
		makeCardReg.setInitUrl(fileName);
		makeCardReg.setAppUpload(new Date());
		makeCardReg.setOkFlag(OKFlag.FALSE.getValue());// 未定版
		// 当前卡样图案状态为“待下载”
		makeCardReg.setPicStatus(MakeCardRegState.WAITE_LOAD.getValue());
		makeCardReg.setSmsFlag(SMSFlag.CLOSED.getValue());
		makeCardReg.setUpdateBy(sessionUser.getUserId());
		makeCardReg.setUpdateTime(new Date());

		this.makeCardRegDAO.insert(makeCardReg);
		
		return makeCardReg;
	}

	public boolean canceCardPic(String makeId, String reason,
			UserInfo sessionUser) throws BizException {
		Assert.notEmpty(makeId, "要取消的制卡ID不能为空");
		Assert.notEmpty(reason, "取消的理由不能为空");
		Assert.notNull(sessionUser, "当前登录的用户对象不能为空");

		MakeCardReg makeCardReg = new MakeCardReg();
		try {
			makeCardReg = (MakeCardReg) makeCardRegDAO.findByPkWithLock(makeId);
		} catch (Exception e) {
			throw new BizException("锁定制卡登记[" + makeId + "]记录失败，可能已经被锁定！");
		}
		Assert.notNull(makeCardReg, "找不到制卡登记[" + makeId + "]");

		makeCardReg.setPicStatus(MakeCardRegState.INVALID.getValue()); // 作废
		makeCardReg.setReason(reason);
		makeCardReg.setUpdateBy(sessionUser.getUserId());
		makeCardReg.setUpdateTime(new Date());

		return makeCardRegDAO.update(makeCardReg) > 0;
	}

	public boolean passCardPic(String makeId, UserInfo sessionUser)
			throws BizException {
		Assert.notEmpty(makeId, "要定版审核通过的制卡ID不能为空");
		Assert.notNull(sessionUser, "当前登录的用户对象不能为空");

		MakeCardReg makeCardReg = new MakeCardReg();
		try {
			makeCardReg = (MakeCardReg) makeCardRegDAO.findByPkWithLock(makeId);
		} catch (Exception e) {
			throw new BizException("锁定制卡登记[" + makeId + "]记录失败，可能已经被锁定！");
		}
		Assert.notNull(makeCardReg, "找不到制卡登记[" + makeId + "]对应的记录！");

		// 修改卡样图案状态为有效
		makeCardReg.setPicStatus(MakeCardRegState.EFFECTIVE.getValue());
		makeCardReg.setFinUrl(makeCardReg.getInitUrl());
		makeCardReg.setOkFlag(OKFlag.TRUE.getValue()); // 定版
		makeCardReg.setOkDate(DateUtil.formatDate("yyyyMMdd"));
		makeCardReg.setUpdateBy(sessionUser.getUserId());
		makeCardReg.setUpdateTime(new Date());

		return makeCardRegDAO.update(makeCardReg) > 0;
	}

	public boolean downloadCardPic(String makeId, UserInfo sessionUser)
			throws BizException {
		Assert.notEmpty(makeId, "要下载卡样的制卡ID不能为空");
		Assert.notNull(sessionUser, "当前登录的用户对象不能为空");

		MakeCardReg makeCardReg = (MakeCardReg) makeCardRegDAO.findByPk(makeId);
		Assert.notNull(makeCardReg, "找不到制卡登记[" + makeId + "]");
		
		boolean flag = true;
		if (StringUtils.equals(MakeCardRegState.WAITE_LOAD.getValue(), makeCardReg.getPicStatus())) {
			makeCardReg.setPicStatus(MakeCardRegState.WAITE_AUDIT.getValue());// 待审核
			makeCardReg.setUpdateBy(sessionUser.getUserId());
			makeCardReg.setUpdateTime(new Date());
			
			flag = makeCardRegDAO.update(makeCardReg) > 0;
		}
		
		this.downloadCardPic(makeCardReg.getInitUrl());

		return flag;
	}

	public void isFileExist(MakeCardReg makeCardReg) throws BizException {
		Assert.notNull(makeCardReg, "查到的制卡登记对象不能为空。");
		File imgFile = new File(makeCardReg.getInitUrl());
		Assert.isTrue(!(!imgFile.exists() || imgFile.isDirectory()), "找不到指定的卡样图案");
	}

	public boolean downloadMakeFile(String id, UserInfo sessionUser)
			throws BizException {
		Assert.notEmpty(id, "要下载卡样的制卡申请ID不能为空");
		Assert.notNull(sessionUser, "当前登录的用户对象不能为空");

		MakeCardApp makeCardApp = (MakeCardApp) makeCardAppDAO.findByPk(NumberUtils.toLong(id));
		Assert.notNull(makeCardApp, "找不到制卡申请ID[" + id + "]对应的记录。");
		
		boolean flag = true;
		if (StringUtils.equals(makeCardApp.getStatus(), MakeCardAppState.CREATE_SUCCESS.getValue())) {
			makeCardApp.setStatus(MakeCardAppState.ALREADY_LOAD.getValue());
			makeCardApp.setUpdateBy(sessionUser.getUserId());
			makeCardApp.setUpdateTime(new Date());
			
			flag = makeCardAppDAO.update(makeCardApp) > 0;
		}
		
		String fileName = makeCardApp.getMakeId() + "_"	+ makeCardApp.getAppId() + ".tar";
		this.downloadMakeCardFile(fileName);
		
		return flag;
	}
	
	/**
	 * 上传卡样文件
	 * 
	 * @param uploadFile 上传文件名
	 * @return
	 * @throws BizException
	 */
	private boolean uploadCardStyleFile(File uploadFile, String fileName) throws BizException {
		String ftpServer = SysparamCache.getInstance().getFtpServerIP();
		String user = SysparamCache.getInstance().getFtpServerUser();
		String pwd = SysparamCache.getInstance().getFtpServerPwd();
		String ftpPath = SysparamCache.getInstance().getCardStyleFtpSavePath();
		String webSavePath = SysparamCache.getInstance().getCardStyleLocalWebSavePath();
		// 先将卡样文件传到本地
		this.saveToWebLocalPath(uploadFile, fileName, webSavePath);
		logger.debug("uploadFile:" + uploadFile.getName() + "===" + uploadFile.length());
		File locFile = null;
		try {
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
		boolean flag = false;
		try {
			flag = ftpCallBackTemplate.processFtpCallBack(uploadCallBack);
		} catch (CommunicationException e) {
			String msg = "FTP上传时发生异常：" + e.getMessage();
			logger.warn(msg);
			throw new BizException(msg);
		}
		logger.debug("uploadFlag:" + flag);
		return flag;
	}
	
	/**
	 * 下载卡样文件
	 * @return
	 * @throws BizException
	 */
	private boolean downloadCardPic(String fileName) throws BizException {
		String ftpServer = SysparamCache.getInstance().getFtpServerIP();
		String user = SysparamCache.getInstance().getFtpServerUser();
		String pwd = SysparamCache.getInstance().getFtpServerPwd();
		String ftpPath = SysparamCache.getInstance().getCardStyleFtpSavePath();
		
		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);

		// 构造下载回调处理类
		CommonDownloadCallBackImpl downloadCallBack = new CommonDownloadCallBackImpl(ftpPath, fileName);

		// 处理下载
		boolean result = false;
		try {
			result = ftpCallBackTemplate.processFtpCallBack(downloadCallBack);
		} catch (CommunicationException e) {
			throw new BizException(e.getMessage());
		}
		
		if (!result) {
			String msg = "找不到卡样文件[" + fileName + "]";
			logger.warn(msg);
			throw new BizException(msg);
		}
		InputStream inputStream = downloadCallBack.getRemoteReferInputStream();
		if (inputStream == null) {
			return false;
		}
		try {
			IOUtil.downloadFile(inputStream, fileName);
		} catch (IOException ex) {
			String msg = "提取卡样文件[" + fileName + "]异常,原因[" + ex.getMessage() + "]";
			logger.warn(msg);
			throw new BizException(msg);
		}
		
		return result;
	}

	/**
	 * 下载制卡文件
	 * @return
	 * @throws BizException
	 */
	private boolean downloadMakeCardFile(String fileName) throws BizException {
		String ftpServer = SysparamCache.getInstance().getFtpServerIP();
		String user = SysparamCache.getInstance().getFtpServerUser();
		String pwd = SysparamCache.getInstance().getFtpServerPwd();
		String ftpPath = SysparamCache.getInstance().getMakeFileFtpSavePath(); 
		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);
		
		// 构造下载回调处理类
		CommonDownloadCallBackImpl downloadCallBack = new CommonDownloadCallBackImpl(ftpPath, fileName);
		
		// 处理下载
		boolean result = false;
		try {
			result = ftpCallBackTemplate.processFtpCallBack(downloadCallBack);
		} catch (CommunicationException e) {
			throw new BizException(e.getMessage());
		}
		
		if (!result) {
			String msg = "找不到制卡文件[" + fileName + "]";
			logger.warn(msg);
			throw new BizException(msg);
		}
		InputStream inputStream = downloadCallBack.getRemoteReferInputStream();
		if (inputStream == null) {
			return false;
		}
		try {
			IOUtil.downloadFile(inputStream, fileName);
		} catch (IOException ex) {
			String msg = "提取制卡文件[" + fileName + "]异常,原因[" + ex.getMessage() + "]";
			logger.warn(msg);
			throw new BizException(msg);
		}
		
		return result;
	}
	
	/**
	 * 将上传的制卡文件保存到本地
	 * 
	 * @param cardStyleFile
	 * @return
	 */
	private void saveToWebLocalPath(File cardStyleFile, String fileName, String webSavePath) throws BizException {
		try {
			IOUtil.uploadFile(cardStyleFile, fileName, webSavePath);
		} catch (IOException e) {
			logger.warn(e.getMessage());
			throw new BizException(e.getMessage());
		}
	}
}
