
package gnete.card.entity.type;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: TransType.java
 *
 * @description:
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @modify: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-17
 */
public class TransType extends AbstractType{
	
	public static final Map ALL = new LinkedHashMap();
	
	/*
	交易类型代码:
		01 售卡、
		02 现金充值、
		03  转账、
		04 刷卡冲值 
		05 预售卡激活
		10 正常消费
		11 部分消费
		12 次卡消费
		13 过期消费
		14 现金暗折消费
		16 积分消费
		20 积分返利
		21 积分返券
		22 积分兑换礼品
		70卡补账
		71卡调账
		80消费赠送
		
		09 开卡
		50 销户
		51 品牌转换
		32 电子现金消费
		18 积分充值
		38 担保交易
		39 担保交易完成
	*/

	public static final TransType SELL_CARD = new TransType("售卡", "01");
	public static final TransType RECHARGE = new TransType("现金充值", "02");
	public static final TransType TRANSFER = new TransType("转账", "03");
	public static final TransType BRUSH_PECHARGE = new TransType("刷卡充值", "04");
	
	public static final TransType BANK_PECHARGE = new TransType("银行卡充值", "45");
	
	public static final TransType PRESELL_ACTIVATE = new TransType("预售卡激活", "05");
	public static final TransType PRECHARGE_ACTIVATE = new TransType("预充值激活", "06");
	public static final TransType POS_RECHARGE = new TransType("POS充值", "42");
	
	public static final TransType NORMAL_CONSUME = new TransType("正常消费", "10");
	public static final TransType PART_CONSUME = new TransType("部分消费", "11");
	//public static final TransType POINT_CONSUME = new TransType("积分消费", "12");
	public static final TransType TIME_CARD_CONSUME = new TransType("次卡消费", "12");
	public static final TransType EXPIRE_CONSUME = new TransType("过期消费", "13");
	public static final TransType CASH_CONSUME = new TransType("现金暗折消费", "14");
	
	/** 积分消费：输入金额，消费积分 */
	public static final TransType POINT_CONSUME = new TransType("积分消费", "16");
	public static final TransType POINT_CHANGE = new TransType("积分调整", "17");
	public static final TransType POINT_CHARGE = new TransType("积分充值", "18");
	public static final TransType POINT_EXC = new TransType("积分返利", "20");
	public static final TransType POINT_COUPON = new TransType("积分返券", "21");
	public static final TransType POINT_GIFT = new TransType("积分兑换礼品", "22");
	/** 扣减积分：输入积分，扣减积分 */
	public static final TransType POINT_DEDUCTION = new TransType("扣减积分", "44");
	
	public static final TransType PRE_AUTH = new TransType("预授权", "30");
	public static final TransType PRE_AUTH_FINISH = new TransType("预授权完成", "31");
	
	public static final TransType COUPON_CARD = new TransType("派送实物赠券卡", "40");
	public static final TransType COUPON_ECARD = new TransType("派送电子赠券卡", "41");
	
	public static final TransType FENQI_ZHEKOU = new TransType("分期折扣、分期折入", "60");
	public static final TransType POINT_EXPIRE = new TransType("积分过期", "61");
	
	public static final TransType CARD_REFUND = new TransType("卡补账", "70");
	public static final TransType CARD_ADJ = new TransType("卡调账", "71");
	
	/** 消费赠送：使用现金，根据促销活动，赠送积分或赠券 */
	public static final TransType CONSUME_PRESENT = new TransType("消费赠送", "80");

	public static final TransType OPEN_CARD = new TransType("开卡", "09");
	
	public static final TransType CLEAR_ACCT = new TransType("清卡", "49");
	
	public static final TransType CANCEL_ACCT = new TransType("销户", "50");
	public static final TransType BRAND_CHANGE = new TransType("品牌转换", "51");
	//public static final TransType RENEW_CARD = new TransType("换卡", "52");
	
	public static final TransType ELEC_CASE_CONSUME = new TransType("电子现金消费", "32");
	public static final TransType ECASH_DEPOSIT = new TransType("电子现金充值", "07");
	public static final TransType IC_ACTIVE = new TransType("IC卡补登或激活", "33");
	public static final TransType IC_FILL_UP_NOAQ = new TransType("IC卡指定账户圈存", "34");
	public static final TransType IC_EARMARK_AQ = new TransType("IC卡非指定账户圈存", "35");
	public static final TransType IC_FILL_UP = new TransType("IC卡补登圈存", "37");
	
