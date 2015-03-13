<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:hidden id="idBranchCode" name="balanceReportSet.cardBranch"/>
								<s:hidden name="balanceReportSet.dateType"/>
								<s:textfield id="idBranchCode_sel" name="balanceReportSet.branchName" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">请选择发卡机构</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">指定日期</td>
							<td>
								<s:select id="id_month" name="balanceReportSet.generateDate" headerKey="" headerValue="--请选择--" list="monthList" listKey="value" listValue="name" 
								 	cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请指定日期</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">是否生成余额明细报表</td>
							<td>
								<s:select id="id_needDetailFlag" name="balanceReportSet.needDetailFlag" headerKey="" headerValue="--请选择--" list="yesOrNoFlagList" listKey="value" listValue="name" 
								 	cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/report/balanceRepostSet/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_BALANCE_REPORT_SET_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>