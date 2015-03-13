<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<%@ taglib uri="/WEB-INF/runqianReport4.tld" prefix="report"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
				
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<script>	
			$(function(){
				if('${loginRoleType}' == '00' || '${loginRoleType}' == '02'){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20');
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20', '', '', '${loginBranchCode}');
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
				<s:form action="cardMembFeeEarning.do" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<s:if test="centerOrCenterDeptRoleLogined || fenzhiRoleLogined">
								<td align="right">发卡机构</td>
								<td>
									<s:hidden id="idBranchCode" name="formMap.cardBranchCode" cssClass="{required: true}"/>
									<input type="text" id="idBranchCode_sel" value="${fn:branch(formMap.cardBranchCode) }" class="{required: true}" />
								</td>
							</s:if>
							<s:elseif test="cardOrCardDeptRoleLogined">
								<td align="right">发卡机构</td>
								<td>
									<s:select name="formMap.cardBranchCode" list="myCardBranch" listKey="branchCode" listValue="branchName" 
										onmouseover="FixWidth(this)" cssClass="{required: true}"></s:select>
								</td>
							</s:elseif>
							
							<td align="right">清算日期</td>
							<td>
								<s:textfield id="startSettDate" name="formMap.startSettDate" onclick="WdatePicker()" cssClass="{required: true}" cssStyle="width:68px;"/>
								-
								<s:textfield id="endSettDate" name="formMap.endSettDate" onclick="WdatePicker()" cssClass="{required: true}" cssStyle="width:68px;"/>
							</td>
							
							<td height="30">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="$('#idBranchCode_sel').val('');$('#idBranchCode').val('');FormUtils.reset('searchForm');" name="escape" />
							</td>
						</tr>
					</table>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="clear">
			<s:if test="formMap.reportParamsString != ''">
				<report:html name="report1" reportFileName="/card/cardMembFeeEarning.raq"	
				    params="${formMap.reportParamsString}"
					reportEnterUrl="pages/report/card/cardMembFeeEarning.do" 
				    funcBarFontFace="宋体"               
					funcBarFontSize="14px"   	
					needSaveAsExcel="yes"
					needSaveAsPdf="yes"
					needSaveAsWord="yes"
					needPrint="yes"
					funcBarLocation="bottom"
					width="-1"
					useCache="false"
				/>
			</s:if>
		</div>

		<div class="userbox">
		<!-- 
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<span class="note_div">注释：</span>
				<ul class="showli_div">
					<li class="showli_div"></li>
				</ul> 
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		 -->
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>