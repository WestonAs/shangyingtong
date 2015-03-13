package gnete.card.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import gnete.card.entity.CancelCardReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

/**
 * 退卡销户
 * @author lib
 * 2011-03-29
 */
public interface CancelCardService {

	/**
	 * 添加退卡销户登记
	 * @param cancelCardReg
	 * @param updateUserId
	 * @return
	 * @throws BizException
	 */
	void addCancelCard(CancelCardReg cancelCardReg, UserInfo userInfo) throws BizException;
	
	/**
	 * 文件添加退卡销户登记
	 * @param upload
	 * @param uploadFileName
	 * @return
	 * @throws BizException
	 */
	List<String> addFileCancelCardReg(File upload, String uploadFileName, UserInfo user) throws BizException;
	
	/**
	 * 删除退卡销户记录
	 * @param cancelCardRegId
	 * @return
	 * @throws BizException
	 */
	boolean delete(Long cancelCardRegId) throws BizException;
	
	public BigDecimal countAvlbBal(CardInfo cardInfo) throws BizException;
}
