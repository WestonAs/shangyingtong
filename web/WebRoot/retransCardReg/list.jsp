<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
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
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">卡号</td>
							<td><s:textfield name="retransCardReg.cardId" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">商户号</td>
							<td><s:textfield name="retransCardReg.merchId" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">商户名称</td>
							<td><s:textfield name="retransCardReg.merchName"/></td>		
						</tr>
						<tr>					
							<td align="right">终端号</td>
							<td><s:textfield name="retransCardReg.termId"/></td>
							<td align="right">状态</td>
							<td>
								<s:select name="retransCardReg.status" list="@gnete.card.entity.state.RegisterState@getForCheck()"  
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" />
							</td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="6">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="retransCardReg_add">
									<input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/retransCardReg/showAdd.do');" id="input_btn3"  name="escape" />
								</f:pspan>
								<f:pspan pid="retransCardReg_addBatFile">
									<input style="margin-left:30px;" type="button" value="文件方式批量新增" onclick="javascript:gotoUrl('/retransCardReg/showAddBatFile.do');" name="escape" />
								</f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_RETRANSCARDREG_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">补账编号</td>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">发起方</td>
			   <td align="center" nowrap class="titlebg">商户号</td>
			   <td align="center" nowrap class="titlebg">商户名称</td>
			   <td align="center" nowrap class="titlebg">终端号</td>
			   <td align="center" nowrap class="titlebg">补账金额</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="left" nowrap>${retransCardId}</td>
			  <td align="center" nowrap>${cardId}</td>
			  <td align="center" nowrap>${cardBranch}</td>
			  <td align="center" nowrap>${initiator}</td>
			  <td align="center" nowrap>${merchId}</td>
			  <td align="left" nowrap>${merchName}</td>
			  <td align="center" nowrap>${termId}</td>
			  <td align="right" nowrap>${fn:amount(amt)}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/retransCardReg/detail.do?retransCardReg.retransCardId=${retransCardId}">查看</f:link>
			  		<!-- <f:pspan pid="retransCardReg_modify"><f:link href="/retransCardReg/showModify.do?retransCardReg.retransCardId=${retransCardId}">编辑</f:link></f:pspan> -->
			  		<f:pspan pid="retransCardReg_delete">
			  			<a href="javascript:submitUrl('searchForm', '/retransCardReg/delete.do?retransCardReg.retransCardId=${retransCardId}', '确定要删除吗？');" />删除</a>
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