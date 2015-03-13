package gnete.card.msg;

public class MsgType {

	/*
		 报文类型    内容组成(多个ID用,分隔)    	功能说明
		  1001		登记薄ID					卡片建档
		  1002		登记薄ID					卡片建档撤销
		  1003		登记薄ID					成品卡入库
  		  1004		登记薄ID					领卡
		  1005		登记薄ID					卡片返库
		  1006		登记薄ID					现场开奖
		  
		  2001		登记薄ID					售卡
		  2002		登记薄ID					售签单卡
		  2003		登记薄ID					充值
		  2004		登记薄ID					挂失
		  2005		登记薄ID					解挂
		  2006		登记薄ID					补磁
		  2007		登记薄ID					换卡
		  2008		登记薄ID					卡调账
		  2009		登记薄ID					卡补账
		  2010		登记薄ID					卡转帐
		  2011		登记薄ID					冻结
		  2012		登记薄ID					解付
		  2013		登记薄ID					退卡销户
		  2014		登记薄ID					礼品兑换
		  2015		登记薄ID					积分调整
		  2016		登记薄ID					积分返利
		  2017		登记薄ID					账户透支额度
		  2018		登记薄ID					卡延期
		  2019		登记簿ID					准备金调整
		  2021		登记簿ID					批量派赠赠券卡
		  
		  2022 		登记簿ID					预售卡激活
		  2023 		登记簿ID					预充值激活
		  2024		登记簿ID                 积分兑换赠券
		  2025		登记簿ID         			密码重置
		  2026       登记簿ID     			试算平衡
		  2027       登记簿ID     			充值撤销
		  2028		登记簿ID					售卡撤销
		  2029		登记簿ID					退卡销户

		  2034		登记簿ID					IC卡现金充值
		  2035		登记簿ID					IC卡现金充值冲正
		  2037		登记簿ID					IC卡激活
		  2201		登记簿ID					IC卡片参数修改
		  2202		登记簿ID					IC卡换卡
		  2203		登记簿ID					IC卡销卡
		  2204		登记簿ID					IC卡冲正

		  2038		登记簿ID					积分充值
		 
		  
		  3001		登记簿ID					外部卡导入
		  3002		登记簿ID					积分充值及账户变更
		  3003		登记簿ID					短信发送
		  
		  4001		代收流水					代收手动发起
		  4002       代收流水					代收确认
		  4003		登记簿ID					保德会员注册文件上传
		  
		  4005		登记簿ID					抽奖活动
		  4006		登记簿ID					会员关联
		  4007		登记簿ID					会员级别变更

		  4008		登记簿ID					积分累积赠送 明细汇总请求
		  4009		登记簿ID					积分累积赠送 实际赠送请求

		  4010		登记簿ID					赠送赠券

		  6001		登记簿ID					赠送赠券

		  7001		登记簿ID					实名卡充值调账补帐
	 */
	
	//====================== 制卡相关 =======================
	/** 1001 建档 */
	public static final String CREATE_CARD_FILE = "1001"; 
	
	/** 1002 建档撤销 */
	public static final String CREATE_CARD_UNDO = "1002"; 
	
	/** 1003 成品卡入库 */
	public static final String FINISHED_CARD_INPUT = "1003"; 
	
	/** 1004 领卡 */
	public static final String RECEIVE_CARD = "1004"; 
	
	/** 1005 卡片返库 */
	public static final String WITHDRAW_CARD = "1005";
	
	/** 1006 现场开奖 */
	public static final String RUN_LOTTERY = "1006"; 
	
	//================== 售卡及综合业务============================
	/** 2001 售卡 */
	public static final String SALE_CARD = "2001"; 
	
	/** 2002 售签单卡 */
	public static final String SALE_SIGNBILL = "2002"; 
	
	/** 2003 充值 */
	public static final String DEPOSIT = "2003"; 
	
	/** 2004 挂失 */
	public static final String LOSS_CARD = "2004"; 
	
	/** 2005 解挂 */
	public static final String UNLOSS_CARD = "2005"; 
	
	/** 2006 补磁 */
	public static final String ADD_MAG = "2006"; 
	
	/** 2007 换卡 */
	public static final String RENEW_CARD = "2007"; 
	
	/** 2008 卡调账 */
	public static final String ADJ_ACC = "2008"; 
	
	/** 2009 卡补账 */
	public static final String RETRANS_CARD = "2009"; 
	
