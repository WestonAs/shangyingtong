package gnete.card.service;
 
import gnete.card.entity.AccountSystemInfo;
import gnete.card.entity.BusinessSubAccountInfo;
import gnete.card.entity.SubAccountType;
import gnete.etc.BizException;

public interface BusinessSubAccountService {
	
	/**
	 * 修改虚账户类型
	 * @param subAccountType
	 * @return
	 * @throws BizException
	 */
	boolean modifySubAccountType(SubAccountType subAccountType, String sessionUser) throws BizException ;
	
	/**
	 * 修改虚账户体系
	 * @param subAccountType
	 * @return
	 * @throws BizException
	 */
	boolean modifyAccountSystemInfo(AccountSystemInfo accountSystemInfo, String sessionUser) throws BizException ;

	/**
	 * 修改虚账户信息
	 * @param subAccountType
	 * @return
	 * @throws BizException
	 */
	boolean modifyBusinessSubAccountInfo(BusinessSubAccountInfo businessSubAccountInfo, String sessionUser) throws BizException ;

	
	
	
	/**
	 * 添加虚账户体系 
	 * @param subAccountType
	 * @param user
	 * @return
	 * @throws BizException
	 */
	AccountSystemInfo addAccountSystemInfo(AccountSystemInfo accountSystemInfo)
			throws BizException;
	
	
	/**
	 * 添加虚账户
	 * @param subAccountType
	 * @param user
	 * @return
	 * @throws BizException
	 */
	BusinessSubAccountInfo addBusinessSubAccountInfo(BusinessSubAccountInfo businessSubAccountInfo)
			throws BizException;
	

	void modifyBindAcct(BusinessSubAccountInfo bsai) throws BizException;
}
