package gnete.card.service.impl;

import flink.util.AmountUtil;
import gnete.card.dao.RebateRuleDetailDAO;
import gnete.card.entity.Rebate;
import gnete.card.entity.RebateRule;
import gnete.card.entity.RebateRuleDetail;
import gnete.card.entity.type.RebateRuleCalType;
import gnete.card.entity.type.RebateType;
import gnete.card.service.RebateRuleService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rebateRuleService")
public class RebateRuleServiceImpl implements RebateRuleService {

	@Autowired
	private RebateRuleDetailDAO rebateRuleDetailDAO;

	public Rebate calculateRebate(Map<String, Object> params) {

		double sourceAmt = Double.parseDouble((String) params.get("amt"));
		String rebateType = (String) params.get("rebateType"); // 返利方式：0-折现
																// 1-分摊 2-返利指定卡
																// 3-手工
		boolean isMinus = false;
		if (sourceAmt < 0.0) {
			isMinus = true;
			sourceAmt = -sourceAmt;	// 负数将其金额置成正数
		}
		
		RebateRule rebateRule = (RebateRule) params.get("rebateRule");

		String calType = rebateRule.getCalType(); // 计算方式：0-分段 1-累进 2-固定金额
		Map<String, Object> paramsDetail = new HashMap<String, Object>();
		paramsDetail.put("rebateId", rebateRule.getRebateId());
		List<RebateRuleDetail> rebateRuleDetailList = this.rebateRuleDetailDAO.findRebateRuleDetail(paramsDetail);

		Rebate rebate = new Rebate();
		int count = 0;
		double[] rebateUlimit;
		double[] rebateRate;
		double amtTemp = 0.00;

		count = rebateRuleDetailList.size();
		rebateUlimit = new double[count];
		rebateRate = new double[count];
		for (int i = 0; i < count; i++) {
			rebateUlimit[i] = Double.valueOf(rebateRuleDetailList.get(i).getRebateUlimit().toString());
			rebateRate[i] = Double.valueOf(rebateRuleDetailList.get(i)	.getRebateRate().toString());
		}
		int low = 0;
		int high = count - 1;
		while (true) {
			if (sourceAmt > rebateUlimit[low]) {
				low++;
				if (low > count - 1) {
					low--;
					break;
				}
			} else {
				if (sourceAmt < rebateUlimit[high]) {
					high--;
					if (high < 0) {
						high++;
						break;
					}
				} else {
					break;
				}
			}
		}
		if (high < low) {
			low--;
			high++;
		}
		if (low == high) {// 位于段首或段尾
			if (sourceAmt < rebateUlimit[low]) {
				// 小于最小值
				amtTemp = 0.00;
				// ####小于最小值无返利####
//				if (calType.equals(RebateRuleCalType.FIXED.getValue())) { // 固定金额
//					amtTemp = rebateRate[low];
//				} else { // 分段、累进
//					amtTemp = sourceAmt * rebateRate[low] / 100;
//				}
			} else if (sourceAmt == rebateUlimit[low]) {
				// 位于段上
				if (calType.equals(RebateRuleCalType.FIXED.getValue())) { // 固定金额
					amtTemp = rebateRate[low];
				} else if (calType.equals(RebateRuleCalType.SECT.getValue())) { // 分段
					amtTemp = sourceAmt * rebateRate[low] / 100;
				} else if (calType.equals(RebateRuleCalType.SSUM.getValue())) { // 累进
					amtTemp = 0.00;
					for (int i = 0; i <= low; i++) {
						if (i == 0) {
//							amtTemp += rebateUlimit[i] * rebateRate[i] / 100;
						} else {
							amtTemp += (rebateUlimit[i] - rebateUlimit[i - 1])	* rebateRate[i-1] / 100;
						}
					}
					amtTemp += (sourceAmt - rebateUlimit[low]) * rebateRate[low]/ 100; 
				}
			} else if (sourceAmt > rebateUlimit[low]) {
				// 大于最大值
				if (calType.equals(RebateRuleCalType.FIXED.getValue())) { // 固定金额
					amtTemp = rebateRate[low];
				} else if (calType.equals(RebateRuleCalType.SECT.getValue())) { // 分段
					amtTemp = sourceAmt * rebateRate[low] / 100;
				} else if (calType.equals(RebateRuleCalType.SSUM.getValue())) { // 累进
					amtTemp = 0.00;
					for (int i = 0; i <= low; i++) {
						if (i == 0) {
//							amtTemp += rebateUlimit[i] * rebateRate[i] / 100;
						} else {
							amtTemp += (rebateUlimit[i] - rebateUlimit[i - 1])	* rebateRate[i-1] / 100;
						}
					}
					amtTemp += (sourceAmt - rebateUlimit[low]) * rebateRate[low]/ 100; 
				}
			}
		} else {// 位于段间
			if (calType.equals(RebateRuleCalType.FIXED.getValue())) { // 固定金额
				amtTemp = rebateRate[low];
			} else if (calType.equals(RebateRuleCalType.SECT.getValue())) { // 分段
				amtTemp = sourceAmt * rebateRate[low] / 100;
			} else if (calType.equals(RebateRuleCalType.SSUM.getValue())) { // 累进
				amtTemp = 0.00;
				
				//之前的累进多加了首段的值
				for (int i = 0; i <= low; i++) {
					if (i == 0) {
//						amtTemp += rebateUlimit[i] * rebateRate[i] / 100;
					} else {
						amtTemp += (rebateUlimit[i] - rebateUlimit[i - 1])	* rebateRate[i-1] / 100;
					}
				}
				amtTemp += (sourceAmt - rebateUlimit[low]) * rebateRate[low]/ 100; 
//				for (int i = 0; i < low; i++) {
//					if (i == 0) {
//						amtTemp += rebateUlimit[i] * rebateRate[i] / 100;
//					} else {
//						amtTemp += (rebateUlimit[i] - rebateUlimit[i - 1])	* rebateRate[i] / 100;
//					}
//				}
//				amtTemp += (sourceAmt - rebateUlimit[low]) * rebateRate[high]/ 100; 
			}
		}
		
		if (isMinus) {
			sourceAmt = -sourceAmt;	// 如果为负数再改回来
			amtTemp = -amtTemp; // 返利直接置成负数
		}

		if (RebateType.CASH.getValue().equalsIgnoreCase(rebateType)) { // 0-折现
//			rebate.setRebateAmt(BigDecimal.valueOf(0.00));
			rebate.setRebateAmt(BigDecimal.valueOf(amtTemp));
			rebate.setRealAmt(BigDecimal.valueOf(sourceAmt - amtTemp));
		} else { // 1-分摊 2-返利指定卡 3-手工
			rebate.setRebateAmt(BigDecimal.valueOf(amtTemp));
			rebate.setRealAmt(BigDecimal.valueOf(sourceAmt));
		}
		
		// 如果金额为负数，返利金额为正数，需要将返利金额置为负数
		if (rebate.getRealAmt().doubleValue() < 0.0 && rebate.getRebateAmt().doubleValue() > 0.0) {
			rebate.setRebateAmt(AmountUtil.subtract(new BigDecimal(0.0), rebate.getRebateAmt()));
		}

		return rebate;
	}

}
