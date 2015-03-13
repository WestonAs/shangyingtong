package gnete.card.service.impl;

import flink.file.AdjAccImporter;
import flink.util.AmountUtil;
import gnete.card.dao.AdjAccRegDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.SaleDepositCheckConfigDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.dao.TransAccRegDAO;
import gnete.card.dao.TransAccRuleDefDAO;
import gnete.card.dao.TransDAO;
import gnete.card.entity.AdjAccReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.SaleDepositCheckConfig;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.SubAcctBalKey;
import gnete.card.entity.Trans;
import gnete.card.entity.TransAccReg;
import gnete.card.entity.TransAccRuleDef;
import gnete.card.entity.TransAccRuleDefKey;
import gnete.card.entity.UserInfo;
import gnete.card.entity.flag.AdjAccFlag;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.state.ProcState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SubacctType;
import gnete.card.entity.type.TransType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.AccRegService;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accRegService")
public class AccRegServiceImpl implements AccRegService {
	
	@Autowired
	private TransAccRegDAO transAccRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	@Autowired
	private AdjAccRegDAO adjAccRegDAO;
	@Autowired
	private TransDAO transDAO;
	@Autowired
	private TransAccRuleDefDAO transAccRuleDefDAO;
	@Autowired
	private SaleDepositCheckConfigDAO saleDepositCheckConfigDAO;
	@Autowired
	protected WorkflowService workflowService;
	
	public TransAccReg addTransAccReg(TransAccReg transAccReg, UserInfo userInfo) throws BizException {
		Assert.notNull(transAccReg, "添加的卡转账对象不能为空");
		
		if(!StringUtils.isBlank(transAccReg.getInSubacctType())){ // 手动选择的转入子账户类型
			Assert.isTrue(Arrays.asList(SubacctType.DEPOSIT.getValue(), SubacctType.REBATE.getValue())
					.contains(transAccReg.getInSubacctType()), "手动选择的转入子账户类型只能是资金子账户或返利子账户");
		}else{
			transAccReg.setInSubacctType(transAccReg.getSubacctType());
		}
		
		if (StringUtils.equals(transAccReg.getSubacctType(), transAccReg.getInSubacctType())) {
			Assert.notEquals(transAccReg.getInCardId(), transAccReg.getOutCardId(), "转入卡["
					+ transAccReg.getInCardId() + "]与转出卡[" + transAccReg.getOutCardId() + "]不能相同");
		}
		
		CardInfo outCard = (CardInfo) this.cardInfoDAO.findByPk(transAccReg.getOutCardId());
		Assert.notNull(outCard, "转出卡[" + transAccReg.getOutCardId() + "]不存在！");
		
		CardInfo inCard = (CardInfo) this.cardInfoDAO.findByPk(transAccReg.getInCardId());
		Assert.notNull(inCard, "转入卡[" + transAccReg.getInCardId() + "]不存在！");
		
		Assert.equals(outCard.getCardIssuer(), inCard.getCardIssuer(), 
				String.format("转出卡[%s]与转入卡[%s]的发卡机构必须相同", outCard.getCardId(), inCard.getCardId()));
		
		CardOprtPrvlgUtil.checkPrvlg(userInfo.getRole().getRoleType(), userInfo.getBranchNo(), outCard, "卡转账");
		
		SubAcctBal outSubacct = (SubAcctBal) this.subAcctBalDAO.findByPk(
				new SubAcctBalKey(outCard.getAcctId(), transAccReg.getSubacctType()));
		Assert.notNull(outSubacct, "转出卡[" + transAccReg.getOutCardId() + "]不存在该类型[" + transAccReg.getSubacctType() + "]子账户");
		
		SubAcctBal inSubacct = (SubAcctBal) this.subAcctBalDAO.findByPk(
				new SubAcctBalKey(inCard.getAcctId(), transAccReg.getInSubacctType()));
		Assert.notNull(inSubacct, "转入卡[" + transAccReg.getInCardId() + "]不存在该类型[" + transAccReg.getInSubacctType() + "]子账户");
		
		Assert.notTrue(AmountUtil.lt(outSubacct.getAvlbBal(), transAccReg.getAmt()), 
				"转出卡[" + transAccReg.getOutCardId() + "]子账户["+ transAccReg.getOutAccId() + ", " + transAccReg.getSubacctType() +"]余额不足");
		
		transAccReg.setOutAccId(outCard.getAcctId());
		transAccReg.setAvlbBal(outSubacct.getAvlbBal());
		transAccReg.setFznAmt(outSubacct.getFznAmt());
		transAccReg.setInAccId(inCard.getAcctId());
		
		transAccReg.setUpdateTime(new Date());
		transAccReg.setUpdateUser(userInfo.getUserId());
		transAccReg.setCardBranch(outCard.getCardIssuer());
		transAccReg.setInitiator(userInfo.getBranchNo());
		
		
		boolean isNeedCheck = this.isCheckForTransAcc(userInfo);
		if (isNeedCheck) {
			transAccReg.setStatus(RegisterState.WAITED.getValue());
		} else {
			transAccReg.setStatus(RegisterState.PASSED.getValue());
		}
		
		this.transAccRegDAO.insert(transAccReg);
		
		if (isNeedCheck) {
			try {
				this.workflowService.startFlow(transAccReg, WorkflowConstants.TRANS_ACC_ADAPTER, 
						Long.toString(transAccReg.getTransAccId()), userInfo);
			} catch (Exception e) {
				throw new BizException("启动卡转账流程时发生异常。原因：" + e.getMessage());
			}
		} else {
			// 发送报文消息
			MsgSender.sendMsg(MsgType.TRANS_ACC, transAccReg.getTransAccId(), userInfo.getUserId());
		}
		
		return transAccReg; 
	}
	
