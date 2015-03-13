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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		</script>
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
							<td width="80" height="30" align="right">奖票号码</td>
							<td>
								<s:textfield name="awardReg.awdTicketNo" readonly="true"/>
							</td>
							<td width="80" height="30" align="right">卡号: </td>
							<td>
							<s:textfield name="awardReg.cardId" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">抽奖活动编号: </td>
							<td>
								<s:textfield name="awardReg.drawId" readonly="true"/>
							</td>
							<td width="80" height="30" align="right">奖项编号: </td>
							<td>
								<s:textfield name="awardReg.prizeNo" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">交易类型: </td>
							<td>
								<s:textfield name="awardReg.transTypeName" readonly="true"/>
								<s:hidden name="awardReg.transType" />
							</td>
							<td width="80" height="30" align="right">交易流水: </td>
							<td>
								<s:textfield name="awardReg.transSn" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">中奖操作员: </td>
							<td>
								<s:textfield name="awardReg.awdOptr" readonly="true"/>
							</td>
							<td width="80" height="30" align="right">中奖时间: </td>
							<td>
								<s:textfield name="awardReg.awdTime" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">中奖状态</td>
							<td>
								<s:select name="awardReg.awdStatus" list="awdStatusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请输入中奖状态</span>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/prizeAward/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_PRIZEAWARD_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>