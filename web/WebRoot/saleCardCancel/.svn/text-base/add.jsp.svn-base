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
		
		<f:js src="/js/cert/AC_ActiveX.js"/>
		<f:js src="/js/cert/AC_RunActiveContent.js"/>
		
		<f:js src="/js/biz/saleCardCancel/add.js"/>

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
				<s:form id="searchForm" action="findSale.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">售卡批次</td>
							<td><s:textfield name="saleCardReg.saleBatchId" cssClass="{digit:true}"/></td>
							<td align="right">售卡日期</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td align="right">卡号</td>
							<td><s:textfield name="saleCardReg.cardId" cssClass="{digitOrLetter:true}"/></td>
						</tr>
						<tr>
							<td align="right">状态</td>
							<td>
								<s:select name="saleCardReg.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok"/>
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_FINDSALE_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<s:form id="inputForm" action="add.do" cssClass="validate">
			<s:hidden id="id_RandomSessionId" name="saleCardCancel.randomSessionId" />
			<s:hidden id="id_Signature" name="saleCardCancel.signature" />
			<s:hidden id="id_signatureReg" name="signatureReg" />
			<s:hidden id="id_serialNo" name="serialNo"/>
			
			<s:hidden id="id_saleBatchId" name="saleCardCancel.saleBatchId" />
			<s:hidden id="id_realAmt" name="saleCardCancel.realAmt" />
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">请选择</td>
			   <td align="center" nowrap class="titlebg">售卡批次</td>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">售卡日期</td>
			   <td align="center" nowrap class="titlebg">实收金额</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap><input type="radio" name="saleBatchId" value="${saleBatchId}" onclick="SaleCardCancel.setHiddenValue('${saleBatchId}', '${realAmt}')"/></td>
			  <td align="center" nowrap>${saleBatchId}</td>
			  <td align="center" nowrap>${cardId}</td>
			  <td align="center" nowrap>${saleDate}</td>
			  <td align="right" nowrap>${fn:amount(realAmt)}</td>
			  <td align="center" nowrap>${statusName}</td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		
		<div class="contentb clear" id="idInputPage">
			<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td width="80" height="30" align="right">&nbsp;</td>
					<td height="30">
						<input type="button" value="提交" id="input_btn2"  name="ok" onclick="return SaleCardCancel.checkForm();"/>
						<input type="button" value="返回" name="escape" onclick="gotoUrl('/saleCardCancel/list.do?goBack=goBack')" class="ml30" />
					</td>
				</tr>
			</table>
		</div>
		
		<s:token name="_TOKEN_SALE_CANCEL_ADD" />
		</s:form>
		
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
		
		<script type="text/javascript">
			AC_AX_RunContent( 'classid','clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80','id','FTUSBKEYCTRL','style','DISPLAY:none' ); //end AC code
		</script>
		<noscript><OBJECT classid="clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80" id="FTUSBKEYCTRL" style="DISPLAY:none"/></OBJECT></noscript>
	</body>
</html>