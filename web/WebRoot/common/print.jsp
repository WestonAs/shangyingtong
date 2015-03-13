<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<f:css href="/css/page.css"/>
	<f:js src="/js/jquery.js"/>
	<f:js src="/js/sys.js"/>
	<f:js src="/js/common.js"/>
		
	<script>
		//初始化页面大小
		window.resizeTo(window.screen.availWidth,window.screen.availHeight);
		window.moveTo(0,0);
	</script>
</head>

<body>
    <!--注册打印控件-->
    <object id="WebBrowser" classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" width="0"></object>
    
    <!--设置打印标题-->
    <div id="title" >
    </div>
    <br />
    
    <!--设置打印的内容-->
    <div id="print">
    </div>
    
	<script>
   //从父页面获取内容
    var father = window.opener;
	if(father != null) {
		//获取父页面的标题
		//document.getElementById('title').innerHTML =  father.title.innerHTML;
		//获取父页面需要打印的内容
		document.getElementById('print').innerHTML =  father.printArea.innerHTML; 
		SysStyle.setDetailGridStyle();
		print();
	}
    
	function print() {
		document.all.WebBrowser.ExecWB(7,1);
		window.close();
	}
	</script>
</body>
</html>

