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
			<caption>制卡记录详细信息<span class="caption_title"> | <f:link href="/cardStock/makeHistory/list.do?goBack=goBack">返回列表</f:link></span></caption>
						<tr>
							<td width="80" height="30" align="right">制卡申请ID</td>
							<td>
								${makeCardApp.appId}
							</td>
							<td width="80" height="30" align="right">卡样名称</td>
							<td>
								${makeCardApp.makeName}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡BIN号</td>
							<td>
								${makeCardApp.binNo}
							</td>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								${fn:branch(makeCardApp.branchCode)}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡起始号</td>
							<td>
								${makeCardApp.strNo}
							</td>
							<td width="80" height="30" align="right">卡数量</td>
							<td>
								${makeCardApp.cardNum}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">状态</td>
							<td>
								${makeCardApp.statusName}
							</td>
							<td width="80" height="30" align="right">申请人代码</td>
							<td>
								${makeCardApp.appUser}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">交货单位</td>
							<td>
								${makeCardApp.deliveryUnit}
							</td>
							<td width="80" height="30" align="right">交货地点</td>
							<td>
								${makeCardApp.deliveryAdd}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">撤销人代码</td>
							<td>
								${makeCardApp.cancelUser}
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								${makeCardApp.memo}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">撤销日期</td>
							<td>
								${makeCardApp.cancelDate}
							</td>
							<td width="80" height="30" align="right">审核人代码</td>
							<td>
								${makeCardApp.chkUser}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">审核日期</td>
							<td>
								${makeCardApp.chkDate}
							</td>
							<td width="80" height="30" align="right">失败/撤销原因</td>
							<td>
								${makeCardApp.reason}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">更新用户名</td>
							<td>
								${makeCardApp.updateBy}
							</td>
							<td width="80" height="30" align="right">更新时间</td>
							<td>
								<s:date name="makeCardApp.updateTime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>