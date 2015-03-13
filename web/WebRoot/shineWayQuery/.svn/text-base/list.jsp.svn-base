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
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/validate.js"/>
		
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		//检查卡号
		function checkContent() {
			return (true && checkCardNoSearch());
		}
		
		function checkCardNoSearch(){
			var startCardId = $('#id_startCardId').val();
			var endCardId = $('#id_endCardId').val();
			
			// 开始卡号结束卡号都为空的话，直接返回
			if(isEmpty(startCardId) && isEmpty(endCardId)){
				return true;
			}
			
			// 开始卡号不为空的话，结束卡号也不能为空
			//if(!isEmpty(startCardId) && isEmpty(endCardId)){
			//	showMsg('结束卡号不能为空');
			//	return false;
			//}

			// 结束卡号不为空的话，开始卡号也不能为空
			//if(!isEmpty(endCardId) && isEmpty(startCardId)){
			//	showMsg('开始卡号不能为空');
			//	return false;
			//}
			
			if(!validator.isDigit(startCardId)){
				showMsg('开始卡号必须为数字');
				return false;
			}
			
			if(!validator.isDigit(endCardId)){
				showMsg('结束卡号必须为数字');
				return false;
			}
			
			//开始卡号和结束卡号位数要一致
			if(startCardId.length != endCardId.length){
				showMsg("开始卡号和结束卡号的位数不一致。");
				return false;
			}
			
			// 判断结束卡号是否大于等于开始卡号
			if(endCardId < startCardId){ 
				showMsg("结束卡号不能小于开始卡号。");
				return false;
			}
				
			//查询不能超过100万张
			if(Number(endCardId) - Number(startCardId)>1000000){
				showMsg("要查询的卡的张数不得超过100万。");
				return false;
			}
			
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
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>					
							<td align="right">开始卡号</td>
							<td>
								<s:textfield id="id_startCardId" name="startCardId" cssClass="{num:true}"  maxlength="19" />
							</td>
							<td align="right">结束卡号</td>
							<td>
								<s:textfield id="id_endCardId" name="endCardId" cssClass="{num:true}"  maxlength="19" />
							</td>
							<td align="right">卡面值</td>
							<td>
								<s:textfield id="faceValue" name="cardSubClassDef.faceValue" cssClass="{digit:true}"/>
							</td>
						</tr>
						<tr>
							<td align="right">卡类型</td>
							<td>
								<s:select name="cardInfo.cardClass" list="cardTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td align="right">卡状态</td>
							<td>
								<s:select name="cardInfo.cardStatus" list="cardStatusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td width="80" height="30" align="right">充值资金余额</td>
							<td>
								<s:textfield id="avaliableValueStart" name="avaliableValueStart" cssClass="{digit:true}"  cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="avaliableValueEnd" name="avaliableValueEnd" cssClass="{digit:true}"  cssStyle="width:68px;"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">售卡日期</td>
							<td>
								<s:textfield id="cardSaleStartDate" name="cardSaleStartDate" onclick="getSaleStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="cardSaleEndDate" name="cardSaleEndDate" onclick="getSaleEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td width="80" height="30" align="right">消费日期</td>
							<td>
								<s:textfield id="consumeStartDate" name="consumeStartDate" onclick="getConsumeStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="consumeEndDate" name="consumeEndDate" onclick="getConsumeEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td width="80" height="30" align="right">返利资金余额</td>
							<td>
								<s:textfield id="rebateAmtStart" name="rebateAmtStart" cssClass="{digit:true}"  cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="rebateAmtEnd" name="rebateAmtEnd" cssClass="{digit:true}"  cssStyle="width:68px;"/>
							</td>
						</tr>
						<tr>					
							<td align="right">发卡机构</td>
							<td>
							<s:if test="loginRoleType == '00' || loginRoleType == '01'">
								<s:hidden id="idBranchCode" name="cardInfo.cardIssuer"/>
								<s:textfield id="idBranchName" name="branchName" />
							</s:if>
							<s:else>
								<s:select name="cardInfo.cardIssuer" headerKey="" headerValue="--请选择--" 
									list="branchList" listKey="branchCode" listValue="branchName" onmouseover="FixWidth(this)"></s:select>
							</s:else>
							</td>
							<td align="right">售卡机构</td>
							<td>
							<s:if test="loginRoleType == '00' || loginRoleType == '01'">
								<s:hidden id="idSaleBranchCode" name="cardInfo.saleOrgId"/>
								<s:textfield id="idSaleBranchName" name="saleBranchName" />
							</s:if>
							<s:else>
								<s:textfield name="cardInfo.saleOrgId" />
							</s:else>
							</td>
							<td align="right">领卡机构</td>
							<td>
							<s:if test="loginRoleType == '00' || loginRoleType == '01'">
								<s:hidden id="idAppBranchCode" name="cardInfo.appOrgId"/>
								<s:textfield id="idAppBranchName" name="appBranchName" />
							</s:if>
							<s:else>
								<s:textfield name="cardInfo.appOrgId" />
							</s:else>
							</td>
						</tr>
						<tr>
						    <td width="80" height="30" align="right">姓名</td>
							<td>
							<s:textfield name="cardExtraInfo.custName" />
							</td>
						    <td width="80" height="30" align="right">手机号</td>
							<td>
								<s:textfield name="cardExtraInfo.mobileNo" cssClass="{digit:true}" maxlength="30" />
							</td>
							<td width="80" height="30" align="right">身份证号</td>
							<td>
								<s:textfield name="cardExtraInfo.credNo" maxlength="20"/>
							</td>
						<tr>
							<td></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" onclick="return checkContent();"/>
								<input type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" class="ml30"/>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_VERIFY_CARD_BRANCH_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<td align="center" nowrap class="titlebg">卡号</td>
					<td align="center" nowrap class="titlebg">卡类型</td>
					<td align="center" nowrap class="titlebg">卡面值</td>
					<td align="center" nowrap class="titlebg">卡状态</td>
					<td align="center" nowrap class="titlebg">初始金额</td>
					<td align="center" nowrap class="titlebg">充值资金余额</td>
					<td align="center" nowrap class="titlebg">返利资金余额</td>
					<td align="center" nowrap class="titlebg">期间消费次数</td>
					<td align="center" nowrap class="titlebg">期间消费金额</td>
					<td align="center" nowrap class="titlebg">生效日期</td>
					<td align="center" nowrap class="titlebg">失效日期</td>
					<td align="center" nowrap class="titlebg">售卡日期</td>
					<td align="center" nowrap class="titlebg">领卡机构</td>
					<td align="center" nowrap class="titlebg">售卡机构</td>
				</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="left" nowrap>${cardId}</td>
			  <td align="center" nowrap>${cardClassName}</td>
			  <td align="right" nowrap>${fn:amount(faceValue)}</td>
			  <td align="center" nowrap>${cardStatusName}</td>
			  <td align="right" nowrap>${fn:amount(initAmt)}</td>
			  <td align="right" nowrap>${fn:amount(balanceAmt)}</td>
			  <td align="right" nowrap>${fn:amount(bal)}</td>
			  <td align="right" nowrap>${consumeTimes}</td>
			  <td align="right" nowrap>${fn:amount(consumeAmt)}</td>
			  <td align="center" nowrap>${effDate}</td>
			  <td align="center" nowrap>${expirDate}</td>
			  <td align="center" nowrap>${saleDate}</td>
			  <td align="center" nowrap>${appOrgId}</td>
			  <td align="center" nowrap>${saleOrgId}</td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>