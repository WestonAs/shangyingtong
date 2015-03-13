package gnete.card.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.CardExampleDef;
import gnete.card.entity.CardissuerPlan;
import gnete.card.entity.CardissuerPlanFee;
import gnete.card.entity.CardissuerPlanFeeRule;
import gnete.card.entity.CostRecord;
import gnete.card.entity.PlanDef;
import gnete.card.entity.PlanSubRule;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

/**
 * @File: SingleProductService.java
 *
 * @description: 单机产品处理相关service 
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-2-7
 */
public interface SingleProductService {
	
	//====================== 卡样定义相关操作 start =====================================
	/**
	 * 查询卡样定义列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findStylePage(Map<String, Object> params, int pageNumber, int pageSize);

	/**
	 * 选择器用到的列表查询
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findStyleSelectPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 添加卡样定义
	 * @param file 卡样文件
	 * @param fileName 文件名
	 * @param cardExample 卡样定义表
	 * @param planId 套餐编号
	 * @param user 登录用户信息
	 */
	void addStyle(File file, String fileName, CardExampleDef cardExample, String planId, UserInfo user) throws BizException;
	
	/**
	 *  查询符合条件的套餐列表
	 * @param branchCode
	 * @return
	 */
	List<PlanDef> findPlanList(Map<String, Object> params);
	//======================  end  ===================================================
	
	//====================== 套餐定义相关操作 start =====================================
	/**
	 * 查询套餐定义列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findModelPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 *  查询符合条件的卡样列表
	 * @param branchCode
	 * @return
	 */
	List<CardExampleDef> findSytleList(Map<String, Object> params);
	
	/**
	 *  添加套餐定义
	 *  
	 * @param planDef 套餐定义表
	 * @param cardStyleIds 卡样编号
	 * @param serviceIds 套餐权限 
	 * @param subRuleList 套餐收费子规则
	 * @param user 登录用户信息
	 * @throws BizException
	 */
	void addModel(PlanDef planDef, String cardStyleIds, String[] serviceIds, List<PlanSubRule> subRuleList, UserInfo user) throws BizException;
	//======================  end  ===================================================
	
	//====================== 发卡机构套餐相关操作 start =====================================
	/**
	 * 查询发卡机构套餐配置列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 *  添加发卡机构套餐关系 
	 * @param cardissuerPlan
	 * @param memLevels 会员级别
	 * @param discnts 折扣率
	 * @param makeBranch 制卡厂商编号
	 * @param user
	 * @throws BizException
	 */
	void addCardExamplePlan(CardissuerPlan cardissuerPlan, String[] memLevels, String[] discnts, 
			String pointExchangeRate, String makeBranch, UserInfo user) throws BizException;
	//======================  end  ===================================================

	//====================== 单机产品缴费相关操作 start =====================================
	/**
	 * 查询单机产品缴费列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findChargePage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 *  单机产品缴费 
	 * @param plan
	 * @param user
	 * @throws BizException
	 */
	void costCharge(CostRecord costRecord, UserInfo user) throws BizException;
	
	/**
	 * 开发票
	 * @param ids
	 * @throws BizException
	 */
	void singleProductCardInvoice(String ids) throws BizException;
	//======================  end  ===================================================

	//====================== 单机产品发卡机构套餐费用维护相关操作 start =====================================
	/**
	 * 查询单机产品发卡机构套餐费用列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardPlanFeePage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 *  修改发卡机构套餐费用 
	 * @param plan
	 * @param user
	 * @throws BizException
	 */
	void modifyCardPlanFee(CardissuerPlanFee cardissuerPlanFee, 
			List<CardissuerPlanFeeRule> feeRuleList, UserInfo user) throws BizException;
	//======================  end  ===================================================

}
