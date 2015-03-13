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
			
			//选择会员
			var cardIssuer = $('#id_cardIssuer').val();
			Selector.selectMemb('id_membInfoNames', 'id_membInfoIds', false, cardIssuer);
			
			//开始卡号
			$('#id_startCard').focus();
			$('#id_startCard').change(findCardInfo);
			$('#id_cardCount').change(findCardInfo);
			
			$('#id_membImportType').change(membImportTypeChange);
		});
		
		function membImportTypeChange(){
			var membImportType = $('#id_membImportType').val();
			if(membImportType == '01'){
				$('#membInfoId_td_1').hide();
				$('#membInfoId_td_2').hide();
				$('#id_membInfoId').attr('disabled', 'true');
				
				$('#membInfoId_td_3').show();
				$('#membInfoId_td_4').show();
				$('#id_membInfoIds').removeAttr('disabled');
				$('#id_membInfoNames').removeAttr('disabled');
			}else if(membImportType == '02'){
				$('#membInfoId_td_1').show();
				$('#membInfoId_td_2').show();
				$('#id_membInfoId').removeAttr('disabled');
				
				$('#membInfoId_td_3').hide();
				$('#membInfoId_td_4').hide();
				$('#id_membInfoIds').attr('disabled', 'true');
				$('#id_membInfoNames').attr('disabled', 'true');
			}
		}
		
		function findCardInfo(){
			try {
				// 起始卡号
				var cardId = $('#id_startCard').val();
				// 卡数量
				var cardCount = $('#id_cardCount').val();
				if (!checkCardNum(cardId)){
					showMsg("请输入至少18位的数字");
					return;
				}
				if(isEmpty(cardCount) || !validator.isDigit(cardCount)){
					return;
				}
				
				hideMsg();
				// 加载其他信息
				$.post(CONTEXT_PATH + '/vipCard/membAppointReg/searchCardInfo.do', {'cardId':cardId, 'cardCount':cardCount, 'callId':callId()}, 
				  function(data){
					$("#id_endCard").val(data.resultEndCardId);
					var resultMessage = data.resultMessage;
					if(resultMessage.length > 0){
						showMsg(resultMessage);
					} else {
						hideMsg();
					}
				}, 'json');
			} catch (e){}
		}
		
	</script>
		
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<div id="loadingBarDiv"	style="display: none; width: 100%; height: 100%">
			<table style="height: 100%; width: 100%;">
				<tr>
					<td align=center >
						<center>
							<img src="${CONTEXT_PATH}/images/ajax_saving.gif" align="middle" />
							<div id="processInfoDiv" 
								style="font-size: 15px; font-weight: bold;">
								正在处理中，请稍候...
							</div>
							<br />
							<br />
							<br />
						</center>
					</td>
				</tr>
			</table>
		</div>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="add.do" id="inputForm" cssClass="validate">
			    <s:hidden id="id_cardIssuer" name="cardIssuer" /> 
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<caption>${ACT.name}</caption>
					<tr>
						<td width="80" height="30" align="right">开始卡号</td>
						<td> 
							<s:textfield id="id_startCard" name="membAppointReg.startCardId" cssClass="{required:true}"/>
							<span class="field_tipinfo">请输入开始卡号</span>
						</td>
						<td width="80" height="30" align="right">卡连续数</td>
						<td> 
							<s:textfield id="id_cardCount" name="cardCount" cssClass="{required:true, digit:true}"/>
							<span class="field_tipinfo">请输入整数</span>
						</td>
					</tr>
					<tr>
					    <td width="80" height="30" align="right">结束卡号</td>
						<td> 
							<s:textfield id="id_endCard" name="membAppointReg.endCardId" cssClass="required:true readonly" readonly="true"/>
							<span class="field_tipinfo">请输入结束卡号</span>
						</td>
						<td width="80" height="30" align="right">会员录入方式</td>
						<td>
							<s:select id="id_membImportType" name="importType" list="membImportTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}" ></s:select>
						    <span class="field_tipinfo">请选择会员录入方式</span>
						</td>
					</tr>
					<tr >
						<td width="80" height="30" align="right">备注</td> 
						<td>
							<s:textfield name="membAppointReg.remark" ></s:textfield>
						</td>
						<td id="membInfoId_td_1"  width="80" height="30" align="right" style="display: none;">会员资料登记批次ID</td>
						<td id="membInfoId_td_2" style="display: none;" >
							<s:select id="id_membInfoId" name="membInfoReg.membInfoId"  disabled="true" list="membInfoIdList" headerKey="" headerValue="--请选择--" listKey="membInfoId" listValue="membInfoId" cssClass="{required:true}" ></s:select>
						    <span class="field_tipinfo">请选择会员批次ID</span>
						</td>
						<td id="membInfoId_td_3"  width="80" height="30" align="right" style="display: none;">会员资料登记ID</td>
						<td id="membInfoId_td_4" style="display: none;">
						    <s:hidden id="id_membInfoIds" name="membInfoIds"  disabled="true"></s:hidden>
							<s:textfield id="id_membInfoNames" name="membInfoNames"  disabled="true" cssClass="{required:true}" />
						    <span class="field_tipinfo">请选择会员资料</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30" colspan="3">
							<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return submitForm()"/>
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/vipCard/membAppointReg/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_MEMBAPPOINT_ADD"/>
			</s:form>
			<span class="note_div">会员资料登记说明</span>
			<ul class="showli_div">
				<li class="showli_div">若指定批次录入,则会员资料登记批次的会员数必须与卡连续数一样</li>
				<li class="showli_div">若指定会员录入,卡连续数必须为1</li>
			</ul>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>