<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
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

		<script type="text/javascript">
			
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="showAdd.do" id="inputForm" cssClass="validate" method="GET">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name} --&gt; 请先选择发卡机构</caption>
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:select name="makeCardReg.branchCode" list="myCardBranch"
										 headerKey="" headerValue="--请选择--" 
										 listKey="branchCode" listValue="branchName" 
										 cssClass="{required:true}" onmouseover="FixWidth(this);"/>
								<span class="field_tipinfo" >选择发卡机构</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="下一步" id="input_btn2" name="ok"/>
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardStyleFix/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
				</s:form>
			</div>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>