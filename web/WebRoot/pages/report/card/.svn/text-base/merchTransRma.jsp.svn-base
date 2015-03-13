<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="gnete.card.entity.BranchInfo"%>
<%@page import="gnete.card.entity.UserInfo"%>
<%@page import="org.springframework.web.util.WebUtils"%>
<%@page import="gnete.card.dao.BranchInfoDAO"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="gnete.etc.Constants"%>
<%@page import="gnete.card.entity.type.RoleType"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="flink.util.DateUtil"%>
<%@page import="gnete.card.entity.type.RmaType"%>
<%@page import="java.util.ArrayList"%>

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
		<script>
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
    	String errMsg = "";
    	List<RmaType> rmaTypeList = RmaType.getAll();
    	request.setAttribute("rmaTypeList", rmaTypeList);
    	List<BranchInfo> branchList = new ArrayList<BranchInfo>();
    	
    	UserInfo userInfo = (UserInfo) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
    	BranchInfoDAO branchInfoDAO = (BranchInfoDAO) SpringContext.getService("branchInfoDAOImpl");
    	String roleType = userInfo.getRole().getRoleType();
    	
    	StringBuffer params = new StringBuffer(128);
    	
    	// 营运中心或者营运中心部门
    	if(RoleType.CENTER.getValue().equals(roleType)
    		|| RoleType.CENTER_DEPT.getValue().equals(roleType)){
    		showCard = true;
    	}
    	// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(roleType)) {
			showCard = true;
			params.append("parent=" + userInfo.getBranchNo() + ";");
		}
    	// 集团机构角色登录时，可查看自己集团下的发卡机构的报表
		else if (StringUtils.equals(roleType, RoleType.GROUP.getValue())) {
			showCard = true;
			branchList = branchInfoDAO.findByGroupId(userInfo.getBranchNo());
		}
		// 发卡机构或者发卡机构部门
		else if (roleType.equals(RoleType.CARD.getValue())
				|| roleType.equals(RoleType.CARD_DEPT.getValue())){
			showCard = false;
		}
		else {
			errMsg = "没有权限查看。";
		}
		
		request.setAttribute("branchList", branchList);
		request.setAttribute("showCard", showCard);
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
				<form id="searchForm" action="merchTransRma.jsp" method="post" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					    <caption>商户划付报表 | <span class="caption_title"><f:link href="/pages/report/card/rma/list.do">网银通划付文件下载</f:link></span></caption>
						<tr>
							<td align="right">报表类型</td>
							<td>
							<select id="id_reportType" name="reportType" class="{required:true}" onmouseover="FixWidth(this)">
								<option value="">--请选择--</option>
							<c:forEach items="${rmaTypeList}" var="u">
								<option value="${u.value}" <c:if test="${param.reportType eq u.value}">selected</c:if>><c:out value="${u.name }"/></option>	
							</c:forEach>
							</select>
							</td>
						</tr>
						<tr>
							<td align="right">划付日期</td>
							<td>
								<input id="id_rmaDate" type="text" name="rmaDate" onclick="WdatePicker({dateFmt:'yyyyMMdd'})" class="{required:true}" value="${param.rmaDate}" />
							</td>
							<c:if test="${showCard}">
								<td align="right">发卡机构</td>
								<td>
									<c:choose>
										<c:when test="${empty(branchList)}">
											<input type="hidden" id="idBranchCode" name="payCode" value="${param.payCode}"/>
											<input type="text" id="idBranchCode_sel" name="branchName" value="${param.branchName}" class="{required:true}"/>
										</c:when>
										<c:otherwise>
											<select id="id_branchList" name="payCode" class="{required:true}" onmouseover="FixWidth(this)">
												<option value="">请选择</option>
												<c:forEach items="${branchList}" var="b">
													<option value="${b.branchCode}" <c:if test="${param.payCode eq b.branchCode}">selected</c:if>><c:out value="${b.branchName }"/></option>	
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
							<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');$('#idBranchCode_sel').val('');$('#idBranchCode').val('');" name="escape" />
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
			String rmaDate = request.getParameter("rmaDate");
			String payCode = request.getParameter("payCode");
			String reportType = request.getParameter("reportType");
			String printDate = DateUtil.getCurrentDate();
			
			// 判断查询条件是否输入完整。如果不完整，不显示报表
			if(roleType.equals(RoleType.CARD.getValue())||roleType.equals(RoleType.CARD_DEPT.getValue())){
				if(StringUtils.isNotEmpty(rmaDate)){
					showReport = true;
				}			
			} else {
				if(StringUtils.isNotEmpty(rmaDate) && StringUtils.isNotEmpty(payCode)){
					showReport = true;
				}
			}
			
			params.append("rmaDate=" + rmaDate + ";");	
			
			// 发卡机构或机构部门
			if(roleType.equals(RoleType.CARD.getValue())
				|| roleType.equals(RoleType.CARD_DEPT.getValue())){
				payCode = userInfo.getBranchNo();
				
			}
			
			params.append("branchCode=" + payCode + ";");
			BranchInfo branchInfo = (BranchInfo) branchInfoDAO.findByPk(payCode);
			String branchName = "";
			if(branchInfo != null){
				branchName = branchInfo.getBranchName();
			}
			params.append("branchName=" + branchName + ";");
			
			// 发卡机构商户划付报表
			if(RmaType.MERCH_TRANS_RMA.getValue().equals(reportType)){
				params.append("printDate=" + printDate + ";");
			}
			// 积分卡业务商户交易资金清算报表 
			else if(RmaType.MERCH_TRANS_RMA_SET.getValue().equals(reportType)){
				String payAccNo = "";
				String payAccName = "";
				String payBankName = "";
				
				if(branchInfo!=null){
					payAccNo = branchInfo.getAccNo();
					payAccName = branchInfo.getAccName();
					payBankName = branchInfo.getBankName();
				}
				params.append("payAccNo=" + payAccNo + ";");
				params.append("payAccName=" + payAccName + ";");
				params.append("payBankName=" + payBankName + ";");
			}
			
			System.out.println("params = " + params.toString());
		%>
		<%if(showReport){ %>
			<% if(StringUtils.isNotBlank(reportType) && "merchTransRma".equals(reportType)){ %>
				<report:html name="report1" reportFileName="/branch/merchTransRmaCard.raq"	
				    params="<%=params.toString() %>"
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
			<% } if(StringUtils.isNotBlank(reportType) && "merchTransRmaSet".equals(reportType)){ %>
				<report:html name="report2" reportFileName="/branch/merchTransRmaDetailCard.raq"	
				    params="<%=params.toString() %>"
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