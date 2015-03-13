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
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>

		<script>	
		</script>
		
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">客户机构编号</td>
							<td><s:textfield name="wsMsgTypeCtrl.branchCode"/></td>
							<td width="80" height="30" align="right">客户机构名称</td>
							<td><s:textfield name="wsMsgTypeCtrl.branchName"/></td>
							<td width="80" height="30" align="right">接口类型编号</td>
							<td><s:textfield name="wsMsgTypeCtrl.msgType"/></td>
							<td width="80" height="30" align="right">接口类型名称</td>
							<td><s:textfield name="wsMsgTypeCtrl.msgTypeDesc"/></td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">关联发卡机构编号</td>
							<td><s:textfield name="wsMsgTypeCtrl.relatedIssNo"/></td>
							<td width="80" height="30" align="right">关联发卡机构名称</td>
							<td><s:textfield name="wsMsgTypeCtrl.relatedIssName"/></td>
							
							<td width="80" height="30" align="right">状态</td>
							<td>
								<s:select name="wsMsgTypeCtrl.status" list='#{"":"--请选择--", "1":"正常", "0":"注销"}'/>
							</td>
							<td width="80" height="30" align="right">访问类型</td>
							<td>
								<s:select name="formMap.isIssDirectCall" list='#{"":"--请选择--", "1":"直接访问", "0":"关联访问"}'/>
							</td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4" >
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" 
									onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="wsMsgTypeCtrl_add">
									<input style="margin-left:30px;" type="button" value="添加" onclick="javascript:gotoUrl('/para/webServiceConf/wsMsgTypeCtrl/showAdd.do');" id="input_btn3"  name="escape" />
								</f:pspan>
							</td>
						</tr>
					</table>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">客户机构编号</td>
			   <td align="center" nowrap class="titlebg">客户机构名称</td>
			   <td align="center" nowrap class="titlebg">接口类型编号</td>
			   <td align="center" nowrap class="titlebg">接口类型描述</td>
			   <td align="center" nowrap class="titlebg">关联发卡机构编号</td>
			   <td align="center" nowrap class="titlebg">关联发卡机构名称</td>
			   <td align="center" nowrap class="titlebg">状态</td>			   
			   <td align="center" nowrap class="titlebg">更新时间</td>			   
			   <td align="center" nowrap class="titlebg">更新人</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="center" nowrap>${branchCode}</td>
			  <td align="left" nowrap>${branchName}</td>
			  <td align="center" nowrap>${msgType}</td>
			  <td align="left" nowrap>${msgTypeDesc}</td>
			  <td align="center" nowrap>${relatedIssNo}</td>
			  <td align="left" nowrap>${relatedIssName}</td>
			  <td align="center" nowrap>
				  <s:if test='status=="1"' >
				  	正常
				  </s:if>
				  <s:else>
				  	<font color="red">注销</font>
				  </s:else>
			  </td>
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap>${updateBy }</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<s:form id="operateForm" />
					<f:link href="/para/webServiceConf/wsMsgTypeCtrl/detail.do?wsMsgTypeCtrl.branchCode=${branchCode}&wsMsgTypeCtrl.msgType=${msgType}&wsMsgTypeCtrl.relatedIssNo=${relatedIssNo}">
						查看
					</f:link>
			  		&nbsp;
			  		<f:pspan pid="wsMsgTypeCtrl_modify">
			  			<f:link href="/para/webServiceConf/wsMsgTypeCtrl/showModify.do?wsMsgTypeCtrl.branchCode=${branchCode}&wsMsgTypeCtrl.msgType=${msgType}&wsMsgTypeCtrl.relatedIssNo=${relatedIssNo}">
			  				编辑
			  			</f:link>
				  	</f:pspan>
				  	&nbsp;
				  	<f:pspan pid="wsMsgTypeCtrl_delete">
			  			<a href="javascript:submitUrl('operateForm', '/para/webServiceConf/wsMsgTypeCtrl/delete.do?wsMsgTypeCtrl.branchCode=${branchCode}&wsMsgTypeCtrl.msgType=${msgType}&wsMsgTypeCtrl.relatedIssNo=${relatedIssNo}', '确定要删除吗？');" >
			  				删除
			  			</a>
			  		</f:pspan>	
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
				<span class="note_div">注释</span>
				<ul class="showli_div">
					<li class="showli_div">配置了机构-接口类型，并且状态为“正常”，才允许该机构的客户程序访问webService服务端的该接口类型。</li>
					<li class="showli_div"><span class="redfont">示例：客户机构 A，接口类型 B，关联发卡机构为空，表示允许客户机构A调用接口B，访问发卡机构A（自身机构）的数据（直接访问）。</span></li>
					<li class="showli_div"><span class="redfont">示例：客户机构 A，接口类型 B，关联发卡机构 C，表示允许客户机构A调用接口B，访问发卡机构C（关联机构）的数据（关联访问）。</span></li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>