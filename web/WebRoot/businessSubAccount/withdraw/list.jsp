<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
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
		<f:js src="/js/date/WdatePicker.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			function check(btn, pass) {
				if (!FormUtils.hasSelected('withDrawBill.nos')) {
					alert('请选择需要审核的记录');
					return;
				}
				
				var remark = $('#remark').val();
				if (!pass && $.trim(remark) == '') {
					alert('审核不通过时请在备注中输入原因');
					return;
				}
				if(pass){
					$('#state').val("30");
				}else{
					$('#state').val("20");
				}
				$('#checkForm').submit();
			}
			function edit(td, canCheck, id){
				if(canCheck != 'Y'){
					return;
				}
				var fee = td.innerHTML;
				$(td).html("<input type='text' value='"+fee+"' onblur='setFee(this, "+id+")' style='width:50px;'/>");
				td.firstChild.focus();
			}
			function setFee(feeText, id){
				var feeVal = feeText.value;
				var pattern = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
				if(!pattern.test(feeVal)){
					alert('金额不合法');
					return;
				}
				feeVal = trim(feeVal);
				$.ajax({
					 type:'POST',
				     url:CONTEXT_PATH + '/businessSubAccount/withdraw/updateFee.do',
				     async:false,
				     data:{id:id, fee:feeVal},
					 success:function(data) {
				    	 //alert('修改成功');
					 },
					 error:function(data){   
	                     alert("修改失败");
	                     flag = 'error';
	                     return;
	                 }       
				 });
				var td = feeText.parentNode;
				$(td).html(feeVal);
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">录入日期</td>
							<td>
								<s:textfield name="withDrawBill.startCreateDate" onfocus="WdatePicker()" cssStyle="width:70px"></s:textfield> - <s:textfield name="withDrawBill.endCreateDate" onfocus="WdatePicker()" cssStyle="width:70px"></s:textfield>
							</td>
							<td align="right">金额</td>
							<td><s:textfield name="withDrawBill.amount" id="amount" cssClass="{number:true}"/></td> 
							<td align="right">状态</td>
							<td>
								<s:select list="withdrawStates" listKey="value" listValue="name" name="withDrawBill.state" headerKey="" headerValue="--请选择--"></s:select>
							</td> 
						</tr>
						<tr>
							<td align="right">虚户账号</td>
							<td><s:textfield name="withDrawBill.accountId" cssClass="{digitOrLetter:true}"/></td>
							<c:if test="${mer ne 'Y'}">
								<td align="right">客户编号</td>
								<td><s:textfield name="withDrawBill.custId" cssClass="{digitOrLetter:true}"/></td>
							</c:if>
						</tr>
						<tr> 
						<td></td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid = "bs_account_withdraw_add" >
									<input style="margin-left:30px;" type="button" value="新增"  name="escape" id="input_btn3" onclick="javascript:gotoUrl('/businessSubAccount/withdraw/showAdd.do');"/>
								</f:pspan>
							</td>
						</tr>
					</table>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<s:form action="check.do" id="checkForm">
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">
			    <f:pspan pid = "bs_account_withdraw_check">
			   		<input type="checkbox" id="checkAll" onclick="FormUtils.selectAll(this, 'withDrawBill.nos')"/>
			    </f:pspan>
			        提现单号
			   </td>
			   <td align="center" nowrap class="titlebg">
			   	虚户账号
			   </td>
			   <c:if test="${mer ne 'Y'}">
				   <td align="center" nowrap class="titlebg">客户编号/名称</td> 
			   </c:if>		
			   <td align="center" nowrap class="titlebg">提现金额</td>
			   <td align="center" nowrap class="titlebg">收款账号</td> 
			   <td align="center" nowrap class="titlebg">收款户名</td>			   
			   <td align="center" nowrap class="titlebg">录入时间</td>
			   <td align="center" nowrap class="titlebg">完成时间</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">手续费</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap><c:if test="${canCheck eq 'Y'}">
			   		<input type="checkbox" name="withDrawBill.nos" value="${no}"/></c:if>
			    ${no}
			  </td>
			  <td align="center" nowrap>${accountId}</td>
			  <c:if test="${mer ne 'Y'}">
				  <td align="center" nowrap>${custId}/${custName}</td>
			  </c:if>
			  <td align="center" nowrap>${fn:amount(amount)}</td>
			  <td align="center" nowrap>${bankCardNo}</td>
			  <td align="center" nowrap>${bankCardName}</td>
			  <td align="center" nowrap><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td align="center" nowrap><s:date name="finishTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td align="center" nowrap>${stateName}</td>
			  <c:if test="${canCheck eq 'Y'}">
			  	<td align="center" nowrap ondblclick="edit(this, '${canCheck}', '${no}')" title="双击修改手续费">${fn:amount(fee)}</td>
			  </c:if>
			  <c:if test="${canCheck ne 'Y'}">
			  	<td align="center" nowrap>${fn:amount(fee)}</td>
			  </c:if>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/businessSubAccount/withdraw/detail.do?withDrawBill.no=${no}">查看</f:link>
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		<f:pspan pid = "bs_account_withdraw_check">
			<div class="btnbox clear">
				<s:hidden id="state" name="withDrawBill.state" value=""></s:hidden>
				<input type="button" value="审核通过" onclick="check(this, true)"/>&nbsp;
				<input type="button" value="审核不通过" onclick="check(this, false)"/>
				备注<input type="text" name="withDrawBill.remark" id="remark" class="userbox_bt" maxlength="30"/>
			</div>
		</f:pspan>
		<s:token name="_TOKEN_WITHDRAW_CHECK" />
		</s:form>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>