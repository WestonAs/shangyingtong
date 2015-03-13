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
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>	

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#id_cardId').focus();
			$('#id_cardId').change(function(){cardIdChange();});
		});
		
		function cardIdChange(){
			try {
				// 卡号
				var cardId = $('#id_cardId').val();
				if (!checkCardNum(cardId)){
					showMsg("请输入至少18位的数字");
					return;
				}
				if(isEmpty(cardId) || !validator.isDigit(cardId)){
					return;
				}
				
				hideMsg();
				// 加载其他信息
				$.post(CONTEXT_PATH + '/vipCard/membLevelChgReg/searchMembInfo.do', {'cardId':cardId, 'callId':callId()}, 
				  function(data){
					var resultMessage = data.resultMessage;
					if(null != resultMessage && undefined != resultMessage && resultMessage.length > 0){
						showMsg(resultMessage);
					} else {
				      $("#id_origLevel").val(data.membLevel);
			          $("#id_mtClass").val(data.membClass);
					  loadMtClassName();
				      hideMsg();
					}
				}, 'json');
			} catch (e){}
		}
		
		function loadMtClassName(){
			var mtClass =  $('#id_mtClass').val();
			$("#id_curLevel").load(CONTEXT_PATH + "/vipCard/membInfoReg/loadMtClassName.do",{'membClass':mtClass, 'callId':callId()});
		}
		
    </script>
	</head>
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<div id="loadingBarDiv"	style="display: none; width: 100%; height: 100%">
			<table width=100% height=100%>
				<tr>
					<td align=center >
						<center>
							<img src="${CONTEXT_PATH}/images/ajax_saving.gif" align="middle">
							<div id="processInfoDiv"
								style="font-size: 15px; font-weight: bold">
								正在处理中，请稍候...
							</div>
							<br>
							<br>
							<br>
						</center>
					</td>
				</tr>
			</table>
		</div>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">卡号</td>
			                <td><s:textfield id ="id_cardId" name="membLevelChgReg.cardId" cssClass="{required:true}"/><span class="field_tipinfo">请输入卡号</span></td>
			                <td></td>
			                <td></td>
						</tr>
						<tr>
						    <td width="80" height="30" align="right">会员类型</td>
							<td>
								<s:textfield id ="id_mtClass" name="membLevelChgReg.membClass" cssClass="{required:true} readonly" readonly="true" ></s:textfield>
								<span class="field_tipinfo">请输入会员类型</span>
							</td>
						    <td width="80" height="30" align="right">会员级别</td> 
							<td>
								<s:textfield id ="id_origLevel" name="membLevelChgReg.origLevel"  cssClass="{required:true} readonly" readonly="true" ></s:textfield>
								<span class="field_tipinfo">请输入会员级别</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">会员新级别</td>
							<td>
							  <select id="id_curLevel" name="membLevelChgReg.curLevel" list="levelList" class="{required:true}"></select>
							  <span class="field_tipinfo">请选择会员新级别</span></td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="membLevelChgReg.remark"/>
								<span class="field_tipinfo">请输入备注</span>
						  </td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/vipCard/membLevelChgReg/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MEMBLEVELCHGREG_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>