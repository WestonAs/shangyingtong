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
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>运营机构卡BIN申请列表<span class="caption_title"> | <f:link href="/pages/cardBinMgr/cardBinFenzhi/fenzhiBinList.do">运营机构卡BIN列表</f:link></span></caption>
						<tr>
							<td width="80" height="30" align="right">登记薄ID</td>
							<td><s:textfield name="fenzhiCardBinReg.regId" /></td>
							
							<td width="80" height="30" align="right">起始卡BIN</td>
							<td>
								<s:textfield name="fenzhiCardBinReg.strBinNo" />
							</td>
							<td width="80" height="30" align="right">状态</td>
							<td>
								<s:select name="fenzhiCardBinReg.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">申请机构</td>
							<td>
								<s:textfield name="fenzhiCardBinReg.appBranch" />
							</td>
							<td height="30" colspan="4">
								<input style="margin-left:30px;" type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="cardbin_fenzhi_mgr_add">
									<input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/cardBinMgr/cardBinFenzhi/showAdd.do');" id="input_btn3"  name="escape" />
								</f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDBIN_FENZHI_MGR_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">登记薄ID</td>
			   <td align="center" nowrap class="titlebg">所属机构</td>
			   <td align="center" nowrap class="titlebg">起始卡BIN</td>
			   <td align="center" nowrap class="titlebg">卡数量</td>
			   <td align="center" nowrap class="titlebg">申请机构</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="left" nowrap>${regId}</td>
			  <td align="center" nowrap>${branchCode}-${fn:branch(branchCode)}</td>
			  <td align="center" nowrap>${strBinNo}</td>
			  <td align="center" nowrap>${binCount}</td>
			  <td align="center" nowrap>${appBranch}-${fn:branch(appBranch)}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/cardBinMgr/cardBinFenzhi/detail.do?fenzhiCardBinReg.regId=${regId}">明细</f:link>
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