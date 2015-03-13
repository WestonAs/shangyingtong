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
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/validate.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">卡类型</td>
							<td>
								<s:select id="cardTypeId" name="cardInput.cardType" list="cardTypeList" headerKey="" headerValue="--请选择--" 
									listKey="cardTypeCode" listValue="cardTypeName" cssClass="{required:true}">
								</s:select>
								<span class="field_tipinfo">请选择卡类型</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">起始卡号</td>
							<td>
								<s:textfield id="strNoId" name="cardInput.strNo" cssClass="{required:true, minlength:19, digit:true}" maxlength="19" />
								<span id="strNoId_field" class="field_tipinfo">请输入19位数字的起始卡号</span>
							</td>
						</tr>

						<tr>
							<td width="80" height="30" align="right">入库卡数量</td>
							<td>
								<s:textfield id="inputNumId" name="cardInput.inputNum" cssClass="{required:true, digit:true, min:1}" maxlength="8"/>
								<span class="field_tipinfo">请输入入库卡数量</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">结束卡号</td>
							<td>
								<s:textfield id="endNoId" name="cardInput.endNo" readonly="true" />
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="cardInput.memo" />
							</td>
						</tr>
						
						<tr>
							<td height="30" colspan="2">
								<input type="submit" value="确定" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');clearError();" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardStock/finishedCard/list.do?goBack=goBack')" class="ml30" />
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