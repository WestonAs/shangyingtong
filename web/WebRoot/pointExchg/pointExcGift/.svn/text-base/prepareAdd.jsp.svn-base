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
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			function loadPtClassDefList(){
				var cardId = $('#cardId').val();
				if(!checkCardId(cardId)){
					return;
				}
				var ptClass = $('#ptClass').val();
				$('#ptClassDef_div').show().html(LOAD_IMAGE).load(CONTEXT_PATH+'/pointExchg/pointExcGift/ptClassList.do?',{'giftExcReg.cardId':cardId, 'giftExcReg.ptClass':ptClass},function() {
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
				<s:form action="ptClassList.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
							<s:textfield id="cardId" name="giftExcReg.cardId" cssClass="digit:true, minlength:19" maxlength="19"></s:textfield>
							<span class="field_tipinfo" id="">请输入19位卡号</span>
							</td>
						</tr>	
						<!--<tr>
							<td width="80" height="30" align="right">积分类型</td>
							<td>
								<s:select id="ptClass" name="giftExcReg.ptClass" list="ptClassList" listKey="ptClass" listValue="className" headerKey="" headerValue="--请选择--"></s:select>							
							</td>
						</tr>	
						--><tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="查询" name="escape" onclick="loadPtClassDefList()"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pointBus/pointChange/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
				</s:form>
			</div>
			<div id="ptClassDef_div">
				<jsp:include flush="true" page="/pointExchg/pointExcGift/ptClassDefList.jsp"></jsp:include>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>