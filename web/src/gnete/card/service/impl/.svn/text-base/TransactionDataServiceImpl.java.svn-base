package gnete.card.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gnete.card.dao.AddMagRegDAO;
import gnete.card.dao.RmaTransTypeLimitDAO;
import gnete.card.dao.TransLimitDAO;
import gnete.card.entity.RmaTransTypeLimit;
import gnete.card.entity.RmaTransTypeLimitKey;
import gnete.card.entity.TransLimit;
import gnete.card.entity.TransLimitKey;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.DSetTransType;
import gnete.card.service.TransactionDataService;
import gnete.etc.Assert;
import gnete.etc.BizException;

@Service("transactionDataService")
public class TransactionDataServiceImpl implements TransactionDataService {

	@Autowired
	private AddMagRegDAO addMagRegDAO;
	@Autowired
	private TransLimitDAO transLimitDAO;
	@Autowired
	private RmaTransTypeLimitDAO rmaTransTypeLimitDAO;
	
	public boolean delete(long addMagId) throws BizException {
		Assert.notNull(addMagId, "删除的写磁记录不能为空");		
		return this.addMagRegDAO.delete(addMagId) > 0;	
	}

	public boolean addTransLimit(TransLimit[] transLimitList, String userId) throws BizException {
		
		Assert.notNull(transLimitList, "添加的交易控制规则定义对象不能为空。");
		
		for(TransLimit transLimit : transLimitList){
			Assert.notNull(transLimit, "添加的交易控制规则定义对象不能为空。");
			TransLimitKey key = new TransLimitKey();
			key.setCardIssuer(transLimit.getCardIssuer());
			key.setMerNo(transLimit.getMerNo());
			key.setCardBin("*");
			if(StringUtils.isEmpty(transLimit.getTransType())){
				key.setTransType("  ");
			} else{
				key.setTransType(transLimit.getTransType());
			}
			
			Assert.isNull((TransLimit)this.transLimitDAO.findByPk(key), "商户["+transLimit.getMerNo()+"]已经设置了通用卡BIN的交易控制规则定义，不能再设置。");
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			if(transLimit.getCardBin()=="*"){ //通用卡BIN
				params.put("cardIssuer", transLimit.getCardIssuer());
				params.put("merNo", transLimit.getMerNo());
				params.put("transType", "  ");
				Assert.isEmpty(this.transLimitDAO.getTransLimitList(params), 
								"商户["+transLimit.getMerNo()+"]已经设置了某些卡BIN的交易控制规则定义，不能再对通用卡BIN设置规则。");
			} 
			else { // 单独卡BIN
				key.setCardBin(transLimit.getCardBin());
				Assert.isNull((TransLimit)this.transLimitDAO.findByPk(key), "发卡机构、商户["+transLimit.getMerNo()+"]与卡BIN["+transLimit.getCardBin()+
						"]的交易控制规则定义已经存在。");
			}
			
			transLimit.setUpdateBy(userId);
			transLimit.setUpdateTime(new Date());
			if(StringUtils.isEmpty(transLimit.getTransType())){
				transLimit.setTransType("  ");
			} 
			transLimit.setStatus(CardTypeState.NORMAL.getValue());
			this.transLimitDAO.insert(transLimit);
		}
		
		return true;
	}

	public boolean deleteTransLimit(TransLimitKey key) throws BizException {
		Assert.notNull(key, "删除的交易控制规则定义对象不能为空");		
		return this.transLimitDAO.delete(key) > 0;	
	}

	public boolean modifyTransLimit(TransLimit transLimit, String userId) throws BizException {
		
		Assert.notNull(transLimit, "修改的交易控制规则定义对象不能为空。");
		
		if(CardTypeState.NORMAL.getValue().equals(transLimit.getStatus())){
			transLimit.setStatus(CardTypeState.CANCEL.getValue());
		}else if(CardTypeState.CANCEL.getValue().equals(transLimit.getStatus())){
			transLimit.setStatus(CardTypeState.NORMAL.getValue());
		}
		transLimit.setUpdateBy(userId);
		transLimit.setUpdateTime(new Date());
		if(StringUtils.isEmpty(transLimit.getTransType())){
			transLimit.setTransType("  ");
		} 
		return this.transLimitDAO.update(transLimit)>0;
	}

	public void addRmaTransTypeLimit(RmaTransTypeLimit rmaTransTypeLimit,
			String[] transTypes, String userId) throws BizException {
		Assert.notNull(rmaTransTypeLimit, "添加的划付交易限制定义不能为空");
		
		Assert.notEmpty(transTypes, "要添加的交易限制不能为空。");
		Assert.notEmpty(userId, "登录用户信息不能为空");
		
		RmaTransTypeLimitKey key = new RmaTransTypeLimitKey();
		
		for (int i = 0; i < transTypes.length; i++) {
			
			//检查新增对象是否已经存在
			key.setInsCode(rmaTransTypeLimit.getInsCode());
			key.setTransType(transTypes[i]);
			
			RmaTransTypeLimit old = (RmaTransTypeLimit)this.rmaTransTypeLimitDAO.findByPk(key);
			
			Assert.isNull(old, "发卡机构["+ rmaTransTypeLimit.getInsCode() + "]交易类型为[" 
					+ DSetTransType.valueOf(transTypes[i]).getName() + "]的划付交易限制已经定义，不能重复定义");
			
			rmaTransTypeLimit.setTransType(transTypes[i]);
			rmaTransTypeLimit.setStatus(CardTypeState.NORMAL.getValue());		
			rmaTransTypeLimit.setUpdateTime(new Date());
			rmaTransTypeLimit.setUpdateBy(userId);
			
			this.rmaTransTypeLimitDAO.insert(rmaTransTypeLimit); 
		}
	}

	public boolean modifyRmaTransTypeLimit(RmaTransTypeLimit rmaTransTypeLimit,
			String userId) throws BizException {
		Assert.notNull(rmaTransTypeLimit, "修改的发卡机构划付交易限制参数不能为空");		
		
		if(CardTypeState.NORMAL.getValue().equals(rmaTransTypeLimit.getStatus())){
			rmaTransTypeLimit.setStatus(CardTypeState.CANCEL.getValue());
		}else if(CardTypeState.CANCEL.getValue().equals(rmaTransTypeLimit.getStatus())){
			rmaTransTypeLimit.setStatus(CardTypeState.NORMAL.getValue());
		}
		
		rmaTransTypeLimit.setUpdateBy(userId);
		rmaTransTypeLimit.setUpdateTime(new Date());
		return this.rmaTransTypeLimitDAO.update(rmaTransTypeLimit) > 0;
	}

	public boolean deleteRmaTransTypeLimit(RmaTransTypeLimitKey key)
			throws BizException {
		Assert.notNull(key, "删除的划付交易限制参数不能为空");		
		return this.rmaTransTypeLimitDAO.delete(key) > 0;	
	}


}
