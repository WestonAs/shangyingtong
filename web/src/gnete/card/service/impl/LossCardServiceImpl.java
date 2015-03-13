package gnete.card.service.impl;

import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.LossCardRegDAO;
import gnete.card.dao.SaleDepositCheckConfigDAO;
import gnete.card.entity.CardInfo;
import gnete.card.entity.LossCardReg;
import gnete.card.entity.SaleDepositCheckConfig;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.RoleType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.LossCardService;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Filename: LossCardServiceImpl.java 
 * Description: 挂失业务实现层
 * Copyright: Copyright (c)2010
 * Company: 深圳雁联计算系统有限公司
 * 
 * @author: aps-zsg
 * @version: V1.0 Create at: 2010-8-13 下午05:48:12
 * 
 * Modification History: Date Author Version Description
 * ------------------------------------------------------------------ 2010-8-13
 * aps-zsg 1.0 1.0 Version
 */

@Service("LossCardService")
public class LossCardServiceImpl implements LossCardService {
	@Autowired
	private LossCardRegDAO lossCardRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private SaleDepositCheckConfigDAO saleDepositCheckConfigDAO;
	@Autowired
	private WorkflowService workflowService;

	public boolean addLossCard(LossCardReg lossCardReg, UserInfo user)
			throws BizException {
		Assert.notNull(lossCardReg, "添加的挂失对象不能为空！");
		Assert.notNull(user, "登录用户信息不能为空");
		Assert.notEmpty(lossCardReg.getCardId(), "要挂失的卡号不能为空");

		/*// 只有记名卡才能“挂失”。
		Assert.notEmpty(findCardExtraInfo(lossCardReg), "磁卡["
				+ lossCardReg.getCardId() + "]未记名，或输入姓名不对，不能挂失！");*/
		
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(lossCardReg.getCardId());
		
		if (!(CardState.PRESELLED.getValue().equals(cardInfo.getCardStatus())
				|| CardState.ACTIVE.getValue().equals(cardInfo.getCardStatus()))) {
			throw new BizException("只有预售出或者正常状态的磁卡才能挂失。");
		}

		lossCardReg.setCardBranch(cardInfo.getCardIssuer()); // 发卡机构号
		lossCardReg.setUpdateTime(new Date());
		lossCardReg.setUpdateUser(user.getUserId());
		
		boolean isNeedCheck = this.isNeedCheckForLossCard(user);
		
		// 判断是否需要审核 
		if (isNeedCheck) {
			lossCardReg.setStatus(RegisterState.WAITED.getValue());
			
			this.lossCardRegDAO.insert(lossCardReg);
			
			// 启动流程
			try {
				this.workflowService.startFlow(lossCardReg, WorkflowConstants.LOSS_CARD_ADAPTER, 
						Long.toString(lossCardReg.getLossBatchId()), user);
			} catch (Exception e) {
				throw new BizException("启动挂失审核流程时发生异常，原因：" + e.getMessage());
			}
		} else {
			lossCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
			
			this.lossCardRegDAO.insert(lossCardReg);
			//登记成功后，组挂失报文，发送后台修改卡信息表“卡状态”为"挂失"
			MsgSender.sendMsg(MsgType.LOSS_CARD, lossCardReg.getLossBatchId(), lossCardReg.getUpdateUser());
		}

//		if(this.lossCardRegDAO.insert(lossCardReg) != null){
//			//登记成功后，组挂失报文，发送后台修改卡信息表“卡状态”为"挂失"
//			MsgSender.sendMsg(MsgType.LOSS_CARD, lossCardReg.getLossBatchId(), lossCardReg.getUpdateUser());
//			return true;
//		}
//		else{
//			return false;
//		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gnete.card.service.LossCardService#modifyLossCard(long)
	 */
	public boolean modifyLossCard(LossCardReg lossCardReg, String updateUserId)
			throws BizException {
		Assert.notNull(lossCardReg, "更新的挂失对象不能为空");
		//LossCardReg losCarReg = (LossCardReg) this.lossCardRegDAO.findByPk(lossCardReg.getLossBatchId());
		lossCardReg.setUpdateTime(new Date());
		lossCardReg.setUpdateUser(updateUserId);
		int count = this.lossCardRegDAO.update(lossCardReg);

		return count > 0;
	}

	public boolean delete(Long lossBatchId) throws BizException {
		Assert.notNull(lossBatchId, "删除的挂失对象不能为空");
		int count = this.lossCardRegDAO.delete(lossBatchId);
		return count > 0;
	}

	public boolean addLossCardBat(LossCardReg lossCardReg, UserInfo user)
			throws BizException {
		Assert.notNull(lossCardReg, "添加的批量挂失对象不能为空！");
		Assert.notNull(user, "登录用户信息不能为空");
		Assert.notEmpty(lossCardReg.getStartCard(), "起始卡号不能为空");
		Assert.notEmpty(lossCardReg.getEndCard(), "结束卡号不能为空");
		
		String roleType = user.getRole().getRoleType();
		
		CardInfo startCardInfo = (CardInfo) this.cardInfoDAO.findByPk(lossCardReg.getStartCard());
		Assert.notNull(startCardInfo, "起始卡号[" + lossCardReg.getStartCard() + "]不存在");
		
		CardInfo endCardInfo = (CardInfo) this.cardInfoDAO.findByPk(lossCardReg.getEndCard());
		Assert.notNull(endCardInfo, "结束卡号[" + lossCardReg.getEndCard() + "]不存在");
		
		Assert.equals(startCardInfo.getCardBin(), endCardInfo.getCardBin(), 
				"起始卡号[" + lossCardReg.getStartCard() + "]和结束卡号[" + lossCardReg.getEndCard() + "]卡BIN不一致。");
		
		// 取得起始卡号和结束卡号之间的卡列表
		List<CardInfo> cardList = this.cardInfoDAO.getCardList(lossCardReg.getStartCard(), lossCardReg.getEndCard());
		
		Assert.notTrue(cardList.size() <= 0 , "结束卡号不能小于起始卡号。");
		Assert.notTrue(cardList.size() > 1000 , "不能挂失超过1000张卡。");
		
		// 遍历检查卡是否满足挂失要求
		for(CardInfo card : cardList){
			
			Assert.isTrue(card.getCardStatus().equals(CardState.PRESELLED.getValue())
					|| card.getCardStatus().equals(CardState.ACTIVE.getValue()), "只有预售出或者正常状态的磁卡才能挂失。");
			
			//检查登录机构是否有权限
			CardOprtPrvlgUtil.checkPrvlg(roleType, user.getBranchNo(), card, "卡挂失");
	
		}

		lossCardReg.setCardBranch(startCardInfo.getCardIssuer());
		lossCardReg.setUpdateUser(user.getUserId());
		lossCardReg.setUpdateTime(new Date());
		
		boolean isNeedCheck = this.isNeedCheckForLossCard(user);
		
		if (isNeedCheck) {
			lossCardReg.setStatus(RegisterState.WAITED.getValue());
			this.lossCardRegDAO.insert(lossCardReg);
			
			// 启动流程
			try {
				this.workflowService.startFlow(lossCardReg, WorkflowConstants.LOSS_CARD_ADAPTER, 
						Long.toString(lossCardReg.getLossBatchId()), user);
			} catch (Exception e) {
				throw new BizException("启动挂失审核流程时发生异常，原因：" + e.getMessage());
			}
		} else {
			lossCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
			
			this.lossCardRegDAO.insert(lossCardReg);
			//登记成功后，组挂失报文，发送后台修改卡信息表“卡状态”为"挂失"
			MsgSender.sendMsg(MsgType.LOSS_CARD, lossCardReg.getLossBatchId(), lossCardReg.getUpdateUser());
		}
		
//		if(this.lossCardRegDAO.insert(lossCardReg) != null){
//			//登记成功后，组挂失报文，发送后台修改卡信息表“卡状态”为"挂失"
//			MsgSender.sendMsg(MsgType.LOSS_CARD, lossCardReg.getLossBatchId(), lossCardReg.getUpdateUser());
//			return true;
//		}
//		else{
//			return false;
//		}
		
		return true;
	}
	
	/**
	 * 挂失是否需要审核
	 * 
	 * @param userInfo
	 * @return
	 * @throws BizException
	 */
	private boolean isNeedCheckForLossCard(UserInfo userInfo) throws BizException {
		String roleType = userInfo.getRole().getRoleType();
		
		boolean isNeedCheck = false;

		// 发卡机构的话需要检查是否配置了审核权限
		if (RoleType.CARD.getValue().equals(roleType)) {
			SaleDepositCheckConfig config = (SaleDepositCheckConfig) this.saleDepositCheckConfigDAO.findByPk(userInfo.getBranchNo());
			Assert.notNull(config, "发卡机构[" + userInfo.getBranchNo() + "]没有配置审核权限");
			
			isNeedCheck = StringUtils.equals(Symbol.YES, config.getLossCardCheck());
		}
		
		return isNeedCheck;
	}

	/**
	 * @Title: findCardExtraInfo
	 * @Description: 查找磁卡是否已登记姓名
	 * @param lossCardReg
	 * @return List
	 * @throws
	 */
	/*private List findCardExtraInfo(LossCardReg lossCardReg) {

		Map<String, Object> params = new HashMap<String, Object>();
		if (lossCardReg != null) {
			params.put("cardId", lossCardReg.getCardId());
			params.put("custName", lossCardReg.getCustName());
		}

		List list = this.lossCardRegDAO.findCardExtraInfo(params);
		return list;
	}*/
	
}
