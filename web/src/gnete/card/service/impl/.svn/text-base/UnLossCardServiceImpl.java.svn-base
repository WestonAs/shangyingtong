package gnete.card.service.impl;

import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.UnLossCardRegDAO;
import gnete.card.entity.CardInfo;
import gnete.card.entity.UnLossCardReg;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.UnLossCardService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UnLossCardService")
public class UnLossCardServiceImpl implements UnLossCardService {

	@Autowired
	private UnLossCardRegDAO unLossCardRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	
	public boolean addUnLossCard(UnLossCardReg unlossCardReg,
			String updateUserId) throws BizException {

		Assert.notNull(unlossCardReg, "添加的解挂对象不能为空！");
		
		// 只有挂失状态才可以解挂
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(unlossCardReg.getCardId());
		boolean flag = false;
		if(CardState.LOSSREGISTE.getValue().equals(cardInfo.getCardStatus())){
			flag = true;
		}
		Assert.isTrue(flag, "磁卡["+unlossCardReg.getCardId() + "]目前不是挂失状态，不能解挂。");
		
		unlossCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
		unlossCardReg.setUpdateTime(new Date());
		unlossCardReg.setUpdateUser(updateUserId);

		if(this.unLossCardRegDAO.insert(unlossCardReg) != null){
			//登记成功后，组解挂报文，发送后台修改卡信息表“卡状态”为"正常"
			MsgSender.sendMsg(MsgType.UNLOSS_CARD, unlossCardReg.getUnlossBatchId(),
					unlossCardReg.getUpdateUser());
			return true;
		}
		else{
			return false;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gnete.card.service.UnLockCardService#delete(java.lang.Long)
	 */
	public boolean delete(Long unlossBatchId) throws BizException {
		Assert.notNull(unlossBatchId, "删除的解挂对象不能为空");
		int count = this.unLossCardRegDAO.delete(unlossBatchId);
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see gnete.card.service.UnLockCardService#modifyUnLossCard(gnete.card.entity.UnLossCardReg, java.lang.String)
	 */
	public boolean modifyUnLossCard(UnLossCardReg unlossCardReg,
			String updateUserId) throws BizException {
		Assert.notNull(unlossCardReg, "更新的解挂对象不能为空");

		unlossCardReg.setStatus(CardState.ACTIVE.getValue());
		unlossCardReg.setUpdateTime(new Date());
		unlossCardReg.setUpdateUser(updateUserId);
		int count = this.unLossCardRegDAO.update(unlossCardReg);

		return count > 0;
	}

	public boolean addUnLossCardBat(UnLossCardReg unlossCardReg,
			String updateUserId) throws BizException {
		Assert.notNull(unlossCardReg, "添加的解挂对象不能为空！");
		
		unlossCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
		unlossCardReg.setUpdateTime(new Date());
		unlossCardReg.setUpdateUser(updateUserId);

		if(this.unLossCardRegDAO.insert(unlossCardReg) != null){
			//登记成功后，组解挂报文，发送后台修改卡信息表“卡状态”为"正常"
			MsgSender.sendMsg(MsgType.UNLOSS_CARD, unlossCardReg.getUnlossBatchId(),
					unlossCardReg.getUpdateUser());
			return true;
		}
		else{
			return false;
		}
	}

}
