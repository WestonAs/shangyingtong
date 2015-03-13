package gnete.card.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gnete.card.dao.CardMerchRemitAccountDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.CardMerchRemitAccount;
import gnete.card.entity.CardMerchRemitAccountKey;
import gnete.card.entity.MerchInfo;
import gnete.card.service.CardMerchRemitAccService;
import gnete.etc.Assert;
import gnete.etc.BizException;

@Service("cardMerchRemitAccService")
public class CardMerchRemitAccServiceImpl implements CardMerchRemitAccService {
	@Autowired
	private CardMerchRemitAccountDAO cardMerchRemitAccountDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;

	public boolean addCardMerchRemitAccount(
			CardMerchRemitAccount cardMerchRemitAccount, String userId)
			throws BizException {
		Assert.notNull(cardMerchRemitAccount, "添加的划账参数对象不能为空。");
		CardMerchRemitAccountKey key = new CardMerchRemitAccountKey();
		key.setBranchCode(cardMerchRemitAccount.getBranchCode());
		key.setMerchId(cardMerchRemitAccount.getMerchId());
		CardMerchRemitAccount old = (CardMerchRemitAccount) this.cardMerchRemitAccountDAO.findByPk(key);
		Assert.isNull(old, "发卡机构["+cardMerchRemitAccount.getBranchCode()+"]与商户["+cardMerchRemitAccount.getMerchId()+"]的划账参数已经设置，不能重复设置。");
		MerchInfo merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(cardMerchRemitAccount.getMerchId());
		cardMerchRemitAccount.setCurCode(merchInfo.getCurrCode());
		cardMerchRemitAccount.setUpdateBy(userId);
		cardMerchRemitAccount.setUpdateTime(new Date());
		return cardMerchRemitAccountDAO.insert(cardMerchRemitAccount)!=null;
	}

	public boolean deleteCardMerchRemitAccount(CardMerchRemitAccountKey key)
			throws BizException {
		Assert.notNull(key, "删除的划账参数对象不能为空。");
		return cardMerchRemitAccountDAO.delete(key)>0;
	}

	public boolean modifyCardMerchRemitAccount(
			CardMerchRemitAccount cardMerchRemitAccount, String userId) throws BizException {
		Assert.notNull(cardMerchRemitAccount, "修改的划账参数对象不能为空。");
		MerchInfo merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(cardMerchRemitAccount.getMerchId());
		cardMerchRemitAccount.setCurCode(merchInfo.getCurrCode());
		cardMerchRemitAccount.setUpdateBy(userId);
		cardMerchRemitAccount.setUpdateTime(new Date());
		int count = cardMerchRemitAccountDAO.update(cardMerchRemitAccount);
		return count > 0;
	}

}
