package gnete.card.service.impl;

import gnete.card.dao.FreezeRegDAO;
import gnete.card.entity.CardInfo;
import gnete.card.entity.FreezeReg;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.FreezeRegService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Filename: FreezeRegServiceImpl.java Description: Copyright: Copyright (c)2010
 * Company: 深圳雁联计算系统有限公司
 * 
 * @author: aps-zsg
 * @version: V1.0 Create at: 2010-9-15 上午10:39:34
 */

@Service("FreezeRegService")
public class FreezeRegServiceImpl implements FreezeRegService {

	@Autowired
	private FreezeRegDAO freezeRegDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see gnete.card.service.FreezeRegService#addFreeze(gnete.card.entity.FreezeReg,
	 *      java.lang.String)
	 */
	public Long addFreeze(FreezeReg freezeReg, String updateUserId)
			throws BizException {
		Assert.notNull(freezeReg, "添加的冻结对象不能为空！");

		SubAcctBal subAcctBal = findAmt(freezeReg);
		Assert.notNull(subAcctBal, "未查询到账户余额信息，不能添加！");

		CardInfo cardInfo = findCardInfo(freezeReg);
		Assert.notNull(cardInfo.getAcctId(), "输入卡ID的账号为空，不能添加冻结！");
		Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + cardInfo.getCardId() + "]卡状态不是正常状态，不能冻结！");

		Assert.notTrue(subAcctBal.getAvlbBal().compareTo(
				freezeReg.getNewFznAmt()) < 0, "冻结金额不能大于子账户余额！");

		freezeReg.setAcctId(cardInfo.getAcctId());
		freezeReg.setAvlbBal(subAcctBal.getAvlbBal());
		freezeReg.setFznAmt(subAcctBal.getFznAmt());
		freezeReg.setUpdateUser(updateUserId);
		freezeReg.setUpdateTime(new Date());
		freezeReg.setStatus(RegisterState.WAITEDEAL.getValue());
		this.freezeRegDAO.insert(freezeReg);

		// 发报文到后台
		return MsgSender.sendMsg(MsgType.FREEZE, freezeReg.getFreezeId(),freezeReg.getUpdateUser());

	}

	private CardInfo findCardInfo(FreezeReg freezeReg) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardId", freezeReg.getCardId());
		return (CardInfo)this.freezeRegDAO.findCardInfo(params);
	}

	private SubAcctBal findAmt(FreezeReg freezeReg) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (freezeReg != null) {
			params.put("acctId", freezeReg.getAcctId());
			params.put("subacctType", freezeReg.getSubacctType());
		}
		return this.freezeRegDAO.findAmt(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gnete.card.service.FreezeRegService#delete(java.lang.Long)
	 */
	public boolean delete(Long freezeId) throws BizException {
		Assert.notNull(freezeId, "删除的冻结对象不能为空");
		int count = this.freezeRegDAO.delete(freezeId);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gnete.card.service.FreezeRegService#modifyFreeze(gnete.card.entity.FreezeReg,
	 *      java.lang.String)
	 */
	public boolean modifyFreeze(FreezeReg freezeReg, String updateUserId)
			throws BizException {

		Assert.notNull(freezeReg, "更新的冻结对象不能为空");
		freezeReg.setUpdateUser(updateUserId);
		freezeReg.setUpdateTime(new Date());
		freezeReg.setStatus(RegisterState.WAITEDEAL.getValue());
		int count = this.freezeRegDAO.update(freezeReg);
		return count > 0;
	}

	public boolean addFreezeRegBat(FreezeReg freezeReg, String updateUserId)
			throws BizException {
		Assert.notNull(freezeReg, "添加的批量卡账户冻结对象不能为空！");

		freezeReg.setUpdateUser(updateUserId);
		freezeReg.setUpdateTime(new Date());
		freezeReg.setStatus(RegisterState.WAITEDEAL.getValue());
		
		if(this.freezeRegDAO.insert(freezeReg) != null){
			MsgSender.sendMsg(MsgType.FREEZE, freezeReg.getFreezeId(),
					freezeReg.getUpdateUser());
			return true;
		}
		else{
			return false;
		}
	}

}
