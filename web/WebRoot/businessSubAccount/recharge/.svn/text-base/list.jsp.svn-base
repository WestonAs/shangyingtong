<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
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
		<f:js src="/js/date/WdatePicker.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			$(function(){
				$('#amount').blur(function(){
					var val = $(this).val();
					if(val.length > 0){
						var pattern = /^\d*(\.\d+)?$/;
						if(!pattern.test(val)){
							alert('金额不合法!');
							$(this).val('').focus();
						}
						if(parseFloat(val) == 0){
							alert('金额必须大于0');
							$(this).val('').focus();
						}
					}
				});
			});
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
							<td align="right">录入日期</td>
							<td>
								<s:textfield name="rechargeBill.startCreateDate" onfocus="WdatePicker()" cssStyle="width:70px"></s:textfield> - <s:textfield name="rechargeBill.endCreateDate" onfocus="WdatePicker()" cssStyle="width:70px"></s:textfield>
							</td> 
							<td align="right">金额</td>
							<td><s:textfield name="rechargeBill.amount" id="amount" cssClass="{number:true}"/></td> 
							<td align="right">状态</td>
							<td>
								<s:select list="rechargeStates" listKey="value" listValue="name" name="rechargeBill.state" headerKey="" headerValue="--请选择--"></s:select>
							</td> 
						</tr>
						<tr>
							<td align="right">虚户账号</td>
							<td><s:textfield name="rechargeBill.accountId" cssClass="{digitOrLetter:true}"/></td>
							<c:if test="${mer ne 'Y'}">
								<td align="right">客户编号</td>
								<td><s:textfield name="rechargeBill.custId" cssClass="{digitOrLetter:true}"/></td>
							</c:if>
						</tr>
						<tr> 
						<td></td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid = "bs_account_recharge_add" >
									<input style="margin-left:30px;" type="button" value="新增"  name="escape" id="input_btn3" onclick="javascript:gotoUrl('/businessSubAccount/recharge/showAdd.do');"/>
								</f:pspan>
							</td>
						</tr>
					</table>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">充值单号</td>
			   <td align="center" nowrap class="titlebg">虚户账号</td>
			   <c:if test="${mer ne 'Y'}">
				   <td align="center" nowrap class="titlebg">客户编号/名称</td>
			   </c:if>
			   <td align="center" nowrap class="titlebg">充值金额</td>		
			   <td align="center" nowrap class="titlebg">付款账号</td>
			   <td align="center" nowrap class="titlebg">付款户名</td>
			   <td align="center" nowrap class="titlebg">收款账号</td>
			   <td align="center" nowrap class="titlebg">收款户名</td> 
			   <td align="center" nowrap class="titlebg">录入时间</td>			   
			   <td align="center" nowrap class="titlebg">到账时间</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">备注</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${no}</td>
			  <td align="center" nowrap>${accountId}</td>
			  <c:if test="${mer ne 'Y'}">
				  <td align="center" nowrap>${custId}/${custName}</td>
			  </c:if>
			  <td align="center" nowrap>${fn:amount(amount)}</td>
			  <td align="center" nowrap>${bankCardNo}</td>
			  <td align="center" nowrap>${bankCardName}</td>
			  <td align="center" nowrap>${recAcctNo}</td>
			  <td align="center" nowrap>${recAcctName}</td>
			  <td align="center" nowrap><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td align="center" nowrap><s:date name="remitTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td align="center" nowrap>${stateName}</td>
			  <td align="center" nowrap>${note}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/businessSubAccount/recharge/detail.do?rechargeBill.no=${no}">查看</f:link>
			  		<!-- 有审核权限且充值单为待审核状态 -->
			  		<f:pspan pid="bs_account_recharge_check">	
			  			<c:if test="${canCheck eq 'Y'}">
			  				<f:link href="/businessSubAccount/recharge/showCheck.do?rechargeBill.no=${no}">审核</f:link>
			  			</c:if>	
			  		</f:pspan>	
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>