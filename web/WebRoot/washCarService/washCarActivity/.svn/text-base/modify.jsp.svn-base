<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
		    $(function(){
		        
				Selector.selectMerch('idMerchId_sel', 'idMerchId', true, '','${loginBranchCode}');
				var selectCycleId = $('#selectCycleId').val();
				if(selectCycleId == 'Y'){
				   $('#washCarSelectId').show();
				}
				$('#selectCycleId').change(function(){
				    
				    var cycleId = $(this).val();
				    if(cycleId == 'Y'){
				       $('#washCarSelectId').show();
				    }else{
				       $('#washCarSelectId').hide();
				       $('#monFlag').val('');
				       $('#monNum').val('');
				    }
				});
				
				$('#monFlag').change(function(){
				    var monNum = $('#monNum').val();
				   
				    if(monNum == '' || monNum == null){
				       $('#monFlag').val('');
				       showMsg("必须要填写了月次数,此选择才能生效");
				    }
				});
				
				$('#monNum').blur(function(){
				    hideMsg();
				});
			});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					    <s:hidden name="washCarActivity.cardIssuer" />
					    <s:hidden name="washCarActivity.merchId" />
						<tr> 
						    <s:if test="merchantRoleLogined">
							</s:if>
						    <s:else>
							    <td align="right">商户</td>
								<td>
								    <s:textfield name="merchName" cssClass="readonly" readonly="true"/>
								</td>
							</s:else>
						</tr>
						<tr>
						    <td align="right">活动编号</td>
							<td>
							    <s:hidden id="" name="" />
							    <s:textfield id="activityName" name="washCarActivity.activityId" cssClass="readonly" readonly="true"/>
							</td>
						    <td align="right">活动名称</td>
							<td>
							    <s:hidden id="" name="" />
							    <s:textfield id="activityName" name="washCarActivity.activityName" cssClass="{required:true}"/>
							    <span class="field_tipinfo">请输入活动名称</span>
							</td>
						</tr>
						<tr>
						    <td align="right">洗车周期</td>
							<td>
							    <s:select id="selectCycleId" name="washCarActivity.washCarCycle" list="washCarCycleList" headerKey="" headerValue="--请选择--" 
							    listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							    <span class="field_tipinfo">请选择周期</span>
							</td>
						    <td align="right">总次数</td>
							<td>
							    <s:textfield name="washCarActivity.totalNum" cssClass="{required:true,digit:true}"/>
							    <span class="field_tipinfo">请输入总次数,必须为整数形式</span>
							</td>
						</tr>
						<tr id="washCarSelectId" style="display:none;">
							<td align="right">限制每月次数</td>
							<td>
							    <s:textfield id="monNum" name="washCarActivity.limitMonthNum" cssClass="{digit:true}"/>
							    <span class="field_tipinfo">周期年洗才生效,空表示不限,请输入整数</span>
							</td>
							<td align="right">当月不洗是否作废</td>
							<td>
							    <s:select id="monFlag" name="washCarActivity.whetherInvalid" list="washTherInvalIdList" headerKey="" headerValue="--请选择--" 
							    listKey="value" listValue="name"></s:select>
							    <span class="field_tipinfo">年洗并限制月次数才生效</span>
							</td>
						</tr>
						<tr>
						    <td align="right">备注</td>
							<td>
							    <s:textfield name="washCarActivity.remark" />
							    <span class="field_tipinfo">备注信息</span>
							</td>
						</tr>
					   
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/washCarService/washCarActivity/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_WHSHCARACTIVITY_MODIFY"/>
					</div>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>