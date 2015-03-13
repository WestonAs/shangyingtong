package gnete.card.service;

import gnete.card.entity.CenterProxyShares;
import gnete.card.entity.CenterProxySharesKey;
import gnete.etc.BizException;

public interface CenterProxySharesService {
	/**
	 * 添加运营机构与运营机构代理商分润参数
	 * @param branchShares
	 */
	public boolean addCenterProxyShares(CenterProxyShares[] feeArray, String sessionUserCode) throws BizException;
	
	/**
	 * 修改运营机构与运营机构代理商分润参数
	 * @param branchShares
	 */
	public boolean modifyCenterProxyShares(CenterProxyShares centerProxyShares) throws BizException;
	
	/**
	 * 删除运营机构与运营机构代理商分润参数
	 * @param key
	 */
	public boolean deleteCenterProxyShares(CenterProxySharesKey key) throws BizException;

}
