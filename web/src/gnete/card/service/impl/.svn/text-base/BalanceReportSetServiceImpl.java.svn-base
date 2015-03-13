package gnete.card.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flink.util.Paginater;
import gnete.card.dao.BalanceReportSetDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BalanceReportSet;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.DateType;
import gnete.card.service.BalanceReportSetService;
import gnete.etc.Assert;
import gnete.etc.BizException;

@Service("balanceReportSetService")
public class BalanceReportSetServiceImpl implements BalanceReportSetService {

	@Autowired
	private BalanceReportSetDAO balanceReportSetDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	public Paginater findPage(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.balanceReportSetDAO.findBalanceReportSetPage(params,
				pageNumber, pageSize);
	}

	public BalanceReportSet findDetail(String pk) {
		return (BalanceReportSet) this.balanceReportSetDAO.findByPk(pk);
	}

	public BalanceReportSet add(BalanceReportSet balanceReportSet,
			String[] branches, UserInfo user) throws BizException {
		Assert.notNull(balanceReportSet, "要添加的余额报表生成设置规则对象不能为空");
		Assert.notEmpty(balanceReportSet.getGenerateDate(), "指定的日期不能为空");
		Assert.notTrue(ArrayUtils.isEmpty(branches), "要添加的余额报表设置规则的发卡机构数组不能为空");
		
		balanceReportSet.setDateType(DateType.MONTH.getValue());
		balanceReportSet.setUpdateBy(user.getUserId());
		balanceReportSet.setUpdateTime(new Date());
		for (String branchCode : branches) {
			Assert.isNull(this.balanceReportSetDAO.findByPk(branchCode), 
					"发卡机构[" + branchCode + "]的余额报表生成规则已经设置，请勿重复设置。");
			BalanceReportSet set = new BalanceReportSet();
			try {
				BeanUtils.copyProperties(set, balanceReportSet);
			} catch (Exception e) {
				throw new BizException("复制对象时发生异常。" + e.getMessage());
			}
			set.setCardBranch(branchCode);
			
			balanceReportSetDAO.insert(set);
		}
		return null;
	}

	public BalanceReportSet showModify(String cardBranch) throws BizException {
		Assert.notEmpty(cardBranch, "发卡机构号不能为空");
		BranchInfo branchInfo = this.branchInfoDAO.findBranchInfo(cardBranch);
		Assert.notNull(branchInfo, "发卡机构[" + cardBranch + "]不存在");
		
		BalanceReportSet set = (BalanceReportSet) this.balanceReportSetDAO.findByPk(cardBranch);
		set.setBranchName(branchInfo.getBranchName());
		return set;
	}
	
	public boolean modify(BalanceReportSet balanceReportSet, UserInfo user)
			throws BizException {
		Assert.notNull(balanceReportSet, "要修改的余额报表生成规则对象不能为空");
		Assert.notEmpty(balanceReportSet.getCardBranch(), "发卡机构编号不能为空");
		BalanceReportSet oldBalanceReportSet = (BalanceReportSet) this.balanceReportSetDAO.findByPk(balanceReportSet.getCardBranch());
		Assert.notNull(oldBalanceReportSet, "要修改的发卡机构余额报表生成规则已经不存在");
		
		balanceReportSet.setUpdateBy(user.getUserId());
		balanceReportSet.setUpdateTime(new Date());
		return this.balanceReportSetDAO.update(balanceReportSet) > 0;
	}

	public boolean delete(String pk) throws BizException {
		Assert.notEmpty(pk, "要删除的余额报表生成规则的发卡机构不能为空。");
		return this.balanceReportSetDAO.delete(pk) > 0;
	}
}
