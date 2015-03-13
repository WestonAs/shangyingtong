package gnete.card.web.para;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchParaDAO;
import gnete.card.entity.BranchPara;
import gnete.card.entity.BranchParaKey;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BranchParaMgr;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * BranchParaAction.java.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-7-7
 */
public class BranchParaAction extends BaseAction {
	
	@Autowired
	private BranchParaDAO branchParaDAO;

	private BranchPara branchPara;
	
	private Paginater page;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (branchPara != null) {
			params.put("paraCode", branchPara.getParaCode());
			params.put("paraName", MatchMode.ANYWHERE.toMatchString(branchPara.getParaName()));
		}
		this.page = this.branchParaDAO.find(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.branchPara = (BranchPara) this.branchParaDAO.findByPk(
				new BranchParaKey(branchPara.getParaCode(), branchPara.getRefCode(), branchPara.getIsBranch()));
		return DETAIL;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.branchPara = (BranchPara) this.branchParaDAO.findByPk(
				new BranchParaKey(branchPara.getParaCode(), branchPara.getRefCode(), branchPara.getIsBranch()));
		return MODIFY;
	}
	
	// 修改对象操作
	public String modify() throws Exception {
		BranchParaMgr.getInstance().modifyPara(branchPara, this.getSessionUserCode());
		
		String msg = LogUtils.r("修改机构运行参数[{0}]成功", branchPara);
		this.addActionMessage("/para/branchPara/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public BranchPara getBranchPara() {
		return branchPara;
	}

	public void setBranchPara(BranchPara branchPara) {
		this.branchPara = branchPara;
	}

}
