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
		<script>
		$(function(){
			// 判断是否已经是发卡机构了
			var isCard = $(':checkbox[name="typeCodes"][value="20"]').attr('checked');
			// 如果不是发卡机构，当选择了发卡机构时，需要设置发展方
			if (!isCard){
				$(':checkbox[name="typeCodes"][value="20"]').click(function(){
					var checked = $(this).attr('checked');
					if (checked) {
						$('#id_card_tr').show();
						$('#id_card_develop').removeAttr('disabled');
					} else {
						$('#id_card_tr').hide();
						$('#id_card_develop').attr('disabled', 'true');
					}
				});
			}
		});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modifyType.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>修改机构类型信息</caption>
						
						<tr>
							<td width="80" height="20" align="right">机构编号</td>
							<td width="150">${branch.branchCode}</td>
							
							<td width="80" height="20" align="right">机构名称</td>
							<td width="300">${branch.branchName}</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">机构类型列表</td>
							<td colspan="3">
								<s:iterator value="typeList">
									<span <s:if test="value == 00">style="display:none;"</s:if>>
									<input type="checkbox" name="typeCodes" value="${value}" 
										<s:if test="value == 01 && loginRoleType == 01">disabled="true"</s:if>
										<s:if test="branchHasTypes.indexOf(value) != -1">checked</s:if>/>${name}<br />
									</span>
								</s:iterator>
							</td>
						</tr>
						<tr id="id_card_tr" style="display:none;">
							<td width="80" height="30" align="right">发展方</td>
							<td colspan="3">
								<s:select disabled="true" id="id_card_develop" name="branch.developSide" list="developBranchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">发卡机构需要设置发展方</span>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<s:hidden name="branch.branchCode"/>
								<input type="submit" value="提交" id="input_btn2" name="ok" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/branch/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_BRANCH_MODIFY_TYPE"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>