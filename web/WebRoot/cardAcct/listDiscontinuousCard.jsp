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
		
		<f:js src="/js/validate.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			function validateForm(){
				if($("#cardIds").val()!=null && $("#cardIds").val().split("\n").length>41){
					showMsg("输入的卡号不能大于40行！");
					return false;
				}else{
					return true;
				}
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="listDiscontinuousCard.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption><span class="caption_title"><f:link href="/cardAcct/list.do">卡账户详细查询</f:link></span> | 批量不连号卡账户详细查询</caption>
						<tr>					
							<td >
								卡号：
								<s:textarea id="cardIds" name="cardIds" rows="8" cssStyle="width: 30%" />
								<input type="submit" value="查询" id="input_btn2"  name="ok" onclick="return validateForm()" style="margin-left:30px;" />
								<input type="button" value="清除" onclick="FormUtils.reset('searchForm');$('#idBranchName').val('');$('#idBranchCode').val('');" name="escape" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDACCT_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<s:hidden id="id_maxRowCount" name="page.maxRowCount"></s:hidden>
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <!-- 
			   <td align="center" nowrap class="titlebg">账号</td>
			    -->
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">交易次数</td>
			   <td align="center" nowrap class="titlebg">累积充值金额</td>
			   <td align="center" nowrap class="titlebg">返利资金余额</td>
			   <td align="center" nowrap class="titlebg">充值资金余额</td>
			   <td align="center" nowrap class="titlebg">卡失效日期</td>
			   <td align="center" nowrap class="titlebg">卡状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="left" nowrap>${cardId}</td>	
			  <!-- 
			  <td align="center" nowrap>${acctId}</td>
			   -->
			  <td align="left" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="right" nowrap>${tradeCnt}</td>
			  <td align="right" nowrap>${fn:amount(accuChargeAmt)}</td>
			  <td align="right" nowrap>${fn:amount(rebateAvlbBal)}</td>
			  <td align="right" nowrap>${fn:amount(depositAvlbBal)}</td>
			  <td align="center" nowrap>${cardExpirDate}</td>		  
			  <td align="center" nowrap>${cardStatusDesc}</td>			  
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/cardAcct/detail.do?acctInfo.acctId=${acctId}&formMap.fromPage=listDiscontinuousCard">账户明细</f:link>  		
			  		<f:link href="/cardAcct/acctTransList.do?formMap.cardId=${cardId}&formMap.fromPage=listDiscontinuousCard">交易明细</f:link>  				  		
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
				<span class="note_div">注释</span>
				<ul class="showli_div">
					<li class="showli_div">请输入最多40张卡号进行批量卡交易查询，每输入一张卡号后，请加个回车</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>