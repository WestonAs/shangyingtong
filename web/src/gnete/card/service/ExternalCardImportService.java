package gnete.card.service;

import gnete.card.entity.ExternalCardImportReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

import java.io.File;
import java.io.InputStream;

/**
 * 外部卡导入 业务处理
 */
public interface ExternalCardImportService {

	/**
	 * 新增一条外部卡导卡登记簿
	 * 
	 * @param file
	 *            外部卡导入文件
	 * @param importReg
	 *            导入登记簿
	 * @param user
	 *            登录用户信息
	 * @return
	 * @throws BizException
	 */
	ExternalCardImportReg addExternalCardImportReg(File file, ExternalCardImportReg importReg, UserInfo user)
			throws BizException;
	/**
	 * 下载原导入文件
	 * @throws BizException 
	 */
	InputStream downloadOrigFile(Long externalCardImportRegId) throws BizException;

	/**
	 * 重新导入文件
	 * @param file
	 *            新的外部卡导入文件
	 * @param importReg
	 *            导入登记簿
	 * @param user
	 *            登录用户信息
	 * @return
	 * @throws BizException
	 */
	ExternalCardImportReg reImport(File file, ExternalCardImportReg newImportReg, UserInfo user)
	throws BizException;
	
	/** 扣减风险准备金 */
	void deductCardRisk(ExternalCardImportReg importReg) throws BizException;
}
