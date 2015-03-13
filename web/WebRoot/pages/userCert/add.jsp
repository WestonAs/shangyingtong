<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
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
		
		<f:css href="/css/jquery.autocomplete.css"/>
		<f:js src="/js/jquery.autocomplete.js"/>		
	    <f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
	    <f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script type="text/javascript">
		$().ready(function() {
		/*
		    function formatValue(row) {
		       return (row.branchCode + '|' + row.branchName + '|' + row.branchPinYin);
		    }
		    
		    function formatResult(row) {
		       return (row.branchCode +  '|' + row.branchName);
		    }		    	   
		    
		    
			$("#id_assignBranch").autocomplete(CONTEXT_PATH + '/pages/userCert/loadAssignBranchInfo.do', {
		    	minChars: 0,
		    	max:40,
		    	matchContains: true,
		    	mustMatch: true,
		    	autoFill: true,
				dataType: "json",
				parse: function(data) {
					return $.map(data, function(row) {
						return {
							data: row,
							value: formatValue(row),
							result: formatResult(row)
						}
					});
				},
				formatItem: function(row) {
					return formatResult(row);
				}
		    });		  */
		    
		    	if('${loginRoleType}' == '00'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '00,01,11,12,20,21,22,30,31,32');
				}else if('${loginRoleType}' == '01'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '01,11,12,20,21,22,30,31,32', '', '', '${loginBranchCode}');
				}
				
		});			
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		 <div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			  <div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate" method="post" enctype="multipart/form-data">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>机构证书录入(指派)</caption>
						<tr>
						   <td width="80" height="30" align="right">上传机构</td> 
						   <td><s:property value="userInfo.branchNo"/> ${fn:branch(userInfo.branchNo)}</td>
						</tr>
					    <tr>
					        <s:if test="showBranch">
					        <td width="80" height="30" align="right">指派机构</td>
							<td>
							  <!-- 
							    <s:textfield id="id_assignBranch" name="branchInfo.branchCode" cssClass="{required:true}" />	
							 -->
								<s:hidden id="idBranchCode" name="branchInfo.branchCode"/><s:textfield id="idBranchName" name="branchInfo.branchName"  cssClass="{required:true}" />
								  
								<span class="field_tipinfo">请选择指派机构</span>
								
							</td>
							</s:if>
						</tr>
												
						<tr>
							<td width="80" height="30" align="right">证书文件</td>
							<td>
								<s:file name="upload" cssClass="{required:true}" contenteditable="false" ></s:file>
								<span class="field_tipinfo">请选择上传文件(单个证书或压缩文件)</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/userCert/list.do?goBack=goBack');" class="ml30" />
							</td>
						</tr>
									
					</table>
					<s:token name="_TOKEN_CERTIFICATE_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>