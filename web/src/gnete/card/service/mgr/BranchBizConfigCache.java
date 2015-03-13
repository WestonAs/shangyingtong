package gnete.card.service.mgr;

import flink.util.SpringContext;
import gnete.card.dao.BranchBizConfigDAO;
import gnete.card.entity.BranchBizConfig;
import gnete.util.CacheMap;

import org.apache.commons.lang.StringUtils;

/**
 * @Description: 机构简单业务配置缓存类
 */
public class BranchBizConfigCache {
	private static final BranchBizConfigCache	instance			= new BranchBizConfigCache();

	private CacheMap<String, BranchBizConfig>	branchBizConfigMap	= new CacheMap<String, BranchBizConfig>(
																			3 * 60);

	private BranchBizConfigDAO					branchBizConfigDAO;

	private BranchBizConfigCache() {
		if (this.branchBizConfigDAO == null) {
			branchBizConfigDAO = (BranchBizConfigDAO) SpringContext.getService("branchBizConfigDAOImpl");
		}
	}

	public static BranchBizConfigCache getInstance() {
		return instance;
	}

	/** 是否 需要"读M1卡卡号" */
	public static boolean isNeedReadM1CardId(String cardIssuerCode) {
		return isConfigYes(cardIssuerCode, "need_readM1CardId");
	}

	/** 是否 需要"读IC卡卡号" （管理系统售卡、充值时） */
	public static boolean isNeedReadIcCardId(String cardIssuerCode) {
		return isConfigYes(cardIssuerCode, "need_readIcCardId");
	}

	/** 是否 需要会员银行账户信息 */
	public static boolean isNeedMembBankAcctInfo(String cardIssuerCode) {
		return isConfigYes(cardIssuerCode, "need_membBankAcctInfo");
	}

	/** 是否 禁止发卡机构日报表中生成售卡明细页 */
	public static boolean isDisableDayReportSellCardDetailSheet(String cardIssuerCode) {
		return isConfigYes(cardIssuerCode, "disable_dayReport_sellCardDetailSheet");
	}

	/** 对于以下发卡机构的卡，充值时使用“卡的领卡机构”设置充值登记记录的充值机构字段 */
	public static boolean isSetDepositBranchByAppOrgId(String cardIssuerCode) {
		return isConfigYes(cardIssuerCode, "setDepositBranchByAppOrgId");
	}

	/** 是否 需要对“返利资金子账户”充值功能功能 */
	public static boolean isNeedDepositRebateAcct(String cardIssuerCode) {
		return isConfigYes(cardIssuerCode, "needDepositRebateAcct");
	}

	/** 是否 证书用户操作才能“持卡人信息录入”的发卡机构 */
	public static boolean isCertUserCardExtroInfoIssuers(String cardIssuerCode) {
		return isConfigYes(cardIssuerCode, "certUser_cardExtroInfo");
	}

	/** 是否 需要查看“新卡首次充值”交易明细 */
	public static boolean isNeedNewCardFirstDepoist(String cardIssuerCode) {
		return isConfigYes(cardIssuerCode, "need_newCard_firstDeposit");
	}

	/** 是否 需要导出Excel功能（历史交易明细等） */
	public static boolean isNeedExportExcel(String cardIssuerCode) {
		return isConfigYes(cardIssuerCode, "need_exportExcel");
	}
	
	private static boolean isConfigYes(String cardIssuerCode, String configType) {
		String value = getValue(cardIssuerCode, configType);
		return StringUtils.equalsIgnoreCase("Y", value);
	}

	private static String getValue(String branchCode, String configType) {
		BranchBizConfig config = getInstance().findBranchBizConfigFromCache(branchCode, configType);
		if (config != null && "1".equals(config.getStatus())) { // 存在并且启用
			return config.getConfigValue();
		} else {
			return null;
		}
	}

	private BranchBizConfig findBranchBizConfigFromCache(String branchCode, String configType) {
		String key = branchCode + "_" + configType;
		CacheMap<String, BranchBizConfig>.ValueBean vb = branchBizConfigMap.getValueBean(key);
		if (vb == null) {
			synchronized (branchBizConfigMap) {// 防止并发
				vb = branchBizConfigMap.getValueBean(key);
				if (vb == null) {
					BranchBizConfig config = (BranchBizConfig) this.branchBizConfigDAO.findByPk(branchCode,
							configType);
					branchBizConfigMap.put(key, config);
					return config;
				} else {
					return vb.getValue();
				}
			}
		} else {
			return vb.getValue();
		}
	}
}
