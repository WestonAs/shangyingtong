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
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
	    	    
	   	<style type="text/css">
			html { overflow-y: scroll; }
		</style>
			
	</head>
    
	<body>
        <jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<!-- 查询功能区 -->		
		<s:form id="inputForm" action="assign.do" cssClass="validate">
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			
			<div class="contentb">			  
			      <table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
			         <caption>用户证书指派</caption>
			         <tr>
			           <td align="right">指派机构</td>
					   <td>
						  <s:select name="branchInfo.branchCode" headerKey="" headerValue="--请选择--" list="branchs" listKey="branchCode" listValue="branchName" 
						  	cssClass="{required:true}" onmouseover="FixWidth(this)" />
						  <span class="field_tipinfo">请选择指派机构</span>
					   </td>
					   				   
					   <td height="30" align="right">
						  <input type="submit" value="证书指派" id="input_btn2"  name="ok" />
						  <input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
						  <input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/userCert/list.do?goBack=goBack')" class="ml30" />
					   </td>						
					 </tr>			         
				  </table>
			</div>
		</div>

		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg"><input type="checkbox" onclick="FormUtils.selectAll(this, 'ids')" />全选</td>
			   <td align="center" nowrap class="titlebg">所属机构</td>   
			   <td align="center" nowrap class="titlebg">绑定机构</td> 
			   <td align="center" nowrap class="titlebg">用户编号</td>
			   <td align="center" nowrap class="titlebg">证书名称</td>
			   <td align="center" nowrap class="titlebg">证书序号</td>  
			   <td align="center" nowrap class="titlebg">开通日期</td>  
			   <td align="center" nowrap class="titlebg">到期日期</td> 
			   <td align="center" nowrap class="titlebg">证书状态</td>   
			</tr>
			</thead>
			<s:iterator value="userCertificatePage.data"> 
			   <tr>
			      <td><input type="checkbox" name="ids" value="${seqNo}"/></td>
			      <td align="center" nowrap>${fn:branch(branchCode)}</td>
			      <td align="center" nowrap>${fn:branch(bndBranch)}</td>
			      <td align="center" nowrap>${userId}</td>
			      <td align="center" nowrap>${fileName}</td>
			      <td align="center" nowrap>${seqNo}</td>
			      <td align="center" nowrap>${startDate}</td>
			      <td align="center" nowrap>${endDate}</td>
			      <td align="center" nowrap>${useStateName}</td>			     
			   </tr>
			</s:iterator>
			</table>
			<f:paginate name="userCertificatePage"/>
		</div>
		
		<s:token name="_TOKEN_CERTIFICATE_ASSIGN"/>
		</s:form>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>