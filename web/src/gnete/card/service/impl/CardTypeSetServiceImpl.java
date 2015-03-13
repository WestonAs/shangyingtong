package gnete.card.service.impl;

import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.DiscntClassDefDAO;
import gnete.card.dao.InsServiceAuthorityDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.DiscntClassDef;
import gnete.card.entity.InsServiceAuthority;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.PtUsageType;
import gnete.card.entity.type.PtUseLimitType;
import gnete.card.service.CardTypeSetService;
import gnete.card.util.TradeCardTypeMap;
import gnete.etc.Assert;
import gnete.etc.BizException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gnete.etc.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cardTypeSetService")
public class CardTypeSetServiceImpl implements CardTypeSetService {
	
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	@Autowired
	private DiscntClassDefDAO discntClassDefDAO;
	@Autowired
	private InsServiceAuthorityDAO insServiceAuthorityDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	
	public boolean addCardTypeCode(CardTypeCode cardTypeCode) throws BizException{
		return false;
	}

	public boolean deleteCardTypeCode(CardTypeCode cardTypeCode)
			throws BizException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean modifyCardTypeCode(CardTypeCode cardTypeCode)
			throws BizException {
		CardTypeCode element = (CardTypeCode)cardTypeCodeDAO.findByPk(cardTypeCode.getCardTypeCode());
		Assert.notNull(element,"该卡类型不存在");
		Assert.isTrue(element.getStatus().equals(cardTypeCode.getStatus()), "状态不对");
		if(CardTypeState.NORMAL.getValue().equals(element.getStatus())){
			element.setStatus(CardTypeState.CANCEL.getValue());
		}else if(CardTypeState.CANCEL.getValue().equals(element.getStatus())){
			element.setStatus(CardTypeState.NORMAL.getValue());
		}
		element.setUpdateBy(cardTypeCode.getUpdateBy());
		element.setUpdateTime(new Date());
		int count = cardTypeCodeDAO.update(element);
		return count>0;
	}
	
	public boolean addPointClassDef(PointClassDef pointClassDef, String[] ptUseLimitCodes) throws BizException{
		// 检查发卡机构的业务权限
		if (StringUtils.isNotEmpty(pointClassDef.getCardIssuer())) {
			checkServiceAuthority(pointClassDef.getCardIssuer(), CardType.POINT.getValue());
		}
		String usage = pointClassDef.getPtUsage();
		Short ptLatestCyc = null; 
		if(PtUsageType.FORVEREFFECTIVE.getValue().equals(usage)||
				PtUsageType.INSTM2.getValue().equals(usage)||
				PtUsageType.INSTM3.getValue().equals(usage)){
			ptLatestCyc = 1;
		}else if(PtUsageType.INSTM1.getValue().equals(usage)){
			ptLatestCyc = 2;
		}
		
		pointClassDef.setPtLatestCyc(ptLatestCyc);
		BigDecimal ptDeprecRate = pointClassDef.getPtDeprecRate();
		BigDecimal PtDiscntRate = pointClassDef.getPtDiscntRate();
		if(ptDeprecRate!=null && StringUtils.isNotBlank(ptDeprecRate.toString())){
			ptDeprecRate = ptDeprecRate.divide(new BigDecimal(100));
			pointClassDef.setPtDeprecRate(ptDeprecRate);
		}
		if(PtDiscntRate!=null && StringUtils.isNotBlank(PtDiscntRate.toString())){
			PtDiscntRate = PtDiscntRate.divide(new BigDecimal(100));
			pointClassDef.setPtDiscntRate(PtDiscntRate);
		}
		
		/*
		 * 30位的0 1字符串 （0表示没有该用途 , 1表示有该用途）
		 * 第1位 表示积分消费
		 * 第2位 表示积分返利
		 * 第3位 表示积分兑换礼品
		 * 第4位 表示积分兑换赠券
		*/
		List<String> codeList = PtUseLimitType.getAllCode();
		StringBuffer ptUselimit = new StringBuffer(Constants.PT_USE_LIMIT_DEFAULT);
		
		if(ptUseLimitCodes!=null){
			for (String code : ptUseLimitCodes) {
				int index = codeList.indexOf(code);
				ptUselimit.replace(index, index+1, "1");
			}
		}
		
		pointClassDef.setPtUseLimit(ptUselimit.toString());
		Object obj = pointClassDefDAO.insert(pointClassDef);
		return obj!=null;
	}

	public boolean deletePointClassDef(PointClassDef pointClassDef)
			throws BizException {
		PointClassDef classDef = (PointClassDef)this.pointClassDefDAO.findByPk(pointClassDef.getPtClass());
		Assert.notNull(classDef, "此积分子类型不存在");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ptClass", classDef.getPtClass());
		Collection<CardSubClassDef> dataList = this.cardSubClassDefDAO.findCardSubClassDef(params, 0, 20).getData();
		
		Assert.isEmpty(dataList, "该积分子类型已在使用，不能删除！");
		
		int count = pointClassDefDAO.delete(pointClassDef.getPtClass());
		return count>0;
	}

