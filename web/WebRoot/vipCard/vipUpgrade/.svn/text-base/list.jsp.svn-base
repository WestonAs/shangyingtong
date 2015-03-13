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
		<div class="location">您当前所在位置： <span class="redlink"><f:link href="/home.jsp">首页</f:link></span>
		    &gt;  综合业务 &gt; 会员卡管理 &gt; 升级换卡 
		</div>
		<div class="msg" style="display: none; float: left">
			<span id="_msg_content" style="float: left"></span>
			<a id="_msg_close" href="javascript:hideMsg();" style="float: right;color: red">关闭 X</a>
		</div>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption><span class="caption_title"><f:link href="/vipCard/membLevelChgReg/list.do">会员级别变更</f:link></span> | 升级换卡</caption>
						<tr>
							<td align="right">新卡号</td>
							<td><s:textfield name="renewCardReg.newCardId"/></td>
							<td align="right">旧卡号</td>
							<td><s:textfield name="renewCardReg.cardId"/></td>
							<td align="right">持卡人姓名</td>
							<td><s:textfield name="renewCardReg.custName"/></td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="memberupgrade_add"><input style="margin-left:30px;" type="button" value="升级换卡" onclick="javascript:gotoUrl('/vipCard/vipUpgrade/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_VIPUPGRADE_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">换卡登记编号</td>
			   <td align="center" nowrap class="titlebg">旧卡号</td>
			   <td align="center" nowrap class="titlebg">新卡号</td>
			   <td align="center" nowrap class="titlebg">证件类型</td>
			   <td align="center" nowrap class="titlebg">证件号码</td>
			   <td align="center" nowrap class="titlebg">持卡人姓名</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${renewCardId}</td>
			  <td align="center" nowrap>${cardId}</td>
			  <td align="center" nowrap>${newCardId}</td>
			  <td align="center" nowrap>${certTypeName}</td>
			  <td align="center" nowrap>${certNo}</td>
			  <td align="center" nowrap>${custName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/vipCard/vipUpgrade/detail.do?renewCardReg.renewCardId=${renewCardId}">查看</f:link>
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