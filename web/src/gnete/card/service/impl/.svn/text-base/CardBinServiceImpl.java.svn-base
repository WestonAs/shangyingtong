package gnete.card.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardBinDAO;
import gnete.card.dao.CardBinPreMgrDAO;
import gnete.card.dao.CardBinPreRegDAO;
import gnete.card.dao.CardBinRegDAO;
import gnete.card.dao.FenZhiCardBinMgrDAO;
import gnete.card.dao.FenzhiCardBinRegDAO;
import gnete.card.dao.InsServiceAuthorityDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardBin;
import gnete.card.entity.CardBinPreMgr;
import gnete.card.entity.CardBinPreReg;
import gnete.card.entity.CardBinReg;
import gnete.card.entity.FenZhiCardBinMgr;
import gnete.card.entity.FenzhiCardBinReg;
import gnete.card.entity.InsServiceAuthority;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CardBinRegState;
import gnete.card.entity.state.CardBinState;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.state.CheckState;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.state.FenzhiCardBinMgrState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.RoleType;
import gnete.card.service.CardBinService;
import gnete.card.util.TradeCardTypeMap;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

@Service("cardBinService")
public class CardBinServiceImpl implements CardBinService {
	
	@Autowired
	private CardBinPreRegDAO cardBinPreRegDAO;
	@Autowired
	private CardBinPreMgrDAO cardBinPreMgrDAO;
	@Autowired
	private FenZhiCardBinMgrDAO fenZhiCardBinMgrDAO;
	@Autowired
	private FenzhiCardBinRegDAO fenzhiCardBinRegDAO;
	@Autowired
	private CardBinRegDAO cardBinRegDAO;
	@Autowired
	private CardBinDAO cardBinDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private InsServiceAuthorityDAO insServiceAuthorityDAO;
	@Autowired
	private WorkflowService workflowService;

	public void addCardBinPrexReg(CardBinPreReg cardBinPreReg, UserInfo user)
			throws BizException {
		Assert.notNull(cardBinPreReg, "要添加的卡BIN前三位登记对象不能为空");
		Assert.notEmpty(cardBinPreReg.getCardBinPrex(), "卡BIN前三位号不能为空");
		Assert.notEmpty(cardBinPreReg.getBranchCode(), "一级分支机构号不能为空");
		Assert.notNull(user, "登录用户的信息不能为空");
		
		cardBinPreReg.setStatus(CheckState.WAITED.getValue());
		cardBinPreReg.setUpdateBy(user.getUserId());
		cardBinPreReg.setUpdateTime(new Date());
		
		Assert.notTrue(this.cardBinPreMgrDAO.isExist(cardBinPreReg.getCardBinPrex()), "卡BIN前三位[" + cardBinPreReg.getCardBinPrex() + "]已经分配下去");
		
		// 1.在登记薄中插入数据
		this.cardBinPreRegDAO.insert(cardBinPreReg);
		
		// 2.启动审核流程
		try {
			this.workflowService.startFlow(cardBinPreReg, WorkflowConstants.CARD_BIN_PREX_REG_ADAPTER, cardBinPreReg.getId(), user);
		} catch (Exception e) {
			throw new BizException("启动卡BIN前三位申请审核流程失败，原因：" + e.getMessage());
		}
	}

	public CardBinPreReg findCardBinPrexDetail(String pk) {
		return (CardBinPreReg) this.cardBinPreRegDAO.findByPk(pk);
	}

	public Paginater findCardBinPrexPage(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.cardBinPreRegDAO.findCardBinPrexRegPage(params, pageNumber, pageSize);
	}

	public boolean isExistCardBinPrex(String cardBinPrex) throws BizException {
		return this.cardBinPreMgrDAO.isExist(cardBinPrex);
	}
	
