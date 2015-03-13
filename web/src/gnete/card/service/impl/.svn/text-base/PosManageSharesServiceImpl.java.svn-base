package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.PosManageSharesDAO;
import gnete.card.dao.PosManageSharesHisDAO;
import gnete.card.entity.PosManageShares;
import gnete.card.entity.PosManageSharesHis;
import gnete.card.entity.PosManageSharesKey;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.PosManageSharesFeeType;
import gnete.card.service.PosManageSharesService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("posManageSharesService")
public class PosManageSharesServiceImpl implements PosManageSharesService {
	
	@Autowired
	private PosManageSharesDAO posManageSharesDAO;
	
	@Autowired
	private PosManageSharesHisDAO posManageSharesHisDAO;
	
	public boolean addPosManageShares(PosManageShares[] feeArray, String sessionUserCode) throws BizException{
		Assert.notNull(feeArray, "添加的分支机构与机具维护方分润参数对象不能为空");
		for (PosManageShares posManageShares : feeArray){
			Assert.notNull(posManageShares, "添加的分支机构与机具维护方分润参数对象不能为空");
			if(posManageShares.getFeeType().equals(PosManageSharesFeeType.FEE.getValue())){
				posManageShares.setUlMoney(Constants.FEE_MAXACCOUNT);
			}
			PosManageSharesKey posManageSharesKey = new PosManageSharesKey();
			posManageSharesKey.setBranchCode(posManageShares.getBranchCode());
			posManageSharesKey.setPosManageId(posManageShares.getPosManageId());
			posManageSharesKey.setUlMoney(AmountUtil.format(posManageShares.getUlMoney()));
			PosManageShares shares = (PosManageShares)posManageSharesDAO.findByPk(posManageSharesKey);
			Assert.isNull(shares, "该金额段的分润已经配置");
			Map param = new HashMap();
			param.put("posManageId", posManageShares.getPosManageId());
			List list = this.posManageSharesDAO.findPosManageShares(param, 1, 1).getList();
			if(list.size()>0){
				PosManageShares shares1 = (PosManageShares)list.get(0);
				Assert.isTrue(shares1.getFeeType().equals(posManageShares.getFeeType()), 
						"该机具维护方已经配置了"+PosManageSharesFeeType.valueOf(shares1.getFeeType()).getName()+"分润方式,不能再进行其他设置");
				Assert.isTrue(shares1.getCostCycle().equals(posManageShares.getCostCycle()), 
						"该机具维护方已经配置了计费周期:"+CostCycleType.valueOf(shares1.getCostCycle()).getName()+"计费周期,不能再配置其他计费周期");
			}
			posManageShares.setUpdateTime(new Date());
			posManageShares.setModifyDate(DateUtil.getCurrentDate());
			this.posManageSharesDAO.insert(posManageShares);
		}
		return true;
	}
	
	public boolean modifyPosManageShares(PosManageShares posManageShares) throws BizException{
		Assert.notNull(posManageShares, "修改的分支机构与机具维护方分润参数对象不能为空");
		PosManageSharesKey posManageSharesKey = new PosManageSharesKey();
		posManageSharesKey.setBranchCode(posManageShares.getBranchCode());
		posManageSharesKey.setPosManageId(posManageShares.getPosManageId());
		posManageSharesKey.setUlMoney(AmountUtil.format(posManageShares.getUlMoney()));
		PosManageShares shares = (PosManageShares)posManageSharesDAO.findByPk(posManageSharesKey); 
		Assert.notNull(shares, "分支机构与机具维护方分润参数对象已不存在");
		PosManageSharesHis his = new PosManageSharesHis();
		try{
			BeanUtils.copyProperties(his, shares);
		}catch(Exception e){
			throw new BizException("复制对象时出现异常");
		}
		his.setUpdateBy(shares.getUpdateBy());
		his.setUpdateTime(shares.getUpdateTime());
		posManageShares.setUpdateTime(new Date());
		Object obj = this.posManageSharesHisDAO.insert(his);
		
		shares.setNewUlMoney(posManageShares.getNewUlMoney());
		shares.setFeeRate(posManageShares.getFeeRate());
		shares.setModifyDate(DateUtil.getCurrentDate());
		shares.setUpdateBy(posManageShares.getUpdateBy());
		shares.setUpdateTime(new Date());
		int count = this.posManageSharesDAO.update(shares);
		return count>0&&obj!=null;
	}
	
	public boolean deletePosManageShares(PosManageShares posManageShares) throws BizException{
		Assert.notNull(posManageShares, "删除的分支机构与机具维护方分润参数对象不能为空");
		PosManageSharesKey posManageSharesKey = new PosManageSharesKey();
		posManageSharesKey.setBranchCode(posManageShares.getBranchCode());
		posManageSharesKey.setPosManageId(posManageShares.getPosManageId());
		posManageSharesKey.setUlMoney(AmountUtil.format(posManageShares.getUlMoney()));
		PosManageShares shares = (PosManageShares)this.posManageSharesDAO.findByPk(posManageSharesKey);
		Assert.notNull(shares, "分支机构与机具维护方分润参数对象已不存在");
		PosManageSharesHis his = new PosManageSharesHis();
		try{
			BeanUtils.copyProperties(his, shares);
		}catch(Exception e){
			throw new BizException("复制对象时出现异常");
		}
		his.setUpdateTime(shares.getUpdateTime());
		Object obj = this.posManageSharesHisDAO.insert(his);
		int count = this.posManageSharesDAO.delete(posManageSharesKey);
		return count>0&&obj!=null;
	}

}
