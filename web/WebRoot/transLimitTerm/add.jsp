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
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/validate.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<style type="text/css">
			#tranEnableDiv table table .headcell { text-align: right; width:80px; }
		</style>
		
		<script>
			$(function(){
				if('${loginRoleType}' == '00'){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20', bundSelect);
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20', bundSelect, '', '${loginBranchCode}');
				}
				$("#allMerchs").click(allMerchs);
				$("#allTerminals").click(allTerminals);
				$("#allCardBins").click(allCardBins);
				$("input[name^='tranEnableMap']").click(alternative);

				$('#input_btn2').click(function(){
					if($('#inputForm').validate().form()){
						$('#inputForm').submit();
						$('#input_btn2').attr('disabled', 'true');
						$("#loadingBarDiv").show();
						$("#contentDiv").css("display","none");
					}
				});
			});
			
			/** 绑定 弹出界面 选择商户、卡BIN、终端 */
			function bundSelect(){
				$('#idMerchId').val("");
				$('#idMerchId_sel').val("");
				$('#idMerchId_sel').removeMulitselector();
				
				$('#idCardBinNo').val("");
				$('#cardBinNo_sel').val("");
				$('#cardBinNo_sel').removeMulitselector();
				
				var cardBranchNo = $("#idBranchCode").val();
				if(!isEmpty(cardBranchNo)){
					Selector.selectMerch('idMerchId_sel', 'idMerchId', false, cardBranchNo, '', '', '', bundSelectTerminals);
					Selector.selectCardBin('cardBinNo_sel', 'idCardBinNo', false, cardBranchNo);
				}
				
				bundSelectTerminals();
			}

			/** 绑定 弹出界面 选择终端 */
			function bundSelectTerminals(){
				$('#terminals').val("");
				$('#terminals_sel').val("");
				$('#terminals_sel').removeMulitselector();
				
				var cardBranchNo = $("#idBranchCode").val();
				var merchIds = $("#idMerchId").val();
				if(!isEmpty(cardBranchNo) || !isEmpty(merchIds)){
					Selector.selectTerminal('terminals_sel', 'terminals', false, cardBranchNo, merchIds);
				}
			}

			/** 商户全选 */
			function allMerchs(){
				if($("#allMerchs").attr("checked")){//商户全选
					$("#idMerchId").val("");
					enableOrDisableElmt($("#idMerchId"), false);
					$("#idMerchId_sel").val("");
					enableOrDisableElmt($("#idMerchId_sel"), false);
				}else{
					enableOrDisableElmt($("#idMerchId"), true);
					enableOrDisableElmt($("#idMerchId_sel"), true);
				}
				bundSelectTerminals();
			}
			
			/** 终端全选 */
			function allTerminals(){
				if($("#allTerminals").attr("checked")){//终端全选
					$("#terminals").val("");
					enableOrDisableElmt($("#terminals"), false);
					$("#terminals_sel").val("");
					enableOrDisableElmt($("#terminals_sel"), false);
				}else{
					enableOrDisableElmt($("#terminals"), true);
					enableOrDisableElmt($("#terminals_sel"), true);
				}
			}
			
			/** 卡BIN全选 */
			function allCardBins(){
				if($("#allCardBins").attr("checked")){//终端全选
					$("#idCardBinNo").val("");
					enableOrDisableElmt($("#idCardBinNo"), false);
					$("#cardBinNo_sel").val("");
					enableOrDisableElmt($("#cardBinNo_sel"), false);
				}else{
					enableOrDisableElmt($("#idCardBinNo"), true);
					enableOrDisableElmt($("#cardBinNo_sel"), true);
				}
			}

			
			function allCheck(enable){
				if(enable==1){//允许
					$("input[name^='tranEnableMap'][value='0']").removeAttr('checked');
					$("input[name^='tranEnableMap'][value='1']").attr('checked','checked');
				}else if(enable==0){//禁止
					$("input[name^='tranEnableMap'][value='0']").attr('checked','checked');
					$("input[name^='tranEnableMap'][value='1']").removeAttr('checked');
				}else if(enable==-1){//取消
					$("input[name^='tranEnableMap'][value='0']").removeAttr('checked');
					$("input[name^='tranEnableMap'][value='1']").removeAttr('checked');
				}
			}

			/** 交易控制项二选一或不选 */
			function alternative(){
				var checkBox = $(this);
				var name = checkBox.attr('name');
				var val = checkBox.val();
				
				var checkBox2 = $("input[name='"+name+"']").not(checkBox);
				var val2 = checkBox2.val();
				
				if(checkBox.attr("checked") && checkBox2.attr("checked")){//两项都选中，则把第二个项取消
					checkBox2.removeAttr('checked');
				}
			}

			function clearAllFields(){
				$("input[type='text']").val("");
				$("input[type='hidden']").val("");
				enableOrDisableElmt($("input[type='hidden']"), true);
				enableOrDisableElmt($("input[type='text']"), true);
				
				$("input[type='text'][id!='idBranchCode_sel']").removeMulitselector();
				FormUtils.reset('inputForm');
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<jsp:include flush="true" page="/common/loadingBarDiv.jsp"></jsp:include>
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
						<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
							
							<tr id="id_branchCode_center_tr">
								<td width="80" height="30" align="right">发卡机构</td>
								<td>
									<s:hidden id="idBranchCode" name="transLimitTerm.cardIssuer" cssClass="{required:true}"/>
									<s:textfield id="idBranchCode_sel" cssClass="{required:true}"/>
									<span class="field_tipinfo">请选择发卡机构</span>
								</td>
								<td width="80" height="30" align="right">商户</td>
								<td>
									<s:hidden id="idMerchId" name="formMap.merchNos" cssClass="{required:true}"/>
									<s:textfield id="idMerchId_sel" cssClass="{required:true}"/>
									<span class="field_tipinfo">选择</span>
									<label title="所选发卡机构下的全部商户"><input type="checkbox"  id="allMerchs" name="formMap.allMerchs" value="true" />商户全选</label>
								</td>
							</tr>
							<tr>
								<td width="80" height="30" align="right">卡BIN</td>
								<td>
									<s:hidden id="idCardBinNo" name="formMap.cardBins" cssClass="{required:true}"/>
									<s:textfield id="cardBinNo_sel" cssClass="{required:true}"/>
									<span class="field_tipinfo">选择</span>
									<label title="所选发卡机构下的全部卡bin">
										<input type="checkbox"  id="allCardBins" name="formMap.allCardBins" value="true" />卡BIN全选
									</label>
								</td>
								<td width="80" height="30" align="right">终端</td>
								<td>
									<s:hidden id="terminals" name="formMap.termNos" cssClass="{required:true}"/>
									<s:textfield id="terminals_sel" cssClass="{required:true}"/>
									<span class="field_tipinfo">选择</span>
									<label title="所选商户下的全部终端"><input type="checkbox"  id="allTerminals" name="formMap.allTerminals" value="true" />终端全选</label>
								</td>
							</tr>
							<tr>
								<td colspan="11">
									<div class="userbox" id="tranEnableDiv">
										<div>
											<input type="button" onclick="allCheck(1);" value="全部允许"/> &nbsp;&nbsp;&nbsp;&nbsp;
											<input type="button" onclick="allCheck(0);" value="全部禁止"/>&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="button" onclick="allCheck(-1);" value="全部取消"/>&nbsp;&nbsp;&nbsp;&nbsp;
										</div>
										<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
											<tr>
												<td><b>1、预付业务：</b></td>
												<td colspan="10">
													<table class="detail_grid" width="98%" border="0" cellspacing="0" cellpadding="1">
														<tr>
															<td>
																部分消费
															</td>
															<td>
																<s:checkboxlist name="tranEnableMap['11']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
															<td>
																过期消费
															</td>
															<td>
																<s:checkboxlist name="tranEnableMap['13']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
															<td>转账</td>
															<td>
																<s:checkboxlist name="tranEnableMap['3']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
															<td>银行卡充值</td>
															<td>
																<s:checkboxlist name="tranEnableMap['4']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
														</tr>
														<tr>
															<td>现金充值</td>
															<td colspan="11">
																<s:checkboxlist name="tranEnableMap['2']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
										<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
											<tr>
												<td><b>2、积分业务：</b></td>
												<td colspan="10">
													<table class="detail_grid" width="98%" border="0" cellspacing="0" cellpadding="1">
														<tr>
															<td>消费赠送</td>
															<td>
																<s:checkboxlist name="tranEnableMap['80']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
															<td>积分消费</td>
															<td>
																<s:checkboxlist name="tranEnableMap['16']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
															<td>积分兑换礼品</td>
															<td>
																<s:checkboxlist name="tranEnableMap['22']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
															<td>积分返利</td>
															<td>
																<s:checkboxlist name="tranEnableMap['20']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
														</tr>
														<tr>
															<td>积分兑换赠券</td>
															<td>
																<s:checkboxlist name="tranEnableMap['21']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
															<td>扣减积分</td>
															<td colspan="11">
																<s:checkboxlist name="tranEnableMap['44']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
										<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
											<tr>
												<td><b>3、次卡业务：</b></td>
												<td colspan="10">
													<table class="detail_grid" width="98%" border="0" cellspacing="0" cellpadding="1">
														<tr>
															<td>次卡消费</td>
															<td>
																<s:checkboxlist name="tranEnableMap['12']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
										<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
											<tr>
												<td><b>4、单机版业务：</b></td>
												<td colspan="10">
													<table class="detail_grid" width="98%" border="0" cellspacing="0" cellpadding="1">
														<tr>
															<td>制卡</td>
															<td>
																<s:checkboxlist name="tranEnableMap['52']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
															<td>售卡</td>
															<td>
																<s:checkboxlist name="tranEnableMap['53']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
															<td>卡挂失</td>
															<td>
																<s:checkboxlist name="tranEnableMap['55']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
															<td>卡解挂</td>
															<td>
																<s:checkboxlist name="tranEnableMap['63']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
														</tr>
														<tr>
															<td>卡延期</td>
															<td>
																<s:checkboxlist name="tranEnableMap['54']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
															<td>卡销户</td>
															<td>
																<s:checkboxlist name="tranEnableMap['56']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
															<td>卡冻结</td>
															<td>
																<s:checkboxlist name="tranEnableMap['57']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
															<td>卡解冻</td>
															<td>
																<s:checkboxlist name="tranEnableMap['58']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
														</tr>
														<tr>
															<td>缴费</td>
															<td>
																<s:checkboxlist name="tranEnableMap['59']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
															<td>换卡</td>
															<td colspan="11">
																<s:checkboxlist name="tranEnableMap['62']" list="#{'1':'允许',  '0':'禁止'}"> </s:checkboxlist>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							
							<tr style="margin-top: 30px;">
								<td width="80" height="30" align="right">&nbsp;</td>
								<td height="30" colspan="3">
									<input type="submit" value="提交" id="input_btn2"  name="ok" />
									<input type="button" value="清除" name="escape" onclick="clearAllFields();" class="ml30" />
									<input type="button" value="返回" name="escape" onclick="gotoUrl('/transLimitTerm/list.do?goBack=goBack')" class="ml30" />
								</td>
							</tr>
						</table>
					</div>
					<s:token name="_TOKEN_TRANSLIMITTERM_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
				<span class="note_div">注释</span>
				<ul class="showli_div">
				<li class="showli_div">对于不存在的POS交易控制记录，则添加记录；如果其中某一交易控制项目，既没有选择允许，也没有选择禁止，则默认为禁止该交易控制项。</li>
				<li class="showli_div">对于已存在的POS交易控制记录，则更新记录；如果其中某一交易控制项目，既没有选择允许，也没有选择禁止，则表示该记录的该交易控制项不做更改。</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>