	public void checkCardBinPrexReg(CardBinPreReg cardBinPreReg, String userId)
			throws BizException {
		Assert.notNull(cardBinPreReg, "要审核的卡BIN前三位申请登记对象不能为空");
		Assert.notEmpty(userId, "登录用户的用户ID不能为空");
		String cardBinPrex = cardBinPreReg.getCardBinPrex();
		Assert.notEmpty(cardBinPrex, "卡BIN号前三位不能为空");
		
		//1. 首先更改卡BIN前三位申请登记薄的状态为审核通过，同时在卡BIN前三位管理表中插入记录
		cardBinPreReg.setStatus(CheckState.PASSED.getValue());
		cardBinPreReg.setUpdateBy(userId);
		cardBinPreReg.setUpdateTime(new Date());
		
		this.cardBinPreRegDAO.update(cardBinPreReg);
		
		//2. 在卡BIN前三位管理表中插入记录
		Assert.notTrue(this.cardBinPreMgrDAO.isExist(cardBinPreReg.getCardBinPrex()), "卡BIN前三位[" + cardBinPrex + "]已经分配下去");
		CardBinPreMgr cardBinPreMgr = new CardBinPreMgr();
		this.copyCardBinRrexRegToMgr(cardBinPreReg, cardBinPreMgr);
		this.cardBinPreMgrDAO.insert(cardBinPreMgr);
		
		//3. 在分支机构卡BIN分配表给一级分支机构分配卡BIN号 
		List<FenZhiCardBinMgr> list = new ArrayList<FenZhiCardBinMgr>();
		for (int i = 0; i < 1000; i++) {
			FenZhiCardBinMgr fenZhi = new FenZhiCardBinMgr();
			String cardBin = cardBinPrex + StringUtils.leftPad(String.valueOf(i), 3, "0");
			fenZhi.setCardBin(cardBin);
			fenZhi.setCardBinPrex(cardBinPrex);
			fenZhi.setCurrentBranch(cardBinPreReg.getBranchCode());
			fenZhi.setLastBranch(cardBinPreReg.getBranchCode());
			fenZhi.setUseFlag(Symbol.NO);
			fenZhi.setStatus(FenzhiCardBinMgrState.UN_ASSIGN.getValue());
			fenZhi.setUpdateBy(userId);
			fenZhi.setUpdateTime(new Date());
			
			list.add(fenZhi);
		}
		this.fenZhiCardBinMgrDAO.insertBatch(list);
	}
	
	/**
	 * 将登记薄的信息复制到管理表中
	 * @param cardBinPreReg
	 * @param cardBinPreMgr
	 */
	private void copyCardBinRrexRegToMgr(CardBinPreReg cardBinPreReg, CardBinPreMgr cardBinPreMgr) {
		cardBinPreMgr.setCardBinPrex(cardBinPreReg.getCardBinPrex());
		cardBinPreMgr.setBranchCode(cardBinPreReg.getBranchCode());
		cardBinPreMgr.setStatus(CommonState.NORMAL.getValue());
		cardBinPreMgr.setUpdateBy(cardBinPreReg.getUpdateBy());
		cardBinPreMgr.setUpdateTime(cardBinPreReg.getUpdateTime());
	}
	
	public Paginater findFenzhiCardBinRegPage(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.fenzhiCardBinRegDAO.findFenzhiCardBinRegPage(params, pageNumber, pageSize);
	}

	public Paginater findFenzhiCardBinPage(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.fenZhiCardBinMgrDAO.findFenzhiCardBinPage(params, pageNumber, pageSize);
	}

	public FenzhiCardBinReg findFenzhiCardBinRegDetail(String pk) {
		return (FenzhiCardBinReg) this.fenzhiCardBinRegDAO.findByPk(pk);
	}

