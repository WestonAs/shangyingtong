package gnete.card.service.impl;

import gnete.card.dao.CardSubClassTempDAO;
import gnete.card.dao.IcTempParaDAO;
import gnete.card.dao.MembClassTempDAO;
import gnete.card.dao.PointClassTempDAO;
import gnete.card.entity.CardSubClassTemp;
import gnete.card.entity.IcTempPara;
import gnete.card.entity.MembClassTemp;
import gnete.card.entity.PointClassTemp;
import gnete.card.entity.UserInfo;
import gnete.card.entity.flag.CardFlag;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.PtUsageType;
import gnete.card.entity.type.PtUseLimitType;
import gnete.card.service.SingleProductTemplateService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.Symbol;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("singleProductTemplateService")
public class SingleProductTemplateServiceImpl implements
		SingleProductTemplateService {
	
	@Autowired
	private PointClassTempDAO pointClassTempDAO;
	@Autowired
	private MembClassTempDAO membClassTempDAO;
	@Autowired
	private CardSubClassTempDAO subClassTempDAO;
	@Autowired
	private IcTempParaDAO icTempParaDAO;
	
	public boolean deletePointClassTemp(PointClassTemp pointClassTemp)throws BizException {
		PointClassTemp classTemp = (PointClassTemp)this.pointClassTempDAO.findByPk(pointClassTemp.getPtClass());
		Assert.notNull(classTemp, "此积分子类型模板不存在");
		//判断模板是否正在使用
		int count = pointClassTempDAO.delete(pointClassTemp.getPtClass());
		return count>0;
	}
	
	public void addPointClassTemp(PointClassTemp pointClassTemp, String[] ptUseLimitCodes) {
		
		
		String usage = pointClassTemp.getPtUsage();
		Short ptLatestCyc = null; 
		if(PtUsageType.FORVEREFFECTIVE.getValue().equals(usage)||
				PtUsageType.INSTM2.getValue().equals(usage)||
				PtUsageType.INSTM3.getValue().equals(usage)){
			ptLatestCyc = 1;
		}else if(PtUsageType.INSTM1.getValue().equals(usage)){
			ptLatestCyc = 2;
		}
		
		pointClassTemp.setPtLatestCyc(ptLatestCyc);
		BigDecimal ptDeprecRate = pointClassTemp.getPtDeprecRate();
		BigDecimal PtDiscntRate = pointClassTemp.getPtDiscntRate();
		if(ptDeprecRate!=null && StringUtils.isNotBlank(ptDeprecRate.toString())){
			ptDeprecRate = ptDeprecRate.divide(new BigDecimal(100));
			pointClassTemp.setPtDeprecRate(ptDeprecRate);
		}
		if(PtDiscntRate!=null && StringUtils.isNotBlank(PtDiscntRate.toString())){
			PtDiscntRate = PtDiscntRate.divide(new BigDecimal(100));
			pointClassTemp.setPtDiscntRate(PtDiscntRate);
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
		
		pointClassTemp.setPtUseLimit(ptUselimit.toString());
		pointClassTemp.setStatus(CommonState.NORMAL.getValue());
		pointClassTempDAO.insert(pointClassTemp);
	}

	public void modifyPointClassTemp(PointClassTemp pointClassTemp, String[] ptUseLimitCodes,UserInfo sessionUser) throws BizException {
		
		Assert.notNull(pointClassTemp, "传入的积分类型信息不能为空");
		Assert.notEmpty(pointClassTemp.getPtClass(), "积分类型号码不能为空");
		PointClassTemp def = (PointClassTemp) pointClassTempDAO.findByPk(pointClassTemp.getPtClass());
		Assert.notNull(def, "要修改的积分类型模板已经不存在");
		def.setBranchCode(sessionUser.getBranchNo());
		def.setStatus(pointClassTemp.getStatus());
		pointClassTempDAO.update(def);
	}

	public void addMembClassTemp(MembClassTemp membClassTemp,UserInfo sessionUser) throws BizException {
		membClassTemp.setBranchCode(sessionUser.getBranchNo());//机构
		membClassTemp.setUpdateBy(sessionUser.getUserId());
		membClassTemp.setUpdateTime(new Date());
		membClassTemp.setStatus(CommonState.NORMAL.getValue());
		membClassTempDAO.insert(membClassTemp);
	}

	public void modifyMembClassTemp(MembClassTemp membClassTemp,UserInfo sessionUser) throws BizException {
		Assert.notNull(membClassTemp, "传入的会员类型模板信息不能为空");
		Assert.notEmpty(membClassTemp.getMembClass(), "卡类型号码不能为空");
		MembClassTemp def = (MembClassTemp) membClassTempDAO.findByPk(membClassTemp.getMembClass());
		Assert.notNull(def, "要修改的会员类型模板已经不存在");
		def.setBranchCode(sessionUser.getBranchNo());
		def.setUpdateBy(sessionUser.getUserId());
		def.setUpdateTime(new Date());
		def.setStatus(membClassTemp.getStatus());
		membClassTempDAO.update(def);
	}

	public boolean deleteMembClassTemp(MembClassTemp membClassTemp) throws BizException {
		MembClassTemp classTemp = (MembClassTemp)this.membClassTempDAO.findByPk(membClassTemp.getMembClass());
		Assert.notNull(classTemp, "此会员类型模板不存在");
		//判断模板是否正在使用
		int count = membClassTempDAO.delete(membClassTemp.getMembClass());
		return count>0;
	}

	public CardSubClassTemp addSubClassTemp(CardSubClassTemp subClassTemp,IcTempPara icTempPara,UserInfo sessionUser) throws BizException {
		String cardSubClassName = StringUtils.trim(subClassTemp.getCardSubclassName());
		// 检查数据
		Assert.notEmpty(cardSubClassName, "卡类型名称不能为空");
		Assert.notEmpty(subClassTemp.getCardClass(), "卡种不能为空");
		Assert.notEmpty(sessionUser.getBranchNo(), "发卡机构代码不能为空");
		Assert.notEmpty(subClassTemp.getIcType(), "卡片类型不能为空");
		// 卡类型名不能重复 
		Assert.notTrue(this.subClassTempDAO.isExsitCardSubClassName(cardSubClassName), 
				"卡类型名["+ cardSubClassName + "]已经存在，请更换重试");
		subClassTemp.setCardSubclassName(cardSubClassName);
		if (StringUtils.isBlank(subClassTemp.getMustExpirDate())) {
			subClassTemp.setMustExpirDate("20991231");
		}
		subClassTemp.setBranchCode(sessionUser.getBranchNo());
		subClassTemp.setStatus(CommonState.NORMAL.getValue());
		subClassTemp.setUpdateBy(sessionUser.getUserId());
		subClassTemp.setUpdateTime(new Date());
		Object cardSubClass=this.subClassTempDAO.insert(subClassTemp);
		
		// 如果是制IC卡或复合卡的话，则需要在IC个性化参数表中插入数据
		if (!CardFlag.CARD.getValue().equals(subClassTemp.getIcType())) {
			Assert.notNull(icTempPara.getEcashbalance(), "电子现金余额不能为空");
			Assert.notNull(icTempPara.getBalanceLimit(), "电子现金余额上限不能为空");
			Assert.notNull(icTempPara.getAmountLimit(), "电子现金单笔交易限额不能为空");
//			Assert.notEmpty(icTempPara.getOnlineCheck(), "是否执行新卡检测不能为空");
			if (StringUtils.isEmpty(icTempPara.getOnlineCheck())) {
				icTempPara.setOnlineCheck(Symbol.NO);
			}
			icTempPara.setCardSubclass(cardSubClass.toString());
			Assert.notTrue(this.icTempParaDAO.isExist(cardSubClass.toString()), 
					"卡子类型号[" + cardSubClass.toString() + "]的信息已经在IC卡个人化参数表中存在");
			
			this.icTempParaDAO.insert(icTempPara);
		}
		return subClassTemp;
	}

	public boolean deleteSubClassTemp(String cardSubclass) throws BizException {
		CardSubClassTemp classTemp = (CardSubClassTemp)this.subClassTempDAO.findByPk(cardSubclass);
		Assert.notNull(classTemp, "此卡子类型模板不存在");
		//判断模板是否正在使用
		int count = subClassTempDAO.delete(cardSubclass);
		return count>0;
	}

	public void modifySubClassTemp(CardSubClassTemp subClassTemp,UserInfo sessionUser) throws BizException {
		Assert.notNull(subClassTemp, "传入的卡类型信息不能为空");
		Assert.notEmpty(subClassTemp.getCardSubclass(), "卡类型号码不能为空");
		CardSubClassTemp def = (CardSubClassTemp) subClassTempDAO.findByPk(subClassTemp.getCardSubclass());
		Assert.notNull(def, "要修改的卡类型模板已经不存在");
		
		def.setBranchCode(sessionUser.getBranchNo());
		def.setUpdateBy(sessionUser.getUserId());
		def.setUpdateTime(new Date());
		def.setStatus(subClassTemp.getStatus());
		
		this.subClassTempDAO.update(def);
	}


}
