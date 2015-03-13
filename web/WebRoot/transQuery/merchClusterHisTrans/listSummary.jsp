<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>商户集群历史交易汇总</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<script type="text/javascript" src="merchClusterHisTrans.js"></script>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
		$(function(){
			$('#generateExcelBtn').click(function(){generateExcel(true)});
			$('#cardIssuer').change(function(){
				ajaxFindMerchClusterInfos($('#cardIssuer').val());
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
				<s:form id="searchForm" action="listSummary.do" cssClass="validate-tip" >
					<input type="hidden" name="procStatus" value="01"/><!-- 扣款成功 -->
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption><span class="caption_title"><f:link href="/transQuery/merchClusterHisTrans/list.do">商户集群历史交易明细查询</f:link></span> | 商户集群历史交易汇总</caption>
						<tr>
							<td align="right">清算日期</td>
							<td>
								<s:textfield id="startDate" name="settStartDate" onclick='WdatePicker({ maxDate:"#F{$dp.$D(\'endDate\')}" });' cssClass="{required:true}" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="settEndDate" onclick='WdatePicker({minDate:"#F{$dp.$D(\'startDate\')}" });' cssClass="{required:true}" cssStyle="width:68px;"/>
							</td>
							
							<td align="right">发卡机构编号</td>
							<td title="商户集群所属的发卡机构">
								<s:if test="cardOrCardDeptRoleLogined">
									<s:textfield id="cardIssuer" name="cardIssuer" cssClass="{required:true} readonly" readonly="true" />
								</s:if>
								<s:else>
									<s:textfield id="cardIssuer" name="cardIssuer" cssClass="{required:true}"/>
								</s:else>
							</td>
							<td align="right">商户集群</td>
							<td>
								<s:select id="merchClusterId" name="merchClusterId" list="merchClusterInfos" listKey="merchClusterId" listValue="merchClusterName" headerKey="" headerValue="--请选择--" cssClass="{required:true}" />
							</td>
						</tr>
						<tr>
							<td align="right">商户编号</td>
							<td>
								<s:textfield name="merchNo"/>
							</td>
							<td align="right">交易类型</td>
							<td>
								<s:select name="transType" list="@gnete.card.entity.type.TransType@ALL.values()" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" name="ok" onclick="return validateForm();"/>
								<input type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" style="margin-left:30px;" />
								<input type="button" value="导出Excel" id="generateExcelBtn" style="margin-left:30px;" />
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
			<s:hidden id="id_maxRowCount" name="page.maxRowCount"></s:hidden>
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">清算日期</td>
			   <td align="center" nowrap class="titlebg">商户编号</td>
			   <td align="center" nowrap class="titlebg">商户名称</td>
			   <td align="center" nowrap class="titlebg">发卡机构编号</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   
			   <td align="center" nowrap class="titlebg">交易类型</td>
			   <td align="center" nowrap class="titlebg">币种</td>
			   <td align="center" nowrap class="titlebg">交易笔数</td>
			   <td align="center" nowrap class="titlebg">清算金额</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
				<tr>
				  <td align="center" nowrap>${workdate}</td>
				  <td align="left" nowrap>${merchId}</td>
				  <td align="left" nowrap>${fn:merch(merchId)}</td>
				  <td align="center" nowrap>${cardIssuer}</td>
				  <td align="center" nowrap>${fn:branch(cardIssuer)}</td>
				  
				  <td align="center" nowrap>${transTypeName}</td>
				  <td align="center" nowrap>${curCode}</td>
				  <td align="center" nowrap>${transNum}</td>
				  <td align="right" nowrap>${fn:amount(settAmt)}</td>	
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
				<li class="showli_div">清算日期跨度最大为90天。</li>
				<li class="showli_div">导出Excel最大条数为100,000条。</li>
				<li class="showli_div">本页面只汇总处理状态为“扣款成功”的历史记录。</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>