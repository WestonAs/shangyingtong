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
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:css href="/css/multiselctor.css"/>
		
		<script>
			$(function(){
				if('${loginRoleType}' == '00'){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20');
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20', '', '', '${loginBranchCode}');
				}
			});
			
			function downLoadRmaFile(path){
		    	location.href = CONTEXT_PATH + "/pages/report/card/rma/rmaFileDownload.do?path=" + path;
		   	}
		   	
			function check(){
		      	var branchName = $('#idBranchCode_sel').val(); 
				if(isEmpty(branchName)){
					$('#idBranchCode').val(''); 
				}
		   	}

			function checkDownloadForm(btn) {
				if (!FormUtils.hasSelected('paths')) {
					alert('请选择需要合并的文件!');
					return;
				}
				if (FormUtils.getSelectedCount('paths')<2) {
					alert('需要合并的文件不能少于两个!');
					return;
				}
				
				$('#paths').val(FormUtils.getCheckedValues('paths'));
				$('#downloadForm').submit();
				
				$("#loadingBarDiv").css("display","inline");
				$("#contentDiv").css("display","none");

				gotop();
			}

			function gotop(){	
				document.documentElement.scrollTop=0;
			}
			
			function gobottom() {
				var a = document.documentElement || document.body;
				var c = a.scrollHeight;
				document.documentElement.scrollTop = c;
			}
		</script>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<div class="location">您当前所在位置： <span class="redlink"><f:link href="/home.jsp">首页</f:link></span>
		    &gt; 报表统计 &gt; 发卡机构 &gt; 网银通划付文件下载
		</div>
		<div class="msg" style="display: none; float: left">
			<span id="_msg_content" style="float: left"></span>
			<a id="_msg_close" href="javascript:hideMsg();" style="float: right;color: red">关闭 X</a>
		</div>
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
		<div id="contentDiv">
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="list.do" cssClass="validate-tip" >
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>网银通划付文件下载 | <span class="caption_title"><f:link href="/pages/report/card/merchTransRma.jsp">商户划付报表</f:link></span></caption>
						<tr>
							<td align="right">日期</td>
							<td>
								<s:textfield id="id_reportDate" name="reportPathSave.reportDate" onclick="WdatePicker()"/>
							</td>
							<s:if test="'00'.equals(loginRoleType) || '01'.equals(loginRoleType)">
								<td align="right">发卡机构</td>
								<td>
									<s:hidden id="idBranchCode" name="reportPathSave.merchantNo"/>
									<s:textfield id="idBranchCode_sel" name="branchName" />
								</td>
							</s:if>
							<td height="30">
								<input type="submit" value="查询" id="input_btn2"  onclick="return check();" name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_RMA_FILE_LIST"/>
				</s:form>
			</div>
	    <b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">选择</td>
			   <td align="center" nowrap class="titlebg">日期</td>
			   <td align="center" nowrap class="titlebg">机构号</td>
			   <td align="center" nowrap class="titlebg">机构名称</td>
			   <td align="center" nowrap class="titlebg">文件名称</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td><input type="checkbox" name="paths" value="${filePath}"/></td>
			  <td align="center" nowrap>${reportDate}</td>
			  <td align="center" nowrap>${merchantNo}</td>
			  <td nowrap>${fn:branch(merchantNo)}</td>
			  <td nowrap>${reportName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:pspan pid="rma_file_download">
			  		<a href="javascript:downLoadRmaFile('${filePath}');"/>下载</a>
			  		</f:pspan>
			  	</span>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
       <div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
		    <div class="contentb">
				<form id="downloadForm" action="rmaFileCompondAndUpload.do"  method="post">
					<table class="form_grid" width="100%" border="0" cellspacing="3"	cellpadding="0">
						<tr>
							<td>
								<input type="button" value="合并" onclick="checkDownloadForm(this)"	class="ml30" />
							</td>
							<input type="hidden" id="paths" name="paths" value="" />
					</table>
				</form>
				<span class="note_div">划付文件合并规则说明</span>
				<ul class="showli_div">
					<li class="showli_div">相同联行号的文件才能合并。e.g : 	12675810_20130312_102.txt, 12675810_20130313_102.txt</li>
					<li class="showli_div">指定合并日期不连续的不能合并。</li>
					<li class="showli_div">已合并文件不能再合并。 e.g : 12675810_20130312_20130313_102.txt</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		</div>
	    <jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>