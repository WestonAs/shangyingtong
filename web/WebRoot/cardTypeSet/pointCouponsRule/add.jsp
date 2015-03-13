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
		<f:js src="/js/validate.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			var cardIssuer = $('#id_cardIssuerNo').val();
			Selector.selectMerch('id_merchName', 'id_merchNo', true, cardIssuer);
		    
		    $('#id_limitType').change(limitTypeChange);

		    $('#id_pointClass').change(loadMerchGroupId);
		    $('#id_couponClass').change(loadMerchGroupId);
		});

		function loadMerchGroupId(){
			var limitType = $('#id_limitType').val();
			var classId;
			if(limitType == '0'){//积分
				classId = $('#id_pointClass').val();
			}else if(limitType == '1'){//赠券
				classId = $('#id_couponClass').val();
			}
			$.post(CONTEXT_PATH + '/cardTypeSet/pointCouponsRule/loadMerchGroupId.do', 
					{'classId':classId, 'limitType':limitType,'callId':callId()},
					function(data){
						$('#id_merchGroupId').val(data.groupId);
						$('#id_merchGroupName').val(data.groupName);
						$('#input_btn2').removeAttr('disabled');
					}, 
					'json'
				);
		}
		
		function limitTypeChange(){
			var limitType = $('#id_limitType').val();
			if(limitType == '0'){//积分
				$('#id_limitIdTr1').show();
				$('#id_pointClass').removeAttr('disabled');
				
				$('#id_limitIdTr2').hide();
				$('#id_couponClass').attr('disabled',true);
			}else if(limitType == '1'){//赠券
				$('#id_limitIdTr1').hide();
				$('#id_pointClass').attr('disabled',true);
				
				$('#id_limitIdTr2').show();
				$('#id_couponClass').removeAttr('disabled');
			}else{
				$('#id_limitIdTr1').hide();
				$('#id_limitIdTr2').hide();
				$('#id_pointClass').attr('disabled',true);
				$('#id_couponClass').attr('disabled',true);
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
					<s:hidden id="id_cardIssuerNo" name="cardIssuerNo" />
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td width="80" height="30" align="right">限制类型</td>
							<td >
								<s:select id="id_limitType" name="merchGroupPointCouponLimit.limitType" list="limitTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择限制类型</span>
							</td>
							
						</tr>
						<tr  id="id_limitIdTr1"   style="display:none">
						    <td id="id_limitIdTd2"  width="80" height="30" align="right" >类型ID</td>
							<td id="id_pointTd" name="pointTd" >
								<s:select id="id_pointClass" disabled="true" name="merchGroupPointCouponLimit.limitId" list="pointClassDefList"  headerKey="" headerValue="--请选择--" listKey="ptClass" listValue="className" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择积分类型ID</span>
							</td>
						</tr>
						<tr id="id_limitIdTr2"   style="display:none">
						    <td id="id_limitIdTd1"  width="80" height="30" align="right" >类型ID</td>
						    <td id="id_conponTd" name="conponTd">
							    <s:select id="id_couponClass"  disabled="true" name="merchGroupPointCouponLimit.limitId" list="couponClassDefList"  headerKey="" headerValue="--请选择--" listKey="coupnClass" listValue="className" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择赠券类型ID</span>
							</td>
						</tr>
						<tr>
						    <td  id="merchGroup_1"  width="80" height="30" align="right" >商圈</td>
							<td  id="merchGroup_2"  >
								<s:hidden id="id_merchGroupId" name="merchGroupPointCouponLimit.groupId" />
								<s:textfield id="id_merchGroupName" name="groupName" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">请选择商圈</span>
							</td>
						    <c:if test="${loginRoleType!='40'}">
							<td width="80" height="30" align="right">商户</td>
							<td> 
								<s:hidden id="id_merchNo" name="merchGroupPointCouponLimit.merchId" />
								<s:textfield id="id_merchName" name="merchName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择商户</span>
							</td>
							</c:if>
						</tr>
						<tr>
							<td width="80" height="30" align="right">赠送标志</td>
							<td >
								<s:select id="Id_sendFlag" name="merchGroupPointCouponLimit.sendFlag" list="sendFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择赠送标志</span>
							</td>
							<td width="80" height="30" align="right">消费标志</td>
							<td >
								<s:select id="Id_consumeFlag" name="merchGroupPointCouponLimit.consumeFlag" list="consumeFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择消费标志</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td >
								<s:textfield name="merchGroupPointCouponLimit.remark" ></s:textfield>
								<span class="field_tipinfo">请输入备注</span>
							</td>
							<td></td>
							<td></td>
						</tr>

						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="申请" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardTypeSet/pointCouponsRule/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MERCHGROUPPOINTCOUPONLIMIT_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>