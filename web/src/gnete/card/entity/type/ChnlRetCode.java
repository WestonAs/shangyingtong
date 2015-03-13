package gnete.card.entity.type;


import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 向商户反馈查询交易代码.
 * 
 * @author aps-mhc
 */
public class ChnlRetCode extends AbstractType {
	public static final Map ALL = new java.util.LinkedHashMap();

	public static final ChnlRetCode C_0000 = new ChnlRetCode("[process finished]", "处理完成或接收成功", "0000");
	public static final ChnlRetCode C_0001 = new ChnlRetCode("[system process failed]", "系统处理失败", "0001");
	public static final ChnlRetCode C_0002 = new ChnlRetCode("[canceled]", "已撤销", "0002");

	public static final ChnlRetCode C_1000 = new ChnlRetCode("[data error or process error]", "报文内容检查错或者处理错", "1000");
	public static final ChnlRetCode C_1001 = new ChnlRetCode("[data parse error]", "报文解释错", "1001");
	public static final ChnlRetCode C_1002 = new ChnlRetCode("[cannot query transaction, can re-send later]",
			"无法查询到该交易，可以重发", "1002");

	public static final ChnlRetCode C_2000 = new ChnlRetCode("[system is processing]", "系统正在对数据处理", "2000");
	public static final ChnlRetCode C_2001 = new ChnlRetCode("[waiting for merchant audit]", "等待商户审核", "2001");
	public static final ChnlRetCode C_2002 = new ChnlRetCode("[merchant audit failed]", "商户审核不通过", "2002");
	public static final ChnlRetCode C_2003 = new ChnlRetCode("[waiting for sevice provider accept]", "等待银联网络受理", "2003");
	public static final ChnlRetCode C_2004 = new ChnlRetCode("[gnete.com acceptance not passed]", "银联网络不通过受理", "2004");
	public static final ChnlRetCode C_2005 = new ChnlRetCode("[waiting for gnete.com to check]", "等待银联网络复核", "2005");
	public static final ChnlRetCode C_2006 = new ChnlRetCode("[gnete.com check not passed]", "银联网络不通过复核", "2006");
	public static final ChnlRetCode C_2007 = new ChnlRetCode("[submit to bank process]", "提交银行处理", "2007");

	public static final ChnlRetCode C_3028 = new ChnlRetCode("[system busy]", "系统繁忙", "3028");
	public static final ChnlRetCode C_3045 = new ChnlRetCode("[protocol is not effective]", "协议未生效", "3045");
	public static final ChnlRetCode C_3097 = new ChnlRetCode("[channel not support or merchant not support channel]",
			"渠道不支持或者商户不支持此渠道", "3097");

