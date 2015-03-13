package gnete.card.web.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import flink.util.Cn2PinYinHelper;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.UserInfo;
import gnete.etc.Constants;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.WebUtils;

/**
 * 
 * @Project: Card
 * @File: AbstractCardReportLoadImpl.java
 * @See:
 * @description： 
 *    <li>处理当前模板报表生成的抽象类</li>
 *    <li>思路为提供给页面显示和控制的必要参数和根据页面提交的参数组合返回报表模板所需的参数</li>
 *    <li>子类需完成上面提及的部分</li>
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-1
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public abstract class AbstractCardReportLoadImpl implements ICardReportLoad {
	public void loadReportParams(HttpServletRequest request) {
	    UserInfo userInfo = (UserInfo) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
		
	    processUserLoad(request,userInfo);
	}

	public String[] getReportQueryParams(String roleType,String reportType, String[] params) throws Exception{
		if (StringUtils.isEmpty(reportType) || ArrayUtils.isEmpty(params)) {
			return new String[] {};
		}

		return getLoadQueryParams(roleType,reportType,params);
	}
	
	/**
	 * 按机构名称排序
	 * @param branchList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<BranchInfo> sortBranchList(List<BranchInfo> branchList) {
		List<BranchInfo> result = setBranchListValue(branchList);
		
		// 按名称排序
		Collections.sort(result, new Comparator() {
			public int compare(Object o1, Object o2) {
				BranchInfo branch1 = (BranchInfo) o1;
				BranchInfo branch2 = (BranchInfo) o2;
				
				return Cn2PinYinHelper.cn2Spell(branch1.getBranchName()).compareTo(Cn2PinYinHelper.cn2Spell(branch2.getBranchName()));
			}
		});
		return result;
	}
	
	private List<BranchInfo> setBranchListValue(List<BranchInfo> branchList) {
		List<BranchInfo> result = new ArrayList<BranchInfo>();
		for (BranchInfo branchInfo : branchList) {
			String pinyin = Cn2PinYinHelper.cn2FirstSpell(branchInfo.getBranchName());
			String prefix = StringUtils.substring(pinyin, 0, 1).toUpperCase();
			
			branchInfo.setBranchName(prefix + "-" + branchInfo.getBranchName());
			
			result.add(branchInfo);
		}
		
		return result;
	}
	
	/**
	 * 按商户名称名称排序
	 * @param branchList
	 * @return
	 */
	protected List<MerchInfo> sortMerchList(List<MerchInfo> merchList) {
		// 按名称排序
		Collections.sort(merchList, new Comparator() {
			public int compare(Object o1, Object o2) {
				MerchInfo merchInfo1 = (MerchInfo) o1;
				MerchInfo merchInfo2 = (MerchInfo) o2;
				
				return Cn2PinYinHelper.cn2Spell(merchInfo1.getMerchName()).compareTo(Cn2PinYinHelper.cn2Spell(merchInfo2.getMerchName()));
			}
		});
		return merchList;
	}

    protected abstract void processUserLoad(HttpServletRequest request, UserInfo userInfo);

	protected abstract String[] getLoadQueryParams(String roleType,String reportType,String[] params) throws Exception;
	
}
