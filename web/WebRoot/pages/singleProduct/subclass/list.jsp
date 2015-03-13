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
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="form_grid" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">卡类型号</td>
							<td>
								<s:textfield name="cardSubClassTemp.cardSubclass"/>
							</td>
						
							<td align="right">卡类型名称</td>
							<td>
								<s:textfield name="cardSubClassTemp.cardSubclassName"/>
							</td>
							
							<td align="right">卡种</td>
							<td>
								<s:select name="cardSubClassTemp.cardClass" list="cardTypeList" headerKey="" headerValue="--请选择--" listKey="cardTypeCode" listValue="cardTypeName">
								</s:select>
							</td>
						</tr>
	
						<tr>
							<td align="right">绝对失效日期</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="single_product_subclass_add"><input style="margin-left:10px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/singleProduct/subclass/showAdd.do');" id="input_btn3"  name="escape" />
								</f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDSUBCLASS_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">卡类型号</td>
			   <td align="center" nowrap class="titlebg">运营机构</td>
			   <td align="center" nowrap class="titlebg">卡类型名称</td>
			   <td align="center" nowrap class="titlebg">卡种</td>
			   <td align="center" nowrap class="titlebg">绝对失效日期</td>
			   <td align="center" nowrap class="titlebg">卡片类型</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td nowrap>${cardSubclass}</td>
			  <td nowrap>${fn:branch(branchCode)}</td>
			  <td nowrap>${cardSubclassName}</td>
			  <td align="center" nowrap>${cardClassName}</td>
			  <td align="center" nowrap>${mustExpirDate}</td>
			  <td align="center" nowrap>${icTypeName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/singleProduct/subclass/detail.do?cardSubClassTemp.cardSubclass=${cardSubclass}">查看</f:link>
			  			<!-- 
			  			<f:link href="/pages/singleProduct/subclass/showModify.do?cardSubClassTemp.cardSubclass=${cardSubclass}">编辑</f:link>
			  			<a href="javascript:submitUrl('searchForm', '/pages/singleProduct/subclass/delete.do?cardSubclass=${cardSubclass}', '确定要删除吗？');" />删除</a>
			  			 -->
			  			<s:if test="status==10" >
				  			<a href="javascript:submitUrl('searchForm','/pages/singleProduct/subclass/modify.do?cardSubclass=${cardSubclass}&cardSubClassTemp.status=00','确定要设置为正常吗?');">设置为正常</a>
				  		</s:if>
				  		<s:else>
				  			<a href="javascript:submitUrl('searchForm','/pages/singleProduct/subclass/modify.do?cardSubclass=${cardSubclass}&cardSubClassTemp.status=10','确定要设置为注销吗?');">设置为注销</a>
				  		</s:else>
				  		
			  	</span>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>