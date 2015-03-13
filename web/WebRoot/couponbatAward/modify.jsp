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
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

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
					<s:hidden name="couponBatReg.updateBy"></s:hidden>
					<s:hidden name="couponBatReg.branchCode"></s:hidden>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">批量派赠赠券卡编号</td>
							<td>
							<s:textfield name="couponBatReg.couponBatRegId" readonly="true"/>
							</td>
							<td width="80" height="30" align="right">状态</td>
							<td>
							<s:textfield name="couponBatReg.statusName" readonly="true"/>
							<s:hidden name="couponBatReg.status" cssClass="{required:true}"></s:hidden>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">发行机构</td>
							<td>
								<s:select name="couponBatReg.cardIssuer" list="cardIssuerList" headerKey="" headerValue="--请选择--" 
								listKey="branchCode" listValue="branchName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择发行机构</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">起始卡号</td>
							<td>
							<s:textfield name="couponBatReg.startCard" cssClass="{required:true,minlength:19,digit:true}" maxlength="19"/>
								<span class="field_tipinfo">请输入19位起始卡号</span>
							</td>
							<td width="80" height="30" align="right">卡数量</td>
							<td>
							<s:textfield name="couponBatReg.cardNum" cssClass="{required:true,digit:true, min:1}" maxlength="8"/>
								<span class="field_tipinfo">请输入卡数量</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">初始面值</td>
							<td>
							<s:textfield name="couponBatReg.faceValue" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入初始面值</span>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
							<s:textfield name="couponBatReg.remark" />
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>	
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/couponbatAward/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_COUPONBATAWARD_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>