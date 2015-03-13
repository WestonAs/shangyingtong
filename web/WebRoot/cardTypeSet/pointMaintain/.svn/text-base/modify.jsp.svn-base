<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>修改积分费率</title>
		
		<f:css href="/css/page.css"/>
		<f:css href="/css/multiselctor.css"/>
		
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
		$(function(){
			$('#id_ptDiscntRate').blur(function(){
				var rate = $('#id_ptDiscntRate').val();
				validateRate(rate);
			});
		});
		
		function validateRate(rate){
			// 0--100的整数
			if(!isDecimal(rate,"8,2")||rate<0 || rate>100){
				showMsg('请输入0至100的小数。');
				return false;
			} 
			return true;
		}
		
		function check(){
			var rate = $('#id_ptDiscntRate').val();
			return validateRate(rate);
		}
		
		function submitForm(){
			if(!check()){
				return false;
			}
			$("#inputForm").submit();
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">	
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="modify.do" id="inputForm" cssClass="validate">
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td width="80" height="30" align="right">发卡机构</td>
						<td><s:textfield name="merchPointRate.cardIssuer"  cssClass="readonly" readonly="true" /></td>
						
						<td width="80" height="30" align="right">商户号码</td>
						<td><s:textfield name="merchPointRate.merNo"  cssClass="readonly" readonly="true" /></td>
						
						<td width="80" height="30" align="right">积分类型</td>
						<td><s:textfield name="merchPointRate.ptClass"  cssClass="readonly" readonly="true" /></td>
					</tr>
					<tr  id ="ptDiscntRate_tr" >
					    <td width="80" height="30" align="right">积分返利兑换率</td> 
						<td>
							<s:textfield id="id_ptDiscntRate" name="merchPointRate.ptDiscntRate" cssClass="{required:true, decimal:'8,2'}" ></s:textfield><span>%</span>
							<span class="field_tipinfo">请输入兑换率（最多两位小数）</span>
						</td>
						
						<td width="80" height="30" align="right">备注</td> 
						<td>
							<s:textfield name="merchPointRate.remark" ></s:textfield>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30" colspan="3">
							<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return submitForm()"/>
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardTypeSet/pointMaintain/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_POINTCLASS_MODIFY"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>