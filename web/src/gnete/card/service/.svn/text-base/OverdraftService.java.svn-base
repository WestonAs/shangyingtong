package gnete.card.service;

import gnete.card.entity.OverdraftLmtReg;
import gnete.etc.BizException;

/**
 * @File: OverdraftService.java
 *
 * @description: 帐户透支额度调整相关业务处理
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-9-25
 */
public interface OverdraftService {

	/**
	 * 新增账户透支额度调整申请
	 * @param overdraftLmtReg
	 * @return
	 * @throws BizException
	 */
	boolean addOverdraftLmtReg(OverdraftLmtReg overdraftLmtReg, String sessionUserCode) throws BizException;
	
	/**
	 * 修改账户透支额度调整申请
	 * @param overdraftLmtReg
	 * @return
	 * @throws BizException
	 */
	boolean modifyOverdraftLmtReg(OverdraftLmtReg overdraftLmtReg, String sessionUserCode) throws BizException ;

	/**
	 * 删除账户透支额度申请
	 * @param overdraftLmtId
	 * @return
	 * @throws BizException
	 */
	boolean delete(Long overdraftLmtId) throws BizException;
	
}
