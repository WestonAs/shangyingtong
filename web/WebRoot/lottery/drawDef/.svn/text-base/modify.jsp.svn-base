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
		<f:js src="/js/validate.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
		    var cardIssuerNo = $('#Id_cardIssuerNo').val();
			var merchantNo = $('#Id_merchantNo').val();
		    // 卡BIN选择
		    Selector.selectCardBin('cardBinScope_sel', 'idCardBinScope', false, cardIssuerNo, merchantNo);
		    // 发卡机构的特约商户
			Selector.selectMerch('idpinstSel', 'idpinstId', true, cardIssuerNo);
		    $('#pInstType_id').change(function(){
					var value = $(this).val();
					if(value == '1') {
						$('[id^="pinstId_td"]').show();
						$('#idpinstId').removeAttr('disabled');
						$('#idpinstSel').removeAttr('disabled');
					} else {
						$('[id^="pinstId_td"]').hide();
						$('#idpinstId').attr('disabled', 'true');
						$('#idpinstSel').attr('disabled', 'true');
					}
			});
		    
		    //显示商户
		    var type = '${drawDef.pinstType}';
			showType(type);
			
			drawTypeChange();
			$('#drawTypeId').change(drawTypeChange);
		});
		
		function drawTypeChange(){
		    var drawTypeId = $('#drawTypeId').val();
		    if(drawTypeId == '0'){//现场开奖
			    $('#binTransId_tr').hide();
			    $('#amtId_tr').hide();
			    $('#probId_tr1').hide();
			    $('#probId_tr2').hide();
				
				$("#transTypeId").removeClass('{required:true}');
				$("#amtTypeId").removeClass('{required:true}');
				$("#probTypeId").removeClass('{required:true}');
		    }else if(drawTypeId == '1'){//即刷即中
				$('#binTransId_tr').show();
				$('#amtId_tr').show();
				$('#probId_tr1').show();
				$('#probId_tr2').show();
				
				$("#transTypeId").addClass('{required:true}');// 必填
				$("#amtTypeId").addClass('{required:true}');// 必填
				$("#probTypeId").addClass('{required:true}');// 必填
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
		
		function getExpirDate() {
			WdatePicker({minDate:'#F{$dp.$D(\'effDateId\') || \'%y-%M-%d\'}'})
		}
		function getEffDate() {
			WdatePicker({minDate:'%y-%M-%d', maxDate:'#F{$dp.$D(\'expirDateId\')}'})
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
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">抽奖活动ID</td>
							<td>
								<s:textfield id="id_drawId" name="drawDef.drawId" cssClass="required:true readonly" readonly="true"/>
							</td>
							
							<td width="80" height="30" align="right">抽奖活动名称</td>
							<td>
								<s:textfield name="drawDef.drawName" cssClass="{required:true}" maxlength="128"/>
								<span class="field_tipinfo">请输入抽奖活动名称</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">抽奖方法</td>
							<td>
								<s:select  id="drawTypeId"  name="drawDef.drawType" list="drawTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							    <span class="field_tipinfo">请选择抽奖方法</span>
							</td>

							<td width="80" height="30" align="right">抽奖活动简称</td>
							<td>
								<s:textfield name="drawDef.drawShortName" cssClass="{required:true}" maxlength="10"/>
								<span class="field_tipinfo">请输入抽奖活动简称</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">生效日期</td>
							<td>
								<s:textfield name="drawDef.effDate" id="effDateId" onclick="getEffDate();"
									cssClass="{required:true}" />
								<span class="field_tipinfo">生效日期</span>
							</td>
							<td width="80" height="30" align="right">失效日期</td>
							<td>
								<s:textfield name="drawDef.expirDate" id="expirDateId" onclick="getExpirDate();" 
									cssClass="{required:true}"/>
								<span class="field_tipinfo">失效日期</span>
							</td>
						</tr>
						
						<tr>
							<td id="pInstType_td1" width="80" height="30" align="right" >参与机构类型</td>
							<td id="pInstType_td2" >
								<s:select id="pInstType_id" name="drawDef.pinstType" headerKey="" headerValue="--请选择--" list="pInstTypeList" listKey="value" listValue="name" cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择机构类型</span>
							</td>
							<td id="pinstId_td1" width="80" height="30" align="right" style="display: none;">商户</td>
							<td id="pinstId_td2" style="display: none;">
								<s:hidden id="idpinstId" name="drawDef.pinstId" disabled="true" />
								<s:textfield id="idpinstSel" name="pinstId_sel" cssClass="{required:true}" disabled="true"/>
								<span class="field_tipinfo">请选择商户</span>
							</td>
						</tr>
						
						<tr id ="binTransId_tr">
							<td width="80" height="30" align="right">卡BIN范围</td>
							<td>
								<s:hidden id="idCardBinScope" name="drawDef.cardBinScope" />
								<s:textfield id="cardBinScope_sel" name="cardBinScope_sel" />
								<span class="field_tipinfo">请选择卡BIN</span>
							</td>

							<td width="80" height="30" align="right">交易类型</td>
							<td>
								<s:select id="transTypeId" name="drawDef.transType" list="transTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请输入交易类型</span>
							</td>
						</tr>
						
						<tr id ="amtId_tr">
							<td width="80" height="30" align="right">金额类型</td>
							<td>
								<s:select id="amtTypeId" name="drawDef.amtType" list="amtTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请输入金额类型</span>
							</td>

							<td width="80" height="30" align="right">参考金额</td>
							<td>
								<s:textfield name="drawDef.refAmt" cssClass="{num:true}"/>
								<span class="field_tipinfo">请输入参考金额</span>
							</td>
						</tr>
						
						<tr id ="probId_tr1">
							<td width="80" height="30" align="right">概率折算方法</td>
							<td>
								<s:select id="probTypeId" name="drawDef.probType" list="probTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请输入概率折算方法</span>
							</td>
							<td width="80" height="30" align="right">基准概率</td>
							<td>
								<s:textfield name="drawDef.probBase" cssClass="{num:true, max:99999999.9999}"/>
								<span class="field_tipinfo">请输入基准概率</span>
							</td>
						</tr>
						
						<tr id ="probId_tr2">
							<td width="80" height="30" align="right">最大概率倍数</td>
							<td>
								<s:textfield name="drawDef.probMax" cssClass="{num:true, max:999999.99}"/>
								<span class="field_tipinfo">请输入最大概率倍数</span>
							</td>
							<td width="80" height="30" align="right">最小概率倍数</td>
							<td>
								<s:textfield name="drawDef.probMin" cssClass="{digit:true, max:999999.99}"/>
								<span class="field_tipinfo">请输入最小概率倍数</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/lottery/drawDef/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_LOTTER_DRAW_DEF_MODYFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>