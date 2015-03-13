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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/validate.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				$(':text[name="merName"]').each(function(){
					var id = $(this).attr('id');
					var hiddenId = $(this).prev().attr('id');
					Selector.selectMerch(id, hiddenId, true, '${card_BranchNo}');
				});
				$(':text[name="binName"]').each(function(){
					var id = $(this).attr('id');
					var hiddenId = $(this).prev().attr('id');
					Selector.selectCardBin(id, hiddenId, false, '${cardIssuerBin}');
				});
				$(':text[name="subClassName"]').each(function(){
					var id = $(this).attr('id');
					var hiddenId = $(this).prev().attr('id');
					Selector.selectCardSubclass(id, hiddenId, true, '${merchantNo}', '${cardIssuerNo}');
				});
			
				// 卡BIN选择
				Selector.selectCardBin('cardBinScope_sel', 'idCardBinScope', false, '${cardIssuerBin}');
				// 发卡机构的特约商户
				Selector.selectMerch('idpinstSel', 'idpinstId', true, '${card_BranchNo}');
				
				var roleType = '${loginRoleType}';
				// 登录用户角色为商户时
				if(roleType == '40'){
					$('[id^="pinstType_td"]').hide();
					$('#pinstType_id').attr('disabled', 'true');
				} else {
					$('[id^="pinstType_td"]').show();
					$('#pinstType_id').removeAttr('disabled');
				}
				
				$('#pinstType_id').change(function(){
					var value = $(this).val();
					showType(value);
				});
				
				$(':radio[name="addScope"]').click(function(){
					var value = $(this).val();
					showScope(value);
				});
				
				var type = '${promtDef.pinstType}';
				showType(type);
				
				var scope = '${addScope}';
				showScope(scope);
				setTimeout(function(){
					$('[id^="idDetail_"]').find(':text').addClass('u_half');
				}, 100);
			});
			
			function showScope(scope){
				if (scope == 0) {
					$('[id^="idAddScope_"]').hide();
					$('#scopeTypeId').attr('disabled', 'true');
				} else {
					$('[id^="idAddScope_"]').show();
					Selector.selectMerch('idMerNo_Sel_1', 'idMerNo_1', true, '${card_BranchNo}');
					Selector.selectCardBin('idCardBinScope_Sel_1', 'idCardBinScope_1', false, '${cardIssuerBin}');
					Selector.selectCardSubclass('idCardSubclass_Sel_1', 'idCardSubclass_1', true, '${merchantNo}', '${cardIssuerNo}');
					$('#scopeTypeId').removeAttr('disabled');
				}
			}
			
			function showType(type){
				if(type == '1') {
					$('[id^="pinstId_td"]').show();
					$('#idpinstId').removeAttr('disabled');
					$('#idpinstSel').removeAttr('disabled');
				} else {
					$('[id^="pinstId_td"]').hide();
					$('#idpinstId').attr('disabled', 'true');
					$('#idpinstSel').attr('disabled', 'true');
				}
			}
				
			function addItem(){
				var count = $('tr[id^="idDetail_"]').size();
				var merShow = '';
				var role = '${loginRoleType}';
				if(role != '40'){
					merShow = '<td align="center" class="u_half" id="idMerNoTd_'+(count+1) +'">'
										+ '<input type="hidden" id="idMerNo_'+(count+1) +'" name="merNo"/>'
										+ '<input type="text" id="idMerNo_Sel_'+(count+1) +'" name="merNo_Sel" />'
								+ '</td>';
				} 
				var content = '<tr id="idDetail_'+ (count+1) +'">' 
								+ '<td align="center" class="u_half">'+ (count+1) +'</td>' 
								+ merShow
								+ '<td align="center" class="u_half">'
										+ '<input type="hidden" id="idCardBinScope_'+(count+1) +'" name="cardBinScope" />' 
										+ '<input type="text" id="idCardBinScope_Sel_'+(count+1) +'" name="cardBinScope_Sel" />'
								+ '</td>' 
								+ '<td align="center" class="u_half">'
										+ '<input type="hidden" id="idCardSubclass_'+(count+1) +'" name="cardSubclass" />' 
										+ '<input type="text" id="idCardSubclass_Sel_'+(count+1) +'" name="cardSubclass_Sel" />' 
								+ '</td>' 
								+ '<td align="center" class="u_half"><input type="text" name="ptLlimit" value=""/></td>' 
								+ '<td align="center" class="u_half"><input type="text" name="ptUlimit" value=""/></td>' 
								+ '<td align="center" class="u_half"><input type="text" name="membLevel" value=""/></td></tr>';
				$('#idLink').before(content);
				// 卡BIN选择
				Selector.selectCardBin('idCardBinScope_Sel_'+(count+1), 'idCardBinScope_'+(count+1), false, '${cardIssuerBin}');
				// 商户选择
				Selector.selectMerch('idMerNo_Sel_'+(count+1), 'idMerNo_'+(count+1), true, '${card_BranchNo}');
				// 卡子类型选择
				Selector.selectCardSubclass('idCardSubclass_Sel_'+(count+1), 'idCardSubclass_'+(count+1), true, '${merchantNo}', '${cardIssuerNo}');
			
				// 设置样式
				SysStyle.setFormGridStyle();
				$('#idDetail_'+ (count+1)).find(':text').addClass('u_half');
			}
			function deleteItem(){
				var count = $('tr[id^="idDetail_"]').size();
				if (count == 1) {
					showMsg('必须设置一项');
					return;
				}
				$('tr[id^="idDetail_"]:last').remove();
			}
			
			function check(){
				if($('#idAddScope_3').css('display') == 'none'){
					return true;
				}
				var count = $('tr[id^="idDetail_"]').size();
			
				for(i=0; i<count; i++){
					var comm = $('tr[id^="idDetail_"]').eq(i).children();
					var mer = comm.eq(1).children().val();
					var soc = comm.eq(2).children().val();
					var sub = comm.eq(3).children().val();
					var ptL = comm.eq(4).children().val();
					var ptU = comm.eq(5).children().val();
					var lev = comm.eq(6).children().val();
					if(validator.isEmpty(mer) && validator.isEmpty(soc)
						&&validator.isEmpty(sub) && validator.isEmpty(ptL)
						&&validator.isEmpty(ptU) && validator.isEmpty(lev)){
						showMsg("促销活动范围不能全部为空！");
						return false;
					}
					if((validator.isEmpty(ptL)&&(!validator.isEmpty(ptU)))
						||((!validator.isEmpty(ptL))&&validator.isEmpty(ptU))){
						showMsg("积分上限和积分下限必须同为空或同时不为空。");
						return false;
					}
					if(!validator.isEmpty(ptL)&&(!validator.isEmpty(ptU))){
						if(!validator.isDecimal(ptL) || !validator.isDecimal(ptU)){
							showMsg("积分上限和积分下限必须为数值！");
							return false;
						}
						if(ptL >= ptU){
							showMsg("积分下限必须大于积分上限。");
							return false;
						}
					}
					if(!validator.isDigit(lev)){
						showMsg("“会员级别”请输入数字。");
						return false;
					}
				}
				return true;
			}
			
			function submitForm(){
				if(!check()){
					return false;
				}
				$("#inputForm").submit();
			}
			function getEffDateId() {
				WdatePicker({minDate:'%y-%M-%d', maxDate:'#F{$dp.$D(\'expirDateId\')}'});
			}
			function getExpirDateId(){
				WdatePicker({minDate:'#F{$dp.$D(\'effDateId\') || \'%y-%M-%d\'}'});
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<s:hidden id="Id_cardIssuerNo" name="cardIssuerNo" />
					<s:hidden id="Id_merchantNo" name="merchantNo" />
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">协议积分名</td>
							<td>
								<s:hidden name="promtDef.promtId" />
								<s:textfield name="promtDef.promtName" cssClass="{required:true}" maxlength="30"/>
								<span class="field_tipinfo">请输入活动名</span>
							</td>
							
							<td width="80" height="30" align="right"></td>
							<td>
							</td>
						</tr>
						<tr>
							<td id="pinstType_td1" width="80" height="30" align="right" style="display: none;">参与机构类型</td>
							<td id="pinstType_td2" style="display: none;">
								<s:select id="pinstType_id" name="promtDef.pinstType" headerKey="" headerValue="--请选择--" list="issTypeList" listKey="value" listValue="name"
									cssClass="{required:true}" disabled="true"></s:select>
								<span class="field_tipinfo">请选择机构类型</span>
							</td>
							
							<td id="pinstId_td1" width="80" height="30" align="right" style="display: none;">商户</td>
							<td id="pinstId_td2" style="display: none;">
								<s:hidden id="idpinstId" name="promtDef.pinstId" disabled="true"/>
								<s:textfield id="idpinstSel" name="pinstId_sel" cssClass="{required:true}" disabled="true"/>
								<span class="field_tipinfo">请选择商户</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">交易类型</td>
							<td>
								<s:select name="promtDef.transType" list="transTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"
									cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择交易类型</span>
							</td>
							
							<td width="80" height="30" align="right">卡BIN范围</td>
							<td>
								<s:hidden id="idCardBinScope" name="promtDef.cardBinScope" />
								<s:textfield id="cardBinScope_sel" name="cardBinScope_sel" />
								<span class="field_tipinfo">请选择卡BIN</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">生效日期</td>
							<td>
								<s:textfield name="promtDef.effDate" id="effDateId" onclick="getEffDateId();"
									cssClass="{required:true}" />
								<span class="field_tipinfo">请输入生效日期</span>
							</td>
							
							<td width="80" height="30" align="right">失效日期</td>
							<td>
								<s:textfield name="promtDef.expirDate" id="expirDateId" onclick="getExpirDateId();" 
									cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入失效日期</span>
							</td>
						</tr>
						
						<tr>
							<td colspan="2" height="30" align="center">是否有附加范围?
								<input type="radio" name="addScope" value="1" id="idForAddScopeYes" <s:if test="addScope == 1">checked</s:if> /><label for="idForAddScopeYes">是</label>
								<input type="radio" name="addScope" value="0" id="idForAddScopeNo" <s:if test="addScope == 0">checked</s:if>/><label for="idForAddScopeNo">否</label>
							</td>
							<td id="idAddScope_1" width="80" height="30" align="right" style="display: none;">范围类型</td>
							<td id="idAddScope_2" style="display: none;">
								<s:select id="scopeTypeId" name="scopeType" list="scopeTypeList" listKey="value" listValue="name">
								</s:select>
							</td>
						</tr>
						</table>
					
					<hr style="width: 98%; margin: 10px 0px 10px 0px;"/>
					<table class="form_grid" id="idAddScope_3" style="display: none;" 
							width="60%" border="0" cellspacing="3" cellpadding="0">
						<tr>
						   <td align="center" nowrap class="titlebg">序号</td>
						   <td align="center" nowrap class="titlebg">商户号</td>
						   <td align="center" nowrap class="titlebg">卡bin范围</td>
						   <td align="center" nowrap class="titlebg">卡子类型</td>
						   <td align="center" nowrap class="titlebg">积分下限</td>
						   <td align="center" nowrap class="titlebg">积分上限</td>
						   <td align="center" nowrap class="titlebg">会员级别</td>
						</tr>
						
						<s:iterator value="promtScopeList" status="status"> 
						<tr id="idDetail_${id}">
							<td align="center" class="u_half"><s:property value="#status.count"/></td>
							<td align="center" >
								<input type="hidden" id="idMerNo_<s:property value="#status.count"/>" name="merNo" value="${merNo}" />
								<input type="text" id="idMerNo_Sel_<s:property value="#status.count"/>" name="merName" value="${merName}" />
							</td>
							<td align="center" class="u_half">
								<input type="hidden" id="idCardBinScope_<s:property value="#status.count"/>" name="cardBinScope" value="${cardBinScope}" />
								<input type="text" id="idCardBinScope_Sel_<s:property value="#status.count"/>" name="binName" value="${binName}" />
							</td>
							<td align="center" class="u_half">
								<input type="hidden" id="idCardSubclass_<s:property value="#status.count"/>" name="cardSubclass" value="${cardSubclass}" />
								<input type="text" id="idCardSubclass_Sel_<s:property value="#status.count"/>" name="subClassName" value="${subClassName}" />
							</td>
							<td align="center" class="u_half">
								<s:textfield name="ptLlimit" />
							</td>
							<td align="center" class="u_half">
								<s:textfield name="ptUlimit" />
							</td>
							<td align="center" class="u_half">
								<s:textfield name="membLevel" />
							</td>
						</tr>
						</s:iterator>
						<s:if test="(promtScopeList == null) || (promtScopeList.size == 0)">
						<tr id="idDetail_1">
							<td align="center" class="u_half">1</td>
							<td align="center" id="idMerNoTd_1">
								<s:hidden id="idMerNo_1" name="merNo"/>
								<s:textfield id="idMerNo_Sel_1" name="merNo_Sel_1"/>
							</td>
							<td align="center" class="u_half">
								<s:hidden id="idCardBinScope_1" name="cardBinScope"/>
								<s:textfield id="idCardBinScope_Sel_1" name="cardBinScope_Sel_1" />
							</td>
							<td align="center" class="u_half">
								<s:hidden id="idCardSubclass_1" name="cardSubclass"/>
								<s:textfield id="idCardSubclass_Sel_1" name="cardSubclass_Sel_1" />
							</td>
							<td align="center" class="u_half">
								<s:textfield name="ptLlimit" />
							</td>
							<td align="center" class="u_half">
								<s:textfield name="ptUlimit" />
							</td>
							<td align="center" class="u_half">
								<s:textfield name="membLevel" />
							</td>
						</tr>
						</s:if>
						<tr id="idLink" style="line-height: 30px;">
							<td align="left" colspan="4" style="padding-left: 20px;">
								<span class="redlink"><a href="javascript:void(0);" onclick="javascript:addItem();">增加一项</a><a class="ml30" href="javascript:void(0);" onclick="javascript:deleteItem();">删除一项</a></span>
							</td>
						</tr>
					</table>
					<table class="form_grid" width="60%" border="0" cellspacing="3" cellpadding="0">
						<tr style="margin-top: 30px;">
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="提交" id="input_btn2"  name="ok" onclick="submitForm();" />
								<f:pspan pid="protocol_def_search">
									<input type="button" value="修改规则" name="escape" onclick="gotoUrl('/promotions/protocolDef/ruleList.do?promtRuleDef.promtId=${promtDef.promtId}')" class="ml30" />
								</f:pspan>
								<input type="button" value="返回列表" name="escape" onclick="gotoUrl('/promotions/protocolDef/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:hidden />
					<s:token name="_TOKEN_PROTOCOL_DEF_MODIFY"/>
				</s:form>
				
				<span class="note_div">注释</span>
				<ul class="showli_div">
					<li class="showli_div">消费积分协议定义方法和促销活动类似，但只送协议积分。</li>
					<li class="showli_div">协议积分可以与促销活动中“N倍协议积分“这条规则配合使用。</li>
				</ul>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>