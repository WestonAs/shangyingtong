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
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>白卡入库详细信息<span class="caption_title"> | <f:link href="/cardStock/whiteCard/list.do">返回列表</f:link></span></caption>
						<tr>
							<td width="80" height="30" align="right">机构编号</td>
							<td>
								${whiteCardInput.brancCode}
							</td>
							<td width="80" height="30" align="right">卡批次</td>
							<td>
								${whiteCardInput.makeId}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡类型</td>
							<td>
								${whiteCardInput.cardTypeName}
							</td>
							<td width="80" height="30" align="right">卡子类型</td>
							<td>
								${whiteCardInput.cardSubtype}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">入库卡数量</td>
							<td>
								${whiteCardInput.inputNum}
							</td>
							<td width="80" height="30" align="right">入库日期</td>
							<td>
								${whiteCardInput.inputDate}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">状态</td>
							<td>
								${whiteCardInput.statusName}
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								${whiteCardInput.memo}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">审核人代码</td>
							<td>
								${whiteCardInput.chkUser}
							</td>
							<td width="80" height="30" align="right">审核时间</td>
							<td>
								${whiteCardInput.chkTime}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">更新用户名</td>
							<td>
								${whiteCardInput.updateBy}
							</td>
							<td width="80" height="30" align="right">更新时间</td>
							<td>
								<s:date name="whiteCardInput.updateTime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>