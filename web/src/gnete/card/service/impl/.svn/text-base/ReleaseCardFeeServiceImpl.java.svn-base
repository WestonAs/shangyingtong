package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.AmountUtils;
import flink.util.DateUtil;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.FeeCycleStageDAO;
import gnete.card.dao.ReleaseCardFeeDAO;
import gnete.card.dao.ReleaseCardFeeDtlDAO;
import gnete.card.dao.ReleaseCardFeeDtlHisDAO;
import gnete.card.dao.ReleaseCardFeeHisDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.FeeCycleStage;
import gnete.card.entity.ReleaseCardFee;
import gnete.card.entity.ReleaseCardFeeDtl;
import gnete.card.entity.ReleaseCardFeeDtlHis;
import gnete.card.entity.ReleaseCardFeeDtlKey;
import gnete.card.entity.ReleaseCardFeeHis;
import gnete.card.entity.ReleaseCardFeeKey;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.FeeStatus;
import gnete.card.entity.type.ChlFeeContentType;
import gnete.card.entity.type.ChlFeeType;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.FeeCycleType;
import gnete.card.entity.type.ReleaseCardFeeFeeType;
import gnete.card.service.ReleaseCardFeeService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("releaseCardFeeService")
public class ReleaseCardFeeServiceImpl implements ReleaseCardFeeService {
	
	@Autowired
	private ReleaseCardFeeDAO releaseCardFeeDAO;
	@Autowired
	private ReleaseCardFeeHisDAO releaseCardFeeHisDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private ReleaseCardFeeDtlDAO releaseCardFeeDtlDAO;
	@Autowired
	private FeeCycleStageDAO feeCycleStageDAO;
	@Autowired
	private ReleaseCardFeeDtlHisDAO releaseCardFeeDtlHisDAO;
	
	public boolean deleteReleaseCardFee(ReleaseCardFee releaseCardFee, String userId)
			throws BizException {
		ReleaseCardFeeKey releaseCardFeeKey = new ReleaseCardFeeKey();
		releaseCardFeeKey.setBranchCode(releaseCardFee.getBranchCode());
		releaseCardFeeKey.setUlMoney(releaseCardFee.getUlMoney());
		releaseCardFeeKey.setCardBin(releaseCardFee.getCardBin());
		releaseCardFeeKey.setMerchId(releaseCardFee.getMerchId());
		releaseCardFeeKey.setTransType(releaseCardFee.getTransType());
		ReleaseCardFee fee = (ReleaseCardFee)this.releaseCardFeeDAO.findByPk(releaseCardFeeKey);
		Assert.notNull(fee, "该发卡机构手续费参数不存在");
		ReleaseCardFeeHis his = new ReleaseCardFeeHis();
		
		try{
			this.copyReleaseCardFee(his, fee);
			//BeanUtils.copyProperties(his, fee);
		} catch (Exception e) {
			throw new BizException("复制对象时出现异常");
		}
		his.setUpdateBy(releaseCardFee.getUpdateBy());
		his.setUpdateTime(new Date());
		his.setModifyDate(DateUtil.getCurrentDate());
		his.setCurCode(fee.getCurCode());
		Object obj = this.releaseCardFeeHisDAO.insert(his);
		int count = this.releaseCardFeeDAO.delete(releaseCardFeeKey);
		return count>0&&obj!=null;
	}

	public boolean startReleaseCardFee(ReleaseCardFee releaseCardFee, String userId)
			throws BizException {
		ReleaseCardFeeKey releaseCardFeeKey = new ReleaseCardFeeKey();
		releaseCardFeeKey.setBranchCode(releaseCardFee.getBranchCode());
		releaseCardFeeKey.setUlMoney(releaseCardFee.getUlMoney());
		releaseCardFeeKey.setCardBin(releaseCardFee.getCardBin());
		releaseCardFeeKey.setMerchId(releaseCardFee.getMerchId());
		releaseCardFeeKey.setTransType(releaseCardFee.getTransType());
		ReleaseCardFee fee = (ReleaseCardFee)this.releaseCardFeeDAO.findByPk(releaseCardFeeKey);
		Assert.notNull(fee, "发卡机构手续费参数不存在");
		
		fee.setStatus(FeeStatus.EFFECT.getValue());//生效
		fee.setUpdateBy(userId);
		fee.setUpdateTime(new Date());
		
		int count = this.releaseCardFeeDAO.update(fee);
		return count > 0 ;
	}
	
