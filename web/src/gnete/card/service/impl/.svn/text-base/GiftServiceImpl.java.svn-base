package gnete.card.service.impl;

import gnete.card.dao.GiftDefDAO;
import gnete.card.entity.GiftDef;
import gnete.card.entity.state.GiftDefState;
import gnete.card.service.GiftService;
import gnete.card.workflow.dao.WorkflowPosDAO;
import gnete.card.workflow.entity.WorkflowPosKey;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("giftService")
public class GiftServiceImpl implements GiftService {

	@Autowired
	private GiftDefDAO giftDefDAO;
	@Autowired
	private WorkflowPosDAO workflowPosDAO;

	public boolean addGiftDef(GiftDef giftDef) throws BizException {
		
		Assert.notNull(giftDef, "添加的礼品对象不能为空");
		giftDef.setStatus(GiftDefState.WAITED.getValue()); //待审核状态
		giftDef.setGiftUse("0"); //默认为积分兑换
		return this.giftDefDAO.insert(giftDef) != null;
	}

	public boolean modifyGift(GiftDef giftDef) throws BizException {
		
		Assert.notNull(giftDef, "更新的礼品对象不能为空");
		int count = this.giftDefDAO.update(giftDef);
		return count>0;
	}

	public boolean delete(String giftId) throws BizException {

		Assert.notNull(giftId, "删除的礼品对象不能为空");		
		int count = this.giftDefDAO.delete(giftId);
		WorkflowPosKey key = new WorkflowPosKey();
		key.setRefId(giftId);
		key.setWorkflowId(Constants.WORKFLOW_GIFT_DEF);
		int countPos = this.workflowPosDAO.delete(key);
		return count > 0 & countPos>0;
	}

	public boolean isExistGift(String giftId) throws BizException {
		
		Assert.notNull(giftId, "礼品代码不能为空");
		return this.giftDefDAO.findByPk(giftId) != null;
	}
	
}
