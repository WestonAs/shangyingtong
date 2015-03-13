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
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			Selector.selectBranch('idRefId_sel', 'idRefId', true, '20,21,22');
			$('#idIsBranch').change(function(){
				var value = $(this).val();
				$('#idRefId_sel').removeMulitselector();
				$('#idRefId').val('');
				$('#idRefId_sel').val('');
				if (value == 'Y') {
					Selector.selectBranch('idRefId_sel', 'idRefId', true, '20,21,22');
				} else if (value == 'N'){
					Selector.selectMerch('idRefId_sel', 'idRefId', true);
				}
			});
			
			$('#idNeedAudit').change(function(){
				var value = $(this).val();
				if (value == 'Y') {
					$('#idAuditLevel_tr').show();
					$('#idAuditLevel').removeAttr('disabeld');
				} else if (value == 'N'){
					$('#idAuditLevel_tr').hide();
					$('#idAuditLevel').attr('disabeld', 'true');
				}
			});
		});
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
							<td width="100" height="30" align="right">您要配置的流程是</td>
							<td>
								<s:select id="idIsBranch" list="#{'Y':'机构流程','N':'商户流程'}" name="workflowConfig.isBranch" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择类型</span>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right" id="idBranchTd_1">机构(商户)编号</td>
							<td>
								<s:hidden id="idRefId" name="workflowConfig.refId" />
								<s:textfield id="idRefId_sel" name="refId_sel" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择机构(商户)</span>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">可配置的流程</td>
							<td>
								<s:select name="workflowConfig.workflowId" list="workflowList" headerKey="" headerValue="--请选择--" listKey="workflowId" listValue="workflowName" cssClass="{required:true}"></s:select>
								
								<span class="field_tipinfo">请选择流程</span>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">是否需要审核</td>
							<td>
								<s:select id="idNeedAudit" list="#{'Y':'是','N':'否'}" name="workflowConfig.needAudit" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择是否需要审核</span>
							</td>
						</tr>
						<s:if test='"Y".equals(workflowConfig.needAudit)'>
						<tr id="idAuditLevel_tr">
							<td width="100" height="30" align="right">自定义审批级数</td>
							<td>
								<s:textfield id="idAuditLevel" name="workflowConfig.auditLevel" cssClass="{required:true, digit:true}"/>
								<span class="field_tipinfo">请输入自定义审批级数</span>
							</td>
						</tr>
						</s:if>
						<s:else>
						<tr id="idAuditLevel_tr" style="display:none;">
							<td width="100" height="30" align="right">自定义审批级数</td>
							<td>
								<s:textfield id="idAuditLevel" name="workflowConfig.auditLevel" cssClass="{required:true, digit:true}" disabled="true"/>
								<span class="field_tipinfo">请输入自定义审批级数</span>
							</td>
						</tr>
						</s:else>
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/workflow/config/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_WORKFLOW_CONFIG_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>