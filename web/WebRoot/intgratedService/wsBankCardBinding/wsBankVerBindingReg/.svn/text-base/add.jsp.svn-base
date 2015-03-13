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
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<style type="text/css">
			#tranEnableDiv table table .headcell { text-align: right; width:80px; }
		</style>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/validate.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<script type="text/javascript" src="wsBankVerBindingReg.js"></script>
		
		<script>
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<jsp:include flush="true" page="/common/loadingBarDiv.jsp"></jsp:include>
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
			
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<s:hidden id='needSignatureReg' name="formMap.needSignatureReg" />
					<s:hidden id="random" name="formMap.random"/>
					<s:hidden id="serialNo" name="formMap.serialNo"/>
		        	<s:hidden id="mySign" name="formMap.mySign" />
					
					<div>
						<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
							<caption>${ACT.name}</caption>
							<tr id="id_branchCode_center_tr">
								<td width="80" height="30" align="right">卡号</td>
								<td><s:textfield id="cardId" name="wsBankVerBindingReg.cardId" cssClass="{required:true}"/></td>
								<td width="80" height="30" align="right">绑定方式</td>
								<td>
									<s:select id="setStyle" name="wsBankVerBindingReg.setStyle" list='@gnete.card.entity.state.WsBankVerBindingRegSetStyle@getAll()'
										headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 	cssClass="{required:true}"/>
								</td>
							</tr>
							<%-- 绑定、绑定并设置默认时使用 --%>
							<tbody id="bankCardBindingTbody"  class="no">
								<tr >
									<td width="80" height="30" align="right">开户行名称</td>
									<td>
										<s:textfield id="bankName" name="wsBankVerBindingReg.bankName" cssClass="{required:true}"/>
										<span class="field_tipinfo">点击选择</span>
									</td>
									<td width="80" height="30" align="right">开户行号</td>
									<td>
										<s:textfield id="bankNo" name="wsBankVerBindingReg.bankNo" cssClass="{required:true, digit:true, minlength:12} readonly" 
											maxlength="12" readonly="true"/>
										<span class="field_tipinfo">请点击开户行名</span>
									</td>
								</tr>
								<tr>
									<td width="80" height="30" align="right">账户地区</td>
									<td>
										<s:hidden id="accAreaCode" name="wsBankVerBindingReg.accAreaCode" />
										<s:textfield id="accAreaName" name="accAreaName" cssClass="{required:true} readonly" readonly="true"/>
										<span class="field_tipinfo">请选择开户行名</span>
									</td>
									<td width="80" height="30" align="right">账户类型</td>
									<td>
										<s:select id="acctType" name="wsBankVerBindingReg.bankaccttype" list="@gnete.card.entity.type.AcctType@getAll()" 
											headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}" />
										<span class="field_tipinfo">请选择账户类型</span>
									</td>
								</tr>
								<tr>
									<td width="80" height="30" align="right">银行账号</td>
									<td>
										<s:textfield name="wsBankVerBindingReg.bankAcct" cssClass="{required:true, digit:true}" maxlength="32"/>
										<span class="field_tipinfo">请输入银行卡号</span>
									</td>
									<td width="80" height="30" align="right">账户户名</td>
									<td>
										<s:textfield id="accName" name="wsBankVerBindingReg.bankAcctName" maxlength="30" cssClass="{required:true}"/>
										<span class="field_tipinfo">请输入户名</span>
									</td>
								</tr>
								<tr>
									<td width="80" height="30" align="right">开户人手机号</td>
									<td>
										<s:textfield name="wsBankVerBindingReg.mobile" cssClass="{required:true, digit:true}" maxlength="11"/>
										<span class="field_tipinfo"></span>
									</td>
									<td width="80" height="30" align="right">身份证号码</td>
									<td>
										<s:textfield id="idcard" name="wsBankVerBindingReg.idcard" cssClass="{required:true, digitOrLetter:true, minlength:16, maxlength:18}" maxlength="18"/>
										<span class="field_tipinfo">16位或18位</span>
									</td>
								</tr>
							</tbody>
							<%-- 设置默认、解绑时使用 --%>
							<tbody id="bankCardUnbindingTbody"  class="no">
								<tr>
									<td width="80" height="30" align="right">银行账号</td>
									<td>
										<s:textfield name="wsBankVerBindingReg.bankAcct" cssClass="{required:true, digit:true}" maxlength="32"/>
										<span class="field_tipinfo">请输入账号</span>
									</td>
								</tr>
							</tbody>
							<tr style="margin-top: 30px;">
								<td width="80" height="30" align="right">备注</td>
								<td colspan="11"><s:textfield name="wsBankVerBindingReg.remark" size="128"/></td>
							</tr>
							<tr>
								<td></td>
								<td height="30" colspan="11">
									<input type="submit" value="提交" id="input_btn2"  name="ok"  onclick="return validateForm();" />
									<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');" class="ml30" />
									<input type="button" value="返回" name="escape" onclick="gotoUrl('/intgratedService/wsBankCardBinding/wsBankVerBindingReg/list.do?goBack=goBack')" class="ml30" />
								</td>
							</tr>
						</table>
					</div>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<jsp:include flush="true" page="/common/certJsIncluded.jsp"></jsp:include>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>