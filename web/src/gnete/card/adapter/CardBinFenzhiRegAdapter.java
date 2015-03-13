package gnete.card.adapter;

import gnete.card.dao.FenZhiCardBinMgrDAO;
import gnete.card.dao.FenzhiCardBinRegDAO;
import gnete.card.entity.FenZhiCardBinMgr;
import gnete.card.entity.FenzhiCardBinReg;
import gnete.card.entity.state.CheckState;
import gnete.card.entity.state.FenzhiCardBinMgrState;
import gnete.card.service.CardBinService;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: CardBinPrexRegAdapter.java
 *
 * @description: 运营机构卡BIN管理审核流程适配器
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-11-9
 */
@Repository
public class CardBinFenzhiRegAdapter implements WorkflowAdapter {
	
	@Autowired
	private CardBinService cardBinService;
	@Autowired
	private FenzhiCardBinRegDAO fenzhiCardBinRegDAO;
	@Autowired
	private FenZhiCardBinMgrDAO fenZhiCardBinMgrDAO;
	
	private static Logger logger = Logger.getLogger(CardBinFenzhiRegAdapter.class);

	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("新增运营机构卡BIN分配审核通过的相关处理。");
		FenzhiCardBinReg fenzhiCardBinReg = (FenzhiCardBinReg) this.fenzhiCardBinRegDAO.findByPkWithLock(refid);
		this.cardBinService.checkFenzhiCardBinReg(fenzhiCardBinReg, userId);
	}

	public Object getJobslip(String refid) {
		return this.fenzhiCardBinRegDAO.findByPk(refid);
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_CARD_BIN_FENZHI_REG;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("新增运营机构卡BIN分配审核不通过，回退的相关处理。");
		FenzhiCardBinReg fenzhiCardBinReg = (FenzhiCardBinReg) this.fenzhiCardBinRegDAO.findByPkWithLock(refid);
		fenzhiCardBinReg.setUpdateBy(userId);
		fenzhiCardBinReg.setUpdateTime(new Date());
		fenzhiCardBinReg.setStatus(CheckState.FAILED.getValue());
		
		// 将正在分配中的卡BIN状态改变为可分配
		List<FenZhiCardBinMgr> list = new ArrayList<FenZhiCardBinMgr>();
		for (int i = 0; i < fenzhiCardBinReg.getBinCount(); i++) {
			String cardBin = String.valueOf(NumberUtils.toInt(fenzhiCardBinReg.getStrBinNo()) + i);
			cardBin = StringUtils.leftPad(cardBin, 6, "0");
			
			FenZhiCardBinMgr mgr = (FenZhiCardBinMgr) this.fenZhiCardBinMgrDAO.findByPkWithLock(cardBin);
			Assert.notNull(mgr, "卡BIN[" + cardBin + "]不存在");
			
			mgr.setStatus(FenzhiCardBinMgrState.UN_ASSIGN.getValue());
			mgr.setCurrentBranch(fenzhiCardBinReg.getBranchCode()); // 当前所在机构
			mgr.setUpdateBy(userId);
			mgr.setUpdateTime(new Date());
			
			list.add(mgr);
		}
		
		this.fenZhiCardBinMgrDAO.updateBatch(list);
		
		this.fenzhiCardBinRegDAO.update(fenzhiCardBinReg);
	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("流程下发");
	}

}
