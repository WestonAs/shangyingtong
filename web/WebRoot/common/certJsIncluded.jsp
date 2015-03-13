<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<%-- 引入证书验证相关公用的js文件 --%>
<f:js src="/js/cert/AC_ActiveX.js"/>
<f:js src="/js/cert/AC_RunActiveContent.js"/>
<script type="text/javascript">
	AC_AX_RunContent( 'classid','clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80','id','FTUSBKEYCTRL','style','DISPLAY:none' ); //end AC code
</script>
<noscript>
	<OBJECT classid="clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80" id="FTUSBKEYCTRL" style="DISPLAY:none"></OBJECT>
</noscript>