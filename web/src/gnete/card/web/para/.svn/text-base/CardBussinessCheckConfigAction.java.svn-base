package gnete.card.web.para;

import flink.util.LogUtils;
import gnete.card.entity.SaleDepositCheckConfig;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PublishNoticeService;
import gnete.card.tag.NameTag;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardBussinessCheckConfigAction.java
 *
 * @description:  发卡机构业务审核配置
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-3-8
 */
public class CardBussinessCheckConfigAction extends BaseAction {
	
	@Autowired
	private PublishNoticeService publishNoticeService;

	private List<YesOrNoFlag> flagList;
	private String cardBranchName;
	
	private SaleDepositCheckConfig checkConfig;
	
	// 默认方法显示列表页面
	public String execute() throws Exception {
		if (!isCardRoleLogined()) {
			throw new BizException("没有权限查看发卡机构业务审核配置信息");
		}
		this.checkConfig = this.publishNoticeService.findCheckConfig(this.getLoginBranchCode());
		return DETAIL;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!isCardRoleLogined()) {
			throw new BizException("没有权限修改发卡机构业务审核配置");
		}
		this.flagList = YesOrNoFlag.getAll();
		this.checkConfig = this.publishNoticeService.findCheckConfig(this.getLoginBranchCode());
		this.cardBranchName = NameTag.getBranchName(this.getLoginBranchCode());
		return MODIFY;
	}
	
	// 修改对象操作
	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!isCardRoleLogined()) {
			throw new BizException("没有权限修改发卡机构业务审核配置");
		}
		this.publishNoticeService.modifyCheckConfig(checkConfig, this.getSessionUser());
		
		String msg = LogUtils.r("修改发卡机构[{0}]审核权限配置成功", checkConfig.getCardBranch());
		this.addActionMessage("/pages/cardBizCheck/detail.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}

	public SaleDepositCheckConfig getCheckConfig() {
		return checkConfig;
	}

	public void setCheckConfig(SaleDepositCheckConfig checkConfig) {
		this.checkConfig = checkConfig;
	}

	public List<YesOrNoFlag> getFlagList() {
		return flagList;
	}

	public void setFlagList(List<YesOrNoFlag> flagList) {
		this.flagList = flagList;
	}

	public String getCardBranchName() {
		return cardBranchName;
	}

	public void setCardBranchName(String cardBranchName) {
		this.cardBranchName = cardBranchName;
	}
	
}
