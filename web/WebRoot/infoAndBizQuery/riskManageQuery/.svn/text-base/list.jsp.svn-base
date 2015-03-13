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
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">风险等级</td>
							<td>
								<s:select name="formMap.riskLevel" list="riskLevelTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							
							<td align="right">营业执照</td>
							<td><s:textfield name="formMap.license"/></td>
							
							<td align="right">营业执照有效天数 &le;</td>
							<td><s:textfield name="formMap.licenseEffDayCnt" cssClass="{digits:true, min:0}" maxlength="4" cssStyle="width:68px;"/>天</td>

						</tr>
	
						<tr>
							<td align="right">类型</td>
							<td>
								<s:radio name="formMap.type" list="#{'B':' 机构 ', 'M':' 商户 ' }"/>
							</td>
							<td align="right">法人身份证</td>
							<td><s:textfield name="formMap.legalPersonIdcard"/></td>
							
							<td align="right">法人身份证有效期天数 &le;</td>
							<td><s:textfield name="formMap.legalPersonIdcardEffDayCnt" cssClass="{digits:true, min:0}" maxlength="4" cssStyle="width:68px;"/>天</td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_RISK_MANAGE_QUERY_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
					   <td align="center" nowrap class="titlebg">${formMap.type=="B" ? "机构" : "商户"}</td>
					   <td align="center" nowrap class="titlebg">风险等级</td>
					   <td align="center" nowrap class="titlebg">营业执照</td>
					   <td align="center" nowrap class="titlebg">营业执照有效期</td>
					   <td align="center" nowrap class="titlebg">法人身份证</td>
					   <td align="center" nowrap class="titlebg">法人身份证有效期</td>
					</tr>
				</thead>
				<s:iterator value="page.data"> 
					<tr>
					  <td nowrap>
					  	<s:if test='formMap.type=="B"'>
					  		${branchCode} - ${branchName}
					  	</s:if>
					  	<s:elseif test='formMap.type=="M"'>
					  		${merchId} - ${merchName}
					  	</s:elseif>
					  </td>
					  <td align="center" nowrap>${riskLevelName}</td>
					  <td align="center" nowrap>
						  	<s:if test='formMap.type=="B"'> 
						  		${license}
						  	</s:if>
						  	<s:elseif test='formMap.type=="M"'>
						  		${merchCode}
						  	</s:elseif>
					  </td>
					  <td align="center" nowrap><s:date name="licenseExpDate" format="yyyy-MM-dd" /></td>
					  <td align="center" nowrap>${legalPersonIdcard}</td>
					  <td align="center" nowrap><s:date name="legalPersonIdcardExpDate" format="yyyy-MM-dd" /></td>
					</tr>
				</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>