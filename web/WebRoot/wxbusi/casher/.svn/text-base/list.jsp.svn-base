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
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){

			/*$('#id_startCardId').blur(function(){
				var startCardId = $('#id_startCardId').val();
				var endCardId = $('#id_endCardId').val();
				
				if(!isEmpty(startCardId) && !checkIsInteger(startCardId)){
					$('#id_startCardId').val('');
				}

				if(!isEmpty(startCardId) && !isEmpty(endCardId)){
					//起始卡号和结束卡号位数要一致
					if(startCardId.length!=endCardId.length){
						showMsg("开始卡号和结束卡号的位数不一致。");
						//$('#id_endCardId').val('');
						$(':submit').attr('disabled', 'true');
					}
					else {
						if(endCardId<startCardId){ // 判断结束卡号是否大于等于开始卡号
							showMsg("结束卡号要大于等于开始卡号。");
							$(':submit').attr('disabled', 'true');
							return;
						}
						
						//查询不能超过10万张
						if(Number(endCardId)-Number(startCardId)>1000000){
							showMsg("不能查询超过100万张卡。");
							$(':submit').attr('disabled', 'true');
						}
						else {
							$(':submit').removeAttr('disabled');
						}
					}
				}
			});
			
			$('#id_endCardId').blur(function(){
				var startCardId = $('#id_startCardId').val();
				var endCardId = $('#id_endCardId').val();
				
				if(isEmpty(startCardId)){ // 判断是否输入开始卡号
					showMsg("请先输入开始卡号。");
					$('#id_endCardId').val('');
					return;
				}
				
				if(!isEmpty(endCardId) && !checkIsInteger(endCardId)){ // 判断结束卡号是否为数字
					showMsg("结束卡号请输入数字。");
					$('#id_endCardId').val('');
					return;
				}
				
				if(!isEmpty(startCardId) && !isEmpty(endCardId)){
					//起始卡号和结束卡号位数要一致
					if(startCardId.length!=endCardId.length){
						showMsg("开始卡号和结束卡号的位数不一致。");
						//$('#id_endCardId').val('');
						$(':submit').attr('disabled', 'true');
					}
					else {
						if(endCardId<startCardId){ // 判断结束卡号是否大于等于开始卡号
							showMsg("结束卡号要大于等于开始卡号。");
							$(':submit').attr('disabled', 'true');
							return;
						}
						
						//查询不能超过10万张
						if(Number(endCardId)-Number(startCardId)>1000000){
							showMsg("不能查询超过100万张卡。");
							$(':submit').attr('disabled', 'true');
						}
						else {
							$(':submit').removeAttr('disabled');
						}
					}
				}
			});*/
			
		});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="form_grid" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">机构/商户号</td>
							<td>
								<s:textfield id="id_MerchNo" name="wxCashierInfo.merchNo" cssClass="{digit:true}"  maxlength="15" />
							</td>
							<td align="right">收银用户标识</td>
							<td>
								<s:textfield id="id_CashierExtid" name="wxCashierInfo.cashierExtid" cssClass="{digit:true}"  maxlength="32" />
							</td>
							<td align="right">账号分户</td>
							<td>
								<s:textfield id="id_AcctSubject" name="wxCashierInfo.acctSubject" cssClass="{digit:true}"  maxlength="32" />
							</td>
						</tr>
						<tr>	
							<td align="right">创建日期</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>
								&nbsp;&nbsp;-&nbsp;&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td align="right">收银员状态</td>
							<td>
								<s:select name="wxCashierInfo.status" list="wxCashierStateList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td align="right">&nbsp;</td>
							<td align="right">&nbsp;</td>
						</tr>
						<tr>
							<td height="30" colspan="6" align = "center">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_EXPIRECARD_LIST" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">机构/商户号</td>
			   <td align="center" nowrap class="titlebg">收银员用户标识</td>
			   <td align="center" nowrap class="titlebg">账号分户</td>
			   <td align="center" nowrap class="titlebg">收银员姓名</td>
			   <td align="center" nowrap class="titlebg">创建时间</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td nowrap>${merchNo}</td>
			  <td align="center" nowrap>${cashierExtid}</td>
			  <td align="center" nowrap>${acctSubject}</td>
			  <td align="center" nowrap>${cashierName}</td>
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td align="center" nowrap>${statuName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/wxbusi/casher/detail.do?wxCashierInfo.merchNo=${merchNo}&wxCashierInfo.acctSubject=${acctSubject}&wxCashierInfo.cashierExtid=${cashierExtid}">
			  		明细</f:link>	
			  	</span>
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
				<li class="showli_div">请至少输入机构/商户号/收银用户标识/账号分户。</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>
  
