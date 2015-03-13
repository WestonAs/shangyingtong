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
		<f:js src="/js/datePicker/WdatePicker.js"/>

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
							<td align="right">礼品代码</td>
							<td><s:textfield name="giftDef.giftId" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">礼品名称</td>
							<td><s:textfield name="giftDef.giftName"/></td>	
							<td align="right">积分名称</td>
							<td><s:textfield name="giftDef.ptClassName"/></td>	
						</tr>
						<tr>	
							<td></td>					
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="giftdefmgr_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/gift/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_GIFT_LIST" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">礼品代码</td>
			   <td align="center" nowrap class="titlebg">礼品名称</td>
			   <td align="center" nowrap class="titlebg">礼品简称</td>
			   <td align="center" nowrap class="titlebg">联名机构</td>
			   <td align="center" nowrap class="titlebg">积分类型</td>
			   <td align="center" nowrap class="titlebg">积分名称</td>
			   <td align="center" nowrap class="titlebg">兑换积分</td>
			   <td align="center" nowrap class="titlebg">生效日期</td>
			   <td align="center" nowrap class="titlebg">失效日期</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td nowrap>${giftId}</td>
			  <td align="center" nowrap>${giftName}</td>
			  <td align="center" nowrap>${giftChain}</td>
			  <td align="center" nowrap>${fn:branch(jinstId)}${fn:merch(jinstId)}</td>
			  <td align="center" nowrap>${ptClass}</td>
			  <td align="center" nowrap>${ptClassName}</td>
			  <td align="right" nowrap>${fn:amount(ptValue)}</td>
			  <td align="center" nowrap>${effDate}</td>
			  <td align="center" nowrap>${expirDate}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/gift/detail.do?giftDef.giftId=${giftId}">查看</f:link>
			  		<s:if test="#session.SESSION_USER.branchNo==jinstId || #session.SESSION_USER.merchantNo==jinstId">
			  		<s:if test="status==00">
			  		<f:pspan pid="giftdefmgr_modify"><f:link href="/gift/showModify.do?giftDef.giftId=${giftId}">编辑</f:link></f:pspan>
			  		</s:if>
			  		<f:pspan pid="giftdefmgr_delete">
			  			<a href="javascript:submitUrl('searchForm', '/gift/delete.do?giftId=${giftId}', '确定要删除吗？');" />删除</a>
			  		</f:pspan>	
			  		</s:if>	  					  		
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