<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="gnete.card.entity.BranchInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gnete.card.entity.type.DSetTransType"%>
<%@page import="gnete.card.entity.UserInfo"%>
<%@page import="org.springframework.web.util.WebUtils"%>
<%@page import="gnete.card.dao.BranchInfoDAO"%>
<%@page import="gnete.card.service.BaseDataService"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="gnete.etc.Constants"%>
<%@page import="gnete.card.entity.type.RoleType"%>
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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<script type="text/javascript">
			$(function(){
				if ('${SESSION_USER.role.roleType}'=='00'||'${SESSION_USER.role.roleType}'=='02' ){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20');
				} else if ('${SESSION_USER.role.roleType}'=='01' ){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20', '', '', '${SESSION_USER.branchNo}');
				}
			});
		</script>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
	
	<%
    	boolean showCard = false;
    	boolean showReport = false;
    	String reportType = "";
    	String showType = "";
    	String errMsg = "";
    	List<BranchInfo> branchList = new ArrayList<BranchInfo>();
    	//List<MerchInfo> merchList = new ArrayList<MerchInfo>();
    	//List<BranchInfo> cardBranchList = new ArrayList<BranchInfo>();
    	List<DSetTransType> transTypeList = DSetTransType.getList();
    	UserInfo userInfo = (UserInfo) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
    	BranchInfoDAO branchInfoDAO = (BranchInfoDAO) SpringContext.getService("branchInfoDAOImpl");
    	//MerchInfoDAO merchInfoDAO = (MerchInfoDAO) SpringContext.getService("merchInfoDAOImpl");
    	BaseDataService baseDataService = (BaseDataService) SpringContext.getService("baseDataService");
    	String roleType = userInfo.getRole().getRoleType();
    	
    	StringBuffer params1 = new StringBuffer(128);
    	StringBuffer params2 = new StringBuffer(128);
    	
    	// 营运中心或者营运中心部门
    	if(RoleType.CENTER.getValue().equals(roleType)
    		|| RoleType.CENTER_DEPT.getValue().equals(roleType)){
    		reportType = "total";
    		showType = "1";
    		showCard = true;
    		//branchList = branchInfoDAO.findByType(RoleType.CARD.getValue());
    		//params1.append("parent=" + userInfo.getBranchNo() + ";");
    		//merchList = baseDataService.getMyMerch(userInfo);
    	}
    	// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(roleType)) {
			reportType = "total";
			showType = "1";
			showCard = true;
			//branchList = branchInfoDAO.findCardByManange(userInfo.getBranchNo());
			params1.append("parent=" + userInfo.getBranchNo() + ";");
			//merchList = merchInfoDAO.findByManage(userInfo.getBranchNo());
		}
    	// 集团机构角色登录时，可查看自己集团下的发卡机构的报表
		else if (StringUtils.equals(roleType, RoleType.GROUP.getValue())) {
			reportType = "total";
			showType = "1";
			showCard = true;
			branchList = branchInfoDAO.findByGroupId(userInfo.getBranchNo());
		}
		// 发卡机构或者发卡机构部门
		else if (roleType.equals(RoleType.CARD.getValue())
				|| roleType.equals(RoleType.CARD_DEPT.getValue())){
			reportType = "detailCard";
			showType = "2";
			showCard = false;
			//branchList = baseDataService.getMyCardBranch(userInfo);
			//merchList = baseDataService.getMyMerch(userInfo);
			//BranchInfo card = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());
			//params2.append("branchName" + card.getBranchName() + ";");
		}
		else {
			errMsg = "没有权限查看。";
		}
		request.setAttribute("transTypeList", transTypeList);
		request.setAttribute("branchList", branchList);
	 	//request.setAttribute("merchList", merchList);
		request.setAttribute("showCard", showCard);
		request.setAttribute("showType", showType);
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
				<form id="searchForm" action="reportMerchFeeMTotal.jsp" method="post" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">交易月份</td>
							<td>
								<input id="id_feeMonth" type="text" name="feeMonth" onclick="WdatePicker({dateFmt:'yyyyMM'})" class="{required:true}" value="${param.feeMonth}" />
							</td>
							
							<c:if test="${showCard}">
								<td align="right" >发卡机构</td>
								<td>
									 <c:choose>
										  <c:when test='${SESSION_USER.role.roleType eq "00" || SESSION_USER.role.roleType eq "01" || SESSION_USER.role.roleType eq "02"}'>
												<input type="hidden" id="idBranchCode" name="branchCode" value="${param.branchCode}"/>
												<input type="text" id="idBranchCode_sel" name="branchName" value="${param.branchName}" class="{required:true}"/>
										  </c:when>
										  <c:otherwise>
												<select id="id_branchCode" name="branchCode" class="{required:true}" onmouseover="FixWidth(this)">
													<option value="">--请选择--</option>
													<c:forEach items="${branchList}" var="u">
														<option value="${u.branchCode}" <c:if test="${param.branchCode eq u.branchCode}">selected</c:if>><c:out value="${u.branchName }"/></option>	
													</c:forEach>
												</select>
										  </c:otherwise>
									  </c:choose>
								</td>
							</c:if>
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
			String transType = request.getParameter("transType");
			String printDate = DateUtil.getCurrentDate();
			String feeMonthThirteen = "";
			
			if(reportType.equals("total")){
				if(feeMonth!=null && 6 == feeMonth.length()){
					if((feeMonth.substring(4,6)).equals("12")){
						feeMonthThirteen = (new BigDecimal(feeMonth).add(new BigDecimal("1"))).toString();
					}
				}
				params1.append("feeMonth=" + feeMonth + ";");
				params1.append("feeMonthThirteen=" + feeMonthThirteen + ";");	
				params1.append("branchCode=" + branchCode + ";").append("merchId=" + merchId + ";");
				if(branchCode!=null){
					System.out.println("branchCode = " + branchCode);
					BranchInfo branchInfo = (BranchInfo) branchInfoDAO.findByPk(branchCode);
					if(branchInfo!=null){
						params1.append("branchName=" + branchInfo.getBranchName() + ";");
					}
				}
				
				params1.append("transType=" + transType + ";");
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
				params2.append("transType=" + transType + ";");
				params2.append("printDate=" + printDate + ";");
				System.out.println("params2" + params2.toString());
			}
			
			System.out.println("feeMonth=" + feeMonth);
			if(StringUtils.isNotEmpty(feeMonth) && StringUtils.isNotEmpty(branchCode)){
				showReport = true;
			}
		%>
		<%if(showReport){ System.out.println("showReport="+showReport); %>
			<% if(StringUtils.isNotBlank(reportType) && reportType == "total"){ %>
				<report:html name="report1" reportFileName="/merchFeeMTotalCard.raq"	
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
				<report:html name="report2" reportFileName="/merchFeeMTotalCard.raq"	
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
			<%} %>
	  	<%} %>
	  	</div>
	<%} %>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>