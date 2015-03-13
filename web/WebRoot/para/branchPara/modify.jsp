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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<table class="form_grid detail_tbl" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="120" height="30" align="right">机构运行参数代码</td>
							<td>
								${branchPara.paraCode}
								<s:hidden name="branchPara.paraCode" />
							</td>
						</tr>
						<tr>
							<td width="120" height="30" align="right">机构(商户)代码</td>
							<td>
								${branchPara.refCode}
								<s:hidden name="branchPara.refCode" />
							</td>
						</tr>
						<tr>
							<td width="120" height="30" align="right">是否机构</td>
							<td>
								<s:if test="branchPara.isBranch == Y">是</s:if><s:else>否</s:else>
								<s:hidden name="branchPara.isBranch" />
							</td>
						</tr>
						<tr>
							<td width="120" height="30" align="right">参数名称</td>
							<td>
								<s:textfield name="branchPara.paraName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入参数名称</span>
							</td>
						</tr>
						<tr>
							<td width="120" height="30" align="right">参数值</td>
							<td>
								<s:textfield name="branchPara.paraValue" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入参数值</span>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<s:hidden name="branch.branchCode"/>
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/para/branchPara/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_BRANCH_PARA_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>