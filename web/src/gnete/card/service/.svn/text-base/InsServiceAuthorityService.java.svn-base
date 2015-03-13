package gnete.card.service;

import gnete.card.entity.InsServiceAuthority;
import gnete.card.entity.InsServiceAuthorityKey;
import gnete.etc.BizException;

/**
 * @File: InsServiceAuthorityService.java
 *
 * @description: 业务权限参数定义相关处理
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-6-22
 */
public interface InsServiceAuthorityService {
	
	/**
	 * 新增业务权限参数定义
	 * @param insServiceAuthority
	 * @param serviceIds 权限id数组 
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	void addInsServiceAuthority(InsServiceAuthority insServiceAuthority, String[] serviceIds, String userId) throws BizException ;
	
	/**
	 * 修改业务权限参数定义
	 * @param insServiceAuthority
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	boolean modifyInsServiceAuthority(InsServiceAuthority insServiceAuthority, String userId) throws BizException ;
	
	/**
	 * 删除业务权限参数定义
	 * @param key
	 * @return
	 * @throws BizException
	 */
	boolean deleteInsServiceAuthority(InsServiceAuthorityKey key) throws BizException ;
}
