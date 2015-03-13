package gnete.card.service.impl;

import flink.file.CancelCardImporter;
import flink.util.CommonHelper;
import gnete.card.dao.CancelCardRegDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.SaleDepositCheckConfigDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.entity.CancelCardReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.SaleDepositCheckConfig;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.SubAcctBalKey;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SubacctType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.CancelCardService;
import gnete.card.util.BranchUtil;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cancelCardService")
public class CancelCardServiceImpl implements CancelCardService {

	@Autowired
	private CancelCardRegDAO cancelCardRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	@Autowired
	private SaleDepositCheckConfigDAO saleDepositCheckConfigDAO;
	@Autowired
	private WorkflowService workflowService;
	
	public void addCancelCard(CancelCardReg cancelCardReg,
			UserInfo userInfo) throws BizException {
		Assert.notNull(cancelCardReg, "添加的退卡销户对象不能为空！");
		Assert.notEmpty(cancelCardReg.getCardId(), "要退卡销户的卡号不能为空");

		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cancelCardReg.getCardId());
		Assert.notNull(cardInfo, "卡号不存在");
		Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "正常(已激活)的卡才能退卡");
		Assert.notEmpty(cardInfo.getAcctId(), "账号不能为空");
		
		//检查登录机构是否有权限
		CardOprtPrvlgUtil.checkPrvlg(userInfo.getRole().getRoleType(), userInfo.getBranchNo(), cardInfo, "退卡销户");
		
