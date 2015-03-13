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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		function getMinStartDate(){
			WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});
		}
		
		function getMaxEndDate(){
			WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'});
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
							<td align="right">交易流水</td>
							<td><s:textfield name="promtSettCostList.transSn" /></td>
							<td align="right">促销活动id</td>
							<td><s:textfield name="promtSettCostList.promtId" /></td>

						</tr>
						<tr>
							<td align="right">活动赠送方</td>
							<td><s:textfield name="promtSettCostList.promtDonateId" /></td>
							<td align="right">发卡机构</td>
							<td><s:textfield name="promtSettCostList.cardIssuer" /></td>

						</tr>
						<tr>
							<td align="right">商户编号</td>
							<td><s:textfield name="promtSettCostList.merchId" /></td>
							<td align="right">清算日期</td>
							<td>
								<s:textfield id="startDate" name="settStartDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="settEndDate" onclick="getEndDate();" cssStyle="width:68px;"/>
							</td>
					    </tr>
						<tr>
							<td>&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_PROMTSETTCOSTLIST_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
		<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
		<thead>
		<tr>
      <td align="center" nowrap class="titlebg">交易流水</td>
      <td align="center" nowrap class="titlebg">促销活动id</td>
      <td align="center" nowrap class="titlebg">交易类型</td>
      <td align="center" nowrap class="titlebg">清算日期</td>
      <td align="center" nowrap class="titlebg">活动赠送方编号</td>
      <td align="center" nowrap class="titlebg">发卡机构编号</td>
      <td align="center" nowrap class="titlebg">商户编号</td>
      <td align="center" nowrap class="titlebg">状态</td>

      <td align="center" nowrap class="titlebg">操作</td>
		</tr>
		</thead>
		<s:iterator value="page.data"> 
		<tr>		
      <td align="center" nowrap>${transSn}</td>
      <td align="center" nowrap>${promtId}</td>
      <td align="center" nowrap>${transTypeName}</td>
      <td align="center" nowrap>${settDate}</td>
      <td align="center" nowrap>${promtDonateId}-${fn:branch(promtDonateId)}${fn:merch(promtDonateId)}${groupName}</td>
      <td align="center" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
      <td align="center" nowrap>${merchId}-${fn:merch(merchId)}</td>
      <td align="center" nowrap>${statusName}</td>

		  <td align="center" nowrap>
		  	<span class="redlink">
		  		<f:link href="/promotions/promtTrans/detail.do?promtSettCostList.transSn=${transSn}&&promtSettCostList.promtId=${promtId}">查看</f:link>
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