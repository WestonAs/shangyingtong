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

		<f:js src="/js/biz/icCardActive/add.js"/>
		<f:js src="/js/cert/AC_ActiveX.js"/>
		<f:js src="/js/cert/AC_RunActiveContent.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
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
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td height="30" align="center" >请选择刷卡方式：
								<input type="radio" id="idForTouch" name="touchType" value="0" checked="checked"/><label for="idForTouch">接触式</label>
								<input type="radio" id="idForUntouch" name="touchType" value="1"/><label for="idForUntouch">非接触式</label>
							</td>
							<td id="id_touchType">请将卡放入读卡器：
								<input type="button" value="读取卡片信息" id="input_btn2"  name="ok"  onclick="IcCardActive.readCardInfo();"/>
							</td>
						</tr>
					</table>
					<table id="idDepositTbl" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0" >
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
								<s:textfield id="id_cardId" name="icCardActive.cardId" readonly="true" cssClass="{required:true} readonly"/>
							</td>
							<td width="80" height="30" align="right">卡种类</td>
							<td>
								<s:hidden id="id_cardClass" name="icCardActive.cardClass" />
								<s:textfield id="id_cardClassName" name="cardClassName" readonly="true" cssClass="{required:true} readonly"/>
							</td>
							<td width="80" height="30" align="right">卡BIN</td>
							<td>
								<s:textfield id="id_cardBin" readonly="true" cssClass="readonly"/>
							</td>							
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡类型号</td>
							<td>
								<s:textfield id="id_cardSubClass" name="icCardActive.cardSubClass" readonly="true" cssClass="{required:true} readonly"/>
							</td>
							<td width="80" height="30" align="right">卡类型名</td>
							<td>
								<s:textfield id="id_cardSubClassName" readonly="true" cssClass="{required:true} readonly"/>
							</td>							
							<td width="80" height="30" align="right">发卡机构号</td>
							<td>
								<s:textfield id="id_cardBranch" name="icCardActive.cardBranch" readonly="true" cssClass="{required:true} readonly"/>
							</td>							
						</tr>
						<tr>
							<td width="80" height="30" align="right">余额</td>
							<td>
								<s:textfield id="id_lastBalance" name="icCardActive.lastBalance" readonly="true" cssClass="{required:true} readonly bigred"/>
							</td>							
							<td width="80" height="30" align="right">补登账户金额</td>
							<td>
								<s:textfield id="id_avlBal" name="avlBal" readonly="true" cssClass="{required:true} readonly bigred"/>
							</td>						
							<td width="80" height="30" align="right">发卡机构名</td>
							<td>
								<s:textfield id="id_cardBranchName" readonly="true" cssClass="readonly"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡内余额上限</td>
							<td>
								<s:textfield id="id_balanceLimit" name="balanceLimit" readonly="true" cssClass="{required:true} readonly bigred"/>
							</td>						
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield id="id_remark" name="icCardActive.remark" />
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
							
							<s:hidden id="id_cardSn" name="icCardActive.cardSn"/>
							<s:hidden id="id_serialNo" name="serialNo"/>
							<s:hidden id="id_ARQC" name="icCardActive.arqc"/>
							<s:hidden id="id_AQDT" name="icCardActive.aqdt"/>

							<s:hidden id="id_randomSessionId" name="icCardActive.randomSessionid" />
							<s:hidden id="id_signature" name="icCardActive.signature" />
							<s:hidden id="id_signatureReg" name="signatureReg" />

							<s:hidden id="id_error_msg" name="errorMsg" />
							
							<!-- 冲正用到的隐藏字段 -->
							<s:hidden id="id_reversal_amt" name="icEcashReversal.amt" />
							<s:hidden id="id_reversal_cardId" name="icEcashReversal.cardId" />
							<s:hidden id="id_reversal_depositBatchId" name="icEcashReversal.depositBatchId" />
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="激活" id="input_btn2"  name="ok"  onclick="return IcCardActive.checkForm();"/>
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/icCardActive/list.do?goBack=goBack')" class="ml30" />
							</td>
							<td></td><td></td>
						</tr>
					</table>
				</s:form>
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