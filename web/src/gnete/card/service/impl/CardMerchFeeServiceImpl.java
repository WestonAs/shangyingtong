package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.AmountUtils;
import gnete.card.dao.CardMerchFeeDAO;
import gnete.card.dao.CardMerchFeeDtlDAO;
import gnete.card.dao.CardMerchFeeDtlHisDAO;
import gnete.card.dao.CardMerchFeeHisDAO;
import gnete.card.dao.MerchFeeTemplateDAO;
import gnete.card.dao.MerchFeeTemplateDetailDAO;
import gnete.card.entity.CardMerchFee;
import gnete.card.entity.CardMerchFeeDtl;
import gnete.card.entity.CardMerchFeeDtlHis;
import gnete.card.entity.CardMerchFeeDtlKey;
import gnete.card.entity.CardMerchFeeHis;
import gnete.card.entity.CardMerchFeeKey;
import gnete.card.entity.MerchFeeTemplate;
import gnete.card.entity.MerchFeeTemplateDetail;
import gnete.card.entity.MerchFeeTemplateDetailKey;
import gnete.card.entity.MerchFeeTemplateKey;
import gnete.card.entity.type.CardMerchFeeFeeType;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.FeeFeeType;
import gnete.card.service.CardMerchFeeService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cardMerchFeeService")
public class CardMerchFeeServiceImpl implements CardMerchFeeService {
	
	@Autowired
	private CardMerchFeeDAO cardMerchFeeDAO;
	@Autowired
	private CardMerchFeeHisDAO cardMerchFeeHisDAO;
	@Autowired
	private CardMerchFeeDtlHisDAO cardMerchFeeDtlHisDAO;
	@Autowired
	private MerchFeeTemplateDAO merchFeeTemplateDAO;
	@Autowired
	private MerchFeeTemplateDetailDAO merchFeeTemplateDetailDAO;
	@Autowired
	private CardMerchFeeDtlDAO cardMerchFeeDtlDAO;
	
	public boolean addCardMerchFee(CardMerchFee[] feeArray, String createUserId)
			throws BizException {
		Assert.notNull(feeArray, "添加的运营中心与发卡机构手续费参数对象不能为空");
		
		for (CardMerchFee cardMerchFee : feeArray){
			Assert.notNull(cardMerchFee, "添加的运营中心与分支机构分润参数对象不能为空");
			CardMerchFeeKey key = new CardMerchFeeKey();
			key.setBranchCode(cardMerchFee.getBranchCode());
			key.setCardBin(cardMerchFee.getCardBin());
			key.setMerchId(cardMerchFee.getMerchId());
			key.setTransType(cardMerchFee.getTransType());
			key.setUlMoney(AmountUtil.format(cardMerchFee.getUlMoney()));
			CardMerchFee fee = (CardMerchFee)this.cardMerchFeeDAO.findByPk(key);
			Assert.isNull(fee, "此发卡机构和商户的手续费参数已经配置");
			
			Map param = new HashMap();
			param.put("branchCode", cardMerchFee.getBranchCode());
			param.put("cardBin", cardMerchFee.getCardBin());
			param.put("merchId", cardMerchFee.getMerchId());
			param.put("transType", cardMerchFee.getTransType());
			param.put("ulMoney", cardMerchFee.getUlMoney());
			List list = this.cardMerchFeeDAO.findCardMerchFee(param, 1, 1).getList();
			if(list.size()>0){
				CardMerchFee fee1 = (CardMerchFee)list.get(0);
				Assert.isTrue(fee1.getFeeType().equals(cardMerchFee.getFeeType()), 
						"该商户的手续费已经配置了"+CardMerchFeeFeeType.valueOf(fee1.getFeeType()).getName()+",计费方式不能再进行其他设置");
				Assert.isTrue(fee1.getCostCycle().equals(cardMerchFee.getCostCycle()), 
						"该商户的手续费已经配置了计费周期:"+CostCycleType.valueOf(fee1.getCostCycle()).getName()+"计费周期,不能再配置其他计费周期");
			}
			
			cardMerchFee.setUpdateTime(new Date());
			cardMerchFee.setUpdateBy(createUserId);
			cardMerchFeeDAO.insert(cardMerchFee);
		}
		return true;
	}