	public void addFenzhiCardBinReg(FenzhiCardBinReg fenzhiCardBinReg,
			UserInfo user) throws BizException {
		Assert.notNull(fenzhiCardBinReg, "分支机构卡BIN申请对象不能为空");
		Assert.notEmpty(fenzhiCardBinReg.getStrBinNo(), "起始卡BIN不能为空");
		Assert.notNull(fenzhiCardBinReg.getBinCount(), "卡BIN数量不能为空");
		Assert.notEmpty(fenzhiCardBinReg.getAppBranch(), "申请机构不能为空");
		
		if (String.valueOf(NumberUtils.toInt(fenzhiCardBinReg.getStrBinNo()) + fenzhiCardBinReg.getBinCount()).length() > 6) {
			throw new BizException("申请的卡BIN数量过大，请重新申请");
		}
		
		List<FenZhiCardBinMgr> list = new ArrayList<FenZhiCardBinMgr>();
		// 1.检查申请的卡BIN是否属于登录的机构及状态是否为“可分配”, 如果是的话则更改分支机构卡管理表中的对应卡BIN的状态为“分配中”
		for (int i = 0; i < fenzhiCardBinReg.getBinCount(); i++) {
			
			String binNo = String.valueOf(NumberUtils.toInt(fenzhiCardBinReg.getStrBinNo()) + i);
			binNo = StringUtils.leftPad(binNo, 6, "0");
			FenZhiCardBinMgr mgr = (FenZhiCardBinMgr) this.fenZhiCardBinMgrDAO.findByPk(binNo);
			
			Assert.notNull(mgr, "在运营机构卡BIN分配表找不到卡BIN[" + binNo + "]的记录");
			Assert.equals(mgr.getCurrentBranch(), user.getBranchNo(), "卡BIN[" + binNo + "]不属于当前登录的机构，不能分配该卡BIN");
			Assert.equals(mgr.getStatus(), FenzhiCardBinMgrState.UN_ASSIGN.getValue(), "卡BIN[" + binNo + "]不是“可分配状态”，不能分配该卡BIN");
			
			mgr.setStatus(FenzhiCardBinMgrState.ASSIGNING.getValue());
			mgr.setUpdateBy(user.getUserId());
			mgr.setUpdateTime(new Date());
			
			list.add(mgr);
		}
		this.fenZhiCardBinMgrDAO.updateBatch(list);
		
		// 2.在分支机构卡BIN申请登记薄中插入从页面录入的数据。状态为“待审核”
		fenzhiCardBinReg.setBranchCode(user.getBranchNo());
		fenzhiCardBinReg.setStatus(CheckState.WAITED.getValue());
		fenzhiCardBinReg.setUpdateBy(user.getUserId());
		fenzhiCardBinReg.setUpdateTime(new Date());
		
		this.fenzhiCardBinRegDAO.insert(fenzhiCardBinReg);
		
		// 3.启动分支机构卡BIN分配审核流程
		try {
			this.workflowService.startFlow(fenzhiCardBinReg, WorkflowConstants.CARD_BIN_FENZHI_REG_ADAPTER, fenzhiCardBinReg.getRegId(), user);
		} catch (Exception e) {
			throw new BizException("启动分支机构卡BIN分配审核流程失败，原因：" + e.getMessage());
		}
	}

