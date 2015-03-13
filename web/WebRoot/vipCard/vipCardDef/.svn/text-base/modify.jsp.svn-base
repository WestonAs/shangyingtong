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
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			var membLevel = $('#idMembLevel').val();
			var membUpgradeMthd = $('#idMembUpgradeMthd').val();
			var membDegradeMthd = $('#idMembDegradeMthd').val();
			loadMembClass(membLevel, membUpgradeMthd, membDegradeMthd);
			
			$('#idMembUpgradeMthd').change(function(){
				var membLevel = $('#idMembLevel').val();
				var membUpgradeMthd = $('#idMembUpgradeMthd').val();
				var membDegradeMthd = $('#idMembDegradeMthd').val();
				loadMembClass(membLevel, membUpgradeMthd, membDegradeMthd);
			});
			$('#idMembDegradeMthd').change(function(){
				var membLevel = $('#idMembLevel').val();
				var membUpgradeMthd = $('#idMembUpgradeMthd').val();
				var membDegradeMthd = $('#idMembDegradeMthd').val();
				loadMembClass(membLevel, membUpgradeMthd, membDegradeMthd);
			});
		});
		
		function loadMembClass(membLevel, membUpgradeMthd, membDegradeMthd){
			//if(isEmpty(membLevel)||isEmpty(membUpgradeMthd)||isEmpty(membDegradeMthd)){
				//return false;
			//}
			
			// 保级条件为"人工控制"或者为空
			if(membDegradeMthd=='0'||isEmpty(membDegradeMthd)){
				$('#Id_ptBalanDe').hide();
				$('#Id_ptIncreDe').hide();
				$('#Id_cardTimeDe').hide();
				$('td[id^="Id_membDe_td_"]').hide();
				$('[id^="Id_membDe_tds_"]').hide();
				$(':[id^="Id_membDe_tds_"]').attr('disabled', 'true');
			}
			
			// 保级条件不为"人工控制"
			if(membDegradeMthd=='1'||membDegradeMthd=='2'||membDegradeMthd=='3'){
				// 共同部分
				$('td[id^="Id_membDe_td_"]').show();
				$('[id^="Id_membDe_tds_"]').show();
				$('[id^="Id_membDe_tds_"]').removeAttr('disabled');
				
				// 保级条件为"积分余额超过相应阈值"
				if(membDegradeMthd=='1'){
					$('#Id_ptBalanDe').show();
					$('#Id_ptIncreDe').hide();
					$('#Id_cardTimeDe').hide();
				} 
				// 保级条件为"增加积分超过相应阈值"
				else if(membDegradeMthd=='2'){
					$('#Id_ptIncreDe').show();
					$('#Id_ptBalanDe').hide();
					$('#Id_cardTimeDe').hide();
				} 
				// 保级条件为"刷卡次数超过相应阈值"
				else if(membDegradeMthd=='3'){
					$('#Id_cardTimeDe').show();
					$('#Id_ptIncreDe').hide();
					$('#Id_ptBalanDe').hide();
				}
			}
			
			// 升级条件为"人工控制"或者为空
			if(membUpgradeMthd=='0'||isEmpty(membUpgradeMthd)){
				$('#Id_ptBalanUp').hide();
				$('#Id_ptIncreUp').hide();
				$('#Id_cardTimeUp').hide();
				$('td[id^="Id_membUp_td_"]').hide();
				$('[id^="Id_membUp_tds_"]').hide();
				$(':[id^="Id_membUp_tds_"]').attr('disabled', 'true');
			}
			// 升级条件不为"人工控制"
			if(membUpgradeMthd=='1'||membUpgradeMthd=='2'||membUpgradeMthd=='3'){
				// 共同部分
				$('td[id^="Id_membUp_td_"]').show();
				$('[id^="Id_membUp_tds_"]').show();
				$('[id^="Id_membUp_tds_"]').removeAttr('disabled');
				
				// 升级条件为"积分余额超过相应阈值"
				if(membUpgradeMthd=='1'){
					$('#Id_ptBalanUp').show();
					$('#Id_ptIncreUp').hide();
					$('#Id_cardTimeUp').hide();
				}
				// 升级条件为"增加积分超过相应阈值"
				else if(membUpgradeMthd=='2'){
					$('#Id_ptIncreUp').show();
					$('#Id_ptBalanUp').hide();
					$('#Id_cardTimeUp').hide();
				}
				// 升级条件为"刷卡次数超过相应阈值"
				else if(membUpgradeMthd=='3'){
					$('#Id_cardTimeUp').show();
					$('#Id_ptIncreUp').hide();
					$('#Id_ptBalanUp').hide();
				}
			}
		}
		
		function checkField(){
			var count = $('tr[id^="Id_membName_tr_"]').size();
			var membUpgradeMthd = $('#idMembUpgradeMthd').val();
			var membDegradeMthd = $('#idMembDegradeMthd').val();
			for(i=0; i<count; i++){
				var $obj = $('tr[id^="Id_membName_tr_"]').eq(i);
				var $membClassName = $obj.children().eq(1).children();
				var $membUpgradeThrhd = $membClassName.parent().next().children();
				var $membDegradeThrhd = $membUpgradeThrhd.parent().next().children();
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
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<s:hidden name="membClassDef.membClass"></s:hidden>
						<s:hidden name="membClassDef.cardIssuer"></s:hidden>
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">会员名称</td>
							<td>
								<s:textfield name="membClassDef.className" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入会员名称</span>
							</td>
							<td width="80" height="30" align="right">会员级别数</td>
							<td>
								<s:textfield id="idMembLevel" name="membClassDef.membLevel" cssClass="readonly" />
								<span class="field_tipinfo">不能更改级别数</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">会员升级方式</td>
							<td>
								<s:select id="idMembUpgradeMthd" name="membClassDef.membUpgradeMthd" list="membUpgradeMthdTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
 									cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择升级方式</span>
							</td>
							<td width="80" height="30" align="right">会员保级方式</td>
							<td>
								<s:select id="idMembDegradeMthd" name="membClassDef.membDegradeMthd" list="membDegradeMthdTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
 									cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择保级方式</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">绝对失效日期</td>
							<td>
								<s:textfield name="membClassDef.mustExpirDate" cssClass="{required:true}" onclick="WdatePicker({minDate:'%y-%M-%d'})" cssStyle="width:68px;" />
								<span class="field_tipinfo">请输入绝对失效日期</span>
							</td>
							<td width="80" height="30" align="right">关联外部卡号</td>
							<td>
								<s:set name="externalCardType" value='#{"":"未关联", "1":"手机号码"}'/>
								<s:radio name="membClassDef.reserved1" list="#externalCardType"  />
							</td>
						</tr>
					</table>
					<hr style="width: 98%; margin: 10px 0px 10px 0px;"/>
					<table class="form_grid" id="idMemodifyDetailTb" 
							width="60%" border="0" cellspacing="3" cellpadding="0">
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
						<s:iterator value="membClassList" status="mcnStuts">
					    <tr id="Id_membName_tr_<s:property value="#mcnStuts.index"/>">
					      <td align="center" class="u_half" >
					      	<s:property value="#mcnStuts.index+1"/>
					      </td>
					      <td align="center" >
							<input id="Id_membName_tds_<s:property value="#mcnStuts.index"/>" type="text" name="membClassName" value="${membClassName}" />
						  </td>
					      <td id="Id_membUp_td_<s:property value="#mcnStuts.index"/>" style="display: none;" align="center" >
							<input id="Id_membUp_tds_<s:property value="#mcnStuts.index"/>" style="display: none;" type="text" name="membUpgradeThrhd" 
								value="${membUpgradeThrhd}" disabled="disabled"/>
						  </td>
					      <td id="Id_membDe_td_<s:property value="#mcnStuts.index"/>"  style="display: none;" align="center" >
							<input id="Id_membDe_tds_<s:property value="#mcnStuts.index"/>" style="display: none;" type="text" name="membDegradeThrhd" 
								value="${membDegradeThrhd}" disabled="disabled"/>
						  </td>
					    </tr>
						</s:iterator>
					</table>
					<table>
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return checkField();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/vipCard/vipCardDef/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_VIPCARDDEF_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>