	public static final ChnlRetCode D_3000 = new ChnlRetCode("[transaction success]", "交易成功", "3000");
	public static final ChnlRetCode D_3001 = new ChnlRetCode("[check account holder's reason]", "查开户方原因", "3001");
	public static final ChnlRetCode D_3002 = new ChnlRetCode("[confiscated card]", "没收卡", "3002");
	public static final ChnlRetCode D_3003 = new ChnlRetCode("[not accepted]", "不予承兑", "3003");
	public static final ChnlRetCode D_3004 = new ChnlRetCode("[invalid card No.]", "无效卡号", "3004");
	public static final ChnlRetCode D_3005 = new ChnlRetCode("[contact to secrecy dep.]", "受卡方与安全保密部门联系", "3005");
	public static final ChnlRetCode D_3006 = new ChnlRetCode("[card already report the loss]", "已挂失卡", "3006");
	public static final ChnlRetCode D_3007 = new ChnlRetCode("[lost card]", "被窃卡", "3007");
	public static final ChnlRetCode D_3008 = new ChnlRetCode("[balance not enought]", "余额不足", "3008");
	public static final ChnlRetCode D_3009 = new ChnlRetCode("[account not existed]", "无此账户", "3009");
	public static final ChnlRetCode D_3010 = new ChnlRetCode("[expired card]", "过期卡", "3010");
	public static final ChnlRetCode D_3011 = new ChnlRetCode("[password error]", "密码错", "3011");
	public static final ChnlRetCode D_3012 = new ChnlRetCode("[card holder's transaction rejected]", "不允许持卡人进行的交易",
			"3012");
	public static final ChnlRetCode D_3013 = new ChnlRetCode("[exceed withdraw limits]", "超出提款限额", "3013");
	public static final ChnlRetCode D_3014 = new ChnlRetCode("[original amount error]", "原始金额不正确", "3014");
	public static final ChnlRetCode D_3015 = new ChnlRetCode("[exceed number limit of withdraw]", "超出取款次数限制", "3015");
	public static final ChnlRetCode D_3016 = new ChnlRetCode("[report loss bank book]", "已挂失折", "3016");
	public static final ChnlRetCode D_3017 = new ChnlRetCode("[account freezed]", "账户已冻结", "3017");
	public static final ChnlRetCode D_3018 = new ChnlRetCode("[account closed]", "已清户", "3018");
	public static final ChnlRetCode D_3019 = new ChnlRetCode("[original transaction cancedl or reversed]", "原交易已被取消或冲正",
			"3019");
	public static final ChnlRetCode D_3020 = new ChnlRetCode("[account locked temporarily]", "账户被临时锁定", "3020");
	public static final ChnlRetCode D_3021 = new ChnlRetCode("[unregister lines exceed limits]", "未登折行数超限", "3021");
	public static final ChnlRetCode D_3022 = new ChnlRetCode("[bank book No. error]", "存折号码有误", "3022");
	public static final ChnlRetCode D_3023 = new ChnlRetCode("[can not withdraw the amount deposited on the same day]",
			"当日存入的金额当日不能支取", "3023");
	public static final ChnlRetCode D_3024 = new ChnlRetCode("[date switch is processing]", "日期切换正在处理", "3024");
	public static final ChnlRetCode D_3025 = new ChnlRetCode("[PIN format error]", "PIN格式出错 ", "3025");
	public static final ChnlRetCode D_3026 = new ChnlRetCode("[card issuer secercy subsystem failed]", "发卡方保密子系统失败",
			"3026");
	public static final ChnlRetCode D_3027 = new ChnlRetCode("[original transaction not success]", "原始交易不成功", "3027");
	public static final ChnlRetCode D_3028 = new ChnlRetCode("[system busy, submit later]", "系统忙，请稍后再提交", "3028");
	public static final ChnlRetCode D_3029 = new ChnlRetCode("[transaction reversed]", "交易已被冲正", "3029");
	public static final ChnlRetCode D_3030 = new ChnlRetCode("[account No. error]", "账号错误", "3030");
	public static final ChnlRetCode D_3031 = new ChnlRetCode("[account name error]", "账号户名不符", "3031");
	public static final ChnlRetCode D_3032 = new ChnlRetCode("[account currency error]", "账号货币不符", "3032");
	public static final ChnlRetCode D_3033 = new ChnlRetCode("[no original transaction]", "无此原交易", "3033");
	public static final ChnlRetCode D_3034 = new ChnlRetCode("[not current account, or old accont]", "非活期账号，或为旧账号",
			"3034");
	public static final ChnlRetCode D_3035 = new ChnlRetCode("[no original record]", "找不到原记录", "3035");
	public static final ChnlRetCode D_3036 = new ChnlRetCode("[currency error]", "货币错误", "3036");
	public static final ChnlRetCode D_3037 = new ChnlRetCode("[card not valid]", "磁卡未生效", "3037");
	public static final ChnlRetCode D_3038 = new ChnlRetCode("[not valid account]", "非通兑户", "3038");
	public static final ChnlRetCode D_3039 = new ChnlRetCode("[account closed]", "账户已关户", "3039");
	public static final ChnlRetCode D_3040 = new ChnlRetCode("[amount error]", "金额错误", "3040");
	public static final ChnlRetCode D_3041 = new ChnlRetCode("[not bank book account]", "非存折户", "3041");
	public static final ChnlRetCode D_3042 = new ChnlRetCode("[amount less than least amount]", "交易金额小于该储种的最低支取金额",
			"3042");
	public static final ChnlRetCode D_3043 = new ChnlRetCode("[not singed with bank]", "未与银行签约", "3043");
	public static final ChnlRetCode D_3044 = new ChnlRetCode("[timeout rejected]", "超时拒付", "3044");
	public static final ChnlRetCode D_3045 = new ChnlRetCode("[protocol No. not exist]", "合同（协议）号在协议库里不存在", "3045");
	public static final ChnlRetCode D_3046 = new ChnlRetCode("[protocol not valid]", "合同（协议）号还没有生效", "3046");
	public static final ChnlRetCode D_3047 = new ChnlRetCode("[protocol canceled]", "合同（协议）号已撤销", "3047");
	public static final ChnlRetCode D_3048 = new ChnlRetCode("[transaction settled, can not cancel]", "业务已经清算，不能撤销",
			"3048");
	public static final ChnlRetCode D_3049 = new ChnlRetCode("[transaction rejected, can not cancel]", "业务已被拒绝，不能撤销",
			"3049");
	public static final ChnlRetCode D_3050 = new ChnlRetCode("[transaction canceled]", "业务已撤销", "3050");
	public static final ChnlRetCode D_3051 = new ChnlRetCode("[existed transaction]", "重复业务", "3051");
	public static final ChnlRetCode D_3052 = new ChnlRetCode("[no original transaction]", "找不到原业务", "3052");
	public static final ChnlRetCode D_3053 = new ChnlRetCode("[batch return receipt not reach least period limits]",
			"批量回执包未到规定最短回执期限（M日） ", "3053");
	public static final ChnlRetCode D_3054 = new ChnlRetCode("[batch return receipt exceeds period limits]",
			"批量回执包超过规定最长回执期限（N日） ", "3054");
	public static final ChnlRetCode D_3055 = new ChnlRetCode("[transaction amount exceeds bank limits]",
			"当日通兑业务累计金额超过规定金额", "3055");
	public static final ChnlRetCode D_3056 = new ChnlRetCode("[transaction refund]", "退票", "3056");
	public static final ChnlRetCode D_3057 = new ChnlRetCode("[account state error]", "账户状态错误", "3057");
	public static final ChnlRetCode D_3058 = new ChnlRetCode("[SIGN_MSG or certificate error]", "数字签名或证书错", "3058");
	public static final ChnlRetCode D_3059 = new ChnlRetCode(
			"[the result which channel returned  is not clear, system set failure directly]", "渠道返回结果不明确，系统直接置失败",
			"3059");
	public static final ChnlRetCode D_3097 = new ChnlRetCode("[system can not process this account]", "系统不能对该账号进行处理",
			"3097");
	public static final ChnlRetCode D_3999 = new ChnlRetCode("[transaction failed]", "交易失败", "3999");

	public static ChnlRetCode valueOf(String value) {
		ChnlRetCode type = (ChnlRetCode) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("商户反馈代码错误");
		}

		return type;
	}

	private String enName;

	@SuppressWarnings("unchecked")
	protected ChnlRetCode(String enName, String name, String value) {
		super(name, value);
		this.enName = enName;
		ALL.put(value, this);
	}

	public String getEnName() {
		return this.enName;
	}
}