	/** 2010 卡转帐 */
	public static final String TRANS_ACC = "2010"; 
	
	/** 2011 冻结 */
	public static final String FREEZE = "2011"; 
	
	/** 2012 解付 */
	public static final String UNFREEZE = "2012"; 
	
	/** 2013 退卡销户 */
	public static final String CANCEL_CARD = "2013"; 
	
	/** 2014 礼品兑换 */
	public static final String GIFT_EXC = "2014"; 
	
	/** 2015 积分调整 */
	public static final String POINT_CHG = "2015"; 
	
	/** 2016 积分返利 */
	public static final String POINT_EXC = "2016"; 
	
	/** 2017 账户透支额度 */
	public static final String OVERDRAFT_LMT = "2017"; 
	
	/** 2018 卡延期 */
	public static final String CARD_DEFER = "2018"; 
	
	/** 2019 准备金调整 */
	public static final String CARD_RISK = "2019";

//	/**
//	 * 批量售卡
//	 */
//	public static final String SALE_CARD_BAT = "2020"; 
	
	/** 2021 批量派赠赠券卡 */
	public static final String COUPON_BAT_REG = "2021"; 
	
	/** 2022 预售卡激活 */
	public static final String PRE_SELL_ACTIVATE = "2022"; 
	
	/** 2023 预充值激活 */
	public static final String PRE_DEPOSIT_ACTIVATE = "2023";
	
	/** 2024积分兑换赠券 */
	public static final String POINT_EXC_COUPON = "2024"; 
	
	/** 2025密码重置 */
	public static final String PASSWORD_RESET = "2025"; 
	
	/** 2026试算平衡 */
	public static final String TRAIL_BALANCE = "2026"; 
	
	/** 2027充值撤销 */
	public static final String DEPOSIT_CANCEL = "2027"; 

	/** 2028售卡撤销 */
	public static final String SELL_CARD_CANCEL = "2028"; 
	
	/** 2034IC卡电子现金充值 */
	public static final String IC_ECASH_DEPOSIT = "2034"; 

	/** 2035 IC卡冲正 Reversal */
	public static final String IC_ECASH_REVERSAL = "2035"; 
	
	/** 2037 IC卡激活 */
	public static final String IC_CARD_ACTIVE = "2037"; 

	/** 2201 IC卡片参数修改 */
	public static final String IC_CHANGE_PARA = "2201"; 

	/** 2202 IC卡换卡 */
	public static final String IC_RENEW_CARD = "2202"; 

	/** 2203 IC卡销卡 */
	public static final String IC_CANCEL_CARD = "2203"; 

	/** 2204 IC卡冲正 */
	public static final String IC_CARD_REVERSAL = "2204"; 

	/** 2038 积分充值 */
	public static final String DEPOSIT_POINT = "2038"; 
	
	
	/** 3001 外部卡导入 */
	public static final String EXTERNAL_CARD_IMPORT = "3001";
	
	/** 3002 积分充值及账户变更 */
	public static final String POINT_ACC = "3002";
	
	/** 3003 短信发送 */
	public static final String MESSAGE_SEND = "3003";

	/** 4001 代收手动发起 */
	public static final String COLLECT_MANUAL = "4001";

	/** 4002 代收确认 */
	public static final String COLLECT_CONFIRM = "4002";

	/** 4003 会员注册文件导入 */
	public static final String MEMB_IMPORT = "4003";
	
	/** 4005 抽奖活动 */
	public static final String DRAW_AWARD = "4005";
	
	/** 4006 会员关联 */
	public static final String MEMB_APPOINT = "4006";
	
	/** 4007 会员级别变更 */
	public static final String MEMB_LEVLE = "4007";
	
	/** 4008 积分累积赠送 明细汇总请求 */
	public static final String POINT_ACCUMULATION_SEND_SUMMARIZING = "4008";
	
	/** 4009 积分累积赠送 实际赠送请求 */
	public static final String POINT_ACCUMULATION_SEND = "4009";

	/** 4010 赠券赠送(不用) */
	public static final String COUPON_AWARD = "4010";
	
	/** 赠券赠送 */
	public static final String DEPOSIT_COUPON = "6001";
	
	/** 银行卡绑定/解绑/默认卡 */
	public static final String WS_BANK_VER_BINDING_REG = "9003";

	//============== 微信相关业务 =======================
	/** 实名卡充值调账补帐 */
	public static final String WX_DEPOSIT_RECO = "7001";
	
}
