package gnete.card.service.impl;

import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.RenewCardRegDAO;
import gnete.card.dao.SaleDepositCheckConfigDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.RenewCardReg;
import gnete.card.entity.SaleDepositCheckConfig;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.RenewType;
import gnete.card.entity.type.RoleType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.RenewCardRegService;
import gnete.card.tag.NameTag;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("renewCardRegService")
public class RenewCardRegServiceImpl implements RenewCardRegService {
	
	@Autowired
	private RenewCardRegDAO renewCardRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private SaleDepositCheckConfigDAO saleDepositCheckConfigDAO;
	@Autowired
	private WorkflowService workflowService;
	
	public void addRenewCard(RenewCardReg renewCardReg, UserInfo user)
			throws BizException {
		Assert.notNull(renewCardReg, "添加的换卡对象不能为空！");
		
		String cardId = renewCardReg.getCardId();
		String newCardId = renewCardReg.getNewCardId();
		String roleType = user.getRole().getRoleType();
		String branchCode = user.getBranchNo();

		// 检查卡号是否存在
		CardInfo cardInfo = null;
		CardInfo newCardInfo = null;
		cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
		Assert.notNull(cardInfo, "旧卡号[" + cardId + "]不存在。");
		newCardInfo = (CardInfo) cardInfoDAO.findByPk(newCardId);
		Assert.notNull(newCardInfo, "新卡号[" + newCardId + "]不存在。");
		
		//检查登录机构是否有权限
		CardOprtPrvlgUtil.checkPrvlg(roleType, user.getBranchNo(), cardInfo, "换卡");
		
		Assert.equals(newCardInfo.getAppOrgId(), branchCode, "登录机构不是新卡的领卡机构，请输入正确的新卡号。");
		
		// 判断新旧卡号的卡BIN是否一致
		Assert.equals(cardInfo.getCardBin(), newCardInfo.getCardBin(), "新旧卡号卡BIN不一致，不能换卡。");
		// 判断新旧卡号卡种是否一致
		Assert.equals(cardInfo.getCardClass(), newCardInfo.getCardClass(),
				"旧卡[" + cardInfo.getCardClassName() + "]和新卡["+ newCardInfo.getCardClassName() + "]卡种类不同。");
		
		// 判断新旧卡号的状态是否正确
		boolean stateFlag = false;
		if (CardState.ACTIVE.getValue().equals(cardInfo.getCardStatus())
				|| CardState.LOSSREGISTE.getValue().equals(cardInfo.getCardStatus())) {
			stateFlag = true;
		}
		Assert.isTrue(stateFlag, "旧卡[" + cardId + "]目前不是挂失或正常状态，不能换卡。");
		Assert.notNull(newCardInfo.getAppOrgId(), "新卡领卡机构为空，请选择其他新卡。");
		Assert.equals(newCardInfo.getCardStatus(), CardState.FORSALE.getValue(), "新卡不是领卡待售状态，请选择请他新卡。");

		// 换卡登记
		renewCardReg.setAcctId(cardInfo.getAcctId());
		//renewCardReg.setCertType(cardInfo.getCredType());
		renewCardReg.setUpdateUser(user.getUserId());
		renewCardReg.setUpdateTime(new Date());
		renewCardReg.setCardBranch(cardInfo.getCardIssuer());
		renewCardReg.setAppOrgId(cardInfo.getAppOrgId());
		
		renewCardReg.setBranchCode(user.getBranchNo());
		renewCardReg.setBranchName(NameTag.getBranchName(user.getBranchNo()));
		
		if (isNeedCheck(user)) {
			renewCardReg.setStatus(RegisterState.WAITED.getValue());
			this.renewCardRegDAO.insert(renewCardReg);
			
			startRenewCardFlow(renewCardReg, user);
		} else {
			renewCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
			this.renewCardRegDAO.insert(renewCardReg);
		
			// 发报文到后台，以下由后台处理
			MsgSender.sendMsg(MsgType.RENEW_CARD, renewCardReg.getRenewCardId(), user.getUserId());
		}
	}
	
	private void startRenewCardFlow(RenewCardReg renewCardReg, UserInfo user) throws BizException {
		try {
			this.workflowService.startFlow(renewCardReg, 
					WorkflowConstants.RENEW_CARD_ADAPTER, Long.toString(renewCardReg.getRenewCardId()), user);
		} catch (Exception e) {
			throw new BizException("启动换卡审核流程时，发生异常。");
		}
	}

	/*private CardInfo makeRenewCardReg(String cardId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardId", cardId);
		CardInfo cardInfo = (CardInfo) this.renewCardRegDAO
				.makeRenewCardReg(params);
		return cardInfo;
	}*/

	/*private List findCurrentDayTrans(RenewCardReg renewCardReg) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (renewCardReg != null){
			params.put("cardId", renewCardReg.getCardId());
			params.put("sett_Date", findSysparm("010"));
		}
		
		List list = this.renewCardRegDAO.findCurrentDayTrans(params);
		return list;
	}*/

