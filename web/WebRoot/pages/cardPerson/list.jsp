<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
	    <f:js src="/js/date/WdatePicker.js" defer="defer"/>
	    
	   	<style type="text/css">
			html { overflow-y: scroll; }
		</style>
			
	</head>
    
	<body>

		<!-- 查询功能区 -->		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
			   <s:form id="searchForm" action="list.do">
			      <table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
			          <caption>${ACT.name}</caption>			         
			          <tr>
			             <td align="right">查询日期</td>
						 <td>						    
							<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
							<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
						 </td>
						 <td align="right">状态</td>
						 <td>
						   <s:select name="state" headerKey="" headerValue="--请选择--" list="cardPersonStateList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
						 </td>
					 </tr>
					 <tr>	
					   <td height="30" colspan="6"> 
						<input class="ml30" type="submit" value="查询" id="input_btn2"  name="ok" />
						<s:if test="showAdd">
						<f:pspan pid="cardPersonmgr_add">
						  <input class="ml30" type="button" value="增加" onclick="javascript:gotoUrl('/pages/cardPerson/showAdd.do');" id="input_btn3"  name="escape" />
						</f:pspan>	
						</s:if>						
					    <input class="ml30" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
			           </td>
			          </tr>
			      </table>
			      <s:token name="_TOKEN_MAKECARDPERSON_LIST"/>
			   </s:form>
		    </div>
		</div>

		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">制卡厂商</td>   
			   <td align="center" nowrap class="titlebg">选定用户</td> 
			   <td align="center" nowrap class="titlebg">更新用户</td>
			   <td align="center" nowrap class="titlebg">更新时间</td>			   
			   <td align="center" nowrap class="titlebg">选定状态</td>   
			   <td align="center" nowrap class="titlebg">操作处理</td>
			</tr>
			</thead>
			<s:iterator value="cardPersonPage.data"> 
			   <tr>
			      <td align="center" nowrap>${branchNo}-${fn:branch(branchNo)}</td>
			      <td align="center" nowrap>${userId}-${fn:user(userId)}</td>
			      <td align="center" nowrap>${updateUser}</td>
			      <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			      <td align="center" nowrap>${stateName}</td>
			      <td align="center" nowrap>
			        <span class="redlink">
			  		<f:link href="/pages/cardPerson/detail.do?cardPerson.branchNo=${branchNo}&cardPerson.userId=${userId}&cardPerson.state=${state}">明细</f:link>
			  		<s:if test="state== 00">
			  		    <f:pspan pid="cardPersonmgr_modify">
			  		      <f:link href="/pages/cardPerson/showModify.do?cardPerson.branchNo=${branchNo}&cardPerson.userId=${userId}&cardPerson.state=${state}">编辑</f:link>
			  			</f:pspan>
			  		    
			  		    <f:pspan pid="cardPersonmgr_cancel">
			  		      <a href="javascript:submitUrl('searchForm', '/pages/cardPerson/cancle.do?cardPerson.branchNo=${branchNo}&cardPerson.userId=${userId}&cardPerson.state=${state}', '确定要失效吗？');" />失效</a>
			  		    </f:pspan>
			  			
			  		</s:if>			  				  		
			  	    </span>
			      </td>			      
			   </tr>
			</s:iterator>
			</table>
			<f:paginate name="cardPersonPage"/>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>