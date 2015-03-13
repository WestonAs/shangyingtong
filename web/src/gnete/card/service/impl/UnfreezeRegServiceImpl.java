package gnete.card.service.impl;

import gnete.card.dao.UnfreezeRegDAO;
import gnete.card.entity.CardInfo;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.UnfreezeReg;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.UnfreezeRegService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Filename: UnfreezeRegServiceImpl.java Description: Copyright: Copyright
 * (c)2010 Company: 深圳雁联计算系统有限公司
 * 
 * @author: aps-zsg
 * @version: V1.0 Create at: 2010-9-20 下午07:28:13
 */
@Service("UnfreezeRegService")
public class UnfreezeRegServiceImpl implements UnfreezeRegService {

	@Autowired
	private UnfreezeRegDAO unfreezeRegDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see gnete.card.service.UnfreezeRegService#addUnfreeze(gnete.card.entity.UnfreezeReg,
	 *      java.lang.String)
	 */
	public Long addUnfreeze(UnfreezeReg unfreezeReg, String updateUserId)
			throws BizException {
		Assert.notNull(unfreezeReg, "添加的解付对象不能为空！");

		CardInfo cardInfo = findCardInfo(unfreezeReg);
		Assert.notNull(cardInfo.getAcctId(), "输入卡ID的账号为空，不能添加解付！");
		Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + cardInfo.getCardId() + "]卡状态不是正常状态，不能解付！");

		SubAcctBal subAcctBal = findAmt(unfreezeReg);
		Assert.notNull(subAcctBal, "未查询到账户余额信息，不能添加解付！");

		Assert.isTrue(subAcctBal.getFznAmt().compareTo(
				unfreezeReg.getUnfznAmt()) >= 0, "解付金额不能大于冻结金额！");

		unfreezeReg.setAcctId(cardInfo.getAcctId());
		unfreezeReg.setAvlbBal(subAcctBal.getAvlbBal());
		unfreezeReg.setFznAmt(subAcctBal.getFznAmt());
		unfreezeReg.setUpdateUser(updateUserId);
		unfreezeReg.setUpdateTime(new Date());
		unfreezeReg.setStatus(RegisterState.WAITEDEAL.getValue());

		this.unfreezeRegDAO.insert(unfreezeReg);

		// 发报文到后台
		return MsgSender.sendMsg(MsgType.UNFREEZE, unfreezeReg.getUnfreezeId(),
				unfreezeReg.getUpdateUser());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gnete.card.service.UnfreezeRegService#delete(java.lang.Long)
	 */
	public boolean delete(Long unfreezeId) throws BizException {
		Assert.notNull(unfreezeId, "删除的解付对象不能为空");
		int count = this.unfreezeRegDAO.delete(unfreezeId);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gnete.card.service.UnfreezeRegService#modifyUnfreeze(gnete.card.entity.UnfreezeReg,
	 *      java.lang.String)
	 */
	public boolean modifyUnfreeze(UnfreezeReg unfreezeReg, String updateUserId)
			throws BizException {
		Assert.notNull(unfreezeReg, "更新的解付对象不能为空");
		unfreezeReg.setUpdateUser(updateUserId);
		unfreezeReg.setUpdateTime(new Date());
		unfreezeReg.setStatus(RegisterState.WAITEDEAL.getValue());
		int count = this.unfreezeRegDAO.update(unfreezeReg);
		return count > 0;
	}

	private SubAcctBal findAmt(UnfreezeReg unfreezeReg) {

		Map<String, Object> params = new HashMap<String, Object>();
		if (unfreezeReg != null) {
			params.put("acctId", unfreezeReg.getAcctId());
			params.put("subacctType", unfreezeReg.getSubacctType());
		}

		return this.unfreezeRegDAO.findAmt(params);
	}

	private CardInfo findCardInfo(UnfreezeReg unfreezeReg) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardId", unfreezeReg.getCardId());
		return (CardInfo) this.unfreezeRegDAO.findCardInfo(params);
	}

	public boolean addUnfreezeRegBat(UnfreezeReg unfreezeReg,
			String updateUserId) throws BizException {
		Assert.notNull(unfreezeReg, "添加的批量卡账户解付对象不能为空！");

		unfreezeReg.setUpdateUser(updateUserId);
		unfreezeReg.setUpdateTime(new Date());
		unfreezeReg.setStatus(RegisterState.WAITEDEAL.getValue());
		
		if(this.unfreezeRegDAO.insert(unfreezeReg) != null){
			MsgSender.sendMsg(MsgType.UNFREEZE, unfreezeReg.getUnfreezeId(),
					unfreezeReg.getUpdateUser());
			return true;
		}
		else{
			return false;
		}
	}
}
