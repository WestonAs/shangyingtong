package gnete.card.service.impl;

import flink.util.AmountUtil;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.BranchSharesDAO;
import gnete.card.dao.BranchSharesHisDAO;
import gnete.card.dao.ChlFeeDAO;
import gnete.card.dao.ChlFeeTemplateDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BranchShares;
import gnete.card.entity.BranchSharesHis;
import gnete.card.entity.BranchSharesKey;
import gnete.card.entity.ChlFee;
import gnete.card.entity.ChlFeeKey;
import gnete.card.entity.ChlFeeTemplate;
import gnete.card.entity.ChlFeeTemplateKey;
import gnete.card.entity.type.BranchSharesFeeType;
import gnete.card.entity.type.ChlFeeContentType;
import gnete.card.entity.type.ChlFeeType;
import gnete.card.entity.type.CostCycleType;
import gnete.card.service.BranchSharesService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("branchSharesService")
public class BranchSharesServiceImpl implements BranchSharesService {
	
	@Autowired
	private BranchSharesDAO branchSharesDAO;
	@Autowired
	private BranchSharesHisDAO branchSharesHisDAO;
	@Autowired
	private ChlFeeDAO chlFeeDAO;
	@Autowired
	private ChlFeeTemplateDAO chlFeeTemplateDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	public boolean addBranchShares(BranchShares[] feeArray, String sessionUserCode) throws BizException{
		Assert.notNull(feeArray, "设置的分支机构分润参数对象不能为空");
		
		for (BranchShares branchShares : feeArray) {
			Assert.notNull(branchShares, "添加的运营中心与分支机构分润参数对象不能为空");
			if(branchShares.getFeeType().equals(BranchSharesFeeType.FEE.getValue())){
				branchShares.setUlMoney(AmountUtil.format(Constants.FEE_MAXACCOUNT));
			}
			BranchSharesKey branchSharesKey = new BranchSharesKey();
			branchSharesKey.setBranchCode(branchShares.getBranchCode());
			branchSharesKey.setUlMoney(AmountUtil.format(branchShares.getUlMoney()));
			BranchShares shares = (BranchShares)branchSharesDAO.findByPk(branchSharesKey);
			Assert.isNull(shares, "该金额段的分润已经配置");
			Map param = new HashMap();
			param.put("branchCode", branchShares.getBranchCode());
			List list = this.branchSharesDAO.findBranchShares(param, 1, 1).getList();
			if(list.size()>0){
				BranchShares shares1 = (BranchShares)list.get(0);
				Assert.notTrue(shares1.getFeeType().equals(BranchSharesFeeType.FEE.getValue()), 
						"该分支机构已经配置了"+BranchSharesFeeType.FEE.getName()+"分润方式,不能再进行其他设置");
				Assert.isTrue(shares1.getCostCycle().equals(branchShares.getCostCycle()), 
						"该分支机构已经配置了计费周期:"+CostCycleType.valueOf(shares1.getCostCycle()).getName()+"计费周期,不能再配置其他计费周期");
			}
			branchShares.setUpdateTime(new Date());
			this.branchSharesDAO.insert(branchShares);
		}
		return true;
	}
	
	public boolean modifyBranchShares(BranchShares branchShares) throws BizException{
		Assert.notNull(branchShares, "修改的运营中心与分支机构分润参数对象不能为空");
		BranchSharesKey branchSharesKey = new BranchSharesKey();
		branchSharesKey.setBranchCode(branchShares.getBranchCode());
		branchSharesKey.setUlMoney(AmountUtil.format(branchShares.getUlMoney()));
		BranchShares shares = (BranchShares)branchSharesDAO.findByPk(branchSharesKey);
		BranchSharesHis his = new BranchSharesHis();
		try{
			BeanUtils.copyProperties(his, shares);
		} catch (Exception e) {
			throw new BizException("复制对象时出现异常");
		}
		his.setUpdateBy(shares.getUpdateBy());
		his.setUpdateTime(shares.getUpdateTime());
		branchShares.setUpdateTime(new Date());
		Object obj = this.branchSharesHisDAO.insert(his);
		int count = this.branchSharesDAO.update(branchShares);
		return count>0&&obj!=null;
	}
	
