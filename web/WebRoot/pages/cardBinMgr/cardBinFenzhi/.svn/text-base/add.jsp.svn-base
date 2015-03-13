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
		$(function(){
			$('#id_strBinNo').blur(function(){
				var value = $(this).val();
			});
			
			$('#id_binCount').blur(function(){
				var value = $(this).val();
			});
		});
		
		AddFenzhiCardBin = {
			/**
			 * 检查起始卡BIN
			 */
			checkStrBinNo: function(strBinNo){
				var flag = false;
				
				if (isEmpty(strBinNo) || strBinNo.length < 6){
					return;
				}
				if (!validator.isDigit(strBinNo)) {
					return;
				}
			}
			$.ajax({
				url: CONTEXT_PATH + '/addCardBin/isExistBinNo.do',
				data: {'cardBinReg.binNo':strBinNo},
				cache: false,
				async: false,
				type: 'POST',
				dataType: 'json',
				success: function(result){
					if (result.isExist){
						flag = false;
						$('#idCardBinNo_field').html('该卡BIN已存在，请更换').addClass('error_tipinfo').show();
						//$('#idCardBinNo').focus();
						$('#input_btn2').attr('disabled', 'true');
					} else {
						flag = true;
						$('#idCardBinNo_field').removeClass('error_tipinfo').html('卡BIN输入正确');
						$('#input_btn2').removeAttr('disabled');
					}
				}
			});
			return flag;
		}
		function checkStrBinNo(){
			
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
						<s:if test="false">
						<tr>
							<td width="80" height="30" align="right">所属机构</td>
							<td>
								<s:select id="id_issType" name="fenzhiCardBinReg.type" list="issTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择机构</span>
							</td>
						</tr>
						</s:if>
						<tr>
							<td width="80" height="30" align="right">起始卡BIN</td>
							<td>
								<s:textfield id="id_strBinNo" name="fenzhiCardBinReg.strBinNo" cssClass="{required:true, minlength:6}" maxlength="6"/>
								<span class="field_tipinfo">请输入6位数字</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡BIN数量</td>
							<td>
								<s:textfield id="id_binCount" name="fenzhiCardBinReg.binCount" cssClass="{required:true, num:true}" maxlength="6"/>
								<span class="field_tipinfo">请输入整数</span>
							</td>
						</tr>
						<tr>
							<!-- 申请机构必须为自己所管理的下级分支机构，即：23级分支机构 -->
							<td width="80" height="30" align="right">申请机构</td>
							<td>
								<s:select id="id_appBranch" name="fenzhiCardBinReg.appBranch" list="branchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择申请机构</span>
							</td>
						</tr>

						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/cardBinMgr/cardBinFenzhi/list.do')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARD_FENZHI_REG_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>