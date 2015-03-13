package gnete.card.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gnete.card.dao.GiftExcRegDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.dao.PointConsmRuleDefDAO;
import gnete.card.dao.PointExcCouponRegDAO;
import gnete.card.dao.PointExcRegDAO;
import gnete.card.entity.GiftExcReg;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.PointConsmRuleDef;
import gnete.card.entity.PointExcCouponReg;
import gnete.card.entity.PointExcReg;
import gnete.card.entity.state.PromotionsRuleState;
import gnete.card.entity.state.RegisterState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.PointExchgService;
import gnete.etc.Assert;
import gnete.etc.BizException;

@Service("pointExchgService")
public class PointExchgServiceImpl implements PointExchgService {
	
	@Autowired
	private GiftExcRegDAO giftExcRegDAO;
	@Autowired
	private PointExcRegDAO pointExcRegDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private PointConsmRuleDefDAO pointConsmRuleDefDAO;
	@Autowired
	private PointExcCouponRegDAO pointExcCouponRegDAO;

	public boolean addGiftExcReg(GiftExcReg giftExcReg, String sessionUserCode) throws BizException {
		
		Assert.notNull(giftExcReg, "添加的积分兑换礼品登记不能为空");
		giftExcReg.setUpdateTime(new Date());
		giftExcReg.setUpdateUser(sessionUserCode);
		giftExcReg.setStatus(RegisterState.WAITEDEAL.getValue());
		
		if(this.giftExcRegDAO.insert(giftExcReg) != null){
			//登记成功后，组礼品兑换报文，发送后台积分消费相关的表
			MsgSender.sendMsg(MsgType.GIFT_EXC, giftExcReg.getGiftExcId(), sessionUserCode);
			return true;
		}
		else{
			return false;
		}
			
		//return this.giftExcRegDAO.insert(giftExcReg) != null;
	}

	public boolean deleteGiftExcReg(Long giftExcId) throws BizException {
		
		Assert.notNull(giftExcId, "删除的积分兑换礼品登记对象不能为空");		
		int count = this.giftExcRegDAO.delete(giftExcId);
		return count > 0;
	}

	public boolean modifyGiftExcReg(GiftExcReg giftExcReg, String sessionUserCode) throws BizException {
		
		Assert.notNull(giftExcReg, "更新的积分兑换礼品登记对象不能为空");
		giftExcReg.setUpdateUser(sessionUserCode);
		giftExcReg.setUpdateTime(new Date());
		int count = this.giftExcRegDAO.update(giftExcReg);
		return count>0;
	}

	public boolean deletePointExc(Long pointExcId) throws BizException {
		Assert.notNull(pointExcId, "删除的积分返利登记对象不能为空");		
		int count = this.pointExcRegDAO.delete(pointExcId);
		return count > 0;
	}

	public boolean addPointExcReg(PointExcReg pointExcReg, String sessionUserCode) throws BizException {
		
		Assert.notNull(pointExcReg, "添加的积分返利登记对象不能为空");
		String ptClass = pointExcReg.getPtClass();
		
		PointClassDef pointClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(ptClass);
		pointExcReg.setPtRef(pointClassDef.getPtRef());
		pointExcReg.setPtDiscntRate(pointClassDef.getPtDiscntRate());
		
		pointExcReg.setUpdateTime(new Date());
		pointExcReg.setUpdateBy(sessionUserCode);
		pointExcReg.setStatus(RegisterState.WAITEDEAL.getValue());
		
		if(this.pointExcRegDAO.insert(pointExcReg) != null){
			//登记成功后，组礼品返利报文，发送后台，后台处理类似积分消费
			MsgSender.sendMsg(MsgType.POINT_EXC, pointExcReg.getPointExcId(), sessionUserCode);
			return true;
		}
		else{
			return false;
		}
	}

