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
		
		<f:css href="/css/jquery.autocomplete.css"/>
		<f:js src="/js/jquery.autocomplete.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
		function branchSelected(){
			var branchCode = $("#id_branchNo_temp").val();
			var branchName = $("#id_branchName").val();
			$('#id_branchNo2').val(branchCode+'|'+branchName);
			$('#id_merchNo_temp').val('');
			$('#id_merchName').val('');
			$('#id_merchNo2').val('');
			loadUserInfoList(branchCode,'');
		}
		function merchSelected(){
			var merchNo = $("#id_merchNo_temp").val();
			var merchName = $("#id_merchName").val();
			$('#id_merchNo2').val(merchNo+'|'+merchName);
			$('#id_branchNo_temp').val('');
			$('#id_branchName').val('');
			$('#id_branchNo2').val('');			    
			loadUserInfoList('',merchNo);
		}
		  $(function(){
			    $("#id_branchNo").focus(function(){
					var branchCode = $(this).val();
					$('#id_merchNo').val('');
					loadUserInfoList(branchCode,'');
				});
				$("#id_merchNo").focus(function(){
					var merchNo = $(this).val();
					$('#id_branchName').val('');
					loadUserInfoList('',merchNo);
				});
				if('${loginRoleType}' == '00'){
					Selector.selectBranch('id_branchName', 'id_branchNo_temp', true, '00,01,11,12,20,21,22,30,31,32',branchSelected);
					Selector.selectMerch('id_merchName', 'id_merchNo_temp', true,'', '', '','',merchSelected);
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('id_branchName', 'id_branchNo_temp', true, '01,11,12,20,21,22,30,31,32', branchSelected, '', '${userCertificate.branchCode}');
					Selector.selectMerch('id_merchName', 'id_merchNo_temp', true, '', '', '', '${userCertificate.branchCode}',merchSelected);
				}
		  });
		  
		  function clearForm() {
		       FormUtils.reset('inputForm');
		       $('#id_userId').empty();
		  }
		  function loadUserInfoList(branchCode,merchNo) {
		       $('#id_userId').empty();
		       if(isEmpty(branchCode) && isEmpty(merchNo)) {
		          return ;
		       }
		       var branch_arr = new Array();
		       var merchNo_arr = new Array();
		       branch_arr = branchCode.split('|');
		       merchNo_arr = merchNo.split('|');
		       $('#id_userId').load(CONTEXT_PATH + '/pages/userCert/loadUserInfoList.do', 
						{'callId':callId(), 'branchCode':branch_arr[0],'merchNo' : merchNo_arr[0]});
		  }
		  $().ready(function(){
			  var branchCode = $('#id_branchCode').val();
			  var branchUrlData = '/pages/userCert/loadMyBranchInfo.do?userCertificate.branchCode=' + branchCode ;
			  var merchUrlData =  '/pages/userCert/loadMyMerchInfo.do?userCertificate.branchCode=' + branchCode ;
			  
			  $('#id_branchNo').autocomplete(CONTEXT_PATH + branchUrlData, {
		    	minChars: 0,
		    	max:40,
		    	matchContains: true,
		    	mustMatch: true,
		    	autoFill: true,
				dataType: "json",
				delay: 300,
				parse: function(data) {
					return $.map(data, function(row) {					   
						return {
							data: row,
							value: formatBranchValue(row),							
							result: formatBranchResult(row)
						}
					});
				},
				
				formatItem: function(row) {
				    return formatBranchResult(row);
				}
			  });
			 
			  $('#id_merchNo').autocomplete(CONTEXT_PATH + merchUrlData, {
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
							value: formatMerchValue(row),
							result: formatMerchResult(row)
						}
					});
				},
				
				formatItem: function(row) {
				    return formatMerchResult(row);
				}
			  });	
			  
			  function formatBranchValue(row) {
			    return (row.branchCode + '|' + row.branchName + '|' + row.branchPinYin);  
		      }
		      
		      function formatBranchResult(row) {
		        return (row.branchCode + '|' + row.branchName); 
		      }
		  
		      function formatMerchValue(row) {
			    return (row.merchId + '|' + row.merchName + '|' + row.merchPinYin);  
		      }
		      
		      function formatMerchResult(row) {
		        return (row.merchId + '|' + row.merchName); 
		      }
			    
		  });		  
		  		  
		</script>
	</head>
    
	<body >
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="bound.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="98%" border="0" cellspacing="2" cellpadding="0">
						<caption>用户数字证书绑定</caption>
						<tr>
						   <td width="80" height="30" align="right">所属机构:</td>
						   <td><s:property value="userCertificate.branchCode"/> ${fn:branch(userCertificate.branchCode)}<s:hidden name="userCertificate.branchCode" id="id_branchCode" /></td>
						</tr>
					    
						<tr>
							<td width="80" height="30" align="right">证书序列号:</td>
							<td>${userCertificate.seqNo}<s:hidden name="userCertificate.seqNo" /></td>
					    </tr>
						
						<tr>
						    <td width="80" height="30" align="right">证书文件名:</td>
							<td>${userCertificate.fileName}<s:hidden name="userCertificate.fileName" /></td>
					     </tr>
						
						<tr>
						     <td width="80" height="30" align="right">开通日期:</td>
							<td>${userCertificate.startDate}<s:hidden name="userCertificate.startDate" /></td>
						</tr>
																		
						<tr>
					       <s:if test="showBranch">
					         <td width="80" height="30" align="right">选择机构</td>
						     <td>
						     <!-- 
							   <s:textfield id="id_branchNo" name="userInfo.branchNo" />
							    -->
							    <s:hidden id="id_branchNo_temp" /><s:textfield id="id_branchName" />
							    <s:hidden id="id_branchNo2" name="userInfo.branchNo" />
							     
							   <span class="field_tipinfo">请选择机构</span>							 
						     </td>
					       </s:if>	
					       <s:if test="showMerch">
						     <td width="80" height="30" align="right">选择商户</td>
						     <td>
						     <!-- 
							   <s:textfield id="id_merchNo" name="userInfo.merchantNo"  />
							     -->
							   <s:hidden id="id_merchNo_temp" /><s:textfield id="id_merchName" />
							   <s:hidden id="id_merchNo2" name="userInfo.merchantNo"  />
							   <span class="field_tipinfo">请选择商户</span>							 
						     </td>
						   </s:if>
						</tr>	
						
						<tr>
							<td width="80" height="30" align="right">用户列表:</td>
							<td colspan="3">
							    <select id="id_userId" name="userInfo.userId" class="{required:true}" style="width: auto;"></select>
							    <span class="field_tipinfo">请选择绑定用户</span>
							</td>
						</tr>				
											 
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="绑定提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="clearForm();" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/userCert/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:hidden name="userCertificate.useState" />	
					<s:hidden name="userCertificate.dnNo" />
				    <s:token name="_TOKEN_CERTIFICATE_BOUND"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>