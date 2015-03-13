<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>新增赠券子类型定义</title>
		
		<f:css href="/css/page.css"/>
		<f:css href="/css/multiselctor.css"/>
		
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
		<f:js src="/js/biz/cardTypeSet/issType.js"/>
		<f:js src="/js/biz/cardTypeSet/couponClass.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#Id_jinstType').change(jinstTypeChange);
		});
		
		function jinstTypeChange(){
			var jinstType = $('#Id_jinstType').val();
			if(jinstType=='2'){//商圈
				$('#merchGroup_1').show();
				$('#merchGroup_2').show();
				$('#Id_merchGroupId').removeAttr('disabled');
			}else if(jinstType=='0'){//通用
				$('#merchGroup_1').hide();
				$('#merchGroup_2').hide();
				$('#Id_merchGroupId').attr('disabled',true);
			}
		}
		function getNewExpirDate(){
			WdatePicker({minDate:'#F{$dp.$D(\'Id_effDate\')}'});
		} 
		</script>
	
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="add.do" id="inputForm" cssClass="validate">
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td width="80" height="30" align="right">赠券名称</td>
						<td>
							<s:textfield name="couponClassDef.className" cssClass="{required:true}"></s:textfield>
							<span class="field_tipinfo">请输入赠券名称</span>
						</td>
						<td width="80" height="30" align="right">赠券类型简称</td>
						<td>
							<s:textfield name="couponClassDef.classShortName" cssClass="{required:true}" maxlength="6"></s:textfield>
							<span class="field_tipinfo">请输入类型简称</span>
						</td>
					</tr>
					<s:if test="loginRoleType == 20">
						<td width="80" height="30" align="right">赠券类型</td>
						<td>
							<s:select id="Id_jinstType" name="couponClassDef.jinstType" list="jinstTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择积分类型</span>
						</td>
						</s:if>
						<td  id="merchGroup_1"  width="80" height="30" align="right" style="display:none">商圈</td>
						<td  id="merchGroup_2"  style="display:none">
							<s:select id="Id_merchGroupId"  disabled="true" name="couponClassDef.jinstId" list="merchGroupList" headerKey="" headerValue="--请选择--" listKey="groupId" listValue="groupName" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择商圈</span>
						</td>
					</tr>
					<!--<s:if test="loginRoleType == 20">
					<tr id="jinst_tr" >
						<td width="80" height="30" align="right">联名机构类型</td>
						<td>
							<s:select id="Id_jinstType" name="couponClassDef.jinstType" list="jinstTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}" onchange="IssType.issTypeChange('jinstName','Id_jinstId','Id_jinstType')"></s:select>
							<span class="field_tipinfo">请选择联名机构类型</span>
						</td>
						<td id="jinst_td1" width="80" height="30" align="right" style="display:none">联名机构</td>
						<td id="jinst_td2" style="display:none">
							<s:textfield id="jinstName" name="couponClassDef.jinstName" cssClass="{required:true}" disabled="true"></s:textfield>
							<s:hidden id="Id_jinstId" name="couponClassDef.jinstId" cssClass="{required:true}" disabled="true"></s:hidden>
							<span class="field_tipinfo">请选择联名机构</span>
						</td>
					</tr>
					</s:if>
					--><s:if test="loginRoleType == 40">
						<tr>
							<td width="80" height="30" align="right">委托发卡机构</td>
							<td>
								<s:select name="couponClassDef.issId" list="branchList" headerKey="" headerValue="--请选择--" 
								listKey="branchCode" listValue="branchName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">委托发卡机构</span>
							</td>
						</tr>
					</s:if>
					<tr>
						<td width="80" height="30" align="right">赠券使用方法</td>
						<td>
							<s:select id="Id_coupnUsage" name="couponClassDef.coupnUsage" list="couponUsageList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}" onchange="CouponClass.coupnUsageChange()"></s:select>
							<span class="field_tipinfo">请选择使用方法</span>
						</td>
						<td width="80" height="30" align="right">初始面值</td>
						<td>
							<s:textfield name="couponClassDef.faceValue" cssClass="{required:true, digit:true}" maxlength="10"></s:textfield>
							<span class="field_tipinfo">请输入初始面值</span>
						</td>
					</tr>
					<tr>
						<td id="td_ruleParam1_1" width="80" height="30" align="right" style="display:none"><span>满M元</span></td>
						<td id="td_ruleParam1_2" style="display:none">
							<s:textfield id="Id_ruleParam1"  name="couponClassDef.ruleParam1" cssClass="{required:true, digit:true}" maxlength="10"></s:textfield>
							<span class="field_tipinfo">请输入金额</span>
						</td>
						<td id="td_ruleParam2_1" width="80" height="30" align="right" style="display:none">
							<span id="usage_1" style="display:none">消费N元</span>
						</td>
						<td id="td_ruleParam2_2" style="display:none">	
							<s:textfield id="Id_ruleParam2" name="couponClassDef.ruleParam2" cssClass="{required:true, digit:true}" maxlength="10"></s:textfield>
							<span class="field_tipinfo">请输入金额</span>
						</td>
						<td id="td_ruleParam3_1" width="80" height="30" align="right" style="display:none">使用赠券比例</td>
						<td id="td_ruleParam3_2" style="display:none">	
							<s:textfield id="Id_ruleParam3" name="couponClassDef.ruleParam3" cssClass="{required:true}" ></s:textfield>
							<span class="field_tipinfo">请输入比例K</span>
						</td>
						
					</tr>
					<tr>
						<td width="80" height="30" align="right">生效日期</td>
						<td>
							<s:textfield id="Id_effDate"  name="couponClassDef.effDate" cssClass="{required:true}" onclick="WdatePicker({minDate:'%y-%M-%d'})"></s:textfield>
							<span class="field_tipinfo">请输入生效日期</span>
						</td>
						<td width="80" height="30" align="right">失效日期</td>
						<td>	
							<s:textfield id="Id_expirDate" name="couponClassDef.expirDate" cssClass="{required:true}" onclick="getNewExpirDate();"></s:textfield>
							<span class="field_tipinfo">请输入失效日期</span>
						<td>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30" colspan="3">
							<input type="submit" value="提交" id="input_btn2"  name="ok" />
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');PointClass.ptUsageChange()" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardTypeSet/couponClass/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_COUPONCLASS_ADD"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
				<span class="note_div">注释</span>
				<ul class="showli_div">
					<li class="showli_div">赠券名称：最长支持64个中文或128位英文字母。例如沃尔玛国庆通用赠券。</li>
					<li class="showli_div">赠券类型简称：最长支持5个中文或10位英文字母，用于POS机显示赠券名称。</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>