	public boolean deleteBranchShares(BranchShares branchShares) throws BizException{
		Assert.notNull(branchShares, "删除的运营中心与分支机构分润参数对象不能为空");
		BranchSharesKey branchSharesKey = new BranchSharesKey();
		branchSharesKey.setBranchCode(branchShares.getBranchCode());
		branchSharesKey.setUlMoney(AmountUtil.format(branchShares.getUlMoney()));
		BranchShares shares = (BranchShares)this.branchSharesDAO.findByPk(branchSharesKey);
		BranchSharesHis his = new BranchSharesHis();
		try{
			BeanUtils.copyProperties(his, shares);
		}catch(Exception e){
			throw new BizException("复制对象时出现异常");
		}
		his.setUpdateTime(shares.getUpdateTime());
		Object obj = this.branchSharesHisDAO.insert(his);
		int count = this.branchSharesDAO.delete(branchSharesKey);
		return count>0&&obj!=null;
	}

	public boolean addChlFee(ChlFee[] feeArray, String sessionUserCode)
			throws BizException {
		Assert.notNull(feeArray, "设置的分支机构平台运营手续费对象不能为空");
		List<ChlFee> chlFeeList = new ArrayList<ChlFee>();
		
		for (ChlFee chlFee : feeArray) {
			Assert.notNull(chlFee, "添加的运营中心与分支机构平台运营手续费对象不能为空");
			
			if(chlFee.getFeeType().equals(ChlFeeType.FIX_MONEY.getValue())||
					chlFee.getFeeType().equals(ChlFeeType.FIX_RATE.getValue())){
				chlFee.setUlMoney(AmountUtil.format(Constants.FEE_MAXACCOUNT));
			}
			
			ChlFeeKey chlFeeKey = new ChlFeeKey();
			chlFeeKey.setBranchCode(chlFee.getBranchCode());
			chlFeeKey.setFeeContent(chlFee.getFeeContent());
			chlFeeKey.setUlMoney(AmountUtil.format(chlFee.getUlMoney()));
			ChlFee fee = (ChlFee)chlFeeDAO.findByPk(chlFeeKey);
			Assert.isNull(fee, "分支机构为" + chlFee.getBranchCode() + "，计费内容为"+ChlFeeContentType.valueOf(chlFee.getFeeContent()).getName()+
					"，金额段为"+chlFee.getUlMoney()+"的手续费已经配置");
			Map param = new HashMap();
			param.put("branchCode", chlFee.getBranchCode());
			
			// 对于同一个分支机构和同一种计费内容，固定金额、固定比率、累进比率只能设置其中一种
			if(chlFee.getFeeType().equals(ChlFeeType.FIX_MONEY.getValue())||
					chlFee.getFeeType().equals(ChlFeeType.FIX_RATE.getValue())||
					chlFee.getFeeType().equals(ChlFeeType.ACCUMULATIVE_RATE.getValue())){
				//chlFee.setCostCycle(CostCycleType.YEAR.getValue());
				param.put("branchCode", chlFee.getBranchCode());
				param.put("feeContent", chlFee.getFeeContent());
				List<ChlFee> oldChlFeeList = this.chlFeeDAO.getChlFeeList(param);
				Assert.isEmpty(oldChlFeeList, "该分支机构已经设置了"+ChlFeeType.valueOf(chlFee.getFeeType()).getName()+"计费类型，不能再进行重复设置。");
			}
			else{
				//chlFee.setCostCycle(CostCycleType.YEAR.getValue());
			}
			BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(chlFee.getBranchCode());
			chlFee.setCurCode(branchInfo.getCurCode());
			chlFee.setUpdateTime(new Date());
			chlFee.setUpdateBy(sessionUserCode);
			chlFeeList.add(chlFee);
			
		}
		
		for(ChlFee chlFee : chlFeeList){
			this.chlFeeDAO.insert(chlFee);
		}
		
		return true;
	}

	public boolean deleteChlFee(ChlFee chlFee) throws BizException {
		Assert.notNull(chlFee, "删除的运营中心与分支机构平台运营手续费对象不能为空");
		ChlFeeKey key = new ChlFeeKey();
		key.setBranchCode(chlFee.getBranchCode());
		key.setUlMoney(AmountUtil.format(chlFee.getUlMoney()));
		key.setFeeContent(chlFee.getFeeContent());

		return this.chlFeeDAO.delete(key) > 0;
	}

	public boolean modifyChlFee(ChlFee chlFee) throws BizException {
		Assert.notNull(chlFee, "修改的运营中心与分支机构平台运营手续费对象不能为空");
		ChlFeeKey key = new ChlFeeKey();
		key.setBranchCode(chlFee.getBranchCode());
		key.setFeeContent(chlFee.getFeeContent());
		key.setUlMoney(chlFee.getUlMoney());
		ChlFee oldChlFee = (ChlFee) this.chlFeeDAO.findByPk(key);
		//chlFee.setFeeType(oldChlFee.getFeeType());
		chlFee.setCostCycle(oldChlFee.getCostCycle());
		chlFee.setAmtOrCnt(oldChlFee.getAmtOrCnt());
		chlFee.setCurCode(oldChlFee.getCurCode());
		chlFee.setUpdateTime(new Date());
		return this.chlFeeDAO.update(chlFee) > 0;
	}

