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
				var insType =  $('#id_insType').val();
				var insId =  $('#id_insId').val();
				
				if(insType=='0'){ //0-发卡机构
					$('#id_card').show();
					$('#id_merch').hide();
					Selector.selectCardBin('cardBinNo_sel', 'idCardBinNo', false, insId);
					
				} else if(insType=='1'){ //1-商户
					$('#id_card').hide();
					$('#id_merch').show();
					Selector.selectCardBin('cardBinNo_sel', 'idCardBinNo', false, '', insId);
				}
				
			});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<s:hidden id="id_insType" name="couponAwardReg.insType" />
						<tr>
						<td id="id_card" width="80" height="30" align="right" style="display: none">发卡机构</td>
						<td id="id_merch" width="80" height="30" align="right" style="display: none">商户</td>
						<td id="id_branch_2">
							<s:hidden id="id_insId" name="couponAwardReg.insId" ></s:hidden>
							<s:textfield name="couponAwardReg.insName" cssClass="readonly" readonly="true"></s:textfield>
						</td>
						<td>
						<tr>
							<td width="80" height="30" align="right">卡BIN</td>
							<td>
								<s:hidden id="idCardBinNo" name="cardBins"/>
								<s:textfield id="cardBinNo_sel" name="cardBinNo_sel" cssClass="{required:true}"/>
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">赠券类型</td>
							<td>
								<s:select id="Id_couponClass" name="couponAwardReg.couponClass" list="couponClassDefList" headerKey="" 
								headerValue="--请选择--" listKey="coupnClass" listValue="className" cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择赠券类型</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/couponAwardReg/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_COUPON_AWARD_REG_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>