	/**
	 * 判断发卡机构发的卡转账是否需要审核。
	 * @param cardBranch 发卡机构编号
	 * @return
	 * @throws BizException
	 */
	private boolean isCheckForTransAcc(UserInfo user) throws BizException {
		boolean isNeedCheck = false;
		String roleType = user.getRole().getRoleType();
		
		if (RoleType.CARD.getValue().equals(roleType)) {
			SaleDepositCheckConfig config = (SaleDepositCheckConfig) this.saleDepositCheckConfigDAO.findByPk(user.getBranchNo());
			Assert.notNull(config, "发卡机构[" + user.getBranchNo() + "]没有配置转账审核权限");
			
			isNeedCheck = StringUtils.equals(Symbol.YES, config.getTransAccCheck());
		}
		
		return isNeedCheck;
	}
	
	public AdjAccReg addAdjAccReg(AdjAccReg adjAccReg, UserInfo userInfo)
			throws BizException {
		Assert.notNull(adjAccReg, "添加的卡调账对象不能为空");
		
		String transSn = adjAccReg.getTransSn();
		Assert.notNull(transSn, "调账的交易流水不能为空");
		
		Trans trans = (Trans) this.transDAO.findByPk(transSn);
		Assert.notNull(trans, "未找到该交易[" + transSn + "]");
		List<String> states = Arrays.asList(ProcState.DEDSUCCESS.getValue(), ProcState.ADJ.getValue());
		Assert.isTrue(states.contains(trans.getProcStatus()), "只有扣款成功/已调账的交易才能进行调账");
		
		Assert.notEmpty(trans.getCardId(), "卡号不能为空");
		
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(trans.getCardId());
		Assert.notNull(cardInfo, "卡号[" + trans.getCardId() + "]不存在");
		
		double amt = adjAccReg.getAmt().doubleValue();
		Assert.notTrue(amt < 0.0, "调账金额不能小于0");
		
		double canAdjAmt = trans.getSettAmt().subtract(trans.getGoodsAmt()).doubleValue();
		Assert.isTrue(amt <= canAdjAmt, "调账金额必须小于等于可调账金额，请检查");
		
		String roleType = userInfo.getRole().getRoleType();
		if (RoleType.CARD.getValue().equals(roleType)) {
			Assert.isTrue(branchInfoDAO.isSuperBranch(userInfo.getBranchNo(), trans.getCardIssuer()), "用户所在的发卡机构不是交易记录的发卡机构或上级！");
			adjAccReg.setInitiator(userInfo.getBranchNo());
			
		} else if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			Assert.isTrue(branchInfoDAO.isSuperBranch(userInfo.getBranchNo(), trans.getCardIssuer()), "用户所在的发卡机构不是交易记录的发卡机构或上级！");
			adjAccReg.setInitiator(userInfo.getDeptId());
			
		} else if (RoleType.MERCHANT.getValue().equals(roleType)) {
			Assert.equals(userInfo.getMerchantNo(), trans.getMerNo(), "用户所在的商户不是交易记录的商户！");
			adjAccReg.setInitiator(userInfo.getMerchantNo());
			
		}else if(RoleType.CENTER.getValue().equals(roleType)) {
			Assert.equals(TransType.WITH_DRAW.getValue(), trans.getTransType(), "运营中心只能对提现交易做调账操作");
			adjAccReg.setInitiator(userInfo.getBranchNo());
			
		} else {
			throw new BizException("没有权限做调账申请");
		}
		
