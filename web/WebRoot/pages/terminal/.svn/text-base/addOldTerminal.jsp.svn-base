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
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			//Selector.selectBranch('idManageBranch_sel', 'idManageBranch', true, '00,01,31');
			//Selector.selectBranch('idMaintenance_sel', 'idMaintenance', true, '00,01,32');
			Selector.selectBranch('idMaintenance_sel', 'idMaintenance', true, '00,01,32');
			if('${loginRoleType}' == '00'){
				Selector.selectBranch('idManageBranch_sel', 'idManageBranch', true, '00,01,31');
				//Selector.selectBranch('idMaintenance_sel', 'idMaintenance', true, '00,01,32');
				Selector.selectMerch('idMerchId_sel', 'idMerchId', true);
			} else if('${loginRoleType}' == '01'){
				Selector.selectBranch('idManageBranch_sel', 'idManageBranch', true, '01,31', '', '', '${loginBranchCode}');
				//Selector.selectBranch('idMaintenance_sel', 'idMaintenance', true, '01,32', '', '', '${loginBranchCode}');
				Selector.selectMerch('idMerchId_sel', 'idMerchId', true, '', '', '', '${loginBranchCode}');
			}
			
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
				<s:form action="addOldTerminal.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>添加旧系统终端</caption>
						
						<tr>
							<td width="80" height="30" align="right">终端号</td>
							<td>
								<s:textfield name="terminal.termId" cssClass="{required:true, digitOrLetter:true, minlength:8}" maxlength="8"/>
								<span class="field_tipinfo">请输入终端号</span>
								<span class="error_tipinfo">8位数字字母</span>
							</td>
							<td width="80" height="30" align="right">商户编号</td>
							<td>
								<s:hidden id="idMerchId" name="terminal.merchId" />
								<s:textfield id="idMerchId_sel" name="merchId_sel" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择商户</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">出机方</td>
							<td>
								<s:hidden id="idManageBranch" name="terminal.manageBranch" />
								<s:textfield id="idManageBranch_sel" name="manageBranch_sel" />
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
								<span class="field_tipinfo">请选择消费点类型</span>
							</td>
						</tr>
					
						<tr>
							<td width="80" height="30" align="right">门店编号</td>
							<td>
								<s:textfield name="terminal.shopNo"/>
								<span class="field_tipinfo">请输入门店编号</span>
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
								<span class="field_tipinfo">请输入联系人</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">输入方式</td>
							<td>
								<s:select name="terminal.entryMode" list="entryModeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
									cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>

							<td width="80" height="30" align="right">POS联系人电话</td>
							<td>
								<s:textfield name="terminal.posContactPhone" maxlength="16"/>
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<!-- 
							<td width="80" height="30" align="right">是否单机产品终端</td>
							<td>
								<s:select name="terminal.singleProduct" list="singleProductList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
									cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
							 -->
													
							<td width="80" height="30" align="right">备注信息</td>
							<td>
								<s:textfield name="terminal.userMemo"/>
								<span class="field_tipinfo">请输入备注信息</span>
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
					<s:token name="_TOKEN_TERMINAL_ADDOLDTERMINAL"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>