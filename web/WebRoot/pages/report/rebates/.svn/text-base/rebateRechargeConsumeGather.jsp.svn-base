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
				if('${loginRoleType}' == '00'){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20');
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
				<s:form action="rebateRechargeConsumeGather.do" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>返利账户充值消费统计汇总报表<span class="caption_title">
						<f:link href="/pages/report/rebates/rebateRechargeConsumeDetail.do"> |返利账户充值消费统计明细报表
						</f:link></span></caption>
						<tr>
						    <td align="right">清算月份</td>
							<td>
								<s:textfield id="settMonth" name="formMap.settMonth" onclick="WdatePicker({dateFmt:'yyyyMM'});" cssClass="{required: true}"/>
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
			<s:if test="formMap.reportParamsString != null">
				<report:html name="report1" reportFileName="/rebates/rebateRechargeConsumeGather.raq"	
				    params="${formMap.reportParamsString}"
					reportEnterUrl="/pages/report/rebates/rebateRechargeConsumeGather.do" 
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
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>