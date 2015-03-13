<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>添加明折规则定义</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#jinstType_Id').change(function(){
				var value = $(this).val();
				if (value == '1') { //指定商户
					$('#idjinstId').removeAttr('disabled');
					$('#idjinstSel').removeAttr('disabled');
					$('[id^="jinstId_td"]').show();
				} else {
					$('#idjinstId').attr('disabled', 'true');
					$('#idjinstSel').attr('disabled', 'true');
					$('[id^="jinstId_td"]').hide();
				} 
			});
			var cardIssuerNo = $('#id_cardIssuerNo').val();
			Selector.selectMerch('idjinstSel', 'idjinstId', true, cardIssuerNo);
		});
		function check(){
			var settDiscntRate = $('#id_settDiscntRate').val();
			var discntRate = $('#id_discntRate').val();
			if(parseFloat(settDiscntRate) > parseFloat(discntRate)){
				showMsg('暗折折扣率不能大于折扣率！');
				return false;
			}
			if($('#inputForm').validate().form()){
				$('#inputForm').submit();
				$('#input_btn2').attr('disabled', 'true');
				//window.parent.showWaiter();
				$("#loadingBarDiv").css("display","inline");
				$("#contentDiv").css("display","none");
			}
		}
		function getNewExpirDate(){
			WdatePicker({minDate:'#F{$dp.$D(\'effDateId\')}'});
		} 
		function getEffDateId(){
			WdatePicker({minDate:'#F{$dp.$D(\'expirDateId\')}'});
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
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<s:hidden id="id_cardIssuerNo" name="cardIssuerNo" />
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>添加明折规则定义</caption>
						<tr>
							<td width="80" height="30" align="right">折扣协议名</td>
							<td>
								<s:textfield name="discntProtclDef.discntProtclName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入折扣协议名</span>
							</td>
							<td width="80" height="30" align="right">折扣类型</td>
							<td>
								<s:select name="discntProtclDef.discntClass" list="discntClassList"
									listKey="discntClass" listValue="className" cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择折扣类型</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">联名机构类型</td>
							<td>
								<s:select id="jinstType_Id" name="discntProtclDef.jinstType" list="jinstTypeList" listKey="value" listValue="name" cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择机构类型</span>
							</td>
							<td id="jinstId_td1" width="80" height="30" align="right" style="display: none;">商户代码</td>
							<td id="jinstId_td2" style="display: none;">
								<s:hidden id="idjinstId" name="discntProtclDef.jinstId" disabled="true"/>
								<s:textfield id="idjinstSel" name="jinstId_sel" cssClass="{required:true}" disabled="true"/>
								<span class="field_tipinfo">请选择商户</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">折扣率</td>
							<td>
								<s:textfield id="id_discntRate" name="discntProtclDef.discntRate" cssClass="{required:true, num:true, min:1, max:99}" maxlength="2"/>
								<span class="field_tipinfo">输入1-99的整数</span>
							</td>
							<td></td>
							<td></td>
							<!-- 
							<td width="80" height="30" align="right">暗折折扣率</td>
							<td>
								<s:textfield id="id_settDiscntRate" name="discntProtclDef.settDiscntRate" cssClass="{required:true, num:true, min:1, max:99}" maxlength="2"/>
								<span class="field_tipinfo">输入1-99的整数</span>
							</td>
							 -->
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">折扣操作方法</td>
							<td>
								<s:select name="discntProtclDef.discntOperMthd" list="discntOperMthdList" listKey="value" listValue="name" cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
							
							<td width="80" height="30" align="right">排他标志</td>
							<td>
								<s:select name="discntProtclDef.discntExclusiveFlag" list="discntExclusiveList"	listKey="value" listValue="name" cssClass="{required:true}" onmouseover="FixWidth(this)"></s:select>
								<span class="field_tipinfo">请选择排他标志</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">生效日期</td>
							<td>
								<s:textfield name="discntProtclDef.effDate" id="effDateId" onclick="getEffDateId();"
									cssClass="{required:true}" />
								<span class="field_tipinfo">请输入生效日期</span>
							</td>
							
							<td width="80" height="30" align="right">失效日期</td>
							<td>
								<s:textfield name="discntProtclDef.expirDate" id="expirDateId" onclick="getNewExpirDate();" 
									cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入失效日期</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">纸质协议号</td>
							<td>
								<s:textfield name="discntProtclDef.protclPaperSn" cssClass="{required:true, digitOrLetter:true}" maxlength="20"/>
								<span class="field_tipinfo">请输入纸质协议号</span>
							</td>
							
							<td width="80" height="30" align="right"></td>
							<td>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="提交" id="input_btn2"  name="ok" onclick="return check();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/discntProtocolDef/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_DISCNT_PROTOCOL_DEF_ADD"/>
				</s:form>
				
				<span class="note_div">明折规则说明</span>
				<ul class="showli_div">
					<li class="showli_div">明折指的是发卡机构与商户之间的协议折扣，商户通过协议折扣查询获得此折扣。与明折相对的是暗折，暗折只能在促销活动中定义，系统按照暗折规则自动打折。</li>
					<li class="showli_div">折扣协议名：最大长度为30个字符，汉字算两个字符，英文或数字算一个字符。</li>
					<li class="showli_div">联名机构类型：可选择整个发卡机构或者指定某个商户。如果选择整个发卡机构，那么发卡机构的所有卡在它所有特约商户那里都可以享受明折。</li>
					<li class="showli_div">折扣率：填百分数，例如97折填97。</li>
				</ul>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>