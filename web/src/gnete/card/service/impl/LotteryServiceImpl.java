package gnete.card.service.impl;

import gnete.card.dao.AwardRegDAO;
import gnete.card.dao.BrushSetDAO;
import gnete.card.dao.DrawDefDAO;
import gnete.card.dao.DrawInfoRegDAO;
import gnete.card.dao.PrizeDefDAO;
import gnete.card.entity.AwardReg;
import gnete.card.entity.AwardRegKey;
import gnete.card.entity.BrushSet;
import gnete.card.entity.DrawDef;
import gnete.card.entity.DrawInfoReg;
import gnete.card.entity.DrawInfoRegKey;
import gnete.card.entity.PrizeDef;
import gnete.card.entity.PrizeDefKey;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.AwdState;
import gnete.card.entity.state.DrawDefineState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.LotteryService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("lotteryService")
public class LotteryServiceImpl implements LotteryService {

	@Autowired
	private DrawDefDAO drawDefDAO;
	@Autowired
	private BrushSetDAO brushSetDAO;
	@Autowired
	private PrizeDefDAO prizeDefDAO;
	@Autowired
	private DrawInfoRegDAO drawInfoRegDAO;
	@Autowired
	private AwardRegDAO awardRegDAO;

	// @Autowired
	// private DrawSeqPoolDAO drawSeqPoolDAO;

	public DrawDef addDrawDef(DrawDef drawDef, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(drawDef, "抽奖活动定义对象不能为空。");
		Assert.notNull(sessionUser, "登录用户的对象信息不能为空。");
//		Assert.notEmpty(drawDef.getDrawId(), "抽奖活动ID不能为空。"); 改为Sequence

//		List<DrawSeqPool> pooList = drawSeqPoolDAO.findEffectSeq(UseState.UNUSE
//				.getValue());
//		if (!pooList.isEmpty()) {
//			drawDef.setSequenceName(pooList.get(0).getSequenceName());
//		} else {
//			DrawSeqPool seqPool = new DrawSeqPool();
//			seqPool.setSequenceName("DRAW_" + drawDef.getDrawId() + "_"
//					+ UUID.randomUUID().toString().trim().substring(0, 6));
//			seqPool.setStatus(UseState.UNUSE.getValue());
//			drawSeqPoolDAO.insert(seqPool);
//		}
		drawDef.setStatus(DrawDefineState.WAITED.getValue());
		drawDef.setDefOptr(sessionUser.getUserId());
		drawDef.setDefTime(new Date());
		drawDefDAO.insert(drawDef);
		return drawDef;
	}
	
	public boolean modifyDrawDef(DrawDef drawDef, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(drawDef, "抽奖活动定义对象不能为空。");
		Assert.notNull(sessionUser, "登录用户的对象信息不能为空。");
		
		drawDef.setStatus(DrawDefineState.WAITED.getValue());
		drawDef.setDefOptr(sessionUser.getUserId());
		drawDef.setDefTime(new Date());
		return drawDefDAO.update(drawDef) > 0;
	}
	
	public boolean deleteDrawDef(String drawId)
			throws BizException {
		Assert.notNull(drawId, "抽奖活动ID不能为空。");
		
		return drawDefDAO.delete(drawId) > 0;
	}

	public BrushSet addBrushSet(BrushSet brushSet, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(brushSet, "要新增的即刷即中设置对象不能为空。");
		Assert.notNull(sessionUser, "登录用户的对象信息不能为空。");
		Assert.notEmpty(brushSet.getDrawId(), "抽奖活动ID不能为空。");
		Assert.notNull(brushSet.getPrizeNo(), "奖项ID不能为空。");

		brushSet.setUpdateTime(new Date());
		brushSet.setUpdateUser(sessionUser.getUserId());
		brushSetDAO.insert(brushSet);
		return brushSet;
	}

	public boolean modifyBrushSet(BrushSet brushSet, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(brushSet, "要修改的即刷即中设置对象不能为空。");
		Assert.notNull(sessionUser, "登录用户的对象信息不能为空。");
		
		brushSet.setUpdateUser(sessionUser.getUserId());
		brushSet.setUpdateTime(new Date());
		return brushSetDAO.update(brushSet) > 0;
	}

	public boolean deleteBrushSet(BrushSet brushSet) throws BizException {
		Assert.notNull(brushSet, "要删除的即刷即中设置对象不能为空。");
		Assert.notNull(brushSet.getId(), "ID不能为空。");
		return brushSetDAO.delete(brushSet) > 0;
	}
	
