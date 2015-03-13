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
		<f:js src="/js/datePicker/WdatePicker.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			function searchDeposit() {
				$('#searchForm').attr('action', 'list.do').submit();
			}		
			function cancelDeposit() {
				if (!FormUtils.hasSelected('ids')) {
					alert('请选择需要撤销的记录');
					return;
				}
	
				$('#ids').val(FormUtils.getCheckedValues('ids'));
				$('#searchForm').attr('action', 'cancel.do').submit();
			}		
		</script>
		
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td height="30"  align="right">充值批次</td>
							<td height="30" >
								<s:textfield name="depositReg.depositBatchId" cssClass="{digit:true}"/>
							</td>
							<td height="30"  align="right">卡号</td>
							<td height="30" >
								<s:textfield name="depositReg.cardId" />
							</td>							
							<td height="30"  align="right">卡类型</td>
							<td height="30" >
								<s:select name="depositReg.cardClass" list="cardTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>	
						<tr>
							<td height="30"  align="right">撤销标志</td>
							<td height="30" >
								<s:select name="depositReg.depositCancel" list="cancelFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td height="30"  align="right">状态</td>
							<td height="30" >
								<s:select name="depositReg.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2" name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_DEPOSIT_CACEL_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">充值批次</td>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">卡类</td>
			   <td align="center" nowrap class="titlebg">撤销标志</td>
			   <td align="center" nowrap class="titlebg">返利方式</td>
			   <td align="center" nowrap class="titlebg">返利规则</td>
			   <td align="center" nowrap class="titlebg">充值金额</td>
			   <td align="center" nowrap class="titlebg">返利金额</td>
			   <td align="center" nowrap class="titlebg">实收金额</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${depositBatchId}</td>
			  <td align="center" nowrap>${cardId}</td>
			  <td align="center" nowrap>${cardClassName}</td>
			  <td align="center" nowrap>${depositCancelName}</td>
			  <td align="center" nowrap>${rebateTypeName}</td>
			  <td align="center" nowrap>${rebateName}</td>
			  <td align="right" nowrap>${fn:amount(amt)}</td>
			  <td align="right" nowrap>${fn:amount(rebateAmt)}</td>
			  <td align="right" nowrap>${fn:amount(realAmt)}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/depositCancel/detail.do?depositReg.depositBatchId=${depositBatchId}">明细</f:link>
			  	</span>
			  		<f:pspan pid="depositCancelMgr_cancel">
			  		<s:if test='"N".equals(cancelFlag)'>
			  			<s:if test="status == 04">
			  				<a href="javascript:submitUrl('searchForm', '/depositCancel/revoke.do?depositBatchId=${depositBatchId}', '确定要取消充值吗？');"/>取消</a>
			  			</s:if>
			  			<s:elseif test="status == 01">
			  			<span class="redlink">
			  				<a href="javascript:submitUrl('searchForm', '/depositCancel/cancel.do?depositBatchId=${depositBatchId}', '确定要撤销充值吗？');"/>撤销</a>
			  			</span>
			  			</s:elseif>
			  		</s:if>
					</f:pspan>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>