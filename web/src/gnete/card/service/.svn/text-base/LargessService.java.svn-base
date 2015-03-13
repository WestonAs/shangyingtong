package gnete.card.service;

import gnete.card.entity.LargessDef;
import gnete.card.entity.LargessReg;
import gnete.etc.BizException;

public interface LargessService {
	/**
	 * 派赠登记
	 * @param largessReg	派赠登记信息
	 * @return
	 * @throws BizException
	 */
	boolean addLargessReg(LargessReg largessReg, String sessionUserCode) throws BizException;
	
	/**
	 * 新增赠品定义
	 * @param largessDef	赠品定义信息
	 * @return
	 * @throws BizException
	 */
	boolean addLargessDef(LargessDef largessDef, String sessionUserCode) throws BizException;
	
	/**
	 * 修改派赠登记
	 * @param largessReg	派赠登记信息
	 * @return
	 * @throws BizException
	 */
	boolean modifyLargessReg(LargessReg largessReg, String sessionUserCode) throws BizException ;
	
	boolean modifyLargessDef(LargessDef largessDef, String sessionUserCode) throws BizException ;

	boolean delete(Long largessRegId) throws BizException;
	
	boolean deleteLargessDef(Long largessRegId) throws BizException;	
}
