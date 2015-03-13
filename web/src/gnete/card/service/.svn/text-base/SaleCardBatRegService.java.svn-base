package gnete.card.service;

import java.util.List;

import gnete.card.entity.RebateCardReg;
import gnete.card.entity.SaleCardBatReg;
import gnete.card.entity.SaleCardReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

public interface SaleCardBatRegService {

	/**
	 * 添加批量售卡登记
	 * @param saleCardBatReg	批量售卡登记信息
	 * @return
	 * @throws BizException
	 */
	long addSaleCardBatReg(SaleCardReg saleCardReg, SaleCardBatReg saleCardBatReg, 
			List<RebateCardReg> rebateCardRegList, UserInfo user, String serialNo) throws BizException;
	
	/**
	 * 修改批量售卡登记
	 * @param saleCardBatReg	批量售卡登记信息
	 * @param string 
	 * @return
	 * @throws BizException
	 */
	boolean modifySaleCardBatReg(SaleCardBatReg saleCardBatReg, String modifyUserId) throws BizException ;
	
	/**
	 * 删除批量售卡登记
	 * @param string 
	 * @return
	 * @throws BizException
	 */
	boolean deleteSaleCardBatReg(long saleBatchId) throws BizException ;
	
	/**
	 * 激活批量预售卡
	 * @param string 
	 * @return
	 * @throws BizException
	 */
	boolean activate(SaleCardReg saleCardReg, UserInfo user) throws BizException;

	void setMessage(String message);
	
	String getMessage();
	
}
