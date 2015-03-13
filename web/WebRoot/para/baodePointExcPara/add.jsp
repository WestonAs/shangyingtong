<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			var branchCode = $("#id_BranchCode").val();
			Selector.selectMerch('id_merchName', 'id_MerNo', true, branchCode);

			$('#id_Rate').blur(function(){
				var rate = $('#id_Rate').val();
				validateRate(rate);
			});
			
		});

		function validateRate(rate){
			if(isEmpty(rate)){
				return;
			}
			// 0--100的整数
			if(!checkIsInteger(rate)||rate<0 || rate>100){
				showMsg('请输入0至100的整数。');
				$(':submit').attr('disabled', 'true');
				return;
			} else {
				$(':submit').removeAttr('disabled');
			}
		}
		
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<s:hidden name="firstFlag"></s:hidden>
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
						<td width="80" height="30" align="right">发卡机构</td>
						<td>
							<s:hidden id="id_BranchCode" name="branchCode" cssClass="{required:true}" ></s:hidden>
							<s:textfield name="branchName" readonly="true" cssClass="readonly" ></s:textfield>
						</td>
						</tr>
						<s:if test="firstFlag">
						<tr>
						<td width="80" height="30" align="right">商户</td>
						<td>
							<s:textfield name="branchName" readonly="true" cssClass="readonly" ></s:textfield>
						</td>
						</tr>
						</s:if>
						<s:else>
						<tr>
						<td width="80" height="30" align="right" >商户</td>
						<td>
							<s:hidden id="id_MerNo" name="baodePointExcPara.merNo"
								cssClass="{required:true}"></s:hidden>
							<s:textfield  id="id_merchName" cssClass="{required:true}" ></s:textfield>
						</td>
						</tr>
						</s:else>
						<tr>
						<td width="80" height="30" align="right">积分类型</td>
						<td>
							<s:select id="Id_ptClass" name="baodePointExcPara.ptClass" list="ptClassList" headerKey="" 
								headerValue="--请选择--" listKey="ptClass" listValue="className" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择保得分期积分类型</span>	
						</td>
						</tr>
						<tr>
						<td width="80" height="30" align="right">失效积分返还率</td>
						<td>
							<s:textfield id="id_Rate" name="baodePointExcPara.expirExcRate" cssClass="{required:true, decimal:'6,2'}" ></s:textfield><span>%</span>
							<span class="field_tipinfo">请输入1-100的整数</span>	
						</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/para/baodePointExcPara/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_BAODEPOINTEXCPARA_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>