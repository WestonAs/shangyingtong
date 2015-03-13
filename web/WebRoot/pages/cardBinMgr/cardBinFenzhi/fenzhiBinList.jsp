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
				<s:form id="searchForm" action="fenzhiBinList.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption><span class="caption_title"><f:link href="/pages/cardBinMgr/cardBinFenzhi/list.do">运营机构卡BIN申请列表</f:link> | </span>运营机构卡BIN列表</caption>
						<tr>
							<td width="80" height="30" align="right">卡BIN</td>
							<td>
								<s:textfield name="fenZhiCardBinMgr.cardBin" />
							</td>
							<td width="80" height="30" align="right">卡BIN前三位</td>
							<td>
								<s:textfield name="fenZhiCardBinMgr.cardBinPrex" />
							</td>
							<td width="80" height="30" align="right">状态</td>
							<td>
								<s:select name="fenZhiCardBinMgr.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">是否已使用</td>
							<td>
								<s:select name="fenZhiCardBinMgr.useFlag" list="yesOrNoFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td width="80" height="30" align="right">当前所属机构</td>
							<td>
								<s:select name="fenZhiCardBinMgr.currentBranch" list="branchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName"></s:select>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right"></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_FENZHICARDBINMGR_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">卡BIN</td>
			   <td align="center" nowrap class="titlebg">卡BIN前三位</td>
			   <td align="center" nowrap class="titlebg">当前所属运营机构</td>
			   <td align="center" nowrap class="titlebg">上次所属运营机构</td>
			   <td align="center" nowrap class="titlebg">是否已使用</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${cardBin}</td>
			  <td align="center" nowrap>${cardBinPrex}</td>
			  <td align="center" nowrap>${currentBranch}-${fn:branch(currentBranch)}</td>
			  <td align="center" nowrap>${lastBranch}-${fn:branch(lastBranch)}</td>
			  <td align="center" nowrap>${useFlagName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/cardBinMgr/cardBinFenzhi/fenzhiBinDetail.do?fenZhiCardBinMgr.cardBin=${cardBin}">明细</f:link>
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