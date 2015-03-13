package gnete.card.service.mgr;

import flink.util.SpringContext;
import gnete.card.dao.SysparmDAO;
import gnete.card.entity.Sysparm;
import gnete.etc.RuntimeBizException;
import gnete.etc.Symbol;
import gnete.util.CacheMap;
import gnete.util.LocalHostIpManager;

import org.apache.commons.lang.StringUtils;

/**
 * 系统参数缓存
 */
public class SysparamCache {
	private static final SysparamCache	instance	= new SysparamCache();

	private CacheMap<String, Sysparm>	sysParamMap	= new CacheMap<String, Sysparm>(5 * 60);

	private SysparmDAO					sysparmDAO;

	private SysparamCache() {
		if (this.sysparmDAO == null) {
			sysparmDAO = (SysparmDAO) SpringContext.getService("sysparmDAOImpl");
		}
	}

	public static SysparamCache getInstance() {
		return instance;
	}

	private Sysparm getSysParamFromCache(String paraCod) {
		CacheMap<String, Sysparm>.ValueBean vb = sysParamMap.getValueBean(paraCod);
		if (vb == null) {
			synchronized (sysParamMap) {// 防止并发
				vb = sysParamMap.getValueBean(paraCod);
				if (vb == null) {
					Sysparm p = (Sysparm) this.sysparmDAO.findByPk(paraCod);
					sysParamMap.put(paraCod, p);
					return p;
				} else {
					return vb.getValue();
				}
			}
		} else {
			return vb.getValue();
		}
	}
	
	public void remove(String key) {
		this.sysParamMap.remove(key);
	}
	
	public String getParamValue(String paraCod) {
		Sysparm sysparam = this.getSysParamFromCache(paraCod);
		if (sysparam == null) {
			throw new RuntimeBizException("参数[" + paraCod + "]不存在");
		}
		return sysparam.getParaValue();
	}
	
	public int getIntParamValue(String paraCod) {
		try {
			return Integer.valueOf(getParamValue(paraCod)).intValue();
		} catch (NumberFormatException e) {
			throw new RuntimeBizException("参数[" + paraCod + "]格式错误");
		}
	}

	public long getLongPara(String paraCod) {
		try {
			return Long.valueOf(getParamValue(paraCod)).longValue();
		} catch (NumberFormatException e) {
			throw new RuntimeBizException("参数[" + paraCod + "]格式错误");
		}
	}

	public double getDoublePara(String paraCod) {
		try {
			return Double.valueOf(getParamValue(paraCod)).doubleValue();
		} catch (NumberFormatException e) {
			throw new RuntimeBizException("参数[" + paraCod + "]格式错误");
		}
	}

	/**
	 * 得到当前工作日（不从缓存得到，而是直接查询数据库中得到）
	 */
	public String getWorkDateNotFromCache() {
		Sysparm p = (Sysparm) sysparmDAO.findByPk("010");
		return p.getParaValue();
	}

	/**
	 * 得到前一工作日（不从缓存得到，而是直接查询数据库中得到）
	 */
	public String getPreWorkDateNotFromCache() {
		Sysparm p = (Sysparm) sysparmDAO.findByPk("011");
		return p.getParaValue();
	}

	public String getSysUser() {
		return "sys";
	}

	/**
	 * 系统用户默认密码
	 */
	public String getDefaultPwd() {
		return getParamValue("100");
	}

	/**
	 * 系统号，卡号的前三位
	 */
	public String getSysNo() {
		return getParamValue("019");
	}

	/**
	 * 上传卡样图案保存在web服务器上的的路径
	 */
	public String getCardStyleLocalWebSavePath() {
		return getParamValue("020");
	}

	/**
	 * 上传卡样图案保存在FTP服务器上的路径
	 */
	public String getCardStyleFtpSavePath() {
		return getParamValue("029");
	}

	/**
	 * 制卡文件保存目录
	 */
	public String getMakeFileFtpSavePath() {
		return getParamValue("021");
	}

	/**
	 * 得到IC卡充值超时时间
	 */
	public String getIcDepositOverTime() {
		return getParamValue("025");
	}

	/**
	 * 得到卡bin是否限制以99开头
	 */
	public String getBinNoLimit() {
		return getParamValue("027");
	}

	/**
	 * 得到是否需要在登记簿中记录签名信息
	 */
	public String getSignatureReg() {
		return getParamValue("028");
	}

	/**
	 * 是否需要在登记簿中记录签名信息
	 */
	public boolean isNeedSign() {
		return StringUtils.equals(getSignatureReg(), Symbol.YES);
	}

	// ------------ FTP -----------------

	/** FTP服务器主机IP */
	public String getFtpServerIP() {
		return getParamValue("042");
	}

	/** FTP用户名 */
	public String getFtpServerUser() {
		return getParamValue("043");
	}

	/** FTP密码 */
	public String getFtpServerPwd() {
		return getParamValue("044");
	}

 	// ----------- 证书 -----------------
	/**
	 * 用户证书文件目录（FTP目录）
	 */
	public String getUserCertFileRemotePath() {
		return getParamValue("041");
	}

	/**
	 * 上传证书文件保存目录(本地)
	 */
	public String getUploadCertLocalSavePath() {
		return getParamValue("045");
	}

	/**
	 * 上传证书压缩文件名后缀，缺省压缩支持类型(.zip)
	 */
	public String getUploadCertZipFileSuffix() {
		return getParamValue("047");
	}

