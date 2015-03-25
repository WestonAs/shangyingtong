<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>预付卡系统</title>
		<%@ include file="/common/meta.jsp"%>
        <%@ include file="/common/sys.jsp"%>
        
		<f:css href="/css/screen.css"/>
		
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/plugin/jquery.blockUI.js"/>
		<f:js src="/js/biz/main.js"/>
	
	<style type="text/css">
	.top_index_logo{float:left; background:url(${logoConfig.loginSmall}) no-repeat; width:307px;height:81px; overflow:hidden;position:absolute;}
	</style>
	
	<script>
		var timeout    = 500;
		var closetimer = 0;
		var ddmenuitem = 0;
		
		function jsddm_open() {  
			jsddm_canceltimer();
			jsddm_close();
			ddmenuitem = $(this).find('ul').css('visibility', 'visible');
		}
		
		function jsddm_close() {  
			if(ddmenuitem) 
				ddmenuitem.css('visibility', 'hidden');
		}
		
		function jsddm_timer(){  
			closetimer = window.setTimeout(jsddm_close, timeout);
		}
		
		function jsddm_canceltimer(){  
			if(closetimer){  
				window.clearTimeout(closetimer);
				closetimer = null;
			}
		}
		
		$(document).ready(function(){  
			$('#jsddm > li').bind('mouseover', jsddm_open)
		   	$('#jsddm > li').bind('mouseout',  jsddm_timer)
		});
		
	//document.onclick = jsddm_close; 	
	function a(){
		alert('parent');
	}
	</script>
	
	</head>
	
	<body onunload="logout()">
		<!--主框架头部-->
		<div class="top_div">
			<div class="top_box">
				<div class="top_index_logo"></div>
				<div class="top_right">
					<div class="toptext">
						<ul id="jsddm">
						    <li><a href="home.jsp" target="main_area">首页</a>
						    </li>
						    <!-- 
						    <li><a href="#">切换角色</a>
						        <ul id="roleList">
						        	<s:iterator id="role" value="roleList">
							            <li><a href="#" onclick="switchRole('${role.roleId}');">${role.roleName}</a></li>
						        	</s:iterator>
						        </ul>
						    </li>
						     -->
						    <li><a id="logoutId" href="logout.do?bno=${bno}">重新登陆</a></li>
						    <f:pspan pid="modifypass">
								<li><a href="pages/user/showModifyPass.do" target="main_area">修改密码</a></li>
						    </f:pspan>
							<li><a href="logout.do?bno=${bno}">退出系统</a></li>
						</ul>							
					</div>
					<div class="top_menuuser">
						<img src="images/icon0.gif" width="12" height="16" />
						<span style="padding-right: 10px;">您好！<s:property value="#session.SESSION_USER.userName"/>，您的登陆身份是<s:property value="#session.SESSION_USER.role.roleName"/>
						<s:debug></s:debug></span>
					</div>
				</div>
			</div>

			<!--头部横向菜单-->
			<div class="top_menu">
				<div class="top_menutime" id="showtime">
					<script>setInterval("document.getElementById('showtime').innerHTML=(new Date().format('yyyy年MM月dd日 hh:mm:ss'))+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",300);</script>
				</div>
				<div class="top_menutext">
					<div class="top_fenge">
						<img src="images/separated.gif" width="3" height="39" />
					</div>
					
					<div class="top_button" id="topbutton">
						<a href="home.jsp" onFocus="this.blur()" target="main_area">系统首页</a>
						<f:pspan pid="modifypass">
							<a href="pages/user/showModifyPass.do" onFocus="this.blur()" target="main_area">修改密码</a>
						</f:pspan>
					</div>
				</div>
			</div>
		</div>

		<!--左侧菜单区-->
		<div class="side" id="xs">
			<div class="leftbox">
				<!-- 
				<div class="leftmenua">
					<a href="page_home.jsp" onfocus="this.blur()" target="main_area">系统首页
						<img src="images/icon6.gif" width="16" height="16" />
					</a>
				</div>
				 -->
				
				<!-- 一级子菜单 -->
				<div id="menu">
				<s:iterator id="menu1" value="menuTree.children">
					<h1 onclick="javascript:toggleMenu(this, 'menu1_${menu1.code}')"> + ${menu1.name}</h1>
					<span id="menu1_${menu1.code}" class="no" style="display: none;">
						<s:iterator id="menu2" value="children">
						<s:if test='#menu2.entry != null && !"".equals(#menu2.entry)'>
							<h2>&nbsp;&nbsp;&nbsp;<a id="id_${menu2.code}" href="<%=request.getContextPath()%>${menu2.entry}" onclick="highColor(this.id)" onfocus="this.blur()" target="main_area">${menu2.name}</a>
							</h2>
						</s:if>
						<s:else>
							<h2 onclick="javascript:toggleMenu(this, 'menu2_${menu2.code}')"> + ${menu2.name}</h2>
							<ul id="menu2_${menu2.code}" class="no" style="display: none;">
								<s:iterator id="menu3" value="children">
								<a id="id_${menu3.code}" href="<%=request.getContextPath()%>${menu3.entry}" onclick="highColor(this.id)" onfocus="this.blur()" target="main_area"><li>${menu3.name}</li></a>
								</s:iterator>
							</ul>
						</s:else>
						</s:iterator>
					</span>
				</s:iterator>
				</div>
			</div>
		</div>

		<!--显示隐藏按钮-->
		<div class="main_bt">
			<input type="image" id="yc" onClick="menu_xsyc()"
				src="images/yc_icon.gif" onfocus="this.blur()" title="点击显示/隐藏左侧菜单"
				width="7" height="32" border="0" />
		</div>
		
		<!--载入内容区-->
		<div class="main">
		
			<iframe id="main_area" name="main_area" frameborder="0" src="home.jsp"></iframe>
		</div>
	</body>
</html>
