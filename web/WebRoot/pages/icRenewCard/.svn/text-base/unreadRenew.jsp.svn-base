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

		<f:js src="/js/biz/icRenewCard/add.js"/>
		<f:js src="/js/biz/icRenewCard/renewCard.js"/>
		<f:js src="/js/cert/AC_ActiveX.js"/>
		<f:js src="/js/cert/AC_RunActiveContent.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				$('#id_cardId').change(function(){RenewCard.loadCardInfo();});
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
				<s:form action="unreadRenew.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption><span class="caption_title"><f:link href="/pages/icRenewCard/showAdd.do">IC卡可读卡换卡</f:link></span> | IC卡不可读卡或挂失换卡</caption>
						<tr>
							<td height="30" align="center" >请选择刷卡方式：
								<input type="radio" id="idForTouch" name="touchType" value="0" checked="checked"/><label for="idForTouch">接触式</label>
								<input type="radio" id="idForUntouch" name="touchType" value="1"/><label for="idForUntouch">非接触式</label>
							</td>
							<td id="id_touchType">请将卡放入读卡器：
								<input type="button" value="读取新卡卡号" id="input_btn3"  name="ok"  onclick="IcRenewCard.readNewCardId();"/>
							</td>
						</tr>
					</table>
					<table id="idDepositTbl" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0" >
						
						<tr>
							<td width="80" height="30" align="right">旧卡卡号</td>
							<td>
								<s:textfield id="id_cardId" name="icRenewCardReg.oldCardId" cssClass="{required:true, digit:true}" maxlength="19" />
								<span class="field_tipinfo">请输入旧卡卡号</span>
							</td>
							<td width="80" height="30" align="right">新卡卡号</td>
							<td>
								<s:textfield id="id_newCardId" name="icRenewCardReg.newCardId" cssClass="{required:true, digit:true}" maxlength="19" />
								<span class="field_tipinfo">请输入新卡卡号</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">换卡类型</td>
							<td>
								<s:select id="idRenewType" name="icRenewCardReg.renewType" list="renewTypeList" 
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择换卡类型</span>
							</td>
							<td width="80" height="30" align="right">旧卡电子现金余额</td>
							<td>
								<s:textfield id="id_lastBalance" name="icRenewCardReg.oldBalanceAmt" readonly="true" cssClass="{required:true} readonly bigred"/>
								<span class="field_tipinfo">余额不能为空</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">持卡人姓名</td>
							<td>
								<s:textfield id="idCustName" name="icRenewCardReg.custName" />
								<span class="field_tipinfo">请输入姓名</span>
							</td>
							<td width="80" height="30" align="right">证件类型</td>
							<td>
								<s:select id="idCertType" name="icRenewCardReg.certType" list="certTypeList" 
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" />
								<span class="field_tipinfo">请选择类型</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">证件号码</td>
							<td>
								<s:textfield id="idCertNo" name="icRenewCardReg.certNo" />
								<span class="field_tipinfo">请输入号码</span>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="renewCardReg.remark" />
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>
					</table>
					
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<!-- IC卡读卡器用到的隐藏字段 -->
							<s:hidden id="id_dataLength"/>
							<s:hidden id="id_pdol"/>
							<s:hidden id="id_AIP"/>
							<s:hidden id="id_AFL"/>
							<s:hidden id="id_CDOL1Data"/>
							<s:hidden id="id_CDOL2"/>
						
							<s:hidden id="id_serialNo" name="serialNo"/>
							<s:hidden id="id_randomSessionId" name="icRenewCardReg.randomSessionId" />
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
								<input type="button" value="换卡" id="input_btn2" name="ok" onclick="return RenewCard.checkForm();"/>
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/icRenewCard/list.do?goBack=goBack')" class="ml30" />
							</td>
							<td></td><td></td>
						</tr>
					</table>
				</s:form>
				
				<span class="note_div">注意：</span>
				<ul class="showli_div">
					<li class="showli_div">1. 本操作页面只针对于不可读的IC卡或挂失卡。</li>
					<li class="showli_div">2. 新卡的卡号可通过读卡器读取，也可以手工录入。</li>
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