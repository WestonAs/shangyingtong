package gnete.card.adapter;

import gnete.card.dao.CardBinDAO;
import gnete.card.dao.CardBinRegDAO;
import gnete.card.dao.FenZhiCardBinMgrDAO;
import gnete.card.entity.CardBin;
import gnete.card.entity.CardBinReg;
import gnete.card.entity.FenZhiCardBinMgr;
import gnete.card.entity.state.CardBinRegState;
import gnete.card.entity.state.CardBinState;
import gnete.card.entity.state.FenzhiCardBinMgrState;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: CardBinAdapter.java
 * 
 * @description: 卡BIN申请流程审核适配器
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-7-30
 */
@Repository
public class CardBinAdapter implements WorkflowAdapter {

	@Autowired
	private CardBinDAO cardBinDAO;
	@Autowired
	private FenZhiCardBinMgrDAO fenZhiCardBinMgrDAO;
	@Autowired
	private CardBinRegDAO cardBinRegDAO;

	static Logger logger = Logger.getLogger(CardBinAdapter.class);

	public void flowEnd(String refid, String param, String userId) throws BizException {
		logger.debug("审批通过的相关处理。");
		CardBinReg cardBinReg = (CardBinReg) this.cardBinRegDAO.findByPkWithLock(NumberUtils.toLong(refid));
		Assert.notNull(cardBinReg, "卡BIN登记簿中不存在[" + refid + "]的记录");
		cardBinReg.setStatus(CardBinRegState.PASSED.getValue());
		cardBinReg.setUpdateTime(new Date());
		cardBinReg.setUpdateBy(userId);
		this.cardBinRegDAO.update(cardBinReg);

		CardBin cardBin = (CardBin) this.cardBinDAO.findByPkWithLock(cardBinReg.getBinNo());
		Assert.notNull(cardBin, "卡BIN表中卡BIN["+ cardBinReg.getBinNo() +"]的记录不存在");
		cardBin.setStatus(CardBinState.NORMAL.getValue());
		cardBin.setUpdateTime(new Date());
		this.cardBinDAO.update(cardBin); 
		
		FenZhiCardBinMgr mgr = (FenZhiCardBinMgr) this.fenZhiCardBinMgrDAO.findByPkWithLock(cardBinReg.getBinNo());
		Assert.notNull(mgr, "分支机构卡BIN管理表中卡BIN["+ cardBinReg.getBinNo() +"]的记录不存在");
		mgr.setStatus(FenzhiCardBinMgrState.BEEN_USED.getValue());
		mgr.setUpdateBy(userId);
		mgr.setUpdateTime(new Date());
		
		this.fenZhiCardBinMgrDAO.update(mgr);
	}

	public Object getJobslip(String refid) {
		return this.cardBinRegDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return WorkflowConstants.CARD_BIN_REG;
	}

	public void postBackward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("审批不通过，回退的相关处理。");
		CardBinReg cardBinReg = (CardBinReg) this.cardBinRegDAO.findByPkWithLock(NumberUtils.toLong(refid));

		// 更新状态为审核失败
		cardBinReg.setStatus(CardBinRegState.FALURE.getValue());
		cardBinReg.setUpdateTime(new Date());
		cardBinReg.setUpdateBy(userId);
		this.cardBinRegDAO.update(cardBinReg);

		// 删除CarBin管理表的数据
		this.cardBinDAO.delete(cardBinReg.getBinNo());
		
		FenZhiCardBinMgr mgr = (FenZhiCardBinMgr) this.fenZhiCardBinMgrDAO.findByPkWithLock(cardBinReg.getBinNo());
		Assert.notNull(mgr, "分支机构卡BIN管理表中卡BIN["+ cardBinReg.getBinNo() +"]的记录不存在");
		
		mgr.setStatus(FenzhiCardBinMgrState.UN_ASSIGN.getValue());
		mgr.setUpdateBy(userId);
		mgr.setUpdateTime(new Date());
		
		this.fenZhiCardBinMgrDAO.update(mgr);
	}

	public void postForward(String refid, Integer nodeId, String param, String userId)
			throws BizException {
		logger.debug("下发");
	}

}
