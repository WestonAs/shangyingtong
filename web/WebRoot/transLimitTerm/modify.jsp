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
		
		<style type="text/css">
			#tranEnableDiv table table .headcell { text-align: right; width:80px; }
		</style>
		
		<script>
			function allRadioCheck(enable){
				if(enable){
					$("input[name^='tranEnableMap'][value='1']").attr('checked','checked')
				}else{
					$("input[name^='tranEnableMap'][value='0']").attr('checked','checked')
				}
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify" id="inputForm" cssClass="validate">
					<div>
						<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
							<tr>
								<td width="80" height="30" align="right">发卡机构</td>
								<td>
									<s:hidden name="transLimitTerm.cardIssuer" cssClass="{required:true}"/>
									<input type="text" value="${fn:branch(transLimitTerm.cardIssuer)}" class="readonly {required:true}" readonly="readonly"/>
									<span class="field_tipinfo"></span>
								</td>
								<td width="80" height="30" align="right">商户</td>
								<td> 
									<s:hidden name="transLimitTerm.merNo" />
									<input type="text" value="${fn:merch(transLimitTerm.merNo)}" class="readonly {required:true}" readonly="readonly"/>
									<span class="field_tipinfo"></span>
								</td>
							</tr>
							<tr>
								<td width="80" height="30" align="right">卡BIN</td>
								<td> 
									<s:textfield name="transLimitTerm.cardBin" cssClass="readonly {required:true}" readonly="true"/>
									<span class="field_tipinfo"></span>
								</td>
								<td width="80" height="30" align="right">终端号</td>
								<td> 
									<s:textfield name="transLimitTerm.termNo" cssClass="readonly {required:true}" readonly="true"/>
									<span class="field_tipinfo"></span>
								</td>
							</tr>
							<tr>
								<td colspan="11">
									<div class="userbox" id="tranEnableDiv">
										<div>
											<input type="button" onclick="allRadioCheck(true);" value="全部允许"/>
											<input type="button" onclick="allRadioCheck(false);" value="全部禁止"/>
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
																<s:radio name="tranEnableMap['11']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
															<td>
																过期消费
															</td>
															<td>
																<s:radio name="tranEnableMap['13']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
															<td>转账</td>
															<td>
																<s:radio name="tranEnableMap['3']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
															<td>银行卡充值</td>
															<td>
																<s:radio name="tranEnableMap['4']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
														</tr>
														<tr>
															<td>现金充值</td>
															<td colspan="11">
																<s:radio name="tranEnableMap['2']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
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
																<s:radio name="tranEnableMap['80']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
															<td>积分消费</td>
															<td>
																<s:radio name="tranEnableMap['16']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
															<td>积分兑换礼品</td>
															<td>
																<s:radio name="tranEnableMap['22']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
															<td>积分返利</td>
															<td>
																<s:radio name="tranEnableMap['20']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
														</tr>
														<tr>
															<td>积分兑换赠券</td>
															<td>
																<s:radio name="tranEnableMap['21']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
															<td>扣减积分</td>
															<td colspan="11">
																<s:radio name="tranEnableMap['44']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
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
																<s:radio name="tranEnableMap['12']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
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
																<s:radio name="tranEnableMap['52']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
															<td>售卡</td>
															<td>
																<s:radio name="tranEnableMap['53']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
															<td>卡挂失</td>
															<td>
																<s:radio name="tranEnableMap['55']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
															<td>卡解挂</td>
															<td>
																<s:radio name="tranEnableMap['63']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
														</tr>
														<tr>
															<td>卡延期</td>
															<td>
																<s:radio name="tranEnableMap['54']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
															<td>卡销户</td>
															<td>
																<s:radio name="tranEnableMap['56']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
															<td>卡冻结</td>
															<td>
																<s:radio name="tranEnableMap['57']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
															<td>卡解冻</td>
															<td>
																<s:radio name="tranEnableMap['58']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
														</tr>
														<tr>
															<td>缴费</td>
															<td>
																<s:radio name="tranEnableMap['59']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
															</td>
															<td>换卡</td>
															<td colspan="11">
																<s:radio name="tranEnableMap['62']" list="#{'1':'允许',  '0':'禁止'}"> </s:radio>
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
									<%--
									<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
									 --%>
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
				<li class="showli_div">如果没有设置POS终端交易控制，默认为禁止。</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>