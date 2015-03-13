package gnete.card.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gnete.card.dao.OverdraftLmtRegDAO;
import gnete.card.entity.OverdraftLmtReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.service.OverdraftService;
import gnete.etc.Assert;
import gnete.etc.BizException;

@Service("overdraftService")
public class OverdraftServiceImpl implements OverdraftService {

	@Autowired
	private OverdraftLmtRegDAO overdraftLmtRegDAO;
	
	public boolean addOverdraftLmtReg(OverdraftLmtReg overdraftLmtReg, String sessionUserCode)
			throws BizException {
		
		Assert.notNull(overdraftLmtReg, "添加的账户额度申请不能为空");
		overdraftLmtReg.setUpdateTime(new Date());
		overdraftLmtReg.setUpdateUser(sessionUserCode);
		overdraftLmtReg.setStatus(RegisterState.WAITED.getValue());
		return this.overdraftLmtRegDAO.insert(overdraftLmtReg) != null;
	}

	public boolean delete(Long overdraftLmtId) throws BizException {
		
		Assert.notNull(overdraftLmtId, "删除的账户额度申请对象ID不能为空");		
		int count = this.overdraftLmtRegDAO.delete(overdraftLmtId);
		return count > 0;
	}

	public boolean modifyOverdraftLmtReg(OverdraftLmtReg overdraftLmtReg, String sessionUserCode)
			throws BizException {
		
		Assert.notNull(overdraftLmtReg, "更新的透支额度申请对象不能为空");
		overdraftLmtReg.setUpdateUser(sessionUserCode);
		overdraftLmtReg.setUpdateTime(new Date());
		int count = this.overdraftLmtRegDAO.update(overdraftLmtReg);
		return count>0;
	}
}
