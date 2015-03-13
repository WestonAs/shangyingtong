package gnete.etc;

import java.math.BigDecimal;

public abstract class Constants {
	
	//手续费最大金额上限
	public static final BigDecimal FEE_MAXACCOUNT = new BigDecimal("999999999999.99"); 
	// 全部权限点.
	public static final String USER_PRIVILEGE = "USER_PRIVILEGE";
	
	// 权限资源.
	public static final String USER_PRIVILEGE_RES = "USER_PRIVILEGE_RES";
	
	// 用户菜单树.
	public static final String USER_MENU = "USER_MENU";
	
	// 营运中心代码
	public static final String CENTER = "00000000";

	// 积分用途默认代码
	public static final String PT_USE_LIMIT_DEFAULT = "000000000000000000000000000000";
	
	/**
	 * 默认查询密码（123456加密后的密文）
	 */
	public static final String DEFUALT_SEARCH_PW = "ZGV9FQWBANZ2IIHNEJ0+JA==";
	
	/**
	 * 促销活动信息
	 */
	public static final String PROMTDEF_OBJECT_INFO = "promtDef_Object_Info";
	
	/**
	 * 促销活动范围
	 */
	public static final String PROMTSCOPE_LIST = "promtScope_List";
	
	/**
	 * 促销活动规则类型
	 */
	public static final String PROMTRULE_LIST = "promtRule_List";
	
	// 用户菜单列表.
	public static final String USER_MENU_LIST = "USER_MENU_LIST";
	public static final String SESSION_USER = "SESSION_USER";
	public static final String PAGE_SIZE = "pageSize";
	public static final int DEFAULT_PAGE_SIZE = 20;
	public static final int DEFAULT_SELECT_PAGE_SIZE = 10;
	
	//********************************************************************
    public static final String OPER_INFO="OPER_INFO";
	public static final String SAVE_SUCCESS="保存成功";
	public static final String SAVE_FAIL="保存失败";
	public static final String UPDATE_SUCCESS="修改成功";
	public static final String UPDATE_FAIL="修改失败";
	public static final String DELETE_SUCCESS="删除成功";
	public static final String DELETE_FAIL="删除失败";
//********************************************************************
	public static final String DEFAULT_SYSUSER = "system";   //缺省系统用户
	
	public static final String ENT_JF_PARENTPATH= "080";    //积分充值及账户变动文件根目录(例如/home/card/ftpDir/)
	
	public static final String WYT_REMOTE_SERVER_CODE = "082";  //网银通FTP服务器地址
	public static final String WYT_REMOTE_USER_CODE = "083";    //网银通FTP服务器用户名
	public static final String WYT_REMOTE_PWD_CODE = "084";     //网银通FTP服务器口令
	
//******************************************************************
	public static final String FILE_SPLIT = "_";                      //文件组成连接符
	public static final String ZIP_SUFFIX = "zip";                  //ZIP文件	
	public static final String TXT_SUFFIX = "txt";                //TXT文件
	//******************************************************************
//系统日志类型
	public static final String LOG_SYS_INFO="I";  //信息
	public static final String LOG_SYS_WARNING="W"; //警告
	public static final String LOG_SYS_ERROR="E"; //错误
	public static final String LOG_SYS_TYPE="I,W,E";
	
	//系统日志类别
	public static final String LOG_SYS_S="S";  //管理员操作产生
	public static final String LOG_SYS_U="U";  //业务人员产生
	public static final String LOG_SYS_CLASS="S,U";
	
	//系统日志状态
	public static final String LOG_SYS_STAT_VIEW="00";
	public static final String LOG_SYS_STAT_NO_VIEW="11";
	
	//用户日志类型
	public static final String LOG_USER_S="S";  //查询
	public static final String LOG_USER_A="A";  //新增
	public static final String LOG_USER_U="U";  //修改
	public static final String LOG_USER_D="D";  //删除
	public static final String LOG_USER_C="C";  //审核
	public static final String LOG_USER_O="O";  //其他
	public static final String LOG_USER_TYPE="S,A,U,D,C,O";

	
	public static final String SYSUSER = "system";
	
