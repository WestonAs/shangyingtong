package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CardInfo;
import gnete.card.entity.state.CardState;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @File: CardInfoDAO.java
 *
 * @description: 卡信息表相关查询。注：前台不能修改此表，只能做相关的查询
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-3
 */
public interface CardInfoDAO extends BaseDAO {
	
	
	/**
	 * 根据卡子类型查询卡信息表
	 * 
	 * @param cardSubClass 卡子类型号
	 * @return
	 */
	List<CardInfo> findByCardSubClass(String cardSubclass);
	
	/**
	 * 根据起始卡号和结束卡号查找两者之间的卡列表
	 * @param startCard
	 * @param endCard
	 * @return
	 */
	List<CardInfo> getCardList(String startCard, String endCard);
	
	/**
	 * 根据卡BIN找到卡列表
	 * @param strNo
	 * @return
	 */
	List<CardInfo> getCardListByCardBin(String strNo);
	
	/**
	 * 根据卡账号找到卡列表
	 * @param acctId
	 * @return
	 */
	List<CardInfo> getCardListByAcctId(String acctId);
	
	/**
	 * 根据起始卡号和结束卡号，查询符合条件的卡数量
	 * @param strNo 起始卡号
	 * @param endNo 结束卡号
	 * @return
	 */
	Long getCardNum(String strNo, String endNo);
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	Long getCardNumByState(Map<String, Object> params);
	
    // 查找预制卡
	public Paginater findPreCard(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	// 根据卡状态查找预制卡
	 // 查找预制卡
	public Paginater findByState(List<CardState> cardState, int pageNumber,
			int pageSize);
	
	 // 查找过期卡
	public Paginater findExpireCard(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	public Paginater findCardInfoMemb(Map<String, Object> params, int pageNumber,
			int pageSize);

	/**
	 * 查找卡信息
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardInfo(Map<String, Object> params, int pageNumber, int pageSize);
	
	Paginater findCardFile(Map<String, Object> params, int pageNumber, int pageSize);
	
	public CardInfo findByPkWithCheck(Map<String, Object> params);
	
	/**
	 * 查询累积消费充值余额报表数据
	 * @param params
	 * @return
	 */
	List<CardInfo> getConsmChargeBalDataList(Map<String, Object> params);
	
	/**
	 * 根据条件查询卡档案信息
	 * @param params
	 * @return
	 */
	List<CardInfo> findCardFileList(Map<String, Object> params);
	
	/**
	 * 卡余额区间查询
	 * @param strAvlbBal 余额上限
	 * @param endAvlbBal 余额下限
	 * @param cardBranch 发卡机构编号
	 * @return
	 */
	Map<String, Object> findCardBanlanceAmt(BigDecimal strAvlbBal, BigDecimal endAvlbBal, String cardBranch);
	
	/**
	 * 根据发卡机构号，查询是否存在指定的发卡机构的卡号
	 * @param cardIssuer
	 * @return
	 */
	boolean isExsitByCardIssuer(String cardIssuer);
	
	/**
	 * 双汇查询,根据条件查询CardIds
	 * 
	 * @param params
	 * @return
	 */
	Map findShineWayQueryCardIds(Map<String, Object> params) ;
	
	/**
	 * 根据CardIds和消费日期进行双汇查询
	 * 
	 * @param params
	 * @return
	 */
	Paginater findShineWayQueryCardInfo(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 根据卡号，起始日期和结束日期查询某卡的交易总金额和总次数
	 * @param cardId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Map<String, Object> findTransByCardIdAndDate(String cardId, String consumeStartDate, String consumeEndDate);

	/** 
	 * 更新卡信息表中cardId参数指定的记录的的phoneNum字段 
	 */
	int updatePhoneNum(String phoneNum, String cardId);
	
	/**
	 * 批量更新卡信息状态（同时更新update_time字段）
	 */
	void updateCardStatus(List<String> cardIds, String status);

	/**
	 * 更新卡信息状态（同时更新update_time字段）
	 */
	void updateCardStatus(String cardId, String status);
}