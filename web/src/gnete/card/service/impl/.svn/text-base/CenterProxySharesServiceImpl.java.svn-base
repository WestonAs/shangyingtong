package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.CenterProxySharesDAO;
import gnete.card.dao.CenterProxySharesHisDAO;
import gnete.card.entity.CenterProxyShares;
import gnete.card.entity.CenterProxySharesHis;
import gnete.card.entity.CenterProxySharesKey;
import gnete.card.entity.type.CenterProxySharesFeeType;
import gnete.card.entity.type.CostCycleType;
import gnete.card.service.CenterProxySharesService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CenterProxySharesService")
public class CenterProxySharesServiceImpl implements CenterProxySharesService {
	
	@Autowired
	private CenterProxySharesDAO centerProxySharesDAO;
	@Autowired
	private CenterProxySharesHisDAO centerProxySharesHisDAO;
	
	public boolean addCenterProxyShares(CenterProxyShares[] feeArray, String sessionUserCode)
			throws BizException {
		Assert.notNull(feeArray, "运营机构代理商分润参数设置对象不能为空");
		
		for (CenterProxyShares centerProxyShares : feeArray){
			CenterProxySharesKey key = new CenterProxySharesKey();
			key.setBranchCode(centerProxyShares.getBranchCode());
			key.setProxyId(centerProxyShares.getProxyId());
			key.setUlMoney(AmountUtil.format(centerProxyShares.getUlMoney()));
			CenterProxyShares rtn = (CenterProxyShares) centerProxySharesDAO.findByPk(key);
			Assert.isNull(rtn,"该运营机构代理商的此金额段的分润已经配置");
			
			Map param = new HashMap();
			param.put("proxyId", centerProxyShares.getProxyId());
			List list = this.centerProxySharesDAO.findCenterProxyShares(param, 1, 1).getList();
			if(list.size()>0){
				CenterProxyShares shares1 = (CenterProxyShares)list.get(0);
				Assert.isTrue(shares1.getFeeType().equals(centerProxyShares.getFeeType()), 
						"该运营机构代理商已经配置了"+CenterProxySharesFeeType.valueOf(shares1.getFeeType()).getName()+"分润方式,不能再进行其他设置");
				Assert.isTrue(shares1.getCostCycle().equals(centerProxyShares.getCostCycle()), 
						"该运营机构代理商已经配置了计费周期:"+CostCycleType.valueOf(shares1.getCostCycle()).getName()+"计费周期,不能再配置其他计费周期");
			}
			
			centerProxyShares.setModifyDate(DateUtil.getCurrentDate());
			centerProxyShares.setUpdateTime(new Date());
			centerProxyShares.setUpdateBy(sessionUserCode);
			centerProxySharesDAO.insert(centerProxyShares);
		}
		return true;
	}

	public boolean deleteCenterProxyShares(CenterProxySharesKey key)
			throws BizException {
		CenterProxyShares rtn = (CenterProxyShares) centerProxySharesDAO.findByPk(key);
		Assert.notNull(rtn,"该运营机构代理商的此金额段的分润已经不存在");
		CenterProxySharesHis his = new CenterProxySharesHis();
		try{
			BeanUtils.copyProperties(his, rtn);
		} catch (Exception e){
			throw new BizException("复制对象时出现异常");
		}
		his.setUpdateBy(rtn.getUpdateBy());
		his.setUpdateTime(rtn.getUpdateTime());
		Object obj = this.centerProxySharesHisDAO.insert(his);
		int count = centerProxySharesDAO.delete(key);
		return count>0&&obj!=null;
	}

	public boolean modifyCenterProxyShares(CenterProxyShares centerProxyShares)
			throws BizException {
		CenterProxySharesKey key = new CenterProxySharesKey();
		key.setBranchCode(centerProxyShares.getBranchCode());
		key.setProxyId(centerProxyShares.getProxyId());
		key.setUlMoney(AmountUtil.format(centerProxyShares.getUlMoney()));
		CenterProxyShares rtn = (CenterProxyShares) centerProxySharesDAO.findByPk(key);
		Assert.notNull(rtn,"该运营机构代理商的此金额段的分润已经不存在");
		CenterProxySharesHis his = new CenterProxySharesHis();
		try{
			BeanUtils.copyProperties(his, rtn);
		} catch (Exception e){
			throw new BizException("复制对象时出现异常");
		}
		his.setUpdateBy(rtn.getUpdateBy());
		his.setUpdateTime(rtn.getUpdateTime());
		Object obj =  this.centerProxySharesHisDAO.insert(his);
		
		rtn.setFeeRate(centerProxyShares.getFeeRate());
		rtn.setUpdateBy(centerProxyShares.getUpdateBy());
		rtn.setUpdateTime(new Date());
		int count = centerProxySharesDAO.update(rtn);
		return count>0&&obj!=null;
	}

}
