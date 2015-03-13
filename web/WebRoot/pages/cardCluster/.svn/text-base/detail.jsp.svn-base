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

        <f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				var cluster = $('#clusterId_id').val();
				if(isEmpty(cluster)){
					$('#clusterId_id').val('集群代码/名称').css({'color':'gray'});
				}
				var branch = $('#branchCode_id').val();
				if(isEmpty(branch)){
					$('#branchCode_id').val('发卡机构代码/名称').css({'color':'gray'});
				}
				$('#clusterId_id').blur(function(){
					var value = $(this).val();
					if(isEmpty(value)){
						$(this).val('集群代码/名称').css({'color':'gray'});
					} else {
						$(this).css({"color":"#333"});
					}
				});
				$('#clusterId_id').focus(function(){
					var value = $(this).val();
					if(value == '集群代码/名称'){
						$(this).val('').css({"color":"#333"});;
					}
				});
				$('#branchCode_id').blur(function(){
					var value = $(this).val();
					if(isEmpty(value)){
						$(this).val('发卡机构代码/名称').css({'color':'gray'});
					} else {
						$(this).css({"color":"#333"});
					}
				});
				$('#branchCode_id').focus(function(){
					var value = $(this).val();
					if(value == '发卡机构代码/名称'){
						$(this).val('').css({"color":"#333"});
					}
				});
			});
			function clearText() {
				var clusterId = $('#clusterId_id').val();
				var branchCode = $('#branchCode_id').val();
				if(clusterId == '集群代码/名称'){
					$('#clusterId_id').val('');
				}
				if(branchCode == '发卡机构代码/名称'){
					$('#branchCode_id').val('');
				}
				return true;
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="detail.do" cssClass="validate-tip" onsubmit="return clearText();">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td height="30" align="right">集群</td>
							<td><s:textfield id="clusterId_id" name="clusterInfo.clusterid"  cssStyle="font-size:12px;" cssClass="readonly" readonly="true"/></td>
							<!--
							<td height="30" align="right">发卡机构名称</td>
							<td><s:textfield id="branchCode_id" name="cardCluster.branchCode" cssStyle="font-size:12px;"/></td>
							  -->
							<td height="30" align="right">状态</td>
							<td>
								<s:select name="clusterInfo.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name">
								</s:select>
							</td>
						</tr>
						<tr>
							<td height="30" align="right"></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input class="ml30" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="clusterbranch_add">
									<input class="ml30" type="button" value="新增" onclick="javascript:gotoUrl('/pages/cardCluster/showAddClusterBranch.do?clusterInfo.clusterid=${clusterInfo.clusterid}');" id="input_btn3" name="escape" />
								</f:pspan>
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/cardCluster/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARD_MERCH_CLUSTER_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">集群ID</td>
			   <td align="center" nowrap class="titlebg">机构号</td>
			   <td align="center" nowrap class="titlebg">机构名称</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">更新人</td>
			   <td align="center" nowrap class="titlebg">更新时间</td>
			   <td align="center" nowrap class="titlebg">备注</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${clusterid}</td>
			  <td align="center" nowrap>${branchcode}</td>
			  <td align="center" nowrap>${branchname}</td>
			  <td align="center" nowrap>${statusname}</td>
			  <td align="center" nowrap>${updateby}</td>
			  <td align="center" nowrap><s:date name="updatetime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap>${remark}</td>
			  <td align="center" nowrap>
			       <span class="redlink">
			  		   <f:pspan pid="clusterbranch_delete">
		  				   <a href="javascript:submitUrl('searchForm', '/pages/cardCluster/deleteClusterBranch.do?clusterBranch.clusterid=${clusterid}&clusterBranch.branchcode=${branchcode}', '确定要删除吗？');" />删除</a>
		  			   </f:pspan>
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