	public boolean stopReleaseCardFee(ReleaseCardFee releaseCardFee, String userId)
			throws BizException {
		ReleaseCardFeeKey releaseCardFeeKey = new ReleaseCardFeeKey();
		releaseCardFeeKey.setBranchCode(releaseCardFee.getBranchCode());
		releaseCardFeeKey.setUlMoney(releaseCardFee.getUlMoney());
		releaseCardFeeKey.setCardBin(releaseCardFee.getCardBin());
		releaseCardFeeKey.setMerchId(releaseCardFee.getMerchId());
		releaseCardFeeKey.setTransType(releaseCardFee.getTransType());
		ReleaseCardFee fee = (ReleaseCardFee)this.releaseCardFeeDAO.findByPk(releaseCardFeeKey);
		Assert.notNull(fee, "发卡机构手续费参数不存在");
		
		fee.setStatus(FeeStatus.WAIT_EFFECT.getValue());//待生效
		fee.setUpdateBy(userId);
		fee.setUpdateTime(new Date());
		
		int count = this.releaseCardFeeDAO.update(fee);
		return count > 0 ;
	}

	public boolean addChlReleaseCardFee(ReleaseCardFee[] feeArray, String userId)
			throws BizException {
		Assert.notNull(feeArray, "设置的分支机构与发卡机构运营手续费对象不能为空");
		
		for (ReleaseCardFee fee : feeArray) {
			Assert.notNull(fee, "添加的分支机构与发卡机构运营手续费对象不能为空");
			
			if(fee.getFeeType().equals(ChlFeeType.FIX_MONEY.getValue())
					|| fee.getFeeType().equals(ChlFeeType.FIX_RATE.getValue())
					|| fee.getFeeType().equals(ChlFeeType.EXTRA_FIX_COST.getValue())){
				fee.setUlMoney(AmountUtil.format(Constants.FEE_MAXACCOUNT));
			}
			
			ReleaseCardFeeKey key = new ReleaseCardFeeKey();
			key.setBranchCode(fee.getBranchCode());
			key.setMerchId(fee.getMerchId());
			key.setCardBin("*");
			key.setTransType(fee.getTransType());
			key.setUlMoney(AmountUtil.format(fee.getUlMoney()));
			ReleaseCardFee oldFee = (ReleaseCardFee)releaseCardFeeDAO.findByPk(key);
			Assert.isNull(oldFee, "该金额段的手续费已经配置");
			Map param = new HashMap();
			param.put("branchCode", fee.getBranchCode());
			
			if(fee.getFeeType().equals(ChlFeeType.FIX_MONEY.getValue())
					|| fee.getFeeType().equals(ChlFeeType.FIX_RATE.getValue())
					|| fee.getFeeType().equals(ChlFeeType.ACCUMULATIVE_RATE.getValue())){
				//fee.setCostCycle(CostCycleType.YEAR.getValue());
				param.put("feeType", fee.getFeeType());
				ReleaseCardFee oldChlFee = releaseCardFeeDAO.getReleaseCardFee(param);
				Assert.isNull(oldChlFee, "该分支机构已经设置了[" + ChlFeeType.valueOf(fee.getFeeType()).getName() + "]计费类型，不能再进行重复设置。");
			}
			
			// 预付资金、通用积分资金、赠券资金，计费周期默认为"按年"
			if(ChlFeeContentType.PREPAY.getValue().equals(fee.getTransType())||
					ChlFeeContentType.POINT.getValue().equals(fee.getTransType())||
					ChlFeeContentType.COUPON.getValue().equals(fee.getTransType())){
				fee.setCostCycle(CostCycleType.YEAR.getValue());
			}
			// 其他交易类型默认为"按月"
			else{
				fee.setCostCycle(CostCycleType.MONTH.getValue());
			}
			
			BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(fee.getBranchCode());
			fee.setCurCode(branchInfo.getCurCode());
			fee.setUpdateTime(new Date());
			fee.setUpdateBy(userId);
			this.releaseCardFeeDAO.insert(fee);
		}
		return true;
	}

