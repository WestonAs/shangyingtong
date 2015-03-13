package gnete.card.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gnete.card.dao.LargessDefDAO;
import gnete.card.dao.LargessRegDAO;
import gnete.card.entity.LargessDef;
import gnete.card.entity.LargessReg;
import gnete.card.service.LargessService;
import gnete.etc.Assert;
import gnete.etc.BizException;
@Service("LargessService")
public class LargessServiceImpl implements LargessService {

	@Autowired
	private LargessRegDAO largessRegDAO; 
	
	@Autowired
	private LargessDefDAO largessDefDAO;
	
	public boolean addLargessReg(LargessReg largessReg, String sessionUserCode) throws BizException {
	
		Assert.notNull(largessReg, "派赠登记对象不能为空");
		
		/*String largessId = largessReg.getLargessId();
		LargessReg old = (LargessReg)this.largessRegDAO.findByPk(largessId);
		Assert.isNull(old, "已经存在派赠编号[" + largessId +"]");*/
		
		largessReg.setUpdateBy(sessionUserCode);
		largessReg.setUpdateTime(new Date()); //更新时间
		
		return this.largessRegDAO.insert(largessReg) != null;
	}
	
	public boolean addLargessDef(LargessDef largessDef, String sessionUserCode) throws BizException {
		
		Assert.notNull(largessDef, "赠品定义对象不能为空");
		
		/*Long largessId = largessDef.getLargessId();
		LargessDef old = (LargessDef)this.largessDefDAO.findByPk(largessId);
		Assert.isNull(old, "已经存在赠品编号[" + largessId +"]");*/
		
		largessDef.setUpdateTime(new Date()); //更新时间
		largessDef.setUpdateBy(sessionUserCode); //更新用户名
		
		return this.largessDefDAO.insert(largessDef) != null;
	}

	public boolean delete(Long largessRegId) throws BizException {
		
		Assert.notNull(largessRegId, "删除的派赠登记对象不能为空");		

		int count = this.largessRegDAO.delete(largessRegId);
		
		return count > 0;
	}
	
	public boolean deleteLargessDef(Long largessId) throws BizException {
		
		Assert.notNull(largessId, "删除的赠品定义对象不能为空");		

		int count = this.largessDefDAO.delete(largessId);
		
		return count > 0;
	}

	public boolean modifyLargessReg(LargessReg largessReg, String sessionUserCode) throws BizException {
		
		Assert.notNull(largessReg, "更新的派赠登记对象不能为空");
		
		largessReg.setUpdateBy(sessionUserCode);
		largessReg.setUpdateTime(new Date());
		
		int count = this.largessRegDAO.update(largessReg);
		
		return count>0;
	}
	
	public boolean modifyLargessDef(LargessDef largessDef, String sessionUserCode) throws BizException {

		Assert.notNull(largessDef, "更新的赠品定义对象不能为空");
		
		largessDef.setUpdateBy(sessionUserCode);
		largessDef.setUpdateTime(new Date());
		
		int count = this.largessDefDAO.update(largessDef);
		
		return count>0;
	}

	public void setLargessRegDAO(LargessRegDAO largessRegDAO) {
		this.largessRegDAO = largessRegDAO;
	}

	public LargessRegDAO getLargessRegDAO() {
		return largessRegDAO;
	}

	public void setLargessDefDAO(LargessDefDAO largessDefDAO) {
		this.largessDefDAO = largessDefDAO;
	}

	public LargessDefDAO getLargessDefDAO() {
		return largessDefDAO;
	}

}
