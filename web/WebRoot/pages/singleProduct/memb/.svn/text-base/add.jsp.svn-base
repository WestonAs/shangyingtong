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
		
		<f:js src="/js/biz/singleProduct/membClassTempAdd.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#idMembLevel').blur(loadMembClass);
			$('#idMembUpgradeMthd').change(loadMembClass);
			$('#idMembDegradeMthd').change(function(){
				loadMembClass();
			});
		});
		function loadMembClass(){
			var membLevel = $('#idMembLevel').val();
			var membUpgradeMthd = $('#idMembUpgradeMthd').val();
			var membDegradeMthd = $('#idMembDegradeMthd').val();
			if(isEmpty(membLevel)||isEmpty(membUpgradeMthd)||isEmpty(membDegradeMthd)){
				return false;
			}
			if(!checkIsInteger(membLevel)){
				showMsg("级别数应输入整数(大于等于1,小于等于10),输入数字前面不能有空格。");
				$(':submit').attr('disabled', 'true');
				return false;
			} else if(membLevel<1 || membLevel>10){
				// 级别数大于等于1，小于等于10
				showMsg("级别应大于等于1，小于等于10。");
				$(':submit').attr('disabled', 'true');
				return false;
			} else {
				$(':submit').removeAttr('disabled');
			}
			// 保级条件为"人工控制"
			if(membDegradeMthd=='0'){
				$('#Id_ptBalanDe').hide();
				$('#Id_ptIncreDe').hide();
				$('#Id_cardTimeDe').hide();
			}
			// 保级条件为"积分余额超过相应阈值"
			if(membDegradeMthd=='1'){
				$('#Id_ptBalanDe').show();
				$('#Id_ptIncreDe').hide();
				$('#Id_cardTimeDe').hide();
			}
			// 保级条件为"增加积分超过相应阈值"
			if(membDegradeMthd=='2'){
				$('#Id_ptIncreDe').show();
				$('#Id_ptBalanDe').hide();
				$('#Id_cardTimeDe').hide();
			}
			// 保级条件为"刷卡次数超过相应阈值"
			if(membDegradeMthd=='3'){
				$('#Id_cardTimeDe').show();
				$('#Id_ptIncreDe').hide();
				$('#Id_ptBalanDe').hide();
			}
			
			// 升级条件为"人工控制"
			if(membUpgradeMthd=='0'){
				$('#Id_ptBalanUp').hide();
				$('#Id_ptIncreUp').hide();
				$('#Id_cardTimeUp').hide();
			}
			// 升级条件为"积分余额超过相应阈值"
			if(membUpgradeMthd=='1'){
				$('#Id_ptBalanUp').show();
				$('#Id_ptIncreUp').hide();
				$('#Id_cardTimeUp').hide();
			}
			// 升级条件为"增加积分超过相应阈值"
			if(membUpgradeMthd=='2'){
				$('#Id_ptIncreUp').show();
				$('#Id_ptBalanUp').hide();
				$('#Id_cardTimeUp').hide();
			}
			// 升级条件为"刷卡次数超过相应阈值"
			if(membUpgradeMthd=='3'){
				$('#Id_cardTimeUp').show();
				$('#Id_ptIncreUp').hide();
				$('#Id_ptBalanUp').hide();
			}
			
			// 升级方式选择"人工控制",保级条件不为"人工控制"
			if(membUpgradeMthd=='0'&membDegradeMthd!='0'){
				$('#idMemDetailTbl').show();
				$('tr[id^="idDetail_"]').remove();
				for (var i = 1; i <= membLevel; i++){
					var content = '<tr id="idDetail_'+ (i) +'"><td align="center">'+ (i) +'</td>' 
							+ '<td align="center"><input type="text" name="membClassName" class="required:true"/></td>' 
							+ '<td align="center"><input type="text" name="membDegradeThrhd" class="required:true"/></td>' 
							+ '</tr>';
					$('#idMemDetailTbl').append(content);
				}
			}
			// 保级条件选择"人工控制",升级条件不为"人工控制"
			if(membUpgradeMthd!='0'&membDegradeMthd=='0'){
				$('#idMemDetailTbl').show();
				$('tr[id^="idDetail_"]').remove();
				for (var i = 1; i <= membLevel; i++){
					var content = '<tr id="idDetail_'+ (i) +'"><td align="center">'+ (i) +'</td>' 
							+ '<td align="center"><input type="text" name="membClassName" class="required:true"/></td>' 
							+ '<td align="center"><input type="text" name="membUpgradeThrhd" class="required:true"/></td>' 
							+ '</tr>';
					$('#idMemDetailTbl').append(content);
				}
			}
			// 保级条件和升级条件都为"人工控制"
			if(membUpgradeMthd=='0'&membDegradeMthd=='0'){
				$('#idMemDetailTbl').show();
				$('tr[id^="idDetail_"]').remove();
				for (var i = 1; i <= membLevel; i++){
					var content = '<tr id="idDetail_'+ (i) +'"><td align="center">'+ (i) +'</td>' 
							+ '<td align="center"><input type="text" name="membClassName" class="required:true"/></td>' 
							+ '</tr>';
					$('#idMemDetailTbl').append(content);
				}
			}
			// 保级条件和升级条件都不为"人工控制"
			if(membUpgradeMthd!='0'&membDegradeMthd!='0'){
				$('#idMemDetailTbl').show();
				$('tr[id^="idDetail_"]').remove();
				for (var i = 1; i <= membLevel; i++){
					var content = '<tr id="idDetail_'+ (i) +'"><td align="center">'+ (i) +'</td>' 
							+ '<td align="center"><input type="text" name="membClassName" class="required:true"/></td>' 
							+ '<td align="center"><input type="text" name="membUpgradeThrhd" class="required:true, digit:true"/></td>' 
							+ '<td align="center"><input type="text" name="membDegradeThrhd" class="required:true, digit:true"/></td>' 
							+ '</tr>';
					$('#idMemDetailTbl').append(content);
				}
			}

			// 设置样式
			SysStyle.setFormGridStyle();
		}
		
		function checkField(){
			var count = $('tr[id^="idDetail_"]').size();
			var membUpgradeMthd = $('#idMembUpgradeMthd').val();
			var membDegradeMthd = $('#idMembDegradeMthd').val();
			
			for(i=0; i<count; i++){
				var $obj = $('tr[id^="idDetail_"]').eq(i);
				var $membClassName = $obj.children().eq(1).children();
				if(membUpgradeMthd=='0'){
					var $membDegradeThrhd = $membClassName.parent().next().children();
					var $membUpgradeThrhd = $membDegradeThrhd.parent().next().children();
				} else {
					var $membUpgradeThrhd = $membClassName.parent().next().children();
					var $membDegradeThrhd = $membUpgradeThrhd.parent().next().children();
				}
				// 级别名称不能为空
				if(isEmpty($membClassName.val())){
					showMsg("会员级别名称不能为空。");
					return false;
				}
				// 如果存在升级条件
				if(membUpgradeMthd=='1'||membUpgradeMthd=='2'||membUpgradeMthd=='3'){
					if(!isEmpty($membUpgradeThrhd.val())){
						if(!checkIsInteger($membUpgradeThrhd.val())){
							showMsg("升级条件请输入整数。");
							return false;
						} 
					} else {
						showMsg("升级条件不能为空。");
						return false;
					}
				}
				// 如果存在保级条件
				if(membDegradeMthd=='1'||membDegradeMthd=='2'||membDegradeMthd=='3'){
					if(!isEmpty($membDegradeThrhd.val())){
						if(!checkIsInteger($membDegradeThrhd.val())){
							showMsg("保级条件请输入整数。");
							return false;
						} 
					} else {
						showMsg("保级条件不能为空。");
						return false;
					}
				}
			}
			return true;
		}
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
						<tr>	
							<td width="80" height="30" align="right">类型名称</td>
							<td>
							<s:textfield name="membClassTemp.className" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入名称</span>
							</td>
							<td width="80" height="30" align="right">会员级别数</td>
							<td> 
								<s:textfield id="idMembLevel" name="membClassTemp.membLevel" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入数目</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">会员升级方式</td>
							<td>
								<s:select id="idMembUpgradeMthd" name="membClassTemp.membUpgradeMthd" list="membUpgradeMthdTypeList" 
								headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择方式</span>
							</td>
							<td width="80" height="30" align="right">会员保级方式</td>
							<td>
								<s:select id="idMembDegradeMthd" name="membClassTemp.membDegradeMthd" list="membDegradeMthdTypeList" 
								headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择方式</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">绝对失效日期</td>
							<td>
								<s:textfield name="membClassTemp.mustExpirDate" 
								onclick="WdatePicker({startDate:'{%y+20}1231',minDate:'%y-%M-%d'})" cssStyle="width:68px;" value="20991231"/>
								<span class="field_tipinfo">请输入绝对失效日期</span>
							</td>
						</tr>
					</table>
					
					<hr style="width: 98%; margin: 10px 0px 10px 0px;"/>
					<table class="form_grid" id="idMemDetailTbl" 
							width="60%" border="0" cellspacing="3" cellpadding="0" style="display:none;">
						<tr>
						   <td align="center" nowrap class="titlebg">级别序号</td>
						   <td align="center" nowrap class="titlebg">级别名称</td>
						   <td id="Id_ptBalanUp" style="display: none;" align="center" nowrap class="titlebg">升级条件(积分余额阈值)</td>
						   <td id="Id_ptIncreUp" style="display: none;" align="center" nowrap class="titlebg">升级条件(增加积分阈值)</td>
						   <td id="Id_cardTimeUp" style="display: none;" align="center" nowrap class="titlebg">升级条件(刷卡次数阈值)</td>
						   <td id="Id_ptBalanDe" style="display: none;" align="center" nowrap class="titlebg">保级条件(积分余额阈值)</td>
						   <td id="Id_ptIncreDe" style="display: none;" align="center" nowrap class="titlebg">保级条件(增加积分阈值)</td>
						   <td id="Id_cardTimeDe" style="display: none;" align="center" nowrap class="titlebg">保级条件(刷卡次数阈值)</td>
						</tr>
					</table>
					<table class="form_grid" width="60%" border="0" cellspacing="3" cellpadding="0">
						<tr style="margin-top: 30px;">
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return checkField();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/vipCard/vipCardDef/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_SINGLEPRODUCT_MEMB_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
				<span class="note_div">注释</span>
				<ul class="showli_div">
					<li class="showli_div">类型名称：最长支持64个中文或128位英文字母。例如沃尔玛白金会员。</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>