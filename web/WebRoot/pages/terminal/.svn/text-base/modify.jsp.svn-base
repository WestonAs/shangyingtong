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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<f:css href="/css/page.css"/>
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<script type="text/javascript" src="terminalBankAcctInfo.js"></script>
		
		<script>
			$(function(){
				Selector.selectBranch('idManageBranch_sel', 'idManageBranch', true, '00,01,31');
				Selector.selectBranch('idMaintenance_sel', 'idMaintenance', true, '00,01,32');
				
				$("#input_btn2").click(function(){
					if($('#inputForm').validate().form()){
						$('#inputForm').submit();
						$('#input_btn2').attr('disabled', 'true');
						$("#loadingBarDiv").css("display","block");
						$("#contentDiv").css("display","none");
					}
				});
				
			});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<jsp:include flush="true" page="/common/loadingBarDiv.jsp"></jsp:include>
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">终端编号</td>
							<td>
								<s:textfield name="terminal.termId" cssClass="{required:true} readonly" readonly="true"/>
							</td>
							
							<td width="80" height="30" align="right">商户</td>
							<td>
								<s:hidden id="idMerchId" name="terminal.merchId" />
								<s:textfield id="idMerchId_sel" name="merchId_sel" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">请选择商户</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">出机方</td>
							<td>
								<s:hidden id="idManageBranch" name="terminal.manageBranch" />
								<s:textfield id="idManageBranch_sel" name="manageBranch_sel"/>
								<span class="field_tipinfo">请选择出机方</span>
							</td>
							
							<td width="80" height="30" align="right">维护方</td>
							<td>
								<s:hidden id="idMaintenance" name="terminal.maintenance" />
								<s:textfield id="idMaintenance_sel" name="maintenance_sel" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择维护方</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">租机费</td>
							<td>
								<s:textfield name="terminal.rentAmt" cssClass="{num:true}"/>
								<span class="field_tipinfo">请输入租机费</span>
							</td>
							
							<td width="80" height="30" align="right">消费点类型</td>
							<td>
								<s:select name="terminal.expenseType" list="expenseTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">门店编号</td>
							<td>
								<s:textfield name="terminal.shopNo"/>
								<span class="field_tipinfo">请输入编号</span>
							</td>
							
							<td width="80" height="30" align="right">外部索引</td>
							<td>
								<s:textfield name="terminal.pSeq" cssClass="{digit:true}"/>
								<span class="field_tipinfo">请输入数字</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">商户地址</td>
							<td>
								<s:textfield name="terminal.merchAddress" maxlength="80"/>
								<span class="field_tipinfo">请输入商户地址</span>
							</td>
							
							<td width="80" height="30" align="right">POS联系人</td>
							<td>
								<s:textfield name="terminal.posContact" maxlength="20"/>
								<span class="field_tipinfo">输入POS联系人</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">输入方式</td>
							<td>
								<s:select name="terminal.entryMode" list="entryModeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
									cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">是否只允许刷卡</span>
							</td>
						
							<td width="80" height="30" align="right">POS联系人电话</td>
							<td>
								<s:textfield name="terminal.posContactPhone" maxlength="16"/>
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>	
							<td width="80" height="30" align="right">是否单机产品终端</td>
							<td>
								<s:select id="id_singleProduct" name="terminal.singleProduct" list="singleProductList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
									cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">是否单机产品</span>
							</td>
							<!-- td>
								<s:hidden name="terminal.singleProduct" />
								<s:textfield name="terminal.singleProductName" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">是否单机产品</span>
							</td-->
												
							<td width="80" height="30" align="right">备注信息</td>
							<td>
								<s:textfield name="terminal.userMemo"/>
								<span class="field_tipinfo">请输入备注信息</span>
							</td>
						</tr>
	  					<tr>
							<td width="80"  height="30" align="right">是否添加银行账户信息</td>
							<td>
								<s:select id="needBankAcctInfo" name="formMap.needBankAcctInfo" list='#{"0":"否", "1":"是"}' 
									onchange="changeNeedBankAcctInfo()" />
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						<tr id="terminalAddiTr1">
							<td width="80" height="30" align="right">开户行名称</td>
							<td>
								<s:textfield id="bankName" name="terminalAddi.bankName" cssClass="{required:true}"/>
								<span class="field_tipinfo">点击选择</span>
							</td>
							<td width="80" height="30" align="right">开户行号</td>
							<td>
								<s:textfield id="bankNo" name="terminalAddi.bankNo" cssClass="{required:true, digit:true, minlength:12} readonly" 
									maxlength="12" readonly="true"/>
								<span class="field_tipinfo">请点击开户行名</span>
							</td>
						</tr>
						<tr id="terminalAddiTr2">
							<td width="80" height="30" align="right">账户户名</td>
							<td>
								<s:textfield id="accName" name="terminalAddi.accName" maxlength="30" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入户名</span>
							</td>
							<td width="80" height="30" align="right">账号</td>
							<td>
								<s:textfield name="terminalAddi.accNo" cssClass="{ required:true, digitOrAllowChars:'-'}" maxlength="32"/>
								<span class="field_tipinfo">请输入账号</span>
							</td>
						</tr>
						<tr id="terminalAddiTr3">
							<td width="80" height="30" align="right">账户地区</td>
							<td>
								<s:hidden id="accAreaCode" name="terminalAddi.accAreaCode" required="{required:true}"/>
								<s:textfield id="accAreaName" name="formMap.accAreaName" disabled="true" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right">账户类型</td>
							<td>
								<s:select id="acctType" name="terminalAddi.acctType" list="@gnete.card.entity.type.AcctType@getAll()" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" cssClass="{required:true}" />
								<span class="field_tipinfo">请选择账户类型</span>
							</td>
						</tr>
						<tr id="terminalAddiTr4">
							<td width="80" height="30" align="right">账户介质类型</td>
							<td>
								<s:select id="acctMediaType" name="terminalAddi.acctMediaType" list="@gnete.card.entity.type.AcctMediaType@getAll()" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" cssClass="{required:true}" />
								<span class="field_tipinfo">请选择</span>
							</td>
							<td width="80" height="30" align="right">终端名</td>
							<td>
								<s:textfield name="terminalAddi.termName" maxlength="25" cssClass="{required:true}"/>
								<span class="field_tipinfo">摊位号</span>
							</td>
						</tr>
						<tr>	
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/terminal/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_TERMINAL_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>