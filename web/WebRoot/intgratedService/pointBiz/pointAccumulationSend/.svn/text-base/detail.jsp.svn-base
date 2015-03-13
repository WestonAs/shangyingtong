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
		<f:js src="/js/paginater.js"/>
		
		<script type="text/javascript">
			/**
			 * ajax 审核
			 * isPass：true表示审核通过，false表示审核不通过
			 * isBatchSelect：true表示针对批量选择的记录，false表示针对所有待审记录
			 */
			function checkToPass(isPass, isBatchSelect){
				if (isBatchSelect && !FormUtils.hasSelected('batchIds')) {
					alert('请选择需要审核'+(isPass?'通过':'不通过')+'的记录！');
					return;
				}
				if (!isBatchSelect && !confirm("你确定要审核 全部待审核的记录？")){
					return;
				}
				$("input[type='button']").attr("disabled", "disabled");
				$("#detailDataDiv").hide();
				$("#loadingBarDiv").show();
				
				$('#pointSendDetailForm').attr("action", "checkToPass.do");
				$('#pointSendDetailForm').prepend('<input type="hidden" name="pass" value="' + isPass + '" />');
				$('#pointSendDetailForm').prepend('<input type="hidden" name="batchSelect" value="' + isBatchSelect + '" />');
				$('#pointSendDetailForm').prepend('<input type="hidden" name="pageSize" value="' + $("#pageSize_0").val() + '" />');
				$('#pointSendDetailForm').prepend('<input type="hidden" name="pageNumber" value="' + $("#goPageIndex_0").val() + '" />');
				$('#pointSendDetailForm').submit();
			}
			
			/** 赠送处理 */
			function sendPointProcess(){
				if (!confirm("你确定要开始赠送处理？")){
					return;
				}
				$("input[type='button']").attr("disabled", "disabled");
				$.getJSON(CONTEXT_PATH + "/intgratedService/pointBiz/pointAccumulationSend/ajaxPreSendPointCheck.do"
						,{'pointSendApplyReg.applyId':$("#applyId").val(), 'callId':callId()}, 
						function(json){
							if(json.waitedDetailCnt>0){
								alert("还有待审核的明细记录，请先做审核处理！");
								$("input[type='button']").removeAttr("disabled");
							}else{
								$('#pointSendDetailForm').attr("action", "sendPointProcess.do");
								$('#pointSendDetailForm').attr("method", "post");
								$('#pointSendDetailForm').submit();
							}
						}
				);
			}
		</script>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
				<caption>积分累积赠送详细信息<span class="caption_title"> | <f:link href="/intgratedService/pointBiz/pointAccumulationSend/list.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>申请ID</td>
					<td>${pointSendApplyReg.applyId}</td>
					<td>发卡机构</td>
					<td>${pointSendApplyReg.cardIssuer}-${fn:branch(pointSendApplyReg.cardIssuer)}</td>
					<td>积分类型</td>
					<td>${pointSendApplyReg.ptClass}</td>
			  	</tr>
				<tr>
					<td>开始累积日期</td>
					<td>${pointSendApplyReg.beginDate}</td>
					<td>结束累积日期</td>
					<td>${pointSendApplyReg.endDate}</td>
					<td>累积记分阀值</td>
					<td>${pointSendApplyReg.thresholdValue}</td>
			  	</tr>
				<tr>
					<td>赠送积分</td>
					<td>${pointSendApplyReg.sendPoint}</td>
					<td>备注</td>
					<td>${pointSendApplyReg.remark}</td>
					<td>状态</td>
					<td>${pointSendApplyReg.statusName}</td>
			  	</tr>
			  	<tr>
					<td>更新时间</td>
					<td><s:date name="pointSendApplyReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>后台处理结果注释</td>
					<td colspan="3">${pointSendApplyReg.memo}</td>
			  	</tr>
			</table>
		</div>
		<div class="tablebox" > 
			<b><s:label>赠送列表 </s:label></b>
			<s:if test='pointSendApplyReg.status=="01"'> <!-- 汇总成功 -->
				<div style="margin: 10px;">
					<f:pspan pid="pointAccumulationSend_checkToPass">
						<span class="ml30">批量选择：</span>
						<input value="审核通过" title="批量选择审核通过" onclick="checkToPass(true, true)"  type="button" />
						<input value="审核不通过" title="批量选择 审核不通过"  onclick="checkToPass(false, true)" class="ml30" type="button" />
						<span class="ml30">所有待审核的记录：</span>
						<input value="审核通过"  title="所有待审核的记录 审核通过" onclick="checkToPass(true, false)"   type="button" />
						<input value="审核不通过" title="所有待审核的记录 审核不通过" onclick="checkToPass(false, false)" class="ml30"  type="button" />
					</f:pspan>
					<f:pspan pid="pointAccumulationSend_sendPointProcess">
						<span class="ml30">审核完毕后，赠送：</span>
						<input value="赠送处理" title="对审核通过对记录进行赠送" onclick="sendPointProcess(this)"   type="button" />
					</f:pspan>
				</div>
			</s:if>
			<div id="detailDataDiv">
				<form id="pointSendDetailForm" method="get" action="detail.do">
					<input type="hidden" name="pointSendApplyReg.applyId" id="applyId" value="${pointSendApplyReg.applyId}" />
					<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">			
						<thead>
							<tr>
							   <s:if test='pointSendApplyReg.status=="01"'> <!-- 汇总成功 -->
								   <td align="center" nowrap class="titlebg">
										<label><input type="checkbox" onclick="FormUtils.selectAll(this, 'batchIds')" /> 全选</label>
								   </td>
							   </s:if>
							   <td align="center" nowrap class="titlebg">申请ID</td>
							   <td align="center" nowrap class="titlebg">明细行号</td>
							   <td align="center" nowrap class="titlebg">卡号</td>			   
							   <td align="center" nowrap class="titlebg">发卡机构</td>			   
							   <td align="center" nowrap class="titlebg">积分类型</td>			   
							   <td align="center" nowrap class="titlebg">当前账户积分</td>			   
							   <td align="center" nowrap class="titlebg">赠送积分</td>			   
							   <td align="center" nowrap class="titlebg">状态</td>			   
							   <td align="center" nowrap class="titlebg">备注</td>			   
							   <td align="center" nowrap class="titlebg">更新时间</td>			   
							</tr>
						</thead>
						<s:iterator value="page.data"> 
							<tr>
							  <s:if test='pointSendApplyReg.status=="01"'> <!-- 汇总成功 -->
								  <td align="center">
								  	<input type="checkbox" name="batchIds" <s:if test="batchIds.contains(batchId)">checked='checked'</s:if> value="${batchId}" />
								  </td>
							  </s:if>
							  <td align="center" nowrap>${applyId}</td>
							  <td align="center" nowrap>${batchId}</td>
							  <td align="center" nowrap>${cardId}</td>
							  <td align="center" nowrap>${fn:branch(pointSendApplyReg.cardIssuer)}</td>
							  <td align="center" nowrap>${ptClass}</td>	  
							  <td align="center" nowrap>${curAccuPt}</td>	  
							  <td align="center" nowrap>${sendPoint}</td>	  
							  <td align="center" nowrap>${statusName}</td>	  
							  <td align="center" nowrap>${remark}</td>	  
							  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>	  
							</tr>
						</s:iterator>
					</table>
				</form>
				<f:paginate name="page"/>
			</div>
			<div id="loadingBarDiv"	style="display: none; width: 100%; height: 100%">
				<table width=100% height=100%>
					<tr>
						<td align=center >
							<center>
								<img src="${CONTEXT_PATH}/images/ajax_saving.gif" align="middle" />
								<div id="processInfoDiv"
									style="font-size: 15px; font-weight: bold">
									正在处理中，请稍候...
								</div>
								<br/>
								<br/>
								<br/>
							</center>
						</td>
					</tr>
				</table>
			</div>
			
			<span class="note_div">注释</span>
			<ul class="showli_div">
				<li class="showli_div">审核完毕后，才能做赠送处理，且“赠送处理”只能操作一次！</li>
			</ul>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>