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
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/validate.js"/>
		<f:js src="/js/date/WdatePicker.js" />
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		function queryClass(){
			var trailType = $('#id_trailType').val(); 
			var issId = $('#id_issId').val(); 
			//全部或者资金账户
			if(trailType=='00'||trailType=='01'){
				$('#tr_classId').hide();
				$('#td_classId_1').hide();
				$('#td_classId_2').hide();
				$('#id_classId').attr('disabled',true);
			}
			//其他
			else{
				$('#id_classId').removeAttr('disabled');
				$('#tr_classId').show();
				$('#td_classId_1').show();
				$('#td_classId_2').show();
				
				//次卡账户
				if(trailType=='02'){
					$("#id_classId").load(CONTEXT_PATH + "/trailBalance/trailBalanceReg/accuClassList.do",{'trailBalanceReg.trailType':trailType, 'trailBalanceReg.issId':issId, 'callId':callId()});
				}
				//赠券账户
				else if(trailType=='03'){
					$("#id_classId").load(CONTEXT_PATH + "/trailBalance/trailBalanceReg/couponClassList.do",{'trailBalanceReg.trailType':trailType, 'trailBalanceReg.issId':issId, 'callId':callId()});
				}
				//积分账户
				else if(trailType=='04'){
					$("#id_classId").load(CONTEXT_PATH + "/trailBalance/trailBalanceReg/pointClassList.do",{'trailBalanceReg.trailType':trailType, 'trailBalanceReg.issId':issId, 'callId':callId()});
				}
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
					<s:hidden id="id_showCenter" name="showCenter"></s:hidden>
					<s:hidden id="id_showCard" name="showCard"></s:hidden>
					<s:hidden id="id_showMerch" name="showMerch"></s:hidden>
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<s:if test="showCenter">
					<td width="80" height="30" align="right">发卡机构</td>
						<td>
						<s:hidden id="id_cardIssuer" name="trailBalanceReg.issId" disabled="true"></s:hidden>
						<s:textfield  id="branchName" cssClass="{required:true}" disabled="true"></s:textfield>
						<span class="field_tipinfo">请选择发卡机构</span>
						</td>
					</s:if>
					<s:else>
					<td width="80" height="30" align="right">发行机构</td>
					<td>
						<s:textfield name="trailBalanceReg.issId" id="id_issId" cssClass="readonly" readonly="true"/>
					</td>
					</s:else>
					</tr>
						<td width="80" height="30" align="right">试算日期</td>
						<td>
							<s:textfield name="trailBalanceReg.settDate" cssClass="{required:true}" onclick="WdatePicker()"></s:textfield>
						</td>
					<tr>
					<tr>
						<td width="80" height="30" align="right">试算类型</td>
						<td>
						<s:select name="trailBalanceReg.trailType" id="id_trailType" cssClass="{required:true}" onchange="queryClass();" list="trailTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"/>
						<span class="field_tipinfo"></span>
						</td>
					</tr>
					<tr id="tr_classId" style="display:none">
						<td id="td_classId_1" width="80" height="30" align="right" style="display:none">子类型</td>
						<td id="td_classId_2" style="display:none">
						<select name="trailBalanceReg.classId" id="id_classId" class="{required:true}" disabled="true"></select>									
						<span class="field_tipinfo">请先选择试算类型</span>
						</td>
					</tr>
					<tr style="margin-top: 30px;">
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30" colspan="3">
							<input type="submit" value="提交" id="input_btn2"  name="ok" />
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/trailBalance/trailBalanceReg/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
					</table>
					<s:token name="_TOKEN_TRAILBALANCEREG_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>