	public PrizeDef addPrizeDef(PrizeDef prizeDef, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(prizeDef, "要新增的奖项定义对象不能为空。");
		Assert.notNull(sessionUser, "登录用户的对象信息不能为空。");
		
		prizeDef.setDefTime(new Date());
		prizeDef.setDefOptr(sessionUser.getUserId());
		prizeDefDAO.insert(prizeDef);
		return prizeDef;
	}
	
	public boolean modifyPrizeDef(PrizeDef prizeDef, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(prizeDef, "要修改的奖项对象不能为空。");
		Assert.notNull(sessionUser, "登录用户的对象信息不能为空。");
		
		prizeDef.setDefOptr(sessionUser.getUserId());
		prizeDef.setDefTime(new Date());
		
		return prizeDefDAO.update(prizeDef) > 0;
	}

	public boolean deletePrizeDef(PrizeDefKey prizeDefKey) throws BizException {
		Assert.notNull(prizeDefKey, "要删除的奖项对象不能为空。");
		Assert.notEmpty(prizeDefKey.getDrawId(), "抽奖活动ID不能为空。");
		Assert.notNull(prizeDefKey.getPrizeNo(), "奖项ID不能为空。");
		
		return prizeDefDAO.delete(prizeDefKey) > 0;
	}
	
	/** 新增参与抽奖的用户 */
	public DrawInfoReg addDrawInfoReg(DrawInfoReg drawInfoReg, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(drawInfoReg, "抽奖活动用户对象不能为空。");
		Assert.notEmpty(drawInfoReg.getDrawId(), "抽奖活动用户参与的活动ID不能为空。");
		Assert.notEmpty(drawInfoReg.getJionDrawId(), "抽奖活动用户ID不能为空。");
		Assert.notNull(sessionUser, "登录用户的对象信息不能为空。");
		
		drawInfoReg.setStatus(AwdState.WAITED.getValue());
		drawInfoReg.setUpdateBy(sessionUser.getUserId());
		drawInfoReg.setUpdateTime(new Date());
		drawInfoRegDAO.insert(drawInfoReg);
		return drawInfoReg;
	}
	
	/** 删除参与抽奖的用户 */
	public boolean deleteDrawInfoReg(DrawInfoRegKey drawInfoRegKey)
			throws BizException {
		Assert.notNull(drawInfoRegKey, "抽奖活动用户对象不能为空。");
		Assert.notEmpty(drawInfoRegKey.getDrawId(), "抽奖活动用户参与的活动ID不能为空。");
		Assert.notEmpty(drawInfoRegKey.getJionDrawId(), "抽奖活动用户ID不能为空。");
		
		return drawInfoRegDAO.delete(drawInfoRegKey)>0;
	}
	
	/** 启动抽奖 */
	public Long startDrawAction(String drawId, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(sessionUser, "登录用户的对象信息不能为空。");
		Assert.notEmpty(drawId, "抽奖活动ID不能为空。");
		
		// 发报文到后台
		return MsgSender.sendMsg(MsgType.DRAW_AWARD, Long.valueOf(drawId), sessionUser.getUserId());
	}
	
	/** 领奖 */
	public boolean modifyAwardReg(AwardReg awardReg, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(awardReg, "中奖用户对象不能为空。");
		Assert.notEmpty(awardReg.getDrawId(), "中奖用户参与的活动ID不能为空。");
		Assert.notEmpty(awardReg.getAwdTicketNo(), "中奖用户ID不能为空。");
		
		awardReg.setAwdStatus(AwdState.EXCHANGED.getValue());
		awardReg.setExchgTime(new Date());
		awardReg.setExchgOptr(sessionUser.getUserId());
		return awardRegDAO.update(awardReg)>0;
	}
	
	
	/** 删除中奖用户 */
	public boolean deleteAwardReg(AwardRegKey awardRegKey)
			throws BizException {
		Assert.notNull(awardRegKey, "中奖用户对象不能为空。");
		Assert.notEmpty(awardRegKey.getDrawId(), "中奖用户参与的活动ID不能为空。");
		Assert.notEmpty(awardRegKey.getAwdTicketNo(), "中奖用户ID不能为空。");
		
		return awardRegDAO.delete(awardRegKey)>0;
	}
}
