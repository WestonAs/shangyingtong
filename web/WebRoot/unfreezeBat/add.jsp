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
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/date/WdatePicker.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#id_strCardId').focus();
			$('#input_btn2').attr('disabled', 'true');
			
			$('#id_strCardId').change(checkCardIds);
			$('#id_CardCount').change(checkCardIds);
		});

		

		function checkCardIds() {
			var cardId = $('#id_strCardId').val();
			var cardCount = $('#id_CardCount').val();
			if(isEmpty(cardId) || isEmpty(cardCount)){
				return false;
			}
			if(!checkCardNum(cardId)){
				showMsg('起始卡号必须为18位或19位数字');
				return false;
			}
			
			$.post(CONTEXT_PATH + '/unfreezeBat/checkCardId.do', {'cardId':cardId, 'cardCount':cardCount, 'callId':callId()},
				function(json){
					if (json.success){
						$('#id_endCardId').val(json.endCardId);
						$('#idSubAcctType').load(CONTEXT_PATH + '/unfreeze/getSubAcctList.do',{'unfreezeReg.cardId':cardId, 'callId':callId()});
						$('#input_btn2').removeAttr('disabled');
					} else {
						showMsg(json.errorMsg);
						$('#input_btn2').attr('disabled', 'true');
					}
				}, 
				'json'
			);
		}

		// 检查录入数据完整性
		function checkField(){
			// 卡号有效性检查
			var cardId = $('#id_strCardId').val();
			var cardCount = $('#id_CardCount').val();
			
			if(isEmpty(cardId)){
				showMsg("起始卡号不能为空！");
				return false;
			}
			if(isEmpty(cardCount)){
				showMsg("卡连续张数不能为空！");
				return false;
			}
			hideMsg();
			return true;
		}

		function submitDepositForm(){
			try{
				if(!checkField()){
					return false;
				}
				
				hideMsg();
				if($('#inputForm').validate().form()){
					$('#inputForm').submit();
					$('#input_btn2').attr('disabled', 'true');
					$("#loadingBarDiv").css("display","inline");
					$("#contentDiv").css("display","none");
				}
			}catch(err){
				txt="本页中存在错误。\n\n 错误描述：" + err.description + "\n\n"
				showMsg(txt)
			}
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
		<div  id="contentDiv"  class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">起始卡号</td>
							<td>
							<s:textfield id="id_strCardId" name="unfreezeReg.startCard" onkeyup="this.value=this.value.replace(/\D/g,'')" 
							onafterpaste="this.value=this.value.replace(/\D/g,'')" cssClass="{required:true}"  maxlength="19" />
							<span class="error_tipinfo">卡号不正确</span>
							</td>
							<td width="80" height="30" align="right">卡连续数</td>
							<td>
								<s:textfield id="id_CardCount" name="unfreezeReg.cardNum" cssClass="{required:true, digit:true}"/>
								<span class="field_tipinfo">请输入整数</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">结束卡号</td>
							<td>
							<s:textfield id="id_endCardId" name="unfreezeReg.endCard" cssClass="{required:true, digit:true} readonly" maxlength="19" readonly="true" />
								<span class="field_tipinfo" id=""></span>
								<span class="error_tipinfo">卡号不正确</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">子账户类型</td>
							<td>
							<select name="unfreezeReg.subacctType" id="idSubAcctType"	 class="{required:true}"></select>
							<span class="field_tipinfo">请先输入卡号，才能选择账户类型</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">新增解付金额</td>
							<td>
							<s:textfield name="unfreezeReg.unfznAmt" cssClass="{required:true, num:true, decimal:'14,2'}" maxlength="14"></s:textfield>
							<span class="field_tipinfo">小于等于冻结余额</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
							<s:textfield name="unfreezeReg.remark" />
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok"  onclick="return submitDepositForm();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/unfreezeBat/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_BAT_UNFREEZE_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
				<span class="note_div">注释</span>
				<ul class="showli_div">
				<li class="showli_div">只有正常（已激活）状态的卡才能解付。</li>
				<li class="showli_div">批量解付的卡必须为操作机构发行的卡或者所售的卡</li>
				<li class="showli_div">起始卡号和结束卡号位数要一致，卡BIN也需一致。</li>
				<li class="showli_div">起始卡号和结束卡号之间不能超过1000张。</li>
				<li class="showli_div">新增解付金额是指每张卡解付的金额，如一批次卡中有卡不足余额解付的，整批次处理失败。</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>