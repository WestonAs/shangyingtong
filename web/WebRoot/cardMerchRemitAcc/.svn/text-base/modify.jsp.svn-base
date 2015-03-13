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
		<f:js src="/js/date/WdatePicker.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			bind();
		});
		
		function bind(){
			var xferType = $('#id_xferType').val(); 
			
			//01，指定月日期划账
			//02，指定周日期划账
			//03，满额度划账
			//04，指定月日期或满额度划账
			//05，指定周日期或满额度划账
			if(xferType=='01'||xferType=='02'){
				$('#tr_ulMoney').hide();
				$('#td_ulMoney_1').hide();
				$('#td_ulMoney_2').hide();
				$('#id_ulMoney').attr('disabled',true);
				
				if(xferType=='01'){
					$('#tr_dayOfCycle_w').hide();
					$('#td_dayOfCycle_w_1').hide();
					$('#td_dayOfCycle_w_2').hide();
					$('#id_dayOfCycle_w').attr('disabled',true);
					
					$('#id_dayOfCycle_m').removeAttr('disabled');
					$('#tr_dayOfCycle_m').show();
					$('#td_dayOfCycle_m_1').show();
					$('#td_dayOfCycle_m_2').show();
				} 
				else{
					$('#tr_dayOfCycle_m').hide();
					$('#td_dayOfCycle_m_1').hide();
					$('#td_dayOfCycle_m_2').hide();
					$('#id_dayOfCycle_m').attr('disabled',true);
					
					$('#id_dayOfCycle_w').removeAttr('disabled');
					$('#tr_dayOfCycle_w').show();
					$('#td_dayOfCycle_w_1').show();
					$('#td_dayOfCycle_w_2').show();
				}
			}
			else if(xferType=='03'){
				$('#id_ulMoney').removeAttr('disabled');
				$('#tr_ulMoney').show();
				$('#td_ulMoney_1').show();
				$('#td_ulMoney_2').show();
				
				$('#tr_dayOfCycle_m').hide();
				$('#td_dayOfCycle_m_1').hide();
				$('#td_dayOfCycle_m_2').hide();
				$('#id_dayOfCycle_m').attr('disabled',true);
				
				$('#id_dayOfCycle_w').attr('disabled',true);
				$('#tr_dayOfCycle_w').hide();
				$('#td_dayOfCycle_w_1').hide();
				$('#td_dayOfCycle_w_2').hide();
			}
			else if(xferType=='04'||xferType=='05'){
				$('#id_ulMoney').removeAttr('disabled');
				$('#tr_ulMoney').show();
				$('#td_ulMoney_1').show();
				$('#td_ulMoney_2').show();
				
				if(xferType=='04'){
					$('#tr_dayOfCycle_w').hide();
					$('#td_dayOfCycle_w_1').hide();
					$('#td_dayOfCycle_w_2').hide();
					$('#id_dayOfCycle_w').attr('disabled',true);
					
					$('#id_dayOfCycle_m').removeAttr('disabled');
					$('#tr_dayOfCycle_m').show();
					$('#td_dayOfCycle_m_1').show();
					$('#td_dayOfCycle_m_2').show();
				}
				else{
					$('#tr_dayOfCycle_m').hide();
					$('#td_dayOfCycle_m_1').hide();
					$('#td_dayOfCycle_m_2').hide();
					$('#id_dayOfCycle_m').attr('disabled',true);
					
					$('#id_dayOfCycle_w').removeAttr('disabled');
					$('#tr_dayOfCycle_w').show();
					$('#td_dayOfCycle_w_1').show();
					$('#td_dayOfCycle_w_2').show();
				}
			}
			else {
				
				$('#tr_ulMoney').hide();
				$('#td_ulMoney_1').hide();
				$('#td_ulMoney_2').hide();
				$('#id_ulMoney').attr('disabled',true);
				
				$('#tr_dayOfCycle_m').hide();
				$('#td_dayOfCycle_m_1').hide();
				$('#td_dayOfCycle_m_2').hide();
				$('#id_dayOfCycle_m').attr('disabled',true);
				
				$('#tr_dayOfCycle_w').hide();
				$('#td_dayOfCycle_w_1').hide();
				$('#td_dayOfCycle_w_2').hide();
				$('#id_dayOfCycle_w').attr('disabled',true);
			}
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<caption>${ACT.name}</caption>
					<tr>
					<td width="80" height="30" align="right">发卡机构</td>
					<td>
						<s:textfield name="cardMerchRemitAccount.branchCode" id="id_branchCode" cssClass="readonly" readonly="true"/>
					</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">商户</td>
						<td> 
							<s:textfield id="id_merchId" name="cardMerchRemitAccount.merchId" cssClass="readonly" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">划账方式</td>
						<td>
							<s:select id="id_xferType" name="cardMerchRemitAccount.xferType" onchange="bind();" list="xferTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择划账方式</span>
						</td>
					</tr>
					<tr id="tr_ulMoney" style="display: none">
						<td id="td_ulMoney_1" style="display: none" width="80" height="30" align="right">金额上限参数</td>
						<td id="td_ulMoney_2" style="display: none">
						<s:textfield name="cardMerchRemitAccount.ulMoney" id="id_ulMoney" cssClass="{required:true, decimal:'14,2'}" disabled="true" />
						<span class="field_tipinfo">最大输入12位整数，2位小数</span>
						</td>
					</tr>
					<tr id="tr_dayOfCycle_m" style="display: none">
						<td id="td_dayOfCycle_m_1" style="display: none" width="80" height="30" align="right">指定月日期</td>
						<td id="td_dayOfCycle_m_2" style="display: none">
						<s:select name="cardMerchRemitAccount.dayOfCycle" list="dayOfMonthTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" id="id_dayOfCycle_m" cssClass="{required:true}" disabled="true"></s:select>
						<span class="field_tipinfo"></span>
						</td>
					</tr>
					<tr id="tr_dayOfCycle_w" style="display: none">
						<td id="td_dayOfCycle_w_1" style="display: none" width="80" height="30" align="right">指定周日期</td>
						<td id="td_dayOfCycle_w_2" style="display: none">
						<s:select name="cardMerchRemitAccount.dayOfCycle" list="dayOfWeekTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" id="id_dayOfCycle_w" cssClass="{required:true}" disabled="true"></s:select>
						<span class="field_tipinfo"></span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">生效日期</td>
						<td>
						<s:textfield name="cardMerchRemitAccount.effDate" onclick="WdatePicker({minDate:'%y-%M-%d'})" cssClass="{required:true}" />
						<span class="field_tipinfo"></span>
						<br /></td>
					</tr>
						
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardMerchRemitAcc/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDMERCHREMITACC_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>