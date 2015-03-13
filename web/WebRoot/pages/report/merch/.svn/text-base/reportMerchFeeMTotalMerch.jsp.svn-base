<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="gnete.card.entity.BranchInfo"%>
<%@page import="gnete.card.entity.MerchInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gnete.card.entity.type.DSetTransType"%>
<%@page import="gnete.card.entity.UserInfo"%>
<%@page import="org.springframework.web.util.WebUtils"%>
<%@page import="gnete.card.dao.BranchInfoDAO"%>
<%@page import="gnete.card.service.BaseDataService"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="gnete.etc.Constants"%>
<%@page import="gnete.card.entity.type.RoleType"%>
<%@page import="gnete.card.dao.MerchInfoDAO"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="flink.util.DateUtil"%>

<%@ taglib uri="/WEB-INF/runqianReport4.tld" prefix="report"%>

<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:css href="/css/multiselctor.css"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			
			var reType = $('#id_reportType').val();
			if(!isEmpty(reType)){
				showBranch(reType);
			}
			
			var loginRoleType = $('#id_loginRoleType').val();
			var loginBranchCode = $('#id_loginBranchCode').val();
			
			// 营运机构
			if(loginRoleType == '00' ){
				Selector.selectMerch('idMerchId_sel', 'idMerchId', true);
			} 
			// 营运分支机构
			else if(loginRoleType == '01'){
				Selector.selectMerch('idMerchId_sel', 'idMerchId', true, '', '', '', loginBranchCode);
			}
			// 发卡机构 
			else if(loginRoleType == '20'){
				Selector.selectMerch('idMerchId_sel', 'idMerchId', true, loginBranchCode);
			}
		});
					
	    function showBranch(value){
	    	// 营运中心、分支机构、发卡机构
			if("total" == value || 'detailCard' == value){
			    //显示商户列表
			    $("#id_cardBranch_td1").hide();
				$("#id_cardBranch_td2").hide();					
			    $("#id_merch_td1").show();
			    $("#id_merch_td2").show();
				$('#idMerchId').removeAttr('disabled');
				$('#idMerchId_sel').removeAttr('disabled');
			}
			// 商户
			else if('detailMerch' == value){
				//显示发卡机构列表		
				$("#id_merch_td1").hide();
				$("#id_merch_td2").hide();
				$("#id_cardBranch_td1").show();
				$("#id_cardBranch_td2").show();					
				$('#idMerchId').attr('disabled', 'true');
				$('#idMerchId_sel').attr('disabled', 'true');
			}
		}
		</script>
	</head>
	
	<%
    	boolean showCard = false;
    	boolean showMerch = false;
    	boolean showReport = false;
    	String loginBranchCode = "";
    	String reportType = "";
    	String showType = "";
    	String errMsg = "";
    	List<BranchInfo> branchList = new ArrayList<BranchInfo>();
    	List<MerchInfo> merchList = new ArrayList<MerchInfo>();
    	List<DSetTransType> transTypeList = DSetTransType.getList();
    	UserInfo userInfo = (UserInfo) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
    	BranchInfoDAO branchInfoDAO = (BranchInfoDAO) SpringContext.getService("branchInfoDAOImpl");
    	MerchInfoDAO merchInfoDAO = (MerchInfoDAO) SpringContext.getService("merchInfoDAOImpl");
    	BaseDataService baseDataService = (BaseDataService) SpringContext.getService("baseDataService");
    	String roleType = userInfo.getRole().getRoleType();
    	loginBranchCode = userInfo.getBranchNo();
    	
    	StringBuffer params1 = new StringBuffer(128);
    	StringBuffer params2 = new StringBuffer(128);
    	StringBuffer params3 = new StringBuffer(128);
    	
    	// 营运中心或者营运中心部门
    	if(RoleType.CENTER.getValue().equals(roleType)
    		|| RoleType.CENTER_DEPT.getValue().equals(roleType)){
    		reportType = "total";
    		showType = "1";
    		showCard = true;
    		showMerch = true;
    		branchList = branchInfoDAO.findByType(RoleType.CARD.getValue());
    		merchList = baseDataService.getMyMerch(userInfo);
    	}
    	// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(roleType)) {
			reportType = "total";
			showType = "1";
			showCard = true;
			showMerch = true;
			branchList = branchInfoDAO.findCardByManange(userInfo.getBranchNo());
			merchList = merchInfoDAO.findByManage(userInfo.getBranchNo());
		}
		// 发卡机构或者发卡机构部门
		else if (roleType.equals(RoleType.CARD.getValue())
				|| roleType.equals(RoleType.CARD_DEPT.getValue())){
			reportType = "detailCard";
			showType = "2";
			showCard = false;
			showMerch = false;
			branchList = baseDataService.getMyCardBranch(userInfo);
			merchList = baseDataService.getMyMerch(userInfo);
			params2.append("branchCode" + userInfo.getBranchNo() + ";");
		}
		// 商户
		else if (roleType.equals(RoleType.MERCHANT.getValue())){
			reportType = "detailMerch";
			showType = "3";
			showMerch = false;
			showCard = true;
			branchList = baseDataService.getMyCardBranch(userInfo);
		 	merchList = baseDataService.getMyMerch(userInfo);
		} 
		else {
			errMsg = "没有权限查看。";
		}
		request.setAttribute("transTypeList", transTypeList);
		request.setAttribute("branchList", branchList);
	 	request.setAttribute("merchList", merchList);
		request.setAttribute("showCard", showCard);
		request.setAttribute("showMerch", showMerch);
		request.setAttribute("showType", showType);
		request.setAttribute("reportType", reportType);
		request.setAttribute("loginBranchCode", loginBranchCode);
    %>
    
	<body>
		<div class="location">您当前所在位置： <span class="redlink"><f:link href="/home.jsp">首页</f:link></span>
			<c:choose>
				<c:when test="${PRIVILEGE_PATH !=null}">
					<c:forEach items="${PRIVILEGE_PATH}" var="menu">
					&gt; ${menu.name}
					</c:forEach>
				</c:when>
			</c:choose>
		</div>
		<%if(StringUtils.isNotBlank(errMsg)) {%>
		<div class="msg">
			<span id="_msg_content" style="float: left"><%=errMsg %></span>
			<a id="_msg_close" href="javascript:hideMsg();" style="float: right;color: red">关闭 X</a>
		</div>
		<%} else { %>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<form id="searchForm" action="reportMerchFeeMTotalMerch.jsp" method="post" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td style="display: none;">
							<input id="id_reportType" type="text" name="reportType" value="<%=reportType%>" style="display: none;"/>
							<td style="display: none;">
							<input id="id_loginRoleType" type="hidden" name="roleType" value="<%=roleType%>" />
							</td>
							<td style="display: none;">
							<input id="id_loginBranchCode" type="hidden" name="loginBranchCode" value="<%=loginBranchCode%>"/>
							</td>
							<td align="right">交易月份</td>
							<td>
								<input id="id_feeMonth" type="text" name="feeMonth" onclick="WdatePicker({dateFmt:'yyyyMM'})" class="{required:true}" value="${param.feeMonth}" />
							</td>
							
							<td id="id_cardBranch_td1" align="right" style="display: none;">发卡机构</td>
							<td id="id_cardBranch_td2" style="display: none;">
								<select id="id_branchList" name="branchCode" >
									<option value="">请选择</option>
								<c:forEach items="${branchList}" var="u">
									<option value="${u.branchCode}" <c:if test="${param.branchCode eq u.branchCode}">selected</c:if>><c:out value="${u.branchName }"/></option>	
								</c:forEach>
								</select>
							</td>
							<td id="id_merch_td1" align="right" style="display: none;">商户</td>
							<td id="id_merch_td2" style="display: none;">
								<input id="idMerchId" type="hidden" name="merchId"  value="${param.merchId}" 
									class="{required:true}" disabled="disabled"/>
								<input id="idMerchId_sel" type="text" name="merchId_sel" class="{required:true}" 
									value="${param.merchId_sel}" class="{required:true}" disabled="disabled"/>
							</td>
							</tr>
							<tr>
							<td></td>
							<td height="30" colspan="3">
							<input type="submit" value="生成报表" id="input_btn2"  name="ok" />
							<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="clear">
		<%
			String branchCode = request.getParameter("branchCode");
			String merchId = request.getParameter("merchId");
			String feeMonth = request.getParameter("feeMonth");
			String printDate = DateUtil.getCurrentDate();
			String feeMonthThirteen = null;
			
			if(roleType.equals(RoleType.MERCHANT.getValue())){
				if(StringUtils.isNotEmpty(feeMonth)){
					showReport = true;
				}			
			} else {
				if(StringUtils.isNotEmpty(feeMonth) && StringUtils.isNotEmpty(merchId)){
					showReport = true;
				}
			}
			
			if(reportType.equals("total")){
				if(feeMonth!=null && 6 == feeMonth.length()){
					if((feeMonth.substring(4,6)).equals("12")){
						feeMonthThirteen = (new BigDecimal(feeMonth).add(new BigDecimal("1"))).toString();
					}
				}
				params1.append("feeMonth=" + feeMonth + ";");
				params1.append("feeMonthThirteen=" + feeMonthThirteen + ";");
				params1.append("branchCode=" + branchCode + ";").append("merchId=" + merchId + ";");
				MerchInfo merchInfo = (MerchInfo)merchInfoDAO.findByPk(merchId);
				String merchName = "";
				if(merchInfo!=null){
					merchName = merchInfo.getMerchName();
				}
				params1.append("merchName=" + merchName + ";");
				params1.append("printDate=" + printDate + ";");
				System.out.println("params1:" + params1.toString());
			}
			if(reportType.equals("detailCard")){
				if(feeMonth!=null && 6 == feeMonth.length()){
					if((feeMonth.substring(4,6)).equals("12")){
						feeMonthThirteen = (new BigDecimal(feeMonth).add(new BigDecimal("1"))).toString();
					}
				}
				branchCode = userInfo.getBranchNo();
				params2.append("feeMonth=" + feeMonth + ";").append("branchCode=" + branchCode + ";");
				params2.append("feeMonthThirteen=" + feeMonthThirteen + ";");
				BranchInfo branchInfo = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());
				String branchName = branchInfo.getBranchName();
				params2.append("branchName=" + branchName + ";");
				params2.append("merchId=" + merchId + ";");
				MerchInfo merchInfo = (MerchInfo)merchInfoDAO.findByPk(merchId);
				String merchName = "";
				if(merchInfo!=null){
					merchName = merchInfo.getMerchName();
				}
				params2.append("merchName=" + merchName + ";");
				params2.append("printDate=" + printDate + ";");
				System.out.println("params2" + params2.toString());
			}
			if(reportType.equals("detailMerch")){
				if(feeMonth!=null && 6 == feeMonth.length()){
					if((feeMonth.substring(4,6)).equals("12")){
						feeMonthThirteen = (new BigDecimal(feeMonth).add(new BigDecimal("1"))).toString();
					}
				}
				merchId = userInfo.getMerchantNo();
				params3.append("merchId=" + merchId + ";");
				params3.append("feeMonthThirteen=" + feeMonthThirteen + ";");
				params3.append("feeMonth=" + feeMonth + ";").append("branchCode=" + branchCode + ";");
				MerchInfo merchInfo = (MerchInfo)merchInfoDAO.findByPk(userInfo.getMerchantNo());
				String merchName = merchInfo.getMerchName();
				params3.append("merchName=" + merchName + ";");
				params3.append("printDate=" + printDate + ";");
				System.out.println("params3" + params3.toString());
			}
		%>
		<%if(showReport){ %>
			<% if(StringUtils.isNotBlank(reportType) && reportType == "total"){ %>
				<report:html name="report1" reportFileName="/merchFeeMTotalMerch.raq"	
				    params="<%=params1.toString() %>"
				    funcBarFontFace="宋体"               
					funcBarFontSize="14px"   	
					needSaveAsExcel="yes"
					needSaveAsPdf="yes"
					needSaveAsWord="yes"
					needPrint="yes"
					funcBarLocation="bottom"
					width="-1"
					useCache="false"
				/>
			<% } if(StringUtils.isNotBlank(reportType) && reportType == "detailCard"){ %>
				<report:html name="report2" reportFileName="/merchFeeMTotalMerch.raq"	
				    params="<%=params2.toString() %>"
				    funcBarFontFace="宋体"               
					funcBarFontSize="14px"   	
					needSaveAsExcel="yes"
					needSaveAsPdf="yes"
					needSaveAsWord="yes"
					needPrint="yes"
					funcBarLocation="bottom"
					width="-1"
					useCache="false"
				/>
			<% } if(StringUtils.isNotBlank(reportType) && reportType == "detailMerch"){ %>
				<report:html name="report3" reportFileName="/merchFeeMTotalMerch.raq"	
				    params="<%=params3.toString() %>"
				    funcBarFontFace="宋体"               
					funcBarFontSize="14px"   	
					needSaveAsExcel="yes"
					needSaveAsPdf="yes"
					needSaveAsWord="yes"
					needPrint="yes"
					funcBarLocation="bottom"
					width="-1"
					useCache="false"
				/>
			<%} %>
	  	<%} %>
		</div>
	<%} %>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>