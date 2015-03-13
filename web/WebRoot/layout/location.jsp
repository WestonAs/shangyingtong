<%@ page language="java" contentType="text/html; charset=GB18030"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="location">您当前所在位置： <span class="redlink"><f:link href="/home.jsp">首页</f:link></span>
	<s:if test="#request.PRIVILEGE_PATH != null">
		<s:iterator id="menu" value="#request.PRIVILEGE_PATH">
		 &gt; ${menu.name}
		</s:iterator>
	</s:if>
</div>
<div class="msg" style="display: none; float: left">
	<span id="_msg_content" style="float: left"></span>
	<a id="_msg_close" href="javascript:hideMsg();" style="float: right;color: red">关闭 X</a>
</div>