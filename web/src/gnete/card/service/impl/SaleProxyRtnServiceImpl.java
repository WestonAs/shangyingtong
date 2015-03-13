package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.SaleProxyRtnDAO;
import gnete.card.dao.SaleProxyRtnHisDAO;
import gnete.card.entity.SaleProxyRtn;
import gnete.card.entity.SaleProxyRtnHis;
import gnete.card.entity.SaleProxyRtnKey;
import gnete.card.service.SaleProxyRtnService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("saleProxyRtnService")
public class SaleProxyRtnServiceImpl implements SaleProxyRtnService {
	
	@Autowired
	private SaleProxyRtnDAO saleProxyRtnDAO;
	@Autowired
	private SaleProxyRtnHisDAO saleProxyRtnHisDAO;
	
	public boolean addSaleProxyRtn(List<SaleProxyRtn> saleFee, String userCode)
			throws BizException {
		Assert.notTrue(CollectionUtils.isEmpty(saleFee), "添加的运营中心与发卡机构手续费参数对象不能为空");
		
		for (SaleProxyRtn saleProxyRtn : saleFee){
			SaleProxyRtnKey key = new SaleProxyRtnKey();
			key.setBranchCode(saleProxyRtn.getBranchCode());
			key.setProxyId(saleProxyRtn.getProxyId());
			key.setUlMoney(AmountUtil.format(saleProxyRtn.getUlMoney()));
			key.setCardBin(saleProxyRtn.getCardBin());
			key.setTransType(saleProxyRtn.getTransType());
			SaleProxyRtn rtn = (SaleProxyRtn) saleProxyRtnDAO.findByPk(key);
			Assert.isNull(rtn,"该发卡机构的该售卡代理商的此金额段的返利已经配置");
			
			saleProxyRtn.setModifyDate(DateUtil.getCurrentDate());
			saleProxyRtn.setUpdateTime(new Date());
			saleProxyRtn.setUpdateBy(userCode);
			
//			Map param = new HashMap();
//			List list = null;
//			param.put("branchCode", saleProxyRtn.getBranchCode());
//			param.put("proxyId", saleProxyRtn.getProxyId());
//			param.put("cardBin", saleProxyRtn.getCardBin());
//			param.put("transType", saleProxyRtn.getTransType());
//			list = this.saleProxyRtnDAO.findSaleProxyRtn(param, 1, 1).getList();
//			
//			Assert.isTrue(CollectionUtils.isEmpty(list), "该售卡代理商["+ saleProxyRtn.getProxyId() 
//					+"]已经配置了该卡BIN["+ saleProxyRtn.getCardBin() +"]该交易类型的["+ saleProxyRtn.getTransType() +"]的参数");
			
			saleProxyRtnDAO.insert(saleProxyRtn);
		}
		return true;
	}

	public boolean deleteSaleProxyRtn(SaleProxyRtn saleProxyRtn)
			throws BizException {
		SaleProxyRtnKey key = new SaleProxyRtnKey();
		key.setBranchCode(saleProxyRtn.getBranchCode());
		key.setProxyId(saleProxyRtn.getProxyId());
		key.setUlMoney(AmountUtil.format(saleProxyRtn.getUlMoney()));
		key.setCardBin(saleProxyRtn.getCardBin());
		key.setTransType(saleProxyRtn.getTransType());
		SaleProxyRtn rtn = (SaleProxyRtn) saleProxyRtnDAO.findByPk(key);
		Assert.notNull(rtn,"该发卡机构的该售卡代理商的此金额段的返利已经不存在");
		SaleProxyRtnHis his = new SaleProxyRtnHis();
		try{
			BeanUtils.copyProperties(his, rtn);
		}catch(Exception e){
			throw new BizException("复制对象时出现异常");
		}
		his.setUpdateBy(rtn.getUpdateBy());
		his.setUpdateTime(rtn.getUpdateTime());
		Object obj = this.saleProxyRtnHisDAO.insert(his);
		int count = saleProxyRtnDAO.delete(key);
		return count>0&&obj!=null;
	}

	public boolean modifySaleProxyRtn(SaleProxyRtn saleProxyRtn)
			throws BizException {
		SaleProxyRtnKey key = new SaleProxyRtnKey();
		key.setBranchCode(saleProxyRtn.getBranchCode());
		key.setProxyId(saleProxyRtn.getProxyId());
		key.setUlMoney(AmountUtil.format(saleProxyRtn.getUlMoney()));
		key.setCardBin(saleProxyRtn.getCardBin());
		key.setTransType(saleProxyRtn.getTransType());
		SaleProxyRtn rtn = (SaleProxyRtn) saleProxyRtnDAO.findByPk(key);
		Assert.notNull(rtn,"该发卡机构的该售卡代理商的此金额段的返利已经不存在");
		SaleProxyRtnHis his = new SaleProxyRtnHis();
		try{
			BeanUtils.copyProperties(his, rtn);
		}catch(Exception e){
			throw new BizException("复制对象时出现异常");
		}
		his.setUpdateBy(rtn.getUpdateBy());
		his.setUpdateTime(rtn.getUpdateTime());
		Object obj =  this.saleProxyRtnHisDAO.insert(his);
		rtn.setFeeRate(saleProxyRtn.getFeeRate());
		rtn.setUpdateBy(saleProxyRtn.getUpdateBy());
		rtn.setUpdateTime(new Date());
		rtn.setNewUlMoney(saleProxyRtn.getNewUlMoney());
		int count = saleProxyRtnDAO.update(rtn);
		return count>0&&obj!=null;
	}

}