	public boolean addPointConsmRuleDef(PointConsmRuleDef pointConsmRuleDef,
			String sessionUserCode) throws BizException {
		Assert.notNull(pointConsmRuleDef, "添加的积分兑换赠券定义不能为空。");
		
		/*Map<String, Object> params = new HashMap<String, Object>();
		params.put("ptClass", pointConsmRuleDef.getPtClass());
		params.put("couponClass", pointConsmRuleDef.getCouponClass());
		Assert.isNull(this.pointConsmRuleDefDAO.getPointConsmRuleByClass(params), 
				"积分类型"+pointConsmRuleDef.getPtClass()+"和赠券类型"+pointConsmRuleDef.getCouponClass()+"存在规则定义，不可重复定义。");*/
		
		pointConsmRuleDef.setUpdateBy(sessionUserCode);
		pointConsmRuleDef.setUpdateTime(new Date());
		return this.pointConsmRuleDefDAO.insert(pointConsmRuleDef) != null;
	}

	public boolean deletePointConsmRuleDef(String ptExchgRuleId)
			throws BizException {
		
		Assert.notNull(ptExchgRuleId, "删除的积分兑换赠券定义对象不能为空");		
		int count = this.pointConsmRuleDefDAO.delete(ptExchgRuleId);
		return count > 0;
	}

	public boolean modifyPointConsmRuleDef(PointConsmRuleDef pointConsmRuleDef,
			String sessionUserCode) throws BizException {
		
		Assert.notNull(pointConsmRuleDef, "更新的积分兑换赠券定义对象不能为空");
		pointConsmRuleDef.setUpdateBy(sessionUserCode);
		pointConsmRuleDef.setUpdateTime(new Date());
		int count = this.pointConsmRuleDefDAO.update(pointConsmRuleDef);
		return count>0;
	}

	public boolean effectptConsmRuleDef(String ptExchgRuleId)
			throws BizException {
		Assert.notEmpty(ptExchgRuleId, "生效的规则编号不能为空");
		
		PointConsmRuleDef pointConsmRuleDef = (PointConsmRuleDef) this.pointConsmRuleDefDAO.findByPk(ptExchgRuleId);
		Assert.notNull(pointConsmRuleDef, "找不到该规则[" + ptExchgRuleId + "]");
		
		pointConsmRuleDef.setRuleStatus(PromotionsRuleState.EFFECT.getValue());
		pointConsmRuleDef.setUpdateTime(new Date());
		int count = this.pointConsmRuleDefDAO.update(pointConsmRuleDef);
		return count > 0;
	}

	public boolean invalidptConsmRuleDef(String ptExchgRuleId)
			throws BizException {
		Assert.notEmpty(ptExchgRuleId, "失效的规则编号不能为空");
		
		PointConsmRuleDef pointConsmRuleDef = (PointConsmRuleDef) this.pointConsmRuleDefDAO.findByPk(ptExchgRuleId);
		Assert.notNull(pointConsmRuleDef, "找不到该规则[" + ptExchgRuleId + "]");
		
		pointConsmRuleDef.setRuleStatus(PromotionsRuleState.INVALID.getValue());
		pointConsmRuleDef.setUpdateTime(new Date());
		int count = this.pointConsmRuleDefDAO.update(pointConsmRuleDef);
		return count > 0;
	}

	public boolean addPointExcCouponReg(PointExcCouponReg pointExcCouponReg,
			String sessionUserCode) throws BizException {
		Assert.notNull(pointExcCouponReg, "添加的积分兑换赠券登记不能为空");
		pointExcCouponReg.setUpdateTime(new Date());
		pointExcCouponReg.setUpdateBy(sessionUserCode);
		pointExcCouponReg.setStatus(RegisterState.WAITEDEAL.getValue());
		
		if(this.pointExcCouponRegDAO.insert(pointExcCouponReg) != null){
			//登记成功后，组赠券兑换报文，发送后台积分消费相关的表
			MsgSender.sendMsg(MsgType.POINT_EXC_COUPON, pointExcCouponReg.getPointExcCouponRegId(), sessionUserCode);
			return true;
		}
		else{
			return false;
		}
	}

	public boolean deletePointExcCouponReg(Long pointExcCouponRegId)
			throws BizException {
		Assert.notNull(pointExcCouponRegId, "删除的积分兑换赠券对象不能为空");		
		int count = this.pointExcCouponRegDAO.delete(pointExcCouponRegId);
		return count > 0;
	}

	public boolean modifyPointExcCouponReg(PointExcCouponReg pointExcCouponReg,
			String sessionUserCode) throws BizException {
		return false;
	}

}
