package gnete.card.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gnete.card.dao.AwardRegDAO;
import gnete.card.entity.AwardReg;
import gnete.card.entity.AwardRegKey;
import gnete.card.service.AwardRegisterService;
import gnete.etc.Assert;
import gnete.etc.BizException;

@Service("awardRegisterService")
public class AwardRegisterServiceImpl implements AwardRegisterService {
	
	@Autowired
	private AwardRegDAO awardRegDAO;
	
	public boolean updateAwardRegister(AwardReg awardReg, String sessionUserCode)
			throws BizException {
		Assert.notNull(awardReg, "传入的奖品登记对象不能为空。");
		Assert.notNull(sessionUserCode, "登录的用户信息对象不能为空。");
		
		awardReg.setExchgOptr(sessionUserCode);
		awardReg.setExchgTime(new Date());
		
		return awardRegDAO.update(awardReg) > 0;
	}

	public boolean delete(String drawId, String awdTicketNo)
			throws BizException {
		
		Assert.notNull(drawId, "删除的抽奖活动ID不能为空");		
		Assert.notNull(awdTicketNo, "删除的中奖奖票号不能为空");
		
		AwardRegKey awardRegKey = new AwardRegKey();
		awardRegKey.setAwdTicketNo(awdTicketNo);
		awardRegKey.setDrawId(drawId);
		
		int count = this.awardRegDAO.delete(awardRegKey);
		awardRegKey = null;
		
		return count > 0;
	}

}