	/** 冻结/解冻 */
	public static final TransType FREEZE = new TransType("冻结", "62");
	public static final TransType UNFREEZE = new TransType("解冻", "63");
	/** 担保交易 */
	public static final TransType ASSURE = new TransType("担保交易", "38");
	public static final TransType ASSURE_FINISH = new TransType("担保交易完成", "39");
	
	/** 提现/汇款*/
	public static final TransType WITHDRAW_T0 = new TransType("快速提现", "52");
	public static final TransType WITHDRAW_T1 = new TransType("普通提现", "53");
	public static final TransType WITHDRAW_MANUAL_T0 = new TransType("手工快速提现", "54");
	public static final TransType WITHDRAW_MANUAL_T1 = new TransType("手工普通提现", "55");
	
	public static final TransType REMIT_T0 = new TransType("快速汇款", "56");
	public static final TransType REMIT_T1 = new TransType("普通汇款", "57");
	public static final TransType REMIT_MANUAL_T0 = new TransType("手工快速汇款", "58");
	public static final TransType REMIT_MANUAL_T1 = new TransType("手工普通汇款", "59");
	
	public static final TransType Fee = new TransType("手续费", "23");
	
	/** 提现消费 */
	public static final TransType WITH_DRAW = new TransType("提现消费（旧）", "08");
	
	/** 计息 */
	public static final TransType COMPUTE_INTEREST = new TransType("计息", "99");
	/** 结息 */
	public static final TransType SETTLE_INTEREST = new TransType("结息", "98");
	
	// 冲正撤销相关的交易类型
	public static final TransType CANCEL_DEPOSIT = new TransType("充值撤销", "72");
	public static final TransType CANCEL = new TransType("撤销", "90");	
	public static final TransType RETURN_GOODS = new TransType("退货 ", "91");
	public static final TransType REFUND = new TransType("冲正", "92");
	public static final TransType CANCEL_REFUND = new TransType("撤销冲正", "93");
	public static final TransType CANCEL_GOODS = new TransType("退货冲正", "94");
	
	// 单机产品相关交易类型
	public static final TransType SINGLE_SELL_CARD = new TransType("单机产品售卡", "P0");
	public static final TransType SINGLE_CANCEL_ACCOUNT = new TransType("单机产品销户", "P1");
	public static final TransType SINGLE_CHARGE = new TransType("单机产品缴费", "P2");
	public static final TransType SINGLE_CHANGE_CARD = new TransType("单机产品换卡", "P3");
	public static final TransType SINGLE_FROZEN_THAW = new TransType("单机产品冻结解冻", "P4");
	public static final TransType SINGLE_MAKE_CARD = new TransType("单机产品制卡", "P5");
	
	// WebService相关交易
	public static final TransType WS_SALE_CARD = new TransType("WS售卡", "W0");
	
	public static final TransType CHG_PWD_CARD = new TransType("密码修改", "PW");
	
	// 微信WebService相关交易类型
	public static final TransType WX_TRANS_X1 = new TransType("微信开虚拟账号", "X1");
	public static final TransType WX_TRANS_X2 = new TransType("网上银行卡充值", "X2");
	public static final TransType WX_BANK_HANGING = new TransType("网上银行卡充值挂账", "46");
	public static final TransType WX_BANK_HANGING_FINISH = new TransType("充值挂账完成", "47");
	public static final TransType WX_TRANS_X3 = new TransType("微信银行卡绑定", "X3");
	public static final TransType WX_TRANS_X4 = new TransType("微信挂失解挂", "X4");
	public static final TransType WX_TRANS_X5 = new TransType("微信转账", "X5");
	public static final TransType WX_TRANS_X6 = new TransType("微信密码验证/修改/重置", "X6");
	public static final TransType WX_TRANS_X7 = new TransType("微信订单支付", "X7");
	public static final TransType WX_TRANS_X8 = new TransType("微信提现操作", "X8");
	public static final TransType WX_TRANS_X9 = new TransType("微信缴费充值", "X9");
	
	@SuppressWarnings("unchecked")
	protected TransType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static TransType valueOf(String value) {
		TransType type = (TransType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的交易类型");
		}
		return type;
	}
	
	/**
	 * 取消促销活动的交易类型
	 */
	public static List getPromtType() {
		Map params = new HashMap();
		params.put(NORMAL_CONSUME.getValue(), NORMAL_CONSUME);
		params.put(TIME_CARD_CONSUME.getValue(), TIME_CARD_CONSUME);
		params.put(POS_RECHARGE.getValue(), POS_RECHARGE);
		
		return getOrderedList(params);
	}
	
