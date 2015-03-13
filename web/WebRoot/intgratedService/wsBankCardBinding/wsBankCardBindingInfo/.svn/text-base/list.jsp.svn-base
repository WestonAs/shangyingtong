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
		<script language="javascript">
		</script>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
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
							<td align="right">登记ID</td>
							<td><s:textfield name="wsBankCardBindingInfo.seqId" cssClass="{digit:true}"/></td>
							<td align="right">卡号</td>
							<td><s:textfield name="wsBankCardBindingInfo.cardId" cssClass="{digit:true}"/></td>
							<td align="right">外部号码</td>
							<td><s:textfield name="wsBankCardBindingInfo.externalCardId" cssClass="{digit:true}"/></td>
							<s:if test="cardRoleLogined">
								<td align="right">发卡机构</td>
								<td>
									<s:select name="wsBankCardBindingInfo.cardIssuer" list="cardBranchList" 
										headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName"></s:select>
								</td>
							</s:if>
							<s:else>
								<td align="right">发卡机构编号</td>
								<td><s:textfield name="wsBankCardBindingInfo.cardIssuer"/></td>
							</s:else>
						</tr>
						<tr>
							<td align="right">绑定状态</td>
							<td>
								<s:select name="wsBankCardBindingInfo.bindingStatus" list='#{"1":"绑定", "0":"已解绑"}' 
									headerKey="" headerValue="--请选择--"></s:select>
							</td>
							<td align="right">是否默认卡</td>
							<td>
								<s:select name="wsBankCardBindingInfo.isDefault" list='#{"1":"是", "0":"否"}' 
									headerKey="" headerValue="--请选择--"></s:select>
							</td>
							<td align="right">银行账户类型</td>
							<td>
								<s:select name="wsBankCardBindingInfo.bankaccttype" list='#{"0":"对私账户", "1":"对公账户"}' 
									headerKey="" headerValue="--请选择--"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right"></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
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
			   <td align="center" nowrap class="titlebg">ID</td>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">绑定状态</td>
			   <td align="center" nowrap class="titlebg">是否默认卡</td>
			   <td align="center" nowrap class="titlebg">银行账号</td>
			   <td align="center" nowrap class="titlebg">开户行名称</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${seqId}</td>
			  <td align="center" nowrap>${cardId}</td>
			  <td nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="center" nowrap>
					<s:if test='bindingStatus=="1"'>绑定</s:if>
					<s:if test='bindingStatus=="0"'>已解绑</s:if>
			  </td>
			  <td align="center" nowrap>
					<s:if test='isDefault=="1"'>是</s:if>
					<s:if test='isDefault=="0"'>否</s:if>
			  </td>
			  <td align="center" nowrap><f:mask value="${bankCard}" maskLength="8"/></td>
			  <td align="center" nowrap>${bankName}</td>
			  <td align="left" nowrap>
			  	<span class="redlink">
				  	<f:link href="/intgratedService/wsBankCardBinding/wsBankCardBindingInfo/detail.do?wsBankCardBindingInfo.seqId=${seqId}">明细</f:link>
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