	///------------------------------------------分支机构手续费模板----------------------------------------------------------
	public boolean addChlFeeTemplate(ChlFeeTemplate[] feeArray, String sessionUserCode)
			throws BizException {
		Assert.notNull(feeArray, "设置的分支机构平台运营手续费模板对象不能为空");
		List<ChlFeeTemplate> chlFeeTemplateList = new ArrayList<ChlFeeTemplate>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		List<BigDecimal> ulMoneyList = new ArrayList<BigDecimal>();
		for (ChlFeeTemplate chlFeeTemplate : feeArray) {
			Assert.notNull(chlFeeTemplate, "添加的运营中心与分支机构平台运营手续费模板对象不能为空");
			
			if(chlFeeTemplate.getFeeType().equals(ChlFeeType.FIX_MONEY.getValue())||
					chlFeeTemplate.getFeeType().equals(ChlFeeType.FIX_RATE.getValue())){
				chlFeeTemplate.setUlMoney(AmountUtil.format(Constants.FEE_MAXACCOUNT));
			}
			BigDecimal ulMoney = AmountUtil.format(chlFeeTemplate.getUlMoney());
			if(!ulMoneyList.contains(ulMoney)){
				ulMoneyList.add(ulMoney);
//				params.put("feeContent", chlFeeTemplate.getFeeContent());
//				params.put("ulMoney", AmountUtil.format(chlFeeTemplate.getUlMoney()));
//				List<ChlFeeTemplate> fee =chlFeeTemplateDAO.findList(params);
//				Assert.isEmpty(fee, "计费内容为"+ChlFeeContentType.valueOf(chlFeeTemplate.getFeeContent()).getName()+
//						"，金额段为"+chlFeeTemplate.getUlMoney()+"的手续费已经配置");
			}else{
				throw new BizException("金额段重复:"+ulMoney);
			}

			chlFeeTemplate.setCurCode("CNY");
			chlFeeTemplate.setUpdateTime(new Date());
			chlFeeTemplate.setUpdateBy(sessionUserCode);
			chlFeeTemplateList.add(chlFeeTemplate);
			
		}
		
		for(ChlFeeTemplate chlFeeTemplate : chlFeeTemplateList){
			this.chlFeeTemplateDAO.insert(chlFeeTemplate);
		}
		
		return true;
	}

	public boolean deleteChlFeeTemplate(ChlFeeTemplate chlFeeTemplate) throws BizException {
		Assert.notNull(chlFeeTemplate, "删除的运营中心与分支机构平台运营手续费模板对象不能为空");
		ChlFeeTemplate key = new ChlFeeTemplate();
		key.setTemplateId(chlFeeTemplate.getTemplateId());
		key.setUlMoney(AmountUtil.format(chlFeeTemplate.getUlMoney()));
		key.setFeeContent(chlFeeTemplate.getFeeContent());

		return this.chlFeeTemplateDAO.delete(key) > 0;
	}

	public boolean modifyChlFeeTemplate(ChlFeeTemplate chlFeeTemplate) throws BizException {
		Assert.notNull(chlFeeTemplate, "修改的运营中心与分支机构平台运营手续费模板对象不能为空");
		ChlFeeTemplateKey key = new ChlFeeTemplateKey();
		key.setTemplateId(chlFeeTemplate.getTemplateId());
		key.setFeeContent(chlFeeTemplate.getFeeContent());
		key.setUlMoney(chlFeeTemplate.getUlMoney());
		ChlFeeTemplate oldChlFeeTemplate = (ChlFeeTemplate) this.chlFeeTemplateDAO.findByPk(key);
		//chlFeeTemplate.setFeeType(oldChlFeeTemplate.getFeeType());
		chlFeeTemplate.setCostCycle(oldChlFeeTemplate.getCostCycle());
		chlFeeTemplate.setAmtOrCnt(oldChlFeeTemplate.getAmtOrCnt());
		chlFeeTemplate.setCurCode(oldChlFeeTemplate.getCurCode());
		chlFeeTemplate.setUpdateTime(new Date());
		return this.chlFeeTemplateDAO.update(chlFeeTemplate) > 0;
	}
}
