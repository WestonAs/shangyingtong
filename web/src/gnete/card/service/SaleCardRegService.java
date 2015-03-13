package gnete.card.service;

import flink.util.Paginater;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.RebateRule;
import gnete.card.entity.SaleCardBatReg;
import gnete.card.entity.SaleCardReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

import java.util.Map;

public interface SaleCardRegService {
	
	
	/**
	 * 添加售卡登记
	 * @param saleCardReg	售卡登记信息
	 * @return
	 * @throws BizException
	 */
	void addSaleCardReg(SaleCardReg saleCardReg, CardExtraInfo cardExtraInfo, 
			UserInfo userInfo, String serialNo) throws BizException;
	
	/**
	 * 修改售卡登记
	 * @param saleCardReg	售卡登记信息
	 * @param string 
	 * @return
	 * @throws BizException
	 */
	boolean modifySaleCardReg(SaleCardReg saleCardReg, String modifyUserId) throws BizException ;
	
	/**
	 * 删除售卡登记
	 * @param string 
	 * @return
	 * @throws BizException
	 */
	boolean deleteSaleCardReg(long saleBatchId) throws BizException ;
	
	/**
	 * 激活预售卡
	 * @param string 
	 * @return
	 * @throws BizException
	 */
	boolean activate(SaleCardReg saleCardReg, UserInfo user) throws BizException;

	void setMessage(String message);
	
	String getMessage();
	
	//============================== 售卡撤销相关处理 =============================================
	
	/**
	 *  售卡撤销列表页面
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	Paginater findSaleCardCancelPage(Map<String, Object> params, int pageNumber, int pageSize) throws BizException;
	
	/**
	 *  售卡撤销明细
	 * @param saleBatchId
	 * @return
	 * @throws BizException
	 */
	SaleCardReg findSaleCardCancelDetail(Long saleBatchId) throws BizException;
	
	/**
	 * 售卡返利明细
	 * @param rebateId
	 * @return
	 * @throws BizException
	 */
	RebateRule findRebateRule(Long rebateId) throws BizException;
	
	/**
	 *  售卡撤销处理的相关流程。售卡撤销分三种情况:
	 *  <li>
	 *  	1. 售卡状态为成功的。即已经成功售卡的记录，此时的售卡状态为“成功”，需要在售卡登记表中插入撤销的记录，
	 *  	向后台发起售卡撤销报文的。将原售卡记录修改为“待处理”。售卡撤销对象的状态为“待审核”
	 *  </li>
	 *  <li>
	 *  	2. 非发卡机构做批量售卡且手动设置返利，需要审核时。此时的售卡状态为“待审核”，
	 *  	只需要在售卡登记表中插入撤销的记录，将原售卡记录的状态改为“已撤销”，同时要将该售卡审核流程删除。
	 *  </li>
	 *  <li>
	 *  	3. 售卡机构做“预售卡”，售卡状态为“未激活”。此时只需在售卡登记表中插入撤销的记录，<br>
	 *  	将原售卡记录的状态修改为“已撤销”。
	 *  </li>
	 * @param saleCardReg
	 * @param user
	 * @throws BizException
	 */
	void addSaleCardCancel(SaleCardReg saleCardReg, UserInfo user, String serialNo) throws BizException;
	
	/**
	 * 预售卡撤销
	 * @Date 2013-3-29上午11:21:53
	 * @return void
	 */
	void addSaleCardPreCancel(SaleCardBatReg saleCardReg, UserInfo user) throws BizException;
	
	 /**
	  * 售卡撤销成功时，根据售卡撤销的信息，删除持卡人信息表的数据,同时更新卡库存信息表的数据
	 * @param saleCardReg 售卡撤销对象
	 * @throws BizException
	 */
	void updateExtraInfoForSuccess(SaleCardReg saleCardReg)throws BizException;
	
}
