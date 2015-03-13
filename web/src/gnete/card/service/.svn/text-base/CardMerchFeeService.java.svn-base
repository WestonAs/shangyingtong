package gnete.card.service;

import gnete.card.entity.CardMerchFee;
import gnete.card.entity.CardMerchFeeDtl;
import gnete.card.entity.MerchFeeTemplate;
import gnete.card.entity.MerchFeeTemplateDetail;
import gnete.etc.BizException;

public interface CardMerchFeeService {
	
	/**
	 * 添加发卡机构与商户手续费费率参数
	 * @param cardMerchFee
	 * @return
	 * @throws BizException
	 */
	boolean addCardMerchFee(CardMerchFee[] cardMerchFee, String createUserId) throws BizException;
	
	/**
	 * 添加发卡机构与商户手续费费率参数
	 * 同时添加分段明细表
	 * @param cardMerchFee
	 * @return
	 * @throws BizException
	 */
	boolean addCardMerchFee(CardMerchFee[] feeArray, String[] ulimit, String[] feeRate, String createUserId) throws BizException;
	
	/**
	 * 修改发卡机构与商户手续费费率参数
	 * @param cardMerchFee
	 * @return
	 * @throws BizException
	 */
	boolean modifyCardMerchFee(CardMerchFee cardMerchFee, CardMerchFeeDtl[] cardMerchFees, String[] OriginalUlimit, String createUserId) throws BizException;
	
	/**
	 * 删除发卡机构与商户手续费费率参数
	 * @param cardMerchFee
	 * @return
	 * @throws BizException
	 */
	boolean deleteCardMerchFee(CardMerchFee cardMerchFee) throws BizException;
	
	/**
	 * 添加商户手续费模板
	 * @param template
	 * @param feeList
	 * @param createUserId
	 * @return
	 * @throws BizException
	 */
	boolean addMerchFeeTemplate(MerchFeeTemplate template, MerchFeeTemplateDetail[] feeList, String createUserId) throws BizException;
	
	/**
	 * 删除商户模板
	 * @param merchFeeTemplate
	 * @return
	 * @throws BizException
	 */
	boolean deleteMerchFeeTemplate(MerchFeeTemplate merchFeeTemplate) throws BizException;
	
	/**
	 * 删除商户模板明细
	 * @param merchFeeTemplateDetail
	 * @return
	 * @throws BizException
	 */
	boolean deleteMerchFeeTemplateDetail(MerchFeeTemplateDetail merchFeeTemplateDetail) throws BizException;
	
	/**
	 * 修改商户模板明细
	 * @param merchFeeTemplateDetail
	 * @return
	 * @throws BizException
	 */
	boolean modifyMerchFeeTemplateDetail(MerchFeeTemplateDetail merchFeeTemplateDetail, String userId) throws BizException;

	/**
	 * 修改商户模板
	 * @param merchFeeTemplate
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean modifyMerchFeeTemplate(MerchFeeTemplate merchFeeTemplate, String userId) throws BizException;
	
}
