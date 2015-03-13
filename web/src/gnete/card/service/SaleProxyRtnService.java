package gnete.card.service;

import java.util.List;

import gnete.card.entity.SaleProxyRtn;
import gnete.etc.BizException;

public interface SaleProxyRtnService {
	/**
	 * 添加售卡代理商返利参数
	 * @param branchShares
	 */
	public boolean addSaleProxyRtn(List<SaleProxyRtn> saleFee, String sessionUserCode) throws BizException;
	
	/**
	 * 修改售卡代理商返利参数
	 * @param branchShares
	 */
	public boolean modifySaleProxyRtn(SaleProxyRtn saleProxyRtn) throws BizException;
	
	/**
	 * 删除售卡代理商返利参数
	 * @param branchShares
	 */
	public boolean deleteSaleProxyRtn(SaleProxyRtn saleProxyRtn) throws BizException;

}