	public boolean deleteCardMerchFee(CardMerchFee cardMerchFee)
			throws BizException {
		Assert.notNull(cardMerchFee, "删除的商户手续费参数对象不能为空");
		CardMerchFeeKey key = new CardMerchFeeKey();
		key.setBranchCode(cardMerchFee.getBranchCode());
		key.setCardBin(cardMerchFee.getCardBin());
		key.setMerchId(cardMerchFee.getMerchId());
		key.setTransType(cardMerchFee.getTransType());
		key.setUlMoney(AmountUtil.format(cardMerchFee.getUlMoney()));
		CardMerchFee fee = (CardMerchFee)this.cardMerchFeeDAO.findByPk(key);
		Assert.notNull(fee, "删除的商户手续费参数已经不存在");
		
		//记录历史表
		CardMerchFeeHis his = new CardMerchFeeHis();
		try{
			this.copyCardMerchFee(his, fee);
		}catch(Exception e){
			throw new BizException("复制对象时出现异常");
		}
		his.setUpdateTime(new Date());
		his.setUlMoney(fee.getUlMoney());
		his.setCurCode(fee.getCurCode());
		his.setUpdateBy(fee.getUpdateBy());
		this.cardMerchFeeHisDAO.insert(his);
		
		int count = cardMerchFeeDAO.delete(key);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("feeRuleId", fee.getFeeRuleId());
		List<CardMerchFeeDtl> cardMerchFeeDtlList = this.cardMerchFeeDtlDAO.getCardMerchFeeDtlList(params);
		
		for(CardMerchFeeDtl feeDtl : cardMerchFeeDtlList){
			CardMerchFeeDtlHis cardMerchFeeDtlHis = new CardMerchFeeDtlHis();
			cardMerchFeeDtlHis.setId(his.getId());
			cardMerchFeeDtlHis.setFeeRuleId(feeDtl.getFeeRuleId());
			cardMerchFeeDtlHis.setFeeRate(feeDtl.getFeeRate());
			cardMerchFeeDtlHis.setUlMoney(feeDtl.getUlMoney());
			this.cardMerchFeeDtlHisDAO.insert(cardMerchFeeDtlHis);
			
			CardMerchFeeDtlKey cardMerchFeeDtlKey = new CardMerchFeeDtlKey();
			cardMerchFeeDtlKey.setFeeRuleId(feeDtl.getFeeRuleId());
			cardMerchFeeDtlKey.setUlMoney(feeDtl.getUlMoney());
			cardMerchFeeDtlDAO.delete(cardMerchFeeDtlKey);
		}
		
		return count>0;
	}

