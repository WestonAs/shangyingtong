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
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/date/WdatePicker.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#startCardId').blur(function(){
				var startCardId = $('#startCardId').val();
				$.post(CONTEXT_PATH + '/intgratedService/unLossCardBat/checkStartCardId.do', {'unLossCardReg.startCard':startCardId, 'callId':callId()}, 
				function(json){
					if (json.success){
						hideMsg();
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$(':submit').attr('disabled', 'true');
					}
				}, 'json');
			});
			
			$('#endCardId').blur(function(){
				var startCardId = $('#startCardId').val();
				var endCardId = $('#endCardId').val();
				if(isEmpty(startCardId)){
					showMsg("请先输入起始卡号。");
					$('#endCardId').val('');
				}
				if(isEmpty(endCardId)){
					return;
				}
				//起始卡号和结束卡号位数要一致
				if(startCardId.length!=endCardId.length){
					showMsg("起始卡号和结束卡号的位数不一致。");
				}
				$.post(CONTEXT_PATH + '/intgratedService/unLossCardBat/checkCardId.do', {'unLossCardReg.startCard':startCardId, 'unLossCardReg.endCard':endCardId, 'callId':callId()}, 
				function(json){
					if (json.success){
						hideMsg();
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$(':submit').attr('disabled', 'true');
					}
				}, 'json');
			});
			
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
						<tr>
							<td width="80" height="30" align="right">起始卡号</td>
							<td>
							<s:textfield id="startCardId" name="unLossCardReg.startCard" onkeyup="this.value=this.value.replace(/\D/g,'')" 
							onafterpaste="this.value=this.value.replace(/\D/g,'')" cssClass="{required:true}"  maxlength="19" />
							<span class="error_tipinfo">卡号不正确</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">结束卡号</td>
							<td>
							<s:textfield id="endCardId" name="unLossCardReg.endCard" cssClass="{required:true}"  maxlength="19" />
								<span class="field_tipinfo" id=""></span>
								<span class="error_tipinfo">卡号不正确</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
							<s:textfield name="unLossCardReg.remark" />
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/intgratedService/unLossCardBat/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_BAT_UNLOSSCARDREG_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
				<span class="note_div">注释</span>
				<ul class="showli_div">
				<li class="showli_div">只有挂失状态的卡才能解挂。</li>
				<li class="showli_div">批量解挂的卡必须为操作机构发行的卡或者所售的卡。</li>
				<li class="showli_div">起始卡号和结束卡号位数要一致，卡BIN也需一致。</li>
				<li class="showli_div">起始卡号和结束卡号之间不能超过1000张。</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>