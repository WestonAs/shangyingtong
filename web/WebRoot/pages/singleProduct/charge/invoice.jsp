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
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<script>
			function checkSubmitInvoice() {
				if (!FormUtils.hasSelected('ids')) {
					alert('请选择需要开发票的记录');
					return;
				}
				
				$('#ids').val(FormUtils.getCheckedValues('ids'));
				$('#inputForm').submit();
				
				$("#loadingBarDiv").css("display","inline");
				$("#contentDiv").css("display","none");
			}
		</script>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<div id="loadingBarDiv"	style="display: none; width: 100%; height: 100%">
			<table width=100% height=100%>
				<tr>
					<td align=center >
						<center>
							<img src="${CONTEXT_PATH}/images/ajax_saving.gif" align="middle">
							<div id="processInfoDiv"
								style="font-size: 15px; font-weight: bold">
								正在处理中，请稍候...
							</div>
							<br>
							<br>
							<br>
						</center>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="contentDiv">
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="findInvoice.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>单机产品开发票</caption>
						<tr>
							<td align="right">id</td>
							<td>
								<s:textfield name="costRecord.id" />
							</td>
							<td align="right">发卡机构</td>
							<td>
								<s:textfield name="costRecord.branchCode" />
							</td>
							<td align="right">类型</td>
							<td>
								<s:select name="costRecord.type" list="typeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">缴费时间</td>
							<td>
								<s:textfield id="chargeStartDate" name="chargeStartDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="chargeEndDate" name="chargeEndDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							
							<td align="right">产生时间</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td align="right">缴费方式</td>
							<td>
								<s:select name="costRecord.payType" list="payTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right"></td>
							
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok"/>
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_FINDINVOICE_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<s:form id="inputForm" action="invoice.do" cssClass="validate">
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg"><input type="checkbox" onclick="FormUtils.selectAll(this, 'ids')" />全选</td>
			   <td align="center" nowrap class="titlebg">ID</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">终端编号</td>
			   <td align="center" nowrap class="titlebg">类型</td>
			   <td align="center" nowrap class="titlebg">应收金额</td>
			   <td align="center" nowrap class="titlebg">已交金额</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">缴费方式</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td><input type="checkbox" name="ids" value="${id}"/></td>
			  <td align="center" nowrap>${id}</td>
			  <td nowrap>${branchCode}-${fn:branch(branchCode)}</td>
			  <td align="center" nowrap>${termId}</td>
			  <td align="center" nowrap>${typeName}</td>
			  <td align="right" nowrap>${fn:amount(amt)}</td>
			  <td align="right" nowrap>${fn:amount(paidAmt)}</td>
			  <td align="center" nowrap>${payTypeName}</td>
			  <td align="center" nowrap>${statusName}</td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		
		<div class="contentb" id="idInputPage">
			<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td width="80" height="30" align="right">&nbsp;</td>
					<td height="30">
						<input type="button" value="确认开发票" id="input_btn2"  name="ok" onclick="return checkSubmitInvoice();"/>
						<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/singleProduct/charge/list.do?goBack=goBack')" class="ml30" />
					</td>
				</tr>
			</table>
		</div>
		
		<s:token name="_TOKEN_SALE_CANCEL_ADD" />
		</s:form>
		
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
		
	</body>
</html>