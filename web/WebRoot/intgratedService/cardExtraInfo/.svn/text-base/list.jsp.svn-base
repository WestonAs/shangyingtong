<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		
		<script>
			$(function(){
				$('#exportBtn').click(exportMembReg);
			});
	
			function exportMembReg(){
				$.getJSON(CONTEXT_PATH + "/intgratedService/cardExtraInfo/ajaxIsExporting.do",{'callId':callId()}, 
					function(json){
						if(json.success){
							alert("正在生产 导出持卡人信息，请耐心等待...");
						}else{
							// 提交产生excel的请求
							$('#searchForm').attr('action', 'export.do').submit();
							$('#searchForm').attr('action', 'list.do');
							$('#searchForm').find(":submit").attr('disabled', false);
							$('#searchForm').find(":button").attr('disabled', false);
						}
					}
				);
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
							<td align="right">卡号</td>
							<td>
								<s:textfield name="cardExtraInfo.cardId"/>
							</td>
							<td align="right">持卡人姓名</td>
							<td>
								<s:textfield name="cardExtraInfo.custName"/>
							</td>
							<td align="right">证件号码</td>
							<td>
								<s:textfield name="cardExtraInfo.credNo"/>
							</td>
						</tr>
						<tr>
							<td align="right">手机号</td>
							<td>
								<s:textfield name="cardExtraInfo.mobileNo"/>
							</td>
							<td align="right">购卡客户ID</td>
							<td>
								<s:textfield name="cardExtraInfo.cardCustomerId"/>
							</td>
							<s:if test="centerOrCenterDeptRoleLogined">
								<td align="right">发卡机构编号</td>
								<td>
									<s:textfield name="cardExtraInfo.cardBranch"/>
								</td>
							</s:if>
						</tr>
						<tr>
							<td align="right"></td>
							<td height="30" colspan="6">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid = "cardextrainfo_add" style="display: null">
									<input style="margin-left:30px;" type="button" value="录入持卡人信息"  name="escape" id="input_btn3" onclick="javascript:gotoUrl('/intgratedService/cardExtraInfo/showAdd.do');"/>
								</f:pspan> 
								<f:pspan pid="cardextrainfo_add">
									<input style="margin-left:30px;" type="button" value="上传文件" onclick="javascript:gotoUrl('/intgratedService/cardExtraInfo/showAddBat.do');" id="input_btn3"  name="escape" />
								</f:pspan>
								<f:pspan pid="cardextrainfo_export">
									<input style="margin-left:30px;" type="button" value="导出数据" id="exportBtn" />
								</f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDEXTRAINFO_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">购卡客户ID</td>
			   <td align="center" nowrap class="titlebg">持卡人姓名</td>
			   <td align="center" nowrap class="titlebg">证件类型</td>
			   <td align="center" nowrap class="titlebg">证件号码</td>
			   <td align="center" nowrap class="titlebg">手机号</td>
			   <td align="center" nowrap class="titlebg">邮件地址</td>
			   <td align="center" nowrap class="titlebg">生日</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${cardId}</td>
			  <td align="center" nowrap>${cardBranch}-${fn:branch(cardBranch)}</td>
			  <td align="center" nowrap>${cardCustomerId}</td>
			  <td align="center" nowrap>${custName}</td>
			  <td align="center" nowrap>${credTypeName}</td>
			  <td align="center" nowrap>${credNo}</td>
			  <td align="center" nowrap>${mobileNo}</td>
			  <td align="center" nowrap>${email}</td>
			  <td align="center" nowrap>${birthday}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<s:form id="operateForm" />
			  		<f:link href="/intgratedService/cardExtraInfo/detail.do?cardExtraInfo.cardId=${cardId}">查看</f:link>
			  		<f:pspan pid="cardextrainfo_modify">
			  			<f:link href="/intgratedService/cardExtraInfo/showModify.do?cardExtraInfo.cardId=${cardId}">编辑</f:link></f:pspan>
			  		<f:pspan pid="cardextrainfo_chgPassword">
			  			<f:link href="/intgratedService/cardExtraInfo/showChgPassword.do?cardExtraInfo.cardId=${cardId}">修改密码</f:link></f:pspan>
			  		<f:pspan pid="cardextrainfo_delete">
			  			<a href="javascript:submitUrl('operateForm', '/intgratedService/cardExtraInfo/delete.do?cardExtraInfo.cardId=${cardId}', '确定要删除吗？');" />删除</a>
			  		</f:pspan>	
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
				<li class="showli_div">导出的文件是cvs格式的文本文件</li>
				<li class="showli_div">数据行中的字段以英文半角逗号分隔。</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>