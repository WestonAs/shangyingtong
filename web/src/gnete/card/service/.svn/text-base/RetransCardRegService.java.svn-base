package gnete.card.service;

import gnete.card.entity.RetransCardReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

import java.io.File;
import java.math.BigDecimal;

public interface RetransCardRegService {

	/**
	 * 添加卡补账及注册信息
	 * @param retransCardReg	卡补账注册信息
	 * @return
	 * @throws BizException
	 */
	RetransCardReg addRetransCardReg(RetransCardReg retransCardReg, UserInfo userInfo) throws BizException;	
	
	/**
	 * 修改卡补账
	 * @param retransCardReg	卡补账信息
	 * @param string 
	 * @return
	 * @throws BizException
	 */
	boolean modifyRetransCardReg(RetransCardReg retransCardReg, String modifyUserId) throws BizException ;
	
	/**
	 * 删除卡补账
	 * @param string 
	 * @return
	 * @throws BizException
	 */
	boolean deleteRetransCardReg(long retransCardID) throws BizException ;

	/** 文件方式批量卡补账 
	 * @throws BizException */
	void addRetransRegBatFile(File upload, int detailCnt, BigDecimal totalAmt, String remark,
			UserInfo userInfo) throws BizException;
	
}