	/*private String findSysparm(String paraCode) throws BizException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("paraCode", paraCode);
		
		List list = this.renewCardRegDAO.findSysparm(params);
		
		Iterator it = list.iterator();
		Sysparm sys = new Sysparm();
		String sysValue = null;
		
		if(it.hasNext()){
			sys = (Sysparm)it.next();
			sysValue = sys.getParaValue();
		}
		
		Assert.notNull(sysValue, "查找系统参数表中当前工作日失败！");
		
		return sysValue;
	}*/

	/*
	 * 判断新卡号是否属于登录机构的领卡待售卡
	 */
	/*private boolean isNewCardIdAvail(String cardId, UserInfo user) {
		
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
		if(!cardInfo.getCardStatus().equals(CardState.FORSALE.getValue())){
			return false;
		}
		
		String branch = null;
		if(user.getRole().getRoleType().equals(RoleType.CARD.getValue())){
			branch = cardInfo.getCardIssuer();
			return branch.equals(user.getBranchNo());
		}
		else if(user.getRole().getRoleType().equals(RoleType.CARD_SELL.getValue())){
			branch = cardInfo.getAppOrgId();	
			return branch.equals(user.getBranchNo());
		}
		else if(user.getRole().getRoleType().equals(RoleType.CARD_DEPT.getValue())){
			branch = cardInfo.getAppOrgId();	
			return branch.equals(user.getDeptId());
		}
		
		return false;
	}*/

	/*private List findCardInfoStatus(RenewCardReg renewCardReg) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		String cardStatus[] = {"10","22"};
		if(renewCardReg != null){
			params.put("cardId", renewCardReg.getCardId());
			params.put("cardStatus", cardStatus); // 10-正常,22-挂失
		}
		
		List list = this.renewCardRegDAO.findCardInfo(params);
		
		return list;
	}*/

/*	private List findCustName(RenewCardReg renewCardReg) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (renewCardReg != null){
			params.put("cardId", renewCardReg.getCardId());
			params.put("custName", renewCardReg.getCustName());
			params.put("credNo", renewCardReg.getCertNo());
		}
		List list = this.renewCardRegDAO.findCustName(params);
		return list;
	}*/

	public boolean delete(Long renewCardId) throws BizException {
		Assert.notNull(renewCardId, "删除的换卡对象不能为空");
		int count = this.renewCardRegDAO.delete(renewCardId);
		return count > 0;
	}

	public boolean modifyRenewCard(RenewCardReg renewCardReg,
			String updateUserId) throws BizException {
		Assert.notNull(renewCardReg, "更新的换卡对象不能为空");
		
//		renewCardReg.setStatus(CardState.CLOSEACC.getValue());
		renewCardReg.setUpdateUser(updateUserId);
		renewCardReg.setUpdateTime(new Date());
		int count = this.renewCardRegDAO.update(renewCardReg);
		
		return count > 0;
	}

	public void addUpgradeRecord(RenewCardReg renewCardReg, UserInfo user) throws BizException {
		Assert.notNull(renewCardReg, "添加的换卡对象不能为空！");
		Assert.notEmpty(renewCardReg.getCardId(), "要换卡的卡号不能为空");
		Assert.notEmpty(renewCardReg.getNewCardId(), "新卡号不能为空");
		
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(renewCardReg.getCardId());
		Assert.notNull(cardInfo, "旧卡[" + renewCardReg.getCardId() + "]的记录不存在");
		AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
		Assert.notNull(acctInfo, "旧卡[" + renewCardReg.getCardId() + "]的账户不存在");
		
		renewCardReg.setAcctId(acctInfo.getAcctId());
		
		renewCardReg.setUpdateUser(user.getUserId());
		renewCardReg.setUpdateTime(new Date());
		renewCardReg.setRenewType(RenewType.UPGRADECARD.getValue());
		renewCardReg.setCardBranch(cardInfo.getCardIssuer());
		renewCardReg.setAppOrgId(cardInfo.getAppOrgId());
		
		renewCardReg.setBranchCode(user.getBranchNo());
		renewCardReg.setBranchName(NameTag.getBranchName(user.getBranchNo()));
		
		renewCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
		this.renewCardRegDAO.insert(renewCardReg);
	
		// 发报文到后台请求注销掉原卡，激活新卡 
		MsgSender.sendMsg(MsgType.RENEW_CARD, renewCardReg.getRenewCardId(), user.getUserId());
	}
	
	/**
	 * 判断卡延期是否需要审核
	 * @param cardInfo
	 * @return
	 * @throws BizException
	 */
	private boolean isNeedCheck(UserInfo user) throws BizException {
		boolean isCheckForSell = false;
		
		String roleType = user.getRole().getRoleType();

		// 发卡机构的话需要检查是否配置了审核权限
		if (RoleType.CARD.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			SaleDepositCheckConfig config = (SaleDepositCheckConfig) this.saleDepositCheckConfigDAO.findByPk(user.getBranchNo());
			Assert.notNull(config, "发卡机构[" + user.getBranchNo() + "]没有配置审核权限");
			
			isCheckForSell = StringUtils.equals(Symbol.YES, config.getRenewCardCheck());
		}
		
		return isCheckForSell;
	}

}
