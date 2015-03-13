package gnete.card.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.dao.TrailBalanceRegDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.TrailBalanceReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.TrailType;
import gnete.card.service.TrailBalanceService;
import gnete.etc.Assert;
import gnete.etc.BizException;

@Service("trailBalanceService")
public class TrailBalanceServiceImpl implements TrailBalanceService {
	
	@Autowired
	private TrailBalanceRegDAO trailBalanceRegDAO;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;

	public boolean addTrailBalanceReg(TrailBalanceReg trailBalanceReg,
			String userId) throws BizException {
		Assert.notNull(trailBalanceReg, "添加的试算平衡登记对象不能为空。");
		trailBalanceReg.setStatus(RegisterState.WAITED.getValue());
		trailBalanceReg.setUpdateBy(userId);
		trailBalanceReg.setUpdateTime(new Date());
		
		//试算类型是“赠券账户”，发行机构类型填写赠券子类型中的“发行机构类型”
		if(TrailType.COUPON_ACCT.getValue().equals(trailBalanceReg.getTrailType())){
			CouponClassDef couponClassDef = (CouponClassDef) this.couponClassDefDAO.findByPk(trailBalanceReg.getClassId());
			trailBalanceReg.setIssType(couponClassDef.getIssType());
			trailBalanceReg.setClassName(couponClassDef.getClassName());
		}
		//试算类型是“积分账户”，发行机构类型填写积分子类型中的“联名机构类型 ”
		else if(TrailType.POINT_ACCT.getValue().equals(trailBalanceReg.getTrailType())){
			PointClassDef pointClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(trailBalanceReg.getClassId());
			trailBalanceReg.setIssType(pointClassDef.getJinstType());
			trailBalanceReg.setClassName(pointClassDef.getClassName());
		}
		//试算类型为“次卡账户”
		else if(TrailType.TIME_CARD_ACCT.getValue().equals(trailBalanceReg.getTrailType())){
			AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(trailBalanceReg.getClassId());
			trailBalanceReg.setClassName(accuClassDef.getClassName());
		}
		return trailBalanceRegDAO.insert(trailBalanceReg)!=null;
	}

	public boolean deleteTrailBalanceReg(Long id) throws BizException {
		Assert.notNull(id, "删除的试算平衡对象不能为空。");
		return trailBalanceRegDAO.delete(id)>0;
	}

	public boolean modifyTrailBalanceReg(TrailBalanceReg trailBalanceReg,
			String userId) throws BizException {
		Assert.notNull(trailBalanceReg, "修改的试算平衡登记对象不能为空。");
		trailBalanceReg.setStatus(RegisterState.WAITED.getValue());
		trailBalanceReg.setUpdateBy(userId);
		trailBalanceReg.setUpdateTime(new Date());
		return trailBalanceRegDAO.update(trailBalanceReg)>0;
	}

}
