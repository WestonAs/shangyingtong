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
			<caption>卡类型模板详细信息<span class="caption_title"> | <f:link href="/pages/singleProduct/subclass/list.do?goBack=goBack">返回列表</f:link></span></caption>
						<tr>
							<td width="80" height="30" align="right">卡类型号</td>
							<td>
								${cardSubClassTemp.cardSubclass}
							</td>
							<td width="80" height="30" align="right">卡类型名称</td>
							<td>
								${cardSubClassTemp.cardSubclassName}
							</td>
							<td width="80" height="30" align="right">卡种</td>
							<td>
								${cardSubClassTemp.cardClassName}
							</td>
						</tr>
						<tr>
							 
							<td width="80" height="30" align="right">参考面值</td>
							<td>
								${fn:amount(cardSubClassTemp.faceValue)}
							</td>
							<td width="80" height="30" align="right">会员子类型</td>
							<td>
								${membClassTemp.className}
							</td>
							<td width="80" height="30" align="right">卡片类型</td>
							<td>
								${cardSubClassTemp.icTypeName}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">初始会员级别</td>
							<td>
								${cardSubClassTemp.membLevel}
							</td>
							<td width="80" height="30" align="right">开卡积分</td>
							<td>
								${cardSubClassTemp.ptOpencard}
							</td>
							<td width="80" height="30" align="right">积分子类型</td>
							<td>
								${pointClassTemp.className}
							</td>							 
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">密码标志</td>
							<td>
								<s:if test="cardSubClassTemp.chkPwd == 1">是</s:if>
								<s:else>否</s:else>
							</td>
							<td width="80" height="30" align="right">密码类型</td>
							<td>
								${cardSubClassTemp.pwdTypeName}
							</td>
							<td width="80" height="30" align="right">是否强制修改密码</td>
							<td>
								<s:if test='cardSubClassTemp.isChgPwd == "1"'>是</s:if>
								<s:elseif test='cardSubClassTemp.isChgPwd == "0"'>否</s:elseif>
								<s:else>${cardSubClassTemp.isChgPwd}</s:else>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">卡片抵用金</td>
							<td>
								${fn:amount(cardSubClassTemp.cardPrice)}
							</td>
							<td width="80" height="30" align="right">预制卡检查</td>
							<td>
								<s:if test="cardSubClassTemp.chkPfcard == 1">是</s:if>
								<s:else>否</s:else>
							</td>
							<td width="80" height="30" align="right">自动销卡标志</td>
							<td>
								<s:if test="cardSubClassTemp.autoCancelFlag == 1">是</s:if>
								<s:else>否</s:else>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡内额度上限</td>
							<td>
								${fn:amount(cardSubClassTemp.creditUlimit)}
							</td>
							<td width="80" height="30" align="right">单笔充值上限</td>
							<td>
								${fn:amount(cardSubClassTemp.chargeUlimit)}
							</td>
							<td width="80" height="30" align="right">单笔消费上限</td>
							<td>
								${fn:amount(cardSubClassTemp.consmUlimit)}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">失效方式</td>
							<td>
								${cardSubClassTemp.expirMthdName}
							</td>
							<s:if test="cardSubClassTemp.expirMthd == 1">
								<td width="80" height="30" align="right">失效日期</td>
								<td>
									${cardSubClassTemp.expirDate}
								</td>
							</s:if>
							<s:else>
								<td width="80" height="30" align="right">有效期(月数)</td>
								<td>
									${cardSubClassTemp.effPeriod}
								</td>
							</s:else>
							<td width="80" height="30" align="right">卡延期(月数)</td>
							<td>
								${cardSubClassTemp.extenPeriod}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">最大延期次数</td>
							<td>
								${cardSubClassTemp.extenMaxcnt}
							</td>
							<td width="80" height="30" align="right">绝对失效日期</td>
							<td>
								${cardSubClassTemp.mustExpirDate}
							</td>
							<td width="80" height="30" align="right">状态</td>
							<td>
								${cardSubClassTemp.statusName}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">售卡面值能否修改</td>
							<td>
								${cardSubClassTemp.changeFaceValueName}
							</td>
							<td width="80" height="30" align="right">卡能否充值</td>
							<td>
								${cardSubClassTemp.depositFlagName}
							</td>
							<td width="80" height="30" align="right">更新用户名</td>
							<td>
								${cardSubClassTemp.updateBy}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">运营机构编号</td>
							<td>
								${cardSubClassTemp.branchCode}
							</td>
							<td width="80" height="30" align="right">运营机构名称</td>
							<td>
								${fn:branch(cardSubClassTemp.branchCode)}
							</td>
							<td width="80" height="30" align="right">更新时间</td>
							<td>
								<s:date name="cardSubClassTemp.updateTime" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
						</tr>
		</table>
		</div>
		

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>