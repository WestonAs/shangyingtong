package gnete.card.service;

import flink.util.SpringContext;
import gnete.card.dao.BranchParaDAO;
import gnete.card.entity.BranchPara;
import gnete.card.entity.BranchParaKey;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.RuntimeBizException;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 参数管理器
 * @author aps-lih
 * 
 */
public class BranchParaMgr {
	private static final Logger logger = Logger.getLogger(BranchParaMgr.class);
	
	private static BranchParaMgr instance = new BranchParaMgr();
	private Map paraMap = null;				// 机构运行参数映射
	
	private BranchParaDAO branchParaDAO;
	
	private BranchParaMgr() {
		initBranchPara();
	}

	public static BranchParaMgr getInstance() {
		return instance;
	}
	
	/**
	 * 初始化参数.
	 */
	private void initBranchPara() {
		logger.debug("初始化机构运行参数");
		
		paraMap = new HashMap();
		branchParaDAO = (BranchParaDAO) SpringContext.getService("branchParaDAOImpl");
		List branchParas = branchParaDAO.findAll();
		
		for (Iterator i = branchParas.iterator(); i.hasNext();) {
			BranchPara para = (BranchPara) i.next();
			paraMap.put(new BranchParaKey(para.getParaCode(), para.getRefCode(), para.getIsBranch()), 
					para.getParaValue());
		}
	}
	
	public String getPara(BranchParaKey key) {
		String result = (String)paraMap.get(key);
		if (result == null) {
			throw new RuntimeBizException("参数[" + key + "]不存在");
		}
		return result;
	}

	
	public int getIntPara(BranchParaKey key) {
		try {
			return Integer.valueOf(getPara(key)).intValue();
		}
		catch(NumberFormatException e) {
			throw new RuntimeBizException("参数[" + key + "]格式错误");
		}
	}
	
	public long getLongPara(BranchParaKey key) {
		try {
			return Long.valueOf(getPara(key)).longValue();
		}
		catch(NumberFormatException e) {
			throw new RuntimeBizException("参数[" + key + "]格式错误");
		}
	}
	
	public double getDoublePara(BranchParaKey key) {
		try {
			return Double.valueOf(getPara(key)).doubleValue();
		}
		catch(NumberFormatException e) {
			throw new RuntimeBizException("参数[" + key + "]格式错误");
		}
	}
	
	/**
	 * 修改机构运行参数
	 * 
	 * @param para 机构运行参数对象
	 * @return
	 * @throws BizException
	 */
	public boolean modifyPara(BranchPara para, String userId) throws BizException {
		BranchPara old = (BranchPara) this.branchParaDAO.findByPk(
				new BranchParaKey(para.getParaCode(), para.getRefCode(), para.getIsBranch()));
		Assert.notNull(old, "该机构运行参数不存在："+ para.getParaCode());
		
		int count = 0;
		try {
			old.setParaName(para.getParaName());
			old.setParaValue(para.getParaValue());
			old.setUpdateUser(userId);
			old.setUpdateTime(new Date());
			
			count = branchParaDAO.update(old);
		} catch (Exception e) {
			throw new BizException("机构运行参数格式不对，请检查！");
		}
		this.initBranchPara();
		return count > 0;
	}

}