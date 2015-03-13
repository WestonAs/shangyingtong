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
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">发卡机构编号</td>
							<td>
								<s:textfield name="checkConfig.cardBranch" readonly="true" cssClass="{required:true} readonly"/>
								<span class="field_tipinfo">请输入机构号</span>
							</td>
							
							<td width="80" height="30" align="right">发卡机构名称</td>
							<td>
								<s:textfield name="cardBranchName" cssClass="{required:true} readonly"/>
								<span class="field_tipinfo">请输入机构名称</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">售卡是否需要审核</td>
							<td>
								<s:select name="checkConfig.sellCheck" list="flagList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
							
							<td width="80" height="30" align="right">充值是否需要审核</td>
							<td>
								<s:select name="checkConfig.depositCheck" list="flagList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">挂失是否需要审核</td>
							<td>
								<s:select name="checkConfig.lossCardCheck" list="flagList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
							
							<td width="80" height="30" align="right">转账是否需要审核</td>
							<td>
								<s:select name="checkConfig.transAccCheck" list="flagList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">销户是否需要审核</td>
							<td>
								<s:select name="checkConfig.cancelCardCheck" list="flagList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
							
							<td width="80" height="30" align="right">延期是否需要审核</td>
							<td>
								<s:select name="checkConfig.carddeferCheck" list="flagList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>

						<tr>
							<td width="80" height="30" align="right">换卡是否需要审核</td>
							<td>
								<s:select name="checkConfig.renewCardCheck" list="flagList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
							
							<td width="80" height="30" align="right"></td>
							<td>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok"/>
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/cardBizCheck/detail.do')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CHECKCONFIG_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>