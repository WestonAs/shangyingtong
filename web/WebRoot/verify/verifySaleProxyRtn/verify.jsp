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
		
		<script>
			$(function(){
				$(':radio[name="verifyType"]').click(function(){
					var value = $(this).val();
					var remain = $('#idRemain').val();
					if(value == '1'){
						$('#id_recvAmt').val(remain).attr('readonly', 'true').addClass('readonly');
					} else {
						$('#id_recvAmt').val('').removeAttr('readonly').removeClass('readonly');
					}
				});
				$('#id_recvAmt').blur(function(){
					var value = $(this).val();
					var remain = $('#idRemain').val();
					if(isEmpty(value)){
						return false;
					}
					if(parseFloat(value) > parseFloat(remain)){
						$('#idForAddAdminDiff').click();
					}
					if(parseFloat(value) = parseFloat(remain)){
						$('#idForAddAdminEqul').click();
					}
				});
			});
			function check(){
				var type = FormUtils.getRadioedValue('verifyType');
				if(isEmpty(type)){
					showMsg('请选择核销类型');
					return false;
				}
				return true;
			}
		</script>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="verify.do" id="inputForm" cssClass="validate">
				<s:hidden name="mset.proxyId" />
				<s:hidden name="mset.orgId" />
				<s:hidden name="mset.feeMonth" />
				<s:hidden name="mset.curCode" />
				<s:hidden id="hidden_lastFee" name="mset.lastFee"/>
				<s:hidden id="hidden_feeAmt" name="mset.feeAmt"/>
				<table class="form_grid detail_tbl" width="100%" border="0" cellspacing="3" cellpadding="0">
					<caption>${ACT.name}</caption>
					<tr>
						<td width="80" height="30" align="right">售卡代理商</td>
						<td>
							${mset.proxyName}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">上期结转金额</td>
						<td>
							${fn:amount(mset.lastFee)}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">本期返利金额</td>
						<td>
							${fn:amount(mset.feeAmt)}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">剩余核销金额</td>
						<td>
							${fn:amount(remainfeeAmt)}
							<s:hidden id="idRemain" name="remainfeeAmt" />
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">核销类型</td>
						<td>
							<input type="radio" name="verifyType" value="1" id="idForAddAdminEqul"/><label for="idForAddAdminEqul">等额核销</label>
							<input type="radio" name="verifyType" value="2" id="idForAddAdminDiff"/><label for="idForAddAdminDiff">差额核销</label>
							<input type="radio" name="verifyType" value="3" id="idForAddAdminPart"/><label for="idForAddAdminPart">部分核销</label>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">本次核销金额</td>
						<td>
							<s:textfield name="mset.recvAmt" id="id_recvAmt" cssClass="{required:true}"></s:textfield>
							<span class="field_tipinfo">请输入核销金额</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30">
							<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return check();" />
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/verify/verifySaleProxyRtn/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_VERIFY_SALE_PROXY_RTN_VERIFY"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>