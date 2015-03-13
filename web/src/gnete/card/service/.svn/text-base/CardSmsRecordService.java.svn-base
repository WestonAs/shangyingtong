package gnete.card.service;

import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

import java.io.File;

/**
 * 手机短信营销 业务处理
 */
public interface CardSmsRecordService {

	/** 添加 手机短信记录 */
	void addCardSmsRecords(String[] mobileArr, String smsSendContent, UserInfo user) throws BizException;

	/**
	 * 文件方式添加 手机短信记录
	 * 
	 * @return 添加的条数
	 */
	int addCardSmsRecords(File file, UserInfo user) throws BizException;

}
