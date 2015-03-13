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
				$.getJSON(CONTEXT_PATH + "/vipCard/membReg/ajaxIsExporting.do",{'callId':callId()}, 
					function(json){
						if(json.success){
							alert("正在生产 导出会员信息，请耐心等待...");
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
							<td><s:textfield name="membReg.cardId"/></td>		
							
							<td align="right">客户姓名</td>
							<td><s:textfield name="membReg.custName"/></td>
							<td align="right">会员类型</td>
							<td>
								<s:select name="membReg.membClass"  list="membClassDefList" headerKey="" headerValue="--请选择--" 
									listKey="membClass" listValue="className" onmouseover="FixWidth(this);"></s:select>
							</td>	
						</tr>
						<tr>	
							<td align="right">证件号码</td>
							<td><s:textfield name="membReg.credNo"/></td>		
							<td align="right">手机号</td>
							<td><s:textfield name="membReg.mobileNo"/></td>		
							<s:if test="centerOrCenterDeptRoleLogined">
								<td align="right">发卡机构编号</td>
								<td>
									<s:textfield name="membReg.cardIssuer"/>
								</td>
							</s:if>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="memberinforeg_add">
									<input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/vipCard/membReg/showAdd.do');" 
									id="input_btn3"  name="escape" />
								</f:pspan>
								<f:pspan pid="memberinforeg_export">
									<input style="margin-left:30px;" type="button" value="导出数据" id="exportBtn" />
								</f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MEMBREG_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">注册编号</td>
			   <td align="center" nowrap class="titlebg">客户姓名</td>
			   <td align="center" nowrap class="titlebg">会员类型</td>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">证件号码</td>
			   <td align="center" nowrap class="titlebg">手机号</td>
			   <td align="center" nowrap class="titlebg">性别</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="center" nowrap>${membRegId}</td>
			  <td align="center" nowrap>${custName}</td>
			  <td align="center" nowrap>${membClass}</td>
			  <td align="center" nowrap>${cardId}</td>
			  <td align="center" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="center" nowrap>${credNo}</td>
			  <td align="center" nowrap>${mobileNo}</td>
			  <td align="center" nowrap>${sexName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/vipCard/membReg/detail.do?membReg.membRegId=${membRegId}">查看</f:link>
			  		<f:pspan pid="memberinforeg_modify"><f:link href="/vipCard/membReg/showModify.do?membReg.membRegId=${membRegId}">编辑</f:link></f:pspan>
			  		
			  		<f:pspan pid="memberinforeg_delete">
			  			<a href="javascript:submitUrl('searchForm', '/vipCard/membReg/delete.do?membReg.membRegId=${membRegId}', '确定要删除吗？');" />删除</a>
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