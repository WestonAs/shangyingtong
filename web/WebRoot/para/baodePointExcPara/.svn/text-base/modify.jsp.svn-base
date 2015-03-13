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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){

			$('#id_Rate').blur(function(){
				var rate = $('#id_Rate').val();
				validateRate(rate);
			});
			
		});

		function validateRate(rate){
			if(isEmpty(rate)){
				return;
			}
			// 0--100的整数
			if(!checkIsInteger(rate)||rate<0 || rate>100){
				showMsg('请输入0至100的整数。');
				$(':submit').attr('disabled', 'true');
				return;
			} else {
				$(':submit').removeAttr('disabled');
			}
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<table class="form_grid detail_tbl" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
						<td width="80" height="30" align="right">发卡机构：</td>
						<td>
							<s:hidden name="baodePointExcPara.branchCode" cssClass="{required:true}" ></s:hidden>
							${baodePointExcPara.branchCode}-${fn:branch(baodePointExcPara.branchCode)}
						</td>
						</tr>
						<tr>
						<td width="80" height="30" align="right" >商户：</td>
						<td>
							<s:hidden name="baodePointExcPara.merNo" cssClass="{required:true}"></s:hidden>
							${baodePointExcPara.merNo}-${fn:merch(baodePointExcPara.merNo)}
						</td>
						</tr>
						<tr>
						<td width="80" height="30" align="right">积分类型：</td>
						<td>
							<s:hidden name="baodePointExcPara.ptClass" cssClass="{required:true}"></s:hidden>
							${baodePointExcPara.ptClassName}
						</td>
						</tr>
						<tr>
						<td width="80" height="30" align="right">失效积分返还率</td>
						<td>
							<s:textfield id="id_Rate" name="baodePointExcPara.expirExcRate" cssClass="{required:true, decimal:'6,2'}" ></s:textfield><span>%</span>
							<span class="field_tipinfo">请输入1-100的整数</span>	
						</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/para/baodePointExcPara/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_BAODEPOINTEXCPARA_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>