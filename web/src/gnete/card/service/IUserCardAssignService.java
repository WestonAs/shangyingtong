package gnete.card.service;

import gnete.card.entity.MakeCardPerson;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Project: MyCard
 * @File: IUserCardAssignService.java
 * @See:
 * @description：<li>制卡人服务控制接口，包括制卡人列表、制卡人编辑、状态调整以及删除处理等</li>
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-21
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public interface IUserCardAssignService {

	/**
	 * 
	 * @description：得到制卡人信息以及控制处理 [Paginater, isCardAssignePermit]
	 * @param params      前台传递查询参数
	 * @param pageNumber  页号
	 * @param pageSize    页大小
	 * @return
	 * @throws BizException
	 * @version: 2010-12-21 下午07:35:50
	 * @See:
	 */
	Object[] getUserCardAssignResult(Map params, int pageNumber, int pageSize) throws BizException;

	/**
	 * 
	 * @description：得到制卡机构下的用户列表
	 * @param branchCode  制卡机构号
	 * @return
	 * @throws BizException
	 * @version: 2010-12-21 下午07:35:53
	 * @See:
	 */
	List<UserInfo> getUserCardInfoList(String branchCode) throws BizException;

	/**
	 * 
	 * @description：将选择用户设置为制卡人信息并设置为生效状态
	 * @param userId     选择用户
	 * @param loginUser  机构登陆用户
	 * @return
	 * @throws BizException
	 * @version: 2010-12-21 下午07:35:56
	 * @See:
	 */
	boolean processUserCardPersonAdd(String userId, UserInfo loginUser) throws BizException;
	
	/**
	 * 
	  * @description：  获得用于处理编辑制卡人的信息(原制卡人+其他用户列表)
	  * @param cardPerson  待编辑的用户
	  * @return
	  * @throws BizException  
	  * @version: 2010-12-22 上午12:19:57
	  * @See:
	 */
	Object[] getUserCardModifyResult(MakeCardPerson cardPerson) throws BizException;

	/**
	 * 
	 * @description：  将原制卡人信息设置为失效同时绑定选择的用户为新的制卡人
	 * @param cardPerson  待编辑的用户
	 * @param userId      绑定用户
	 * @param loginUser   登陆用户
	 * @return
	 * @throws BizException
	 * @version: 2010-12-21 下午07:36:00
	 * @See:
	 */
	boolean processUserCardPersonModify(MakeCardPerson cardPerson, String userId,UserInfo loginUser) throws BizException;

	
	/**
	 * 
	 * @description：控制当前制卡人的生效失效状态变更
	 * @param cardPerson   选卡人
	 * @param state        变更状态
	 * @param loginUser    登陆用户
	 * @return
	 * @throws BizException
	 * @version: 2010-12-21 下午07:36:06
	 * @See:
	 */
	boolean processUserCardPersonState(MakeCardPerson cardPerson, String state, UserInfo loginUser) throws BizException;

	/**
	 * 
	 * @description：将当前生效的制卡人删除
  	 * @param cardPerson  当前生效制卡人
	 * @return
	 * @throws BizException
	 * @version: 2010-12-21 下午07:36:09
	 * @See:
	 */
	boolean processUserCardPersonDel(MakeCardPerson cardPerson) throws BizException;
	
	/**
	 * 
	  * @description：获得制卡人的明细信息
	  * @param cardPerson    选择制卡人
	  * @return
	  * @throws BizException  
	  * @version: 2010-12-22 下午04:28:35
	  * @See:
	 */
	MakeCardPerson getSelectMakeCardPerson(MakeCardPerson cardPerson) throws BizException ;
}