//		// 退卡销户现在只返还 充值资金账户里的钱
//		SubAcctBalKey key = new SubAcctBalKey();
//		
//		key.setAcctId(cardInfo.getAcctId());
//		key.setSubacctType(SubacctType.DEPOSIT.getValue());
//		
//		SubAcctBal depositAcctBal = (SubAcctBal) this.subAcctBalDAO.findByPk(key);
//		Assert.notNull(depositAcctBal, "卡号[" + cancelCardReg.getCardId() + "]的资金账户不存在");
//		
//		cancelCardReg.setReturnAmt(depositAcctBal.getAvlbBal());
		cancelCardReg.setReturnAmt(countAvlbBal(cardInfo));
		
		cancelCardReg.setBranchCode(userInfo.getBranchNo());

		/*// 退卡，检查该卡状态是否为"正常"
		if(CancelCardFlagType.CANCEL_CARD.getValue().equals(cancelCardReg.getFlag())){
			Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "正常(已激活)的卡才能退卡");
		}
		
		// 销户，(计算返还金额 = 充值 + 返利 + 赠券资金)
		if(CancelCardFlagType.CANCEL_ACCT.getValue().equals(cancelCardReg.getFlag())){
			Assert.notNull(cancelCardReg.getAcctId(), "账号不能为空");
			BigDecimal returnAmt = new BigDecimal("0");
			SubAcctBalKey key = new SubAcctBalKey();
			key.setAcctId(cancelCardReg.getAcctId());
			
			key.setSubacctType(SubacctType.DEPOSIT.getValue());
			SubAcctBal subAcctBalDeposit = (SubAcctBal) this.subAcctBalDAO.findByPk(key);
			if(subAcctBalDeposit!= null){
				returnAmt.add(subAcctBalDeposit.getAvlbBal());
			}
			
			key.setSubacctType(SubacctType.REBATE.getValue());
			SubAcctBal subAcctBalRebate = (SubAcctBal) this.subAcctBalDAO.findByPk(key);
			if(subAcctBalRebate!=null){
				returnAmt.add(subAcctBalRebate.getAvlbBal());
			}
			
			key.setSubacctType(SubacctType.COUPON.getValue());
			SubAcctBal subAcctBalCoupon = (SubAcctBal) this.subAcctBalDAO.findByPk(key);
			if(subAcctBalCoupon!=null){
				returnAmt.add(subAcctBalCoupon.getAvlbBal());
			}
			cancelCardReg.setReturnAmt(returnAmt);
		}*/
		
		cancelCardReg.setUpdateTime(new Date());
		cancelCardReg.setUpdateUser(userInfo.getUserId());
		
		boolean isNeedCheck = this.isCheckForCancelCard(userInfo);
		
		// 需要审核的话则审核后发送报文
		if (isNeedCheck) {
			cancelCardReg.setStatus(RegisterState.WAITED.getValue());
			this.cancelCardRegDAO.insert(cancelCardReg);
			
			try {
				this.workflowService.startFlow(cancelCardReg, WorkflowConstants.CANCEL_CARD_ADAPTER, 
						Long.toString(cancelCardReg.getCancelCardId()), userInfo);
			} catch (Exception e) {
				throw new BizException("启动退卡销户流程时发生异常。原因：" + e.getMessage());
			}
		} else {
			cancelCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
			this.cancelCardRegDAO.insert(cancelCardReg);
			
			//登记成功后，组退卡销户报文
			MsgSender.sendMsg(MsgType.CANCEL_CARD, cancelCardReg.getCancelCardId(), cancelCardReg.getUpdateUser());
		}

		/*if(this.cancelCardRegDAO.insert(cancelCardReg) != null){
			//登记成功后，组退卡销户报文
			MsgSender.sendMsg(MsgType.CANCEL_CARD, cancelCardReg.getCancelCardId(), cancelCardReg.getUpdateUser());
			return true;
		}
		else{
			return false;
		}*/
		
	}
	
    public List<String> addFileCancelCardReg(File upload, String uploadFileName, UserInfo user) throws BizException {
		
		// 检查记录是否合法，合法的记录插入登记簿中，不合法的放在uninsertCardDeferRegList中
		List<String> errorCardList = new ArrayList<String>();
		try{
			CancelCardImporter cancelCardImporter = new CancelCardImporter();
			List<CancelCardReg> cancelCardRegList = cancelCardImporter.getFileImporterList(upload, uploadFileName);
			
			boolean isNeedCheck = this.isCheckForCancelCard(user);
			for(CancelCardReg cancelCardReg : cancelCardRegList){
				String prex = "第[" + (cancelCardRegList.indexOf(cancelCardReg) + 1)+ "]条记录";
				
				// 卡号不能为空
				if (StringUtils.isEmpty(cancelCardReg.getCardId())) {
					errorCardList.add(prex + "卡号为空");
					continue;
				}
				
				CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cancelCardReg.getCardId());
				
				// 检查卡号是否存在
				if (cardInfo == null) {
					errorCardList.add(prex + "卡号[" + cancelCardReg.getCardId() + "]不存在");
					continue;
				}else{
					if(!CardState.ACTIVE.getValue().equals(cardInfo.getCardStatus())){
						errorCardList.add(prex + "卡号[" + cancelCardReg.getCardId() + "]正常(已激活)的卡才能退卡");
						continue;
					}
					if(CommonHelper.isEmpty(cardInfo.getAcctId())){
						errorCardList.add(prex + "卡号[" + cancelCardReg.getCardId() + "]账号不能为空");
						continue;
					}
					//检查登录机构是否有权限
					String msg = checkPrvlg(user.getRole().getRoleType(), user.getBranchNo(), cardInfo, "退卡销户");
					if(CommonHelper.isNotEmpty(msg)){
						errorCardList.add(prex + msg);
						continue;
					}
				}
				cancelCardReg.setReturnAmt(countAvlbBal(cardInfo));
				cancelCardReg.setBranchCode(user.getBranchNo());
				cancelCardReg.setUpdateTime(new Date());
				cancelCardReg.setUpdateUser(user.getUserId());
				
				// 需要审核的话则审核后发送报文
				if (isNeedCheck) {
					cancelCardReg.setStatus(RegisterState.WAITED.getValue());
					this.cancelCardRegDAO.insert(cancelCardReg);
					
					try {
						this.workflowService.startFlow(cancelCardReg, WorkflowConstants.CANCEL_CARD_ADAPTER,Long.toString(cancelCardReg.getCancelCardId()), user);
					} catch (Exception e) {
						throw new BizException("启动退卡销户流程时发生异常。原因：" + e.getMessage());
					}
				} else {
					cancelCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
					this.cancelCardRegDAO.insert(cancelCardReg);
					
					//登记成功后，组退卡销户报文
					MsgSender.sendMsg(MsgType.CANCEL_CARD, cancelCardReg.getCancelCardId(), cancelCardReg.getUpdateUser());
				}
			}
		}catch (Exception e) {
			throw new BizException(e);
		}
		return errorCardList;
	}

	public boolean delete(Long cancelCardRegId) throws BizException {
		Assert.notNull(cancelCardRegId, "删除的退卡销户对象不能为空");
		int count = this.cancelCardRegDAO.delete(cancelCardRegId);
		return count > 0;
	}
	
	public BigDecimal countAvlbBal(CardInfo cardInfo) throws BizException{
		BigDecimal amt = new BigDecimal("0");
		// 退卡销户现在只返还 充值资金和返利资金账户里的钱
		SubAcctBalKey depositkey = new SubAcctBalKey();
		depositkey.setAcctId(cardInfo.getAcctId());
		depositkey.setSubacctType(SubacctType.DEPOSIT.getValue());
		SubAcctBal depositAcctBal = (SubAcctBal) this.subAcctBalDAO.findByPk(depositkey);
		Assert.notNull(depositAcctBal, "卡号[" + cardInfo.getCardId() + "]的充值资金账户不存在");
		if(depositAcctBal.getAvlbBal() != null){
			amt = amt.add(depositAcctBal.getAvlbBal());
		}
		
		SubAcctBalKey rebatekey = new SubAcctBalKey();
		rebatekey.setAcctId(cardInfo.getAcctId());
		rebatekey.setSubacctType(SubacctType.REBATE.getValue());
		SubAcctBal rebateAcctBal = (SubAcctBal) this.subAcctBalDAO.findByPk(rebatekey);
		Assert.notNull(rebateAcctBal, "卡号[" + cardInfo.getCardId() + "]的返利资金账户不存在");
		if(rebateAcctBal.getAvlbBal() != null){
			amt = amt.add(rebateAcctBal.getAvlbBal());
		}
		return amt;
	}
	
	public String checkPrvlg(String loginRoleType, String loginBranchCode, CardInfo cardInfo, String oprtName) throws BizException {
		String msg = null;
		if (RoleType.CARD.getValue().equals(loginRoleType) || RoleType.CARD_DEPT.getValue().equals(loginRoleType)) {
			if(!BranchUtil.isBelong2SameTopBranch(loginBranchCode, cardInfo.getCardIssuer())){
			    msg = String.format("登录机构与卡[%s]的发行机构不属于同一顶级机构，不能做[%s]处理。", cardInfo.getCardId(),oprtName);
			}
		} else if (RoleType.CARD_SELL.getValue().equals(loginRoleType)) {
			if(!loginBranchCode.equals(cardInfo.getAppOrgId())){
			    msg = String.format("该售卡代理不是卡[%s]的领入机构，不能做[%s]处理", cardInfo.getCardId(), oprtName);
			}
		} else {
			msg = String.format("非发卡机构、机构网点或者售卡代理不能做[%s]处理。", oprtName);
		}
		return msg;
	}
	
	/**
	 * 判断登录的机构退卡销户是否需要审核。
	 * @param cardBranch 登录机构号
	 * @return
	 * @throws BizException
	 */
	private boolean isCheckForCancelCard(UserInfo userInfo) throws BizException {
		boolean isNeedCheck = false;
		String roleType = userInfo.getRole().getRoleType();
		
		if (RoleType.CARD.getValue().equals(roleType)) {
			SaleDepositCheckConfig config = (SaleDepositCheckConfig) this.saleDepositCheckConfigDAO.findByPk(userInfo.getBranchNo());
			Assert.notNull(config, "发卡机构[" + userInfo.getBranchNo() + "]没有配置退卡销户审核权限");
			
			isNeedCheck = StringUtils.equals(Symbol.YES, config.getCancelCardCheck());
		}
		
		return isNeedCheck;
	}

}
