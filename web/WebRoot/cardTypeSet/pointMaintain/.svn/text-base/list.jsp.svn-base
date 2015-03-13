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
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td><s:textfield name="merchPointRate.cardIssuer" /></td>
							
							<td width="80" height="30" align="right">商户号码</td>
							<td><s:textfield name="merchPointRate.merNo" /></td>
							
							<td width="80" height="30" align="right">积分类型</td>
							<td><s:textfield name="merchPointRate.ptClass" /></td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="pointmaintain_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/cardTypeSet/pointMaintain/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_POINTMAINTAIN_LIST"/>
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
			   <td align="center" nowrap class="titlebg">商户名称</td>
			   <td align="center" nowrap class="titlebg">积分类型</td>
			   <td align="center" nowrap class="titlebg">积分返利兑换率</td>
			   <td align="center" nowrap class="titlebg">更新日期</td>
			   <td align="center" nowrap class="titlebg">备注</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
              <td align="left" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>		  
              <td align="left" nowrap>${merNo}-${fn:merch(merNo)}</td>
			  <td align="center" nowrap>${ptClass}</td>
			  <td align="center" nowrap>${fn:percentPre(ptDiscntRate*100, 2)}</td>
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap>${remark}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/cardTypeSet/pointMaintain/detail.do?merchPointRate.ptClass=${ptClass}&merchPointRate.cardIssuer=${cardIssuer}&merchPointRate.merNo=${merNo}">查看</f:link>
			  		<f:pspan pid="pointmaintain_modify"><f:link href="/cardTypeSet/pointMaintain/showModify.do?merchPointRate.ptClass=${ptClass}&merchPointRate.cardIssuer=${cardIssuer}&merchPointRate.merNo=${merNo}">修改</f:link></f:pspan>
			  		<f:pspan pid="pointmaintain_delete">	<a href="javascript:submitUrl('searchForm', '/cardTypeSet/pointMaintain/delete.do?ptClass=${ptClass}&cardIssuer=${cardIssuer}&merNo=${merNo}', '确定要删除吗？');" />删除</a></f:pspan>
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