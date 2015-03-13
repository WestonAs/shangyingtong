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
		<f:js src="/js/date/WdatePicker.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<!--  -->
		 <div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			  <div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate" method="post" enctype="multipart/form-data">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>制卡人选定</caption>
					    <tr>					       
							<td width="80" height="30" align="right">用户列表</td>
							<td>
								<s:select name="userId" id="userId" headerKey="" headerValue="--请选择--" list="userInfoList" listKey="userId" listValue="userName" cssClass="{required:true}">
								</s:select>
								<span class="field_tipinfo">请选择制卡人</span>
							</td>							
						</tr>						
						
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" name="ok" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/cardPerson/list.do?goBack=goBack');" class="ml30" />
							</td>
						</tr>
									
					</table>
					<s:token/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>