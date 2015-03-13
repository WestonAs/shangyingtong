<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
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
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			$().ready(function(){
				var bankAccts = document.getElementsByName("businessSubAccountInfo.bindAccts");
				var bindAccts = '${bindAccts}';
				var bindAcctArray = bindAccts.split(",");
				for(var i=0; i<bankAccts.length; i++){
					 for(var j=0; j<bindAcctArray.length; j++){
						 if(bankAccts[i].value == bindAcctArray[j]){
							 bankAccts[i].checked = true;
							 $('#rd'+bankAccts[i].value).attr('disabled', false);
						 }
					 }
				}
				var radios = document.getElementsByName("businessSubAccountInfo.defaultAcct");
				var defaultAcct = '${defaultAcct}';
				for(var i=0; i<radios.length; i++){
					if(radios[i].value == defaultAcct){
						radios[i].checked = true;
					}
				}
			});
			function toggle(checkbox){
				if(checkbox.checked == true){
					$('#rd'+checkbox.value).attr('disabled', false);
				}else{
					$('#rd'+checkbox.value).attr('checked', false);
					$('#rd'+checkbox.value).attr('disabled', true);
				}
			}
			function save(form){
				if(!FormUtils.hasSelected('businessSubAccountInfo.bindAccts')){
					alert('请至少选中一个需要绑定的银行账户');
					return;
				}
				if(!FormUtils.hasRadio('businessSubAccountInfo.defaultAcct')){
					alert('请选择默认账户');
					return;
				}
				form.submit();
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<s:form action="bindBankAcct.do" cssClass="validate">
		<s:hidden name="businessSubAccountInfo.accountId"></s:hidden>
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
					<tr>
						<td align="center" nowrap class="titlebg">
							账号
						</td>
						<td align="center" nowrap class="titlebg">
							户名
						</td>
						<td align="center" nowrap class="titlebg">
							开户行
						</td>
						<td align="center" nowrap class="titlebg">
							开户行所在地
						</td>
						<td align="center" nowrap class="titlebg">
							默认账户
						</td>
					</tr>
				</thead>
			<s:iterator value="#request.bankAccts"> 
			<tr>
			  <td align="center" nowrap><input type="checkbox" name="businessSubAccountInfo.bindAccts" value="${acctNo}" onclick="toggle(this)"/>  ${acctNo}</td>
			  <td align="center" nowrap>${acctName}</td>
			  <td align="center" nowrap>${bankName}</td>
			  <td align="center" nowrap>${bankAdd}</td>
			  <td align="center" class="redlink">
			  <!-- 商户对自已的实体账户有修改权限 -->
			  <!-- 
			  		<f:link href="/businessSubAccount/businessSubAccountInfo/changeDefault.do?businessSubAccountInfo.accountId=${accountId}&businessSubAccountInfo.bankCardNo=${bankCardNo}">设为默认账户</f:link>
			 	 -->
			 	 <input type="radio" name="businessSubAccountInfo.defaultAcct" id="rd${acctNo}" disabled="disabled" value="${acctNo}"/>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		<div class="btnbox">
			<input type="button" value="保存" onclick="save(this.form)"/>&nbsp;
			<input type="button" value="返回" onclick="gotoUrl('/businessSubAccount/businessSubAccountInfo/list.do')"/>
		</div>
		<s:token name="_TOKEN_BIND_ACCT" />
		</s:form>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>