	public boolean modifyCardMerchFee(CardMerchFee cardMerchFee, CardMerchFeeDtl[] cardMerchFeeDtlList, String[] originalUlimit, String createUserId) throws BizException {
		
		Assert.notNull(cardMerchFee, "修改的商户手续费分润参数对象不能为空。");
		
		//更新商户手续费表
		CardMerchFeeKey key = new CardMerchFeeKey();
		key.setBranchCode(cardMerchFee.getBranchCode());
		key.setCardBin(cardMerchFee.getCardBin());
		key.setMerchId(cardMerchFee.getMerchId());
		key.setTransType(cardMerchFee.getTransType());
		key.setUlMoney(AmountUtil.format(cardMerchFee.getUlMoney()));
		CardMerchFee fee = (CardMerchFee)cardMerchFeeDAO.findByPk(key);
		
		//记录历史表
		CardMerchFeeHis his = new CardMerchFeeHis();
		try{
			his.setBranchCode(fee.getBranchCode());
			his.setCostCycle(fee.getCostCycle());
			his.setFeeRate(fee.getFeeRate());
			his.setFeeType(fee.getFeeType());
			his.setMerchId(fee.getMerchId());
			his.setMinAmt(fee.getMinAmt());
			his.setModifyDate(fee.getModifyDate());
			his.setMaxAmt(fee.getMaxAmt());
			his.setTransType(fee.getTransType());
			his.setCardBin(fee.getCardBin());
			his.setUlMoney(fee.getUlMoney());
			his.setCurCode(fee.getCurCode());
			his.setFeeRuleId(fee.getFeeRuleId());	
		} catch(Exception e){
			throw new BizException("复制对象时出现异常");
		}
		
		his.setUpdateBy(fee.getUpdateBy());
		his.setUpdateTime(new Date());
		this.cardMerchFeeHisDAO.insert(his);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("feeRuleId", fee.getFeeRuleId());
		List<CardMerchFeeDtl> cardMerchFeeDtls = this.cardMerchFeeDtlDAO.getCardMerchFeeDtlList(params);
		
		for(CardMerchFeeDtl cardMerchFeeDtl : cardMerchFeeDtls){
			CardMerchFeeDtlHis cardMerchFeeDtlHis = new CardMerchFeeDtlHis();
			cardMerchFeeDtlHis.setId(his.getId());
			cardMerchFeeDtlHis.setFeeRuleId(cardMerchFeeDtl.getFeeRuleId());
			cardMerchFeeDtlHis.setFeeRate(cardMerchFeeDtl.getFeeRate());
			cardMerchFeeDtlHis.setUlMoney(cardMerchFeeDtl.getUlMoney());
			this.cardMerchFeeDtlHisDAO.insert(cardMerchFeeDtlHis);
		}
		
		fee.setFeeRate(cardMerchFee.getFeeRate());
		fee.setMinAmt(cardMerchFee.getMinAmt());
		fee.setMaxAmt(cardMerchFee.getMaxAmt());
		fee.setUpdateBy(createUserId);
		fee.setUpdateTime(new Date());
		int count = cardMerchFeeDAO.update(fee);
		
		CardMerchFeeDtlKey cardMerchFeeDtlKey = new CardMerchFeeDtlKey();
		List<CardMerchFeeDtl> feeDtlList = new ArrayList<CardMerchFeeDtl>();
		
		if(cardMerchFeeDtlList.length>0 && originalUlimit.length>0){
			//更新商户手续费明细表
			for(int i=0; i<cardMerchFeeDtlList.length; i++){
				cardMerchFeeDtlKey.setFeeRuleId(fee.getFeeRuleId());
				cardMerchFeeDtlKey.setUlMoney(AmountUtils.parseBigDecimal(originalUlimit[i]));
				CardMerchFeeDtl cardMerchFeeDtl = (CardMerchFeeDtl) this.cardMerchFeeDtlDAO.findByPk(cardMerchFeeDtlKey);
				cardMerchFeeDtl.setNewUlMoney(cardMerchFeeDtlList[i].getUlMoney());
				cardMerchFeeDtl.setFeeRate(cardMerchFeeDtlList[i].getFeeRate());
				feeDtlList.add(cardMerchFeeDtl);
			}
			
			this.cardMerchFeeDtlDAO.updateBatch(feeDtlList);
		}
		
		return count>0;
	}
	
	private void copyCardMerchFee(CardMerchFeeHis his,CardMerchFee fee){
		his.setBranchCode(fee.getBranchCode());
		his.setCardBin(fee.getCardBin());
		his.setCostCycle(fee.getCostCycle());
		his.setFeeRate(fee.getFeeRate());
		his.setFeeType(fee.getFeeType());
		his.setMaxAmt(fee.getMaxAmt());
		his.setMerchId(fee.getMerchId());
		his.setMinAmt(fee.getMinAmt());
		his.setModifyDate(fee.getModifyDate());
		his.setTransType(fee.getTransType());
		his.setFeeRuleId(fee.getFeeRuleId());
	}

