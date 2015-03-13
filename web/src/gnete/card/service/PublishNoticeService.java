package gnete.card.service;

import java.io.File;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.CardPrevConfig;
import gnete.card.entity.LogoConfig;
import gnete.card.entity.PublishNotice;
import gnete.card.entity.SaleDepositCheckConfig;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

/**
 * @File: PublishNoticeService.java
 * 
 * @description: 发布通知信息Service
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-18
 */
public interface PublishNoticeService {

	/**
	 * 发布新通知
	 * 
	 * @param publishNotice
	 * @param userId
	 * @throws BizException
	 */
	PublishNotice addPublishNotice(PublishNotice publishNotice, String userId)
			throws BizException;

	/**
	 * 删除通知
	 * 
	 * @param id
	 * @return
	 * @throws BizException
	 */
	boolean deletePublishNotice(String id) throws BizException;

	/**
	 * 隐藏通知，不显示
	 * 
	 * @param id
	 * @return
	 * @throws BizException
	 */
	boolean hidePublishNotice(String id) throws BizException;

	/**
	 * 显示某条通知
	 * 
	 * @param id
	 * @return
	 * @throws BizException
	 */
	boolean showPublishNotice(String id) throws BizException;
	
	/**
	 * 新增发卡机构logo参数配置
	 * @param logoConfig 发卡机构logo参数配置对象
	 * @param homeBigFile 首页大图
	 * @param homeSmallFile 首页小图
	 * @param loginSmallFile 登录后小图
	 * @param user 登录用户信息
	 * @throws BizException
	 */
	void addCardLogoConfig(LogoConfig logoConfig, File homeBigFile, File homeSmallFile, File loginSmallFile, UserInfo user) throws BizException;
	
	/**
	 * 删除发卡机构logo参数配置（只删除数据库里的记录）
	 * @param branchCode
	 * @return
	 * @throws BizException
	 */
	boolean deleteCardLogoConfig(String branchCode) throws BizException;

	/**
	 * 取得发卡机构审核权限配置明细
	 * @param cardBranch
	 * @return
	 */
	SaleDepositCheckConfig findCheckConfig(String cardBranch);
	
	/**
	 * 修改发卡机构审核权限配置
	 * @param config
	 * @throws BizException
	 */
	void modifyCheckConfig(SaleDepositCheckConfig config, UserInfo user) throws BizException;
	
	//================ 发卡机构卡前三位配置 =====================
	/**
	 * 列表页面，分页查询
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardPrevConfigPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 新增发卡机构卡前三位配置
	 * @param cardPrevConfig
	 * @param user
	 * @throws BizException
	 */
	void addCardPrevConfig(CardPrevConfig cardPrevConfig, UserInfo user) throws BizException;
	
	/**
	 * 删除发卡机构卡前三位配置，如果已经发卡，则不能删除。
	 * @param branchCode
	 * @throws BizException
	 */
	void deleteCardPrevConfig(String branchCode) throws BizException;
}
