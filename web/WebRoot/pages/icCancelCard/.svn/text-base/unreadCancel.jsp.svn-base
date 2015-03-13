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

		<f:js src="/js/biz/icCancelCard/add.js"/>
		<f:js src="/js/biz/icCancelCard/cancelCard.js"/>
		<f:js src="/js/cert/AC_ActiveX.js"/>
		<f:js src="/js/cert/AC_RunActiveContent.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				$('#id_feeAmt').val('0.0');
				$('#id_cardId').change(function(){CancelCard.loadCardInfo();});
			});
		</script>
		<object id="HiddenCOM" classid="clsid:681A7F73-27BA-4362-A899-897D94DAF08A"></object>
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
				<s:form action="unreadCancel.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption><span class="caption_title"><f:link href="/pages/icCancelCard/showAdd.do">IC卡可读卡销卡</f:link></span> | IC卡不可读卡或挂失销卡</caption>
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
								<s:textfield id="id_cardId" name="icCancelCardReg.cardId" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入卡号</span>
							</td>
							<td width="80" height="30" align="right">销卡类型</td>
							<td>
								<s:select id="idCancelType" name="icCancelCardReg.cancelType" list="cancelTypeList" 
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择销卡类型</span>
							</td>						
						</tr>
						<tr>
							<td width="80" height="30" align="right">旧卡电子现金余额</td>
							<td>
								<s:textfield id="id_lastBalance" name="icCancelCardReg.balanceAmt" readonly="true" cssClass="{required:true} readonly bigred"/>
								<span class="field_tipinfo">余额不能为空</span>
							</td>	
							<td width="80" height="30" align="right">销卡手续费</td>
							<td>
								<s:textfield id="id_feeAmt" name="icCancelCardReg.feeAmt" cssClass="{required:true}"/>
								<span class="field_tipinfo">手续费不能为空</span>
							</td>							
						</tr>
							<td width="80" height="30" align="right">持卡人姓名</td>
							<td>
								<s:textfield id="idCustName" name="icCancelCardReg.custName" />
								<span class="field_tipinfo">请输入姓名</span>
							</td>
							<td width="80" height="30" align="right">证件类型</td>
							<td>
								<s:select id="idCertType" name="icCancelCardReg.certType" list="certTypeList" headerKey="" 
									headerValue="--请选择--" listKey="value" listValue="name" />
								<span class="field_tipinfo">请选择类型</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">证件号码</td>
							<td>
								<s:textfield id="idCertNo" name="icCancelCardReg.certNo" />
								<span class="field_tipinfo">请输入证件号码</span>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield id="id_remark" name="icCancelCardReg.remark" />
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>
					</table>
					
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							
							<s:hidden id="id_serialNo" name="serialNo"/>

							<s:hidden id="id_randomSessionId" name="icCancelCardReg.randomSessionId" />
							<s:hidden id="id_signature" name="signature" />
							<s:hidden id="id_signatureReg" name="signatureReg" />

							<s:hidden id="id_error_msg" name="errorMsg" />
							
							<!-- 冲正用到的隐藏字段 -->
							<s:hidden id="id_reversal_refId" name="icCardReversal.refId" />
							
							<!-- 卡附加信息表中的隐藏字段 -->
							<s:hidden id="id_CustName_Hidden"/>
							<s:hidden id="id_CertType_Hidden"/>
							<s:hidden id="id_CertNo_Hidden"/>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="销卡" id="input_btn2"  name="ok"  onclick="return CancelCard.checkForm();"/>
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/icCancelCard/list.do?goBack=goBack')" class="ml30" />
							</td>
							<td></td><td></td>
						</tr>
					</table>
				</s:form>
				
				<span class="note_div">注意：</span>
				<ul class="showli_div">
					<li class="showli_div">1. 本操作页面只针对于不可读的IC卡或挂失卡。</li>
				</ul>
			</div>
				 <script type="text/javascript">
					AC_AX_RunContent( 'classid','clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80','id','FTUSBKEYCTRL','style','DISPLAY:none' ); //end AC code
				</script>
				<noscript><OBJECT classid="clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80" id="FTUSBKEYCTRL" style="DISPLAY:none"/></OBJECT></noscript>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>