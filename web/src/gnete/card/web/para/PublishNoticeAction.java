package gnete.card.web.para;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.PublishNoticeDAO;
import gnete.card.entity.PublishNotice;
import gnete.card.entity.flag.ShowFlag;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PublishNoticeService;
import gnete.card.web.BaseAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: PublishNoticeAction.java
 *
 * @description: 管理员发布系统通知
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2010-11-16
 */
public class PublishNoticeAction extends BaseAction {

	@Autowired
	private PublishNoticeDAO publishNoticeDAO;
	@Autowired
	private PublishNoticeService publishNoticeService;

	private PublishNotice publishNotice;

	private List showFlagList;

	private Paginater page;
	private String id;

	@Override
	public String execute() throws Exception {
		showFlagList = ShowFlag.getAll();

		Map<String, Object> params = new HashMap<String, Object>();
		if (publishNotice != null) {
			params.put("id", MatchMode.ANYWHERE.toMatchString(publishNotice
					.getId()));
			params.put("title", MatchMode.ANYWHERE.toMatchString(publishNotice
					.getTitle()));
			params.put("pubUser", MatchMode.ANYWHERE
					.toMatchString(publishNotice.getPubUser()));
			params.put("showFlag", publishNotice.getShowFlag());
		}
		page = publishNoticeDAO.findPublishNotice(params, getPageNumber(),
				getPageSize());
		return LIST;
	}

	public String detail() throws Exception {
		publishNotice = (PublishNotice) publishNoticeDAO.findByPk(publishNotice.getId());
		return DETAIL;
	}

	public String showAdd() throws Exception {
		return ADD;
	}

	public String add() throws Exception {

		publishNoticeService.addPublishNotice(publishNotice, getSessionUserCode());
		String msg = LogUtils.r("管理员[{0}]发布通知[{1}]成功", publishNotice
				.getPubUser(), publishNotice.getId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/publishNotice/list.do", msg);
		return SUCCESS;
	}

	public String delete() throws Exception {
		publishNoticeService.deletePublishNotice(id);
		String msg = LogUtils.r("删除通知[{0}]成功", id);
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/publishNotice/list.do", msg);
		return SUCCESS;
	}
	
	public String hide() throws Exception {
		publishNoticeService.hidePublishNotice(id);
		String msg = LogUtils.r("隐藏通知[{0}]成功", id);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/publishNotice/list.do", msg);
		return SUCCESS;
	}
	
	public String show() throws Exception {
		publishNoticeService.showPublishNotice(id);
		String msg = LogUtils.r("显示通知[{0}]成功", id);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/publishNotice/list.do", msg);
		return SUCCESS;
	}


	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public PublishNotice getPublishNotice() {
		return publishNotice;
	}

	public void setPublishNotice(PublishNotice publishNotice) {
		this.publishNotice = publishNotice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List getShowFlagList() {
		return showFlagList;
	}

	public void setShowFlagList(List showFlagList) {
		this.showFlagList = showFlagList;
	}

}
