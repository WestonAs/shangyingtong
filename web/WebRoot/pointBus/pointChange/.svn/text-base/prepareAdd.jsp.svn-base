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
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<f:js src="/js/biz/gift/addGift.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			
			function loadPointBalList(){
				var cardId = $('#cardId').val();
				if(cardId == null || cardId == undefined || cardId == ""){
					return;
				}
				$.post(CONTEXT_PATH + '/pointBus/pointChange/validateCardId.do', {'pointChgReg.cardId':cardId, 'callId':callId()},
				function(json){
					if (json.success){
						//$('#idButton').removeAttr('disabled');
					} else {
						showMsg(json.error);
						//$('#idButton').attr('disabled', 'true');
					}
				}, 'json');
				
				var ptClass = $('#ptClass').val();
				$('#pointBal_div').show().html(LOAD_IMAGE).load(CONTEXT_PATH+'/pointBus/pointChange/pointBalList.do?',{'pointChgReg.cardId':cardId, 'pointChgReg.ptClass':ptClass},function() {
					SysStyle.setDataGridStyle();
				});
			}
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="pointBalList.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
							<s:textfield id="cardId" name="pointChgReg.cardId" 
							onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
							 cssClass="{required:true}"  maxlength="19" />
							<span class="field_tipinfo" >请输入卡号</span>
							</td>
						</tr>	
						<!--<tr>
						</tr>	
						--><tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" id="idButton" value="查询" name="escape" onclick="loadPointBalList()" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pointBus/pointChange/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
				</s:form>
			</div>
			<div id="pointBal_div">
				<jsp:include flush="true" page="/pointBus/pointChange/pointBalList.jsp"></jsp:include>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>