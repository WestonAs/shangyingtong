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
		<script>
			$(function(){
			});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="showDrawInfo.do" cssClass="validate-tip" >
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td height="30" align="right">抽奖活动ID</td>
							<td><s:textfield id="drawId_id" name="drawDef.drawId"  cssStyle="font-size:12px;" cssClass="readonly" readonly="true"/></td>
							<td height="30" align="right">用户标识</td>
							<td><s:textfield id="cardIssuer_id" name="drawInfoReg.jionDrawId" cssStyle="font-size:12px;"/></td>
							<td height="30" align="right">参与机构编号</td>
							<td><s:textfield id="joinDrawId_id" name="drawInfoReg.cardIssuer" cssStyle="font-size:12px;"/></td>
						</tr>
						<tr>
							<td height="30" align="right"></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input class="ml30" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<s:if test="drawStatus == 10">
									<f:pspan pid="draw_info_add">
										<input class="ml30" type="button" value="新增" onclick="javascript:gotoUrl('/lottery/drawDef/showAddDrawInfo.do?drawDef.drawId=${drawDef.drawId}');" id="input_btn3" name="escape" />
									</f:pspan>
								</s:if>
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/lottery/drawDef/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARD_DRAWINFOREG_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">抽奖活动ID</td>
			   <td align="center" nowrap class="titlebg">抽奖活动名称</td>
			   <td align="center" nowrap class="titlebg">用户标识</td>
			   <td align="center" nowrap class="titlebg">用户姓名</td>
			   <td align="center" nowrap class="titlebg">用户手机</td>
			   <td align="center" nowrap class="titlebg">参与机构</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">更新人</td>
			   <td align="center" nowrap class="titlebg">更新时间</td>
			   <td align="center" nowrap class="titlebg">备注</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${drawId}</td>
			  <td align="center" nowrap>${drawName}</td>
			  <td align="center" nowrap>${jionDrawId}</td>
			  <td align="center" nowrap>${name}</td>
			  <td align="center" nowrap>${mobilePhone}</td>
			  <td align="center" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}${fn:merch(cardIssuer)}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>${updateBy}</td>
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap>${remark}</td>
			  <td align="center" nowrap>
			       <span class="redlink">
			  		   <f:pspan pid="draw_info_delete">
		  				   <a href="javascript:submitUrl('searchForm', '/lottery/drawDef/deleteDrawInfo.do?drawId=${drawId}&jionDrawId=${jionDrawId}', '确定要删除吗？');" />删除</a>
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