package gnete.card.web.stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.SampleCheckDAO;
import gnete.card.entity.SampleCheck;
import gnete.card.entity.state.CheckResult;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardStockService;
import gnete.card.web.BaseAction;

/**
 * @File: SampleCheckAction.java
 * 
 * @description: 制卡抽检处理相关
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-19
 */
public class SampleCheckAction extends BaseAction {

	@Autowired
	private SampleCheckDAO sampleCheckDAO;
	@Autowired
	private CardStockService cardStockService;

	private SampleCheck sampleCheck;

	private List resultList;

	private Paginater page;

	@Override
	public String execute() throws Exception {
		resultList = CheckResult.getAll();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", getSessionUser().getBranchNo());
		if (sampleCheck != null) {
			params.put("cardId", MatchMode.ANYWHERE.toMatchString(sampleCheck
					.getCardId()));
			params.put("checkResult", sampleCheck.getCheckResult());
			params.put("makeId", MatchMode.ANYWHERE.toMatchString(sampleCheck
					.getMakeId()));
		}
		page = sampleCheckDAO.findSampleCheckPage(params, getPageNumber(),
				getPageSize());
		return LIST;
	}

	public String detail() throws Exception {
		sampleCheck = (SampleCheck) sampleCheckDAO
				.findByPk(sampleCheck.getId());
		return DETAIL;
	}

	public String showAdd() throws Exception {
		return ADD;
	}

	public String add() throws Exception {
		cardStockService.addSampleCheck(sampleCheck, getSessionUser());
		String msg = LogUtils.r("新增ID为[{0}]的制卡抽检记录成功。", sampleCheck.getId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/cardStock/sampleCheck/list.do", msg);
		return SUCCESS;
	}

	public String delete() throws Exception {
		boolean flag = cardStockService.delteSampleCheck(sampleCheck, getSessionUser());
		if (flag) {
			String msg = LogUtils.r("删除ID为[{0}]的制卡抽检对象成功。", sampleCheck.getId());
			this.log(msg, UserLogType.DELETE);
			this.addActionMessage("/cardStock/sampleCheck/list.do", msg);
		}
		return SUCCESS;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public SampleCheck getSampleCheck() {
		return sampleCheck;
	}

	public void setSampleCheck(SampleCheck sampleCheck) {
		this.sampleCheck = sampleCheck;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
}
