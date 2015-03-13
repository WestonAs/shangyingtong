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
		<f:js src="/js/date/WdatePicker.js"/>
		
		<script type="text/javascript" src="adjAccReg.js"></script>
		<script>
		function checkTrans(){
			if (!FormUtils.hasSelected('transSns')){
				alert('未选择交易记录');
				return false;
			}
			
			var needSignatureReg = $('#needSignatureReg').val();
			if (needSignatureReg == 'true' && !CheckUSBKey()) { // 证书签名失败
				return false;
			}
		}
		function checkCardIssuer(){
			if($("#idCardIssuer").length>0 && $("#idCardIssuer").val()!=$("#idloginBranchCode").val() && $("[name='trans.cardId']").val()==""){
				showMsg("选择的发卡机构不是登录用户所在的机构，还需要输入卡号！");
				return false;
			}
			hideMsg();
			return true;
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="findAdjBat.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">交易流水</td>
							<td><s:textfield name="trans.transSn" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">清算日期</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td align="right">卡号</td>
							<td><s:textfield name="trans.cardId" cssClass="{digitOrLetter:true}"/></td>
						</tr>
						<tr>
							<td align="right">商户编号</td>
							<td><s:textfield name="trans.merNo" cssClass="{digitOrLetter:true}"/></td>
							<s:if test="loginRoleType == 40 || loginRoleType == 20">
								<td align="right">发卡机构</td>
								<td>
									<s:select id="idCardIssuer" name="trans.cardIssuer" list="cardIssuerList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName"
										cssClass="{required:true}" value="loginBranchCode" onmouseover="FixWidth(this)"></s:select>
									<s:hidden id="idloginBranchCode" name="loginBranchCode" disabled="disabled"/>
								</td>
							</s:if>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok"  onclick="return checkCardIssuer()"/>
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_ADJACCREG_ADDBAT"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<s:form id="searchForm" action="addBat.do" cssClass="validate">
			<!-- 数据列表区 -->
			<div class="tablebox">
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
				<tr>
				   <td align="center" nowrap class="titlebg"><input type="checkbox" onclick="FormUtils.selectAll(this, 'transSns')" />全选</td>
				   <td align="center" nowrap class="titlebg">交易流水</td>
				   <td align="center" nowrap class="titlebg">清算日期</td>
				   <td align="center" nowrap class="titlebg">交易时间</td>
				   <td align="center" nowrap class="titlebg">交易类型</td>
				   <td align="center" nowrap class="titlebg">卡号</td>
				   <td align="center" nowrap class="titlebg">商户号</td>
				   <td align="center" nowrap class="titlebg">交易金额</td>
				</tr>
				</thead>
				<s:iterator value="page.data"> 
				<tr>
				  <td align="center"><input type="checkbox" name="transSns" value="${transSn}"/></td>	
				  <td align="center" nowrap>${transSn}</td>
				  <td align="center" nowrap>${settDate}</td>
				  <td align="center" nowrap>${transDatetimeName}</td>
				  <td align="center" nowrap>${transTypeName}</td>
				  <td align="center" nowrap>${cardId}</td>
				  <td align="center" nowrap>${merNo}</td>
				  <td align="right" nowrap>${fn:amount(transAmt)}</td>
				</tr>
				</s:iterator>
				</table>
				<f:paginate name="page"/>
			</div>
			
			<div class="contentb clear" id="idInputPage">
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30">
							<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return checkTrans();"/>
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/adjAccReg/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
			</div>
			
			<s:token />
			<s:hidden id="needSignatureReg" name="formMap.needSignatureReg" />
			<s:hidden id="serialNo" name="formMap.serialNo"/>
			<s:hidden id="randomSessionId" name="formMap.randomSessionId" />
			<s:hidden id="signature" name="formMap.signature" />
		</s:form>
		
		<jsp:include flush="true" page="/common/certJsIncluded.jsp"></jsp:include>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>