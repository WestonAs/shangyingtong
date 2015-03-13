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
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<s:hidden name="formMap.modifyType"></s:hidden>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">卡类型号</td>
							<td>
								<s:textfield name="cardSubClassDef.cardSubclass" readonly="true" cssClass="readonly {required:true}"/>
							</td>
							<td width="80" height="30" align="right">卡类型名称</td>
							<td>
								<s:textfield name="cardSubClassDef.cardSubclassName" readonly="true" cssClass="readonly {required:true}"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">失效方式</td>
							<td>
								<s:textfield name="cardSubClassDef.expirMthdName" readonly="true" cssClass="readonly {required:true}"/>
							</td>
							<s:if test="cardSubClassDef.specifyDateExpire">
								<td id="id_expirDate_td1" width="80" height="30" align="right">指定日期</td>
								<td id="id_expirDate_td2">
									<s:textfield id="id_expirDate" name="cardSubClassDef.expirDate" 
										onclick="WdatePicker({startDate:'{%y+2}1231', minDate:'%y-%M-%d'})" 
										cssClass="{required:true}" />
									<span class="field_tipinfo">到该日期失效</span>
								</td>
							</s:if>
							<s:else>
								<td id="id_effPeriod_td1" width="80" height="30" align="right">指定月数</td>
								<td id="id_effPeriod_td2">
									<s:textfield id="id_effPeriod" name="cardSubClassDef.effPeriod" cssClass="{required:true, digit:true}" 
										maxlength="4"/>
									<span class="field_tipinfo">售卡经该月数失效</span>
								</td>
							</s:else>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" name="ok"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardSubClass/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDSTYLEFIX_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			<br/>
			
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<span class="note_div">注释</span>
				<ul class="showli_div">
					<li class="showli_div">对卡类型的修改，<span class="redfont">仅对新制卡生效</span>，原有的旧卡属性不会改变。 </li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>