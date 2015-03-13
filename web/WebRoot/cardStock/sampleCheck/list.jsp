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
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="list.do" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">卡号</td>
							<td>
								<s:textfield name="sampleCheck.sampleCheck" cssClass="{digitOrLetter:true}"/>
								<span class="error_tipinfo">卡号只能数字或字母</span>
							</td>
							<td align="right">抽检结果</td>
							<td>
								<s:select name="sampleCheck.checkResult" list="resultList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" ></s:select>
							</td>
							<td align="right">卡批次号</td>
							<td>
								<s:textfield name="sampleCheck.makeId" cssClass="{digitOrLetter:true}" />
								<span class="error_tipinfo">卡批次号只能数字或字母</span>
							</td>
						</tr>
						<tr>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="samplecheck_add">
									<input style="margin-left:10px;" type="button" value="新增" onclick="javascript:gotoUrl('/cardStock/sampleCheck/showAdd.do');" id="input_btn3"  name="escape" />
								</f:pspan>
							</td>
						</tr>
					</table>
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
			   <td align="center" nowrap class="titlebg">抽检结果</td>
			   <td align="center" nowrap class="titlebg">卡批次号</td>
			   <td align="center" nowrap class="titlebg">抽检人代码</td>
			   <td align="center" nowrap class="titlebg">抽检日期</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td nowrap>${cardId}</td>
			  <td align="center" nowrap>${checkResultName}</td>
			  <td align="center" nowrap>${makeId}</td>
			  <td align="center" nowrap>${checkUser}</td>
			  <td align="center" nowrap><s:date name="checkDate" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/cardStock/makeHistory/detail.do?sampleCheck.id=${id}">明细</f:link>
			  	</span>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>