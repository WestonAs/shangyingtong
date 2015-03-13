package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.MerchProxySharesDAO;
import gnete.card.dao.MerchProxySharesHisDAO;
import gnete.card.entity.MerchProxyShares;
import gnete.card.entity.MerchProxySharesHis;
import gnete.card.entity.MerchProxySharesKey;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.MerchProxySharesFeeType;
import gnete.card.service.MerchProxySharesService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("merchProxySharesService")
public class MerchProxySharesServiceImpl implements MerchProxySharesService {
	
	@Autowired
	private MerchProxySharesDAO merchProxySharesDAO;
	@Autowired
	private MerchProxySharesHisDAO merchProxySharesHisDAO;
	
	public boolean addMerchProxyShares(MerchProxyShares[] array,
			String userCode) throws BizException {
		for (MerchProxyShares merchProxyShares : array) {
			MerchProxySharesKey key = new MerchProxySharesKey();
			key.setBranchCode(merchProxyShares.getBranchCode());
			key.setProxyId(merchProxyShares.getProxyId());
			key.setMerchId(merchProxyShares.getMerchId());
			key.setUlMoney(AmountUtil.format(merchProxyShares.getUlMoney()));
			MerchProxyShares rtn = (MerchProxyShares) merchProxySharesDAO.findByPk(key);
			Assert.isNull(rtn,"该发卡机构与商户代理商分润参数已经配置");
			Map param = new HashMap();
			param.put("branchCode", merchProxyShares.getBranchCode());
			param.put("proxyId", merchProxyShares.getProxyId());
			param.put("merchId", merchProxyShares.getMerchId());
			List list = this.merchProxySharesDAO.findMerchProxyShares(param, 1, 1).getList();
			if(list.size()>0){
				MerchProxyShares shares1 = (MerchProxyShares)list.get(0);
				if (StringUtils.isNotEmpty(shares1.getFeeType())){
					Assert.isTrue(merchProxyShares.getFeeType().equals(shares1.getFeeType()), 
							"该商户代理商已经配置了"+MerchProxySharesFeeType.valueOf(shares1.getFeeType()).getName()+"分润方式,不能再进行其他设置");
				}
				if (StringUtils.isNotEmpty(shares1.getCostCycle())){
					Assert.isTrue(merchProxyShares.getCostCycle().equals(shares1.getCostCycle()), 
							"该商户代理商已经配置了计费周期:"+CostCycleType.valueOf(shares1.getCostCycle()).getName()+"计费周期,不能再配置其他计费周期");
				}
			}
			
			merchProxyShares.setModifyDate(DateUtil.getCurrentDate());
			merchProxyShares.setUpdateBy(userCode);
			merchProxyShares.setUpdateTime(new Date());
			merchProxySharesDAO.insert(merchProxyShares);
		}
		return true;
	}

	public boolean deleteMerchProxyShares(MerchProxyShares merchProxyShares)
			throws BizException {
		MerchProxySharesKey key = new MerchProxySharesKey();
		key.setBranchCode(merchProxyShares.getBranchCode());
		key.setProxyId(merchProxyShares.getProxyId());
		key.setMerchId(merchProxyShares.getMerchId());
		key.setUlMoney(AmountUtil.format(merchProxyShares.getUlMoney()));
		MerchProxyShares rtn = (MerchProxyShares) merchProxySharesDAO.findByPk(key);
		Assert.notNull(rtn,"该发卡机构与商户代理商分润参数已经不存在");
		MerchProxySharesHis his = new MerchProxySharesHis();
		try{
			BeanUtils.copyProperties(his, rtn);
		}catch(Exception e){
			throw new BizException("复制对象时出现异常");
		}
		his.setUpdateBy(rtn.getUpdateBy());
		his.setUpdateTime(rtn.getUpdateTime());
		Object obj = this.merchProxySharesHisDAO.insert(his);
		
		int count = merchProxySharesDAO.delete(key);
		return count>0&&obj!=null;
	}

	public boolean modifyMerchProxyShares(MerchProxyShares merchProxyShares)
			throws BizException {
		MerchProxySharesKey key = new MerchProxySharesKey();
		key.setBranchCode(merchProxyShares.getBranchCode());
		key.setMerchId(merchProxyShares.getMerchId());
		key.setProxyId(merchProxyShares.getProxyId());
		key.setUlMoney(AmountUtil.format(merchProxyShares.getUlMoney()));
		MerchProxyShares rtn = (MerchProxyShares) merchProxySharesDAO.findByPk(key);
		Assert.notNull(rtn,"该发卡机构与商户代理商分润参数已经不存在");
		MerchProxySharesHis his = new MerchProxySharesHis();
		try{
			BeanUtils.copyProperties(his, rtn);
		}catch(Exception e){
			throw new BizException("复制对象时出现异常");
		}
		his.setUpdateBy(rtn.getUpdateBy());
		his.setUpdateTime(rtn.getUpdateTime());
		Object obj =  this.merchProxySharesHisDAO.insert(his);
		
		rtn.setNewUlMoney(merchProxyShares.getNewUlMoney());
		rtn.setFeeRate(merchProxyShares.getFeeRate());
		rtn.setUpdateBy(merchProxyShares.getUpdateBy());
		rtn.setUpdateTime(new Date());
		int count = merchProxySharesDAO.update(rtn);
		return count>0&&obj!=null;
	}

}
