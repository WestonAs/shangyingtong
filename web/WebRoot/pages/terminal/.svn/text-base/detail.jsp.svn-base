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
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div id="printArea" >
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>终端详细信息<span class="caption_title"> | <f:link href="/pages/terminal/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>终端编号</td>
				<td>${terminal.termId}</td>
				<td>商户号</td>
				<td>${fn:merch(terminal.merchId)}</td>
		  	</tr>
			<tr>
				<td>出机方</td>
				<td>${fn:branch(terminal.manageBranch)}</td>
				<td>维护方</td>
				<td>${fn:branch(terminal.maintenance)}</td>
		  	</tr>
			<tr>
				<td>消费点类型</td>
				<td>${terminal.expenseTypeName}</td>
				<td>联线处理要求</td>
				<td>${terminal.linkWork}</td>
		  	</tr>
			<tr>
				<td>终端的当前黑名单版本</td>
				<td>${terminal.blackVer}</td>
				<td>是否验证IP地址</td>
				<td>${terminal.checkIpName}</td>
			</tr>
			<tr>
				<td>网络地址</td>
				<td>${terminal.macAddress}</td>
				<td>密钥索引</td>
				<td>${terminal.keyIndex}</td>
		  	</tr>
			<tr>
				<td>PIN工作密钥</td>
				<td>${terminal.pin}</td>
				<td>MAC工作密钥</td>
				<td>${terminal.mac}</td>
		  	</tr>
			<tr>
				<td>PIN工作密钥（LMK保护的）</td>
				<td>${terminal.pinlmk}</td>
				<td>MAC工作密钥（LMK保护的）</td>
				<td>${terminal.maclmk}</td>
		  	</tr>
			<tr>
				<td>SESSION KEY</td>
				<td>${terminal.sessionKey}</td>
				<td>整包加密标志</td>
				<td>${terminal.pkgFlag}</td>
		  	</tr>
			<tr>
				<td>输入方式</td>
				<td>${terminal.entryModeName}</td>
				<td>POS状态</td>
				<td>${terminal.posStatusName}</td>
		  	</tr>
			<tr>
				<td>批次号</td>
				<td>${terminal.batchNo}</td>
				<td>备注信息</td>
				<td>${terminal.userMemo}</td>
		  	</tr>
			<tr>
				<td>门店编号</td>
				<td>${terminal.shopNo}</td>
				<td>序号</td>
				<td>${terminal.pSeq}</td>
		  	</tr>
			<tr>
				<td>商户地址</td>
				<td colspan="3">${terminal.merchAddress}</td>
			</tr>
			<tr>
				<td>POS联系人</td>
				<td>${terminal.posContact}</td>
				<td>POS联系人电话</td>
				<td>${terminal.posContactPhone}</td>
		  	</tr>
			<tr>
				<td>通信方式</td>
				<td>${terminal.commTypeName}</td>
				<td>来源号码</td>
				<td>${terminal.srcTelNo}</td>
		  	</tr>
		  	<tr>
				<td>租机费</td>
				<td>${fn:amount(terminal.rentAmt)}</td>
				<td>是否单机产品终端</td>
				<td>${terminal.singleProductName}</td>
			</tr>
			<tr>
				<td>终端添加时间</td>
				<td><s:date name="terminal.createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>更新时间</td>
				<td>
					<s:date name="terminal.updateTime" format="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
			<tr>
				<td>更新人</td>
				<td colspan="3">${terminal.updateBy}</td>
		  	</tr>
		  	<s:if test="terminalAddi != null">
			  	<tr>
					<td>开户行号</td>
					<td>${terminalAddi.bankNo}</td>
					<td>开户行名称</td>
					<td>${terminalAddi.bankName}</td>
			  	</tr> 	
			  	<tr>
					<td>账户户名</td>
					<td>${terminalAddi.accName}</td>
					<td>账号</td>
					<td>${terminalAddi.accNo}</td>
				</tr>
				<tr>
					<td>账户地区码</td>
					<td>${terminalAddi.accAreaCode}</td>
					<td>账户类型</td>
					<td>${terminalAddi.acctTypeName}</td>
			  	</tr> 	
			  	<tr>
					<td>账户介质类型</td>
					<td>${terminalAddi.acctMediaTypeName}</td>
					<td>终端名</td>
					<td>${terminalAddi.termName}</td>
			  	</tr> 	
		  	</s:if>
		</table>
		</div>
		</div>
		
		<div style="padding-left: 30px;margin: 0px;">
			<input type="button" value="打印" onclick="openPrint();"/>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>