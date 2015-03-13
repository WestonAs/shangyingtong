package gnete.card.service;

import gnete.card.entity.CardInput;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.MakeCardReg;
import gnete.card.entity.SampleCheck;
import gnete.card.entity.UserInfo;
import gnete.card.entity.WhiteCardInput;
import gnete.etc.BizException;

import java.util.List;

/**
 * @File: CardInputService.java
 * 
 * @description: 卡入库业务处理
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-20
 */
public interface CardStockService {

	/**
	 * 成品卡入库新建
	 * 
	 * @param cardInput
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	CardInput addCardInput(CardInput cardInput, UserInfo sessionUser)
			throws BizException;

	/**
	 * 判断输入的卡号是否与选择的卡类型匹配
	 * 
	 * @param cardInput
	 * @return
	 * @throws BizException
	 */
	boolean isCorrectStrNo(CardInput cardInput, UserInfo sessionUser)
			throws BizException;

	/**
	 * 根据输入的起始卡号，卡数量，计算出结束卡号
	 * 
	 * @param cardInput
	 * @return
	 * @throws BizException
	 */
	String getEndNo(CardInput cardInput) throws BizException;

	/**
	 * 白卡入库新建
	 * 
	 * @param whiteCardInput
	 * @param sessionUser
	 * @return
	 */
	WhiteCardInput addWhiteCardInput(WhiteCardInput whiteCardInput,
			UserInfo sessionUser) throws BizException;

	/**
	 * 根据所选卡类别获得可选的卡子类型列表
	 * 
	 * @return
	 * @throws BizException
	 */
	List<CardSubClassDef> getSubTypeList(WhiteCardInput whiteCardInput,
			UserInfo sessionUser) throws BizException;

	/**
	 * 根据卡子类型获得可选的制卡登记ID列表
	 * 
	 * @return
	 * @throws BizException
	 */
	List<MakeCardReg> getMakeIdList(WhiteCardInput whiteCardInput,
			UserInfo sessionUser) throws BizException;

	/**
	 * 制卡抽检记录新建
	 * 
	 * @param sampleCheck
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	SampleCheck addSampleCheck(SampleCheck sampleCheck, UserInfo sessionUser)
			throws BizException;

	/**
	 * 删除制卡抽检表的记录
	 * 
	 * @param sampleCheck
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	boolean delteSampleCheck(SampleCheck sampleCheck, UserInfo sessionUser)
			throws BizException;

	/**
	 * 根据起始卡号，卡数量更新库存信息状态
	 * 
	 * @param strNo
	 * @param cardNum
	 * @param status
	 * @throws BizException
	 */
	void updateCardStockState(String strNo, int cardNum, String status)
			throws BizException;

}
