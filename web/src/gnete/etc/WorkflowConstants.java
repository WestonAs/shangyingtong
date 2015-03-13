package gnete.etc;

public class WorkflowConstants {

	//***************** 流程适配器名 ***************************************************
	public final static String TEST_ADAPTER = "TestAdapter";
	public final static String TRANS_ACC_ADAPTER = "transAccAdapter";
	public final static String ADJ_ACC_ADAPTER = "adjAccAdapter";
	
	public final static String SALE_CARD_BATCH_ADAPTER = "saleCardBatchAdapter";
	public final static String DEPOSIT_BATCH_ADAPTER = "depositBatchAdapter";

	public final static String PROMOTIONS_ADAPTER = "promotionsAdapter";
	public final static String PROTOCOL_ADAPTER = "protocolAdapter";
	
	public final static String CARDSUBCLASS_ADAPTER = "cardSubClassDefAdapter";
	
	public final static String BRANCH_ADAPTER = "branchAdapter";
	public final static String MERCH_ADAPTER = "merchAdapter";
	public final static String MERCH_CARD_ADAPTER = "merchCardAdapter";
	
	/** 抽奖活动审核适配器名 */
	public final static String LOTTERY_ADAPTER = "lotteryAdapter";
	
	/** 卡BIN前三位审核流程适配器 */
	public final static String CARD_BIN_PREX_REG_ADAPTER = "cardBinPrexRegAdapter";
	/** 运营机构卡BIN审核流程适配器 */
	public final static String CARD_BIN_FENZHI_REG_ADAPTER = "cardBinFenzhiRegAdapter";

	/** 售卡撤销审核流程适配器名  */
	public final static String SALE_CARD_CANCEL_ADAPTER = "saleCardCancelAdapter";
	
	/** 退卡销户审核流程适配器 */
	public final static String CANCEL_CARD_ADAPTER = "cancelCardAdapter";
	/** 挂失审核流程适配 */
	public final static String LOSS_CARD_ADAPTER = "lossCardAdapter";

	/** 换卡流程审核适配器 2012-6-14新加 */
	public final static String RENEW_CARD_ADAPTER = "renewCardAdapter";

	/** 卡延期流程审核适配器 2012-6-14新加 */
	public final static String CARD_DEFFER_ADAPTER = "cardDefferAdapter";

	/** 积分充值流程审核适配器 2012-6-25新加 */
	public final static String DEPOSIT_POINT_ADAPTER = "depositPointAdapter";
	
	/** 高级折扣流程审核适配器 */
	public final static String GREAT_DISCNT_PROTCL_DEF_ADAPTER = "greatDiscntProtclDefAdapter";
	
	/** 赠券赠送 审核适配器 */
	public final static String DEPOSIT_COUPON_ADAPTER = "depositCouponAdapter";
	
	/** 外部卡开卡 审核适配器*/
	public final static String EXTERNAL_NUM_MAKE_CARD_ADAPTER = "externalNumMakeCardAdapter";
	
	/** 缴交会费 审核适配器*/
	public final static String WASH_CAR_SVC_MBSHIP_DUES_ADAPTER = "washCarSvcMbShipDuesAdapter";
	
	
	
	/* *************************************************************************************
	 *			 流程名
	 ***************************************************************************************/
	
	/** 发卡机构卡BIN申请审核流程 */
	public static final String CARD_BIN_REG = "CardBinReg";
	
	/** 卡BIN前三位申请审核流程 */
	public static final String WORKFLOW_CARD_BIN_PREX_REG = "CardBinPrexReg";
	
	/** 分支机构卡BIN分配审核流程 */
	public static final String WORKFLOW_CARD_BIN_FENZHI_REG = "CardBinFenzhiReg";

	/** 卡子类型审核流程 */
	public static final String CARD_SUB_CLASS_DEF = "CardSubClassDef";

	/**  制卡申请审核流程 */
	public final static String MAKE_CARD_APP = "MakeCardApp";

	/** 默认节点对应的REF_ID */
	public final static String DEFAULT_REF_ID = "0";

	/** 成品卡入库审核流程 */
	public final static String FINISHED_CARD_STOCK = "FinishedCardStock";

	/** 白卡入库审核流程 */
	public final static String WHITE_CARD_STOCK = "WhiteCardStock";

	/** 领卡审核流程 */
	public final static String CARD_RECEIVE = "CardReceive";

	/** 返库审核流程 */
	public final static String CARD_WITHDRAW = "CardWithdraw";

	/** 促销活动规则定义审核流程 */
	public final static String PROMOTIONS_DEFINE = "PromotionsDefine";
	
	/** 协议积分活动审核流程 */
	public final static String PROTOCOL_DEFINE = "ProtocolDefine";
	
	/** 抽奖活动定义审核流程 */
	public final static String DRAW_DEFINE = "DrawDefine";
	
	/** 转帐流程 */
	public static final String WORKFLOW_TRANSFER = "TransAcc";
	
	/** 调账流程 */
	public static final String WORKFLOW_ADJ = "AdjAcc";

	/** 配额流程 */
	public static final String WORKFLOW_BRANCH_SELL_REG = "BranchSellReg";

	/** 批量售卡审批流程 */
	public static final String WORKFLOW_SALE_CARD_BATCH = "SaleCardBatch";
	
	/** 售卡撤销审核流程 */
	public final static String WORKFLOW_SALECARD_CANCEL = "SaleCardCancel";
	
	/** 批量充值审核流程 */
	public static final String WORKFLOW_DEPOSIT_BATCH = "DepositBatch";
	
	/** 充值撤销审核流程 */
	public final static String WORKFLOW_DEPOSIT_CANCEL = "DepositCancel";

	/** 新增机构审核流程 */
	public static final String WORKFLOW_ADD_BRANCH = "BranchAddCheck";

	/** 新增商户审核流程(运营中心或分支机构或运营代理添加商户时启动本流程) */
	public static final String WORKFLOW_ADD_MERCH = "MerchAddCheck";

	/** 新增商户审核流程(发卡机构添加商户时启动本流程) */
	public static final String WORKFLOW_CARD_ADD_MERCH = "MerchAddCardCheck";
	
	/** 退卡销户审核流程 */
	public static final String WORKFLOW_CANCEL_CARD = "CancelCardCheck";

	/** 挂失审核流程 */
	public static final String WORKFLOW_LOSS_CARD = "LossCardCheck";

	/** 换卡审核流程 2012-6-14 新加 */
	public static final String WORKFLOW_RENEW_CARD = "RenewCardCheck";
	
	/** 卡延期审核流程 2012-6-14 新加 */
	public final static String WORKFLOW_CARD_DEFFER = "CardDefferCheck";
	
	/** 积分制充值审核流程 2012-6-25 新加 */
	public final static String WORKFLOW_DEPOSIT_POINT = "DepositPointCheck";
	
	/** 高级折扣 */
	public static final String WORKFLOW_GREAT_DISCNT_PROTCL_DEF = "GreatDiscntProtclDef";
	
	/** 赠券赠送 */
	public final static String WORKFLOW_DEPOSIT_COUPON = "DepositCouponCheck";
	
	/** 外部号码开卡审核流程 */
	public final static String WORKFLOW_EXTERNAL_NUM_MAKE_CARD = "ExternalNumMakeCardCheck";
	
	/** 缴交会费审核流程 */
	public final static String WASH_CAR_SVC_MB_SHIP_DUES = "WashCarSvcMbShipDuesCheck";
	
	
}
