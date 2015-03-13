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
		<f:js src="/js/validate.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#id_protclList').change(loadCardIssuer);
			}
		});

		function loadProtclList(){
			var branchCode = $('#id_branchCode').val();
			if(isEmpty(branchCode)){
				return;
			}
			$('#id_protclList').load(CONTEXT_PATH + '/greatDiscount/greatDiscntProtclCentFee/loadProtclList.do',{'callId':callId(), 'cardIssuer':branchCode},
					function(){this.val(${greatDiscntProtclCentFee.greatDiscntProtclNo});});
		}

		function loadCardIssuer(){
			var protclId = $('#id_protclList').val();
			if(isEmpty(protclId)){
				return;
			}
			$.post(CONTEXT_PATH + '/greatDiscount/greatDiscntProtclCentFee/loadCardIssuer.do', {'protclId':protclId, 'callId':callId()}, 
				    function(data){
				        if(data.success){
				            $('#id_cardIssuer').val(data.cardIssuer);
				            $('#input_btn2').removeAttr('disabled');
				        } else {
				            showMsg(data.errorMsg);
				            $('#id_cardIssuer').val('');
				            $('#input_btn2').attr('disabled', 'true');
				        }
				    }, 'json');
		}

		function getExpirDate() {
			WdatePicker({minDate:'#F{$dp.$D(\'id_effDate\') || \'%y-%M-%d\'}'});
		}
		function getEffDate() {
			WdatePicker({minDate:'%y-%M-%d', maxDate:'#F{$dp.$D(\'id_expirDate\')}'});
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>

		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
						    <td width="80" height="30" align="right">运营机构</td>
							<td >
								<s:hidden id="id_branchCode" name="greatDiscntProtclCentFee.chlCode" cssClass="{required:true} readonly" readonly="true"/>
								<s:textfield id="id_branchCodeName" name="cardBranchName"  cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">请输入运营机构</span>
							</td>
							<td width="80" height="30" align="right">折扣协议</td>
							<td >
							<s:hidden id="id_protclName" name="greatDiscntProtclCentFee.greatDiscntProtclName" cssClass="{required:true} readonly" readonly="true"/>
							<s:textfield id="id_protclNo" name="greatDiscntProtclCentFee.greatDiscntProtclNo"  cssClass="{required:true} readonly" readonly="true"/>
<!-- 							    <s:select name="greatDiscntProtclCentFee.greatDiscntProtclNo" list="protclList" headerKey="" headerValue="--请选择--" listKey="greatDiscntProtclId" listValue="greatDiscntProtclName"></s:select> -->
<!-- 							    <select id="id_protclList" name="greatDiscntProtclCentFee.greatDiscntProtclNo" style="width: auto;" class="{required:true}"> -->
<!-- 								    <option value="">--请选择--</option> -->
<!-- 							    </select> -->
								<span class="field_tipinfo">请输入折扣协议</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">联名发卡机构</td>
							<td >
								<s:textfield id="id_cardIssuer" name="greatDiscntProtclCentFee.cardIssuer"  cssClass="readonly" readonly="true"></s:textfield>
								<span class="field_tipinfo">请输入联名发卡机构</span>
							</td>
							<td width="80" height="30" align="right">运营中心收益</td>
							<td >
								<s:textfield name="greatDiscntProtclCentFee.centIncomeFee" cssClass="{required:true, digit:true,min:1, max:99}"></s:textfield>
								<span class="field_tipinfo">请输入1至99的整数(百分比)</span>
							</td>
						</tr>
						<tr>
						    <td width="80" height="30" align="right">插入时间</td>
							<td >
								<s:textfield name="greatDiscntProtclCentFee.insertTime"  cssClass="readonly" readonly="true"><s:param name="value"><s:date name="greatDiscntProtclCentFee.insertTime" format="yyyy-MM-dd HH:mm:ss" /></s:param></s:textfield>
								<span class="field_tipinfo">请输入插入时间</span>
							</td>
							<td width="80" height="30" align="right">更新时间</td>
							<td >
								<s:textfield name="greatDiscntProtclCentFee.updateTime"  cssClass="readonly" readonly="true"><s:param name="value"><s:date name="greatDiscntProtclCentFee.updateTime" format="yyyy-MM-dd HH:mm:ss" /></s:param></s:textfield>
								<span class="field_tipinfo">请输入更新时间</span>
							</td>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td >
								<s:textfield name="greatDiscntProtclCentFee.remark" ></s:textfield>
								<span class="field_tipinfo">请输入备注</span>
							</td>
                            <td></td>
                            <td></td>
						</tr>

						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="申请" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/greatDiscount/greatDiscntProtclCentFee/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_GREATDISCNTPROTCLCENTFEE_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>