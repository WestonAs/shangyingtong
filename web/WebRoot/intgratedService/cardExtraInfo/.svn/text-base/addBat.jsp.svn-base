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
			var showCenter = $('#id_showCenter').val();
			var showCard = $('#id_showCard').val();
			var showFenZhi = $('#id_showFenZhi').val();
			var parent = $('#id_parent').val();
			
			//营运中心、营运中心部门
			if(showCenter=='true' || showFenZhi=='true'){
				// 营运中心、中心部门
				if(showCenter=='true'){
					Selector.selectBranch('branchName', 'id_cardIssuer_1', true, '20');
				}
				// 分支机构
				else if(showFenZhi=='true'){
					Selector.selectBranch('branchName', 'id_cardIssuer_1', true, '20', '', '', parent);
				}
			}
		});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="addBat" id="inputForm" enctype="multipart/form-data" cssClass="validate">
					<s:hidden id="id_showCenter" name="showCenter"></s:hidden>
					<s:hidden id="id_showCard" name="showCard"></s:hidden>
					<s:hidden id="id_showFenZhi" name="showFenZhi"></s:hidden>
					<s:hidden id="id_parent" name="parent"></s:hidden>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<s:if test="showCenter || showFenZhi">
							<tr>
								<td width="80" height="30" align="right">发卡机构</td>
								<td>
								<s:hidden id="id_cardIssuer_1" name="branchInfo.branchCode" disabled="true" />
								<s:textfield  id="branchName" cssClass="{required:true}" disabled="true" />
								<span class="field_tipinfo">请选择发卡机构</span>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr>	
								<td width="80" height="30" align="right">发卡机构</td>
								<td>
									<s:select id="idCardIssuer" name="branchInfo.branchCode" list="cardIssuerList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName"
										cssClass="{required:true}" value="loginBranchCode"></s:select>
									<span class="field_tipinfo">请选择发卡机构</span>
								</td>
							</tr>
						</s:else>
						<tr>
							<td width="80" height="30" align="right">导入文件</td>
							<td>
								<s:file name="upload" cssClass="{required:true}" contenteditable="false" ></s:file>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						<tr>	
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/intgratedService/cardExtraInfo/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_BAT_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
				<span class="note_div">注释</span>
				<ul class="showli_div">
					<li class="showli_div">导入文件支持txt格式 。下载 <b><a href="cardExtraInfoTmpl.txt">持卡人信息模板</a></b></li>
					<li class="showli_div">证件类型填写代码：01-身份证；10-学生证 。</li>
					<li class="showli_div">是否开通短信通知：0-不开通；1-开通 。</li>
					<li class="showli_div">导入格式，表头和数据都以“|”分隔，如下所示:
						<ul class="showCicleLi_div">
							<li class="showCicleLi_div">表头：卡号|姓名|证件类型|证件号|证件有效期|职业|国籍|联系地址|联系电话|手机号|邮件地址|是否开通短信通知|生日|备注</li>
							<li class="showCicleLi_div">数据：2081234567890123456|用户姓名|01|123456789012345678|20160101|职业|中国|联系地址|020-12345678|13500000000|abc@def.com|0|20120101|测试备注</li>
						</ul>
					</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>