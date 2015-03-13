package gnete.card.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

public interface CardExtraInfoService {

	boolean addCardExtraInfo(CardExtraInfo cardExtraInfo, String updateBy) throws BizException;
	
	boolean modifyCardExtraInfo(CardExtraInfo cardExtraInfo, String updateBy) throws BizException ;

	boolean deleteCardExtraInfo(String cardId) throws BizException;
	
	boolean modifyCardPassWord(CardInfo cardInfo, String updateBy) throws BizException ;
	
	/**
	 * 修改查询密码
	 * @param oldPass
	 * @param newPass
	 * @param sessionUser
	 */
	void modifyPass(String cardId, String oldPass, String newPass, UserInfo sessionUser) throws BizException ;
	
	/**
	 * 验证持卡人登录
	 * @param cardExtraInfo
	 * @param password
	 * @throws BizException
	 */
	void checkLogin(CardExtraInfo cardExtraInfo, String password) throws BizException ;
	
	/**
	 * 文件方式批量新增持卡人信息
	 * @param upload
	 * @param uploadFileName
	 * @param userCode
	 * @param user
	 * @param branchCode 选择的机构编号
	 * @return
	 * @throws Exception
	 */
	List<CardExtraInfo> addCardExtraInfoBat(File upload, String uploadFileName, UserInfo user, String branchCode) throws Exception;

	void export(HttpServletResponse response, Map<String, Object> params) throws IOException;
}
