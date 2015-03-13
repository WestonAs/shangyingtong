package gnete.card.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gnete.card.dao.InsServiceAuthorityDAO;
import gnete.card.entity.InsServiceAuthority;
import gnete.card.entity.InsServiceAuthorityKey;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.InsServiceType;
import gnete.card.entity.type.IssType;
import gnete.card.service.InsServiceAuthorityService;
import gnete.etc.Assert;
import gnete.etc.BizException;

@Service("insServiceAuthorityService")
public class InsServiceAuthorityServiceImpl implements
		InsServiceAuthorityService {

	@Autowired
	private InsServiceAuthorityDAO insServiceAuthorityDAO;
	
	public void addInsServiceAuthority(InsServiceAuthority insServiceAuthority,
			String[] serviceIds, String userId) throws BizException {
		Assert.notNull(insServiceAuthority, "添加的业务权限对象不能为空");
		
		Assert.notEmpty(serviceIds, "要添加的权限不能为空。");
		Assert.notEmpty(userId, "登录用户信息不能为空");
		
		for (int i = 0; i < serviceIds.length; i++) {
			InsServiceAuthority serviceAuthority = new InsServiceAuthority();
			
			//检查新增对象是否已经存在
			InsServiceAuthorityKey key = new InsServiceAuthorityKey();
			key.setInsId(insServiceAuthority.getInsId());
			key.setInsType(insServiceAuthority.getInsType());
			key.setServiceId(serviceIds[i]);
			
			InsServiceAuthority old = (InsServiceAuthority)this.insServiceAuthorityDAO.findByPk(key);
			Assert.isNull(old, IssType.valueOf(insServiceAuthority.getInsType()).getName() 
					+ "["+ insServiceAuthority.getInsId() + "]业务类型为[" 
					+ InsServiceType.valueOf(serviceIds[i]).getName() + "]的参数已经定义，不能重复定义");
			
			serviceAuthority.setInsId(key.getInsId());
			serviceAuthority.setInsType(key.getInsType());
			serviceAuthority.setServiceId(key.getServiceId());
			serviceAuthority.setStatus(CardTypeState.NORMAL.getValue());		
			serviceAuthority.setUpdateTime(new Date());
			serviceAuthority.setUpdateBy(userId);
			
			this.insServiceAuthorityDAO.insert(serviceAuthority); 
		}
	}


	public boolean modifyInsServiceAuthority(
			InsServiceAuthority insServiceAuthority, String userId)
			throws BizException {
		Assert.notNull(insServiceAuthority, "更新的业务权限参数对象不能为空");		
		
		if(CardTypeState.NORMAL.getValue().equals(insServiceAuthority.getStatus())){
			insServiceAuthority.setStatus(CardTypeState.CANCEL.getValue());
		}else if(CardTypeState.CANCEL.getValue().equals(insServiceAuthority.getStatus())){
			insServiceAuthority.setStatus(CardTypeState.NORMAL.getValue());
		}
		insServiceAuthority.setUpdateBy(userId);
		insServiceAuthority.setUpdateTime(new Date());
		return this.insServiceAuthorityDAO.update(insServiceAuthority)>0;
	}
	
	public boolean deleteInsServiceAuthority(InsServiceAuthorityKey key) throws BizException {
		Assert.notNull(key, "删除的业务权限参数对象不能为空");		
		return this.insServiceAuthorityDAO.delete(key) > 0;	
	}

}