	public boolean addMerchFeeTemplate(MerchFeeTemplate template,
			MerchFeeTemplateDetail[] feeList, String createUserId)
			throws BizException {
		Assert.notNull(template, "添加的商户手续费模板对象不能为空！");
		Assert.notNull(feeList, "添加的商户手续费模板对象不能为空！");
		
		template.setUpdateBy(createUserId);
		template.setUpdateTime(new Date());
		this.merchFeeTemplateDAO.insert(template);
		
		for (MerchFeeTemplateDetail merchFeeTemplateDetail : feeList){
			Assert.notNull(merchFeeTemplateDetail, "添加的商户手续费模板明细对象不能为空!");
			
			merchFeeTemplateDetail.setTemplateId(template.getTemplateId());
			merchFeeTemplateDetail.setUpdateTime(new Date());
			merchFeeTemplateDetail.setUpdateBy(createUserId);
			merchFeeTemplateDetailDAO.insert(merchFeeTemplateDetail);
		}
		return true;
	}

	public boolean deleteMerchFeeTemplate(MerchFeeTemplate merchFeeTemplate)
			throws BizException {
		return false;
	}

	public boolean deleteMerchFeeTemplateDetail(
			MerchFeeTemplateDetail merchFeeTemplateDetail) throws BizException {
		
		Assert.notNull(merchFeeTemplateDetail, "删除的商户模板明细对象不能为空！");
		
		MerchFeeTemplateDetailKey key = new MerchFeeTemplateDetailKey();
		key.setCardBin(merchFeeTemplateDetail.getCardBin());
		key.setTemplateId(merchFeeTemplateDetail.getTemplateId());
		key.setTransType(merchFeeTemplateDetail.getTransType());
		key.setUlMoney(merchFeeTemplateDetail.getUlMoney());
		
		MerchFeeTemplateDetail old = (MerchFeeTemplateDetail)this.merchFeeTemplateDetailDAO.findByPk(key);
		Assert.notNull(old, "删除的商户模板明细已经不存在！");
		
		MerchFeeTemplateKey merchFeeTemplateKey = new MerchFeeTemplateKey();
		merchFeeTemplateKey.setTemplateId(merchFeeTemplateDetail.getTemplateId());
		merchFeeTemplateKey.setBranchCode(merchFeeTemplateDetail.getBranchCode());
		
		int count1 = 0;
		int count2 = 0;
		
		// 计费方式为“金额固定比例”或者“按交易笔数”
		if(CardMerchFeeFeeType.EACH.getValue().equals(merchFeeTemplateDetail.getFeeType())||
				CardMerchFeeFeeType.FEE.getValue().equals(merchFeeTemplateDetail.getFeeType())){
			count1 = this.merchFeeTemplateDetailDAO.delete(key); // 删除商户手续费模板明细
			count2 = this.merchFeeTemplateDAO.delete(merchFeeTemplateKey); // 删除商户手续费模板
			return count1 > 0 & count2 > 0;
		}
		// 计费方式为“金额分段比例”或者“金额段阶梯收费”
		else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("templateId", merchFeeTemplateDetail.getTemplateId());
			List<MerchFeeTemplateDetail> detailList = this.merchFeeTemplateDetailDAO.getMerchFeeTemplateDetailList(params);
			
			if(detailList.size() == 1){ // 如果一个商户模板只存在一个明细
				count1 = this.merchFeeTemplateDetailDAO.delete(key); // 删除商户手续费模板明细
				count2 = this.merchFeeTemplateDAO.delete(merchFeeTemplateKey); // 删除商户手续费模板
				return count1 > 0 & count2 > 0;
			}
			else if(detailList.size() > 1){ // 如果一个商户模板存在多个明细 
				return this.merchFeeTemplateDetailDAO.delete(key) > 0; // 删除商户手续费模板明细
			}
			else {
				throw new BizException("不存在要删除的商户模板明细！");
			}
		}
		
	}

	public boolean modifyMerchFeeTemplateDetail(
			MerchFeeTemplateDetail merchFeeTemplateDetail, String userId) throws BizException {
		Assert.notNull(merchFeeTemplateDetail, "修改的商户手续费模板明细对象不能为空！");
		
		MerchFeeTemplateDetailKey key = new MerchFeeTemplateDetailKey();
		key.setCardBin(merchFeeTemplateDetail.getCardBin());
		key.setTemplateId(merchFeeTemplateDetail.getTemplateId());
		key.setTransType(merchFeeTemplateDetail.getTransType());
		key.setUlMoney(merchFeeTemplateDetail.getUlMoney());
		
		MerchFeeTemplateDetail old = (MerchFeeTemplateDetail)this.merchFeeTemplateDetailDAO.findByPk(key);
		Assert.notNull(old, "删除的商户模板明细已经不存在！");

		merchFeeTemplateDetail.setUpdateBy(userId);
		merchFeeTemplateDetail.setUpdateTime(new Date());
		
		int count = merchFeeTemplateDetailDAO.update(merchFeeTemplateDetail);
		return count>0;
	}

	public boolean modifyMerchFeeTemplate(MerchFeeTemplate merchFeeTemplate,
			String userId) throws BizException {
		Assert.notNull(merchFeeTemplate, "修改的商户手续费模板对象不能为空！");
		
		MerchFeeTemplateKey key = new MerchFeeTemplateKey();
		key.setTemplateId(merchFeeTemplate.getTemplateId());
		key.setBranchCode(merchFeeTemplate.getBranchCode());
		
		MerchFeeTemplate old = (MerchFeeTemplate)this.merchFeeTemplateDAO.findByPk(key);
		Assert.notNull(old, "删除的商户模板已经不存在！");

		merchFeeTemplate.setUpdateBy(userId);
		merchFeeTemplate.setUpdateTime(new Date());
		
		int count = merchFeeTemplateDAO.update(merchFeeTemplate);
		return count>0;
	}

	public boolean addCardMerchFee(CardMerchFee[] feeArray, String[] ulimit,
			String[] feeRate, String createUserId) throws BizException {
		
		//分段，取最小的费率插入分支机构与发卡机构手续费参数
		for(CardMerchFee cardMerchFee : feeArray){
			
			//检查是否已经存在
			CardMerchFeeKey cardMerchFeeKey = new CardMerchFeeKey();
			cardMerchFeeKey.setBranchCode(cardMerchFee.getBranchCode());
			cardMerchFeeKey.setCardBin(cardMerchFee.getCardBin());
			cardMerchFeeKey.setMerchId(cardMerchFee.getMerchId());
			cardMerchFeeKey.setTransType(cardMerchFee.getTransType());
			cardMerchFeeKey.setUlMoney(cardMerchFee.getUlMoney());
			CardMerchFee fee = (CardMerchFee)this.cardMerchFeeDAO.findByPk(cardMerchFeeKey);
			Assert.isNull(fee, "此发卡机构和商户的手续费参数已经配置。");
			
			cardMerchFee.setUpdateBy(createUserId); // 更新时间
			cardMerchFee.setUpdateTime(new Date()); // 更新用户名
			
			if (cardMerchFee.getFeeType().equals(FeeFeeType.TRADE_NUM_STAGE_CUM.getValue())
					|| cardMerchFee.getFeeType().equals(FeeFeeType.TRADE_NUM_STAGE.getValue())
					|| cardMerchFee.getFeeType().equals(FeeFeeType.MONEY_STAGE.getValue())
					|| cardMerchFee.getFeeType().equals(FeeFeeType.MONEY_STAGE_CUM.getValue())) { 
				
				this.cardMerchFeeDAO.insert(cardMerchFee);
				
				List<CardMerchFeeDtl> list = new ArrayList<CardMerchFeeDtl>();
				
				// 插入手续费分段明细表
				for (int i = 0; i < ulimit.length; i++) {
					CardMerchFeeDtl feeDtl = new CardMerchFeeDtl();
					feeDtl.setUlMoney(AmountUtils.parseBigDecimal(ulimit[i]));
					feeDtl.setFeeRate(AmountUtils.parseBigDecimal(feeRate[i]));
					feeDtl.setFeeRuleId(cardMerchFee.getFeeRuleId());
					
					list.add(feeDtl);
				}
				this.cardMerchFeeDtlDAO.insertBatch(list);
			} 
			// 交易笔数、金额固定比例
			else {
				this.cardMerchFeeDAO.insert(cardMerchFee);
			}
		} // end of for
		
		return true;
	}
}