	public boolean modifyPointClassDef(PointClassDef pointClassDef, String[] ptUseLimitCodes)
			throws BizException {
		
		PointClassDef classDef = (PointClassDef)pointClassDefDAO.findByPk(pointClassDef.getPtClass());
		Assert.notNull(classDef, "该积分子类型不存在");
		pointClassDef.setCardIssuer(classDef.getCardIssuer());
		pointClassDef.setJinstId(classDef.getJinstId());
		pointClassDef.setJinstName(classDef.getJinstName());
		pointClassDef.setJinstType(classDef.getJinstType());
		
		String usage = pointClassDef.getPtUsage();
		Short ptLatestCyc = null; 
		if(PtUsageType.FORVEREFFECTIVE.getValue().equals(usage)||
				PtUsageType.INSTM2.getValue().equals(usage)||
				PtUsageType.INSTM3.getValue().equals(usage)){
			ptLatestCyc = 1;
		}else if(PtUsageType.INSTM1.getValue().equals(usage)){
			ptLatestCyc = 2;
		}
		
		pointClassDef.setPtLatestCyc(ptLatestCyc);
		BigDecimal ptDeprecRate = pointClassDef.getPtDeprecRate();
		BigDecimal PtDiscntRate = pointClassDef.getPtDiscntRate();
		if(ptDeprecRate!=null && StringUtils.isNotBlank(ptDeprecRate.toString())){
			ptDeprecRate = ptDeprecRate.divide(new BigDecimal(100));
			pointClassDef.setPtDeprecRate(ptDeprecRate);
		}
		if(PtDiscntRate!=null && StringUtils.isNotBlank(PtDiscntRate.toString())){
			PtDiscntRate = PtDiscntRate.divide(new BigDecimal(100));
			pointClassDef.setPtDiscntRate(PtDiscntRate);
		}
		
		// 修改积分用途
		List<String> codeList = PtUseLimitType.getAllCode();
		StringBuffer ptUselimit = new StringBuffer(Constants.PT_USE_LIMIT_DEFAULT);
		
		if(ptUseLimitCodes!=null){
			for (String code : ptUseLimitCodes) {
				int index = codeList.indexOf(code);
				ptUselimit.replace(index, index+1, "1");
			}
		}
		
		pointClassDef.setPtUseLimit(ptUselimit.toString());
		
		int count = pointClassDefDAO.update(pointClassDef);
		return count>0;
	}

	public boolean addCouponClassDef(CouponClassDef couponClassDef)
			throws BizException {
		Assert.notEmpty(couponClassDef.getIssId(), "发行机构不能为空");
		// 检查发卡机构的业务权限
		if (StringUtils.isNotEmpty(couponClassDef.getIssId())) {
			checkServiceAuthority(couponClassDef.getIssId(), CardType.COUPON.getValue());
		}
		
		Object obj = couponClassDefDAO.insert(couponClassDef);
		return obj!=null;
	}

	public boolean deleteCouponClassDef(CouponClassDef couponClassDef)
			throws BizException {
		CouponClassDef classDef = (CouponClassDef)couponClassDefDAO.findByPk(couponClassDef.getCoupnClass());
		Assert.notNull(classDef, "此赠券子类型不存在");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("couponClass", classDef.getCoupnClass());
		Collection<CardSubClassDef> dataList = this.cardSubClassDefDAO.findCardSubClassDef(params, 0, 20).getData();
		
		Assert.isEmpty(dataList, "该赠券子类型已在使用，不能删除！");
		
		int count = couponClassDefDAO.delete(couponClassDef.getCoupnClass());
		return count>0;
	}

	public boolean modifyCouponClassDef(CouponClassDef couponClassDef)
			throws BizException {
		Assert.notNull(couponClassDef, "修改的赠券子类型不能为空");
		int count = couponClassDefDAO.update(couponClassDef);
		return count>0;
	}

	public boolean addAccuClassDef(AccuClassDef accuClassDef)
			throws BizException {
		Assert.notEmpty(accuClassDef.getCardIssuer(), "发卡结构编号不能为空");
		// 检查发卡机构的业务权限
		if (StringUtils.isNotEmpty(accuClassDef.getCardIssuer())) {
			checkServiceAuthority(accuClassDef.getCardIssuer(), CardType.ACCU.getValue());
		}
		Object obj = accuClassDefDAO.insert(accuClassDef);
		return obj!=null;
	}

