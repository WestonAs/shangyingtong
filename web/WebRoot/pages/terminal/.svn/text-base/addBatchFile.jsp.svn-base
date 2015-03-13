<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>文件方式批量新增终端</title>
		
		<f:css href="/css/page.css"/>
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/date/WdatePicker.js"/>

		<f:css href="/css/jquery.autocomplete.css"/>
		<f:js src="/js/jquery.autocomplete.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			//Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '00,01,11,20,21');
			if('${loginRoleType}' == '00'){
				Selector.selectBranch('idManageBranch_sel', 'idManageBranch', true, '00,01');
			} else if('${loginRoleType}' == '01'){
				Selector.selectBranch('idManageBranch_sel', 'idManageBranch', true, '01', '', '', '${loginBranchCode}');
			}
			
			$("#input_btn2").click(function(){
				if($('#inputForm').validate().form()){
					$('#inputForm').submit();
					$('#input_btn2').attr('disabled', 'true');
					$("#loadingBarDiv").css("display","block");
					$("#contentDiv").css("display","none");
				}
			});
		});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<jsp:include flush="true" page="/common/loadingBarDiv.jsp"></jsp:include>
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="addBatchFile.do" id="inputForm" enctype="multipart/form-data" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>文件方式批量新增终端</caption>
						
						<tr>
							<td width="80" height="30" align="right">上传文件</td>
							<td height="40">
								<s:file name="upload" cssClass="{required:true}" contenteditable="false" />
								<span class="field_tipinfo">请选择文件</span>
							</td>
						</tr>
						
						<tr >	
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/terminal/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MERCH_FILE_ADD"/>
					</div>
					
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		
		<div class="userbox" style="padding-top: 5px">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<span class="note_div">注释：</span>
				<ul class="showli_div">
					<li class="showli_div">上传文件必须为<span class="redfont">文本格式文件</span>。后缀名必须为：<span class="redfont"> csv 或 txt </span>，下载 <b><a href="addTerminalTmpl.txt">新增终端模板</a></b></li>
					<li class="showli_div">文件第一行是<span class="redfont">标题行</span>，固定为：<span class="redfont">商户编号,出机方编号,维护方编号,商户地址,POS联系人,POS联系人电话,输入方式,终端数量</span></li>
					<li class="showli_div">文件其余行是<span class="redfont">数据行</span>，其中：
						“<span class="redfont">商户地址</span>”中不要包含英文半角逗号；&nbsp;&nbsp;&nbsp;&nbsp;
						“<span class="redfont">输入方式</span>”字段值为：0-只允许刷卡，1-可以刷卡和手工录入卡号；&nbsp;&nbsp;&nbsp;&nbsp;
					</li>
					<li class="showli_div">商户编号、维护方编号、输入方式、终端数量 不能为空；</li>
					<li class="showli_div">导入文件的<span class="redfont">数据行</span>最多100行；</li>
					<li class="showli_div">导入文件的<span class="redfont">新增终端总数量</span>最多1000台；</li>
				</ul> 
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>