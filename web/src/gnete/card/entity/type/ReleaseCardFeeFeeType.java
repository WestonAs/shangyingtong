package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 发卡机构手续费计费方式
 * @author aps-lib
 */
public class ReleaseCardFeeFeeType extends AbstractType {
	public static final Map ALL = new HashMap();
	
	/*
	1: 按交易笔数固定金额
	2: 按交易笔数分段
	3: 按交易笔数分段累计

	5: 按交易金额固定比率单笔保底封顶
	6: 按交易金额分段
	7: 按交易金额分段累进
	*/
	/*
	public static final ReleaseCardFeeFeeType TRADE_NUM = new ReleaseCardFeeFeeType("按交易笔数", "1");
	public static final ReleaseCardFeeFeeType TRADE_NUM_STAGE = new ReleaseCardFeeFeeType("按交易笔数分段", "2");
	public static final ReleaseCardFeeFeeType TRADE_NUM_STAGE_CUM = new ReleaseCardFeeFeeType("按交易笔数分段阶梯收费", "3");
	public static final ReleaseCardFeeFeeType MONEY_RATE_MAX_MIN = new ReleaseCardFeeFeeType("金额固定比例保底封顶", "5");
	public static final ReleaseCardFeeFeeType MONEY_STAGE = new ReleaseCardFeeFeeType("金额分段比例", "6");
	public static final ReleaseCardFeeFeeType MONEY_STAGE_CUM = new ReleaseCardFeeFeeType("金额段阶梯收费", "7");*/
	
	public static final ReleaseCardFeeFeeType TRADE_NUM = new ReleaseCardFeeFeeType("按交易笔数固定金额", "1");
	public static final ReleaseCardFeeFeeType TRADE_NUM_STAGE = new ReleaseCardFeeFeeType("按交易笔数分段", "2");
	public static final ReleaseCardFeeFeeType TRADE_NUM_STAGE_CUM = new ReleaseCardFeeFeeType("按交易笔数分段累计", "3");
	public static final ReleaseCardFeeFeeType MONEY_RATE_MAX_MIN = new ReleaseCardFeeFeeType("按交易金额固定比率单笔保底封顶", "5");
	public static final ReleaseCardFeeFeeType MONEY_STAGE = new ReleaseCardFeeFeeType("按交易金额分段", "6");
	public static final ReleaseCardFeeFeeType MONEY_STAGE_CUM = new ReleaseCardFeeFeeType("按交易金额阶梯收费", "7");
	
	public static ReleaseCardFeeFeeType valueOf(String value) {
		ReleaseCardFeeFeeType type = (ReleaseCardFeeFeeType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("没有该计费方式");
		}

		return type;
	}
	
	@SuppressWarnings("unchecked")
	protected ReleaseCardFeeFeeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
	
	public static List getTradeSum(){
		Map params = new HashMap();
		params.put(TRADE_NUM.getValue(), TRADE_NUM);
		params.put(TRADE_NUM_STAGE.getValue(), TRADE_NUM_STAGE);
		params.put(TRADE_NUM_STAGE_CUM.getValue(), TRADE_NUM_STAGE_CUM);
		return getOrderedList(params);
	}

	/**
	 * 商户手续费交易为储值消费、赠券消费、通用积分、折扣功能时
	 * 对应的计费方式
	 * @return
	 */
	public static List getForMerchFee(){
		Map params = new HashMap();
		params.put(TRADE_NUM.getValue(), TRADE_NUM);
		params.put(MONEY_RATE_MAX_MIN.getValue(), MONEY_RATE_MAX_MIN);
		params.put(MONEY_STAGE.getValue(), MONEY_STAGE);
		params.put(MONEY_STAGE_CUM.getValue(), MONEY_STAGE_CUM);
		return getOrderedList(params);
	}

	/**
	 * 商户手续费交易为次卡消费、专属积分、积分兑换礼品时
	 * 对应的计费方式
	 * @return
	 */
	public static List getForMerchFeeOther(){
		Map params = new HashMap();
		params.put(TRADE_NUM.getValue(), TRADE_NUM);
		params.put(TRADE_NUM_STAGE.getValue(), TRADE_NUM_STAGE);
		params.put(TRADE_NUM_STAGE_CUM.getValue(), TRADE_NUM_STAGE_CUM);
		return getOrderedList(params);
	}
	
}
