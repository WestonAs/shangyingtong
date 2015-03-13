<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>
<%@ include file="/common/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp"%>
		<%@ include file="/common/sys.jsp"%>
		<title>${ACT.name}</title>

		<f:css href="/css/page.css" />
		<f:css href="/css/multiselctor.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/plugin/jquery.metadata.js" />
		<f:js src="/js/plugin/jquery.validate.js" />
		<f:js src="/js/plugin/jquery.multiselector.js" />
		<f:js src="/js/date/WdatePicker.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />

		<style type="text/css">
html {
	overflow-y: scroll;
}
</style>
	<script type="text/javascript">
		function selectBranch(){
			var parent = "<s:property value='#session.SESSION_USER.branchNo'/>";
			Selector.selectBranch('custName', 'custId', true, '20', '', '', parent);
		}
		function changeBranchType(){
			var branchType = $('#branchType').val();
			if(branchType == '20'){
				$('#tr1').show();
				$('#custId').val('');
				$('#custName').val('');
			} else if(branchType == '01'){
				$('#tr1').hide();
				$('#custId').val("<s:property value='#session.SESSION_USER.branchNo'/>");
				$('#custName').val('当前运营机构');
			}
		}
		function selectBank(){
			Selector.selectBank('bankName','bankNo',true);
		}
	</script>
	</head>

	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>

		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
						<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
							<caption>
								${ACT.name}
							</caption>
							<tr>
								<td class="formlabel">
									机构类型
								</td>
								<td>
									<s:select list="branchTypes" listKey="value" listValue="name" name="branchType" id="branchType" onchange="changeBranchType()"></s:select>
								</td>
							</tr>
							<tr id="tr1">
								<td class="formlabel">
									开设机构
								</td>
								<td>
									<s:textfield id="custName" name="accountSystemInfo.custName" cssClass="{required:true}" maxlength="19" onclick="selectBranch()"/>
									<s:hidden id="custId" name="accountSystemInfo.custId"></s:hidden>
									<span class="field_tipinfo">请选择开设机构</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									虚户体系名称
								</td>
								<td>
									<s:textfield id="systemName" name="accountSystemInfo.systemName" cssClass="{required:true}" maxlength="32"></s:textfield>
									<span class="field_tipinfo">请输入虚户体系名称</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									账号
								</td>
								<td>
									<s:textfield id="acctNo" name="accountSystemInfo.acctNo" cssClass="{required:true,digitOrAllowChars:'-',minlength:10,maxlength:32}" maxlength="32"></s:textfield>
									<span class="field_tipinfo">请输入正确格式的账号</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									户名
								</td>
								<td>
									<s:textfield id="acctName" name="accountSystemInfo.acctName" cssClass="{required:true}" maxlength="50"></s:textfield>
									<span class="field_tipinfo">请输入户名</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									开户行
								</td>
								<td>
									<s:textfield id="bankName" name="accountSystemInfo.bankName" cssClass="{required:true}" maxlength="50" onfocus="selectBank()"></s:textfield>
									<s:hidden name="accountSystemInfo.bankNo" id="bankNo"></s:hidden>
									<span class="field_tipinfo">请选择开户行</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									渠道商户号
								</td>
								<td>
									<s:textfield id="chnlMerNo" name="accountSystemInfo.chnlMerNo" cssClass="{required:true,minlength:15,maxlength:15,digits:true}" maxlength="15"></s:textfield>
									<span class="field_tipinfo">请输入15位渠道商户号</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									ftp地址
								</td>
								<td>
									<s:textfield id="ftpAdd" name="accountSystemInfo.ftpAdd" cssClass="{required:true}" maxlength="25"></s:textfield>
									<span class="field_tipinfo">请输入ftp地址</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									ftp端口
								</td>
								<td>
									<s:textfield id="ftpPort" name="accountSystemInfo.ftpPort" cssClass="{required:true}" maxlength="25"></s:textfield>
									<span class="field_tipinfo">请输入ftp端口号</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									ftp用户名
								</td>
								<td>
									<s:textfield id="ftpUser" name="accountSystemInfo.ftpUser" cssClass="{required:true}" maxlength="32"></s:textfield>
									<span class="field_tipinfo">请输ftp用户名</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									ftp密码
								</td>
								<td>
								    <s:textfield name="accountSystemInfo.ftpPwd" id="ftpPwd" cssClass="{required:true}"></s:textfield>
									<span class="field_tipinfo">请输ftp密码</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									ftp路径
								</td>
								<td>
									<s:textfield id="ftpPath" name="accountSystemInfo.ftpPath" cssClass="{required:true}" maxlength="25"></s:textfield>
									<span class="field_tipinfo">请输ftp路径</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									单笔手续费
								</td>
								<td>
									<s:textfield id="unitFee" name="accountSystemInfo.unitFee" cssClass="{required:true,number:true,min:0.01}" maxlength="25"></s:textfield>
									<span class="field_tipinfo">请输入单笔手续费</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									状态
								</td>
								<td>
									<s:select list="#request.states" listKey="value" listValue="name" name="accountSystemInfo.state" cssClass="{required:true}"></s:select>
									<span class="field_tipinfo">请选择体系状态</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									备注
								</td>
								<td>
									<s:textfield id="remark" name="accountSystemInfo.remark"  maxlength="50"></s:textfield>
									<span class="field_tipinfo"></span>
								</td>
							</tr>
							<tr>
								<td width="80" height="30" align="right">
									&nbsp;
								</td>
								<td height="30" colspan="3">
									<input type="submit" value="提交" id="input_btn2" name="ok" />
									<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
									<input type="button" value="返回" name="escape" onclick="gotoUrl('/businessSubAccount/accountSystemInfo/list.do')" class="ml30" />
								</td>
							</tr>
						</table>
						<s:token name="_TOKEN_ACCOUNT_SYSTEM_INFO_ADD" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>