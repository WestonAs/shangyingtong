package gnete.card.service;

import gnete.card.entity.CardSubClassTemp;
import gnete.card.entity.IcTempPara;
import gnete.card.entity.MembClassTemp;
import gnete.card.entity.PointClassTemp;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

public interface SingleProductTemplateService {
	/**
	 * 新增一条积分子类型模板定义
	 * @param pointClassTemp
	 * @param ptUseLimitCodes
	 * @return
	 */
	void addPointClassTemp(PointClassTemp pointClassTemp,String[] ptUseLimitCodes)throws BizException;
	/**
	 * 删除一条积分子类型模板定义
	 * @param pointClassTemp
	 * @param ptUseLimitCodes
	 * @return
	 */
	boolean deletePointClassTemp(PointClassTemp pointClassTemp)throws BizException;
	/**
	 * 修改一条积分子类型模板定义
	 * @param pointClassTemp
	 * @param ptUseLimitCodes
	 * @return
	 */
	void modifyPointClassTemp(PointClassTemp pointClassTemp,String[] ptUseLimitCodes,UserInfo sessionUser)throws BizException;
	/**
	 * 新增一条会员类型模板定义
	 * @param membClassTemp
	 * @return
	 */
	void addMembClassTemp(MembClassTemp membClassTemp,UserInfo sessionUser)throws BizException;
	/**
	 * 修改一条会员类型模板定义
	 * @param membClassTemp
	 * @return
	 */
	void modifyMembClassTemp(MembClassTemp membClassTemp,UserInfo sessionUser)throws BizException;
	/**
	 * 删除一条会员类型模板定义
	 * @param pointClassTemp
	 * @param ptUseLimitCodes
	 * @return
	 */
	boolean deleteMembClassTemp(MembClassTemp membClassTemp)throws BizException;
	
	
	
	/**
	 * 新增一条卡类型模板定义
	 * @param membClassTemp
	 * @return
	 */
	CardSubClassTemp addSubClassTemp(CardSubClassTemp subClassTemp,IcTempPara icTempPara,UserInfo sessionUser)throws BizException;
	/**
	 * 修改一条卡类型模板定义
	 * @param subClassTemp
	 * @return
	 */
	void modifySubClassTemp(CardSubClassTemp subClassTemp,UserInfo sessionUser)throws BizException;
	/**
	 * 删除一条卡类型模板定义
	 * @param pointClassTemp
	 * @param ptUseLimitCodes
	 * @return
	 */
	boolean deleteSubClassTemp(String cardSubclass)throws BizException;
	
}
