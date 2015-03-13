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
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
	    <f:js src="/js/date/WdatePicker.js" defer="defer"/>
	    
	    <f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
    	
	   	<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
					if('${loginRoleType}' == '00'){
						Selector.selectBranch('idBranchName', 'idBranchCode', true, '00,01,11,12,20,21,22,30,31,32');
						Selector.selectBranch('idBndBranchName', 'idBndBranchCode', true, '00,01,11,12,20,21,22,30,31,32');
					}else if('${loginRoleType}' == '01'){
						Selector.selectBranch('idBranchName', 'idBranchCode', true, '01,11,12,20,21,22,30,31,32', '', '', '${loginBranchCode}');
						Selector.selectBranch('idBndBranchName', 'idBndBranchCode', true, '01,11,12,20,21,22,30,31,32', '', '', '${loginBranchCode}');
					}
			});
			
			//清除
			function clearForm(){
				$('#idBranchCode').val('');
				$('#idBranchName').val('');
				$('#idBndBranchCode').val('');
				$('#idBndBranchName').val('');
				FormUtils.reset('searchForm');
			}
			
			function goUerCertUrl(){
			}
		</script>
	</head>
    
	<body>
        <jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<!-- 查询功能区 -->		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
			   <s:form id="searchForm" action="list.do">
			      <table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
			          <caption>${ACT.name}</caption>
			          <tr>
			             <s:if test="showBranch">
						  <td align="right">所属机构</td>
						  <td>
						  <!-- 
						  	<s:textfield name="userCertificate.branchName" />
						  	 -->
						  	<s:hidden id="idBranchCode" name="userCertificate.branchCode"/><s:textfield id="idBranchName" name="userCertificate.branchName" />
						  	
							<%--<s:select name="userCertificate.branchName" headerKey="" headerValue="--请选择--" list="branchs" listKey="branchCode" listValue="branchName" cssClass="{required:true}"></s:select>
						  --%></td>
						 </s:if>
						 <s:if test="showMerch">
							<td align="right">绑定机构</td>
							<td>
							<!-- 
								<s:textfield name="userCertificate.bndBranchName" />
								 -->
								<s:hidden id="idBndBranchCode" /><s:textfield id="idBndBranchName" name="userCertificate.bndBranchName" />
								<%--<s:select name="userCertificate.bndBranchName" headerKey="" headerValue="--请选择--" list="merchs" listKey="merchId" listValue="merchName" cssClass="{required:true}"></s:select>
							--%></td>
						 </s:if>
						 <td align="right">证书状态</td>
						 <td>
						   <s:select name="useState" headerKey="" headerValue="--请选择--" list="userCertStateList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
						 </td>						
			          </tr>
			          <tr>
			             <td align="right">用户编号</td>
						 <td><s:textfield name="userCertificate.userId" /></td>
						 <td align="right">证书序号</td>
						 <td><s:textfield name="seqNo"/></td>
			             <td align="right">查询日期</td>
						 <td>						    
							<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:67px;"/>&nbsp;-&nbsp;
							<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:67px;"/>
						 </td>						 
					 </tr>
					 <tr>	
					 	<td align="right">证书名称</td>
						<td><s:textfield name="userCertificate.fileName" /></td>
					 	<td height="30" colspan="5" align="left">
							<input type="submit" value="查询" id="input_btn2"  name="ok" />
							<input style="margin-left:30px;" type="button" value="清除" onclick="clearForm();" name="escape" />
						<s:if test="showAdd">
						  <f:pspan pid="usercertmgr_add">
						   	<input style="margin-left:30px;" type="button" value="证书录入" onclick="javascript:gotoUrl('/pages/userCert/showAdd.do');" id="input_btn3"  name="escape" />
						  </f:pspan>	
						</s:if>								
						<s:if test="showExport">
						  <f:pspan pid="usercertmgr_add">
						    <input style="margin-left:30px;" type="button" value="证书导出" onclick="javascript:gotoUrl('/pages/userCert/export.do');" id="input_btn3"  name="escape" />
						  </f:pspan>	
						</s:if>					
			           </td>
			          </tr>
			      </table>
			      <s:token name="_TOKEN_CERTIFICATE_LIST"/>
			   </s:form>
		    </div>
		    <b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">所属机构</td>   
			   <td align="center" nowrap class="titlebg">绑定机构</td> 
			   <td align="center" nowrap class="titlebg">用户编号</td>
			   <td align="center" nowrap class="titlebg">用户名</td>
			   <td align="center" nowrap class="titlebg">证书序号</td> 
			   <td align="center" nowrap class="titlebg">证书名称</td>  
			   <td align="center" nowrap class="titlebg">开通日期</td>  
			   <td align="center" nowrap class="titlebg">到期日期</td> 
			   <td align="center" nowrap class="titlebg">证书绑定状态</td>    
			   <td align="center" nowrap class="titlebg">证书状态</td>    
			   <td align="center" nowrap class="titlebg">证书处理</td>
			</tr>
			</thead>
			<s:iterator value="userCertificatePage.data"> 
			   <tr>
			      <td align="center" nowrap>${fn:branch(branchCode)}</td>
			      <td align="center" nowrap>${fn:branch(bndBranch)}${fn:merch(bndBranch)}</td>
			      <td align="center" nowrap>${userId}</td>
			      <td align="center" nowrap>${fn:user(userId)}</td>			      
			      <td align="center" nowrap>${seqNo}</td>
			      <td align="center" nowrap>${fileName}</td>
			      <td align="center" nowrap>${startDate}</td>
			      <td align="center" nowrap>${endDate}</td>
			      <td align="center" nowrap>${useStateName}</td>
			      <td align="center" nowrap>${stateName}</td>
			      <td align="center" nowrap>
			        <span class="redlink">
			           <%-- 增加operateForm，解决bug：用户做证书处理动作时提交时，如果提交searchForm，会造成将searchForm中的字段也作为参数提交到后台；//其实这种处理方式很烂，目前只是简短解决该bug  --%>
			           <s:form id="operateForm" />
			           
			  		   <f:link href="/pages/userCert/detail.do?userCertificate.dnNo=${dnNo}&userCertificate.seqNo=${seqNo}&userCertificate.startDate=${startDate}">明细</f:link>			  		   
			  		   <s:if test="useState == 00">
			  		      <s:if test="showAssign">			  		         
			  		        <!-- 进行单个指派 -->			  		       
			                <f:link href="/pages/userCert/showSingleAssign.do?userCertificate.dnNo=${dnNo}&userCertificate.seqNo=${seqNo}&userCertificate.startDate=${startDate}&userCertificate.useState=${useState}">证书指派</f:link>
			  		      </s:if>
			  		      <s:if test="showRemove">			  		          
			  		        <!-- 进行移除 -->			  		        
			  		        <a href="javascript:submitUrl('operateForm', '/pages/userCert/remove.do?userCertificate.dnNo=${dnNo}&userCertificate.seqNo=${seqNo}&userCertificate.startDate=${startDate}&userCertificate.useState=${useState}&userCertificate.fileName=${fileName}', '确定要删除吗？');"/>证书移除</a>
			  		      </s:if>
			  		   </s:if> 
			  		   <s:elseif test="useState == 01">
			  		      <s:if test="showBound">
			  		        <s:if test="userId == null">
			  		          <!-- 申请绑定 -->
			  		          <f:link href="/pages/userCert/showBound.do?userCertificate.dnNo=${dnNo}&userCertificate.seqNo=${seqNo}&userCertificate.startDate=${startDate}&userCertificate.useState=${useState}&userCertificate.branchCode=${branchCode}&userCertificate.fileName=${fileName}">申请绑定</f:link>
			  		        </s:if>
			  		      </s:if>
			  		      <s:if test="showRecycle">			  		           
			  		        <!-- 进行回收 -->	
			  		        <a href="javascript:submitUrl('operateForm', '/pages/userCert/recycle.do?userCertificate.dnNo=${dnNo}&userCertificate.seqNo=${seqNo}&userCertificate.startDate=${startDate}&userCertificate.useState=${useState}', '确定要回收吗？');"/>证书回收</a>		  		            
			  		      </s:if>
			  		   </s:elseif>
			  		   <s:elseif test="useState == 02">
			  		      <!-- 1 暂停绑定 -->
			  		      <f:link href="/pages/userCert/terminate.do?userCertificate.dnNo=${dnNo}&userCertificate.seqNo=${seqNo}&userCertificate.startDate=${startDate}&userCertificate.useState=${useState}&userCertificate.userId=${userId}">暂停使用</f:link>
			  		      <!-- 2 解除绑定 -->
			  		      <s:if test="showUnBound">			  		        
			  		          <a href="javascript:submitUrl('operateForm', '/pages/userCert/unBound.do?userCertificate.dnNo=${dnNo}&userCertificate.seqNo=${seqNo}&userCertificate.startDate=${startDate}&userCertificate.useState=${useState}&userCertificate.userId=${userId}', '确定要解除绑定吗？');"/>解除绑定</a>			  		       
			  		      </s:if>
			  		   </s:elseif>
			  		   <s:elseif test="useState == 03">
			  		      <!-- 1 重新绑定 -->
			  		      <f:link href="/pages/userCert/reBound.do?userCertificate.dnNo=${dnNo}&userCertificate.seqNo=${seqNo}&userCertificate.startDate=${startDate}&userCertificate.useState=${useState}&userCertificate.userId=${userId}">重新使用</f:link>
			  		      <!-- 2 变更绑定 -->
			  		      <s:if test="showChgBound">			  		        
			  		          <f:link href="/pages/userCert/showChgBound.do?userCertificate.dnNo=${dnNo}&userCertificate.seqNo=${seqNo}&userCertificate.startDate=${startDate}&userCertificate.useState=${useState}&userCertificate.fileName=${fileName}&userCertificate.userId=${userId}&userCertificate.branchCode=${branchCode}">变更绑定</f:link>  
			  		      </s:if>
			  		   </s:elseif>
			  		   <s:if test="bndBranch != null">			  		          
			  		        <!-- 进行更新 -->	
			  		        <f:link href="/pages/userCert/showUpdate.do?userCertificate.dnNo=${dnNo}&userCertificate.seqNo=${seqNo}&userCertificate.startDate=${startDate}&userCertificate.userId=${userId}">证书更新</f:link>		  		        
			  		   </s:if>		  		   		
			  	    </span>
			      </td>			      
			   </tr>
			</s:iterator>
			</table>
			<f:paginate name="userCertificatePage"/>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>