	public void checkFenzhiCardBinReg(FenzhiCardBinReg fenzhiCardBinReg,
			String userId) throws BizException {
		Assert.notNull(fenzhiCardBinReg, "要审核的分支机构卡BIN分配申请登记对象不能为空");
		Assert.notEmpty(fenzhiCardBinReg.getStrBinNo(), "起始卡BIN不能为空");
		Assert.notNull(fenzhiCardBinReg.getBinCount(), "卡BIN数量不能为空");
		Assert.notEmpty(fenzhiCardBinReg.getAppBranch(), "申请机构不能为空");
		Assert.notEmpty(userId, "登录用户的用户ID不能为空");
		
		// 1.将分支机构卡BIN分配登记簿中对应的记录改为“审核通过”
		fenzhiCardBinReg.setStatus(CheckState.PASSED.getValue());
		fenzhiCardBinReg.setUpdateBy(userId);
		fenzhiCardBinReg.setUpdateTime(new Date());
		
		this.fenzhiCardBinRegDAO.update(fenzhiCardBinReg);
		
		// 2.更改分支机构卡BIN管理表中的对应卡BIN的状态为“可分配”
		List<FenZhiCardBinMgr> list = new ArrayList<FenZhiCardBinMgr>();
		for (int i = 0; i < fenzhiCardBinReg.getBinCount(); i++) {
			String cardBin = String.valueOf(NumberUtils.toInt(fenzhiCardBinReg.getStrBinNo()) + i);
			cardBin = StringUtils.leftPad(cardBin, 6, "0");
			
			FenZhiCardBinMgr mgr = (FenZhiCardBinMgr) this.fenZhiCardBinMgrDAO.findByPkWithLock(cardBin);
			Assert.notNull(mgr, "在运营机构卡BIN分配表找不到卡BIN[" + cardBin + "]的记录");
			
			mgr.setStatus(FenzhiCardBinMgrState.UN_ASSIGN.getValue());
			mgr.setCurrentBranch(fenzhiCardBinReg.getAppBranch()); // 当前所在机构为申请机构
			mgr.setLastBranch(fenzhiCardBinReg.getBranchCode()); // 上次所在机构
			mgr.setUpdateBy(userId);
			mgr.setUpdateTime(new Date());
			
			list.add(mgr);
		}
		this.fenZhiCardBinMgrDAO.updateBatch(list);
	}

