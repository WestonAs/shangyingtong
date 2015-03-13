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
		<f:js src="/js/biz/singleProduct/pointClassTemp.js"/>
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
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">积分名称</td>
							<td><s:textfield name="pointClassTemp.className" /></td>
							
							<td width="80" height="30" align="right">积分使用方法</td>
							<td>
								<s:select id="" name="pointClassTemp.ptUsage" list="ptUsageList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
							 
							</td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="single_product_point_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/singleProduct/point/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_SINGLEPRODUCTPOINTCLASS_TEMP_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">积分类型</td>
			   <td align="center" nowrap class="titlebg">类型名称</td>
			   <td align="center" nowrap class="titlebg">机构</td>
			   <td align="center" nowrap class="titlebg">积分折扣率</td>
			   <td align="center" nowrap class="titlebg">金额类型</td>
			   <td align="center" nowrap class="titlebg">积分使用方法</td>
			   <td align="center" nowrap class="titlebg">参考积分</td>
			   <!-- 
			   <td align="center" nowrap class="titlebg">积分分期方法</td>
			   <td align="center" nowrap class="titlebg">积分有效期数</td>
			   <td align="center" nowrap class="titlebg">积分分期数(月数)</td>
			   <td align="center" nowrap class="titlebg">可用最新期数</td>
			   <td align="center" nowrap class="titlebg">积分折旧率</td>
			   <td align="center" nowrap class="titlebg">积分类型参数1</td>
			   <td align="center" nowrap class="titlebg">积分类型简称</td>
			   <td align="center" nowrap class="titlebg">积分兑换规则类型</td>
			   
			   <td align="center" nowrap class="titlebg">积分用途</td>
			   -->	
			   <td align="center" nowrap class="titlebg">状态</td>   	
			    	   
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td>${ptClass}</td>
			  <td align="center" nowrap>${className}</td>
			  <td align="center" nowrap>${fn:branch(branchCode)}</td>
			  <td align="center" nowrap>${ptDiscntRate}</td>
			  <td align="center" nowrap>${amtTypeName}</td>
			  <td align="center" nowrap>${ptUsageName}</td>
				<td align="center" nowrap>${ptRef}</td>
	  		  <!-- 
			  <td align="center" nowrap>${ptInstmMthd}</td>
			  <td align="center" nowrap>${ptValidityCyc}</td>		  		  
	  		  <td align="center" nowrap>${instmPeriod}</td>
			  <td align="center" nowrap>${ptLatestCyc}</td>
			  <td align="center" nowrap>${ptDeprecRate}</td>
			  <td align="center" nowrap>${ptClassParam1}</td>
			  <td align="center" nowrap>${classShortName}</td>			  
			  <td align="center" nowrap>${ptExchgRuleType}</td>
			  
			  <td align="center" nowrap>${ptUseLimit}</td>
			   -->	
			  <td align="center" nowrap>${statusName}</td>	
			 
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/singleProduct/point/detail.do?pointClassTemp.ptClass=${ptClass}">查看</f:link>
				  		<s:if test="status==10" >
				  			<a href="javascript:submitUrl('searchForm','/pages/singleProduct/point/modify.do?pointClassTemp.ptClass=${ptClass}&pointClassTemp.status=00','确定要设置为正常吗?');">设置为正常</a>
				  		</s:if>
				  		<s:else>
				  			<a href="javascript:submitUrl('searchForm','/pages/singleProduct/point/modify.do?pointClassTemp.ptClass=${ptClass}&pointClassTemp.status=10','确定要设置为注销吗?');">设置为注销</a>
				  		</s:else>
				  		
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