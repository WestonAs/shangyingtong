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
			$('#awdType_id').change(function(){//奖品类型
				var value = $(this).val();
				 showTypePoint(value);
		    });
			
			$('#pInstType_id').change(function(){//参与机构类型
				var value = $(this).val();
				showTypeMerch(value);
	    	});
			
			//参与机构类型
		    var type_p = '${prizeDef.jinstType}';
		    showTypeMerch(type_p);
			
			//奖品类型
		    var type_m = '${prizeDef.awdType}';
			showTypePoint(type_m);
			
		});
		
		function showTypeMerch(type){
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
		
		function showTypePoint(value){
			if(value == '01' || value == '02') {
				loadSubClass(value);
				$('#prizeCnt_tr').show();
				$('#prizeCnt_id').removeAttr('disabled');
				$('#subClassId').removeAttr('disabled');
			} else {
				$('#prizeCnt_tr').hide();
				$('#prizeCnt_id').attr('disabled', 'true');
				$('#subClassId').attr('disabled', 'true');
			}
		}
		
		function loadSubClass(value){
			var classId = '${prizeDef.classId}';//选中
			$('#subClassId').load(CONTEXT_PATH + '/lottery/prizeDef/getSubClassList.do', {'ruleType':value, 'callId':callId()},function(){$(this).val(classId);});
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate" >
				    <s:hidden id="Id_cardIssuerNo" name="cardIssuerNo" />
					<s:hidden id="Id_merchantNo" name="merchantNo" />
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">抽奖活动</td>
							<td>
								<s:textfield name="prizeDef.drawId" cssClass="required:true readonly" readonly="true"/>
								<span class="field_tipinfo">请选择抽奖活动</span>
							</td>
							
							<td width="80" height="30" align="right">奖项编号</td>
							<td>
								<s:textfield name="prizeDef.prizeNo" cssClass="required:true readonly" readonly="true"/>
								<span class="field_tipinfo">请输入奖项编号</span>
							</td>
						</tr>
						<tr>
							<td id="pInstType_td1" width="80" height="30" align="right" >参与机构类型</td>
							<td id="pInstType_td2" >
								<s:select id="pInstType_id" name="prizeDef.jinstType" headerKey="" headerValue="--请选择--" list="pInstTypeList" listKey="value" listValue="name" cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择机构类型</span>
							</td>
							<td id="pinstId_td1" width="80" height="30" align="right" style="display: none;">商户</td>
							<td id="pinstId_td2" style="display: none;">
								<s:hidden id="idpinstId" name="prizeDef.jinstId" disabled="true" />
								<s:textfield id="idpinstSel" name="pinstId_sel" cssClass="{required:true}" disabled="true"/>
								<span class="field_tipinfo">请选择商户</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">奖项名称</td>
							<td>
								<s:textfield name="prizeDef.prizeName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入奖项名称</span>
							</td>
							
							<td width="80" height="30" align="right">奖项简称</td>
							<td>
								<s:textfield name="prizeDef.shortName" cssClass="{required:true}" maxlength="10"/>
								<span class="field_tipinfo">请输入奖项简称</span>
							</td>
						</tr>
						<tr>
						    <td width="80" height="30" align="right">奖品类型</td>
							<td>
								<s:select id="awdType_id" name="prizeDef.awdType" list="awdTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择奖品类型</span>
							</td>
							<td width="80" height="30" align="right">奖品总数</td>
							<td>
								<s:textfield name="brushSet.totalAwdCnt" cssClass="{required:true, num:true}"/>
								<span class="field_tipinfo">请输入奖品总数</span>
							</td>
						</tr>
						
						<tr id="prizeCnt_tr"  style="display: none;">
						    <td width="80" height="30" align="right">奖品数值</td>
							<td>
								<s:textfield id="prizeCnt_id" name="prizeDef.prizeCnt" cssClass="{required:true, num:true}" disabled="true" />
								<span class="field_tipinfo">请输入奖品数值</span>
							</td>
							<td width="80" height="30" align="right">奖品子类型</td>
							<td>
								<select  id="subClassId" name="prizeDef.classId" class="{required:true}" disabled="true" >
								</select>
								<span class="field_tipinfo">请输入奖品子类型</span>
							</td>
						</tr>
						<tr>
						    <td width="80" height="30" align="right">备注</td>
						    <td>
								<s:textfield name="prizeDef.remark"/>
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/lottery/prizeDef/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_LOTTER_PRIZE_DEF_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>