	public boolean addReleaseCardFee(ReleaseCardFee[] feeArray, String[] ulimit, String[] feeRate, UserInfo user)
			throws BizException {
		
		//分段，取最小的费率插入分支机构与发卡机构手续费参数
		for(ReleaseCardFee releaseCardFee : feeArray){
			List<ReleaseCardFee> feeList = this.releaseCardFeeDAO.findReleaseCardFeeList(releaseCardFee
					.getBranchCode(), releaseCardFee.getCardBin(), releaseCardFee.getMerchId(),
					releaseCardFee.getTransType());
			Assert.isEmpty(feeList, "此发卡机构的手续费参数已经配置。");

			releaseCardFee.setUpdateBy(user.getUserId()); // 更新时间
			releaseCardFee.setUpdateTime(new Date()); // 更新用户名
			
			if(releaseCardFee.getEffDate().equals(DateUtil.getCurrentDate())){ //如果生效日期是当日，状态为“生效”
				releaseCardFee.setStatus(FeeStatus.EFFECT.getValue());
			} else { //如果生效日期是当日，状态为“生效”
				releaseCardFee.setStatus(FeeStatus.WAIT_EFFECT.getValue());
			}
			
			if (releaseCardFee.getFeeType().equals(ReleaseCardFeeFeeType.TRADE_NUM_STAGE_CUM.getValue())
					|| releaseCardFee.getFeeType().equals(ReleaseCardFeeFeeType.TRADE_NUM_STAGE.getValue())
					|| releaseCardFee.getFeeType().equals(ReleaseCardFeeFeeType.MONEY_STAGE.getValue())
					|| releaseCardFee.getFeeType().equals(ReleaseCardFeeFeeType.MONEY_STAGE_CUM.getValue())) { 
				
				//检查计费周期是否大于调整周期
				if(releaseCardFee.getFeeCycleType() != null && releaseCardFee.getAdjustCycleType() != null){
					if(new BigDecimal(releaseCardFee.getFeeCycleType()).compareTo(new BigDecimal(releaseCardFee.getAdjustCycleType())) <= 0){
						Assert.isTrue(false, "计费周期应大于调整周期。");
					}
				}
				
				this.releaseCardFeeDAO.insert(releaseCardFee);
				
				// 插入手续费分段明细表
				for (int i = 0; i < ulimit.length; i++) {
					ReleaseCardFeeDtl feeDtl = new ReleaseCardFeeDtl();
					feeDtl.setUlMoney(AmountUtils.parseBigDecimal(ulimit[i]));
					feeDtl.setFeeRate(AmountUtils.parseBigDecimal(feeRate[i]));
					feeDtl.setFeeRuleId(releaseCardFee.getFeeRuleId());
					this.releaseCardFeeDtlDAO.insert(feeDtl);
				}
			} 
			// 交易笔数、金额固定比例
			else {
				this.releaseCardFeeDAO.insert(releaseCardFee);
			}
			
			// 协议月，插入计费周期分期规则表
			if(releaseCardFee.getFeeCycleType().equals(FeeCycleType.PROTOCOL_MONTH.getValue())){
				FeeCycleStage feeCycleStage = new FeeCycleStage();
				feeCycleStage.setFeeRuleId(releaseCardFee.getFeeRuleId());
				feeCycleStage.setFeeCycleType(releaseCardFee.getFeeCycleType());
				//feeCycleStage.setFeeDate(releaseCardFee.getFeeDate());
				feeCycleStage.setFeePrinciple(releaseCardFee.getFeePrinciple());
				Date feeDate = DateUtil.formatDate(releaseCardFee.getFeeDate(), "yyyyMMdd");
				
				for(int i=0; i < releaseCardFee.getAdjustMonths(); i++){
					feeCycleStage.setFeeBeginDate(DateUtil.getPreMonthDay(feeDate));
					feeCycleStage.setFeeEndDate(DateUtil.getDate(feeDate, "yyyyMMdd"));
					feeCycleStage.setFeeDate(DateUtil.getDate(feeDate, "yyyyMMdd"));
					feeCycleStage.setUpdateBy(user.getUserId());
					feeCycleStage.setUpdateTime(new Date());
					this.feeCycleStageDAO.insert(feeCycleStage);
					feeDate = DateUtil.formatDate(DateUtil.getNextMonthDay(feeDate), "yyyyMMdd");
				}
			}
		} // end of for
		
		return true;
	}

