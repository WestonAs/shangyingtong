package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.PosProvSharesDAO;
import gnete.card.dao.PosProvSharesHisDAO;
import gnete.card.entity.PosProvShares;
import gnete.card.entity.PosProvSharesHis;
import gnete.card.entity.PosProvSharesKey;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.PosProvSharesFeeType;
import gnete.card.service.PosProvSharesService;
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

@Service("posProvSharesService")
public class PosProvSharesServiceImpl implements PosProvSharesService {
	
	@Autowired
	private PosProvSharesDAO posProvSharesDAO;
	
	@Autowired
	private PosProvSharesHisDAO posProvSharesHisDAO;
	
	public boolean addPosProvShares(PosProvShares[] feeArray, String sessionUserCode) throws BizException{
		Assert.notNull(feeArray, "添加的分支机构与机具出机方分润参数对象不能为空");
		
		for (PosProvShares posProvShares : feeArray){
			Assert.notNull(posProvShares, "添加的分支机构与机具出机方分润参数对象不能为空");

			if(posProvShares.getFeeType().equals(PosProvSharesFeeType.FEE.getValue())){
				posProvShares.setUlMoney(AmountUtil.format(Constants.FEE_MAXACCOUNT));
			}
			
			PosProvSharesKey posProvSharesKey = new PosProvSharesKey();
			posProvSharesKey.setBranchCode(posProvShares.getBranchCode());
			posProvSharesKey.setPosProvId(posProvShares.getPosProvId());
			posProvSharesKey.setUlMoney(AmountUtil.format(posProvShares.getUlMoney()));
			PosProvShares shares = (PosProvShares)posProvSharesDAO.findByPk(posProvSharesKey);
			Assert.isNull(shares, "该金额段的分润已经配置");
			Map param = new HashMap();
			param.put("posProvId", posProvShares.getPosProvId());
			param.put("branchCode", posProvShares.getBranchCode());
			List<PosProvShares> list = this.posProvSharesDAO.findPosProvShares(param, 1, 1).getList();
			
			for(PosProvShares share : list){
				Assert.isTrue(share.getFeeType().equals(posProvShares.getFeeType()), "" +
						"该机具出机方已经配置了"+PosProvSharesFeeType.valueOf(share.getFeeType()).getName()+"分润方式不能再进行其他设置");
				Assert.isTrue(share.getCostCycle().equals(posProvShares.getCostCycle()), 
						"该机具出机方已经配置了计费周期:"+CostCycleType.valueOf(share.getCostCycle()).getName()+"计费周期,不能再配置其他计费周期");
			}
			
			/*if(list.size() > 0){
				PosProvShares shares1 = (PosProvShares)list.get(0);
				Assert.isTrue(shares1.getFeeType().equals(posProvShares.getFeeType()), "" +
						"该机具出机方已经配置了"+PosProvSharesFeeType.valueOf(shares1.getFeeType()).getName()+",分润方式不能再进行其他设置");
				Assert.isTrue(shares1.getCostCycle().equals(posProvShares.getCostCycle()), 
						"该机具出机方已经配置了计费周期:"+CostCycleType.valueOf(shares1.getCostCycle()).getName()+"计费周期,不能再配置其他计费周期");
			}*/
			
			posProvShares.setUpdateTime(new Date());
			this.posProvSharesDAO.insert(posProvShares);
		}
		return true;
	}
	
	public boolean modifyPosProvShares(PosProvShares posProvShares) throws BizException{
		Assert.notNull(posProvShares, "修改的分支机构与机具出机方分润参数对象不能为空");
		PosProvSharesKey posProvSharesKey = new PosProvSharesKey();
		posProvSharesKey.setBranchCode(posProvShares.getBranchCode());
		posProvSharesKey.setPosProvId(posProvShares.getPosProvId());
		posProvSharesKey.setUlMoney(AmountUtil.format(posProvShares.getUlMoney()));
		PosProvShares shares = (PosProvShares)posProvSharesDAO.findByPk(posProvSharesKey); 
		Assert.notNull(shares, "分支机构与机具出机方分润参数对象已不存在");
		PosProvSharesHis his = new PosProvSharesHis();
		try{
			BeanUtils.copyProperties(his, shares);
		}catch(Exception e){
			throw new BizException("复制对象时出现异常");
		}
		his.setUpdateBy(shares.getUpdateBy());
		his.setUpdateTime(shares.getUpdateTime());
		posProvShares.setUpdateTime(new Date());
		Object obj = this.posProvSharesHisDAO.insert(his);
		shares.setNewUlMoney(posProvShares.getNewUlMoney());
		shares.setFeeRate(posProvShares.getFeeRate());
		shares.setModifyDate(DateUtil.getCurrentDate());
		shares.setUpdateBy(posProvShares.getUpdateBy());
		shares.setUpdateTime(new Date());
		int count = this.posProvSharesDAO.update(shares);
		return count>0&&obj!=null;
	}
	
	public boolean deletePosProvShares(PosProvShares posProvShares) throws BizException{
		Assert.notNull(posProvShares, "删除的分支机构与机具出机方分润参数对象不能为空");
		PosProvSharesKey posProvSharesKey = new PosProvSharesKey();
		posProvSharesKey.setBranchCode(posProvShares.getBranchCode());
		posProvSharesKey.setPosProvId(posProvShares.getPosProvId());
		posProvSharesKey.setUlMoney(AmountUtil.format(posProvShares.getUlMoney()));
		PosProvShares shares = (PosProvShares)this.posProvSharesDAO.findByPk(posProvSharesKey);
		Assert.notNull(shares, "分支机构与机具出机方分润参数对象已不存在");
		PosProvSharesHis his = new PosProvSharesHis();
		try{
			BeanUtils.copyProperties(his, shares);
		}catch(Exception e){
			throw new BizException("复制对象时出现异常");
		}
		his.setUpdateTime(shares.getUpdateTime());
		Object obj = this.posProvSharesHisDAO.insert(his);
		int count = this.posProvSharesDAO.delete(posProvSharesKey);
		return count>0&&obj!=null;
	}

}