	/**
	 * 上传证书后缀名文件
	 */
	public String getUploadCertFileFuffix() {
		return getParamValue("048");
	}

	/**
	 * 获得C.A证书所在路径
	 */
	public String getCACertFileRemotePath() {
		return getParamValue("049");
	}

	/**
	 * 获得C.A证书文件名
	 */
	public String getCACertFileName() {
		return getParamValue("050");
	}

	/**
	 * 是否启用C.A证书检验
	 */
	public String getCACertFileVerify() {
		return getParamValue("051");
	}

	/**
	 * 得到外部卡导入文件存放在FTP服务器上的路径
	 */
	public String getExternalCardFileRemotePath() {
		return getParamValue("052");
	}

	/**
	 * 获得读取积分充值及账户变更文件的时间，格式为HHmm
	 */
	public String getPointAccReadTime() {
		return getParamValue("053");
	}

	/**
	 * 取得对账文件保存目录
	 */
	public String getAccountCheckFileSavePath() {
		return getParamValue("054");
	}

	/**
	 * 取得潮州移动机构号
	 */
	public String getCZCMCCBranchCode() {
		return getParamValue("T02");
	}

	// ----------- 网银通划付相关 --------------------------
	/**
	 * 网银通文件保存路径(发卡机构划付)
	 */
	public String getClear2PathBankBranchSavePath() {
		return getParamValue("090");
	}

	/**
	 * 生成网银通划付文件调度时刻（HHmm）
	 */
	public String getClear2PayCheckTime() {
		return getParamValue("091");
	}

	/**
	 * 生成划付文件保存路径
	 */
	public String getClear2PathBankSavePath() {
		return getParamValue("092");
	}

	/**
	 * 电子联行号服务端文件保存路径
	 */
	public String getClear2PayBankSeqFilePath() {
		return getParamValue("093");
	}

	/**
	 * 电子联行号文件名称
	 */
	public String getClear2PayBankSeqFileName() {
		return getParamValue("094");
	}

	/**
	 * 取得系统旧报表Excel文件的的保存路径
	 */
	public String getOldReportSavePath() {
		return getParamValue("095");
	}

	/**
	 * 取得系统旧报表txt文件在FTP服务器上的保存路径
	 */
	public String getOldTxtReportSavePath() {
		return getParamValue("096");
	}

	/**
	 * 得到保得会员注册文件存放在FTP服务器上的路径
	 */
	public String getMembFilePath() {
		return getParamValue("077");
	}

	/**
	 * 取得划付文件在FTP服务器上的保存路径
	 */
	public String getRmaFilePath() {
		return getParamValue("075");
	}

	/**
	 * 消息定时器时间间隔, 单位: 秒.
	 */
	public int getMsgTimerInterval() {
		return getIntParamValue("022");
	}

	/**
	 * 得到后台生成报表的时间，格式为HHmm
	 */
	public String getGernerateReportTime() {
		return getParamValue("032");
	}

	/**
	 * 数字证书检查时间（HH:mm）
	 */
	public String getCardCertCheckTime() {
		return getParamValue("081");
	}

	/**
	 * 获得处理回盘文件处理时间间隔
	 */
	public int getReturnTimerInterval() {
		return getIntParamValue("400");
	}

	/**
	 * web单任务调度服务器IP
	 */
	public String getWebSingleTaskHostIp() {
		return getParamValue("107");
	}

	/** 本机是不是web单任务调度服务器 */
	public boolean isLocalWebSingleTaskServer() {
		return LocalHostIpManager.isSameIp(getWebSingleTaskHostIp());
	}
	
	/** 报表FTP服务器主机IP */
	public String getReportFtpServerIP() {
		return getParamValue("R01");
	}

	/** 报表FTP用户名 */
	public String getReportFtpServerUser() {
		return getParamValue("R02");
	}

	/** 报表FTP密码 */
	public String getReportFtpServerPwd() {
		return getParamValue("R03");
	}

	/**
	 * 后台生成报表文件的保存目录路径
	 */
	public String getReportFolderPath() {
		return getParamValue("030");
	}
	
	/** 本机是不是报表FTP服务器 */
	public boolean isLocalReportFtpServer() {
		return LocalHostIpManager.isSameIp(getReportFtpServerIP());
	}
	
	
	/**
	 * 取得后台日志文件在FTP服务器上的保存路径
	 */
	public String getBgLogFileRemotePath() {
		return getParamValue("078");
	}

	/** 后台1日志文件在FTP服务器IP */
	public String getBg1LogFtpIp() {
		return getParamValue("101");
	}

	/** 后台1日志文件在FTP用户 */
	public String getBg1LogFtpUser() {
		return getParamValue("102");
	}

	/** 后台1日志文件在FTP密码 */
	public String getBg1LogFtpPassword() {
		return getParamValue("103");
	}

	/** 后台2日志文件在FTP服务器IP */
	public String getBg2LogFtpIp() {
		return getParamValue("104");
	}

	/** 后台2日志文件在FTP用户 */
	public String getBg2LogFtpUser() {
		return getParamValue("105");
	}

	/** 后台2日志文件在FTP密码 */
	public String getBg2LogFtpPassword() {
		return getParamValue("106");
	}
	
	/** 机构logo图片ftp保存路径 */
	public String getBranchLogoFtpSavePath() {
		return getParamValue("108");
	}
	
}