	/**
	 * 取消抽奖活动的交易类型
	 */
	public static List getDrawType() {
		Map params = new HashMap();
		params.put(NORMAL_CONSUME.getValue(), NORMAL_CONSUME);
		params.put(PART_CONSUME.getValue(), PART_CONSUME);
		
		return getOrderedList(params);
	}
	
	public static List getTransType() {
		Map params = new HashMap();
		params.put(NORMAL_CONSUME.getValue(), NORMAL_CONSUME);
		params.put(PART_CONSUME.getValue(), PART_CONSUME);
		params.put(TIME_CARD_CONSUME.getValue(), TIME_CARD_CONSUME);
		params.put(RECHARGE.getValue(), RECHARGE);
		params.put(BRUSH_PECHARGE.getValue(), BRUSH_PECHARGE);
		
		return getOrderedList(params);
	}
	
	public static List getMonitorTransType() {
		Map params = new HashMap();
		params.put(NORMAL_CONSUME.getValue(), NORMAL_CONSUME);// 正常消费
		params.put(PART_CONSUME.getValue(), PART_CONSUME);// 部分消费
		params.put(TIME_CARD_CONSUME.getValue(), TIME_CARD_CONSUME);// 次卡消费
		params.put(EXPIRE_CONSUME.getValue(), EXPIRE_CONSUME);// 过期消费
		params.put(POINT_CONSUME.getValue(), POINT_CONSUME);// 积分消费
		params.put(BRUSH_PECHARGE.getValue(), BRUSH_PECHARGE);// 刷卡充值
		params.put(CONSUME_PRESENT.getValue(), CONSUME_PRESENT);// 消费赠送
		return getOrderedList(params);
	}
	
	/**
	 * 非实时交易监控
	 * 	10: 正常消费,
	 *  11: 部分消费
	 *  12: 次卡消费,
	 *  13: 过期消费,
	 *  30: 预授权,
	 *  31: 预授权完成
	 *  80: 消费赠送
	 *  32: 电子现金消费
	 * @Date 2013-3-20上午10:08:23
	 * @return List
	 */
	public static List getNonRealTimeMonitorTransType() {
		Map params = new HashMap();
		params.put(NORMAL_CONSUME.getValue(), NORMAL_CONSUME);// 正常消费
		params.put(PART_CONSUME.getValue(), PART_CONSUME);// 部分消费
		params.put(TIME_CARD_CONSUME.getValue(), TIME_CARD_CONSUME);// 次卡消费
		params.put(EXPIRE_CONSUME.getValue(), EXPIRE_CONSUME);// 过期消费
		params.put(PRE_AUTH.getValue(), PRE_AUTH);//  预授权
		params.put(PRE_AUTH_FINISH.getValue(), PRE_AUTH_FINISH);//  预授权完成
		params.put(CONSUME_PRESENT.getValue(), CONSUME_PRESENT);// 消费赠送
		params.put(ELEC_CASE_CONSUME.getValue(), ELEC_CASE_CONSUME);// 电子现金消费
		return getOrderedList(params);
	}
	
	public static List getAll(){
		return getOrderedList(TransType.ALL);
	}

	/** 获得受限的交易查询类型 */
	public static List<TransType> getLimitedTransQueryType(){
		Map<String, TransType> params = new HashMap<String, TransType>();
		params.put(NORMAL_CONSUME.getValue(), NORMAL_CONSUME);// 正常消费
		params.put(PART_CONSUME.getValue(), PART_CONSUME);// 部分消费
		params.put(EXPIRE_CONSUME.getValue(), EXPIRE_CONSUME);// 过期消费
		return getOrderedList(params);
	}
	
	/** 获得高级折扣交易查询类型 */
	public static List<TransType> getGreatDiscntTransType(){
		Map<String, TransType> params = new HashMap<String, TransType>();
		params.put(NORMAL_CONSUME.getValue(), NORMAL_CONSUME);// 正常消费
		params.put(PART_CONSUME.getValue(), PART_CONSUME);// 部分消费
		params.put(TIME_CARD_CONSUME.getValue(), TIME_CARD_CONSUME);// 次卡消费
		params.put(EXPIRE_CONSUME.getValue(), EXPIRE_CONSUME);// 过期消费
		params.put(CONSUME_PRESENT.getValue(), CONSUME_PRESENT);// 消费赠送
		return getOrderedList(params);
	}
	
	/** 发卡机构会员手续费交易类型 */
	public static List<TransType> getCardMembFeeTransTypeList() {
		Map<String, TransType> params = new LinkedHashMap<String, TransType>();
		params.put(BRUSH_PECHARGE.getValue(), BRUSH_PECHARGE);
		params.put(WITH_DRAW.getValue(), WITH_DRAW);
		return getOrderedList(params);
	}
}
