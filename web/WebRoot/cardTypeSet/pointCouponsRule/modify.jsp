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
		<f:js src="/js/validate.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
				<script>
		$(function(){
		});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>

		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<s:hidden id="id_cardIssuerNo" name="cardIssuerNo" />
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td width="80" height="30" align="right">限制类型</td>
							<td >
							    <s:hidden name="merchGroupPointCouponLimit.limitType" />
								<s:textfield name="merchGroupPointCouponLimit.limitTypeName" cssClass="{required:true} readonly" readonly="true"/>
<%--								<s:select id="id_limitType" name="merchGroupPointCouponLimit.limitType" list="limitTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true} readonly" readonly="true"></s:select>--%>
								<span class="field_tipinfo">请选择限制类型</span>
							</td>
							
						    <td id="id_limitIdTd2"  width="80" height="30" align="right" >类型ID</td>
							<td id="id_pointTd" name="pointTd" >
							    <s:hidden name="merchGroupPointCouponLimit.limitId" />
								<s:textfield name="merchGroupPointCouponLimit.ptName" cssClass="{required:true} readonly" readonly="true"/>
<%--								<s:select id="id_pointClass" disabled="true" name="merchGroupPointCouponLimit.limitId" list="pointClassDefList"  headerKey="" headerValue="--请选择--" listKey="ptClass" listValue="className" cssClass="{required:true} readonly" readonly="true"></s:select>--%>
								<span class="field_tipinfo">请选择积分类型ID</span>
							</td>
						</tr>
						<tr>
						    <td  id="merchGroup_1"  width="80" height="30" align="right" >商圈</td>
							<td  id="merchGroup_2"  >
							    <s:hidden name="merchGroupPointCouponLimit.groupId" />
								<s:textfield name="merchGroupPointCouponLimit.groupName" cssClass="{required:true} readonly" readonly="true"/>
<%--								<s:select id="Id_merchGroupId"  name="merchGroupPointCouponLimit.groupId" list="merchGroupList" headerKey="" headerValue="--请选择--" listKey="groupId" listValue="groupName" cssClass="{required:true} readonly" readonly="true"></s:select>--%>
								<span class="field_tipinfo">请选择商圈</span>
							</td>
							<td width="80" height="30" align="right">商户</td>
							<td> 
								<s:hidden id="id_merchNo" name="merchGroupPointCouponLimit.merchId" />
								<s:textfield id="id_merchName" name="merchGroupPointCouponLimit.merchName" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">请选择商户</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">赠送标志</td>
							<td >
								<s:select id="Id_sendFlag" name="merchGroupPointCouponLimit.sendFlag" list="sendFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择赠送标志</span>
							</td>
							<td width="80" height="30" align="right">消费标志</td>
							<td >
								<s:select id="Id_consumeFlag" name="merchGroupPointCouponLimit.consumeFlag" list="consumeFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择消费标志</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">状态</td>
							<td >
								<s:hidden name="merchGroupPointCouponLimit.status" ></s:hidden>
								<s:textfield name="merchGroupPointCouponLimit.statusName"  cssClass="readonly" readonly="true"></s:textfield>
								<span class="field_tipinfo">请输入状态</span>
							</td>
							<td width="80" height="30" align="right">插入时间</td>
							<td >
								<s:textfield name="merchGroupPointCouponLimit.insertTime"  cssClass="readonly" readonly="true">
								<s:param name="value"><s:date name="merchGroupPointCouponLimit.insertTime" format="yyyy-MM-dd HH:mm:ss"/></s:param></s:textfield>
								<span class="field_tipinfo">请输入插入时间</span>
							</td>

						</tr>
						<tr>
							<td width="80" height="30" align="right">更新时间</td>
							<td >
								<s:textfield name="merchGroupPointCouponLimit.updateTime"  cssClass="readonly" readonly="true">
								<s:param name="value"><s:date name="merchGroupPointCouponLimit.updateTime" format="yyyy-MM-dd HH:mm:ss"/></s:param></s:textfield>
								<span class="field_tipinfo">请输入更新时间</span>
							</td>
							<td width="80" height="30" align="right">更新用户名</td>
							<td >
								<s:textfield name="merchGroupPointCouponLimit.updateBy" cssClass="readonly" readonly="true"></s:textfield>
								<span class="field_tipinfo">请输入更新用户名</span>
							</td>

						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td >
								<s:textfield name="merchGroupPointCouponLimit.remark" ></s:textfield>
								<span class="field_tipinfo">请输入备注</span>
							</td>
							<td></td>
							<td></td>

						</tr>

						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="申请" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardTypeSet/pointCouponsRule/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MERCHGROUPPOINTCOUPONLIMIT_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>