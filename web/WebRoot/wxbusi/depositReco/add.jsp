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
		
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
		
		function checkField(){			
			return true;
		}
		
		function submitForm(){
			try{
				$('#inputForm').submit();
	
				$("#loadingBarDiv").css("display","inline");
				$("#contentDiv").css("display","none");
				return true;
			}catch(err){
	 			txt="本页中存在错误。\n\n 错误描述：" + err.description + "\n\n"
				alert(txt)
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
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<!-- 
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
								<s:textfield name="wxDepositReconReg.cardId" cssClass="{required:true, digit:true}" maxlength="19"/>
								<span class="field_tipinfo">请输入卡号</span>
							</td>							
						</tr>
						 -->
						
						<tr>
							<td width="80" height="30" align="right">外部卡号</td>
							<td>
								<s:textfield name="wxDepositReconReg.extCardId" cssClass="{required:true, digit:true}" maxlength="19"/>
								
								<span class="field_tipinfo">输入网付宝手机号</span>								
							</td>
						</tr>

						<tr>
							<td width="80" height="30" align="right">对账明细ID</td>
							<td>
								<s:textfield name="wxDepositReconReg.reconDetailId" cssClass="{required:true, digit:true}" maxlength="19"/>
								
								<span class="field_tipinfo">输入对账结果明细ID，为数字</span>								
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">操作类型</td>
							<td>
								<s:select name="wxDepositReconReg.opeType" list="opeTypeList" 
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择操作类型</span>						
							</td>													
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">调整金额</td>
							<td>
								<s:textfield name="wxDepositReconReg.transAmt" cssClass="{required:true, num:true, decimal:'12,2'} bigred" cssStyle="width:110px;" maxlength="14"/><span>元</span>	
								<span class="field_tipinfo">最大10位整数，2位小数</span>	
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="wxDepositReconReg.remark" />								
							</td>													
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="提交" id="input_btn2" onclick="submitForm();" name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/wxbusi/depositReco/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_WX_RETRANSCARDREG_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>