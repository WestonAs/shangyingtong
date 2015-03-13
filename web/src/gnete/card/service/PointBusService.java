package gnete.card.service;

import gnete.card.entity.CardInfo;
import gnete.card.entity.DepositPointBatReg;
import gnete.card.entity.DepositPointReg;
import gnete.card.entity.PointChgReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

import java.io.File;
import java.util.List;

public interface PointBusService {
	
	/**
	 * 积分调整
	 * 
	 * @param reg
	 * @param user
	 * @return
	 * @throws BizException
	 */
	boolean pointChange(PointChgReg reg, UserInfo user) throws BizException;
	
	/**
	 * 文件方式积分调整
	 * @param upload
	 * @param uploadFileName
	 * @param user
	 * @return
	 * @throws Exception
	 */
	List<PointChgReg> addFilePointChgReg(File upload, String uploadFileName, UserInfo user) throws Exception;
	
	/**
	 * 添加单笔积分充值登记信息
	 * 
	 * @param depositPointReg
	 * @param user
	 * @param serialNo
	 * @throws BizException
	 */
	void addDepositPointReg(DepositPointReg depositPointReg, UserInfo user, String serialNo) throws BizException;
	
	/**
	 * 根据传来的卡号，检查该卡能否做积分充值(批量时为起始卡号，批量时卡数量为空)
	 * 
	 * @param cardId
	 * @param cardCount
	 * @param user
	 * @return
	 * @throws BizException
	 */
	CardInfo checkCardId(String cardId, String cardCount, UserInfo user) throws BizException;
	
	/**
	 * 添加批量充值登记信息
	 * 
	 * @param depositPointReg
	 * @param depositPointBatReg
	 * @param user
	 * @param serialNo
	 * @throws BizException
	 */
	void addDepositPointBatReg(DepositPointReg depositPointReg, DepositPointBatReg depositPointBatReg, 
			String cardCount, UserInfo user, String serialNo) throws BizException;

	/**
	 *  通过文件方式做批量积分充值
	 *  
	 * @param depositPointReg
	 * @param upload
	 * @param cardCount
	 * @param user
	 * @param serialNo
	 * @param limitId
	 * @throws BizException
	 */
	void addDepositPointBatRegFile(DepositPointReg depositPointReg, File upload, 
		String cardCount, UserInfo user, String serialNo, String limitId) throws BizException;
}
