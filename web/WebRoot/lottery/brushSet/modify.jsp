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
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
		<script>
		$(function(){
			$('#drawId_Id').change(function(){
				var drawId = $(this).val();
			$("#prizeNoId").load(CONTEXT_PATH + "/lottery/brushSet/prizeDefList.do",{'burshSet.drawId':drawId});
			});
		});
		</script>

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
							<td width="80" height="30" align="right">抽奖活动名</td>
							<td>
								<s:select id="drawId_Id" name="brushSet.drawId" list="drawDefList" headerKey="" headerValue="--请选择--" listKey="drawId" listValue="drawName" disabled="true"></s:select>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">中奖当天序号</td>
							<td>
								<s:textfield name="brushSet.awdSeq" cssClass="{required:true, Num:true}"/>
								<span class="field_tipinfo">请输入中奖当天序号</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">奖项名称</td>
							<td id="prizeNoId">
								<select name="brushSet.prizeNo" style="width: 165px" class="{required:true}">
								</select>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">中奖奖票号</td>
							<td>
								<s:textfield name="brushSet.contact"/>
								<span class="field_tipinfo">请输入中奖奖票号</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/lottery/brushSet/list.do')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_LOTTER_BRUSH_SET_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>