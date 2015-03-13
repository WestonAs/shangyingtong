package gnete.card.service;

import gnete.card.entity.PasswordResetReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

public interface CardPasswordService {
	/**
	 * 添加密码重置登记簿
	 * @param passwordResetReg
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean addPasswordResetReg(PasswordResetReg passwordResetReg, UserInfo user) throws BizException;
	
	/**
	 * 删除密码重置登记簿
	 * @param passwordResetRegId
	 * @return
	 * @throws BizException
	 */
	boolean deletePasswordResetReg(Long passwordResetRegId) throws BizException;

}
