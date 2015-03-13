package gnete.card.service;

import gnete.card.entity.ReleaseCardFee;
import gnete.card.entity.ReleaseCardFeeDtl;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

public interface ReleaseCardFeeService {
	/**
	 * 添加发卡机构分润手续费参数
	 * @param feeArray
	 */
	public boolean addReleaseCardFee(ReleaseCardFee[] feeArray, String[] ulimit, String[] feeRate, UserInfo user) throws BizException;
	
	/**
	 * 添加分支机构与发卡机构手续费参数
	 * @param feeArray
	 */
	public boolean addChlReleaseCardFee(ReleaseCardFee[] feeArray, String userId) throws BizException;
	
	/**
	 * 修改发卡机构分润手续费参数
	 * @param ulmoney 
	 * @param branchShares
	 */
	public boolean modifyReleaseCardFee(ReleaseCardFee releaseCardFee, ReleaseCardFeeDtl[] releaseCardFeeDtls, String[] OriginalUlimit, String userId) throws BizException;
	
	/**
	 * 删除发卡机构分润手续费参数
	 * @param branchShares
	 */
	public boolean deleteReleaseCardFee(ReleaseCardFee releaseCardFee, String userId) throws BizException;
	
	/**
	 * 启用发卡机构分润手续费参数
	 * @param branchShares
	 */
	public boolean startReleaseCardFee(ReleaseCardFee releaseCardFee, String userId) throws BizException;
	
	/**
	 * 停用发卡机构分润手续费参数
	 * @param branchShares
	 */
	public boolean stopReleaseCardFee(ReleaseCardFee releaseCardFee, String userId) throws BizException;
	
}
