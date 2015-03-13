package gnete.card.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gnete.card.dao.BaodePointExcParaDAO;
import gnete.card.entity.BaodePointExcPara;
import gnete.card.entity.BaodePointExcParaKey;
import gnete.card.service.BaodeService;
import gnete.etc.Assert;
import gnete.etc.BizException;

@Service("baodeService")
public class BaodeServiceImpl implements BaodeService {

	@Autowired
	private BaodePointExcParaDAO baodePointExcParaDAO;
	
	public boolean addBaodePointExcPara(BaodePointExcPara baodePointExcPara,
			String userId) throws BizException {
		Assert.isTrue(baodePointExcPara.getBranchCode()!=null && baodePointExcPara.getMerNo()!=null && 
						baodePointExcPara.getPtClass()!=null, "发卡机构、商户、积分类型不能为空！");
		
		BaodePointExcParaKey key = new BaodePointExcParaKey();
		key.setBranchCode(baodePointExcPara.getBranchCode());
		key.setMerNo(baodePointExcPara.getMerNo());
		key.setPtClass(baodePointExcPara.getPtClass());
		
		BaodePointExcPara oldPara = (BaodePointExcPara) this.baodePointExcParaDAO.findByPk(key);
		Assert.isNull(oldPara, "发卡机构[" + baodePointExcPara.getBranchCode() + 
				"]、商户" + baodePointExcPara.getMerNo()+ "]、积分类型" + "[" + 
				baodePointExcPara.getPtClass() + "]参数定义已经存在！");
		
		baodePointExcPara.setUpdateBy(userId);
		baodePointExcPara.setUpdateTime(new Date());
		
		return this.baodePointExcParaDAO.insert(baodePointExcPara)!=null;
	}

	public boolean deleteBaodePointExcPara(BaodePointExcParaKey key)
			throws BizException {
		Assert.isTrue(key.getBranchCode()!=null && key.getMerNo()!=null && 
						key.getPtClass()!=null, "发卡机构、商户、积分类型不能为空！");
		
		return this.baodePointExcParaDAO.delete(key) > 0;
	}

	public boolean modifyBaodePointExcPara(BaodePointExcPara baodePointExcPara,
			String userId) throws BizException {
		Assert.isTrue(baodePointExcPara.getBranchCode()!=null && baodePointExcPara.getMerNo()!=null && 
				baodePointExcPara.getPtClass()!=null, "发卡机构、商户、积分类型不能为空！");

		baodePointExcPara.setUpdateBy(userId);
		baodePointExcPara.setUpdateTime(new Date());
		
		return this.baodePointExcParaDAO.update(baodePointExcPara) > 0;
	}

}
