package gnete.card.service;

import gnete.card.entity.AwardDefKey;
import gnete.card.entity.AwardReg;
import gnete.card.entity.AwardRegKey;
import gnete.card.entity.BrushSet;
import gnete.card.entity.DrawDef;
import gnete.card.entity.DrawInfoReg;
import gnete.card.entity.DrawInfoRegKey;
import gnete.card.entity.PrizeDef;
import gnete.card.entity.PrizeDefKey;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

/**
 * @File: LotteryService.java
 * 
 * @description: 抽奖处理相关接口
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-9-25
 */
public interface LotteryService {

	/**
	 * 新增抽奖活动定义
	 * 
	 * @param drawDef
	 * @return
	 * @throws BizException
	 */
	DrawDef addDrawDef(DrawDef drawDef, UserInfo sessionUser)
			throws BizException;
	
	/**
	 * 新增抽奖活动定义
	 * 
	 * @param drawDef
	 * @return
	 * @throws BizException
	 */
	boolean modifyDrawDef(DrawDef drawDef, UserInfo sessionUser)
			throws BizException;
	
	/**
	 * 新增抽奖活动定义
	 * 
	 * @param drawDef
	 * @return
	 * @throws BizException
	 */
	boolean deleteDrawDef(String drawId)
			throws BizException;

	/**
	 * 新增即刷即中设置
	 * 
	 * @param brushSet
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	BrushSet addBrushSet(BrushSet brushSet, UserInfo sessionUser)
			throws BizException;

	/**
	 * 修改即刷即中设置
	 * 
	 * @param brushSet
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	boolean modifyBrushSet(BrushSet brushSet, UserInfo sessionUser)
			throws BizException;

	/**
	 * 删除即刷即中设置
	 * 
	 * @param brushSet
	 * @return
	 * @throws BizException
	 */
	boolean deleteBrushSet(BrushSet brushSet) throws BizException;

	/**
	 * 新增奖项定义
	 * 
	 * @param prizeDef
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	PrizeDef addPrizeDef(PrizeDef prizeDef, UserInfo sessionUser)
			throws BizException;

	/**
	 * 修改奖项设置
	 * 
	 * @param prizeDef
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	boolean modifyPrizeDef(PrizeDef prizeDef, UserInfo sessionUser)
			throws BizException;

	/**
	 * 删除奖项设置
	 * 
	 * @param prizeDef
	 * @return
	 * @throws BizException
	 */
	boolean deletePrizeDef(PrizeDefKey prizeDefKey) throws BizException;
	
	/** 新增参与抽奖的用户 */
	public DrawInfoReg addDrawInfoReg(DrawInfoReg drawInfoReg, UserInfo sessionUser)  throws BizException;
	
	/** 删除参与抽奖的用户 */
	boolean deleteDrawInfoReg(DrawInfoRegKey drawInfoRegKey)  throws BizException;
	
	/** 启动抽奖 */
	Long startDrawAction(String drawId, UserInfo sessionUser) throws BizException;
	
	/** 领奖 */
	boolean modifyAwardReg(AwardReg awardReg, UserInfo sessionUser)  throws BizException;
	
	/** 删除中奖用户 */
	boolean deleteAwardReg(AwardRegKey awardRegKey)  throws BizException;
}