	public Paginater findCardBinRegPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.cardBinRegDAO.findCardBinReg(params, pageNumber, pageSize);
	}
	
	public Paginater findCardBinPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.cardBinDAO.findCardBin(params, pageNumber, pageSize);
	}
	
	public void addCardBin(CardBinReg cardBinReg, UserInfo user) throws BizException {
		Assert.notNull(cardBinReg, "卡BIN申请对象不能为空");
		Assert.notNull(user, "登录用户信息不能为空");
		Assert.notEmpty(cardBinReg.getCardIssuer(), "发卡机构编号不能为空");
		Assert.notEmpty(cardBinReg.getBinNo(), "卡BIN号码不能为空");
		Assert.notEmpty(cardBinReg.getCardType(), "卡种不能为空");
		
		BranchInfo cardBranch = (BranchInfo) branchInfoDAO.findByPk(cardBinReg.getCardIssuer());
		Assert.notNull(cardBranch, "机构信息表中不存在该发卡机构的信息");
		Assert.notEmpty(cardBranch.getCurCode(), "该发卡机构的货币代码为空");
		
		Assert.notTrue(this.isExistCardBin(cardBinReg.getBinNo()), "该卡BIN已被使用");
		String roleType = user.getRole().getRoleType();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("insId", cardBinReg.getCardIssuer());
		params.put("serviceId", TradeCardTypeMap.getInsServiceTradeType(cardBinReg.getCardType()));
		params.put("status", CardTypeState.NORMAL.getValue());
		
		String cardTypeName = (CardType.ALL.get(cardBinReg.getCardType()) == null ? "" 
				: CardType.valueOf(cardBinReg.getCardType()).getName());
		
		// add Start 2014年3月13日11:27:37 只能定义一个为虚拟账户的卡BIN
		if (CardType.VIRTUAL.getValue().equals(cardBinReg.getCardType())) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("cardType", cardBinReg.getCardType());
			
			Assert.isEmpty(cardBinDAO.findCardBin(map), "只能定义一个为虚拟账户的卡BIN");
		}
		// end
		
		List<InsServiceAuthority> authorityList = this.insServiceAuthorityDAO.getInsServiceAuthority(params);
		if (CollectionUtils.isEmpty(authorityList)) {
			throw new BizException("发卡机构[" + cardBinReg.getCardIssuer() + "]没有开通[" + cardTypeName + "]功能，请联系运营分支机构管理员。");
		}
		
		// 将分支机构卡BIN管理表中的记录修改
		FenZhiCardBinMgr mgr = (FenZhiCardBinMgr) this.fenZhiCardBinMgrDAO.findByPkWithLock(cardBinReg.getBinNo());
		Assert.notNull(mgr, "分支卡BIN管理表中没有卡BIN[" + cardBinReg.getBinNo() + "]的记录");
		
		String fenzhiBranchCode = "";
		// 发卡机构申请卡BIN时
		if (RoleType.CARD.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			BranchInfo branchInfo = this.branchInfoDAO.findBranchInfo(user.getBranchNo());
			fenzhiBranchCode = branchInfo.getParent();
		}
		// 运营机构申请卡BIN时
		else if (RoleType.FENZHI.getValue().equals(roleType)) {
			fenzhiBranchCode = user.getBranchNo();
		}
		Assert.equals(mgr.getCurrentBranch(), fenzhiBranchCode, "该卡BIN现在不属于运营机构[" + fenzhiBranchCode + "]，不能给发卡机构分配");
		
		mgr.setUseFlag(Symbol.YES);
		mgr.setStatus(FenzhiCardBinMgrState.ASSIGNING.getValue());
		mgr.setUpdateBy(user.getUserId());
		mgr.setUpdateTime(new Date());
		
		this.fenZhiCardBinMgrDAO.update(mgr);
		
		// 在卡BIN登记簿中插入记录
		cardBinReg.setCurrCode(cardBranch.getCurCode());
		cardBinReg.setUpdateTime(new Date());
		cardBinReg.setUpdateBy(user.getUserId());
		cardBinReg.setStatus(CardBinRegState.WAITED.getValue());// 待审核状态
		this.cardBinRegDAO.insert(cardBinReg);

		CardBin cardBin = new CardBin();
		cardBin.setBinNo(cardBinReg.getBinNo());
		cardBin.setBinName(cardBinReg.getBinName());
		cardBin.setCardType(cardBinReg.getCardType());
		cardBin.setCurrCode(cardBinReg.getCurrCode());
		cardBin.setUpdateTime(cardBinReg.getUpdateTime());
		cardBin.setUpdateBy(user.getUserId());
		cardBin.setStatus(CardBinState.WAITED.getValue());// 待审核状态
		cardBin.setCardIssuer(cardBinReg.getCardIssuer());

		this.cardBinDAO.insert(cardBin);
		
		// 启动单个流程
		try {
			this.workflowService.startFlow(cardBinReg, "cardBinAdapter", Long.toString(cardBinReg.getId()), user);
		} catch (Exception e) {
			throw new BizException("启动卡BIN审核流程时发生异常，原因：" + e.getMessage());
		}
	}
	
	public String getBinNo(UserInfo user) {
		String roleType = user.getRole().getRoleType();
		String loginBranch = user.getBranchNo();
		String fenzhiBranchCode = "";
		// 发卡机构申请卡BIN时
		if (RoleType.CARD.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			BranchInfo branchInfo = this.branchInfoDAO.findBranchInfo(loginBranch);
			fenzhiBranchCode = branchInfo.getParent();
		}
		// 运营机构申请卡BIN时
		else if (RoleType.FENZHI.getValue().equals(roleType)) {
			fenzhiBranchCode = loginBranch;
		}
		
		String binNo = "";
		if (StringUtils.isNotEmpty(fenzhiBranchCode)) {
			int i = 0;
			while (true) {
				binNo = this.fenZhiCardBinMgrDAO.findMinAbleCardBin(fenzhiBranchCode);
				// 检查卡bin是否已经存在
				boolean isExist = cardBinDAO.isExist(binNo);
				// 如果不存在则跳出while循环
				if (!isExist) {
					break;
				}
				
				i++;
				// 防止死循环
				if (i >= 100) {
					break;
				}
			}
		}
		
		return binNo;
	}
	
	public boolean isExistCardBin(String binNo) throws BizException {
		Assert.notNull(binNo, "卡BIN号码不能为空");
		return this.cardBinDAO.findByPk(binNo) != null;
	}
	
	public CardBinReg findCardBinRegDetail(Long pk) {
		return (CardBinReg) this.cardBinRegDAO.findByPk(pk);
	}
}
