package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class TransDtlType extends AbstractType{
	
	public static final Map ALL = new HashMap();

	public static final TransDtlType SELL_CARD = new TransDtlType("售卡", "01");
	public static final TransDtlType RECHARGE = new TransDtlType("现金充值", "02");
	public static final TransDtlType TRANSFER = new TransDtlType("转账", "03");
	
	public static final TransDtlType BRUSH_PECHARGE = new TransDtlType("刷卡充值", "04");
	public static final TransDtlType PRESELL_ACTIVATE = new TransDtlType("预售卡激活", "05");
	public static final TransDtlType PRECHARGE_ACTIVATE = new TransDtlType("预充值激活", "06");
	
	public static final TransDtlType NORMAL_CONSUME = new TransDtlType("正常消费", "10");
	public static final TransDtlType PART_CONSUME = new TransDtlType("部分消费", "11");
	public static final TransDtlType TIME_CARD_CONSUME = new TransDtlType("次卡消费", "12");
	
	public static final TransDtlType POINT_CONSUME = new TransDtlType("积分消费", "16");
	public static final TransDtlType POINT_CHARGE = new TransDtlType("积分充值", "18");
	public static final TransDtlType POINT_EXC = new TransDtlType("积分返利", "20");
	public static final TransDtlType POINT_COUPON = new TransDtlType("积分返券", "21");
	public static final TransDtlType POINT_GIFT = new TransDtlType("积分兑换礼品", "22");
	public static final TransDtlType DISCOUNT_BY_STAGE = new TransDtlType("分期折扣", "60");
	public static final TransDtlType POINT_EXPIRE = new TransDtlType("积分过期", "61");
	
	public static final TransDtlType PRE_AUTH_FINISH = new TransDtlType("预授权完成", "31");
	
	public static final TransDtlType CARD_REFUND = new TransDtlType("卡补账", "70");
	public static final TransDtlType CARD_ADJ = new TransDtlType("卡调账", "71");
	
	/** 消费赠送：使用现金，根据促销活动，赠送积分或赠券 */
	public static final TransDtlType CONSUME_PRESENT = new TransDtlType("消费赠送", "80");	
	
	public static final TransDtlType CANCEL = new TransDtlType("撤销", "90");	
	public static final TransDtlType RETURN_GOODS = new TransDtlType("退货 ", "91");
	public static final TransDtlType REFUND = new TransDtlType("冲正", "92");
	public static final TransDtlType CANCEL_REFUND = new TransDtlType("撤销冲正", "93");
	
	public static final TransDtlType CLEAR_ACCT = new TransDtlType("清卡", "49");
	
	/** 冻结/解冻 */
	public static final TransDtlType FREEZE = new TransDtlType("冻结", "62");
	public static final TransDtlType UNFREEZE = new TransDtlType("解冻", "63");
	
	/** 微信银行卡充值 */
	public static final TransDtlType WX_TRANS_X2 = new TransDtlType("网上银行卡充值", "X2");
	public static final TransDtlType WX_BANK_HANGING = new TransDtlType("网上银行卡充值挂账", "46");
	public static final TransDtlType WX_BANK_HANGING_FINISH = new TransDtlType("充值挂账完成", "47");
	
	/** 提现/汇款*/
	public static final TransDtlType WITHDRAW_T0 = new TransDtlType("快速提现", "52");
	public static final TransDtlType WITHDRAW_T1 = new TransDtlType("普通提现", "53");
	public static final TransDtlType WITHDRAW_MANUAL_T0 = new TransDtlType("手工快速提现", "54");
	public static final TransDtlType WITHDRAW_MANUAL_T1 = new TransDtlType("手工普通提现", "55");
	
	public static final TransDtlType REMIT_T0 = new TransDtlType("快速汇款", "56");
	public static final TransDtlType REMIT_T1 = new TransDtlType("普通汇款", "57");
	public static final TransDtlType REMIT_MANUAL_T0 = new TransDtlType("手工快速汇款", "58");
	public static final TransDtlType REMIT_MANUAL_T1 = new TransDtlType("手工普通汇款", "59");
	
	public static final TransDtlType Fee = new TransDtlType("手续费", "23");
	
	/** 提现消费 */
	public static final TransDtlType WITH_DRAW = new TransDtlType("提现消费（旧）", "08");
	/** 计息 */
	public static final TransDtlType COMPUTE_INTEREST = new TransDtlType("计息", "99");
	/** 结息 */
	public static final TransDtlType SETTLE_INTEREST = new TransDtlType("结息", "98");
	
	protected TransDtlType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static TransDtlType valueOf(String value) {
		TransDtlType type = (TransDtlType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的交易类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(TransDtlType.ALL);
	}
	
	// 交易账户变动明细的交易类型
	public static List getTransAcctDtlType() {
		Map params = new HashMap();
		params.put(SELL_CARD.getValue(), SELL_CARD);
		params.put(RECHARGE.getValue(), RECHARGE);
		params.put(TRANSFER.getValue(), TRANSFER);
		params.put(NORMAL_CONSUME.getValue(), NORMAL_CONSUME);
		params.put(PART_CONSUME.getValue(), PART_CONSUME);
		params.put(TIME_CARD_CONSUME.getValue(), TIME_CARD_CONSUME);
		params.put(POINT_CONSUME.getValue(), POINT_CONSUME);
		params.put(POINT_EXC.getValue(), POINT_EXC);
		params.put(POINT_COUPON.getValue(), POINT_COUPON);
		params.put(POINT_GIFT.getValue(), POINT_GIFT);
		params.put(CANCEL.getValue(), CANCEL);
		params.put(RETURN_GOODS.getValue(), RETURN_GOODS);
		params.put(REFUND.getValue(), REFUND);
		params.put(CANCEL_REFUND.getValue(), CANCEL_REFUND);
		params.put(CARD_REFUND.getValue(), CARD_REFUND);
		params.put(BRUSH_PECHARGE.getValue(), BRUSH_PECHARGE);
		params.put(PRESELL_ACTIVATE.getValue(), PRESELL_ACTIVATE);
		params.put(PRECHARGE_ACTIVATE.getValue(), PRECHARGE_ACTIVATE);
		
		return getOrderedList(params);
	}
	
	// 交易积分变动明细的交易类型
	public static List getTransPointDtlType() {
		Map params = new HashMap();
		params.put(POINT_CONSUME.getValue(), POINT_CONSUME);
		params.put(POINT_EXC.getValue(), POINT_EXC);
		params.put(POINT_COUPON.getValue(), POINT_COUPON);
		params.put(POINT_GIFT.getValue(), POINT_GIFT);
		params.put(DISCOUNT_BY_STAGE.getValue(), DISCOUNT_BY_STAGE);
		params.put(POINT_EXPIRE.getValue(), POINT_EXPIRE);
		params.put(CANCEL.getValue(), CANCEL);
		params.put(RETURN_GOODS.getValue(), RETURN_GOODS);
		params.put(REFUND.getValue(), REFUND);
		params.put(CANCEL_REFUND.getValue(), CANCEL_REFUND);
		
		return getOrderedList(params);
	}
	
}
