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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				// 选择分支机构
				Selector.selectBranch('id_branchName', 'id_branchCode', true, '01');
				
			   $('#id_templateId').blur(function(){
					var value = $(this).val();

					if(isEmpty(value)){ 
						$('#id_feeRateTr').hide();
						$('#idSectTbl').hide();
						return;
					}
					
					$.post(CONTEXT_PATH + '/fee/chlFee/getTempaltePara.do', {'chlFeeTemplate.templateId':value, 'callId':callId()}, 
						function(json){
							if (json.success){
								$('#id_feeContent').val(json.feeContentName);
								$('#id_costCycle').val(json.costCycleName);
								$('#id_feeType').val(json.feeTypeName);
								$('#id_feeRate').val(json.feeRate);
								$(':submit').removeAttr('disabled');

								//费率类型
								var feeType = json.feeType;
								if (feeType == '1' || feeType == '2') {
									$('#id_feeRateTr').show();
									$('#id_feeRate').removeAttr('disabled');
									$('#idSectTbl').hide();
									if(feeType == '1'){
										$('#id_feeRateTd_1').hide();
										$('#id_feeRateTd_2').show();
										$('#id_feeRateTr_').show();
									} else{
										$('#id_feeRateTd_1').show();
										$('#id_feeRateTd_2').hide();
										$('#id_feeRateTr_').hide();
									}
								} else {
									$('#id_feeRateTr').hide();
									$('#id_feeRateTr_').hide();
									$('#id_feeRate').attr('disabled', 'true');
									$('#idSectTbl').show();
								}
								
								if (feeType == '1') {
									getChinese();
									$('#id_feeRateTip').html('元');
								} else if (feeType == '2') {
									$('#id_feeRateTip').html('%');
								} else{
									// 分段金额
									var amtOrFeeRate;
									if(feeType == '3'){
										amtOrFeeRate='收费金额(元)';
									}
									// 分段比例、累进比例
									else if(feeType == '4' || feeType == '6'){
										amtOrFeeRate='费率(%)';
									}
	
									//费率内容
									var segment;
									feeContent = json.feeContent;
									// 预付资金、通用积分资金、 赠券资金
									if(feeContent == '1' || feeContent == '2' || feeContent == '3'){
										segment='分段金额上限';
									}
									// 专属积分交易笔数
									else if(feeContent == '4'){
										segment='分段交易笔数';
									}
									// 次卡可用次数
									else if(feeContent == '5'){
										segment='分段次数';
									}
									// 会员数、折扣卡会员数
									else if(feeContent == '6' || feeContent == '7'){
										segment='分段会员数';
									}
									//alert(segment+ " "+amtOrFeeRate+" "+value+ " "+feeType+" "+feeContent);
									$("#idSectTbl").load(CONTEXT_PATH + "/fee/chlFee/chlFeeTemplateList.do",{'chlFeeTemplate.templateId':value,'segment':segment,'amtOrFeeRate':amtOrFeeRate,'callId':callId()});
								}
							} else {
								showMsg(json.error);
								$('#id_feeContent').val('');
								$('#id_costCycle').val('');
								$('#id_feeType').val('');
								$('#id_feeRate').val('');
								$(':submit').attr('disabled', 'true');
							}
						}, 'json');
				});
			});
			
			function submitForm(){
				$("#inputForm").submit();
			}
			
			// 根据金额获得中文大写
			function getChinese(){
				var feeRate = $('#id_feeRate').val(); 
				var chinese = AmountInWords(feeRate);
				$("#id_feeRateToChinese").html(chinese);
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="addTemplate.do" id="inputForm" cssClass="validate">
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<caption>${ACT.name}</caption>
					<tr>
						<td width="80" height="30" align="right">分支机构</td>
						<td>
							<s:hidden id="id_branchCode" name="chlFee.branchCode" cssClass="{required:true}"></s:hidden>
							<s:textfield  id="id_branchName" cssClass="{required:true}"></s:textfield>
							<span class="field_tipinfo">请选择分支机构</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">请选择模板</td>
						<td>
							<s:select id="id_templateId" name="chlFeeTemplate.templateId" list="chlFeeTemplateList" headerKey="" headerValue="--请选择--" listKey="templateId" listValue="templateName" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择模板</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费内容</td>
						<td>
							<s:textfield id="id_feeContent" name="chlFee.feeContent" cssClass="readonly" readonly="true"></s:textfield>
							<span class="field_tipinfo">请选择计费内容</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费周期</td>
						<td>
							<s:textfield name="chlFee.costCycle" id="id_costCycle"	cssClass="readonly" readonly="true"></s:textfield>
							<span class="field_tipinfo">预付资金、通用积分资金、赠券资金默认为"按年"，其他为"按月"</span>	
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费类型</td>
						<td>
							<s:textfield id="id_feeType" name="chlFee.feeType" cssClass="readonly" readonly="true"></s:textfield>
							<span class="field_tipinfo">请选择计费类型</span>
						</td>
					</tr>
					<tr id="id_feeRateTr" style="display: none;">
						<td id="id_feeRateTd_1" width="80" height="30" align="right">费率</td>
						<td id="id_feeRateTd_2" width="80" height="30" align="right">金额</td>
						<td>
							<s:textfield id="id_feeRate" name="feeRateComma" onkeyup="getChinese()" onblur="getChinese()" 
								cssClass="readonly" readonly="true" >
							</s:textfield><span id="id_feeRateTip"></span>
							<span class="field_tipinfo">最大为10位整数，4位小数</span>
						</td>
					</tr>
					<tr id="id_feeRateTr_" style="display:none">
						<td width="80" height="30" align="right">大写：</td>
						<td colspan="3">
							<span id="id_feeRateToChinese" style="font-size: 16px;"></span>
						</td>
					</tr>
				</table>
				<div id="idSectTbl" style="display: none;"></div>
				<table class="form_grid" width="80%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td width="80" height="30" align="right"></td>
						<td height="30" colspan="3"> 
							<input type="button" value="提交" id="input_btn2"  name="ok" onclick="return submitForm()"/>
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/chlFee/showAdd.do')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_CHLFEE_ADD"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>