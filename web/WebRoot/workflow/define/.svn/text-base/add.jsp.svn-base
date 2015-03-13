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
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<script>
			$(function(){
				$('#idDefaultLevel').blur(loadDefaultName);
				$('#idType').css('width', '400px');
			});
			function loadDefaultName(){
				var defaultLevel = $('#idDefaultLevel').val();
				if(isEmpty(defaultLevel)){
					return false;
				}
				// 级别数大于等于1，小于等于10
				if(defaultLevel < 1 || defaultLevel > 10){
					showMsg("审批级数应大于等于1，小于等于10。");
					return false;
				}
				
				// 根据级别数显示相应数目的输入框
				$('#idWorkflowDefineTbl').show();
				$('#idTip').show();
				$('tr[id^="idDetail_"]').remove();
				for (var i = 0; i <= defaultLevel; i++){
					var content = '<tr id="idDetail_'+ (i) +'"><td align="center">'+ (i) +'</td>' 
								+ '<td align="center"><input type="text" name="names" /></td>' 
								+ '</tr>';
					$('#idWorkflowDefineTbl').append(content);
				}
	
				// 设置样式
				SysStyle.setFormGridStyle();
			}
			
			function checkField(){
				var count = $('tr[id^="idDetail_"]').size();
				for(i=0; i < count; i++){
					var $obj = $('tr[id^="idDetail_"]').eq(i);
					var $name = $obj.children().eq(1).children();
					
					if(isEmpty($name.val())){
						showMsg("节点名称不能为空。");
						return false;
					}
				}
				return true;
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">流程编号</td>
							<td>
								<s:textfield name="workflow.workflowId" cssClass="{required:true, digitOrLetter:true}"/>
								<span class="field_tipinfo">请输入流程编号</span>
							</td>
							
							<td width="80" height="30" align="right">流程名称</td>
							<td>
								<s:textfield name="workflow.workflowName" cssClass="{required:true}"/>
								<span class="field_tipinfo">输入流程名称</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">录入权限点</td>
							<td>
								<s:textfield name="workflow.inputLimit" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入权限点</span>
							</td>
							
							<td width="80" height="30" align="right">审批权限点</td>
							<td>
								<s:textfield name="workflow.auditLimit" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入权限点</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">流程流转类型</td>
							<td>
								<s:select id="idType" name="isReg" list="nodeFollowList" 
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">默认审批级数</td>
							<td>
								<s:textfield id="idDefaultLevel" name="workflow.defaultLevel" cssClass="{required:true, digit:true, max: 10}"/>
								<span class="field_tipinfo">输入不大于10的正整数</span>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="workflow.remark" maxlength="128"/>
								<span class="field_tipinfo">备注</span>
							</td>
						</tr>
					</table>
					
					<table class="form_grid" id="idWorkflowDefineTbl" 
						width="30%" border="0" cellspacing="3" cellpadding="0" style="display:none;">
						<tr>
						   <td align="center" nowrap class="titlebg">序号</td>
						   <td align="center" nowrap class="titlebg">节点名称</td>
						</tr>
					</table>
					<ul class="showli_div">
					    <li class="showli_div">
							<span class="redfont" id="idTip">
								注意：序号为0填写录入节点，比如"制卡录入"，下面依次填写每个审批节点名称，比如"制卡复核"、"制卡审核"。
							</span>
						</li>
						<li class="showli_div">
							<span class="redfont" >
								注意：审批权限点的值要与WORKFLOW表的AUDIT_LIMIT字段的一样. (WORKFLOW表的AUDIT_LIMIT字段的值是从页面权限表Privilege中的limit_id取得)
							</span>
						</li>
					</ul>
						
					<table class="form_grid" width="60%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" name="ok" onclick="return checkField();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/workflow/define/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					
					<s:token name="_TOKEN_WORKFLOW_DEFINE_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>