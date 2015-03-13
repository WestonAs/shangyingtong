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
			<caption>卡样定版详细信息<span class="caption_title"> | <f:link href="/cardStyleFix/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
							<td width="80" height="30" align="right">卡样ID</td>
							<td>
								${makeCardReg.makeId }
							</td>
							<td width="80" height="30" align="right">卡样名称</td>
							<td>
								${makeCardReg.makeName }
							</td>
							<td width="80" height="30" align="right">卡子类型</td>
							<td>
								${makeCardReg.cardTypeName }
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								${makeCardReg.branchCode} - ${fn:branch(makeCardReg.branchCode)}
							</td>
							
							<td width="80" height="30" align="right">制卡厂商</td>
							<td>
								${fn:branch(makeCardReg.makeUser)}
							</td>
							
							<td width="80" height="30" align="right">制卡方式</td>
							<td>	
								${makeCardReg.makeTypeName }
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">初始卡样</td>
							<td>
								${makeCardReg.initUrl }
							</td>
							
							<td width="80" height="30" align="right">定版卡样</td>
							<td>
								${makeCardReg.finUrl }
							</td>
							
							<td width="80" height="30" align="right">发卡机构上传日期</td>
							<td>
								<s:date name="makeCardReg.appUpload" format="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">是否定版</td>
							<td>
								${makeCardReg.okFlagName }
							</td>
							
							<td width="80" height="30" align="right">定版日期</td>
							<td>
								${makeCardReg.okDate }
							</td>
							
							<td width="80" height="30" align="right">取消原因</td>
							<td>
								${makeCardReg.reason }
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">当前卡样图案状态</td>
							<td>
								${makeCardReg.picStatusName }
							</td>
							<td width="80" height="30" align="right">更新用户名</td>
							<td>
								${makeCardReg.updateBy }
							</td>
							<td width="80" height="30" align="right">更新时间</td>
							<td>
								<s:date name="makeCardReg.updateTime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>