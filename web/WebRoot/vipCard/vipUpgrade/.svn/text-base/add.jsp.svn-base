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
		<!-- 
		<f:js src="/js/biz/vipCard/membUpgrade.js"/> -->

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#id_cardId').blur(function(){
				var cardId = $('#id_cardId').val();
				if(isEmpty(cardId)){
					$('#id_oldLevel_td1').hide();
					$('#id_oldLevel_td2').hide();
					$('#id_oldLevel').val('');
					$('#id_membName_Tr').hide();
					$('#id_membUpgrade_Tr').hide();
					return;
				}
				if (cardId == null || cardId == undefined || cardId == ""){
					$('#id_oldLevel_td1').hide();
					$('#id_oldLevel_td2').hide();
					$('#id_membName_Tr').hide();
					$('#id_membUpgrade_Tr').hide();
					$('#id_oldLevel').val('');
					return;
				}
				
				$.post(CONTEXT_PATH + '/vipCard/vipUpgrade/cardIdCheck.do', {'renewCardReg.cardId': cardId, 'callId':callId()}, 
				function(json){
					if (json.success){
						$('#id_level_Tr').show();
						$('#id_oldLevel_td1').show();
						$('#id_oldLevel_td2').show();
						$('#id_oldLevel').val(json.oldCardLevel);
						// 显示会员类型、级别数
						$('#id_membName_Tr').show();
						$('#id_membClass_td1').show();
						$('#id_membClass_td2').show();
						$('#id_membClass').val(json.className);
						$('#id_membName_td1').show();
						$('#id_membName_td2').show();
						$('#id_membName').val(json.membClassName);
						// 显示会员升级方式、会员升级阈值
						$('#id_membUpgrade_Tr').show();
						$('#id_UpgradeMthd_td1').show();
						$('#id_UpgradeMthd_td2').show();
						$('#id_UpgradeMthd').val(json.upgradeMthd);
						$('#id_UpgradeThrhd_td1').show();
						$('#id_UpgradeThrhd_td2').show();
						$('#id_UpgradeThrhd').val(json.upgradeThrhd);
						$('#input_btn2').removeAttr('disabled');
						hideMsg();
					} else {
						showMsg(json.error);
						$('#id_oldLevel_td1').hide();
						$('#id_oldLevel_td2').hide();
						$('#id_oldLevel').val('');
						$('#id_membName_Tr').hide();
						$('#id_membUpgrade_Tr').hide();
						$('#input_btn2').attr('disabled', 'true');
					}
				}, 'json');
			});
		
			$('#id_newCardId').blur(function(){
				var newCardId = $('#id_newCardId').val();
				var cardId = $('#id_cardId').val();
				if(isEmpty(cardId)){
					showMsg("请先输入旧卡号。");
					$('#id_newCardId').val('');
					$('#id_newLevel_td1').hide();
					$('#id_newLevel_td2').hide();
					return;
				}
				if(cardId == null || cardId == undefined || cardId == ""){
					//$('#id_newCardId').val('');
					$('#id_newLevel_td1').hide();
					$('#id_newLevel_td2').hide();
					$('#id_newLevel').val('');
					return;
				}
				$.post(CONTEXT_PATH + '/vipCard/vipUpgrade/newCardIdCheck.do', {'renewCardReg.newCardId':newCardId, 'renewCardReg.cardId':cardId, 'callId':callId()}, 
				function(json){
					if (json.success){
						$('#id_level_Tr').show();
						$('#id_newLevel_td1').show();
						$('#id_newLevel_td2').show();
						$('#id_newLevel').val(json.newCardLevel);
						$(':submit').removeAttr('disabled');
						hideMsg();
					} else {
						showMsg(json.error);
						$('#id_newLevel_td1').hide();
						$('#id_newLevel_td2').hide();
						$('#id_newLevel').val('');
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
							<td width="80" height="30" align="right">旧卡号</td>
							<td>
								<s:textfield id="id_cardId" name="renewCardReg.cardId" cssClass="{required:true, digit:true}" maxlength="19"/>
							</td>
							<td width="80" height="30" align="right">新卡号</td>
							<td>
							<s:textfield id="id_newCardId" name="renewCardReg.newCardId" cssClass="{required:true, digit:true}" maxlength="19"></s:textfield>
							</td>
						</tr>
						<tr id="id_level_Tr" style="display: none;">
							<td id="id_oldLevel_td1" style="display: none;" width="80" height="30" align="right">旧卡会员级别</td>
							<td id="id_oldLevel_td2" style="display: none;">
							<s:textfield id="id_oldLevel" cssClass="readonly" readonly="true"></s:textfield></td>
							<td id="id_newLevel_td1" style="display: none;" width="80" height="30" align="right">新卡会员级别</td>
							<td id="id_newLevel_td2" style="display: none;">
							<s:textfield id="id_newLevel" cssClass="readonly" readonly="true"></s:textfield></td>
						</tr>
						<tr id="id_membName_Tr" style="display: none;">
							<td id="id_membClass_td1" style="display: none;" width="80" height="30" align="right">会员类型</td>
							<td id="id_membClass_td2" style="display: none;">
							<s:textfield id="id_membClass" cssClass="readonly" readonly="true"></s:textfield></td>
							<td id="id_membName_td1" style="display: none;" width="80" height="30" align="right">级别名称</td>
							<td id="id_membName_td2" style="display: none;">
							<s:textfield id="id_membName" cssClass="readonly" readonly="true"></s:textfield></td>
						</tr>
						<tr id="id_membUpgrade_Tr" style="display: none;">
							<td id="id_UpgradeMthd_td1" style="display: none;" width="80" height="30" align="right">会员升级方式</td>
							<td id="id_UpgradeMthd_td2" style="display: none;">
							<s:textfield id="id_UpgradeMthd" cssClass="readonly" readonly="true"></s:textfield></td>
							<td id="id_UpgradeThrhd_td1" style="display: none;" width="80" height="30" align="right">级别升级阈值</td>
							<td id="id_UpgradeThrhd_td2" style="display: none;">
							<s:textfield id="id_UpgradeThrhd" cssClass="readonly" readonly="true"></s:textfield></td>
						</tr>
						
						<tr>
						</tr>
						<tr>
							<td width="80" height="30" align="right">持卡人姓名</td>
							<td>
							<s:textfield name="renewCardReg.custName" ></s:textfield>
								<span class="field_tipinfo">请输入姓名</span>
							</td>
							<td width="80" height="30" align="right">证件类型</td>
							<td>
								<s:select id="idCertType" name="renewCardReg.certType" list="certTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
								<span class="field_tipinfo">请选择类型</span>
							</td>
						</tr>
						<tr>	
							<td width="80" height="30" align="right">证件号码</td>
							<td><s:textfield name="renewCardReg.certNo" />
								<span class="field_tipinfo">请输入号码</span>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
							<s:textfield name="renewCardReg.remark" />
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/vipCard/vipUpgrade/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_VIPUPGRADE_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>