	public boolean deleteAccuClassDef(AccuClassDef accuClassDef)
			throws BizException {
		AccuClassDef classDef = (AccuClassDef)accuClassDefDAO.findByPk(accuClassDef.getAccuClass());
		Assert.notNull(classDef, "该次卡子类型不存在");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("frequencyClass", classDef.getAccuClass());
		Collection<CardSubClassDef> dataList = this.cardSubClassDefDAO.findCardSubClassDef(params, 0, 20).getData();
		
		Assert.isEmpty(dataList, "该次卡子类型已在使用，不能删除！");
		
		int count = accuClassDefDAO.delete(classDef.getAccuClass());
		return count>0;
	}

	public boolean modifyAccuClassDef(AccuClassDef accuClassDef)
			throws BizException {
		AccuClassDef classDef = (AccuClassDef)accuClassDefDAO.findByPk(accuClassDef.getAccuClass());
		Assert.notNull(classDef, "该次卡子类型不存在");
		accuClassDef.setCardIssuer(classDef.getCardIssuer());
		accuClassDef.setJinstId(classDef.getJinstId());
		accuClassDef.setJinstName(classDef.getJinstName());
		accuClassDef.setJinstType(classDef.getJinstType());
		int count = accuClassDefDAO.update(accuClassDef);
		return count>0;
	}

	public boolean addDiscntClassDef(DiscntClassDef discntClassDef)
			throws BizException {
		Assert.notNull(discntClassDef, "添加的折扣子类型对象不能为空");
		Assert.notEmpty(discntClassDef.getCardIssuer(), "发卡结构编号不能为空");
		// 检查发卡机构的业务权限
		if (StringUtils.isNotEmpty(discntClassDef.getCardIssuer())) {
			checkServiceAuthority(discntClassDef.getCardIssuer(), CardType.DISCNT.getValue());
		}
		return this.discntClassDefDAO.insert(discntClassDef) != null;
	}

	public boolean deleteDiscntClassDef(String discntClass)
			throws BizException {
		DiscntClassDef classDef = (DiscntClassDef) this.discntClassDefDAO.findByPk(discntClass);
		Assert.notNull(discntClass, "删除的折扣子类型对象不能为空");	
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("discntClass", classDef.getDiscntClass());
		Collection<CardSubClassDef> dataList = this.cardSubClassDefDAO.findCardSubClassDef(params, 0, 20).getData();
		
		Assert.isEmpty(dataList, "该折扣子类型已在使用，不能删除！");
		
		int count = this.discntClassDefDAO.delete(discntClass);
		return count > 0;
	}

	public boolean modifyDiscntClassDef(DiscntClassDef discntClassDef)
			throws BizException {
		Assert.notNull(discntClassDef, "更新的折扣子类型对象不能为空");
		int count = this.discntClassDefDAO.update(discntClassDef);
		return count>0;
	}
	
	private void checkServiceAuthority(String cardIssuer, String cardType) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("insId", cardIssuer);
		params.put("serviceId", TradeCardTypeMap.getInsServiceTradeType(cardType));
		params.put("status", CardTypeState.NORMAL.getValue());
		List<InsServiceAuthority> authorityList = this.insServiceAuthorityDAO.getInsServiceAuthority(params);
		if (CollectionUtils.isEmpty(authorityList)) {
			throw new BizException("发卡机构[" + cardIssuer + "]没有开通[" 
					+ CardType.valueOf(cardType).getName() 
					+ "]功能，请联系运营分支机构管理员。");
		}
	}

	public String getPtUseLimit(String ptUseLimit) throws BizException {
		Assert.notNull(ptUseLimit, "解析的积分用途对象不能为空！");
		
		List<PtUseLimitType> ptUseLimitList = PtUseLimitType.getAll();
		String ptUseLimitChinese = "";
		
		for(int i = 0; i < ptUseLimit.length(); i++){
			if(ptUseLimit.substring(i, i+1).equals("1")){ 
				ptUseLimitChinese = ptUseLimitChinese + ptUseLimitList.get(i).getName() + "、";
			}
		}
		
		// 如果字符串非空，则剔除最后一个逗号
		if(StringUtils.isNotEmpty(ptUseLimitChinese)){
			ptUseLimitChinese = ptUseLimitChinese.substring(0, ptUseLimitChinese.length()-1);
		}
		
		return ptUseLimitChinese;
	}

	public List getPtUseLimitCode(String ptUseLimit) throws BizException {
		Assert.notNull(ptUseLimit, "解析的积分用途对象不能为空！");
		
		List<PtUseLimitType> ptUseLimitList = PtUseLimitType.getAll();
		List<String> ptUseLimitCodeList = new ArrayList<String>();
		
		for(int i = 0; i < ptUseLimit.length(); i++){
			if(ptUseLimit.substring(i, i+1).equals("1")){ 
				ptUseLimitCodeList.add(ptUseLimitList.get(i).getValue());
			}
		}
		
		return ptUseLimitCodeList;
	}
	
}