	// 根节点权限编号.
	public static final String ROOT_PRIVILEGE_CODE = "00";
	public static final String ADD_MAG_PRIVILEGE_CODE = "0601";
	public static final String LOSS_CARD_PRIVILEGE_CODE = "0602";
	public static final String UNLOSS_CARD_PRIVILEGE_CODE = "0603";
	public static final String RENEW_CARD_PRIVILEGE_CODE = "0604";
	public static final String OVER_DRAFT_LMT_PRIVILEGE_CODE = "0512";
	public static final String CARD_DEFER_PRIVILEGE_CODE = "0508";
	public static final String FREEZE_PRIVILEGE_CODE = "0510";
	public static final String UNFREEZE_PRIVILEGE_CODE = "0511";
	public static final String POINT_EXC_GIFT_PRIVILEGE_CODE = "0610";
	
	// 充值权限点
	public static final String PRIVILEGE_DEPOSIT_CARD = "depositCard_add";
	public static final String PRIVILEGE_DEPOSIT_BAT_CARD = "depositCardBatDeposit_add";
	public static final String PRIVILEGE_DISCNT_BAT_CARD = "depositCardBatDiscnt_add";
	public static final String PRIVILEGE_ACCU_BAT_CARD = "depositCardBatAccu_add";
	public static final String DEPOSITBATREGFILE_ADD = "depositBatRegFile_add";
	
	// 单张售卡权限点
	public static final String PRIVILEGE_SALE_DEPOSIT = "saleCardDeposit_add";
	public static final String PRIVILEGE_SALE_MEMB = "saleCardMemb_add";
	public static final String PRIVILEGE_SALE_DISCNT = "saleCardDiscnt_add";
	public static final String PRIVILEGE_SALE_ACCU = "saleCardAccu_add";
	
	// 单张预售权限点
	public static final String PRIVILEGE_PRE_SALE_DEPOSIT = "saleCardPreDeposit_add";
	public static final String PRIVILEGE_PRE_SALE_MEMB = "saleCardPreMemb_add";
	public static final String PRIVILEGE_PRE_SALE_DISCNT = "saleCardPreDiscnt_add";
	public static final String PRIVILEGE_PRE_SALE_ACCU = "saleCardPreAccu_add";
	
	// 批量售卡权限点
	public static final String PRIVILEGE_BAT_SALE_DEPOSIT = "saleCardBatDeposit_add";
	public static final String PRIVILEGE_BAT_SALE_DISCNT = "saleCardBatDiscnt_add";
	public static final String PRIVILEGE_BAT_SALE_ACCU = "saleCardBatAccu_add";
	public static final String PRIVILEGE_BAT_SALE_MEMB = "saleCardBatMemb_add";
	
	// 批量预售权限点
	public static final String PRIVILEGE_PRE_BAT_SALE_DEPOSIT = "saleCardBatPreDeposit_add";
	public static final String PRIVILEGE_PRE_BAT_SALE_DISCNT = "saleCardBatPreDiscnt_add";
	public static final String PRIVILEGE_PRE_BAT_SALE_ACCU = "saleCardBatPreAccu_add";
	public static final String PRIVILEGE_PRE_BAT_SALE_MEMB = "saleCardBatPreMemb_add";
	
	public static final String WORKFLOW_GIFT_DEF = "GiftDef";
	
	// 客户返利流程
	public static final String WORKFLOW_CUSTOMER_REBATE_REG = "CustomerRebateReg";
	
	// 签单规则流程
	public static final String WORKFLOW_SIGN_RULE_REG = "SignRuleReg";
	
	// 会员定义流程
	public static final String WORKFLOW_MEMB_CLASS_DEF = "MembClassDef";
	
	// 卡补账流程
	public static final String WORKFLOW_RETRANS_CARD_REG = "RetransCardReg";
	
	// 卡调账流程
	public static final String WORKFLOW_ADJ_ACC_REG = "AdjAccReg";
	
	// 准备金流程
	public static final String WORKFLOW_CARD_RISK_REG = "CardRiskReg";
	
	// 透支额度调整流程
	public static final String WORKFLOW_OVERDRAFT_LMT_REG = "OverdraftLmtReg";
	
	// 密码重置流程
	public static final String WORKFLOW_PASSWORD_RESET_REG = "PasswordResetReg";
	
	// 试算平衡流程
	public static final String WORKFLOW_TRAIL_BALANCE_REG = "TrailBalanceReg";
	
	// 积分调整流程
	public static final String WORKFLOW_POINT_CHG_REG = "PointChgReg";

	// 赠送赠券流程
	public static final String WORKFLOW_COUPON_AWARD_REG = "CouponAwardReg";
	
	public static final String START_PRIVILEGE = "###WWW";
	
	
}