	public boolean modifyReleaseCardFee(ReleaseCardFee releaseCardFee,
			ReleaseCardFeeDtl[] releaseCardFeeDtls, String[] originalUlimit, String userId) throws BizException {
		Assert.notNull(releaseCardFee, "修改的发卡机构手续费参数对象不能为空。");
		//更新发卡机构手续费表
		ReleaseCardFeeKey key = new ReleaseCardFeeKey();
		key.setBranchCode(releaseCardFee.getBranchCode());
		key.setCardBin(releaseCardFee.getCardBin());
		key.setMerchId(releaseCardFee.getMerchId());
		key.setTransType(releaseCardFee.getTransType());
		key.setUlMoney(AmountUtil.format(releaseCardFee.getUlMoney()));
		ReleaseCardFee fee = (ReleaseCardFee)this.releaseCardFeeDAO.findByPk(key);
		
		//记录历史表
		ReleaseCardFeeHis his = new ReleaseCardFeeHis();
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
		this.releaseCardFeeHisDAO.insert(his);
		
		//明细历史表
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("feeRuleId", fee.getFeeRuleId());
		List<ReleaseCardFeeDtl> feeDtls = this.releaseCardFeeDtlDAO.getReleaseCardFeeDtlList(params);
		
		for(ReleaseCardFeeDtl releaseCardFeeDtl : feeDtls){
			ReleaseCardFeeDtlHis releaseCardFeeDtlHis = new ReleaseCardFeeDtlHis();
			releaseCardFeeDtlHis.setId(his.getId());
			releaseCardFeeDtlHis.setFeeRuleId(releaseCardFeeDtl.getFeeRuleId());
			releaseCardFeeDtlHis.setFeeRate(releaseCardFeeDtl.getFeeRate());
			releaseCardFeeDtlHis.setUlMoney(releaseCardFeeDtl.getUlMoney());
			this.releaseCardFeeDtlHisDAO.insert(releaseCardFeeDtlHis);
		}
		
		//周期明细表
		
		//更新发卡机构手续费
		fee.setFeeRate(releaseCardFee.getFeeRate());
		fee.setMinAmt(releaseCardFee.getMinAmt());
		fee.setMaxAmt(releaseCardFee.getMaxAmt());
		fee.setEffDate(releaseCardFee.getEffDate());
		fee.setExpirDate(releaseCardFee.getExpirDate());
		fee.setUpdateBy(userId);
		fee.setUpdateTime(new Date());
		int count = releaseCardFeeDAO.update(fee);
		
		ReleaseCardFeeDtlKey releaseCardFeeDtlKey = new ReleaseCardFeeDtlKey();
		List<ReleaseCardFeeDtl> feeDtlList = new ArrayList<ReleaseCardFeeDtl>();
		
		if(releaseCardFeeDtls.length>0 && originalUlimit.length>0){
			//更新发卡机构手续费明细表
			for(int i=0; i<releaseCardFeeDtls.length; i++){
				releaseCardFeeDtlKey.setFeeRuleId(fee.getFeeRuleId());
				releaseCardFeeDtlKey.setUlMoney(AmountUtils.parseBigDecimal(originalUlimit[i]));
				ReleaseCardFeeDtl releaseCardFeeDtl = (ReleaseCardFeeDtl) this.releaseCardFeeDtlDAO.findByPk(releaseCardFeeDtlKey);
				releaseCardFeeDtl.setNewUlMoney(releaseCardFeeDtls[i].getUlMoney());
				releaseCardFeeDtl.setFeeRate(releaseCardFeeDtls[i].getFeeRate());
				feeDtlList.add(releaseCardFeeDtl);
			}
			
			this.releaseCardFeeDtlDAO.updateBatch(feeDtlList);
		}
		
		return count>0;
	}
	
	private void copyReleaseCardFee(ReleaseCardFeeHis his, ReleaseCardFee fee){
		his.setBranchCode(fee.getBranchCode());
		his.setMerchId(fee.getMerchId());
		his.setCardBin(fee.getCardBin());
		his.setTransType(fee.getTransType());
		his.setUlMoney(fee.getUlMoney());
		his.setCostCycle(fee.getCostCycle());
		his.setFeeType(fee.getFeeType());

		his.setFeeRate(fee.getFeeRate());
		his.setMaxAmt(fee.getMaxAmt());
		his.setMinAmt(fee.getMinAmt());
		his.setModifyDate(fee.getModifyDate());
		his.setFeeRuleId(fee.getFeeRuleId());
		his.setAmtOrCnt(fee.getAmtOrCnt());
		his.setEffDate(fee.getFeeDate());
		his.setExpirDate(fee.getExpirDate());
		his.setFeeRuleId(fee.getFeeRuleId());
		his.setFeePrinciple(fee.getFeePrinciple());
		his.setChlCode(fee.getChlCode());
		his.setFeeCycleType(fee.getFeeCycleType());
		his.setAdjustCycleType(fee.getAdjustCycleType());
		his.setAdjustMonths(fee.getAdjustMonths());
		his.setFeeDate(fee.getFeeDate());
		his.setStatus(fee.getStatus());
	}

}
