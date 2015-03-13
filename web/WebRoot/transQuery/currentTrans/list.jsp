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
		<f:js src="/js/common.js?v=20130802"/>
		<f:js src="/js/paginater.js"/>
		
		<f:js src="/js/biz/card/externalCardId.js?v=20141202"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				$('#transType').change(changeTransType);
				$('#exportCsvBtn').click(function (){generateFile()});
				
				$('#transType').change();//初始调用
			});
			
			function changeTransType(){
				if($("#transType").val()=="03"){//转账
					$("[id^='eiaCardIdT']").show();
				}else{
					$("#eiaCardId").val("");
					$("[id^='eiaCardIdT']").hide();
				}
			}
			
			/** 下载 产生 交易明细文件 */
			function generateFile(){
				$.getJSON(CONTEXT_PATH + "/transQuery/currentTrans/ajaxIsGeneratingFile.do",{'callId':callId()}, 
					function(json){
						if("1"==json.returnCode){
							alert("正在生产当日交易明细文件，请耐心等待...");
						}else{
							var action = "generate.do";
							// 提交产生excel的请求
							$('#searchForm').attr('action', action).submit();
							$('#searchForm').attr('action', 'list.do');
							$('#searchForm').find(":submit").attr('disabled', false);
							$('#searchForm').find(":button").attr('disabled', false);
						}
					}
				);
			}
		</script>
		
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">系统跟踪号</td>
							<td><s:textfield name="trans.sysTraceNum" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">卡号</td>
							<td><s:textfield name="trans.cardId" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">处理状态</td>
							<td>
							<s:select name="trans.procStatus" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">发起方编号</td>
							<td><s:textfield name="trans.merNo"/></td>
							<td align="right">发起方名称</td>
							<td><s:textfield name="trans.issuerName"/></td>
							<td align="right">终端号</td>
							<td><s:textfield name="trans.termlId"/></td>
						</tr>
						<tr>
							<s:if test="('20'.equals(loginRoleType) || '23'.equals(loginRoleType) ) ">
								<td align="right">发卡机构</td>
								<td>
									<s:select name="trans.cardIssuer" list="cardIssuerList" listKey="branchCode" listValue="branchName" headerKey="" headerValue="--请选择--" onmouseover="FixWidth(this)"></s:select>
								</td>
							</s:if>
							<s:else>
								<td align="right">发卡机构编号</td>
								<td><s:textfield name="trans.cardIssuer"/></td>
								<td align="right">发卡机构名称</td>
								<td><s:textfield name="trans.branchName"/></td>
							</s:else>
							<td align="right">交易类型</td>
							<td>
							<s:select id="transType" name="trans.transType" list="transType" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">交易流水</td>
							<td><s:textfield name="trans.transSn"/></td>
							<td align="right">
								<s:checkbox id='useExternalCardSearch' name="formMap.useExternalCardSearch" onclick="clickUseExternalCardSearch();"/>
								<label for="useExternalCardSearch">外部号码</label>
							</td>
							<td>
								<s:textfield id="externalCardId" name="trans.reserved4" cssClass="{digit:true}" />
							</td>
							<td>&nbsp;</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<input style="margin-left:30px;" type="button" value="导出Csv" id="exportCsvBtn"  name="ok" />
							</td>
						</tr>
						<tr id="eiaCardIdTr">
							<td align="right">转入卡号</td>
							<td colspan="11">
								<s:textfield id="eiaCardId" name="trans.eiaCardId" cssClass="{num:true}" maxLength="19"/>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CURRENTTRANS_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">系统跟踪号</td>
			   <td align="center" nowrap class="titlebg">交易流水</td>
			   <td align="center" nowrap class="titlebg">清算日期</td>
			   <td align="center" nowrap class="titlebg">处理时间</td>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   
			   <td name="externalCardColName" align="center" nowrap class='titlebg <s:if test="formMap.useExternalCardSearch!='true'">no</s:if>'>
				   外部号码
			   </td>
			   <td id="eiaCardIdTd" align="center" nowrap class="titlebg">转入卡号</td>
			   <td align="center" nowrap class="titlebg">交易类型</td>
			   <td align="center" nowrap class="titlebg">交易金额/次数</td>
			   <td align="center" nowrap class="titlebg">清算金额</td>
			   <td align="center" nowrap class="titlebg">处理状态</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">发起方</td>
			   <td align="center" nowrap class="titlebg">终端号</td>
			   <s:if test="#session.SESSION_USER.role.roleType!=40">
			   <td align="center" nowrap class="titlebg">操作</td>
			   </s:if>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${sysTraceNum}</td>
			  <td align="center" nowrap>${transSn}</td>
			  <td align="center" nowrap>${settDate}</td>
			  <td align="center" nowrap><s:date name="rcvTime" format="MM-dd HH:mm:ss"/></td>	
			  <td align="center" nowrap>${cardId}</td>
			  <td name="externalCardColValue" align="center" nowrap <s:if test="formMap.useExternalCardSearch!='true'">class='no'</s:if> >
				${reserved4}
			  </td>
			  <td id="eiaCardIdTd" align="center" nowrap>${eiaCardId}</td>
			  <td align="center" nowrap>${transTypeName}</td>
			  <td align="right" nowrap>${fn:amount(transAmt)}</td>	
			  <td align="right" nowrap>${fn:amount(settAmt)}</td>	
			  <td align="center" nowrap>${procStatusName}</td>		
			  <td align="left" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>		  
			  <td align="left" nowrap>${merNo}-${fn:branch(merNo)}${fn:merch(merNo)}</td>		
			  <td align="center" nowrap>${termlId}</td>	
			  <s:if test="#session.SESSION_USER.role.roleType!=40">
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<a href="javascript:openContextDialog('/transQuery/currentTrans/detail.do?trans.transSn=${transSn}');">明细</a>
			  	</span>
			  </td>
			  </s:if>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>