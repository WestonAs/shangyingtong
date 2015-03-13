package gnete.card.service;

import gnete.card.entity.BranchShares;
import gnete.card.entity.ChlFee;
import gnete.card.entity.ChlFeeTemplate;
import gnete.etc.BizException;

public interface BranchSharesService {
	/**
	 * 添加运营中心与分支机构分润参数
	 * @param branchShares
	 */
	public boolean addBranchShares(BranchShares[] feeArray, String sessionUserCode) throws BizException;
	
	/**
	 * 修改运营中心与分支机构分润参数
	 * @param branchShares
	 */
	public boolean modifyBranchShares(BranchShares branchShares) throws BizException;
	
	/**
	 * 删除运营中心与分支机构分润参数
	 * @param branchShares
	 */
	public boolean deleteBranchShares(BranchShares branchShares) throws BizException;
	/**
	 * 添加运营中心与分支机构平台运营手续费
	 * @param feeArray
	 * @param sessionUserCode
	 * @return
	 * @throws BizException
	 */
	public boolean addChlFee(ChlFee[] feeArray, String sessionUserCode) throws BizException;
	/**
	 * 修改运营中心与分支机构平台运营手续费
	 * @param chlFee
	 * @return
	 * @throws BizException
	 */
	public boolean modifyChlFee(ChlFee chlFee) throws BizException;
	
	/**
	 * 删除运营中心与分支机构平台运营手续费
	 * @param chlFee
	 * @return
	 * @throws BizException
	 */
	public boolean deleteChlFee(ChlFee chlFee) throws BizException;

	
	
	/**
	 * 添加运营中心与分支机构平台运营手续费模板
	 * @param feeArray
	 * @param sessionUserCode
	 * @return
	 * @throws BizException
	 */
	public boolean addChlFeeTemplate(ChlFeeTemplate[] feeArray, String sessionUserCode) throws BizException;
	/**
	 * 修改运营中心与分支机构平台运营手续费模板
	 * @param chlFeeTemplate
	 * @return
	 * @throws BizException
	 */
	public boolean modifyChlFeeTemplate(ChlFeeTemplate chlFeeTemplate) throws BizException;
	
	/**
	 * 删除运营中心与分支机构平台运营手续费模板
	 * @param chlFeeTemplate
	 * @return
	 * @throws BizException
	 */
	public boolean deleteChlFeeTemplate(ChlFeeTemplate chlFeeTemplate) throws BizException;
}
