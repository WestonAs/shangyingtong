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
		
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:css href="/css/multiselctor.css"/>
		
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/validate.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				Selector.selectBank('idBank_sel', 'idBank', true);
				
				var reType = $('#idRebateType').val();
				isShow(reType);
				
				$('#idRebateType').change(function(){
					var value = $(this).val();
					isShow(value);
				});
				$('#idRebateCard').blur(function(){
					$('#idRebateCard_field').html('请输入返利卡号');
					var value = $(this).val();
					var cardBranch = $('#id_CardBranch').val();
					
					if(isEmpty(cardBranch)){
						showMsg('发卡机构号不能为空');
						return;
					}
					if (value == undefined || value.length < 19){
						return;
					}
					if (!validator.isDigit(value)) {
						return;
					}
					$.post(CONTEXT_PATH + '/customerRebateMgr/cardCustomer/isCorrectRebateCard.do', {'cardCustomer.rebateCard':value, 'cardCustomer.cardBranch':cardBranch, 'callId':callId()}, 
						function(json){
							if(!json.isCorrectRebateCard){
								$('#idRebateCard_field').html('卡号错误或无效').addClass('error_tipinfo').show();
								$('#input_btn2').attr('disabled', 'true');
							} else {
								clearCarBinError();
							}
						}, 'json');
				});
				
				$('#input_btn2').click(function(){
					if($('#inputForm').validate().form()){
						$('#inputForm').submit();
						$('#input_btn2').attr('disabled', 'true');
						//window.parent.showWaiter();
						$("#loadingBarDiv").css("display","inline");
						$("#contentDiv").css("display","none");
					}
				});
			});
			
			function isShow(value){
				if(value == '0'){
						$('td[id^="idRebateCard_td"]').show();
						$('#idRebateCard').removeAttr('disabled');
					} else {
						$('td[id^="idRebateCard_td"]').hide();
						$('#idRebateCard').attr('disabled', 'true');
					}
			}
			
			function clearCarBinError(){
				$('#idRebateCard_field').removeClass('error_tipinfo').html('请输入返利卡号');
				$('#input_btn2').removeAttr('disabled');
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
		
		<div  id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">购卡客户ID</td>
							<td>
								<s:textfield name="cardCustomer.cardCustomerId" readonly="true" cssClass="readonly"/>
							</td>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:textfield id="id_CardBranch" name="cardCustomer.cardBranch" readonly="true" cssClass="readonly"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">购卡客户名称</td>
							<td>
								<s:textfield name="cardCustomer.cardCustomerName" cssClass="{required:true}"/>
								<span class="field_tipinfo">名称不能为空</span>
							</td>
							<td width="80" height="30" align="right">国/地税号</td>
							<td>
								<s:textfield name="cardCustomer.taxNo" cssClass="{digitOrLetter:true}"/>
								<span class="field_tipinfo">请输入数字字母</span>
							</td>
						</tr>
						<tr>	
							<td width="80" height="30" align="right">返利方式</td>
							<td>
								<s:select id="idRebateType" name="cardCustomer.rebateType" list="rebateTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择返利方式</span>
							</td>
							<td id="idRebateCard_td1" width="80" height="30" align="right" style="display: none;">返利卡号</td>
							<td id="idRebateCard_td2" style="display: none;">
								<s:textfield id="idRebateCard" name="cardCustomer.rebateCard" cssClass="{required:true, digit:true, minlength:19}" maxlength="19"/>
								<span id="idRebateCard_field" class="field_tipinfo">请输入返利卡号</span>
								<span class="error_tipinfo">请输入19位数字</span>
							</td>
						</tr>						
						<tr>
							<td width="80" height="30" align="right">团购卡号</td>
							<td>
								<s:textfield name="cardCustomer.groupCardId" cssClass="{maxlength:19}"/>
								<span class="field_tipinfo">请输入团购卡号</span>
							</td>
						
							<td width="80" height="30" align="right">开户行名</td>
							<td>
								<s:textfield id="idBank_sel" name="cardCustomer.bank" cssClass="{required:true}"/>
								<span class="field_tipinfo">点击选择</span>
							</td>
						</tr>						
						<tr>
							<td width="80" height="30" align="right">开户行号</td>
							<td>
								<s:textfield id="idBank" name="cardCustomer.bankNo" cssClass="{required:true, digit:true, minlength:12} readonly" maxlength="12" readonly="true"/>
								<span class="field_tipinfo">请点击开户行名</span>
							</td>
							
							<td width="80" height="30" align="right">银行账号</td>
							<td>
								<s:textfield name="cardCustomer.bankAccNo" cssClass="{required:true, digitOrAllowChars:'-'}" maxlength="32"/>
								<span class="field_tipinfo">请输入账号</span>
							</td>
						</tr>						
						
						<tr>
							<td width="80" height="30" align="right">营业执照</td>
							<td>
								<s:textfield name="cardCustomer.license" maxlength="32" cssClass="{digitOrLetter:true}"/>
								<span class="field_tipinfo">请输入数字字母</span>
							</td>
							
							<td width="80" height="30" align="right">组织机构号</td>
							<td>
								<s:textfield name="cardCustomer.organization" maxlength="32" cssClass="{digitOrLetter:true}"/>
								<span class="field_tipinfo">请输入数字字母</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">单位地址</td>
							<td>
								<s:textfield name="cardCustomer.companyAddress" cssClass="{maxlength:100}"/>
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right">单位经营范围</td>
							<td>
								<s:textfield name="cardCustomer.companyBusinessScope" cssClass="{maxlength:100}"/>
								<span class="field_tipinfo"></span>
							</td>
						</tr>	
						<tr>
							<td width="80" height="30" align="right">风险等级</td>
							<td>
								<s:select name="cardCustomer.riskLevel" list="@gnete.card.entity.type.RiskLevelType@getAll()"  
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" />
								<span class="field_tipinfo">选择风险级别</span>
							</td>
							<td width="80" height="30" align="right">经办人</td>
							<td>
								<s:textfield name="cardCustomer.contact" cssClass="{maxlength:20, required:true}"/>
								<span class="field_tipinfo">请输入经办人</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">证件类型</td>
							<td>
								<s:select name="cardCustomer.credType" list="certTypeList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择证件类型</span>
							</td>
							
							<td width="80" height="30" align="right">证件号码</td>
							<td>
								<s:textfield name="cardCustomer.credNo" cssClass="{digitOrLetter:true, required:true}" maxlength="32"/>
								<span class="field_tipinfo">请输入证件号码</span>
							</td>
						</tr>						
						<tr>
							<td width="80" height="30" align="right">证件有效期</td>
							<td>
								<s:textfield name="cardCustomer.credNoExpiryDate" onfocus="WdatePicker({dateFmt:'yyyyMMdd',autoPickDate:true,isShowOthers:false})"/>
								<span class="field_tipinfo">请选择日期，格式：yyyyMMdd</span>
							</td>
							<td width="80" height="30" align="right">职业</td>
							<td>
								<s:textfield name="cardCustomer.career" cssClass="{maxlength:16}"/>
								<span class="field_tipinfo"></span>
							</td>
						</tr>						
						<tr>
							<td width="80" height="30" align="right">国籍</td>
							<td>
								<s:textfield name="cardCustomer.nationality" cssClass="{maxlength:16}"/>
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right">邮编</td>
							<td>
								<s:textfield name="cardCustomer.zip" cssClass="{digit:true, maxlength:10}"/>
								<span class="field_tipinfo">请输入邮编</span>
							</td>
						</tr>						
						<tr>
							<td width="80" height="30" align="right">地址</td>
							<td>
								<s:textfield name="cardCustomer.address"/>
								<span class="field_tipinfo">请输入地址</span>
							</td>
							
							<td width="80" height="30" align="right">联系电话</td>
							<td>
								<s:textfield name="cardCustomer.phone" cssClass="{digit:true, maxlength:20}"/>
								<span class="field_tipinfo">请输入联系电话</span>
							</td>
						</tr>						
						<tr>
							<td width="80" height="30" align="right">传真号</td>
							<td>
								<s:textfield name="cardCustomer.fax" cssClass="{digit:true, maxlength:20}"/>
								<span class="field_tipinfo">请输入传真号</span>
							</td>							
							
							<td width="80" height="30" align="right">Email</td>
							<td>
								<s:textfield name="cardCustomer.email" cssClass="{email:true}"/>
								<span class="field_tipinfo">请输入Email</span>
							</td>
						</tr>	
						<tr>
							<td>&nbsp;</td>
							<td height="30" colspan="3">
								<s:hidden name="cardCustomer.cardCustomerCode"/>
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');clearCarBinError();" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/customerRebateMgr/cardCustomer/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDCUSTOMER_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>