		adjAccReg.setCardBranch(cardInfo.getCardIssuer());
		
		adjAccReg.setTransType(trans.getTransType());
		adjAccReg.setAcctId(trans.getAcctId());
		adjAccReg.setCardId(trans.getCardId());
		adjAccReg.setMerNo(trans.getMerNo());
		adjAccReg.setTermlId(trans.getTermlId());
		adjAccReg.setPlatform(trans.getPlatform());
		adjAccReg.setTransAmt(trans.getTransAmt());
		adjAccReg.setProcTime(trans.getProcTime());
		adjAccReg.setProcStatus(trans.getProcStatus());
		adjAccReg.setGoodsStatus(trans.getGoodsStatus());
		
		adjAccReg.setStatus(RegisterState.WAITED.getValue());
		adjAccReg.setUpdateTime(new Date());
		adjAccReg.setUpdateUser(userInfo.getUserId());
		adjAccReg.setFlag(AdjAccFlag.ADJ_ACC.getValue());
		
		this.adjAccRegDAO.insert(adjAccReg);
		
		try {
			this.workflowService.startFlow(adjAccReg, WorkflowConstants.ADJ_ACC_ADAPTER, 
					Long.toString(adjAccReg.getAdjAccId()), userInfo);
		} catch (Exception e) {
			throw new BizException("启动调账审核流程时，发生异常。原因：" + e);
		}
		return adjAccReg;
	}
	
	public List<AdjAccReg> addAdjAccRegBat(String[] transSns, UserInfo userInfo) throws BizException {
		Assert.notNull(transSns, "退货相关交易流水不能为空");
		List<AdjAccReg> adjAccRegList = new ArrayList<AdjAccReg>();
		
		String initiator = "";
		String roleType = userInfo.getRole().getRoleType();
		if (RoleType.CARD.getValue().equals(roleType)) {
			initiator = userInfo.getBranchNo();
		} else if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			initiator = userInfo.getDeptId();
		} else if (RoleType.MERCHANT.getValue().equals(roleType)) {
			initiator = userInfo.getMerchantNo();
		} else {
			throw new BizException("没有权限做调账申请");
		}
		
		for (String transSn : transSns) {
			Trans trans = (Trans) this.transDAO.findByPk(transSn);
			Assert.notNull(trans, "未找到该交易[" + transSn + "]");
//			Assert.notTrue(!ProcState.DEDSUCCESS.getValue().equals(trans.getProcStatus()), "只有扣款成功的交易才能进行退货");
			Assert.equals(trans.getProcStatus(), ProcState.DEDSUCCESS.getValue(), "只有扣款成功的交易才能进行调账");
			
			Assert.notEmpty(trans.getCardId(), "卡号不能为空");
			
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(trans.getCardId());
			Assert.notNull(cardInfo, "卡号[" + trans.getCardId() + "]不存在");
			
			if (RoleType.CARD.getValue().equals(roleType)) {
				Assert.isTrue(branchInfoDAO.isSuperBranch(userInfo.getBranchNo(), trans.getCardIssuer()), "用户所在的发卡机构不是交易记录的发卡机构或上级！");
			} else if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
				Assert.isTrue(branchInfoDAO.isSuperBranch(userInfo.getBranchNo(), trans.getCardIssuer()), "用户所在的发卡机构不是交易记录的发卡机构或上级！");
			} else if (RoleType.MERCHANT.getValue().equals(roleType)) {
				Assert.equals(userInfo.getMerchantNo(), trans.getMerNo(), "用户所在的商户不是交易记录的商户！");
			} else {
				throw new BizException("没有权限做调账申请");
			}
			
			AdjAccReg adjAccReg = new AdjAccReg();
			
			adjAccReg.setTransSn(transSn);
			adjAccReg.setTransType(trans.getTransType());
			adjAccReg.setAcctId(trans.getAcctId());
			adjAccReg.setCardId(trans.getCardId());
			adjAccReg.setMerNo(trans.getMerNo());
			adjAccReg.setTermlId(trans.getTermlId());
			adjAccReg.setPlatform(trans.getPlatform());
			adjAccReg.setTransAmt(trans.getTransAmt());
			adjAccReg.setProcTime(trans.getProcTime());
			adjAccReg.setProcStatus(trans.getProcStatus());
			adjAccReg.setGoodsStatus(trans.getGoodsStatus());
			adjAccReg.setAmt(trans.getTransAmt());
			
			adjAccReg.setStatus(RegisterState.WAITED.getValue());
			adjAccReg.setUpdateTime(new Date());
			adjAccReg.setUpdateUser(userInfo.getUserId());
			adjAccReg.setFlag(AdjAccFlag.ADJ_ACC.getValue());
			adjAccReg.setInitiator(initiator);
			adjAccReg.setCardBranch(cardInfo.getCardIssuer());
			
			adjAccRegList.add(adjAccReg);
			this.adjAccRegDAO.insert(adjAccReg);
			
			try {
				this.workflowService.startFlow(adjAccReg, WorkflowConstants.ADJ_ACC_ADAPTER, 
						Long.toString(adjAccReg.getAdjAccId()), userInfo);
			} catch (Exception e) {
				throw new BizException("启动调账审核流程时，发生异常。原因：" + e);
			}
		}
		return adjAccRegList;
	}

	public boolean addTransAccRuleDef(TransAccRuleDef transAccRuleDef,
			UserInfo userInfo) throws BizException {
		Assert.notNull(transAccRuleDef, "添加的转账规则定义对象不能为空。");
		
		TransAccRuleDefKey key = new TransAccRuleDefKey();
		key.setBranchCode(transAccRuleDef.getBranchCode());
		key.setCardBin1(transAccRuleDef.getCardBin1());
		key.setCardBin2(transAccRuleDef.getCardBin2());
		Assert.isNull((TransAccRuleDef)this.transAccRuleDefDAO.findByPk(key), "发卡机构["+transAccRuleDef.getBranchCode()+"]与卡BIN[" + transAccRuleDef.getCardBin1() +
					"]和卡BIN[" + transAccRuleDef.getCardBin2() + "]的转账规则定义已经存在。");
		
		key.setCardBin1(transAccRuleDef.getCardBin2());
		key.setCardBin2(transAccRuleDef.getCardBin1());
		Assert.isNull((TransAccRuleDef)this.transAccRuleDefDAO.findByPk(key), "发卡机构["+transAccRuleDef.getBranchCode()+"]与卡BIN[" + transAccRuleDef.getCardBin1() +
				"]和卡BIN[" + transAccRuleDef.getCardBin2() + "]的转账规则定义已经存在。");
		
		transAccRuleDef.setStatus(CardTypeState.NORMAL.getValue());
		transAccRuleDef.setUpdateBy(userInfo.getUserId());
		transAccRuleDef.setUpdateTime(new Date());
		
		return this.transAccRuleDefDAO.insert(transAccRuleDef) != null;
	}

	public boolean deleteTransAccRuleDef(TransAccRuleDefKey key)
			throws BizException {
		Assert.notNull(key, "删除的转账规则定义对象不能为空");		
		return this.transAccRuleDefDAO.delete(key) > 0;	
	}

	public boolean modifyTransAccRuleDef(TransAccRuleDef transAccRuleDef,
			UserInfo userInfo) throws BizException {
		Assert.notNull(transAccRuleDef, "修改的转账规则定义对象不能为空。");
		
		if(CardTypeState.NORMAL.getValue().equals(transAccRuleDef.getStatus())){
			transAccRuleDef.setStatus(CardTypeState.CANCEL.getValue());
		}else if(CardTypeState.CANCEL.getValue().equals(transAccRuleDef.getStatus())){
			transAccRuleDef.setStatus(CardTypeState.NORMAL.getValue());
		}
		transAccRuleDef.setUpdateBy(userInfo.getUserId());
		transAccRuleDef.setUpdateTime(new Date());
		return this.transAccRuleDefDAO.update(transAccRuleDef)>0;
	}
	
	public List<AdjAccReg> addFileAdjAccReg(File upload,
			String uploadFileName, String userCode, UserInfo user)
			throws Exception {
		String initiator = "";
		String roleType = user.getRole().getRoleType();
		if (RoleType.CARD.getValue().equals(roleType)) {
			initiator = user.getBranchNo();
		} else if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			initiator = user.getDeptId();
		} else if (RoleType.MERCHANT.getValue().equals(roleType)) {
			initiator = user.getMerchantNo();
		} else {
			throw new BizException("没有权限做调账申请");
		}
		
		// 检查记录是否合法，合法的记录插入登记簿中，不合法的放在uninsertPointChgRegList中
		List<AdjAccReg> uninsertAdjAccRegList = new ArrayList<AdjAccReg>();
		try{
			AdjAccImporter adjAccImporter = new AdjAccImporter();
			List<AdjAccReg> adjAccRegList = adjAccImporter.getFileImporterList(upload, uploadFileName);
			
			for(AdjAccReg adjAccReg : adjAccRegList){
				
				Trans trans = (Trans) this.transDAO.findByPk(adjAccReg.getTransSn());
				if(trans != null){
					// 正常消费、部分消费、次卡消费、过期消费的交易才能退货
					if(!TransType.NORMAL_CONSUME.getValue().equals(trans.getTransType())
							&&!TransType.PART_CONSUME.getValue().equals(trans.getTransType())
							&&!TransType.TIME_CARD_CONSUME.getValue().equals(trans.getTransType())
							&&!TransType.EXPIRE_CONSUME.getValue().equals(trans.getTransType())){
						
						adjAccReg.setRemark("交易类型不正确，不能退货。");
						uninsertAdjAccRegList.add(adjAccReg);
					}
					else{
						Assert.notEmpty(trans.getCardId(), "卡号不能为空");
						
						CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(trans.getCardId());
						Assert.notNull(cardInfo, "卡号[" + trans.getCardId() + "]不存在");
						
						if (RoleType.CARD.getValue().equals(roleType)) {
							Assert.isTrue(branchInfoDAO.isSuperBranch(user.getBranchNo(), trans.getCardIssuer()), "用户所在的发卡机构不是交易记录的发卡机构或上级！");
						} else if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
							Assert.isTrue(branchInfoDAO.isSuperBranch(user.getBranchNo(), trans.getCardIssuer()), "用户所在的发卡机构不是交易记录的发卡机构或上级！");
						} else if (RoleType.MERCHANT.getValue().equals(roleType)) {
							Assert.equals(user.getMerchantNo(), trans.getMerNo(), "用户所在的商户不是交易记录的商户！");
						} else {
							throw new BizException("没有权限做调账申请");
						}
						
						adjAccReg.setTransType(trans.getTransType());
						adjAccReg.setAcctId(trans.getAcctId());
						adjAccReg.setCardId(trans.getCardId());
						adjAccReg.setMerNo(trans.getMerNo());
						adjAccReg.setTermlId(trans.getTermlId());
						adjAccReg.setPlatform(trans.getPlatform());
						adjAccReg.setTransAmt(trans.getTransAmt());
						adjAccReg.setProcTime(trans.getProcTime());
						adjAccReg.setProcStatus(trans.getProcStatus());
						adjAccReg.setGoodsStatus(trans.getGoodsStatus());
						adjAccReg.setAmt(trans.getTransAmt());
						
						adjAccReg.setStatus(RegisterState.WAITED.getValue());
						adjAccReg.setUpdateTime(new Date());
						adjAccReg.setUpdateUser(user.getUserId());
						adjAccReg.setFlag(AdjAccFlag.ADJ_ACC.getValue());
						
						adjAccReg.setInitiator(initiator);
						adjAccReg.setCardBranch(cardInfo.getCardIssuer());
						
						this.adjAccRegDAO.insert(adjAccReg);
						//启动单个流程
						this.workflowService.startFlow(adjAccReg, WorkflowConstants.ADJ_ACC_ADAPTER, 
								Long.toString(adjAccReg.getAdjAccId()), user);
					}
				}
				else{
					adjAccReg.setRemark("输入的交易不存在，不能退货。");
					uninsertAdjAccRegList.add(adjAccReg);
				}
			}
		}catch (Exception e) {
			throw e;
		}
		return uninsertAdjAccRegList;
	}

}
