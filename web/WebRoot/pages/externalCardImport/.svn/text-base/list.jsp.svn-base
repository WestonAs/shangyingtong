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
		
		<script type="text/javascript" src="externalCardImport.js"></script>
		
		<script language="javascript">
		</script>
		
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
				<s:form id="searchForm" cssClass="validate-tip"> <!-- 默认当前action -->
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<s:hidden name="externalCardImportReg.uptype" />
						
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">卡BIN</td>
							<td><s:textfield name="externalCardImportReg.binNo" cssClass="{digit:true}"/></td>
							
							<td align="right">文件名</td>
							<td><s:textfield name="externalCardImportReg.fileName"/></td>

							<td align="right">发卡机构</td>
							<td>
								<s:select name="externalCardImportReg.cardBranch" list="cardBranchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName"></s:select>
							</td>
						</tr>
	
						<tr>
							<td align="right">状态</td>
							<td>
								<s:select name="externalCardImportReg.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								
								<s:if test='externalCardImportReg.externalNumMakeCard'>
									<s:set var="add_privilege" value='"externalNumMakeCard_reg_add"'/>
								</s:if>
								<s:else>
									<s:set var="add_privilege" value='"externalCardImport_add"'/>
								</s:else>
								
								<f:pspan pid="${add_privilege}">
									<input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/externalCardImport/showAdd.do?externalCardImportReg.uptype=${externalCardImportReg.uptype}');" id="input_btn3"  name="escape" />
								</f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_EXTERNAL_CARD_IMPORT_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">卡BIN号</td>
			   <td align="center" nowrap class="titlebg">卡子类型</td>
			   <td align="center" nowrap class="titlebg">总笔数</td>
			   <td align="center" nowrap class="titlebg">文件名</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${cardBranch}-${fn:branch(cardBranch)}</td>
			  <td align="center" nowrap>${binNo}</td>
			  <td align="center" nowrap>${cardSubclassName}</td>
			  <td align="right" nowrap>${totalCount}</td>
			  <td align="center" nowrap>${fileName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="left" nowrap>
			  	<span class="redlink">
				  	<a href="javascript:downloadOrigFile(${id},'${fileName}');" >下载原文件</a>
				  	&nbsp;
				  	<f:link href="/pages/externalCardImport/detail.do?externalCardImportReg.id=${id}">查看</f:link>
				  	&nbsp;
				  	<f:pspan pid="${add_privilege}">
					  	<s:if test="status == @gnete.card.entity.state.ExternalCardImportState@DEAL_FAILED.value && !externalCardImportReg.externalNumMakeCard"><!-- 处理状态为处理失败，并且不是外部号码开卡 -->
					  		<f:link href="/pages/externalCardImport/showReImport.do?externalCardImportReg.id=${id}">重新导入文件</f:link>
					  	</s:if>
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