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
		<f:js src="/js/paginater.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		function loadAcct(acctId, cardId){
			$('#idOutCardId').val(cardId);
			$('#idAcctPage').html(LOAD_IMAGE).load(CONTEXT_PATH + '/transAccReg/findSubAcct.do', {
				'acctId':acctId, "callId":callId()}, function(){
				$('#idInputPage').show();
				SysStyle.setDataGridStyle();
				SysStyle.setFormGridStyle();
				SysStyle.setButtonStyle();
				SysStyle.addRequiredStyle();
			});
		}

		/** 单选转入子账户类型 */
		function radioInSubacctType(){
			var outSubAcctType = $('input:radio[name="subAcctType"]:checked').val();
			if(outSubAcctType == "01" || outSubAcctType == "02"){
				$("[id^='inSubacctTypeTd']").show();
				$("[name='transAccReg.inSubacctType']").removeAttr("disabled");
				$("[name='transAccReg.inSubacctType'][value='"+outSubAcctType+"']").attr("checked","checked");
			}else{
				$("[id^='inSubacctTypeTd']").hide();
				$("[name='transAccReg.inSubacctType']").attr("disabled","disabled");
			}
		}
		/** 表单域校验 */
		function validateForm() {
			var signatureReg = $('#needSignatureReg').val();
			if (signatureReg == 'true' && !CheckUSBKey()) { // 证书签名失败
				return false;
			}
			if (!FormUtils.hasRadio('subAcctType')){
				alert('未选择子账户');
				return false;
			}
			$('#idSubAcctType').val(FormUtils.getRadioedValue('subAcctType'));
			return true;
		}
		
		function CheckUSBKey() {
			// 检查飞天的key
			var isOnline = document.all.FTUSBKEYCTRL.IsOnline;
			if (isOnline == 0) {
				if (FTDoSign()) { // 调用FT的签名函数
					return true;
				} else {
					return false;
				}
			} else {
				showMsg("请检查USB Key是否插入或USB Key是否正确！");
				return false;
			}
			return true;
		}

		/* 飞天的Key的签名函数 */
		function FTDoSign() {
			var SetCertResultRet = document.all.FTUSBKEYCTRL.SetCertResult; // 选择证书
			if (SetCertResultRet == 0) {
				var serialNumber = document.all.FTUSBKEYCTRL.GetCertSerial;
				$('#serialNo').val(serialNumber);
			} else {
				showMsg("选择证书失败");
				return false;
			}
			return true;
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="findCard.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">卡号</td>
							<td>
								<s:textfield id="cardId" name="cardId" cssClass="{required:true, digitOrLetter:true}"/>
								<span class="field_tipinfo">请输入卡号</span>
							</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok"/>
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<input style="margin-left:30px;" type="button" value="返回" name="escape" onclick="gotoUrl('/transAccReg/list.do')" class="ml30" />
							</td>
						</tr>	
					</table>
					<s:token/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<s:form id="searchForm" action="add.do" cssClass="validate">
			<!-- 数据列表区 -->
			<div class="tablebox">
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
						   <td align="center" nowrap class="titlebg">请选择</td>
						   <td align="center" nowrap class="titlebg">卡号</td>
						   <td align="center" nowrap class="titlebg">卡种类</td>
						   <td align="center" nowrap class="titlebg">账户ID</td>
						   <td align="center" nowrap class="titlebg">建档日期</td>
						   <td align="center" nowrap class="titlebg">卡状态</td>
						</tr>
					</thead>
					<s:iterator value="cardPage.data"> 
						<tr>
						  <td align="center" nowrap><input type="radio" name="outAccRadio" onclick="loadAcct('${acctId}', '${cardId}')" /></td>
						  <td align="center" nowrap>${cardId}</td>
						  <td align="center" nowrap>${cardClassName}</td>
						  <td align="center" nowrap>${acctId}</td>
						  <td align="center" nowrap>${createDate}</td>
						  <td align="center" nowrap>${cardStatusName}</td>
						</tr>
					</s:iterator>
				</table>
				<f:paginate name="cardPage"/>
			</div>
			
			<div class="tablebox" id="idAcctPage">
			</div>
			<s:hidden name="transAccReg.subacctType" id="idSubAcctType"/>
			<s:hidden name="transAccReg.outCardId" id="idOutCardId"/>
			<s:token name="_TOKEN_TRANS_ACC_REG_ADD"/>
			
			<s:hidden id='needSignatureReg' name="formMap.needSignatureReg" />
			<s:hidden id="serialNo" name="formMap.serialNo"/>
			<jsp:include flush="true" page="/common/certJsIncluded.jsp"></jsp:include>
		</s:form>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>