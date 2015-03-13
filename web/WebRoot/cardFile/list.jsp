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
		
		<f:js src="/js/validate.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<f:js src="/js/biz/card/externalCardId.js?v=20141202"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){

			if('${loginRoleType}' == '00'){
				Selector.selectBranch('idBranchName', 'idBranchCode', true, '20');
			} else if('${loginRoleType}' == '01'){
				Selector.selectBranch('idBranchName', 'idBranchCode', true, '20', '', '', '${loginBranchCode}');
			}
				
			$('#input_btn3').click(function(){
				var maxRowCount = $('#id_maxRowCount').val();
				
				if(isEmpty(maxRowCount)){
					showMsg("请先查询数据。");
				} else if(maxRowCount==0){
					showMsg("没有数据下载。");
				} else if(maxRowCount > 65000){
					showMsg("数据太多，请缩小数据范围。");
				} else {
					$('#searchForm').attr('action', 'generate.do').submit();
					$('#searchForm').attr('action', 'list.do');
					$('#searchForm').find(":submit").attr('disabled', false);
					$('#searchForm').find(":button").attr('disabled', false);
				}
			});
		});

		//检查卡号
		function checkContent() {

			var branchCode = $('#idBranchCode').val();
			var cardBin = $('#id_cardBin').val();
			var loginType = $('#id_loginType').val();
			var strCardId = $('#id_startCardId').val();
			var endCardId = $('#id_endCardId').val();

			if(loginType=='00'||loginType=='01'||loginType=='02'){ //营运中心、中心部门、运营机构
				if( $("#useExternalCardSearch:checked").length>0 && !isEmpty($("#externalCardId").val()) ){
					return true;
				}
				if(isEmpty(branchCode) && isEmpty(cardBin) && (isEmpty(strCardId) && isEmpty(endCardId))){
					alert("请至少输入发卡机构，卡BIN或起止卡号查询条件。");
					return false;
				}
			}
			
			return (true && checkCardNoSearch());
		}
		
		function checkCardNoSearch(){
			var startCardId = $('#id_startCardId').val();
			var endCardId = $('#id_endCardId').val();
			
			// 结束卡号为空，直接返回
			if (isEmpty(endCardId)) {
				return true;
			}

			// 结束卡号不为空的话，开始卡号也不能为空
			if(!isEmpty(endCardId) && isEmpty(startCardId)){
				showMsg('开始卡号不能为空');
				return false;
			}
			
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
				
			//查询不能超过10万张
			if(Number(endCardId) - Number(startCardId)>100000000){
				showMsg("要查询的卡的张数不得超过1000万。");
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
				<s:form id="searchForm" action="list.do" cssClass="validate-tip" >
					<s:hidden id="id_loginType" name="loginRoleType" />
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>						
							<td align="right">开始卡号</td>
							<td>
								<s:textfield id="id_startCardId" name="startCardId" cssClass="{digit:true}"  maxlength="19" />
							</td>
							<td align="right">结束卡号</td>
							<td>
								<s:textfield id="id_endCardId" name="endCardId" cssClass="{digit:true}"  maxlength="19" />
							</td>
							<td align="right">发卡机构</td>
							<td>
								<s:if test="centerRoleLogined || fenzhiRoleLogined">
									<s:hidden id="idBranchCode" name="cardInfo.cardIssuer"/>
									<s:textfield id="idBranchName" name="branchName" />
								</s:if>
								<s:else>
									<s:select id="idBranchCode" name="cardInfo.cardIssuer" list="cardBranchList" 
										headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName" />
								</s:else>
							</td>
						</tr>
						<tr>
							<td align="right">卡BIN</td>
							<td>
								<s:textfield id="id_cardBin" name="cardInfo.cardBin" cssClass="{digit:true}" maxlength="6"/>
							</td>
							<td align="right">卡种类</td>
							<td>
								<s:select name="cardInfo.cardClass" list="cardTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td align="right">卡状态</td>
							<td>
								<s:select name="cardInfo.cardStatus" list="cardStatusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>	
							<td align="right">购卡客户ID</td>
							<td>
								<s:textfield name="cardInfo.cardCustomerId" cssClass="{digit:true}" />
							</td>
							<td align="right">
								<s:checkbox id='useExternalCardSearch' name="formMap.useExternalCardSearch" onclick="clickUseExternalCardSearch();"/>
								<label for="useExternalCardSearch">外部号码</label>
							</td>
							<td>
								<s:textfield id="externalCardId" name="cardInfo.externalCardId" cssClass="{digit:true}" />
							</td>

							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" onclick="return checkContent();"/>
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');$('#idBranchName').val('');$('#idBranchCode').val('');" name="escape" />
								<input style="margin-left:30px;" type="button" value="导出Excel" id="input_btn3"  name="ok" />
							</td>
						</tr>
					</table>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<s:hidden id="id_maxRowCount" name="page.maxRowCount"></s:hidden>
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td name="externalCardColName" align="center" nowrap class='titlebg <s:if test="formMap.useExternalCardSearch!='true'">no</s:if>'>
				   外部号码
			   </td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">卡种类</td>
			   <td align="center" nowrap class="titlebg">卡类型号</td>
			   <td align="center" nowrap class="titlebg">购卡客户ID</td>
			   <td align="center" nowrap class="titlebg">卡状态</td>
			   
			   <td align="center" nowrap class="titlebg">失效日期</td>
			   <td align="center" nowrap class="titlebg">操作</td>		   
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>			
			  <td align="center" nowrap>${cardId}</td>
			  <td name="externalCardColValue" align="center" nowrap <s:if test="formMap.useExternalCardSearch!='true'">class='no'</s:if> >
				${externalCardId}
			  </td>
			  <td>${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="center" nowrap>${cardClassName}</td>
			  <td align="center" nowrap>${cardSubclass}</td>
			  <td align="center" nowrap>${cardCustomerId}</td>
			  <td align="center" nowrap>${cardStatusName}</td>
			  
			  <td align="center" nowrap>${expirDate}</td>
			  <td align="center" nowrap>
			  <span class="redlink">
			  	<f:link href="/cardFile/detail.do?cardInfo.cardId=${cardId}">明细</f:link>
			  </span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		
		<s:if test="centerOrCenterDeptRoleLogined || fenzhiRoleLogined">
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
				<div class="contentb">
					<span class="note_div">注释</span>
					<ul class="showli_div">
					<li class="showli_div">请至少输入发卡机构或者卡BIN或同时输入开始卡号和结束卡号查询条件。</li>
					<li class="showli_div">如果需要查询卡号，必须同时输入开始卡号和结束卡号查询，且查询的张数不得超过1000万。</li>
					</ul>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
		</s:if>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>
