package gnete.card.service;

import java.io.File;
import java.util.List;
import gnete.card.entity.AdjAccReg;
import gnete.card.entity.TransAccReg;
import gnete.card.entity.TransAccRuleDef;
import gnete.card.entity.TransAccRuleDefKey;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

public interface AccRegService {

	/**
	 * 添加卡转账及注册信息
	 * @param transAccReg	卡转账注册信息
	 * @return
	 * @throws BizException
	 */
	TransAccReg addTransAccReg(TransAccReg transAccReg, UserInfo userInfo) throws BizException;

	/**
	 * 添加卡调账信息
	 * @param adjAccReg
	 * @param sessionUserCode
	 * @return
	 */
	AdjAccReg addAdjAccReg(AdjAccReg adjAccReg, UserInfo userInfo) throws BizException;	
	
	/**
	 * 批量添加卡调账信息
	 * @param transSns
	 * @param createUserId
	 * @return
	 * @throws BizException
	 */
	List<AdjAccReg> addAdjAccRegBat(String[] transSns, UserInfo userInfo) throws BizException;	
	
	/**
	 * 添加转账规则定义
	 * @param transAccRuleDef
	 * @param createUserId
	 * @return
	 * @throws BizException
	 */
	boolean addTransAccRuleDef(TransAccRuleDef transAccRuleDef, UserInfo userInfo) throws BizException;
	
	/**
	 * 修改转账规则定义（注销或者正常状态）
	 * @param transAccRuleDef
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	public boolean modifyTransAccRuleDef(TransAccRuleDef transAccRuleDef, UserInfo userInfo) throws BizException;
	
	/**
	 * 删除转账规则定义
	 * @param key
	 * @return
	 * @throws BizException
	 */
	public boolean deleteTransAccRuleDef(TransAccRuleDefKey key) throws BizException;
	
	/**
	 * 处理文件方式上传的批量退货
	 * @param upload
	 * @param uploadFileName
	 * @param userCode
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public List<AdjAccReg> addFileAdjAccReg(File upload, String uploadFileName, String userCode, UserInfo user) throws Exception;
}
