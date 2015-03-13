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
				if (!FormUtils.hasSelected('transBill.ids')) {
					alert('请选择需要审核的记录');
					return;
				}
				var remark = $('#remark').val();
				if (!pass && $.trim(remark) == '') {
					alert('审核不通过时请在备注中输入原因');
					return;
				}
				if(pass){
					$('#checkPass').val("Y");
				}else{
					$('#checkPass').val("N");
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
				     url:CONTEXT_PATH + '/businessSubAccount/transfer/updateFee.do',
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
								<s:textfield name="transBill.startCreateDate" onfocus="WdatePicker()" cssStyle="width:70px"></s:textfield> - <s:textfield name="transBill.endCreateDate" onfocus="WdatePicker()" cssStyle="width:70px"></s:textfield>
							</td>
							<td align="right">金额</td>
							<td><s:textfield name="transBill.amount" id="amount"/></td> 
							<td align="right">状态</td>
							<td>
								<s:select list="transStates" listKey="value" listValue="name" name="transBill.state" headerKey="" headerValue="--请选择--"></s:select>
							</td> 
						</tr>
						<tr>
							<td align="right">付款账号</td>
							<td><s:textfield name="transBill.payerAccountId" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">收款账号</td>
							<td><s:textfield name="transBill.payeeAccountId" cssClass="{digitOrLetter:true}"/></td>
						</tr>
						<tr> 
						<td></td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid = "bs_account_transfer_add" >
									<input style="margin-left:30px;" type="button" value="新增"  name="escape" id="input_btn3" onclick="javascript:gotoUrl('/businessSubAccount/transfer/showAdd.do');"/>
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
			   <f:pspan pid = "bs_account_transfer_check">
			   		<input type="checkbox" id="checkAll" onclick="FormUtils.selectAll(this, 'transBill.ids')"/>
			       </f:pspan>  转账单号</td>
			   <td align="center" nowrap class="titlebg">
			   	付款账号
			   </td>
			   <c:if test="${mer ne 'Y'}">
				   <td align="center" nowrap class="titlebg">付款客户编号/名称</td> 
			   </c:if>		
			   <td align="center" nowrap class="titlebg">转账金额</td>
			   <td align="center" nowrap class="titlebg">收款账号</td> 
			   <td align="center" nowrap class="titlebg">收款客户编号/名称</td>
			   <td align="center" nowrap class="titlebg">是否跨体系</td>			   
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
			   		<input type="checkbox" name="transBill.ids" value="${id}"/>
			    </c:if>${id}</td>
			  <td align="center" nowrap>
			    ${payerAccountId}
			  </td>
			  <c:if test="${mer ne 'Y'}">
				  <td align="center" nowrap>${payerCustId}/${payerCustName}</td>
			  </c:if>
			  <td align="center" nowrap>${fn:amount(amount)}</td>
			  <td align="center" nowrap>${payeeAccountId}</td>
			  <td align="center" nowrap>${payeeCustId}/${payeeCustName}</td>
			  <td align="center" nowrap>
			    <c:if test="${crossSystem eq 'Y'}">是</c:if>
				<c:if test="${crossSystem eq 'N'}">否</c:if>
			  </td>
			  <td align="center" nowrap><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td align="center" nowrap><s:date name="finishTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td align="center" nowrap>${stateName}</td>
			  <c:if test="${canCheck eq 'Y' and crossSystem eq 'Y'}">
			  	<td align="center" nowrap ondblclick="edit(this, '${canCheck}', '${id}')" title="双击修改手续费">${fn:amount(fee)}</td>
			  </c:if>
			  <c:if test="${canCheck ne 'Y' or crossSystem ne 'Y'}">
			  	<td align="center" nowrap>${fn:amount(fee)}</td>
			  </c:if>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/businessSubAccount/transfer/detail.do?transBill.id=${id}">查看</f:link>
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		<f:pspan pid = "bs_account_transfer_check">
			<div class="btnbox clear">
				<s:hidden id="checkPass" name="transBill.checkPass" value=""></s:hidden>
				<input type="button" value="审核通过" onclick="check(this, true)"/>&nbsp;
				<input type="button" value="审核不通过" onclick="check(this, false)"/>
				备注<input type="text" name="transBill.remark" id="remark" class="userbox_bt" maxlength="30"/>
			</div>
		</f:pspan>
		<s:token name="_TOKEN_TRANS_CHECK" />
		</s:form>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>