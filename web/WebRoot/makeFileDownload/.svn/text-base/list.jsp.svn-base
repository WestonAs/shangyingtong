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
		<f:js src="/js/date/WdatePicker.js"/>

		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>		
		<script>
			$(function(){
				if('${loginRoleTypeCode}' == '00'){
					$('#id_branchCode').val('');
					Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20');
				} else if('${loginRoleTypeCode}' == '01'){
					$('#id_branchCode').val('');
					Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20', '', '', '${loginBranchCode}');
				}
			});
		</script>
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
							<td align="right">制卡申请ID</td>
							<td>
								<s:textfield name="makeCardApp.appId"/>
							</td>
							<td align="right">卡样名称</td>
							<td>
								<s:textfield name="makeCardApp.makeName" title="模糊查询"/>
							</td>
							<td align="right">状态</td>
							<td>
								<s:select name="makeCardApp.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">开始卡号</td>
							<td>
								<s:textfield name="makeCardApp.strNo" cssClass="{digit:true}"/>
							</td>
							<td align="right">卡BIN</td>
							<td>
								<s:textfield name="makeCardApp.binNo" cssClass="{digit:true}"/>
							</td>
							<td align="right">更新日期</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
						</tr>
						<tr>
							<s:if test="centerOrCenterDeptRoleLogined || fenzhiRoleLogined">
								<td align="right">发卡机构</td>
								<td>
									<s:hidden id="id_branchCode" name="makeCardApp.branchCode"/>
									<s:textfield id="id_branchCodeName" name="makeCardApp.branchName"/>
								</td>
							</s:if>
							<s:elseif test="cardOrCardDeptRoleLogined">
								<td align="right">发卡机构</td>
								<td>
									<s:select name="makeCardApp.branchCode" list="myCardBranch"
										 headerKey="" headerValue="--请选择--" 
										 listKey="branchCode" listValue="branchName" 
										 onmouseover="FixWidth(this);" />
								</td>
							</s:elseif>
							<s:else>
								<td align="right">发卡机构编号</td>
								<td>
									<s:textfield name="makeCardApp.branchCode" cssClass="{digit:true}"/>
								</td>
								<td align="right">发卡机构名称</td>
								<td>
									<s:textfield name="makeCardApp.branchName" title="模糊查询"/>
								</td>
							</s:else>
							
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.clearFormFields('searchForm')" name="escape" />
								<input style="margin-left:10px;" type="button" value="下载解密工具" onclick="javascript:gotoUrl('/activeX/zhikachangshangwenjian.zip');" id="input_btn3"  name="escape" />
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
			   <td align="center" nowrap class="titlebg">制卡申请ID</td>
			   <td align="center" nowrap class="titlebg">卡样名称</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">卡BIN号</td>
			   <td align="center" nowrap class="titlebg">卡起始号</td>
			   <td align="center" nowrap class="titlebg">参考面值</td>
			   <td align="center" nowrap class="titlebg">卡数量</td>
			   <td align="center" nowrap class="titlebg">参考面值总金额(万元)</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">更新日期</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td nowrap>${appId}</td>
			  <td align="center" nowrap>${makeName}</td>
			  <td align="left" nowrap>${branchCode}- ${branchName}</td>
			  <td align="center" nowrap>${binNo}</td>
			  <td align="center" nowrap>${strNo}</td>
			  <td align="center" nowrap>${faceValue}</td>
			  <td align="center" nowrap>${cardNum}</td>
			  <td align="center" nowrap>
			  	<s:text name="global.format.moneyThousandSeparator">
			  		<s:param value="faceValue.doubleValue()*cardNum.doubleValue()/10000.0" />
			  	</s:text>
			  </td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd"/> </td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/makeFileDownload/detail.do?makeCardApp.id=${id}">明细</f:link>
			  		<s:if test='"06".equals(status) || "09".equals(status)'>
			  		<f:pspan pid="makefiledownload_download">
			  			<f:link href="/makeFileDownload/showDownload.do?id=${id}">下载制卡文件</f:link>
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